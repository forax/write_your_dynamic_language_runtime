package fr.umlv.smalljs.ast;

import static java.util.Objects.requireNonNull;

public class Literal<T> extends AbstractExpr {
  private final T value;

  Literal(T value, int lineNumber) {
    super(lineNumber);
    this.value = requireNonNull(value);
  }
  
  public T getValue() {
    return value;
  }
  
  @Override
  public String toString() {
    return value.toString();
  }
}