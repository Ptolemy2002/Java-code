package main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * Many Java methods that could be useful in various situations.
 *
 */
public class Tools {

	/**
	 * Read and write to files
	 */
	public static class Files {

		public static String getNewLineChar() {
			return System.getProperty("line.separator");
		}

		/**
		 * Reads the data from a file path. Use double "\"s for file path (single "/" on
		 * linux)
		 * 
		 * Will create the path and file if it doesn't exist.
		 * 
		 * @param filePath
		 * @return the data present in the file. Will return an empty string if the file
		 *         was created or "null" if an IOException happens.
		 */
		public static String readFromFile(String filePath) {
			try {
				String result = "";

				File file = new File(filePath);

				file.getParentFile().mkdirs();
				file.createNewFile();

				BufferedReader br = new BufferedReader(new FileReader(file));

				String st;
				while ((st = br.readLine()) != null) {
					result += st + "\n";
				}

				br.close();

				return result.substring(0, result.length() - 1 <= 0 ? 0 : result.length() - 1);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		/**
		 * Writes data to a file path. Use double "\"s for file path (single "/" on
		 * linux).
		 * 
		 * Will create the path and file if it doesn't exist.
		 * 
		 * @param filePath
		 * @param data
		 */
		public static void writeToFile(String filePath, String data) {
			try {
				data = data.replaceAll("\n", getNewLineChar());

				File file = new File(filePath);

				file.getParentFile().mkdirs();
				file.createNewFile();

				BufferedWriter writer = new BufferedWriter(new FileWriter(file));

				writer.write(data);

				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Anything involving the console, including all the ask methods
	 */
	public static class Console {
		
		private static Scanner reader = new Scanner(new BufferedInputStream(System.in));
		
		/**
		 * Ask a question for the user to type an answer to. If the ansswer is not a
		 * double, one of 2 things will happen. 1) return null 2) If goOn is true, will
		 * ask again and notify user.
		 * 
		 * @param question the question to ask
		 * @param goOn     whether to continue asking until a valid answer is given.
		 * @return the double the user has given or null if answer is invalid and goOn
		 *         is false
		 **/
		public static Double askDouble(String question, boolean goOn) {
			System.out.print(question + " ");

			try {
				Double result = reader.nextDouble();
				reader.nextLine();
				return result;
			} catch (InputMismatchException e) {
				if (goOn) {
					System.out.println("Invalid type! Must be double.");
					// Dismiss the exception
					reader.next();
					return askDouble(question, true);
				} else {
					// Dismiss the exception
					reader.next();
					return null;
				}
			}
		}

		/**
		 * Ask a question for the user to type an answer to. If the ansswer is not an
		 * int, one of 2 things will happen. 1) return null 2) If goOn is true, will ask
		 * again and notify user.
		 * 
		 * @param question the question to ask
		 * @param goOn     whether to continue asking until a valid answer is given.
		 * @return the integer the user has given or null if answer is invalid and goOn
		 *         is false
		 **/
		public static Integer askInt(String question, boolean goOn) {
			System.out.print(question + " ");

			try {
				Integer result = reader.nextInt();
				reader.nextLine();
				return result;
			} catch (InputMismatchException e) {
				if (goOn) {
					System.out.println("Invalid type! Must be int.");
					// Dismiss the exception
					reader.next();
					return askInt(question, true);
				} else {
					// Dismiss the exception
					reader.next();
					return null;
				}
			}

		}

		/**
		 * Ask a question for the user to type an answer to. Answer can be anything and
		 * will be reported as a string
		 * 
		 * @param question the question to ask
		 * @return the string the user has given or null if answer is invalid
		 **/
		public static String ask(String question) {
			System.out.print(question + " ");

			try {
				String result = reader.nextLine();
				return result;
			} catch (InputMismatchException e) {
				// Dismiss the exception
				reader.next();
				return null;
			}

		}

		/**
		 * Ask a question for the user to type an answer to. If the ansswer is not a
		 * boolean, one of 2 things will happen. 1) return null 2) If goOn is true, will
		 * ask again and notify user.
		 * 
		 * This method will accept "yes", "no", "true", or "false" in any case.
		 * 
		 * @param question the question to ask
		 * @param goOn     whether to continue asking until a valid answer is given.
		 * @return the boolean the user has given or null if answer is invalid and goOn
		 *         is false
		 **/
		public static Boolean askBoolean(String question, boolean goOn) {
			System.out.print(question + " ");

			try {
				String result = reader.nextLine();

				if (result.equalsIgnoreCase("true") || result.equalsIgnoreCase("yes")) {
					return true;
				} else if (result.equalsIgnoreCase("false") || result.equalsIgnoreCase("no")) {
					return false;
				} else {
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				if (goOn) {
					System.out.println("Invalid type! Must be boolean.");
					// Dismiss the exception
					reader.next();
					return askBoolean(question, true);
				} else {
					// Dismiss the exception
					reader.next();
					return null;
				}
			}

		}

		/**
		 * Print the list to the console in a user-friendly way.
		 * 
		 * @param name the human readable name of the list
		 * @param list the list to print
		 */
		public static void printList(String name, List<Object> list) {
			System.out.println(name + ":");

			for (int i = 0; i < list.size(); i++) {
				System.out.println((i + 1) + ") " + list.get(i).toString());
			}
		}

		/**
		 * Ask the user to select from the list. If the answer is not in the list or an
		 * index in the list, one of 2 things will happen. 1) return null 2) If goOn is
		 * true, will ask again and notify user.
		 * 
		 * 
		 * @param name The human readable name of the list.
		 * @param list the list to choose from
		 * @param goOn whether to continue asking until a valid answer is given.
		 * @param cancelString 
		 * @return the boolean the user has given or null if answer is invalid and goOn
		 *         is false
		 **/
		public static Object askSelection(String name, List<Object> list, boolean goOn, String cancelString) {
			List<Object> newList = new ArrayList<Object>();

			for (Object i : list) {
				if (i instanceof Integer) {
					newList.add("int(" + i.toString() + ")");
				} else {
					newList.add(i.toString());
				}
			}

			if (askBoolean("Would you like to show the list '" + name + "'?", true)) {
				printList(name, newList);
			}
			
			System.out.println("Type '" + cancelString + "' to cancel.");
			String choice = ask("Choose an item in '" + name + "' (or the index of that item)");

			while (true) {
				if (choice.equalsIgnoreCase(cancelString)) {
					return null;
				}
				
				if (newList.contains(choice)) {
					return list.get(newList.indexOf(choice));
				}

				try {
					int indexChoice = Integer.parseInt(choice);
					return list.get(indexChoice - 1);
				} catch (NumberFormatException | IndexOutOfBoundsException e) {
					if (goOn) {
						System.out.println("Invalid item! must be inside list or an index of list.");
						choice = ask("Choose an item in '" + name + "' (or the index of that item)");
					} else {
						return null;
					}
				}
			}

		}
	}

	/**
	 * Rounding, random, and other number operations
	 */
	public static class Numbers {

		/**
		 * 
		 * Generates a random double between the minimum and maximum. Use the randomInt
		 * method for decimals.
		 * 
		 * @param min the minumum number to generate
		 * @param max the maximum number to generate
		 * @return a random double between min and max
		 */
		public static double randomDouble(double min, double max) {
			if (max < min) {
				throw new IllegalArgumentException("max must be less than min.");
			}

			double result = Math.random() * (max - min) + min;
			if (result < min || result > max) {
				throw new Error("Result was out of bounds when generating random double with min: " + min + " max "
						+ max + ". generated " + result);
			} else {
				return result;
			}
		}

		/**
		 * 
		 * Generates a random integer between the minimum and maximum. Use the
		 * randomDouble method for decimals.
		 * 
		 * @param min the minimum number to generate
		 * @param max the maximum number to generate
		 * @return a random integer between min and max
		 */
		public static int randomInt(int min, int max) {
			if (max < min) {
				throw new IllegalArgumentException("max must be greater than min.");
			}

			int result = doubleToInt(randomDouble((double) min, ((double) max) + 0.99));

			if (result < min || result > max) {
				throw new Error("Result was out of bounds when generating random int with min: " + min + " max " + max
						+ ". generated " + result);
			} else {
				return result;
			}
		}

		/**
		 * 
		 * Rounds your specified double to the amount of decimal places you specify. 1
		 * decimal place will round to the nearest 10th, two to the nearest hundredth,
		 * and so on. Use roundFloat for floats.
		 * 
		 * @param number        the number to round
		 * @param decimalPlaces the amount of decimal places to round the number to (0
		 *                      for none)
		 * @return the double rounded to the specified amount of decimal places.
		 */
		public static double roundDouble(double number, double decimalPlaces) {
			return (double) ((double) Math.round(number * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces));
		}

		/**
		 * 
		 * Rounds your specified float to the amount of decimal places you specify. 1
		 * decimal place will round to the nearest 10th, two to the nearest hundredth,
		 * and so on. Use roundDouble for doubles.
		 * 
		 * @param number        the number to round
		 * @param decimalPlaces the amount of decimal places to round the number to (0
		 *                      for none)
		 * @return the float rounded to the specified amount of decimal places.
		 */
		public static float roundFloat(float number, float decimalPlaces) {
			return (float) ((float) Math.round(number * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces));
		}

		/**
		 * Gets the 3 dimensional distance to the specified point.
		 * 
		 * @param x1 begin x position
		 * @param y1 begin y position
		 * @param z1 begin z position
		 * @param x2 end x position
		 * @param y2 end y position
		 * @param z2 end z position
		 * @return the distance between the 2 sets of points
		 */
		public static double distanceTo(double x1, double y1, double z1, double x2, double y2, double z2) {
			return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
		}

		/**
		 * Gets the 2 dimensional distance to the specified point.
		 * 
		 * @param x1 begin x position
		 * @param y1 begin y position
		 * @param x2 end x position
		 * @param y2 end y position
		 * @return the distance between the 2 sets of points
		 */
		public static double distanceTo(double x1, double y1, double x2, double y2) {
			return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
		}

		/**
		 * Workaround for casting a double to an int. Will floor the double then cast it
		 * integer.
		 * 
		 * @param dub the double to cast
		 */
		public static int doubleToInt(Double dub) {
			return (int) Math.floor(dub);
		}

		/**
		 * Convert the specified amount of items from the specified currency into an
		 * expression of singular items.
		 * 
		 * @param currency the set of values Must be a HashMap with each item and the
		 *                 corresponding value in double form. For example, {"$1 bill":
		 *                 1.0, "Penny": 0.01}
		 * @param count    the amount of each item in the currency you want to convert.
		 *                 An unspecified item will be assumed 0.
		 * @param base     the unit that the result will be in. For example, if you want
		 *                 the result in 2 dollar bills instead of 1 dollar bills, set
		 *                 this to 2.0. If less than or equal to 0, will be assumed 1.
		 */
		public static Double toValue(HashMap<String, Double> currency, HashMap<String, Double> count, Double base) {
			BigDecimal result = BigDecimal.valueOf(0);

			for (String i : count.keySet()) {
				// Remainder will not be specified in the currency
				if (!i.equals("$Remainder")) {
					result = result.add(BigDecimal.valueOf(Math.abs(currency.get(i)) * count.get(i)));
				}
			}

			// Add the remainder to the end
			if (count.containsKey("$Remainder")) {
				result = result.add(BigDecimal.valueOf(count.get("$Remainder")));
			}

			return result
					.divide(BigDecimal.valueOf(base).compareTo(BigDecimal.valueOf(0)) == 1 ? BigDecimal.valueOf(base)
							: BigDecimal.valueOf(1))
					.doubleValue();
		}

		/**
		 * Convert the specified value in "startBase" to it's equivalent in "endBase"
		 * given the Currency
		 * 
		 * @param value     the value to convert
		 * @param startBase the base the value is currently at
		 * @param endBase   the base to end at.
		 */
		public static Double baseToBase(Double value, Double startBase, Double endBase) {
			return value * (startBase / endBase);
		}

		/**
		 * Converts the specified value into your specified set of group values. For
		 * example, this method could convert inches into feet, yards, etc. It could
		 * also do money conversions.
		 * 
		 * @param value     the value to convert
		 * @param currency  the set of values Must be a HashMap with each item and the
		 *                  corresponding value in double form. For example, {"$1 bill":
		 *                  1.0, "Penny": 0.01}. Will take the absolute value of the
		 *                  number
		 * @param inventory An optional parameter specifying the maximum amount of each
		 *                  item can be used in the result. Must be HashMap with each
		 *                  item and the maximum count in Integer form. If an item is
		 *                  not specified, count will be assumed Infinity.
		 * @param base      allows you to edit the base of the currency without changing
		 *                  the currency itself. For example, if your value is in $2
		 *                  bills instead of $1 bills, set base to 2.0. If less than or
		 *                  equal to 0, will be assumed 1.
		 */
		public static LinkedHashMap<String, Double> toCurrency(Double value, HashMap<String, Double> currency,
				HashMap<String, Integer> inventory, Double base) {
			if (inventory == null) {
				inventory = new HashMap<String, Integer>();
			}

			// Scale up value
			// Use BigDecimal so that we can work with exact values. Counts are converted to
			// double at the end.
			BigDecimal workingValue = BigDecimal.valueOf(base).compareTo(BigDecimal.valueOf(0)) == 1
					? BigDecimal.valueOf(base).multiply(BigDecimal.valueOf(value))
					: BigDecimal.valueOf(value);

			LinkedHashMap<String, Double> result = new LinkedHashMap<String, Double>();

			/* Sort the currency by value */

			// Cast set to a list
			List<Entry<String, Double>> sortedList = new ArrayList<Entry<String, Double>>(currency.entrySet());

			// Use comparator to sort by value in the map.
			Collections.sort(sortedList, new Comparator<Map.Entry<String, Double>>() {
				public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
					return o2.getValue().compareTo(o1.getValue());
				}
			});

			// System.out.println(sortedList);

			/* Use sorted map for currency calculation */

			for (Entry<String, Double> i : sortedList) {
				Double amount = 0.0;
				// If not specified, assume count to be infinity
				Integer count = inventory.get(i.getKey()) == null ? Integer.MAX_VALUE
						: Math.abs(inventory.get(i.getKey()));
				BigDecimal worth = BigDecimal.valueOf(Math.abs(currency.get(i.getKey())));

				if (workingValue.compareTo(BigDecimal.valueOf(0)) == 1) {
					// For positive numbers
					while (worth.compareTo(workingValue) <= 0 && amount < count) {
						workingValue = workingValue.subtract(worth);
						amount++;
					}
				} else {
					// For negative numbers
					while (worth.multiply(BigDecimal.valueOf(-1)).compareTo(workingValue) >= 0 && amount * -1 < count) {
						workingValue = workingValue.add(worth);
						amount--;
					}
				}

				result.put(i.getKey(), amount);
			}

			// How much is left
			result.put("$Remainder", workingValue.doubleValue());

			return result;
		}
	}
}