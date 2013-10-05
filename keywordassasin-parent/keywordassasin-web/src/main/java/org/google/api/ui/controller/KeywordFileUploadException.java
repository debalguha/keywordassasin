package org.google.api.ui.controller;

public class KeywordFileUploadException extends RuntimeException {
	public static final String KEYWORD_LIMIT_EXCEED_FOR_FILE = "The number of keyword uploaded is more than the permissible limit of {0}";
	public static final String KEYWORD_LIMIT_EXCEED_FOR_SUBSCRIPTION = "You have already exceeded the quota{0} of your subscription.";
	public static final String SUBSCRIPTION_EXPIRED = "Your subscription has been expired.";
	public static final String MSNG_REQD_ATTRIB_IN_UPLD_FILE = "The uploaded file does not have one or all of the following columns; {0}, {1}, {2}";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2829571305089458258L;
	public KeywordFileUploadException(String message) {
		super(message);
	}
	public KeywordFileUploadException(String message, Throwable t) {
		super(message, t);
	}	
}
