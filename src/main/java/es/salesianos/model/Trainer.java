package es.salesianos.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Trainer {
	private String name;
	private int aux;
	private int aux2;

	@Autowired
	private Team team;

	@Autowired
	private Ball ball;

	private Pokemon primary;
	private Pokemon secondary;
	private Pokemon pokemon;
	private Pokemon wildPokemon;

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
	public Pokemon getPrimary() {
		return primary;
	}
	public void setPrimary(Pokemon primary) {
		this.primary = primary;
	}
	public Pokemon getSecondary() {
		return secondary;
	}
	public void setSecondary(Pokemon secondary) {
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
	public int getAux2() {
		return aux2;
	}
	public void setAux2(int aux2) {
		this.aux2 = aux2;
	}
	public Pokemon getWildPokemon() {
		return wildPokemon;
	}
	public void setWildPokemon(Pokemon wildPokemon) {
		this.wildPokemon = wildPokemon;
	}
	public Ball getBall() {
		return ball;
	}
	public void setBall(Ball ball) {
		this.ball = ball;
	}

}
