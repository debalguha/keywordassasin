package org.google.api.http;

import java.util.concurrent.Callable;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class GetThread implements Callable<String> {
	private final HttpClient httpClient;
	private final HttpContext context;
	private final HttpGet httpget;
	private final int id;
	private static final Log logger = LogFactory.getLog(GetThread.class);

	public GetThread(HttpClient httpClient, HttpGet httpget, int id) {
		this.httpClient = httpClient;
		this.context = new BasicHttpContext();
		this.httpget = httpget;
		this.id = id;
	}

	@Override
	public String call() throws Exception {
		logger.info(id + " - about to get something from " + httpget.getURI());
		String resp = null;
		int counter = 1;
		try {
			while (true) {
				try {
					//Statutory delay
					Thread.sleep(RandomUtils.nextInt(900));
				} catch (Exception e) {
				}
				HttpResponse response = httpClient.execute(httpget, context);
				if (response.getStatusLine().getStatusCode() != 200) {
					logger.error("Status Code returned "
							+ response.getStatusLine().getStatusCode());
					logger.error("Status Message :: "
							+ EntityUtils.toString(response.getEntity()));
					Thread.sleep(5000);
					counter++;
					if(counter<5)
						continue;
					else
						return null;
				}
				System.out.print(id + " - get executed");
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					resp = EntityUtils.toString(entity);
				} else {
					logger.error("Entity is NULL!!! Response :: "
							+ response.toString());
				}
				System.out.println("Response: " + resp);
				EntityUtils.consume(entity);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			httpget.abort();
			logger.info(id + " - error: " + e);
		}
		return resp.trim().substring(resp.lastIndexOf(":") + 1);
	}
}
