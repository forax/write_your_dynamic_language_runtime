package fr.umlv.smalljs.main;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

import fr.umlv.smalljs.astinterp.main.ASTInterpreterMain;

public class Main {
	interface Runner {
		void run(String[] args) throws IOException;
	}
	
	interface Finder {
		Optional<Runner> find(String[] args);
		
		default Finder or(Finder finder) {
			return args -> find(args).or(() -> finder.find(args));
		}
		
		static Finder of(String interpreterName, Supplier<Runner> interpreter) {
			return args -> Optional.of(args)
					.filter(_args -> _args.length >= 1)
					.map(_args -> _args[0])
					.filter(interpreterName::equals)
					.map(__ -> interpreter.get());
		}
	}
	
	private static String[] shift(String[] args) {
		return Arrays.stream(args).skip(1).toArray(String[]::new);
	}
	
  public static void main(String[] args) throws IOException {
		var finder = Finder.of("ast", () -> ASTInterpreterMain::main).
		    //or(Finder.of("stack", () -> () -> StackInterpreterMain.main(args))).
				//or(Finder.of("jvm", () -> () -> JVMInterpreterMain.main(args))).
		    or(_args -> Optional.of(__ -> {
		  	  System.out.println("java fr.umlv.minijs.main.Main ast|stack|jvm [script.js]");
		  	  System.out.println();
		  	  System.out.println("  invalid arguments " + String.join(", ", _args));
		    }));
		
		finder.find(args).orElseThrow().run(shift(args));
	}
}
