package fr.umlv.smalljs.stackinterp;

import fr.umlv.smalljs.rt.JSObject;

public interface TagValues {
	// every values are stored as 32 bits integer, boolean, small ints, constant (dictionary object) and reference
	// the suffix indicates the kind of value
	//    1 -> small ints or boolean (TRUE/FALSE)
	//   10 -> dictionary index
	//   00 -> reference 
	
  static boolean isSmallInt(int value) {
    return (value & 0b1) == 0b1;
  }

  static int encodeSmallInt(int value) {
    return value << 1 | 0b1;
  }
  static int decodeSmallInt(int value) {
    return value >>> 1;
  }

  static int encodeDictObject(Object object, Dictionary dict) {
    return dict.index(object) << 2 | 0b10;
  }
  static Object decodeDictObject(int value, Dictionary dict) {
    return dict.getConst(value >>> 2);
  }
  
  static boolean isReference(int value) {
    return (value & 0b11) == 0b00;
  }
  
  static int encodeReference(int reference) {
    return reference << 2 | 0b00;
  }
  static int decodeReference(int value) {
    return value >>> 2;
  }

  static Object decodeAnyValue(int tagValue, Dictionary dict, int[] heap) {
    if (TagValues.isSmallInt(tagValue)) {
      return TagValues.decodeSmallInt(tagValue);
    }
    if (TagValues.isReference(tagValue)) {
      var ref = TagValues.decodeReference(tagValue);
      var clazz = (JSObject) decodeDictObject(heap[ref], dict);
      return clazz.mirror(offset -> decodeAnyValue(heap[ref - OBJECT_HEADER_SIZE - (int)offset], dict, heap));
    }
    return TagValues.decodeDictObject(tagValue, dict);
  }
  static int encodeAnyValue(Object object, Dictionary dict) {
  	int smallInt;
    if (object instanceof Integer && (smallInt = (Integer)object) >= 0) {
      return TagValues.encodeSmallInt(smallInt);
    }
    return TagValues.encodeDictObject(object, dict);
  }

  int TRUE = TagValues.encodeSmallInt(1);
  int FALSE = TagValues.encodeSmallInt(0);
  
  int OBJECT_HEADER_SIZE = 2;  // CLASS_DESCRIPTOR + GC_POINTER
}
