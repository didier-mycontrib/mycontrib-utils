package org.mycontrib.generic.test.spring;

import java.io.Serializable;

import org.junit.Test;
import org.mycontrib.generic.test.GenericDaoTest;
import org.springframework.transaction.annotation.Transactional;


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

public abstract class  GenericDaoTestSpring<T,ID extends Serializable> extends GenericDaoTest<T,ID>{
	
    
    @Test
	@Transactional
	public void testGenericDao_CRUD_InOneTx() {
		super.testGenericDao_CRUD_InOneTx();
	}
              
    
	
}
