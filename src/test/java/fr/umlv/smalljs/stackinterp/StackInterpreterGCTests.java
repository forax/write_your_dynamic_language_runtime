package fr.umlv.smalljs.stackinterp;

import fr.umlv.smalljs.rt.Failure;
import fr.umlv.smalljs.rt.JSObject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

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
    var fun = JSObject.newFunction("main", (self, receiver, args) -> {
    	      throw new Failure("native call not supported");
        }
    );
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

//  @Tag("Q2") @Test
//  public void gcTest() {
//  	var dict = new Dictionary();
//  	var pointClass = JSObject.newObject(null);
//  	pointClass.register("x", 0);
//  	pointClass.register("y", 1);
//  	int[] instrs = {
//  		/* 0:*/ CONST, encodeSmallInt(100),
//  	  /* 2:*/ STORE, 1,
//
//  	  /* 4:*/ LOAD, 1,
//  	  /* 6:*/ JUMP_IF_FALSE, 29,
//
//  	  /* 8:*/ CONST, encodeSmallInt(1),
//  	  /*10:*/ CONST, encodeSmallInt(2),
//  	  /*12:*/ NEW, encodeDictObject(pointClass, dict),
//  	  /*14:*/ POP,
//
//  	  /*15:*/ LOOKUP, encodeDictObject("-", dict),
//  	  /*17:*/ CONST, encodeDictObject(UNDEFINED, dict),
//  	  /*19:*/ LOAD, 1,
//  	  /*21:*/ CONST, encodeSmallInt(1),
//  	  /*23:*/ FUNCALL, 2,
//  	  /*25:*/ STORE, 1,
//
//  	  /*27:*/ GOTO, 4,
//
//  	  /*29:*/ CONST, encodeDictObject(UNDEFINED, dict),
//  	  /*31:*/ RET
//  	};
//    execute(new Code(instrs, 1, 2), dict);
//  }
//
//  @Tag("Q3") @Test
//  public void gcTestRewriteField() {
//  	var dict = new Dictionary();
//  	var clazz = JSObject.newObject(null);
//  	clazz.register("field", 0);
//  	int[] instrs = {
//  		/* 0:*/ CONST, encodeSmallInt(21),
//    	/* 2:*/ NEW, encodeDictObject(clazz, dict),
//    	/* 4:*/ POP,  // should be GCed
//
//    	/* 5:*/ CONST, encodeSmallInt(42),
//    	/* 7:*/ NEW, encodeDictObject(clazz, dict),
//    	/* 9:*/ STORE, 2, // should not be GCed
//
//    	/*11:*/ LOAD, 2,
//    	/*13:*/ CONST, encodeSmallInt(84),
//    	/*15:*/ NEW, encodeDictObject(clazz, dict),
//    	/*17:*/ PUT, encodeDictObject("field", dict), // modification after creation
//
//  		/*19:*/ CONST, encodeSmallInt(100),
//  	  /*21:*/ STORE, 1,
//
//  	  /*23:*/ LOAD, 1,
//  	  /*25:*/ JUMP_IF_FALSE, 46,
//
//  	  /*27:*/ LOAD, 2,
//  	  /*29:*/ NEW, encodeDictObject(clazz, dict),
//  	  /*31:*/ POP,  // should be GCed
//
//  	  /*32:*/ LOOKUP, encodeDictObject("-", dict),
//  	  /*34:*/ CONST, encodeDictObject(UNDEFINED, dict),
//  	  /*36:*/ LOAD, 1,
//  	  /*38:*/ CONST, encodeSmallInt(1),
//  	  /*40:*/ FUNCALL, 2,
//  	  /*42:*/ STORE, 1,
//
//  	  /*44:*/ GOTO, 23,
//
//  	  /*46:*/ LOAD, 2,
//  	  /*48:*/ GET, encodeDictObject("field", dict),
//  	  /*50:*/ GET, encodeDictObject("field", dict),
//  	  /*52:*/ PRINT,
//
//  	  /*53:*/ CONST, encodeDictObject(UNDEFINED, dict),
//  	  /*31:*/ RET
//  	};
//    assertEquals("84\n", execute(new Code(instrs, 1, 3), dict));
//  }
//
//  @Tag("Q4") @Test
//  public void gcTestWithFields() {
//  	var dict = new Dictionary();
//  	var pointClass = JSObject.newObject(null);
//  	pointClass.register("x", 0);
//  	pointClass.register("y", 1);
//  	var emptyClass = JSObject.newObject(null);
//  	int[] instrs = {
//  		/* 0:*/ CONST, encodeSmallInt(100),
//  	  /* 2:*/ STORE, 1,
//
//  	  /* 4:*/ LOAD, 1,
//  	  /* 6:*/ JUMP_IF_FALSE, 29,
//
//  	  /* 8:*/ NEW, encodeDictObject(emptyClass, dict),
//  	  /*10:*/ NEW, encodeDictObject(emptyClass, dict),
//  	  /*12:*/ NEW, encodeDictObject(pointClass, dict),
//
//  	  /*14:*/ POP,
//
//  	  /*15:*/ LOOKUP, encodeDictObject("-", dict),
//  	  /*17:*/ CONST, encodeDictObject(UNDEFINED, dict),
//  	  /*19:*/ LOAD, 1,
//  	  /*21:*/ CONST, encodeSmallInt(1),
//  	  /*23:*/ FUNCALL, 2,
//  	  /*25:*/ STORE, 1,
//
//  	  /*27:*/ GOTO, 4,
//
//  	  /*29:*/ CONST, encodeDictObject(UNDEFINED, dict),
//  	  /*31:*/ RET
//  	};
//    execute(new Code(instrs, 1, 2), dict);
//  }
//
//  @Tag("Q5") @Test
//  public void gcTestLikedList() {
//  	var dict = new Dictionary();
//  	var linkClass = JSObject.newObject(null);
//  	linkClass.register("value", 0);
//  	linkClass.register("next", 1);
//  	int[] instrs = {
//  		/* 0:*/ CONST, encodeSmallInt(100),
//  	  /* 2:*/ STORE, 1,
//  	  /* 4:*/ LOAD, 1,
//  	  /* 6:*/ JUMP_IF_FALSE, 60,
//  	  /* 8:*/ CONST, encodeSmallInt(10),
//  	  /*10:*/ STORE, 2,
//  	  /*12:*/ CONST, encodeDictObject(UNDEFINED, dict),
//  	  /*14:*/ STORE, 3,
//  	  /*16:*/ LOAD, 2,
//  	  /*18:*/ JUMP_IF_FALSE, 42,
//  	  /*20:*/ LOAD, 3,
//  	  /*22:*/ LOAD, 2,
//  	  /*24:*/ NEW, encodeDictObject(linkClass, dict),
//  	  /*26:*/ STORE, 3,
//
//  	  /*28:*/ LOOKUP, encodeDictObject("-", dict),
//  	  /*30:*/ CONST, encodeDictObject(UNDEFINED, dict),
//  	  /*32:*/ LOAD, 2,
//  	  /*34:*/ CONST, encodeSmallInt(1),
//  	  /*36:*/ FUNCALL, 2,
//  	  /*38:*/ STORE, 2,
//
//  	  /*40:*/ GOTO, 16,
//  	  /*42:*/ CONST, encodeDictObject(UNDEFINED, dict),  // free
//  	  /*44:*/ STORE, 3,
//
//  	  /*46:*/ LOOKUP, encodeDictObject("-", dict),
//  	  /*48:*/ CONST, encodeDictObject(UNDEFINED, dict),
//  	  /*50:*/ LOAD, 1,
//  	  /*52:*/ CONST, encodeSmallInt(1),
//  	  /*54:*/ FUNCALL, 2,
//  	  /*56:*/ STORE, 1,
//  	  /*58:*/ GOTO, 4,
//  	  /*60:*/ CONST, encodeDictObject(UNDEFINED, dict),
//  	  /*62:*/ RET
//  	};
//    execute(new Code(instrs, 1, 4), dict);
//  }
}
