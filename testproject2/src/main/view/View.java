package main.view;

import java.util.ArrayList;

import main.controller.*;
import main.integration.*; 	// Needs Discount class for printouts and Exception classes for exception handling
import main.model.*;		// Needs Item and Sale classes for printouts

public class View {

	private Controller controller;

	/**
	 * The constructor for View. Enter a controller to attach the View to.
	 * @param contr - The controller to attach
	 */
	public View(Controller contr) {
		this.controller = contr;
		
		// TotalRevenue-Observers are stored in the View layer and should be added from View to preserve MVC/encapsulation.
		this.controller.addObserver(new TotalRevenueFileOutput());	
		this.controller.addObserver(new TotalRevenueView());	
	}


	
	////// Printing Section

	/**
	 * A block of User Interface printouts supposed to be printed when starting a new Sale. 
	 * Prints confirmation of starting a new Sale.
	 */
	public void printStartSale(){
		System.out.println("\n\nNew Sale started at: " + controller.getSale().getTime() +"\n");
	}
	/**
	 * A block of User Interface printouts supposed to be printed after every addItem operation. 
	 * Prints Item information.
	 * @param item - the Item to print info from
	 * @param quantity - quantity that was added
	 */
	public void printAddItem(Item item, int quantity){
		System.out.println("Added "+ quantity + " item(s) with itemID: " + item.getItemIdentifier());
		System.out.println("Item ID: " + item.getItemIdentifier());
		System.out.println("Item description: " + item.getItemDescription());
		System.out.println("Item price: " + item.getPrice() + " SEK");
		System.out.println("Item VAT: " + item.getVAT() + "% \n");
	}
	/**
	 * A block of User Interface printouts supposed to be printed after every addItem operation. 
	 * Prints Totals.
	 */
	public void printCurrentTotals(){
		System.out.printf("Total cost(VAT included): %.2f  SEK\n", controller.getSale().getTotalPrice());
		System.out.printf("Total VAT: %.2f  SEK \n\n", controller.getSale().getTotalVAT());
	}
	/**
	 * A block of User Interface printouts supposed to be printed after every getDiscount operation. 
	 * Prints number of discounts.
	 */
	public void printGetDiscount(){
		System.out.println("Eligible discounts found: " + controller.getSale().getDiscounts().size());

		// Printout details of discount list.
		ArrayList<Discount> discounts = controller.getSale().getDiscounts();
		for(int i = 0; i<discounts.size(); i++){
			System.out.println(discounts.get(i).getDiscountDescription());
		}
		System.out.println("");
	}
	/**
	 * A block of User Interface printouts supposed to be printed after every payment operation. 
	 * Prints confirmation of payment and updating of systems. 
	 */
	public void printPay(){
		System.out.printf("Payment received: %.2f SEK\n", controller.getSale().getPaymentAmount());
		System.out.printf("Change returned: %.2f SEK\n", controller.getSale().getChangeAmount());

		// Printout about updating external systems.
		System.out.println("\nSent sale info to external accounting system.");
		System.out.println("Sent instructions to external inventory system:");
		ArrayList<Item> items = controller.getSale().getItems();
		for(int i = 0; i<items.size(); i++){
			Item a = items.get(i);
            System.out.println("Decrease inventory quantity of itemID " + a.getItemIdentifier() + " by " + a.getQuantity() + " units.");;
        }
	}	
	/**
	 * Formats a receipt for the current Sale.
     * @return String description of Sale, a Receipt with parameters ordered as in constructor.
     */
    public String createReceipt() {
		ArrayList<Item> items = controller.getSale().getItems();
		ArrayList<Discount> discounts = controller.getSale().getDiscounts();
        String itemListString = "";
		String discountListString = "";
        for(int i = 0; i<items.size(); i++){
			Item a = items.get(i);
            itemListString += a.getItemDescription() + "   " + a.getQuantity() + " x " + a.getPrice() + "   " + a.getQuantity()*a.getPrice() + " SEK \n";
			if(i == items.size() - 1){itemListString += "\n";}
        }
		for(int i = 0; i<discounts.size(); i++){
			discountListString += discounts.get(i).getDiscountDescription() + "\n";
			if(i == discounts.size() - 1){discountListString += "\n";}
		}
		Sale b = controller.getSale();
        return "\n-----------Receipt Start-----------\nTime of Sale: " + b.getTime() + "\n\n" + itemListString + discountListString + "Total: " + String.format("%.2f",b.getTotalPrice()) + " SEK \nVAT: " + String.format("%.2f",b.getTotalVAT())+ " SEK \n\nPayment: "+ String.format("%.2f",b.getPaymentAmount()) + " SEK\nChange: " + String.format("%.2f",b.getChangeAmount()) + " SEK\n------------Receipt End------------\n";
    }
	/**
     * Constructs and Prints receipt from View. Sends copy to PrinterHandler to be physically printed.
     */
    public void printReceipt() {
		// System Printout
		String receipt = createReceipt();
		System.out.println(receipt);
		// External Printer
		this.controller.printReceiptPhysical(receipt);
	}



