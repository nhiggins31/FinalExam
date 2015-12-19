package base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import domain.RateDomainModel;
import util.HibernateUtil;

public class RateDAL {


	public static ArrayList<RateDomainModel> getRates() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;	
		ArrayList<RateDomainModel> rates = new ArrayList<RateDomainModel>();
		
		try {
			tx = session.beginTransaction();	
			
			List rs = session.createQuery("FROM RateDomainModel").list();
			for (Iterator iterator = rs.iterator(); iterator.hasNext();) {
				RateDomainModel r = (RateDomainModel)iterator.next();
				rates.add(r);
			}
			
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return rates;
	}
	
	
	public static double getRate(int GivenCreditScore) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		double rate = 0;
		try {
			tx = session.beginTransaction();
			int credScore = GivenCreditScore;
			if (credScore >= 800) {
				credScore = 600;
			}
			else if (credScore >= 750) {
				credScore = 750;
			}
			else if (credScore >= 700) {
				credScore = 700;
			}
			else if (credScore >= 650) {
				credScore = 650;
			}
			else if (credScore >= 600) {
				credScore = 600;
			}
			else {
				System.out.println("Interest rate for credit score: " +GivenCreditScore+ " does not exist in database. Rate is set to 5% by default.");
				credScore = 600;
			}
			String sql = new String("Select InterestRate from RateDomainModel WHERE MINCREDITSCORE = " +credScore);
			Query rateQuery = session.createQuery(sql);
			List rates = rateQuery.list();
			rate = (Double)rates.get(0);
			tx.commit();
			
	}
		catch (HibernateException e) {
	if (tx != null)
	tx.rollback();
	e.printStackTrace();
	} finally {
	session.close();
	}
	return rate;

	}

	}