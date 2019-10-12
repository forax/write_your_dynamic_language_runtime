package fr.umlv.smalljs.jvminterp.main;

import static fr.umlv.smalljs.ast.ASTBuilder.createScript;
import static java.lang.System.in;
import static java.nio.file.Files.newBufferedReader;
import static java.nio.file.Paths.get;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import fr.umlv.smalljs.ast.Script;
import fr.umlv.smalljs.jvminterp.JVMInterpreter;

// java -cp lib/tatoo-runtime.jar:lib/asm-7.1.jar:lib/asm-util-7.1.jar:classes/ fr.umlv.smalljs.main.JVMInterpreterMain
public class JVMInterpreterMain {
  public static void main(String... args) throws IOException {
    Script script;
    try(Reader reader = (args.length > 0)? newBufferedReader(get(args[0])): new InputStreamReader(in)) {
      script = createScript(reader);
    }
    JVMInterpreter.interpret(script, System.out);
  }
}
