package com.onionsquare.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onionsquare.core.dao.AdminDao;
import com.onionsquare.core.model.Admin;

@Repository("adminDao")
public class AdminDaoImpl extends AbstractDAO<Admin, Integer> implements
		AdminDao {

	@Autowired
	public AdminDaoImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public Admin findByUserName(String userName) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Admin.class);
		detachedCriteria.add(Restrictions.eq("username", userName));

		List<Admin> userInfoList = findByCriteria(detachedCriteria);
		if (userInfoList != null && userInfoList.size() > 0) {
			return userInfoList.get(0);
		}
		return null;
	}

	public Admin findAdminByRole(String roleName) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Admin.class);
		detachedCriteria.add(Restrictions.eq("roleName", roleName));
		List<Admin> adminList = findByCriteria(detachedCriteria);
		if (adminList != null && adminList.size() > 0) {
			return adminList.get(0);
		}
		return null;
	}
	 
	public List<Admin> findAdminList(String userName, String roleName) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Admin.class);
		if(userName !=null || roleName != null) {
			if(userName != null){
				detachedCriteria.add(Restrictions.eq("username", userName));
			}
			if(roleName != null) {
				detachedCriteria.add(Restrictions.eq("roleName", roleName));
			}
			return findByCriteria(detachedCriteria);			 
		}		
		return new ArrayList<Admin>(); //return empty list	 
	}
	public Admin findAdminByUserNameAndRole(String userName, String roleName){
		List<Admin> adminList = findAdminList(userName,roleName);
		if(adminList.size() > 0) {
			return adminList.get(0);
		}
		return null ;
	}
	
	public List<Admin> findAllAdminByRole(String roleName) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Admin.class);
		detachedCriteria.add(Restrictions.eq("roleName", roleName));
		List<Admin> adminList = findByCriteria(detachedCriteria);
		return adminList ;
	}

	@Override
	public List<Admin> getAdminListExcludingLoginAdmin(int adminId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Admin.class);
		detachedCriteria.add(Restrictions.ne("adminId", adminId));
		List<Admin> adminList = findByCriteria(detachedCriteria);
		return adminList ;
	}
}
