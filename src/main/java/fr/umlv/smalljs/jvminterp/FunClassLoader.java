package fr.umlv.smalljs.jvminterp;

import fr.umlv.smalljs.rt.JSObject;

final class FunClassLoader extends ClassLoader {
  private final FunDictionary dictionary;
  private final JSObject global; 
  
  FunClassLoader(FunDictionary dictionary, JSObject global) {
    this.dictionary = dictionary;
    this.global = global;
    super();
  }

  public JSObject getGlobal() {
    return global;
  }

  public FunDictionary getDictionary() {
    return dictionary;
  }
  
  public Class<?> createClass(String name, byte[] instrs) {
    return defineClass(name, instrs, 0, instrs.length);
  }
}