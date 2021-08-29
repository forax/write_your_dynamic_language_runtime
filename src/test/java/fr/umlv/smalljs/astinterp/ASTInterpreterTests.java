package fr.umlv.smalljs.astinterp;

import static fr.umlv.smalljs.ast.ASTBuilder.createScript;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import fr.umlv.smalljs.rt.Failure;

@SuppressWarnings("static-method")
public class ASTInterpreterTests {
  private static String execute(String code) {
    var script = createScript(new StringReader(code));
    var outStream = new ByteArrayOutputStream(8192);
    ASTInterpreter.interpret(script, new PrintStream(outStream, false, UTF_8));
    return outStream.toString(UTF_8).replace("\r\n", "\n");
  }


  @Tag("Q2") @Test
  public void helloString() {
    assertEquals("", execute("\"hello\"\n"));
  }

  /*
  @Tag("Q3") @Test
  public void integer3() {
    assertEquals("", execute("3\n"));
  }

  @Tag("Q4") @Test
  public void print() {
    assertEquals("hello\n", execute("print(\"hello\")\n"));
    assertEquals("foobar\n", execute("print('foobar')\n"));
    assertEquals("3\n", execute("print(3)\n"));
  }

  @Tag("Q5") @Test
  public void printPrint() {
    assertFalse(execute("print(print)\n").isEmpty());
  }

  @Tag("Q6") @Test
  public void printOperations() {
    assertEquals("5\n", execute("print(3 + 2)\n"));
    assertEquals("1\n", execute("print(3 - 2)\n"));
    assertEquals("6\n", execute("print(3 * 2)\n"));
    assertEquals("1\n", execute("print(3 / 2)\n"));
  }

  @Tag("Q7") @Test
  public void printPrint3() {
    assertEquals("3\nundefined\n", execute("print(print(3))\n"));
  }

  @Tag("Q8") @Test
  public void printAVariable() {
    assertEquals("3\n", execute("""
            var a = 3;
            print(a);
            """));
  }
  @Tag("Q8") @Test
  public void printSeveralVariables() {
    assertEquals("7\n", execute("""
            var a = 3;
            var b = 4;
            print(a + b);
            """));
  }
  @Tag("Q8") @Test
  public void printSeveralAssignments() {
    assertEquals("42\n42\n", execute("""
            var a = 42;
            var b = a;
            print(a);
            print(b);
            """));
  }
  @Tag("Q8") @Test
  public void printSeveralArguments() {
    assertEquals("hello Bob\n", execute("""
            var me = 'Bob';
            print('hello', me);
            """));
  }
  
  @Tag("Q9") @Test
  public void printAVariableDefinedAfter() {
    assertEquals("undefined\n", execute("print(a);\nvar a = 2;\n"));
  }

  @Tag("Q10") @Test
  public void callAUserDefinedFunctionAndPrint() {
    assertEquals("3\n", execute("""
            function foo(x) {
              return x + 1;
            }
            print(foo(2));
            """));
  }
  @Tag("Q10") @Test
  public void callAUserDefinedFunctionWithTheWrongNumberOfArguments() {
  	assertThrows(Failure.class, () -> execute("""
            function foo(a, b) {
            }
            print(foo(2));
            """));
  }
  @Tag("Q10") @Test
  public void callSeveralFunctions() {
    assertEquals("foo\nbar\n", execute("""
            function foo() {
              print('foo');
              bar();
            }
            function bar() {
              print('bar');
            }
            foo();
            """));
  }
  @Tag("Q10") @Test
  public void callVariableFunction() {
    assertEquals("6\n6\n", execute("""
            var foo = function bar(x) {
              return x * 2;}
            print(foo(3));
            print(bar(3));
            """));
  }
  @Tag("Q10") @Test
  public void callVariableFunction2() {
    assertEquals("6\n", execute("""
            var foo = function (x) {
              return x * 2;}
            print(foo(3));
            """));
  }
  @Tag("Q10") @Test
  public void callFunctionWithNoReturn() {
    assertEquals("undefined\n", execute("""
            function undef() { }
            print(undef());
            """));
  }
  
  @Tag("Q11") @Test
  public void printWithAnIf() {
    assertEquals("false\n", execute("""
            var a = 2;
            if (a == 3) {
              print("true");
            } else {
              print("false");
            }
            """));
  }
  @Tag("Q11") @Test
  public void printWithAnIf2() {
    assertEquals("true\n", execute("""
            var a = 3;
            if (a == 3) {
              print("true");
            } else {
              print("false");
            }
            """));
  }
  @Tag("Q11") @Test
  public void printVariableWeirdScope() {
    assertEquals("false\nundefined\n", execute("""
            var a = 2;
            if (a == 3) {
              print("true");
              var b = 'hello';
            } else {
              print("false");
            }
            print(b);"""));
  }
  @Tag("Q11") @Test
  public void printVariableWeirdScope2() {
    assertEquals("true\nhello\n", execute("""
            var a = 3;
            if (a == 3) {
              print("true");
              var b = 'hello';
            } else {
              print("false");
            }
            print(b);"""));
  }
  @Tag("Q11") @Test
  public void callAUserDefinedFunctionWithAnIf() {
    assertEquals("0\n7\n", execute("""
            function f(x) {
                if (x < 3) {
                  return 0;
                } else {
                  return x;
                }
            }
            print(f(2));
            print(f(7));
            """));
  }
  @Tag("Q11") @Test
  public void callAUserDefinedFunctionWithAnIfAndAVariabe() {
    assertEquals("0\n7\n", execute("""
            function f(x) {
                if (x < 3) {
                  var a = 0;
                } else {
                  var a = x;
                }
                return a;
            }
            print(f(2));
            print(f(7));
            """));
  }
  
  @Tag("Q12") @Test
  public void callFibo() {
    assertEquals("21\n", execute("""
            function fibo(n) {
                if (n < 2) {
                  return 1
                } else {
                  return fibo(n - 1) + fibo(n - 2)
                }
              }

            print(fibo(7))
            """));
  }
  @Tag("Q12") @Test
  public void callRecursiveFunction() {
    assertEquals("24\n", execute("""
            function fact(n) {
              if (n < 1) {
                return 1;
              } else {
                return n * fact(n - 1);
              }
            }
            print(fact(4));
            """));
  }
  @Tag("Q12") @Test
  public void callSeveralOperations() {
    assertEquals("5\n-1\n6\n0\n", execute("""
            function calc(f, a, b) {
             return f(a, b);
            }
            print(calc(+, 2, 3));
            print(calc(-, 2, 3));
            print(calc(*, 2, 3));
            print(calc(/, 2, 3));
            """));
  }
  @Tag("Q12") @Test
  public void callAndRewrite() {
    assertEquals("2\n9\n", execute("""
            function f() { return op(); }
            function op() { return 2; }
            print(f());
            function op() { return 9; }
            print(f());
            """));
  }
  
  @Tag("Q13") @Test
  public void createAnObject() {
    assertEquals("""
            { // object
              x: 1
              y: 2
              proto: null
            }
            """,
        execute("""
                var o = {
                    x: 1,
                    y: 2
                };
                print(o);
                """));
  }
  
  @Tag("Q14") @Test
  public void createAnObjectFromAVariableValue() {
    assertEquals("""
            { // object
              x: 1
              y: 2
              proto: null
            }
            """,
        execute("""
                var a = 1;
                var o = {
                  x: a,
                  y: a + 1
                }
                print(o);
                """));
  }
  @Tag("Q14") @Test
  public void createAnObjectEvaluationOrder() {
    assertEquals(
        "a\nb\n",
        execute("""
                var foo = {
                  a: print('a'),
                  b: print('b')
                };"""));
  }
  
  
  @Tag("Q15") @Test
  public void objectGetAFieldValue() {
    assertEquals(
        "John\n",
        execute("""
                var john = { name: "John" };
                print(john.name);
                """));
  }
  @Tag("Q15") @Test
  public void objectGetAFieldNoValue() {
    assertEquals(
        "undefined\n",
        execute("""
                var john = { name: "John" };
                print(john.foo);
                """));
  }
  
  @Tag("Q16") @Test
  public void objectSetAFieldValue() {
    assertEquals(
        "Jane\n",
        execute("""
                var john = { name: "John" };
                john.name = "Jane";
                print(john.name);
                """));
  }
  @Tag("Q16") @Test
  public void objectGetAndSetAField() {
    assertEquals(
        "2\n9\n",
        execute("""
                function f(o) { return o.field; }
                var obj = { field: 2 };
                print(f(obj));
                obj.field = 9;
                print(f(obj));
                """));
  }
  
  @Tag("Q17") @Test
  public void objectCallAMethod() {
    assertEquals(
        "hello 42\nhello 42\n",
        execute("""
                var object = {
                  bar: "hello",
                  foo: function(x) {
                         print(this.bar, x);
                       }
                };
                object.foo(42);
                object.foo(42);
                """));
  }*/
}
