package fr.umlv.smalljs.jvminterp;

import fr.umlv.smalljs.ast.Expr.Fun;
import java.util.ArrayList;

final class FunDictionary {
  private final ArrayList<Fun> dictionary;

  FunDictionary() {
    dictionary = new ArrayList<>();
    super();
  }

  public int register(Fun fun) {
    var id = dictionary.size();
    dictionary.add(fun);
    return id;
  }
  
  public Fun lookupAndClear(int id) {
    var fun = dictionary.get(id);
    dictionary.set(id, null);     // Fun will be garbage collected
    return fun;
  }
}