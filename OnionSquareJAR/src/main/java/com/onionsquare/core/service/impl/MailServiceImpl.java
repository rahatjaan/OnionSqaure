package com.onionsquare.core.service.impl;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.onionsquare.core.service.MailService;
import com.onionsquare.core.util.OnionSquareConstants;

@Service
public class MailServiceImpl implements MailService {

	public static final Logger LOGGER = Logger.getLogger(MailServiceImpl.class.getName());
	
	@Autowired
	private MailSender mailSender;
	
	public void sendSellerConfirmationEmail(String to, String confirmationType,int sellerId, String token){
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("OnionSquare Registration <no-reply@onionsquare.com>");
		message.setTo(to);
		message.setSubject("Onionsquare | Registration confirmation");
		String url = OnionSquareConstants.BASE_URL+confirmationType+"?id="+sellerId+"&token="+token;
		message.setText("Click the link below to complete registration process. "+url);
		
		LOGGER.info("Sending confirmation email to "+ to);
		mailSender.send(message);
		
	}
	
	public void sendInvitaionEmail(String to, String confirmationType,int sellerId, String token, String sellername){
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("OnionSquare Registration <no-reply@onionsquare.com>");
		message.setTo(to);
		message.setSubject("Onionsquare | Invitation");
		String url = OnionSquareConstants.BASE_URL+confirmationType+"?id="+sellerId+"&token="+token;
		try{
		Icon icon = new ImageIcon("src/UI/Images/default_pic.png");
		}catch(Exception e){
			LOGGER.info("Onionsquare logo cannot be added to message due to internal error");
		}
		message.setText("Greeting! "+sellername+" sent you invitation to join OnionSquare.To sign up click the link below."+url);
		
		LOGGER.info("Sending confirmation email to "+ to);
		mailSender.send(message);
		
	}
	
	public void sendEmail(String from, String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		
		LOGGER.info("Sending email to "+ to +" with subject: "+subject);
		mailSender.send(message);
	}

	@Override
	public void sendInquiryToStore(String from, String to, String messages) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("OnionSquare inquiry message from  <"+from+">");
		message.setTo(to);
		message.setSubject("Onionsquare | Inquiry Message");
		message.setText(messages);
		
		LOGGER.info("Sending inquiry message email to "+ to);
		mailSender.send(message);		
	}
	
	/* 
	 * ankur:Send email for new password
	 * 
	 */
	@Override
	public void sendForgotPasswordEmail(String to,String newPassword) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("OnionSquare ForgotPassword <no-reply@onionsquare.com>");
		message.setTo(to);
		message.setSubject("Onionsquare | Forgot Password");
		message.setText("Your new password is "+newPassword);
		
		LOGGER.info("Sending Forgot Password email to "+ to);
		mailSender.send(message);
	}
}
