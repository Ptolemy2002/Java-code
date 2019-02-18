package main;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(15);
		list.add(2);
		list.add(42);
		list.add(55);
		list.add(67);
		list.add(15);
		
		System.out.println(Tools.Console.askSelection("Generic list", list, true, "CANCEL", true, true, true));
	}

}
