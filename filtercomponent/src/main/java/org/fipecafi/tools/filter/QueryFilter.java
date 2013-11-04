package org.fipecafi.tools.filter;

public class QueryFilter extends OptionFilter {
	private String query;
	private String searchField;

	public QueryFilter(FilterPojo filterPojo){
		super(filterPojo);
		this.query = filterPojo.getQuery();
		this.searchField = filterPojo.getSearchField();
	}
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	@Override
	public void accept(FilterVisitor visitor) {
		visitor.visit(this);
	}
}
