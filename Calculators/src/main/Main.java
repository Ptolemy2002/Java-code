/**
 * The main class with the main method
 */
package main;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
public class Main {

	/**
	 * Called initially with the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		HashMap<String, Double> timeCurrency = new HashMap<String, Double>();
		timeCurrency.put("second", 1.0);
		timeCurrency.put("millisecond", 0.01);
		timeCurrency.put("microsecond", 1.0E-6);
		timeCurrency.put("nanosecond", 1.0E-9);
		timeCurrency.put("minute", 60.0);
		timeCurrency.put("hour", 3600.0);
		timeCurrency.put("day", 86400.0);
		timeCurrency.put("week", 604800.0);
		timeCurrency.put("month", 2592000.0);
		timeCurrency.put("year", 31557600.0);
		timeCurrency.put("decade", 315576000.0);
		timeCurrency.put("century", 3155760000.0);
		timeCurrency.put("milenium", 31557600000.0);
		timeCurrency.put("eon", 8.64E13);
		
		Double gen = Tools.Numbers.randomDouble(-10E20, 10E20);
		System.out.println(gen);
		HashMap<String, Double> result = Tools.Numbers.toCurrency(gen, timeCurrency, null, 1.0);
		Double value = Tools.Numbers.toValue(timeCurrency, result, 1.0);
		
		System.out.println(result);
		System.out.println(value);
		System.out.println(value.equals(gen));
		
		System.out.println(timeCurrency.get(Tools.Console.askSelection("Time labels", new ArrayList<Object>(timeCurrency.keySet()), true, "CANCEL")));
	}

}
