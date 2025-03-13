package fr.umlv.smalljs.ast;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public sealed interface Expr {
  int lineNumber();

  sealed interface Statement {
    // maker interface for expressions that return void
  }

  record Block(List<Expr> exprs, int lineNumber) implements Expr, Statement {
    public Block {
      requireNonNull(exprs);
      lineNumber = exprs.stream().mapToInt(Expr::lineNumber).findFirst().orElse(lineNumber);
    }
  }

  record FieldAccess(Expr receiver, String name, int lineNumber) implements Expr {
    public FieldAccess {
      requireNonNull(receiver);
      requireNonNull(name);
    }
  }

  record FieldAssignment(Expr receiver, String name, Expr expr, int lineNumber) implements Expr, Statement {
    public FieldAssignment {
      requireNonNull(receiver);
      requireNonNull(name);
      requireNonNull(expr);
    }
  }

  record Fun(Optional<String> optName, List<String> parameters, Block body, int lineNumber) implements Expr {
    public Fun {
      requireNonNull(optName);
      requireNonNull(parameters);
      requireNonNull(body);
    }
  }

  record FunCall(Expr qualifier, List<Expr> args, int lineNumber) implements Expr {
    public FunCall {
      requireNonNull(qualifier);
      requireNonNull(args);
    }
  }

  record If(Expr condition, Block trueBlock, Block falseBlock, int lineNumber) implements Expr, Statement {
    public If {
      requireNonNull(condition);
      requireNonNull(trueBlock);
      requireNonNull(falseBlock);
    }
  }

  record Literal(Object value, int lineNumber) implements Expr {
    public Literal {
      requireNonNull(value);
    }
    @Override
    public String toString() {
      return value.toString();
    }
  }

  record LocalVarAccess(String name, int lineNumber) implements Expr {
    public LocalVarAccess {
      requireNonNull(name);
    }
  }

  record LocalVarAssignment(String name, Expr expr, boolean declaration, int lineNumber) implements Expr, Statement {
    public LocalVarAssignment {
      requireNonNull(name);
      requireNonNull(expr);
    }
  }

  record MethodCall(Expr receiver, String name, List<Expr> args, int lineNumber) implements Expr {
    public MethodCall {
      requireNonNull(receiver);
      requireNonNull(name);
      requireNonNull(args);
    }
  }

  record ObjectLiteral(Map<String, Expr> initMap, int lineNumber) implements Expr {
    public ObjectLiteral {
      // don't use Map.copyOf here because the order is not guaranteed
      requireNonNull(initMap);
    }
  }

  record Return(Expr expr, int lineNumber) implements Expr, Statement {
    public Return {
      requireNonNull(expr);
    }
  }
}
