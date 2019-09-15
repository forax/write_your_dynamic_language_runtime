package fr.umlv.smalljs.ast;

import java.util.Map;

public class New extends AbstractExpr {
  private final Map<String, Expr> initMap;

  New(Map<String, Expr> initMap, int lineNumber) {
    super(lineNumber);
    this.initMap = initMap;
  }

  public Map<String, Expr> getInitMap() {
    return initMap;
  }
}
