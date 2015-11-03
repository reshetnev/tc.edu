package com.mycompany.webapp.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;

import com.mycompany.webapp.domain.Course;
import com.mycompany.webapp.domain.Detail;
import com.mycompany.webapp.domain.User;
import com.mycompany.webapp.domain.form.ApprovalForm;
import com.mycompany.webapp.domain.form.UpdateForm;

import freemarker.template.TemplateException;

public interface  MailService {
	
	public void sendMail(List<String> emailsTo, List<String> emailsCc, MimeMessage message)
			throws AddressException, MessagingException;
	
	public void sendCourseAnnouncementMail(List<String> emailsKM, List<String> emailsDM,
			String emailOwner, String owner, UpdateForm updateForm, String courseId)
					throws IOException, TemplateException, MessagingException;
	
	public void sendCourseApprovalUpdateMail(User owner, List<String> emailsKM, List<String> emailsDM,
			String login, Course course, ApprovalForm approvalForm)
					throws IOException, TemplateException, MessagingException;
	
	public void sendNewCourseAddedMail(List<String> emailsU, User owner, List<String> emailsKM,
			List<String> emailsDM, Course course, Detail detail, String courseId)
					throws IOException, TemplateException, MessagingException;
	
	public void sendCourseRejectedMail(User owner, List<String> emailsKM, List<String> emailsDM,
			Course course, String courseId)
					throws IOException, TemplateException, MessagingException;
	
	public void sendCourseDeletedMail(List<String> emailsKM, List<String> emailsDM, User owner, 
			Course course, Detail detail)
					throws IOException, TemplateException, MessagingException;
	
	public void sendCourseIsOpen(List<String> emailsSubscribers, Course course)
			throws MessagingException, IOException, TemplateException;
	
	public void sendCourseIsReady(Course course)
			throws MessagingException, IOException, TemplateException;
	
	public void sendCourseStarted(List<String> emailsAttendees, Course course)
			throws MessagingException, IOException, TemplateException;
	
	public void sendCourseFinished(List<String> emailsAttendees, Course course)
			throws MessagingException, IOException, TemplateException;
	
	public void sendCourseNeedsVote(List<String> emailsAttendeesNotEvaluated, Course course)
			throws MessagingException, IOException, TemplateException;

}
