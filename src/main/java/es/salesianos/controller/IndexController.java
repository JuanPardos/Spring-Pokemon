package es.salesianos.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import es.salesianos.model.AbstractBag;
import es.salesianos.model.Bag;
import es.salesianos.model.Item;
import es.salesianos.model.Person;
import es.salesianos.model.Weapon;

@Controller
public class IndexController {

	private static Logger log = LogManager.getLogger(IndexController.class);

	private Person person;
	private Item item;
	private Bag bag1;
	private AbstractBag bag;

	@GetMapping("/")
	public ModelAndView index() {
		person = new Person();
		person.setItem(new Item());
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("person", person);
		return modelAndView;
	}

	
	@PostMapping("insert")
	public ModelAndView personInsert(Person person) {
		log.debug("personInsert:" + this.person.toString());		
		addPageData(person);
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("recipe", person);
		return modelAndView;
	}
	
	private void addPageData(Person personForm) {

		if (!StringUtils.isEmpty(personForm.getName())) {
			this.person.setName(personForm.getName());
		}

		if (!StringUtils.isEmpty(personForm.getBag())) {
			bag.addItem(item);
			personForm.setName("");
			this.person.getBag().addItem(item);
		}
		if (!StringUtils.isEmpty(personForm.getItem())) {
			Item item = new Item();
			item.setName(personForm.getName());
			personForm.setItem(item);
			this.person.getItem().getName();
		}
	}
	
	@PostMapping("itemInsert")
	public ModelAndView itemInsert(Person person) {
		log.debug("ingredientInsert:" + this.person.toString());
		addPageData(person);
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("recipe", person);
		return modelAndView;
	}

	@PostMapping("switchWeapon")
	public ModelAndView switchWeapon(Person person) {
		Weapon tmp;
		tmp = this.person.getPrimary();
		this.person.setPrimary(this.person.getSecondary());
		this.person.setSecondary(tmp);
		System.out.println("El arma activa es " + this.person.getPrimary().getName());
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("person", person);
		return modelAndView;
	}



}
