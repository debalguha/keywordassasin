package org.google.api.ui.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.google.api.model.ModelObject;
import org.google.api.util.Constants;
import org.google.api.util.ResearchUtil;
import org.goolgeapi.keyword.research.ResearchTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ManagedBean(name="activityController")
@SessionScoped
public class ActivityController {
	private ModelProcessor processor;
	private boolean disabledStartState = false;
	private boolean disabledPauseState = true;
	private boolean disabledResumeState = true;
	private boolean disabledStopState = true;
	private boolean pollStart = false;
	private boolean pollStop = false;
	private static Log logger = LogFactory.getLog(ActivityController.class);
	
	@Autowired//(name ="chartController", value="#{chartController}")
	private ChartController controller;
	
	@SuppressWarnings("unchecked")
	public void start(){
		logger.info("ActivityController start invoked!!");
		final HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		final Collection<ModelObject> models = (Collection<ModelObject>)session.getAttribute(UIBaseControllerBean.CURRENT_FILE_IN_PROCESS);
		final String configFileContent = session.getServletContext().getAttribute(Constants.CONFIGFILEFORURLFINDER.name()).toString();
		final double []cpcRanges = findCPCRange(models);
		if(models==null || models.isEmpty())
			return;
		controller.clear();
		processor = new ModelProcessor(models, configFileContent, cpcRanges, session);
		processor.start();
		this.disabledStartState = true;
		this.disabledPauseState = false;
		this.disabledResumeState = true;
		this.disabledStopState = false;
		this.pollStart = true;
		this.pollStop = false;
	}
	public void pause(){
		logger.info("ActivityController pause invoked!!");
		processor.pauseProcessing();
		this.disabledPauseState = true;
		this.disabledResumeState = false;
		this.disabledStartState = true;
		this.disabledStopState = false;
		this.pollStart = false;
		this.pollStop = true;		
	}
	public void stop(){
		logger.info("ActivityController stop invoked!!");
		processor.stopProcessing();
		this.disabledStopState = true;
		this.disabledPauseState = true;
		this.disabledResumeState = true;
		this.disabledStartState = false;
		this.pollStart = false;
		this.pollStop = true;	
	}
	public void resume(){
		logger.info("ActivityController resume invoked!!");
		processor.resumeProcessing();
		this.disabledResumeState = true;
		this.disabledPauseState = false;
		this.disabledStopState = false;
		this.disabledStartState = true;
		this.pollStart = true;
		this.pollStop = false;		
	}
	public void resetStates(){
		this.disabledStartState = false;
		this.disabledPauseState = true;
		this.disabledResumeState = true;
		this.disabledStopState = true;
		this.pollStart = false;
		this.pollStop = true;
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
	
	public class ModelProcessor extends Thread{
		private Collection<ModelObject> allModels;
		private String webHarvestConfig;
		private double[] cpcRanges;
		private volatile boolean pause;
		private volatile boolean stop;
		private final Log logger = LogFactory.getLog(ModelProcessor.class);
		private HttpSession session;
		public ModelProcessor(Collection<ModelObject> allModels,
				String webHarvestConfig, double[] cpcRanges, HttpSession theSession) {
			super();
			this.allModels = allModels;
			this.webHarvestConfig = webHarvestConfig;
			this.cpcRanges = cpcRanges;
			this.session = theSession;
			this.stop = false;
			this.pause = false;
		}

		public void pauseProcessing(){
			this.pause = true;
		}
		
		public void resumeProcessing(){
			this.pause = false;
		}
		
		public void stopProcessing(){
			this.stop = true;
		}
		@Override
		public void run() {
			try {
				for(ModelObject model : allModels){
					if(this.stop){
						logger.warn("Stop requested!!");
						break;
					}
					while(this.pause){
						try {
							logger.info("Processing paused for 5 seconds!");
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if(model.isProcessingCompleted())
						continue;
					logger.info("Proceeding to work with model : "+model);
					//randomizeKeywordRating(model);
					//Thread.sleep(1000);
					ResearchTask task = new ResearchTask(ResearchUtil.getInstance().getEnricherMap(), cpcRanges, webHarvestConfig, model);
					task.run();
					logger.info("End model processing.");
					model.setProcessingCompleted(true);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error occurred in processor thread!!", e);
			}finally{
				session.removeAttribute(Constants.INPROCESS.name());
				resetStates();
			}
		}

		/*private void randomizeKeywordRating(ModelObject model) {
			Strength []acceptableStrengths = new Strength[]{Strength.AMAZING, Strength.GREAT, Strength.GOOD, Strength.BAD, Strength.TERRIBLE};
			model.setKeywordRating(acceptableStrengths[RandomUtils.nextInt(acceptableStrengths.length)]);
			logger.info("Keyword rating now: "+model.getKeywordRating());
		}*/
	}
	public boolean getStartState(){
		logger.info("Start state returning : "+disabledStartState);
		return disabledStartState;
	}
	public boolean getPauseState(){
		logger.info("Pause state returning : "+disabledPauseState);
		return disabledPauseState;
	}
	public boolean getResumeState(){
		logger.info("Resume state returning : "+disabledResumeState);
		return disabledResumeState;
	}
	public boolean getStopState(){
		logger.info("Stop state returning : "+disabledStopState);
		return disabledStopState;
	}
	public boolean getPollStart(){
		return this.pollStart;
	}
	public boolean getPollStop(){
		logger.info("Poll stop returning : "+disabledStopState);
		return this.pollStop;
	}
	public void setController(ChartController controller) {
		this.controller = controller;
	}
}
