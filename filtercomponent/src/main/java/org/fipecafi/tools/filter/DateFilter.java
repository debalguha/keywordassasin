package org.fipecafi.tools.filter;

import java.util.Date;


public class DateFilter extends Filter {
	private Date startDate;
	private Date endDate;
	private int choice;
	private int withinVal;
	private String withinChoice;
	private int moreThanVal;
	private String moreThanChoice;
	public DateFilter(FilterPojo filterPojo){
		this.defaultAttr = filterPojo.isDefaultAttr();
		this.filterName = filterPojo.getFilterName();
		this.type = filterPojo.getType();
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Override
	public void accept(FilterVisitor visitor) {
		visitor.visit(this);
	}
	public int getWithinVal() {
		return withinVal;
	}
	public void setWithinVal(int withinVal) {
		this.withinVal = withinVal;
	}
	public String getWithinChoice() {
		return withinChoice;
	}
	public void setWithinChoice(String withinChoice) {
		this.withinChoice = withinChoice;
	}
	public int getMoreThanVal() {
		return moreThanVal;
	}
	public void setMoreThanVal(int moreThanVal) {
		this.moreThanVal = moreThanVal;
	}
	public String getMoreThanChoice() {
		return moreThanChoice;
	}
	public void setMoreThanChoice(String moreThanChoice) {
		this.moreThanChoice = moreThanChoice;
	}
	public int getChoice() {
		return choice;
	}
	public void setChoice(int choice) {
		this.choice = choice;
	}
}
