package xyz.polyomino.shiponym;

/**
 * A program for procedurally generating ship names. Blame my fevered mind... and Sam.
 * @author Jacob Allen
 *
 */
public class Shiponym {

	/**
	 * Stores the current version of the program as a string. (And adds some random magic.)
	 */
	private static String version = "0.1.1." + (Math.round(Math.random() * 1000f));

	/**
	 * Prints the current version of the program.
	 */
	private static void version() {
		System.out.println("Welcome to Shiponym-NameSmusher 5000(tm)(r)(rtm)(xxl) by Jacob Allen, Version " + version);
	}

	/**
	 * Prints information. Prepends "[info]" to the text supplied and prints it.
	 * @param text the text to print
	 */
	private static void info(String text) {
		System.out.println("[info] " + text);
	}

	/**
	 * Prints warnings. Prepends "[warning]" to the text supplied and prints it.
	 * @param text the text to print
	 */
	@SuppressWarnings("unused")
	private static void warning(String text) {
		System.out.println("[warning] " + text);
	}

	/**
	 * Prints errors. Prepends "[error]" to the text supplied and prints it.
	 * @param text the text to print
	 */
	private static void error(String text) {
		System.out.println("[error] " + text);
	}

	/**
	 * The entry point for the application.
	 * @param args an array of command line arguments.
	 */
	public static void main(String[] args) {

		version();

		if (args.length == 0) {
			error("I'm gonna need some names to work. =|");
			return;
		} else if (args.length == 1) {
			error("I'm gonna need more than one name to work. =/");
			return;
		} else if (args.length > 2) {
			error("Sorry I only support two names right now. =(");
			return;
		}

		Smusher smusher = new Smusher();

		info("Starting...");

		for (String arg : args) {
			smusher.addNameFromString(arg);
		}

		for (Name name : smusher) {
			info("Name: " + name);
		}

		info("Finished.");

	}

}
