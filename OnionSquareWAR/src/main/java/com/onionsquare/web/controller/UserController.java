package com.onionsquare.web.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.onionsquare.core.model.Seller;
import com.onionsquare.core.model.Store;
import com.onionsquare.core.service.MailService;
import com.onionsquare.core.service.SellerService;
import com.onionsquare.core.util.OnionSquareConstants;
/**
 * 
 * handle login ,logout of customer ,admin and seller
 *
 */
@Controller
public class UserController extends AbstractController{
	
	public static final Logger LOGGER = Logger.getLogger(StoreController.class.getName());
	
	
	
	@Autowired
	private SellerService sellerService;
	
	@RequestMapping(value="/admin", method = RequestMethod.GET)
	public String adminLogin(ModelMap model) {	
		if(isAdminLoggedIn())
			return "admin-home"; 
		else if (isSellerLoggedIn() || isCustomerLoggedIn())
			return  "home";
		model.addAttribute("sellerLogin", false);
		return "admin-login";	
	}
	
	
 
	@RequestMapping(value="/admin/login/{errorMessage}", method = RequestMethod.GET)
	public String adminLoginError(ModelMap model ,@PathVariable final String errorMessage) { 
		model.addAttribute("error", true);
		model.addAttribute("errorMessage",errorMessage);
		model.addAttribute("sellerLogin", false);

		return "admin-login"; 
	}
 
	@RequestMapping(value="/admin/logout", method = RequestMethod.GET)
	public String adminLogout(HttpServletRequest request,ModelMap model) { 
		setCurrentUserId(0);
		request.getSession().invalidate();
		model.addAttribute("sellerLogin", false);

		return "admin-login"; 
	}
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public String adminHome(ModelMap model) {
		model.addAttribute("message", "Welcome SUPER-ADMIN");
		return "admin-home"; 
	}
	
	@RequestMapping(value="/seller-login", method = RequestMethod.GET)
	public ModelAndView sellerLogin(ModelMap model) { 		
		if(isSellerLoggedIn()){
			return new ModelAndView("redirect:/seller/seller-home");
		}
		else if (isAdminLoggedIn() || isCustomerLoggedIn())
			return  new ModelAndView("home");
		return new ModelAndView("seller-login");
	}
 
	@RequestMapping(value="/seller/login/{errorMessage}", method = RequestMethod.GET)
	public String sellerLoginError(ModelMap model, @PathVariable final String errorMessage,HttpServletRequest request) { 
		if(errorMessage.equalsIgnoreCase("accountDisabled"))			
           saveError(request, "Please verify you mail, You account has not been activated");
		else 
			saveError(request, "Enter correct username and password");
		return "seller-login"; 
	}
	
	@RequestMapping(value="/{storeSubDomain}/seller-login", method = RequestMethod.GET)
	public ModelAndView sellerLoginFromStore(ModelMap model,@PathVariable final String storeSubDomain, HttpServletRequest request) { 		
		if(isSellerLoggedIn()){
			return new ModelAndView("redirect:/seller/seller-home");
		}
		
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return  new ModelAndView("page-not-found");
		 model = mandatoryInStore(model,store,storeSubDomain);
		 navigationMap = new  LinkedHashMap<String, String>();
		 navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		 navigationMap.put(OnionSquareConstants.LOGIN, "/"+storeSubDomain+"/seller-login");
		 model.addAttribute("navigationMap", navigationMap);
		 return new ModelAndView("seller-login-store");
	}
 
	@RequestMapping(value="/{storeSubDomain}/seller/login/{errorMessage}", method = RequestMethod.GET)
	public String sellerLoginErrorFromStore(ModelMap model, @PathVariable final String errorMessage,HttpServletRequest request,@PathVariable final String storeSubDomain) { 
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return  "page-not-found";
		 
		 model = mandatoryInStore(model,store,storeSubDomain);
		if(errorMessage.equalsIgnoreCase("accountDisabled"))			
           saveError(request, "Please verify you mail, You account has not been activated");
		else 
			saveError(request, "Enter correct username and password");
		
		
		navigationMap = new  LinkedHashMap<String, String>();
		navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		navigationMap.put(OnionSquareConstants.LOGIN, "/"+storeSubDomain+"/seller-login");
		model.addAttribute("navigationMap", navigationMap);
		return "seller-login-store"; 
	}
 
