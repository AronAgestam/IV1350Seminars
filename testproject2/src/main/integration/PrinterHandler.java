package main.integration;

import main.model.*;

public class PrinterHandler {

	private Sale receiptInformation;

	public PrinterHandler() {
	}

	public void printReceipt(Sale saleInformation) {
		System.out.println(saleInformation);
		this.receiptInformation = saleInformation;
	}

}
