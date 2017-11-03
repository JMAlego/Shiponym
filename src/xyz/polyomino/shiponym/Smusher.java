package xyz.polyomino.shiponym;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Smusher implements Iterable<Name> {

	private List<Name> names;
	private static Set<Character> consonents = new HashSet<Character>(Arrays.asList('b','c','d','f','h','j','k','l','m','n','o','p','q','r','s','t','v','w','x','z'));
	private static Set<Character> vowels = new HashSet<Character>(Arrays.asList('a','e','i','o','u'));
	private static Set<Character> vowel_likes = new HashSet<Character>(Arrays.asList('y'));
	
	private static Set<Character> repeatable = new HashSet<Character>(Arrays.asList('e','l','m','n','o','p','t'));
	private static Set<String> unusual_pairs = new HashSet<String>(Arrays.asList("ht","lh","dt","bk"));

	public Smusher() {
		names = new ArrayList<Name>();
	}

	public Smusher(ArrayList<Name> name_list) {
		names = name_list;
	}
	
	private static double clamp(double val, double min, double max) {
	    return Math.max(min, Math.min(max, val));
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
	 * Rate the names for how shippy they seem. (And how normal they seem, no strings of just 't's...)
	 *  
	 * @param names_to_rate
	 *            the names to rate as a set
	 * @return the rated names as a dictionary
	 */
	public Map<String, Integer> rateNames(Set<String> names_to_rate) {
		Map<String, Integer> rated_names = new HashMap<String, Integer>();
		
		for(String name : names_to_rate) {
			//Initial rating
			int rating = 100;
			//Rating 1 - currently disabled
			/*if(name.length() != averageLastNameLength())
				rating *= 0.2 + (4.0f/Math.sqrt(Math.pow(name.length(),1.5) + Math.pow(averageLastNameLength(),1.5)));*/
			
			//Rating 2
			double balance = 0;
			for(Character chr : name.toCharArray()) {
				if(consonents.contains(chr))
					balance++;
				if(vowels.contains(chr))
					balance--;
				if(vowel_likes.contains(chr))
					clamp(-balance,-0.5,0.5);
			}
			balance = Math.abs(balance/name.length());
			rating = (int) (rating*(1-balance));
			//Rating 3
			double bad_repeats = 0;
			Character last = null;
			Character before = null;
			for(Character chr : name.toCharArray()) {
				if(chr == last && !repeatable.contains(chr))
					bad_repeats++;
				if(last != null && unusual_pairs.contains(last.toString()+chr.toString()))
					bad_repeats += 2;
				if(last != null && before != null && last == before && last == chr)
					bad_repeats += 3;
				if(last != null && before != null && consonents.contains(before) && consonents.contains(last) && consonents.contains(chr))
					bad_repeats += 1;
				if(last != null && before != null && vowels.contains(before) && vowels.contains(last) && vowels.contains(chr))
					bad_repeats += 1;
				before = last;
				last = chr;
			}
			bad_repeats = Math.abs(bad_repeats/name.length());
			rating = (int) (rating*(1-bad_repeats));
			//Add fully rated name
			rated_names.put(name, rating);
		}
		
		return rated_names;
	}
	
	public static Set<String> filterResults(Map<String, Integer> rated_names) {
		Set<String> results = new HashSet<String>();
		double average_rating = 0;
		int count = 0;
		
		for(Entry<String, Integer> rated_name : rated_names.entrySet()) {
			average_rating += rated_name.getValue();
			count++;
		}
		average_rating = average_rating / count;
		average_rating = Math.max(50, average_rating);
		
		for(Entry<String, Integer> rated_name : rated_names.entrySet()) {
			if(rated_name.getValue() > average_rating)
				results.add(rated_name.getKey());
		}
		
		return results;
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
