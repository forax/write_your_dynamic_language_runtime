package fr.umlv.smalljs.stackinterp;

import static java.util.Objects.requireNonNull;

public record Code(int[] instrs, int parameterCount, int slotCount) {
	public Code {
		if (parameterCount < 1 || slotCount < 1 || parameterCount > slotCount) {
			throw new IllegalArgumentException("invalid parameter or slot count");
		}
		requireNonNull(instrs);
	}
}