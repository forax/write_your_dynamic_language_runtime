package fr.umlv.smalljs.stackinterp.main;

import static fr.umlv.smalljs.rt.JSObject.UNDEFINED;
import static fr.umlv.smalljs.stackinterp.Instructions.*;
import static fr.umlv.smalljs.stackinterp.Instructions.DUP;
import static fr.umlv.smalljs.stackinterp.Instructions.FUNCALL;
import static fr.umlv.smalljs.stackinterp.Instructions.GOTO;
import static fr.umlv.smalljs.stackinterp.Instructions.JUMP_IF_FALSE;
import static fr.umlv.smalljs.stackinterp.Instructions.LOAD;
import static fr.umlv.smalljs.stackinterp.Instructions.LOOKUP;
import static fr.umlv.smalljs.stackinterp.Instructions.POP;
import static fr.umlv.smalljs.stackinterp.Instructions.PRINT;
import static fr.umlv.smalljs.stackinterp.Instructions.REGISTER;
import static fr.umlv.smalljs.stackinterp.Instructions.RET;
import static fr.umlv.smalljs.stackinterp.Instructions.STORE;
import static fr.umlv.smalljs.stackinterp.TagValues.encodeDictObject;
import static fr.umlv.smalljs.stackinterp.TagValues.encodeSmallInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import fr.umlv.smalljs.rt.Failure;
import fr.umlv.smalljs.rt.JSObject;
import fr.umlv.smalljs.stackinterp.Code;
import fr.umlv.smalljs.stackinterp.Dictionary;
import fr.umlv.smalljs.stackinterp.StackInterpreter;

@SuppressWarnings("static-method")
public class StackInterpreterInstrTests {
	private static JSObject newFunction(String name, Code code) {
    var fun = JSObject.newFunction(name, (self, receiver, args) -> {
    	      throw new Failure("native call not supported");
        }
    );
    fun.register("__code__", code);
    return fun;
  }
  private static String execute(Code mainCode, Dictionary dict) {
    var outStream = new ByteArrayOutputStream(8192);
    var globalEnv = StackInterpreter.createGlobalEnv(new PrintStream(outStream));
    var mainFun = newFunction("main", mainCode);
    globalEnv.register("main", mainFun);
    StackInterpreter.execute(mainFun, dict, globalEnv);
    return outStream.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
  }

  
  @Tag("Q2") @Test
  public void helloString() {
  	// "hello"
  	var dict = new Dictionary();
  	int[] instrs = {
  		CONST, encodeDictObject("hello", dict),
  		RET
  	};
    assertEquals("", execute(new Code(instrs, 1, 1), dict));
  }

  @Tag("Q3") @Test
  public void integer3() {
  	// 3
  	var dict = new Dictionary();
  	int[] instrs = {
  		CONST, encodeSmallInt(3),
  		RET
  	};
    assertEquals("", execute(new Code(instrs, 1, 1), dict));
  }
  
  @Tag("Q4") @Test
  public void print3() {
    // print(3)
  	var dict = new Dictionary();
  	int[] instrs = {
  		CONST, encodeSmallInt(3),
  		PRINT,
  		RET
  	};
    assertEquals("3\n", execute(new Code(instrs, 1, 1), dict));
  }
  @Tag("Q4") @Test
  // print("hello")
  public void printHello() {
  	var dict = new Dictionary();
  	int[] instrs = {
  		CONST, encodeDictObject("hello", dict),
  		PRINT,
  		RET
  	};
    assertEquals("hello\n", execute(new Code(instrs, 1, 1), dict));
  }
  
  @Tag("Q5") @Test
  public void nativePrintHello() {
  	// print("hello")
  	var dict = new Dictionary();
  	int[] instrs = {
  		LOOKUP, encodeDictObject("print", dict),
  		CONST, encodeDictObject(UNDEFINED, dict),
  		CONST, encodeDictObject("hello", dict),
  		FUNCALL, 1,
  		RET
  	};
    assertEquals("hello\n", execute(new Code(instrs, 1, 1), dict));
  }
  @Tag("Q5") @Test
  public void nativePrint3() {
    // print(3)
  	var dict = new Dictionary();
  	int[] instrs = {
  			LOOKUP, encodeDictObject("print", dict),
    		CONST, encodeDictObject(UNDEFINED, dict),
    		CONST, encodeSmallInt(3),
    		FUNCALL, 1,
    		RET
  	};
    assertEquals("3\n", execute(new Code(instrs, 1, 1), dict));
  }
  
