package com.mycompany.webapp.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.mycompany.webapp.domain.Course;
import com.mycompany.webapp.domain.Detail;
import com.mycompany.webapp.domain.User;
import com.mycompany.webapp.domain.form.ApprovalForm;
import com.mycompany.webapp.domain.form.UpdateForm;
import com.mycompany.webapp.enums.StatesEnum;
import com.mycompany.webapp.service.MailService;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Service
public class MailServiceImpl implements MailService {
	
    // Init. Logger 
    private static final Logger log = Logger.getLogger(MailServiceImpl.class);
	
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private Configuration freemarkerMailConfiguration;
    
	@Override
	public void sendMail(List<String> emailsTo, List<String> emailsCc,
			MimeMessage message) {
		
		InternetAddress[] to = new InternetAddress[emailsTo.size()];
		for (int i=0; i<to.length; i++) {
			try {
				to[i]=new InternetAddress(emailsTo.get(i));
			} catch (AddressException e) {
				log.error("InternetAddress is invalid", e);
			}
		}
        try {
			message.setRecipients(Message.RecipientType.TO, to);
		} catch (MessagingException e) {
			log.error("Message is invalid", e);
		}
		InternetAddress[] cc = new InternetAddress[emailsCc.size()];
		for (int i=0; i<cc.length; i++) {
			try {
				cc[i]=new InternetAddress(emailsCc.get(i));
			} catch (AddressException e) {
				log.error("InternetAddress is invalid", e);
			}
		}
        try {
			message.setRecipients(Message.RecipientType.CC, cc);
		} catch (MessagingException e) {
			log.error("Message is invalid", e);
		}
        mailSender.send(message);
	}

