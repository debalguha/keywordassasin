package org.goolgeapi.keyword.research;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.google.api.model.ModelObject;

public class CommercialIntentEnricher implements Enricher {
	private static final Log logger = LogFactory.getLog(CommercialIntentEnricher.class);
	@Override
	public void enrich(ModelObject model, EnricherContext ctx)throws Exception {
		logger.info("CommercialIntentEnricher Enricher invoked with keyword :: "+model.getKeyword());
		double []cpcRange = (double [])ctx.getParamValue(ResearchConstants.CPCRANGE.name());
		double meanCPC = cpcRange[1];
		double cpc = model.getCpc();
		logger.info("CPC : "+cpc+", MeanCPC :: "+meanCPC);
		if(cpc <= meanCPC){
			if(cpc>1.25d && model.getAdsCount()>4)
				model.setCommercialIntent(true);
			else
				model.setCommercialIntent(false);
		}else
			model.setCommercialIntent(true);
		logger.info("Computed commercial intent : "+model.isCommercialIntent());
	}
	public int getSequence(){
		return 7;
	}	
}
