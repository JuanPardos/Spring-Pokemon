package es.salesianos.model;

import java.util.List;

public class BagLevel1 implements Bag {

	private List<Item> items;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addItem(Item item) {
		// TODO Auto-generated method stub

	}

	@Override
	public int spaceAvalaible() {
		// TODO Auto-generated method stub
		return 0;
	}


}
