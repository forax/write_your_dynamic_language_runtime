package fr.umlv.smalljs.ast;

import static java.util.Objects.requireNonNull;

public class FieldAccess extends AbstractExpr {
  private final String name;
  private final Expr receiver;

  FieldAccess(Expr receiver, String name, int lineNumber) {
    super(lineNumber);
    this.receiver = requireNonNull(receiver);
    this.name = requireNonNull(name);
  }
  
  public Expr getReceiver() {
    return receiver;
  }
  public String getName() {
    return name;
  }
}
