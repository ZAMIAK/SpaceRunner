package model;

public enum SHIP {

	SHIPONE("view/resources/shipchooser/spaceShips_001.png", "view/resources/shipchooser/shiplife/spaceShips_001.png"),
	SHIPTWO("view/resources/shipchooser/spaceShips_002.png", "view/resources/shipchooser/shiplife/spaceShips_002.png"),
	SHIPTREE("view/resources/shipchooser/spaceShips_005.png", "view/resources/shipchooser/shiplife/spaceShips_005.png"),
	SHIPFOUR("view/resources/shipchooser/spaceShips_009.png", "view/resources/shipchooser/shiplife/spaceShips_009.png");
	
	private String urlShip;
	private String urlLife;
	
	private SHIP(String urlShip, String urlLife) {
		this.urlShip = urlShip;
		this.urlLife = urlLife;
	}
	
	public String getUrl() {
		return this.urlShip;
	}

	public String getUrlLife() {
		return urlLife;
	}
}
