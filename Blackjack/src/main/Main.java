package main;

import java.awt.GraphicsEnvironment;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cards.Card;
import cards.CardPlayer;
import cards.Deck;
import cards.EnumCardNumber;
import cards.EnumCardSuit;
import cards.blackjack.BlackjackGame;

@SuppressWarnings({ "serial", "unchecked" })
public class Main {
	/**
	 * This should be true if running in eclipse, but false otherwise.
	 */
	public static final boolean DEBUG_MODE = false;

	public static Double minBet = 2.0;
	public static Double maxBet = 500.0;
	public static Double minAIBet = 2.0;
	public static Double maxAIBet = 500.0;
	public static Integer maxHits = Integer.MAX_VALUE;
	public static boolean autoSave = true;

	public static String currentDeck = "standard";
	public static Deck deck = Deck.STANDARD_52;
	public static HashMap<String, Deck> decks = new HashMap<>();

	public static BlackjackGame game;
	public static final String PATH = Tools.Variables.getAppdata() + "\\Ptolemy's code\\Blackjack";
	public static final String LAUNCHER_PATH = Tools.Variables.getAppdata()
			+ "\\Ptolemy's code\\Blackjack\\temp\\launcher.bat";
	public static final String VERSION = "1.1";
	public static final int VERSION_CODE = 1;
	public static final String[][] patchNotes = { { "global release" },
			{ "alerts will be made when a player goes bankrupt or goes into debt.", "bug fixes", "Added patch notes" } };
	public static final ArrayList<String> versionCodes = new ArrayList<String>() {
		{
			add("1.0");
			add("1.1");
		}
	};

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
				System.out.println(
						"\"maximum hits\" is currently " + (maxHits == Integer.MAX_VALUE ? "Infinity" : maxHits));
				if (Tools.Console.askBoolean("Would you like to set it to infinity?", true)) {
					maxHits = Integer.MAX_VALUE;
					System.out.println(
							"Changed \"maximum hits\" to " + (maxHits == Integer.MAX_VALUE ? "Infinity" : maxHits));
				} else {
					if (Tools.Console.askBoolean("Would you like to change it?", true)) {
						maxAIBet = Tools.Console.askDouble("What would you like to change it to?", true, x -> x >= 1,
								"Must be at least 1.");
						System.out.println(
								"Changed \"maximum hits\" to " + (maxHits == Integer.MAX_VALUE ? "Infinity" : maxHits));
					}
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

		String choice = Tools.Console.askSelection("Actions", choices, true,
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

	public static void saveDecks() {
		if (!Tools.Files.fileExists(PATH + "\\decks.json")) {
			System.out.println("The decks.json file doesn't exist yet. Initializing it...");
			if (Tools.Files.writeToFile(PATH + "\\decks.json", "{}")) {
				System.out.println("Successfully initialized the decks.json file.");
			} else {
				System.out.println("There was an error initializing the file \"" + PATH + "\\decks.json" + "\"");
			}
		}
		JSONObject decksSave = null;
		try {
			decksSave = (JSONObject) new JSONParser().parse(Tools.Files.readFromFile(PATH + "\\decks.json"));
		} catch (ParseException e) {
		}

		if (decksSave == null) {
			System.out.println("The decks save is either corrupted or not compatible with this verison!");
			if (Tools.Console.askBoolean("Would you like to load defaults (you will lose data)!", true)) {
				Tools.Files.writeToFile(PATH + "\\decks.json", "{}");
				decksSave = new JSONObject();
			}
		}

		if (decksSave != null) {
			for (String i : decks.keySet()) {
				JSONArray cards = new JSONArray();
				for (Card j : decks.get(i).getCards()) {
					JSONArray card = new JSONArray();
					card.add(j.number.toString());
					card.add(j.suit.toString());
					card.add(j.faceUp);
					cards.add(card);
				}
				decksSave.put(i, cards);
			}
			if (Tools.Files.writeToFile(PATH + "\\decks.json", decksSave.toJSONString())) {
				System.out.println("Successfully saved the decks.");
			} else {
				System.out.println("There was an error writing to the file \"" + PATH + "\\decks.json" + "\"");
			}

		}

	}

	public static void loadDecks() {
		if (!Tools.Files.fileExists(PATH + "\\decks.json")) {
			System.out.println("The decks.json file doesn't exist yet. Initializing it...");
			if (Tools.Files.writeToFile(PATH + "\\decks.json", "{}")) {
				System.out.println("Successfully initialized the decks.json file.");
			} else {
				System.out.println("There was an error initializing the file \"" + PATH + "\\decks.json" + "\"");
			}
		}

		JSONObject decksSave = null;
		try {
			decksSave = (JSONObject) new JSONParser().parse(Tools.Files.readFromFile(PATH + "\\decks.json"));
		} catch (ParseException e) {
		}

		if (decksSave == null) {
			System.out.println("The decks save is either corrupted or not compatible with this verison!");
			if (Tools.Console.askBoolean("Would you like to load defaults (you will lose data)!", true)) {
				Tools.Files.writeToFile(PATH + "\\decks.json", "{}");
				decksSave = new JSONObject();
			}
		}

		if (decksSave != null) {
			decks = new HashMap<String, Deck>();
			for (Object i : decksSave.keySet()) {
				decks.put((String) i, new Deck(new Card[] {}));
				JSONArray cards = (JSONArray) decksSave.get(i);
				for (Object j : cards) {
					if (j instanceof JSONArray) {
						JSONArray card = (JSONArray) j;
						decks.get(i)
								.putCardAtBottom(new Card().setNumber(EnumCardNumber.fromString((String) card.get(0)))
										.setSuit(EnumCardSuit.fromString((String) card.get(1)))
										.setFaceUp((Boolean) card.get(2)));
					} else if (j instanceof String) {
						if (decks.containsKey((String) j)) {
							decks.get(i).appendDeck(decks.get((String) j));
						}
					}
				}
			}
			System.out.println("Successfully loaded the saved decks.");
		}
	}

	public static void loadSave(JSONObject save) {
		game.setMaxHits(((Long) save.get("maxHits")).intValue());
		autoSave = (Boolean) save.get("autoSave");
		minBet = (Double) save.get("minBet");
		maxBet = (Double) save.get("maxBet");
		minAIBet = (Double) save.get("minAIBet");
		maxAIBet = (Double) save.get("maxAIBet");
		loadDecks();
		String temp = (String) save.get("deck");
		if (decks.containsKey(temp)) {
			deck = decks.get(temp);
		} else {
			deck = Deck.STANDARD_52;
		}

		JSONArray players = (JSONArray) save.get("players");
		for (Object i : players) {
			JSONObject data = (JSONObject) i;
			game.addNewPlayer((Boolean) data.get("ai")).setName((String) data.get("name"))
					.setMoney((Double) data.get("money")).setBet((Double) data.get("bet"));
		}
	}

	public static void saveTo(String save, JSONObject value) {
		saveDecks();
		JSONObject latestSave = new JSONObject();
		try {
			latestSave = (JSONObject) new JSONParser()
					.parse(Tools.Files.readFromFile(PATH + "\\saves\\" + save + ".json"));
		} catch (ParseException e) {
		}
		for (Object i : value.keySet()) {
			latestSave.put(i, value.get(i));
		}

		if (!Tools.Files.writeToFile(PATH + "\\saves\\" + save + ".json", latestSave.toJSONString())) {
			System.out.println("There was an error saving to the save \"" + save + "\"");
		} else {
			System.out.println("Successfully saved to the save file \"" + save + "\"");
		}
	}

	public static void saveToDefault() {
		saveDecks();
		if (!DEBUG_MODE) {
			JSONObject latestSave = new JSONObject();
			JSONObject defaultSave = new JSONObject();
			try {
				latestSave = (JSONObject) new JSONParser()
						.parse(Tools.Files.readFromFile(PATH + "\\saves\\latest.json"));
			} catch (ParseException e) {
			}
			try {
				defaultSave = (JSONObject) new JSONParser()
						.parse(Tools.Files.getResource("/assets/default.json", Main.class));
			} catch (ParseException e) {
			}

			for (Object i : defaultSave.keySet()) {
				latestSave.put(i, defaultSave.get(i));
			}

			if (!Tools.Files.writeToFile(PATH + "\\saves\\latest.json", latestSave.toJSONString())) {
				System.out.println("There was an error writing to the latest save file!");
			} else {
				System.out.println("Successfully wrote to the latest save file!");
			}
		} else {
			JSONObject latestSave = new JSONObject();
			JSONObject defaultSave = new JSONObject();
			try {
				latestSave = (JSONObject) new JSONParser()
						.parse(Tools.Files.readFromFile(PATH + "\\saves\\latest.json"));
			} catch (ParseException e) {
			}
			try {
				defaultSave = (JSONObject) new JSONParser()
						.parse(Tools.Files.readFromFile("src\\assets\\default.json"));
			} catch (ParseException e) {
			}

			for (Object i : defaultSave.keySet()) {
				latestSave.put(i, defaultSave.get(i));
			}

			if (!Tools.Files.writeToFile(PATH + "\\saves\\latest.json", latestSave.toJSONString())) {
				System.out.println("There was an error writing to the latest save file!");
			} else {
				System.out.println("Successfully wrote to the latest save file!");
			}
		}
	}

	public static void saveToDefault(String save) {
		saveDecks();
		if (!DEBUG_MODE) {
			JSONObject latestSave = new JSONObject();
			JSONObject defaultSave = new JSONObject();
			try {
				latestSave = (JSONObject) new JSONParser()
						.parse(Tools.Files.readFromFile(PATH + "\\saves\\" + save + ".json"));
			} catch (ParseException e) {
			}
			try {
				defaultSave = (JSONObject) new JSONParser()
						.parse(Tools.Files.getResource("/assets/default.json", Main.class));
			} catch (ParseException e) {
			}

			for (Object i : defaultSave.keySet()) {
				latestSave.put(i, defaultSave.get(i));
			}

			if (!Tools.Files.writeToFile(PATH + "\\saves\\" + save + ".json", latestSave.toJSONString())) {
				System.out.println("There was an error writing to the save file \"" + save + "\"");
			} else {
				System.out.println("Successfully wrote to the save file \"" + save + "\"");
			}
		} else {
			JSONObject latestSave = new JSONObject();
			JSONObject defaultSave = new JSONObject();
			try {
				latestSave = (JSONObject) new JSONParser()
						.parse(Tools.Files.readFromFile(PATH + "\\saves\\" + save + ".json"));
			} catch (ParseException e) {
			}
			try {
				defaultSave = (JSONObject) new JSONParser()
						.parse(Tools.Files.readFromFile("src\\assets\\default.json"));
			} catch (ParseException e) {
			}

			for (Object i : defaultSave.keySet()) {
				latestSave.put(i, defaultSave.get(i));
			}

			if (!Tools.Files.writeToFile(PATH + "\\saves\\" + save + ".json", latestSave.toJSONString())) {
				System.out.println("There was an error writing to the save file \"" + save + "\"");
			} else {
				System.out.println("Successfully wrote to the save file \"" + save + "\"");
			}
		}
	}

	public static void loadSaveWithErrorCheck(String saveChoice) {
		JSONObject save = null;
		try {
			save = (JSONObject) new JSONParser()
					.parse(Tools.Files.readFromFile(PATH + "\\saves\\" + saveChoice + ".json"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (save == null) {
			System.out.println("There was an error interpreting the save file \"" + saveChoice + "\"");

			if (Tools.Console.askBoolean("Would you like to load the default save (you will lose data)?", true)) {
				System.out.println("Loading defaults...");
				if (!DEBUG_MODE) {
					if (!Tools.Files.writeToFile(PATH + "\\saves\\" + saveChoice + ".json",
							Tools.Files.getResource("/assets/default.json", Main.class))) {
						System.out.println("There was an error writing to the save file!");
					}
				} else {
					if (!Tools.Files.writeToFile(PATH + "\\saves\\" + saveChoice + ".json",
							Tools.Files.readFromFile("src\\assets\\default.json"))) {
						System.out.println("There was an error writing to the latest save file!");
					}
					// System.out.println(Tools.Files.readFromFile("src\\assets\\default.json"));
				}
			}
		} else {
			try {
				loadSave(save);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("The save file is either corrupted or incompatible with this version.");
				if (Tools.Console.askBoolean("Would you like to load the default save (you will lose data)?", true)) {
					System.out.println("Loading defaults...");
					if (!DEBUG_MODE) {
						if (!Tools.Files.writeToFile(PATH + "\\saves\\" + saveChoice + ".json",
								Tools.Files.getResource("/assets/default.json", Main.class))) {
							System.out.println("There was an error writing to the save file!");
						}
					} else {
						if (!Tools.Files.writeToFile(PATH + "\\saves\\" + saveChoice + ".json",
								Tools.Files.readFromFile("src\\assets\\default.json"))) {
							System.out.println("There was an error writing to the latest save file!");
						}
						// System.out.println(Tools.Files.readFromFile("src\\assets\\default.json"));
					}
				}
			}
		}
		System.out.println("Loaded the current data from " + saveChoice + ".json");
	}

	public static JSONObject getCurrentSave() {
		JSONObject res = new JSONObject();
		res.put("autoSave", autoSave);
		res.put("maxHits", maxHits);
		res.put("minBet", minBet);
		res.put("maxBet", maxBet);
		res.put("minAIBet", minAIBet);
		res.put("maxAIBet", maxAIBet);
		res.put("deck", currentDeck);
		res.put("deck", currentDeck);
		res.put("version", VERSION);
		JSONArray players = new JSONArray();
		for (CardPlayer i : game.getPlayers()) {
			JSONObject temp = new JSONObject();
			temp.put("name", i.getName());
			temp.put("ai", i.isAI());
			temp.put("money", i.getMoney());
			temp.put("bet", i.getBet());
			players.add(temp);
		}
		res.put("players", players);

		return res;
	}

	public static void deckEdit() {
		if (Tools.Console.askBoolean("Would you like to create a new deck?", true)) {
			String name = Tools.Console.ask("What is the name of the new deck?", true,
					x -> !x.equals("standard") && !decks.containsKey(x), "Cannot be standard or already existing!");
			decks.put(name, new Deck(new Card[] {}));
			System.out.println("Created an empty deck.");
		} else if (Tools.Console.askBoolean("Would you like to delete a deck?", true)) {
			String d = Tools.Console.askSelection("Decks", new ArrayList<String>(decks.keySet()), true,
					"Choose a deck to edit.", "CANCEL", true, true, true, false);
			if (d != null) {
				decks.remove(d);
				System.out.println("Removed the deck.");
			}
		} else {
			String choice = Tools.Console.askSelection("Decks", new ArrayList<String>(decks.keySet()), true,
					"Choose a deck to edit.", "CANCEL", true, true, true, false);

			if (choice != null) {
				Deck d = decks.get(choice);
				if (Tools.Console.askBoolean("Would you like to show the contents of this deck?", true)) {
					// Put all cards face up so that user can view them.
					Deck shownDeck = new Deck(d);
					for (Card i : shownDeck.getCards()) {
						i.setFaceUp(true);
					}
					Tools.Console.printList(choice, shownDeck.getCards(), true, 10, "CANCEL");
				}

				ArrayList<String> choices = new ArrayList<String>() {
					{
						add("add");
						add("remove");
						add("append");
						add("delete deck");
						add("append deck");
					}
				};
				String choice1 = Tools.Console.askSelection("Choices", choices, true, "Choose an action to perform.",
						"CANCEL", true, true, true, false);
				if (choice1 != null) {
					switch (choice1) {
					case "add":
						EnumCardSuit suit = Tools.Console.askSelection("Suits", EnumCardSuit.getValues(), true,
								"Choose a suit for your card.", "CANCEL", true, true, true);
						if (suit != null) {
							EnumCardNumber number = Tools.Console.askSelection("Numbers", EnumCardNumber.getValues(),
									true, "Choose a number for your card.", "CANCEL", true, true, true);
							if (number != null) {
								Integer index = Tools.Console.askInt("Choose an index to put your card in.", true,
										x -> x >= Math.min(d.getCards().size(), 1) && x <= d.getCards().size(),
										"Minimun value is 1. Maximum value is " + d.getCards().size());
								d.putCardAt(new Card(number, suit, true), index);
							}
						}
						break;
					case "append":
						EnumCardSuit suit1 = Tools.Console.askSelection("Suits", EnumCardSuit.getValues(), true,
								"Choose a suit for your card.", "CANCEL", true, true, true);
						if (suit1 != null) {
							EnumCardNumber number = Tools.Console.askSelection("Numbers", EnumCardNumber.getValues(),
									true, "Choose a number for your card.", "CANCEL", true, true, true);
							if (number != null) {
								d.putCardAtBottom(new Card(number, suit1, true));
							}
						}
						break;
					case "remove":
						Integer index = Tools.Console.askInt("Choose an index to put your card in.", true,
								x -> x >= 1 && x <= d.getCards().size(),
								"Minimun value is 1. Maximum value is " + d.getCards().size()) - 1;
						d.removeCard(index);
					case "append deck":
						ArrayList<String> deckKeys = new ArrayList<String>(decks.keySet());
						deckKeys.remove(choice);
						deckKeys.add("standard");
						String choice2 = Tools.Console.askSelection("Decks", deckKeys, true, "Choose a deck to append.",
								"CANCEL", true, true, true, false);
						if (choice2 != null) {
							if (!choice2.equals("standard")) {
								d.appendDeck(decks.get(choice2));
							} else {
								d.appendDeck(Deck.STANDARD_52);
							}
						}
						break;
					}
				}

			}
		}
	}

	public static void printPatchNotes(String version) {
		for (int i = versionCodes.indexOf(version); i <= versionCodes.indexOf(VERSION); i++) {
			System.out.println("v" + versionCodes.get(i) + ":");
			for (String j : patchNotes[i]) {
				System.out.println("- " + j);
			}
		}
	}

	public static void main(String[] args) {
		boolean cont = true;
		if (!(DEBUG_MODE)) {
			Console console = System.console();
			if (console == null && !GraphicsEnvironment.isHeadless()) {
				String filename = Main.class.getProtectionDomain().getCodeSource().getLocation().toString()
						.substring(6);
				try {
					File batch = new File(LAUNCHER_PATH);
					Tools.Files.deleteFile(batch);
					File parent = batch.getParentFile();
					if (!parent.exists() && !parent.mkdirs()) {
						throw new IOException("Couldn't create dir: " + parent);
					}
					batch.createNewFile();
					PrintWriter writer = new PrintWriter(batch);
					writer.println("@echo off");
					writer.println("java -jar " + filename);
					writer.println("exit");
					writer.flush();
					writer.close();
					Runtime.getRuntime().exec("cmd /c start \"\" \"" + batch.getPath() + "\"");
				} catch (IOException e) {
					e.printStackTrace();
				}
				cont = false;
			}
		}

		if (cont) {
			System.out.println("Blackjack v" + VERSION);
			game = new BlackjackGame(deck);

			if (!DEBUG_MODE) {
				// System.out.println(Tools.Files.readFromFile(PATH + "\\version.txt"));
				if (!Tools.Files.fileExists(PATH + "\\saves\\latest.json")
						|| !Tools.Files.fileExists(PATH + "\\version.txt")
						|| !Tools.Files.readFromFile(PATH + "\\version.txt").equals(VERSION)) {
					System.out.println("The latest save file does not yet exist or is not up to date.");
					System.out.println("Initializing it...");
					if (!Tools.Files.writeToFile(PATH + "\\saves\\latest.json",
							Tools.Files.getResource("/assets/default.json", Main.class))) {
						System.out.println("There was an error initializing the latest save file!");
					} else {
						System.out.println("Initialized!");
					}
				}
			} else {
				if (!Tools.Files.fileExists(PATH + "\\saves\\latest.json")
						|| !Tools.Files.fileExists(PATH + "\\version.txt")
						|| !Tools.Files.readFromFile(PATH + "\\version.txt").equals(VERSION)) {
					System.out.println("The latest save file does not yet exist or is not up to date.");
					System.out.println("Initializing it...");
					if (!Tools.Files.writeToFile(PATH + "\\saves\\latest.json",
							Tools.Files.readFromFile("src\\assets\\default.json"))) {
						System.out.println("There was an error initializing the latest save file!");
					} else {
						System.out.println("Initialized!");
					}
				}
				// System.out.println(Tools.Files.readFromFile("src\\assets\\default.json"));
			}
			
			loadSaveWithErrorCheck("latest");
			System.out.println("");
			
			if (!Tools.Files.readFromFile(PATH + "\\version.txt").equals(VERSION)) {
				System.out.println("Welcome to the new version of Blackjack!");
				if (Tools.Console.askBoolean("Would you like to read the patch notes?", true)) {
					printPatchNotes(Tools.Files.readFromFile(PATH + "\\version.txt").equals("") ? "1.0"
							: Tools.Files.readFromFile(PATH + "\\version.txt"));
				}
			}
			Tools.Files.writeToFile(PATH + "\\version.txt", VERSION);
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
					add("bet reset");
					add("save latest");
					add("auto save enable");
					add("auto save disable");
					add("load latest");
					add("save as");
					add("load file");
					add("delete save");
					add("deck edit");
					add("set deck");
					add("deck standard");
					add("restore defaults");
					add("patch notes");
				}
			};

			loop: while (true) {
				game.setMaxHits(maxHits);
				if (autoSave) {
					System.out.println("Auto save is on! Saving to \"latest.json\"...");
					saveTo("latest", getCurrentSave());
				}

				for (CardPlayer i : game.getPlayers()) {
					if (Math.abs(i.getMoney()) < 0.01) {
						System.out.println(i.toString() + " has gone bankrupt!");
					} else if (i.getMoney() < 0) {
						System.out.println(i.toString() + " has gone $" + Math.abs(i.getMoney()) + " into debt!");
					}
				}

				String choice = Tools.Console
						.askSelection("Command Choices", choices, true,
								"What would you like to do (\"help\" for choices)?", null, true, false, false)
						.toLowerCase();
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
					System.out.println("properties - edit some global propeerties of the game.");
					System.out.println("rules - read the rules again.");
					System.out.println("help - show this list.");
					System.out.println("quit - end the program.");
					System.out.println("bet reset - Resets all players' bets.");
					System.out.println("save latest - save the current data to the latest save.");
					System.out.println(
							"auto save enable - enable auto save. The computer will save after every change made.");
					System.out.println("auto save disable - disable auto save. You will need to save manually.");
					System.out.println(
							"save as - save as a new save file that you can restore from with the load command");
					System.out.println("load file - load from a save file you have created");
					System.out.println("delete save - delete a save from the file system");
					System.out.println(
							"deck edit - create deck presets and edit the deck that will be used during the game.");
					System.out.println("set deck - set the current deck to be used. Must have length of at least 10.");
					System.out.println("deck standard - load the standard deck.");
					System.out.println(
							"restore defaults - will delete the latest save file and restore default settings.");
					System.out.println("patch notes - view the patch notes of any specific version of Blackjack.");

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
				case "bet reset":
					for (CardPlayer i : game.getPlayers()) {
						i.setBet(0.0);
						System.out.println("Reset " + i.toString() + "'s bet!");
					}
					break;
				case "auto save enable":
					autoSave = true;
					System.out.println("Auto save has been enabled!");
					break;
				case "auto save disable":
					autoSave = false;
					System.out.println("Auto save has been disabled!");
					break;
				case "save latest":
					Tools.Files.writeToFile(PATH + "\\saves\\latest.json", getCurrentSave().toJSONString());
					System.out.println("Saved the current data to latest.json");
					break;
				case "load latest":
					loadSaveWithErrorCheck("latest");
					break;
				case "save as":
					if (Tools.Console.askBoolean("Would you like to view the current saves?", true)) {
						Tools.Console.printList(Tools.Files.getFilesInFolder(PATH + "\\saves", "json"));
					}
					String save1 = Tools.Console.ask("What save do you want to save to (does not have to exist)?");
					if (save1 != null) {
						saveTo(save1, getCurrentSave());
					}

					break;
				case "load file":
					loadSaveWithErrorCheck(
							Tools.Console.askSelection("Saves", Tools.Files.getFilesInFolder(PATH + "\\saves", "json"),
									true, "Choose a save file to load from (or the index off that save file)", "CANCEL",
									true, true, true));
					break;
				case "delete save":
					String save3 = Tools.Console.askSelection("Save files",
							Tools.Files.getFilesInFolder(PATH + "\\saves", "json"), true,
							"Choose a save file to delete", "CANCEL", true, true, true);
					if (save3 != null) {
						if (Tools.Console.askBoolean(
								"This cannot be undone! Would you still like to delete the save file?", true)) {
							Tools.Files.deleteFile(new File(PATH + "\\saves\\" + save3 + ".json"));
						}
					}
					break;
				case "deck edit":
					deckEdit();
					break;
				case "set deck":
					String choice2 = Tools.Console.askSelection("Decks", new ArrayList<String>(decks.keySet()), true,
							"Choose the deck to use.", "CANCEL", true, true, true);
					currentDeck = choice2;
					deck = decks.get(choice2);
					System.out.println("Successfully changed the deck.");
					break;
				case "deck standard":
					currentDeck = "standard";
					deck = Deck.STANDARD_52;
					break;
				case "restore defaults":
					System.out.println("All of your saves will be kept except the latest one.");
					if (Tools.Console.askBoolean("This cannot be undone! Would you still like to restore defaults?",
							true)) {
						Tools.Files.deleteFile(new File(PATH + "\\saves\\latest.json"));
						System.out.println("A game restart is required.");
						System.out.println("Goodbye.");
						break loop;
					}
					break;
					
				case "patch notes":
					System.out.println("Versions are in order from earliest to latest.");
					String v = Tools.Console.askSelection("Versions", versionCodes, true, "Pick a version tom view patch notes for.", "CANCEL", true, true, true, false);
					if (v != null) {
						System.out.println("v" + v + ":");
						for (String i : patchNotes[versionCodes.indexOf(v)]) {
							System.out.println("- " + i);
						}
					}
				}
				System.out.println("");
			}
		}
	}

}
