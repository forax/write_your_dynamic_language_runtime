package fr.umlv.smalljs.stackinterp;

import static fr.umlv.smalljs.rt.JSObject.UNDEFINED;
import static fr.umlv.smalljs.stackinterp.TagValues.decodeAnyValue;
import static fr.umlv.smalljs.stackinterp.TagValues.decodeDictObject;
import static fr.umlv.smalljs.stackinterp.TagValues.encodeAnyValue;
import static fr.umlv.smalljs.stackinterp.TagValues.encodeDictObject;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import fr.umlv.smalljs.ast.Script;
import fr.umlv.smalljs.rt.Failure;
import fr.umlv.smalljs.rt.JSObject;

public class StackInterpreter {
	private static void push(int[] stack, int sp, int value) {
		stack[sp] = value;
	}

	private static int pop(int[] stack, int sp) {
		return stack[sp];
	}

	private static int peek(int[] stack, int sp) {
		return stack[sp - 1];
	}

	private static void store(int[] stack, int bp, int offset, int value) {
		stack[bp + offset] = value;
	}

	private static int load(int[] stack, int bp, int offset) {
		return stack[bp + offset];
	}

	private static void dumpStack(String message, int[] stack, int sp, int bp, Dictionary dict, int[] heap) {
		System.err.println(message);
		for (var i = sp - 1; i >= 0; i = i - 1) {
			var value = stack[i];
			try {
				System.err.println(((i == bp) ? "->" : "  ") + value + " " + decodeAnyValue(value, dict, heap));
			} catch (IndexOutOfBoundsException | ClassCastException e) {
				System.err.println(((i == bp) ? "->" : "  ") + value + " (can't decode)");
			}
		}
		System.err.println();
	}

	private static final int GC_OFFSET = 1;
	private static final int GC_MARK = -1;
	private static final int GC_EMPTY = -2;

	private static final int BP_OFFSET = 0;
	private static final int PC_OFFSET = 1;
	private static final int FUN_OFFSET = 2;
	private static final int ACTIVATION_SIZE = 3;

	private static final int RECEIVER_BASE_ARG_OFFSET = -1;
	private static final int QUALIFIER_BASE_ARG_OFFSET = -2;

