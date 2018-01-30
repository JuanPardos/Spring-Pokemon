package es.salesianos.service;

import org.springframework.stereotype.Service;

import es.salesianos.model.Item;
import es.salesianos.model.Person;
import es.salesianos.model.Weapon;

@Service
public class StudentService implements es.salesianos.service.Service {

	@Override
	public void addItem(Item item, Person person) {
		String type = person.getItem().getType();
		if ("mochila".equals(type)) {
			if (!person.getBag().isFull()) {
				person.getBag().addItem(item);

			}
			System.out.println("la mochila dispone de " + person.getBag().spaceAvalaible() + " kilos de almacenaje");
		}

		if ("custom".equals(type)) {
			person.getPrimary().getItems().add(item);
		}
		if ("weapon".equals(type)) {
			if (person.getItem().getName() != person.getPrimary().getName()) {
				Weapon wp = new Weapon();
				wp.setName(person.getPrimary().getName());
				person.setPrimary(wp);
			}
		}
	}



}
