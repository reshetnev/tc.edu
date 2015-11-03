package com.mycompany.webapp.service;

import java.util.List;
import java.util.Map;

import com.mycompany.webapp.domain.Category;
import com.mycompany.webapp.domain.Course;
import com.mycompany.webapp.domain.Detail;
import com.mycompany.webapp.domain.State;
import com.mycompany.webapp.domain.Subscribe;
import com.mycompany.webapp.domain.User;
import com.mycompany.webapp.domain.form.ApprovalForm;

public interface CourseService {
	
	public Course findById(int id);

	public void addCourse(Course course);
	
	public void addCourse(Course course, Detail detail, State state, Category category);
	
	public void updateCourse(Course course);
	
	public void updateCourse(Course course, User user, ApprovalForm approvalForm);
	
	public void updateCourse(Course course, State stateNew, State stateRejected);	
	
	public void updateCourse(Course course, Detail detail, State stateReview, State stateProposal, Category category);
	
	public void updateCourse(Course course, Subscribe subscribe, User user);
	
	public void deleteCourse(Course course);	
	
	public List<Course> getAllCourses();
	
	public List<String> getEmailsSubscribersForCourse(Course course);
	
	public List<String> getEmailsAttendeesForCourse(Course course);
	
	public List<String> getEmailsAttendeesNotEvaluatedForCourse(Course course);
	
	public int getGrade(Course course, String email);
	
	public double getMark(Course course);
	
	public List<Course> getMyCourses(String email);
	
	public List<Course> getCoursesByCategory(String category);
	
	public List<Course> getMyCoursesByCategory(String email, String category);
	
	public Map<String, String> getMapDecisionsAll();
	
	public Map<String, String> getMapDecisionsKM(Course course);
	
	public Map<String, String> getMapDecisionsDM(Course course);
	
	

}