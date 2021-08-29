package fr.umlv.smalljs.astinterp;

import fr.umlv.smalljs.ast.Expr;
import fr.umlv.smalljs.ast.Expr.Block;
import fr.umlv.smalljs.ast.Expr.FieldAccess;
import fr.umlv.smalljs.ast.Expr.FieldAssignment;
import fr.umlv.smalljs.ast.Expr.Fun;
import fr.umlv.smalljs.ast.Expr.FunCall;
import fr.umlv.smalljs.ast.Expr.If;
import fr.umlv.smalljs.ast.Expr.Literal;
import fr.umlv.smalljs.ast.Expr.LocalVarAccess;
import fr.umlv.smalljs.ast.Expr.LocalVarAssignment;
import fr.umlv.smalljs.ast.Expr.MethodCall;
import fr.umlv.smalljs.ast.Expr.New;
import fr.umlv.smalljs.ast.Expr.Return;
import fr.umlv.smalljs.ast.Script;
import fr.umlv.smalljs.rt.Failure;
import fr.umlv.smalljs.rt.JSObject;
import fr.umlv.smalljs.rt.JSObject.Invoker;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.stream.Collectors;

import static fr.umlv.smalljs.rt.JSObject.UNDEFINED;

public class ASTInterpreter {
  private static JSObject asJSObject(Object value, Expr failedExpr) {
		if (!(value instanceof JSObject jsObject)) {
			throw new Failure("at line " + failedExpr.lineNumber() + ", type error " + value + " is not a JSObject");
		}
		return jsObject;
  }

  static Object visit(Expr expr, JSObject env) {
    return switch (expr) {
      case Block block -> {
				//throw new UnsupportedOperationException("TODO Block");
        // TODO loop over all instructions
        yield UNDEFINED;
      }
      case Literal literal -> {
				throw new UnsupportedOperationException("TODO Literal");
			}
      case FunCall funCall -> {
				throw new UnsupportedOperationException("TODO FunCall");
      }
      case LocalVarAccess localVarAccess -> {
				throw new UnsupportedOperationException("TODO LocalVarAccess");
			}
      case LocalVarAssignment localVarAssignment -> {
				throw new UnsupportedOperationException("TODO LocalVarAssignment");
      }
      case Fun fun -> {
				throw new UnsupportedOperationException("TODO Fun");
      }
      case Return _return -> {
				throw new UnsupportedOperationException("TODO Return");
      }
      case If _if -> {
				throw new UnsupportedOperationException("TODO If");
      }
      case New _new -> {
				throw new UnsupportedOperationException("TODO New");
      }
      case FieldAccess fieldAccess -> {
        throw new UnsupportedOperationException("TODO FieldAccess");
      }
      case FieldAssignment fieldAssignment -> {
        throw new UnsupportedOperationException("TODO FieldAssignment");
      }
      case MethodCall methodCall -> {
        throw new UnsupportedOperationException("TODO MethodCall");
      }
    };
  }

  @SuppressWarnings("unchecked")
  public static void interpret(Script script, PrintStream outStream) {
    JSObject globalEnv = JSObject.newEnv(null);
    Block body = script.body();
    globalEnv.register("global", globalEnv);
    globalEnv.register("print", JSObject.newFunction("print", (self, receiver, args) -> {
      System.err.println("print called with " + Arrays.toString(args));
      outStream.println(Arrays.stream(args).map(Object::toString).collect(Collectors.joining(" ")));
      return UNDEFINED;
    }));
    globalEnv.register("+", JSObject.newFunction("+", (self, receiver, args) -> (Integer) args[0] + (Integer) args[1]));
    globalEnv.register("-", JSObject.newFunction("-", (self, receiver, args) -> (Integer) args[0] - (Integer) args[1]));
    globalEnv.register("/", JSObject.newFunction("/", (self, receiver, args) -> (Integer) args[0] / (Integer) args[1]));
    globalEnv.register("*", JSObject.newFunction("*", (self, receiver, args) -> (Integer) args[0] * (Integer) args[1]));
    globalEnv.register("%", JSObject.newFunction("%", (self, receiver, args) -> (Integer) args[0] * (Integer) args[1]));

    globalEnv.register("==", JSObject.newFunction("==", (self, receiver, args) -> args[0].equals(args[1]) ? 1 : 0));
    globalEnv.register("!=", JSObject.newFunction("!=", (self, receiver, args) -> !args[0].equals(args[1]) ? 1 : 0));
    globalEnv.register("<", JSObject.newFunction("<", (self, receiver, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) < 0) ? 1 : 0));
    globalEnv.register("<=", JSObject.newFunction("<=", (self, receiver, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) <= 0) ? 1 : 0));
    globalEnv.register(">", JSObject.newFunction(">", (self, receiver, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) > 0) ? 1 : 0));
    globalEnv.register(">=", JSObject.newFunction(">=", (self, receiver, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) >= 0) ? 1 : 0));
    visit(body, globalEnv);
  }
}

