package no.uio.inf5750.assignment2.service.impl;

import java.util.Collection;
import java.util.Set;

import no.uio.inf5750.assignment2.dao.*;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Degree;
import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.service.StudentSystem;

import org.springframework.transaction.annotation.Transactional;


@Transactional
public class DefaultStudentSystem implements StudentSystem{
	
	CourseDAO course;
	DegreeDAO degree;
	StudentDAO student;

	
	@Override
	public int addCourse(String courseCode, String name) {
		return course.saveCourse(new Course(courseCode,name));
	}

	@Override
	public void updateCourse(int courseId, String courseCode, String name) {
		course.getCourse(courseId).setCourseCode(courseCode);
		course.getCourse(courseId).setName(name);
	}

	@Override
	public Course getCourse(int courseId) {
		return course.getCourse(courseId);
	}

	@Override
	public Course getCourseByCourseCode(String courseCode) {
		return course.getCourseByCourseCode(courseCode);
	}

	@Override
	public Course getCourseByName(String name) {
		return course.getCourseByName(name);
	}

	@Override
	public Collection<Course> getAllCourses() {
		return course.getAllCourses();
	}

	@Override
	public void delCourse(int courseId) {
		course.delCourse(course.getCourse(courseId));
	}

	@Override
	public void addAttendantToCourse(int courseId, int studentId) {
		Set<Student> attendants = course.getCourse(courseId).getAttendants();
		attendants.add(student.getStudent(studentId));
		course.getCourse(courseId).setAttendants(attendants);
	}

	@Override
	public void removeAttendantFromCourse(int courseId, int studentId) {
		Set<Student> attendants = course.getCourse(courseId).getAttendants();
		attendants.remove(student.getStudent(studentId));
		course.getCourse(courseId).setAttendants(attendants);
	}

	@Override
	public int addDegree(String type) {
		return degree.saveDegree(new Degree(type));
	}

	@Override
	public void updateDegree(int degreeId, String type) {
		degree.getDegree(degreeId).setType(type);
	}

	@Override
	public Degree getDegree(int degreeId) {
		return degree.getDegree(degreeId);
	}

	@Override
	public Degree getDegreeByType(String type) {
		return degree.getDegreeByType(type);
	}

	@Override
	public Collection<Degree> getAllDegrees() {
		return degree.getAllDegrees();
	}

	@Override
	public void delDegree(int degreeId) {
		degree.delDegree(degree.getDegree(degreeId));
	}

	@Override
	public void addRequiredCourseToDegree(int degreeId, int courseId) {
		Set<Course> courses =  degree.getDegree(degreeId).getRequiredCourses();
		courses.add(course.getCourse(courseId));
		degree.getDegree(degreeId).setRequiredCourses(courses);
	}

	@Override
	public void removeRequiredCourseFromDegree(int degreeId, int courseId) {
		Set<Course> courses =  degree.getDegree(degreeId).getRequiredCourses();
		courses.remove(course.getCourse(courseId));
		degree.getDegree(degreeId).setRequiredCourses(courses);
	}

	@Override
	public int addStudent(String name) {
		return student.saveStudent(new Student(name));
	}

	@Override
	public void updateStudent(int studentId, String name) {
		student.getStudent(studentId).setName(name);
	}

	@Override
	public Student getStudent(int studentId) {
		return student.getStudent(studentId);
	}

	@Override
	public Student getStudentByName(String name) {
		return student.getStudentByName(name);
	}

	@Override
	public Collection<Student> getAllStudents() {
		return student.getAllStudents();
	}

	@Override
	public void delStudent(int studentId) {
		student.delStudent(student.getStudent(studentId));
	}

	@Override
	public void addDegreeToStudent(int studentId, int degreeId) {
		Set<Degree> degrees = student.getStudent(studentId).getDegrees();
		degrees.add(degree.getDegree(degreeId));
		student.getStudent(studentId).setDegrees(degrees);
	}

	@Override
	public void removeDegreeFromStudent(int studentId, int degreeId) {
		Set<Degree> degrees = student.getStudent(studentId).getDegrees();
		degrees.remove(degree.getDegree(degreeId));
		student.getStudent(studentId).setDegrees(degrees);
	}

	@Override
	public boolean studentFulfillsDegreeRequirements(int studentId, int degreeId) {
		Set <Course> studCourses = student.getStudent(studentId).getCourses();
		Set <Course> reqCourses = degree.getDegree(degreeId).getRequiredCourses();		
		return studCourses.containsAll(reqCourses);
	}
	
}