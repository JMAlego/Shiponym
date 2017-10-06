package xyz.polyomino.shiponym;

import java.util.ArrayList;
import java.util.Iterator;

public class Smusher implements Iterable<Name> {

	private ArrayList<Name> names;

	public Smusher() {
		names = new ArrayList<Name>();
	}

	public Smusher(ArrayList<Name> name_list) {
		names = name_list;
	}

	/**
	 * Adds a name of type Name to the smusher!
	 * @param new_name the name to smush!
	 */
	public void addName(Name new_name) {
		names.add(new_name);
	}

	/**
	 * Adds a name as a string to the smusher!
	 * @param new_name the name to smush!
	 */
	public void addNameFromString(String new_name) {
		names.add(new Name(new_name));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String to_return = "{";
		for(Name name : names) {
			to_return += (to_return == "" ? "" : ", ") + name.getFullName();
		}
		to_return = "}";
		return to_return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Name> iterator() {
		return names.iterator();
	}

}
