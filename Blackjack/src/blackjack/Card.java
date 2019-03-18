package blackjack;

public class Card {
	public EnumCardSuit suit;
	public EnumCardNumber number;
	public boolean faceUp;

	public Card(EnumCardNumber number, EnumCardSuit suit) {
		this.number = number;
		this.suit = suit;
	}

	public Card(EnumCardNumber number, EnumCardSuit suit, boolean faceUp) {
		this.number = number;
		this.suit = suit;
		this.faceUp = faceUp;
	}

	public Card() {
		this.number = EnumCardNumber.pickRandom();
		this.suit = EnumCardSuit.pickRandom();
		this.faceUp = false;
	}

	public Card(boolean face) {
		this.number = face ? EnumCardNumber.pickRandomFace() : EnumCardNumber.pickRandomNumber();
		this.suit = EnumCardSuit.pickRandom();
		this.faceUp = false;
	}

	public Card(boolean face, boolean faceUp) {
		this.number = face ? EnumCardNumber.pickRandomFace() : EnumCardNumber.pickRandomNumber();
		this.suit = EnumCardSuit.pickRandom();
		this.faceUp = faceUp;
	}

	public Card setSuit(EnumCardSuit suit) {
		this.suit = suit;
		return this;
	}

	public Card setNumber(EnumCardNumber number) {
		this.number = number;
		return this;
	}

	public Card setFaceUp(boolean faceUp) {
		this.faceUp = faceUp;
		return this;
	}

	public boolean isCard(EnumCardNumber number, EnumCardSuit suit) {
		return this.number == number && this.suit == suit;
	}

	/**
	 * Convert this object into a string.
	 * 
	 * @return {@code number + " of " + type + "s"}
	 */
	@Override
	public String toString() {
		return number.toString() + " of " + suit.toString() + "s " + "(" + (faceUp ? "face up" : "face down") + ")"; // Examples:
																														// 3
																														// of
																														// Clubs,
																														// Jack
																														// of
																														// Diamonds
	}
}
