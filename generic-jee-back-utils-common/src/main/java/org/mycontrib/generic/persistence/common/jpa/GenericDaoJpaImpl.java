package org.mycontrib.generic.persistence.common.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 
 * @author Didier
 **/

public class GenericDaoJpaImpl<T,ID extends Serializable> extends AbstractGenericDaoJpaImpl<T,ID> {
	
		
	public GenericDaoJpaImpl() {
		super();		
	}

	@PersistenceContext() //with default (unique) persitent Unit.
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
}
