package main;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<Double> list = new ArrayList<>();
		list.add(1.5);
		list.add(4.2);
		list.add(5.6);
		
		if (Tools.Console.askSelection("Generic list", list, true, "CANCEL") instanceof Double) {
			System.out.println("Test passed");
		} else {
			System.out.println("Test failed");
		}
	}

}
