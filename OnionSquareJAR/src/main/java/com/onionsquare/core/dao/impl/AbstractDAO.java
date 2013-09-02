package com.onionsquare.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.onionsquare.core.dao.GenericDAO;
import com.onionsquare.core.model.Category;
import com.onionsquare.core.model.Store;


public abstract class AbstractDAO<T, ID extends Serializable> extends HibernateDaoSupport implements GenericDAO<T, ID>{

	/**
	 * @uml.property  name="persistentClass"
	 */
	@SuppressWarnings("unchecked")
	private Class<T> persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	
	/**
	 * @see com.iso.cwav.core.dao.GenericDao#delete(java.lang.Object)
	 */
	public void delete(T persistentInstance)
	{
		getHibernateTemplate().delete(persistentInstance);				
	}

	/**
	 * @see com.iso.cwav.core.dao.GenericDao#deleteAll(List)
	 */
	public void deleteAll(Collection<T> persistentInstanceList) 
	{
		getHibernateTemplate().deleteAll(persistentInstanceList);		
	}
	
	/**
	 * @see com.iso.cwav.core.dao.GenericDao#save(java.lang.Object)
	 */
	public Serializable save(T transientInstance)
	{		
		return getHibernateTemplate().save(transientInstance);		
	}

	/**
	 * @see com.iso.cwav.core.dao.GenericDao#saveOrUpdate(java.lang.Object)
	 */
	public void saveOrUpdate(T transientInstance) 
	{
		getHibernateTemplate().saveOrUpdate(transientInstance);
	}
	
	/**
	 * @param persistentInstanceList
	 */
	public void saveOrUpdateAll(Collection<T> persistentInstanceList)
	{
		if(persistentInstanceList != null)
		{
			getHibernateTemplate().saveOrUpdateAll(persistentInstanceList);				
		}
	}

	/**
	 * @see com.iso.cwav.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(T persistentInstance) 
	{		
		getHibernateTemplate().merge(persistentInstance);		
	}
	
	/**
	 * @see com.iso.cwav.core.dao.GenericDao#findAll()
	 */

	public List<T> findAll() 
	{
		return getHibernateTemplate().loadAll(persistentClass);
	}
	
	public List<T> findAll(int pageNumber, int pageSize,int storeId , int categoryId)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(persistentClass);
		if(storeId>0){
		Store store = new Store();
		store.setStoreId(storeId);
		criteria.add(Restrictions.eq("store",store));
		}
		if(categoryId>0){
			Category category = new Category();
			category.setCategoryId(categoryId);
			criteria.add(Restrictions.eq("category", category));
		}

		
		return findByCriteria(criteria, pageNumber, pageSize);
	}
	/**
	 * @see com.iso.cwav.core.dao.GenericDao#findAll(int, int)
	 */
	public List<T> findAll(int pageNumber, int pageSize)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(persistentClass);
		
		return findByCriteria(criteria, pageNumber, pageSize);
	}

	/**
	 * @see com.iso.cwav.core.dao.GenericDao#findById(java.io.Serializable)
	 */
	
	public T findById(ID id) 
	{
		if(id != null) {
			return (T) getHibernateTemplate().get(persistentClass, id);
		}
		return null ;
	}
	
	/**
	 * @see com.iso.cwav.core.dao.GenericDao#findByExample(Object)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance) 
	{
		return getHibernateTemplate().findByExample(exampleInstance);
	}
	
	/**
	 * @see com.iso.cwav.core.dao.GenericDao#findByCriteria(DetachedCriteria)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(DetachedCriteria criteria)
	{
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	/**
	 * @see com.iso.cwav.core.dao.GenericDao#findByCriteria(DetachedCriteria, int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(DetachedCriteria criteria, int pageNumber, int pageSize)
	{
		return criteria.getExecutableCriteria(getHibernateTemplate().getSessionFactory().getCurrentSession()).setFirstResult(pageNumber * pageSize).setMaxResults(pageSize).list();
	}
}