  @Tag("Q6") @Test
  public void printOperationAdd() {
  	// print(3 + 2)
  	var dict = new Dictionary();
  	int[] instrs = {
  			LOOKUP, encodeDictObject("+", dict),
    		CONST, encodeDictObject(UNDEFINED, dict),
    		CONST, encodeSmallInt(3),
    		CONST, encodeSmallInt(2),
    		FUNCALL, 2,
    		PRINT,
    		RET
  	};
    assertEquals("5\n", execute(new Code(instrs, 1, 1), dict));
  }
  @Tag("Q6") @Test
  public void printOperationSub() {
    // print(3 - 2)
  	var dict = new Dictionary();
  	int[] instrs = {
  			LOOKUP, encodeDictObject("-", dict),
    		CONST, encodeDictObject(UNDEFINED, dict),
    		CONST, encodeSmallInt(3),
    		CONST, encodeSmallInt(2),
    		FUNCALL, 2,
    		PRINT,
    		RET
  	};
    assertEquals("1\n", execute(new Code(instrs, 1, 1), dict));
  }
  @Tag("Q6") @Test
  public void printOperationMul() {
    // print(3 * 2)
  	var dict = new Dictionary();
  	int[] instrs = {
  			LOOKUP, encodeDictObject("*", dict),
    		CONST, encodeDictObject(UNDEFINED, dict),
    		CONST, encodeSmallInt(3),
    		CONST, encodeSmallInt(2),
    		FUNCALL, 2,
    		PRINT,
    		RET
  	};
    assertEquals("6\n", execute(new Code(instrs, 1, 1), dict));
  }
  @Tag("Q6") @Test
  public void printOperationDiv() {
    // print(3 / 2)
  	var dict = new Dictionary();
  	int[] instrs = {
  			LOOKUP, encodeDictObject("/", dict),
    		CONST, encodeDictObject(UNDEFINED, dict),
    		CONST, encodeSmallInt(3),
    		CONST, encodeSmallInt(2),
    		FUNCALL, 2,
    		PRINT,
    		RET
  	};
    assertEquals("1\n", execute(new Code(instrs, 1, 1), dict));
  }
  
  @Tag("Q7") @Test
  public void printPrint3() {
  	// print(print(3))
    var dict = new Dictionary();
  	int[] instrs = {
  			CONST, encodeSmallInt(3),
    		PRINT,
    		PRINT,
    		RET
  	};
    assertEquals("3\nundefined\n", execute(new Code(instrs, 1, 1), dict));
  }
  @Tag("Q7") @Test
  public void nativePrintPrint3() {
  	// print(print(3))
    var dict = new Dictionary();
  	int[] instrs = {
  			LOOKUP, encodeDictObject("print", dict),
    		CONST, encodeDictObject(UNDEFINED, dict),
    		CONST, encodeSmallInt(3),
    		FUNCALL, 1,
    		PRINT,
    		RET
  	};
  	assertEquals("3\nundefined\n", execute(new Code(instrs, 1, 1), dict));
  }
  
  @Tag("Q8") @Test
  public void printAVariable() {
    // var a = 3;
   	// print(a);
  	var dict = new Dictionary();
  	int[] instrs = {
  			CONST, encodeSmallInt(3),
    		STORE, 1,
    		LOAD, 1,
    		PRINT,
    		RET
  	};
  	assertEquals("3\n", execute(new Code(instrs, 1, 2), dict));
  }
  @Tag("Q8") @Test
  public void printSeveralVariables() {
    // var a = 3;
   	// var b = 4;
   	// print(a + b);
  	var dict = new Dictionary();
  	int[] instrs = {
  			CONST, encodeSmallInt(3),
    		STORE, 1,
    		CONST, encodeSmallInt(4),
    		STORE, 2,
    		LOOKUP, encodeDictObject("+", dict),
    		CONST, encodeDictObject(UNDEFINED, dict),
    		LOAD, 1,
    		LOAD, 2,
    		FUNCALL, 2,
    		PRINT,
    		RET
  	};
  	assertEquals("7\n", execute(new Code(instrs, 1, 3), dict));
  }
  @Tag("Q8") @Test
  public void printSeveralAssignments() {
    // var a = 42;
   	// var b = a;
   	// print(a);
  	// print(b);
  	var dict = new Dictionary();
  	int[] instrs = {
  			CONST, encodeSmallInt(42),
    		STORE, 1,
    		LOAD, 1, 
    		STORE, 2,
    		LOAD, 1, 
    		PRINT,
    		POP,
    		LOAD, 2, 
    		PRINT,
    		RET
  	};
  	assertEquals("42\n42\n", execute(new Code(instrs, 1, 3), dict));
  }
  @Tag("Q8") @Test
  public void printSeveralArguments() {
  	// var me = 'Bob';
  	// print('hello', me);
  	var dict = new Dictionary();
  	int[] instrs = {
  			CONST, encodeDictObject("Bob", dict),
    		STORE, 1,
    		LOOKUP, encodeDictObject("print", dict),
    		CONST, encodeDictObject(UNDEFINED, dict),
    		CONST, encodeDictObject("hello", dict),
    		LOAD, 1, 
    		FUNCALL, 2,
    		RET
  	};
  	assertEquals("hello Bob\n", execute(new Code(instrs, 1, 2), dict));
  }
  
