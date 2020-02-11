package es.salesianos.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Trainer {
	private String name;
	private int aux;

	@Autowired
	private Team team;

	private PokeAttacking primary;
	private PokeAttacking secondary;
	private Pokemon pokemon;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public PokeAttacking getPrimary() {
		return primary;
	}
	public void setPrimary(PokeAttacking primary) {
		this.primary = primary;
	}
	public PokeAttacking getSecondary() {
		return secondary;
	}
	public void setSecondary(PokeAttacking secondary) {
		this.secondary = secondary;
	}
	public Pokemon getPokemon() {
		return pokemon;
	}
	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}
	public int getAux() {
		return aux;
	}
	public void setAux(int aux) {
		this.aux = aux;
	}

}
