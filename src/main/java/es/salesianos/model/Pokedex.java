package es.salesianos.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Pokedex")
public class Pokedex {
	protected List<Pokemon> pokemons = new ArrayList<Pokemon>();

	public List<Pokemon> getPokemons() {
		return pokemons;
	}

	public void setPokemons(List<Pokemon> pokemons) {
		this.pokemons = pokemons;
	}

	public void addPokemon(Pokemon pokemon) {
		pokemons.add(pokemon);
	}

}
