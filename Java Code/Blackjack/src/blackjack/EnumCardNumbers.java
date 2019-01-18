package blackjack;

import main.Tools;

public enum EnumCardNumbers {
	ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JOKER, QUEEN, KING, ACE;

	public static EnumCardNumbers pickRandom() {
		return values()[Tools.Numbers.randomInt(0, values().length - 1)];
	}

	public static EnumCardNumbers pickRandomNumber() {
		return getNumbers()[Tools.Numbers.randomInt(0, getNumbers().length - 1)];
	}

	public static EnumCardNumbers pickRandomFace() {
		return getFaces()[Tools.Numbers.randomInt(0, getFaces().length - 1)];
	}

	public static EnumCardNumbers[] getNumbers() {
		return new EnumCardNumbers[] { ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN };
	}

	public static EnumCardNumbers[] getFaces() {
		return new EnumCardNumbers[] { JOKER, QUEEN, ACE, KING };
	}

	public static boolean isFace(EnumCardNumbers item) {
		return item == JOKER || item == QUEEN || item == ACE || item == KING;
	}

	public static boolean isNumber(EnumCardNumbers item) {
		return !isFace(item);
	}
	
	@Override
	public String toString() {
		if (isNumber(this)) {
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
