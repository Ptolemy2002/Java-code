package blackjack;

import main.Tools;

public enum EnumCardTypes {
	CLUB, HEART, SPADE, DIAMOND;

	public static EnumCardTypes pickRandom() {
		return values()[Tools.Numbers.randomInt(0, values().length - 1)];
	}
	
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
}
