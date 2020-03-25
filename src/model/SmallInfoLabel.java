package model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SmallInfoLabel extends Label {
    private final static String FONT_PATH = "src/model/resources/Space.otf";
    private final static String BACKGROUND_IMAGE = "/view/resources/glassPanel_corners.png";

    public SmallInfoLabel(String text){
        setPrefWidth(160);
        setPrefHeight(50);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE ,130,50,false,true), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        setBackground(new Background(backgroundImage));
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10,7,10,7));
        setLabelFont();
        setText(text);
        setTextFill(Color.WHITE);
    }

    private void setLabelFont(){
        try{
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),12));
        }catch(FileNotFoundException e){
            setFont(Font.font("Verdana",12));
        }

    }
}
