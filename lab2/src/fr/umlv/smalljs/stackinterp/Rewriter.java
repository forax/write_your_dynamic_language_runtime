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
import static fr.umlv.smalljs.stackinterp.Instructions.STORE;
import static fr.umlv.smalljs.stackinterp.TagValues.encodeDictObject;
import static fr.umlv.smalljs.stackinterp.TagValues.encodeSmallInt;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import fr.umlv.smalljs.ast.Block;
import fr.umlv.smalljs.ast.Expr;
import fr.umlv.smalljs.ast.Fun;
import fr.umlv.smalljs.ast.FunCall;
import fr.umlv.smalljs.ast.If;
import fr.umlv.smalljs.ast.Instr;
import fr.umlv.smalljs.ast.Literal;
import fr.umlv.smalljs.ast.LocalVarAccess;
import fr.umlv.smalljs.ast.LocalVarAssignment;
import fr.umlv.smalljs.ast.Return;
import fr.umlv.smalljs.ast.VoidVisitor;
import fr.umlv.smalljs.rt.Failure;
import fr.umlv.smalljs.rt.JSObject;

public class Rewriter {
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

	private Rewriter(Dictionary dict, InstrBuffer buffer, JSObject globalEnv) {
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
		var rewriter = new Rewriter(dict, buffer, globalEnv);
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
				for (var instr : block.getInstrs()) {
					visitVariable(instr, env);
				}
			}).when(Literal.class, (literal, env) -> {
				// do nothing
			}).when(FunCall.class, (funCall, env) -> {
				// do nothing
			}).when(LocalVarAssignment.class, (localVarAssignment, env) -> {
				if (localVarAssignment.isDeclaration()) {
					env.register(localVarAssignment.getName(), env.length());
				}
			}).when(LocalVarAccess.class, (localVarAccess, env) -> {
				// do nothing
			}).when(Fun.class, (fun, env) -> {
				// do nothing
			}).when(Return.class, (_return, env) -> {
				// do nothing
			}).when(If.class, (_if, env) -> {
				visitVariable(_if.getTrueBlock(), env);
				visitVariable(_if.getFalseBlock(), env);
			});

	private void rewrite(Expr expr, JSObject env) {
		visitor.visit(expr, env);
	}

	public static VoidVisitor<JSObject> createVisitor(InstrBuffer buffer, Dictionary dict, JSObject globalEnv) {
		var visitor = new VoidVisitor<JSObject>();
		visitor.when(Block.class, (block, env) -> {
			for (var expr : block.getInstrs()) {
				visitor.visit(expr, env);
				if (!(expr instanceof Instr)) {
					buffer.emit(POP);
				}
			}
		}).when(Literal.class, (literal, env) -> {
			var value = literal.getValue();
			// test if it's a positive integers
			if (value instanceof Integer && ((Integer) value) >= 0) {
				// emit a small int
				//TODO
			} else {
				// emit a dictionary object
				//TODO
			}
		}).when(FunCall.class, (funCall, env) -> {
		  // emit the qualifier
			visitor.visit(funCall.getQualified(), env);
			// emit undefined
			buffer.emit(CONST).emit(encodeDictObject(UNDEFINED, dict));
			// visit all arguments
			for (var arg : funCall.getArgs()) {
				visitor.visit(arg, env);
			}
			// emit the funcall
			buffer.emit(FUNCALL).emit(funCall.getArgs().size());
		}).when(LocalVarAccess.class, (localVarAccess, env) -> {
			// find if there is a local variable in the environment with the name
			var slotOrUndefined = env.lookup(localVarAccess.getName());
			if (slotOrUndefined == UNDEFINED) {
				// emit a lookup with the name
				buffer.emit(LOOKUP).emit(encodeDictObject(localVarAccess.getName(), dict));
			} else {
				// load the local variable with the slot
				buffer.emit(LOAD).emit((Integer) slotOrUndefined);
			}
		}).when(LocalVarAssignment.class, (localVarAssignment, env) -> {
			// visit the expression
			// TODO
			// find if there is a local variable in the env from the name
			var slotOrUndefined = UNDEFINED;  //FIXME 
			if (slotOrUndefined == UNDEFINED) {
				throw new Failure("unknown local variable " + localVarAssignment.getName());
			}
			// emit a store at the variable slot
			// TODO
		}).when(Fun.class, (fun, env) -> {
			// create a JSObject function
			// TODO
			// emit a const on the function
			// TODO
			// if the name is present emit a code to register the function in the global environment
			// TODO
		}).when(Return.class, (_return, env) -> {
			// emit a visit and a RET
			// TODO
		}).when(If.class, (_if, env) -> {
			// visit the condition
			// TODO
			// emit a JUMP_IF_FALSE and a placeholder
		  // TODO
			// visit the true block
		  // TODO
			// emit a goto with another placeholder
		  // TODO
			// patch the first placeholder
		  // TODO
			// visit the false block
		  // TODO
			// patch the second place holder
		  // TODO
		});
		return visitor;
	}

	private final VoidVisitor<JSObject> visitor;
}
