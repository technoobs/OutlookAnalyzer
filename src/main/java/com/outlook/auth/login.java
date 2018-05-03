package com.outlook.auth;

import java.net.URI;

import javafx.scene.text.Text;
import microsoft.exchange.webservices.data.autodiscover.IAutodiscoverRedirectionUrl;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.ItemId;

public class login {

	private String loginEmail;
	private String password;
	private boolean loggedIn = false;
	private ExchangeService service;
	ExchangeCredentials credentials;

	public login(String email, String pass, Text actiontarget) {
		loginEmail = email;
		password = pass;

		System.out.println("\nLogging in ...");
		service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);

		credentials = new WebCredentials(loginEmail, password);
		service.setCredentials(credentials);

		try {
			service.setUrl(new URI("https://outlook.office365.com/EWS/Exchange.asmx"));

			if (!isAuthorized()) {
				actiontarget.setText("Error Logging in please check your email and password");
			} else {
				setLoggedIn(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static class RedirectionUrlCallback implements IAutodiscoverRedirectionUrl {
		public boolean autodiscoverRedirectionUrlValidationCallback(String redirectionUrl) {
			return redirectionUrl.toLowerCase().startsWith("https://");
		}
	}

	public ExchangeService getService() {
		return service;
	}

	public boolean isAuthorized() {
		try {
			EmailMessage.bind(service, new ItemId("__dummyId__"), PropertySet.IdOnly);
		} catch (Exception e) {
			if (e.getMessage().contains("Unauthorized")) {
				return false;
			}
		}
		System.out.println("\n Logged in...");
		return true;

	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}
}
