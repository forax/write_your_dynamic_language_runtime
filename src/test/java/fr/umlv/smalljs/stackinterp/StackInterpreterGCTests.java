package fr.umlv.smalljs.stackinterp;

import fr.umlv.smalljs.rt.Failure;
import fr.umlv.smalljs.rt.JSObject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static fr.umlv.smalljs.rt.JSObject.UNDEFINED;
import static fr.umlv.smalljs.stackinterp.Instructions.CONST;
import static fr.umlv.smalljs.stackinterp.Instructions.FUNCALL;
import static fr.umlv.smalljs.stackinterp.Instructions.GET;
import static fr.umlv.smalljs.stackinterp.Instructions.GOTO;
import static fr.umlv.smalljs.stackinterp.Instructions.JUMP_IF_FALSE;
import static fr.umlv.smalljs.stackinterp.Instructions.LOAD;
import static fr.umlv.smalljs.stackinterp.Instructions.LOOKUP;
import static fr.umlv.smalljs.stackinterp.Instructions.NEW;
import static fr.umlv.smalljs.stackinterp.Instructions.POP;
import static fr.umlv.smalljs.stackinterp.Instructions.PRINT;
import static fr.umlv.smalljs.stackinterp.Instructions.PUT;
import static fr.umlv.smalljs.stackinterp.Instructions.RET;
import static fr.umlv.smalljs.stackinterp.Instructions.STORE;
import static fr.umlv.smalljs.stackinterp.TagValues.encodeDictObject;
import static fr.umlv.smalljs.stackinterp.TagValues.encodeSmallInt;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("static-method")
public class StackInterpreterGCTests {
	private static JSObject newMainFunction(Code code) {
    var fun = JSObject.newFunction("main", (receiver, args) -> {
      throw new Failure("native call not supported");
    });
    fun.register("__code__", code);
    return fun;
  }
  private static String execute(Code mainCode, Dictionary dict) {
    var outStream = new ByteArrayOutputStream(8192);
    var globalEnv = StackInterpreter.createGlobalEnv(new PrintStream(outStream, false, UTF_8));
    var mainFun = newMainFunction(mainCode);
    globalEnv.register("main", mainFun);
    StackInterpreter.execute(mainFun, dict, globalEnv);
    return outStream.toString(UTF_8).replace("\r\n", "\n");
  }

