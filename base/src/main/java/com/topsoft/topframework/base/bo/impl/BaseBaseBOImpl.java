package com.topsoft.topframework.base.bo.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.topsoft.topframework.base.bo.BaseBO;
import com.topsoft.topframework.base.dao.BaseDAO;
import com.topsoft.topframework.base.domain.Entity;
import com.topsoft.topframework.base.paging.DataPage;
import com.topsoft.topframework.base.paging.Page;

@Transactional(rollbackFor=Throwable.class)
public class BaseBaseBOImpl<T extends Entity<ID>,ID> implements BaseBO<T,ID> {

	protected BaseDAO<T,ID> baseDao;
	
	public BaseBaseBOImpl(BaseDAO<T,ID> baseDao){
    	this.baseDao = baseDao;
    }
	
	@Override
	public T insertOrUpdate( T dto ){
		return baseDao.insertOrUpdate( dto );
	}
	
	@Override
	public T insert( T dto ){
		return baseDao.insert( dto );
	}
	
	@Override
	public T update( T dto ){
		return baseDao.update( dto );
	}
	
	@Override
	public void removeByID( ID id ){
		baseDao.removeByID( id );
	}

	@Override
	public void remove( T dto ){
		baseDao.remove( dto );
	}
	
	@Override
	public List<T> findAll(){
		return baseDao.findAll();
	}
	
	@Override
	public DataPage<T> findAllPage( Page p ){
		return baseDao.findAllPage( p );
	}

	@Override
	public T findByID( ID id ){
		return baseDao.findByID( id );
	}	
}