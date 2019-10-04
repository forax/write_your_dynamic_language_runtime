package fr.umlv.smalljs.stackinterp.main;

import static fr.umlv.smalljs.rt.JSObject.UNDEFINED;
import static fr.umlv.smalljs.stackinterp.Instructions.CONST;
import static fr.umlv.smalljs.stackinterp.Instructions.FUNCALL;
import static fr.umlv.smalljs.stackinterp.Instructions.GOTO;
import static fr.umlv.smalljs.stackinterp.Instructions.JUMP_IF_FALSE;
import static fr.umlv.smalljs.stackinterp.Instructions.LOAD;
import static fr.umlv.smalljs.stackinterp.Instructions.LOOKUP;
import static fr.umlv.smalljs.stackinterp.Instructions.NEW;
import static fr.umlv.smalljs.stackinterp.Instructions.POP;
import static fr.umlv.smalljs.stackinterp.Instructions.RET;
import static fr.umlv.smalljs.stackinterp.Instructions.STORE;
import static fr.umlv.smalljs.stackinterp.TagValues.encodeDictObject;
import static fr.umlv.smalljs.stackinterp.TagValues.encodeSmallInt;

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
public class StackInterpreterGCTests {
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
  public void gcTest() {
  	var dict = new Dictionary();
  	var pointClass = JSObject.newObject(null);
  	pointClass.register("x", 0);
  	pointClass.register("y", 1);
  	int[] instrs = {
  		/* 0:*/ CONST, encodeSmallInt(100),
  	  /* 2:*/ STORE, 1,
  	  
  	  /* 4:*/ LOAD, 1,
  	  /* 6:*/ JUMP_IF_FALSE, 29,
  	  
  	  /* 8:*/ CONST, encodeSmallInt(1),
  	  /*10:*/ CONST, encodeSmallInt(2),
  	  /*12:*/ NEW, encodeDictObject(pointClass, dict), 
  	  /*14:*/ POP,
  	  
  	  /*15:*/ LOOKUP, encodeDictObject("-", dict), 
  	  /*17:*/ CONST, encodeDictObject(UNDEFINED, dict),
  	  /*19:*/ LOAD, 1,
  	  /*21:*/ CONST, encodeSmallInt(1),
  	  /*23:*/ FUNCALL, 2,
  	  /*25:*/ STORE, 1,
  	  
  	  /*27:*/ GOTO, 4,
  	  
  	  /*29:*/ CONST, encodeDictObject(UNDEFINED, dict),
  	  /*31:*/ RET
  	};
    execute(new Code(instrs, 1, 2), dict);
  }
  
  @Tag("Q3") @Test
  public void gcTestWithFields() {
  	var dict = new Dictionary();
  	var pointClass = JSObject.newObject(null);
  	pointClass.register("x", 0);
  	pointClass.register("y", 1);
  	var emptyClass = JSObject.newObject(null);
  	int[] instrs = {
  		/* 0:*/ CONST, encodeSmallInt(100),
  	  /* 2:*/ STORE, 1,
  	  
  	  /* 4:*/ LOAD, 1,
  	  /* 6:*/ JUMP_IF_FALSE, 29,
  	  
  	  /* 8:*/ NEW, encodeDictObject(emptyClass, dict), 
  	  /*10:*/ NEW, encodeDictObject(emptyClass, dict),
  	  /*12:*/ NEW, encodeDictObject(pointClass, dict),
  	  
  	  /*14:*/ POP,
  	  
  	  /*15:*/ LOOKUP, encodeDictObject("-", dict), 
  	  /*17:*/ CONST, encodeDictObject(UNDEFINED, dict),
  	  /*19:*/ LOAD, 1,
  	  /*21:*/ CONST, encodeSmallInt(1),
  	  /*23:*/ FUNCALL, 2,
  	  /*25:*/ STORE, 1,
  	  
  	  /*27:*/ GOTO, 4,
  	  
  	  /*29:*/ CONST, encodeDictObject(UNDEFINED, dict),
  	  /*31:*/ RET
  	};
    execute(new Code(instrs, 1, 2), dict);
  }
  
  @Tag("Q4") @Test
  public void gcTestLikedList() {
  	var dict = new Dictionary();
  	var linkClass = JSObject.newObject(null);
  	linkClass.register("value", 0);
  	linkClass.register("next", 1);
  	int[] instrs = {
  		/* 0:*/ CONST, encodeSmallInt(100),
  	  /* 2:*/ STORE, 1,
  	  /* 4:*/ LOAD, 1,
  	  /* 6:*/ JUMP_IF_FALSE, 60,
  	  /* 8:*/ CONST, encodeSmallInt(10),
  	  /*10:*/ STORE, 2,
  	  /*12:*/ CONST, encodeDictObject(UNDEFINED, dict),
  	  /*14:*/ STORE, 3,
  	  /*16:*/ LOAD, 2,
  	  /*18:*/ JUMP_IF_FALSE, 42,
  	  /*20:*/ LOAD, 3,
  	  /*22:*/ LOAD, 2,
  	  /*24:*/ NEW, encodeDictObject(linkClass, dict),
  	  /*26:*/ STORE, 3,
  	  
  	  /*28:*/ LOOKUP, encodeDictObject("-", dict), 
  	  /*30:*/ CONST, encodeDictObject(UNDEFINED, dict),
  	  /*32:*/ LOAD, 2,
  	  /*34:*/ CONST, encodeSmallInt(1),
  	  /*36:*/ FUNCALL, 2,
  	  /*38:*/ STORE, 2,
  	  
  	  /*40:*/ GOTO, 16,
  	  /*42:*/ CONST, encodeDictObject(UNDEFINED, dict),  // free
  	  /*44:*/ STORE, 3,
  	  
  	  /*46:*/ LOOKUP, encodeDictObject("-", dict), 
  	  /*48:*/ CONST, encodeDictObject(UNDEFINED, dict),
  	  /*50:*/ LOAD, 1,
  	  /*52:*/ CONST, encodeSmallInt(1),
  	  /*54:*/ FUNCALL, 2,
  	  /*56:*/ STORE, 1,
  	  /*58:*/ GOTO, 4,
  	  /*60:*/ CONST, encodeDictObject(UNDEFINED, dict),
  	  /*62:*/ RET
  	};
    execute(new Code(instrs, 1, 4), dict);
  }
}
