package com.outlook.auth;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import microsoft.exchange.webservices.data.autodiscover.IAutodiscoverRedirectionUrl;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;

public class login {

	private String loginEmail;
	private String password;
	private boolean loggedIn = false;
	private ExchangeService service;
	ExchangeCredentials credentials;

	public login(String email, String pass, Text actiontarget) {
		loginEmail = email;
		password = pass;

		service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);

		System.out.println("\nLogging in ...");

		credentials = new WebCredentials(loginEmail, password);
		service.setCredentials(credentials);
		

		try {
			service.autodiscoverUrl(loginEmail, new RedirectionUrlCallback());
			System.out.println(service.getUrl());
			
			setLoggedIn(true);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			actiontarget.setText("Error Logging in");
			e.printStackTrace();
		}
		System.out.println("\n Logged in...");
	}

	static class RedirectionUrlCallback implements IAutodiscoverRedirectionUrl {
		public boolean autodiscoverRedirectionUrlValidationCallback(String redirectionUrl) {
			return redirectionUrl.toLowerCase().startsWith("https://");
		}
	}

	public ExchangeService getService() {
		return service;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}
