package xyz.polyomino.shiponym;

import java.util.Map;
import java.util.Set;

/**
 * A program for procedurally generating ship names. Blame my fevered mind...
 * and Sam.
 * 
 * @author Jacob Allen
 *
 */
public class Shiponym {

	static boolean debug_output_enabled = true;
	
	/**
	 * Stores the current version of the program as a string. (And adds some random
	 * magic.)
	 */
	private static final String version = "0.1.1." + (Math.round(Math.random() * 1000f));

	/**
	 * Prints the current version of the program.
	 */
	private static void version() {
		System.out.println("Welcome to Shiponym-NameSmusher 5000(tm)(r)(rtm)(xxl) by Jacob Allen, Version " + version);
	}

	/**
	 * Prints information. Prepends "[info]" to the text supplied and prints it.
	 * 
	 * @param text
	 *            the text to print
	 */
	private static void info(String text) {
		System.out.println(Colour.Bold(Colour.Blue("[info] ")) + text);
	}
	
	/**
	 * Prints information. Prepends "[debug]" to the text supplied and prints it.
	 * 
	 * @param text
	 *            the text to print
	 */
	private static void debug(String text) {
		if(debug_output_enabled)
			System.out.println(Colour.Bold(Colour.Rainbow("[debug] ")) + text);
	}

	/**
	 * Prints warnings. Prepends "[warning]" to the text supplied and prints it.
	 * 
	 * @param text
	 *            the text to print
	 */
	@SuppressWarnings("unused")
	private static void warning(String text) {
		System.out.println(Colour.Bold(Colour.Yellow("[warning] ")) + text);
	}

	/**
	 * Prints errors. Prepends "[error]" to the text supplied and prints it.
	 * 
	 * @param text
	 *            the text to print
	 */
	private static void error(String text) {
		System.out.println(Colour.Bold(Colour.Red("[error] ")) + text);
	}

	/**
	 * The entry point for the application.
	 * 
	 * @param args
	 *            an array of command line arguments.
	 */
	public static void main(String[] args) {

		version();

		if (args.length == 0) {
			error("I'm gonna need some names to work. =|");
			return;
		} else if (args.length == 1) {
			error("I'm gonna need more than one name to work. =/");
			return;
		}

		Smusher smusher = new Smusher();

		info("Starting...");

		for (String arg : args) {
			smusher.addNameFromString(arg);
		}

		info("Smushing the following:");

		for (Name name : smusher) {
			info(" - " + name);
		}
		
		info("Generating combined name fragments...");
		
		Set<String> nameFragments = smusher.combineNameFragments();

		debug(nameFragments.toString());
		
		info("Rating candidates fragments...");
		
		Map<String, Integer> ratedNames = smusher.rateNames(nameFragments);
		
		debug(ratedNames.toString());
		
		info("Filtering candidates...");
		
		Set<String> filteredNames = Smusher.filterResults(ratedNames);
		
		info("Results:");
		
		info(filteredNames.toString());

		info("Finished.");

	}

}
