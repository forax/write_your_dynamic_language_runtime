package fr.umlv.smalljs.stackinterp;

import static fr.umlv.smalljs.rt.JSObject.UNDEFINED;
import static fr.umlv.smalljs.stackinterp.Instructions.CONST;
import static fr.umlv.smalljs.stackinterp.Instructions.DUP;
import static fr.umlv.smalljs.stackinterp.Instructions.FUNCALL;
import static fr.umlv.smalljs.stackinterp.Instructions.GET;
import static fr.umlv.smalljs.stackinterp.Instructions.GOTO;
import static fr.umlv.smalljs.stackinterp.Instructions.JUMP_IF_FALSE;
import static fr.umlv.smalljs.stackinterp.Instructions.LOAD;
import static fr.umlv.smalljs.stackinterp.Instructions.LOOKUP;
import static fr.umlv.smalljs.stackinterp.Instructions.NEW;
import static fr.umlv.smalljs.stackinterp.Instructions.POP;
import static fr.umlv.smalljs.stackinterp.Instructions.PUT;
import static fr.umlv.smalljs.stackinterp.Instructions.REGISTER;
import static fr.umlv.smalljs.stackinterp.Instructions.RET;
import static fr.umlv.smalljs.stackinterp.Instructions.STORE;
import static fr.umlv.smalljs.stackinterp.Instructions.SWAP;
import static fr.umlv.smalljs.stackinterp.TagValues.encodeDictObject;
import static fr.umlv.smalljs.stackinterp.TagValues.encodeSmallInt;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.umlv.smalljs.ast.Expr;
import fr.umlv.smalljs.ast.Expr.Block;
import fr.umlv.smalljs.ast.Expr.Call;
import fr.umlv.smalljs.ast.Expr.FieldAccess;
import fr.umlv.smalljs.ast.Expr.FieldAssignment;
import fr.umlv.smalljs.ast.Expr.Fun;
import fr.umlv.smalljs.ast.Expr.Identifier;
import fr.umlv.smalljs.ast.Expr.If;
import fr.umlv.smalljs.ast.Expr.Statement;
import fr.umlv.smalljs.ast.Expr.Literal;
import fr.umlv.smalljs.ast.Expr.MethodCall;
import fr.umlv.smalljs.ast.Expr.ObjectLiteral;
import fr.umlv.smalljs.ast.Expr.Return;
import fr.umlv.smalljs.ast.Expr.VarAssignment;
import fr.umlv.smalljs.rt.Failure;
import fr.umlv.smalljs.rt.JSObject;

final class InstrRewriter {
	static final class InstrBuffer {
		private int[] instrs;
		private int size;

		InstrBuffer() {
			instrs = new int[32];
			super();
		}

		InstrBuffer emit(int value) {
			if (size == instrs.length) {
				instrs = Arrays.copyOf(instrs, size << 1);
			}
			instrs[size++] = value;
			return this;
		}

		int label() {
			return size;
		}

		int placeholder() {
			return size++;
		}

		void patch(int position, int label) {
			instrs[position] = label;
		}

		int[] toInstrs() {
			return Arrays.copyOf(instrs, size);
		}
	}

	static JSObject createFunction(String name, List<String> parameters, Block body, Dictionary dict) {
		var env = JSObject.newEnv(null);

		env.register("this", 0);
		for (var parameter : parameters) {
			env.register(parameter, env.length());
		}
		visitVariable(body, env);

		var buffer = new InstrBuffer();
		visit(body, env, buffer, dict);
		buffer.emit(CONST).emit(encodeDictObject(UNDEFINED, dict));
		buffer.emit(RET);

		var instrs = buffer.toInstrs();
		Instructions.dump(instrs, dict);

		var code = new Code(instrs, parameters.size() + 1 /* this */, env.length());
		var function = JSObject.newFunction(name, JSObject.NO_INVOKER_MH);
		function.register("__code__", code);
		return function;
	}

	private static void visitVariable(Expr expression, JSObject env) {
		switch (expression) {
			case Block(List<Expr> exprs, _) -> {
				for (var expr : exprs) {
					visitVariable(expr, env);
				}
			}
			case VarAssignment(String name, _, boolean declaration, _) -> {
				if (declaration) {
					env.register(name, env.length());
				}
			}
			case If(_, Block trueBlock, Block falseBlock, _) -> {
				visitVariable(trueBlock, env);
				visitVariable(falseBlock, env);
			}
			case Literal _, Call _, Identifier _, Fun _, Return _, ObjectLiteral _, FieldAccess _,
					 FieldAssignment _, MethodCall _ -> {
				// do nothing
			}
		};
	}

