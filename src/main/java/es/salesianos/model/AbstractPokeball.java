package es.salesianos.model;

public class AbstractPokeball implements Ball {

	public float capturePower;

	@Override
	public float getCapturePower() {
		return capturePower;
	}

	public void setCapturePower(float capturePower) {
		this.capturePower = capturePower;
	}

}
