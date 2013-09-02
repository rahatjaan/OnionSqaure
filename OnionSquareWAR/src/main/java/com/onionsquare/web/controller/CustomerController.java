package com.onionsquare.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.onionsquare.core.model.Address;
import com.onionsquare.core.model.Admin;
import com.onionsquare.core.model.Customer;
import com.onionsquare.core.model.Inquiry;
import com.onionsquare.core.model.LineItem;
import com.onionsquare.core.model.Order;
import com.onionsquare.core.model.OrderStatus;
import com.onionsquare.core.model.Product;
import com.onionsquare.core.model.Seller;

import com.onionsquare.core.model.State;
import com.onionsquare.core.model.Store;
import com.onionsquare.core.service.AddressService;
import com.onionsquare.core.service.AdminService;
import com.onionsquare.core.service.CustomerService;
import com.onionsquare.core.service.CustomerTrackerService;
import com.onionsquare.core.service.InquiryService;
import com.onionsquare.core.service.LineItemService;
import com.onionsquare.core.service.MailService;
import com.onionsquare.core.service.OrderService;
import com.onionsquare.core.service.SellerService;
import com.onionsquare.core.util.OnionSquareConstants;
import com.onionsquare.web.model.ShoppingCart;
import com.onionsquare.web.service.ShoppingCartService;

@Controller
public class CustomerController  extends AbstractController{
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AddressService addressService;
	
	
	@Autowired 
	private ShoppingCartService shoppingCartService; 
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private LineItemService lineItemService;
	
   @Autowired
   private SellerService sellerService;
   
   @Autowired
   private AdminService adminService;
   
   @Autowired
   private MailService mailService;
   
   @Autowired
   private InquiryService inquiryService;
	
	@Autowired
	private CustomerTrackerService customerTrackerService;
	
	private PasswordEncoder encoder;
	
	public static final Logger LOGGER = Logger.getLogger(StoreController.class.getName());
	
	@RequestMapping(value="/{storeSubDomain}/customer/customer-home", method = RequestMethod.GET)
	public ModelAndView customerHome(ModelMap model ,HttpServletRequest request,@PathVariable final String storeSubDomain) throws IOException {
		
		Customer customer=customerService.getCustomerByCustomerId(getCurrentUserId());
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return new ModelAndView("page-not-found");
		 
		 model = mandatoryInStore(model,store,storeSubDomain);
		 navigationMap = new  LinkedHashMap<String, String>();
		 navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		 navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"+storeSubDomain+"/customer/customer-home");		 
		
		 if(shoppingCartService.getShoppingCart().getShoppingCartTotalAmount()>0){
			 Address address;
			 if(request.getSession().getAttribute("shippingAddress")!=null){
				 address = (Address) request.getSession().getAttribute("shippingAddress");			 
				 customer.setShippingAddress(address);
			 }else{
				 customer.setShippingAddress(customer.getBillingAddress());
			 }
		   if(customer.getShippingAddress()!=null)
			    if(customer.getShippingAddress().getCountry()!=null){
					    model.addAttribute("shippingStateList", getStateByCountryId(customer.getShippingAddress().getCountry().getCountryId()));
	
				}
			
			model.addAttribute("countryList", getCountryList());			 
			model.addAttribute("customer", customer);
			model.addAttribute("readonly", true);
			model.addAttribute("noOfItems",request.getSession().getAttribute("noOfItems"));			
			navigationMap.put(OnionSquareConstants.BILLING_SHIPPING_DETAIL, "/"+storeSubDomain+"/customer/customer-home");
			model.addAttribute("navigationMap", navigationMap);

			 return new ModelAndView("customer-checkout");
		}
		 
		model.addAttribute("customer", customer);
		model.addAttribute("navigationMap", navigationMap);
		 
		 return new ModelAndView("customer-home", "model", model); 
	}
	
	@RequestMapping(value="/{storeSubDomain}/customer/customer", method = RequestMethod.GET)
	public ModelAndView customer(ModelMap model ,HttpServletRequest request,@PathVariable final String storeSubDomain) throws IOException {
		
		Customer customer = customerService
				.getCustomerByCustomerId(getCurrentUserId());
		Store store = storeExist(storeSubDomain);
		if (store == null)
			return new ModelAndView("page-not-found");

		model = mandatoryInStore(model, store, storeSubDomain);
		navigationMap = new LinkedHashMap<String, String>();
		navigationMap.put(OnionSquareConstants.HOME, "/" + storeSubDomain);
		navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"
				+ storeSubDomain + "/customer/customer-home");

		model.addAttribute("customer", customer);
		model.addAttribute("navigationMap", navigationMap);

