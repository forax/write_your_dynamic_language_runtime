package fr.umlv.smalljs.ast;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class FunCall extends AbstractExpr {
  private final Expr qualified;
  private final List<Expr> args;

  FunCall(Expr qualified, List<Expr> args, int lineNumber) {
    super(lineNumber);
    this.qualified = requireNonNull(qualified);
    this.args = args;
  }
  
  public Expr getQualified() {
    return qualified;
  }
  public List<Expr> getArgs() {
    return args;
  }
}
