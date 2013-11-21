package org.google.api.ui.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.affbeastmode.kwassasin.model.User;
import com.affbeastmode.kwassasin.model.UserTransaction;
import com.affbeastmode.kwassasin.service.KeywordService;

@Component
@ManagedBean(name="profileController")
@SessionScoped
public class ProfileController {
	private static final Log logger = LogFactory.getLog(ProfileController.class);
	@Autowired
	private KeywordService service;
	
	public Collection<UserTransaction> getAllTransactions(){
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		List<UserTransaction> allTxns = new ArrayList<UserTransaction>();
		User user = null;
		try {
			logger.info("Going to find the User object for "+request.getRemoteUser());
			user = service.findUserByUserId(request.getRemoteUser());
			logger.info("Found User object. With total "+user.getTransactions()==null?0:user.getTransactions().size()+" transactions.");
			if(user.getTransactions()!=null)
				allTxns.addAll(user.getTransactions());
			return allTxns;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<UserTransaction>(0);
	}
	
	public void unsubscribe(){
		logger.info("unsubscribe clicked.");
	}
	
	public void removeProfile(){
		logger.info("removeProfile clicked.");
	}
}
