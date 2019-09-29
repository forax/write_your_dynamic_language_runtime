package fr.umlv.smalljs.stackinterp;

import java.util.ArrayList;
import java.util.HashMap;

public class Dictionary {
	private final HashMap<Object, Integer> indexMap = new HashMap<>();
	private final ArrayList<Object> constants = new ArrayList<>();

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
