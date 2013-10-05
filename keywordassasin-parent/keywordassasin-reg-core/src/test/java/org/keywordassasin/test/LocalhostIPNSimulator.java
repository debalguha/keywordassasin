package org.keywordassasin.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class LocalhostIPNSimulator {
	static {
	    //for localhost testing only
	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	    new javax.net.ssl.HostnameVerifier(){
 
	        public boolean verify(String hostname,
	                javax.net.ssl.SSLSession sslSession) {
	            if (hostname.equals("localhost") || hostname.equals("munai-pc.sapient.com")) {
	                return true;
	            }
	            return false;
	        }
	    });
	}	
	public static void main(String[] args) throws Exception{
		String url = args[0];//https://localhost:8443/registration/controller/confirm.do?uuid=933c4da6-9de2-4b6e-ab15-3c62d2537450
		String params = args[1];//"mc_gross=30.00&protection_eligibility=Eligible&address_status=confirmed&payer_id=762Z2YPLH7JGL&address_street=1 Main St&payment_date=08:42:54 Oct 02, 2013 PDT&payment_status=Completed&charset=windows-1252&address_zip=95131&first_name=Customer&option_selection1=Gold&mc_fee=1.17&address_country_code=US&address_name=Customer Any&notify_version=3.7&subscr_id=I-T244DG0W86HM&payer_status=verified&business=debal.guha_facilitator@yahoo.com&address_country=United States&address_city=San Jose&verify_sign=ApLkW6-L.nTa3z6FJh1wDVHpSyTnAyfkIofFuKkCuhTWDRwmIwwgY43W&payer_email=debal.guh_personal@yahoo.coma&option_name1=Payment Options&txn_id=8FA12138P4320592U&payment_type=instant&last_name=Any&address_state=CA&receiver_email=debal.guha_facilitator@yahoo.com&payment_fee=1.17&receiver_id=59FVYTRXZ7AFU&txn_type=subscr_payment&item_name=Keyword Assasin Subscription&mc_currency=USD&residence_country=US&test_ipn=1&transaction_subject=Keyword Assasin Subscription&payment_gross=30.00&ipn_track_id=dcac8ce96cef2";
        System.out.println(args[1]);
		URL u = new URL(url);
        //HttpsURLConnection uc = (HttpsURLConnection) u.openConnection();
        HttpURLConnection uc = (HttpURLConnection) u.openConnection();
        uc.setDoOutput(true);
        uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        uc.setRequestProperty("Host", "munai-pc.sapient.com");
        uc.setRequestMethod("POST");
        PrintWriter pw = new PrintWriter(uc.getOutputStream());
        pw.println(params);
        pw.close();
        
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        String res = in.readLine();
        in.close(); 
        System.out.println(res);;
	}

}
