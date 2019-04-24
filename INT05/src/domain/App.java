package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.time.temporal.ChronoUnit;

import domain.Account;

public class App{
	
	/*
	 * Redistribute money from accounts 
	 */
	public List<Account> redistributeMoney(List<Account> accounts) {

	double sum=0;
	//get sum from all accounts
	for (Account a : accounts)
	{
		sum+=a.getAmount();
		a.setAmount(0);
	}
	
	//get intervals from accounts
	double[] interval = new double[10];
	int k=-1;
	List<InterestRate> irList = accounts.get(0).getInterestList();//we suppose that all accounts are the same intervals as in the example
	for(InterestRate ir : irList )
	{
		interval[++k]=ir.getMin();
		interval[++k]=ir.getMax();
	}
	
	//redistribute by percent rate
	if(sum<=interval[0]) 
	{
		for(Account a : accounts)
			if(a.getName().contentEquals("PLATINUM"))
				a.setAmount(sum);
	}
	else
		if(sum>interval[1] && sum <= 2*interval[1])
		{
			for(Account a : accounts)
				if(a.getName().contentEquals("PLATINUM"))
					a.setAmount(interval[0]);
				else if (a.getName().contentEquals("GOLD"))
					a.setAmount(sum-interval[0]);
		}
		else
			if(sum>2*interval[1] && sum <=4*interval[1])
			{
				for(Account a : accounts)
					if(a.getName().contentEquals("PLATINUM"))
						a.setAmount(interval[1]);
					else if (a.getName().contentEquals("GOLD"))
						a.setAmount(sum-interval[1]);
			}
			else
				if(sum>2*interval[1]+1000 && sum <= interval[1]+interval[3])
				{
					for(Account a : accounts)
						if(a.getName().contentEquals("GOLD"))
							a.setAmount(interval[1]);
						else if (a.getName().contentEquals("PLATINUM"))
							a.setAmount(sum-interval[3]);
				}
				else
					if(sum>interval[1]+interval[1] && sum <=2*interval[3])
					{
						for(Account a : accounts)
							if(a.getName().contentEquals("PLATINUM"))
								a.setAmount(interval[3]);
							else if (a.getName().contentEquals("GOLD"))
								a.setAmount(sum-interval[3]);
					}
					else
						if(sum>2*interval[3])
						{
							for(Account a : accounts)
								if(a.getName().contentEquals("PLATINUM"))
									a.setAmount(interval[3]);
								else if (a.getName().contentEquals("GOLD"))
									a.setAmount(interval[3]);
								else if(a.getName().contentEquals("SILVER"))
									a.setAmount(sum-2*interval[3]);
						}
	
	return accounts;
		
	}
	
	/*
	 * Compute the sum from all account after 39 months considering expiration date from each account
	 */
	int compute(List<Account> accounts, LocalDate startDate, int opt)
	{
		int sum=0;
		int sumConsiderExpDate=0;
		//get nr of days is curent year
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int numOfDays = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
		
		for(Account a : accounts)
		{
			long daysTillExpire = ChronoUnit.DAYS.between(startDate, a.getExpirationDate());

			for(InterestRate ir : a.getInterestList())

				if(a.getAmount()>=ir.getMin() && a.getAmount()<=ir.getMax())
				{
					sumConsiderExpDate+=a.getAmount()+(((a.getAmount()*ir.getInterestPercent()) / numOfDays )*daysTillExpire);
					sum+=a.getAmount()+(a.getAmount()*ir.getInterestPercent()*3.25); //39 months
				}
			
		}
		if (opt==1)
		return sum;
		else 
		return sumConsiderExpDate;
	}
	
	
	public static void main(String[] args) {
		App app = new App();
		List<Account> accounts = new ArrayList<>();
		List<InterestRate> accountInterestRate= new ArrayList<InterestRate>();
		LocalDate date; 

		/*
		 * Adding accounts
		 */
		accountInterestRate.add(new InterestRate(0.0,500,0.003));
		accountInterestRate.add(new InterestRate(500.000000000001,5000.0,0.002));
		date = LocalDate.of(2020, 5, 23);
		accounts.add(new Account("SILVER",accountInterestRate,date,5000));
		accountInterestRate.clear();
		
		accountInterestRate.add(new InterestRate(0.0,500,0.006));
		accountInterestRate.add(new InterestRate(500.000000000001,5000.0,0.004));
		date = LocalDate.of(2020, 7, 5);
		accounts.add(new Account("GOLD",accountInterestRate,date,700));
		accountInterestRate.clear();
		
		accountInterestRate.add(new InterestRate(0.0,500,0.009));
		accountInterestRate.add(new InterestRate(500.000000000001,5000.0,0.005));
		date = LocalDate.of(2020, 3, 15);
		accounts.add(new Account("PLATINUM",accountInterestRate,date,300));
		
		app.redistributeMoney(accounts);
		System.out.println("After redistribute money we have following amount in each account:");
		for (Account a : accounts)
			System.out.println(a.getName()+": "+a.getAmount());
		
		LocalDate startDate = LocalDate.of(2019,03,19);
		System.out.println("Sum of all accounts after 39 months without considering expirationDate: "+app.compute(accounts,startDate,1));
		System.out.println("Sum of all accounts after 39 months considering expirationDate: "+app.compute(accounts,startDate,2));
		
		
	}
	
	
	
}
	
