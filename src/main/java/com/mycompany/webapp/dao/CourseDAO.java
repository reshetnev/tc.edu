package com.mycompany.webapp.dao;

import java.util.List;

import com.mycompany.webapp.domain.Course;

public interface CourseDAO {
	
	public Course findById(int id);

	public void addCourse(Course course);
	
	public void updateCourse(Course course);
	
	public void deleteCourse(Course course);
	
	public List<Course> getAllCourses();

}