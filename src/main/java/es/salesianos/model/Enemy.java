package es.salesianos.model;

import org.springframework.stereotype.Component;

@Component
public class Enemy {

	private Pokemon pokemon;

	public Pokemon getPokemon() {
		return pokemon;
	}
	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}
}
