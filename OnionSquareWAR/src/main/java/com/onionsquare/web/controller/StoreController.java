package com.onionsquare.web.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.onionsquare.core.model.Admin;
import com.onionsquare.core.model.Category;
import com.onionsquare.core.model.Customer;
import com.onionsquare.core.model.CustomerTracker;
import com.onionsquare.core.model.Inquiry;
import com.onionsquare.core.model.LineItem;
import com.onionsquare.core.model.Order;
import com.onionsquare.core.model.OrderStatus;
import com.onionsquare.core.model.Product;
import com.onionsquare.core.model.Seller;
import com.onionsquare.core.model.SellerNetwork;
import com.onionsquare.core.model.Store;
import com.onionsquare.core.service.AdminService;
import com.onionsquare.core.service.CategoryService;
import com.onionsquare.core.service.CustomerService;
import com.onionsquare.core.service.CustomerTrackerService;
import com.onionsquare.core.service.InquiryService;
import com.onionsquare.core.service.LineItemService;
import com.onionsquare.core.service.MailService;
import com.onionsquare.core.service.OrderService;
import com.onionsquare.core.service.ProductService;
import com.onionsquare.core.service.SellerNetworkService;
import com.onionsquare.core.service.SellerService;
import com.onionsquare.core.service.StoreService;
import com.onionsquare.core.util.OnionSquareConstants;
/**
 * 
 * this class deals with store created by seller
 *
 */
@Controller
public class StoreController extends AbstractController {

	@Autowired
	private StoreService storeService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private LineItemService lineItemService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private SellerNetworkService sellerNetworkService;
	
	@Autowired
	private CustomerTrackerService customerTrackerService;
	
	@Autowired
	private CustomerService customerService;
	
   @Autowired
   private  InquiryService inquiryService;
   
   @Autowired
   private  AdminService adminService;
   
   @Autowired
   private  SellerService sellerService;
	
	

	private List<Category> categoryList;
	private List<Product> productList;
	private Product product;
	private Category category;
	private Store store;

	private Map<String,String> navigationMap;

	public static final Logger LOGGER = Logger.getLogger(StoreController.class
			.getName());