	private static void visit(Expr expression, JSObject env, InstrBuffer buffer, Dictionary dict) {
		switch (expression) {
			case Block(List<Expr> exprs, int lineNumber) -> {
				throw new UnsupportedOperationException("TODO Block");
				// for each expression of the block
					// visit the expression
					// if the expression is not a statement (the value still on stack)
					//if (!(instr instanceof Statement)) {
						  // ask to remove the top of the stack
						  // buffer.emit(POP);
					//}
				//}
			}
			case Literal(Object literalValue, int lineNumber) -> {
				throw new UnsupportedOperationException("TODO Literal");
				// test if the literal value is a positive integers
				//if (literalValue instanceof Integer value && value >= 0) {
				// emit a small int
				//buffer.emit(...).emit(...);
				//} else {
				// emit a dictionary object
				//buffer.emit(...).emit(...);
				//}
			}
			case Call(Expr qualifier, List<Expr> args, int lineNumber) -> {
				throw new UnsupportedOperationException("TODO Call");
				// visit the qualifier
				//visit(...);
				// emit undefined
				//buffer.emit(...).emit(...)
				// visit all arguments
				//for (var arg : args) {
				//	visit(...);
				//}
				// emit the funcall
				//buffer.emit(...).emit(...);
			}
			case Identifier(String name, int lineNumber) -> {
				throw new UnsupportedOperationException("TODO Identifier");
				// get the local variable name
				//var name = ...
				// find if there is a local variable in the environment with the name
				//var slot = env.lookupOrDefault(...);
				//if (slot == null) {
				// emit a lookup with the name
				//  buffer.emit(...).emit(...);
				//} else {
				// load the local variable with the slot
				//  buffer.emit(...).emit(...);
				//}
			}
			case VarAssignment(String name, Expr expr, boolean declaration, int lineNumber) -> {
				throw new UnsupportedOperationException("TODO VarAssignment");
				// visit the expression
				// visit(...);
				// find if there is a local variable in the env from the name
				//var slot = env.lookupOrDefault(...);
				//if (slot == null) {
				//	throw new Failure("unknown variable " + name);
				//}
				// emit a store at the variable slot
				//buffer.emit(...).emit(...);
			}
			case Fun(String name, List<String> parameters, boolean topLevel, Block body, int lineNumber) -> {
				throw new UnsupportedOperationException("TODO Fun");
				// create a JSObject function
				// var function = createFunction(name, parameters, body, dict);
				// emit a const on the function
				//buffer.emit(...).emit(...);
				// if it's a toplevel register the function in the global environment
				//if (topLevel) {
				//  buffer.emit(DUP);
				//  buffer.emit(...).emit(...);
				//}
			}
			case Return(Expr expr, int lineNumber) -> {
				throw new UnsupportedOperationException("TODO Return");
				// emit a visit of the expression
				//visit(...);
				// emit a RET
			}
			case If(Expr condition, Block trueBlock, Block falseBlock, int lineNumber) -> {
				throw new UnsupportedOperationException("TODO If");
				// visit the condition
				//visit(...);
				// emit a JUMP_IF_FALSE and a placeholder
				//var falsePlaceHolder = buffer.emit(JUMP_IF_FALSE).placeholder();
				// visit the true block
				//visit(...);
				// emit a goto with another placeholder
				//var endPlaceHolder = buffer.emit(GOTO).placeholder();
				// patch the first placeholder
				//buffer.patch(..., buffer.label());
				// visit the false block
				//visit(...);
				// patch the second placeholder
				//buffer.patch(..., buffer.label());
			}
			case ObjectLiteral(Map<String, Expr> initMap, int lineNumber) -> {
				throw new UnsupportedOperationException("TODO ObjectLiteral");
				// create a JSObject class
				//var clazz = JSObject.newObject(null);
				// loop over all the field initializations
				//initMap.forEach((fieldName, expr) -> {
				  // register the field name with the right slot
				  //clazz.register(...);
				  // visit the initialization expression
				  //visit(...);
				//});
				// emit a NEW with the class
				//buffer.emit(...).emit(...);
			}
			case FieldAccess(Expr receiver, String name, int lineNumber) -> {
				throw new UnsupportedOperationException("TODO FieldAccess");
				// visit the receiver
				//visit(...);
				// emit a GET with the field name
				//buffer.emit(...).emit(...);
			}
			case FieldAssignment(Expr receiver, String name, Expr expr, int lineNumber) -> {
				throw new UnsupportedOperationException("TODO FieldAssignment");
				// visit the receiver
				//visit(...);
				// visit the expression
				//visit(...);
				// emit a PUT with the field name
				//buffer.emit(...).emit(...);
			}
			case MethodCall(Expr receiver, String name, List<Expr> args, int lineNumber) -> {
				throw new UnsupportedOperationException("TODO MethodCall");
				// visit the receiver
				//visit(...);
				// emit a DUP, get the field name and emit a SWAP of the qualifier and the receiver
				//buffer.emit(DUP);
				//buffer.emit(...).emit(...);
				//buffer.emit(SWAP);
				// visit all arguments
				//for (var arg : args) {
				  //visit(...);
				//}
				// emit the funcall
				//buffer.emit(...).emit(...);
			}
		}
	}
}
