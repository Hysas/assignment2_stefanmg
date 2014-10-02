package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import no.uio.inf5750.assignment2.dao.StudentDAO;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Student;

public class HibernateStudentDao implements StudentDAO{

	static Logger logger = Logger.getLogger(HibernateStudentDao.class);
	private SessionFactory sessionFactory;

	
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		Hibernate.initialize(sessionFactory);    
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public int saveStudent(Student student) {		
		return (Integer) sessionFactory.getCurrentSession().save(student);
	}

	@Override
	public Student getStudent(int id) {
		return (Student) sessionFactory.getCurrentSession().get(Student.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Student getStudentByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Course.class);
		List<Student> students = criteria.list();
		
		for(Student student : students){
			if (student.getName().equals(name)) return student;
		}		
		
		return null;		
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Student> getAllStudents() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Student.class);
		List<Student> students = criteria.list();
		
		return students;		
	}

	@Override
	public void delStudent(Student student) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(student);
	}	
}