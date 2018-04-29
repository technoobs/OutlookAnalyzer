package com.outlook.view.scenes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SimplePropertyModel {
	private final StringProperty user;
	private final StringProperty category;
	private final StringProperty oldest;
	private final StringProperty count;
	
	SimplePropertyModel(String user, String category, String oldest, String count) {
		this.user = new SimpleStringProperty(user);
		this.category = new SimpleStringProperty(category);
		this.oldest = new SimpleStringProperty(oldest);
		this.count = new SimpleStringProperty(count);
		
	}	
	
	public StringProperty getUser() {
		return user;
	}
	public StringProperty getCategory() {
		return category;
	}
	public StringProperty getOldest() {
		return oldest;
	}

	public StringProperty getCount() {
		return count;
	}
	
	public StringProperty userProperty() {
		return user;
	}
	public StringProperty categoryProperty() {
		return category;
	}
	public StringProperty oldestProperty() {
		return oldest;
	}

	public StringProperty countProperty() {
		return count;
	}
	

	

	
}
