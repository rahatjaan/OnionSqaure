package com.onionsquare.core.service;

import java.util.List;

public interface RoleService {

	/**
	 * Return  roles available. The roles are assigned by Super admin who creates Chief Manager users of onionsquare.com
	 * @return List of Admin roles.
	 */
	List<String> getAllAdminRoles();
}
