package main.integration;
import main.model.*;

import java.util.ArrayList;

public class EISHandler {

	private ArrayList<Item> storeItems;

	/**
	 * The EIS isn't fully modelled, but a handful of items have been entered manually in the constructor to make the program functional. 
	 */
	public EISHandler() {
		this.storeItems = new ArrayList<Item>();
		storeItems.add(new Item(1, "Banan, Gul", 20, 6, 1));
		storeItems.add(new Item(2, "Morot, Orange", 15, 6, 1));
		storeItems.add(new Item(3, "Potatis, Brun", 10, 6, 1));
	}

	/**
	 * Searches for item information in External Inventory System by entering an item identifier.
	 * @param itemIdentifier
	 * @return found item corresponding to item identifier
	 */
	public Item searchItem(int itemIdentifier) throws ItemNotFoundException {
		for(int i = 0; i<storeItems.size(); i++){
			int currentItemID = storeItems.get(i).getItemIdentifier();
			if(currentItemID == itemIdentifier){
				Item foundItem = storeItems.get(i);
				return foundItem;
			}
		}
		throw new ItemNotFoundException("Item Identifier not found: " + itemIdentifier + "\n");
	}

	/**
	 * Handles the updating of the External Inventory System with sale information. Does nothing since External Systems are not fully modelled.
	 * @param currentSale is the current sale.
	 */
	 
	public void updateSystem(Sale currentSale) {
		/* 
		for(int i = 0; i<currentSale.getItems().size(); i++){
			Item saleItem = currentSale.getItems().get(i);
			for(int j = 0; j<storeItems.size(); j++){
				int saleItemID = saleItem.getItemInformation().getItemIdentifier();
				int storeItemID = storeItems.get(j).getItemInformation().getItemIdentifier();
				if(saleItemID == storeItemID){
					int storeItemQuantity = storeItems.get(j).getQuantity();
					int saleItemQuantity = saleItem.getQuantity();
					storeItems.get(j).setQuantity(storeItemQuantity - saleItemQuantity);
				}
			}
		}
		*/
	}
}