	////// User Visible Methods
	/// Exceptions are currently caught in the Example runs instead of in the User methods.
	/// This causes the Sale to be abandoned entirely from an exception rather than let a partial Sale take place.

	/**
     * User-Method for starting a Sale and its related printouts.  
	 */
	public void startSaleProcedure() {
		controller.startSale();
		// Prints confirmation of starting a Sale and the current time.
		printStartSale();
	}
	/**
     * User-Method for adding an Item and its related printouts. Self-contained, as it should appear to a User.
	 * @param itemIdentifier - 1,2,3 are valid, 1000 causes database exception
	 * @param quantity - Quantity added to Sale of the Item
	 * @throws ItemNotFoundException - Propagates exceptions from searchItem in EISHandler. Logged by Controller.
	 * @throws DatabaseOfflineException - Propagates exceptions from searchItem in EISHandler. Logged by Controller.
	 */
	public void addItemProcedure(int itemIdentifier, int quantity) throws ItemNotFoundException, DatabaseOfflineException{
		Item addedItem = this.controller.addItem(itemIdentifier, quantity);
		// Printouts to system.out - Confirms Item added, presents new total price.
		printAddItem(addedItem, quantity);
		printCurrentTotals();
	}
	/**
	 * User-Method for getting Discounts and its related printouts.
	 * @param customerID - 1000 causes exception
	 * @throws DatabaseOfflineException - Propagates exceptions from searchDiscount in DiscountHandler. Logged by Controller.
	 */
	public void getDiscountProcedure(int customerID) throws DatabaseOfflineException{
		controller.getDiscount(customerID);
		// Printouts to system.out - Confirms eligible discounts
		printGetDiscount(); 
		printCurrentTotals();
	}
	/**
	 * User-Method for receving payment and printing receipt. 
	 * @param amountPaid Payment amount recieved
	 */
	public void payProcedure(double amountPaid){
		this.controller.pay(amountPaid);
		// Printout to system.out - Confirm payment, print receipt - Also sends receipt to external PrinterHandler 
		printPay();
		printReceipt();
	}



	////// Example Runs
	/// Exceptions are currently caught in the Example runs instead of in the User methods.
	/// This causes the Sale to be abandoned entirely from an exception rather than let a partial Sale take place.

	/**
    * Example-run containing the procedures for 3 sales. 
	* The 3 sales showcase the function of the TotalRevenue classes in printing the accumulated revenue to terminal and file.
	* Sale 1 also shows the systems handling of adding multiple of the same item.  
	* Aborts sale if exception thrown.
	*/
	public void exampleRunTotalRevenue(){
		try{
			startSaleProcedure();
			addItemProcedure(1, 1);
			addItemProcedure(1, 1);
			addItemProcedure(2, 2);
			addItemProcedure(2, 2);
			addItemProcedure(3, 3);
			addItemProcedure(3, 3);
			getDiscountProcedure(1007);
			payProcedure(300);

			startSaleProcedure();
			addItemProcedure(2, 47);
			//getDiscountProcedure(1002);	// omit discounts to shorten printout
			payProcedure(800);

			startSaleProcedure();
			addItemProcedure(3, 132);
			//getDiscountProcedure(1002);	// omit discounts to shorten printout
			payProcedure(200);
		}
		catch(ItemNotFoundException | DatabaseOfflineException exception){
			System.out.println("Exception: " + exception.getMessage());
		}
	}
	/**
    * Example-run containing the procedures for 1 sale. 
	* Attempts to add a non-existent item, to show the ItemNotFoundException.
	* Aborts sale when exception thrown.
	*/
	public void exampleRunItemNotFound(){
		try{
			startSaleProcedure();
			addItemProcedure(4, 4);
			getDiscountProcedure(1009);
			payProcedure(800);
		}
		catch(ItemNotFoundException | DatabaseOfflineException exception){
			System.out.println("Exception: " + exception.getMessage());
		}
	}
	/**
    * Example-run containing the procedures for 1 sale. 
	* Attempts to add an item with an identifier that causes the simulation of an offline Item Database, to show the DatabaseOfflineException.
	* Aborts sale when exception thrown.
	*/
	public void exampleRunItemDatabase(){
		try{
			startSaleProcedure();
			addItemProcedure(1000, 4);
			getDiscountProcedure(1001);
			payProcedure(800);
		}
		catch(ItemNotFoundException | DatabaseOfflineException exception){
			System.out.println("Exception: " + exception.getMessage());
		}
	}
	/**
    * Example-run containing the procedures for 1 sale. 
	* Attempts to find discounts for a Customer ID that causes the simulation of an offline Discount Database, to show the DatabaseOfflineException.
	* Aborts sale when exception thrown.
	*/
	public void exampleRunDiscountDatabase(){
		try{
			startSaleProcedure();
			addItemProcedure(2, 4);
			getDiscountProcedure(1000);
			payProcedure(800);
		}
		catch(ItemNotFoundException | DatabaseOfflineException exception){
			System.out.println("Exception: " + exception.getMessage());
		}
	}


}
