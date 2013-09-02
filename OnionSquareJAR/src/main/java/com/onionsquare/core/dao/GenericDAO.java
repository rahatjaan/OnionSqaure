package com.onionsquare.core.dao;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
//import org.hibernate.criterion.DetachedCriteria;

public interface GenericDAO<T, ID extends Serializable> 
{
	/**
	 * This method deletes the persistent instance.
	 * 
	 * @param persistentInstance The persistentInstance to delete.
	 */
	void delete(T persistentInstance);
	
	/**
	 * This method deletes all the persistent instance.
	 * 
	 * @param persistentInstanceList The persistentInstanceList to delete.
	 */
	void deleteAll(Collection<T> persistentInstanceList);
	
	/**
	 * This method will save the transient instance by assigning a generated indentifier.
	 * 
	 * @param transientInstance The transientInstance to save.
	 */
	Serializable save(T transientInstance);
	
	/**
	 * This method will either save or update the transient instance based on the unsaved-value.
	 * 
	 * @param transientInstance The transientInstance to save or update.
	 */
	void saveOrUpdate(T transientInstance);
	
	/**
	 * This method will either save or update the transient instances based on the unsaved-value.
	 * 
	 * @param persistentInstanceList The persistentInstanceList of transientInstances to save or update.
	 */
	void saveOrUpdateAll(Collection<T> persistentInstanceList);
		
	/**
	 * This method will update the persistent instance.
	 * 
	 * @param persistentInstance The persistentInstance to update.
	 */
	void update(T persistentInstance);
	
	/**
	 * This method will find an object by id.
	 * 
	 * @param id  The id to use.
	 * @return Returns the object.
	 * @param <T>
	 */
	T findById(ID id);
	
	/**
	 * This method will find all rows of the type of class.
	 * 
	 * @return Returns the list.
	 */
	List<T> findAll();
	
	/**
	 * This method will find all rows of the type of class based on the page number and page size.
	 * 
	 * @param pageNumber The pageNumber to set.
	 * @param pageSize The pageSize to set.
	 * @return Returns the list.
	 */
	List<T> findAll(int pageNumber, int pageSize);
	
	List<T> findAll(int pageNumber, int pageSize,int storeId, int categoryId);

	
	/**
	 * This method will find all rows that match the example instance.
	 * 
	 * @param exampleInstance The exampleInstance to set.
	 * @return Returns the list.
	 */
	List<T> findByExample(T exampleInstance);
	
	/**
	 * This method will perform a searched based on the criteria.
	 * 
	 * @param criteria The criteria to set.
	 * @return Returns the list.
	 */
	List<T> findByCriteria(DetachedCriteria criteria);
	
	/**
	 * This method will perform a search based on the criteria and on the page size and page number.
	 * 
	 * @param criteria The criteria to set.
	 * @param pageSize The pageSize to set.
	 * @param pageNumber The pageNumber to set.
	 * @return Returns the list.
	 */
	List<T> findByCriteria(DetachedCriteria criteria, int pageSize, int pageNumber);	
}
