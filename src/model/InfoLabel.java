package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class InfoLabel extends Label {

	public final static String FONT_PATH = "src/model/resources/Space.otf";
	public final static String BACKGROUND_IMAGE = "view/resources/glassPanel_corners.png";
	
	public InfoLabel(String text) {
		
		setPrefWidth(380);
		setPrefHeight(49);
		setText(text);
		setTextFill(Color.WHITE);
		setWrapText(true);
		setLabelFont();
		setAlignment(Pos.CENTER);
		
		BackgroundImage background = new  BackgroundImage(new Image(BACKGROUND_IMAGE, 380, 49, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		setBackground(new Background(background));
	}
	
	private void setLabelFont() {
		
		try {
			setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),23));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Tahoma", 23));
		}
	}
	
}
