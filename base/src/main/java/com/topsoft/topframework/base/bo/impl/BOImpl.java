package com.topsoft.topframework.base.bo.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.topsoft.topframework.base.bo.BO;
import com.topsoft.topframework.base.dao.DAO;
import com.topsoft.topframework.base.domain.Entity;
import com.topsoft.topframework.base.paging.DataPage;
import com.topsoft.topframework.base.paging.Page;

@Transactional(rollbackFor=Throwable.class)
public class BOImpl<T extends Entity<ID>,ID> implements BO<T,ID> {

	protected DAO<T,ID> dao;
	
	public BOImpl(DAO<T,ID> dao ){
    	this.dao = dao;
    }
	
	@Override
	public T insertOrUpdate( T dto ){
		return dao.insertOrUpdate( dto );
	}
	
	@Override
	public T insert( T dto ){
		return dao.insert( dto );
	}
	
	@Override
	public T update( T dto ){
		return dao.update( dto );
	}
	
	@Override
	public void removeByID( ID id ){
		dao.removeByID( id );
	}

	@Override
	public void remove( T dto ){
		dao.remove( dto );
	}
	
	@Override
	public List<T> findAll(){
		return dao.findAll();
	}
	
	@Override
	public DataPage<T> findAllPage( Page p ){
		return dao.findAllPage( p );
	}

	@Override
	public T findByID( ID id ){
		return dao.findByID( id );
	}	
}