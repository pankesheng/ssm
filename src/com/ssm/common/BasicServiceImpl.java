package com.ssm.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zcj.web.context.SystemContext;


public abstract class BasicServiceImpl<T, ID extends Serializable, M extends BasicMapper<T, ID>> implements BasicService<T, ID> {

	protected abstract M getMapper();
	
	@Override
	public void insert(T entity) {
		getMapper().insert(entity);
	}

	@Override
	public void delete(ID id) {
		getMapper().delete(id);
	}

	@Override
	public void deleteByIds(Collection<ID> ids) {
		getMapper().deleteByIds(ids);
	}
	
	@Override
	public void cleanTable() {
		getMapper().cleanTable();
	}

	@Override
	public void update(T entity) {
		getMapper().update(entity);
	}

	@Override
	public T findById(ID id) {
		return getMapper().findById(id);
	}

	@Override
	public List<T> find(String orderBy, Map<String, Object> qbuilder, Integer size) {
		return getMapper().find(qbuilder, null, size, orderBy);
	}

	@Override
	public List<T> findByPage(String orderBy, Map<String, Object> qbuilder) {
		return getMapper().find(qbuilder, SystemContext.getOffset(), SystemContext.getPagesize(), orderBy);
	}

	@Override
	public int getTotalRows(Map<String, Object> qbuilder) {
		return getMapper().getTotalRows(qbuilder);
	}
	
	@Override
	public Map<String, Object> initQbuilder(String key, Object value) {
		Map<String, Object> query = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(key) && value != null) {			
			query.put(key, value);
		}
		return query;
	}
	
	@Override
	public Map<String, Object> initQbuilder(String[] keys, Object[] values) {
		Map<String, Object> query = new HashMap<String, Object>();
		if (keys != null && keys.length > 0 && values != null && values.length > 0 && keys.length==values.length) {
			for (int i = 0; i < keys.length; i++) {
				if (StringUtils.isNotBlank(keys[i]) && values[i] != null) {								
					query.put(keys[i], values[i]);
				}
			}
		}
		return query;
	}

}