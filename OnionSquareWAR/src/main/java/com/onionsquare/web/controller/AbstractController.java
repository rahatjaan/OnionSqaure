package com.onionsquare.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.onionsquare.core.model.Category;
import com.onionsquare.core.model.Country;
import com.onionsquare.core.model.Inquiry;
import com.onionsquare.core.model.SellerNetwork;
import com.onionsquare.core.model.State;
import com.onionsquare.core.model.Store;
import com.onionsquare.core.model.User;
import com.onionsquare.core.service.CategoryService;
import com.onionsquare.core.service.CountryService;
import com.onionsquare.core.service.InquiryService;
import com.onionsquare.core.service.SellerNetworkService;
import com.onionsquare.core.service.StateService;
import com.onionsquare.core.service.StoreService;
import com.onionsquare.core.util.OnionSquareConstants;

@Controller
public class AbstractController {
	
	private int currentUserId;
	private String currentUserRole;
	public Map<String,String> navigationMap;
	
	public static final String MESSAGES_KEY = "successMessages";
	public static final String ERROR_KEY = "errors";
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private StateService stateService;
	
	@Autowired
	private SellerNetworkService sellerNetworkService;
	
	@Autowired
	private InquiryService inquiryService;
	
	protected int getCurrentUserId(){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			User user = (User) authentication.getPrincipal();
			if (user != null) {
				this.currentUserId = user.getUserId();
			}
		}else{
			this.currentUserId=-1;
		}
		return this.currentUserId;
	}
	
	
	public void setCurrentUserId(int currentUserId) {
		this.currentUserId = currentUserId;
	}


	protected boolean isSellerLoggedIn(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			return authentication.getAuthorities().contains(new SimpleGrantedAuthority(OnionSquareConstants.ROLE_SELLER));
		}
		
		return false;
	}
	
	protected boolean isCustomerLoggedIn(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			return authentication.getAuthorities().contains(new SimpleGrantedAuthority(OnionSquareConstants.ROLE_CUSTOMER));
		}
		
		return false;
	}
	
	protected boolean isAdminLoggedIn(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			return authentication.getAuthorities().contains(new SimpleGrantedAuthority(OnionSquareConstants.ROLE_ADMIN));
		}
		return false;
	}
	
	public String getCurrentUserRole() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null) {
			User user = (User) auth.getPrincipal();
			if (user != null) {
				Collection<GrantedAuthority> grandAuthiority = user.getAuthorities();
				this.currentUserRole = grandAuthiority.iterator().next().getAuthority();
			}
		}
		return currentUserRole;
	}
	
	
	public Store storeExist(String domainName){
		
		Store store = storeService.getStoreBySubDomainName(domainName);	   
		return store;
	}
	
	public ModelMap mandatoryInStore(ModelMap model, Store store , String storeSubDomain){
		
		List<Category> categoryList	= categoryService.listAllCategoriesByStoreId(store.getStoreId());
		List<SellerNetwork>sellerNetworkList = sellerNetworkService.listAllStoresInSellerNetwork(store.getSeller().getSellerId(),true);


		model.addAttribute("sellerNetworkList", sellerNetworkList);
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("storeSubDomain",storeSubDomain);
		model.addAttribute("facebookLink", store.getFacebookLink());
		
		return model;
		
		
	}
	
	
	public ModelMap senderReceiverConversation(ModelMap model, int inquiryId, int userType){
		Inquiry inquiry = new Inquiry();
		Inquiry newInquiry = new Inquiry();
		List<Inquiry> inquiryList = new ArrayList<Inquiry>();
		
		inquiry = inquiryService.getInquiryByInquiryId(inquiryId);
		
		if(inquiry.getParentInquiry()==null){
			inquiryList.add(inquiry);				
			inquiryList.addAll(inquiryService.findChildrenInquiryByParentInquiryId(inquiryId));	
			inquiry.setParentInquiry(inquiry);
			newInquiry.setParentInquiry(inquiry);
		}
		else {		
			inquiryList.add(inquiryService.getInquiryByInquiryId(inquiry.getParentInquiry().getInqueryId()));
			inquiryList.addAll(inquiryService.findChildrenInquiryByParentInquiryId(inquiry.getParentInquiry().getInqueryId()));
			newInquiry.setParentInquiry(inquiry.getParentInquiry());
		}
		
		if(inquiry.getReceiverUserType().equals(userType)){				
			newInquiry.setReceiverUserId(inquiry.getSenderUserId());
			newInquiry.setReceiverUserType(inquiry.getSenderUserType());
			newInquiry.setReceiverEmail(inquiry.getSenderEmail());
			newInquiry.setReceiverName(inquiry.getSenderName());
			newInquiry.setSenderName(inquiry.getReceiverName());
			newInquiry.setSenderUserType(inquiry.getReceiverUserType());
		}else{
			newInquiry.setReceiverUserId(inquiry.getReceiverUserId());
			newInquiry.setReceiverUserType(inquiry.getReceiverUserType());
			newInquiry.setReceiverEmail(inquiry.getReceiverEmail());
			newInquiry.setReceiverName(inquiry.getReceiverName());
			newInquiry.setSenderName(inquiry.getSenderName());
			newInquiry.setSenderUserType(inquiry.getSenderUserType());


		}
		model.addAttribute("newInquiry",newInquiry);
		model.addAttribute("inquiryList", inquiryList);
		return model;
	}
	
	

	public void saveError(HttpServletRequest request, String error) {	
		request.getSession().setAttribute(ERROR_KEY, error);
	}

	public void saveMessage(HttpServletRequest request, String msg) {
		request.getSession().setAttribute(MESSAGES_KEY, msg);
	}
	
	public List<Country> getCountryList(){
		List<Country> countryList = countryService.getCountryList();
		return countryList;
		
	}
	
	public List<State> getStateByCountryId(int countryId){
		List<State> stateList = new ArrayList<State>();
		if(countryId > 0)
			stateList = stateService.getStatesByCountryId(countryId);
		
		return stateList;
	}
	
	
	

	
}
