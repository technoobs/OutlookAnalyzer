package com.outlook.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import microsoft.exchange.webservices.data.property.complex.StringList;

public class UserInbox {
	private ArrayList<Email> emails = new ArrayList<>();
	private Map<StringList, Integer> emailCountMap  = new HashMap<>();
	private Map<StringList, LocalDateTime> emailOldestMap  = new HashMap<>();


	public Map<StringList, Integer> getEmailCountMap() {
		return emailCountMap;
	}

	public Map<StringList, LocalDateTime> getEmailOldestMap() {
		return emailOldestMap;
	}

	public ArrayList<Email> getEmails() {
		return emails;
	}
	
	public void addEmail(Email email) {
		if(emailCountMap.get(email.getCategories())== null) { 
			
			emailCountMap.put(email.getCategories(),1);
			emailOldestMap.put(email.getCategories(), email.getRecived());
		
		}else if (emailCountMap.get(email.getCategories())!=null) {
			
			emailCountMap.put(email.getCategories(),
					emailCountMap.get(email.getCategories()) + 1);
			
		   checkforOldestDate(email,emailOldestMap);
		}
		
		emails.add(email);
	}

	private void checkforOldestDate(Email email, Map<StringList, LocalDateTime> map) {
		if(email.getRecived().isAfter(map.get(email.getCategories()))) {
			map.put(email.getCategories(), email.getRecived());
		}
		
	}

	public void setEmails(ArrayList<Email> emails) {
		this.emails = emails;
	}

	
	@Override
	public String toString() {
		return "UserInbox \n[emails=" + emails + ", emailCountMap=" + emailCountMap + ", emailOldestMap=" + emailOldestMap
				+ "]";
	}

	
}
