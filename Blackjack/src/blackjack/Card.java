package blackjack;

public class Card {
	public EnumCardTypes type;
	public EnumCardNumbers number;
	
	public Card(EnumCardNumbers number, EnumCardTypes type) {
		this.number = number;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return number.toString() + " of " + type.toString() + "s"; //Examples: 3 of Clubs, Jack of Diamonds
	}
}
