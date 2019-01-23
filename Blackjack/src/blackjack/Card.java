package blackjack;

public class Card {
	public EnumCardType type;
	public EnumCardNumber number;
	
	public Card(EnumCardNumber number, EnumCardType type) {
		this.number = number;
		this.type = type;
	}
	
	/**
	 * Convert this object into a string.
	 * 
	 * @return {@code number + " of " + type + "s"}
	 */
	@Override
	public String toString() {
		return number.toString() + " of " + type.toString() + "s"; //Examples: 3 of Clubs, Jack of Diamonds
	}
}
