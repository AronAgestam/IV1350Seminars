package main.model;

import main.integration.Discount; // Needs Disocunt Class to store discounts in Sale object.

import java.time.LocalTime;
import java.util.ArrayList;

public class Sale {

	private double totalPrice;
	private double totalVAT;
	private LocalTime time;
	private ArrayList<Item> items;
	private double paymentAmount;
	private double changeAmount;
	private ArrayList<Discount> discounts;

    private ArrayList<SaleObserver> saleObservers; // Sale Observers should be in Sale

	/**
	* Constructs a Sale object and populates an empty Sale.
	*/
	public Sale() {
        this.totalPrice = 0;
        this.totalVAT = 0;
        this.time = LocalTime.now();
        this.items = new ArrayList<Item>();
        this.paymentAmount = 0;
        this.changeAmount = 0;
        this.discounts = new ArrayList<Discount>();

		this.saleObservers = new ArrayList<SaleObserver>();	// Sale Observers should be in Sale
	}

	/**
    * Adds an item to the current sale. Increases quantity if item already present, and only once if item listed multiple times.
	* @param newItem the item that is being added.
	* @param enteredQuantity the quantity of the item being added.
    */
	public void addItem(Item newItem, int enteredQuantity) {
		ArrayList<Item> oldItems = this.getItems();
		boolean itemFound = false;
		double updatedTotalPrice = 0;
		double updatedTotalVAT = 0;
		// Item found in Item list
		for(int i = 0; i<oldItems.size(); i++){
			Item currentItem = oldItems.get(i);
			int currentItemID = currentItem.getItemIdentifier();
			int newItemID = newItem.getItemIdentifier();
			if((currentItemID == newItemID) && (itemFound == false)){
				currentItem.setQuantity(currentItem.getQuantity() + enteredQuantity);
				itemFound = true;
			}
		}
		// Item not in Item list
		if(itemFound == false){
			newItem.setQuantity(enteredQuantity);
			oldItems.add(newItem);
		}
		for(int i = 0; i<oldItems.size(); i++){
			updatedTotalPrice += oldItems.get(i).getPrice() * oldItems.get(i).getQuantity();
			updatedTotalVAT += oldItems.get(i).getVAT() * oldItems.get(i).getQuantity() *oldItems.get(i).getPrice() * 0.01;
		}
		setTotalPrice(updatedTotalPrice);
		setTotalVAT(updatedTotalVAT);
	}

    /**
     * @return double return the totalPrice
     */
    public double getTotalPrice() {
        return totalPrice;
    }
    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(double newTotalPrice) {
        this.totalPrice = newTotalPrice;
    }

    /**
     * @return double return the totalVAT
     */
    public double getTotalVAT() {
        return totalVAT;
    }
    /**
     * @param totalVAT the totalVAT to set
     */
    public void setTotalVAT(double newTotalVAT) {
        this.totalVAT = newTotalVAT;
    }

    /**
     * @return LocalTime return the time
     */
    public LocalTime getTime() {
        return time;
    }
    /**
     * @param time the time to set
     */
    public void setTime(LocalTime newTime) {
        this.time = newTime;
    }

    /**
     * @return ArrayList<Item> return the items
     */
    public ArrayList<Item> getItems() {
        return items;
    }
    /**
     * @param items the items to set
     */
    public void setItems(ArrayList<Item> newItems) {
        this.items = newItems;
    }

    /**
     * @return String return the storeName
     */
    public double getPaymentAmount() {
        return paymentAmount;
    }
    /**
     * Sets payment value when recieving a payment.
     * Also calls updateObservers to update TotalRevenue. 
     * @param payment The payment recieved
     */
    public void setPaymentAmount(double payment) {
        this.paymentAmount = payment;
        this.updateObservers();
    }

    /**
     * @return String return the storeAddress
     */
    public double getChangeAmount() {
        return changeAmount;
    }
    /**
     * @param storeAddress the storeAddress to set
     */
    public void setChangeAmount(double change) {
        this.changeAmount = change;
    }

    /**
     * @return the discounts
     */
    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }
    /**
     * @param discounts the discounts to set
     */
    public void addDiscounts(ArrayList<Discount> discounts) {
        this.discounts.addAll(discounts);
    }
    
	/**
	 * Adds a new Observer.
	 * @param newObserver
	 */
	public void addObserver(SaleObserver newObserver){
		saleObservers.add(newObserver);
	}
    /**
	 * Notifies Observers with total price of the current Sale.
     * Should be called at the end of a Sale after recieving payment.
     * Used by TotalRevenue-classes.
	 */
	public void updateObservers(){
		for(int i = 0; i<saleObservers.size(); i++){
			saleObservers.get(i).revenueUpdate(totalPrice);
		}
    }
}
