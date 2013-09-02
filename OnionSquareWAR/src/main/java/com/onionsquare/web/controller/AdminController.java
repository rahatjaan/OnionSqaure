package com.onionsquare.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.onionsquare.core.model.Admin;
import com.onionsquare.core.model.Customer;
import com.onionsquare.core.model.Inquiry;
import com.onionsquare.core.model.Seller;
import com.onionsquare.core.model.Store;
import com.onionsquare.core.service.AdminService;
import com.onionsquare.core.service.CustomerService;
import com.onionsquare.core.service.InquiryService;
import com.onionsquare.core.service.MailService;
import com.onionsquare.core.service.OrderService;
import com.onionsquare.core.service.SellerService;
import com.onionsquare.core.service.StoreService;
import com.onionsquare.core.util.OnionSquareConstants;

@Controller
public class AdminController  extends AbstractController{
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private  StoreService storeService;	
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private  SellerService sellerService;	
	
	
	
	@Autowired
	private InquiryService inquiryService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/admin-inquiry")
	public String inquiryToAdmin(ModelMap model, HttpServletRequest request ) {
		Inquiry inquiry = new Inquiry();
		model.addAttribute("inquiry",inquiry);
		int currentUserId = getCurrentUserId();
		
		if(currentUserId>0){
			 if(isAdminLoggedIn()){
				 saveError(request, "Message cannot be send to yourself");
			      model.addAttribute("mode", "button_disabled");
			 }else{			 
				 model.addAttribute("mode", "sender_email_disabled");
			}
			 
		 }
		return "admin-inquiry";
		
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/send-admin-inquiry")
	public String sendInquiryToAdmin(@ModelAttribute Inquiry inquiry,ModelMap model, HttpServletRequest request ) {
        try{
			  inquiry.setReceiverUserType(OnionSquareConstants.USER_TYPE_ADMIN);
			  Admin admin = adminService.getAdminByRoleName("ROLE_ADMIN").get(0);
			  inquiry.setReceiverUserId(admin.getAdminId());			 
			  inquiry.setInquiryStatus(true);
			  Inquiry parentInquiry = new Inquiry();
			  parentInquiry.setInqueryId(0);
			  inquiry.setParentInquiry(parentInquiry);
			  inquiry.setPostedDate(new Date());
			  inquiry.setReceiverEmail(admin.getAdminEmail());
			  inquiry.setReceiverName(admin.getUsername());
			   int currentUserId = getCurrentUserId();
			   if(currentUserId>0 ){
				  inquiry.setSenderUserId(currentUserId);
				  if(isCustomerLoggedIn()){
					  Customer customer = customerService.getCustomerByCustomerId(currentUserId);
					  inquiry.setSenderEmail(customer.getEmail());
					  inquiry.setSenderName(customer.getFullName());
					  inquiry.setSenderUserType(OnionSquareConstants.USER_TYPE_CUSTOMER);
				  }
				  else if(isSellerLoggedIn()){
					     Seller seller = sellerService.getSellerBySellerId(currentUserId);
						 inquiry.setSenderEmail(seller.getUsername());
						 inquiry.setSenderName(seller.getFullName());
					      inquiry.setSenderUserType(OnionSquareConstants.USER_TYPE_SELLER);
				  }	
					 model.addAttribute("mode", "sender_email_disabled");


			    } else{
				  inquiry.setSenderUserType(OnionSquareConstants.USER_TYPE_GUEST);
				  inquiry.setSenderUserId(0);
			    }
			  adminService.sendInquiryFromWebMaster(inquiry);
			  mailService.sendInquiryToStore(inquiry.getSenderEmail(), inquiry.getReceiverEmail(),inquiry.getInquiryContent());
               inquiry = new Inquiry();
               saveMessage(request, "Your message is send successfully");
			
        	
        }catch(Exception e){
        	
        	saveError(request,"Message cannot be send to the admin due to internal error");
        }
		
		model.addAttribute("inquiry",inquiry);
		return "admin-inquiry";
		
	}
	@RequestMapping(method = RequestMethod.GET, value = "/admin/view-admin-inquiries")
	public String inquiryFromAdmin(ModelMap model, HttpServletRequest request ) {
		
		List<Inquiry> inquiryList = new ArrayList<Inquiry>();
	    if(getCurrentUserRole().equals("ROLE_ADMIN"))
		  inquiryList = inquiryService.findParentInquiryBySenderOrReceiverType(OnionSquareConstants.USER_TYPE_MANAGER ,OnionSquareConstants.USER_TYPE_ADMIN , true);
	    else
		  inquiryList = inquiryService.findParentInquiryBySenderOrReceiverManager(getCurrentUserId(),OnionSquareConstants.USER_TYPE_MANAGER,OnionSquareConstants.USER_TYPE_ADMIN, true);
	
		for(Inquiry inquiry:inquiryList){
			inquiry.setStore(storeService.getStoreBySellerId(inquiry.getSenderUserId()));
		}
		if(inquiryList.size()==0)
			saveError(request, "Message List is empty");
		model.addAttribute("inquiryList",inquiryList);
		model.addAttribute("sender", "admin");
		return "admin-inquiries";
			
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/admin/view-seller-inquiries")
	public String inquiryFromSellers(ModelMap model, HttpServletRequest request ) {
		
		List<Inquiry> inquiryList = new ArrayList<Inquiry>();
	
		inquiryList = inquiryService.findParentInquiryBySenderOrReceiverType(OnionSquareConstants.USER_TYPE_SELLER, OnionSquareConstants.USER_TYPE_ADMIN, true);
		for(Inquiry inquiry:inquiryList){
			inquiry.setStore(storeService.getStoreBySellerId(inquiry.getSenderUserId()));
		}
		if(inquiryList.size()==0)
			saveError(request, "Message List is empty");
		model.addAttribute("inquiryList",inquiryList);
		model.addAttribute("sender", "seller");
		return "admin-inquiries";
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/admin/view-customer-inquiries")
	public String inquiryFromCustomer(ModelMap model, HttpServletRequest request ) {
		
		List<Inquiry> inquiryList = new ArrayList<Inquiry>();
	
		inquiryList = inquiryService.findParentInquiryBySenderOrReceiverType(OnionSquareConstants.USER_TYPE_CUSTOMER, OnionSquareConstants.USER_TYPE_ADMIN, true);
		if(inquiryList.size()==0)
			saveError(request, "Message List is empty");
		model.addAttribute("inquiryList",inquiryList);
		model.addAttribute("sender", "customer");
		return "admin-inquiries";
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/admin/view-guest-inquiries")
	public String inquiryFromGuest(ModelMap model, HttpServletRequest request ) {
		
		List<Inquiry> inquiryList = new ArrayList<Inquiry>();
		inquiryList = adminService.listParentInquiriesPostedByGuest();
		if(inquiryList.size()==0)
			saveError(request, "Message List is empty");
		model.addAttribute("inquiryList",inquiryList);
		model.addAttribute("sender", "guest");
		return "admin-inquiries";
		
	}
	
//	@RequestMapping(method = RequestMethod.GET, value = "/admin/view-guest-inquiry")
//	public String viewGuestInquiry(ModelMap model, HttpServletRequest request ) {
//		
//		Inquiry inquiry = new Inquiry();	
//		if(request.getParameter("inquiryId")!=null){
//			int inquiryId = Integer.parseInt(request.getParameter("inquiryId"));
//			inquiry = adminService.getInquiryByInquiryId(inquiryId);
//		}
//	    if(inquiry==null)
//	    	inquiry = new Inquiry();
//	    model.addAttribute("inquiry", inquiry);
//	    model.addAttribute("guest", "guest");
//	    return "admin-inquiry-visitor";
//	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/admin/admin-view-inquiries")
	public String viewInquiry(ModelMap model, HttpServletRequest request ) {		
	
		if(request.getParameter("inquiryId")!=null){
			int inquiryId = Integer.parseInt(request.getParameter("inquiryId"));
			if(getCurrentUserRole().equals("ROLE_ADMIN"))
				model = senderReceiverConversation( model, inquiryId, OnionSquareConstants.USER_TYPE_ADMIN);
			else
				model = senderReceiverConversation( model, inquiryId, OnionSquareConstants.USER_TYPE_MANAGER);

		}
		return "admin-view-inquiries";	
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/admin/admin-manager-inquiries")
	public String viewAdminManagerInquiries(ModelMap model, HttpServletRequest request ) {		
	
		if(request.getParameter("inquiryId")!=null){
			int inquiryId = Integer.parseInt(request.getParameter("inquiryId"));
			if(getCurrentUserRole().equals("ROLE_ADMIN"))
				model = senderReceiverConversation( model, inquiryId, OnionSquareConstants.USER_TYPE_ADMIN);
			else
				model = senderReceiverConversation( model, inquiryId, OnionSquareConstants.USER_TYPE_MANAGER);
	
		}
		return "admin-view-inquiries";	
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/admin/admin-reply")
	public String sendSellerMessageFromAdmin(ModelMap model, HttpServletRequest request, @ModelAttribute Inquiry inquiry) {
		
		try{
			  Admin admin = adminService.getAdminUserById(getCurrentUserId());
			  inquiry.setSenderEmail(admin.getAdminEmail());
			  inquiry.setSenderName(admin.getUsername());
			  inquiry.setSenderUserId(admin.getAdminId());
			  inquiry.setPostedDate(new Date());
			  inquiry.setInquiryStatus(true);
		      inquiryService.saveInquiry(inquiry);
			  mailService.sendInquiryToStore(inquiry.getSenderEmail(),inquiry.getReceiverEmail() ,inquiry.getInquiryContent());			
		}catch(Exception e){
			saveError(request, "Message cannot be send due to internal error");
			
		}
		if(inquiry.getInqueryId()==null)
		    return "redirect:/admin/admin-view-inquiries?inquiryId="+inquiry.getParentInquiry().getInqueryId();
		else
	    return "redirect:/admin/admin-view-inquiries?inquiryId="+inquiry.getInqueryId();

	}
	
	
	
	
//	@RequestMapping(method = RequestMethod.GET, value = "/admin/view-customer-inquiries")
//	public String inquiryFromCustomers(ModelMap model, HttpServletRequest request ) {
//		List<Inquiry> inquiryList = new ArrayList<Inquiry>();
//		inquiryList = adminService.listParentInquiriesPostedByCustomer(getCurrentUserId());
//		for(Inquiry inquiry:inquiryList){
//			inquiry.setCustomer(customerService.getCustomerByCustomerId(inquiry.getSenderUserId()));
//		}
//		model.addAttribute("inquiryList",inquiryList);
//		model.addAttribute("sender", "customer");
//		return "admin-view-inquiries";
//		
//	}
	
	@RequestMapping(value="/admin/manager-form", method = RequestMethod.GET)
	public String managerForm(ModelMap model , HttpServletRequest request) {		
		Admin manager = new Admin();			
		model.addAttribute("manager", manager);		
		return "manager-form"; 
	}
	
	@RequestMapping(value="/admin/manager-list", method = RequestMethod.GET)
	public String managerList(ModelMap model , HttpServletRequest request) {
		List<Admin> managerList ;
		managerList = adminService.getAdminByRoleName("ROLE_MANAGER");
		if(managerList==null){
			saveMessage(request, "Manager List is empty");
			managerList = new ArrayList<Admin>();
		}else if(managerList.size()==0)
		{
			saveMessage(request, "Manager List is empty");
		}
		model.addAttribute("managerList", managerList);		
		return "manager-list"; 
	}
	
	@RequestMapping(value="/admin/edit-manager", method = RequestMethod.GET)
	public String editManager(ModelMap model , HttpServletRequest request) {
		Integer	managerId=0;
		Admin manager= new Admin();
		String manager_id = request.getParameter("managerId");
		try{
		if(manager_id==null || manager_id=="")
			throw new Exception("Manager id is null or empty.");
		 managerId = Integer.parseInt(manager_id); 
		if(managerId==0 || managerId<0)
			throw new Exception("Manager id is equal to zero.");
		  manager = adminService.getAdminUserById(managerId);
		if(manager==null){
			throw new Exception("Manager cannot be displayed due to internal error");
		}
		}catch (Exception e) {
			saveError(request, e.getMessage());
			List<Admin> managerList ;
			managerList = adminService.getAdminByRoleName("ROLE_MANAGER");	
			model.addAttribute("managerList", managerList);		
			return "manager-list";
			
		}
		model.addAttribute("manager", manager);	
		model.addAttribute("mode", "edit");
		return "manager-form";  
	}
	
	
	@RequestMapping(method = RequestMethod.POST , value = "/admin/save-manager")
	public String saveCustomer(@ModelAttribute("manager") Admin manager,HttpServletRequest request, Model model){
		
		
			if(request.getParameter("action").equals("save")){
				try{
						Admin adminExist = adminService.getAdminByUserName(manager.getUsername());
						if(adminExist==null){
							manager.setCreatedDate(new Date());			
							manager.setRoleName("ROLE_MANAGER");
							Md5PasswordEncoder encoder = new Md5PasswordEncoder();
						    manager.setPassword(encoder.encodePassword(manager.getPassword(), null));			
							adminService.createAdminUser(manager);
							saveMessage(request, "Manager is created successfully.");
						    manager = new Admin();
					    }else{
					    	saveError(request, "Username already exist.Please enter another username.");
				       }
				 }catch(Exception e){
		                saveError(request, "Manager cannot be saved due to internal error.");
		        

		 		 }
				model.addAttribute("manager", manager);			     
		        return "manager-form";
			}else{
				try{
					Admin adminExist = adminService.getAdminByUserName(manager.getUsername());
					manager.setModifiedDate(new Date());		
					manager.setRoleName("ROLE_MANAGER");
				    manager.setPassword(adminExist.getUsername());			
					adminService.updateAdminUser(manager);
					saveMessage(request, "Manager is updated successfully.");
				}catch(Exception e){
					saveError(request, "Manager cannot be edited due to internal error");
				}
				List<Admin> managerList ;
				managerList = adminService.getAdminByRoleName("ROLE_MANAGER");
				if(managerList.size()==0){
					saveMessage(request, "Manager List is empty");
				}
				model.addAttribute("managerList", managerList);		
				return "manager-list"; 
				
			}
		
		
				
	}
	@RequestMapping(method = RequestMethod.GET, value = "/admin/view-financial-stat")
	public String viewFinancialStat(ModelMap model, HttpServletRequest request) {
		List<Store> storeList = storeService.getAllActiveStores();		
	    Date fromDate = null;
	    Date toDate = null;
	    String minDate = null;
	    String maxDate = null;
	    int selectedIndex=0;
	    List<Double> revenue_cost = new ArrayList<Double>();
		try {		
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
			String filterCriteria = request.getParameter("filterCriteria");
			if(filterCriteria!=null){
			//filter data on daily basis
				if(filterCriteria.equals("1")){
					fromDate = new Date();
	                toDate  = null;	
	                selectedIndex=1;
				}//filter data on weekly basis
				else if(filterCriteria.equals("2")){
					fromDate = new Date(System.currentTimeMillis() - (7 * 1000 * 60 * 60 * 24));
	                toDate  = new Date();	
	                selectedIndex=2;
				}//filter data on  current month basis
				else if(filterCriteria.equals("3")){
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					int month = cal.get(Calendar.MONTH)+1;
					int year = cal.get(Calendar.YEAR);
					if(month>12){
						month=1;
						year +=1;
					}
					fromDate = simpleDateFormat.parse(month+"/01/"+year);
					toDate = new Date();
					selectedIndex = 3;		
				}//filter data on  previous month basis
				else if (filterCriteria.equals("4")){
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					int currentMonth = cal.get(Calendar.MONTH)+1;
					int year = cal.get(Calendar.YEAR);

					if(currentMonth>12){
						currentMonth=1;
						year +=1;
					}
					int previousMonth = cal.get(Calendar.MONTH);
					fromDate = simpleDateFormat.parse(previousMonth+"/01/"+year);
					toDate = simpleDateFormat.parse(currentMonth+"/01/"+year);
					selectedIndex = 4;
				}
			}
			minDate = request.getParameter("fromDate");
			maxDate = request.getParameter("toDate");		
			if(minDate!=null)
			      fromDate = simpleDateFormat.parse(minDate);
			if(maxDate!=null)
			      toDate = simpleDateFormat.parse(maxDate);			
				 
			 for(Store store:storeList){	
				 revenue_cost = orderService.calculateTotalRevenueForStore(store.getStoreId(),fromDate, toDate);
				 store.setRevenue(revenue_cost.get(0));
				 store.setOnionsquareProfit(revenue_cost.get(1));
		         
			 }
				
				
			} catch (Exception e) {
				saveError(request, "Order list cannot be  retrieved due to internal error.");
			}
		    
		    model.addAttribute("storeList", storeList);
		    model.addAttribute("fromDate", minDate);
		    model.addAttribute("toDate", maxDate);
		    model.addAttribute("selectedIndex", selectedIndex);
	
			return "financial-stat";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/admin/view-stores")
	public String viewStoreOrder(ModelMap model, HttpServletRequest request) {
	  
		try {
			List<Store> storeList = storeService.getAllActiveStores();
		    model.addAttribute("storeList", storeList);				
		} catch (Exception e) {
				saveError(request, "Store list cannot be  retrieved due to internal error.");
		}	
		return "admin-view-stores";
	}
	
//	@RequestMapping(method = RequestMethod.GET, value = "/admin/view-store-orders")
//	public String viewStoreDetail(ModelMap model, HttpServletRequest request) {
//		List<Order> orderList = new ArrayList<Order>();
//        String storeId = request.getParameter("storeId");
//        String minDate = request.getParameter("fromDate");
//        String maxDate = request.getParameter("toDate");
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        Date fromDate = null;
//        Date toDate = null;
//        try {
//	        if(minDate!=null)
//	        	fromDate = simpleDateFormat.parse(minDate);
//	        if(maxDate!=null)
//	        	toDate = simpleDateFormat.parse(maxDate);			
//			  if(storeId!=null){
//				  orderList = orderService.listOrdersByStoreId(Integer.parseInt(storeId),fromDate, toDate);					
//				}		
//			} catch (Exception e) {
//				saveError(request, "Order  cannot be  retrieved due to internal error.");
//			}	
//          model.addAttribute("orderList", orderList);
//          model.addAttribute("storeId",storeId);
//          model.addAttribute("fromDate", minDate);
//		  model.addAttribute("toDate", maxDate);
//	
//		  return "admin-view-orders";
//	}
//	
	
//	@RequestMapping(method = RequestMethod.GET, value = "/admin/view-orderDetail")
//	public String viewOrderDetail(ModelMap model, HttpServletRequest request) {
//        Order order = new Order();
//        List<LineItem> lineItemList = new ArrayList<LineItem>();
//		String orderId = request.getParameter("orderId");      
//   		try{
//			  if(orderId!=null){
//				  order = orderService.getOrderByOrderId(Integer.parseInt(orderId));
//				  lineItemList = lineItemService.listLineItemByOrderId(order.getOrderId());
//				}		
//			} catch (Exception e) {
//				saveError(request, "Order  cannot be  retrieved due to internal error.");
//			}	
//             model.addAttribute("order", order);
//             model.addAttribute("lineItemList", lineItemList);	
//			return "admin-view-orderDetail";
//	}
//	
	@RequestMapping(method = RequestMethod.GET, value = "/admin/store-home")
	public String viewStoreAdminPage(ModelMap model, HttpServletRequest request) {
        String storeId = request.getParameter("storeId");
      
        try {
	          if(storeId!=null){
	        	  Store store = storeService.getStoreById(Integer.parseInt(storeId));
	              request.getSession().setAttribute("sellerId", store.getSeller().getSellerId());
	          }
			} catch (Exception e) {
				saveError(request, "Store cannot be retrieved due to internal error");
			}	
         
	
		  return "redirect:/seller/seller-home";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/admin/contact-seller")
	public String inquiryToSellerFromAdmin(ModelMap model, HttpServletRequest request ) {
		
		 String storeId = request.getParameter("storeId");
		 Store store = new Store();
	      
        try {
	          if(storeId!=null){
	        	   store = storeService.getStoreById(Integer.parseInt(storeId));
	          }
			} catch (Exception e) {
				saveError(request, "Store cannot be retrieved due to internal error");
			}	
		Inquiry inquiry = new Inquiry();
		inquiry.setReceiverUserId(store.getSeller().getSellerId());
		inquiry.setReceiverEmail(store.getSeller().getUsername());
		inquiry.setReceiverName(store.getSeller().getFullName());
		model.addAttribute("inquiry",inquiry);
		return "admin-inquiry-seller";
		
	}
	@RequestMapping(method = RequestMethod.POST, value = "/admin/send-inquiry-seller")
	public String sendInquiryToSeller(@ModelAttribute Inquiry inquiry,ModelMap model, HttpServletRequest request ) {
		List<Store> storeList = storeService.getAllActiveStores();

        try{
			  inquiry.setSenderUserType(OnionSquareConstants.USER_TYPE_ADMIN);
			  Admin admin = adminService.getAdminByRoleName("ROLE_ADMIN").get(0);			  
			  inquiry.setSenderUserId(admin.getAdminId());
			  inquiry.setSenderName(admin.getUsername());
			  inquiry.setSenderEmail(admin.getAdminEmail());
			  inquiry.setReceiverUserType(OnionSquareConstants.USER_TYPE_SELLER);
			 

			  
			  inquiry.setInquiryStatus(true);
			  Inquiry parentInquiry = new Inquiry();
			  parentInquiry.setInqueryId(0);
			  inquiry.setParentInquiry(parentInquiry);
			  inquiry.setPostedDate(new Date());
			  inquiryService.saveInquiry(inquiry);
			   mailService.sendInquiryToStore(inquiry.getSenderEmail(),inquiry.getReceiverEmail(),inquiry.getInquiryContent());
               inquiry = new Inquiry();
               saveMessage(request, "Your message is send successfully");
               storeList = storeService.getAllActiveStores();
              if(storeList==null)
            	  storeList = new ArrayList<Store>();
      		
        	
        }catch(Exception e){
        	
        	saveError(request,"Message cannot be send to the store due to internal error");
        	model.addAttribute("inquiry",inquiry);
    		return "admin-inquiry-seller";
        }
		
	
		    model.addAttribute("storeList", storeList);
		    return "admin-view-stores";
		    

		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/admin/admin-inquiry")
	public String inquiryToAdminOrManager(ModelMap model, HttpServletRequest request ) {
		Inquiry inquiry = new Inquiry();
		List<Admin> adminList = adminService.getAdminListExcludingLoginAdmin(getCurrentUserId());
		Admin admin = adminService.getAdminUserById(getCurrentUserId());
		inquiry.setSenderEmail(admin.getAdminEmail());
		inquiry.setSenderName(admin.getUsername());
		inquiry.setSenderUserId(admin.getAdminId());
		if(getCurrentUserRole().equalsIgnoreCase("ROLE_ADMIN"))
		  inquiry.setSenderUserType(OnionSquareConstants.USER_TYPE_ADMIN);
		else
		  inquiry.setSenderUserType(OnionSquareConstants.USER_TYPE_MANAGER);
		
		model.addAttribute("adminList", adminList);
		model.addAttribute("inquiry",inquiry);
		return "admin-manager-inquiry";
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/admin/send-admin-inquiry")
	public String sendInquiryToAdminOrManager(@ModelAttribute Inquiry inquiry,ModelMap model, HttpServletRequest request ) {
		try{
		 Admin admin = adminService.getAdminUserById(inquiry.getReceiverUserId());
		 inquiry.setReceiverEmail(admin.getAdminEmail());
		 inquiry.setReceiverName(admin.getUsername());
		 if(admin.getRoleName().equalsIgnoreCase("ROLE_ADMIN"))
			  inquiry.setReceiverUserType(OnionSquareConstants.USER_TYPE_ADMIN);
			else
			  inquiry.setReceiverUserType(OnionSquareConstants.USER_TYPE_MANAGER);
		  Inquiry parentInquiry = new Inquiry();
		  parentInquiry.setInqueryId(0);
		  inquiry.setParentInquiry(parentInquiry);
		  inquiry.setPostedDate(new Date());
		  inquiry.setInquiryStatus(true);
		  inquiryService.saveInquiry(inquiry);
		  mailService.sendInquiryToStore(inquiry.getSenderEmail(),inquiry.getReceiverEmail() ,inquiry.getInquiryContent());			
          inquiry = new Inquiry();
          saveMessage(request, "Message is send successfully");
		}catch(Exception e){
			saveError(request, "Message cannot be send due to internal error");

		}
		Admin admin = adminService.getAdminUserById(getCurrentUserId());
		List<Admin> adminList = adminService.listAdminUsers();
		adminList.remove(admin);
		model.addAttribute("adminList", adminList);
		model.addAttribute("inquiry",inquiry);
		return "admin-manager-inquiry";
		
	}
	
	@RequestMapping(value="/admin/admin-changePassword", method = RequestMethod.GET)
	public String viewUpdatePassword(HttpServletRequest request, ModelMap model) {
		  Admin admin = new Admin();
		 
		  admin = adminService.getAdminUserById(getCurrentUserId());	  
		  model.addAttribute("admin", admin);

		    return "admin-changePassword"; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/admin/changePassword")
	public String updateSellerPassword(@ModelAttribute("admin") Admin admin,HttpServletRequest request, Model model){
		 try{
	        	Admin updateAdmin= adminService.getAdminUserById(getCurrentUserId());
	        	updateAdmin.setModifiedDate(new Date());		   

	        	PasswordEncoder  encoder = new Md5PasswordEncoder();		
			    String oldPassword=encoder.encodePassword(admin.getOldPassword(), null);
			    if(!updateAdmin.getPassword().equals(oldPassword) ){
			    	saveError(request, "Old password is not correct .Please enter correct password");
			    }else{
				    encoder = new Md5PasswordEncoder();
				    updateAdmin.setPassword(encoder.encodePassword(admin.getPassword(), null));		
				    adminService.updateAdminUser(updateAdmin);
					saveMessage(request, "Your password has been updated successfully.");	
					admin = new Admin();
				 }
		    
		 }catch(Exception e){
             saveError(request, "Admin cannot be edited due to internal error.");
		 }
		
	     model.addAttribute("admin", admin);
	     return "admin-changePassword";
		 
	}
	
	@RequestMapping(value="/admin/admin-details", method = RequestMethod.GET)
	public String editDetailsView(HttpServletRequest request, ModelMap model) {
		  Admin admin = new Admin();
		 
		  admin = adminService.getAdminUserById(getCurrentUserId());	  
		  model.addAttribute("admin", admin);

		    return "admin-editDetails"; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/admin/edit-details")
	public String editDetails(@ModelAttribute("admin") Admin admin,HttpServletRequest request, Model model){
		 try{
			 
	        	Admin updateAdmin= adminService.getAdminUserById(getCurrentUserId());
				admin.setRoleName(updateAdmin.getRoleName()); 
	        	updateAdmin.setModifiedDate(new Date());		   
                updateAdmin.setAdminEmail(admin.getAdminEmail());
                updateAdmin.setPaypalEmail(admin.getPaypalEmail());
                updateAdmin.setUsername(admin.getUsername());
                updateAdmin.setPaypalAppId(admin.getPaypalAppId());
                updateAdmin.setPaypalUsername(admin.getPaypalUsername());
                updateAdmin.setPaypalPassword(admin.getPaypalPassword());
                updateAdmin.setPaypalSignature(admin.getPaypalSignature());
                updateAdmin.setPaypalMode(admin.getPaypalMode());
	            adminService.updateAdminUser(updateAdmin);
				saveMessage(request, "Admin details are  edited successfully");	
		    
		 }catch(Exception e){
             saveError(request, "Admin cannot be edited due to internal error.");
		 }
		
	     model.addAttribute("admin", admin);
	     return "admin-editDetails";
		 
		 }
	
	@RequestMapping(value = "/admin/onionsquare-terms-of-use")
	public String OnionsquareTermsOfUse(){
		return "admin-terms-of-use";
	}
	
	@RequestMapping(value = "/admin/onionsquare-privacy-policy")
	public String OnionsquarePrivacyPolicy(){
		return "admin-privacy-policy";
	}
		
	/**
	 * ankur: Generate new 5 digit alphanumeric password, update it into database and send it via email to user.
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/admin-forgot-password", method = RequestMethod.POST)
	public String getForgotPassword(HttpServletRequest request,ModelMap model){
		String newPassword = RandomStringUtils.randomAlphanumeric(5);		 
		String userName = StringUtils.defaultString((String)request.getParameter("username"));
		 
		if(userName.length() > 0) {
			Admin admin = adminService.getAdminByUserName(userName);
			if(admin != null) {
				Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				admin.setPassword(encoder.encodePassword(newPassword, null));
				adminService.updateAdminUser(admin);
				
				try {
					mailService.sendForgotPasswordEmail(admin.getAdminEmail(), newPassword);
					saveMessage(request, "New password has been sent to "+admin.getAdminEmail());
				} catch(Exception ex) {
					ex.printStackTrace();
					saveError(request, "Email cannot be send due to internal error.");
				}
			} else
				saveError(request, "Entered username is not registered with OnionSquare");
		} else {
			saveError(request, "Please enter username.");
		}
		
		model.addAttribute("sellerLogin", false);
		return "admin-forgot-password";
	}
}