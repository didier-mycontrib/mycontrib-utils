package org.mycontrib.generic.persistence.common.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;

import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.exception.conversion.helper.jpa.JpaGenericExceptionConverterHelper;
import org.mycontrib.generic.exception.factory.GenericExceptionFactory;
import org.mycontrib.generic.exception.factory.WithLogGenericExceptionFactory;
import org.mycontrib.generic.persistence.GenericDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class  AbstractGenericDaoJpaImpl<T,ID extends Serializable> implements GenericDao<T,ID> {
	
	private Class<T> persistentClass;
	protected EntityManager entityManager;
	private JpaEntityUtil<ID> jpaEntityUtil= new JpaEntityUtil<ID>();
	
	private static Logger defaultLogger = LoggerFactory.getLogger(AbstractGenericDaoJpaImpl.class);
	private GenericExceptionFactory genExceptionFactory;
	
	@SuppressWarnings("unchecked")
	private void initPersistentClass(){
		try {
	    	   //System.out.println(getClass().getSimpleName());
	    	   ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();  
	    	   //System.out.println("parameterizedType="+parameterizedType.toString());
	    	   Type typeT = parameterizedType.getActualTypeArguments()[0];
	    	   //System.out.println(typeT.toString());
	    	   if(!typeT.toString().equals("T")){
	    		   this.persistentClass = (Class<T>) typeT;
	    		   //System.out.println("persistentClass="+persistentClass.getSimpleName());
	    	   }
		} catch (Exception e) {
			//e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
	
	public AbstractGenericDaoJpaImpl() {
		initPersistentClass();
		this.genExceptionFactory = new WithLogGenericExceptionFactory(defaultLogger)
                                       .withExConverterHelper(new JpaGenericExceptionConverterHelper());
     }
	

	public Class<T> getPersistentClass() {
		return persistentClass;
	}
	
	//méthode à redéfinir via
	//@PersistenceContext() ou @PersistenceContext(unitName="myPersistenceUnit")
	//public setEntityManager(EntityManager entityManager){
		//this.entityManager=entityManager;
	//}
	public abstract void setEntityManager(EntityManager entityManager);
	
	// getter for "entityManager" to use in dao subclass
	public EntityManager getEntityManager() {
		return entityManager;
	}


	@Override
	public  void deleteEntity(ID pk) throws GenericException{
		try {
			Object e= getEntityById(pk);
			if(e==null){
				throw genExceptionFactory.newNotExistException("not found for delete")
				     .addDetail("pk", String.valueOf(pk));
			}
			else{
				removeEntity(e);
			}
		} catch (Exception ex) {
			throw genExceptionFactory.convertToGenericException(ex)
		      .addDetail("AbstractGenericDaoJpaImpl.deleteEntity","with failure"); 
		}
	}


	@Override
	public void removeEntity(Object e)  throws GenericException {
		try {
		entityManager.remove(e);
		} catch (Exception ex) {
			throw genExceptionFactory.convertToGenericException(ex)
		      .addDetail("AbstractGenericDaoJpaImpl.removeEntity","with failure"); 
		}
	}

	@Override
	public  T updateEntity(T e)  throws GenericException {
		T pe = null;
		try {
			pe=entityManager.merge(e);
		} catch (Exception ex) {
			throw genExceptionFactory.convertToGenericException(ex)
		      .addDetail("AbstractGenericDaoJpaImpl.updateEntity","with failure");
		}
		return pe;
	}

	@Override
	public  T getEntityById(ID pk)  throws GenericException {
		T pe = null;
		try {
			pe= entityManager.find(persistentClass, pk);
		} catch (Exception ex) {
			throw genExceptionFactory.convertToGenericException(ex)
		      .addDetail("AbstractGenericDaoJpaImpl.getEntityById","with failure");
		}
	    return pe;
	}

	@Override
	public  T persistNewEntity(T e)  throws GenericException {
		try {
			entityManager.persist(e);
		} catch (javax.persistence.EntityExistsException ex) {
			throw genExceptionFactory.newConflictException("DUPLICATE_ID",ex)
			   .addDetail("AbstractGenericDaoJpaImpl.newConflictException","with failure");
		} catch (RuntimeException ex){
			throw genExceptionFactory.convertToGenericException(ex)
		      .addDetail("AbstractGenericDaoJpaImpl.persistNewEntity","with failure");
		}
		return e;
	}

	@Override
	public ID persistIdNewEntity(T e)   throws GenericException {
		ID id=null;
		try {
			entityManager.persist(e);
			id=jpaEntityUtil.getIdOfJpaEntity(e);
		} catch (Exception ex) {
			throw genExceptionFactory.convertToGenericException(ex)
		      .addDetail("AbstractGenericDaoJpaImpl.persistIdNewEntity","with failure");
			}
		return id;
	}
	
	

}
