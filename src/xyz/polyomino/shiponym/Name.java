package xyz.polyomino.shiponym;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * A class representing a simple name, with a last name, possibly a first name,
 * and possibly middle name(s). Can do simple operations such as tell if the
 * name is double barrelled. Can iterate over each element in the name, in order
 * first to last.
 * 
 * @author Jacob Allen
 * 
 */
public class Name implements Iterable<String> {
	private String first = null;
	private String middle = null;
	private String last = null;
	private boolean double_barrelled = false;

	/**
	 * Creates an instance of Name from a string containing a full name.
	 * 
	 * @param full_name
	 *            the full name (with spaces)
	 */
	public Name(String full_name) {
		if (full_name == null) {
			throw new NullPointerException("Name must not be null.");
		}
		if (full_name.trim() == "") {
			throw new IllegalArgumentException("Name must not be empty.");
		}

		String[] names = full_name.trim().split(" ");

		if (names.length == 1) {
			last = names[0];
		}
		if (names.length > 1) {
			first = names[0];
			last = names[names.length - 1];
		}
		if (names.length > 2) {
			middle = "";
			for (String part : names) {
				middle += (middle == "" ? "" : " ") + part;
			}
		}
		if (last.contains("-")) {
			double_barrelled = true;
		}
	}

	/**
	 * Creates an instance of Name from strings containing a first name, and a last
	 * name.
	 * 
	 * @param first_name
	 *            the first name
	 * @param last_name
	 *            the last name
	 */
	public Name(String first_name, String last_name) {
		first = first_name;
		last = last_name;
		if (last.contains("-")) {
			double_barrelled = true;
		}
	}

	/**
	 * Creates an instance of Name from strings containing a fist name, middle names
	 * (with spaces between them), and a last name.
	 * 
	 * @param first_name
	 *            the first name
	 * @param middle_names
	 *            the middle names (with spaces if needed)
	 * @param last_name
	 *            the last name
	 */
	public Name(String first_name, String middle_names, String last_name) {
		first = first_name;
		middle = middle_names;
		last = last_name;
		if (last.contains("-")) {
			double_barrelled = true;
		}
	}

	/**
	 * Gets the full name as a string.
	 * 
	 * @return the full name as a string
	 */
	public String getFullName() {
		if (middle == null) {
			return getShortName();
		}
		return first + " " + middle + " " + last;
	}

	/**
	 * Gets the short name I.E. first name + last name as a string.
	 * 
	 * @return the short name
	 */
	public String getShortName() {
		return first + " " + last;
	}

	/**
	 * Gets the first name.
	 * 
	 * @return the first name
	 */
	public String getFirstName() {
		return first;
	}

	/**
	 * Gets the last name.
	 * 
	 * @return the last name
	 */
	public String getLastName() {
		return last;
	}

	/**
	 * Checks if the name is double barrelled (hyphenated).
	 * 
	 * @return true if the last name is double barrelled (or more)
	 */
	public boolean isDoubleBarrelled() {
		return double_barrelled;
	}

	/**
	 * Gets a randomly selected last name if the name is double barrelled, else just
	 * returns the last name as a string.
	 * 
	 * @return the randomly selected last name as a string
	 */
	public String getSingleLastName() {
		if (double_barrelled) {
			String[] last_names = last.split("-");
			return last_names[(new Random()).nextInt(last_names.length)];
		} else {
			return last;
		}
	}

	/**
	 * Generates fragments of the last name from both ends of the name.
	 * 
	 * @return the resulting HashSet of name fragments
	 */
	public Set<String> generateLastNameFragments() {
		Set<String> resultantSet = new HashSet<String>();

		for (String lastname : last.split("-")) {
			String currentNameFragment = "";
			String reversedLastName = "";

			for (char character : lastname.toCharArray()) {
				currentNameFragment += character;
				if (currentNameFragment != "")
					resultantSet.add(currentNameFragment.toLowerCase());
				reversedLastName = character + reversedLastName;
			}

			currentNameFragment = "";
			for (char character : reversedLastName.toCharArray()) {
				currentNameFragment = character + currentNameFragment;
				if (currentNameFragment != "")
					resultantSet.add(currentNameFragment.toLowerCase());
			}
		}

		return resultantSet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getFullName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<String> iterator() {
		ArrayList<String> names_list = new ArrayList<String>();
		names_list.add(first);
		if (middle != null) {
			for (String name : middle.split(" ")) {
				names_list.add(name);
			}
		}
		names_list.add(last);
		return names_list.iterator();
	}
}
