/**
 * 
 */
package org.google.api.services.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.google.api.services.SessionProvider;

import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.axis.v201302.cm.Language;
import com.google.api.ads.adwords.axis.v201302.cm.Location;
import com.google.api.ads.adwords.axis.v201302.cm.Paging;
import com.google.api.ads.adwords.axis.v201302.o.AttributeType;
import com.google.api.ads.adwords.axis.v201302.o.IdeaType;
import com.google.api.ads.adwords.axis.v201302.o.LanguageSearchParameter;
import com.google.api.ads.adwords.axis.v201302.o.LocationSearchParameter;
import com.google.api.ads.adwords.axis.v201302.o.RelatedToQuerySearchParameter;
import com.google.api.ads.adwords.axis.v201302.o.RequestType;
import com.google.api.ads.adwords.axis.v201302.o.SearchParameter;
import com.google.api.ads.adwords.axis.v201302.o.TargetingIdea;
import com.google.api.ads.adwords.axis.v201302.o.TargetingIdeaPage;
import com.google.api.ads.adwords.axis.v201302.o.TargetingIdeaSelector;
import com.google.api.ads.adwords.axis.v201302.o.TargetingIdeaServiceInterface;
import com.google.api.ads.adwords.lib.client.AdWordsSession;

/**
 * @author debal
 * 
 */
public class TargetingIdeaServiceWrapper extends AbstractServiceWraper {
	private static AdWordsServices adwordsServices = new AdWordsServices();
	public static final String REQ_KEYWORD = "REQ_KEYWORD";
	private boolean initialized = false;

	@Override
	public void initWrapper() throws Exception {
		initialized = true;
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}

	@SuppressWarnings("unused")
	@Override
	public Map<?, ?> handleParameterInternal(Map<String, Object> paramMap)
			throws Exception {
		String email = paramMap.get(REQ_TOKEN.EMAIL).toString();
		AdWordsSession session = SessionProvider
				.createOrFindAdWordSession(email);
		TargetingIdeaServiceInterface targetingIdeaService = adwordsServices
				.get(session, TargetingIdeaServiceInterface.class);
		TargetingIdeaSelector selector = new TargetingIdeaSelector();
		selector.setRequestType(RequestType.STATS);
		selector.setIdeaType(IdeaType.KEYWORD);
		selector.setRequestedAttributeTypes(new AttributeType[] {
				AttributeType.KEYWORD_TEXT, AttributeType.SEARCH_VOLUME,
				AttributeType.AVERAGE_CPC });
        RelatedToQuerySearchParameter relatedToQuerySearchParameter = new RelatedToQuerySearchParameter();
        relatedToQuerySearchParameter.setQueries((String [])paramMap.get(REQ_KEYWORD));
        List<SearchParameter> searchParams = new ArrayList<SearchParameter>();
        searchParams.add(relatedToQuerySearchParameter);
		if (paramMap.containsKey(REQ_TOKEN.LANG_CODE)) {
			Language language = new Language();
			language.setCode(paramMap.get(REQ_TOKEN.LANG_CODE).toString());
	        LanguageSearchParameter languageSearchParameter = new LanguageSearchParameter();
	        languageSearchParameter.setLanguages(new Language[]{language});
	        searchParams.add(languageSearchParameter);
		}
		if (paramMap.containsKey(REQ_TOKEN.LOC_CODE)) {
			Location location = new Location();
			location.setLocationName(paramMap.get(REQ_TOKEN.LOC_CODE).toString());
	        LocationSearchParameter locationSearchParameter = new LocationSearchParameter();
	        locationSearchParameter.setLocations(new Location[]{location});
	        searchParams.add(locationSearchParameter);
		}
		selector.setLocaleCode("US");
        Paging paging = new Paging();
        paging.setStartIndex(0);
        paging.setNumberResults(10000);
        selector.setPaging(paging);
 
        TargetingIdeaPage page = targetingIdeaService.get(selector);       
        if (page.getEntries() != null && page.getEntries().length > 0) {
        	for (TargetingIdea targetingIdea : page.getEntries()) {
               /* Map<AttributeType, Attribute> data = MapUtils.toMap(targetingIdea.getData());
                String kwd = ((StringAttribute) data.get(AttributeType.KEYWORD_TEXT)).getValue();
                Long monthlySearches = ((LongAttribute) data.get(AttributeType.SEARCH_VOLUME)).getValue();
                 
                System.out.println(kwd + ": " + monthlySearches);*/
            }
        }
		return null;
	}

}
