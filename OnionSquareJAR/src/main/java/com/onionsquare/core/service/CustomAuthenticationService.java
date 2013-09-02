package com.onionsquare.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.onionsquare.core.dao.AdminDao;
import com.onionsquare.core.dao.CustomerDao;
import com.onionsquare.core.dao.SellerDao;
import com.onionsquare.core.model.Admin;
import com.onionsquare.core.model.Customer;
import com.onionsquare.core.model.Seller;
import com.onionsquare.core.model.User;
import com.onionsquare.core.util.OnionSquareConstants;

@Service
public class CustomAuthenticationService implements UserDetailsService {

	String userRole;
	UserDetails user;

	@Autowired
	private SellerDao sellerDao;

	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private CustomerDao customerDao;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		List<GrantedAuthority> grandAuthority = new ArrayList<GrantedAuthority>();

		if (userRole.equalsIgnoreCase(OnionSquareConstants.ADMIN_TYPE)) {

			Admin admin = adminDao.findByUserName(username);

			if (admin != null) {
				if(admin.getRoleName().equals(OnionSquareConstants.ROLE_ADMIN))
					grandAuthority.add(new SimpleGrantedAuthority(OnionSquareConstants.ROLE_ADMIN));
				else
				grandAuthority.add(new SimpleGrantedAuthority(OnionSquareConstants.ROLE_MANAGER));
				
				user = new User(admin.getId(), admin.getUsername(),
						admin.getPassword(), true, true, true, true,
						grandAuthority);
			} else {
				throw new UsernameNotFoundException(
						"Cannot find admin with username: " + username);
			}
		} else if (userRole.equalsIgnoreCase(OnionSquareConstants.SELLER_TYPE)) {

			Seller seller = sellerDao.findSellerByUserName(username);
			if (seller != null) {
				
				grandAuthority.add(new SimpleGrantedAuthority(
						OnionSquareConstants.ROLE_SELLER));
				if(seller.getStatus()){
				user = new User(seller.getSellerId(), seller.getUsername(),
						seller.getPassword(), true, true, true, true,
						grandAuthority);
				}else{
					user = new User(seller.getSellerId(), seller.getUsername(),
							seller.getPassword(), false, true, true, true,
							grandAuthority);
				}
			} else {
				throw new UsernameNotFoundException(
						"Cannot find seller with username:" + username);
			}
		}else if (userRole.equalsIgnoreCase(OnionSquareConstants.CUSTOMER_TYPE)) {

			Customer customer = customerDao.findByUserName(username);
			if (customer != null) {
				
				grandAuthority.add(new SimpleGrantedAuthority(
						OnionSquareConstants.ROLE_CUSTOMER));
				if(customer.isStatus()){
				user = new User(customer.getCustomerId(), customer.getUserName(),
						customer.getPassword(), true, true, true, true,
						grandAuthority);
				}else{
					user = new User(customer.getCustomerId(), customer.getUserName(),
							customer.getUserName(), false, true, true, true,
							grandAuthority);
				}
			} else {
				throw new UsernameNotFoundException(
						"Cannot find customer with username:" + username);
			}
		}

		return user;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

}
