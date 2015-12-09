package com.ssm.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BasicService<T, ID extends Serializable> {

	public void insert(T entity);

	public void update(T entity);
	
	public void delete(ID id);

	public void deleteByIds(Collection<ID> ids);
	
	public void cleanTable();

	public T findById(ID id);
	
	public List<T> find(String orderBy, Map<String, Object> qbuilder, Integer size);
	
	public List<T> findByPage(String orderBy, Map<String, Object> qbuilder);

	public int getTotalRows(Map<String, Object> qbuilder);
	
	public Map<String, Object> initQbuilder(String key, Object value);
	
	public Map<String, Object> initQbuilder(String[] keys, Object[] values);

}