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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Many Java methods that could be useful in various situations.
 * 
 * @author Ptolemy2002
 * @version 1.2.6
 */
public class Tools {

	/**
	 * A collection of single method interfaces that are able to be used as lambdas
	 */
	public static class Lambdas {
		public static interface IntegerConstraint {
			public boolean allowed(Integer x);
		}

		public static interface DoubleConstraint {
			public boolean allowed(Double x);
		}
	}

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
		 * Ask a question for the user to type an answer to. If the ansswer is not a
		 * double (or constraints are not met), one of 2 things will happen. 1) return
		 * null 2) If goOn is true, will ask again and notify user.
		 * 
		 * @param question    the question to ask
		 * @param goOn        whether to continue asking until a valid answer is given.
		 * @param constraints The constraints used to tell whether the input is allowed.
		 * @return the double the user has given or null if answer is invalid and goOn
		 *         is false
		 **/
		public static Double askDouble(String question, boolean goOn, Lambdas.DoubleConstraint constraints) {
			System.out.print(question + " ");

			try {
				Double result = reader.nextDouble();
				reader.nextLine();

				if (constraints.allowed(result)) {
					return result;
				} else {
					System.out.println("Invalid input!");
					if (goOn) {
						return askDouble(question, goOn, constraints);
					} else {
						return null;
					}
				}
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
		 * Ask a question for the user to type an answer to. If the ansswer is not a
		 * double (or constraints are not met), one of 2 things will happen. 1) return
		 * null 2) If goOn is true, will ask again and notify user.
		 * 
		 * @param question    the question to ask
		 * @param goOn        whether to continue asking until a valid answer is given.
		 * @param constraints The constraints used to tell whether the input is allowed.
		 * @param description A user readable description of constraints. Will be shown
		 *                    if the user gives an input that does not follow the
		 *                    constraints.
		 * @return the double the user has given or null if answer is invalid and goOn
		 *         is false
		 **/
		public static Double askDouble(String question, boolean goOn, Lambdas.DoubleConstraint constraints,
				String description) {
			System.out.print(question + " ");

			try {
				Double result = reader.nextDouble();
				reader.nextLine();

				if (constraints.allowed(result)) {
					return result;
				} else {
					System.out.println("Invalid input! " + description);
					if (goOn) {
						return askDouble(question, goOn, constraints);
					} else {
						return null;
					}
				}
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
		 * Ask a question for the user to type an answer to. If the answer is not an int
		 * (or constraints are not met), one of 2 things will happen. 1) return null 2)
		 * If goOn is true, will ask again and notify user.
		 * 
		 * @param question    the question to ask
		 * @param goOn        whether to continue asking until a valid answer is given.
		 * @param constraints The constraints used to tell whether the input is allowed.
		 * @return the integer the user has given or null if answer is invalid and goOn
		 *         is false
		 **/
		public static Integer askInt(String question, boolean goOn, Lambdas.IntegerConstraint constraints) {
			System.out.print(question + " ");

			try {
				Integer result = reader.nextInt();
				reader.nextLine();

				if (constraints.allowed(result)) {
					return result;
				} else {
					System.out.println("Invalid input!");
					if (goOn) {
						return askInt(question, goOn, constraints);
					} else {
						return null;
					}
				}

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
		 * Ask a question for the user to type an answer to. If the ansswer is not an
		 * int (or constraints are not met), one of 2 things will happen. 1) return null
		 * 2) If goOn is true, will ask again and notify user.
		 * 
		 * @param question    the question to ask
		 * @param goOn        whether to continue asking until a valid answer is given.
		 * @param constraints The constraints used to tell whether the input is allowed.
		 * @param description A user readable description of constraints. Will be shown
		 *                    if the user gives an input that does not follow the
		 *                    constraints.
		 * @return the integer the user has given or null if answer is invalid and goOn
		 *         is false
		 **/
		public static Integer askInt(String question, boolean goOn, Lambdas.IntegerConstraint constraints,
				String description) {
			System.out.print(question + " ");

			try {
				Integer result = reader.nextInt();
				reader.nextLine();

				if (constraints.allowed(result)) {
					return result;
				} else {
					System.out.println("Invalid input! " + description);
					return askInt(question, goOn, constraints);
				}

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
					return askBoolean(question, true);
				} else {
					return null;
				}
			}
		}

		/**
		 * Print the list to the console in a user-friendly way.
		 * 
		 * @param name      the human readable name of the list
		 * @param list      the list to print
		 * @param showIndex whether to show the index of the item
		 */
		public static <T> void printList(String name, List<T> list, boolean showIndex) {
			System.out.println(name + ":");

			for (int i = 0; i < list.size(); i++) {
				if (showIndex) {
					System.out.println((i + 1) + ") " + list.get(i).toString());
				} else {
					System.out.println(list.get(i).toString());
				}
			}
		}

		/**
		 * Print the list to the console in a user-friendly way.
		 * 
		 * @param list      the list to print
		 * @param showIndex whether to show the index of the item
		 */
		public static <T> void printList(List<T> list, boolean showIndex) {
			for (int i = 0; i < list.size(); i++) {
				if (showIndex) {
					System.out.println((i + 1) + ") " + list.get(i).toString());
				} else {
					System.out.println(list.get(i).toString());
				}
			}
		}

		/**
		 * Print the list to the console in a user-friendly way.
		 * 
		 * @param list      the list to print
		 * @param showIndex whether to show the index of the item
		 */
		public static <T> void printList(List<T> list) {
			printList(list, false);
		}

		/**
		 * Ask the user to select from the list. If the answer is not in the list or an
		 * index in the list, one of 2 things will happen. 1) return null 2) If goOn is
		 * true, will ask again and notify user.
		 * 
		 * 
		 * @param name         The human readable name of the list.
		 * @param list         the list to choose from
		 * @param goOn         whether to continue asking until a valid answer is given.
		 * @param instructions the instructions to give the player when picking an item.
		 * @param cancelString the string used to cancel. If null, an answer is
		 *                     required.
		 * @param smart        defines whether to test if user input is valid smartly
		 *                     (ignore case, user musn't say all of it.)
		 * @param acceptIndex  whether to accept the index of an item as input. If
		 *                     false, will also not show the index when printing list.
		 * @param askShow      whether to ask the user to show the list.
		 * @return the item the user has picked or null if the user cancelled or a valid
		 *         name was not given and goOn was false.
		 **/
		public static <T> T askSelection(String name, List<T> list, boolean goOn, String instructions,
				String cancelString, boolean smart, boolean acceptIndex, boolean askShow) {
			List<String> newList = new ArrayList<>();

			for (T i : list) {
				newList.add(i.toString());
			}
			if (askShow) {
				if (askBoolean("Would you like to show the list '" + name + "'?", true)) {
					printList(name, newList, acceptIndex);
				}
			}

			if (cancelString != null) {
				System.out.println("Type \"" + cancelString + "\" to cancel.");
			}

			String choice = ask(instructions);

			while (true) {
				if (cancelString != null && choice.equalsIgnoreCase(cancelString)) {
					return null;
				}
				
				ArrayList<String> matches = smartMatches(newList, choice);
				int count = matches.size();
				if ((smart && count == 1) || (!(smart) && newList.contains(choice))) {
					if (smart) {
						try {
							if (!acceptIndex)
								throw new NumberFormatException();
							int indexChoice = Integer.parseInt(choice);
							if (indexChoice <= newList.size() && indexChoice >= 1) {
								if (Console.askBoolean("Did you mean index " + indexChoice + "?", true)) {
									return list.get(indexChoice - 1);
								} else {
									throw new NumberFormatException();
								}
							} else {
								throw new NumberFormatException();
							}
						} catch (NumberFormatException | IndexOutOfBoundsException e) {
							int index = newList.indexOf(matches.get(0));
							String res = newList.get(index);
							if (res.equalsIgnoreCase(choice)) {
								return list.get(index);
							}
							
							if (Console.askBoolean("Did you mean \"" + res + "\"?", true)) {
								return list.get(index);
							} else {
								choice = ask(instructions);
							}
							
						}
					} else {
						return list.get(newList.indexOf(choice));
					}
				} else if ((smart && smartCount(newList, choice) != 0)) {
					if (goOn) {
						for (String i : matches) {
							if (Console.askBoolean("Did you mean \"" + i + "\"?", true)) {
								return list.get(newList.indexOf(i));
							}
						}

						try {
							if (!acceptIndex)
								throw new NumberFormatException();
							int indexChoice = Integer.parseInt(choice);
							if (indexChoice <= newList.size() && indexChoice >= 1) {
								if (Console.askBoolean("Did you mean index " + indexChoice + "?", true)) {
									return list.get(indexChoice - 1);
								} else {
									throw new NumberFormatException();
								}
							} else {
								throw new NumberFormatException();
							}
						} catch (NumberFormatException | IndexOutOfBoundsException e) {
							System.out.println("Ambiguous input!");
							choice = ask(instructions);
						}
					} else {
						System.out.println("Ambiguous input!");
						return null;
					}
				} else {
					try {
						if (!acceptIndex)
							throw new NumberFormatException();
						int indexChoice = Integer.parseInt(choice);
						if (indexChoice <= newList.size() && indexChoice >= 1) {
							if (Console.askBoolean("Did you mean index " + indexChoice + "?", true)) {
								return list.get(indexChoice - 1);
							} else {
								throw new NumberFormatException();
							}
						} else {
							throw new NumberFormatException();
						}
					} catch (NumberFormatException | IndexOutOfBoundsException e) {
						if (goOn) {
							System.out.println(acceptIndex
									? "Invalid item! must be inside list or an index of list \"" + name + "\""
									: "Invalid item! must be inside list \"" + name + "\"");
							choice = ask(instructions);
						} else {
							System.out.println(acceptIndex
									? "Invalid item! must be inside list or an index of list \"" + name + "\""
									: "Invalid item! must be inside list \"" + name + "\"");
							return null;
						}
					}
				}

			}

		}

		/**
		 * Ask the user to select from the list. If the answer is not in the list or an
		 * index in the list, one of 2 things will happen. 1) return null 2) If goOn is
		 * true, will ask again and notify user.
		 * 
		 * 
		 * @param name         The human readable name of the list.
		 * @param list         the list to choose from
		 * @param goOn         whether to continue asking until a valid answer is given.
		 * @param cancelString the string used to cancel. If null, an answer is
		 *                     required.
		 * @param smart        defines whether to test if user input is valid smartly
		 *                     (ignore case, user musn't say all of it.)
		 * @param acceptIndex  whether to accept the index of an item as input.
		 * @param askShow      whether to ask the user to show the list.
		 * @return the item the user has picked or null if the user cancelled or a valid
		 *         name was not given and goOn was false.
		 **/
		public static <T> T askSelection(String name, List<T> list, boolean goOn, String cancelString, boolean smart,
				boolean acceptIndex, boolean askShow) {
			return askSelection(name, list, goOn,
					acceptIndex ? "Choose an item inside of '" + name + "' (or the index of the item)"
							: "Choose an item inside of '" + name + "'",
					cancelString, smart, acceptIndex, askShow);
		}

		/**
		 * Ask the user to select from the list. If the answer is not in the list or an
		 * index in the list, one of 2 things will happen. 1) return null 2) If goOn is
		 * true, will ask again and notify user.
		 * 
		 * 
		 * @param name  The human readable name of the list.
		 * @param list  the list to choose from
		 * @param goOn  whether to continue asking until a valid answer is given.
		 * @param smart defines whether to test if user input is valid smartly (ignore
		 *              case, user musn't say all of it.)
		 * @return the boolean the user has given or null if answer is invalid and goOn
		 *         is false
		 **/
		public static <T> T askSelection(String name, List<T> list, boolean goOn, boolean smart) {
			return askSelection(name, list, goOn, null, smart, true, true);
		}

		/**
		 * Ask the user to select from the list. If the answer is not in the list or an
		 * index in the list, one of 2 things will happen. 1) return null 2) If goOn is
		 * true, will ask again and notify user.
		 * 
		 * 
		 * @param name         The human readable name of the list.
		 * @param list         the list to choose from
		 * @param goOn         whether to continue asking until a valid answer is given.
		 * @param cancelString the string used to cancel. If null, an answer is
		 *                     required.
		 * @param smart        defines whether to test if user input is valid smartly
		 *                     (ignore case, user musn't say all of it.)
		 * @return the boolean the user has given or null if answer is invalid and goOn
		 *         is false
		 **/
		public static <T> T askSelection(String name, List<T> list, boolean goOn, String cancelString, boolean smart) {
			return askSelection(name, list, goOn, cancelString, smart, true, true);
		}

		/**
		 * Used by smart input methods to test if one string can be resolved using the
		 * given input.
		 * 
		 * Ignores case. Also ignores any punctuation. User must only provide enough input to resolve the item.
		 * If the user provides exactly one of the items (case insensitive), all
		 * ambiguity is ignored.
		 * 
		 * @param s     the string
		 * @param input the input the user gave you.
		 * @return Whether it can be resolved.
		 */
		public static boolean smartEquals(String s, String input) {
			//Remove leading, trailing, and consectutive spaces
			s = s.trim().replaceAll("\\s{2,}", " ");
			input = input.trim().replaceAll("\\s{2,}", " ");
			
			if (input.equalsIgnoreCase(s))
				return true;
			if (s.toLowerCase().startsWith(input.toLowerCase()))
				return true;

			String[] words1 = s.split(" ");
			String[] words2 = input.split(" ");
			if (words2.length > words1.length)
				return false;

			if (words2.length == 1) {
				// Acronym detection
				char[] chars = words2[0].toCharArray();

				if (!(chars.length > words1.length)) {
					int matches2 = 0;
					for (int j = 0; j < chars.length; j++) {
						if (Character.toLowerCase(words1[j].charAt(0)) == Character.toLowerCase(chars[j])) {
							// System.out.println(words1[j + i] + ", " + chars[j]);
							matches2++;
						}
					}
					if (matches2 == chars.length) {
						return true;
					}
				}
			}

			int matches = words1.length - words2.length;
			for (int i = 0; i < words2.length; i++) {
				// System.out.println(words1[i]);
				if (words1[i].toLowerCase().startsWith(words2[i].toLowerCase())) {
					// System.out.println(words1[i] + ", " + words2[i]);
					matches++;
				} else {
					// Acronym detection
					char[] chars = words2[i].toCharArray();

					if (!(chars.length > words1.length - i)) {
						int matches2 = 0;
						for (int j = 0; j < chars.length; j++) {
							if (Character.toLowerCase(words1[j + i].charAt(0)) == Character.toLowerCase(chars[j])) {
								// System.out.println(words1[j + i] + ", " + chars[j]);
								matches2++;
							}
						}
						if (matches2 == chars.length) {
							// System.out.println(matches2);
							i += matches2;
							matches += matches2;
						} else {
							return false;
						}
					}
				}
			}

			// System.out.println(s + ": " + matches);
			if (matches >= words1.length)
				return true;
			
			//Detect the use of punctuation and remove it if found
			if (Pattern.compile("[^\\p{L}0-9 ]").matcher(input).find() || Pattern.compile("[^\\p{L}0-9 ]").matcher(s).find()) {
				//Each punctiation will act as a separator for words. So "int(5)" will resolve to "int 5"
				return smartEquals(s.replaceAll("[^\\p{L}0-9 ]", " "), input.replaceAll("[^\\p{L}0-9 ]", " "));
			}

			return false;
		}

		/**
		 * Will test if the user's input can be resolved to any item in the list and
		 * return the amount of items it finds.
		 * 
		 * Ignores case. Also ignores any punctuation. User must only provide enough input to resolve the item.
		 * If the user provides exactly one of the items (case insensitive), all
		 * ambiguity is ignored.
		 * 
		 * @param list  the list of possible outcomes
		 * @param input the input of the user
		 * @return the amount of items that can be resolved.
		 */
		public static int smartCount(List<String> list, String input) {
			return smartMatches(list, input).size();
		}

		/**
		 * Will test if the user's input can be resolved to any item in the list and
		 * return the index of the first item it finds.
		 * 
		 * Ignores case. Also ignores any punctuation. User must only provide enough input to resolve the item.
		 * If the user provides exactly one of the items (case insensitive), all
		 * ambiguity is ignored.
		 * 
		 * @param list  the list of possible outcomes
		 * @param input the input of the user
		 * @return the index of the first item found.
		 */
		public static int smartIndex(List<String> list, String input) {
			ArrayList<String> res = smartMatches(list, input);
			if (res.size() >= 1) {
				return list.indexOf(res.get(0));
			} else {
				return -1;
			}
		}

		/**
		 * Will test if the user's input can be resolved to any item in the list and
		 * return everything it finds.
		 * 
		 * Ignores case. Also ignores any punctuation. User must only provide enough input to resolve the item.
		 * If the user provides exactly one of the items (case insensitive), all
		 * ambiguity is ignored.
		 * 
		 * @param list  the list of possible outcomes
		 * @param input the input of the user
		 * @return all the matches found in the list in item 1
		 */
		public static ArrayList<String> smartMatches(List<String> list, String input) {
			ArrayList<String> res = new ArrayList<>();
			if (list.size() == 0)
				return res;

			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equalsIgnoreCase(input)) {
					res.add(list.get(i));
					return res;
				}
			}

			for (int i = 0; i < list.size(); i++) {
				if (smartEquals(list.get(i), input)) {
					res.add(list.get(i));
				}
			}

			return res;
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