  @Tag("Q9") @Test
  public void printAnUndefindeVariable() {
  	// print(a);
  	// var a = 2;
  	var dict = new Dictionary();
  	int[] instrs = {
  			LOAD, 1,
    		PRINT,
    		RET
  	};
  	assertEquals("undefined\n", execute(new Code(instrs, 1, 2), dict));
  }
  
  @Tag("Q10") @Test
  public void callAUserDefinedFunctionAndPrint() {
  	// function foo(x) {
    //   return x + 1;
    // }
    // print(foo(2));
    var dict = new Dictionary();
    int[] foo = {
  			LOOKUP, encodeDictObject("+", dict),
  			CONST, encodeDictObject(UNDEFINED, dict),
  			LOAD, 1,
  			CONST, encodeSmallInt(1),
  			FUNCALL, 2,
    		RET
  	};
    var fooFun = newFunction("foo", new Code(foo, 2, 2));
  	int[] main = {
  			CONST, encodeDictObject(fooFun, dict),
  			REGISTER, encodeDictObject("foo", dict),
  			LOOKUP, encodeDictObject("foo", dict),
  			CONST, encodeDictObject(UNDEFINED, dict),
  			CONST, encodeSmallInt(2),
  			FUNCALL, 1,
    		PRINT,
    		RET
  	};
  	assertEquals("3\n", execute(new Code(main, 1, 1), dict));
  }
  @Tag("Q10") @Test
  public void callAUserDefinedFunctionWithTheWrongNumberOfArguments() {
  	// function foo(a, b) {
    // }
    // print(foo(2));
  	var dict = new Dictionary();
    int[] foo = {
    		CONST, encodeDictObject(UNDEFINED, dict),
    		RET
  	};
    var fooFun = newFunction("foo", new Code(foo, 3, 3));
  	int[] main = {
  			CONST, encodeDictObject(fooFun, dict),
  			REGISTER, encodeDictObject("foo", dict),
  			LOOKUP, encodeDictObject("foo", dict),
  			CONST, encodeDictObject(UNDEFINED, dict),
  			CONST, encodeSmallInt(2),
  			FUNCALL, 1,
    		PRINT,
    		RET
  	};
    assertThrows(Failure.class, () -> execute(new Code(main, 1, 1), dict));
  }
  @Tag("Q10") @Test
  public void callSeveralFunctions() {
  	// function foo() {
    //   print('foo');
    //   bar();
    // }
    // function bar() {
    //  print('bar');
    // } 
    // foo();
    var dict = new Dictionary();
    int[] foo = {
    		CONST, encodeDictObject("foo", dict),
    		PRINT,
    		POP,
    		LOOKUP, encodeDictObject("bar", dict),
    		CONST, encodeDictObject(UNDEFINED, dict),
    		FUNCALL, 0,
    		POP,
    		CONST, encodeDictObject(UNDEFINED, dict),
    		RET
  	};
    var fooFun = newFunction("foo", new Code(foo, 1, 1));
    int[] bar = {
    		CONST, encodeDictObject("bar", dict),
    		PRINT,
    		POP,
    		CONST, encodeDictObject(UNDEFINED, dict),
    		RET
  	};
    var barFun = newFunction("bar", new Code(bar, 1, 1));
  	int[] main = {
  			CONST, encodeDictObject(fooFun, dict),
  			REGISTER, encodeDictObject("foo", dict),
  			CONST, encodeDictObject(barFun, dict),
  			REGISTER, encodeDictObject("bar", dict),
  			LOOKUP, encodeDictObject("foo", dict),
  			CONST, encodeDictObject(UNDEFINED, dict),
  			FUNCALL, 0,
    		POP,
    		CONST, encodeDictObject(UNDEFINED, dict),
    		RET
  	};
  	assertEquals("foo\nbar\n", execute(new Code(main, 1, 1), dict));
  }
  @Tag("Q10") @Test
  public void callVariableFunction() {
  	// var foo = function bar(x) {
    //   return x * 2;
    // }
    // print(foo(3));
    // print(bar(3));
  	var dict = new Dictionary();
    int[] bar = {
    		LOOKUP, encodeDictObject("*", dict),
    		CONST, encodeDictObject(UNDEFINED, dict),
    		LOAD, 1,
    		CONST, encodeSmallInt(2),
    		FUNCALL, 2,
    		RET
  	};
    var barFunction = newFunction("bar", new Code(bar, 2, 2));
  	int[] main = {
  			CONST, encodeDictObject(barFunction, dict),
  			DUP,
  			REGISTER, encodeDictObject("bar", dict),
  			STORE, 1,
  			LOAD, 1,
  			CONST, encodeDictObject(UNDEFINED, dict),
  			CONST, encodeSmallInt(3),
  			FUNCALL, 1,
    		PRINT,
    		POP,
    		LOOKUP, encodeDictObject("bar", dict),
    		CONST, encodeDictObject(UNDEFINED, dict),
   			CONST, encodeSmallInt(3),
   			FUNCALL, 1,
    		PRINT, 		
    		RET
  	};
    assertEquals("6\n6\n", execute(new Code(main, 1, 2), dict));
  }
  @Tag("Q10") @Test
  public void callVariableFunction2() {
  	// var foo = function (x) {
    //   return x * 2;
    // }
    // print(foo(3));
    var dict = new Dictionary();
    int[] foo = {
    		LOOKUP, encodeDictObject("*", dict),
    		CONST, encodeDictObject(UNDEFINED, dict),
    		LOAD, 1,
    		CONST, encodeSmallInt(2),
    		FUNCALL, 2,
    		RET
  	};
    var fooFunction = newFunction("lambda", new Code(foo, 2, 2));
  	int[] main = {
  			CONST, encodeDictObject(fooFunction, dict),
  			STORE, 1,
  			LOAD, 1,
  			CONST, encodeDictObject(UNDEFINED, dict),
  			CONST, encodeSmallInt(3),
  			FUNCALL, 1,
    		PRINT,
    		RET
  	};
    assertEquals("6\n", execute(new Code(main, 1, 2), dict));
  }
  @Tag("Q10") @Test
  public void callFunctionWithNoReturn() {
  	// function undef() { }
    // print(undef());
  	var dict = new Dictionary();
    int[] undef = {
    		CONST, encodeDictObject(UNDEFINED, dict),
    		RET
  	};
    var undefFunction = newFunction("lambda", new Code(undef, 1, 1));
  	int[] main = {
  			CONST, encodeDictObject(undefFunction, dict),
  			REGISTER, encodeDictObject("undef", dict),
  			LOOKUP, encodeDictObject("undef", dict),
  			CONST, encodeDictObject(UNDEFINED, dict),
  			FUNCALL, 0,
    		PRINT,
    		RET
  	};
    assertEquals("undefined\n", execute(new Code(main, 1, 1), dict));
  }
  
