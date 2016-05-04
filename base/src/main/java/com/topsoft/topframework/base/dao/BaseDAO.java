package com.topsoft.topframework.base.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import com.topsoft.topframework.base.domain.IEntity;
import com.topsoft.topframework.base.paging.DataPage;
import com.topsoft.topframework.base.paging.Page;

public interface BaseDAO<T extends IEntity<ID>,ID>{

	public T insert( T domain );
	public T update( T domain );
	public T insertOrUpdate( T domain );
	public void remove( T domain );
	public void removeByID( ID id );
	
	public T findByID( ID id );
	public List<T> findAll();
	public DataPage<T> findAllPage( Page p );
	
	public T readObject( CriteriaQuery<T> query );
	public List<T> readAllObjects( CriteriaQuery<T> query );
	public DataPage<T> readAllPagedObjects( CriteriaQuery<T> query, Page page );
}