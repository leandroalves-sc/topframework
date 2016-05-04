package com.topsoft.topframework.base.bo;

import java.util.List;

import com.topsoft.topframework.base.domain.Entity;
import com.topsoft.topframework.base.paging.DataPage;
import com.topsoft.topframework.base.paging.Page;

public interface BaseBO<T extends Entity<ID>,ID>{

	T insert( T domain );
	T update( T domain );
	T insertOrUpdate( T domain );
	void remove( T domain );
	void removeByID( ID id );
	
	T findByID( ID id );
	List<T> findAll();
	DataPage<T> findAllPage( Page p );
}