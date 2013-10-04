package org.goolgeapi.keyword.research;

import org.google.api.model.ModelObject;

public interface Enricher {
	public void enrich(ModelObject model, EnricherContext ctx) throws Exception;
	public int getSequence();
}
