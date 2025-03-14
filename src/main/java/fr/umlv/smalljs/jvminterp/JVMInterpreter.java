package fr.umlv.smalljs.jvminterp;

import static fr.umlv.smalljs.rt.JSObject.UNDEFINED;
import static java.util.stream.Collectors.joining;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import fr.umlv.smalljs.ast.Script;
import fr.umlv.smalljs.rt.JSObject;

public final class JVMInterpreter {
  @SuppressWarnings("unchecked")
  private static JSObject createGlobalEnv(PrintStream outStream) {
    var globalEnv = JSObject.newEnv(null);
    globalEnv.register("globalThis", globalEnv);
    globalEnv.register("undefined", UNDEFINED);
    globalEnv.register("print", JSObject.newFunction("print", (_, args) -> {
      //System.err.println("print called with " + Arrays.toString(args));
      outStream.println(Arrays.stream(args).map(String::valueOf).collect(joining(" ")));
      return UNDEFINED;
    }));
    globalEnv.register("+", JSObject.newFunction("+", (_, args) -> (Integer) args[0] + (Integer) args[1]));
    globalEnv.register("-", JSObject.newFunction("-", (_, args) -> (Integer) args[0] - (Integer) args[1]));
    globalEnv.register("/", JSObject.newFunction("/", (_, args) -> (Integer) args[0] / (Integer) args[1]));
    globalEnv.register("*", JSObject.newFunction("*", (_, args) -> (Integer) args[0] * (Integer) args[1]));
    globalEnv.register("%", JSObject.newFunction("%", (_, args) -> (Integer) args[0] % (Integer) args[1]));
    globalEnv.register("==", JSObject.newFunction("==", (_, args) -> args[0].equals(args[1])));
    globalEnv.register("!=", JSObject.newFunction("!=", (_, args) -> !args[0].equals(args[1])));
    globalEnv.register("<", JSObject.newFunction("<",   (_, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) < 0)));
    globalEnv.register("<=", JSObject.newFunction("<=", (_, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) <= 0)));
    globalEnv.register(">", JSObject.newFunction(">",   (_, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) > 0)));
    globalEnv.register(">=", JSObject.newFunction(">=", (_, args) -> (((Comparable<Object>) args[0]).compareTo(args[1]) >= 0)));
    return globalEnv;
  }

  public static void interpret(Script script, PrintStream outStream) {
    var globalEnv = createGlobalEnv(outStream);
    var body = script.body();
    var function = ByteCodeRewriter.createFunction("main", List.of(), body, globalEnv);
    function.invoke(UNDEFINED);
  }
}
