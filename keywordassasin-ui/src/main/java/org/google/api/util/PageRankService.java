package org.google.api.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.google.api.http.HttpWebUtil;

public class PageRankService {
	static final public String [] GOOGLE_PR_DATACENTER_IPS = new String[]{
        "64.233.161.100",
        "64.233.161.101",
        "64.233.177.17",
        "64.233.183.91",
        "64.233.185.19",
        "64.233.189.44",
        "66.102.1.103",
        "66.102.9.115",
        "66.249.81.101",
        "66.249.89.83",
        "66.249.91.99",
        "66.249.93.190",
        "72.14.203.107",
        "72.14.205.113",
        "72.14.255.107",
        "toolbarqueries.google.com",
        };
	private static final Log logger = LogFactory.getLog(PageRankService.class);
	public static List<Integer> getPR(String []domainsToWorkWith) {
       List<Integer>pageRank = new ArrayList<Integer>(domainsToWorkWith.length);
        int counter=15;
        logger.info("Total domains : "+domainsToWorkWith.length);
        while(true){
        	try {
        		String []domains = new String [domainsToWorkWith.length];
        		System.arraycopy(domainsToWorkWith, 0, domains, 0, domainsToWorkWith.length);
        		for(int i=0;i<domains.length;i++){
        			StringBuilder builder = new StringBuilder();
        			JenkinsHash jHash = new JenkinsHash();
        			long hash = jHash.hash(("info:" + domains[i]).getBytes());
        			builder.append("http://").append(GOOGLE_PR_DATACENTER_IPS[counter]).
        				append("/tbr?client=navclient-auto&hl=en&").append("ch=6").
        					append(hash).append("&ie=UTF-8&oe=UTF-8&features=Rank&q=info:").append(domains[i]);
        			domains[i] = builder.toString();
        		}
				pageRank = fetchPageRank(domains);
				if(pageRank==null){
					logger.warn("Page rank fetcher returned null!!!");
					continue;
				}
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        return pageRank;
    }
	private static List<Integer>fetchPageRank(String []domain) throws Exception{
		HttpWebUtil webUtil = HttpWebUtil.getInstance();
		String []retVal = webUtil.firePageRankGetRequest(domain);
		List<Integer> ranks = new ArrayList<Integer>();
		//int []ranks = new int[retVal.length];
		for(String rankStr : retVal){
			if(rankStr==null)
				ranks.add(10);
			else
				ranks.add(Integer.parseInt(rankStr.length()==0?"10":rankStr));
				//ranks[counter++] = Integer.parseInt(rankStr);
		}
		return ranks;
	}
	
	public static void main(String args[]) throws Exception{
		String []domains = new String[]{"http://www.4shared.com", "http://www.rapidshare.com", "http://github.com", "http://sourceforge.net"};
		System.out.println(PageRankService.getPR(domains));
	}

}
