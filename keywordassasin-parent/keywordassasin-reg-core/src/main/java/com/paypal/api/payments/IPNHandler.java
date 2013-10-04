package com.paypal.api.payments;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.affbeastmode.kwassasin.model.IPNInfo;

@Component("ipnHandler")
public class IPNHandler {
	private static final Log logger = LogFactory.getLog(IPNHandler.class);
	@Resource(name = "ipnQueue")
	private LinkedBlockingQueue<IPNInfo> ipnQueue;
	@Value("${ipn.url}")
	private String ipnUrl;
	
	public IPNInfo handleIPN(Map<String, String[]> reqMap, String uuid) throws Exception{
		logger.info("inside ipn");
        IPNInfo ipnInfo = new IPNInfo();
        String requestParams = this.getAllRequestParams(reqMap);
        logger.info(requestParams);
      //2. Prepare 'notify-validate' command with exactly the same parameters
        Iterator<String> en = reqMap.keySet().iterator();
        StringBuilder cmd = new StringBuilder("cmd=_notify-validate");
        String paramName;
        String paramValue;
        while (en.hasNext()) {
            paramName = en.next();
            paramValue = reqMap.get(paramName)[0];
            cmd.append("&").append(paramName).append("=")
                    .append(URLEncoder.encode(paramValue, reqMap.get("charset")[0]));
        }
      //3. Post above command to Paypal IPN URL ipnUrl
        URL u = new URL(ipnUrl);
        HttpsURLConnection uc = (HttpsURLConnection) u.openConnection();
        uc.setDoOutput(true);
        uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        uc.setRequestProperty("Host", "www.paypal.com");
        uc.setRequestMethod("POST");
        PrintWriter pw = new PrintWriter(uc.getOutputStream());
        pw.println(cmd.toString());
        pw.close();

        //4. Read response from Paypal
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        String res = in.readLine();
        in.close(); 
        logger.info("Response from paypal: "+res);
        if(!res.equalsIgnoreCase("VERIFIED")){
        	logger.error("Unable to handle an IPN which can not be verified. Possible malicious attempt!!");
        	throw new Exception("Unable to handle an IPN which can not be verified. Possible malicious attempt!!");
        }
        
      //5. Capture Paypal IPN information
        ipnInfo.setLogTime(System.currentTimeMillis());
        ipnInfo.setItemName(reqMap.get("item_name")[0]);
        ipnInfo.setPaymentStatus(reqMap.get("payment_status")!=null?reqMap.get("payment_status")[0]:"");
        ipnInfo.setPaymentAmount(reqMap.get("mc_gross")!=null?reqMap.get("mc_gross")[0]:"");
        ipnInfo.setPaymentCurrency(reqMap.get("mc_currency")!=null?reqMap.get("mc_currency")[0]:"");
        ipnInfo.setTxnId(reqMap.get("txn_id")!=null?reqMap.get("txn_id")[0]:"");
        ipnInfo.setReceiverEmail(reqMap.get("receiver_email")!=null?reqMap.get("receiver_email")[0]:"");
        ipnInfo.setPayerEmail(reqMap.get("payer_email")!=null?reqMap.get("payer_email")[0]:"");
        ipnInfo.setPaymentFee(reqMap.get("mc_fee")!=null?reqMap.get("mc_fee")[0]:"");
        ipnInfo.setChosenOption(reqMap.get("option_selection1")!=null?reqMap.get("option_selection1")[0]:"");
        ipnInfo.setResponse(res);
        ipnInfo.setRequestParam(requestParams); 
        ipnInfo.setUUID(uuid);
        logger.info("Waiting to put this IPN into queue, transaction ID: "+ipnInfo.getTxnId());
        ipnQueue.put(ipnInfo);
        logger.info("Successfully put this IPN into queue, transaction ID: "+ipnInfo.getTxnId());
		return ipnInfo;
	}
	private String getAllRequestParams(Map<String, String[]> reqMap) {
		StringBuilder sb = new StringBuilder("\nREQUEST PARAMETERS\n");
        for (Iterator<String> it = reqMap.keySet().iterator(); it.hasNext();)
        {
            String pn = it.next();
            sb.append(pn).append("\n");
            String[] pvs = (String[]) reqMap.get(pn);
            for (int i = 0; i < pvs.length; i++) {
                String pv = pvs[i];
                sb.append("\t").append(pv).append("\n");
            }
        }
        return sb.toString();		
	}
	public void setIpnQueue(LinkedBlockingQueue<IPNInfo> ipnQueue) {
		this.ipnQueue = ipnQueue;
	}
}
