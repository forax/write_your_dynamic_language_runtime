package fr.umlv.smalljs.stackinterp.main;

import static fr.umlv.smalljs.ast.ASTBuilder.createScript;
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
import fr.umlv.smalljs.stackinterp.StackInterpreter;

@SuppressWarnings("static-method")
public class StackInterpreterTests {
  private static String execute(String code) {
    var script = createScript(new StringReader(code));
    var outStream = new ByteArrayOutputStream(8192);
    StackInterpreter.interpret(script, new PrintStream(outStream));
    return outStream.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
  }

  
  @Tag("Q2") @Test
  public void helloString() {
    assertEquals("", execute("\"hello\"\n"));
  }

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
    assertEquals("3\n", execute(
        "var a = 3;\n" +
        "print(a);\n"));
  }
  @Tag("Q8") @Test
  public void printSeveralVariables() {
    assertEquals("7\n", execute(
    		"var a = 3;\n" + 
    		"var b = 4;\n" + 
    		"print(a + b);\n"));
  }
  @Tag("Q8") @Test
  public void printSeveralAssignments() {
    assertEquals("42\n42\n", execute(
    		"var a = 42;\n"  +
    		"var b = a;\n"   + 
    		"print(a);\n"    +
    		"print(b);\n"));
  }
  @Tag("Q8") @Test
  public void printSeveralArguments() {
    assertEquals("hello Bob\n", execute(
    		"var me = 'Bob';\n" + 
    		"print('hello', me);\n"));
  }
  
  @Tag("Q9") @Test
  public void printAVariableDefinedAfter() {
    assertEquals("undefined\n", execute("print(a);\nvar a = 2;\n"));
  }
  
  @Tag("Q10") @Test
  public void callAUserDefinedFunctionAndPrint() {
    assertEquals("3\n", execute(
        "function foo(x) {\n" +
        "  return x + 1;\n"   +
        "}\n"                 +
        "print(foo(2));\n"));
  }
  @Tag("Q10") @Test
  public void callAUserDefinedFunctionWithTheWrongNumberOfArguments() {
  	assertThrows(Failure.class, () -> execute(
        "function foo(a, b) {\n" +
        "}\n"                 +
        "print(foo(2));\n"));
  }
  @Tag("Q10") @Test
  public void callSeveralFunctions() {
    assertEquals("foo\nbar\n", execute(
        "function foo() {\n" + 
        "  print('foo');\n"  + 
        "  bar();\n"         + 
        "}\n"                +  
        "function bar() {\n" + 
        "  print('bar');\n"  + 
        "}\n"                + 
        "foo();\n"));
  }
  @Tag("Q10") @Test
  public void callVariableFunction() {
    assertEquals("6\n6\n", execute(
        "var foo = function bar(x) {\n" +
        "  return x * 2;" +
        "}\n" +
        "print(foo(3));\n" +
        "print(bar(3));\n"));
  }
  @Tag("Q10") @Test
  public void callVariableFunction2() {
    assertEquals("6\n", execute(
        "var foo = function (x) {\n" +
        "  return x * 2;" +
        "}\n" +
        "print(foo(3));\n"));
  }
  @Tag("Q10") @Test
  public void callFunctionWithNoReturn() {
    assertEquals("undefined\n", execute(
        "function undef() { }\n" +
        "print(undef());\n"));
  }
  
  @Tag("Q11") @Test
  public void printWithAnIf() {
    assertEquals("false\n", execute(
    		"var a = 2;\n"          + 
    		"if (a == 3) {\n"       + 
    		"  print(\"true\");\n"  + 
    		"} else {\n"            + 
    		"  print(\"false\");\n" + 
    		"}\n"));
  }
  @Tag("Q11") @Test
  public void printWithAnIf2() {
    assertEquals("true\n", execute(
    		"var a = 3;\n"          + 
    		"if (a == 3) {\n"       + 
    		"  print(\"true\");\n"  + 
    		"} else {\n"            + 
    		"  print(\"false\");\n" + 
    		"}\n"));
  }
  @Tag("Q11") @Test
  public void callAUserDefinedFunctionVarsInitialized() {
    assertEquals("undefined\n", execute(
        "function foo(x) {\n" +
        "  if (x == 3) {\n"   +
        "    var a = 42;\n"   +
        "  } else {\n"        +
        "  }\n"               +
        "  return a;\n"       +
        "}\n"                 +
        "print(foo(2));\n"));
  }
  @Tag("Q11") @Test
  public void printVariableWeirdScope() {
    assertEquals("false\nundefined\n", execute(
    		"var a = 2;\n"          + 
    		"if (a == 3) {\n"       + 
    		"  print(\"true\");\n"  + 
    		"  var b = 'hello';\n"  + 
    		"} else {\n"            + 
    		"  print(\"false\");\n" + 
    		"}\n" + 
    		"print(b);"));
  }
  @Tag("Q11") @Test
  public void printVariableWeirdScope2() {
    assertEquals("true\nhello\n", execute(
    		"var a = 3;\n"          + 
    		"if (a == 3) {\n"       + 
    		"  print(\"true\");\n"  + 
    		"  var b = 'hello';\n"  + 
    		"} else {\n"            + 
    		"  print(\"false\");\n" + 
    		"}\n" + 
    		"print(b);"));
  }
  @Tag("Q11") @Test
  public void callAUserDefinedFunctionWithAnIf() {
    assertEquals("0\n7\n", execute(
        "function f(x) {\n"  + 
        "    if (x < 3) {\n" + 
        "      return 0;\n"  + 
        "    } else {\n"     + 
        "      return x;\n"  + 
        "    }\n"            + 
        "}\n"                + 
        "print(f(2));\n"     + 
        "print(f(7));\n"));
  }
  @Tag("Q11") @Test
  public void callAUserDefinedFunctionWithAnIfAndAVariabe() {
    assertEquals("0\n7\n", execute(
        "function f(x) {\n"  +
        "    if (x < 3) {\n" + 
        "      var a = 0;\n" + 
        "    } else {\n"     + 
        "      var a = x;\n" + 
        "    }\n"            + 
        "    return a;\n"    +
        "}\n"                + 
        "print(f(2));\n"     + 
        "print(f(7));\n"));
  }
  
  @Tag("Q12") @Test
  public void callFibo() {
    assertEquals("21\n", execute(
        "function fibo(n) {\n"                     + 
        "    if (n < 2) {\n"                       + 
        "      return 1\n"                         + 
        "    } else {\n"                           + 
        "      return fibo(n - 1) + fibo(n - 2)\n" + 
        "    }\n"                                  + 
        "  }\n"                                    + 
        "\n"                                       + 
        "print(fibo(7))\n"));
  }
  @Tag("Q12") @Test
  public void callRecursiveFunction() {
    assertEquals("24\n", execute(
        "function fact(n) {\n"           +
        "  if (n < 1) {\n"               +
        "    return 1;\n"                +		
        "  } else {\n"                   +
        "    return n * fact(n - 1);\n"  +
        "  }\n"                          +
        "}\n"                            +
        "print(fact(4));\n"));
  }
  @Tag("Q12") @Test
  public void callSeveralOperations() {
    assertEquals("5\n-1\n6\n0\n", execute(
        "function calc(f, a, b) {\n"  +
        " return f(a, b);\n"          +
        "}\n"                         +
        "print(calc(+, 2, 3));\n"      +
        "print(calc(-, 2, 3));\n"      +
        "print(calc(*, 2, 3));\n"      +
        "print(calc(/, 2, 3));\n"));
  }
  @Tag("Q12") @Test
  public void callAndRewrite() {
    assertEquals("2\n9\n", execute(
        "function f() { return op(); }\n" +
        "function op() { return 2; }\n"   +
        "print(f());\n"                   +
        "function op() { return 9; }\n"   +
        "print(f());\n"));
  }
  
  
  @Tag("Q13") @Test
  public void createAnObject() {
    assertEquals(
        "{ // object\n"   +
        "  x: 1\n"        +
        "  y: 2\n"        +
        "  proto: null\n" +
        "}\n",
        execute(
        "var o = {\n" + 
        "    x: 1,\n" + 
        "    y: 2\n"  + 
        "};\n"        +
        "print(o);\n"));
  }
  
  @Tag("Q14") @Test
  public void createAnObjectFromAVariableValue() {
    assertEquals(
        "{ // object\n"   +
        "  x: 1\n"        + 
        "  y: 2\n"        + 
        "  proto: null\n" +
        "}\n",
        execute(
        "var a = 1;\n" + 
        "var o = {\n"  + 
        "  x: a,\n"    + 
        "  y: a + 1\n" + 
        "}\n"          +
        "print(o);\n"));
  }
  @Tag("Q14") @Test
  public void createAnObjectEvaluationOrder() {
    assertEquals(
        "a\nb\n",
        execute(
        "var foo = {\n" + 
        "  a: print('a'),\n" + 
        "  b: print('b')\n" + 
        "};"));
  }
  
  @Tag("Q15") @Test
  public void objectGetAFieldValue() {
    assertEquals(
        "John\n",
        execute(
        "var john = { name: \"John\" };\n" + 
        "print(john.name);\n"));
  }
  @Tag("Q15") @Test
  public void objectGetAFieldNoValue() {
    assertEquals(
        "undefined\n",
        execute(
        "var john = { name: \"John\" };\n" + 
        "print(john.foo);\n"));
  }
  
  @Tag("Q16") @Test
  public void objectSetAFieldValue() {  // patch visit variable
    assertEquals(
        "Jane\n",
        execute(
        "var john = { name: \"John\" };\n" + 
        "john.name = \"Jane\";\n"          + 
        "print(john.name);\n"));
  }
  @Tag("Q16") @Test
  public void objectGetAndSetAField() {
    assertEquals(
        "2\n9\n",
        execute(
        "function f(o) { return o.field; }\n" +    
        "var obj = { field: 2 };\n"           + 
        "print(f(obj));\n"                    +
        "obj.field = 9;\n"                    +
        "print(f(obj));\n"));
  }
  
  @Tag("Q17") @Test
  public void objectCallAMethod() {
    assertEquals(
        "hello 42\nhello 42\n",
        execute(
        "var object = {\n"               + 
        "  bar: \"hello\",\n"            +
        "  foo: function(x) {\n"         +
        "         print(this.bar, x);\n" +
        "       }\n"                     +
        "};\n"                           + 
        "object.foo(42);\n"              +
        "object.foo(42);\n"));
  }
}
