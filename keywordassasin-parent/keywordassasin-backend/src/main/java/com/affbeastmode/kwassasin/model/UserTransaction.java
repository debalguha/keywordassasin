package com.affbeastmode.kwassasin.model;

import java.math.BigDecimal;
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
@Table(schema = "keyword_assasin",name="user_transaction")
public class UserTransaction {
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected long ID;
	protected BigDecimal amountReceived;
	protected BigDecimal paymentFee;
	private Date creationDate;
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH})
	@JoinColumn(name = "PLAN_ID", nullable = false)	
	private Plan plan;
	@Column(name="transaction_detail", nullable=false, length=10000)
	private String transactionDetailAsJSON;
	@Column(name="payment_state", nullable=false, length=20)
	private String paymentState;
	@Column(name="txnId", nullable=false, length=20)
	private String txnId;
	
	
	public BigDecimal getAmountReceived() {
		return amountReceived;
	}
	public void setAmountReceived(BigDecimal amountReceived) {
		this.amountReceived = amountReceived;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public long getID() {
		return ID;
	}
	public Plan getPlan() {
		return plan;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	public String getTransactionDetailAsJSON() {
		return transactionDetailAsJSON;
	}
	public void setTransactionDetailAsJSON(String transactionDetailAsJSON) {
		this.transactionDetailAsJSON = transactionDetailAsJSON;
	}
	public String getPaymentState() {
		return paymentState;
	}
	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}
	public BigDecimal getPaymentFee() {
		return paymentFee;
	}
	public void setPaymentFee(BigDecimal paymentFee) {
		this.paymentFee = paymentFee;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
}