  @Tag("Q11") @Test
  public void printWithAnIf() {
  	// var a = 2;
		// if (a == 3) {
		//   print("true"); 
		// } else { 
		//  print("false");
		// }
  	var dict = new Dictionary();
  	int[] main = {
  			/* 0*/ CONST, encodeSmallInt(2),
  			/* 2*/ STORE, 1,
  			/* 4*/ LOOKUP, encodeDictObject("==", dict),
  			/* 6*/ CONST, encodeDictObject(UNDEFINED, dict),
  			/* 8*/ LOAD, 1,
  			/*10*/ CONST, encodeSmallInt(3),
  			/*12*/ FUNCALL, 2,
  			/*14*/ JUMP_IF_FALSE, 22,
  			/*16*/ CONST, encodeDictObject("true", dict),
  			/*18*/ PRINT,
  			/*19*/ POP,
  			/*20*/ GOTO, 26,
  			/*22*/ CONST, encodeDictObject("false", dict),
  			/*24*/ PRINT,
  			/*25*/ POP,
  			/*26*/ CONST, encodeDictObject(UNDEFINED, dict),
  			/*28*/ RET
  	};
    assertEquals("false\n", execute(new Code(main, 1, 2), dict));
  }
  @Tag("Q11") @Test
  public void printWithAnIf2() {
    // var a = 3;
 		// if (a == 3) {
 		//   print("true"); 
 		// } else { 
 		//  print("false");
 		// }
   	var dict = new Dictionary();
   	int[] main = {
   			/* 0*/ CONST, encodeSmallInt(3),
   			/* 2*/ STORE, 1,
   			/* 4*/ LOOKUP, encodeDictObject("==", dict),
   			/* 6*/ CONST, encodeDictObject(UNDEFINED, dict),
   			/* 8*/ LOAD, 1,
   			/*10*/ CONST, encodeSmallInt(3),
   			/*12*/ FUNCALL, 2,
   			/*14*/ JUMP_IF_FALSE, 22,
   			/*16*/ CONST, encodeDictObject("true", dict),
   			/*18*/ PRINT,
   			/*19*/ POP,
   			/*20*/ GOTO, 26,
   			/*22*/ CONST, encodeDictObject("false", dict),
   			/*24*/ PRINT,
   			/*25*/ POP,
   			/*26*/ CONST, encodeDictObject(UNDEFINED, dict),
   			/*28*/ RET
   	};
    assertEquals("true\n", execute(new Code(main, 1, 2), dict));
  }
  @Tag("Q11") @Test
  public void printVariableWeirdScope() {
    // var a = 2; 
		// if (a == 3) {
		//   print("true"); 
		//   var b = 'hello'; 
		// } else {
		//   print("false"); 
		// } 
		// print(b);
    var dict = new Dictionary();
    int[] main = {
    		/* 0*/ CONST, encodeSmallInt(2),
    		/* 2*/ STORE, 1,
    		/* 4*/ LOOKUP, encodeDictObject("==", dict),
    		/* 6*/ CONST, encodeDictObject(UNDEFINED, dict),
    		/* 8*/ LOAD, 1,
    		/*10*/ CONST, encodeSmallInt(3),
    		/*12*/ FUNCALL, 2,
    		/*14*/ JUMP_IF_FALSE, 26,
    		/*16*/ CONST, encodeDictObject("true", dict),
    		/*18*/ PRINT,
    		/*19*/ POP,
    		/*20*/ CONST, encodeDictObject("hello", dict),
    		/*22*/ STORE, 2,
    		/*24*/ GOTO, 30,
    		/*26*/ CONST, encodeDictObject("false", dict),
    		/*28*/ PRINT,
    		/*29*/ POP,
    		/*30*/ LOAD, 2,
    		/*32*/ PRINT,
    		/*33*/ CONST, encodeDictObject(UNDEFINED, dict),
    		/*35*/ RET
    };
    assertEquals("false\nundefined\n", execute(new Code(main, 1, 3), dict));
  }
  @Tag("Q11") @Test
  public void printVariableWeirdScope2() {
    // var a = 3; 
 		// if (a == 3) {
 		//   print("true"); 
 		//   var b = 'hello'; 
 		// } else {
 		//   print("false"); 
 		// } 
 		// print(b);
    var dict = new Dictionary();
    int[] main = {
     		/* 0*/ CONST, encodeSmallInt(3),
     		/* 2*/ STORE, 1,
     		/* 4*/ LOOKUP, encodeDictObject("==", dict),
     		/* 6*/ CONST, encodeDictObject(UNDEFINED, dict),
     		/* 8*/ LOAD, 1,
     		/*10*/ CONST, encodeSmallInt(3),
     		/*12*/ FUNCALL, 2,
     		/*14*/ JUMP_IF_FALSE, 26,
     		/*16*/ CONST, encodeDictObject("true", dict),
     		/*18*/ PRINT,
     		/*19*/ POP,
     		/*20*/ CONST, encodeDictObject("hello", dict),
     		/*22*/ STORE, 2,
     		/*24*/ GOTO, 30,
     		/*26*/ CONST, encodeDictObject("false", dict),
     		/*28*/ PRINT,
     		/*29*/ POP,
     		/*30*/ LOAD, 2,
     		/*32*/ PRINT,
     		/*33*/ CONST, encodeDictObject(UNDEFINED, dict),
     		/*35*/ RET
    };
    assertEquals("true\nhello\n", execute(new Code(main, 1, 3), dict));
  }
  @Tag("Q11") @Test
  public void callAUserDefinedFunctionWithAnIf() {
    // function f(x) {
    //   if (x < 3) { 
    //     return 0;
    //   } else {
    //     return x; 
    //   }
    // } 
    // print(f(2)); 
    // print(f(7));
    var dict = new Dictionary();
    int[] f = {
    		/* 0*/ LOOKUP, encodeDictObject("<", dict),
    		/* 2*/ CONST, encodeDictObject(UNDEFINED, dict),
    		/* 4*/ LOAD, 1,
    		/* 6*/ CONST, encodeSmallInt(3),
    		/* 8*/ FUNCALL, 2,
    		/*10*/ JUMP_IF_FALSE, 15,
    		/*12*/ CONST, encodeSmallInt(0),
    		/*14*/ RET,
    		/*15*/ LOAD, 1,
    		/*17*/ RET
  	};
    var fFunction = newFunction("lambda", new Code(f, 2, 2));
    int[] main = {
     		CONST, encodeDictObject(fFunction, dict),
     		DUP,
     		REGISTER, encodeDictObject("f", dict),
     		POP,
     		LOOKUP, encodeDictObject("f", dict),
     		CONST, encodeDictObject(UNDEFINED, dict),
     		CONST, encodeSmallInt(2),
     		FUNCALL, 1,
     		PRINT,
     		POP,
     		LOOKUP, encodeDictObject("f", dict),
     		CONST, encodeDictObject(UNDEFINED, dict),
     		CONST, encodeSmallInt(7),
     		FUNCALL, 1,
     		PRINT,
     		POP,
     		CONST, encodeDictObject(UNDEFINED, dict),
     		RET
    };
    assertEquals("0\n7\n", execute(new Code(main, 1, 1), dict));
  }
  @Tag("Q11") @Test
  public void callAUserDefinedFunctionWithAnIfAndAVariabe() {
  	// function f(x) {
    //   if (x < 3) {
    //     var a = 0;
    //   } else {
    //     var a = x;
    //   }
    //   return a;
    // }
    // print(f(2)); 
    // print(f(7));
  	var dict = new Dictionary();
    int[] f = {
    		/* 0*/ LOOKUP, encodeDictObject("<", dict),
    		/* 2*/ CONST, encodeDictObject(UNDEFINED, dict),
    		/* 4*/ LOAD, 1,
    		/* 6*/ CONST, encodeSmallInt(3),
    		/* 8*/ FUNCALL, 2,
    		/*10*/ JUMP_IF_FALSE, 18,
    		/*12*/ CONST, encodeSmallInt(0),
    		/*14*/ STORE, 2,
    		/*16*/ GOTO, 22,
    		/*18*/ LOAD, 1,
    		/*20*/ STORE, 2,
    		/*22*/ LOAD, 2,
    		/*24*/ RET
  	};
    var fFunction = newFunction("lambda", new Code(f, 2, 3));
    int[] main = {
     		CONST, encodeDictObject(fFunction, dict),
     		DUP,
     		REGISTER, encodeDictObject("f", dict),
     		POP,
     		LOOKUP, encodeDictObject("f", dict),
     		CONST, encodeDictObject(UNDEFINED, dict),
     		CONST, encodeSmallInt(2),
     		FUNCALL, 1,
     		PRINT,
     		POP,
     		LOOKUP, encodeDictObject("f", dict),
     		CONST, encodeDictObject(UNDEFINED, dict),
     		CONST, encodeSmallInt(7),
     		FUNCALL, 1,
     		PRINT,
     		POP,
     		CONST, encodeDictObject(UNDEFINED, dict),
     		RET
    };
    assertEquals("0\n7\n", execute(new Code(main, 1, 1), dict));
  }
  
  
  @Tag("Q12") @Test
  public void callFibo() {
  	// function fibo(n) {
    //   if (n < 2) {
    //     return 1
    //   } else {
    //     return fibo(n - 1) + fibo(n - 2)
    //   } 
    // }
    // print(fibo(7))
  	var dict = new Dictionary();
    int[] fibo = {
    		/* 0*/ LOOKUP, encodeDictObject("<", dict),
    		/* 2*/ CONST, encodeDictObject(UNDEFINED, dict),
    		/* 4*/ LOAD, 1,
    		/* 6*/ CONST, encodeSmallInt(2),
    		/* 8*/ FUNCALL, 2,
    		/*10*/ JUMP_IF_FALSE, 15,
    		/*12*/ CONST, encodeSmallInt(1),
    		/*14*/ RET,
    		/*15*/ LOOKUP, encodeDictObject("+", dict),
    		       CONST, encodeDictObject(UNDEFINED, dict),
    		       LOOKUP, encodeDictObject("fibo", dict),
    		       CONST, encodeDictObject(UNDEFINED, dict),
    		       LOOKUP, encodeDictObject("-", dict),
    		       CONST, encodeDictObject(UNDEFINED, dict),
    		       LOAD, 1,
    		       CONST, encodeSmallInt(1),
    		       FUNCALL, 2,
    		       FUNCALL, 1,
    		       LOOKUP, encodeDictObject("fibo", dict),
    		       CONST, encodeDictObject(UNDEFINED, dict),
    		       LOOKUP, encodeDictObject("-", dict),
    		       CONST, encodeDictObject(UNDEFINED, dict),
    		       LOAD, 1,
    		       CONST, encodeSmallInt(2),
    		       FUNCALL, 2,
    		       FUNCALL, 1,
    		       FUNCALL, 2,
    		       RET
  	};
    var fiboFunction = newFunction("fibo", new Code(fibo, 2, 2));
    int[] main = {
     		CONST, encodeDictObject(fiboFunction, dict),
     		DUP,
     		REGISTER, encodeDictObject("fibo", dict),
     		POP,
     		LOOKUP, encodeDictObject("fibo", dict),
     		CONST, encodeDictObject(UNDEFINED, dict),
     		CONST, encodeSmallInt(7),
     		FUNCALL, 1,
     		PRINT,
     		POP,
     		CONST, encodeDictObject(UNDEFINED, dict),
     		RET
    };
    assertEquals("21\n", execute(new Code(main, 1, 1), dict));
  }
  @Tag("Q12") @Test
  public void callRecursiveFunction() {
  	// function fact(n) {
    //   if (n < 1) {
    //     return 1;		
    //   } else {
    //     return n * fact(n - 1);
    //   }
    // }
    // print(fact(4));\n"
  	var dict = new Dictionary();
    int[] fact = {
    		/* 0*/ LOOKUP, encodeDictObject("<", dict),
    		/* 2*/ CONST, encodeDictObject(UNDEFINED, dict),
    		/* 4*/ LOAD, 1,
    		/* 6*/ CONST, encodeSmallInt(1),
    		/* 8*/ FUNCALL, 2,
    		/*10*/ JUMP_IF_FALSE, 15,
    		/*12*/ CONST, encodeSmallInt(1),
    		/*14*/ RET,
    		/*15*/ LOOKUP, encodeDictObject("*", dict),
    		       CONST, encodeDictObject(UNDEFINED, dict),
    		       LOAD, 1,
    		       LOOKUP, encodeDictObject("fact", dict),
    		       CONST, encodeDictObject(UNDEFINED, dict),
    		       LOOKUP, encodeDictObject("-", dict),
    		       CONST, encodeDictObject(UNDEFINED, dict),
    		       LOAD, 1,
    		       CONST, encodeSmallInt(1),
    		       FUNCALL, 2,
    		       FUNCALL, 1,
    		       FUNCALL, 2,
    		       RET
  	};
    var factFunction = newFunction("fact", new Code(fact, 2, 2));
    int[] main = {
     		CONST, encodeDictObject(factFunction, dict),
     		DUP,
     		REGISTER, encodeDictObject("fact", dict),
     		POP,
     		LOOKUP, encodeDictObject("fact", dict),
     		CONST, encodeDictObject(UNDEFINED, dict),
     		CONST, encodeSmallInt(4),
     		FUNCALL, 1,
     		PRINT,
     		POP,
     		CONST, encodeDictObject(UNDEFINED, dict),
     		RET
    };
    assertEquals("24\n", execute(new Code(main, 1, 1), dict));
  }
  @Tag("Q12") @Test
  public void callSeveralOperations() {
  	// function calc(f, a, b) {
    //   return f(a, b);
    // }
    // print(calc(+, 2, 3));
    // print(calc(-, 2, 3));
    // print(calc(*, 2, 3));
    // print(calc(/, 2, 3));
  	var dict = new Dictionary();
    int[] calc = {
    		LOAD, 1,
    		CONST, encodeDictObject(UNDEFINED, dict),
    		LOAD, 2,
    		LOAD, 3,
    		FUNCALL, 2,
    		RET
  	};
    var calcFunction = newFunction("calc", new Code(calc, 4, 4));
    int[] main = {
     		CONST, encodeDictObject(calcFunction, dict),
     		DUP,
     		REGISTER, encodeDictObject("calc", dict),
     		POP,
     		LOOKUP, encodeDictObject("calc", dict),
     		CONST, encodeDictObject(UNDEFINED, dict),
     		LOOKUP, encodeDictObject("+", dict),
     		CONST, encodeSmallInt(2),
     		CONST, encodeSmallInt(3),
     		FUNCALL, 3,
     		PRINT,
     		POP,
     		LOOKUP, encodeDictObject("calc", dict),
     		CONST, encodeDictObject(UNDEFINED, dict),
     		LOOKUP, encodeDictObject("-", dict),
     		CONST, encodeSmallInt(2),
     		CONST, encodeSmallInt(3),
     		FUNCALL, 3,
     		PRINT,
     		POP,
     		LOOKUP, encodeDictObject("calc", dict),
     		CONST, encodeDictObject(UNDEFINED, dict),
     		LOOKUP, encodeDictObject("*", dict),
     		CONST, encodeSmallInt(2),
     		CONST, encodeSmallInt(3),
     		FUNCALL, 3,
     		PRINT,
     		POP,
     		LOOKUP, encodeDictObject("calc", dict),
     		CONST, encodeDictObject(UNDEFINED, dict),
     		LOOKUP, encodeDictObject("/", dict),
     		CONST, encodeSmallInt(2),
     		CONST, encodeSmallInt(3),
     		FUNCALL, 3,
     		PRINT,
     		POP,
     		CONST, encodeDictObject(UNDEFINED, dict),
     		RET
    };
    assertEquals("5\n-1\n6\n0\n", execute(new Code(main, 1, 1), dict));
  }
  
