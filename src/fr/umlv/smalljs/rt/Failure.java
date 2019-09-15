package fr.umlv.smalljs.rt;

public class Failure extends RuntimeException {
  private static final long serialVersionUID = -962639976506078628L;

  public Failure(String message, Throwable cause) {
    super(message, cause);
  }

  public Failure(String message) {
    super(message);
  }
}
