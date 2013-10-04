package org.google.api.ui.listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.google.api.model.ModelObject;
import org.google.api.ui.controller.UIBaseControllerBean;
import org.google.api.util.Constants;
import org.google.api.util.ResearchUtil;

public class KeyWordResearchSessionListener implements
		HttpSessionAttributeListener {
	
	@SuppressWarnings("unchecked")
	@Override
	public void attributeAdded(final HttpSessionBindingEvent event) {
		if(event.getName().equals(UIBaseControllerBean.CURRENT_FILE_IN_PROCESS)){
/*			final Collection<ModelObject> models = (Collection<ModelObject>)event.getValue();
			final String configFileContent = event.getSession().getServletContext().getAttribute(Constants.CONFIGFILEFORURLFINDER.name()).toString();
			final double []cpcRanges = findCPCRange(models);
			new Thread(new Runnable(){
				@Override
				public void run() {
					ResearchUtil.getInstance().beginResearch(models, configFileContent, cpcRanges);
					event.getSession().removeAttribute(Constants.INPROCESS.name());
				}
			}).start();*/
		}
	}

	private double[] findCPCRange(Collection<ModelObject> models) {
		double []retVal = new double[3];
		double meanCPC = 0.0d;
		Collection<Double> cpcPopulation = new ArrayList<Double>();
		for(ModelObject model : models){
			cpcPopulation.add(model.getCpc());
			meanCPC += model.getCpc();
		}
		retVal[0] = Collections.min(cpcPopulation);
		retVal[1] = meanCPC/models.size();;
		retVal[2] = Collections.max(cpcPopulation);
		return retVal;
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub

	}

}
