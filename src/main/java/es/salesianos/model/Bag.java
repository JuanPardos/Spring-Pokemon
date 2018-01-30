package es.salesianos.model;

public interface Bag {

	public boolean isFull();

	public void addItem(Item item);

	public int spaceAvalaible();
}
