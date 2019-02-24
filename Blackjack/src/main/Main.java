package main;

import java.io.File;
import java.util.ArrayList;

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

	public static Double minBet = 2.0;
	public static Double maxBet = 500.0;
	public static Double minAIBet = 2.0;
	public static Double maxAIBet = 500.0;
	public static Integer maxHits = Integer.MAX_VALUE;
	public static boolean autoSave = true;
	public static final boolean DEBUG_MODE = true;

	public static BlackjackGame game;
	public static final String PATH = Tools.Variables.getAppdata() + "\\Ptolemy's code\\Blackjack";

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

	public static void loadSave(JSONObject save) {
		game.setMaxHits(((Long) save.get("maxHits")).intValue());
		autoSave = (Boolean) save.get("autoSave");
		minBet = (Double) save.get("minBet");
		maxBet = (Double) save.get("maxBet");
		minAIBet = (Double) save.get("minAIBet");
		maxAIBet = (Double) save.get("maxAIBet");

		JSONArray players = (JSONArray) save.get("players");
		for (Object i : players) {
			JSONObject data = (JSONObject) i;
			game.addNewPlayer((Boolean) data.get("ai")).setName((String) data.get("name"))
					.setMoney((Double) data.get("money")).setBet((Double) data.get("bet"));
		}
	}

	public static void saveTo(String save, String value) {
		if (!Tools.Files.writeToFile(PATH + "\\saves\\" + save + ".json", value)) {
			System.out.println("There was an error saving to the save \"" + save + "\"");
		} else {
			System.out.println("Successfully saved to the save file \"" + save + "\"");
		}
	}

	public static void saveToDefault() {
		if (!DEBUG_MODE) {
			if (!Tools.Files.writeToFile(PATH + "\\saves\\latest.json",
					Tools.Files.getResource("/files/default.json", Main.class))) {
				System.out.println("There was an error writing to the latest save file!");
			}
		} else {
			if (!Tools.Files.writeToFile(PATH + "\\saves\\latest.json",
					Tools.Files.readFromFile("src\\assets\\default.json"))) {
				System.out.println("There was an error writing to the latest save file!");
			}
			// System.out.println(Tools.Files.readFromFile("src\\assets\\default.json"));
		}
	}

	public static void saveToDefault(String save) {
		if (!DEBUG_MODE) {
			if (!Tools.Files.writeToFile(PATH + "\\saves\\" + save + ".json",
					Tools.Files.getResource("/files/default.json", Main.class))) {
				System.out.println("There was an error writing to the latest save file!");
			}
		} else {
			if (!Tools.Files.writeToFile(PATH + "\\saves\\" + save + ".json",
					Tools.Files.readFromFile("src\\assets\\default.json"))) {
				System.out.println("There was an error writing to the latest save file!");
			}
			// System.out.println(Tools.Files.readFromFile("src\\assets\\default.json"));
		}
	}

	public static JSONObject getCurrentSave() {
		JSONObject res = new JSONObject();
		res.put("autoSave", autoSave);
		res.put("maxHits", maxHits);
		res.put("minBet", minBet);
		res.put("maxBet", maxBet);
		res.put("minAIBet", minAIBet);
		res.put("maxAIBet", maxAIBet);
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

	public static void main(String[] args) {
		game = new BlackjackGame(new Deck());

		if (!DEBUG_MODE) {
			if (!Tools.Files.fileExists(PATH + "\\saves\\latest.json")) {
				System.out.println("The latest save file does not yet exist.");
				System.out.println("Initializing it...");
				if (!Tools.Files.writeToFile(PATH + "\\saves\\latest.json",
						Tools.Files.getResource("/files/default.json", Main.class))) {
					System.out.println("There was an error initializing the latest save file!");
				}
			}
		} else {
			if (!Tools.Files.fileExists(PATH + "\\saves\\latest.json")) {
				System.out.println("The latest save file does not yet exist.");
				System.out.println("Initializing it...");
				if (!Tools.Files.writeToFile(PATH + "\\saves\\latest.json",
						Tools.Files.readFromFile("src\\assets\\default.json"))) {
					System.out.println("There was an error initializing the latest save file!");
				}
			}
			// System.out.println(Tools.Files.readFromFile("src\\assets\\default.json"));
		}

		JSONObject latestSave = null;
		try {
			latestSave = (JSONObject) new JSONParser().parse(Tools.Files.readFromFile(PATH + "\\saves\\latest.json"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (latestSave == null) {
			System.out.println("There was an error interpreting the latest save file!");

			if (Tools.Console.askBoolean("Would you like to load the default save (you will lose data)?", true)) {
				System.out.println("Loading defaults...");
				saveToDefault();
			}
		} else {
			try {
				loadSave(latestSave);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("The latest save file is corrupted!");
				if (Tools.Console.askBoolean("Would you like to load the default save (you will lose data)?", true)) {
					saveToDefault();
				}
			}
		}

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
			}
		};

		loop: while (true) {
			game.setMaxHits(maxHits);
			if (autoSave) {
				System.out.println("Auto save is on! Saving to \"latest.json\"...");
				saveTo("latest", getCurrentSave().toJSONString());
			}
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
				System.out.println("properties - edit some global propeerties of the game.");
				System.out.println("rules - read the rules again.");
				System.out.println("help - show this list.");
				System.out.println("quit - end the program.");
				System.out.println("bet reset - Resets all players' bets.");
				System.out.println("save latest - save the current data to the latest save.");
				System.out.println(
						"auto save enable - enable auto save. The computer will save after every change made.");
				System.out.println("auto save disable - disable auto save. You will need to save manually.");
				System.out.println("save as - save as a new save file that you can restore from with the load command");
				System.out.println("load file - load from a save file you have created");
				System.out.println("delete save - delete a save from the file system");

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
				JSONObject save = null;
				try {
					save = (JSONObject) new JSONParser().parse(Tools.Files.readFromFile(PATH + "\\saves\\latest.json"));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				if (save == null) {
					System.out.println("There was an error interpreting the latest save file!");

					if (Tools.Console.askBoolean("Would you like to load the default save (you will lose data)?",
							true)) {
						System.out.println("Loading defaults...");
						saveToDefault();
					}
				} else {
					try {
						loadSave(latestSave);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("The latest save file is corrupted!");
						if (Tools.Console.askBoolean("Would you like to load the default save (you will lose data)?",
								true)) {
							System.out.println("Loading defaults...");
							if (!DEBUG_MODE) {
								if (!Tools.Files.writeToFile(PATH + "\\saves\\latest.json",
										Tools.Files.getResource("/files/default.json", Main.class))) {
									System.out.println("There was an error writing to the latest save file!");
								}
							} else {
								if (!Tools.Files.writeToFile(PATH + "\\saves\\latest.json",
										Tools.Files.readFromFile("src\\assets\\default.json"))) {
									System.out.println("There was an error writing to the latest save file!");
								}
								// System.out.println(Tools.Files.readFromFile("src\\assets\\default.json"));
							}
						}
					}
				}
				System.out.println("Loaded the current data from latest.json");
				break;
			case "save as":
				if (Tools.Console.askBoolean("Would you like to view the current saves?", true)) {
					Tools.Console.printList(Tools.Files.getFilesInFolder(PATH + "\\saves", "json"));
				}
				String save1 = Tools.Console.ask("What save do you want to save to (does not have to exist)?");
				if (save1 != null) {
					saveTo(save1, getCurrentSave().toJSONString());
				}

				break;
			case "load file":
				String saveChoice = Tools.Console.askSelection("Saves",
						Tools.Files.getFilesInFolder(PATH + "\\saves", "json"), true,
						"Choose a save file to load from (or the index off that save file)", "CANCEL", true, true,
						true);

				JSONObject save2 = null;
				try {
					save2 = (JSONObject) new JSONParser()
							.parse(Tools.Files.readFromFile(PATH + "\\saves\\" + saveChoice + ".json"));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				if (save2 == null) {
					System.out.println("There was an error interpreting the save file!");

					if (Tools.Console.askBoolean("Would you like to load the default save (you will lose data)?",
							true)) {
						System.out.println("Loading defaults...");
						saveToDefault(saveChoice);
					}
				} else {
					try {
						loadSave(save2);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("The save file is corrupted!");
						if (Tools.Console.askBoolean("Would you like to load the default save (you will lose data)?",
								true)) {
							System.out.println("Loading defaults...");
							if (!DEBUG_MODE) {
								if (!Tools.Files.writeToFile(PATH + "\\saves\\" + save2 + ".json",
										Tools.Files.getResource("/files/default.json", Main.class))) {
									System.out.println("There was an error writing to the save file!");
								}
							} else {
								if (!Tools.Files.writeToFile(PATH + "\\saves\\" + save2 + ".json",
										Tools.Files.readFromFile("src\\assets\\default.json"))) {
									System.out.println("There was an error writing to the save file!");
								}
								// System.out.println(Tools.Files.readFromFile("src\\assets\\default.json"));
							}
						}
					}
				}
				System.out.println("Loaded the current data from \"" + saveChoice + ".json\"");
				break;
			case "delete save":
				String save3 = Tools.Console.askSelection("Save files",
						Tools.Files.getFilesInFolder(PATH + "\\saves", "json"), true, "Choose a save file to delete",
						"CANCEL", true, true, true);
				if (save3 != null) {
					if (Tools.Console.askBoolean("This cannot be undone! Would you still like to delete the save file?",
							true)) {
						Tools.Files.deleteFile(new File(PATH + "\\saves\\" + save3 + ".json"));
					}
				}
				break;
			}
			System.out.println("");
		}
	}

}
