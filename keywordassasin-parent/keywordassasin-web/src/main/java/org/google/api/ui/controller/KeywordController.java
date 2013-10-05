package org.google.api.ui.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

@ManagedBean(name="keywordController")
@SessionScoped
public class KeywordController extends UIBaseControllerBean{
	private String seedWord;
	private float cpcUpperLimit;
	private String miningType;
	private String location;
	public String getSeedWord() {
		return seedWord;
	}
	public void setSeedWord(String seedWord) {
		this.seedWord = seedWord;
	}
	public float getCpcUpperLimit() {
		return cpcUpperLimit;
	}
	public void setCpcUpperLimit(float cpcUpperLimit) {
		this.cpcUpperLimit = cpcUpperLimit;
	}
	public String getMiningType() {
		return miningType;
	}
	public void setMiningType(String miningType) {
		this.miningType = miningType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String startDigging(ActionEvent event){
		System.out.println("Digging started!!");
		return null;
	}
}
