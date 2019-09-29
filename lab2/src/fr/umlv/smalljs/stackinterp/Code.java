package fr.umlv.smalljs.stackinterp;

import java.util.Objects;

public class Code {
	private final int[] instrs;
	private final int parameterCount;
	private final int slotCount;

	public Code(int[] instrs, int parameterCount, int slotCount) {
		if (parameterCount < 1 || slotCount < 1 || parameterCount > slotCount) {
			throw new IllegalArgumentException("invalid parameter or slot count");
		}
		this.instrs = Objects.requireNonNull(instrs);
		this.parameterCount = parameterCount;
		this.slotCount = slotCount;
	}

	public int[] getInstrs() {
		return instrs;
	}

	public int getParameterCount() {
		return parameterCount;
	}

	public int getSlotVarCount() {
		return slotCount;
	}

	@Override
	public String toString() {
		return "instrs:" + instrs.length + " params:" + parameterCount + " slots:" + slotCount;
	}
}