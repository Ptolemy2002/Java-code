package main;

import java.util.ArrayList;

import cards.Card;
import cards.CardPlayer;
import cards.Deck;
import cards.EnumCardNumber;
import cards.EnumCardSuit;
import cards.blackjack.BlackjackGame;

@SuppressWarnings("serial")
public class Main {

	public static Double minBet = 2.0;
	public static Double maxBet = 500.0;
	public static Double minAIBet = 2.0;
	public static Double maxAIBet = 500.0;
	public static Integer maxHits = Integer.MAX_VALUE;

	public static BlackjackGame game;

	public static void testToString() {
		while (true) {
			if (Tools.Console.askBoolean("Would you like to cancel the toString test?", true))
				break;

			EnumCardNumber number = Tools.Console.askSelection("Card Numbers", EnumCardNumber.getValues(), true,
					"CANCEL", true, true, true);
			EnumCardSuit type = Tools.Console.askSelection("Card Types", EnumCardSuit.getValues(), true, "CANCEL", true,
					true, true);

			if (!(number == null || type == null)) {
				System.out.println(new Card(number, type).toString());
			}
		}
	}

	public static void properties() {
		ArrayList<String> properties = new ArrayList<String>() {
			{
				add("minimum bet");
				add("maximum bet");
				add("minimum AI bet");
				add("maximum AI bet");
				add("maximum hits");
			}
		};

		String choice = Tools.Console.askSelection("Properties", properties, true, "Pick a property to edit", "CANCEL",
				true, true, true);
		if (choice != null) {
			switch (choice) {
			case "minumum bet":
				System.out.println("Description: the minimum bet a player can make.");
				System.out.println("\"minimum bet\" is currently $" + minBet);
				if (Tools.Console.askBoolean("Would you like to change it?", true)) {
					minBet = Tools.Console.askDouble("What would you like to change it to?", true, x -> x >= 0.01,
							"Bet must be at least 1 penny.");
					System.out.println("Changed \"minumum bet\" to " + minBet);
				}
				break;
			case "maximum bet":
				System.out.println("Description: the maximum bet a player can make.");
				System.out.println("\"maximum bet\" is currently $" + maxBet);
				if (Tools.Console.askBoolean("Would you like to change it?", true)) {
					maxBet = Tools.Console.askDouble("What would you like to change it to?", true, x -> x >= minBet,
							"Bet must be at least the value of \"minumum bet\" ($" + minBet + ")");
					System.out.println("Changed \"maximum bet\" to " + maxBet);
				}
				break;
			case "minimum AI bet":
				System.out.println("Description: the minimum bet an AI can make.");
				System.out.println("\"minimum AI bet\" is currently $" + minAIBet);
				if (Tools.Console.askBoolean("Would you like to change it?", true)) {
					minAIBet = Tools.Console.askDouble("What would you like to change it to?", true, x -> x >= 0.01,
							"Bet must be at least 1 penny.");
					System.out.println("Changed \"minumum AI bet\" to $" + minAIBet);
				}
				break;
			case "maximum AI bet":
				System.out.println("Description: the maximum bet an AI can make.");
				System.out.println("\"maximum AI bet\" is currently $" + maxAIBet);
				if (Tools.Console.askBoolean("Would you like to change it?", true)) {
					maxAIBet = Tools.Console.askDouble("What would you like to change it to?", true, x -> x >= minAIBet,
							"Bet must be at least the value of \"minumum AI bet\" ($" + minAIBet + ")");
					System.out.println("Changed \"maximum AI bet\" to " + maxAIBet);
				}
				break;
			case "maximum hits":
				System.out.println("Description: the maximum amount of hits a player can make in a turn.");
				System.out.println("\"maximum hits\" is currently " + maxHits);
				if (Tools.Console.askBoolean("Would you like to change it?", true)) {
					maxAIBet = Tools.Console.askDouble("What would you like to change it to?", true, x -> x >= 1,
							"Must be at least 1.");
					System.out.println("Changed \"maximum hits\" to " + maxHits);
				}
				break;
			}
		}
	}

	public static void betSetup() {
		ArrayList<CardPlayer> players = game.getPlayers();
		if (players.isEmpty()) {
			System.out.println("There are no players registered.");
		} else {
			CardPlayer player = Tools.Console.askSelection("Players", players, true, "Choose a player.", "CANCEL", true,
					true, true);
			if (player != null) {
				if (player.isAI()) {
					if (Tools.Console.askBoolean("Would you like to randomize the bet?", true)) {
						player.makeBet(minAIBet, maxAIBet);
					} else {
						player.setBet(Tools.Console.askDouble(
								player.toString() + "'s bet is $" + player.getBet()
										+ ". What would you like to change it to?",
								true, x -> x >= minAIBet && x <= maxAIBet,
								"The minimum AI bet is $" + minAIBet + ". The maximum AI bet is $" + maxAIBet
										+ " (you can change them in properties)."));
						System.out.println("Changed bet!");
					}
				} else {
					player.setBet(Tools.Console.askDouble(
							player.toString() + "'s bet is $" + player.getBet()
									+ ". What would you like to change it to?",
							true, x -> x >= minBet && x <= maxBet, "The minimum bet is $" + minBet
									+ ". The maximum bet is $" + maxBet + " (you can change them in properties)."));
					System.out.println("Changed bet!");
				}
			}
		}
	}

