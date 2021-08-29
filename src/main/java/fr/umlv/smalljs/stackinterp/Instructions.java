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
				// no-arg instr
				case DUP, POP, SWAP, RET, PRINT -> {
					System.err.println(strings[instr]);
				}
				// int arg instr
				case LOAD, STORE, GOTO, JUMP_IF_FALSE, FUNCALL -> {
					var operand = instrs[pc++];
					System.err.println(strings[instr] + " " + operand);
				}
				// dictionary constant arg instr
				case LOOKUP, REGISTER, NEW, GET, PUT -> {
					var operand = instrs[pc++];
					System.err.println(strings[instr] + " " + TagValues.decodeDictObject(operand, dict));
				}
				// int or dictionary arg instr
				case CONST -> {
					var operand = instrs[pc++];
					if (TagValues.isSmallInt(operand)) {
						System.err.println(strings[instr] + " " + TagValues.decodeSmallInt(operand));
					} else {
						System.err.println(strings[instr] + " " + TagValues.decodeDictObject(operand, dict));
					}
				}
				default -> throw new AssertionError("unknown instr " + instr);
			}
		}
		System.err.println();
	}
}