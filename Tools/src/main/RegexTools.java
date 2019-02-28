package main;

import java.util.List;

/**
 * Some handy regular expression tools
 */
public class RegexTools {
	public static final String WHITESPACE = "\\s";
	public static final String BEGINNING_OF_STRING = "^";
	public static final String END_OF_STRING = "$";
	public static final String CHAR = ".";
	public static final String DIGIT = "\\d";
	public static final String NON_DIGIT = "\\D";
	public static final String NON_WHITESPACE = "\\S";
	public static final String WORD_CHAR = "\\w";
	public static final String NON_WORD_CHAR = "\\W";
	public static final String BOUNDARY_CHAR = "\\b";

	/**
	 * Will return a regular expression that will match if any of the specified
	 * expressions are present.
	 * 
	 * @param regex the list of expressions
	 * @return a regular expression that will match any of the specified expressions
	 */
	public static String anyOf(String... regex) {
		String res = "[";
		for (String i : regex) {
			// If it must be grouped and is not already
			if (i.length() > 0 && !(i.charAt(0) == '(' && i.charAt(i.length() - 1) == ')')) {
				res += "(" + i + ")";
			} else {
				res += i;
			}
		}
		res += "]";

		return res;
	}

	/**
	 * Will return a regular expression that will match if any of the specified
	 * expressions are present.
	 * 
	 * @param regex the list of expressions
	 * @return a regular expression that will match any of the specified expressions
	 */
	public static String anyOf(List<String> regex) {
		String res = "[";
		for (String i : regex) {
			// If it must be grouped and is not already
			if (i.length() > 0 && !(i.charAt(0) == '(' && i.charAt(i.length() - 1) == ')')) {
				res += "(" + i + ")";
			} else {
				res += i;
			}
		}
		res += "]";

		return res;
	}

	/**
	 * Will return a regular expression that will match if none of the specified
	 * expressions are present.
	 * 
	 * @param regex the list of expressions
	 * @return a regular expression that will match any expression that is not of
	 *         the specified expressions
	 */
	public static String noneOf(String... regex) {
		String res = "[^";
		for (String i : regex) {
			// If it must be grouped and is not already
			if (i.length() > 0 && !(i.charAt(0) == '(' && i.charAt(i.length() - 1) == ')')) {
				res += "(" + i + ")";
			} else {
				res += i;
			}
		}
		res += "]";

		return res;
	}

	/**
	 * Will return a regular expression that will match if none of the specified
	 * expressions are present.
	 * 
	 * @param regex the list of expressions
	 * @return a regular expression that will match any expression that is not of
	 *         the specified expressions
	 */
	public static String noneOf(List<String> regex) {
		String res = "[^";
		for (String i : regex) {
			// If it must be grouped and is not already
			if (i.length() > 0 && !(i.charAt(0) == '(' && i.charAt(i.length() - 1) == ')')) {
				res += "(" + i + ")";
			} else {
				res += i;
			}
		}
		res += "]";

		return res;
	}

	/**
	 * Will return a regular expression that will match if a number in the specified
	 * range is present
	 * 
	 * @param start the start of the range
	 * @param end   the end of the range
	 * @return a regular expression that will match if a number in the specified
	 *         range is present
	 */
	public static String digitRange(int start, int end) {
		return "(" + start + "-" + end + ")";
	}

	/**
	 * Will return a regular expression that will match 0 or more of the specified
	 * expression
	 * 
	 * @param regex the expression to match
	 * @return a regular expression that will match 0 or more of the specified
	 *         expression
	 */
	public static String anyAmountOf(String regex) {
		if (regex.length() > 1 && !(regex.charAt(0) == '(' && regex.charAt(regex.length() - 1) == ')')) {
			return "((" + regex + ")*)";
		} else if (regex.length() > 0 && regex.charAt(0) == '(' && regex.charAt(regex.length() - 1) == ')') {
			return "(" + regex + "*)";
		} else {
			return "()*";
		}
	}

	/**
	 * Will return a regular expression that will match 1 or more of the specified
	 * expression
	 * 
	 * @param regex the expression to match
	 * @return a regular expression that will match 1 or more of the specified
	 *         expression
	 */
	public static String oneOrMoreOf(String regex) {
		if (regex.length() > 1 && !(regex.charAt(0) == '(' && regex.charAt(regex.length() - 1) == ')')) {
			return "((" + regex + ")+)";
		} else if (regex.length() > 0 && regex.charAt(0) == '(' && regex.charAt(regex.length() - 1) == ')') {
			return "(" + regex + "+)";
		} else {
			return "()+";
		}
	}

