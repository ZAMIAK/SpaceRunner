package view;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.SHIP;

import java.util.Random;

public class GameViewManager {
	
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	
	private static final int GAME_WIDTH = 600;
	private static final int GAME_HEIGHT = 800;
	
	private Stage menuStage;
	private ImageView ship;

	private boolean isLeftKeyPressed;
	private boolean isRightKeyPressed;
	private int angle;
	private AnimationTimer gameTimer;

	private GridPane gridPane1;
	private GridPane gridPane2;
	private final static String BACKGROUND_IMAGE  = "view/resources/back.png";

	private final static String METEOR_BROWN_IMAGE_SMALL = "view/resources/spaceMeteorsBrownSmall.png";
	private final static String METEOR_GREY_IMAGE_SMALL = "view/resources/spaceMeteorsGreySmall.png";

	private ImageView[] brownMeteorsSmall;
	private ImageView[] greyMeteorsSmall;
	Random randomPositionGenerator;

	public GameViewManager() {
		initializeStage();
		createKeyListeners();
		randomPositionGenerator = new Random();
	}

	private void createKeyListeners() {
		
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if(event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = true;
				}else if (event.getCode() == KeyCode.RIGHT){
					isRightKeyPressed = true;
				}
			}
			
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if(event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = false;
				}else if (event.getCode() == KeyCode.RIGHT){
					isRightKeyPressed = false;
				}
			}
			
		});
	}

	private void initializeStage() {
		
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
		gameStage = new Stage();
		gameStage.setScene(gameScene);
	}
	
	public void createNewGame(Stage menuStage, SHIP choosenShip) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		createBackground();
		createShip(choosenShip);
		createGameElements();
		createGameLoop();
		gameStage.show();
	}

	private void createGameElements(){
		brownMeteorsSmall = new ImageView[3];
		for(int i =0; i<brownMeteorsSmall.length; i++){
			brownMeteorsSmall[i] = new ImageView(METEOR_BROWN_IMAGE_SMALL);
			setNewElementPosition(brownMeteorsSmall[i]);
			gamePane.getChildren().add(brownMeteorsSmall[i]);
		}

		greyMeteorsSmall = new ImageView[3];
		for(int i =0; i<greyMeteorsSmall.length; i++){
			greyMeteorsSmall[i] = new ImageView(METEOR_GREY_IMAGE_SMALL);
			setNewElementPosition(greyMeteorsSmall[i]);
			gamePane.getChildren().add(greyMeteorsSmall[i]);
		}
	}

	private void moveGameElements(){
		for(int i =0; i < brownMeteorsSmall.length; i++){
			brownMeteorsSmall[i].setLayoutY(brownMeteorsSmall[i].getLayoutY()+7);
			brownMeteorsSmall[i].setRotate(brownMeteorsSmall[i].getRotate()+4);
		}
		for(int i =0; i < greyMeteorsSmall.length; i++){
			greyMeteorsSmall[i].setLayoutY(greyMeteorsSmall[i].getLayoutY()+7);
			greyMeteorsSmall[i].setRotate(greyMeteorsSmall[i].getRotate()+4);
		}
	}

	private void checkIfElementsAreBehindTheShipAndRelocate(){
		for(int i =0; i < brownMeteorsSmall.length; i++){
			if(brownMeteorsSmall[i].getLayoutY() >900){
				setNewElementPosition(brownMeteorsSmall[i]);
			}
		}

		for(int i =0; i < greyMeteorsSmall.length; i++){
			if(greyMeteorsSmall[i].getLayoutY() >900){
				setNewElementPosition(greyMeteorsSmall[i]);
			}
		}
	}

	private void setNewElementPosition(ImageView image){
		image.setLayoutX(randomPositionGenerator.nextInt(370));
		image.setLayoutY(-(randomPositionGenerator.nextInt(3200)+600));
	}
	
	private void createShip(SHIP choosenShip) {
		ship = new ImageView(choosenShip.getUrl());
		ship.setLayoutX(GAME_WIDTH / 2.5);
		ship.setLayoutY(GAME_HEIGHT - 90);
		gamePane.getChildren().add(ship);
	}

	private void createGameLoop(){
		gameTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				moveBackground();
				moveGameElements();
				checkIfElementsAreBehindTheShipAndRelocate();
				moveShip();
			}
		};
		gameTimer.start();
	}

	private void moveShip(){
		if(isLeftKeyPressed && !isRightKeyPressed){
			if(angle > -30){
				angle -= 5;
			}
			ship.setRotate(angle);
			if(ship.getLayoutX() > -20){
				ship.setLayoutX(ship.getLayoutX() - 3);
			}
		}
		if(isRightKeyPressed && !isLeftKeyPressed){
			if(angle < 30){
				angle += 5;
		}
			ship.setRotate(angle);
			if(ship.getLayoutX() < 522){
				ship.setLayoutX(ship.getLayoutX() + 3);
			}
		}
		if(!isLeftKeyPressed && !isRightKeyPressed){
			if(angle < 0){
				angle +=5;
			}else if(angle > 0){
				angle -=5;
			}

		}
		if(isLeftKeyPressed && isRightKeyPressed){
			if(angle < 0){
				angle +=5;
			}else if(angle > 0){
				angle -=5;
			}
			ship.setRotate(angle);
		}
	}

	private void createBackground(){
		gridPane1 = new GridPane();
		gridPane2 = new GridPane();

		for(int i = 0; i<12; i++){
			ImageView backgroundImage1 = new ImageView(BACKGROUND_IMAGE);
			ImageView backgroundImage2 = new ImageView(BACKGROUND_IMAGE);
			GridPane.setConstraints(backgroundImage1, i%3, i/3);
			GridPane.setConstraints(backgroundImage2, i%3, i/3);
			gridPane1.getChildren().add(backgroundImage1);
			gridPane2.getChildren().add(backgroundImage2);
		}
		gridPane2.setLayoutY(-1024);
		gamePane.getChildren().addAll(gridPane1, gridPane2);
	}

	private void moveBackground(){
		gridPane1.setLayoutY(gridPane1.getLayoutY() +0.5);
		gridPane2.setLayoutY(gridPane2.getLayoutY() +0.5);

		if(gridPane1.getLayoutY() >= 1024){
			gridPane1.setLayoutY(-1024);
		}

		if(gridPane2.getLayoutY() >= 1024){
			gridPane2.setLayoutY(-1024);
		}
	}
}
