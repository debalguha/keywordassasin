package org.fipecafi.tools.filter;

import java.util.ArrayList;
import java.util.List;

public class FilterWrapper {
	private String filterName;
	private List<Filter> filters;
	public String getFilterName() {
		return filterName;
	}
	public List<Filter> getFilters() {
		return filters;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}
	public void addFilter(Filter filter){
		if(filters == null)
			filters = new ArrayList<Filter>();
		filters.add(filter);
	}
}
