package org.mycontrib.generic.test.spring;

import java.io.Serializable;

import org.junit.Test;
import org.mycontrib.generic.test.GenericDaoTestWithDbUnit;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Didier Defrance
 * 
 * Classe de Test generique pour tester un dao dans le cadre suivant:
 *     - via JUnit4 et SpringTest
 *     - utilisation de dbUnit pour gerer le contenu la base de donnees lors des tests
 *     - test d'un dao dont le code est base sur heritage de GenericDao(JpaImpl)
 *     
 * Utilisation:
 * 
 *     creer une sous classe heritant de GenericDaoTestWithDbUnit
 */

public abstract class GenericDaoTestWithDbUnitSpring<T,ID extends Serializable> extends GenericDaoTestWithDbUnit<T,ID> {
	
	 @Test
		@Transactional
		public void testGenericDao_CRUD_InOneTx() {
			super.testGenericDao_CRUD_InOneTx();
		}
	 
}
