package es.salesianos.model;

public class AbstractPokeball implements Ball {

	public float capturePower;
	public String name; //Usado en el index en el if, para sacar el perfil activo.

	@Override
	public float getCapturePower() {
		return capturePower;
	}

	public void setCapturePower(float capturePower) {
		this.capturePower = capturePower;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