  @Tag("Q13") @Test
  public void createAnObject() {
    // var o = {
    //   x: 1
    //   y: 2
    // };
    // print(o);
    var dict = new Dictionary();
    var clazz = JSObject.newObject(null);
    clazz.register("x", 0);
    clazz.register("y", 1);
  	int[] instrs = {
  			CONST, encodeSmallInt(1),
  			CONST, encodeSmallInt(2),
  			NEW, encodeDictObject(clazz, dict),
  			STORE, 1,
  			LOAD, 1,
    		PRINT,
    		RET
  	};
  	assertEquals(
  			"{ // object\n"   +
        "  x: 1\n"        +
        "  y: 2\n"        +
        "  proto: null\n" +
        "}\n",
        execute(new Code(instrs, 1, 2), dict));
  }
  
  @Tag("Q14") @Test
  // var a = 1; 
  // var o = {
  //   x: a,
  //   y: a + 1
  // }
  // print(o);
  public void createAnObjectFromAVariableValue() {
  	var dict = new Dictionary();
    var clazz = JSObject.newObject(null);
    clazz.register("x", 0);
    clazz.register("y", 1);
  	int[] instrs = {
  			CONST, encodeSmallInt(1),
  			STORE, 1,
  			LOAD, 1,
  			LOOKUP, encodeDictObject("+", dict),
  			CONST, encodeDictObject(UNDEFINED, dict),
  			LOAD, 1,
  			CONST, encodeSmallInt(1),
  			FUNCALL, 2,
  			NEW, encodeDictObject(clazz, dict),
  			STORE, 2,
  			LOAD, 2,
    		PRINT,
    		RET
  	};
    assertEquals(
        "{ // object\n"   +
        "  x: 1\n"        + 
        "  y: 2\n"        + 
        "  proto: null\n" +
        "}\n",
        execute(new Code(instrs, 1, 3), dict));
        
  }
  @Tag("Q14") @Test
  public void createAnObjectEvaluationOrder() {
  	// var foo = {
    //   a: print('a'),
    //   b: print('b')
    // };
  	var dict = new Dictionary();
    var clazz = JSObject.newObject(null);
    clazz.register("a", 0);
    clazz.register("b", 1);
  	int[] instrs = {
  			CONST, encodeDictObject("a", dict),
  			PRINT,
  			CONST, encodeDictObject("b", dict),
  			PRINT,
  			NEW, encodeDictObject(clazz, dict),
  			STORE, 1,
  			CONST, encodeDictObject(UNDEFINED, dict),
    		RET
  	};
    assertEquals(
        "a\nb\n",
        execute(new Code(instrs, 1, 2), dict));
  }
  
