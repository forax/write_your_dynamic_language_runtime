package fr.umlv.smalljs.main;

import static fr.umlv.smalljs.ast.ASTBuilder.createScript;

import fr.umlv.smalljs.ast.Script;
import fr.umlv.smalljs.astinterp.ASTInterpreter;
import fr.umlv.smalljs.jvminterp.JVMInterpreter;
import fr.umlv.smalljs.stackinterp.StackInterpreter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;

// run with /path/to/jdk-22/bin/java --class-path lib/tatoo-runtime.jar:target/smalljs-1.0.jar fr.umlv.smalljs.main.Main ast samples/hello.js
public class Main {
  private static BiConsumer<Script, PrintStream> interpreter(String name) {
    return switch (name) {
      case "ast" -> ASTInterpreter::interpret;
      case "stack" -> StackInterpreter::interpret;
      case "jvm" -> JVMInterpreter::interpret;
      default -> throw new IllegalArgumentException("unkonwn interpreter " + name);
    };
  }

  private static void printHelp() {
    System.err.println("""
            Help:
              fr.umlv.smalljs.main.Main interpreter input-file.js
                available interpreters: "ast", "stack" or "jvm"
            """);
    System.exit(1);
  }

  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      printHelp();
      return;
    }
    var interpreter = interpreter(args[0]);
    var text = Files.readString(Path.of(args[1]));
    var script = createScript(text);
    try {
      interpreter.accept(script, System.out);
    } catch(RuntimeException e) {
      e.printStackTrace();
      printHelp();
    }
  }
}
