package fr.umlv.smalljs.stackinterp.main;

import static fr.umlv.smalljs.ast.ASTBuilder.createScript;
import static java.lang.System.in;
import static java.nio.file.Files.newBufferedReader;
import static java.nio.file.Paths.get;

import java.io.IOException;
import java.io.InputStreamReader;

import fr.umlv.smalljs.ast.Script;
import fr.umlv.smalljs.stackinterp.StackInterpreter;

public class StackInterpreterMain {
  public static void main(String[] args) throws IOException {
    Script script;
    try(var reader = (args.length > 0)? newBufferedReader(get(args[0])): new InputStreamReader(in)) {
      script = createScript(reader);
    }
    StackInterpreter.interpret(script, System.out);
  }
}
