package org.google.api.ui.listener;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.google.api.util.Constants;
import org.google.api.util.ResearchUtil;

public class ApplicationContextListener implements ServletContextListener {
	private static final Log logger = LogFactory.getLog(ApplicationContextListener.class);
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext ctx = event.getServletContext();
		logger.info("Config file path : "+ctx.getRealPath("/WEB-INF/classes/web-harvest-config.xml"));
		File configFile = new File(ctx.getRealPath("/WEB-INF/classes/web-harvest-config.xml"));
		File configFileForAdCount = new File(ctx.getRealPath("/WEB-INF/classes/web-harvest-ad-count-config.xml"));
		logger.info("Obtained configFile.");
		String fileContent = null;
		String fileContentForAdCount = null;
		try {
			fileContent = FileUtils.readFileToString(configFile);
			fileContentForAdCount = FileUtils.readFileToString(configFileForAdCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ctx.setAttribute(Constants.CONFIGFILEFORURLFINDER.name(), fileContent);
		ctx.setAttribute(Constants.CONFIGFILEFORADCOUNTFINDER.name(), fileContentForAdCount);
		ResearchUtil.getInstance(event.getServletContext().getInitParameter(Constants.ENRICHER_CLASS_NAMES.name()).split(","));
	}

}
