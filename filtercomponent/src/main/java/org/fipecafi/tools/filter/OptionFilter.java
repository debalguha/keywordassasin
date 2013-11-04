package org.fipecafi.tools.filter;

import java.util.Collection;
import java.util.List;


public class OptionFilter extends Filter {
	protected List<Option> options;
	protected int[] values;
	public OptionFilter(FilterPojo filterPojo){
		this.options = filterPojo.getOptions();
		this.defaultAttr = filterPojo.isDefaultAttr();
		this.filterName = filterPojo.getFilterName();
		this.type = filterPojo.getType();
	}
	public Collection<Option> getOptions() {
		return options;
	}
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	@Override
	public void accept(FilterVisitor visitor) {
		visitor.visit(this);
	}
	public int[] getValues() {
		return values;
	}
	public void setValues(int[] values) {
		this.values = values;
	}	
}
