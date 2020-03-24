package view;

import java.util.ArrayList;
import java.util.List;

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
import javafx.stage.Stage;
import model.SpaceRunnerButton;

public class ViewManager {
	
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 768;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	private final static int MENU_BUTTONS_START_X = 100;
	private final static int MENU_BUTTONS_START_Y = 150;
	
	List<SpaceRunnerButton> menuButtons;
	
	public ViewManager() {
		
		menuButtons = new ArrayList<>();
		mainPane= new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		createButtons();
		createBackground();
		createLogo();
	}
	
	public Stage getMainStage() {
		
		return mainStage;
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
	}
	
	private void createScoresButton() {
		SpaceRunnerButton scoresButton = new SpaceRunnerButton("SCORES");
		addMenuButton(scoresButton);
	}
	
	private void createHistButton() {
		SpaceRunnerButton histButton = new SpaceRunnerButton("HISTOIRE");
		addMenuButton(histButton);
	}
	
	private void createCreditsButton() {
		SpaceRunnerButton creditsButton = new SpaceRunnerButton("CREDITS");
		addMenuButton(creditsButton);
	}
	
	private void createExitButton() {
		SpaceRunnerButton exitButton = new SpaceRunnerButton("QUITTER");
		addMenuButton(exitButton);
	}
	
	private void createBackground() {
		Image backgroundImage = new Image("view/resources/back.png");
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.SPACE, BackgroundRepeat.SPACE, BackgroundPosition.CENTER, null);
		mainPane.setBackground(new Background(background));
	}
	
	private void createLogo() {
		
		ImageView logo = new ImageView("view/resources/title.png");
		logo.setLayoutX(400);
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
