# write_your_dynamic_language_runtime
How to write interpreters or dynamic compilers for dynamically typed languages on top of the JVM

This repository provide the basis to implement your own
- Abstract Syntax Tree Interpreter
- Stack based Interpreter
- Java Virtual Machine based Interpreter

of a dynamically typed language named [smalljs](smalljs.md) which is a almost subset of JavaScript.

How to compile and create a jar
---
run ant with a recent JDK (at least Java 11)
```
  ant
```
a jar named smalljs.jar is generated

How to run it
---
```
  java -jar smalljs.jar ast samples/hello.js
  java -jar smalljs.jar stack samples/hello.js
  java -jar smalljs.jar jvm samples/hello.js
```
with 'ast' being the AST interpreter, 'stack' being the Stack based interpreter and 'jvm' being the JVM based interpreter.
