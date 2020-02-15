package es.salesianos.model;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("superball")
public class Superball extends AbstractPokeball {

	public Superball() {
		capturePower = (float) 0.85;
	}
}
