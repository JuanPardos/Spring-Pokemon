package es.salesianos.model;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("pokeball")
public class Pokeball extends AbstractPokeball {

	public Pokeball() {
		capturePower = (float) 0.7;
	}
}
