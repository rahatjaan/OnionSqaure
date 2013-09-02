package com.onionsquare.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.onionsquare.core.model.Admin;
import com.onionsquare.core.model.Inquiry;
import com.onionsquare.core.model.Invitation;
import com.onionsquare.core.model.Seller;
import com.onionsquare.core.model.SellerNetwork;
import com.onionsquare.core.model.Store;
import com.onionsquare.core.service.AdminService;
import com.onionsquare.core.service.InquiryService;
import com.onionsquare.core.service.InvitationService;
import com.onionsquare.core.service.MailService;
import com.onionsquare.core.service.OrderService;
import com.onionsquare.core.service.SellerNetworkService;
import com.onionsquare.core.service.SellerService;
import com.onionsquare.core.service.StoreService;
import com.onionsquare.core.util.OnionSquareConstants;

@Controller
public class SellerController extends AbstractController   {	
	
	@Autowired 
	private SellerService sellerService;	
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private InvitationService invitationService;
	
	@Autowired
	private SellerNetworkService sellerNetworkService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private OrderService orderService;

	
	private Seller seller;
	
	private Store store;
	
	private Invitation invitation;
	
	private PasswordEncoder encoder;
	
	private int sellerId ;
	
	public static final Logger LOGGER = Logger.getLogger(StoreController.class.getName());
	
	
	public SellerController(){
		
	}
	
