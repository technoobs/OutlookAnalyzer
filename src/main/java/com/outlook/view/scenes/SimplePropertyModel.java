package com.outlook.view.scenes;

import javafx.beans.property.SimpleStringProperty;

public class SimplePropertyModel {
	private final SimpleStringProperty user;
	private final SimpleStringProperty category;
	private final SimpleStringProperty oldest;
	private final SimpleStringProperty count;
	
	SimplePropertyModel(String user, String category, String oldest, String count) {
		this.user = new SimpleStringProperty(user);
		this.category = new SimpleStringProperty(category);
		this.oldest = new SimpleStringProperty(oldest);
		this.count = new SimpleStringProperty(count);
		
	}	
	
	public SimpleStringProperty getUser() {
		return user;
	}
	public SimpleStringProperty getCategory() {
		return category;
	}
	public SimpleStringProperty getOldest() {
		return oldest;
	}

	public SimpleStringProperty getCount() {
		return count;
	}
	

	
}
