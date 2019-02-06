package main;

import java.util.ArrayList;

import cards.Card;
import cards.Deck;
import cards.EnumCardNumber;
import cards.EnumCardSuit;
import cards.blackjack.BlackjackGame;

public class Main {

	public static Double minBet = 2.0;
	public static Double maxBet = 500.0;
	
	public static BlackjackGame game;

	public static void testToString() {
		while (true) {
			if (Tools.Console.askBoolean("Would you like to cancel the toString test?", true))
				break;

			EnumCardNumber number = Tools.Console.askSelection("Card Numbers", EnumCardNumber.getValues(), true,
					"CANCEL");
			EnumCardSuit type = Tools.Console.askSelection("Card Types", EnumCardSuit.getValues(), true, "CANCEL");

			if (!(number == null || type == null)) {
				System.out.println(new Card(number, type).toString());
			}
		}
	}
	
	public static void properties() {
		@SuppressWarnings("serial")
		ArrayList<String> properties = new ArrayList<String>() {
			{
				add("minimum bet");
				add("maximum bet");
			}
		};
		switch (Tools.Console.askSelection("Properties", properties, true, "CANCEL")) {
		
		}
	}

	public static void main(String[] args) {
		BlackjackGame.printDescription();
		game = new BlackjackGame(new Deck());
		@SuppressWarnings("serial")
		ArrayList<String> choices = new ArrayList<String>() {
			{
				add("play");
				add("player setup");
				add("properties");
				add("quit");
			}
		};
		
		loop: while (true) {
			System.out.println("Pick a command to run");
			switch (Tools.Console.askSelection("Commands", choices, true, null)) {
			case "play":
				game.start();
				break;
			case "quit":
				break loop;
			case "properties":
				properties();
				break;
			}
		}
		
	}

}
