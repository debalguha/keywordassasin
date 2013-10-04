package com.affbeastmode.kwassasin.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "keyword_assasin",name="users")
@NamedQueries({
	@NamedQuery(
	name = "findUserById",
	query = "from User s where s.userName = :userId"
	),
	@NamedQuery(
	name = "findUserByUUID",
	query = "from User s where s.UUID = :uuid"
	)	
})
public class User {
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected long ID;
	@Column(name="user_name", nullable=false, length=100, unique=true)
	private String userName;
	@Column(name="password", nullable=false, length=100)
	private String password;
	@Column(name="email", nullable=false, length=100, unique=true)
	private String email; 
	private boolean isActive;

	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name="USER_ID")
	private Set<UserSubscription> subscriptions;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinColumn(name="USER_ID")
	private Set<UserActivity> activities;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name="USER_ID")
	private Set<UserTransaction> transactions;
	
	@Column(name="timezone_id", nullable=false, length=256)
	private String timezoneId;
	
	@Column(name="uuid", nullable=true, length=50)
	private String UUID;
	
	private ConfirmationState state;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Set<UserSubscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(Set<UserSubscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public Set<UserActivity> getActivities() {
		return activities;
	}

	public void setActivities(Set<UserActivity> activities) {
		this.activities = activities;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", email=" + email + ", isActive=" + isActive+ "]";
	}

	public String getTimezoneId() {
		return timezoneId;
	}

	public void setTimezoneId(String timezoneId) {
		this.timezoneId = timezoneId;
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public Set<UserTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<UserTransaction> transactions) {
		this.transactions = transactions;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public ConfirmationState getState() {
		return state;
	}

	public void setState(ConfirmationState state) {
		this.state = state;
	}
}
