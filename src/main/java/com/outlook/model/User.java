package com.outlook.model;

public class User {
	UserInbox inbox = new UserInbox();
	String emailAddress;
	
	
	public User(String email) {
		setEmailAddress(email);
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public UserInbox getInbox() {
		return inbox;
	}

	public void setInbox(UserInbox inbox) {
		this.inbox = inbox;
	}

	@Override
	public String toString() {
		return "User [inbox=" + inbox + ", emailAddress=" + emailAddress + "]";
	}
	
	

}
