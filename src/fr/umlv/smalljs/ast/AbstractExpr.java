package fr.umlv.smalljs.ast;

abstract class AbstractExpr implements Expr {
  private final int lineNumber;

  AbstractExpr(int lineNumber) {
    this.lineNumber = lineNumber;
  }
  
  @Override
  public int getLineNumber() {
    return lineNumber;
  }
}
