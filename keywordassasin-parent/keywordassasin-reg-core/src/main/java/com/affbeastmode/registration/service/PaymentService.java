package com.affbeastmode.registration.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.affbeastmode.kwassasin.dao.KeywordDAO;
import com.affbeastmode.kwassasin.model.Plan;
import com.affbeastmode.kwassasin.model.User;
import com.affbeastmode.kwassasin.model.UserTransaction;
import com.affbeastmode.registration.ui.form.RegistrationForm;
import com.paypal.api.payments.Address;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.CreditCard;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.api.payments.util.GenerateAccessToken;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.PayPalRESTException;

@Transactional
@Service("paymentService")
public class PaymentService {
	private static final Logger logger = Logger.getLogger(PaymentService.class);
	@Autowired
	private KeywordDAO keywordDao;
	@Resource(name="paypalProps")
	private Properties paypalProps;
	private DecimalFormat format = new DecimalFormat("#######.##");
	
	public Payment processPayment(RegistrationForm formData) throws Exception{
		Address billingAddress = new Address();
		billingAddress.setCity(formData.getCity());
		billingAddress.setCountryCode("US");
		billingAddress.setLine1(formData.getAddress1());
		billingAddress.setLine2(formData.getAddress2());
		billingAddress.setPostalCode(String.valueOf(formData.getZip()));
		billingAddress.setState(formData.getState());

		// ###CreditCard
		// A resource representing a credit card that can be
		// used to fund a payment.
		CreditCard creditCard = new CreditCard();
		creditCard.setBillingAddress(billingAddress);
		creditCard.setCvv2(String.valueOf(formData.getCvv2()));
		creditCard.setExpireMonth(formData.getMonth());
		creditCard.setExpireYear(formData.getYear());
		creditCard.setFirstName(formData.getName().split("\\s")[0]);
		creditCard.setLastName(formData.getName().split("\\s")[1]);
		creditCard.setNumber(formData.getCardNumber());
		creditCard.setType(formData.getCardType().toLowerCase());

		// ###Details
		// Let's you specify details of a payment amount.
		Details details = new Details();
		details.setShipping("0");
		double subTotal = formData.getAmount();
		String subTotalStr = format.format(subTotal);
		if(subTotalStr.substring(subTotalStr.indexOf("[.]")+1).length()==1)
			subTotalStr.concat("0");
		details.setSubtotal(subTotalStr);
		double tax = formData.getAmount()*0.12; 
		String taxStr = format.format(tax);
		if(taxStr.substring(taxStr.indexOf("[.]")+1).length()==1)
			taxStr.concat("0");
		details.setTax(taxStr);

		// ###Amount
		// Let's you specify a payment amount.
		Amount amount = new Amount();
		amount.setCurrency("USD");
		// Total must be equal to sum of shipping, tax and subtotal.
		double total = formData.getAmount()+tax;
		String totalStr = format.format(total);
		if(totalStr.substring(totalStr.indexOf("[.]")+1).length()==1)
			totalStr.concat("0");
		amount.setTotal(totalStr);
		amount.setDetails(details);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction
				.setDescription("Payment for Keyword Assasin tools subscription.");

		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		FundingInstrument fundingInstrument = new FundingInstrument();
		fundingInstrument.setCreditCard(creditCard);

		List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
		fundingInstrumentList.add(fundingInstrument);

		Payer payer = new Payer();
		payer.setFundingInstruments(fundingInstrumentList);
		payer.setPaymentMethod("credit_card");

		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		Payment createdPayment = null;
		try {
			String accessToken = GenerateAccessToken.getAccessToken();
			APIContext apiContext = new APIContext(accessToken);
			logger.info("Going to send payment request:- "+payment.toJSON());
			createdPayment = payment.create(apiContext);
			logger.info("Created payment with id = " + createdPayment.getId()
					+ " and status = " + createdPayment.getState());
		} catch (PayPalRESTException e) {
			e.printStackTrace();
			throw new Exception(e);
		}		
		return createdPayment;
	}
	@Transactional
	public void registerUSerAndCreateTransaction(Payment payment, User user, Plan plan) throws Exception {
		this.createUserTrnsaction(payment, user, plan);
		keywordDao.saveUserWithRole(user, "user");
	}
	@Transactional
	public void createUserTrnsaction(Payment payment, User user, Plan plan) {
		UserTransaction transaction = new UserTransaction();
		transaction.setAmountReceived(new BigDecimal(payment.getTransactions().get(0).getAmount().getTotal()));
		transaction.setCreationDate(new Date());
		transaction.setPlan(plan);
		transaction.setTransactionDetailAsJSON(payment.toJSON());
		transaction.setPaymentState(PaymentState.getFromValue(payment.getState()).name());
		Set<UserTransaction> txns = user.getTransactions();
		if(txns==null){
			txns = new HashSet<UserTransaction>(1);
			user.setTransactions(txns);
		}
		txns.add(transaction);
	}
	public void setKeywordDao(KeywordDAO keywordDao) {
		this.keywordDao = keywordDao;
	}
	public void setPaypalProps(Properties paypalProps) {
		this.paypalProps = paypalProps;
	}
}
