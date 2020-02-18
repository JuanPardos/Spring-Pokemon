package es.salesianos.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import es.salesianos.model.Pokemon;
import es.salesianos.model.Trainer;

@Controller
public class IndexController {
	private int contSleep = 0;
	private static Logger log = LogManager.getLogger(IndexController.class);
	private double multiplier = 1.0;
	private int aux3; //Se usa para actualizar la tabla cuando el pokemon en combate recibe daño.

	@Autowired
	private Trainer trainer;

	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("trainer", this.trainer);
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
			Pokemon weapon = new Pokemon();

			if (trainer.getPrimary() == null) {
				trainer.setPrimary(weapon);
				insertMethod(trainerForm, pokemon);
			} else {
				trainer.setSecondary(weapon);
				insertMethod(trainerForm, pokemon);
			}

			weapon.setName(pokemon.getName());
			weapon.setType(pokemon.getType());
			weapon.setLevel(pokemon.getLevel());
			weapon.setStatus(pokemon.getStatus());
			weapon.setAttack(pokemon.getAttack());
			weapon.setMaxHP(pokemon.getMaxHP());
			weapon.setHP(pokemon.getMaxHP());

			this.trainer.setPokemon(pokemon);
		}
	}

	private void insertMethod(Trainer trainerForm, Pokemon pokemon) {
		pokemon.setName(trainerForm.getPokemon().getName());
		pokemon.setLevel(trainerForm.getPokemon().getLevel());
		pokemon.setStatus(trainerForm.getPokemon().getStatus());
		pokemon.setType(trainerForm.getPokemon().getType());

		if (trainerForm.getPokemon().getLevel() > 0 && trainerForm.getPokemon().getLevel() <= 100) {
			pokemon.setAttack((int) (Math.random() * (10 + (trainerForm.getPokemon().getLevel()) / 2))
				+ ((trainerForm.getPokemon().getLevel()) / 2) + 1);
			pokemon.setMaxHP(trainerForm.getPokemon().getLevel() * 4);
			pokemon.setHP(pokemon.getMaxHP());
			this.trainer.getTeam().addPokemon(pokemon);
		} else
			trainer.setFeedback("El nivel del pokemon no es correcto, 1 al 100");
	}

	private void insertEnemy(Trainer trainerForm, Pokemon enemyPokemon) {
		String nombres[] = {"Flygon", "Salamence", "Giratina", "Groudon", "Kyogre", "Rayquaza", "Squirtle", "Charizard",
			"Smeargle", "Regirock", "Registeel", "Regice", "Giraffarig", "Porygon2", "Lucario", "Gorka (Shiny)",
			"Raychu", "Necrozma", "Absol"};

		String tipos[] = {"Agua", "Fuego", "Planta"};

		enemyPokemon.setName(nombres[(int) (Math.random() * nombres.length)]);
		enemyPokemon.setLevel((int) (Math.random() * 60) + 40);
		enemyPokemon.setAttack(
			(int) (Math.random() * (5 + (enemyPokemon.getLevel()) / 2)) + ((enemyPokemon.getLevel() / 2)) + 1);
		enemyPokemon.setMaxHP(enemyPokemon.getLevel() * 4);
		enemyPokemon.setHP(enemyPokemon.getMaxHP());
		enemyPokemon.setCaptureRate((int) (Math.random() * 40) + 75); //Valores entre 75 y 115.
		enemyPokemon.setStatus("Vivo");
		enemyPokemon.setType(tipos[(int) (Math.random() * tipos.length)]);
	}

	@PostMapping("switchPokemon")
	public ModelAndView switchPokemon(Trainer trainerForm) {
		Pokemon tmp;
		aux3 = trainerForm.getAux();

		tmp = this.trainer.getPrimary();
		if (this.trainer.getTeam().setAttackingPokemon(trainerForm.getAux()).getStatus() == "Muerto") {
			trainer.setFeedback("El pokemon está debilitado, no puede pelear");
		} else {
			this.trainer.setPrimary(this.trainer.getTeam().setAttackingPokemon(trainerForm.getAux()));
		}
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
		this.trainer.setPrimary(null);

		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("trainer", this.trainer);
		return modelAndView;
	}

	@PostMapping("createEnemy")
	public ModelAndView createEnemy(Trainer trainerForm) {
		Pokemon enemyPokemon = new Pokemon();
		trainer.setWildPokemon(enemyPokemon);
		insertEnemy(trainerForm, enemyPokemon);

		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("trainer", this.trainer);
		return modelAndView;
	}

	@PostMapping("combat")
	public ModelAndView combat(Trainer trainerForm) {
		double multiplier = Math.random();
		if (trainer.getPrimary().getStatus() == "Vivo") {
			trainer.getWildPokemon()
				.setHP(trainer.getWildPokemon().getHP() - (int) ((trainer.getPrimary().getAttack()) * multiplier));

			if (trainer.getWildPokemon().getStatus() != "Durmiendo") {
				trainer.getPrimary()
					.setHP(trainer.getPrimary().getHP() - (int) ((trainer.getWildPokemon().getAttack()) * multiplier));
			}

			trainer.getTeam().getPokemons().get(aux3).setHP(trainer.getPrimary().getHP()); //Actualiza la tabla.
		} else {
			trainer.setFeedback(trainer.getPrimary().getName() + " no puede combatir");
		}

		if (trainer.getPrimary().getHP() <= 0) {
			trainer.getTeam().getPokemons().get(aux3).setHP(0);
			trainer.getTeam().getPokemons().get(aux3).setStatus("Muerto");
			trainer.getPrimary().setHP(0);
			trainer.getPrimary().setStatus("Muerto");
		}

		if (trainer.getWildPokemon().getStatus() == "Durmiendo") {
			contSleep += 1;
		}
		if (contSleep < 4 && trainer.getWildPokemon().getStatus() == "Durmiendo") {
			trainer.setFeedback("El pokemon salvaje no puede atacar porque esta durmiendo");
		} else {
			contSleep = 0;
			trainer.getWildPokemon().setStatus("Vivo");
		}

		if (trainer.getWildPokemon().getHP() <= 0) {
			trainer.getWildPokemon().setHP(0);
			trainer.getWildPokemon().setStatus("Muerto");
			trainer.setFeedback(trainer.getWildPokemon().getName() + " se ha debilitado");
			createEnemy(trainerForm);
		}

		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("trainer", this.trainer);

		return modelAndView;
	}

	@PostMapping("falseAttack")
	public ModelAndView falseAttack(Trainer trainerForm) {
		if (trainer.getPrimary().getStatus() == "Vivo") {
			trainer.getWildPokemon()
				.setHP(trainer.getWildPokemon().getHP() - (int) (trainer.getPrimary().getAttack() * 0.8)); //FalsoTortazo es un 20% menos potente que un ataque normal
			trainer.getPrimary().setHP(trainer.getPrimary().getHP() - trainer.getWildPokemon().getAttack());
		} else {
			trainer.setFeedback(trainer.getPrimary().getName() + " no puede combatir");
		}

		if (trainer.getPrimary().getHP() <= 0) {
			trainer.getPrimary().setHP(0);
			trainer.getPrimary().setStatus("Muerto");
		}

		if (trainer.getWildPokemon().getHP() <= 0) {
			trainer.getWildPokemon().setHP(1);
		}
		if (trainer.getWildPokemon().getStatus() == "Durmiendo") {
			contSleep += 1;
		}

		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("trainer", this.trainer);
		return modelAndView;
	}

	@PostMapping("heal")
	public ModelAndView heal(Trainer trainerForm) {
		if (trainer.getPrimary().getStatus() == "Vivo") {
			if (trainer.getPrimary().getHP() < trainer.getPrimary().getMaxHP()) {
				if (trainer.getPrimary().getHP() + 50 > trainer.getPrimary().getMaxHP()) {
					trainer.getPrimary().setHP(trainer.getPrimary().getMaxHP());
					trainer.setFeedback(trainer.getPrimary().getName() + " se ha curado 50 HP");
				} else {
					trainer.getPrimary().setHP(trainer.getPrimary().getHP() + 50);
					trainer.setFeedback(trainer.getPrimary().getName() + " se ha curado 50 HP");
				}
			} else {
				trainer.setFeedback(trainer.getPrimary().getName() + " no se puede curar, tiene toda la vida");
			}
		} else
			trainer.setFeedback(trainer.getPrimary().getName() + " no se puede curar, está debilitado");

		if (trainer.getWildPokemon().getStatus() == "Durmiendo") {
			contSleep += 1;
		}

		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("trainer", this.trainer);
		return modelAndView;
	}

	@PostMapping("capture")
	public ModelAndView capture(Trainer trainerForm) {
		int random = (int) (Math.random() * 100);
		if (random <= trainer.getBall().getCapturePower()) {
			trainer.getPokedex().addPokemon(trainer.getWildPokemon());
			trainer.setFeedback("El pokemon salvaje fue capturado");
			createEnemy(trainerForm);
		} else
			trainer.setFeedback("El pokemon salvaje se ha escapado");

		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("trainer", this.trainer);
		return modelAndView;
	}

	@PostMapping("sleep")
	public ModelAndView sleep(Trainer trainerForm) {
		int random = (int) (Math.random() * 100); //Aleatorio para dormir.
		if (random >= 40 && trainer.getWildPokemon().getStatus() != "Durmiendo") {
			trainer.getWildPokemon().setStatus("Durmiendo");
			contSleep += 1;
			trainer.setFeedback(trainer.getWildPokemon().getName() + " se ha puesto a dormir");
		} else {
			if (random <= 40) {
				trainer.setFeedback("El somnífero ha fallado");
				trainer.getPrimary().setHP(
					trainer.getPrimary().getHP() - (int) ((trainer.getWildPokemon().getAttack()) * (1 / multiplier)));
			} else {
				trainer.setFeedback("El pokemon ya se encuentra dormido");
			}
		}

		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("trainer", this.trainer);
		return modelAndView;
	}

}
