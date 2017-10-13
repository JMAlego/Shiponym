package xyz.polyomino.shiponym;

/**
 * A helper class for formatting command line output with ANSI escape sequences.
 * 
 * @author Jacob Allen
 *
 */
public class Colour {
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_BLACK = "\u001B[30m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_PURPLE = "\u001B[35m";
	private static final String ANSI_CYAN = "\u001B[36m";
	private static final String ANSI_WHITE = "\u001B[37m";
	private static final String[] RAINBOW = { ANSI_RED, ANSI_YELLOW, ANSI_GREEN, ANSI_CYAN, ANSI_BLUE, ANSI_PURPLE };
	private static final short RAINBOW_LENGTH = 6;

	/**
	 * Makes the provided text black.
	 * 
	 * @param text
	 *            the text to colour
	 */
	public static final String Black(String text) {
		return ANSI_BLACK + text + ANSI_RESET;
	}

	/**
	 * Makes the provided text red.
	 * 
	 * @param text
	 *            the text to colour
	 */
	public static final String Red(String text) {
		return ANSI_RED + text + ANSI_RESET;
	}

	/**
	 * Makes the provided text green.
	 * 
	 * @param text
	 *            the text to colour
	 */
	public static final String Green(String text) {
		return ANSI_GREEN + text + ANSI_RESET;
	}

	/**
	 * Makes the provided text yellow.
	 * 
	 * @param text
	 *            the text to colour
	 */
	public static final String Yellow(String text) {
		return ANSI_YELLOW + text + ANSI_RESET;
	}

	/**
	 * Makes the provided text blue.
	 * 
	 * @param text
	 *            the text to colour
	 */
	public static final String Blue(String text) {
		return ANSI_BLUE + text + ANSI_RESET;
	}

	/**
	 * Makes the provided text purple.
	 * 
	 * @param text
	 *            the text to colour
	 */
	public static final String Purple(String text) {
		return ANSI_PURPLE + text + ANSI_RESET;
	}

	/**
	 * Makes the provided text cyan.
	 * 
	 * @param text
	 *            the text to colour
	 */
	public static final String Cyan(String text) {
		return ANSI_CYAN + text + ANSI_RESET;
	}

	/**
	 * Makes the provided text white.
	 * 
	 * @param text
	 *            the text to colour
	 */
	public static final String White(String text) {
		return ANSI_WHITE + text + ANSI_RESET;
	}

	/**
	 * Makes the provided text rainbow.
	 * 
	 * @param text
	 *            the text to colour
	 */
	public static final String Rainbow(String text) {
		int index = 0;
		String returnString = "";
		while (index < text.length()) {
			returnString += RAINBOW[index % RAINBOW_LENGTH] + text.charAt(index);
			index++;
		}
		return returnString + ANSI_RESET;
	}

}