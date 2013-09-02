package com.onionsquare.core.dao;

import java.util.List;

import com.onionsquare.core.model.Admin;

public interface AdminDao extends GenericDAO<Admin, Integer> {	
	
	Admin findByUserName(String userName);
	/**
	 * Return Admin object with the role name passed as parameter
	 * 
	 * @param roleName: String representation of role name of the admin user.
	 * @return
	 */
	Admin findAdminByRole(String roleName) ;
	/**
	 * Return List of Admin objects with the specified user name and role name passed as parameters.
	 * @param userName: user name of the user
	 * @param roleName: role name of the user
	 */
	List<Admin> findAdminList(String userName, String roleName);
	/**
	 * Return an Admin Object by its username and role passed as parameter. If it exists return the Admin object otherwise return null
	 * @param userName
	 * @param roleName
	 * @return
	 */
	Admin findAdminByUserNameAndRole(String userName, String roleName);
	
	List<Admin> findAllAdminByRole(String roleName);
	
	List<Admin> getAdminListExcludingLoginAdmin(int adminId);
}
