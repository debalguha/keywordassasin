package com.affbeastmode.kwassasin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(schema = "keyword_assasin",name="plan_master")
@NamedQueries({
	@NamedQuery(
	name = "findPlanByName",
	query = "from Plan p where p.planName = :planName"
	)
})
public class Plan {
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected long ID;
	@Column(name="PLANNAME", nullable=false, length=50)
	private String planName;
	private String featureText;
	private int totalKeywordLimit;
	private int dailyRestriction;
	private int uploadRestriction;
	private int durationInDays;
	private int discount;
	private char subscriptionPeriod;
	private double cost;
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getFeatureText() {
		return featureText;
	}
	public void setFeatureText(String featureText) {
		this.featureText = featureText;
	}
	public int getDailyRestriction() {
		return dailyRestriction;
	}
	public void setDailyRestriction(int dailyRestriction) {
		this.dailyRestriction = dailyRestriction;
	}
	public int getUploadRestriction() {
		return uploadRestriction;
	}
	public void setUploadRestriction(int uploadRestriction) {
		this.uploadRestriction = uploadRestriction;
	}
	public int getDurationInDays() {
		return durationInDays;
	}
	public void setDurationInDays(int durationInDays) {
		this.durationInDays = durationInDays;
	}
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public int getTotalKeywordLimit() {
		return totalKeywordLimit;
	}
	public void setTotalKeywordLimit(int totalKeywordLimit) {
		this.totalKeywordLimit = totalKeywordLimit;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public char getSubscriptionPeriod() {
		return subscriptionPeriod;
	}
	public void setSubscriptionPeriod(char subscriptionPeriod) {
		this.subscriptionPeriod = subscriptionPeriod;
	}
	@Override
	public String toString() {
		return "Plan [ID=" + ID + ", planName=" + planName + ", featureText=" + featureText + ", totalKeywordLimit=" + totalKeywordLimit + ", dailyRestriction=" + dailyRestriction + ", uploadRestriction=" + uploadRestriction + ", durationInDays=" + durationInDays + ", discount=" + discount + ", subscriptionPeriod=" + subscriptionPeriod + ", cost=" + cost + "]";
	}
}
