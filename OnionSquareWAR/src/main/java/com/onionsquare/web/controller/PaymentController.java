package com.onionsquare.web.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

//import com.onionsquare.core.model.Address;
//import com.onionsquare.core.model.Category;
//import com.onionsquare.core.model.Customer;
//import com.onionsquare.core.model.Store;
//import com.onionsquare.core.service.CustomerService;
//import com.onionsquare.core.service.StoreService;
//import com.onionsquare.web.model.ShoppingCart;
//import com.onionsquare.web.service.ShoppingCartService;
//import com.paypal.api.payments.Amount;
//import com.paypal.api.payments.AmountDetails;
//import com.paypal.api.payments.Item;
//import com.paypal.api.payments.ItemList;
//import com.paypal.api.payments.Link;
//import com.paypal.api.payments.Payer;
//import com.paypal.api.payments.Payment;
//import com.paypal.api.payments.RedirectUrls;
//import com.paypal.api.payments.ShippingAddress;
//import com.paypal.api.payments.Transaction;
//import com.paypal.core.rest.APIContext;
//import com.paypal.core.rest.OAuthTokenCredential;
//import com.paypal.core.rest.PayPalRESTException;
//import com.paypal.core.rest.PayPalResource;

@Controller
public class PaymentController {
	
//	@Autowired
//	private ShoppingCartService shoppingCartService;
//	
//	@Autowired
//	private CustomerService customerService;
//	
//	@Autowired
//	private StoreService storeService;
//	
//	private APIContext apiContext = null;
//	private String accessToken = null;
//	Map<String, String> map = new HashMap<String, String>();
//
//	
//	@RequestMapping(method = RequestMethod.GET, value = "/{storeSubDomain}/customer/payment")
//	public @ResponseBody String payment(HttpServletRequest request, Model model,@PathVariable final String storeSubDomain){
//		
//		try{
//			
//			InputStream is = PaymentController.class.getClassLoader().getResourceAsStream("sdk_config.properties");
//			try {
//				PayPalResource.initConfig(is);
//			} catch (PayPalRESTException e) {
//			}
//			Customer customer = customerService.getCustomerByCustomerId(getCurrentUserId());
//			Store store = storeService.getStoreBySubDomainName(storeSubDomain);
//			if(store!=null){
//				accessToken =new OAuthTokenCredential(store.getPayPalUserName(), store.getPayPalPassword()).getAccessToken();
//				apiContext = new APIContext(accessToken);
//						
//				ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();
//				AmountDetails amountDetails = new AmountDetails();
//				amountDetails.setShipping("1");
//				amountDetails.setSubtotal("10");
//				amountDetails.setTax("1");
//				
//				
//				Amount amount = new Amount();
//				amount.setCurrency("USD");
//				amount.setDetails(amountDetails);
//				amount.setTotal("12");
//				
//				Item item = new Item();
//				item.setName("Dress");
//				item.setPrice("2");
//				item.setQuantity("5");
//				item.setSku("2");
//				item.setCurrency("USD");
//				
//				List<Item> itemList = new ArrayList<Item>();
//				itemList.add(item);
//				
//				ItemList itemList1 = new ItemList();
//				itemList1.setItems(itemList);
//				ShippingAddress billingAddress = new ShippingAddress();
//				billingAddress.setLine1("52 N Main ST");
//				billingAddress.setCity("Johnstown");
//				billingAddress.setCountryCode("US");
//				billingAddress.setPostalCode("43210");
//				billingAddress.setState("OH");
//				billingAddress.setRecipientName("kapila");
//				itemList1.setShippingAddress(billingAddress);
//				Transaction transaction = new Transaction();
//				transaction.setAmount(amount);
//				transaction.setItemList(itemList1);
//				
//				
//				List<Transaction> transactions = new ArrayList<Transaction>();
//				transactions.add(transaction);
//
//			
//				Payer payer = new Payer();
//				payer.setPaymentMethod("paypal");
//
//				Payment payment = new Payment();
//				payment.setIntent("sale");
//				payment.setPayer(payer);
//				payment.setTransactions(transactions);
//				
//				// ###Redirect URLs
//				RedirectUrls redirectUrls = new RedirectUrls();
//				String guid = UUID.randomUUID().toString().replaceAll("-", "");
//				redirectUrls.setCancelUrl(request.getScheme() + "://"
//						+ request.getServerName() + ":" + request.getServerPort()
//						+ request.getContextPath() + "/roshan/customer/payFailure?guid=" + guid);
//				redirectUrls.setReturnUrl(request.getScheme() + "://"
//						+ request.getServerName() + ":" + request.getServerPort()
//						+ request.getContextPath() + "/roshan/customer/paySuccess?guid=" + guid);
//				payment.setRedirectUrls(redirectUrls);
//				
//				try {
//					Payment createdPayment = payment.create(apiContext);
//					
//					Iterator<Link> links = createdPayment.getLinks().iterator();
//					while (links.hasNext()) {
//						Link link = links.next();
//						if (link.getRel().equalsIgnoreCase("approval_url")) {
//							request.setAttribute("redirectURL", link.getHref());
//						}
//					}
//					request.setAttribute("response", Payment.getLastResponse());
//					map.put("6ab5a7b1a60d4ad982034f79fb477bb6", createdPayment.getId());
//				} catch (PayPalRESTException e) {
//					request.setAttribute("error", e.getMessage());
//				}
//				
//			}
//		}
//		 catch(PayPalRESTException e){
//			 saveError(request, e.getMessage());
//		 } 
//			
//		 catch(Exception e){
//                saveError(request, "Customer cannot be saved due to internal error.");
//		    	return (String)request.getAttribute("redirectURL");
//
// 		 }
//	
//	     
//	     return (String)request.getAttribute("redirectURL");	
//		
//	}

}
