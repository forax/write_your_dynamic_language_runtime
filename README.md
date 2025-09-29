# write_your_dynamic_language_runtime

This repository is used for the course "How to implement a dynamic language on the JVM" at University Gustave Eiffel (Paris France)

This repository provides the basis to implement your own
- Abstract Syntax Tree Interpreter
- Stack-based Interpreter
- Java Virtual-Machine-based Interpreter

of a dynamically typed language named [smalljs](smalljs.md) which is almost a subset of JavaScript.

How to compile and create a jar
---
run Maven with a Java 25
```
  mvn package
```
a jar named smalljs-2.0.jar in the folder `target` is generated

How to run it
---
Still with Java 25
```
  java -jar target/smalljs-2.0.jar ast samples/hello.js
  java -jar target/smalljs-2.0.jar stack samples/hello.js
  java -jar target/smalljs-2.0.jar jvm samples/hello.js
```
with 'ast' being the AST interpreter, 'stack' being the Stack-based interpreter and 'jvm' being the JVM-based interpreter.