	/**
	 * Will return a regular expression that will match 0 or 1 of the specified
	 * expression
	 * 
	 * @param regex the expression to match
	 * @return a regular expression that will match 0 or 1 of the specified
	 *         expression
	 */
	public static String optional(String regex) {
		if (regex.length() > 1 && !(regex.charAt(0) == '(' && regex.charAt(regex.length() - 1) == ')')) {
			return "((" + regex + ")?)";
		} else if (regex.length() > 0 && regex.charAt(0) == '(' && regex.charAt(regex.length() - 1) == ')') {
			return "(" + regex + "?)";
		} else {
			return "()?";
		}
	}

	/**
	 * Will return a regular expression that will match exactly {@code c} of the
	 * specified expression
	 * 
	 * @param regex the expression to match
	 * @param c     the anount of times it should be present
	 * @return a regular expression that will match exactly {@code c} of the
	 *         specified expression
	 */
	public static String count(String regex, int c) {
		if (regex.length() > 1 && !(regex.charAt(0) == '(' && regex.charAt(regex.length() - 1) == ')')) {
			return "((" + regex + "){" + c + "})";
		} else if (regex.length() > 0 && regex.charAt(0) == '(' && regex.charAt(regex.length() - 1) == ')') {
			return "(" + regex + "{" + c + "})";
		} else {
			return "(){" + c + "}";
		}
	}

	/**
	 * Will return a regular expression that will match at least {@code c} of the
	 * specified expression
	 * 
	 * @param regex the expression to match
	 * @param c     the anount of times it should be present
	 * @return a regular expression that will match at least {@code c} of the
	 *         specified expression
	 */
	public static String atLeast(String regex, int c) {
		if (regex.length() > 1 && !(regex.charAt(0) == '(' && regex.charAt(regex.length() - 1) == ')')) {
			return "((" + regex + "){," + c + "})";
		} else if (regex.charAt(0) == '(' && regex.charAt(regex.length() - 1) == ')') {
			return "(" + regex + "{," + c + "})";
		} else if (regex.length() == 1) {
			return regex + "{," + c + "}";
		} else {
			return "(){," + c + "}";
		}
	}

	/**
	 * Will return a regular expression that will match at most {@code c} of the
	 * specified expression
	 * 
	 * @param regex the expression to match
	 * @param c     the anount of times it should be present
	 * @return a regular expression that will match at most {@code c} of the
	 *         specified expression
	 */
	public static String atMost(String regex, int c) {
		if (regex.length() > 1 && !(regex.charAt(0) == '(' && regex.charAt(regex.length() - 1) == ')')) {
			return "((" + regex + "){" + c + ",})";
		} else if (regex.charAt(0) == '(' && regex.charAt(regex.length() - 1) == ')') {
			return "(" + regex + "{" + c + ",})";
		} else if (regex.length() == 1) {
			return regex + "{" + c + ",}";
		} else {
			return "(){" + c + ",}";
		}
	}

	/**
	 * Will return a regular expression that will match in between {@code c} and
	 * {@code c1} of the specified expression
	 * 
	 * @param regex the expression to match
	 * @param c     the anount of times it should be present
	 * @return a regular expression that will match in between {@code c} and
	 *         {@code c1} of the specified expression
	 */
	public static String inBetween(String regex, int c, int c1) {
		if (regex.length() > 1 && !(regex.charAt(0) == '(' && regex.charAt(regex.length() - 1) == ')')) {
			return "((" + regex + "){" + c + "," + c1 + "})";
		} else if (regex.length() > 0 && regex.charAt(0) == '(' && regex.charAt(regex.length() - 1) == ')') {
			return "(" + regex + "{" + c + "," + c1 + "})";
		} else {
			return "(){" + c + "," + c1 + "})";
		}
	}

	/**
	 * Use this method on any raw string so that you don't accidentally create any
	 * regex. DO NOT USE THIS ON A REGEX STRING!!!
	 * 
	 * @param s the string to escape
	 * @return the escaped string
	 */
	public static String escape(String s) {
		return s.replace("\\", "\\\\").replace(".", "\\.").replace("[", "\\[").replace("]", "\\]").replace("{", "\\{")
				.replace("}", "\\}").replace("(", "\\(").replace(")", "\\)").replace("<", "\\<").replace(">", "\\>")
				.replace("*", "\\*").replace("+", "\\+").replace("-", "\\-").replace("=", "\\=").replace("?", "\\?")
				.replace("^", "\\^").replace("$", "\\$").replace("|", "\\|");
	}
}