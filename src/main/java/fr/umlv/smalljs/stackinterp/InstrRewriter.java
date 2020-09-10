package fr.umlv.smalljs.stackinterp;

import static fr.umlv.smalljs.rt.JSObject.UNDEFINED;
import static fr.umlv.smalljs.stackinterp.Instructions.CONST;
import static fr.umlv.smalljs.stackinterp.Instructions.DUP;
import static fr.umlv.smalljs.stackinterp.Instructions.FUNCALL;
import static fr.umlv.smalljs.stackinterp.Instructions.GOTO;
import static fr.umlv.smalljs.stackinterp.Instructions.JUMP_IF_FALSE;
import static fr.umlv.smalljs.stackinterp.Instructions.LOAD;
import static fr.umlv.smalljs.stackinterp.Instructions.LOOKUP;
import static fr.umlv.smalljs.stackinterp.Instructions.POP;
import static fr.umlv.smalljs.stackinterp.Instructions.REGISTER;
import static fr.umlv.smalljs.stackinterp.Instructions.RET;
import static fr.umlv.smalljs.stackinterp.Instructions.*;
import static fr.umlv.smalljs.stackinterp.TagValues.encodeDictObject;
import static fr.umlv.smalljs.stackinterp.TagValues.encodeSmallInt;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import fr.umlv.smalljs.ast.Expr;
import fr.umlv.smalljs.ast.Expr.Block;
import fr.umlv.smalljs.ast.Expr.FieldAccess;
import fr.umlv.smalljs.ast.Expr.FieldAssignment;
import fr.umlv.smalljs.ast.Expr.Fun;
import fr.umlv.smalljs.ast.Expr.FunCall;
import fr.umlv.smalljs.ast.Expr.If;
import fr.umlv.smalljs.ast.Expr.Instr;
import fr.umlv.smalljs.ast.Expr.Literal;
import fr.umlv.smalljs.ast.Expr.LocalVarAccess;
import fr.umlv.smalljs.ast.Expr.LocalVarAssignment;
import fr.umlv.smalljs.ast.Expr.MethodCall;
import fr.umlv.smalljs.ast.Expr.New;
import fr.umlv.smalljs.ast.Expr.Return;
import fr.umlv.smalljs.ast.VoidVisitor;
import fr.umlv.smalljs.rt.Failure;
import fr.umlv.smalljs.rt.JSObject;

public class InstrRewriter {
	static class InstrBuffer {
		private int[] instrs;
		private int size;

