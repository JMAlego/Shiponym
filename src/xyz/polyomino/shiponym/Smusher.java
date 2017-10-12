package xyz.polyomino.shiponym;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
	 * 
	 * @param new_name
	 *            the name to smush!
	 */
	public void addName(Name new_name) {
		names.add(new_name);
	}

	/**
	 * Adds a name as a string to the smusher!
	 * 
	 * @param new_name
	 *            the name to smush!
	 */
	public void addNameFromString(String new_name) {
		names.add(new Name(new_name));
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unused")
	private double averageLastNameLength() {
		double name_size_sum = 0;
		for (Name name : names) {
			name_size_sum += name.getLastName().length();
		}
		return name_size_sum / (double) names.size();
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unused")
	private double averageFisrtNameLength() {
		double nameSizeSum = 0;
		for (Name name : names) {
			nameSizeSum += name.getFirstName().length();
		}
		return nameSizeSum / (double) names.size();
	}

	/**
	 * @param remainingSets
	 * @return
	 */
	private HashSet<String> combineStringSets(ArrayList<HashSet<String>> inputSets) {
		HashSet<String> resultantSet = new HashSet<String>();
		
		if(inputSets.isEmpty()) {
			return new HashSet<String>();
		}
		
		boolean firstTimeAround = true;
		for(HashSet<String> set : inputSets) {
			HashSet<String> newResultantSet = new HashSet<String>();
			if(firstTimeAround) {
				newResultantSet.addAll(set);
				firstTimeAround = false;
			}else {
				for(String firstPart : resultantSet) {
					for(String secondPart : set) {
						newResultantSet.add(firstPart + secondPart);
						newResultantSet.add(secondPart + firstPart);
					}
				}
			}
			resultantSet = newResultantSet;
		}
		
		return resultantSet;
	}

	/**
	 * @return
	 */
	public HashSet<String> combineNameFragments() {
		HashSet<String> resultantSet = new HashSet<String>();
		ArrayList<HashSet<String>> nameFragmentSets = new ArrayList<HashSet<String>>();

		for (Name name : names) {
			nameFragmentSets.add(name.generateLastNameFragments());
		}

		resultantSet = combineStringSets(nameFragmentSets);

		return resultantSet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String to_return = "{";
		for (Name name : names) {
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
