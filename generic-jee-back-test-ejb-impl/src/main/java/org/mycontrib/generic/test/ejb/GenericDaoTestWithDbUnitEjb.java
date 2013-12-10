package org.mycontrib.generic.test.ejb;

import java.io.Serializable;

import javax.naming.Context;
import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.mycontrib.generic.test.GenericDaoTestWithDbUnit;
import org.mycontrib.generic.test.ejb.AbstractOpenEjbTest;
import org.mycontrib.generic.test.ejb.OpenEjbTestUtil;

public abstract class GenericDaoTestWithDbUnitEjb<T,ID extends Serializable> extends GenericDaoTestWithDbUnit<T,ID>{
	
	protected static Context ctx;
	private DataSource ds;
	
	
	public GenericDaoTestWithDbUnitEjb(){
		initDataSource();
		initDao();
	}
	
	public abstract void initDao(); //à redéfinir pour initialiser le dao à tester
	/* exemple de redéfinition de initDao() :
	 * 
	 public void initDao(){
		dao = OpenEjbTestUtil.initEjbServiceIfNull(ctx, dao, "ClientDaoJpa" + "Local");
		this.setGenericDao(dao);		
     }
	 */
	
	@BeforeClass
	public static void initJdbcProperties() {
		AbstractOpenEjbTest.initJdbcProperties();
		ctx = AbstractOpenEjbTest.getContext();	
	}
	
	
	public void initDataSource(){
		//System.out.println("**********  GenericDaoTestWithDbUnitEjb.initDataSource *******************");
		ds = OpenEjbTestUtil.initDataSourceIfNull(ctx, ds);
		if(ds==null)
			System.out.println("********  DS is null in initDataSource ********************");
		else
		  super.setDataSource(ds);
	}
	
	public static <T> T initEjbServiceIfNull(T service,String ejbJndiName){
		return OpenEjbTestUtil.initEjbServiceIfNull(ctx, service, ejbJndiName);
	}
	

}
