package org.google.api.ui.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.google.api.model.ModelObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.affbeastmode.kwassasin.model.User;
import com.affbeastmode.kwassasin.service.KeywordService;

@Component
@ManagedBean(name="statusBean")
@SessionScoped
public class OperationStatusController extends UIBaseControllerBean {
	private static final Log logger = LogFactory.getLog(OperationStatusController.class);
	
	@Autowired
	private KeywordService service;
	@SuppressWarnings("unchecked")
	public Collection<ModelObject> getModels(){
		logger.info("Request Arrived.");
		Collection<ModelObject> modelsToOutput = new ArrayList<ModelObject>();
		logger.info("Going to find session");
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		logger.info("Found Session "+session);
		Collection<ModelObject> models = (Collection<ModelObject>)session.getAttribute(CURRENT_FILE_IN_PROCESS);
		
		if(models!=null && !models.isEmpty()){
			logger.info("Found models "+models.size());
			List<ModelObject> sortedModels = new ArrayList<ModelObject>(models.size());
			sortedModels.addAll(models);			
			Collections.sort(sortedModels);
			modelsToOutput.addAll(sortedModels);
		}else
			logger.info("No models found!!");
		logger.info("Sending back "+modelsToOutput.size()+" transformed objects working with "+(models==null?0:models.size())+" core objects.");
		return modelsToOutput;
	}
	
	public boolean getPoll(){
		logger.info("Poll request arrived-getPoll!!");
		return true;
	}
	
	public boolean poll(){
		logger.info("Poll request arrived-poll!!");
		return true;
	}
	
	public boolean getSubscriptionStatus() throws Exception{
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext();
		User user = service.findUserByUserId(request.getRemoteUser());
		return user.getSubscriptions().iterator().next().getPlanEndDate().after(new Date());
	}
	
	public String getPlanEndDate() throws Exception{
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext();
		User user = service.findUserByUserId(request.getRemoteUser());
		DateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		return format.format(user.getSubscriptions().iterator().next().getPlanEndDate());
	}
}
