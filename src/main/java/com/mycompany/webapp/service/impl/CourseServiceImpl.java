package com.mycompany.webapp.service.impl;
 
 import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.webapp.dao.CourseDAO;
import com.mycompany.webapp.domain.Attend;
import com.mycompany.webapp.domain.Category;
import com.mycompany.webapp.domain.Course;
import com.mycompany.webapp.domain.Detail;
import com.mycompany.webapp.domain.State;
import com.mycompany.webapp.domain.Subscribe;
import com.mycompany.webapp.domain.User;
import com.mycompany.webapp.domain.form.ApprovalForm;
import com.mycompany.webapp.enums.RolesEnum;
import com.mycompany.webapp.enums.StatesEnum;
import com.mycompany.webapp.service.CourseService;
 
@Service
public class CourseServiceImpl implements CourseService {
	
    @Autowired 
    private CourseDAO courseDAO;
    
	@Override
	@Transactional
	public Course findById(int id) {
		return courseDAO.findById(id);
	}    

	@Override
    @Transactional
    public void addCourse(Course course) {
        courseDAO.addCourse(course);
    }
	
	@Override
    @Transactional
	public void addCourse(Course course, Detail detail, State state, Category category) {
		course.setVoteKM(StatesEnum.PROPOSAL.ordinal());
		course.setVoteDM(StatesEnum.PROPOSAL.ordinal());
		course.setReasonKM("");
		course.setReasonDM("");
		course.setDetail(detail);
		detail.setCourse(course);
		detail.setqSubs(1);
		detail.setqAtts(1);
		course.setState(state);
		state.getCourse().add(course);
		course.setCategory(category);
		category.getCourse().add(course);
		addCourse(course);		
	}	
	
	@Override
    @Transactional
	public void updateCourse(Course course) {
			courseDAO.updateCourse(course);
	}
	
	@Override
	@Transactional
	public void updateCourse(Course course, User user, ApprovalForm approvalForm) {
		if (user.getRole().getRoleId()==RolesEnum.KNOWLEDGE_MANAGER.getRoleId()) {
			StatesEnum stateEnum = StatesEnum.valueOf(approvalForm.getDecision());
			course.setVoteKM(stateEnum.ordinal());
			course.setReasonKM(approvalForm.getReason());
		}
		if (user.getRole().getRoleId()==RolesEnum.DEPARTMENT_MANAGER.getRoleId()) {
			StatesEnum stateEnum = StatesEnum.valueOf(approvalForm.getDecision());
			course.setVoteDM(stateEnum.ordinal());
			course.setReasonDM(approvalForm.getReason());			
		}
		updateCourse(course);
	}
	
	@Override
	@Transactional
	public void updateCourse(Course course, State stateNew, State stateRejected) {
		if ((course.getVoteKM()==StatesEnum.NEW.ordinal())&&
				(course.getVoteDM()==StatesEnum.NEW.ordinal())) {
			course.setState(stateNew);
			updateCourse(course);
		} else if ((course.getVoteKM()==StatesEnum.REJECTED.ordinal()&&
				(course.getVoteDM()!=StatesEnum.PROPOSAL.ordinal()))||
				(course.getVoteDM()==StatesEnum.REJECTED.ordinal()&&
				course.getVoteKM()!=StatesEnum.PROPOSAL.ordinal())) {
			course.setState(stateRejected);
			updateCourse(course);				
		}
		
	}
	
	@Override
    @Transactional
	public void updateCourse(Course course, Detail detail, State stateReview, State stateProposal, Category category) {
		course.setVoteKM(StatesEnum.PROPOSAL.ordinal());
		course.setVoteDM(StatesEnum.PROPOSAL.ordinal());
		course.setReasonKM("");
		course.setReasonDM("");
		if (course.getState().getStateId()==StatesEnum.REJECTED.getStateId()) {
			course.setState(stateProposal);
		}		
		course.setDetail(detail);			
		detail.setCourse(course);
		if (stateReview!=null) {
			course.setState(stateReview);
		}
		course.setCategory(category);
		updateCourse(course);
		
	}
	
	@Override
    @Transactional
	public void updateCourse(Course course, Subscribe subscribe, User user) {
		subscribe.setCourse(course);
		subscribe.setUser(user);	
		course.getSubscribes().add(subscribe);
		updateCourse(course);
		
	}
	
	@Override
	@Transactional
	public void deleteCourse(Course course) {
		courseDAO.deleteCourse(course);
		
	}	
	
    @Override
	@Transactional
	public List<Course> getAllCourses() {
		return courseDAO.getAllCourses();
	}

