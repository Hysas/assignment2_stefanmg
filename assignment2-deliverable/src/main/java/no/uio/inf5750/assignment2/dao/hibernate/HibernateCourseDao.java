package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import no.uio.inf5750.assignment2.dao.CourseDAO;
import no.uio.inf5750.assignment2.model.Course;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class HibernateCourseDao implements CourseDAO {

	static Logger logger = Logger.getLogger(HibernateCourseDao.class);
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public int saveCourse(Course course) {
		return (Integer) sessionFactory.getCurrentSession().save(course);
	}

	@Override
	public Course getCourse(int id) {
		return (Course) sessionFactory.getCurrentSession().get(Course.class, id);
	}

	@Override
	public Course getCourseByCourseCode(String courseCode) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Course course = null;
		
		try {
			tx = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Course> courses = session.createQuery("FROM Course ORDER by id DESC").list();
			
			if (!courses.isEmpty()) {
				while (courses.iterator().hasNext()) {
					course = courses.iterator().next();
					if (course.getCourseCode().equals(courseCode)) break;
				}
			}
			tx.commit();
		} catch (HibernateException e){
			if (tx != null) tx.rollback();
			logger.error("DB query failed", e);
		} finally {
			session.close();
		}		
		return course;
	}

	@Override
	public Course getCourseByName(String name) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Course course = null;
		
		try {
			tx = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Course> courses = session.createQuery("FROM Course ORDER by id DESC").list();
			
			if (!courses.isEmpty()) {
				while (courses.iterator().hasNext()) {
					course = courses.iterator().next();
					if (course.getName().equals(name)) break;
				}
			}
			tx.commit();
		} catch (HibernateException e){
			if (tx != null) tx.rollback();
			logger.error("DB query failed", e);
		} finally {
			session.close();
		}		
		return course;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Course> getAllCourses() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Course> courses = null;
		
		try {
			tx = session.beginTransaction();			
			courses = session.createQuery("FROM course ORDER by id DESC").list();					
			tx.commit();
		} catch (HibernateException e){
			if (tx != null) tx.rollback();
			logger.error("DB query failed", e);
		} finally {
			session.close();
		}	
		
		return courses;
	}

	@Override
	public void delCourse(Course course) {
		sessionFactory.getCurrentSession().delete(course);
	}
}