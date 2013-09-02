package com.onionsquare.core.service;

public interface MailService {

	public void sendEmail(String from, String to, String subject, String body);
	
	public void sendSellerConfirmationEmail(String to,String confirmationType, int sellerId, String token);
	
	public void sendInvitaionEmail(String to, String confirmationType,int sellerId, String token, String sellername);

	public void sendInquiryToStore(String from, String to,String messages);

	/*ankur: Send email for new password*/
	public void sendForgotPasswordEmail(String to, String newPassword);
}
