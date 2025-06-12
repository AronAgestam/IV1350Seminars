package main.integration;
import main.model.*; // Needs Item and Sale classes - could be reduced to Item if updateInventorySystem is changed to receive an Item list.

import java.util.ArrayList;

public class EISHandler {

	// Item registry for the store
	private ArrayList<Item> storeItems;

	/**
	 * The EIS isn't fully modelled, but a handful of items have been entered manually in the constructor to make the program functional. 
	 * This constructor enters a few items in the Item Registry called storeItems.
	 */
	public EISHandler() {
		this.storeItems = new ArrayList<Item>();
		storeItems.add(new Item(1, "Banan, Gul", 20, 6, 1));
		storeItems.add(new Item(2, "Morot, Orange", 15, 6, 1));
		storeItems.add(new Item(3, "Potatis, Brun", 10, 6, 1));
	}

	/**
	 * Searches for item information in External Inventory System by entering an item identifier.
	 * Approximated by searching in a locally saved inventory list called storeItems.
	 * An Offline Database is simulated if a item ID of 1000 is entered. This throws a new DatabaseOfflineException.
	 * @param itemIdentifier - item to find, throws exception if itemID is 1000
	 * @return found item corresponding to item identifier
	 * @throws ItemNotFoundException originates here
	 * @throws DatabaseOfflineException originates here
	 */
	public Item searchItem(int itemIdentifier) throws ItemNotFoundException, DatabaseOfflineException {
		// Hardcoded simulation of offline Item Database
		if(itemIdentifier == 1000){throw new DatabaseOfflineException("Item Database offline! \n");}

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
	 * Handles the updating of the External Inventory System with sale information. 
	 * Acts as an interface to the imagined External Inventory System. 
	 * Local system passes Sale information to it and expects it to do the rest.
	 * Does nothing of consequence since External Systems are not modelled.
	 * @param currentSale is the current sale.
	 */
	public void updateInventorySystem(Sale currentSale) {
		// Example of how to extract the information the External Inventory System needs to be updated with.
		int numberOfItems = currentSale.getItems().size();
		int[][]itemsLost = new int[numberOfItems][2];

		// Fill array detailing items lost. First column for itemID, second for number of items.
		for(int i = 0; i<currentSale.getItems().size(); i++){
			Item saleItem = currentSale.getItems().get(i);
			itemsLost[i][0] = saleItem.getItemIdentifier();
			itemsLost[i][1] = saleItem.getQuantity();
		}
		// Pass completed array to External Inventory System to decrease item quantities in inventory.
			// Pretend that happens here since that system doesn't exist.
	}
}