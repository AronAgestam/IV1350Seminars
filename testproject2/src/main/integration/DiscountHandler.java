package main.integration;

import java.util.*;

import main.model.*;

public class DiscountHandler {

	private Sale saleInfo;
	private int customerID;

	public DiscountHandler() {
	}

	public ArrayList<Discount> searchDiscount(Sale saleInfo, int customerID) throws DatabaseOfflineException{
		this.saleInfo = saleInfo;
		this.customerID = customerID;
		if(this.customerID == 1000){throw new DatabaseOfflineException("Database offline! \n");}
		ArrayList<Discount> applicableDiscounts = new ArrayList<Discount>();
		Discount costDisc = costDiscount(); 
		ArrayList<Discount> itemDisc = itemDiscounts(); 
		Discount customerDisc = customerDiscount(); 
		if(costDisc!=null){applicableDiscounts.add(costDiscount());}
		if(itemDisc!=null){applicableDiscounts.addAll(itemDiscounts());}
		if(customerDisc!=null){applicableDiscounts.add(customerDiscount());}
		for(int i = 0; i<applicableDiscounts.size(); i++){saleInfo.setTotalPrice(saleInfo.getTotalPrice() - applicableDiscounts.get(i).getDiscountSum());}

		return applicableDiscounts;
	}

    /**
     * When passed a list of all bought items, it returns an array list of sums to be reduced from the
	 * total cost of the entire sale. The list is empty if there’s no discount.
	 * 
     */
	private ArrayList<Discount> itemDiscounts(){
		double discountValue = 0;
		double discountRate = 0;
		ArrayList<Item> itemList = saleInfo.getItems();
		ArrayList<Discount> discountList = new ArrayList<Discount>();

		for(int i = 0; i<itemList.size(); i++){
			if(itemList.get(i).getQuantity() >= 2){
				discountRate = 0.20;
				discountValue = itemList.get(i).getPrice() * itemList.get(i).getQuantity() * discountRate;
				discountList.add(new Discount(discountValue, discountRate, "Duplicate Item -"));
			}
		}
		return discountList;
	}

    /**
     * When passed the total cost of the entire sale, it applies a percentage to be
	 * reduced from this total cost. The percentage is zero if there’s no discount.
	 * 
     */
	private Discount costDiscount(){
		double discountValue = 0;
		double discountRate = 0;
		double totalPrice = saleInfo.getTotalPrice();

		if(totalPrice>=200){
			discountRate = 0.04;
			discountValue = totalPrice * discountRate;
			return new Discount(discountValue, discountRate, "Total Cost over 200 -");
		}
		if(totalPrice>=100){
			discountRate = 0.02;
			discountValue = totalPrice * discountRate;
			return new Discount(discountValue, discountRate, "Total Cost over 100 -");
		}
		return null;
	}

    /**
     * When passed the customer id, it applies a percentage to be reduced from the
	 * total cost of the entire sale. The percentage is zero if there’s no discount.
	 * 
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
				return new Discount(discountValue, discountRate, "Loyal customer -");
			}
		}
		return null;
	}
}
