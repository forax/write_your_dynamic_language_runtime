stack interpreter
---


values
---

smalljs runtime has 3 kinds of values, integers, strings and user defined objects.

For the interpreter, every values is a 32 bits tagged integer,
the interpreter uses the last bits to differentiate the kind of values.
The same encoding is used for the value on stack or in the heap.

```
xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxx1 -> small ints (on 31 bits) or boolean (TRUE/FALSE)
xxxxxxxx xxxxxxxx xxxxxxxx xxxxxx10 -> dictionary index
xxxxxxxx xxxxxxxx xxxxxxxx xxxxxx00 -> reference on heap
```

This encoding (which is roughly similar to the one used by V8) as the advantages that simple operations
like an addition or a substraction on small integerss can be done directly without doing the conversion
between a tagged value to the real integer and back.

By example, let suppose we want to execute `a + b`, with `a = 2` and `b = 10`,
let's call x and y the encoding of a and b, 2 is encoded as 5 and 10 as 21,
more generally, given the way small integers are encoded `x = 2a + 1` and `y = 2b + 1`,
so instead of decoding 5 and 21, doing the addition and recoding them, we can add the encoded value and subtract one.

  (2a + 1) + (2b + 1) <=> 2 (a + b) + 1 + 1 <=> (x + y) + 1
   
and for the subtraction

  (2a + 1) - (2b + 1) <=> 2 (a - b) + 1 - 1 <=> (x - y) - 1

We will not use this optimization but feel free to implement them.


opcodes
---

the opcodes used by the interpreter is a sequence of 1 to 2 ints, the first int is the value of the opcode from 1 to 20,
the second optional value is a value that depend on the kind of opcodes.

```
  int CONST = 1;            // CONST tag_value (smallint or dictionary_index)
  int LOOKUP = 2;           // LOOKUP dictionary_index (String global_name)
  int REGISTER = 3;         // REGISTER dictionary_index (String global_name)
  int LOAD = 4;             // LOAD  slot_index
  int STORE = 5;            // STORE slot_index
  int DUP = 6;              
  int POP = 7;              
  int FUNCALL = 8;          // FUNCALL argument_count
  int RET = 9;
  int GOTO = 10;            // GOTO  instr_index
  int JUMP_IF_FALSE = 11;   // JUMP_IF_FALSE instr_index
  int NEW = 12;             // NEW dictionary_index (JSObject object)
  int GET = 13;             // GET dictionary_index (String field_name)
  int PUT = 14;             // PUT dictionary_index (String field_name)
  
  int PRINT = 20;           // print for debugging
```

  - `CONST` load a constant on stack, it can be a small integer or a dictionary value
  - `LOOKUP` load a value from the global context using the string encoded as a dictionary value
  - `REGISTER` store the top of the stack into the global context using the string encoded as a dictionary value
  - `LOAD` load a local variable from the local array of the current function at index `slot_index`
  - `STORE` store the top of the stack to the local array of the current function at index `slot_index`
  - `DUP` duplicate the top of the stack
  - `POP` remove the top of the stack
  - `FUNCALL` call a function with a qualifier value and the arguments all on top of the stack
  - `RET` stop the current function, remove the current stack frame and insert the top of the stack as the return value
  - `GOTO` jump unconditionally to a specific instruction index
  - `JUMP_IF_FALSE` jump if the top of the stack is 0 or null to the instruction index
  - `NEW` allocate the memory necessary to store all the field from the JSObject 
  - `GET` get the value of the field from an object on top of the stack
  - `PUT` store into a field the value on top of the stack into an object on (top - 1) of the stack 
  - `PRINT` print the top of the stack.
  
 examples of codes
 ---
 
 `print(2 + 10)` is translated to
 ```
 0: LOOKUP 10     // JSObject of "+" 
 2: CONST 6       // undefined
 4: CONST 5
 6: CONST 21
 8: FUNCALL 2
10: PRINT
 ```
 
 `a = 2; b = a; print(b)`
```
 0: CONST 5
 2: STORE 1
 4: LOAD 1
 6: STORE 2
 8: LOAD 2
10: PRINT
```

stack frame
---
 
A stack frame is the part of the stack that correspond to a function call.
A stack frame is composed of
  - an array of local variables that contains the parameter and then the local variables
  - an activation zone that contains the information to be able to come back of a function call
    - the base pointer `bp`
    - the instruction pointer aka program counter `pc`
    - the current function `fun`
  - a local stack
 
When calling a function, the smalljs semantics requires the arguments to be copied on the stack,
to avoid that copy at runtime, the argument of a function call and the parameter the called function
reference the same part of the stack (that's why the activation zone is in the middle). 

By example, if we have the following code
```
  function f(i, j) {  // (this, i,  j)
    print(j);
  }
  var a = 2;
  f(a, 10);
```
is transformed to the following opcodes.
```
// code of function f
 0: LOAD 2
 2: PRINT
 3: POP
 4: CONST 6   // undefined
 6: RET
 
// code of function main
 0: CONST 30     // JSObject
 2: REGISTER 34  // "f"
 4: CONST 5
 6: STORE 1
 8: LOOKUP 34    // "f"
10: CONST 6      // undefined = this
12: LOAD 1
14: CONST 21
16: FUNCALL 2
18: RET
```

when `print(j)` is called the stack is (from the bottom to the top)
```
                                stack         : 21
                                ---------------------
                                activation fun: main
                                activation pc : 12
                                activation bp : 0
                                ---------------------
      stack         : 21        slot var 2    : 21
      stack         : 5         slot var 1    : 5           
      stack         : undef  f: slot var 0    : undef         |
      stack         : 34                                     / \
      ---------------------                                   |
      activation fun: 0                                       |
      activation pc : 0                                       |
      activation bp : 0                                       |
      ---------------------                                   |
      slot var 1    : 5
main: slot var 0    : undef
``` 

Note that to be able to construct the stack that way, the maximum number of slot variables need to be computed
doing a static analysis on the code.

Moreover, if we want to detect a stack overflow when calling a method before adding a new stack frame,
we need to be able to compute the size of the stack frame, for that we need the maximum size of the stack
of a stack frame which can also be computed using a static analysis on the code.

When interpreting the code of a method, we need:
 - `function` the current function
 - `instrs` the array of instruction of the current function
 - `bp` the base pointer, the index of the first local variable of the current stack frame
 - `pc` the program counter, the index of the current instruction in the array of instruction
 - `sp` the stack pointer, the pointer on the top the stack of the current stack frame

