package com.affbeastmode.registration.ui.form;

public class RegistrationForm {
	private String name;
	private String txtEmail;
	private String confEmail;
	private String password;
	private String confPassword;
	private String plan;
	private String timezoneId;
	private String address1;
	private String address2;
	private String country;
	private String state;
	private String city;
	private int zip;
	private String cardType;
	private String cardNumber;
	private String confCardNumber;
	private int cvv2;
	private float amount;
	private int month;
	private int year;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTxtEmail() {
		return txtEmail;
	}
	public void setTxtEmail(String txtEmail) {
		this.txtEmail = txtEmail;
	}
	public String getConfEmail() {
		return confEmail;
	}
	public void setConfEmail(String confEmail) {
		this.confEmail = confEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfPassword() {
		return confPassword;
	}
	public void setConfPassword(String confPassword) {
		this.confPassword = confPassword;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getConfCardNumber() {
		return confCardNumber;
	}
	public void setConfCardNumber(String confCardNumber) {
		this.confCardNumber = confCardNumber;
	}
	public int getCvv2() {
		return cvv2;
	}
	public void setCvv2(int cvv2) {
		this.cvv2 = cvv2;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getTimezoneId() {
		return timezoneId;
	}
	public void setTimezoneId(String timezoneId) {
		this.timezoneId = timezoneId;
	}
	@Override
	public String toString() {
		return "RegistrationForm [name=" + name + ", txtEmail=" + txtEmail + ", confEmail=" + confEmail + ", password=" + password + ", confPassword=" + confPassword + ", plan=" + plan
				+ ", timezoneId=" + timezoneId + ", address1=" + address1 + ", address2=" + address2 + ", country=" + country + ", state=" + state + ", city=" + city + ", zip=" + zip + ", cardType="
				+ cardType + ", cardNumber=" + cardNumber + ", confCardNumber=" + confCardNumber + ", cvv2=" + cvv2 + ", amount=" + amount + ", month=" + month + ", year=" + year + "]";
	}
}
