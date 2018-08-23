package common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import entity.Acceleration_info;
import entity.DaoValue;

public class CommonMethod {
	/**
	 * @author haianh
	 * 
	 */
	
	private static SessionFactory factory = null;
	
	public static SessionFactory getSessionFactory() {
		try {
			factory = new Configuration().configure().buildSessionFactory();
		}catch (Throwable ex) { 
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex); 
		}
		return factory;
	}
	
	public static int update(List<DaoValue> list) {
		int index = 1;
		factory = getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			for (DaoValue daoValue : list) {
				session.update(daoValue);
			}
			tx.commit();
		} catch(HibernateException he) {
			if(tx!=null) tx.rollback();
			he.printStackTrace();
			index = -1;
		} finally {
			session.close();
		}
		return index;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Acceleration_info> getListDaoValue(String startTime, String endTime){
		factory = getSessionFactory();
		List<Acceleration_info> list = new ArrayList<Acceleration_info>();
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query<Acceleration_info> query = session.createQuery("FROM Acceleration_info WHERE triggerTime BETWEEN '"+startTime+"' AND '" +endTime+"'");
			list = query.list();
	         for (Iterator<Acceleration_info> iterator = list.iterator(); iterator.hasNext();){
	        	 Acceleration_info accelaration_info = (Acceleration_info) iterator.next(); 
	        	 System.out.println(accelaration_info.getId());
	        	 System.out.println(accelaration_info.getTriggerTime());
	         }
			tx.commit();
		} catch(HibernateException he) {
			if(tx!=null) tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}
	
	public static int insertAcceleration(Acceleration_info acceleration_info){
		int index = 1;
		factory = getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(acceleration_info);
			tx.commit();
		} catch(HibernateException he) {
			if(tx!=null) tx.rollback();
			he.printStackTrace();
			index = -1;
		} finally {
			session.close();
		}
		return index;
	}
	
	public static String getCurrentTimeStamp(Date date) {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    String strDate = sdfDate.format(date);
	    return strDate;
	}
}
