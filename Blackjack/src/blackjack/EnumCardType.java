package blackjack;

import java.util.ArrayList;
import java.util.List;

import main.Tools;

public enum EnumCardType {
	CLUB, HEART, SPADE, DIAMOND;

	/**
	 * Pick a random value
	 * 
	 * @return A random value in this enum.
	 */
	public static EnumCardType pickRandom() {
		return values()[Tools.Numbers.randomInt(0, values().length - 1)];
	}

	/**
	 * Convert this object into a string.
	 * 
	 * @return The singular form of the face name. To get the plural form, append
	 *         "s" to the value.
	 */
	@Override
	public String toString() {
		switch (this) {
		case CLUB:
			return "Club";
		case HEART:
			return "Heart";
		case SPADE:
			return "Spade";
		case DIAMOND:
			return "Diamond";
		default:
			return null;
		}
	}

	/**
	 * Get all values in this enum.
	 * 
	 * @return An ArrayList containing all values in this enum.
	 */
	public static List<EnumCardType> getValues() {
		EnumCardType[] temp = values();
		List<EnumCardType> res = new ArrayList<>();

		for (EnumCardType i : temp) {
			res.add(i);
		}

		return res;
	}
}
