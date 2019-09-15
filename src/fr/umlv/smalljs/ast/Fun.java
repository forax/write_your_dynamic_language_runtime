package fr.umlv.smalljs.ast;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

public class Fun extends AbstractExpr {
  private final Optional<String> name;
  private final List<String> parameters;
  private final Block body;

  Fun(Optional<String> name, List<String> parameters, Block body, int lineNumber) {
    super(lineNumber);
    this.name = requireNonNull(name);
    this.parameters = parameters;
    this.body = requireNonNull(body);
  }
  
  public boolean isAnonymous() {
    return !name.isPresent();
  }
  public Optional<String> getName() {
    return name;
  }
  public List<String> getParameters() {
    return parameters;
  }
  public Block getBody() {
    return body;
  }
}
