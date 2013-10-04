package org.goolgeapi.keyword.research;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.google.api.model.ModelObject;
import org.google.api.util.ResearchUtil;

public class WebHarvestEnricher implements Enricher {
	private static final Log logger = LogFactory.getLog(WebHarvestEnricher.class);
	@Override
	public void enrich(ModelObject model, EnricherContext ctx) throws Exception {
		String webHarvestConfig = ctx.getParamValue(ResearchConstants.SCRAPPERCONFIG.name()).toString();
		logger.info("Retrieve web harvest config : "+webHarvestConfig);
		Map<ResearchConstants, Object> scrapperMap = ResearchUtil.doGoogleScrape(model.getKeyword(), webHarvestConfig);
		ctx.addParam(ResearchConstants.ADCOUNT.name(), scrapperMap.get(ResearchConstants.ADCOUNT));
		ctx.addParam(ResearchConstants.REALCOMPPAGES.name(), scrapperMap.get(ResearchConstants.REALCOMPPAGES));
		ctx.addParam(ResearchConstants.TOTALCOMPPAGES.name(), scrapperMap.get(ResearchConstants.TOTALCOMPPAGES));
		ctx.addParam(ResearchConstants.URLLIST.name(), scrapperMap.get(ResearchConstants.URLLIST));
		model.setTotalNumberOfCompetitivePages((Long)scrapperMap.get(ResearchConstants.TOTALCOMPPAGES));
		model.setRealNumberOfCompetitivePages((Long)scrapperMap.get(ResearchConstants.REALCOMPPAGES));
		model.setAdsCount((Integer)scrapperMap.get(ResearchConstants.ADCOUNT));
	}

	@Override
	public int getSequence() {
		return 1;
	}
}
