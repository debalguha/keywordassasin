package com.affbeastmode.registration.service;

public enum PaymentState {
	CREATED, COMPLETED, APPROVED, FAILED, CANCELED, EXPIRED, UNKNOWN;
	public static PaymentState getFromValue(String value){
		if(value.toUpperCase().equalsIgnoreCase(CREATED.toString()))
			return CREATED;
		else if(value.toUpperCase().equalsIgnoreCase(COMPLETED.toString()))
			return COMPLETED;
		else if(value.toUpperCase().equalsIgnoreCase(APPROVED.toString()))
			return APPROVED;
		else if(value.toUpperCase().equalsIgnoreCase(FAILED.toString()))
			return FAILED;
		else if(value.toUpperCase().equalsIgnoreCase(CANCELED.toString()))
			return CANCELED;
		else if(value.toUpperCase().equalsIgnoreCase(EXPIRED.toString()))
			return EXPIRED;
		else
			return UNKNOWN;
	}
}
