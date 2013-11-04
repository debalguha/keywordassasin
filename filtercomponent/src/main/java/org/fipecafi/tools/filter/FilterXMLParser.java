package org.fipecafi.tools.filter;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class FilterXMLParser {
	public static Filters marshallXmlConfig(InputStream configFile) throws Exception{
		JAXBContext ctx = JAXBContext.newInstance(Filters.class);
		Unmarshaller unmarshaller = ctx.createUnmarshaller();
		Filters filters = (Filters)unmarshaller.unmarshal(configFile);
		return filters;
	}
	public static Map<String, IFilter> filterMap(List<IFilter> filters){
		Map<String, IFilter> filterMap = new HashMap<String, IFilter>();
		for(IFilter filter : filters)
			filterMap.put(filter.getFilterName(), filter);
		return filterMap;
	}
	public static Map<String, FilterWrapper> wrapFilters(List<Filter> allFilters){
		Map<String, FilterWrapper> wrapperMap = new HashMap<String, FilterWrapper>();
		for(Filter filter : allFilters){
			String filterName = filter.getFilterName();
			FilterWrapper wrapper = wrapperMap.get(filterName);
			if(wrapper==null){
				wrapper = new FilterWrapper();
				wrapper.setFilterName(filterName);
				wrapperMap.put(filterName, wrapper);
			}
			wrapper.addFilter(filter);
		}
		return wrapperMap;
	}
}
