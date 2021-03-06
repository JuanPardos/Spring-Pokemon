package es.salesianos.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Team")
public class Team {
	protected List<Pokemon> pokemons = new ArrayList<Pokemon>();

	public int capacity = 6;

	private int sumPokemons() {
		int sum = 0;
		for (Pokemon pokemon : pokemons) {
			try {
				sum += 1;
			} catch (Exception e) {
			}
		}
		return sum;
	}

	public List<Pokemon> getPokemons() {
		return pokemons;
	}

	public void setPokemons(List<Pokemon> pokemons) {
		this.pokemons = pokemons;
	}

	public Pokemon setAttackingPokemon(int a) {
		Pokemon poke = new Pokemon();

		poke.setAttack(pokemons.get(a).getAttack());
		poke.setLevel(pokemons.get(a).getLevel());
		poke.setMaxHP(pokemons.get(a).getMaxHP());
		poke.setHP(pokemons.get(a).getHP());
		poke.setName(pokemons.get(a).getName());
		poke.setType(pokemons.get(a).getType());
		poke.setStatus(pokemons.get(a).getStatus());

		return poke;
	}

	public void removePokemonById(int b) {
		pokemons.remove(b);
	}

	public boolean isFull() {
		if (sumPokemons() == capacity)
			return true;
		else
			return false;
	}

	public void addPokemon(Pokemon pokemon) {
		if (isFull()) {
			System.out.println("El equipo esta completo");
		} else {
			pokemons.add(pokemon);
		}
	}

}
