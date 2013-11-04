package org.fipecafi.tools.filter;

public interface IFilter {
	public abstract void accept(FilterVisitor visitor);
	public abstract String getFilterName();
	public abstract String getType();
}
