package org.goolgeapi.keyword.research;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.google.api.model.ModelObject;
import org.google.api.util.PageRankService;

public class PageRankEnricher implements Enricher {
	private static Log logger = LogFactory.getLog(PageRankEnricher.class);
	@SuppressWarnings("unchecked")
	@Override
	public void enrich(ModelObject model, EnricherContext ctx) throws Exception {
		logger.info("Rank Enricher invoked with keyword : "+model.getKeyword());
		Collection<String> urls = (Collection<String>)ctx.getParamValue(ResearchConstants.URLLIST.name());
		List<String> domains = new ArrayList<String>();
		for(String url : urls){
			String trimmedURL = trimURLString(url);
			if(trimmedURL!=null && !trimmedURL.isEmpty())
				domains.add("http://".concat(trimmedURL));
		}
		logger.info("Domains to query for : "+domains);
		List<Integer> ranks = PageRankService.getPR(domains.toArray(new String[0]));
		logger.info("Ranks obtained:: "+Arrays.asList(ranks));
		double avgPageRank = 0.0d;
		for(int rank : ranks)
			avgPageRank+=rank;
		logger.info("Average Rank : "+(avgPageRank/(ranks.size())));
		model.setAveragePageRank(avgPageRank/(ranks.size()));
		ctx.addParam(ResearchConstants.RANKINFO.name(), ranks);
	}
	private String trimURLString(String url) throws Exception{
		String nextString = url.substring(url.indexOf("http://")+"http://".length());
		String theOtherString = nextString.substring(0, nextString.indexOf("/"));
		return theOtherString;
	}
	
	public static void main(String []args) throws Exception{
		String str = "/url?q=http://www.dog-obedience-training-review.com/&sa=U&ei=evQZUoC4Gcj4rQfMwIGQCA&ved=0CHgQFjAU&usg=AFQjCNG5OSSdUMmkj7PENRitPWoVxAl0xg";
		String nextString = str.substring(str.indexOf("http://")+"http://".length());
		System.out.println(nextString);
		String theOtherString = nextString.substring(0, nextString.indexOf("/"));
		System.out.println(theOtherString);
	}
	public int getSequence(){
		return 2;
	}	
}
