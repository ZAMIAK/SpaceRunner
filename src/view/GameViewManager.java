package view;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.SHIP;
import model.SmallInfoLabel;

import java.nio.file.Paths;
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
	
	private static final String STAR_SONG = "src/view/resources/key.mp3";
	private static final String METEOR_SONG = "src/view/resources/win.mp3";
	private MediaPlayer starSong;
	private MediaPlayer meteorSong;
	private static final double VOLUME_SONG = 0.1;

	private ImageView[] brownMeteorsSmall;
	private ImageView[] greyMeteorsSmall;
	Random randomPositionGenerator;

	private ImageView star;
	private SmallInfoLabel pointsLabel;
	private ImageView[] playerLifes;
	private int playerLife;
	private int points;
	private final static String GOLD_STAR = "view/resources/star_gold.png";

	private final static int STAR_RADIUS = 12;
	private final static int SHIP_RADIUS = 27;
	private final static int METEOR_RADIUS = 20;
	private final static double DROP_STAR = 1.1;

	public GameViewManager() {
		initializeStage();
		createKeyListeners();
		randomPositionGenerator = new Random();
		createSongs();
	}
	
	private void createSongs() {
		Media myStartSong = new Media(Paths.get(STAR_SONG).toUri().toString());
		starSong = new MediaPlayer(myStartSong);
		starSong.setVolume(VOLUME_SONG);
		
		Media myMeteorSong = new Media(Paths.get(METEOR_SONG).toUri().toString());
		meteorSong = new MediaPlayer(myMeteorSong);
		meteorSong.setVolume(VOLUME_SONG);
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
		//gameStage.setResizable(false);
		//gameStage.initStyle(StageStyle.UNDECORATED);
	}
	
	public void createNewGame(Stage menuStage, SHIP choosenShip) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		createBackground();
		createShip(choosenShip);
		createGameElements(choosenShip);
		createGameLoop();
		gameStage.show();
	}

	private void createGameElements(SHIP choosenShip){
		playerLife = 2;
		star = new ImageView(GOLD_STAR);
		setNewElementPosition(star);
		gamePane.getChildren().add(star);
		pointsLabel = new SmallInfoLabel("PTS : 00");
		pointsLabel.setLayoutX(460);
		pointsLabel.setLayoutY(20);
		gamePane.getChildren().add(pointsLabel);
		playerLifes = new ImageView[3];

		for(int i = 0; i <playerLifes.length;i++){
			playerLifes[i] = new ImageView((choosenShip.getUrlLife()));
			playerLifes[i].setLayoutX(455+(i*50));
			playerLifes[i].setLayoutY(80);
			gamePane.getChildren().add(playerLifes[i]);

		}
		
		setGameDificult(3);
	}
	
	private void setGameDificult(int num) {
		
		if(brownMeteorsSmall != null)
			gamePane.getChildren().removeAll(brownMeteorsSmall);
		if(greyMeteorsSmall != null)
			gamePane.getChildren().removeAll(greyMeteorsSmall);
		
		brownMeteorsSmall = new ImageView[num];
		for(int i =0; i<brownMeteorsSmall.length; i++){
			brownMeteorsSmall[i] = new ImageView(METEOR_BROWN_IMAGE_SMALL);
			setNewElementPosition(brownMeteorsSmall[i]);
			gamePane.getChildren().add(brownMeteorsSmall[i]);
		}

		greyMeteorsSmall = new ImageView[num];
		for(int i =0; i<greyMeteorsSmall.length; i++){
			greyMeteorsSmall[i] = new ImageView(METEOR_GREY_IMAGE_SMALL);
			setNewElementPosition(greyMeteorsSmall[i]);
			gamePane.getChildren().add(greyMeteorsSmall[i]);
		}
	}

	private void moveGameElements(){
		star.setLayoutY(star.getLayoutY() + 5);
		for(int i =0; i < brownMeteorsSmall.length; i++){
			brownMeteorsSmall[i].setLayoutY(brownMeteorsSmall[i].getLayoutY()+12);
			brownMeteorsSmall[i].setRotate(brownMeteorsSmall[i].getRotate()+6);
		}
		for(int i =0; i < greyMeteorsSmall.length; i++){
			greyMeteorsSmall[i].setLayoutY(greyMeteorsSmall[i].getLayoutY()+12);
			greyMeteorsSmall[i].setRotate(greyMeteorsSmall[i].getRotate()+6);
		}
		points++;
		String textToSet = "PTS : ";
		if(points <10){
			textToSet = textToSet + "0";
		}
		pointsLabel.setText(textToSet + points);
	}

	private void checkIfElementsAreBehindTheShipAndRelocate(){
		if(star.getLayoutY() > 1200){
			setNewElementPosition(star);
		}
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
		image.setLayoutX(randomPositionGenerator.nextInt(580));
		image.setLayoutY(-(randomPositionGenerator.nextInt(3200)+600));
	}
	
	private void createShip(SHIP choosenShip) {
		ship = new ImageView(choosenShip.getUrl());
		ship.setLayoutX(GAME_WIDTH / 2.5);
		ship.setLayoutY(GAME_HEIGHT - 200);
		gamePane.getChildren().add(ship);
	}

	private void createGameLoop(){
		gameTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if(points > 1000 && points < 1100) {
					setGameDificult(10);
				}else if(points > 3000 && points < 3100) {
					setGameDificult(15);
				}else if(points > 5000 && points < 5100) {
					setGameDificult(20);
				}else if(points > 10000 && points < 10100) {
					setGameDificult(25);
				}
				moveBackground();
				moveGameElements();
				checkIfElementsAreBehindTheShipAndRelocate();
				checkIfElementsCollides();
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
				ship.setLayoutX(ship.getLayoutX() - 10);
			}
		}
		if(isRightKeyPressed && !isLeftKeyPressed){
			if(angle < 30){
				angle += 5;
		}
			ship.setRotate(angle);
			if(ship.getLayoutX() < 522){
				ship.setLayoutX(ship.getLayoutX() + 10);
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

	private void checkIfElementsCollides(){
		if(SHIP_RADIUS + STAR_RADIUS > calculateDistance(ship.getLayoutX()+49,star.getLayoutX()+15,ship.getLayoutY()+37, star.getLayoutY()+15)){
			setNewElementPosition(star);
			points *= DROP_STAR;
			String textToSet = "PTS : ";
			if(points <10){
				textToSet = textToSet + "0";
			}
			pointsLabel.setText(textToSet + points);
			starSong.play();
			createSongs();
		}

		for(int i = 0; i < brownMeteorsSmall.length; i++){
			if(METEOR_RADIUS + SHIP_RADIUS > calculateDistance(ship.getLayoutX()+49,brownMeteorsSmall[i].getLayoutX()+20,ship.getLayoutY()+37, brownMeteorsSmall[i].getLayoutY()+20)){
				removeLife();
				setNewElementPosition(brownMeteorsSmall[i]);
			}
		}

		for(int i = 0; i < greyMeteorsSmall.length; i++){
			if(METEOR_RADIUS + SHIP_RADIUS > calculateDistance(ship.getLayoutX()+49,greyMeteorsSmall[i].getLayoutX()+20,ship.getLayoutY()+37, greyMeteorsSmall[i].getLayoutY()+20)){
				removeLife();
				setNewElementPosition(greyMeteorsSmall[i]);
			}
		}
	}

	private void removeLife(){
		gamePane.getChildren().remove(playerLifes[playerLife]);
		playerLife--;
		meteorSong.play();
		createSongs();
		if(playerLife<0){
			gameStage.close();
			gameTimer.stop();
			menuStage.show();
		}
	}

	private double calculateDistance(double x1, double x2, double y1, double y2){
		return Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2));
	}
}