	@Override
	@Transactional
	public List<String> getEmailsSubscribersForCourse(Course course) {
		List<Subscribe> subscribes=course.getSubscribes();
		List <String> emails=new ArrayList<String>();
		for (Subscribe subscribe:subscribes) {
			emails.add(subscribe.getUser().getEmail());
		}
		return emails;
	}

	@Override
	@Transactional
	public List<String> getEmailsAttendeesForCourse(Course course) {
		List<Attend> attends=course.getAttends();
		List <String> emails=new ArrayList<String>();
		for (Attend attend:attends) {
			emails.add(attend.getUser().getEmail());
		}
		return emails;
	}
	
	@Override
	@Transactional
	public List<String> getEmailsAttendeesNotEvaluatedForCourse(Course course) {
		List<Attend> attends=course.getAttends();
		List <String> emails=new ArrayList<String>();
		for (Attend attend:attends) {
			if (attend.getGrade()==0) {
				emails.add(attend.getUser().getEmail());
			}
		}
		return emails;
	}

	@Override
	@Transactional
	public double getMark(Course course) {
		List<Attend> attends=course.getAttends();
		int sumGrade=0;
		int quantityGraded=0;
		for (Attend attend:attends) {
			if (attend.getGrade()!=0) {
				sumGrade=sumGrade+attend.getGrade();
				quantityGraded++;
			}
		}
		double mark=0;
		if (quantityGraded!=0) {
			mark= (double) sumGrade/quantityGraded;
		}		
		return mark;
	}

	@Override
	@Transactional
	public int getGrade(Course course, String email) {
		List<Attend> attends=course.getAttends();
		int grade=0;
		for (Attend attend:attends) {
			if ((attend.getUser().getEmail()).compareTo(email)==0) {
				grade=attend.getGrade();
			}
		}
		return grade;
	}

	@Override
	@Transactional
	public List<Course> getMyCourses(String email) {
		List<Course> myCourses=new ArrayList<Course>();
		List<Course> allCourses=getAllCourses();
		for (Course course:allCourses) {
			List<String> emailsSubscribers=getEmailsSubscribersForCourse(course);
			for (String emailSubscriber:emailsSubscribers) {
				if (emailSubscriber.compareTo(email)==0)
					myCourses.add(course);
			}			
			List<String> emailsAttendees=getEmailsAttendeesForCourse(course);
			for (String emailAttendee:emailsAttendees) {
				if (emailAttendee.compareTo(email)==0)
					myCourses.add(course);
			}
			if (course.getDetail().getLecturer().compareTo(email)==0)
				myCourses.add(course);
		}
		return myCourses;
	}

	@Override
	@Transactional
	public List<Course> getCoursesByCategory(String category) {
		List<Course> coursesByCategory=new ArrayList<Course>();
		List<Course> allCourses=getAllCourses();		
		for (Course course:allCourses) {
			if (course.getCategory().getCategory().compareTo(category)==0)
				coursesByCategory.add(course);
		}
		return coursesByCategory;
	}

	@Override
	@Transactional
	public List<Course> getMyCoursesByCategory(String email, String category) {
		List<Course> myCoursesByCategory=new ArrayList<Course>();		
		List<Course> myCourses = getMyCourses(email);
		for (Course course:myCourses) {
			if (course.getCategory().getCategory().compareTo(category)==0)
				myCoursesByCategory.add(course);
		}
		return myCoursesByCategory;
	}

	@Override
	@Transactional
	public Map<String, String> getMapDecisionsAll() {
		Map<String, String> mapDecisionsAll = new LinkedHashMap<String, String>();
		mapDecisionsAll.put("","Select one");
		mapDecisionsAll.put("NEW","Approve");
		mapDecisionsAll.put("REJECTED","Reject");
		return mapDecisionsAll;
	}

	@Override
	@Transactional
	public Map<String, String> getMapDecisionsKM(Course course) {
		Map<String, String> mapDecisionsKM = new LinkedHashMap<String, String>();
		if (course.getVoteKM()==StatesEnum.NEW.ordinal()) {
			mapDecisionsKM.put("NEW","Approve");
		} else if (course.getVoteKM()==StatesEnum.REJECTED.ordinal()) {
			mapDecisionsKM.put("REJECTED","Reject");
		} 		
		return mapDecisionsKM;
	}

	@Override
	@Transactional
	public Map<String, String> getMapDecisionsDM(Course course) {
		Map<String, String> mapDecisionsDM = new LinkedHashMap<String, String>();
		if (course.getVoteDM()==StatesEnum.NEW.ordinal()) {
			mapDecisionsDM.put("NEW","Approve");
		} else if (course.getVoteDM()==StatesEnum.REJECTED.ordinal()) {
			mapDecisionsDM.put("REJECTED","Reject");
		} 		
		return mapDecisionsDM;
	}

}