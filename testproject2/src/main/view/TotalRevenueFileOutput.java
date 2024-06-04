package main.view;

import java.io.*;
import main.model.SaleObserver;

/**
 * TotalRevenueFileOutput implements the interface SaleObserver.
 */
public class TotalRevenueFileOutput implements SaleObserver {
    private double totalRevenue;
    private PrintWriter outputFile;
    /**
     * Creates a new file with specified file name.
     */
    public TotalRevenueFileOutput() {
        try{
           this.outputFile = new PrintWriter(new FileWriter("revenue.txt"), true); 
        }catch(IOException exception){
           System.out.println("File creation failed");
        }
    }
    /**
     * Prints output file with a specific String detailing the Total Revenue.
     * @param totalPrice receives the total price of the sale
     */
    @Override
    public void revenueUpdate(double totalPrice){
        this.totalRevenue += totalPrice;
        this.outputFile.println("Total Revenue: " + this.totalRevenue);
    }
}