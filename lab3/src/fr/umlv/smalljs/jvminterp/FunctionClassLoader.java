package fr.umlv.smalljs.jvminterp;

import fr.umlv.smalljs.rt.JSObject;

class FunctionClassLoader extends ClassLoader {
  private final Dictionary dictionary;
  private final JSObject global; 
  
  FunctionClassLoader(Dictionary dictionnary, JSObject global) {
    this.dictionary = dictionnary;
    this.global = global;
  }

  JSObject getGlobal() {
    return global;
  }
  Dictionary getDictionary() {
    return dictionary;
  }
  
  Class<?> createClass(String name, byte[] instrs) {
    return defineClass(name, instrs, 0, instrs.length);
  }
}