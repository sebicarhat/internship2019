package domain;

public class InterestRate {

	double min;
	double max;
	double interestPercent;
	/*
	 * Constructor with parameters
	 */
	public InterestRate(double min, double max, double d){
		this.min=min;
		this.max=max;
		this.interestPercent=d;
		
	}
	
	public double getMin() {return this.min;}
	public double getMax() {return this.max;}
	public double getInterestPercent() {return this.interestPercent;}
	
}
