## MethodHandle "Function Pointers on Steroids"

For the Java Virtual machine, before JSR 292, there were two ways to call a method:
1. Standard call, that uses the Java semantics
2. Reflection call (`java.lang.reflect`), that find the method at runtime,
   check the security when callled, boxed the arguments.

The `java.lang.invoke` package provides a new way to call methods:
- the creation of a`MethodHandle` is done at runtime, not compile time.
- the security is checked once at creation time,
- the arguments are not boxed.

Furthermore, a `MethodHandle` is seen both as a function pointer and as native code
that is inlined in the calling code by the JIT (if the method handle is a constant).


### The API

A. MethodType

An immutable class that defines the "shape" of the method, return type and parameter types.
Like constant strings, two method types with the same shape are the same (have the same adress in memory).

```java
// A method that takes (String, int) and returns void
MethodType type = MethodType.methodType(void.class, String.class, int.class);

MethodType type2 = MethodType.methodType(void.class, String.class, int.class);
IO.println(type == type2);  // true 
```

B. MethodHandles.Lookup

The factory for creating method handles. It enforces access checks (class modifiers, method modifiers, etc)
at the moment of creation, not execution.

```java
// create a Lookup object for the current class
MethodHandles.Lookup lookup = MethodHandles.lookup();

IO.println(lookup.lookupClass());  // the current class
```

The way to create a method handle is to use the Lookup object's findVirtual, findStatic, findSpecial,
or findConstructor methods, depending on the type of method you want to reference.

For example, to create a method handle to the method String::concat:
```java
MethodHandle mh = lookup.findVirtual(String.class, "concat", MethodType.methodType(String.class, String.class));
```

The type of the method handle is available via the method `methodHandle.type()`.

```java
MethodHandle mh = lookup.findVirtual(String.class, "concat", MethodType.methodType(String.class, String.class));
IO.println(mh.type());  // MethodType{String (String, tring)}
```

A method handle can be invoked using the method `invokeExact`, which requires exact type matching.
This is the most efficient way to invoke a method handle (pointers comparison + call).

```java
MethodHandle mh = lookup.findVirtual(String.class, "concat", MethodType.methodType(String.class, String.class));
String result = (String) mh.invokeExact("Hello ", "World");
IO.println(result);
```

Alternatively, the method `invoke` can be used for invocation with conversions/boxing,
which allows for more flexible type matching but is slower.

```java
MethodHandle mh = lookup.findVirtual(String.class, "concat", MethodType.methodType(String.class, String.class));
Object result = mh.invoke("Hello ", "World");  // convert String to Object
IO.println(result);
```

If the method type does not match, an exception `WrongMethodTypeException` is thrown.


C. MethodHandle combinations

The API allows combining method handles using the following methods:

- `asType`:
  Casts the method handle arguments/return value to new types.

```java
MethodHandle mh = lookup.findVirtual(String.class, "concat", MethodType.methodType(String.class, String.class));
MethodHandle mh2 = mh.asType(MethodType.methodType(Object.class, Object.class, Object.class));

Object arg0 = "Hello ";
Object arg1 = "World";
Object result = mh2.invokeExact(arg0, arg1);
IO.println(result);  // Hello World
```

We can note that `invoke()` is equivalent to `asType()` + `invokeExact()`.

- `constant`:
  Creates a method handle that returns a constant value.

```java
MethodHandle mh = MethodHandles.constant(String.class, "Hello World");

String result = (String) mh.invokeExact();
IO.println(result);  // Hello World
```

- `insertArguments`:
  Inserts arguments at a position.

```java
MethodHandle mh = lookup.findVirtual(String.class, "concat", MethodType.methodType(String.class, String.class));
MethodHandle mh2 = mh.insertArguments(1, "World");

String result = (String) mh2.invokeExact("Hello ");  // only one argument !
IO.println(result);  // Hello World
```

If there is only one argument which is not a primitive type, then `MethodHandle.bindTo()` can be used instead.

- `dropArguments`:
  Adds a new argument that will be unused.

```java
MethodHandle mh = lookup.findVirtual(String.class, "concat", MethodType.methodType(String.class, String.class));
MethodHandle mh2 = mh.dropArguments(0, int.class);

String result = (String) mh2.invokeExact(42, "Hello ", "World");  // 42 is unused
IO.println(result);  // Hello World
```

This method is used when the method signature has to be a specific shape (method type),
see the example of `guardWithTest()` below.

- `isVarargsCollector()`:
  A method type does not encode whether the method accepts a variable number of arguments,
  but it can be determined by checking if the method handle is a varargs collector.

`asType()` can then be used to change the method signature to accept several arguments
that will be collected into an array.

```java
MethodHandle mh = lookup.findStatic(Arrays.class, "asList", MethodType.methodType(List.class, Object[].class));
IO.println(mh.isVarargsCollector());  // true

MethodHandle mh2 = mh.asType(MethodType.methodType(List.class, String.class, String.class));
IO.println(mh2.isVarargsCollector());  // false

var result = mh2.invokeExact("Hello", "World");
IO.println(result);  // [Hello, World]
```

- `withVarargs(boolean)` and `asVarargsCollector`:
  Converts a method handle to accept (or not) a variable number of arguments.