	@RequestMapping(value="/seller-form", method = RequestMethod.GET)
	public ModelAndView sellerLogin(ModelMap model) { 		
		if(isSellerLoggedIn()){
			return new ModelAndView("redirect:/seller/seller-home");
		}
		Seller seller = new Seller();
		model.addAttribute("seller",seller);
	
		model.addAttribute("countryList", getCountryList());
		return new ModelAndView("seller-form");
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/save-seller")
	public String saveSeller(@ModelAttribute("seller") Seller seller,HttpServletRequest request, Model model){
		 try{
			 
			 //ankur: Validate with Captcha
			 String actualcapthaValue = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			 String capthaValue = StringUtils.defaultString(request.getParameter("captcha"));
			 if(!capthaValue.equals(actualcapthaValue)) {
				 saveError(request, "Value doesn't match with image.");
				 model.addAttribute("seller",seller);	    		
		    	 model.addAttribute("countryList", getCountryList());
				 return "seller-form";
			 }

           //check invitaion 
			 if(seller.getInvitationId()!=null  && !seller.getInvitationId().isEmpty())
			 {
				 invitation =  invitationService.getInvitationById(Integer.parseInt(seller.getInvitationId()));
				 if(invitation!=null){
					  encoder = new Md5PasswordEncoder();
					 String token =  encoder.encodePassword(seller.getUsername(), invitation.getSeller().getSellerId());
					 if(token.equals(seller.getToken())){
						 seller.setRefererSeller(invitation.getSeller());
					     invitation.setInvitationStatus(true);
					     invitationService.updateInvitationStatus(invitation);
					 }
				 }	
			 }
		    seller.setCreatedDate(new Date());
		    seller.setCreatedDate(new Date());
		    
		    Seller tempSeller=sellerService.getSellerBySellerName(seller.getUsername());
		    if(tempSeller!=null){
		    	saveError(request, "Username already exist ,Please enter another email Address");
		    }else{
			     encoder = new Md5PasswordEncoder();
		    	String token = UUID.randomUUID().toString();
		    	seller.setToken(token);
		    	seller.setStatus(false);
		    	seller.setPassword(encoder.encodePassword(seller.getPassword(), null));
		    	Seller newSeller = sellerService.signUpSeller(seller);
		    	
		    	try{
			    String verifyToken = encoder.encodePassword(token, newSeller.getSellerId());			    
			    mailService.sendSellerConfirmationEmail(newSeller.getUsername(), "seller-register-verification",newSeller.getSellerId(), verifyToken);
			    saveMessage(request, "Please check your email to complete the registration.");
			     seller= new Seller();
		    	}catch(Exception e)
		    	{
		    		//sellerService.removeSellerBySellerId(newSeller.getSellerId());
		    		LOGGER.warn("Email cannot be send due to internal error. "+e.getMessage());
		    		saveError(request, "Registration failed: Email cannot be send due to internal error.");
		    		model.addAttribute("seller",seller);
		    		
		    		model.addAttribute("countryList", getCountryList());
		    		return "seller-form";
		    	}
		    }
		 }catch(Exception e){
                saveError(request, "Seller cannot be saved due to internal error.");
            	model.addAttribute("seller",seller);	    		
	    		model.addAttribute("countryList", getCountryList());
	    		return "seller-form";
            	
 		 }
		 
	     model.addAttribute("seller", seller);
	     model.addAttribute("showSignUp", "showSignUp");
	     return "home";	
		
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/{storeSubDomain}/save-seller")
	public String saveSellerFromStore(@ModelAttribute("seller") Seller seller,HttpServletRequest request, ModelMap model,@PathVariable final String storeSubDomain){
		 try{
            
			 Store store = storeExist(storeSubDomain);
			 if(store==null)
					return "page-not-found";
			 
			 model = mandatoryInStore(model,store,storeSubDomain);
			 
			 String actualcapthaValue = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			 String capthaValue = StringUtils.defaultString(request.getParameter("captcha"));
			 if(!capthaValue.equals(actualcapthaValue)) {
				 saveError(request, "Value doesn't match with image.");
				 model.addAttribute("countryList", getCountryList());
			     model.addAttribute("seller", seller);
			     model.addAttribute("storeSubDomain", storeSubDomain);
		 		 return "seller-form-store";
			 }
			 
			 
           //check invitaion 
			 if(seller.getInvitationId()!=null  && !seller.getInvitationId().isEmpty())
			 {
				 invitation =  invitationService.getInvitationById(Integer.parseInt(seller.getInvitationId()));
				 if(invitation!=null){
					  encoder = new Md5PasswordEncoder();
					 String token =  encoder.encodePassword(seller.getUsername(), invitation.getSeller().getSellerId());
					 if(token.equals(seller.getToken())){
						 seller.setRefererSeller(invitation.getSeller());
					     invitation.setInvitationStatus(true);
					     invitationService.updateInvitationStatus(invitation);
					 }
				 }	
			 }
		    seller.setCreatedDate(new Date());
		    seller.setCreatedDate(new Date());
		    
		    Seller tempSeller=sellerService.getSellerBySellerName(seller.getUsername());
		    if(tempSeller!=null){
		    	
		    	saveError(request, "Username already exist ,Please enter another email Address");
		    	
		    }else{
		    	
			    encoder = new Md5PasswordEncoder();
		    	String token = UUID.randomUUID().toString();
		    	seller.setToken(token);
		    	seller.setStatus(false);
		    	seller.setPassword(encoder.encodePassword(seller.getPassword(), null));
		    	Seller newSeller = sellerService.signUpSeller(seller);		
		    	
		    	try{
		    		
				    String verifyToken = encoder.encodePassword(token, newSeller.getSellerId());			    
				    mailService.sendSellerConfirmationEmail(newSeller.getUsername(), "seller-register-verification",newSeller.getSellerId(), verifyToken);
				    saveMessage(request, "Please check your email to complete the registration.");
				    seller= new Seller();
				    model.addAttribute("seller", seller);
				    model.addAttribute("showSignUp", "showSignUp");
				    return "home";
		    	
		    	}catch(Exception e)
		    	{
		    		sellerService.removeSellerBySellerId(newSeller.getSellerId());
		    		LOGGER.warn("Email cannot be send due to internal error. "+e.getMessage());
		    		saveError(request, "Registration failed: Email cannot be send due to internal error.");

		    	}
		    }
		 }catch(Exception e){
                saveError(request, "Seller cannot be saved due to internal error.");
               
 		 }
		 
		 model.addAttribute("countryList", getCountryList());
	     model.addAttribute("seller", seller);
	     model.addAttribute("storeSubDomain", storeSubDomain);
 		 return "seller-form-store";
		 
	  	
		
	}
	
	@RequestMapping(value="/seller/seller-home", method = RequestMethod.GET)
	public String sellerHome(ModelMap model, HttpServletRequest request) {
		
		getStoreSellerId(request);	
			
		store = storeService.getStoreBySellerId(sellerId);
  		if(store==null){
  			return createStore(model);
		}
		request.getSession().setAttribute("storeSubDomain", store.getSubDomainName());

  		Seller seller =sellerService.getSellerBySellerId(sellerId);
  		String sellerName = seller.getFirstName();
		request.getSession().setAttribute("sellerName", sellerName);
	    model.addAttribute("sellerName",sellerName);
 		model.addAttribute("message", "Welcome Seller");
 		model.addAttribute("seller",seller);
	    model.addAttribute("storeSubDomain",store.getSubDomainName());

		return "seller-home"; 
	}
	
   
	
	@RequestMapping(value="/seller-register-verification", method = RequestMethod.GET)
	public String verifyEmail(@ModelAttribute("seller") Store store,HttpServletRequest request, Model model) {
		
		String sellerId = request.getParameter("id");
		String sellerToken = request.getParameter("token");			
 		seller=sellerService.getSellerBySellerId(Integer.parseInt(sellerId));
 		if(seller!=null)
 		{
 			PasswordEncoder encoder = new Md5PasswordEncoder();
	    	String token=encoder.encodePassword(seller.getToken(), sellerId);
	    	if(token.equals(sellerToken))
	    	{
	    		seller.setStatus(true);
	    		sellerService.updateSeller(seller);
	    	}
	    	saveMessage(request, "Registration is completed. Please login. ");
	    	
 		}else{
 			saveError(request, "Problem activating seller");
 		}
		return "seller-login"; 
	}
	
	@RequestMapping(value="/seller/invitation-program", method = RequestMethod.GET)
	public String invitationProgram(ModelMap model , HttpServletRequest request) {
	 store = storeService.getStoreBySellerId(getCurrentUserId());
  		if(store==null){
  			return createStore(model);
		}
		
       invitation = new Invitation(); 
       Store store = storeService.getStoreBySellerId(getCurrentUserId());
       String sellerName =(String)request.getSession().getAttribute("sellerName");
	   model.addAttribute("sellerName",sellerName);
 	   model.addAttribute("invitation",invitation);
	   model.addAttribute("storeSubDomain",store.getSubDomainName());

	return "invitation-program"; 
	}
	
	
	@RequestMapping(value="/seller/add-invitee", method = RequestMethod.POST)
	public String addInvitee(@ModelAttribute("invitation") Invitation invitation,HttpServletRequest request, Model model) {
		
		try{
			invitation.setInvitationDate(new Date());
			Seller seller = sellerService.getSellerBySellerId(getCurrentUserId());
			invitation.setSeller(seller);
			PasswordEncoder encoder = new Md5PasswordEncoder();
		    String verifyToken = encoder.encodePassword(invitation.getInviteeEmail(), seller.getSellerId());
            invitation.setToken(verifyToken);	 
            invitation.setInvitationStatus(false);
            invitationService.saveInvitation(invitation);
	    	try{
	    		store = storeService.getStoreById(getCurrentUserId());
	    		String sellerFullName= seller.getFirstName()+" "+seller.getMiddleName()+" "+seller.getLastName();
			    mailService.sendInvitaionEmail(invitation.getInviteeEmail(),"seller-invitation-verification", invitation.getInvitationId(), verifyToken,sellerFullName);
			    saveMessage(request, "Invitation is sent successfully");
	            invitation = new Invitation();
 	    	}catch(Exception e)
	    	{
 	    		LOGGER.warn("Email cannot be sent due to internal error. "+e.getMessage());
	    		saveError(request, "Invitation cannot be sent due to internal error");
	    	}			
		}catch(Exception e){
			
    		saveError(request, "Invitation cannot be sent due to internal error");			
		}
		String sellerName =(String)request.getSession().getAttribute("sellerName");
	    model.addAttribute("sellerName",sellerName);
		invitation = new Invitation();       
 		model.addAttribute("invitation",invitation);
 	   model.addAttribute("storeSubDomain",store.getSubDomainName());
		return "invitation-program"; 
	}
	
	@RequestMapping(value="/seller-invitation-verification", method = RequestMethod.GET)
	public String verifyInviteeEmail(@ModelAttribute("seller") Store store,HttpServletRequest request, ModelMap model) {
	
		String sellerName =(String)request.getSession().getAttribute("sellerName");
	    model.addAttribute("sellerName",sellerName);
		String invitationId = request.getParameter("id");
		String invitationToken = request.getParameter("token");			
 		seller = new Seller();
		model.addAttribute("invitationId", Integer.parseInt(invitationId));
		model.addAttribute("invitationToken", invitationToken );
		model.addAttribute("countryList", getCountryList());
		model.addAttribute("seller", seller);
	     return "seller-form";	
	}
	
	
	@RequestMapping(value="/seller/seller-changePassword", method = RequestMethod.GET)
	public String viewUpdatePassword(HttpServletRequest request, ModelMap model) {
		  Seller seller = new Seller();
		  store = storeService.getStoreBySellerId(getCurrentUserId());
	  		if(store==null){
	  			return createStore(model);
			}
		    model.addAttribute("storeSubDomain",store.getSubDomainName());
		 	seller.setSellerId(getCurrentUserId());
		 	String sellerName =(String)request.getSession().getAttribute("sellerName");
		    model.addAttribute("sellerName",sellerName);  
			model.addAttribute("seller", seller);

		    return "seller-changePassword"; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/seller/changePassword")
	public String updateSellerPassword(@ModelAttribute("seller") Seller seller,HttpServletRequest request, Model model){
		 try{
                store = storeService.getStoreById(getCurrentUserId());
	        	seller.setModifiedDate(new Date());		   
	        	Seller updateSeller = sellerService.getSellerBySellerId(seller.getSellerId());
			    encoder = new Md5PasswordEncoder();		
			    String oldPassword=encoder.encodePassword(seller.getOldPassword(), null);
			    if(!updateSeller.getPassword().equals(oldPassword) ){
			    	saveError(request, "Old password is not correct .Please enter correct password");
			    }else{
				    encoder = new Md5PasswordEncoder();
				    updateSeller.setPassword(encoder.encodePassword(seller.getPassword(), null));		
				    sellerService.updateSeller(updateSeller);
					saveMessage(request, "Your password has been updated successfully.");	
				 }
		    
		 }catch(Exception e){
             saveError(request, "Seller cannot be edited due to internal error.");
		 }
		 String sellerName =(String)request.getSession().getAttribute("sellerName");
		 model.addAttribute("sellerName",sellerName);
	     model.addAttribute("seller", seller);
		 model.addAttribute("storeSubDomain",store.getSubDomainName());
	     return "seller-changePassword";
		 
		 }
	
	@RequestMapping(value="/seller/seller-editForm", method = RequestMethod.GET)
	public String sellerForm(HttpServletRequest request, ModelMap model) {
		getStoreSellerId(request);	
	   store = storeService.getStoreBySellerId(sellerId);
  		if(store==null){
  			return createStore(model);
		}
		Seller seller = new Seller();
		seller=sellerService.getSellerBySellerId(sellerId);
		if(seller.getState().getStateId()!=1 && seller.getCountry().getCountryId()!=1)
			model.addAttribute("stateList", getStateByCountryId(seller.getCountry().getCountryId()));
	    model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
		model.addAttribute("sellerName",request.getSession().getAttribute("sellerName"));
		model.addAttribute("countryList", getCountryList());
		model.addAttribute("seller", seller);
		return "seller-editForm"; 
	}
	
	@RequestMapping(value="/seller/seller-profile", method = RequestMethod.GET)
	public String sellerProfile(HttpServletRequest request, ModelMap model) {
		Seller seller = new Seller();
		getStoreSellerId(request);	
	    store = storeService.getStoreBySellerId(sellerId);
  		if(store==null){
  			return createStore(model);
		}
  		
		seller=sellerService.getSellerBySellerId(sellerId);
	    model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
		model.addAttribute("sellerName",request.getSession().getAttribute("sellerName"));
		model.addAttribute("seller", seller);
		model.addAttribute("store",store);
		model.addAttribute("seller_profile", true);
		return "seller-profile"; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/seller/save-profile-picture")
	public String uploadPicture(@ModelAttribute("store") Store store,HttpServletRequest request, ModelMap model){
		 String imagePath = "seller"; 
	     
	     if(store.getSellerPicture().getSize()>0){
				MultipartFile   filedata= (CommonsMultipartFile) store.getSellerPicture();
	
				if(filedata!=null){
					File folder = new File(OnionSquareConstants.UPLOAD_URL+imagePath);
				     if(!folder.exists())
							folder.mkdirs();
					String filePath = OnionSquareConstants.UPLOAD_URL+imagePath+"/"+ store.getStoreId()+".jpg";							
				    File destination = new File(filePath);					 
				    try {
				    	if(destination.exists())
				    		destination.delete();
				    	filedata.transferTo(destination);
				    	saveMessage(request, "Seller Profile is  uploaded successfully");

				    }catch(Exception e){
				    	saveError(request, "Seller Profile cannot be uploaded");
				    }
				}
	     }
				
	     store = storeService.getStoreBySellerId(getCurrentUserId());
  		if(store==null){
  			return createStore(model);
		}
		
		seller=sellerService.getSellerBySellerId(getCurrentUserId());
	    model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
		model.addAttribute("sellerName",request.getSession().getAttribute("sellerName"));
		model.addAttribute("seller", seller);
		model.addAttribute("store",store);
		return "seller-profile"; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/seller/edit-seller")
	public String editCustomer(@ModelAttribute("seller") Seller seller,HttpServletRequest request, Model model){
		 try{
			 String sellerName =(String)request.getSession().getAttribute("sellerName");
			 model.addAttribute("sellerName",sellerName);
			 
        	Seller updateSeller = sellerService.getSellerBySellerId(seller.getSellerId());	
        	seller.setPassword(updateSeller.getPassword());
        	seller.setCreatedDate(updateSeller.getCreatedDate());
        	seller.setModifiedDate(new Date());
            seller.setLastLoginDate(updateSeller.getLastLoginDate());
            seller.setRefererSeller(updateSeller.getRefererSeller());
        	sellerService.updateSeller(seller);        	
			saveMessage(request, "Seller Profile has been edited successfully."); 
		    
		 }catch(Exception e){
             saveError(request, "Seler cannot be edited due to internal error.");
           

		 }
		 
	     model.addAttribute("seller", seller);
	     model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
		 model.addAttribute("sellerName",request.getSession().getAttribute("sellerName"));
		 model.addAttribute("countryList", getCountryList());
		 model.addAttribute("stateList", getStateByCountryId(seller.getCountry().getCountryId()));


	     return "seller-editForm";
		 
		 }
	
	
	@RequestMapping(value="/seller/invitation-list", method = RequestMethod.GET)
	public String invitationList(HttpServletRequest request, ModelMap model) {	
		List<Invitation> invitationList = new ArrayList<Invitation>();
		getStoreSellerId(request);	
	    store = storeService.getStoreBySellerId(sellerId);
  		if(store==null){
  			return createStore(model);
		}
		invitationList = invitationService.getAllInvitationSentBySellerId(sellerId, null);
		if(invitationList.size()==0)
			saveMessage(request, "Invitaion list is empty");
		model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
	    model.addAttribute("sellerName",request.getSession().getAttribute("sellerName"));
		model.addAttribute("seller", seller);
		model.addAttribute("invitationList", invitationList);
		return "invitation-list"; 
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/seller/seller-inquiries")
	public String inquiriesToAdmin(ModelMap model, HttpServletRequest request ) {
//		getStoreSellerId(request);	
	  store = storeService.getStoreBySellerId(getCurrentUserId());
  		if(store==null){
  			return createStore(model);
		}
		List<Inquiry> inquiryList = new ArrayList<Inquiry>();
		inquiryList = inquiryService.findParentInquiryBySenderOrReceiver(store.getSeller().getSellerId(), OnionSquareConstants.USER_TYPE_SELLER, true);
		if(inquiryList.size()==0)
			saveMessage(request, "Inquiry list is empty");
		
		model.addAttribute("inquiryList",inquiryList);
		model.addAttribute("sellerId",store.getSeller().getSellerId());
		model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
		model.addAttribute("sellerName",request.getSession().getAttribute("sellerName"));
		return "seller-inquiries";
		
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/seller/seller-view-inquiries")
	public String sellerAdminInquiries(ModelMap model, HttpServletRequest request ) {
		getStoreSellerId(request);	
	    store = storeService.getStoreBySellerId(sellerId);
  		if(store==null){
  			return createStore(model);
		}
		
		if(request.getParameter("inquiryId")!=null){
			
			int inquiryId = Integer.parseInt(request.getParameter("inquiryId"));
			model = senderReceiverConversation( model, inquiryId, OnionSquareConstants.USER_TYPE_SELLER);
		
		}
		
		model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
	    model.addAttribute("sellerName",request.getSession().getAttribute("sellerName"));
		return "seller-view-inquiries";
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/seller/send-reply")
	public String sendSellerMessageFromAdmin(ModelMap model, HttpServletRequest request, @ModelAttribute Inquiry inquiry) {
		try{
			  Seller seller = sellerService.getSellerBySellerId(getCurrentUserId());
			  inquiry.setSenderEmail(seller.getUsername());
			  inquiry.setSenderName(seller.getFullName());
			  inquiry.setSenderUserType(OnionSquareConstants.USER_TYPE_SELLER);
			  inquiry.setSenderUserId(seller.getSellerId());
			  inquiry.setPostedDate(new Date());
			  inquiry.setInquiryStatus(true);
		      inquiryService.saveInquiry(inquiry);
			  mailService.sendInquiryToStore(inquiry.getSenderEmail(),inquiry.getReceiverEmail() ,inquiry.getInquiryContent());
		}catch(Exception e){
			saveError(request, "Message cannot be send due to internal error");
		}
	    return "redirect:/seller/seller-view-inquiries?inquiryId="+inquiry.getInqueryId();

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/seller/seller-inquiry-admin")
	public String inquiryToAdmin(ModelMap model, HttpServletRequest request ) {	
	  store = storeService.getStoreBySellerId(getCurrentUserId());
  		if(store==null){
  			return createStore(model);
		}
		 Inquiry inquiry = new Inquiry();
		 Inquiry parentInquiry = new Inquiry();
		 parentInquiry.setInqueryId(0);
		 Admin admin = adminService.getAdminByRoleName("ROLE_ADMIN").get(0);
		 inquiry.setReceiverEmail(admin.getAdminEmail());
		 inquiry.setReceiverName(admin.getUsername());
		 inquiry.setReceiverUserId(admin.getAdminId());
		 inquiry.setReceiverUserType(OnionSquareConstants.USER_TYPE_ADMIN);
		 inquiry.setParentInquiry(parentInquiry);
		model.addAttribute("inquiry",inquiry);
	    model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
		model.addAttribute("sellerName",request.getSession().getAttribute("sellerName"));
		return "seller-inquiry-admin";
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/seller/send-seller-inquiry")
	public String sendInquiryToAdmin(@ModelAttribute Inquiry inquiry,ModelMap model, HttpServletRequest request ) {
        try{
			  Seller seller = sellerService.getSellerBySellerId(getCurrentUserId());
			  Admin admin = adminService.getAdminByRoleName("ROLE_ADMIN").get(0);
			  inquiry.setReceiverUserId(admin.getAdminId());
			  inquiry.setReceiverEmail(admin.getAdminEmail());
			  inquiry.setReceiverName(admin.getUsername());
			  inquiry.setReceiverUserType(OnionSquareConstants.USER_TYPE_ADMIN);
			  inquiry.setSenderEmail(seller.getUsername());
			  inquiry.setSenderName(seller.getFullName());
			  inquiry.setSenderUserType(OnionSquareConstants.USER_TYPE_SELLER);
			  inquiry.setSenderUserId(seller.getSellerId());
			  inquiry.setPostedDate(new Date());
			  inquiry.setInquiryStatus(true);
			  Inquiry parentInquiry = new Inquiry();
			  parentInquiry.setInqueryId(OnionSquareConstants.PARENT_INQUIRY_ID);
			  inquiry.setParentInquiry(parentInquiry);
		      inquiryService.saveInquiry(inquiry);
			  mailService.sendInquiryToStore(seller.getUsername(),admin.getAdminEmail() ,inquiry.getInquiryContent());

               inquiry = new Inquiry();
               saveMessage(request, "Your message is send successfully to Admin");
			
        	
        }catch(Exception e){
        	
        	saveError(request,"Message cannot be send to the admin due to internal error");
        }
		
		model.addAttribute("inquiry",inquiry);
		model.addAttribute("sellerName",request.getSession().getAttribute("sellerName"));
	    model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));

		return "seller-inquiry-admin";
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/seller/sellerNetwork")
	public String viewSellerNetwork(ModelMap model, HttpServletRequest request) {
    
      List<SellerNetwork> sellerNetworkList = new ArrayList<SellerNetwork>();	
      SellerNetwork sellerNetwork = new SellerNetwork();
	try {
		getStoreSellerId(request);	
	    store = storeService.getStoreBySellerId(sellerId);
  		if(store==null){
  			return createStore(model);
		}	  
	     Seller seller = sellerService.getSellerBySellerId(sellerId);
	     sellerNetworkList = sellerNetworkService.listAllStoresInSellerNetwork(seller.getSellerId(), null);
	
	   } catch (Exception e) {
				saveError(request, "Seller Network list cannot be retrieved due to internal error");
	   }
      String sellerName =(String)request.getSession().getAttribute("sellerName");

	   model.addAttribute("sellerNetworkList", sellerNetworkList);	
	   model.addAttribute("sellerNetwork", sellerNetwork);
	   model.addAttribute("sellerName",sellerName);
	   model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
	   return "seller-network";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/seller/edit-sellerNetwork")
	public String editSellerNetwork(ModelMap model, HttpServletRequest request) {
		 SellerNetwork sellerNetwork= new SellerNetwork();
         List<SellerNetwork> sellerNetworkList = new ArrayList<SellerNetwork>();
		try {           
			  store = storeService.getStoreBySellerId(getCurrentUserId());
		  		if(store==null){
		  			return createStore(model);
				}
			if(request.getParameter("sellerNetworkId")!=null){
				int sellerNetworkId = Integer.parseInt(request.getParameter("sellerNetworkId"));
				sellerNetwork = sellerNetworkService.getSellerInNetworkById(sellerNetworkId);
			}
		

			if (sellerNetwork != null) {

				model.addAttribute("mode", "edit");
			} else {
				sellerNetwork = new SellerNetwork();
				saveError(request, "Seller Network id is incorrect");
			}
			
		    sellerNetworkList = sellerNetworkService.listAllStoresInSellerNetwork(getCurrentUserId(), null);


		} catch (Exception e) {
			saveError(request, "Seller network cannot be edited due to internal error");
		}
	    String sellerName =(String)request.getSession().getAttribute("sellerName");

	    model.addAttribute("sellerNetworkList", sellerNetworkList);	
	    model.addAttribute("sellerNetwork", sellerNetwork);
	    model.addAttribute("sellerName",sellerName);
	    model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));

		return "seller-network";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/seller/delete-sellerNetwork")
	public String deleteFromSellerNetwork(ModelMap model, HttpServletRequest request) {
		
		 SellerNetwork sellerNetwork= new SellerNetwork();
         List<SellerNetwork> sellerNetworkList = new ArrayList<SellerNetwork>();
		try {            
			if(request.getParameter("sellerNetworkId")!=null){
				 int sellerNetworkId = Integer.parseInt(request.getParameter("sellerNetworkId"));
				 sellerNetwork = sellerNetworkService.getSellerInNetworkById(sellerNetworkId);
			}
			
			if (sellerNetwork != null) {
	            sellerNetworkService.removeSellerFromSellerNetworkProgram(sellerNetwork);
				sellerNetwork = new SellerNetwork();
				saveError(request, "Store is deleted successfully from Seller's Network ");
			} else
				saveError(request, "Store added in Seller Network is null");
			
			sellerNetworkList = sellerNetworkService.listAllStoresInSellerNetwork(getCurrentUserId(), null);

		} catch (Exception e) {
			saveError(request, "Store cannot be deleted from Seller's Network ");
		}
	
	   String sellerName =(String)request.getSession().getAttribute("sellerName");

	    model.addAttribute("sellerNetworkList", sellerNetworkList);	
	    model.addAttribute("sellerNetwork", sellerNetwork);
	    model.addAttribute("sellerName",sellerName);
	    model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
		return "seller-network";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/seller/updated-sellerNetwork")
	public String addProduct(ModelMap model, HttpServletRequest request,
			@ModelAttribute("sellerNetwork") SellerNetwork sellerNetwork) {
		
         List<SellerNetwork> sellerNetworkList = new ArrayList<SellerNetwork>();
         boolean duplicateUrl= false;
		
	 try {

		
			if (request.getParameter("action").equals("save")) {
				
				Store store =storeService.getStoreBySubDomainName(sellerNetwork.getPeerStoreUrl());
			 	
				 if(store==null)
					  saveError(request, "Store url is incorrect .Please enter correct url");
				  else{
					   sellerNetworkList = sellerNetworkService.listAllStoresInSellerNetwork(getCurrentUserId(), null);
					   for(SellerNetwork sellerNetwork2:sellerNetworkList)
					   {
						   if(sellerNetwork2.getPeerStoreUrl().equals(sellerNetwork.getPeerStoreUrl())){
							   duplicateUrl= true;
							   break;

						   }
								   
					   }
					   if(duplicateUrl)
						   saveError(request, "Store is already added to Seller Network");
					   else{
						   sellerNetwork.setDisplayName(store.getStoreName());
						   Seller seller = sellerService.getSellerBySellerId(getCurrentUserId());
						   sellerNetwork.setSeller(seller);
						   sellerNetworkService.addStoreInNetwork(sellerNetwork);
							saveMessage(request, "Store is added to Seller's Network successfully");
							sellerNetwork = new SellerNetwork();
					   }

				  }
			     

			} else if (request.getParameter("action").equals("edit")){
			
		
			sellerNetworkService.updateSellerNetwork(sellerNetwork);
		    saveMessage(request, "Store status is updated in Seller's Network");
			sellerNetwork = new SellerNetwork();

			}
			
			
		
		} catch (Exception e) {
			saveError(request,
					"Store status cannot be  updated in Seller's Network");
		}
		sellerNetworkList = sellerNetworkService.listAllStoresInSellerNetwork(getCurrentUserId(), null);

	   String sellerName =(String)request.getSession().getAttribute("sellerName");

	    model.addAttribute("sellerNetworkList", sellerNetworkList);	
	    model.addAttribute("sellerNetwork", sellerNetwork);
	    model.addAttribute("sellerName",sellerName);
	    model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));

	    return "seller-network";
	}
	
	
	@RequestMapping(value = "/seller/onionsquare-terms-of-use")
	public String OnionsquareTermsOfUse(ModelMap model, HttpServletRequest request){
		model.addAttribute("sellerName",request.getSession().getAttribute("sellerName"));
	    model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
		return "seller-terms-of-use";
	}
	
	@RequestMapping(value = "/seller/onionsquare-privacy-policy")
	public String OnionsquarePrivacyPolicy(ModelMap model, HttpServletRequest request){
		model.addAttribute("sellerName",request.getSession().getAttribute("sellerName"));
	    model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
		return "seller-privacy-policy";
	}
	
	public String createStore(ModelMap model){
		store= new Store();
		seller= new Seller();
		seller.setSellerId(getCurrentUserId());
		store.setSeller(seller);
		model.addAttribute("store", store);
	    if(store.getCountry()!=null){
			    model.addAttribute("stateList", getStateByCountryId(store.getCountry().getCountryId()));

		}
		model.addAttribute("countryList", getCountryList());
		return "store-form";
	}
	
   public void getStoreSellerId(HttpServletRequest request){
		if(getCurrentUserRole().equalsIgnoreCase("ROLE_ADMIN") || getCurrentUserRole().equalsIgnoreCase("ROLE_MANAGER"))
		{   
			String sellerId = request.getSession().getAttribute("sellerId").toString();
			if(sellerId!=null )
				this.sellerId = Integer.parseInt(sellerId);
		
		}else{
			this.sellerId = getCurrentUserId();
		}
   }   

	/**
	 * ankur: Generate new 5 digit alphanumeric password, update it into database and send it via email to user.
	 *  
	 * @param request
	 * @param model
	 * @param redirectPage
	 * @return
	 */
	@RequestMapping(value={"/get-seller-forgot-password"}, method = RequestMethod.POST)
	public String userForgotPassword(HttpServletRequest request, ModelMap model) {
		generateNewPassword(request);
		return "seller-forgot-password";
	}
	
	/**
	 * ankur: Generate new 5 digit alphanumeric password, update it into database and send it via email to user.
	 *  
	 * @param request
	 * @param model
	 * @param storeSubDomain
	 * @return
	 */
	@RequestMapping(value={"/{storeSubDomain}/get-seller-forgot-password"}, method = RequestMethod.POST)
	public String userForgotPasswordFromStore(HttpServletRequest request, ModelMap model,@PathVariable final String storeSubDomain) {
		Store store = storeExist(storeSubDomain);
		if(store==null)
			return "page-not-found";
		else
			generateNewPassword(request);
		return "store-seller-forgot-password";
	}
	
	/**
	 * ankur: Generate new 5 digit alphanumeric password, update it into database and send it via email to user.
	 * @param request
	 */
	private void generateNewPassword(HttpServletRequest request) {
		String newPassword = RandomStringUtils.randomAlphanumeric(5);
		
		String username = StringUtils.defaultString(String.valueOf(request.getParameter("username")));
		if(username.length() > 0) {
			Seller seller = sellerService.getSellerBySellerName(username);
			if(seller != null) {
				encoder = new Md5PasswordEncoder();			
				seller.setPassword(encoder.encodePassword(newPassword, null));
				sellerService.updateSeller(seller);
				
				try {
					mailService.sendForgotPasswordEmail(seller.getUsername(), newPassword);
					saveMessage(request, "New password has been sent to "+seller.getUsername());
				} catch(Exception ex) {
					ex.printStackTrace();
					saveError(request, "Email cannot be send due to internal error.");
				}
			}else{
				saveError(request, "Entered username is not registered with OnionSquare.");
			}
		} else {
			saveError(request, "Please enter Username.");
		}
	}
}
