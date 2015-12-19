package ch.makery.address.model;

import domain.RateDomainModel;
import base.RateDAL;
import org.apache.poi.ss.formula.functions.FinanceLib;


public class Rate extends RateDomainModel {
	
	public static double getPayment(int creditscore, int loanamount, int years)
	{
		double interestrate = RateDAL.getRate(creditscore);
		double monthlypayments = -1*FinanceLib.pmt(interestrate/1200, years*12, loanamount, 0, false);
		
		
		return monthlypayments;
	}
}