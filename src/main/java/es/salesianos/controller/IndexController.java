package es.salesianos.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import es.salesianos.model.Enemy;
import es.salesianos.model.PokeAttacking;
import es.salesianos.model.Pokemon;
import es.salesianos.model.Trainer;

@Controller
public class IndexController {

	private static Logger log = LogManager.getLogger(IndexController.class);

	@Autowired
	private Trainer trainer;

	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("trainer", this.trainer);
		//modelAndView.addObject("enemy", this.enemy);
		return modelAndView;
	}

	@PostMapping("insert")
	public ModelAndView trainerInsert(Trainer trainerForm) {
		log.debug("personInsert:" + this.trainer.toString());
		ModelAndView modelAndView = new ModelAndView("index");
		addPageData(trainerForm);
		modelAndView.addObject("trainer", this.trainer);
		return modelAndView;
	}

	private void addPageData(Trainer trainerForm) {
		if (!StringUtils.isEmpty(trainerForm.getName())) {
			trainer.setName(trainerForm.getName());
		}

		if (!StringUtils.isEmpty(trainerForm.getPokemon())) {
			Pokemon pokemon = new Pokemon();
			PokeAttacking weapon = new PokeAttacking();

			if (trainer.getPrimary() == null) {
				trainer.setPrimary(weapon);
				insertMethod(trainerForm, pokemon);
			} else {
				trainer.setSecondary(weapon);
				insertMethod(trainerForm, pokemon);
			}

			weapon.setName(pokemon.getName());
			weapon.setLevel(pokemon.getLevel());
			weapon.setStatus(pokemon.getStatus());
			weapon.setAttack(pokemon.getAttack());
			weapon.setHP(pokemon.getHP());
			weapon.setMaxHP(pokemon.getMaxHP());

			this.trainer.setPokemon(pokemon);
		}
	}

	private void insertMethod(Trainer trainerForm, Pokemon pokemon) {
		pokemon.setName(trainerForm.getPokemon().getName());
		pokemon.setLevel(trainerForm.getPokemon().getLevel());
		pokemon.setStatus(trainerForm.getPokemon().getStatus());

		if (trainerForm.getPokemon().getLevel() > 0 && trainerForm.getPokemon().getLevel() <= 100) {
			pokemon.setAttack((int) (Math.random() * (10 + (trainerForm.getPokemon().getLevel()) / 2))
				+ ((trainerForm.getPokemon().getLevel()) / 2) + 1);
			pokemon.setHP(trainerForm.getPokemon().getLevel() * 4);
			pokemon.setMaxHP(pokemon.getHP());
			this.trainer.getTeam().addPokemon(pokemon);
		} else
			System.out.println("El nivel del pokemon no es correcto");
	}

	private void insertEnemy(Trainer trainerForm, Enemy enemyPokemon) {
		enemyPokemon.setName("Magikarp");
		enemyPokemon.setLevel((int) (Math.random() * 30) + 70);
		enemyPokemon.setAttack(
			(int) (Math.random() * (5 + (enemyPokemon.getLevel()) / 2)) + ((enemyPokemon.getLevel() / 2)) + 1);
		enemyPokemon.setHP(enemyPokemon.getLevel() * 4);
		enemyPokemon.setMaxHP(enemyPokemon.getHP());
		enemyPokemon.setStatus("Vivo");
	}

	@PostMapping("switchPokemon")
	public ModelAndView switchPokemon(Trainer trainerForm) {
		PokeAttacking tmp;

		tmp = this.trainer.getPrimary();
		this.trainer.setPrimary(this.trainer.getTeam().setAttackingPokemon(trainerForm.getAux()));
		this.trainer.setSecondary(tmp);

		if (this.trainer.getPrimary().getName() != null) {
			System.out.println("El pokemon activo es " + this.trainer.getPrimary().getName());
		}

		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("trainer", this.trainer);
		return modelAndView;
	}

	@PostMapping("releasePokemon")
	public ModelAndView releasePokemon(Trainer trainerForm) {
		this.trainer.getTeam().removePokemonById(trainerForm.getAux2());

		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("trainer", this.trainer);
		return modelAndView;
	}

	@PostMapping("createEnemy")
	public ModelAndView createEnemy(Trainer trainerForm) {
		Enemy enemyPokemon = new Enemy();
		trainer.setWildPokemon(enemyPokemon);
		insertEnemy(trainerForm, enemyPokemon);

		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("trainer", this.trainer);
		return modelAndView;
	}

	@PostMapping("combat")
	public ModelAndView combat(Trainer trainerForm) {
		if (trainer.getPrimary().getStatus() == "Vivo") {
			trainer.getWildPokemon().setHP(trainer.getWildPokemon().getHP() - trainer.getPrimary().getAttack());
			trainer.getPrimary().setHP(trainer.getPrimary().getHP() - trainer.getWildPokemon().getAttack());
		} else {
			System.out.println(trainer.getPrimary().getName() + " no puede combatir");
		}

		if (trainer.getPrimary().getHP() <= 0) {
			trainer.getPrimary().setHP(0);
			trainer.getPrimary().setStatus("Muerto");
		}

		if (trainer.getWildPokemon().getHP() <= 0) {
			trainer.getWildPokemon().setHP(0);
			trainer.getWildPokemon().setStatus("Muerto");
		}

		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("trainer", this.trainer);
		return modelAndView;
	}

}