	public StoreController() {
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{storeSubDomain}")
	public String displayStore(@ModelAttribute("seller") Store store,HttpServletRequest request, ModelMap model,@PathVariable final String storeSubDomain){
	
		try{
			List<SellerNetwork> sellerNetworkList;
			store= storeService.getStoreBySubDomainName(storeSubDomain);
			int productsSize=0;
			if(store!=null)
			{
				categoryList=categoryService.listAllCategoriesByStoreId(store.getStoreId());
				sellerNetworkList = sellerNetworkService.listAllStoresInSellerNetwork(store.getSeller().getSellerId(),true);
				if(categoryList.size()<=0 ){
					productList = new ArrayList<Product>();
				}else{		
					
					  productsSize= productService.getProductsByStoreId(store.getStoreId(), true, null).size();
					  productList= productService.getProductListByPageNoAndPageSize(0, 4, store.getStoreId(), 0);
				}					
				request.getSession().setAttribute("storeId", store.getStoreId());
				model.addAttribute("productList", productList);
				model.addAttribute("productListSize", productsSize);

				model.addAttribute("store",store);
				mandatoryInStore(model,store,storeSubDomain);
				
			}else
			{
				if(!(storeSubDomain.equals("/seller-form") || storeSubDomain.equals("/seller-login")))
					saveError(request, "Store doesnot exist");
			    return "redirect:/" ;
			 }
		}catch(Exception e){
			saveError(request, "Store cannot be displayed due to internal error");
		    return "redirect:/" ;

		}
		navigationMap = new  HashMap<String, String>();
		navigationMap.put("HOME", "/"+storeSubDomain);
		
		model.addAttribute("navigationMap", navigationMap);
		model.addAttribute("storeSubDomain", storeSubDomain);
		
		return "store-home";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{storeSubDomain}/topSeller/{categoryId}")
	public String topSeller(@ModelAttribute("seller") Store store,HttpServletRequest request, ModelMap model,@PathVariable final String storeSubDomain,@PathVariable Integer categoryId){
		String categoryName ="";
		try{
			store= storeService.getStoreBySubDomainName(storeSubDomain);
			int productsSize =0;
			if(store!=null)
			{
				categoryList=categoryService.listAllCategoriesByStoreId(store.getStoreId());
				if(categoryList.size()<=0 ){
					productList = new ArrayList<Product>();
				}else{		
					  productsSize= productService.getProductsByStoreId(store.getStoreId(), true, categoryId).size();
					  productList= productService.getTopSellsProductsByStoreId(store.getStoreId(),true, categoryId,0,4);
				}					
				request.getSession().setAttribute("storeId", store.getStoreId());
				model.addAttribute("productList", productList);
				model.addAttribute("productListSize", productsSize);
				model.addAttribute("store",store);
				if(categoryId>0){
					categoryName = categoryService.getCategoryById(categoryId).getCategoryName();
					model.addAttribute("categoryName", categoryName);
				}	
				mandatoryInStore(model,store,storeSubDomain);
				
			}else
			{
				if(!(storeSubDomain.equals("/seller-form") || storeSubDomain.equals("/seller-login")))
					saveError(request, "Store doesnot exist");
			    return "redirect:/" ;
			 }
		}catch(Exception e){
			saveError(request, "Store cannot be displayed due to internal error");
		    return "redirect:/" ;

		}
		
		navigationMap = new  HashMap<String, String>();		
		navigationMap.put("HOME", "/"+storeSubDomain);
		if(categoryId>0)
			navigationMap.put(categoryName, "/"+storeSubDomain+"/category/"+categoryId);
		

		model.addAttribute("navigationMap", navigationMap);
		model.addAttribute("storeSubDomain", storeSubDomain);
		model.addAttribute("topSeller", "1");

		return "store-home";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{storeSubDomain}/category/{categoryId}")
	public String displayStoreCategory(@ModelAttribute("seller") Store store,HttpServletRequest request, ModelMap model,@PathVariable final String storeSubDomain,@PathVariable final int categoryId){
	    String categoryName ="";
		try{
			store= storeService.getStoreBySubDomainName(storeSubDomain);
			int productsSize =0;
			if(store!=null)
			{
				categoryList=categoryService.listAllCategoriesByStoreId(store.getStoreId());
				if(categoryList.size()<=0 ){
					productList = new ArrayList<Product>();
				}else{
					
					if(categoryId>0){
						  productList= productService.getProductListByPageNoAndPageSize(0, 4, store.getStoreId(), categoryId);
						  productsSize= productService.getProductsByStoreId(store.getStoreId(), true, categoryId).size();
					     categoryName = categoryService.getCategoryById(categoryId).getCategoryName();
						model.addAttribute("categoryName", categoryName);
					}else
					{
					  productsSize= productService.getProductsByStoreId(store.getStoreId(), true, null).size();
					  productList= productService.getProductListByPageNoAndPageSize(0, 4, store.getStoreId(), 0);
					}

				}					
					request.getSession().setAttribute("storeId", store.getStoreId());
					model.addAttribute("productList", productList);
					model.addAttribute("productListSize", productsSize);
					model.addAttribute("categoryId", categoryId);

					model.addAttribute("store",store);	
					mandatoryInStore( model,  store ,  storeSubDomain);
				
			}else
			{
				if(!(storeSubDomain.equals("/seller-form") || storeSubDomain.equals("/seller-login")))
					saveError(request, "Store doesnot exist");
			    return "redirect:/" ;
			 }
		}catch(Exception e){
			saveError(request, "Store cannot be displayed due to internal error");
		    return "redirect:/" ;

		}
		navigationMap = new  HashMap<String, String>();
		navigationMap.put("HOME", "/"+storeSubDomain);
		navigationMap.put(categoryName, "/"+storeSubDomain+"/category/"+categoryId);
		
		model.addAttribute("navigationMap", navigationMap);
		
		return "store-home";
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/seller/store-home")
	public String storeHome(ModelMap model, HttpServletRequest request) {
        
		if (store== null) {
			return isStoreAvailable(model);
		}
		categoryList = categoryService.listAllCategoriesByStoreId(store
				.getStoreId());

		model.addAttribute("categoryList", categoryList);
		model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
		model.addAttribute("sellerName",request.getSession().getAttribute("sellerName"));		
	  

		return "store-home";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/store")
	public String stores(ModelMap model, HttpServletRequest request) {
      
		List<Store>  storeList = new ArrayList<Store>();
		Map<String, List<Product>> productsList =  new HashMap<String, List<Product>>();
		storeList = storeService.getAllStores();
		for(Store store:storeList){
			productList=productService.getProductsByStoreId(store.getStoreId(), true, null);
			if(productList == null)
				productList= new ArrayList<Product>();
			productsList.put(store.getSubDomainName(), productList);			
		}
		
		model.addAttribute("productsList", productsList);
		model.addAttribute("storeList", storeList);
				

		return "stores";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/seller/edit-store")
	public String editStoreView(ModelMap model, HttpServletRequest request) {
		Store store= getStore(request);
		if (store == null) {
			return isStoreAvailable(model);
		}
		
		String sellerName =(String)request.getSession().getAttribute("sellerName");
		
		if(store.getCountry()!=null){
			    model.addAttribute("stateList", getStateByCountryId(store.getCountry().getCountryId()));

		}
		
		model.addAttribute("store", store);
		model.addAttribute("mode","edit");		
		model.addAttribute("sellerName",sellerName);
		model.addAttribute("countryList", getCountryList());
		return "store-form";
	}
	
	

	@RequestMapping(method = RequestMethod.GET, value = "/seller/store-product")
	public String viewProduct(ModelMap model, HttpServletRequest request) {

		try {
			if (getStore(request) == null) {
				return isStoreAvailable(model);
			}

			getReferenceForProduct(store.getStoreId());
			product = new Product();
			product.setStore(store);

		} catch (Exception e) {
			saveError(request, "Caught exception in product page");
		}
		

		model.addAttribute("categoryList", categoryList);
		model.addAttribute("Product", product);
		model.addAttribute("productList", productList);
		model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
	   model.addAttribute("sellerName",request.getSession().getAttribute("sellerName"));
		return "store-product";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/seller/edit-product")
	public String editProduct(ModelMap model, HttpServletRequest request) {
		try {

			if (getStore(request) == null) {
				return isStoreAvailable(model);
			}
			if(request.getParameter("productId")!=null){
				int productId = Integer.parseInt(request.getParameter("productId"));
				product = productService.getProductById(productId);
			}
		

			if (product != null) {

				model.addAttribute("mode", "edit");
			} else {
				product = new Product();
				saveError(request, "Product is null");
			}
			

			categoryList = categoryService.listAllCategoriesByStoreId(store.getStoreId());
			productList = productService.getProductsByStoreId(store.getStoreId(), true, null);

		} catch (Exception e) {
			saveError(request, "Caught exception when retrieving product");
		}
	    String sellerName =(String)request.getSession().getAttribute("sellerName");

	    model.addAttribute("categoryList", categoryList);
		model.addAttribute("Product", product);
		model.addAttribute("productList", productList);
		model.addAttribute("sellerName",sellerName);
		model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
		return "store-product";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/seller/delete-product")
	public String deleteProduct(ModelMap model, HttpServletRequest request) {
		try {
			if (getStore(request) == null) {
				return isStoreAvailable(model);
			}
            
			if(request.getParameter("productId")!=null){
				 int productId = Integer.parseInt(request.getParameter("productId"));
			     product = productService.getProductById(productId);
			}
			
			if (product != null) {
				String filePath =StringUtils.substringBeforeLast(product.getImageName(), "/");
				productService.removeProduct(product);
				 filePath = OnionSquareConstants.UPLOAD_URL+filePath;
				File  productImagePath= new File(filePath);
				if(productImagePath.exists())
					productImagePath.delete();
				
				saveError(request, "Product " + product.getProductName()
						+ "is deleted successfully");

			} else
				saveError(request, "Product is null");
			

			product = new Product();
			getReferenceForProduct(store.getStoreId());

		} catch (Exception e) {
			saveError(request, "Caught exception in product page");
		}
		String sellerName =(String)request.getSession().getAttribute("sellerName");
	    model.addAttribute("sellerName",sellerName);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("Product", product);
		model.addAttribute("productList", productList);
		model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
		return "store-product";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/seller/product")
	public String addProduct(ModelMap model, HttpServletRequest request,
			@ModelAttribute("product") Product product) {
		
		try {
			if (getStore(request) == null) {
				return isStoreAvailable(model);
			}	

			if (request.getParameter("action").equals("save")) {
			
				 productService.addNewProductInStore(product);
			     String imagePath = product.getStore().getStoreId()+"/"+ product.getCategory().getCategoryId()+"/"+product.getProductId(); 
			     MultipartFile uploadFile = product.getProductImageFiles().get(0);
			     
			     if(uploadFile.getSize()>0){
						MultipartFile   filedata= (CommonsMultipartFile) uploadFile;
			
						if(filedata!=null){
							File folder = new File(OnionSquareConstants.UPLOAD_URL+imagePath);
						     if(!folder.exists())
									folder.mkdirs();
							String filePath = OnionSquareConstants.UPLOAD_URL+imagePath+"/"+product.getProductId()+"_"+0+".jpg";							
						    File destination = new File(filePath);					 
						    try {
						    	filedata.transferTo(destination);
						    }catch(Exception e){
						    	saveError(request, "Product Image cannot be uploaded");
						    }
						    
						}
					
				  }
				saveMessage(request, "Product has been added successfully.");

			} 
			
			String imageName =  product.getStore().getStoreId()+"/"+ product.getCategory().getCategoryId()+"/"+product.getProductId()+"/"+product.getProductId()+"_"+0+".jpg";
			product.setImageName(imageName);
			productService.updateProductInStore(product);
			
			 if (request.getParameter("action").equals("edit")){
			  
			    saveMessage(request, "Product has been edited successfully.");
			}   
			
			
		
		} catch (Exception e) {
			saveError(request,
					"Product cannot be saved due to internal error");
		}
		getReferenceForProduct(store.getStoreId());
		String sellerName =(String)request.getSession().getAttribute("sellerName");
	    model.addAttribute("sellerName",sellerName);
		model.addAttribute("categoryList", categoryList);
		product = new Product();
		product.setStore(store);
		model.addAttribute("Product", product);
		model.addAttribute("productList", productList);
		model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
		return "store-product";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/seller/store-product-images")
	public String showProductImages (ModelMap model, HttpServletRequest request){
		String message = request.getParameter("message");
		if(request.getParameter("productId")!=null){
			
			int productId =Integer.parseInt(request.getParameter("productId"));
			 product = productService.getProductById(productId);			
			if(product!=null)
				product =getProductImages(product);
		}
		
		if(message!=null){
			if(message.equals("1"))
				saveError(request, "Cannot upload more than "+(OnionSquareConstants.MAX_PRODUCT_IMAGE_UPLOAD+1)+" images.");
			else 
				saveError(request, "Product image cannot be uploaded due to internal error.");

		}
	

		String sellerName =(String)request.getSession().getAttribute("sellerName");
	    model.addAttribute("sellerName",sellerName);		
		model.addAttribute("product", product);
	    model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
		return "store-product-images";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/seller/edit-product-images")
	public String addProductImages (ModelMap model, HttpServletRequest request,@ModelAttribute("product") Product product){
		
		int count =0;
		try{
			String imagePath = product.getStore().getStoreId()+"/"+ product.getCategory().getCategoryId()+"/"+product.getProductId();
			File folder = new File(OnionSquareConstants.UPLOAD_URL+imagePath);
			
			if(!folder.exists()){
				folder.mkdirs();
			}else if(folder.listFiles().length>0){
				File[] files = folder.listFiles();
				if(files.length>OnionSquareConstants.MAX_PRODUCT_IMAGE_UPLOAD)
					return "redirect:/seller/store-product-images?productId="+product.getProductId()+"&message="+1;
				File lastModifiedFile = files[0];
				for (int i = 1; i < files.length; i++) {
				   if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				       lastModifiedFile = files[i];
				   }
				}
				
				String lastModifiedFileName = lastModifiedFile.getName();
				String image = StringUtils.substringAfter(lastModifiedFileName, "_");
			    count = Integer.parseInt(StringUtils.substringBefore(image, "."));
			    ++count;
			}		
			
			 for (MultipartFile uploadFile :product.getProductImageFiles())
			 {
					if(uploadFile.getSize()>0){
						MultipartFile   filedata= (CommonsMultipartFile) uploadFile;
			
						if(filedata!=null){
							String filePath = OnionSquareConstants.UPLOAD_URL+imagePath+"/"+product.getProductId()+"_"+count+".jpg";							
						    File destination = new File(filePath);					 
						    try {
						    	filedata.transferTo(destination);
						    }catch(Exception e){
								return "redirect:/seller/store-product-images?productId="+product.getProductId()+"&message="+2;

						    }
						    
						}
						count ++;
				  }
				 
			 }			
					
		}catch(Exception e){
			return "redirect:/seller/store-product-images?productId="+product.getProductId()+"&message="+2;
		}
		
			
		return "redirect:/seller/store-product-images?productId="+product.getProductId();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/seller/delete-product-image")
	public String deleteProductImages (ModelMap model, HttpServletRequest request){
		
		String imageName= request.getParameter("imageName");	
		String productId = request.getParameter("productId");
		if(productId!=null){
		File deleteImage = new File(OnionSquareConstants.UPLOAD_URL+imageName);
		
		if(deleteImage.delete())
			saveMessage(request, "Image  is deleted successfully");
		else
			saveError(request, "Image  cannot be deleted");
		}
	
	
			
		return "redirect:/seller/store-product-images?productId="+productId;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/seller/store-category")
	public String viewCategory(ModelMap model, HttpServletRequest request) {
		try {
			if (getStore(request) == null) {
				return isStoreAvailable(model);
			}
			getReferenceForCategory(store.getStoreId());

				category = new Category();
			

			category.setStore(store);
		} catch (Exception e) {
			saveError(request, "Caught exception in Category Page");
		}
		String sellerName =(String)request.getSession().getAttribute("sellerName");
	    model.addAttribute("sellerName",sellerName);
		model.addAttribute("category", category);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
		return "store-category";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/seller/edit-category")
	public String editCategory(ModelMap model, HttpServletRequest request) {
		try {
			if (getStore(request) == null) {
				return isStoreAvailable(model);
			}

           if(request.getParameter("categoryId")!=null){
        	   int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        	   category = categoryService.getCategoryById(categoryId);
				model.addAttribute("mode", "edit");
           }
				
			

			getReferenceForCategory(store.getStoreId());
			if (category == null) {
				category = new Category();
			}

			category.setStore(store);
		} catch (Exception e) {
			saveError(request, "Caught exception in Category Page");
		}
		String sellerName =(String)request.getSession().getAttribute("sellerName");
	    model.addAttribute("sellerName",sellerName);
		model.addAttribute("category", category);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
		return "store-category";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/seller/delete-category")
	public String deleteCategory(ModelMap model, HttpServletRequest request) {
		try {
			if (getStore(request) == null) {
				return isStoreAvailable(model);
			}

		     int categoryId=0;
			 if(request.getParameter("categoryId")!=null){
				 categoryId = Integer.parseInt(request.getParameter("categoryId"));
	        	   category = categoryService.getCategoryById(categoryId);
					model.addAttribute("mode", "edit");
	           }
			List<Product> productList= productService.getProductsByStoreId(store.getStoreId(),true, categoryId);

			if (productList.size()<=0) {
				categoryService.removeCategory(category);
				saveError(request,
						"Category '" + category.getCategoryName()
								+ "' has been deleted. ");

			} else
				saveError(request,	"Unable to delete Category '"+ category.getCategoryName()+ "' since it has products associated with it.");			

			getReferenceForCategory(store.getStoreId());
			category = new Category();
			category.setStore(store);
		} catch (Exception e) {
			saveError(request, "Caught exception in Category Page");
		}
		String sellerName =(String)request.getSession().getAttribute("sellerName");
	    model.addAttribute("sellerName",sellerName);
		model.addAttribute("category", category);
		model.addAttribute("categoryList", categoryList);
	    model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
		return "store-category";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/seller/category")
	public String addCategory(ModelMap model, HttpServletRequest request,
			@ModelAttribute("seller") Category category) {
		try {
			if (getStore(request) == null) {
				return isStoreAvailable(model);
			}
			category.setCategoryStatus(true);
			if (request.getParameter("action").equals("save")) {
				categoryService.addNewCategory(category);
				saveMessage(request, "Category has been added successfully.");

			} else if (request.getParameter("action").equals("edit")) {
				categoryService.updateCategory(category);
				saveMessage(request, "Category has been edited successfully.");

			}

		} catch (Exception e) {
			saveError(request, "Category cannot be saved due to internal error");
		}
		// storeId = category.getStore().getStoreId();
		getReferenceForCategory(store.getStoreId());
		category = new Category();
		category.setStore(store);
		String sellerName =(String)request.getSession().getAttribute("sellerName");
	    model.addAttribute("sellerName",sellerName);
		model.addAttribute("category", category);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));

		return "store-category";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/seller/view-products")
	public String viewAllProductsByStore(ModelMap model, HttpServletRequest request,
			@ModelAttribute("seller") Category category) {
		try {
				if (getStore(request) == null) {
					return isStoreAvailable(model);
				}
				categoryList = categoryService.listAllCategoriesByStoreId(getStore(request).getStoreId());
			   if(category== null)	 
				   productList= productService.getProductsByStoreId(getStore(request).getStoreId(), true, null);
			   else
					productList= productService.getProductsByStoreId(getStore(request).getStoreId(), true, category.getCategoryId());
	
			} catch (Exception e) {
				saveError(request, "Product cannot be retrieved due to internal error.");
			}
			
			if(productList== null)
				productList= new ArrayList<Product>();
		
			if(categoryList==null)
				categoryList = new ArrayList<Category>();
			if(category==null)
				category= new Category();
			String sellerName =(String)request.getSession().getAttribute("sellerName");
		    model.addAttribute("sellerName",sellerName);
			model.addAttribute("Category", category);
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("productList", productList);
			model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
	
			return "store-view";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/seller/store-order")
	public String viewStoreOrder(ModelMap model, HttpServletRequest request) {
		List<Order> orderList = new ArrayList<Order>();	
		Order order = new Order();
		try {
			
			  Store store = getStore(request);
				if ( store== null) {
					return isStoreAvailable(model);
				}			
				
			  	OrderStatus orderStatus = new OrderStatus();
				orderStatus.setOrderStatusId(2);
				order.setOrderStatus(orderStatus);
				order.setStore(store);
				orderList = orderService.findOrdersByFilterCriteria(order);
				if(orderList.size()==0){
					saveMessage(request, "Order list is empty");
				}
				
		} catch (Exception e) {
				saveError(request, "Order list cannot be  retrieved due to internal error.");
		}
		String sellerName =(String)request.getSession().getAttribute("sellerName");
		model.addAttribute("sellerName", sellerName);
		model.addAttribute("orderList", orderList);
		model.addAttribute("order", order);
		model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
	
		return "store-order";
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/seller/store-order")
	public String viewStoreOrderByCriteria(ModelMap model, HttpServletRequest request ,@ModelAttribute Order order) {
		List<Order> orderList = new ArrayList<Order>();
		SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy/MM/dd");
		try {
			  Store store = getStore(request);
				if ( store== null) {
					return isStoreAvailable(model);
				}
						
				order.setStore(store);
				orderList = orderService.findOrdersByFilterCriteria(order);
				if(orderList.size()==0){
					saveMessage(request, "Order list is empty");
				}
				order.setFromDate(dateFormat.parse(dateFormat.format(order.getFromDate())));
				order.setToDate(dateFormat.parse(dateFormat.format(order.getToDate())));

				
		} catch (Exception e) {
				saveError(request, "Order list cannot be  retrieved due to internal error.");
		}
		String sellerName =(String)request.getSession().getAttribute("sellerName");
		model.addAttribute("sellerName", sellerName);
		model.addAttribute("orderList", orderList);
		model.addAttribute("order", order);
		model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
	
		return "store-order";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/seller/store-current-order")
	public String viewStoreCurrentOrder(ModelMap model, HttpServletRequest request) {
		List<Order> orderList = new ArrayList<Order>();	
		Order order = new Order();
		try {
			  Store store = getStore(request);
				if ( store== null) {
					return isStoreAvailable(model);
				}		
				OrderStatus orderStatus = new OrderStatus();
				orderStatus.setOrderStatusId(1);
				order.setOrderStatus(orderStatus);
				order.setStore(store);
				orderList = orderService.findOrdersByFilterCriteria(order);
				if(orderList.size()==0){
					saveMessage(request, "Order list is empty");
				}
				
		} catch (Exception e) {
				saveError(request, "Order list cannot be  retrieved due to internal error.");
		}
		String sellerName =(String)request.getSession().getAttribute("sellerName");
		model.addAttribute("sellerName", sellerName);
		model.addAttribute("orderList", orderList);
		model.addAttribute("order", order);
	    model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
		return "store-current-order";
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/seller/store-current-order")
	public String viewStoreCurrentOrderByCriteria(ModelMap model, HttpServletRequest request ,@ModelAttribute Order order) {
		List<Order> orderList = new ArrayList<Order>();
		try {
			  Store store = getStore(request);
				if ( store== null) {
					return isStoreAvailable(model);
				}
				
			   	OrderStatus orderStatus = new OrderStatus();
				orderStatus.setOrderStatusId(1);
				order.setOrderStatus(orderStatus);
				order.setStore(store);
				orderList = orderService.findOrdersByFilterCriteria(order);
				if(orderList.size()==0){
					saveMessage(request, "Order list is empty");
				}
				
		} catch (Exception e) {
				saveError(request, "Order list cannot be  retrieved due to internal error.");
		}
		String sellerName =(String)request.getSession().getAttribute("sellerName");
		model.addAttribute("sellerName", sellerName);
		model.addAttribute("orderList", orderList);
		model.addAttribute("order", order);
		model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
	
		return "store-current-order";
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/seller/change-order-status")
	public String changeOrderStatus(ModelMap model, HttpServletRequest request ) {	
		Order order = new Order();
		try {
			  Store store = getStore(request);
				if ( store== null) {
					return isStoreAvailable(model);
				}
				
			 String statusId = request.getParameter("statusId");
			 String orderId = request.getParameter("orderId");
			 OrderStatus orderStatus = new OrderStatus();
			 orderStatus.setOrderStatusId(Integer.parseInt(statusId));
			 order = orderService.getOrderByOrderId(Integer.parseInt(orderId));
			 order.setOrderStatus(orderStatus);
			 order.setModifiedDate(new Date());
			 orderService.updateOrder(order);
		     saveMessage(request, "Order status is updated successfully");
				
		} catch (Exception e) {
				saveError(request, "Order list cannot be  retrieved due to internal error.");
		}
	    model.addAttribute("order", order);
	    model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
	 	model.addAttribute("sellerName",request.getSession().getAttribute("sellerName"));
		return "store-current-order";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/seller/store-orderDetail")
	public String viewStoreOrderDetail(ModelMap model, HttpServletRequest request) {
		Order order = new Order();
		List<LineItem> lineItemList = new ArrayList<LineItem>();
		try {
				if (getStore(request) == null) {
					return isStoreAvailable(model);
				}
				
				if(request.getParameter("order").equals("current_order"))
					model.addAttribute("order_type", "current_order");
				else
					model.addAttribute("order_type", "order_history");
				
				if(request.getParameter("orderId")!=null)
				{
					int orderId = Integer.parseInt(request.getParameter("orderId"));
					order = orderService.getOrderByOrderId(orderId);
				    lineItemList = lineItemService.listLineItemByOrderId(orderId);
				}else{
					saveError(request, "order Id is not found ");
				    return "redirect:/seller/store-order";
				}
					
				
			} catch (Exception e) {
				saveError(request, "Order Detail cannot be  retrieved due to internal error.");
				return "redirect:/seller/store-order";
			}
			String sellerName =(String)request.getSession().getAttribute("sellerName");
		    model.addAttribute("sellerName",sellerName);
		    model.addAttribute("order", order);
		    model.addAttribute("lineItemList", lineItemList);
		    model.addAttribute("storeSubDomain",request.getSession().getAttribute("storeSubDomain"));
	
			return "store-orderDetail";
	}
	
	
	
	@RequestMapping(value="/seller/save-store", method = RequestMethod.POST)
	public String saveStore(@ModelAttribute("store") Store store,HttpServletRequest request, Model model) {
		
		 Store existingStore = storeService.getStoreBySubDomainName(store.getSubDomainName());
		 String sellerName =(String)request.getSession().getAttribute("sellerName");
		 model.addAttribute("sellerName",sellerName);

		 if (request.getParameter("action").equals("save")) {
			 
			 try{			
				   if(existingStore!=null && existingStore.getStoreId()>0)
				   {
					   saveError(request, "Subdomain already exist, Please enter another subdomain name.");
				
				   }else{
					   	store.setCreatedDate(new Date());
					    store.setStoreStatus(true);
				        storeService.createNewStore(store);
						saveMessage(request, "Store is created Successfully");
						model.addAttribute("mode", "edit");
				    }						
				}catch(Exception e){
						 saveError(request, "Store cannot be created due to internal error.");
				}
			    String imagePath = "seller";
			    if(store.getSellerPicture()!=null){
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
						    }catch(Exception e){
						    	saveError(request, "Seller Profile cannot be uploaded");
						    }
						}  
					}
					
				  }
		 }else if (request.getParameter("action").equals("edit")) {
			 try{
			 store.setModifiedDate(new Date());
			 store.setStoreStatus(true);
			 store.setCreatedDate(existingStore.getCreatedDate());
			 storeService.updateStoreProfile(store);
			 this.store =storeService.getStoreById(store.getStoreId());
			 saveMessage(request, "Store has been updated successfully.");
			 model.addAttribute("mode", "edit");
			 }catch(Exception e){
				 saveError(request, "Store cannot be updated due to internal error.");
			 	 model.addAttribute("mode", "edit");
				 
			 }
			 
		 }
		
		 if(store.getCountry()!=null){
		    model.addAttribute("stateList", getStateByCountryId(store.getCountry().getCountryId()));

		 }
		 String storeDomain = (String)request.getSession().getAttribute("storeSubDomain");
		 if(storeDomain==null)
			 storeDomain = store.getSubDomainName();
			 
			 
	 	model.addAttribute("countryList", getCountryList());
 		model.addAttribute("store",store);
 	    model.addAttribute("storeSubDomain",storeDomain);
		return "store-form"; 
	}
	@RequestMapping(method = RequestMethod.GET, value = "/{storeSubDomain}/product/{productId}")
	public String viewProductFromStore(ModelMap model, HttpServletRequest request ,@PathVariable String storeSubDomain,@PathVariable int productId) {		
		
		
		Store store = storeExist(storeSubDomain);
		
		 if(store==null)
				return "page-not-found";
		 
		 model = mandatoryInStore(model,store,storeSubDomain);
		if(productId>0){
			product = productService.getProductById(productId);
			if(product!=null){
				product = getProductImages(product);
				CustomerTracker customerTracker = new CustomerTracker();
				customerTracker.setProduct(product);
				int customerId = getCurrentUserId();
				if(customerId>0)
				{
					Customer customer = customerService.getCustomerByCustomerId(getCurrentUserId());
					customerTracker.setCustomer(customer);
				}
				customerTracker.setTrackedDate(new Date());
				customerTrackerService.updateCustomerTracker(customerTracker);
			}
			
		}else{
			saveError(request, "Product  cannot be found");
		}	
		navigationMap = new  LinkedHashMap<String, String>();
		navigationMap.put("HOME", "/"+storeSubDomain);
		navigationMap.put(product.getCategory().getCategoryName(), "/"+storeSubDomain+"/category/"+product.getCategory().getCategoryId());
		navigationMap.put(product.getProductName(), "/"+storeSubDomain+"/product/"+product.getProductId());
		model.addAttribute("navigationMap", navigationMap);
		model.addAttribute("storeSubDomain", storeSubDomain);	
        model.addAttribute("product", product);
        
		return "store-view-product";
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{storeSubDomain}/store-inquiry")
	public String inquiryToStore(ModelMap model, HttpServletRequest request ,@PathVariable String storeSubDomain) {
		 Store store = storeExist(storeSubDomain);
		 if(store==null)
				return "page-not-found";
		 
		 int currentUserId = getCurrentUserId();
		 
		 model = mandatoryInStore(model,store,storeSubDomain);
		 navigationMap = new  HashMap<String, String>();
		 navigationMap.put("HOME", "/"+storeSubDomain);
		 navigationMap.put("Inquiry", "/"+storeSubDomain+"/store-inquiry");
		 model.addAttribute("navigationMap", navigationMap);
		 Inquiry inquiry = new Inquiry();	
		 model.addAttribute("storeSubDomain", storeSubDomain);	

		 if(currentUserId>0){
			 if(store.getSeller().getSellerId()==currentUserId ){
				if(isSellerLoggedIn()){
				 saveError(request, "Message cannot be send to yourself");
			      model.addAttribute("mode", "button_disabled");
				}else{
					model.addAttribute("mode", "sender_email_disabled");
				}
			 }else{			 
				 model.addAttribute("mode", "sender_email_disabled");
			}
			 
		 }
		 model.addAttribute("inquiry",inquiry);

		return "store-inquiry";
		
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/{storeSubDomain}/send-store-inquiry")
	public String sendInquiryToStore(@ModelAttribute Inquiry inquiry,ModelMap model, HttpServletRequest request ,@PathVariable String storeSubDomain) {
        try{
        	
	       	 Store store = storeExist(storeSubDomain);
			 if(store==null)
					return "page-not-found";
			 
			 model = mandatoryInStore(model,store,storeSubDomain);
			 navigationMap = new  HashMap<String, String>();
			 navigationMap.put("HOME", "/"+storeSubDomain);
			 navigationMap.put("Inquiry", "/"+storeSubDomain+"/store-inquiry");
			 model.addAttribute("navigationMap", navigationMap);
		   Inquiry parentInquiry = new Inquiry();
		   parentInquiry.setInqueryId(0);
		   inquiry.setParentInquiry(parentInquiry);
		   inquiry.setReceiverName(store.getSeller().getFullName());
		   inquiry.setReceiverEmail(store.getSeller().getUsername());
		   inquiry.setReceiverUserId(store.getSeller().getSellerId());
		   inquiry.setReceiverUserType(OnionSquareConstants.USER_TYPE_SELLER);
		   inquiry.setInquiryStatus(true);
		   inquiry.setPostedDate(new Date());
		   int currentUserId = getCurrentUserId();
		   if(currentUserId>0 ){
			  inquiry.setSenderUserId(currentUserId);
			  if(getCurrentUserRole().equalsIgnoreCase(OnionSquareConstants.ROLE_CUSTOMER)){
				  Customer customer = customerService.getCustomerByCustomerId(currentUserId);
				  inquiry.setSenderEmail(customer.getEmail());
				  inquiry.setSenderName(customer.getFullName());
				  inquiry.setSenderUserType(OnionSquareConstants.USER_TYPE_CUSTOMER);
			  }
			  else if(getCurrentUserRole().equalsIgnoreCase(OnionSquareConstants.ROLE_ADMIN)){
				     Admin admin = adminService.getAdminUserById(currentUserId);
					 inquiry.setSenderEmail(admin.getAdminEmail());
					 inquiry.setSenderName(admin.getFullName());
				      inquiry.setSenderUserType(OnionSquareConstants.USER_TYPE_ADMIN);
			  } else if(getCurrentUserRole().equalsIgnoreCase(OnionSquareConstants.ROLE_MANAGER)){
				    Admin admin = adminService.getAdminUserById(currentUserId);
				    inquiry.setSenderEmail(admin.getAdminEmail());
				    inquiry.setSenderName(admin.getFullName());
				    inquiry.setSenderUserType(OnionSquareConstants.USER_TYPE_MANAGER);
			  }	else if(getCurrentUserRole().equalsIgnoreCase(OnionSquareConstants.ROLE_SELLER)){
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
		  
		   inquiryService.saveInquiry(inquiry);
		   mailService.sendInquiryToStore(inquiry.getSenderEmail(), store.getSeller().getUsername(),inquiry.getInquiryContent());
	       inquiry = new Inquiry();
	       saveMessage(request, "Your message is send successfully");
			
        	
        }catch(Exception e){
        	
        	saveError(request,"Message cannot be send to the store due to internal error");
        }
		
		model.addAttribute("inquiry",inquiry);
		return "store-inquiry";
		
	}
	
	
	
	
	private void getReferenceForProduct(int storeId) {
		if (categoryList == null)
			categoryList = categoryService.listAllCategoriesByStoreId(storeId);
		productList = productService.getProductsByStoreId(storeId, true, null);

	}

	private void getReferenceForCategory(int storeId) {

		categoryList = categoryService.listAllCategoriesByStoreId(storeId);

	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	private String isStoreAvailable(ModelMap model) {
		// if(store==null){
		store = new Store();
		Seller seller = new Seller();
		seller.setSellerId(getCurrentUserId());
		store.setSeller(seller);		
		model.addAttribute("store", store);
		model.addAttribute("countryList", getCountryList());
		return "store-form";
		// }
		// return null;
	}

	public Store getStore(HttpServletRequest request) {
		
		if(getCurrentUserRole().equalsIgnoreCase("ROLE_ADMIN") || getCurrentUserRole().equalsIgnoreCase("ROLE_MANAGER"))
		{   
			String sellerId = request.getSession().getAttribute("sellerId").toString();
			if(sellerId!=null )
				store = storeService.getStoreBySellerId(Integer.parseInt(sellerId));
		
		}else if (store != null) {
			if ( store.getStoreId()!=null && isSellerLoggedIn() && store.getSeller().getSellerId() == getCurrentUserId()) {
				
			}else{
				store =null;
				getStore(request);
			}
		} else {
			store = storeService.getStoreBySellerId(getCurrentUserId());
		}

		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
	public Product getProductImages(Product product){
		
		String imagePath = product.getStore().getStoreId()+"/"+ product.getCategory().getCategoryId()+"/"+product.getProductId();
		File folder = new File(OnionSquareConstants.UPLOAD_URL+imagePath);
		if(folder.exists()){
			File[] files = folder.listFiles();
			if(files!=null){
				List<String> productImagesName = new ArrayList<String>();
				for(File imageFiles:files){
					productImagesName.add(imagePath+"/"+imageFiles.getName());
				}
				product.setProductImagesName(productImagesName);
			}
		}
		return product;
	}
	
	
	 public ModelMap mandatoryToStore(ModelMap model ,Store store ){
		 List<Category>categoryList=categoryService.listAllCategoriesByStoreId(store.getStoreId());
		 List<SellerNetwork>sellerNetworkList = sellerNetworkService.listAllStoresInSellerNetwork(store.getSeller().getSellerId(),true);


		 model.addAttribute("sellerNetworkList", sellerNetworkList);
		 model.addAttribute("categoryList", categoryList);
         
		 return model;
		 
	 }
	 
	 @RequestMapping(method = RequestMethod.GET, value = "/seller/view-financial-stat")
	 public String viewFinancialStat(ModelMap model, HttpServletRequest request) {

			  Store store = getStore(request);
				if ( store== null) {
					return isStoreAvailable(model);
				}
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
					 
					 revenue_cost = orderService.calculateTotalRevenueForStore(store.getStoreId(),fromDate, toDate);
					 store.setRevenue(revenue_cost.get(0));
					 store.setOnionsquareProfit(revenue_cost.get(1));
					 Double profit = store.getRevenue()- store.getOnionsquareProfit();
					 store.setProfit(profit);
			         
				 
					
					
				} catch (Exception e) {
					saveError(request, "Order list cannot be  retrieved due to internal error.");
				}
			    
			    model.addAttribute("store", store);
			    model.addAttribute("fromDate", minDate);
			    model.addAttribute("toDate", maxDate);
			    model.addAttribute("selectedIndex", selectedIndex);
		
				return "seller-financial-stat";
		}
	 
	 @RequestMapping(method = RequestMethod.GET, value = "/products/{pageNo}/{pageSize}/{storeId}/{categoryId}")
		public @ResponseBody List<Product> productListByPageNoAndPageSize( ModelMap model,HttpServletRequest request ,@PathVariable Integer pageNo  ,@PathVariable Integer pageSize ,@PathVariable Integer storeId,@PathVariable Integer categoryId) {
			
			 List<Product> productList ;
			//neccessary fields of product is added to this object
			 String topSeller = request.getParameter("topSeller");
			 if(topSeller.equals("1"))
				  productList= productService.getTopSellsProductsByStoreId(storeId,true, categoryId,pageNo,pageSize);
			 else
				 productList = productService.getProductListByPageNoAndPageSize(pageNo, pageSize,storeId,categoryId);
			 List<Product> responseList =  new ArrayList<Product>();
			 for(Product product:productList){
				product.setDescription(product.getDescription());
				product.setCategory(null);
				product.setProductId(product.getProductId());
				product.setUnitPrice(product.getUnitPrice());
				product.setStore(null);
               responseList.add(product);				 
			 }
			 return productList;
			 
		}

}
