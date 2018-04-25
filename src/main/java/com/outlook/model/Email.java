package com.outlook.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import microsoft.exchange.webservices.data.property.complex.StringList;

public class Email {

	private StringList categories;
	private LocalDateTime recived;



	public Email(StringList category, Date recived){
		this.setRecived(recived.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		this.setCategories(category);
	}

	public StringList getCategories() {
		return categories;
	}

	public void setCategories(StringList categories) {
		this.categories = categories;
	}

	public LocalDateTime getRecived() {
		return recived;
	}

	public void setRecived(LocalDateTime recived) {
		this.recived = recived;
	}	
	
	@Override
	public String toString() {
		return "\nEmail [categories=" + categories + ", recived=" + recived + "]";
	}
}
