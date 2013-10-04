package org.goolgeapi.keyword.research;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.google.api.model.ModelObject;

public class ResearchTask implements Runnable {
	private Map<Integer, Enricher>enricherMap;
	private double[] cpcRange;
	private String scrapperConfigurationFileContent;
	private ModelObject model;
	private static final Log logger = LogFactory.getLog(ResearchTask.class);
	
	
	public ResearchTask(Map<Integer, Enricher> enricherMap, double[] cpcRange,
			String scrapperConfigurationFileContent, ModelObject model) {
		super();
		this.enricherMap = enricherMap;
		this.cpcRange = cpcRange;
		this.scrapperConfigurationFileContent = scrapperConfigurationFileContent;
		this.model = model;
	}


	@Override
	public void run() {
		EnricherContext ctx = new EnricherContext();
		ctx.addParam(ResearchConstants.CPCRANGE.name(), cpcRange);
		ctx.addParam(ResearchConstants.SCRAPPERCONFIG.name(), scrapperConfigurationFileContent);
		logger.info("Totoal enrichers "+enricherMap.size());
		for(Integer seq : enricherMap.keySet()){
			Enricher enricher = enricherMap.get(seq);
			try {
				logger.info("Invoking enricher : "+enricher);
				enricher.enrich(model, ctx);
				logger.info("Finished Enricher.");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Failed in enricher "+enricher+". With model "+model, e);
				throw new RuntimeException(e);
			}
		}
	}

}
