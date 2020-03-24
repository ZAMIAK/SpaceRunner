package model;

public enum SHIP {

	SHIPONE("view/resources/shipchooser/spaceShips_001.png"),
	SHIPTWO("view/resources/shipchooser/spaceShips_002.png"),
	SHIPTREE("view/resources/shipchooser/spaceShips_005.png"),
	SHIPFOUR("view/resources/shipchooser/spaceShips_009.png");
	
	private String urlShip;
	
	private SHIP(String urlShip) {
		this.urlShip = urlShip;
	}
	
	public String getUrl() {
		return this.urlShip;
	}
}
