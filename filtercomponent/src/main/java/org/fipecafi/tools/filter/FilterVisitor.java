package org.fipecafi.tools.filter;

public interface FilterVisitor {
	public void visit(QueryFilter filter);
	public void visit(OptionFilter filter);
	public void visit(DateFilter filter);
}
