package org.alliancegenome.agr_submission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class BaseSQLDAO<E extends BaseEntity> extends BaseDAO<E> {

	@PersistenceContext(name="primary")
	protected EntityManager entityManager;

	protected BaseSQLDAO(Class<E> myClass) {
		super(myClass);
	}
	
	public E persist(E entity) {
		log.debug("SqlDAO: persist: " + entity);
		entityManager.persist(entity);
		return entity;
	}

	public E find(Long id) {
		log.debug("SqlDAO: find: " + id + " " + myClass);
		E entity = entityManager.find(myClass, id);
		log.debug("Entity Found: " + entity);
		return entity;
	}

	public List<E> findAll() {
		log.debug("SqlDAO: findAll: " + myClass);
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<E> cq = cb.createQuery(myClass);
		Root<E> rootEntry = cq.from(myClass);
		CriteriaQuery<E> all = cq.select(rootEntry);
		TypedQuery<E> allQuery = entityManager.createQuery(all);
		return allQuery.getResultList();
	}

	public E merge(E entity) {
		log.debug("SqliteDAO: merge: " + entity);
		entityManager.merge(entity);
		return entity;
	}
	
	public E remove(Long id) {
		log.debug("SqliteDAO: remove: " + id);
		E entity = find(id);
		entityManager.remove(entity);
		return entity;
	}

	public E findByField(String field, String value) {
		log.debug("SqlDAO: findByField: " + field + " " + value);
		HashMap<String, Object> params = new HashMap<>();
		params.put(field, value);
		List<E> list = search(params);
		log.debug("Result List: " + list);
		if(list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	protected List<E> search(Map<String, Object> params) {
		return search(params, null);
	}
	
	public List<E> search(Map<String, Object> params, String orderByField) {
		log.debug("Search By Params: " + params + " Order by: " + orderByField);
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<E> query = builder.createQuery(myClass);
		Root<E> root = query.from(myClass);
		//System.out.println("Root: " + root);
		List<Predicate> restrictions = new ArrayList<Predicate>();
		//System.out.println(params);
		for(String key: params.keySet()) {
			Path<Object> column = null;
			//System.out.println("Key: " + key);
			if(key.contains(".")) {
				String[] objects = key.split("\\.");
				for(String s: objects) {
					//System.out.println("Looking up: " + s);
					if(column != null) {
						column = column.get(s);
					} else {
						column = root.get(s);
					}
					//System.out.println(column.getAlias());
				}
			} else {
				column = root.get(key);
			}

			//System.out.println(column.getAlias());

			Object value = params.get(key);
			if (value instanceof Integer) {
				Integer desiredValue = (Integer) value;
				restrictions.add(builder.equal(column, desiredValue));
			} else {
				String desiredValue = (String) value;
				restrictions.add(builder.equal(column, desiredValue));
			}
		}
		if(orderByField != null) {
			query.orderBy(builder.asc(root.get(orderByField)));
		}

		query.where(builder.and(restrictions.toArray(new Predicate[0])));

		return entityManager.createQuery(query).getResultList();
	}

}
