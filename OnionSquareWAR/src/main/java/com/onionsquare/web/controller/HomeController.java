package com.onionsquare.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.onionsquare.core.model.Seller;

@Controller
public class HomeController {	
	
	@RequestMapping(method=RequestMethod.GET, value="/")
	public String home(Model model){ 
	  return "home";
	 }
	
	@RequestMapping(value = "/404errorpage")
	public String handle404(){
		return "page-not-found";
	}
	
	@RequestMapping(value = "/onionsquare-description")
	public String onionsquareDescription(){
		return "onionsquare-description";
	}
	
	@RequestMapping(value = "/onionsquare-work")
	public String onionsquareWork(){
		return "onionsquare-work";
	}
	
	@RequestMapping(value = "/onionsquare-terms-of-use")
	public String OnionsquareTermsOfUse(){
		return "onionsquare-terms-of-use";
	}
	
	@RequestMapping(value = "/onionsquare-privacy-policy")
	public String OnionsquarePrivacyPolicy(){
		return "onionsquare-privacy-policy";
	}
	
	@RequestMapping(value = "/seller-terms-conditions")
	public String sellerTermsAndConditions(){
		return "seller-terms-conditions";
	}
	
	@RequestMapping(value = "/customer-terms-conditions")
	public String customerTermsAndConditions(){
		return "customer-terms-conditions";
	}
}