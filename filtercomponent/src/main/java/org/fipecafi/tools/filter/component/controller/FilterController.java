package org.fipecafi.tools.filter.component.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.fipecafi.tools.filter.FilterXMLParser;
import org.fipecafi.tools.filter.IFilter;
import org.fipecafi.tools.filter.demo.servlet.DerbyUtil;

@ManagedBean(name = "filterController")
@SessionScoped
public class FilterController {
	// private Map<String, FilterWrapper> filterConfig;
	private Map<String, IFilter> filterMap;
	private List<IFilter> optionFilters;
	private List<IFilter> dateFilters;
	private String choice;
	public FilterController() {
		optionFilters = new ArrayList<IFilter>();
		dateFilters = new ArrayList<IFilter>();
		InputStream is;
		try {
			is = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/filter.xml");
			if(is!=null)
				init(is);
			else
				System.out.println("Input stream is null!!");			
		} catch (RuntimeException e) {
			//e.printStackTrace();
			if(!Boolean.getBoolean("junit"))
				throw e;
		}
	}
	
	public void init(InputStream is){
		try {
			FilterDBVisitor visitor = new FilterDBVisitor(DerbyUtil.getConnection());
			filterMap = FilterXMLParser.filterMap(FilterXMLParser.marshallXmlConfig(is).getFilters());
			for (IFilter filter : filterMap.values()){
				filter.accept(visitor);
				if(filter.getType().equalsIgnoreCase("DATE"))
					dateFilters.add(filter);
				else
					optionFilters.add(filter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getMenuCount() {
		return filterMap.keySet().size();
	}

	public Collection<String> getFilterNames() {
		return filterMap.keySet();
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public List<IFilter> getOptionFilters() {
		return optionFilters;
	}

	public List<IFilter> getDateFilters() {
		return dateFilters;
	}

	public Map<String, IFilter> getFilterMap() {
		return filterMap;
	}
}
