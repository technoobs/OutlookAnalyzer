package com.outlook.view.alerts;

import javafx.scene.control.Alert;

public class LogoutAlert extends Alert {

	public LogoutAlert(AlertType alertType) {
		super(AlertType.CONFIRMATION);
		this.setTitle("Logout?");
		this.setHeaderText("Are you sure you want to logout?");
		String s ="Logging out will lose all your data.";
		this.setContentText(s);
	}

}
