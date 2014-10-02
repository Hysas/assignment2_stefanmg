package no.uio.inf5750.assignment2.service.impl;

import java.util.Collection;
import java.util.Set;

//import org.hibernate.Session;
//import org.hibernate.SessionFactory;


import org.hibernate.Hibernate;

import no.uio.inf5750.assignment2.dao.*;
import no.uio.inf5750.assignment2.model.*;
import no.uio.inf5750.assignment2.service.StudentSystem;

public class DefaultStudentSystem implements StudentSystem{
	
	private CourseDAO courseDao;
	private DegreeDAO degreeDao;
	private StudentDAO studentDao;	

	public void setCourseDao(CourseDAO courseDao) {
		Hibernate.initialize(courseDao);
		this.courseDao = courseDao;
	}
	
	public void setStudentDao(StudentDAO studentDao) {
		Hibernate.initialize(studentDao);
		this.studentDao = studentDao;
	}
	
	public void setDegreeDao(DegreeDAO degreeDao) {
		Hibernate.initialize(degreeDao);
		this.degreeDao = degreeDao;
	}
	
	@Override
	public int addCourse(String courseCode, String name) {
		Course course = new Course(courseCode,name);
		return courseDao.saveCourse(course);
	}

	@Override
	public void updateCourse(int courseId, String courseCode, String name) {
		Course course = getCourse(courseId);
		course.setCourseCode(courseCode);
		course.setName(name);
	}

	@Override
	public Course getCourse(int courseId) {
		Course course = courseDao.getCourse(courseId);
		return course;
	}

	@Override
	public Course getCourseByCourseCode(String courseCode) {
		Course course = courseDao.getCourseByCourseCode(courseCode);
		return course;
	}

	@Override
	public Course getCourseByName(String name) {
		Course course = courseDao.getCourseByName(name);
		return course;
	}

	@Override
	public Collection<Course> getAllCourses() {		
		return courseDao.getAllCourses();
	}

	@Override
	public void delCourse(int courseId) {
		Course course = getCourse(courseId);
		
		Collection<Student> students = getAllStudents();
		Collection<Degree> degrees = getAllDegrees();
		
		for(Student student : students) {
			removeAttendantFromCourse(courseId,student.getId());
		}
		
		for(Degree degree : degrees) {
			removeRequiredCourseFromDegree(degree.getId(), courseId);
		}
		
		courseDao.delCourse(course);
	}

	@Override
	public void addAttendantToCourse(int courseId, int studentId) {		
		Student student = getStudent(studentId);
		Course course = getCourse(courseId);
		student.getCourses().add(course);
		course.getAttendants().add(student);			
	}

	@Override
	public void removeAttendantFromCourse(int courseId, int studentId) {
		Student student = getStudent(studentId);
		Course course = getCourse(courseId);
		student.getCourses().remove(course);
		course.getAttendants().remove(student);
	}

	@Override
	public int addDegree(String type) {
		Degree degree = new Degree(type);
		return degreeDao.saveDegree(degree);
	}

	@Override
	public void updateDegree(int degreeId, String type) {
		Degree degree = degreeDao.getDegree(degreeId);
		degree.setType(type);		
	}

	@Override
	public Degree getDegree(int degreeId) {
		Degree degree = degreeDao.getDegree(degreeId);
		return degree;
	}

	@Override
	public Degree getDegreeByType(String type) {
		Degree degree = degreeDao.getDegreeByType(type);
		return degree;
	}

	@Override
	public Collection<Degree> getAllDegrees() {
		return degreeDao.getAllDegrees();
	}

	@Override
	public void delDegree(int degreeId) {
		Degree degree = getDegree(degreeId);
		Collection<Student> students = getAllStudents();
		
		for(Student student : students) {
			removeDegreeFromStudent(student.getId(),degreeId);
		}
		
		degreeDao.delDegree(degree);
	}

	@Override
	public void addRequiredCourseToDegree(int degreeId, int courseId) {
		Degree degree = getDegree(degreeId);
		Course course = getCourse(courseId);
		degree.getRequiredCourses().add(course);
	}

	@Override
	public void removeRequiredCourseFromDegree(int degreeId, int courseId) {
		Degree degree = getDegree(degreeId);
		Course course = getCourse(courseId);
		degree.getRequiredCourses().remove(course);
	}

	@Override
	public int addStudent(String name) {
		Student student = new Student(name);
		return studentDao.saveStudent(student);
	}

	@Override
	public void updateStudent(int studentId, String name) {
		Student student = getStudent(studentId);
		student.setName(name);
	}

	@Override
	public Student getStudent(int studentId) {
		Student student = studentDao.getStudent(studentId);
		return student;
	}

	@Override
	public Student getStudentByName(String name) {
		Student student = studentDao.getStudentByName(name);
		return student;
	}

	@Override
	public Collection<Student> getAllStudents() {
		return studentDao.getAllStudents();
	}

	@Override
	public void delStudent(int studentId) {	
		Student student = getStudent(studentId);
		Collection<Course> courses = getAllCourses();
				
		for(Course course: courses) {
			removeAttendantFromCourse(course.getId(),studentId);
		}
		
		studentDao.delStudent(student);		
	}

	@Override
	public void addDegreeToStudent(int studentId, int degreeId) {
		Student student = getStudent(studentId);
		Degree degree = getDegree(degreeId);
		student.getDegrees().add(degree);		
	}

	@Override
	public void removeDegreeFromStudent(int studentId, int degreeId) {
		Student student = getStudent(studentId);
		Degree degree = getDegree(degreeId);
		student.getDegrees().remove(degree);	
	}

	@Override
	public boolean studentFulfillsDegreeRequirements(int studentId, int degreeId) {
		Student student = getStudent(studentId);
		Degree degree = getDegree(degreeId);
		Set <Course> studCourses = student.getCourses();
		Set <Course> reqCourses = degree.getRequiredCourses();		
		return studCourses.containsAll(reqCourses);
	}
	
}