package org.mycontrib.generic.persistence.spring;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mycontrib.generic.persistence.GenericDao;
import org.springframework.transaction.annotation.Transactional;


public abstract class  AbstractGenericDaoHibernateTxImpl<T,ID extends Serializable> implements GenericDao<T,ID> {
	
	private Class<T> persistentClass;
	
    protected SessionFactory sessionFactory=null;
	
    //methode d'injection de dependance qui sera heritee
    //pour <property name="sessionFactory" ref="mySessionFactory"/> dans <bean name="...dao">
	public void setSessionFactory(SessionFactory sf)
	{
		sessionFactory = sf;
	}

	
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
	
	public AbstractGenericDaoHibernateTxImpl() {
		initPersistentClass();
     }
	

	public Class<T> getPersistentClass() {
		return persistentClass;
	}
	
	//ou bien utilisation de hibernateTemplate en "protected" dans les sous classes
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
	
	public Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}



	@Override
	@Transactional
	public  void deleteEntity(ID pk) {
		Object e= getEntityById(pk);
		if(e!=null)
			removeEntity(e);
	}


	@Override
	@Transactional
	public void removeEntity(Object e) {
		getCurrentSession().delete(e);
	}

	@Override
	@Transactional
	public  T updateEntity(T e) {
		getCurrentSession().update(e);
		return e;
	}

	@Override
	@Transactional
	public  T getEntityById(ID pk) {
		return (T) getCurrentSession().get(persistentClass, pk);
	}

	@Override
	@Transactional
	public  T persistNewEntity(T e) {
		getCurrentSession().persist(e);
		//getCurrentSession().save(e);
		return e;
	}

}
