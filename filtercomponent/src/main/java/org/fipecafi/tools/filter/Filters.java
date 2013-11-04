package org.fipecafi.tools.filter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Filters {

	private List<FilterPojo> filterConfigs;
	private List<IFilter> filters;

	public List<IFilter> getFilters() {
		if(filterConfigs!=null && !filterConfigs.isEmpty() && (filters==null || filters.isEmpty())){
			filters = new ArrayList<IFilter>();
			for(FilterPojo filterPojo : filterConfigs){
				String type = filterPojo.getType();
				if(type.equalsIgnoreCase("OPTIONS"))
					filters.add(new OptionFilter(filterPojo));
				else if(type.equalsIgnoreCase("QUERY"))
					filters.add(new QueryFilter(filterPojo));
				else if(type.equalsIgnoreCase("DATE"))
					filters.add(new DateFilter(filterPojo));
			}
		}
		return filters;
	}
	
	public List<FilterPojo> getFilterConfigs() {
		return filterConfigs;
	}
	@XmlElement(name="filter")
	public void setFilterConfigs(List<FilterPojo> filterConfigs) {
		this.filterConfigs = filterConfigs;
	}
}
