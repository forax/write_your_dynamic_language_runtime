package fr.umlv.smalljs.ast;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiFunction;

public class Visitor<E, R> {
  private final HashMap<Class<?>, BiFunction<Expr, ? super E, ? extends R>> functionMap = new HashMap<>();
  
  @SuppressWarnings("unchecked")
  public <T/* extends Expr*/> Visitor<E,R> when(Class<T> type, BiFunction<? super T, ? super E, ? extends R> function) {
    functionMap.put(type, (BiFunction<Expr, ? super E, ? extends R>)function);
    return this;
  }
  
  private static <E, R> BiFunction<Expr, ? super E, ? extends R> unknownType(Class<? extends Expr> type) {
    return (__1, __2) -> { throw new IllegalArgumentException("no method registered for " + type.getName()); };
  }
  
  public R visit(Expr expr, E env) {
	  Class<? extends Expr> type = expr.getClass();  // implicit expr nullcheck
    Objects.requireNonNull(env);
    return functionMap
        .getOrDefault(type, unknownType(type))
        .apply(expr, env);
  }
}
