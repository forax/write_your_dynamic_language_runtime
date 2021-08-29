package fr.umlv.smalljs.jvminterp;

import fr.umlv.smalljs.rt.JSObject;

class FunClassLoader extends ClassLoader {
  private final FunDictionary dictionary;
  private final JSObject global; 
  
  FunClassLoader(FunDictionary dictionary, JSObject global) {
    this.dictionary = dictionary;
    this.global = global;
  }

  JSObject getGlobal() {
    return global;
  }
  FunDictionary getDictionary() {
    return dictionary;
  }
  
  Class<?> createClass(String name, byte[] instrs) {
    return defineClass(name, instrs, 0, instrs.length);
  }
}