package com.onionsquare.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.onionsquare.core.model.Address;
import com.onionsquare.core.model.Customer;
import com.onionsquare.core.model.LineItem;
import com.onionsquare.core.model.Order;
import com.onionsquare.core.model.Store;
import com.onionsquare.core.service.AddressService;
import com.onionsquare.core.service.CategoryService;
import com.onionsquare.core.service.CustomerService;
import com.onionsquare.core.service.LineItemService;
import com.onionsquare.core.service.StoreService;
import com.onionsquare.core.util.OnionSquareConstants;
import com.onionsquare.web.model.ShoppingCart;
import com.onionsquare.web.service.ShoppingCartService;

@Controller
public class ShoppingCartController  extends AbstractController{


	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private CustomerService customerService;
	
	
	@Autowired
	private LineItemService lineItemService;
	
	
	
	@Autowired
	private StoreService storeService;

	@RequestMapping(value="/{storeSubDomain}/viewcart", method = RequestMethod.GET)
	public String viewCart(HttpServletRequest request, ModelMap model,@PathVariable final String storeSubDomain) {
		
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return "page-not-found";
		 
		 model = mandatoryInStore(model,store,storeSubDomain);
		 ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();	 
		 List<List<LineItem>> lineItemListByStore = new ArrayList<List<LineItem>>();
		 List<Double> grandTotal = new ArrayList<Double>();
		 List<Store> storeList = new ArrayList<Store>();
		 for(LineItem lineItem1:shoppingCart.getLineItemMap().values() ){
			   Store lineItem_store= lineItem1.getProduct().getStore();
			   int store_position =-1;
			   for(Store store1:storeList){
				   if(store1.getSubDomainName().equals(lineItem_store.getSubDomainName()))
					   store_position = storeList.indexOf(store1);
			   }
			   if(store_position ==-1)
				   storeList.add(lineItem_store);
			  if( lineItemListByStore.size()<(store_position+1) || store_position==-1)
			  {
				  List<LineItem> lineItemByStore = new ArrayList<LineItem>();
				  lineItemListByStore.add(lineItemByStore);
				  store_position = lineItemListByStore.size()-1;
				  grandTotal.add(lineItem1.getSubTotal());
			  }else
			  {
				  Double prev_total =grandTotal.get(store_position);
				  grandTotal.set(store_position, prev_total+lineItem1.getSubTotal());

			  }
			  
			  lineItemListByStore.get(store_position).add(lineItem1);
			  
		
		 }
	
		
		 model.addAttribute("lineItemListByStore", lineItemListByStore);
		 model.addAttribute("grandTotal", grandTotal);		
		 model.addAttribute("noOfItems",request.getSession().getAttribute("noOfItems"));
		 navigationMap = new  LinkedHashMap<String, String>();
		 navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
//		 navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"+storeSubDomain+"/customer/customer-home");
		 navigationMap.put(OnionSquareConstants.VIEW_CART, "/"+storeSubDomain+"/viewcart");
		 model.addAttribute("navigationMap", navigationMap);
		
		
		 return"customer-viewcart";	
		
	}

	@RequestMapping(value="/{storeSubDomain}/additem",method = RequestMethod.GET)
	public  String addItem(@RequestParam(value = "productId") Integer productId, HttpServletRequest request,@PathVariable final String storeSubDomain) {
		
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return "page-not-found";
		 
		// Add item in shoppingCart.	
		shoppingCartService.addItemToCart(productId, 1);
		ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();
		
		Collection<LineItem> lineItemCollection =  shoppingCart.getLineItemMap().values();
		List lineItemList = new ArrayList<LineItem>(lineItemCollection);	
		int totalItems= lineItemService.getTotalItemsInLineItemList(lineItemList);
		request.getSession().setAttribute("noOfItems", totalItems);	
		return "redirect:/"+store.getSubDomainName()+"/viewcart";
	}

