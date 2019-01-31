package main;

import java.util.ArrayList;

import cards.Card;
import cards.Deck;
import cards.EnumCardNumber;
import cards.EnumCardSuit;
import cards.blackjack.BlackjackGame;
import cards.blackjack.BlackjackPlayer;

public class Main {

	public static final Double MIN_BET = 2.0;
	public static final Double MAX_BET = 500.0;
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

	public static void main(String[] args) {
		game = new BlackjackGame(new Deck());
		
		int players = Tools.Console.askInt("How many players (not including dealer)?", true, x -> x >= 2,
				"The minimum value is 2.");
		game.addPlayer(new BlackjackPlayer(game, 1));
	}

}
