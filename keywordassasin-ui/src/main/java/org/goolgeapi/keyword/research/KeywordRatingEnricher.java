package org.goolgeapi.keyword.research;

import org.google.api.model.ModelObject;
import org.google.api.util.Strength;

public class KeywordRatingEnricher implements Enricher {

	@Override
	public void enrich(ModelObject model, EnricherContext ctx) throws Exception {
		if(!model.isCommercialIntent()){
			if(model.getCompetitionStrength().equals(Strength.MODERATE))
				model.setKeywordRating(Strength.GOOD);
			else
				model.setKeywordRating(Strength.BAD);
		}else{
			switch(model.getCompetitionStrength()){
				case VERY_LOW: 
					model.setKeywordRating(Strength.AMAZING);
					break;
				case LOW:
					model.setKeywordRating(Strength.GREAT);
					break;	
				case MODERATE:
					model.setKeywordRating(Strength.GOOD);
					break;	
				case HIGH:
					model.setKeywordRating(Strength.BAD);
					break;	
				case VERY_HIGH:
					model.setKeywordRating(Strength.TERRIBLE);
					break;						
			default:
				model.setKeywordRating(Strength.UNKNOWN);
				break;
			}
		}
	}

	@Override
	public int getSequence() {
		return 8;
	}

}
