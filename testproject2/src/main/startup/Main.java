package main.startup;

import java.util.Locale;

import main.controller.*;
import main.view.*;


public class Main {

	public static void main(String[] args) {
		// Just in case
        System.out.println("Hej värld");
		// Printformatting was using commas for numbers and I didn't like it
		Locale.setDefault(Locale.CANADA); 			
		
		Controller controller = new Controller();
		View view = new View(controller);
		
		view.exampleRunTotalRevenue();
		view.exampleRunItemNotFound();
		view.exampleRunItemDatabase();
		view.exampleRunDiscountDatabase();
	}
}
