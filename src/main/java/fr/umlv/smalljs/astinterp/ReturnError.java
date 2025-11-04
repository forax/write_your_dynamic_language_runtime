package fr.umlv.smalljs.astinterp;

import static java.util.Objects.requireNonNull;

@SuppressWarnings("serial")
final class ReturnError extends Error {
  private final Object value;

  ReturnError(Object value) {
    this.value = requireNonNull(value);
    super(null, null, false, false);
  }
  
  public Object getValue() {
    return value;
  }
}
