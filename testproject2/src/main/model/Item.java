package main.model;

public class Item {

    private int itemIdentifier;
	private String itemDescription;
	private double price;
	private double VAT;
	private int quantity = 0;

	/**
	 * 
	 * @param itemIdentifier
	 * @param itemDescription
	 * @param price
	 * @param VAT
	 * @param quantity
	 */
	public Item(int itemIdentifier, String itemDescription,	double price, double VAT, int quantity) {
        this.itemIdentifier = itemIdentifier;
        this.itemDescription = itemDescription;
        this.price = price;
        this.VAT = VAT;
		this.quantity = quantity;
	}

    public Item(int itemIdentifier, String itemDescription,	double price, double VAT) {
        this.itemIdentifier = itemIdentifier;
        this.itemDescription = itemDescription;
        this.price = price;
        this.VAT = VAT;
		this.quantity = 1;
	}

    /**
     * @return int return the itemIdentifier
     */
    public int getItemIdentifier() {
        return itemIdentifier;
    }
    /**
     * @param itemIdentifier the itemIdentifier to set
     */
    public void setItemIdentifier(int itemIdentifier) {
        this.itemIdentifier = itemIdentifier;
    }

    /**
     * @return String return the itemDescription
     */
    public String getItemDescription() {
        return itemDescription;
    }
    /**
     * @param itemDescription the itemDescription to set
     */
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    /**
     * @return double return the price
     */
    public double getPrice() {
        return price;
    }
    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return double return the VAT
     */
    public double getVAT() {
        return VAT;
    }
    /**
     * @param VAT the VAT to set
     */
    public void setVAT(double VAT) {
        this.VAT = VAT;
    }

    /**
     * @return int return the quantity
     */
    public int getQuantity() {
        return quantity;
    }
    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
