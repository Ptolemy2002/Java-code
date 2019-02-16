package cards;

import java.util.ArrayList;
import java.util.List;

import main.Tools;

public enum EnumCardNumber {
	ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JOKER, QUEEN, KING, ACE;

	/**
	 * Pick a random value
	 * 
	 * @return A random value in this enum.
	 */
	public static EnumCardNumber pickRandom() {
		return values()[Tools.Numbers.randomInt(0, values().length - 1)];
	}

	/**
	 * Pick a random number (not a face). The numbers are {@code [ONE, TWO, THREE,
	 * FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN]}
	 * 
	 * @return A random value classified as a number in this Enum.
	 */
	public static EnumCardNumber pickRandomNumber() {
		return getNumbers().get(Tools.Numbers.randomInt(0, getNumbers().size() - 1));
	}

	/**
	 * Pick a random face. The faces are {@code [JOKER, QUEEN, KING, ACE]}
	 * 
	 * @return A random value classified as a face in this Enum.
	 */
	public static EnumCardNumber pickRandomFace() {
		return getFaces().get(Tools.Numbers.randomInt(0, getFaces().size() - 1));
	}

	/**
	 * Get all values classified as a number in this enum. The numbers are
	 * {@code [ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN]}
	 * 
	 * @return An ArrayList containing all values classified as a number in this
	 *         enum.
	 */
	public static List<EnumCardNumber> getNumbers() {
		EnumCardNumber[] temp = new EnumCardNumber[] { ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN };
		List<EnumCardNumber> res = new ArrayList<>();

		for (EnumCardNumber i : temp) {
			res.add(i);
		}

		return res;
	}

	/**
	 * Get all values classified as a face in this enum. The faces are
	 * {@code [JOKER, QUEEN, KING]}
	 * 
	 * @return An ArrayList containing all values classified as a face in this enum.
	 */
	public static List<EnumCardNumber> getFaces() {
		EnumCardNumber[] temp = new EnumCardNumber[] { JOKER, QUEEN, KING };
		List<EnumCardNumber> res = new ArrayList<>();

		for (EnumCardNumber i : temp) {
			res.add(i);
		}

		return res;
	}

	/**
	 * Get all values in this enum.
	 * 
	 * @return An ArrayList containing all values in this enum.
	 */
	public static List<EnumCardNumber> getValues() {
		EnumCardNumber[] temp = values();
		List<EnumCardNumber> res = new ArrayList<>();

		for (EnumCardNumber i : temp) {
			res.add(i);
		}

		return res;
	}

	/**
	 * Test if the item is a face. The faces are {@code [JOKER, QUEEN, KING]}
	 * 
	 * @param item
	 *            The item to test
	 * @return Whether the item is a face
	 */
	public static boolean isFace(EnumCardNumber item) {
		return item == JOKER || item == QUEEN || item == KING;
	}

	/**
	 * Test if the item is a number (not a face). The numbers are
	 * {@code [ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN]}
	 * 
	 * @param item
	 *            The item to test
	 * @return Whether the item is a face
	 */
	public static boolean isNumber(EnumCardNumber item) {
		return !isFace(item);
	}

	/**
	 * Convert this object into a string.
	 * 
	 * @return Either the number form (1, 2, 3, ...), The string form of the face
	 *         (Queen, King, ...), or {@code "Jack"} (in case of Joker)
	 */
	@Override
	public String toString() {
		if (isNumber(this) && this != ACE) {
			return ((Integer) (this.ordinal() + 1)).toString();
		} else {
			switch (this) {
			case JOKER:
				return "Jack";
			case QUEEN:
				return "Queen";
			case KING:
				return "King";
			case ACE:
				return "Ace";
			default:
				return null;
			}
		}
	}
}
