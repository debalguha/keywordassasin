package org.google.api.util;

public enum Strength {
	VERY_LOW("Very Low", 5), LOW("Low", 4), MODERATE("Moderate", 3), HIGH("High", 2), VERY_HIGH(
			"Very High", 1), AMAZING("Amazing", 5), GREAT("GREAT", 4), GOOD("GOOD", 3), BAD(
			"BAD", 2), TERRIBLE("TERRIBLE", 1), UNKNOWN("UNKNOWN", -1);
	private String strength;
	private int weight;
	private Strength(String strength, int weight) {
		this.strength = strength;
		this.weight = weight;
	}

	@Override
	public String toString() {
		return strength;
	}
	
	public int getWeight(){
		return weight;
	}
}
