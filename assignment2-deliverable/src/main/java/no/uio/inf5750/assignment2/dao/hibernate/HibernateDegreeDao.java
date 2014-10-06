package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import no.uio.inf5750.assignment2.dao.DegreeDAO;
import no.uio.inf5750.assignment2.model.Course;
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

	@SuppressWarnings("unchecked")
	@Override
	public Degree getDegreeByType(String type) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Course.class);			
		List<Degree> degrees = criteria.list();
		
		for(Degree degree : degrees){
			if (degree.getType().equals(type)) return degree;
		}		
		
		return null;	
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Degree> getAllDegrees() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Degree.class);
		List<Degree> events = criteria.list();
		
		return events;	
	}

	@Override
	public void delDegree(Degree degree) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(degree);
	}
}