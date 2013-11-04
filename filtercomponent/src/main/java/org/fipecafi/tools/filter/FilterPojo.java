package org.fipecafi.tools.filter;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class FilterPojo{
	
	private boolean defaultAttr;
	
	private String filterName;
	
	private String type;
	
	private String searchField;
	
	private String query;

	private List<Option> options;
	public String getFilterName() {
		return filterName;
	}
	@XmlElement
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	public String getType() {
		return type;
	}
	@XmlElement
	public void setType(String type) {
		this.type = type;
	}
	public String getSearchField() {
		return searchField;
	}
	@XmlElement
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getQuery() {
		return query;
	}
	@XmlElement
	public void setQuery(String query) {
		this.query = query;
	}
	public List<Option> getOptions() {
		return options;
	}
	@XmlElementWrapper(name = "options")
	@XmlElement(name="option")
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	public boolean isDefaultAttr() {
		return defaultAttr;
	}
	@XmlAttribute(name="default")
	public void setDefaultAttr(boolean defaultAttr) {
		this.defaultAttr = defaultAttr;
	}
}