	public static Object execute(JSObject function, Dictionary dict, JSObject globalEnv) {
		var stack = new int[96 /* 4096 */];
		var heap = new int[96 /* 4096 */];
		var code = (Code) function.lookup("__code__");
		var instrs = code.getInstrs();

		var undefined = encodeDictObject(UNDEFINED, dict);

		// var hp = 0; // heap pointer
		var pc = 0; // instruction pointer
		var bp = 0; // base pointer
		var sp = bp + code.getSlotVarCount() + ACTIVATION_SIZE; // stack pointer

		// initialize all local variables
		// TODO

		for (;;) {
			switch (instrs[pc++]) {
			case Instructions.CONST: {
				throw new UnsupportedOperationException("TODO");
				//TODO
				//continue;
			}
			case Instructions.LOOKUP: {
				String name = (String) decodeDictObject(instrs[pc++], dict);
				push(stack, sp++, encodeAnyValue(globalEnv.lookup(name), dict));
				continue;
			}
			case Instructions.REGISTER: {
				String name = (String) decodeDictObject(instrs[pc++], dict);
				Object value = decodeAnyValue(pop(stack, --sp), dict, heap);
				globalEnv.register(name, value);
				continue;
			}
			case Instructions.LOAD: {
				// load value from the local slots
				//int value = load(...); FIXME
				// push it to the top of the stack
				//TODO
				continue;
			}
			case Instructions.STORE: {
				// pop value from the stack
				//TODO
				// store it in the local slots
				//TODO
				continue;
			}
			case Instructions.DUP: {
			  // get value on top of the stack
				// TODO
				// push it on top of the stack
				// TODO
				continue;
			}
			case Instructions.POP: {
				// adjust the stack pointer
				// TODO
				continue;
			}
			case Instructions.FUNCALL: {
				// DEBUG
				// dumpStack(">start funcall dump", stack, sp, bp, dict, heap);

				var argumentCount = instrs[pc++];
				var baseArg = sp - argumentCount;
				// stack[baseArg] is the first argument
				// stack[baseArg + RECEIVER_BASE_ARG_OFFSET] is the receiver
				// stack[baseArg + QUALIFIER_BASE_ARG_OFFSET] is the qualifier (aka the function)

				// decode qualifier
				JSObject newFunction = null; // FIXME
				var maybeCode = newFunction.lookup("__code__");
				if (maybeCode == UNDEFINED) { // native call !
					// decode receiver
					Object receiver = null; // FIXME

					// decode arguments
					// TODO

					// System.err.println("call native " + newFunction.getName() + " with " + receiver + " " + java.util.Arrays.toString(args));

					// call native function
					// TODO

					// fixup sp
					sp = baseArg - 2 /* this and qualifier */;

					// push return value
					// TODO
					continue;
				}

				// initialize new code
				code = (Code) maybeCode;

				// check number of arguments
				if (code.getParameterCount() != argumentCount + 1/* this */) {
					throw new Failure("wrong number of arguments for " + newFunction.getName() + " expected "
							+ (code.getParameterCount() - 1) + " but was " + argumentCount);
				}

				// save bp/pc/code in activation zone
				// stack[activation + offset] = ??
				int activation = 0; // FIXME
				// TODO

				// initialize pc, bp and sp
				// TODO

				// initialize all locals that are not parameters
				// stack[bp + ??] = undefined
				// TODO

				// initialize function and instrs of the new function
				function = newFunction;
				instrs = code.getInstrs();

				// DEBUG
				// dumpStack(">end funcall dump", stack, sp, bp, dict, heap);

				continue;

			}
			case Instructions.RET: {
				// DEBUG
				// dumpStack("> start ret dump", stack, sp, bp, dict, heap);

				// get the return value
				var result = pop(stack, --sp);

				System.err.println("ret " + decodeAnyValue(result, dict, heap));

				// find activation and restore pc
				//TODO
				pc = 0; //FIXME
				if (pc == 0) {
					// end of the interpreter
					return decodeAnyValue(result, dict, heap);
				}

				// restore sp, function and bp
				// TODO

				// restore code and instrs
				// TODO

				// push return value
				push(stack, sp++, result);

				// DEBUG
				// dumpStack("> end ret dump", stack, sp, bp, dict, heap);
				continue;

			}
			case Instructions.GOTO: {
				// change the program counter to the label
				//TODO
				continue;
			}
			case Instructions.JUMP_IF_FALSE: {
				// get the label
				// TODO
				// get the value on top of the stack
				// TODO
				// if condition is false change the program counter to the label
				// TODO
				continue;
			}

			case Instructions.NEW: {
				throw new UnsupportedOperationException("TODO");
			}
			case Instructions.GET: {
				throw new UnsupportedOperationException("TODO");
			}
			case Instructions.PUT: {
				throw new UnsupportedOperationException("TODO");
			}

			case Instructions.PRINT: {
			  // pop the value on top of the stack
				//TODO
				// decode the value
				//TODO
				// find "print" in the global environment
				//TODO
				// invoke it
				//TODO
				// push undefined on the stack
				//TODO
				continue;
			}
			default:
				throw new Error("unknown instruction " + instrs[pc - 1]);
			}
		}
	}

	public static JSObject createGlobalEnv(PrintStream outStream) {
		JSObject globalEnv = JSObject.newEnv(null);
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
		globalEnv.register("<", JSObject.newFunction("<",
				(self, receiver, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) < 0) ? 1 : 0));
		globalEnv.register("<=", JSObject.newFunction("<=",
				(self, receiver, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) <= 0) ? 1 : 0));
		globalEnv.register(">", JSObject.newFunction(">",
				(self, receiver, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) > 0) ? 1 : 0));
		globalEnv.register(">=", JSObject.newFunction(">=",
				(self, receiver, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) >= 0) ? 1 : 0));

		return globalEnv;
	}

	public static void interpret(Script script, PrintStream outStream) {
		JSObject globalEnv = createGlobalEnv(outStream);
		var body = script.getBody();
		var function = Rewriter.createFunction(Optional.of("main"), Collections.emptyList(), body, new Dictionary(),
				globalEnv);
		function.invoke(UNDEFINED, new Object[0]);
	}
}
