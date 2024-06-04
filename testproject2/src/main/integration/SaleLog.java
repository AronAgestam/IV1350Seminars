package main.integration;

import java.util.*;

import main.model.*;

public class SaleLog {

	private ArrayList <Sale> saleDetails;

	public SaleLog() {
		this.saleDetails = new ArrayList<Sale>(); 
	}
	
	/**
	 * Sends sale information to a Sale Log for logging. Sale Log not modelled.
	 * @param saleInfo
	 */
	public void addSaleToLog(Sale saleInfo) {
		this.saleDetails.add(saleInfo);
	}

}
