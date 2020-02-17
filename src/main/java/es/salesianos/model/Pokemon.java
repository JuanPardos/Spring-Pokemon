package es.salesianos.model;

public class Pokemon {

	private String name;
	private int level;
	private int attack;
	private int maxHP;
	private String status = "Vivo";
	private int HP = maxHP;
	private int captureRate;
	private String type;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getHP() {
		return HP;
	}
	public void setHP(int hP) {
		HP = hP;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getMaxHP() {
		return maxHP;
	}
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCaptureRate() {
		return captureRate;
	}
	public void setCaptureRate(int captureRate) {
		this.captureRate = captureRate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
