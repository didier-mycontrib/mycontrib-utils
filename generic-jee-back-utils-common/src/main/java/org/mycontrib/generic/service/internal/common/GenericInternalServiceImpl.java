package org.mycontrib.generic.service.internal.common;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.mycontrib.generic.converter.GenericBeanConverter;
import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.exception.conversion.helper.jpa.JpaGenericExceptionConverterHelper;
import org.mycontrib.generic.exception.factory.GenericExceptionFactory;
import org.mycontrib.generic.exception.factory.WithLogGenericExceptionFactory;
import org.mycontrib.generic.persistence.GenericDao;
import org.mycontrib.generic.service.internal.GenericInternalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GenericInternalServiceImpl 
 * 
 * @author formation
 *
 * @param <D> : type of DTO (ex: Compte or CompteDto)
 * @param <E> : type of Entity (ex: _Compte) 
 * @param <ID> : type of ID/pk (ex: Long , String , ...)
 * 
 */

 /* Exemple Utilisation:
@Named
@Transactional
public class GestionComptesImpl implements GestionComptes {
	
	private GenericInternalService<Compte,Long> genericInternalService =null;
	
	@PostConstruct  //@Post initialization(s) after injection
	protected void initGenericInternalService(){
		genericInternalService = new GenericInternalServiceImpl<Compte,_Compte,Long>(cptDao,beanConverter,logger){};
		// new instance of anonymous inner class witch inherit of GenericSuperClass
	}
	   
	@Inject
	private GenericBeanConverter beanConverter;
	
	@Inject
	private DaoCompte cptDao; //inherit of GenericDao<_Compte,Long>
...*/
 


public class GenericInternalServiceImpl<D,E,ID extends Serializable> implements
		GenericInternalService<D, ID> {
	
	private static Logger defaultLogger = LoggerFactory.getLogger(GenericInternalServiceImpl.class);
	private GenericExceptionFactory genExceptionFactory ;
	
	
	private GenericDao<E,ID> genericDao;
	private GenericBeanConverter beanConverter;
	private Class<E> persistentClass;
	private Class<D> dtoClass;
	
	
	private void initPersistentClass(){
		try {
			   //System.out.println(getClass().getSimpleName());
	    	   ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass(); 
			   //System.out.println("parameterizedType="+parameterizedType.toString());
	    	   Type typeE = parameterizedType.getActualTypeArguments()[1];
			   //System.out.println(typeE.toString());
	    	   if(!typeE.toString().equals("E")){
	    		   this.persistentClass = (Class<E>) typeE;
	    		   //System.out.println("persistentClass="+persistentClass.getSimpleName());
	    	   }
		} catch (Exception e) {
			//e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
	
	private void initDtoClass(){
		try {	    	   
			   //System.out.println(getClass().getSimpleName());
	    	   ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();  
	    	   //System.out.println("parameterizedType="+parameterizedType.toString());
	    	   Type typeD = parameterizedType.getActualTypeArguments()[0];
	    	   //System.out.println(typeD.toString());
	    	   if(!typeD.toString().equals("D")){
	    		   this.dtoClass = (Class<D>) typeD;
	    		   //System.out.println("dtoClass="+dtoClass.getSimpleName());
	    	   }
		} catch (Exception e) {
			//e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
	
	public GenericInternalServiceImpl() {
		this(null,null,defaultLogger);  }
	
	public GenericInternalServiceImpl(GenericDao<E,ID> genericDao,GenericBeanConverter beanConv) {
		this(genericDao,beanConv,defaultLogger);
     }
	
	public GenericInternalServiceImpl(GenericDao<E,ID> genericDao,GenericBeanConverter beanConv,Logger logger) {
		initPersistentClass();
		initDtoClass();
		this.setGenericDao(genericDao);
		this.setBeanConverter(beanConv);
		genExceptionFactory = new WithLogGenericExceptionFactory(logger)
		                      .withExConverterHelper(new JpaGenericExceptionConverterHelper());
		                     /// .withExConverterHelper(new SpringGenericExceptionConverterHelper());
     }


	
    // for injection
	public void setGenericDao(GenericDao<E, ID> genericDao) {
		this.genericDao = genericDao;
	}
	
	
	// for injection
	public void setBeanConverter(GenericBeanConverter beanConverter) {
		this.beanConverter = beanConverter;
	}


	//@Transactional in real service (not in internal service ?)
	public void updateEntityFromDto(D d)  throws GenericException {
		try {
			genericDao.updateEntity(beanConverter.convert(d,persistentClass));			
		} catch (Exception e) {
			throw genExceptionFactory.convertToGenericException(e)
			      .addDetail("GenericInternalServiceImpl.updateEntityFromDto","with failure"); 
		}	
	}

	//@Transactional(readOnly=true) in real service (not in internal service ?)
	public D getDtoById(ID pk)  throws GenericException {
		D dto=null;
		try {
			dto= beanConverter.convert(genericDao.getEntityById(pk),dtoClass);
		} catch (Exception e) {
			throw genExceptionFactory.convertToGenericException(e)
		      .addDetail("GenericInternalServiceImpl.getDtoById","with failure");
		}
		return dto;
		}

	public ID persistIdNewEntityFromDto(D d)  throws GenericException {
		ID pk=null;
		try {
			pk=genericDao.persistIdNewEntity(beanConverter.convert(d,persistentClass));	
		} catch (Exception e) {
			throw genExceptionFactory.convertToGenericException(e)
		      .addDetail("GenericInternalServiceImpl.persistNewEntityFromDto","with failure"); 
		}
		return pk;
	}

}
