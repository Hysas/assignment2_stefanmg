/*package no.uio.inf5750.assignment2.dao;

import no.uio.inf5750.assignment2.dao.hibernate.HibernateCourseDao;
import no.uio.inf5750.assignment2.model.Course;
import static junit.framework.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

public class CourseDAOTest {
		
	private CourseDAO courseDAO;
	
	Course course;	
	
	@Before
	public void init() {
		course = new Course("INF1000","Programmering");		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testSave() {
		int id = courseDAO.saveCourse(course);
		course = courseDAO.getCourse(id);
		assertEquals(id,course.getId());
	}
}*/