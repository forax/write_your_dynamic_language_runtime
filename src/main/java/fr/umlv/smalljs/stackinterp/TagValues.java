package fr.umlv.smalljs.stackinterp;

import fr.umlv.smalljs.rt.JSObject;

public interface TagValues {
	// every value are stored as 32 bits integer, boolean, small ints, constant (dictionary object) and reference
	// the suffix indicates the kind of value
	//    1 -> small integers (SmallInt) or boolean (TRUE/FALSE)
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
    if (isSmallInt(tagValue)) {
      return decodeSmallInt(tagValue);
    }
    if (isReference(tagValue)) {
      var ref = decodeReference(tagValue);
      var clazz = (JSObject) decodeDictObject(heap[ref], dict);
      return clazz.mirror(offset -> decodeAnyValue(heap[ref + OBJECT_HEADER_SIZE + (int)offset], dict, heap));
    }
    return decodeDictObject(tagValue, dict);
  }
  static int encodeAnyValue(Object object, Dictionary dict) {
  	if (object instanceof Integer smallInt && smallInt >= 0) {
      return encodeSmallInt(smallInt);
    }
    return encodeDictObject(object, dict);
  }

  int TRUE = encodeSmallInt(1);
  int FALSE = encodeSmallInt(0);
  
  int OBJECT_HEADER_SIZE = 2;  // CLASS_DESCRIPTOR + GC_POINTER
}
