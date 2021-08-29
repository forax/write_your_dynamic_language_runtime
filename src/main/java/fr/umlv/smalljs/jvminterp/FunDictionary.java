package fr.umlv.smalljs.jvminterp;

import java.util.ArrayList;

import fr.umlv.smalljs.ast.Expr.Fun;

class FunDictionary {
  private final ArrayList<Fun> dictionnary = new ArrayList<>();
  
  int register(Fun fun) {
    var id = dictionnary.size();
    dictionnary.add(fun);
    return id;
  }
  
  Fun lookupAndClear(int id) {
    var fun = dictionnary.get(id);
    dictionnary.set(id, null);     // Fun will be garbage collected
    return fun;
  }
}