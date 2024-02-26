package fr.umlv.smalljs.rt;

import java.io.Serial;

public final class Failure extends RuntimeException {
  @Serial
  private static final long serialVersionUID = -962639976506078628L;

  public Failure(String message, Throwable cause) {
    super(message, cause);
  }

  public Failure(String message) {
    super(message);
  }
}
