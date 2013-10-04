package org.google.api.ui.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.google.api.model.ModelObject;
import org.google.api.util.CSVInputFileParser;
import org.google.api.util.Constants;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;

import com.affbeastmode.kwassasin.model.Plan;
import com.affbeastmode.kwassasin.model.User;
import com.affbeastmode.kwassasin.service.KeywordService;

@Named("fileUploadController")
@Scope("session")
public class FileUploadController extends UIBaseControllerBean {
	private static final Log logger = LogFactory.getLog(FileUploadController.class);
	private String []columnNames = new String[]{"Keyword", "Monthly Searches","CPC($)", "Comp Pages", "Real Comp. Pages","CP Strength","Average PR","PR Strength","Competition","Commercial","Ads Count","Keyword Ranking"};
	//private TextFor
	@ManagedProperty(value = "#{chartController}")
	private ChartController observer;
	
	@Inject
	private KeywordService service;
	
	public void handleFileUpload(FileUploadEvent event) throws IOException {
		logger.info("Invoked!!");
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession(false);
		if(session.getAttribute("in-process")!=null){
			FacesMessage msg = new FacesMessage("Failed","A process in already running.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		session.removeAttribute(CURRENT_FILE_IN_PROCESS);
		FacesMessage msg = null;
		logger.info("User: "+request.getRemoteUser());
		logger.info("User: "+request.getUserPrincipal().getName());
		try {
			doUpload(event, session, request.getRemoteUser());
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succesful", event.getFile()
					.getFileName() + " is uploaded.");			
		} catch (Exception e) {
			e.printStackTrace();
			msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Failed!", event.getFile()
					.getFileName() + " is uploaded.");	
		}

		logger.info(event.getFile()
				.getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		

	}

	private void doUpload(FileUploadEvent event, HttpSession session, String userId) throws Exception{
		User user = service.findUserByUserId(userId);
		Plan plan = user.getSubscriptions().iterator().next().getPlan();
		if(service.checkSubscriptionExpiration(user))
			throw new KeywordFileUploadException(KeywordFileUploadException.SUBSCRIPTION_EXPIRED);
		File uploadedFileInServer = createUploadFileFromConetent(event.getFile().getContents(), event.getFile().getFileName());
		Collection<ModelObject> models = CSVInputFileParser.parseCSVFile(uploadedFileInServer);
		if(service.keywordRemaining(user)<=0)
			throw new KeywordFileUploadException(MessageFormat.format(KeywordFileUploadException.KEYWORD_LIMIT_EXCEED_FOR_SUBSCRIPTION, plan.getTotalKeywordLimit()));
		if(plan.getDailyRestriction()<models.size())
			throw new KeywordFileUploadException(MessageFormat.format(KeywordFileUploadException.KEYWORD_LIMIT_EXCEED_FOR_SUBSCRIPTION, plan.getDailyRestriction()));
		addObserverToModel(models);
		logger.info("Models created : "+models==null?0:models.size());
		session.setAttribute(CURRENT_FILE_IN_PROCESS, models);
		logger.info("Models bound into session");
		session.setAttribute(Constants.INPROCESS.name(), true);
	}

	private void addObserverToModel(Collection<ModelObject> models) {
		for(ModelObject model : models)
			model.addObserver(observer);
	}

	private static File createUploadFileFromConetent(byte[] contents, String fileName) throws IOException {
		File theFile = File.createTempFile(fileName, "tmp");
		OutputStream outs = new FileOutputStream(theFile);
		outs.write(contents);
		outs.close();
		return theFile;
	}
	public static void main(String args[]) throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:/Debal/keyword_ideas.csv"))));
		String line = null;
		logger.info("Starting reading file");
		StringBuilder builder = new StringBuilder();
		while((line = reader.readLine())!=null){
			builder.append(line);
		}
		reader.close();
		createUploadFileFromConetent(builder.toString().getBytes(), "testFile.csv");
	}
	
    @SuppressWarnings("unchecked")
	public StreamedContent getFile() throws IOException {
		logger.info("Going to find session");
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		logger.info("Found Session "+session);
		Collection<ModelObject> models = (Collection<ModelObject>)session.getAttribute(CURRENT_FILE_IN_PROCESS);
		logger.info("Creating out file.");
    	File outFile = new File("Keyword_Analysis_Result.csv");
    	outFile.createNewFile();
    	PrintWriter writer = new PrintWriter(outFile);
    	BufferedWriter bw = new BufferedWriter(writer);
    	StringBuilder builder = new StringBuilder();
    	for(String str : columnNames)
    		builder.append(str).append(",");
    	bw.write(builder.toString());
    	bw.newLine();
    	int counter =0;
    	for(ModelObject model : models){
    		builder = new StringBuilder(model.outputModel());
        	bw.write(builder.toString());
        	bw.newLine();
        	counter++;
    	}
    	logger.info("Wrote "+counter+" rows into the file.");
    	bw.flush();
    	bw.close();
    	return new DefaultStreamedContent(new FileInputStream(outFile), "application/csv", outFile.getName());
    }

	public void setObserver(ChartController observer) {
		this.observer = observer;
	}

	public void setService(KeywordService service) {
		this.service = service;
	}	

}
