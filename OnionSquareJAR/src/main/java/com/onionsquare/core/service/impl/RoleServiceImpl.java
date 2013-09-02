package com.onionsquare.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.onionsquare.core.service.RoleService;
import com.onionsquare.core.util.OnionSquareConstants;

@Service
public class RoleServiceImpl implements RoleService{	
	
	public List<String> getAllAdminRoles() {
		List<String> roleList = new ArrayList() ;
		roleList.add(OnionSquareConstants.ROLE_ADMIN);
		roleList.add(OnionSquareConstants.ROLE_MANAGER);		
		return roleList ;
	}

}
