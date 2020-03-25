package view;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.InfoLabel;
import model.SHIP;
import model.ShipPicker;
import model.SpaceRunnerButton;
import model.SpaceRunnerSubscene;

public class ViewManager {
	
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 768;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	private final static int MENU_BUTTONS_START_X = 100;
	private final static int MENU_BUTTONS_START_Y = 150;
	
	private static final String MUSIC = "src/view/resources/Recall_of_the_Shadows.mp3";
	private MediaPlayer music;
	
	private SpaceRunnerSubscene startSubScene;
	private SpaceRunnerSubscene scoresSubScene;
	private SpaceRunnerSubscene histSubScene;
	private SpaceRunnerSubscene creditsSubScene;
	private SpaceRunnerSubscene sceneToHide;
	
	List<SpaceRunnerButton> menuButtons;
	List<ShipPicker> shipList;
	
	private SHIP choosenShip;
	
	public ViewManager() {
		
		menuButtons = new ArrayList<>();
		mainPane= new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		createSubScenes();
		createButtons();
		createBackground();
		createLogo();
		startMusic();
	}
	
	public Stage getMainStage() {
		
		return mainStage;
	}
	
	private void startMusic() {
		
		Media backgroundMusic = new Media(Paths.get(MUSIC).toUri().toString());
		music = new MediaPlayer(backgroundMusic);
		music.setVolume(0.07);
		music.play();
		
	}
	
	private void showSubScene(SpaceRunnerSubscene subScene) {
		if(sceneToHide != null) {
			sceneToHide.moveSubScene();
		}
		
		subScene.moveSubScene();
		sceneToHide = subScene;
	}
	
	private void createSubScenes() {
		
		startSubScene = new SpaceRunnerSubscene();
		mainPane.getChildren().add(startSubScene);
		
		scoresSubScene = new SpaceRunnerSubscene();
		mainPane.getChildren().add(scoresSubScene);
		
		histSubScene = new SpaceRunnerSubscene();
		mainPane.getChildren().add(histSubScene);
		
		creditsSubScene = new SpaceRunnerSubscene();
		mainPane.getChildren().add(creditsSubScene);
		
		createShipChooserSubScene();
	}
	
	private void createShipChooserSubScene() {
		
		startSubScene = new SpaceRunnerSubscene();
		mainPane.getChildren().add(startSubScene);
		
		InfoLabel chooseShipLabel = new InfoLabel("CHOIX DU VAISSEAU");
		chooseShipLabel.setLayoutX(110);
		chooseShipLabel.setLayoutY(40);
		startSubScene.getPane().getChildren().add(chooseShipLabel);
		startSubScene.getPane().getChildren().add(createShipsToChoose());
		startSubScene.getPane().getChildren().add(createButtonToStart());
	}

	private HBox createShipsToChoose() {
		HBox box = new HBox();
		box.setSpacing(20);
		shipList = new ArrayList<>();
		for(SHIP ship : SHIP.values()) {
			ShipPicker shipToPick = new ShipPicker(ship);
			shipList.add(shipToPick);
			box.getChildren().add(shipToPick);
			shipToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					for(ShipPicker ship : shipList) {
						ship.setIsCircleChoosen(false);
					}
					shipToPick.setIsCircleChoosen(true);
					choosenShip = shipToPick.getShip();
				}
			});
		}
		box.setLayoutX(280 - (118 * 2));
		box.setLayoutY(130);
		return box;
	}
	
	private SpaceRunnerButton createButtonToStart() {
		SpaceRunnerButton startButton = new SpaceRunnerButton("LANCER");
		startButton.setLayoutX(350);
		startButton.setLayoutY(300);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arevent) {
				// TODO Auto-generated method stub
				if(choosenShip != null) {
					GameViewManager gameManager = new GameViewManager();
					gameManager.createNewGame(mainStage, choosenShip);
				}
			}
			
		});
		
		return startButton;
	}
	
	
	
	private void addMenuButton(SpaceRunnerButton button) {
		button.setLayoutX(MENU_BUTTONS_START_X);
		button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	}
	
	
	
	private void createButtons() {
		
		createStartButton();
		createScoresButton();
		createHistButton();
		createCreditsButton();
		createExitButton();
	}
	
	private void createStartButton() {
		SpaceRunnerButton startButton = new SpaceRunnerButton("JOUER");
		addMenuButton(startButton);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(startSubScene);
			}
			
		});
	}
	
	private void createScoresButton() {
		SpaceRunnerButton scoresButton = new SpaceRunnerButton("SCORES");
		addMenuButton(scoresButton);
		
		scoresButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(scoresSubScene);
			}
			
		});
	}
	
	private void createHistButton() {
		SpaceRunnerButton histButton = new SpaceRunnerButton("HISTOIRE");
		addMenuButton(histButton);
		
		histButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(histSubScene);
			}
			
		});
	}
	
	private void createCreditsButton() {
		SpaceRunnerButton creditsButton = new SpaceRunnerButton("CREDITS");
		addMenuButton(creditsButton);
		
		creditsButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(creditsSubScene);
			}
			
		});
	}
	
	private void createExitButton() {
		SpaceRunnerButton exitButton = new SpaceRunnerButton("QUITTER");
		addMenuButton(exitButton);
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				mainStage.close();
			}
			
		});
	}
	
	private void createBackground() {
		Image backgroundImage = new Image("view/resources/back.png");
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.SPACE, BackgroundRepeat.SPACE, BackgroundPosition.CENTER, null);
		mainPane.setBackground(new Background(background));
	}
	
	private void createLogo() {
		
		ImageView logo = new ImageView("view/resources/title.png");
		logo.setLayoutX(380);
		logo.setLayoutY(50);
		
		logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				logo.setEffect(new DropShadow());
			}
		});
		
		logo.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				logo.setEffect(null);
			}
		});
		
		mainPane.getChildren().add(logo);
	}

}
