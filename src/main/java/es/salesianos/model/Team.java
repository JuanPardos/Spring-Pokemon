package es.salesianos.model;

import org.springframework.stereotype.Component;

@Component
public interface Team {

	public boolean isFull();
	public void addPokemon(Pokemon pokemon);
	public Pokemon setAttackingPokemon(int a);
	public void removePokemonById(int b);

}
