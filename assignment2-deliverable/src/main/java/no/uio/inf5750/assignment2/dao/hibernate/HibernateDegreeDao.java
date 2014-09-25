package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import no.uio.inf5750.assignment2.dao.DegreeDAO;
import no.uio.inf5750.assignment2.model.Degree;

public class HibernateDegreeDao implements DegreeDAO{

	static Logger logger = Logger.getLogger(HibernateDegreeDao.class);
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public int saveDegree(Degree degree) {
		return (Integer) sessionFactory.getCurrentSession().save(degree);
	}

	@Override
	public Degree getDegree(int id) {
		return (Degree) sessionFactory.getCurrentSession().get(Degree.class, id);
	}

	@Override
	public Degree getDegreeByType(String type) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Degree degree = null;
		
		try {
			tx = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Degree> degrees = session.createQuery("FROM Course ORDER by id DESC").list();
			
			if (!degrees.isEmpty()) {
				while (degrees.iterator().hasNext()) {
					degree = degrees.iterator().next();
					if (degree.getType().equals(type)) break;
				}
			}
			tx.commit();
		} catch (HibernateException e){
			if (tx != null) tx.rollback();
			logger.error("DB query failed", e);
		} finally {
			session.close();
		}		
		return degree;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Degree> getAllDegrees() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Degree> degrees = null;
		
		try {
			tx = session.beginTransaction();			
			degrees = session.createQuery("FROM degree ORDER by id DESC").list();					
			tx.commit();
		} catch (HibernateException e){
			if (tx != null) tx.rollback();
			logger.error("DB query failed", e);
		} finally {
			session.close();
		}	
		
		return degrees;
	}

	@Override
	public void delDegree(Degree degree) {
		sessionFactory.getCurrentSession().delete(degree);
	}
	

}