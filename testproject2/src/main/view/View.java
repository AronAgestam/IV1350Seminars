package main.view;

import main.controller.*;
import main.integration.*;
import main.model.*;

public class View {

	private Controller controller;

	public View(Controller contr) {
		this.controller = contr;
		this.controller.addObserver(new TotalRevenueView());
        this.controller.addObserver(new TotalRevenueFileOutput());
	}

	public void exampleRunTotalRevenue(){
		try{
		controller.startSale();
		Item a = controller.addItem(1, 1);
		controller.printAddItem(a);
		controller.printCurrentTotals();
		controller.getDiscount(1001);
		controller.printGetDiscount();
		controller.pay(3000);
		controller.printPay();
		controller.endSale();

		controller.startSale();
		Item b = controller.addItem(2, 40);
		controller.printAddItem(b);
		controller.printCurrentTotals();
		controller.getDiscount(1002);
		controller.printGetDiscount();
		controller.pay(4000);
		controller.printPay();
		controller.endSale();
		}
		catch(ItemNotFoundException | DatabaseOfflineException exception){
			System.out.println("Exception: " + exception.getMessage());
		}
	}

	public void exampleRunDatabaseOffline(){
	try{
		controller.startSale();
		Item e = controller.addItem(2, 20);
		controller.printAddItem(e);
		controller.printCurrentTotals();
		controller.getDiscount(1000);
		controller.printGetDiscount();
		controller.pay(500);
		controller.printPay();
		controller.endSale();
		}
	catch(ItemNotFoundException | DatabaseOfflineException exception){
		System.out.println("Exception: " + exception.getMessage());
		}
	}

	public void exampleRunItemNotFound(){
	try{
		controller.startSale();
		Item f = controller.addItem(7, 20);
		controller.printAddItem(f);
		controller.printCurrentTotals();
		controller.getDiscount(1003);
		controller.printGetDiscount();
		controller.pay(500);
		controller.printPay();
		controller.endSale();
		}
	catch(ItemNotFoundException | DatabaseOfflineException exception){
		System.out.println("Exception: " + exception.getMessage());
		}
	}

}