  @Tag("Q15") @Test
  public void objectGetAFieldValue() {
  	// var john = { name: \"John\" };
    // print(john.name);
  	var dict = new Dictionary();
    var clazz = JSObject.newObject(null);
    clazz.register("name", 0);
  	int[] instrs = {
  			CONST, encodeDictObject("John", dict),
  			NEW, encodeDictObject(clazz, dict),
  			STORE, 1,
  			LOAD, 1,
  			GET, encodeDictObject("name", dict),
  			PRINT,
  			POP,
  			CONST, encodeDictObject(UNDEFINED, dict),
    		RET
  	};
    assertEquals("John\n",
        execute(new Code(instrs, 1, 2), dict));
  }
  @Tag("Q15") @Test
  public void objectGetAFieldNoValue() {
  	// var john = { name: \"John\" };
    // print(john.foo);
  	var dict = new Dictionary();
    var clazz = JSObject.newObject(null);
    clazz.register("name", 0);
  	int[] instrs = {
  			CONST, encodeDictObject("John", dict),
  			NEW, encodeDictObject(clazz, dict),
  			STORE, 1,
  			LOAD, 1,
  			GET, encodeDictObject("foo", dict),
  			PRINT,
  			POP,
  			CONST, encodeDictObject(UNDEFINED, dict),
    		RET
  	};
  	assertEquals("undefined\n",
        execute(new Code(instrs, 1, 2), dict));
        
  }
  
