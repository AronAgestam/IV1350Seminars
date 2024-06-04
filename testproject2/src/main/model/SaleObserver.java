package main.model;

/**
 * Interface implemented by classes printing total revenue to console and file.
 */
public interface SaleObserver {
    /**
     * @param totalPrice receives the 
     * total price of the sale(including VAT)
     */
    public void revenueUpdate(double totalPrice);
}