	/*
  @Tag("Q2") @Test
  public void gcTest() {
  	var dict = new Dictionary();
  	var pointClass = JSObject.newObject(null);
  	pointClass.register("x", 0);
  	pointClass.register("y", 1);
  	int[] instrs = {
  	   CONST, encodeSmallInt(100),                 //  0
  	   STORE, 1,                                         //  2

  	   LOAD, 1,                                          //  4
  	   JUMP_IF_FALSE, 29,                                //  6

  	   CONST, encodeSmallInt(1),                   //  8
  	   CONST, encodeSmallInt(2),                   // 10
  	   NEW, encodeDictObject(pointClass, dict),   // 12
  	   POP,

  	   LOOKUP, encodeDictObject("-", dict),       // 15
  	   CONST, encodeDictObject(UNDEFINED, dict),  // 17
  	   LOAD, 1,                                          // 19
  	   CONST, encodeSmallInt(1),                   // 21
  	   FUNCALL, 2,                                       // 23
  	   STORE, 1,                                         // 25

  	   GOTO, 4,                                          // 27

  	   CONST, encodeDictObject(UNDEFINED, dict),  // 29
  	   RET                                               // 31
  	};
    execute(new Code(instrs, 1, 2), dict);
  }

  @Tag("Q3") @Test
  public void gcTestRewriteField() {
  	var dict = new Dictionary();
  	var clazz = JSObject.newObject(null);
  	clazz.register("field", 0);
  	int[] instrs = {
  		  CONST, encodeSmallInt(21),                  //  0
    	  NEW, encodeDictObject(clazz, dict),        //  2
    	  POP,  // should be GCed                           //  4
        
    	  CONST, encodeSmallInt(42),                  //  5
    	  NEW, encodeDictObject(clazz, dict),        //  7
    	  STORE, 2, // should not be GCed                   //  9
        
    	  LOAD, 2,                                          // 11
    	  CONST, encodeSmallInt(84),                  // 13
    	  NEW, encodeDictObject(clazz, dict),        // 15
    	  PUT, encodeDictObject("field", dict),      // 17
        
  		  CONST, encodeSmallInt(100),                 // 19
  	    STORE, 1,                                         // 21
        
  	    LOAD, 1,                                          // 23
  	    JUMP_IF_FALSE, 46,                                // 25
        
  	    LOAD, 2,                                          // 27
  	    NEW, encodeDictObject(clazz, dict),        // 29
  	    POP,  // should be GCed                           // 31
        
  	    LOOKUP, encodeDictObject("-", dict),       // 32
  	    CONST, encodeDictObject(UNDEFINED, dict),  // 34
  	    LOAD, 1,                                          // 36
  	    CONST, encodeSmallInt(1),                   // 38
  	    FUNCALL, 2,                                       // 40
  	    STORE, 1,                                         // 42
        
  	    GOTO, 23,                                         // 44
        
  	    LOAD, 2,                                          // 46
  	    GET, encodeDictObject("field", dict),      // 48
  	    GET, encodeDictObject("field", dict),      // 50
  	    PRINT,
        
  	    CONST, encodeDictObject(UNDEFINED, dict),  // 53
  	    RET                                               // 55
  	};
    assertEquals("84\n", execute(new Code(instrs, 1, 3), dict));
  }

  @Tag("Q4") @Test
  public void gcTestWithFields() {
  	var dict = new Dictionary();
  	var pointClass = JSObject.newObject(null);
  	pointClass.register("x", 0);
  	pointClass.register("y", 1);
  	var emptyClass = JSObject.newObject(null);
  	int[] instrs = {
  		 CONST, encodeSmallInt(100),                 //  0
  	   STORE, 1,                                         //  2
       
  	   LOAD, 1,                                          //  4
  	   JUMP_IF_FALSE, 29,                                //  6
       
  	   NEW, encodeDictObject(emptyClass, dict),   //  8
  	   NEW, encodeDictObject(emptyClass, dict),   // 10
  	   NEW, encodeDictObject(pointClass, dict),   // 12
       
  	   POP,                                              // 14
       
  	   LOOKUP, encodeDictObject("-", dict),       // 15
  	   CONST, encodeDictObject(UNDEFINED, dict),  // 17
  	   LOAD, 1,                                          // 19
  	   CONST, encodeSmallInt(1),                   // 21
  	   FUNCALL, 2,                                       // 23
  	   STORE, 1,                                         // 25
       
  	   GOTO, 4,                                          // 27
       
  	   CONST, encodeDictObject(UNDEFINED, dict),  // 29
  	   RET                                               // 31
  	};
    execute(new Code(instrs, 1, 2), dict);
  }

  @Tag("Q5") @Test
  public void gcTestLikedList() {
  	var dict = new Dictionary();
  	var linkClass = JSObject.newObject(null);
  	linkClass.register("value", 0);
  	linkClass.register("next", 1);
  	int[] instrs = {
  		  CONST, encodeSmallInt(100),                 //  0
  	    STORE, 1,                                         //  2
  	    LOAD, 1,                                          //  4
  	    JUMP_IF_FALSE, 60,                                //  6
  	    CONST, encodeSmallInt(10),                  //  8
  	    STORE, 2,                                         // 10
  	    CONST, encodeDictObject(UNDEFINED, dict),  // 12
  	    STORE, 3,                                         // 14
  	    LOAD, 2,                                          // 16
  	    JUMP_IF_FALSE, 42,                                // 18
  	    LOAD, 3,                                          // 20
  	    LOAD, 2,                                          // 22
  	    NEW, encodeDictObject(linkClass, dict),    // 24
  	    STORE, 3,                                         // 26
        
  	    LOOKUP, encodeDictObject("-", dict),       // 28
  	    CONST, encodeDictObject(UNDEFINED, dict),  // 30
  	    LOAD, 2,                                          // 32
  	    CONST, encodeSmallInt(1),                   // 34
  	    FUNCALL, 2,                                       // 36
  	    STORE, 2,                                         // 38
        
  	    GOTO, 16,                                         // 40
  	    CONST, encodeDictObject(UNDEFINED, dict),  // 42
  	    STORE, 3,                                         // 44
        
  	    LOOKUP, encodeDictObject("-", dict),       // 46
  	    CONST, encodeDictObject(UNDEFINED, dict),  // 48
  	    LOAD, 1,                                          // 50
  	    CONST, encodeSmallInt(1),                   // 52
  	    FUNCALL, 2,                                       // 54
  	    STORE, 1,                                         // 56
  	    GOTO, 4,                                          // 58
  	    CONST, encodeDictObject(UNDEFINED, dict),  // 60
  	    RET                                               // 62
  	};
    execute(new Code(instrs, 1, 4), dict);
  }
	*/
}
