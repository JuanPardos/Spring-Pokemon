package es.salesianos.model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTeam implements Team {
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

	@Override
	public boolean isFull() {
		if (sumPokemons() == capacity)
			return true;
		else
			return false;
	}

	@Override
	public void addPokemon(Pokemon pokemon) {
		if (isFull()) {
			System.out.println("Team is full");
		} else {
			pokemons.add(pokemon);
		}
	}
}
