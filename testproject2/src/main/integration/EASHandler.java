package main.integration;

import main.model.Sale; // Needs Sale class - could be removed if updateAccountingSystems is changed to receive extracted doubles.

public class EASHandler {

	// Only made to comply with step 14s demand of updating a local store register.
	private double storeAccount;

	/**
	 * Store Register amount is initialized as 2000. Does not retain state between different runs of the program. 
	 * Resets when a new EASHandler is created. This happens when a new Controller is created, as in the start of Main.
	 */
	public EASHandler() {
		storeAccount = 2000;
	}

	/**
	 * Updates the imaginary External Accounting System with sale information. 
	 * Increases store register account by the price/recieved payment in the specified Sale.
	 * Should be called after recieving payment/ending a Sale.
	 * @param currentSale is the current sale.
	 */
	public void updateAccountingSystems(Sale currentSale) {
		// Add Revenue to Register / Store Account
		storeAccount += currentSale.getTotalPrice();

		// For External Accounting
		double addedRevenue = currentSale.getTotalPrice(); 
		double addedVAT = currentSale.getTotalVAT();

	}

}
