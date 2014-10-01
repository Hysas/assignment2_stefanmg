package no.uio.inf5750.assignment2.service.impl;

import java.util.Collection;
import java.util.Set;

import no.uio.inf5750.assignment2.dao.*;
import no.uio.inf5750.assignment2.model.*;
import no.uio.inf5750.assignment2.service.StudentSystem;

public class DefaultStudentSystem implements StudentSystem{
	
	private CourseDAO courseDao;
	private DegreeDAO degreeDao;
	private StudentDAO studentDao;

	public void setCourseDao(CourseDAO courseDao) {
		this.courseDao = courseDao;
	}
	
	public void setStudentDao(StudentDAO studentDao) {
		this.studentDao = studentDao;
	}
	
	public void setDegreeDao(DegreeDAO degreeDao) {
		this.degreeDao = degreeDao;
	}
	
	@Override
	public int addCourse(String courseCode, String name) {
		return courseDao.saveCourse(new Course(courseCode,name));
	}

	@Override
	public void updateCourse(int courseId, String courseCode, String name) {
		courseDao.getCourse(courseId).setCourseCode(courseCode);
		courseDao.getCourse(courseId).setName(name);
	}

	@Override
	public Course getCourse(int courseId) {
		return courseDao.getCourse(courseId);
	}

	@Override
	public Course getCourseByCourseCode(String courseCode) {
		return courseDao.getCourseByCourseCode(courseCode);
	}

	@Override
	public Course getCourseByName(String name) {
		return courseDao.getCourseByName(name);
	}

	@Override
	public Collection<Course> getAllCourses() {
		return courseDao.getAllCourses();
	}

	@Override
	public void delCourse(int courseId) {
		courseDao.delCourse(courseDao.getCourse(courseId));
	}

	@Override
	public void addAttendantToCourse(int courseId, int studentId) {
		Set<Student> attendants = courseDao.getCourse(courseId).getAttendants();
		attendants.add(studentDao.getStudent(studentId));
		courseDao.getCourse(courseId).setAttendants(attendants);
	}

	@Override
	public void removeAttendantFromCourse(int courseId, int studentId) {
		Set<Student> attendants = courseDao.getCourse(courseId).getAttendants();
		attendants.remove(studentDao.getStudent(studentId));
		courseDao.getCourse(courseId).setAttendants(attendants);
	}

	@Override
	public int addDegree(String type) {
		return degreeDao.saveDegree(new Degree(type));
	}

	@Override
	public void updateDegree(int degreeId, String type) {
		degreeDao.getDegree(degreeId).setType(type);
	}

	@Override
	public Degree getDegree(int degreeId) {
		return degreeDao.getDegree(degreeId);
	}

	@Override
	public Degree getDegreeByType(String type) {
		return degreeDao.getDegreeByType(type);
	}

	@Override
	public Collection<Degree> getAllDegrees() {
		return degreeDao.getAllDegrees();
	}

	@Override
	public void delDegree(int degreeId) {
		degreeDao.delDegree(degreeDao.getDegree(degreeId));
	}

	@Override
	public void addRequiredCourseToDegree(int degreeId, int courseId) {
		Set<Course> courses =  degreeDao.getDegree(degreeId).getRequiredCourses();
		courses.add(courseDao.getCourse(courseId));
		degreeDao.getDegree(degreeId).setRequiredCourses(courses);
	}

	@Override
	public void removeRequiredCourseFromDegree(int degreeId, int courseId) {
		Set<Course> courses =  degreeDao.getDegree(degreeId).getRequiredCourses();
		courses.remove(courseDao.getCourse(courseId));
		degreeDao.getDegree(degreeId).setRequiredCourses(courses);
	}

	@Override
	public int addStudent(String name) {		
		return studentDao.saveStudent(new Student(name));
	}

	@Override
	public void updateStudent(int studentId, String name) {
		studentDao.getStudent(studentId).setName(name);
	}

	@Override
	public Student getStudent(int studentId) {
		return studentDao.getStudent(studentId);
	}

	@Override
	public Student getStudentByName(String name) {
		return studentDao.getStudentByName(name);
	}

	@Override
	public Collection<Student> getAllStudents() {
		return studentDao.getAllStudents();
	}

	@Override
	public void delStudent(int studentId) {
		studentDao.delStudent(studentDao.getStudent(studentId));
	}

	@Override
	public void addDegreeToStudent(int studentId, int degreeId) {
		Set<Degree> degrees = studentDao.getStudent(studentId).getDegrees();
		degrees.add(degreeDao.getDegree(degreeId));
		studentDao.getStudent(studentId).setDegrees(degrees);
	}

	@Override
	public void removeDegreeFromStudent(int studentId, int degreeId) {
		Set<Degree> degrees = studentDao.getStudent(studentId).getDegrees();
		degrees.remove(degreeDao.getDegree(degreeId));
		studentDao.getStudent(studentId).setDegrees(degrees);
	}

	@Override
	public boolean studentFulfillsDegreeRequirements(int studentId, int degreeId) {
		Set <Course> studCourses = studentDao.getStudent(studentId).getCourses();
		Set <Course> reqCourses = degreeDao.getDegree(degreeId).getRequiredCourses();		
		return studCourses.containsAll(reqCourses);
	}
	
}