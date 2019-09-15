package fr.umlv.smalljs.ast;

import java.util.List;

public class Block extends AbstractExpr implements Instr {
  private final List<Expr> instrs;

  Block(List<Expr> instrs, int lineNumberIfEmpty) {
    super(instrs.stream().mapToInt(Expr::getLineNumber).findFirst().orElse(lineNumberIfEmpty));
    this.instrs = instrs;
  }

  public List<Expr> getInstrs() {
    return instrs;
  }
}
