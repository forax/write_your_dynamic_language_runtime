package fr.umlv.smalljs.ast;

import static java.util.Objects.requireNonNull;

public class LocalVarAccess extends AbstractExpr {
  private final String name;

  LocalVarAccess(String name, int lineNumber) {
    super(lineNumber);
    this.name = requireNonNull(name);
  }
  
  public String getName() {
    return name;
  }
}
