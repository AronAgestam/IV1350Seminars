package main.controller;
import main.model.*;
import main.integration.*;
import java.util.*;

public class Controller {

	private Sale currentSale;
	private SaleLog saleLog;
	private EISHandler eis;
	private EASHandler eas;
	private DiscountHandler dh;
	private PrinterHandler ph;
	private ArrayList<SaleObserver> saleObservers; 
	private ExceptionLogger exceptionLogger;


	public Controller() {
		this.eis = new EISHandler();
		this.eas = new EASHandler();
		this.dh = new DiscountHandler();
		this.ph = new PrinterHandler();
		this.saleObservers = new ArrayList<SaleObserver>();
		this.exceptionLogger = new ExceptionLogger();
	}
	/**
	 * Creates and saves a new Sale.
	 */
	public void startSale() {
		this.currentSale = new Sale();
	}

	/**
	 * Adds an item to current Sale. Searches for the item in EIS, and sends it to the Sale to append to its list of Items. Also returns the item.
	 * @param itemIdentifier 
	 * @param quantity
	 * @return 
	 * @throws ItemNotFoundException
	 */
	public Item addItem(int itemIdentifier, int quantity) throws ItemNotFoundException{
		try{
			Item addedItem = eis.searchItem(itemIdentifier);
			this.currentSale.addItem(addedItem, quantity);
			return addedItem;
		}
		catch (ItemNotFoundException exception){
			this.exceptionLogger.logException(exception);
			throw exception;
		}
	}

	/**
	 * Searches for valid discounts in Discount Handler, and applies any returned to current Sale. Simulates an offline database by throwing an exception for CustomerID 1000. 
	 * @param customerID
	 * @throws DatabaseOfflineException
	 */
	public void getDiscount(int customerID) throws DatabaseOfflineException{
		try{
			this.currentSale.addDiscounts(dh.searchDiscount(this.currentSale, customerID));
		}
		catch (DatabaseOfflineException exception){
			this.exceptionLogger.logException(exception);
			throw exception;
		}
	}

	/**
	 * Sets payment and change in current Sale for receipt printing and accounting. No check for a payment value lower than total cost.
	 * @param amount
	 */
	public void pay(int amount) {
		this.currentSale.setPaymentAmount(amount);
		this.currentSale.setChangeAmount(amount - this.currentSale.getTotalPrice());
	}

	/**
	 * Adds a new Observer
	 * @param newObserver
	 */
	public void addObserver(SaleObserver newObserver){
		saleObservers.add(newObserver);
	}

	/**
	 * A block of User Interface printouts supposed to be printed after every addItem operation.
	 * @param item
	 */
	public void printAddItem(Item item){
		System.out.println("Added "+ item.getQuantity() + " item with itemID " + item.getItemIdentifier());
		System.out.println("Item ID " + item.getItemIdentifier());
		System.out.println("Item description: " + item.getItemDescription());
		System.out.println("Item price " + item.getPrice() + " SEK");
		System.out.println("Item VAT " + item.getVAT() + "% \n");
	}

	/**
	 * A block of User Interface printouts supposed to be printed after every addItem operation. Prints Totals
	 */
	public void printCurrentTotals(){
		System.out.println("Total cost(VAT included): " + this.currentSale.getTotalPrice() + " SEK");
		System.out.println("Total VAT: " + this.currentSale.getTotalVAT() + " SEK \n");
	}

	/**
	 * A block of User Interface printouts supposed to be printed after every getDiscount operation.
	 */
	public void printGetDiscount(){
		System.out.println("Eligible discounts found: " + this.currentSale.getDiscounts().size());
	}

	/**
	 * A block of User Interface printouts supposed to be printed after every payment operation.
	 */
	public void printPay(){
		System.out.println("Payment recieved\n");
	}

	/**
	 * For updating total revenue for the Observers.
	 */
	public void endSale(){
		ph.printReceipt(this.currentSale);
		for(int i = 0; i<saleObservers.size(); i++){
			saleObservers.get(i).revenueUpdate(currentSale.getTotalPrice());
		}
	}

}
