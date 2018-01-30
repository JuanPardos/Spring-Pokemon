package es.salesianos.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Person {

	@Autowired
	private Bag bag;
	private Weapon primary;
	private Weapon secondary;
	private Item item;

	public Bag getBag() {
		return bag;
	}
	public void setBag(Bag bag) {
		this.bag = bag;
	}
	public Weapon getPrimary() {
		return primary;
	}
	public void setPrimary(Weapon primary) {
		this.primary = primary;
	}
	public Weapon getSecondary() {
		return secondary;
	}
	public void setSecondary(Weapon secondary) {
		this.secondary = secondary;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}

}
