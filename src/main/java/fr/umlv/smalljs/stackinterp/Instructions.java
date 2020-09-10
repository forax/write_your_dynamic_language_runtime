package fr.umlv.smalljs.stackinterp;

public interface Instructions {
	int CONST = 1; // CONST tag_value (smallint or dictionary_index)
	int LOOKUP = 2; // LOOKUP dictionary_index (String global_name)
	int REGISTER = 3; // REGISTER dictionary_index (String global_name)
	int LOAD = 4; // LOAD slot_index
	int STORE = 5; // STORE slot_index
	int DUP = 6;
	int POP = 7;
	int SWAP = 8;
	int FUNCALL = 9; // FUNCALL argument_count
	int RET = 10;
	int GOTO = 11; // GOTO instr_index
	int JUMP_IF_FALSE = 12; // JUMP_IF_FALSE instr_index
	int NEW = 13; // NEW dictionary_index (JSObject object)
	int GET = 14; // GET dictionary_index (String field_name)
	int PUT = 15; // PUT dictionary_index (String field_name)

	int PRINT = 20;

	static void dump(int[] instrs, Dictionary dict) {
		var strings = new String[] { null, "CONST", "LOOKUP", "REGISTER", "LOAD", "STORE", "DUP", "POP", "SWAP",
				"FUNCALL", "RET", "GOTO", "JUMP_IF_FALSE", "NEW", "GET", "PUT", null, null, null, null, "PRINT" };
		for (var pc = 0; pc < instrs.length;) {
			System.err.print(pc + " ");
			var instr = instrs[pc++];
			switch (instr) {
			case DUP: // no-arg instr
			case POP:
			case SWAP:
			case RET:
			case PRINT:
				System.err.println(strings[instr]);
				continue;

			case LOAD: // int arg instr
			case STORE:
			case GOTO:
			case JUMP_IF_FALSE:
			case FUNCALL: {
				var operand = instrs[pc++];
				System.err.println(strings[instr] + " " + operand);
				continue;
			}

			case LOOKUP: // dictionary constant arg instr
			case REGISTER:
			case NEW:
			case GET:
			case PUT: {
				var operand = instrs[pc++];
				System.err.println(strings[instr] + " " + TagValues.decodeDictObject(operand, dict));
				continue;
			}

			case CONST: { // int or dictionary arg instr
				var operand = instrs[pc++];
				if (TagValues.isSmallInt(operand)) {
					System.err.println(strings[instr] + " " + TagValues.decodeSmallInt(operand));
				} else {
					System.err.println(strings[instr] + " " + TagValues.decodeDictObject(operand, dict));
				}
				continue;
			}

			default:
				throw new Error("unknown instr " + instr);
			}
		}
		System.err.println();
	}
}