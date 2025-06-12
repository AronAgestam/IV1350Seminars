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
	private ExceptionLogger exceptionLogger;

	private ArrayList<SaleObserver> saleObservers;

	/**
	 * Creates a new Controller object with TotalRevenue Observers.
	 */
	public Controller() {
		this.eis = new EISHandler();
		this.eas = new EASHandler();
		this.dh = new DiscountHandler();
		this.ph = new PrinterHandler();
		this.exceptionLogger = new ExceptionLogger();
		this.saleLog = new SaleLog();

		// Create Observer list to pass to each new Sale. Observers are preserved in Controller between Sales.
		this.saleObservers = new ArrayList<SaleObserver>();
	}
	/**
	 * Adds a new Observer.
	 * @param newObserver
	 */
	public void addObserver(SaleObserver newObserver){
		saleObservers.add(newObserver);
	}
	/**
	 * Creates and saves a new Sale, also attaching the controllers Observers to Sale.
	 */
	public void startSale() {
		this.currentSale = new Sale();

		// Passes the controllers preserved Observers to each new Sale.
		for(int i = 0; i<saleObservers.size(); i++){
			this.currentSale.addObserver(saleObservers.get(i));
		}
	}
	/**
	 * Returns the currentSale object. View needs to access Sale parameters for printouts.
	 */
	public Sale getSale() {
		return this.currentSale;
	}

	/**
	 * Adds an item to current Sale. Searches for the item in EIS, and sends it to the Sale to append to its list of Items. Also returns the item.
	 * Simulates an offline database by throwing an exception for itemID 1000.
	 * @param itemIdentifier - item to find
	 * @param quantity - quantity of item to add
	 * @return Item object containing item information
	 * @throws ItemNotFoundException   originates from searchItem in Inventory system if itemID is invalid
	 * @throws DatabaseOfflineException   originates from searchItem in Inventory system if itemID is 1000
	 */
	public Item addItem(int itemIdentifier, int quantity) throws ItemNotFoundException, DatabaseOfflineException{
		try{
			Item addedItem = eis.searchItem(itemIdentifier);
			this.currentSale.addItem(addedItem, quantity);
			return addedItem;
		}
		catch (ItemNotFoundException | DatabaseOfflineException exception){
			this.exceptionLogger.logException(exception);
			throw exception;
		}
	}

	/**
	 * Searches for valid discounts in Discount Handler, and applies any found discounts to current Sale. 
	 * Simulates an offline database by throwing an exception for customerID 1000. 
	 * @param customerID - 1000 causes exception
	 * @throws DatabaseOfflineException - originates from searchDiscount in DiscountHandler if customerID is 1000
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
	 * (The Sale-method setPaymentAmount used here will cause TotalRevenue to be updated via Observers.) 
	 * 
	 * Updates Accounting/Inventory Systems and SaleLog with Sale and Payment information.
	 * 
	 * @param amount The amount paid. A value lower than totalPrice causes a negative changeAmount value in Sale.
	 */
	public void pay(double amount) {
		this.currentSale.setPaymentAmount(amount);
		this.currentSale.setChangeAmount(amount - this.currentSale.getTotalPrice());

		// Pass Sale information to External Systems
		this.eas.updateAccountingSystems(currentSale);
		this.eis.updateInventorySystem(currentSale);
		this.saleLog.addSaleToLog(currentSale);
	}

	/**
	 * Sends String containing the receipt to PrinterHandler - Receipt is "printed" out on imaginary paper. 
	 * @param receiptString - String containing the formatted receipt to be printed
	 */
	public void printReceiptPhysical(String receiptString){
		ph.printReceiptPaper(receiptString);
	}

}
