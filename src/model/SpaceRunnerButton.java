package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class SpaceRunnerButton extends Button {
	
	private final String FONT_PATH = "src/model/resources/Space.otf";
	private final String BUTTON_PRESSED_STYLE= "-fx-background-color: transparent; -fx-background-image: url('/model/resources/barHorizontal_red_mid";
	private final String BUTTON_FREE_STYLE= "-fx-background-color: transparent; -fx-background-image: url('/model/resources/barHorizontal_red_mid";
	
	public SpaceRunnerButton(String text) {
		setText(text);
	}
	
	private void setButtonFont() {
		
		try {
			setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Tahoma", 23));
		}
	}
	
	private void setButtonPressedStyle() {
		setStyle(BUTTON_PRESSED_STYLE);
		setPrefHeight(45);
		setLayoutY(getLayoutY() + 4);
	}
	
	private void setButtonRealeasedStyle() {
		setStyle(BUTTON_FREE_STYLE);
		setPrefHeight(49);
		setLayoutY(getLayoutY() + 4);
	}
	
}
