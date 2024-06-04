package main.startup;

import main.controller.*;
import main.view.*;


public class Main {

	public static void main(String[] args) {
        System.out.println("Hej värld");
		Controller controller = new Controller();
		View view = new View(controller);
		view.exampleRunTotalRevenue();
		view.exampleRunItemNotFound();
		view.exampleRunDatabaseOffline();
	}

}
