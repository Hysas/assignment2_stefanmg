package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;
import java.util.List;

import no.uio.inf5750.assignment2.dao.CourseDAO;
import no.uio.inf5750.assignment2.model.Course;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.Criteria;
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
	@SuppressWarnings("unchecked")
	public Course getCourseByCourseCode(String courseCode) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Course.class);
		List<Course> courses = criteria.list();
				
		for(Course course : courses){
			if (course.getCourseCode().equals(courseCode)) return course;
		}		
		
		return null;			
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public Course getCourseByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Course.class);
		List<Course> courses = criteria.list();
		
		for(Course course : courses){
			if (course.getName().equals(name)) return course;
		}		
		
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Course> getAllCourses() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Course.class);
		Collection<Course> events = criteria.list();
		
		return events;
	}

	@Override
	public void delCourse(Course course) {
		sessionFactory.getCurrentSession().delete(course);
	}
}