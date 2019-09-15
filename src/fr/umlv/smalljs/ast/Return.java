package fr.umlv.smalljs.ast;

import static java.util.Objects.requireNonNull;

public class Return extends AbstractExpr implements Instr {
  private final Expr expr;

  Return(Expr expr) {
    super(expr.getLineNumber());
    this.expr = requireNonNull(expr);
  }
  
  public Expr getExpr() {
    return expr;
  }
}