	@RequestMapping(value="/{storeSubDomain}/removeitem/productId/{productId}" ,method= RequestMethod.GET)
	public String removeCartItem(@PathVariable final Integer productId, ModelMap model,HttpServletRequest request,@PathVariable final String storeSubDomain) {
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return "page-not-found";
		 
		 model = mandatoryInStore(model,store,storeSubDomain);
			
		shoppingCartService.removeItemFromCart(productId);
		 ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();	 
		 List<List<LineItem>> lineItemListByStore = new ArrayList<List<LineItem>>();
		 List<Double> grandTotal = new ArrayList<Double>();
		 List<Store> storeList = new ArrayList<Store>();
		 for(LineItem lineItem1:shoppingCart.getLineItemMap().values() ){
			   Store lineItem_store= lineItem1.getProduct().getStore();
			   int store_position =-1;
			   for(Store store1:storeList){
				   if(store1.getSubDomainName().equals(lineItem_store.getSubDomainName()))
					   store_position = storeList.indexOf(store1);
			   }
			   if(store_position ==-1)
				   storeList.add(lineItem_store);
			  if( lineItemListByStore.size()<(store_position+1) || store_position==-1)
			  {
				  List<LineItem> lineItemByStore = new ArrayList<LineItem>();
				  lineItemListByStore.add(lineItemByStore);
				  store_position = lineItemListByStore.size()-1;
				  grandTotal.add(lineItem1.getSubTotal());
			  }else
			  {
				  Double prev_total =grandTotal.get(store_position);
				  grandTotal.set(store_position, prev_total+lineItem1.getSubTotal());

			  }
			  
			  lineItemListByStore.get(store_position).add(lineItem1);
			  
		
		 }
	
		Collection<LineItem> lineItemCollection =  shoppingCart.getLineItemMap().values();
		List lineItemList = new ArrayList<LineItem>(lineItemCollection);	
		int totalItems= lineItemService.getTotalItemsInLineItemList(lineItemList);
		
		request.getSession().setAttribute("noOfItems", totalItems);	
		 model.addAttribute("lineItemListByStore", lineItemListByStore);
		 model.addAttribute("grandTotal", grandTotal);		
		 model.addAttribute("noOfItems",request.getSession().getAttribute("noOfItems"));
		 navigationMap = new  LinkedHashMap<String, String>();
		 navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		 navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"+storeSubDomain+"/customer/customer-home");
		 navigationMap.put(OnionSquareConstants.VIEW_CART, "/"+storeSubDomain+"/viewcart");
		 model.addAttribute("navigationMap", navigationMap);
		
		
		 return"customer-viewcart";	 
	}
	@RequestMapping("/{storeSubDomain}/update-cart/productId/{productId}/quantity/{quantity}")
	public String updateCartItem(@PathVariable final Integer productId,@PathVariable final Integer quantity, ModelMap model, HttpServletRequest request,@PathVariable final String storeSubDomain) {
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return "page-not-found";
		 
		 model = mandatoryInStore(model,store,storeSubDomain);
	
		
		shoppingCartService.updateProductQuantityInCart(productId, quantity);
		
		 ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();	 
		 List<List<LineItem>> lineItemListByStore = new ArrayList<List<LineItem>>();
		 List<Double> grandTotal = new ArrayList<Double>();
		 List<Store> storeList = new ArrayList<Store>();
		 for(LineItem lineItem1:shoppingCart.getLineItemMap().values() ){
			   Store lineItem_store= lineItem1.getProduct().getStore();
			   int store_position =-1;
			   for(Store store1:storeList){
				   if(store1.getSubDomainName().equals(lineItem_store.getSubDomainName()))
					   store_position = storeList.indexOf(store1);
			   }
			   if(store_position ==-1)
				   storeList.add(lineItem_store);
			  if( lineItemListByStore.size()<(store_position+1) || store_position==-1)
			  {
				  List<LineItem> lineItemByStore = new ArrayList<LineItem>();
				  lineItemListByStore.add(lineItemByStore);
				  store_position = lineItemListByStore.size()-1;
				  grandTotal.add(lineItem1.getSubTotal());
			  }else
			  {
				  Double prev_total =grandTotal.get(store_position);
				  grandTotal.set(store_position, prev_total+lineItem1.getSubTotal());

			  }
			  
			  lineItemListByStore.get(store_position).add(lineItem1);
			  
		
		 }
	    
		Collection<LineItem> lineItemCollection =  shoppingCart.getLineItemMap().values();
		List lineItemList = new ArrayList<LineItem>(lineItemCollection);	
		int totalItems= lineItemService.getTotalItemsInLineItemList(lineItemList);
		
		request.getSession().setAttribute("noOfItems", totalItems);	
		
		 model.addAttribute("lineItemListByStore", lineItemListByStore);
		 model.addAttribute("grandTotal", grandTotal);		
		 model.addAttribute("noOfItems",request.getSession().getAttribute("noOfItems"));
		 navigationMap = new  LinkedHashMap<String, String>();
		 navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		 navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"+storeSubDomain+"/customer/customer-home");
		 navigationMap.put(OnionSquareConstants.VIEW_CART, "/"+storeSubDomain+"/viewcart");
		 model.addAttribute("navigationMap", navigationMap);
		
		
		 return"customer-viewcart";	
	}
	
