package fr.umlv.smalljs.ast;

import static java.util.Objects.requireNonNull;

import java.util.List;

public class MethodCall extends AbstractExpr {
  private final String name;
  private final Expr receiver;
  private final List<Expr> args;

  MethodCall(Expr receiver, String name, List<Expr> args, int lineNumber) {
    super(lineNumber);
    this.receiver = requireNonNull(receiver);
    this.name = requireNonNull(name);
    this.args = args;
  }
  
  public Expr getReceiver() {
    return receiver;
  }
  public String getName() {
    return name;
  }
  public List<Expr> getArgs() {
    return args;
  }
}
