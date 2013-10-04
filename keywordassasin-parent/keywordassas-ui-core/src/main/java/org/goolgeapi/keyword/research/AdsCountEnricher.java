package org.goolgeapi.keyword.research;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.google.api.model.ModelObject;
import org.google.api.util.ResearchUtil;

public class AdsCountEnricher implements Enricher {
	//private Pattern pattern = Pattern.compile("<li class=\"ads-ad\">.*</li>");
	private static final Log logger = LogFactory.getLog(AdsCountEnricher.class);
	@Override
	public void enrich(ModelObject model, EnricherContext ctx) throws Exception {
		logger.info("AdsCountEnricher invoked with keyword :: "+model.getKeyword());
		String adCountConfigContent = ctx.getParamValue(ResearchConstants.SCRAPPERCONFIGFORADCOUNT.name()).toString();
		int adCounter = ResearchUtil.scrapeGoogleForAdCount(model.getKeyword(), adCountConfigContent);
		logger.info("Total ad count "+adCounter);
		model.setAdsCount(adCounter);
	}
	public int getSequence(){
		return 6;
	}
	public static void main(String args[])throws Exception{
		AdsCountEnricher enricher = new AdsCountEnricher();
		ModelObject obj = new ModelObject();
		obj.setKeyword("Dog training books");
		EnricherContext ctx = new EnricherContext();
		ctx.addParam(ResearchConstants.SCRAPPERCONFIGFORADCOUNT.name(), "<?xml version=\"1.0\" encoding=\"UTF-8\"?><config><var-def name=\"webpage\"><html-to-xml><http url=\"https://www.google.com/search?q=Dog training books\"></http></html-to-xml></var-def><var-def name=\"link\"><xpath expression=\"count(//div[@id='rhs_block']//li)\"><var name=\"webpage\"/></xpath></var-def></config>");
		enricher.enrich(obj, ctx);
	}
}
