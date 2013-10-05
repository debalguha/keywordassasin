package com.paypal.api.payments;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.affbeastmode.kwassasin.model.IPNInfo;
import com.affbeastmode.kwassasin.model.Plan;
import com.affbeastmode.kwassasin.model.User;
import com.affbeastmode.kwassasin.model.UserSubscription;
import com.affbeastmode.kwassasin.model.UserTransaction;
import com.affbeastmode.kwassasin.service.IPNInfoService;
import com.affbeastmode.kwassasin.service.KeywordService;
import com.affbeastmode.registration.service.MailService;
import com.affbeastmode.registration.service.PaymentState;

public class IPNProcessorTask implements Runnable{
	private IPNInfoService ipnService;
	private KeywordService keywordService;
	private MailService mailService;
	private IPNInfo ipnInfo;
	private static final Log logger = LogFactory.getLog(IPNProcessorTask.class);
	public IPNProcessorTask(IPNInfoService ipnService, IPNInfo ipnInfo, KeywordService keywordService, MailService mailService){
		this.ipnService = ipnService;
		this.ipnInfo = ipnInfo;
		this.keywordService = keywordService;
		this.mailService = mailService;
	}
	public void run() {
		if(ipnInfo.getResponse().equalsIgnoreCase("VERIFIED")){
			String paymentStatus = ipnInfo.getPaymentStatus();
			String transactionId = ipnInfo.getTxnId();
			if(paymentStatus==null || paymentStatus.isEmpty() || transactionId==null || transactionId.isEmpty()){
				logger.info("It is the first IPN with no payment status. Ignoring it: "+ipnInfo);
				return;
			}
			try {
				if(ipnService.storeIPNInfo(ipnInfo)){
					String uuid = ipnInfo.getUUID();
					String chosenPlanStr = ipnInfo.getChosenOption();
					logger.info("UUID: "+uuid);
					logger.info("Plan chosen: "+chosenPlanStr);
					Plan chosenPlan = keywordService.findPlanByName(chosenPlanStr);
					User user = keywordService.findUserByUUID(uuid);
					logger.info("User found for UUID: "+user);
					logger.info("mc_gross: "+ipnInfo.getPaymentAmount());
					logger.info("mc_fee: "+ipnInfo.getPaymentFee());
					logger.info("txn_id: "+ipnInfo.getTxnId());
					logger.info("payment_status: "+ipnInfo.getPaymentStatus());
					UserSubscription subscription = new UserSubscription();
					Calendar currentDate = Calendar.getInstance();
					subscription.setCreationDate(currentDate.getTime());
					subscription.setPlanStartDate(currentDate.getTime());
					Calendar planEndDate = Calendar.getInstance();
					planEndDate.setTime(currentDate.getTime());
					planEndDate.add(Calendar.DAY_OF_YEAR, chosenPlan.getDurationInDays());
					subscription.setPlan(chosenPlan);
					subscription.setPlanEndDate(planEndDate.getTime());
					subscription.setActive(true);
					
					UserTransaction transaction  = new UserTransaction();
					transaction.setAmountReceived(new BigDecimal(ipnInfo.getPaymentAmount()));
					transaction.setPaymentFee(new BigDecimal(ipnInfo.getPaymentFee()));
					transaction.setTxnId(ipnInfo.getTxnId());
					PaymentState paymentState = PaymentState.getFromValue(ipnInfo.getPaymentStatus());
					logger.info("Payment state: "+paymentState.toString());
					transaction.setPaymentState(paymentState.toString());
					transaction.setPlan(chosenPlan);
					transaction.setTransactionDetailAsJSON(ipnInfo.getRequestParam());
					Set<UserSubscription> subs = user.getSubscriptions();
					if(subs==null){
						subs = new HashSet<UserSubscription>(1);
						user.setSubscriptions(subs);
					}
					subs.add(subscription);
					Set<UserTransaction> txns = user.getTransactions();
					if(txns == null){
						txns = new HashSet<UserTransaction>();
						user.setTransactions(txns);
					}
					txns.add(transaction);
					logger.info("Adjusted user object and set transactions and subscriptions");
					if(paymentState.equals(PaymentState.COMPLETED)){
						keywordService.registerUserWithRole(user);
						logger.info("Sending activation mail.");
						mailService.sendMailTo(user, "Your profile is now active. Please click https://localhost:8443/googleapi-1.0.0.1/ to login to the tool. If you face any difficulty logging in, please inform us immediately. The support email is kwassasin-support@gmail.com");
						logger.info("Activation mail sent.");
					}else{
						logger.info("Updating user but not activating since payment not completed yet.");
						keywordService.updateUser(user);
						logger.info("Updating user completed.");
					}					
				}
			} catch (Exception e) {
				logger.error("Error while processing IPN. IPN info is stored though.", e);
				e.printStackTrace();
			}
		}else
			logger.warn("Not processing an unverified IPN: "+ipnInfo);
	}

}
