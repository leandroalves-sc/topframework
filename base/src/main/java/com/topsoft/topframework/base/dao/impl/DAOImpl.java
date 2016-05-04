package com.topsoft.topframework.base.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import com.topsoft.topframework.base.dao.DAO;
import com.topsoft.topframework.base.domain.Entity;
import com.topsoft.topframework.base.paging.DataPage;
import com.topsoft.topframework.base.paging.Page;

public abstract class DAOImpl<T extends Entity<ID>, ID> implements DAO<T, ID> {

	@PersistenceContext
	protected EntityManager entityManager;

	private Class<T> entityClass;

	public DAOImpl() {
		this.entityClass = getPersistentClass();
	}

	@Override
	public T insertOrUpdate(T dto) {

		if (isNew(dto))
			return insert(dto);
		else
			return update(dto);
	}

	private boolean isNew(T dto) {

		if (dto.getId() == null)
			return true;

		return findByID(dto.getId()) == null;
	}

	@Override
	public T insert(T dto) {
		entityManager.persist(dto);
		entityManager.flush();
		return dto;
	}

	@Override
	public T update(T dto) {

		if (findByID(dto.getId()) == null)
			return insert(dto);

		return entityManager.merge(dto);
	}

	@Override
	public void removeByID(ID id) {

		T dto = findByID(id);

		if (dto == null)
			throw new OptimisticLockException("Object not found: id=" + id + " classe=" + entityClass);

		entityManager.remove(dto);
	}

	@Override
	public void remove(T dto) {

		if (dto == null || dto.getId() == null)
			throw new OptimisticLockException("Object not found: id=" + dto.getId() + " classe=" + entityClass);

		removeByID(dto.getId());
	}

	@Override
	public List<T> findAll() {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(entityClass);
		Root<T> root = query.from(entityClass);

		List<Order> order = findAllOrder(builder, root);

		if (order != null && !order.isEmpty()) {
			query.orderBy(order);
		}

		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public DataPage<T> findAllPage(Page p) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(entityClass);

		return readAllPagedObjects(query, p);
	}

	@Override
	public T findByID(ID id) {
		return entityManager.find(entityClass, id);
	}

	@Override
	public T readObject(CriteriaQuery<T> query) {

		try {

			return entityManager.createQuery(query).getSingleResult();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public DataPage<T> readAllPagedObjects(CriteriaQuery<T> query, Page page) {

		if (page == null || !Page.isValid(page))
			return null;

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		countQuery.select(criteriaBuilder.count(countQuery.from(entityClass)));
		Long count = entityManager.createQuery(countQuery).getSingleResult();

		TypedQuery<T> tQuery = entityManager.createQuery(query);

		tQuery.setFirstResult(page.getStartRow());
		tQuery.setMaxResults(page.getPageSize());

		List<T> list = tQuery.getResultList();

		return new DataPage<T>(list, count, page);
	}

	@Override
	public List<T> readAllObjects(CriteriaQuery<T> query) {
		return entityManager.createQuery(query).getResultList();
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return entityManager.getCriteriaBuilder();
	}

	@SuppressWarnings("unchecked")
	private Class<T> getPersistentClass() {

		if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
			return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
		else if (getClass().getGenericSuperclass() instanceof Class) {

			Class<T> c = (Class<T>) getClass().getGenericSuperclass();
			return (Class<T>) ((ParameterizedType) c.getGenericSuperclass()).getActualTypeArguments()[0];
		}

		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public List<Order> findAllOrder(CriteriaBuilder builder, Root<T> root) {
		return null;
	}
}