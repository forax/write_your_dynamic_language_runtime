package fr.umlv.smalljs.jvminterp;

import static fr.umlv.smalljs.rt.JSObject.UNDEFINED;
import static java.util.stream.Collectors.joining;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import fr.umlv.smalljs.ast.Expr.Block;
import fr.umlv.smalljs.ast.Script;
import fr.umlv.smalljs.rt.JSObject;

public final class JVMInterpreter {
  @SuppressWarnings("unchecked")
  public static void interpret(Script script, PrintStream outStream) {
    JSObject globalEnv = JSObject.newEnv(null);
    Block body = script.body();
    globalEnv.register("global", globalEnv);
    globalEnv.register("print", JSObject.newFunction("print", (receiver, args) -> {
        //System.err.println("print called with " + Arrays.toString(args));
        outStream.println(Arrays.stream(args).map(String::valueOf).collect(joining(" ")));
        return UNDEFINED;
    }));
    globalEnv.register("+", JSObject.newFunction("+", (receiver, args) -> (Integer) args[0] + (Integer) args[1]));
    globalEnv.register("-", JSObject.newFunction("-", (receiver, args) -> (Integer) args[0] - (Integer) args[1]));
    globalEnv.register("/", JSObject.newFunction("/", (receiver, args) -> (Integer) args[0] / (Integer) args[1]));
    globalEnv.register("*", JSObject.newFunction("*", (receiver, args) -> (Integer) args[0] * (Integer) args[1]));
    globalEnv.register("%", JSObject.newFunction("%", (receiver, args) -> (Integer) args[0] % (Integer) args[1]));

    globalEnv.register("==", JSObject.newFunction("==", (receiver, args) -> args[0].equals(args[1])));
    globalEnv.register("!=", JSObject.newFunction("!=", (receiver, args) -> !args[0].equals(args[1])));
    globalEnv.register("<", JSObject.newFunction("<",   (receiver, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) < 0)));
    globalEnv.register("<=", JSObject.newFunction("<=", (receiver, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) <= 0)));
    globalEnv.register(">", JSObject.newFunction(">",   (receiver, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) > 0)));
    globalEnv.register(">=", JSObject.newFunction(">=", (receiver, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) >= 0)));

    JSObject function = ByteCodeRewriter.createFunction("main", List.of(), body, globalEnv);
    function.invoke(UNDEFINED, new Object[0]);
  }
}
