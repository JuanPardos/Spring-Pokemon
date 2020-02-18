package es.salesianos.model;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("ultraball")
public class Ultraball extends AbstractPokeball {

	public Ultraball() {
		capturePower = 50;
	}
}
