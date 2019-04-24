package domain;
import java.time.LocalDate;
import java.util.*;


public class Account {
	String name;
	List<InterestRate> interestRateList;
	LocalDate expirationDate;
	double amount;
	/*
	 * Constructor with parameters
	 */
	Account(String name, List<InterestRate> interestRateList, LocalDate expirationDate, double amount){
		this.name=name;
		this.interestRateList = new ArrayList<>(interestRateList);
		this.expirationDate=expirationDate;
		this.amount=amount;
	}
	
	public String getName() {return this.name;}
	
	public List<InterestRate> getInterestList(){return this.interestRateList;}
	
	public double getAmount() {return this.amount;}
	
	public void setAmount(double amount) {this.amount=amount;}
	
	public LocalDate getExpirationDate() {return this.expirationDate;}
	
	

}

