package fr.umlv.smalljs.stackinterp;

import java.util.ArrayList;
import java.util.HashMap;

final class Dictionary {
	private final HashMap<Object, Integer> indexMap;
	private final ArrayList<Object> constants;

	Dictionary() {
		indexMap = new HashMap<>();
		constants = new ArrayList<>();
		super();
	}

	public int index(Object constant) {
		return indexMap.computeIfAbsent(constant, key -> {
			var index = constants.size();
			constants.add(key);
			return index;
		});
	}

	public Object getConst(int index) {
		return constants.get(index);
	}
}
