package com.outlook.view.scenes;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.outlook.controller.SearchController;
import com.outlook.model.User;
import com.outlook.model.UserInbox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.property.complex.StringList;

public class SecondScene {
	
	Stage primaryStage; 
	ExchangeService service;
	
	SearchController sc = null;

	
	private TableView table = new TableView();
	
	
	SecondScene (Stage primaryStage, ExchangeService service, File file, Text actiontarget) throws Exception{
		this.primaryStage = primaryStage;
		this.service = service;
		this.sc = new SearchController(file, service, actiontarget);

	}

	public void getSecondScene() {
		 Scene scene = new Scene(new Group());
		 primaryStage.setTitle("WorkCount");
		 primaryStage.setWidth(1000);
		 primaryStage.setHeight(1000);
		 
		 final Label tLabel = new Label("Work Count");
	     
    	 TableColumn userCol = new TableColumn("Email Address");
    	 userCol.setCellValueFactory(
    			 new PropertyValueFactory<SimplePropertyModel, String>("user"));
    			 
    	 TableColumn catCol = new TableColumn("Category");
    	 catCol.setCellValueFactory(
    			 new PropertyValueFactory<SimplePropertyModel, String>("category"));
    			 
    	 TableColumn countCol = new TableColumn("Number of emails");
    	 countCol.setCellValueFactory(
    			 new PropertyValueFactory<SimplePropertyModel, String>("count"));
    	 
    	TableColumn oldestCol = new TableColumn("Oldest Column");
    	 oldestCol.setCellValueFactory(
    			 new PropertyValueFactory<SimplePropertyModel, String>("oldest"));
    	 
    	
    	 List<SimplePropertyModel> spl = new ArrayList<>();
    	 
    	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	     for (User user: sc.getUsers()) {
	    	 
	    	 for(Map.Entry<StringList, Integer> entry : user.getInbox().getEmailCountMap().entrySet()) {
		    	 SimplePropertyModel spm = new SimplePropertyModel(
		    			 user.getEmailAddress(),
		    			 entry.getKey().toString(),
		    			 user.getInbox().getEmailOldestMap().get(entry.getKey()).format(formatter).toString(),
		    			 entry.getValue().toString()
		    			 );
		    	 
		    	 spl.add(spm);
	    	 }

	     }
	     ObservableList<SimplePropertyModel> data = FXCollections.observableList(spl);
	     
	     table.setItems(data);
	     table.getColumns().addAll(userCol,catCol,countCol,oldestCol);    
	     
	     Button btn = new Button("Back");
	     
	     btn.setOnAction(new EventHandler<ActionEvent>() {
	     	 
	         @Override
	         public void handle(ActionEvent e) {
	             primaryStage.setScene(new OpeningScene().getOpeningScene(primaryStage));
	         }
	     });
	    
	     final VBox vbox = new VBox();
	     vbox.setSpacing(5);
	     vbox.setPadding(new Insets(10, 0, 0, 10));
	     vbox.getChildren().addAll(tLabel, table);
	     
	    
	     ((Group) scene.getRoot()).getChildren().addAll(vbox);
	     primaryStage.setScene(scene);
	     primaryStage.show();
		
		} 
}
