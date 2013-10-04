package com.affbeastmode.kwassasin.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "keyword_assasin",name="user_activity")
public class UserActivity {
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected long ID;
	private int keywordProcessed;
	private Date activityDate;
	private int uploadCount;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH})
	@JoinColumn(name = "PLAN_ID", nullable = false)	
	private Plan plan;

	public int getKeywordProcessed() {
		return keywordProcessed;
	}

	public void setKeywordProcessed(int keywordProcessed) {
		this.keywordProcessed = keywordProcessed;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public int getUpoadCount() {
		return uploadCount;
	}

	public void setUpoadCount(int upoadCount) {
		this.uploadCount = upoadCount;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	@Override
	public String toString() {
		return "UserActivity [keywordProcessed=" + keywordProcessed + ", activityDate=" + activityDate + ", upoadCount=" + uploadCount 
				+ ", plan=" + plan + "]";
	}
}
