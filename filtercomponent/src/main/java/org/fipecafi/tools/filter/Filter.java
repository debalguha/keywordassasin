package org.fipecafi.tools.filter;

public abstract class Filter implements IFilter{
	protected boolean defaultAttr;
	protected String filterName;
	protected String type;
	
	
	public boolean isDefaultAttr() {
		return defaultAttr;
	}
	public void setDefaultAttr(boolean defaultAttr) {
		this.defaultAttr = defaultAttr;
	}
	public String getFilterName() {
		return filterName;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