	@Override
	public void sendCourseAnnouncementMail(List<String> emailsKM, List<String> emailsDM,
			String emailOwner, String owner, UpdateForm updateForm, String courseId)
					throws IOException, TemplateException, MessagingException {
		
		List<String> emailsTo=new ArrayList<String>();
		emailsTo.addAll(emailsKM);
		emailsTo.addAll(emailsDM);
		List<String> emailsCc=new ArrayList<String>();
		emailsCc.add(emailOwner);
		MimeMessage message = mailSender.createMimeMessage();
		message.setSubject("Course Announcement");		
		Map<String, String> templateVars = new HashMap<String, String>();
		templateVars.put("LectorName", owner);
		templateVars.put("CourseName", updateForm.getName());
		templateVars.put("CourseCategory", updateForm.getCategory());
		templateVars.put("CourseDescription", updateForm.getDescription());
		templateVars.put("CourseLinks", updateForm.getLinks());
		templateVars.put("CourseApproveLink", "http://localhost:8080/tc.edu/courses/"+courseId+"/approve");
		StringBuffer content = new StringBuffer();
	    content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
	    		freemarkerMailConfiguration.getTemplate("course_announcement.ftl"), templateVars));
		message.setContent(content.toString(), "text/html");
		sendMail(emailsTo, emailsCc, message);
	}

	@Override
	public void sendCourseApprovalUpdateMail(User owner, List<String> emailsKM, List<String> emailsDM,
			String login, Course course, ApprovalForm approvalForm)
					throws IOException, TemplateException, MessagingException {
		
		List<String> emailsTo=new ArrayList<String>();
		emailsTo.add(owner.getEmail());
		List<String> emailsCc=new ArrayList<String>();
		emailsCc.addAll(emailsKM);
		emailsCc.addAll(emailsDM);		
		MimeMessage message = mailSender.createMimeMessage();
		message.setSubject("Course Approval Update");		
		Map<String, String> templateVars = new HashMap<String, String>();
		String managerDecision;
		if (approvalForm.getDecision().compareTo(StatesEnum.REJECTED.name())==0) {
			managerDecision="Reject";
		} else {
			managerDecision="Approve";
		}
		templateVars.put("ManagerName", login);
		templateVars.put("CourseName", course.getName());
		templateVars.put("CourseLecturer", owner.getLogin());
		templateVars.put("ManagerDecision", managerDecision);
		templateVars.put("ManagerReason", approvalForm.getReason());
		StringBuffer content = new StringBuffer();
	    content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
	    		freemarkerMailConfiguration.getTemplate("course_approval_update.ftl"), templateVars));
		message.setContent(content.toString(), "text/html");
		sendMail(emailsTo, emailsCc, message);
		
	}

	@Override
	public void sendNewCourseAddedMail(List<String> emailsU, User owner, List<String> emailsKM,
			List<String> emailsDM, Course course, Detail detail, String courseId)
					throws IOException, TemplateException, MessagingException {
		
		List<String> emailsTo=new ArrayList<String>();
		emailsTo.addAll(emailsU);
		List<String> emailsCc=new ArrayList<String>();
		emailsCc.add(owner.getEmail());
		emailsCc.addAll(emailsKM);		
		emailsCc.addAll(emailsDM);
		MimeMessage message = mailSender.createMimeMessage();
		message.setSubject("New Course Added");		
		Map<String, String> templateVars = new HashMap<String, String>();
		templateVars.put("LectorName", owner.getLogin());
		templateVars.put("CourseName", course.getName());
		templateVars.put("CourseCategory", course.getCategory().getCategory());
		templateVars.put("CourseDescription", detail.getDescription());
		templateVars.put("CourseLinks", detail.getLinks());
		templateVars.put("CourseDetailLink", "http://localhost:8080/tc.edu/courses/"+courseId);
		templateVars.put("CourseSubscribeLink", "http://localhost:8080/tc.edu/courses/"+courseId+"/subscribe");
		StringBuffer content = new StringBuffer();
	    content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
	    		freemarkerMailConfiguration.getTemplate("new_course_added.ftl"), templateVars));
		message.setContent(content.toString(), "text/html");
		sendMail(emailsTo, emailsCc, message);
		
	}

	@Override
	public void sendCourseRejectedMail(User owner, List<String> emailsKM, List<String> emailsDM,
			Course course, String courseId)
					throws IOException, TemplateException, MessagingException {
		
		List<String> emailsTo=new ArrayList<String>();
		emailsTo.add(owner.getEmail());
		List<String> emailsCc=new ArrayList<String>();
		emailsCc.addAll(emailsKM);
		emailsCc.addAll(emailsDM);		
		MimeMessage message = mailSender.createMimeMessage();
		message.setSubject("Course Rejected");		
		Map<String, String> templateVars = new HashMap<String, String>();
		String numberVote;
		if (course.getVoteKM()==course.getVoteDM()) {
			numberVote="both";
		} else {
			numberVote="one";
		}
		String managerDecisionKM;
		if (course.getVoteKM()==StatesEnum.REJECTED.ordinal()) {
			managerDecisionKM="Reject";
		} else {
			managerDecisionKM="Approve";
		}
		String managerDecisionDM;
		if (course.getVoteDM()==StatesEnum.REJECTED.ordinal()) {
			managerDecisionDM="Reject";
		} else {
			managerDecisionDM="Approve";
		}		
		templateVars.put("numberVote", numberVote);		
		templateVars.put("CourseName", course.getName());
		templateVars.put("ManagerDecisionKM", managerDecisionKM);
		templateVars.put("ManagerReasonKM", course.getReasonKM());
		templateVars.put("ManagerDecisionDM", managerDecisionDM);
		templateVars.put("ManagerReasonDM", course.getReasonDM());
		templateVars.put("CourseUpdateLink", "http://localhost:8080/tc.edu/courses/"+courseId+"/update");		
		StringBuffer content = new StringBuffer();
	    content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
	    		freemarkerMailConfiguration.getTemplate("course_rejected.ftl"), templateVars));
		message.setContent(content.toString(), "text/html");
		sendMail(emailsTo, emailsCc, message);
		
	}

	@Override
	public void sendCourseDeletedMail(List<String> emailsKM, List<String> emailsDM, User owner, 
			Course course, Detail detail) throws IOException, TemplateException, MessagingException {
		
		List<String> emailsTo=new ArrayList<String>();
		emailsTo.addAll(emailsKM);
		emailsTo.addAll(emailsDM);
		List<String> emailsCc=new ArrayList<String>();
		emailsCc.add(owner.getEmail());
		MimeMessage message = mailSender.createMimeMessage();
		message.setSubject("Course Deleted");		
		Map<String, String> templateVars = new HashMap<String, String>();
		templateVars.put("LecturerName", owner.getLogin());
		templateVars.put("CourseName", course.getName());
		templateVars.put("CourseCategory", course.getCategory().getCategory());
		templateVars.put("CourseDescription", detail.getDescription());
		templateVars.put("CourseLinks", detail.getLinks());
		String managerDecisionKM;
		if (course.getVoteKM()==StatesEnum.REJECTED.ordinal()) {
			managerDecisionKM="Reject";
		} else {
			managerDecisionKM="Approve";
		}
		String managerDecisionDM;
		if (course.getVoteDM()==StatesEnum.REJECTED.ordinal()) {
			managerDecisionDM="Reject";
		} else {
			managerDecisionDM="Approve";
		}		
		templateVars.put("CourseName", course.getName());
		templateVars.put("ManagerDecisionKM", managerDecisionKM);
		templateVars.put("ManagerReasonKM", course.getReasonKM());
		templateVars.put("ManagerDecisionDM", managerDecisionDM);
		templateVars.put("ManagerReasonDM", course.getReasonDM());
		StringBuffer content = new StringBuffer();
	    content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
	    		freemarkerMailConfiguration.getTemplate("course_deleted.ftl"), templateVars));
		message.setContent(content.toString(), "text/html");
		sendMail(emailsTo, emailsCc, message);		
	}

	@Override
	public void sendCourseIsOpen(List<String> emailsSubscribers, Course course)
			throws MessagingException, IOException, TemplateException {
		
		List<String> emailsTo=new ArrayList<String>();
		emailsTo.addAll(emailsSubscribers);
		List<String> emailsCc=new ArrayList<String>();
		emailsCc.add(course.getDetail().getLecturer());
		MimeMessage message = mailSender.createMimeMessage();
		message.setSubject("Course Is Open");		
		Map<String, String> templateVars = new HashMap<String, String>();
		templateVars.put("CourseName", course.getName());
		templateVars.put("CourseLecturer", course.getDetail().getLecturer());
		StringBuffer content = new StringBuffer();
	    content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
	    		freemarkerMailConfiguration.getTemplate("course_is_open.ftl"), templateVars));
		message.setContent(content.toString(), "text/html");
		sendMail(emailsTo, emailsCc, message);
		
	}

	@Override
	public void sendCourseIsReady(Course course) throws MessagingException,
			IOException, TemplateException {
		
		List<String> emailsTo=new ArrayList<String>();
		emailsTo.add(course.getDetail().getLecturer());
		List<String> emailsCc=new ArrayList<String>();
		MimeMessage message = mailSender.createMimeMessage();
		message.setSubject("Course Is Ready To Start");		
		Map<String, String> templateVars = new HashMap<String, String>();
		templateVars.put("CourseName", course.getName());
		templateVars.put("CourseLecturer", course.getDetail().getLecturer());
		StringBuffer content = new StringBuffer();
	    content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
	    		freemarkerMailConfiguration.getTemplate("course_is_ready.ftl"), templateVars));
		message.setContent(content.toString(), "text/html");
		sendMail(emailsTo, emailsCc, message);
		
	}

	@Override
	public void sendCourseStarted(List<String> emailsAttendees, Course course)
			throws MessagingException, IOException, TemplateException {
		
		List<String> emailsTo=new ArrayList<String>();
		emailsTo.addAll(emailsAttendees);
		List<String> emailsCc=new ArrayList<String>();
		MimeMessage message = mailSender.createMimeMessage();
		message.setSubject("Course Has Been Started");		
		Map<String, String> templateVars = new HashMap<String, String>();
		templateVars.put("CourseName", course.getName());
		templateVars.put("CourseLecturer", course.getDetail().getLecturer());
		StringBuffer content = new StringBuffer();
	    content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
	    		freemarkerMailConfiguration.getTemplate("course_started.ftl"), templateVars));
		message.setContent(content.toString(), "text/html");
		sendMail(emailsTo, emailsCc, message);
		
	}

	@Override
	public void sendCourseFinished(List<String> emailsAttendees, Course course)
			throws MessagingException, IOException, TemplateException {
		
		List<String> emailsTo=new ArrayList<String>();
		emailsTo.addAll(emailsAttendees);
		List<String> emailsCc=new ArrayList<String>();
		MimeMessage message = mailSender.createMimeMessage();
		message.setSubject("Course Was Finished");		
		Map<String, String> templateVars = new HashMap<String, String>();
		templateVars.put("CourseName", course.getName());
		templateVars.put("CourseLecturer", course.getDetail().getLecturer());
		StringBuffer content = new StringBuffer();
	    content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
	    		freemarkerMailConfiguration.getTemplate("course_finished.ftl"), templateVars));
		message.setContent(content.toString(), "text/html");
		sendMail(emailsTo, emailsCc, message);
		
	}

	@Override
	public void sendCourseNeedsVote(List<String> emailsAttendeesNotEvaluated, Course course)
			throws MessagingException, IOException, TemplateException {
		
		List<String> emailsTo=new ArrayList<String>();
		emailsTo.addAll(emailsAttendeesNotEvaluated);
		List<String> emailsCc=new ArrayList<String>();
		MimeMessage message = mailSender.createMimeMessage();
		message.setSubject("Course Still Needs Your Vote");		
		Map<String, String> templateVars = new HashMap<String, String>();
		templateVars.put("CourseName", course.getName());
		templateVars.put("CourseLecturer", course.getDetail().getLecturer());
		StringBuffer content = new StringBuffer();
	    content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
	    		freemarkerMailConfiguration.getTemplate("course_needs_vote.ftl"), templateVars));
		message.setContent(content.toString(), "text/html");
		sendMail(emailsTo, emailsCc, message);
		
	}


}
