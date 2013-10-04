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
@Table(schema = "keyword_assasin",name="ipn_info")
@NamedQueries({
	@NamedQuery(
	name = "findIpnInfoByTransactionId",
	query = "from IPNInfo i where i.txnId = :txnId"
	)
})
public class IPNInfo {
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected long ID;
	private long logTime;
	private String itemName;
	@Column(nullable=false, length=10)
	private String paymentStatus;
	@Column(nullable=true, length=10)
	private String paymentAmount;
	@Column(nullable=false, length=5)
	private String paymentCurrency;
	@Column(nullable=false, length=20)
	private String txnId;
	private String receiverEmail;
	private String payerEmail;
	@Column(nullable=false, length=10)
	private String paymentFee;
	@Column(nullable=false, length=10)
	private String chosenOption;
	private String response;
	@Column(nullable=false, columnDefinition="TEXT")
	private String requestParam;
	@Column(name="uuid", nullable=true, length=50)
	private String UUID;	
	public long getLogTime() {
		return logTime;
	}
	public void setLogTime(long logTime) {
		this.logTime = logTime;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getPaymentCurrency() {
		return paymentCurrency;
	}
	public void setPaymentCurrency(String paymentCurrency) {
		this.paymentCurrency = paymentCurrency;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getReceiverEmail() {
		return receiverEmail;
	}
	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}
	public String getPayerEmail() {
		return payerEmail;
	}
	public void setPayerEmail(String payerEmail) {
		this.payerEmail = payerEmail;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getRequestParam() {
		return requestParam;
	}
	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public String getPaymentFee() {
		return paymentFee;
	}
	public void setPaymentFee(String paymentFee) {
		this.paymentFee = paymentFee;
	}
	public void setChosenOption(String chosenOption) {
		this.chosenOption = chosenOption;
	}
	public String getChosenOption() {
		return chosenOption;
	}
}
