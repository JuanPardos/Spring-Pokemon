package es.salesianos.model;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("masterball")
public class Masterball extends AbstractPokeball {

	public Masterball() {
		capturePower = 100;
		name = "Masterball";
	}
}
