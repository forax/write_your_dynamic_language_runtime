package fr.umlv.smalljs.astinterp;

import static java.util.Objects.requireNonNull;

@SuppressWarnings("serial")
class ReturnError extends Error {
  private final Object value;

  ReturnError(Object value) {
    super(null, null, false, false);
    this.value = requireNonNull(value);
  }
  
  public Object getValue() {
    return value;
  }
}
