package xyz.polyomino.shiponym;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	 * Get the average last name length for the instance of Smusher.
	 * 
	 * @return the average length as a double
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
	 * Get the average first name length for the instance of Smusher.
	 * 
	 * @return the average length as a double
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
	 * Distributes
	 * 
	 * @param inputSets
	 *            An ArrayList of HashSets which represent name fragments as
	 *            strings.
	 * @return the combination of the input sets
	 */
	private Set<String> combineStringSets(List<Set<String>> inputSets) {
		Set<String> resultantSet = new HashSet<String>();

		if (inputSets.isEmpty()) {
			return new HashSet<String>();
		}

		boolean firstTimeAround = true;
		for (Set<String> set : inputSets) {
			Set<String> newResultantSet = new HashSet<String>();

			if (firstTimeAround) {
				newResultantSet.addAll(set);
				firstTimeAround = false;
			} else {
				for (String firstPart : resultantSet) {
					for (String secondPart : set) {
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
	 * Rate the names for how shippy they seem.
	 *  
	 * @param names_to_rate
	 *            the names to rate as a set
	 * @return the rated names as a dictionary
	 */
	public Map<String, Integer> rateNames(Set<String> names_to_rate) {
		Map<String, Integer> rated_names = new HashMap<String, Integer>();
		
		for(String name : names_to_rate) {
			int rating = 100;
			if(name.length() != averageLastNameLength())
				rating *= 0.2 + (4.0f/Math.sqrt(Math.pow(name.length(),1.5) + Math.pow(averageLastNameLength(),1.5)));
			rated_names.put(name, rating);
		}
		
		return rated_names;
	}

	/**
	 * Combines name fragments with even distribution. The first step in smushing.
	 * 
	 * @return the set of combined name fragments
	 */
	public Set<String> combineNameFragments() {
		Set<String> resultantSet = new HashSet<String>();
		List<Set<String>> nameFragmentSets = new ArrayList<Set<String>>();

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