When a method handle is created from an existing method handle, it loses its varargs status.
This method can be used to convert a method handle back to a varargs collector.

```java
MethodHandle mh = lookup.findStatic(Arrays.class, "asList", MethodType.methodType(List.class, Object[].class));
IO.println(mh.isVarargsCollector());  // true
MethodHandle mh2 = MethodHandles.dropArgument(mh, 0, int.class);
IO.println(mh2.isVarargsCollector());  // false

MethodHandle mh3 = mh2.withVarargs(true);    // or mh2.asVarargsCollector(Object[].class);
IO.println(mh3.isVarargsCollector());  // true

List<String> result = (List<String>) m3.invoke(42, "Hello", "World");  // invoke not invokeExact !
IO.println(result);  // [Hello, World]
```

- `guardWithTest`:
  Creates a new method handle that calls either a method handle (the target) or the another method handle
  (the fallback) if the test condition is true.

It acts as a conditional branch, an `if` statement.

The target method handle and the fallback method handle must have the same shape,
the test method handle also has types of parameters but returns a boolean.

```java
MethodHandle test = lookup.findVirtual(String.class, "isEmpty", MethodType.methodType(boolean.class));
MethodHandle target = lookup.findVirtual(String.class, "toUpperCase", MethodType.methodType(String.class));
MethodHandle fallback = MethodHandles.dropArguments(MethodHandles.constant(String.class, "BOOM!"), 0, String.class);
MethodHandle guarded = MethodHandles.guardWithTest(test, target, fallback);

String result = (String) guarded.invokeExact("hello");
IO.println(result);  // HELLO 
```

There are a lot of other methods that emulate existing bytcodes in the API.
See the [Javadoc](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/invoke/MethodHandles.html)
for more details.


## The "Dynamic" Problem

### Invokedynamic

The bytecode of the virtual machine is typed, which means that the JVM cannot generate a standard bytecode instruction
for a dynamic language.

By example, in Java, the following code:
```java
int x = 10;
int y = 20;
int result = x + y;
```

can be directly transalted to the following Java bytecode:
```bytecode
ldc 10
ldc 20
invokestatic java/lang/Integer "sum" (II)I
```

But in a dynamic language, like JavaScript, the following code:
```js
var x = 10;
var y = 20;
var result = x + y;
```

if translated to Java bytecode
```bytecode
ldc 10
invokestatic java/lang/Integer "valueOf" (I)Ljava/lang/Integer;  // box the int
ldc 20
invokestatic java/lang/Integer "valueOf" (I)Ljava/lang/Integer;  // box the int
invokestatic java/lang/Integer "sum" (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
```
it will not work because the JVM will reject the last call because there is no method
`java.lang.Integer.sum(Object, Object)` in the API.


To solve that problem, the JSR 292 introduced a new instruction called `invokedynamic`.
It allows the JVM to generate a standard bytecode instruction for a dynamic language.

```bytecode
ldc 10
invokestatic java/lang/Integer "valueOf" (I)Ljava/lang/Integer;
ldc 20
invokestatic java/lang/Integer "valueOf" (I)Ljava/lang/Integer;
invokedynamic "+" (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; BSM:bootstrap
```

Unlike the `invokestatic` instruction, the `invokedynamic` instruction requires a bootstrap method
to be called before the actual call.
The bootstrap method is responsible for finding the method to call and do the adaptation of the arguments
using method handles.

```java
// called once at runtime per invokedynamic instruction
public static CallSite bootstrap(MethodHandles.Lookup lookup, String name, MethodType type) {
  // find the method to call
  var mh = lookup.findStatic(Integer.class, "sum", MethodType.methodType(int.class, int.class, int.class));
  
  // adapt the method signature to accept Objects
  var target = mh.asType(type);
  
  return new ConstantCallSite(target);
}
```

At runtime, the JVM will call the bootstrap method and will pass the lookup object,
the name of the method and the method type.
The bootstrap method will return a `CallSite` object that will be used by the interpreter of the JVM
to call the method handle.

Then when the JIT compiles the code, because the method handle inside the `CallSite` is a constant,
the JVM will inline the method handle at callsite and generate the assembly code.


### Constant Dynamic

Constant Dynamic is a variant of invokedynamic that allows the JVM to generate a standard bytecode instruction
for the constants of a dynamic language, like 10 and 20 in the example.

So instead of:
```bytecode
ldc 10
invokestatic java/lang/Integer "valueOf" (I)Ljava/lang/Integer;
ldc 20
invokestatic java/lang/Integer "valueOf" (I)Ljava/lang/Integer;
invokedynamic "+" (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; BSM:bootstrap
```

using the constant dynamic constants, we can have:
```bytecode
ldc constantdynamic BSM:constant(10)
ldc constantdynamic BSM:constant(20)
invokedynamic "+" (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; BSM:bootstrap
```

With the bootstrap method:
```java
// called once at runtime per constant (one for 10, one for 20)
public static Object constant(MethodHandles.Lookup lookup, String name, Class<?> type, int value) {
  var result = Integer.valueOf(value);
  return result;
}
```

The bootstrap method returns the constant value directly.
