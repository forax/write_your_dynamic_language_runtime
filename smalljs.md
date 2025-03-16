smalljs
---

the smalljs programming language is mostly a subset of JavaScript.


semantics
---

smalljs has 4 kinds of values, 3 primitive types and one reference type
- undefined, 
- int, a primitive 32 bits signed integers
- String, a character string (with 31 bits max size)
- JSObject, any objects

A JSObject is a dictionary that associate a String to a value
there are 3 sub-kinds of JSObject
- function,
  has 2 special keys, '__code__' that returns the opcodes of the function or 'undefined' if the function is native (+, -, ==, etc are native functions) and 'apply' which contains the function itself so 'f(3)' is equivalent to 'f.apply(3)'.
- environment,
  which associate a String to a function.
  When a function is declared with a name, the name is stored into the global environment.
  The way to access to the predefined environment which contains all the native functions is to use the name 'global'.
- user defined objets
  has a special key 'proto'

operators precedence
--
the usual rules applied, * is chosen instead of +, etc

no boolean
--
the language has no real booleans so the int 0 and null are false, everything else is true

local variable resolution
--
when resolving the name of a variable, the interpreter first search for a local variable with the name, if it doesn't exist it looks for the name in the global environment otherwise an error is raised.
For example
```
var a = foo;   // no local variable 'foo', so equivalent to global.foo
a              // local variable 'a'
```

field resolution
--
when accessing to a field, the interpreter first lookup up into the current instance, then into its prototype (using the field 'proto' recursively until the field 'proto' is null) otherwise 'undefined' is returned.

matching of parameters/arguments during a function call
--
if the number of arguments doesn't match the number of parameters, an error is reported.
each parameter is assigned to the corresponding argument from left to right.

moreover, a function can be called either using a function call or a method call.
Inside a function, there is an hidden parameter named 'this' that correspond to the receiver if the function was called using a method call or 'undefined' if the function was called using a function call.

instance creation
--
when creating an instance, the field values are evaluated in the order of declaration.
For example
```
var foo = {
  a: print('a'),
  b: print('b')
};
```
will first print 'a' then 'b'. 


AST
---
the abstract syntax tree is composed of several kind of nodes

Expr(int lineNumber)
root of the hierarchy

Literal(Object value)
constant expression (undefined, int or String)
```
undefined  // undefined
42         // an int
'hello'    // a String
"hello"    // a String
```

Block(List<Expr> statements)
```
{
   // 0 or more statements
}
```

If(Expr condition, Block trueBlock, Block falseBlock)
```
if (2 == 3) {
  // true block
} else {
  // false block
}
```

Fun(String name, List<String> parameters, boolean toplevel, Block body)
function declaration
```
function foo(x) {
  // block  
}
```

Return(Expr expr)
```
return;   // equivalent to return undefined;
return 3;
```

LocalVarAssignment(String name, Expr expr, booolean declaration)
```
var a = 3;   // declaration=true
a = 3;       // declaration=false
```

LocalVarAccess(String name)
```
a   // read a local variable
```

FunCall(Exr qualified, List<Expr> args)
a function call using a name or an expression
```
2 + 3 // function call equivalent to +(2, 3)

function hello()
hello();  // function call

function bar(int x) { }
var foo = bar;
foo(3);  // function call
```

ObjectLiteral(Map<String, Expr> initMap)
allocation of an instance
```
var a = {
  foo: 4
  bar: 2 + 3
};
```

FieldAssignment(Expr receiver, String name, Expr expr)
set the value of a field of an instance
```
a.foo = 42;
```

FieldAccess(Expr receiver, String name)
get the value of a field of an instance
```
a.foo
```

MethodCall(Expr receiver, String name, List<Expr> args)
call a method on an instance
```
a.foo(3, 'hello')
```

