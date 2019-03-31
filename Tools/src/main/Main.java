package main;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		System.out.println(Tools.Strings.prettyPrintJSON("{\"hello\":[1,3,4,5],\"hi\":{\"1\":6,\"{2}\":7}}"));
		/*Tools.Files.copyFiles("C:\\Users\\Ptolemy\\Desktop\\temp", "C:\\Users\\Ptolemy\\Desktop\\temp1");
		System.out.println(System.nanoTime());
		double time = Tools.Variables.getTime();
		System.out.println(time);
		System.out.println(Tools.Variables.isAfter(time, Tools.Variables.getTime()) + ", " + Tools.Variables.isBefore(time, Tools.Variables.getTime()));
		
		List<Integer> list = new ArrayList<>();
		list.add(15);
		list.add(2);
		list.add(42);
		list.add(55);
		list.add(67);
		list.add(15);
		
		System.out.println(Tools.Console.askSelection("Generic list", list, true, "Choose an item.", "CANCEL", true, true, true, false));
		System.out.println(System.nanoTime());
		System.out.println(Tools.Variables.getTime());
		System.out.println(Tools.Variables.timeSince(time));
		System.out.println(Tools.Variables.isAfter(time, Tools.Variables.getTime()) + ", " + Tools.Variables.isBefore(time, Tools.Variables.getTime()));*/
	}

}
