package com.onionsquare.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.Line;

import org.springframework.aop.aspectj.AspectJAdviceParameterNameDiscoverer.AmbiguousBindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.onionsquare.core.model.Address;
import com.onionsquare.core.model.Admin;
import com.onionsquare.core.model.Customer;
import com.onionsquare.core.model.LineItem;
import com.onionsquare.core.model.Order;
import com.onionsquare.core.model.OrderStatus;
import com.onionsquare.core.model.Seller;
import com.onionsquare.core.model.Store;
import com.onionsquare.core.service.AddressService;
import com.onionsquare.core.service.AdminService;
import com.onionsquare.core.service.CustomerService;
import com.onionsquare.core.service.LineItemService;
import com.onionsquare.core.service.OrderService;
import com.onionsquare.core.util.OnionSquareConstants;
import com.onionsquare.web.model.ShoppingCart;
import com.onionsquare.web.service.ShoppingCartService;
import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;
import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.FundingConstraint;
import com.paypal.svcs.types.ap.FundingTypeInfo;
import com.paypal.svcs.types.ap.FundingTypeList;
import com.paypal.svcs.types.ap.InvoiceData;
import com.paypal.svcs.types.ap.InvoiceItem;
import com.paypal.svcs.types.ap.PayRequest;
import com.paypal.svcs.types.ap.PayResponse;
import com.paypal.svcs.types.ap.PaymentDetailsRequest;
import com.paypal.svcs.types.ap.PaymentInfo;
import com.paypal.svcs.types.ap.Receiver;
import com.paypal.svcs.types.ap.ReceiverIdentifier;
import com.paypal.svcs.types.ap.ReceiverList;
import com.paypal.svcs.types.ap.ReceiverOptions;
import com.paypal.svcs.types.ap.SenderIdentifier;
import com.paypal.svcs.types.ap.SenderOptions;
import com.paypal.svcs.types.ap.SetPaymentOptionsRequest;
import com.paypal.svcs.types.common.ClientDetailsType;
import com.paypal.svcs.types.common.ErrorData;
import com.paypal.svcs.types.common.RequestEnvelope;

@Controller
public class AdaptivePaymentController extends AbstractController {

	@Autowired
	private ShoppingCartService shoppingCartService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private LineItemService lineItemService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private AddressService addressService;

