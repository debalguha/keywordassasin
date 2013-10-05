package org.google.api.model;

import org.google.api.ui.listener.ModelObserver;
import org.google.api.util.Strength;

public class ModelObject implements Comparable<ModelObject> {
	private String keyword;
	private long totalNumberOfCompetitivePages;
	private long realNumberOfCompetitivePages;
	private Strength competitivePageStrength;
	private Strength competitionStrength;
	private double averagePageRank;
	private Strength pageRankStrentgh;
	private double adwordCompetition;
	private int localMonthlySearches;
	private int globalMonthlySearches;
	private double cpc;
	private boolean commercialIntent;
	private int adsCount;
	private Strength keywordRating;
	private boolean processingCompleted;
	private transient ModelObserver observer;

	public ModelObject(){
		this.keywordRating = Strength.UNKNOWN;
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public long getRealNumberOfCompetitivePages() {
		return realNumberOfCompetitivePages;
	}

	public void setRealNumberOfCompetitivePages(long realNumberOfCompetitivePages) {
		this.realNumberOfCompetitivePages = realNumberOfCompetitivePages;
	}

	public Strength getCompetitionStrength() {
		return competitionStrength;
	}

	public void setCompetitionStrength(Strength competitionStrength) {
		this.competitionStrength = competitionStrength;
	}

	public double getAveragePageRank() {
		return averagePageRank;
	}

	public void setAveragePageRank(double averagePageRank) {
		this.averagePageRank = averagePageRank;
	}

	public Strength getPageRankStrentgh() {
		return pageRankStrentgh;
	}

	public void setPageRankStrentgh(Strength pageRankStrentgh) {
		this.pageRankStrentgh = pageRankStrentgh;
	}

	public boolean isCommercialIntent() {
		return commercialIntent;
	}

	public void setCommercialIntent(boolean commercialIntent) {
		this.commercialIntent = commercialIntent;
	}

	public int getAdsCount() {
		return adsCount;
	}

	public void setAdsCount(int adsCount) {
		this.adsCount = adsCount;
	}

	public Strength getKeywordRating() {
		return keywordRating;
	}

	public void setKeywordRating(Strength keywordRating) {
		this.keywordRating = keywordRating;
	}

	public double getAdwordCompetition() {
		return adwordCompetition;
	}

	public void setAdwordCompetition(double adwordCompetition) {
		this.adwordCompetition = adwordCompetition;
	}

	public int getLocalMonthlySearches() {
		return localMonthlySearches;
	}

	public void setLocalMonthlySearches(int localMonthlySearches) {
		this.localMonthlySearches = localMonthlySearches;
	}

	public int getGlobalMonthlySearches() {
		return globalMonthlySearches;
	}

	public void setGlobalMonthlySearches(int globalMonthlySearches) {
		this.globalMonthlySearches = globalMonthlySearches;
	}

	public double getCpc() {
		return cpc;
	}

	public void setCpc(double cpc) {
		this.cpc = cpc;
	}

	@Override
	public String toString() {
		return "ModelObject [keyword=" + keyword + ", realNumberOfCompetitivePages=" + realNumberOfCompetitivePages + ", competitionStrength=" + competitionStrength + ", averagePageRank="
				+ averagePageRank + ", pageRankStrentgh=" + pageRankStrentgh + ", adwordCompetition=" + adwordCompetition + ", localMonthlySearches=" + localMonthlySearches
				+ ", globalMonthlySearches=" + globalMonthlySearches + ", cpc=" + cpc + ", commercialIntent=" + commercialIntent + ", adsCount=" + adsCount + ", keywordRating=" + keywordRating + "]";
	}

	public String outputModel() {
		return keyword + "," + marshallNullValue(localMonthlySearches) + "," + marshallNullValue(cpc) + "," + marshallNullValue(totalNumberOfCompetitivePages) + ","
				+ marshallNullValue(realNumberOfCompetitivePages) + "," + marshallNullValue(competitivePageStrength) + "," + marshallNullValue(averagePageRank) + ","
				+ marshallNullValue(pageRankStrentgh) + "," + marshallNullValue(competitionStrength) + "," + marshallNullValue(commercialIntent) + "," + marshallNullValue(adsCount) + ","
				+ marshallNullValue(keywordRating);
	}

	private String marshallNullValue(Object element) {
		return element == null ? "" : String.valueOf(element);
	}

	public long getTotalNumberOfCompetitivePages() {
		return totalNumberOfCompetitivePages;
	}

	public void setTotalNumberOfCompetitivePages(long totalNumberOfCompetitivePages) {
		this.totalNumberOfCompetitivePages = totalNumberOfCompetitivePages;
	}

	public boolean isProcessingCompleted() {
		return processingCompleted;
	}

	public void setProcessingCompleted(boolean processingCompleted) {
		this.processingCompleted = processingCompleted;
		if (this.processingCompleted)
			observer.observe(this);

	}

	public Strength getCompetitivePageStrength() {
		return competitivePageStrength;
	}

	public void setCompetitivePageStrength(Strength competitivePageStrength) {
		this.competitivePageStrength = competitivePageStrength;
	}

	public void setObserver(ModelObserver observer) {
		this.observer = observer;
	}

	public void addObserver(ModelObserver observer) {
		this.observer = observer;
	}

	@Override
	public int compareTo(ModelObject o) {
		return this.keywordRating.compareTo(o.keywordRating);
	}

}
