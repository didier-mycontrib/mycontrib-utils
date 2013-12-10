package org.mycontrib.generic.persistence.spring;

import java.io.Serializable;

import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.persistence.common.jpa.GenericDaoJpaImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Didier
 *
 * NB: penser à placer 
 * <context:component-scan base-package="org.mycontrib"/>
 * dans la configuration Spring de l'application utilisatrice
 */

//@Transactional et @Named à placer dans sous classe prise en charge par Spring
//with @Transactional of Spring
public class GenericDaoJpaSpring<T,ID extends Serializable> extends GenericDaoJpaImpl<T,ID> {
	
		
	public GenericDaoJpaSpring() {
		super();		
	}

	@Override
	@Transactional
	public void deleteEntity(ID pk) throws GenericException {
		super.deleteEntity(pk);
	}

	@Override
	@Transactional
	public void removeEntity(Object e) throws GenericException {
		super.removeEntity(e);
	}

	@Override
	@Transactional
	public T updateEntity(T e) throws GenericException {
		return super.updateEntity(e);
	}

	@Override
	@Transactional(readOnly=true)
	public T getEntityById(ID pk) throws GenericException {
		return super.getEntityById(pk);
	}

	@Override
	@Transactional
	public T persistNewEntity(T e) throws GenericException {
		return super.persistNewEntity(e);
	}

	@Override
	@Transactional
	public ID persistIdNewEntity(T e) throws GenericException {
		return super.persistIdNewEntity(e);
	}
	
	

	
}