	@RequestMapping(value="/{storeSubDomain}/proceedToCheckout/store/{storeId}", method = RequestMethod.GET)
	public String checkCustomerStatus(HttpServletRequest request, Model model,@PathVariable final String storeSubDomain,@PathVariable final int storeId) {	
		 Store store = storeService.getStoreById(storeId);
		 if(store==null)
				return "page-not-found";
		 
	    
		if(isCustomerLoggedIn()){
			return "redirect:/"+store.getSubDomainName()+"/customer/customer-home";
		}else{
			return "redirect:/"+store.getSubDomainName()+"/customer-login";
		}

		
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/{storeSubDomain}/customer/edit-customerAddress")
	public String editCustomerAddress(@ModelAttribute("customer") Customer customer,HttpServletRequest request, ModelMap model,@PathVariable final String storeSubDomain){
		 Store store ; 
		 try{ 
				  store = storeExist(storeSubDomain);
				  model = mandatoryInStore(model,store,storeSubDomain);
	        	  Customer updateCustomer=customerService.getCustomerByCustomerId(customer.getCustomerId());
	              Address shippingAddress= customer.getShippingAddress();
				  if(store==null)
						return "page-not-found";						               
				      
                  if(!updateCustomer.getBillingAddress().equal(shippingAddress)){
			        request.getSession().setAttribute("shippingAddress", shippingAddress);
                 }
				  
			     
		   }catch(Exception e){
             saveError(request, "Shipping address cannot be edited due to internal error.");
             model.addAttribute("customer", customer);   
             model.addAttribute("countryList", getCountryList());
			if (customer.getShippingAddress() != null) {
				if (customer.getShippingAddress().getCountry() != null)
					model.addAttribute("shippingStateList",
							getStateByCountryId(customer.getShippingAddress()
									.getCountry().getCountryId()));

			}
             model.addAttribute("noOfItems",request.getSession().getAttribute("noOfItems"));
		     return "customer-checkout";
		 }	
		 
	     ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();
	    
	     model.addAttribute("store", store);
	     model.addAttribute("customer", customer);	   
	     model.addAttribute("noOfItems",request.getSession().getAttribute("noOfItems"));
	     model.addAttribute("lineItemList", shoppingCart.getLineItemByStore(store.getStoreId()));
	     model.addAttribute("status", true);
	     model.addAttribute("grandTotal", shoppingCart.getShoppingCartTotalAmountByStore(store.getStoreId()));
	 	
	     navigationMap = new  LinkedHashMap<String, String>();	 	
		 navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		 navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"+storeSubDomain+"/customer/customer");
		 navigationMap.put(OnionSquareConstants.VIEW_CART, "/"+storeSubDomain+"/viewcart");
		 navigationMap.put(OnionSquareConstants.BILLING_SHIPPING_DETAIL,"/"+storeSubDomain+"/customer/customer-home");
		 if(!request.getParameter("action").equals("edit")){
		    navigationMap.put(OnionSquareConstants.PROCEED_TO_CHECKOUT, "/"+storeSubDomain+"/customer/customer-checkout");
		 }

		model.addAttribute("navigationMap", navigationMap);		
	     return "customer-checkout";
		 
		 }
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/{storeSubDomain}/customer/customer-checkout")
	public String customerCheckOut( HttpServletRequest request, ModelMap model,@PathVariable final String storeSubDomain){
		 Store store ;
		 Customer customer = new Customer();
		 try{ 
			      customer = customerService.getCustomerByCustomerId(getCurrentUserId());

				  store = storeExist(storeSubDomain);
				 if(store==null)
						return "page-not-found";
			     ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();
				 model = mandatoryInStore(model,store,storeSubDomain);
  
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
		     return "customer-checkout";
		 }	
		 

	 	navigationMap = new  LinkedHashMap<String, String>();
		navigationMap.put(OnionSquareConstants.HOME, "/"+storeSubDomain);
		navigationMap.put(OnionSquareConstants.CUSTOMER_HOME, "/"+storeSubDomain+"/customer/customer");
		navigationMap.put(OnionSquareConstants.VIEW_CART, "/"+storeSubDomain+"/viewcart");
		navigationMap.put(OnionSquareConstants.BILLING_SHIPPING_DETAIL,"/"+storeSubDomain+"/customer/customer-home");
		navigationMap.put(OnionSquareConstants.PROCEED_TO_CHECKOUT, "/"+storeSubDomain+"/customer/customer-checkout");

		model.addAttribute("navigationMap", navigationMap);		
	     return "customer-checkout";
		 
		 }
	
	 

	
}