	public static void playerSetup() {
		ArrayList<CardPlayer> players = game.getPlayers();
		if (players.isEmpty()) {
			System.out.println("There are no players registered.");
		} else {
			if (Tools.Console.askBoolean(
					"There are " + players.size() + " players registered. Would you like to view the players?", true)) {
				Tools.Console.printList(players, true);
			}
		}

		ArrayList<String> choices = new ArrayList<String>() {
			{
				add("add");
			}
		};
		if (!players.isEmpty()) {
			choices.add("remove");
			choices.add("edit");
		}

		String choice = Tools.Console.askSelection("Choices", choices, true,
				"Choose an action (or the index of that action)", "CANCEL", true, true, true);
		if (choice != null) {
			switch (choice) {
			case "add":
				CardPlayer player = game
						.addNewPlayer(Tools.Console.askBoolean("Would you like your player to be an AI?", true));
				if (Tools.Console.askBoolean(
						"Your player's name is \"" + player.getName() + "\". Would you like to change it?", true)) {
					player.setName(Tools.Console.ask("What is the new name?"));
				}
				if (Tools.Console.askBoolean("Your player has $" + player.getMoney() + ". Would you like to change it?",
						true)) {
					if (player.isAI()) {
						player.setMoney(
								Tools.Console.askDouble("What is the new money amount?", true, x -> x >= minAIBet,
										"The minimum AI bet is $" + minAIBet + " (you can change it in properties)."));
					} else {
						player.setMoney(Tools.Console.askDouble("What is the new money amount?", true, x -> x >= minBet,
								"The minimum bet is $" + minBet + " (you can change it in properties)."));
					}
				}
				break;
			case "remove":
				players.remove(Tools.Console.askSelection("Players", players, true, "Pick a player", "CANCEL", true,
						true, true));
				break;
			case "edit":
				CardPlayer player1 = Tools.Console.askSelection("Players", players, true, "Pick a player", "CANCEL",
						true, true, true);
				if (Tools.Console.askBoolean(
						"Your player's name is \"" + player1.getName() + "\". Would you like to change it?", true)) {
					player1.setName(Tools.Console.ask("What is the new name?"));
				}
				if (Tools.Console.askBoolean(
						"Your player has $" + player1.getMoney() + ". Would you like to change it?", true)) {
					if (player1.isAI()) {
						player1.setMoney(
								Tools.Console.askDouble("What is the new money amount?", true, x -> x >= minAIBet,
										"The minimum AI bet is $" + minAIBet + " (you can change it in properties)."));
					} else {
						player1.setMoney(
								Tools.Console.askDouble("What is the new money amount?", true, x -> x >= minBet,
										"The minimum bet is $" + minBet + " (you can change it in properties)."));
					}

				}
				break;
			}
		}
	}

	public static void main(String[] args) {
		game = new BlackjackGame(new Deck()).setMaxHits(maxHits);
		game.addNewPlayer(false).setMoney(500.0);
		game.addNewPlayer(true).setMoney(500.0);

		System.out.println("Welcome to Blackjack!");
		if (Tools.Console.askBoolean("Would you like to hear the rules?", true))
			game.printDescription();
		System.out.println("Okay! Let's go!");
		System.out.println("");

		ArrayList<String> choices = new ArrayList<String>() {
			{
				add("play");
				add("player setup");
				add("properties");
				add("quit");
				add("help");
				add("rules");
				add("bet setup");
			}
		};

		loop: while (true) {
			game.setMaxHits(maxHits);
			String choice = Tools.Console.askSelection("Command Choices", choices, true,
					"What would you like to do (\"help\" for choices)?", null, true, false, false).toLowerCase();
			System.out.println("");
			switch (choice) {
			case "play":
				if (game.getPlayers().isEmpty()) {
					System.out.println("There are no players! Use the \"player setup\" command to add some.");
				} else {
					for (CardPlayer i : game.getPlayers()) {
						if (i.getBet() == 0) {
							System.out.println(i.toString() + " has no bet.");
							if (i.isAI()) {
								i.makeBet(minAIBet, maxAIBet);
							} else {
								i.makeBet(minBet, maxBet);
							}
						}
					}

					game.start();
				}
				break;
			case "quit":
				System.out.println("Goodbye.");
				break loop;
			case "properties":
				properties();
				break;
			case "help":
				System.out.println(
						"play - Play a game. There must be at least one registered player, and all registered players must have bets for this to work.");
				System.out.println(
						"player setup - This command allows you to register, edit, and remove players. You can add an AI or a user. You can also edit the money a player has.");
				System.out.println(
						"By default there is one player called \"Player 1\" and one AI called \"Player 2\", and they both have $500");
				System.out.println("bet setup - This command allows you to override the bet of any player.");
				System.out.println(
						"Set a player's bet to 0 if you would like them to choose at the beginning of a game.");
				System.out.println("rules - read the rules again.");
				System.out.println("help - show this list.");
				System.out.println("quit - end the program.");

				System.out.println("");
				System.out.println(
						"You do not need to specify the entire command. You only need to specify enough to isolate the meaning of your input.");
				break;
			case "rules":
				game.printDescription();
				break;
			case "player setup":
				playerSetup();
				break;
			case "bet setup":
				ArrayList<String> choices1 = new ArrayList<String>() {
					{
						add("automatic");
						add("manual");
					}
				};
				if (Tools.Console.askSelection("Choices", choices1, true,
						"Would you like to use automatic or manual mode?", "CANCEL", true, false, false)
						.equalsIgnoreCase("automatic")) {
					game.makeBets(minBet, maxBet, minAIBet, maxAIBet);
				} else {
					betSetup();
				}
				break;
			}
			System.out.println("");
		}
	}

}
