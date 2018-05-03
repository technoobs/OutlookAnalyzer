package com.outlook;

import com.outlook.view.scenes.OpeningScene;

import javafx.application.Application;

import javafx.stage.Stage;

public class MainApp extends Application{

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Outlook work count");
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);
        primaryStage.show();
        
        OpeningScene openingScene = new OpeningScene();
        openingScene.getOpeningScene(primaryStage);
	}

}
