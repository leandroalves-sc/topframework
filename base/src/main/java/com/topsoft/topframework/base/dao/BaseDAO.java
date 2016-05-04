package com.topsoft.topframework.base.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import com.topsoft.topframework.base.domain.Entity;
import com.topsoft.topframework.base.paging.DataPage;
import com.topsoft.topframework.base.paging.Page;

public interface BaseDAO<T extends Entity<ID>,ID>{

	T insert( T domain );
	T update( T domain );
	T insertOrUpdate( T domain );
	void remove( T domain );
	void removeByID( ID id );
	
	T findByID( ID id );
	List<T> findAll();
	DataPage<T> findAllPage( Page p );
	
	T readObject( CriteriaQuery<T> query );
	List<T> readAllObjects( CriteriaQuery<T> query );
	DataPage<T> readAllPagedObjects( CriteriaQuery<T> query, Page page );
}