  @Tag("Q16") @Test
  public void objectSetAFieldValue() {
    // var john = { name: \"John\" };
    // john.name = \"Jane\";
    // print(john.name);
  	var dict = new Dictionary();
    var clazz = JSObject.newObject(null);
    clazz.register("name", 0);
  	int[] instrs = {
  			CONST, encodeDictObject("John", dict),
  			NEW, encodeDictObject(clazz, dict),
  			STORE, 1,
  			LOAD, 1,
  			CONST, encodeDictObject("Jane", dict),
  			PUT, encodeDictObject("name", dict),
  			LOAD, 1,
  			GET, encodeDictObject("name", dict),
  			PRINT,
  			POP,
  			CONST, encodeDictObject(UNDEFINED, dict),
    		RET
  	};
    assertEquals("Jane\n",
        execute(new Code(instrs, 1, 2), dict));
        
  }
  
  @Tag("Q17") @Test
  public void objectCallAMethod() {
  	// var object = {
    //   bar: \"hello\",
    //   foo: function(x) {
    //          print(this.bar, x);
    //        }
    //};
    //object.foo(42);
  	var dict = new Dictionary();
  	int[] foo = {
    		LOOKUP, encodeDictObject("print", dict),
    		CONST, encodeDictObject(UNDEFINED, dict),
    		LOAD, 0,
    		GET, encodeDictObject("bar", dict),
    		LOAD, 1,
    		FUNCALL, 2,
    		CONST, encodeDictObject(UNDEFINED, dict),
    		RET
  	};
    var fooFunction = newFunction("lambda", new Code(foo, 2, 2));
    var clazz = JSObject.newObject(null);
    clazz.register("bar", 0);
    clazz.register("foo", 1);
  	int[] instrs = {
  			CONST, encodeDictObject("hello", dict),
  			CONST, encodeDictObject(fooFunction, dict),
  			NEW, encodeDictObject(clazz, dict),
  			STORE, 1,
  			LOAD, 1,
  			DUP,
  			GET, encodeDictObject("foo", dict),
  			SWAP,
  			CONST, encodeSmallInt(42),
  			FUNCALL, 1,
  			POP,
  			CONST, encodeDictObject(UNDEFINED, dict),
    		RET
  	};
  	assertEquals("hello 42\n",
        execute(new Code(instrs, 1, 2), dict));
  }
}
