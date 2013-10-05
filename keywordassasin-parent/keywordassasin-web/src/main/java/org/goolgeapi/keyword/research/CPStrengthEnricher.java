package org.goolgeapi.keyword.research;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.google.api.model.ModelObject;
import org.google.api.util.Strength;

public class CPStrengthEnricher implements Enricher {
	private static Log logger = LogFactory.getLog(CPStrengthEnricher.class);
	@Override
	public void enrich(ModelObject model, EnricherContext ctx)throws Exception {
		int vlRangeMin = 200;
		int lowRangeMin = 201;
		int lowRangeMax = 399;
		int moderateRangeMin = 400;
		int moderateRangeMax = 499;
		int highRangeMin = 500;
		int highRangeMax = 699;
		int vhRangeMin = 700;
		int realCompPages = new Long(model.getRealNumberOfCompetitivePages()).intValue();
		logger.info("Begining CP Strength Computation: "+realCompPages);
		if(realCompPages<=vlRangeMin)
			model.setCompetitivePageStrength(Strength.VERY_LOW);
		else if(realCompPages>lowRangeMin && realCompPages<=lowRangeMax)
			model.setCompetitivePageStrength(Strength.LOW);
		else if(realCompPages>moderateRangeMin && realCompPages<=moderateRangeMax)
			model.setCompetitivePageStrength(Strength.MODERATE);
		else if(realCompPages>highRangeMin && realCompPages<=highRangeMax)
			model.setCompetitivePageStrength(Strength.HIGH);
		else if(realCompPages>vhRangeMin)
			model.setCompetitivePageStrength(Strength.VERY_HIGH);
		//logger.info("Competition computed :: "+model.getCompetitionStrength().name());
		logger.info("Competition computed :: "+model.getCompetitivePageStrength().name());
	}
	public int getSequence(){
		return 4;
	}	
}
