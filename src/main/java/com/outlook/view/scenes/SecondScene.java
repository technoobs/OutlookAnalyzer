package com.outlook.view.scenes;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.outlook.controller.SearchController;
import com.outlook.model.User;
import com.outlook.view.Utils.TableUtils;
import com.outlook.view.alerts.LogoutAlert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.property.complex.StringList;

public class SecondScene {
	
	Stage primaryStage; 
	ExchangeService service;
	
	SearchController sc = null;
	
	SecondScene (Stage primaryStage, ExchangeService service, File file, Text actiontarget) throws Exception{
		this.primaryStage = primaryStage;
		this.service = service;
		this.sc = new SearchController(file, service, actiontarget);

	}

	public void getSecondScene() {
		 Scene scene = new Scene(new Group());
		 primaryStage.setTitle("WorkCount");		 
		 final Label tLabel = new Label("Work Count");
	     
    	 TableColumn<SimplePropertyModel, String> userCol = new TableColumn<SimplePropertyModel, String>("Email Address");
    	 userCol.setCellValueFactory(
    			 new PropertyValueFactory<SimplePropertyModel, String>("user"));
    			 
    	 TableColumn<SimplePropertyModel, String> catCol = new TableColumn<SimplePropertyModel, String>("Category");
    	 catCol.setCellValueFactory(
    			 new PropertyValueFactory<SimplePropertyModel, String>("category"));
    			 
    	 TableColumn<SimplePropertyModel, String> countCol = new TableColumn<SimplePropertyModel, String>("Number of emails");
    	 countCol.setCellValueFactory(
    			 new PropertyValueFactory<SimplePropertyModel, String>("count"));
    	 
    	TableColumn<SimplePropertyModel, String> oldestCol = new TableColumn<SimplePropertyModel, String>("Oldest Column");
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
		    	 
		    	 System.out.println( user.getEmailAddress() +
		    			 entry.getKey().toString()+
		    			 user.getInbox().getEmailOldestMap().get(entry.getKey()).format(formatter).toString()+
		    			 entry.getValue().toString());
		    	 
		    	 spl.add(spm);
	    	 }

	     }

	     ObservableList<SimplePropertyModel> data = FXCollections.observableList(spl);

	     
	 	TableView<SimplePropertyModel> table = new TableView<>();

	     table.setItems(data);
	     table.getColumns().addAll(userCol,catCol,countCol,oldestCol); 
	     table.getSelectionModel().setCellSelectionEnabled(true);
	     table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	     
	     TableUtils.installCopyPasteHandler(table);
	     
	     Button btn = new Button("Back");
	    
	     btn.setOnAction(new EventHandler<ActionEvent>() {
	     	 
	         @Override
	         public void handle(ActionEvent e) {
	        	 
	        	 Alert logout = new LogoutAlert(null);
	        	 Optional<ButtonType> result = logout.showAndWait();
	        	 
	        	 if ((result.isPresent()) & (result.get() == ButtonType.OK)) {
	        		 primaryStage.setScene(new OpeningScene().getOpeningScene(primaryStage));
	        		 primaryStage.show();
	        		}
	        	 
	             
	         }
	     });
	    
	     final VBox vbox = new VBox(10);
	     vbox.setSpacing(5);
	     vbox.setPadding(new Insets(10, 10, 10, 10));
	     vbox.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.80));
	     vbox.getChildren().addAll(tLabel, table,btn);
	     
	    
	     ((Group) scene.getRoot()).getChildren().addAll(vbox);
	     
	     
	     
	     
	     primaryStage.setScene(scene);
	     primaryStage.show();
		
		} 
}
