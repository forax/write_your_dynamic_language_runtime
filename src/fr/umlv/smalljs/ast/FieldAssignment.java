package fr.umlv.smalljs.ast;

import static java.util.Objects.requireNonNull;

public class FieldAssignment extends AbstractExpr implements Instr {
  private final Expr receiver;
  private final String name;
  private final Expr expr;

  public FieldAssignment(Expr receiver, String name, Expr expr, int lineNumber) {
    super(lineNumber);
    this.receiver = requireNonNull(receiver);
    this.name = requireNonNull(name);
    this.expr = requireNonNull(expr);
  }
  
  public Expr getReceiver() {
    return receiver;
  }
  public String getName() {
    return name;
  }
  public Expr getExpr() {
    return expr;
  }
}
