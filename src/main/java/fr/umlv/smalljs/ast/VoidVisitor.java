package fr.umlv.smalljs.ast;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiConsumer;

public class VoidVisitor<E> {
  private final HashMap<Class<?>, BiConsumer<Expr, ? super E>> consumerMap = new HashMap<>();
  
  @SuppressWarnings("unchecked")
  public <T /*extends Expr*/> VoidVisitor<E> when(Class<T> type, BiConsumer<? super T, ? super E> consumer) {
    consumerMap.put(type, (BiConsumer<Expr, ? super E>)consumer);
    return this;
  }
  
  private static <E> BiConsumer<Expr, ? super E> unknownType(Class<? extends Expr> type) {
    return (__1, __2) -> { throw new IllegalArgumentException("no method registered for " + type.getName()); };
  }
  
  public void visit(Expr expr, E env) {
	  Class<? extends Expr> type = expr.getClass();  // implicit expr nullcheck
    Objects.requireNonNull(env);
    consumerMap
        .getOrDefault(type, unknownType(type))
        .accept(expr, env);
  }
}
