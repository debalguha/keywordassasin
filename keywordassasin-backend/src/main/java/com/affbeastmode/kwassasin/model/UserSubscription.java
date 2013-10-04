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
@Table(schema = "keyword_assasin",name="user_subscription")
public class UserSubscription {
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected long ID;
	private Date planStartDate;
	private Date planEndDate;
	private Date creationDate;
	private boolean isActive;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH})
	@JoinColumn(name = "PLAN_ID", nullable = false)	
	private Plan plan;

	public Date getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}

	public Date getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	@Override
	public String toString() {
		return "UserSubscription [planStartDate=" + planStartDate + ", planEndDate=" + planEndDate + ", creationDate=" + creationDate + ", isActive=" + isActive + ", plan=" + plan + "]";
	} 
}
