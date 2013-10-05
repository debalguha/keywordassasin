package org.goolgeapi.keyword.research;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.google.api.model.ModelObject;
import org.google.api.util.Strength;

public class CompetitionStrengthEnricher implements Enricher {
	private static final Log logger = LogFactory.getLog(CompetitionStrengthEnricher.class);
	@Override
	public void enrich(ModelObject model, EnricherContext ctx) throws Exception {
		Strength cpStrength = model.getCompetitivePageStrength();
		Strength prStrength = model.getPageRankStrentgh();
		logger.info("Proceeding to compute competition strength - ["+cpStrength+", "+prStrength+"]");
		Strength targetCompStrength = findCompetitionStrength(cpStrength, prStrength);
		model.setCompetitionStrength(targetCompStrength);
		logger.info("Computed competition Strength "+targetCompStrength);
	}

	private Strength findCompetitionStrength(Strength cpStrength, Strength prStrength){
		int cpWeight = cpStrength.getWeight();
		int prWeight = prStrength.getWeight();
		int cumulativeWeight = Integer.parseInt(String.valueOf(cpWeight).concat(String.valueOf(prWeight)));
		if(checkVeryLowRange(cumulativeWeight))
			return Strength.VERY_LOW;
		if(checkLowRange(cumulativeWeight))
			return Strength.LOW;
		if(checkModerateRange(cumulativeWeight))
			return Strength.MODERATE;
		if(checkHighRange(cumulativeWeight))
			return Strength.HIGH;
		if(checkVeryHighRange(cumulativeWeight))
			return Strength.VERY_HIGH;		
		return Strength.UNKNOWN;
	}
	
	private boolean checkVeryLowRange(int weight){
		if(weight>=53)
			return true;
		return false;
	}
	
	private boolean checkLowRange(int weight){
		if(weight<53 && weight>=43)
			return true;
		return false;
	}
	
	private boolean checkModerateRange(int weight){
		if(weight<43 && weight>=33)
			return true;
		return false;
	}	
	
	private boolean checkHighRange(int weight){
		if(weight<33 && weight>=23)
			return true;
		return false;
	}	
	
	private boolean checkVeryHighRange(int weight){
		if(weight<23)
			return true;
		return false;
	}	
	@Override
	public int getSequence() {
		return 5;
	}

}
