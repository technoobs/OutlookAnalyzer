package com.outlook.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.outlook.model.Email;
import com.outlook.model.User;
import com.outlook.model.UserInbox;

import javafx.scene.text.Text;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.property.complex.Mailbox;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;

public class SearchController {
	
	private ArrayList<User> users = new ArrayList<>();
	private ExchangeService service;
	
	
	public SearchController(File inboxesToAccess, ExchangeService service, Text actiontarget) {
		this.service = service;
		
		try {
			setUpUsers(inboxesToAccess);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			actiontarget.setText("Error setting up users");
			e.printStackTrace();
		}
		
		for (User user : users) {
			try {
				user.setInbox(trawlInbox(user));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				actiontarget.setText("Error trawling inbox.");
				e.printStackTrace();
				continue;
			}
			System.out.println(this.getClass().getSimpleName() + ": set up inbox for " + user.getEmailAddress() + ": " +user.toString() );
		}
	}

	private void setUpUsers(File inboxesToAccess) throws IOException {
		System.out.println("Setting up Users to search through");
		
		FileReader reader = new FileReader(inboxesToAccess);
		BufferedReader bfr = new BufferedReader(reader);
		
		
		String email;
		while ((email = bfr.readLine()) != null) {
			
			System.out.println(email);
			if(email.equals("") | email.equals(" ")) {
				// dont add a user.
			}else {
				User newUser = new User(email.trim().replace(",", ""));
				users.add(newUser);
			}
		}
		
		System.out.println("\nFinnished setting up Users");
	
		bfr.close();
	}

	private UserInbox trawlInbox(User user) throws Exception {
		UserInbox inbox = new UserInbox();
		int itemsToReturn = 1000;
		int offset = 0;
		ItemView view = new ItemView(itemsToReturn);
		FindItemsResults<Item> findResults = null;
		FolderId folder = new FolderId(WellKnownFolderName.Inbox, new Mailbox(user.getEmailAddress()));

		
		do {
			System.out.println("\nFinding results for: " + user.getEmailAddress());
			try {
				
				findResults = service.findItems(folder, view);
				System.out.println(" Total items found: " + findResults.getTotalCount());
			} catch (Exception e) {
				System.out.println("Error finding items for user " + user.getEmailAddress());
				e.printStackTrace();
				throw e;
			}		
			
			
			for(Item item : findResults) {
				Email email = new Email(item.getCategories(),
										item.getDateTimeReceived());
				inbox.addEmail(email);
			}
			
			offset += itemsToReturn;
			view.setOffset(offset);
	    } while (findResults.isMoreAvailable());
		
		return inbox;
	}

	public ArrayList<User> getUsers() {
		return users;
	}


}
