package org.google.api.http;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.auth.params.AuthPNames;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

public class HttpWebUtil {
	private static PoolingClientConnectionManager cm = null;
	private static HttpWebUtil me = new HttpWebUtil();
	//private static AtomicInteger inProgressAtomic = new AtomicInteger(0);
	/*private Lock incLock = new ReentrantLock();
	private Lock decLock = new ReentrantLock();*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static ExecutorService service = new ThreadPoolExecutor(5, 5000,
			60, TimeUnit.SECONDS, new ArrayBlockingQueue(10000));
	private static CompletionService<String> cs = new ExecutorCompletionService<String>(
			service);

	private HttpWebUtil() {
		cm = new PoolingClientConnectionManager();
		cm.setMaxTotal(1000);
	}

	public static HttpWebUtil getInstance() {
		return me;
	}

	public String[] firePageRankGetRequest(String[] urisToGet) throws Exception {

		String[] toRet = new String[urisToGet.length];
		HttpClient httpclient = new DefaultHttpClient(cm);
		if (Boolean.getBoolean("http.proxy.enable")) {
			Credentials creds = null;
			if (Boolean.getBoolean("http.proxy.ntlm")) {
				List<String> authpref = new ArrayList<String>();
				authpref.add(AuthPolicy.NTLM);
				((DefaultHttpClient) httpclient).getAuthSchemes().register(
						"ntlm", new NTLMSchemeFactory());
				creds = new NTCredentials(
						System.getProperty("http.proxy.user"),
						System.getProperty("http.proxy.password"),
						System.getProperty("http.proxy.workstation"),
						System.getProperty("http.proxy.host"));
				httpclient.getParams().setParameter(
						AuthPNames.TARGET_AUTH_PREF, authpref);
			} else {
				creds = new UsernamePasswordCredentials(
						System.getProperty("http.proxy.user"),
						System.getProperty("http.proxy.password"));
				HttpHost proxy = new HttpHost(
						System.getProperty("http.proxy.host"),
						Integer.parseInt(System.getProperty("http.proxy.port")));
				httpclient.getParams().setParameter(
						ConnRoutePNames.DEFAULT_PROXY, proxy);
			}
			((DefaultHttpClient) httpclient).getCredentialsProvider()
					.setCredentials(
							new AuthScope(
									System.getProperty("http.proxy.host"),
									Integer.parseInt(System
											.getProperty("http.proxy.port"))),
							creds);
		}
		List<Future<String>> futs = new ArrayList<Future<String>>();
		GetThread[] threads = new GetThread[urisToGet.length];
		//incLock.lock();
		for (int i = 0; i < threads.length; i++) {
			HttpGet httpget = new HttpGet(urisToGet[i]);
			threads[i] = new GetThread(httpclient, httpget, i + 1);
			futs.add(cs.submit(threads[i]));
		}
		//incLock.unlock();
		int counter = 0;
		//decLock.lock();
		while (counter < futs.size()) {
			Future<String> fut = cs.poll();
			if (fut == null)
				Thread.sleep(999);
			else {
				toRet[counter] = fut.get();
				counter++;
			}
		}
		//decLock.unlock();
		return toRet;
	}

	public String fireGetRequest(URIBuilder builder) throws Exception {
		HttpClient httpclient = new DefaultHttpClient(cm);
		URI uriToFire = builder.build();
		HttpGet httpget = new HttpGet(uriToFire);
		System.out.println(httpget.getURI());
		HttpGet httpGet = new HttpGet(uriToFire);
		// httpGet.
		GetThread getThread = new GetThread(httpclient, httpGet,
				RandomUtils.nextInt());
		return getThread.call();
	}

	public static void main(String args[]) throws Exception {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http").setHost("www.google.com").setPath("/search")
				.setParameter("q", "httpclient")
				.setParameter("btnG", "Google Search").setParameter("aq", "f")
				.setParameter("oq", "");
		HttpWebUtil.getInstance().fireGetRequest(builder);
	}
}
