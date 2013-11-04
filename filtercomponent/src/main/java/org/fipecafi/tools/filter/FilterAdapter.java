package org.fipecafi.tools.filter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class FilterAdapter extends XmlAdapter<FilterPojo, Filter> {

	@Override
	public Filter unmarshal(FilterPojo filterPojo) throws Exception {
		String type = filterPojo.getType();
		if(type.equalsIgnoreCase("OPTIONS"))
			return new OptionFilter(filterPojo);
		else if(type.equalsIgnoreCase("QUERY"))
			return new QueryFilter(filterPojo);
		else if(type.equalsIgnoreCase("DATE"))
			return new DateFilter(filterPojo);
		return null;
	}

	@Override
	public FilterPojo marshal(Filter filter) throws Exception {
		return null;
	}

}