	private DecimalFormat decimalFormat = new DecimalFormat("#.##");
	private Admin admin;
    /**
     * customer click the payment button , it will redirect to the paypal after storing the order items.
     * @param request
     * @param model
     * @param storeSubDomain
     * @return
     */
	@RequestMapping(method = RequestMethod.GET, value = "/{storeSubDomain}/customer/payment")
	public @ResponseBody
	Map<String, String> payment(HttpServletRequest request, Model model,
			@PathVariable final String storeSubDomain) {
		
		Map<String, String> paymentMap = new LinkedHashMap<String, String>();
		Address shippingAddress = (Address) request.getSession().getAttribute(
				"shippingAddress");
		
		Order order = new Order();

		try {
			//get the store in which payment is to be done
			Store store = storeExist(storeSubDomain);
			
			Customer customer = customerService
					.getCustomerByCustomerId(getCurrentUserId());

			if (store == null) {
				
				paymentMap.put("page", "page-not-found");
				return paymentMap;

			}
			try {
				
				admin = new Admin();
				admin = adminService.getAdminByRoleName("ROLE_ADMIN").get(0);
				
			} catch (Exception e) {
				paymentMap
						.put("error",
								"Admin data cannot be retrieved due to internal error.");
				return paymentMap;

			}
			
			if (admin.getPaypalEmail()=="" || admin.getPaypalEmail() == null
					|| admin.getPaypalUsername()=="" || admin.getPaypalUsername() == null
					|| admin.getPaypalPassword()=="" || admin.getPaypalPassword() == null
					|| admin.getPaypalSignature()=="" || admin.getPaypalSignature() == null
					|| admin.getPaypalAppId()=="" || admin.getPaypalAppId() == null) {
				
					paymentMap.put("error", "Admin has not added paypal information");				
					return paymentMap;

			}
			
			Map<String,String> paypalMap = new HashMap<String,String>();
			paypalMap.put("acct1.UserName", admin.getPaypalUsername());
			paypalMap.put("acct1.Password", admin.getPaypalPassword());
			paypalMap.put("acct1.Signature", admin.getPaypalSignature());
			paypalMap.put("acct1.AppId", admin.getPaypalAppId());
			
			if(admin.getPaypalMode().equalsIgnoreCase("LIVE")){
				
				paypalMap.put("service.RedirectURL", "https://www.paypal.com/webscr&cmd=");
				paypalMap.put("mode", "live");
				
			}else{
				
				paypalMap.put("service.RedirectURL", "https://www.sandbox.paypal.com/webscr&cmd=");
				paypalMap.put("mode", "sandbox");
				
			}
			
			paypalMap.put("service.DevCentralURL", "https://developer.paypal.com");

			AdaptivePaymentsService adaptivePaymentService = new AdaptivePaymentsService(
					paypalMap);

			RequestEnvelope env = new RequestEnvelope();
			env.setErrorLanguage("en_US");
			
			ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();

			List<Receiver> receiver = new ArrayList<Receiver>();
			
			//primary receiver is the store owner
			Receiver rec = new Receiver();
			
			//total amount to be paid to the store owner
			Double storeTotalAmount = shoppingCart
					.getShoppingCartTotalAmountByStore(store.getStoreId());
			
			rec.setAmount(storeTotalAmount);
			rec.setEmail(store.getPaypalEmail());
			rec.setPrimary(true);
			receiver.add(rec);
			
			Double profit_gained = store.getSeller().getInvitationProfit();
			
			// calculate commission for each order to onionsquare
			Double commissionToOnionSquare = 0.08 * storeTotalAmount;
			if (profit_gained != null)
				if (profit_gained > 0) {
					commissionToOnionSquare = commissionToOnionSquare
							- profit_gained;
					if (commissionToOnionSquare < 2)
						commissionToOnionSquare = 2.0;
				}

			order.setProfitToOnionsquare(commissionToOnionSquare);
			order.setTotalAmount(storeTotalAmount);
			
			rec = new Receiver();
			rec.setAmount(commissionToOnionSquare);
			rec.setEmail(admin.getPaypalEmail());
			rec.setPrimary(false);
			
			receiver.add(rec);
			
			ReceiverList receiverlst = new ReceiverList(receiver);
			PayRequest payRequest = new PayRequest();
			payRequest.setReceiverList(receiverlst);
			payRequest.setRequestEnvelope(env);
			payRequest.setFeesPayer("PRIMARYRECEIVER");

			ClientDetailsType clientDetails = new ClientDetailsType();
			clientDetails.setCustomerId(String.valueOf(getCurrentUserId()));
			payRequest.setClientDetails(clientDetails);

			SenderIdentifier senderIdentifier = new SenderIdentifier();
			senderIdentifier.setEmail(customer.getPaypal_email());
			payRequest.setSender(senderIdentifier);
			payRequest.setActionType("CREATE");
			payRequest.setCancelUrl(OnionSquareConstants.BASE_URL
					+ storeSubDomain + "/customer/payFailure");
			payRequest.setCurrencyCode("USD");
			payRequest.setReturnUrl(OnionSquareConstants.BASE_URL
					+ storeSubDomain + "/customer/paySuccess");

			PayResponse resp = adaptivePaymentService.pay(payRequest);
			if (resp != null) {

				if (resp.getResponseEnvelope().getAck().toString()
						.equalsIgnoreCase("SUCCESS")) {

					ReceiverOptions receiverOptions = new ReceiverOptions();
					Collection<LineItem> lineItemCollection = shoppingCart.getLineItemByStore(store.getStoreId());				
					List<LineItem> lineItemList = new ArrayList<LineItem>(
							lineItemCollection);
					InvoiceItem item;
					List<InvoiceItem> invoiceItem = new ArrayList<InvoiceItem>();

					for (LineItem lineItem : lineItemList) {
						if (lineItem.getProduct().getStore().getStoreId()
								.equals(store.getStoreId())) {
							item = new InvoiceItem();
							item.setName(lineItem.getProduct().getProductName());
							item.setPrice(lineItem.getSubTotal());
							item.setItemCount(lineItem.getQuantity());
							item.setItemPrice(lineItem.getProduct()
									.getUnitPrice());
							invoiceItem.add(item);
						}
						lineItem.setLineItemId(null);

					}
					
					OrderStatus orderStatus = new OrderStatus();
					orderStatus.setStatusName("InComplete");
					orderStatus.setOrderStatusId(3);
					order.setCreatedDate(new Date());
					order.setPaymentMethod(1);
					order.setOrderStatus(orderStatus);
					order.setLineItemList(lineItemList);
					order.setNumberOfItems(lineItemService
							.getTotalItemsInLineItemList(lineItemList));
					
					order.setStore(store);
					if (shippingAddress == null) {
						
						shippingAddress = customer.getBillingAddress();
						shippingAddress.setAddressId(null);
						addressService
								.saveShippingOrBillingAddress(shippingAddress);						
						order.setShippingAddress(shippingAddress);
						order.setBillingAddress(shippingAddress);
					} else if (shippingAddress.getAddressId() == null) {
						shippingAddress = customer.getBillingAddress();
						shippingAddress.setAddressId(null);
						addressService
								.saveShippingOrBillingAddress(shippingAddress);
						order.setShippingAddress(shippingAddress);
						order.setBillingAddress(shippingAddress);
					} else {
						shippingAddress.setAddressId(null);
						addressService
								.saveShippingOrBillingAddress(shippingAddress);
						Address billingAddress = customer.getBillingAddress();
						billingAddress.setAddressId(null);
						addressService
								.saveShippingOrBillingAddress(billingAddress);
						order.setShippingAddress(shippingAddress);
						order.setBillingAddress(billingAddress);

					}
					order.setCorrelation_id(resp.getResponseEnvelope()
							.getCorrelationId());
					order.setTime_stamp(resp.getResponseEnvelope()
							.getTimestamp());
					order.setCustomer(customer);
					//create order and save it to the database and set the orderId in session
					if(order.getOrderId()==null)
					  orderService.createOrderForStore(order);
					request.getSession().setAttribute("orderId",
							order.getOrderId());

					InvoiceData invoiceData = new InvoiceData();
					invoiceData.setItem(invoiceItem);

					ReceiverIdentifier receiverIdentifier = new ReceiverIdentifier();
					receiverIdentifier.setEmail(store.getPaypalEmail());
					receiverOptions.setInvoiceData(invoiceData);

					receiverOptions.setReceiver(receiverIdentifier);

					List<ReceiverOptions> receiverOptionList = new ArrayList<ReceiverOptions>();
					receiverOptionList.add(receiverOptions);

					SetPaymentOptionsRequest paymentRequest = new SetPaymentOptionsRequest();
					paymentRequest.setReceiverOptions(receiverOptionList);
					paymentRequest.setPayKey(resp.getPayKey());
					paymentRequest.setRequestEnvelope(env);
					adaptivePaymentService.setPaymentOptions(paymentRequest);

					request.getSession().setAttribute("payKey",
							resp.getPayKey());
					model.addAttribute("payKey", resp.getPayKey());
					paymentMap.put("payKey", resp.getPayKey());

				} else {

					List<ErrorData> errorList = resp.getError();
					String error = "";
					for (ErrorData errorData : errorList) {
						if (!error.equals(""))
							error = error + ". " + errorData.getMessage();
						else
							error = errorData.getMessage();
					}

					paymentMap.put("error", error);
				}

			}
		} catch (SSLConfigurationException e) {
			saveError(request, e.getMessage());
		} catch (InvalidCredentialException e) {
			saveError(request, e.getMessage());

		} catch (UnsupportedEncodingException e) {
			saveError(request, e.getMessage());
		} catch (HttpErrorException e) {
			saveError(request, e.getMessage());

		} catch (InvalidResponseDataException e) {
			saveError(request, e.getMessage());

		} catch (ClientActionRequiredException e) {
			saveError(request, e.getMessage());

		} catch (MissingCredentialException e) {
			saveError(request, e.getMessage());

		} catch (OAuthException e) {
			saveError(request, e.getMessage());

		} catch (IOException e) {
			saveError(request, e.getMessage());

		} catch (InterruptedException e) {
			saveError(request, e.getMessage());

		}
		return paymentMap;
	}
}