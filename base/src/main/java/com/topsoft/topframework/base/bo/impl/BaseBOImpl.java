package com.topsoft.topframework.base.bo.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.topsoft.topframework.base.bo.BaseBO;
import com.topsoft.topframework.base.dao.BaseDAO;
import com.topsoft.topframework.base.domain.IEntity;
import com.topsoft.topframework.base.paging.DataPage;
import com.topsoft.topframework.base.paging.Page;

@Transactional(rollbackFor=Throwable.class)
public class BaseBOImpl<T extends IEntity<ID>,ID> implements BaseBO<T,ID>{
	
	protected BaseDAO<T,ID> dao;
	
	public BaseBOImpl( BaseDAO<T,ID> dao ){
    	
    	super();
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