package org.goolgeapi.keyword.research;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.google.api.model.ModelObject;
import org.google.api.util.Strength;

public class PRstrengthEnricher implements Enricher {
	private static final Log logger = LogFactory.getLog(PRstrengthEnricher.class);
	@Override
	public void enrich(ModelObject model, EnricherContext ctx) throws Exception {
		double pagerankMean = 5.0d;
		double pagerankHighMean = 2.5d;
		double pagerankLowMean = 7.5d;
		logger.info("PRstrengthEnricher Enricher invoked with keyword :: "+model.getKeyword());
		double actualPageRank = model.getAveragePageRank();
		logger.info("Average Page Rank :"+actualPageRank+", Pgae Rank Mean: "+pagerankMean+", Page Rank High Mean : "+pagerankHighMean+", Page rank low mean : "+pagerankLowMean);
		
		if(actualPageRank<=pagerankMean){
			logger.info("Page Rank test-for high: "+(actualPageRank<=pagerankHighMean));
			if(actualPageRank<=pagerankHighMean)
				model.setPageRankStrentgh(Strength.VERY_HIGH);
			else
				model.setPageRankStrentgh(Strength.HIGH);
		}else{
			logger.info("Page Rank test-for low: "+(actualPageRank<=pagerankLowMean));
			if(actualPageRank<=pagerankLowMean)
				model.setPageRankStrentgh(Strength.LOW);
			else
				model.setPageRankStrentgh(Strength.VERY_LOW);
		}
		logger.info("PRStrength computed :: "+model.getPageRankStrentgh().name());
	}

	@Override
	public int getSequence() {
		return 3;
	}

}
