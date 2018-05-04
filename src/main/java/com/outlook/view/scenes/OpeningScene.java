package com.outlook.view.scenes;

import java.io.File;

import com.outlook.auth.login;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import microsoft.exchange.webservices.data.core.ExchangeService;

public class OpeningScene {

	public Scene getOpeningScene(Stage primaryStage) {

		final FileChooser fileChooser = new FileChooser();
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Scene scene = new Scene(grid, primaryStage.getHeight(), primaryStage.getWidth());
		primaryStage.setScene(scene);

		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("Your email:");
		grid.add(userName, 0, 1);

		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);

		Button btn = new Button("Sign in");
		
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.CENTER);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);

		final Text actiontarget = new Text();
		grid.add(actiontarget, 1, 6);

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				actiontarget.setFill(Color.FIREBRICK);
				actiontarget.setText("Sign in button pressed logging in");

				login login = new login(userTextField.getText(), pwBox.getText(), actiontarget);
				ExchangeService service = login.getService();

				File file = null;
				if(login.isLoggedIn()) {
					file = fileChooser.showOpenDialog(primaryStage);
				}
				
				if (file != null && login.isLoggedIn()) {
					SecondScene secondScene = null;

						try {
							secondScene = new SecondScene(primaryStage, service, file,actiontarget);
						} catch (Exception e1) {
							actiontarget.setText("There has been an error");
							e1.printStackTrace();
						}

					
					secondScene.getSecondScene();
				}else if (file == null && login.isLoggedIn()){
					actiontarget.setText("You need to select a txt file with email addresses to log in");
				}
			}
		});

		return scene;

	}

}
