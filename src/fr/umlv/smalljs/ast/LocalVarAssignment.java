package fr.umlv.smalljs.ast;

import static java.util.Objects.requireNonNull;

public class LocalVarAssignment extends AbstractExpr implements Instr {
  private final String name;
  private final Expr expr;
  private final boolean declaration;

  LocalVarAssignment(String name, Expr expr, boolean declaration, int lineNumber) {
    super(lineNumber);
    this.name = requireNonNull(name);
    this.expr = requireNonNull(expr);
    this.declaration = declaration;
  }
  
  public String getName() {
    return name;
  }
  public Expr getExpr() {
    return expr;
  }
  public boolean isDeclaration() {
    return declaration;
  }
}
