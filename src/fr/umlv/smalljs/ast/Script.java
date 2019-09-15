package fr.umlv.smalljs.ast;

import java.util.Objects;

public class Script {
  private final Block body;

  Script(Block body) {
    this.body = Objects.requireNonNull(body);
  }

  public Block getBody() {
    return body;
  }
}