	@RequestMapping(value="/seller/logout", method = RequestMethod.GET)
	public String sellerLogout(HttpServletRequest request,ModelMap model) { 
		try{
			Seller seller = sellerService.getSellerBySellerId(getCurrentUserId());
			seller.setLastLoginDate(new Date());
			sellerService.updateSeller(seller);
			setCurrentUserId(0);
			request.getSession().invalidate();
		
		}catch(Exception e){
			saveError(request, "Unable to logout properly due to internal error");
		}
		return "seller-login"; 
	}
	
	@RequestMapping(value="/{storeSubDomain}/customer-login", method = RequestMethod.GET)
	public ModelAndView customerLogin(ModelMap model,@PathVariable final String storeSubDomain , HttpServletRequest request) { 
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return  new ModelAndView("page-not-found");
		 
		 model = mandatoryInStore(model,store,storeSubDomain);
		if(isCustomerLoggedIn()){
			return new ModelAndView("redirect:"+storeSubDomain+"/customer/customer-home");
		}
		request.getSession().setAttribute("storeDomain", storeSubDomain);

		navigationMap = new  LinkedHashMap<String, String>();
		navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		navigationMap.put(OnionSquareConstants.LOGIN, "/"+storeSubDomain+"/customer-login");
		model.addAttribute("navigationMap", navigationMap);
	
		return new ModelAndView("customer-login");
	}
	

	@RequestMapping(value="/customer-login", method = RequestMethod.GET)
	public ModelAndView customerLoginIndex(ModelMap model, HttpServletRequest request) { 

	   
		return new ModelAndView("customer-login");
	}
 
	@RequestMapping(value="/{storeSubDomain}/customer-login/{errorMessage}", method = RequestMethod.GET)
	public ModelAndView customerLoginFailure(HttpServletRequest request,ModelMap model,@PathVariable final String storeSubDomain,@PathVariable final String errorMessage) { 		
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return new ModelAndView("page-not-found");
		 
		 model = mandatoryInStore(model,store,storeSubDomain);
		
		if(isCustomerLoggedIn()){
			return new ModelAndView("redirect:"+storeSubDomain+"/customer/customer-home");
		}

		
		if(errorMessage!=null && !errorMessage.isEmpty())
			saveError(request, "Enter correct username and password");
		
		navigationMap = new  LinkedHashMap<String, String>();
		navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		navigationMap.put(OnionSquareConstants.LOGIN, "/"+storeSubDomain+"/customer-login");
		model.addAttribute("navigationMap", navigationMap);
	
		return new ModelAndView("customer-login");
	}
	

 
	@RequestMapping(value="/{storeSubDomain}/customer/logout", method = RequestMethod.GET)
	public String customerLogout(HttpServletRequest request,ModelMap model,@PathVariable final String storeSubDomain) { 
		setCurrentUserId(0);
		request.getSession().invalidate();
		
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return "page-not-found";
		 
		 model = mandatoryInStore(model,store,storeSubDomain);
		 navigationMap = new  LinkedHashMap<String, String>();
		 navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		 model.addAttribute("navigationMap", navigationMap);
		 return "customer-login"; 
	}
	
	/**
	 * ankur: redirect to Forgot Password screen.
	 *  
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/seller-forgot-password", method = RequestMethod.GET)
	public String sellerForgotPassword(HttpServletRequest request, ModelMap model) {
		return "seller-forgot-password";
	}
	
	/**
	 * ankur: redirect to Forgot Password screen of Store.
	 *  
	 * @param request
	 * @param model
	 * @param storeSubDomain
	 * @return
	 */
	@RequestMapping(value="/{storeSubDomain}/seller-forgot-password", method = RequestMethod.GET)
	public String sellerForgotPasswordFromStore(HttpServletRequest request, ModelMap model,@PathVariable final String storeSubDomain) {
		Store store = storeExist(storeSubDomain);
		 if(store==null)
				return "page-not-found";
		 
		return "store-seller-forgot-password";
	}
	
	/**
	 * ankur: redirect to Forgot Password screen of Customer.
	 *  
	 * @param request
	 * @param model
	 * @param storeSubDomain
	 * @return
	 */
	@RequestMapping(value="/{storeSubDomain}/customer-forgot-password", method = RequestMethod.GET)
	public String customerForgotPassword(HttpServletRequest request, ModelMap model,@PathVariable final String storeSubDomain) {
		Store store = storeExist(storeSubDomain);
		 if(store==null)
				return "page-not-found";
		
		 return "customer-forgot-password";
	}

	/**
	 * ankur: redirect to Forgot Password screen for Admin
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/admin-forgot-password", method=RequestMethod.GET)
	public String adminForgotPassword(HttpServletRequest request,ModelMap model){
		model.addAttribute("sellerLogin", false);
		return "admin-forgot-password";
	}
}