package fr.umlv.smalljs.main;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import fr.umlv.smalljs.astinterp.main.ASTInterpreterMain;

public class Main {
	interface Runner {
		void run(String[] args) throws IOException;
	}
	
	interface Finder<T> {
		Optional<Runner> find(T value);
		
		default <R> Finder<R> invmap(Function<? super R, ? extends Optional<T>> mapper) {
			return value -> mapper.apply(value).flatMap(this::find);
		}
		
		default Finder<T> or(Finder<? super T> finder) {
			return args -> find(args).or(() -> finder.find(args));
		}
		
		static Finder<String> of(String interpreterName, Supplier<Runner> interpreter) {
			return name -> Optional.of(name).filter(interpreterName::equals).map(__ -> interpreter.get());
		}
	}
	
	private static String[] shift(String[] args) {
		return Arrays.stream(args).skip(1).toArray(String[]::new);
	}
	
  public static void main(String[] args) throws IOException {
		var finder = Finder.of("ast", () -> ASTInterpreterMain::main)
		    //.or(Finder.of("stack", () -> StackInterpreterMain::main)
				//.or(Finder.of("jvm", () -> JVMInterpreterMain::main)
				.invmap((String[] _args) -> Optional.of(_args).filter(__args -> __args.length >= 1).map(__args -> __args[0]))
		    .or(_args -> Optional.of(__ -> {
		  	  System.out.println("java fr.umlv.minijs.main.Main ast|stack|jvm [script.js]");
		  	  System.out.println();
		  	  System.out.println("  invalid arguments " + String.join(", ", _args));
		    }));
		
		finder.find(args).orElseThrow().run(shift(args));
	}
}
