package main.view;

import main.model.SaleObserver;
/**
 * TotalRevenueView implements the interface SaleObserver.
 */
public class TotalRevenueView implements SaleObserver {
    private double totalRevenue;
    
    /**
     * Prints a specific String to console.
    * @param totalPrice receives the total price of the sale (including VAT)
    */
    @Override
    public void revenueUpdate(double totalPrice){
        totalRevenue += totalPrice;
        System.out.printf("Total revenue: %.2f SEK \n\n", totalRevenue);
    }
}