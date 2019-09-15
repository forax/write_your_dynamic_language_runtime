package fr.umlv.smalljs.ast;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

public class If extends AbstractExpr implements Instr{
  private final Block trueBlock;
  private final Block falseBlock;
  private final Expr condition;

  If(Expr condition, Block trueBlock, Block falseBlock) {
    super(condition.getLineNumber());
    this.condition = Objects.requireNonNull(condition);
    this.trueBlock = requireNonNull(trueBlock);
    this.falseBlock =  requireNonNull(falseBlock);
  }

  public Expr getCondition() {
    return condition;
  }
  public Block getTrueBlock() {
    return trueBlock;
  }
  public Block getFalseBlock() {
    return falseBlock;
  }
}
