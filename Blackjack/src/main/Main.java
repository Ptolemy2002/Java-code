package main;

import java.util.Arrays;

import blackjack.Card;
import blackjack.EnumCardNumbers;
import blackjack.EnumCardTypes;

public class Main {
	
	public static void testToString() {
		while (true) {
			if (Tools.Console.askBoolean("Would you like to cancel the toString test?", true)) break;
				
			EnumCardNumbers number = (EnumCardNumbers) Tools.Console.askSelection("Card Numbers", Arrays.asList((Object[]) EnumCardNumbers.values()), true, "CANCEL");
			EnumCardTypes type = (EnumCardTypes) Tools.Console.askSelection("Card Types", Arrays.asList((Object[]) EnumCardTypes.values()), true, "CANCEL");
			
			if (!(number == null || type == null)) {
				System.out.println(new Card(number, type).toString());
			}
		}
	}
	
	public static void main(String[] args) {
		testToString();
	}

}
