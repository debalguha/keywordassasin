package com.affbeastmode.registration.ui.form;

public class NewRegistrationForm {
	private String txtEmail;
	private String password;
	private String timezoneId;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTimezoneId() {
		return timezoneId;
	}
	public void setTimezoneId(String timezoneId) {
		this.timezoneId = timezoneId;
	}
	public String getTxtEmail() {
		return txtEmail;
	}
	public void setTxtEmail(String txtEmail) {
		this.txtEmail = txtEmail;
	}
	@Override
	public String toString() {
		return "NewRegistrationForm [txtEmail=" + txtEmail + ", password=" + password + ", timezoneId=" + timezoneId + "]";
	}
}
