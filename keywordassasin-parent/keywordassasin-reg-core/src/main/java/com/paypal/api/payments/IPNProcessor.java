package com.paypal.api.payments;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.affbeastmode.kwassasin.model.IPNInfo;
import com.affbeastmode.kwassasin.service.IPNInfoService;
import com.affbeastmode.kwassasin.service.KeywordService;
import com.affbeastmode.registration.service.MailService;

@Component("ipnProcessor")
public class IPNProcessor extends Thread implements InitializingBean{
	private static final Log logger = LogFactory.getLog(IPNProcessor.class);
	@Resource(name = "ipnQueue")
	private LinkedBlockingQueue<IPNInfo> ipnQueue;
	private ExecutorService service;
	@Autowired
	private IPNInfoService ipnService;
	@Autowired
	private KeywordService keywordService;
	@Autowired
	private MailService mailService;
	@Value("${ipn.processor.sleepTime}")
	private int sleepTime;
	
	public IPNProcessor(){
		service = new ThreadPoolExecutor(30, 50, 10, TimeUnit.NANOSECONDS, new ArrayBlockingQueue<Runnable>(1000));
	}
	
	@Override
	public void run() {
		while(true){
			try{
				Thread.sleep(sleepTime);
				List<IPNInfo> ipns = new ArrayList<IPNInfo>();
				ipnQueue.drainTo(ipns);
				if(ipns.isEmpty())
					continue;
				for(IPNInfo ipnInfo : ipns){
					service.submit(new IPNProcessorTask(ipnService, ipnInfo, keywordService, mailService));
				}
			}catch(Exception e){
				logger.error("Error while processing IPN.", e);
				e.printStackTrace();
			}
		}
	}
	
	public void setIpnQueue(LinkedBlockingQueue<IPNInfo> ipnQueue) {
		this.ipnQueue = ipnQueue;
	}

	public void setService(ExecutorService service) {
		this.service = service;
	}

	public void setIpnService(IPNInfoService ipnService) {
		this.ipnService = ipnService;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public void setKeywordService(KeywordService keywordService) {
		this.keywordService = keywordService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public void afterPropertiesSet() throws Exception {
		this.start();
	}
}
