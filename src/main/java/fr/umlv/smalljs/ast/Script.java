package fr.umlv.smalljs.ast;

import fr.umlv.smalljs.ast.Expr.Block;

import static java.util.Objects.requireNonNull;

public record Script(Block body) {
  public Script {
    requireNonNull(body);
  }
}
