package com.dreamer8.yosimce.server.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Generated at Fri Aug 05 17:29:02 CLT 2011
 *
 * @author Salto-db Generator v1.0.16 / Pojos + Hibernate mapping + Generic DAO
 * @see http://www.hibernate.org/328.html
 */
public interface GenericDAO<T, ID extends Serializable> {

	T getById(ID id, boolean lock);

	T getById(ID id);
	
	T loadById(ID id);

	List<T> findAll();
	
	List<T> findByCriteria(Map criterias);
	
	public List<T> findByExample(T exampleInstance, String[] excludeProperty);

	void save(T entity);

	void update(T entity);

	void saveOrUpdate(T entity);

	void delete(T entity);
	
	void deleteById(ID id);
	
}