		return new ModelAndView("customer-home", "model", model);
	}
	
	@RequestMapping(value="/{storeSubDomain}/customer-form", method = RequestMethod.GET)
	public String customerForm(ModelMap model , HttpServletRequest request,@PathVariable final String storeSubDomain) {
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return "page-not-found";
		 
		 model = mandatoryInStore(model,store,storeSubDomain);
		
		Customer customer = new Customer();				
		model.addAttribute("customer", customer);
		 if(customer.getShippingAddress()!=null){
			 if(customer.getShippingAddress().getCountry()!=null)
			    model.addAttribute("stateList", getStateByCountryId(customer.getShippingAddress().getCountry().getCountryId()));

		}
		navigationMap = new  LinkedHashMap<String, String>();
		navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		navigationMap.put(OnionSquareConstants.SIGNUP, "/"+storeSubDomain+"/customer/customer-home");
		model.addAttribute("navigationMap", navigationMap);
		model.addAttribute("countryList", getCountryList());
		model.addAttribute("noOfItems",request.getSession().getAttribute("noOfItems"));
		return "customer-form"; 
	}
	
	@RequestMapping(value="/customer-form", method = RequestMethod.GET)
	public String customerFormInHomePage(ModelMap model , HttpServletRequest request) {
		
		
		Customer customer = new Customer();				
		model.addAttribute("customer", customer);
		 if(customer.getShippingAddress()!=null){
			 if(customer.getShippingAddress().getCountry()!=null)
			    model.addAttribute("stateList", getStateByCountryId(customer.getShippingAddress().getCountry().getCountryId()));

		}

		model.addAttribute("countryList", getCountryList());
		model.addAttribute("noOfItems",request.getSession().getAttribute("noOfItems"));
		return "customer-form-index"; 
	}
	
	@RequestMapping(value="/{storeSubDomain}/seller-form", method = RequestMethod.GET)
	public ModelAndView sellerLogin(ModelMap model,@PathVariable final String storeSubDomain) { 		
		if(isSellerLoggedIn()){
			return new ModelAndView("redirect:/seller/seller-home");
		}
		
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return new ModelAndView("page-not-found");
		 
		 model = mandatoryInStore(model,store,storeSubDomain);
		Seller seller = new Seller();
		model.addAttribute("seller",seller);
	
		model.addAttribute("countryList", getCountryList());
		return new ModelAndView("seller-form-store");
	}
	
	
	
	@RequestMapping(value="/{storeSubDomain}/seller-profile", method = RequestMethod.GET)
	public String sellerProfile(HttpServletRequest request, ModelMap model,@PathVariable final String storeSubDomain) {
		Seller seller = new Seller();
	   
			 Store store = storeExist(storeSubDomain);
			 if(store==null)
					return "page-not-found";
			 
			 model = mandatoryInStore(model,store,storeSubDomain);
		
		seller=sellerService.getSellerBySellerId(store.getSeller().getSellerId());
		model.addAttribute("sellerName",request.getSession().getAttribute("sellerName"));
		model.addAttribute("seller", seller);
		model.addAttribute("store",store);
		return "store-seller-profile"; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/{storeSubDomain}/save-customer")
	public ModelAndView saveCustomer(@ModelAttribute("customer") Customer customer,HttpServletRequest request, ModelMap model,@PathVariable final String storeSubDomain){
		
		try{
			 Store store = storeExist(storeSubDomain);
			 if(store==null)
					return new ModelAndView("page-not-found");
			 
			 model = mandatoryInStore(model,store,storeSubDomain);
			 
			 //ankur: Validate with Captcha
			 String actualcapthaValue = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			 String capthaValue = StringUtils.defaultString(request.getParameter("captcha"));
			 if(!capthaValue.equals(actualcapthaValue)) {
				 saveError(request, "Value doesn't match with image.");
				 model.addAttribute("customer", customer);
				 model.addAttribute("noOfItems",request.getSession().getAttribute("noOfItems"));
        
		 		if(customer.getBillingAddress()!=null){
		 			if(customer.getBillingAddress().getCountry()!=null)
		 				model.addAttribute("billingStateList", getStateByCountryId(customer.getBillingAddress().getCountry().getCountryId()));
	
		 		}
				 model.addAttribute("countryList", getCountryList());
				 return new ModelAndView("customer-form","model", model);
			 }
		
           	customer.setCreatedDate(new Date());
		    
		    Customer tempCustomer=customerService.getCustomerByUserName(customer.getUserName());
		    if(tempCustomer!=null){
		    	saveError(request, "Username already exist ,Please enter another username");
		    	model.addAttribute("customer", customer); 
		 		model.addAttribute("noOfItems",request.getSession().getAttribute("noOfItems"));
		 		if(customer.getShippingAddress()!=null){
	    			 if(customer.getShippingAddress().getCountry()!=null)
	    			    model.addAttribute("shippingStateList", getStateByCountryId(customer.getShippingAddress().getCountry().getCountryId()));
	
	    		}
		 		if(customer.getBillingAddress()!=null){
	    			 if(customer.getBillingAddress().getCountry()!=null)
	    			    model.addAttribute("billingStateList", getStateByCountryId(customer.getBillingAddress().getCountry().getCountryId()));
	
	    		}
               model.addAttribute("countryList", getCountryList());
		      return new ModelAndView("customer-form","model", model);
		    }else{		    	
		    		    encoder = new Md5PasswordEncoder();
				    	customer.setStatus(true);
				    	customer.setPassword(encoder.encodePassword(customer.getPassword(), null));
				   
				    	Address address= customer.getBillingAddress();
				    	addressService.saveShippingOrBillingAddress(address);
				    	customer.setBillingAddress(address);
				    	customerService.signUpCustomer(customer);
				    	saveMessage(request, "Congratulation, You have been signed up successfully.");			
			    		    	
		    
		    }
		 }catch(Exception e){
                saveError(request, "Customer cannot be saved due to internal error.");
                model.addAttribute("customer", customer);
        		model.addAttribute("noOfItems",request.getSession().getAttribute("noOfItems"));
        
		 		if(customer.getBillingAddress()!=null){
	    			 if(customer.getBillingAddress().getCountry()!=null)
	    			    model.addAttribute("billingStateList", getStateByCountryId(customer.getBillingAddress().getCountry().getCountryId()));
	
	    		}
                model.addAttribute("countryList", getCountryList());
		    	return new ModelAndView("customer-form","model", model);

 		 }
		model.addAttribute("customer", customer);
		model.addAttribute("noOfItems",request.getSession().getAttribute("noOfItems"));
		
	     
	     return new ModelAndView("redirect:/"+storeSubDomain+"/customer-login");	
		
	}
	
	@RequestMapping(value="/{storeSubDomain}/customer/edit-customer", method = RequestMethod.GET )
	public String customerForm(HttpServletRequest request, ModelMap model,@PathVariable final String storeSubDomain) {
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return "page-not-found";
		 
		 model = mandatoryInStore(model,store,storeSubDomain);
		Customer customer = new Customer();
	   
	    	customer = customerService.getCustomerByCustomerId(getCurrentUserId());
	    if(customer==null)
	    	customer = new Customer();
		
		if(customer.getBillingAddress()!=null){
			 if(customer.getBillingAddress().getCountry()!=null)
			    model.addAttribute("billingStateList", getStateByCountryId(customer.getBillingAddress().getCountry().getCountryId()));

		}
		model.addAttribute("customer", customer);
		model.addAttribute("mode", "edit");
		model.addAttribute("noOfItems",request.getSession().getAttribute("noOfItems"));		
		model.addAttribute("countryList", getCountryList());
		navigationMap = new  LinkedHashMap<String, String>();
		navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"+storeSubDomain+"/customer/customer-home");
		navigationMap.put(OnionSquareConstants.UPDATE_PROFILE, "/"+storeSubDomain+"/customer/edit-customer");
		model.addAttribute("navigationMap", navigationMap);
		return "customer-editForm"; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/{storeSubDomain}/customer/save-customer")
	public String editCustomer(@ModelAttribute("customer") Customer customer,HttpServletRequest request, ModelMap model ,@PathVariable final String storeSubDomain){
		 try{
			 Store store = storeExist(storeSubDomain);
			 if(store==null)
					return "page-not-found";
			 
			 model = mandatoryInStore(model,store,storeSubDomain);
        	customer.setModifiedDate(new Date());
		    
		    Customer tempCustomer=customerService.getCustomerByUserName(customer.getUserName());
		    if(!tempCustomer.getCustomerId().equals(customer.getCustomerId())){
		    	saveError(request, "Username already exist ,Please enter another email Address.");
		    	
		    }else{
		    
		    	 if (request.getParameter("action").equals("edit")) {
						 tempCustomer= customerService.getCustomerByCustomerId(customer.getCustomerId());
						 customer.setPassword(tempCustomer.getPassword());
						 customer.setCreatedDate(tempCustomer.getCreatedDate());
						
					     
					     Address address = customer.getBillingAddress();
					     address.setAddressId(null);
					     addressService.saveShippingOrBillingAddress(address);
					     customer.setBillingAddress(address);
					     
					     customer.setStatus(true);
					     customerService.updateCustomer(customer);					
						 saveMessage(request, "You profile has been edited successfully");
	                     
				}	
			    		    	
		    }  
		    
		 }catch(Exception e){
             saveError(request, "Customer cannot be edited due to internal error.");           

		 }
		
	
	 	 if(customer.getBillingAddress()!=null){
   			 if(customer.getBillingAddress().getCountry()!=null)
   			    model.addAttribute("billingStateList", getStateByCountryId(customer.getBillingAddress().getCountry().getCountryId()));

   		 }
		 navigationMap = new  LinkedHashMap<String, String>();
		 navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		 navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"+storeSubDomain+"/customer/customer-home");
		 navigationMap.put(OnionSquareConstants.UPDATE_PROFILE, "/"+storeSubDomain+"/customer/edit-customer");
		 model.addAttribute("navigationMap", navigationMap);
	     model.addAttribute("customer", customer);
	     model.addAttribute("mode", "edit");
	     model.addAttribute("countryList", getCountryList());
	     model.addAttribute("noOfItems",request.getSession().getAttribute("noOfItems"));
	     return "customer-editForm";
		 
	}
	
	@RequestMapping(value="/{storeSubDomain}/customer/customer-changePassword", method = RequestMethod.GET)
	public String viewUpdatePassword(HttpServletRequest request, ModelMap model ,@PathVariable final String storeSubDomain) {
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return "page-not-found";
		 
		 model = mandatoryInStore(model,store,storeSubDomain);
		Customer customer = new Customer();
	    
	    customer.setCustomerId(getCurrentUserId());
	    
	    
		model.addAttribute("customer", customer);
		model.addAttribute("noOfItems",request.getSession().getAttribute("noOfItems"));
		navigationMap = new  LinkedHashMap<String, String>();
		navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"+storeSubDomain+"/customer/customer-home");
		navigationMap.put(OnionSquareConstants.CHANGE_PASSWORD, "/"+storeSubDomain+"/customer/customer-changePassword");
		model.addAttribute("navigationMap", navigationMap);
		return "customer-changePassword"; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/{storeSubDomain}/customer/changePassword")
	public String updateCustomerPassword(@ModelAttribute("customer") Customer customer,HttpServletRequest request, ModelMap model ,@PathVariable final String storeSubDomain){
		 try{
				 Store store = storeExist(storeSubDomain);
				 if(store==null)
						return "page-not-found";
				 
				 model = mandatoryInStore(model,store,storeSubDomain);

	        	customer.setModifiedDate(new Date());		    
			    Customer updateCustomer=customerService.getCustomerByCustomerId(customer.getCustomerId());
			    encoder = new Md5PasswordEncoder();		
			    String oldPassword=encoder.encodePassword(customer.getOldPassword(), null);
			    if(!updateCustomer.getPassword().equals(oldPassword)){
			    	saveError(request, "Old password is not correct .Please enter correct password");
			    }else if( !updateCustomer.getEmail().equals(customer.getEmail()))
			    {
			    	saveError(request, "Email is not correct .Please enter correct email");

			    }
			    	else{
			    	
				    encoder = new Md5PasswordEncoder();
			    	updateCustomer.setPassword(encoder.encodePassword(customer.getPassword(), null));				
				    customerService.updateCustomer(updateCustomer);					
					saveMessage(request, "Your password has been updated successfully.");	
				 }
		    
		 }catch(Exception e){
             saveError(request, "Customer cannot be edited due to internal error.");
		}
		navigationMap = new  LinkedHashMap<String, String>();
		navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"+storeSubDomain+"/customer/customer-home");
		navigationMap.put(OnionSquareConstants.CHANGE_PASSWORD, "/"+storeSubDomain+"/customer/customer-changePassword");
		model.addAttribute("navigationMap", navigationMap);
	    model.addAttribute("customer", customer);
	    model.addAttribute("noOfItems",request.getSession().getAttribute("noOfItems"));
	    return "customer-changePassword";
		 
	}
	
	

	@RequestMapping(value="/{storeSubDomain}/customer/paySuccess", method = RequestMethod.GET)
	public String saveOrder(HttpServletRequest request, ModelMap model,@PathVariable final String storeSubDomain) {
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return "page-not-found";
		 Seller seller = store.getSeller();
         Order order= new Order();
		 model = mandatoryInStore(model,store,storeSubDomain);
		Customer customer = customerService.getCustomerByCustomerId(getCurrentUserId());
//		Order order =  (Order) request.getSession().getAttribute("order");
		ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();
        List<LineItem> lineItemList = shoppingCart.getLineItemByStore(store.getStoreId());
		try{
			 Seller referrerSeller = seller.getRefererSeller();
			 Double storeTotalAmount = shoppingCart.getShoppingCartTotalAmountByStore(store.getStoreId());

			 if(referrerSeller!=null)
			 {
				 Double invitation_profit = 0.005*storeTotalAmount;
			     referrerSeller.setInvitationProfit(invitation_profit);
				 sellerService.updateSeller(referrerSeller);

		
			 }
			 Double profit_gained =  store.getSeller().getInvitationProfit();
			 if(profit_gained!=null){
				 if(profit_gained>0){
					 Double commissionToOnionSquare = 0.08 * storeTotalAmount;
					 Double  remaining_profit= commissionToOnionSquare-profit_gained;
					 if(remaining_profit<=2){
						 seller.setInvitationProfit(profit_gained+2-commissionToOnionSquare);
					 }else{
						 seller.setInvitationProfit(0.0);
					 }					
					 sellerService.updateSeller(seller);

				 }
			    
			  
			 }
			  
			 
			 String orderId = request.getSession().getAttribute("orderId").toString();
			 
			 OrderStatus orderStatus = new OrderStatus();
			 orderStatus.setStatusName("Pending");
			 orderStatus.setOrderStatusId(1);
			 
			 order = orderService.getOrderByOrderId(Integer.parseInt(orderId));
			 order.setOrderStatus(orderStatus);
			 orderService.updateOrder(order);
			
			
			
		}catch(Exception e){
			
			saveMessage(request,"Order cannot be saved due to internal error");
		}
		

        for(LineItem lineItem: lineItemList){
        	if(lineItem.getProduct().getStore().getStoreId().equals(store.getStoreId()))
        		shoppingCart.removeItemFromCart(lineItem.getProduct().getProductId());
        }
        
    	Collection<LineItem> lineItemCollection =  shoppingCart.getLineItemMap().values();
    	List<LineItem> lineItem_list = new ArrayList<LineItem>(lineItemCollection);
		int totalItems= lineItemService.getTotalItemsInLineItemList(lineItem_list);
		request.getSession().setAttribute("noOfItems", totalItems);	
		
		model.addAttribute("customer",customer);
		model.addAttribute("order",order);
		model.addAttribute("lineItemList", lineItemList);
		navigationMap = new  LinkedHashMap<String, String>();
		navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"+storeSubDomain+"/customer/customer-home");
		navigationMap.put(OnionSquareConstants.ORDER, "/"+storeSubDomain+"/customer/customer-order");
		navigationMap.put(OnionSquareConstants.ORDER_DETAIL, "/"+storeSubDomain+"/customer/customer-orderDetail/orderId/"+order.getOrderId());
		model.addAttribute("navigationMap", navigationMap);
		saveMessage(request, "Payment is successfully completed");
		return "customer-orderDetail"; 
	}
	
	@RequestMapping(value="/{storeSubDomain}/customer/payFailure", method = RequestMethod.GET)
	public String showOrderList(HttpServletRequest request, ModelMap model,@PathVariable final String storeSubDomain) {
		Store store ; 
		 Customer customer= new Customer();
		 try{ 
			  customer = customerService.getCustomerByCustomerId(getCurrentUserId());
 
				  store = storeExist(storeSubDomain);
				 if(store==null)
						return "page-not-found";
			     ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();
				 model = mandatoryInStore(model,store,storeSubDomain);
				 
				 String orderId = request.getSession().getAttribute("orderId").toString();
				 Order order = orderService.getOrderByOrderId(Integer.parseInt(orderId));
				 orderService.removeOrderFromStore(order);
				 Address address =order.getShippingAddress();
				 addressService.removeShippingOrBillingAddress(address.getAddressId());
                 if(!order.getShippingAddress().getAddressId().equals(order.getBillingAddress().getAddressId()))
                	 addressService.removeShippingOrBillingAddress(order.getBillingAddress().getAddressId());
			     model.addAttribute("lineItemList", shoppingCart.getLineItemByStore(store.getStoreId()));
			     model.addAttribute("store", store);
			     model.addAttribute("customer", customer);
			     model.addAttribute("status", true);
			     model.addAttribute("grandTotal", shoppingCart.getShoppingCartTotalAmountByStore(store.getStoreId()));
			     model.addAttribute("noOfItems",request.getSession().getAttribute("noOfItems"));
				 
					     
		   }catch(Exception e){
             saveError(request, "Checkout page cannot be displayed due to internal error");
             model.addAttribute("customer", customer);   
             model.addAttribute("noOfItems",request.getSession().getAttribute("noOfItems"));
 			 model.addAttribute("readonly", true);

		     return "customer-checkout";
		 }	
		 

	 	navigationMap = new  LinkedHashMap<String, String>();
		navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"+storeSubDomain+"/customer/customer");
		navigationMap.put(OnionSquareConstants.VIEW_CART, "/"+storeSubDomain+"/viewcart");
		navigationMap.put(OnionSquareConstants.BILLING_SHIPPING_DETAIL,"/"+storeSubDomain+"/customer/customer-home");
		navigationMap.put(OnionSquareConstants.PROCEED_TO_CHECKOUT, "/"+storeSubDomain+"/customer/customer-checkout");
		model.addAttribute("readonly", true);
		model.addAttribute("navigationMap", navigationMap);		
	     return "customer-checkout";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{storeSubDomain}/customer/customer-order")
	public String viewCustomerOrder(ModelMap model, HttpServletRequest request,@PathVariable final String storeSubDomain) {
		List<Order> orderList = new ArrayList<Order>();
		try {			
				 Store store = storeExist(storeSubDomain);
				 if(store==null)
						return "page-not-found";
				 
				 model = mandatoryInStore(model,store,storeSubDomain);
				
				orderList = orderService.listOrdersByCustomerId(getCurrentUserId());
				if(orderList.size()==0){
					saveMessage(request, "Order list is empty");
				}
				
			} catch (Exception e) {
				saveError(request, "Order list cannot be  retrieved due to internal error.");
			}
			
		    model.addAttribute("orderList", orderList);
			navigationMap = new  LinkedHashMap<String, String>();
			navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
			navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"+storeSubDomain+"/customer/customer-home");
			navigationMap.put(OnionSquareConstants.ORDER, "/"+storeSubDomain+"/customer/customer-order");
			model.addAttribute("navigationMap", navigationMap);
	
			return "customer-order";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{storeSubDomain}/customer/customer-orderDetail/orderId/{orderId}")
	public String viewCustomerOrderDetail(ModelMap model, HttpServletRequest request,@PathVariable final String storeSubDomain,@PathVariable final Integer orderId) {
		Order order = new Order();
		List<LineItem> lineItemList = new ArrayList<LineItem>();
		try {
			
			 Store store = storeExist(storeSubDomain);
			 if(store==null)
					return "page-not-found";
			 
			 model = mandatoryInStore(model,store,storeSubDomain);
				
				
				
				order = orderService.getOrderByOrderId(orderId);
			    lineItemList = lineItemService.listLineItemByOrderId(orderId);
				
			} catch (Exception e) {
				saveError(request, "Order list cannot be  retrieved due to internal error.");
			}
			
		    model.addAttribute("order", order);
		    model.addAttribute("lineItemList", lineItemList);
			navigationMap = new  LinkedHashMap<String, String>();
			navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
			navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"+storeSubDomain+"/customer/customer-home");
			navigationMap.put(OnionSquareConstants.ORDER, "/"+storeSubDomain+"/customer/customer-order");
			navigationMap.put(OnionSquareConstants.ORDER_DETAIL, "/"+storeSubDomain+"/customer/customer-orderDetail/orderId/"+orderId);
			model.addAttribute("navigationMap", navigationMap);
	
			return "customer-orderDetail";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{storeSubDomain}/customer/customer-inquiries")
	public String inquiriesToAdmin(ModelMap model, HttpServletRequest request ,@PathVariable final String storeSubDomain) {
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return "page-not-found";
		 
		 model = mandatoryInStore(model,store,storeSubDomain);	
		 
		 Customer customer = customerService.getCustomerByCustomerId(getCurrentUserId());
		 List<Inquiry> inquiryList = new ArrayList<Inquiry>();
		 inquiryList = inquiryService.findParentInquiryBySenderOrReceiver(customer.getCustomerId(), OnionSquareConstants.USER_TYPE_CUSTOMER, true);
			                       
	    if(inquiryList.size()==0){
	    	saveMessage(request, "Customer Inquiry list is empty.");
	    }
		model.addAttribute("inquiryList",inquiryList);
		navigationMap = new  LinkedHashMap<String, String>();
		navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"+storeSubDomain+"/customer/customer-home");
		navigationMap.put(OnionSquareConstants.MESSAGES, "/"+storeSubDomain+"/customer/customer-inquiries");
		model.addAttribute("navigationMap", navigationMap);
		
		return "customer-inquiries";
		
	}
	

	@RequestMapping(method = RequestMethod.GET, value = "/{storeSubDomain}/customer/customer-inquiry-admin")
	public String inquiryToAdmin(ModelMap model, HttpServletRequest request ,@PathVariable final String storeSubDomain) {
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return "page-not-found";
		 
		 model = mandatoryInStore(model,store,storeSubDomain);
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
		navigationMap = new  LinkedHashMap<String, String>();
		navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		if(isCustomerLoggedIn())
			navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"+storeSubDomain+"/customer/customer-home");
		navigationMap.put(OnionSquareConstants.MESSAGES, "/"+storeSubDomain+"/customer/customer-inquiries");
		navigationMap.put(OnionSquareConstants.NEW_MESSAGE, "/"+storeSubDomain+"/customer/customer-inquiry-admin");

		model.addAttribute("navigationMap", navigationMap);
		 return "customer-inquiry";
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{storeSubDomain}/customer/customer-inquiry-seller")
	public String inquiryToSeller(ModelMap model, HttpServletRequest request ,@PathVariable final String storeSubDomain) {
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return "page-not-found";		 
		 model = mandatoryInStore(model,store,storeSubDomain);
		 Inquiry inquiry = new Inquiry();
		 Inquiry parentInquiry = new Inquiry();
		 parentInquiry.setInqueryId(0);
		 inquiry.setReceiverEmail(store.getSeller().getUsername());
		 inquiry.setReceiverName(store.getSeller().getFullName());
		 inquiry.setReceiverUserId(store.getSeller().getSellerId());
		 inquiry.setReceiverUserType(OnionSquareConstants.USER_TYPE_SELLER);
		 inquiry.setParentInquiry(parentInquiry);
		 model.addAttribute("inquiry",inquiry);
		navigationMap = new  LinkedHashMap<String, String>();
		navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		if(isCustomerLoggedIn())
			navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"+storeSubDomain+"/customer/customer-home");
		navigationMap.put(OnionSquareConstants.MESSAGES, "/"+storeSubDomain+"/customer/customer-inquiries");
		navigationMap.put(OnionSquareConstants.NEW_MESSAGE, "/"+storeSubDomain+"/customer/customer-inquiry-admin");

		model.addAttribute("navigationMap", navigationMap);
		 return "customer-inquiry";
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{storeSubDomain}/customer/send-customer-inquiry")
	public String sendInquiryToAdmin(@ModelAttribute Inquiry inquiry,ModelMap model, HttpServletRequest request ,@PathVariable final String storeSubDomain ) {
        try{
	       	 Store store = storeExist(storeSubDomain);
			 if(store==null)
					return "page-not-found";			 
			 model = mandatoryInStore(model,store,storeSubDomain);
			  Customer customer = customerService.getCustomerByCustomerId(getCurrentUserId());
			  inquiry.setSenderEmail(customer.getEmail());
			  inquiry.setSenderName(customer.getFullName());
			  inquiry.setSenderUserType(OnionSquareConstants.USER_TYPE_CUSTOMER);
			  inquiry.setSenderUserId(customer.getCustomerId());
			  inquiry.setPostedDate(new Date());
			  inquiry.setInquiryStatus(true);
		      inquiryService.saveInquiry(inquiry);
			  mailService.sendInquiryToStore(inquiry.getSenderEmail(),inquiry.getReceiverEmail() ,inquiry.getInquiryContent());

             inquiry = new Inquiry();
             saveMessage(request, "Your message is send successfully ");
			
        	
        }catch(Exception e){
        	
        	saveError(request,"Message cannot be send to the admin due to internal error");
        }
		
		model.addAttribute("inquiry",inquiry);
		return "customer-inquiry";
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{storeSubDomain}/customer/customer-view-inquiries")
	public String sellerAdminInquiries(ModelMap model, HttpServletRequest request ,@PathVariable final String storeSubDomain) {	
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return "page-not-found";
		 
		 model = mandatoryInStore(model,store,storeSubDomain);
		
		
		String inquiry_Id = request.getParameter("inquiryId");
		if(inquiry_Id!=null){
			int inquiryId = Integer.parseInt(inquiry_Id);
			model = senderReceiverConversation(model, inquiryId, OnionSquareConstants.USER_TYPE_CUSTOMER);
		}

		navigationMap = new  LinkedHashMap<String, String>();
		navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"+storeSubDomain+"/customer/customer-home");
		navigationMap.put(OnionSquareConstants.MESSAGES, "/"+storeSubDomain+"/customer/customer-inquiries");
		if(request.getParameter("inquiryId")!=null){
		navigationMap.put(OnionSquareConstants.MESSAGE_DETAIL, "/"+storeSubDomain+"/customer/customer-view-inquiries.jsp?inquiryId="+inquiry_Id);
		}

		model.addAttribute("navigationMap", navigationMap);
		return "customer-view-inquiries";
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{storeSubDomain}/customer/send-reply")
	public String sendSellerMessageFromAdmin(ModelMap model, HttpServletRequest request, @ModelAttribute Inquiry inquiry,@PathVariable final String storeSubDomain) {
		try{			
			 Customer customer = customerService.getCustomerByCustomerId(getCurrentUserId());
			  inquiry.setSenderEmail(customer.getEmail());
			  inquiry.setSenderName(customer.getFullName());
			  inquiry.setSenderUserId(customer.getCustomerId());
			  inquiry.setSenderUserType(OnionSquareConstants.USER_TYPE_CUSTOMER);
			  inquiry.setPostedDate(new Date());
			  inquiry.setInquiryStatus(true);
		      inquiryService.saveInquiry(inquiry);
			  mailService.sendInquiryToStore(inquiry.getSenderEmail(),inquiry.getReceiverEmail() ,inquiry.getInquiryContent());

		}catch(Exception e){
			saveError(request, "Message cannot be send due to internal error");
		}
	    return "redirect:/"+storeSubDomain+"/customer/customer-view-inquiries?inquiryId="+inquiry.getInqueryId();

	}
	
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/{storeSubDomain}/customer/customer-view-products")
	public String viewProducts(ModelMap model, HttpServletRequest request ,@PathVariable final String storeSubDomain) {
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return "page-not-found";
		 
		 model = mandatoryInStore(model,store,storeSubDomain);		
		 List<Product> productList =customerTrackerService.getViewProductsByCustomerId(getCurrentUserId());		
		 model.addAttribute("productList",productList);
		navigationMap = new  LinkedHashMap<String, String>();
		navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		if(isCustomerLoggedIn())
			navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"+storeSubDomain+"/customer/customer-home");
		navigationMap.put(OnionSquareConstants.VIEWED_PRODUCTS, "/"+storeSubDomain+"/customer/customer-view-products");
		model.addAttribute("navigationMap", navigationMap);
		 
		 return "store-home";
		
	}
	
	

	
	
	@RequestMapping(method = RequestMethod.GET, value = "/stateList")
	public @ResponseBody Map<String,String> stateList( ModelMap model ,@RequestParam Integer countryId ) {
		
		 List<State> stateList = getStateByCountryId(countryId);
		 Map<String,String> stateMap =new LinkedHashMap<String, String>();
		 
		 for(State state:stateList){
			 stateMap.put(String.valueOf(state.getStateId()), state.getStateName());
		 }
		 
		 return stateMap;
		 
	}
	
	 @RequestMapping(value = "/{storeSubDomain}/onionsquare-work")
	 public String onionsquareWork(ModelMap model, @PathVariable final String storeSubDomain){
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return "page-not-found";
		 
		 model = mandatoryInStore(model,store,storeSubDomain);
		 return "customer-onionsquare-work";
	 }
		
	 @RequestMapping(value = "/{storeSubDomain}/onionsquare-terms-of-use")
	 public String OnionsquareTermsOfUse(ModelMap model, @PathVariable final String storeSubDomain){
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return "page-not-found";
		 
		 model = mandatoryInStore(model,store,storeSubDomain);
		 return "customer-onionsquare-terms-of-use";
	 }
	
	 @RequestMapping(value = "/{storeSubDomain}/onionsquare-privacy-policy")
	 public String OnionsquarePrivacyPolicy(ModelMap model, @PathVariable final String storeSubDomain){
		Store store = storeExist(storeSubDomain);
		if(store==null)
				return "page-not-found";
		 
		model = mandatoryInStore(model,store,storeSubDomain);
		return "customer-onionsquare-privacy-policy";
	 }

	 /**
 	  * ankur: Generate new 5 digit alphanumeric password, update it into database and send it via email to user.
	  *  
	  * @param request
	  * @param model
	  * @param redirectPage
	  * @return
	  */
	 @RequestMapping(value={"/{storeSubDomain}/get-customer-forgot-password"}, method = RequestMethod.POST)
	 public String customerForgotPassword(HttpServletRequest request, ModelMap model,@PathVariable final String storeSubDomain) {
		 Store store = storeExist(storeSubDomain);
			if(store==null)
					return "page-not-found";
		 
		 String newPassword = RandomStringUtils.randomAlphanumeric(5);		 
		 String userName = StringUtils.defaultString((String)request.getParameter("username"));
		 
		 if(userName.length() > 0){
			 Customer customer = customerService.getCustomerByUserName(userName);
			 if(customer != null){
				 encoder = new Md5PasswordEncoder();
				 customer.setPassword(encoder.encodePassword(newPassword, null));
				 customerService.updateCustomer(customer);
				 
				 try {
					mailService.sendForgotPasswordEmail(customer.getEmail(), newPassword);
					saveMessage(request, "New password has been sent to "+customer.getEmail());
				 } catch(Exception ex) {
					ex.printStackTrace();
					saveError(request, "Email cannot be send due to internal error.");
				 }
			 }else
				 saveError(request, "Entered username is not registered with OnionSquare");
			 
		 } else {
			 saveError(request, "Please enter username.");
		 }
		 
		 return "customer-forgot-password";
	 }

}
