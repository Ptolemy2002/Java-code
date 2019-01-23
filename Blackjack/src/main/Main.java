package main;


import blackjack.Card;
import blackjack.EnumCardNumber;
import blackjack.EnumCardType;

public class Main {
	
	public static void testToString() {
		while (true) {
			if (Tools.Console.askBoolean("Would you like to cancel the toString test?", true)) break;
				
			EnumCardNumber number = Tools.Console.askSelection("Card Numbers",  EnumCardNumber.getValues(), true, "CANCEL");
			EnumCardType type = Tools.Console.askSelection("Card Types", EnumCardType.getValues(), true, "CANCEL");
			
			if (!(number == null || type == null)) {
				System.out.println(new Card(number, type).toString());
			}
		}
	}
	
	public static void main(String[] args) {
		testToString();
	}

}
