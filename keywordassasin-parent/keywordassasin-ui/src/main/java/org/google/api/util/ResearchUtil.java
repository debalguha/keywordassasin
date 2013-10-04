package org.google.api.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.google.api.model.ModelObject;
import org.goolgeapi.keyword.research.Enricher;
import org.goolgeapi.keyword.research.ResearchConstants;
import org.goolgeapi.keyword.research.ResearchTask;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.ScraperContext;
import org.webharvest.runtime.variables.Variable;
import org.xml.sax.InputSource;

public class ResearchUtil {
	private static ResearchUtil theInstance = new ResearchUtil();
	private static String ENRICHER_PACKAGE = "org.goolgeapi.keyword.research.";
	private static final Log logger = LogFactory.getLog(ResearchUtil.class);
	//private ExecutorService service = new ThreadPoolExecutor(500, 1000,Long.parseLong("60"), TimeUnit.MINUTES,new ArrayBlockingQueue<Runnable>(3000));
	private static AtomicBoolean inititalized = new AtomicBoolean(false);
	private Map<Integer, Enricher> enricherMap = new TreeMap<Integer, Enricher>();

	public static ResearchUtil getInstance(String[] enrichers) {
		synchronized (ResearchUtil.class) {
			if (!inititalized.get()) {
				inititalized.compareAndSet(false, true);
				try {
					theInstance.init(enrichers);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}

		return theInstance;
	}
	
	public static ResearchUtil getInstance(){
		return theInstance;
	}
	@SuppressWarnings("unchecked")
	private void init(String[] enrichers) throws Exception {
		int dummySequence = 3;
		logger.info("Initializing enrichers");
		for(String enricherStr : enrichers){
			String name = ResearchUtil.ENRICHER_PACKAGE.concat(enricherStr);
			Class<Enricher> enricherClass = (Class<Enricher>)Class.forName(name);
			Enricher enricher = enricherClass.newInstance();
			enricherMap.put(enricher.getSequence()==-1?(dummySequence++):enricher.getSequence(), enricher);
		}
		logger.info("Enrichers initialized");
	}

	public static Collection<String> scrapeGoogleForKeyword(String keyword,
			String scrapperConfigurationFileContent) {
		logger.info("Scrapping request arrived for URL!!");
		String scrapperConfigContent = scrapperConfigurationFileContent
				.replaceFirst("#searchKeyword", keyword);
		Variable urls = (Variable) doScrape(scrapperConfigContent).getContext().get("link");
		logger.info("URLS is "+urls+". Going to parse URls.");
		return parseURLSToRespectiveDomain(urls);
	}
	
	public static int scrapeGoogleForAdCount(String keyword, String scrapperConfigurationFileContent) {
		logger.info("Scrapping request arrived for AdCount!!");
		String scrapperConfigContent = scrapperConfigurationFileContent
				.replaceFirst("#searchKeyword", keyword);
		Variable var = (Variable) doScrape(scrapperConfigContent).getContext().get("link");
		logger.info("AdCount is "+var+".");
		return var.toInt();
	}	
	
	public static Map<ResearchConstants, Object> doGoogleScrape(String keyword, String scrapperConfig){
		Map<ResearchConstants, Object> retVal = new HashMap<ResearchConstants, Object>();
		logger.info("Scrapping request arrived!!");
		String scrapperConfigContent = scrapperConfig.replaceAll("#KEYWORD", keyword);
		ScraperContext ctx = doScrape(scrapperConfigContent).getContext();
		Variable linkVar = (Variable) ctx.get("links");
		retVal.put(ResearchConstants.URLLIST, parseURLSToRespectiveDomain(linkVar));
		Variable totalCompPagesVar = (Variable) ctx.get("totalCompPages");
		retVal.put(ResearchConstants.TOTALCOMPPAGES, parseCopetitivePagesText(totalCompPagesVar.toString()));
		Variable adCountVar = (Variable) ctx.get("adCount");
		retVal.put(ResearchConstants.ADCOUNT, adCountVar.toInt());
		Variable realCompPages = (Variable) ctx.get("realCompPages");
		retVal.put(ResearchConstants.REALCOMPPAGES, parseCopetitivePagesText(realCompPages.toString()));
		return retVal;
	}
	
	private static Long parseCopetitivePagesText(String element) {
		try {
			String []elements = element.split("\\s");		
			String targetElement = elements[1].replaceAll(",", "");
			return Long.parseLong(targetElement);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Unable to parse cometitive page numbers: "+element);
		}
		return 0L;
	}
	
	public static void main(String args[]) throws Exception{
		System.out.println(parseCopetitivePagesText("About 1,960,000 results"));
	}
	private static Scraper doScrape(String scrapperConfig){
		ScraperConfiguration config = new ScraperConfiguration(new InputSource(
				new StringReader(scrapperConfig)));
		Scraper scraper = new Scraper(config,
				System.getProperty("java.io.tmpdir"));
		logger.info("Scrapping execution will start!");
		scraper.execute();
		logger.info("Scrapping executed!");
		return scraper;
	}

	private static Collection<String> parseURLSToRespectiveDomain(Variable var) {
		logger.info("UR parsing request arrived!!");
		Object[] urls = var.toArray();
		logger.info("Total "+urls.length+" urls to be parsed.");
		Collection<String> validUrls = new ArrayList<String>();
		for (Object url : urls) {
			logger.info("Validating URL : "+url.toString());
			if (url.toString().startsWith("/url"))
				validUrls.add(url.toString());
		}
		logger.info("Returning "+validUrls.size()+" valid urls.");
		return validUrls;
	}

	public void beginResearch(Collection<ModelObject> models,
			String scrapperConfigForURL, double[] cpcRanges) {
		int counter=1;
		for(ModelObject model : models){
			logger.info("Proceeding to work with model : "+model);
			if(counter%5==0)
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			ResearchTask task = new ResearchTask(enricherMap, cpcRanges, scrapperConfigForURL, model);
			//service.submit(task);
			task.run();
			logger.info("End model processing.");
		}
	}
	public Map<Integer, Enricher> getEnricherMap() {
		return enricherMap;
	}
	
}