		InstrBuffer() {
			instrs = new int[32];
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

	private InstrRewriter(Dictionary dict, InstrBuffer buffer, JSObject globalEnv) {
		this.visitor = createVisitor(buffer, dict, globalEnv);
	}

	public static JSObject createFunction(Optional<String> name, List<String> parameters, Block body, Dictionary dict, JSObject globalEnv) {
		var env = JSObject.newEnv(null);

		env.register("this", 0);
		for (var parameter : parameters) {
			env.register(parameter, env.length());
		}
		visitVariable(body, env);

		var buffer = new InstrBuffer();
		var rewriter = new InstrRewriter(dict, buffer, globalEnv);
		rewriter.rewrite(body, env);
		buffer.emit(CONST).emit(encodeDictObject(UNDEFINED, dict));
		buffer.emit(RET);

		var instrs = buffer.toInstrs();
		Instructions.dump(instrs, dict);

		var code = new Code(instrs, parameters.size() + 1 /* this */, env.length());
		var function = JSObject.newFunction(name.orElse("lambda"), (self, receiver, args) -> {
			if (receiver != UNDEFINED || args.length != 0) {
				throw new Failure("can not interpret a function with a receiver and/or arguments");
			}
			return StackInterpreter.execute(self, dict, globalEnv);
		});
		function.register("__code__", code);
		return function;
	}

	private static void visitVariable(Expr expr, JSObject env) {
		VARIABLE_VISITOR.visit(expr, env);
	}

	private static final VoidVisitor<JSObject> VARIABLE_VISITOR = new VoidVisitor<JSObject>()
			.when(Block.class, (block, env) -> {
				for (var instr : block.instrs()) {
					visitVariable(instr, env);
				}
			}).when(Literal.class, (literal, env) -> {
				// do nothing
			}).when(FunCall.class, (funCall, env) -> {
				// do nothing
			}).when(LocalVarAssignment.class, (localVarAssignment, env) -> {
				if (localVarAssignment.declaration()) {
					env.register(localVarAssignment.name(), env.length());
				}
			}).when(LocalVarAccess.class, (localVarAccess, env) -> {
				// do nothing
			}).when(Fun.class, (fun, env) -> {
				// do nothing
			}).when(Return.class, (_return, env) -> {
				// do nothing
			}).when(If.class, (_if, env) -> {
				visitVariable(_if.trueBlock(), env);
				visitVariable(_if.falseBlock(), env);
			}).when(New.class, (_new, env) -> {
			  // do nothing
			}).when(FieldAccess.class, (fieldAccess, env) -> {
			  // do nothing
			}).when(FieldAssignment.class, (fieldAssignment, env) -> {
			  // do nothing
			}).when(MethodCall.class, (methodCall, env) -> {
			  // do nothing
			});

	private void rewrite(Expr expr, JSObject env) {
		visitor.visit(expr, env);
	}

	public static VoidVisitor<JSObject> createVisitor(InstrBuffer buffer, Dictionary dict, JSObject globalEnv) {
		var visitor = new VoidVisitor<JSObject>();
		visitor.when(Block.class, (block, env) -> {
			// for each expression of the block
			for (var expr : block.instrs()) {
				// visit the expression
				visitor.visit(expr, env);
				// if the expression is an instruction (i.e. return void)
				if (!(expr instanceof Instr)) {
					// ask to top the top of the stack
					buffer.emit(POP);
				}
			}
		}).when(Literal.class, (literal, env) -> {
			throw new UnsupportedOperationException("TODO Literal");
			// get the literal value
			//var value = ...
			// test if it's a positive integers
			//if (value instanceof Integer && ((Integer) value) >= 0) {
				// emit a small int
				//buffer.emit(...).emit(...);
			//} else {
				// emit a dictionary object
				//buffer.emit(...).emit(...);
			//}
		}).when(FunCall.class, (funCall, env) -> {
			throw new UnsupportedOperationException("TODO FunCall");
			// visit the qualifier
			//visitor.visit(...);
			// emit undefined
			//buffer.emit(...).emit(...)
			// visit all arguments
			//for (var arg : funCall.args()) {
			//	visitor.visit(arg, env);
			//}
			// emit the funcall
			//buffer.emit(...).emit(...);
		}).when(LocalVarAccess.class, (localVarAccess, env) -> {
			throw new UnsupportedOperationException("TODO LocalVarAccess");
			// get the local variable name
			//var name = ...
			// find if there is a local variable in the environment with the name
			//var slotOrUndefined = env.lookup(...);
			//if (slotOrUndefined == UNDEFINED) {
				// emit a lookup with the name
				//buffer.emit(...).emit(...);
			//} else {
				// load the local variable with the slot
				//buffer.emit(...).emit(...);
			//}
		}).when(LocalVarAssignment.class, (localVarAssignment, env) -> {
			throw new UnsupportedOperationException("TODO LocalVarAssignment");
			// visit the expression
			// visitor.visit(...);
			// get the local variable name
			//var name = ...
			// find if there is a local variable in the env from the name
			//var slotOrUndefined = env.lookup(...);
			//if (slotOrUndefined == UNDEFINED) {
			//	throw new Failure("unknown local variable " + name);
			//}
			// emit a store at the variable slot
			//buffer.emit(...).emit(...);
		}).when(Fun.class, (fun, env) -> {
			throw new UnsupportedOperationException("TODO Fun");
			// create a JSObject function
			///var function = createFunction(fun.name(), fun.parameters(), fun.body(), dict, globalEnv);
			// emit a const on the function
			//buffer.emit(...).emit(...);
			// if the name is present emit a code to register the function in the global environment
			//fun.name().ifPresent(name -> {
				//buffer.emit(DUP);
				//buffer.emit(...).emit(...);
			//});
		}).when(Return.class, (_return, env) -> {
			throw new UnsupportedOperationException("TODO Return");
			// emit a visit of the expression
			//visitor.visit(...);
			// emit a RET
			//buffer.emit(RET);
		}).when(If.class, (_if, env) -> {
			throw new UnsupportedOperationException("TODO If");
			// visit the condition
			//visitor.visit(...);
			// emit a JUMP_IF_FALSE and a placeholder
			//var falsePlaceHolder = buffer.emit(JUMP_IF_FALSE).placeholder();
			// visit the true block
			//visitor.visit(...);
			// emit a goto with another placeholder
			//var endPlaceHolder = buffer.emit(GOTO).placeholder();
			// patch the first placeholder
			//buffer.patch(..., buffer.label());
			// visit the false block
			//visitor.visit(...);
			// patch the second place holder
			//buffer.patch(..., buffer.label());
		}).when(New.class, (_new, env) -> {
			throw new UnsupportedOperationException("TODO New");
		  // create a JSObject class
			//var clazz = JSObject.newObject(null);
			// loop over all the field initializations
			//_new.initMap().forEach((fieldName, expr) -> {
				// register the field name with the right slot
			  //clazz.register(...);
			  // visit the initialization expression
				//visitor.visit(...);
			//});
			// emit a NEW with the class
			//buffer.emit(...).emit(...);
		}).when(FieldAccess.class, (fieldAccess, env) -> {
			throw new UnsupportedOperationException("TODO FieldAccess");
			// visit the receiver
			//visitor.visit(...);
			// emit a GET with the field name
			//buffer.emit(...).emit(...);
		}).when(FieldAssignment.class, (fieldAssignment, env) -> {
			throw new UnsupportedOperationException("TODO FieldAssignment");
			// visit the receiver
			//visitor.visit(...);
		  // visit the expression
		  //visitor.visit(...);
			// emit a PUT with the field name
			//buffer.emit(...).emit(...);
		}).when(MethodCall.class, (methodCall, env) -> {
			throw new UnsupportedOperationException("TODO MethodCall");
		  // visit the receiver
		  //visitor.visit(...);
		  // emit a DUP, get the field name and emit a SWAP of the qualifier and the receiver
		  //buffer.emit(DUP);
		  //buffer.emit(...).emit(...);
		  //buffer.emit(SWAP);
		  // visit all arguments
		  //for (var arg : methodCall.args()) {
		    //visitor.visit(...);
			//}
		  // emit the funcall
			//buffer.emit(...).emit(...);
		});
		return visitor;
	}

	private final VoidVisitor<JSObject> visitor;
}
