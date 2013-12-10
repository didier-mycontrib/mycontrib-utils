package org.mycontrib.generic.test.spring;

import java.io.Serializable;

import junit.framework.Assert;

import org.junit.Test;
import org.mycontrib.generic.persistence.GenericDao;
import org.mycontrib.generic.test.GenericDaoSimpleTest;
import org.mycontrib.generic.test.GenericDaoTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Didier Defrance
 * 
 * 
 */

public abstract class  GenericDaoSimpleTestSpring<T,ID extends Serializable> extends GenericDaoSimpleTest<T,ID>{
	
	@Test
	@Transactional
	public void simpleTestGenericDao_CRUD_InOneTx() {
		super.testGenericDao_CRUD_InOneTx();
	}
       
              
	
}
