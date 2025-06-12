package main.integration;

import java.util.*;

import main.model.*; // Needs Item and Sale classes to decide/calculate Discounts.

public class DiscountHandler {

	private Sale saleInfo;
	private int customerID;

	public DiscountHandler() {
	}

	/**
     * Searches discount "database" for eligible discounts. This is simulated by using methods costDiscount, itemDiscounts, customerDiscount to find discounts.
	 * An Offline Database is simulated if a customer ID of 1000 is entered. This throws a new DatabaseOfflineException.
	 * Edits the entered Sales totalPrice to the discounted price.
	 * @param customerID - 1000 throws Exception, 1001:1005 gives extra discounts
	 * @param saleInfo - the sale to APPLY discounts to
	 * @return list of eligible/applied discounts
	 * @throws DatabaseOfflineException originates here
     */	
	public ArrayList<Discount> searchDiscount(Sale saleInfo, int customerID) throws DatabaseOfflineException{
		this.saleInfo = saleInfo;
		this.customerID = customerID;
		// Hardcoded simulation of offline Discount Database
		if(this.customerID == 1000){throw new DatabaseOfflineException("Discount Database offline! \n");}

		ArrayList<Discount> applicableDiscounts = new ArrayList<Discount>();
		Discount costDisc = costDiscount(); 
		ArrayList<Discount> itemDisc = itemDiscounts(); 
		Discount customerDisc = customerDiscount(); 

		if(costDisc!=null){applicableDiscounts.add(costDiscount());}
		if(itemDisc!=null){applicableDiscounts.addAll(itemDiscounts());}
		if(customerDisc!=null){applicableDiscounts.add(customerDiscount());}

		// Adjust totalPrice of Sale by applying all found discounts. Discounts stack without diminishing returns.
		double formerPrice = saleInfo.getTotalPrice();
		for(int i = 0; i<applicableDiscounts.size(); i++){formerPrice = (formerPrice - applicableDiscounts.get(i).getDiscountSum());}
		saleInfo.setTotalPrice(formerPrice);

		return applicableDiscounts;
	}

    /**
     * When passed a list of all bought items, it returns an array list of sums to be reduced from the
	 * total cost of the entire sale. 
	 * Returns an empty list if there’s no discount.
	 * @return Arraylist of Discount objects (may be empty)
     */
	private ArrayList<Discount> itemDiscounts(){
		double discountValue = 0;
		double discountRate = 0;
		ArrayList<Item> itemList = saleInfo.getItems();
		ArrayList<Discount> discountList = new ArrayList<Discount>();

		for(int i = 0; i<itemList.size(); i++){
			if(itemList.get(i).getQuantity() >= 2){
				double iPrice = itemList.get(i).getPrice();
				int iNum = itemList.get(i).getQuantity();
				String iInfo = itemList.get(i).getItemDescription();

				discountRate = 0.20;
				discountValue = iPrice * iNum * discountRate;
				String desc = "Duplicate Item: "+iNum+" x "+iInfo+": - "+iPrice+ " x "+iNum+" x "+discountRate;
				String descFull = "Discount: -" + String.format("%.2f",discountValue) + " SEK (-" + discountRate *100 +"%, " + desc + ")";
				discountList.add(new Discount(discountValue, discountRate, descFull));
			}
		}
		return discountList;
	}

    /**
     * When passed the total cost of the entire sale, it applies a percentage to be
	 * reduced from this total cost. The percentage is zero if there’s no discount.
	 * Returns null if there's no discount.
	 * @return Discount object (or null)
     */
	private Discount costDiscount(){
		double discountValue = 0;
		double discountRate = 0;
		double totalPrice = saleInfo.getTotalPrice();

		if(totalPrice>=200){
			discountRate = 0.04;
			discountValue = totalPrice * discountRate;
			String desc = "Total Cost over 200: - " + totalPrice + " x " + discountRate;
			String descFull = "Discount: -" + String.format("%.2f",discountValue) + " SEK (-" + discountRate *100 +"%, " + desc + ")";
			return new Discount(discountValue, discountRate, descFull);
		}
		if(totalPrice>=100){
			discountRate = 0.02;
			discountValue = totalPrice * discountRate;
			String desc = "Total Cost over 100: - " + totalPrice + " x " + discountRate;
			String descFull = "Discount: -" + discountValue + " SEK (-" + discountRate *100 +"%, " + desc + ")";
			return new Discount(discountValue, discountRate, descFull);
		}
		return null;
	}

    /**
     * When passed the customer id, it applies a percentage to be reduced from the
	 * total cost of the entire sale. The percentage is zero if there’s no discount.
	 * Returns null if there's no discount.
	 * @return Discount object (or null)
     */
	private Discount customerDiscount(){
		double discountValue = 0;
		double discountRate = 0;
		double totalPrice = saleInfo.getTotalPrice();

		//ArrayList <Integer> listOfID= new ArrayList<Integer>();
		int[] listOfID = {1001,1002,1003,1004,1005};
		for(int i = 0; i<listOfID.length; i++){
			if (customerID == listOfID[i]){
				discountRate = 0.06;
				discountValue = totalPrice*discountRate;
				String desc = "Loyal customer: -" + totalPrice + " x " + discountRate;
				String descFull = "Discount: -" + String.format("%.2f",discountValue) + " SEK (-" + discountRate *100 +"%, " + desc + ")";
				return new Discount(discountValue, discountRate, descFull);
			}
		}
		return null;
	}
}
