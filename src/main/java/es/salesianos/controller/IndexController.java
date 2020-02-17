package es.salesianos.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.internal.compiler.ast.BreakStatement;
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
	private static Logger log = LogManager.getLogger(IndexController.class);
	private double multiplier = 1.0;
	private int aux3; //Se usa para actualizar la tabla cuando los pokemons reciben daño.

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
			System.out.println("El nivel del pokemon no es correcto");
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
		enemyPokemon.setCaptureRate((int) (Math.random() * 40) + 70); //Valores entre 70 y 110.
		enemyPokemon.setStatus("Vivo");
		enemyPokemon.setType(tipos[(int) (Math.random() * 3)]);
	}

	@PostMapping("switchPokemon")
	public ModelAndView switchPokemon(Trainer trainerForm) {
		Pokemon tmp;
		aux3 = trainerForm.getAux();

		tmp = this.trainer.getPrimary();
		if (this.trainer.getTeam().setAttackingPokemon(trainerForm.getAux()).getStatus() == "Vivo") {
			this.trainer.setPrimary(this.trainer.getTeam().setAttackingPokemon(trainerForm.getAux()));
		} else
			System.out.println("El pokemon esta debilitado, no puede pelear");

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
		getCombatMultiplier();
		if (trainer.getPrimary().getStatus() == "Vivo") {
			trainer.getWildPokemon()
				.setHP(trainer.getWildPokemon().getHP() - (int) ((trainer.getPrimary().getAttack()) * multiplier));
			trainer.getPrimary().setHP(
				trainer.getPrimary().getHP() - (int) ((trainer.getWildPokemon().getAttack()) * (1 / multiplier)));

			trainer.getTeam().getPokemons().get(aux3).setHP(trainer.getPrimary().getHP()); //Actualiza la tabla.
		} else {
			System.out.println(trainer.getPrimary().getName() + " no puede combatir");
		}

		if (trainer.getPrimary().getHP() <= 0) {
			trainer.getTeam().getPokemons().get(aux3).setHP(0);
			trainer.getTeam().getPokemons().get(aux3).setStatus("Muerto");
			trainer.getPrimary().setHP(0);
			trainer.getPrimary().setStatus("Muerto");
		}

		if (trainer.getWildPokemon().getHP() <= 0) {
			trainer.getWildPokemon().setHP(0);
			trainer.getWildPokemon().setStatus("Muerto");
			System.out.println("Has debilitado al pokemon enemigo !");
			createEnemy(trainerForm);
		}

		System.out.println(multiplier);

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
			System.out.println(trainer.getPrimary().getName() + " no puede combatir");
		}

		if (trainer.getPrimary().getHP() <= 0) {
			trainer.getPrimary().setHP(0);
			trainer.getPrimary().setStatus("Muerto");
		}

		if (trainer.getWildPokemon().getHP() <= 0) {
			trainer.getWildPokemon().setHP(1);
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
				} else {
					trainer.getPrimary().setHP(trainer.getPrimary().getHP() + 50);
				}
			} else {
				System.out.println(trainer.getPrimary().getName() + " no se puede curar, tiene toda la vida");
			}
		} else
			System.out.println(trainer.getPrimary().getName() + " no se puede curar, está debilitado");

		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("trainer", this.trainer);
		return modelAndView;
	}

	@PostMapping("capture")
	public ModelAndView capture(Trainer trainerForm) {
		float RNG = (int) ((Math.random() * 50) + 20); //Saca numero entre 20 y 70, sirve para la captura.
		int LuckyCapture = (int) ((Math.random() * 25)); //Numeros del 1 al 25.

		if (LuckyCapture == 1) { //Captura critica, 4% de probabilidad. Sin importar que pokeball uses o vida del enemigo que lo puedes capturar.
			System.out.println("!El pokemon ha sido captura mediante Captura Crítica! ");
			if (!trainer.getTeam().isFull()) {
				this.trainer.getTeam().addPokemon(trainer.getWildPokemon());
				createEnemy(trainerForm);
			} else
				System.out.println("No se puede añadir al equipo, esta completo");
		} else {
			if (RNG * (trainer.getBall().getCapturePower())
				+ ((((float) (trainer.getWildPokemon().getMaxHP() - (float) trainer.getWildPokemon().getHP())
					/ (float) (trainer.getWildPokemon().getMaxHP())) * 100) * 0.7) >= trainer.getWildPokemon()
						.getCaptureRate()) { //Tiene en cuenta el RNG, el tipo de pokeball, la vida perdida y el indice de captura del enemigo.
				System.out.println("El pokemon ha sido capturado");
				if (!trainer.getTeam().isFull()) {
					this.trainer.getTeam().addPokemon(trainer.getWildPokemon());
					createEnemy(trainerForm);
				} else
					System.out.println("No se puede añadir al equipo, esta completo");
			} else {
				if (this.trainer.getPrimary().getStatus() != "Muerto") {
					System.out.println("El pokemon se ha escapado");

					//Solo para DEBUGG, muestra en consola el valor de captura.
					System.out.println(RNG * (trainer.getBall().getCapturePower())
						+ ((((float) (trainer.getWildPokemon().getMaxHP() - (float) trainer.getWildPokemon().getHP())
							/ (float) (trainer.getWildPokemon().getMaxHP())) * 100) * 0.7)
						+ " < " + trainer.getWildPokemon().getCaptureRate());

					trainer.getPrimary().setHP(trainer.getPrimary().getHP() - trainer.getWildPokemon().getAttack());
					if (trainer.getPrimary().getHP() <= 0) {
						trainer.getPrimary().setHP(0);
						trainer.getPrimary().setStatus("Muerto");
					}
				} else
					System.out.println("Cambia de pokemon para seguir capturando");
			}
		}

		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("trainer", this.trainer);
		return modelAndView;
	}

	//NO FUNCIONA !
	public void getCombatMultiplier() {
		if (this.trainer.getPrimary().getType() == "Planta") {
			switch (this.trainer.getWildPokemon().getType()) {
				case "Agua" :
					multiplier = 2.0;
					break;
				default :
					multiplier = 0.5;
					break;
			}
		}

		if (this.trainer.getPrimary().getType() == "Fuego") {
			switch (this.trainer.getWildPokemon().getType()) {
				case "Planta" :
					multiplier = 2.0;
					break;
				default :
					multiplier = 0.5;
					break;
			}
		}

		if (this.trainer.getPrimary().getType() == "Agua") {
			switch (this.trainer.getWildPokemon().getType()) {
				case "Fuego" :
					multiplier = 2.0;
					break;
				default :
					multiplier = 0.5;
					break;
			}
		}

	}
}
