package org.mycontrib.generic.test;

import java.io.Serializable;

import junit.framework.Assert;

import org.junit.Test;
import org.mycontrib.generic.persistence.GenericDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Didier Defrance
 * 
 * Classe de Test generique pour tester un dao dans le cadre suivant:
 *     - via JUnit4 et SpringTest
 *     - test d'un dao dont le code est base sur heritage de GenericDao<T,ID>(JpaImpl ou ...)
 *     
 * Utilisation:
 * 
 *     creer une sous classe heritant de GenericDaoTest<T,ID> 
 *     ou T est le type d'entite persistante et ID le type de l'identifiant (clef primaire)
 *     
 *     et paramétrer log4j.properties avec
 *     log4j.logger.org.mycontrib.generic.test=info , CONSOLE ou ...
 */

public abstract class  GenericDaoTest<T,ID extends Serializable> {
	private static Logger logger = LoggerFactory.getLogger(GenericDaoTest.class);
	protected GenericDao<T,ID> genericDao;
	protected ID genericPk;
	
	public void setGenericDao(GenericDao<T,ID> genericDao) {
		this.genericDao = genericDao;
	}
	
	//callback à coder:
	public abstract ID getPkOfEntity(T entity);
	public abstract T newEntity();
	public abstract void assertValuesOfNewEntity(T entity);
	public abstract void changeValuesOfEntity(T entity);
	public abstract void assertChangedValuesOfNewEntity(T entity);
	
	//callback utiles si relations (1-1 , 1-n , n-n , ... ) , redefinition non obligatoire si heritage de GenericDaoSimpleTest
	public abstract void displayAttachedOtherEntities(T mainEntity); // afficher choses en dehors de toString()
	
	public abstract void attachNewOtherEntities(T mainEntity);
	public abstract void assertNewAttachedOtherEntities(T mainEntity);
	
	public abstract void updateAttachedOtherEntities(T mainEntity);
	public abstract void assertApdatedAttachedOtherEntities(T mainEntity);
		
	//code du TestGenerique
	
	public GenericDao<T,ID> getGenericDao() {
		return genericDao;
	}

	
	public ID getGenericPk() {
		return genericPk;
	}

	public void setGenericPk(ID genericPk) {
		this.genericPk = genericPk;
	}

	@Test
	// sans transaction globale , et donc plusieurs petites transactions séparées
	public void testGenericDao_CRUD() {
		//************* version sans grande transaction globale (plein de petites transactions)
    	//              persistant, detache , persistant, detache , ....
    	//              pas d'affichage sophistique (probleme sur lazy=true)
    	//******************************************************************
		logger.info("****** test CRUD sur T avec plusieurs petites transactions (via DAO GenericDao<T>) *****");
		// sequence habituelle : new & save , get(All) , maj , update , get , delete , ...
		boolean withDeep=false;
		sequence_crudT(withDeep);
		logger.info("****** end of CRUD test avec plusieurs petites transactions *****\n");
	}
    
	//avec @Test et @Transactional sur une version à redéfinir dans une sous classe avec Spring
	public void testGenericDao_CRUD_InOneTx() {
		//************* version avec grande transaction globale (de bout en bout a l'etat persistant)
    	//              affichages sophistiques possibles (pas de probleme avec lazy=true)
    	//******************************************************************
		logger.info("****** test CRUD sur T en une seule transaction (via DAO GenericDao<T>) *****");
		// sequence habituelle : new & save , get(All) , maj , update , get , delete , ...
		boolean withDeep=true;
		sequence_crudT(withDeep);
		logger.info("****** end of CRUD test en une seule transaction *****\n");
	}
              
    public void sequence_crudT(boolean withDeep){
		createEntity(withDeep); verifNewEntity(withDeep);
		updateEntity(withDeep); verifUpdateEntity(withDeep); 
		deleteEntity(withDeep); verifDeleteEntity(withDeep);
	}
	public static Logger getLogger() {
		return logger;
	}

	private void createEntity(boolean withDeep) {
		try {
		    T newEntity = this.newEntity(); //callback (in subclass)
	        if(withDeep){
	        // eventuelles liaisions avec d'autres entites
	        	this.attachNewOtherEntities(newEntity);
	        }
			genericDao.persistNewEntity(newEntity); // appel d'une methode transactionnelle
			this.genericPk = this.getPkOfEntity(newEntity);
			if(withDeep)
			     logger.debug("\t id(pk) de la nouvelle entite creee (withDeep) : " + genericPk);
			else
				 logger.info("\t id(pk) de la nouvelle entite creee: " + genericPk);
			} catch(RuntimeException ex){
				logger.error(ex.getMessage());
      	    	Assert.fail(ex.getMessage());
      			}
		}
		
		private void verifNewEntity(boolean withDeep) {
		T e=null;
			try {
			e = genericDao.getEntityById(this.genericPk);
			this.assertValuesOfNewEntity(e);
			if(withDeep)
				logger.debug("\t valeurs initiales de l'entite (creee - withDepp): " + e.toString());
			else
				logger.info("\t valeurs initiales de l'entite (creee): " + e.toString());
			if(withDeep){
				// eventuel test ou affichage d'un element (ou collection) lie(e) - lazy=true ? 
			    assertNewAttachedOtherEntities(e);
			    displayAttachedOtherEntities(e);	    			
			  }
			
			} catch(RuntimeException  ex){
				logger.error(ex.getMessage());
      	    	Assert.fail(ex.getMessage());
      		}
		}
					
		private void updateEntity(boolean withDeep) {
		T e=null;
		try {
			e = genericDao.getEntityById(this.genericPk);
			this.changeValuesOfEntity(e);
			
			if(withDeep){
			    // eventuelle modification d'une liaison avec une autre entite
				updateAttachedOtherEntities(e);
			  }
			genericDao.updateEntity(e);  // appel d'une methode transactionnelle
		    } catch(Exception ex){
		    	logger.error(ex.getMessage());
      	    	Assert.fail(ex.getMessage());
      		}
		}
		
		
		private void verifUpdateEntity(boolean withDeep) {
		T e=null;
			try {
			e = genericDao.getEntityById(this.genericPk);
			this.assertChangedValuesOfNewEntity(e);
			if(withDeep)
				logger.debug("\t nouvelle valeur de l'entite (modifiee - withDeep): " + e.toString());
			else
				logger.info("\t nouvelle valeur de l'entite (modifiee): " + e.toString());
			if(withDeep){
			    // + eventuel test ou affichage d'un element (ou collection) lie(e) - lazy=true ? 
				assertApdatedAttachedOtherEntities(e);
				displayAttachedOtherEntities(e);
			  }
			} catch(RuntimeException  ex){
				logger.error(ex.getMessage());
      	    	Assert.fail(ex.getMessage());
      		}
		}
		    	    
		private void deleteEntity(boolean withDeep) {
		try {
			genericDao.deleteEntity(this.genericPk);
			} catch(RuntimeException  ex){
				logger.error(ex.getMessage());
      	    	Assert.fail(ex.getMessage());
      		}	
		}   
		
	
		private void verifDeleteEntity(boolean withDeep) {
		try {
			T e = genericDao.getEntityById(this.genericPk);
			if(e==null){
				if(withDeep)
					logger.debug("\t entite bien supprimee - withDeep");
				else
				    logger.info("\t entite bien supprimee");
			}
			Assert.assertTrue(e == null);
		} catch(RuntimeException ex){
			    logger.error(ex.getMessage());
      	    	Assert.fail(ex.getMessage());
      		}
	   }

	
}
