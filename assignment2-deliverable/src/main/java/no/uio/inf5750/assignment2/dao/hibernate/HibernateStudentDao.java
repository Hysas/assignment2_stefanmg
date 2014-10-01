package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import no.uio.inf5750.assignment2.dao.StudentDAO;
import no.uio.inf5750.assignment2.model.Student;

public class HibernateStudentDao implements StudentDAO{

	static Logger logger = Logger.getLogger(HibernateStudentDao.class);
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
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

	@Override
	public Student getStudentByName(String name) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Student student = null;
		
		try {
			tx = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Student> students = session.createQuery("FROM student ORDER by id DESC").list();
			
			if (!students.isEmpty()) {
				while (students.iterator().hasNext()) {
					student = students.iterator().next();
					if (student.getName().equals(name)) break;
				}
			}
			tx.commit();
		} catch (HibernateException e){
			if (tx != null) tx.rollback();
			logger.error("DB query failed", e);
		} finally {
			session.close();
		}		
		return student;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Student> getAllStudents() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Student> students = null;
		
		try {
			tx = session.beginTransaction();			
			students = session.createQuery("FROM student ORDER by id DESC").list();					
			tx.commit();
		} catch (HibernateException e){
			if (tx != null) tx.rollback();
			logger.error("DB query failed", e);
		} finally {
			session.close();
		}	
		
		return students;
	}

	@Override
	public void delStudent(Student student) {
		sessionFactory.getCurrentSession().delete(student);
	}
	
}