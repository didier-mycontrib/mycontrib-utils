package org.mycontrib.generic.test.ejb;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public abstract class OpenEjbTestUtil {
	
		
	private static final String TEST_OPENEJB_DS="MyDataBase_TestDS";
	//private static final String TEST_OPENEJB_DS="java:/MinibankDS"; //le prefixe java:/ de jboss pose probleme à OpenEJB
	
	
	public static <T> T initEjbServiceIfNull(Context context, T service,String ejbJndiName){
		if(service==null){
	        try{
	          service= (T) context.lookup(ejbJndiName);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		   }
		return service;
	}
	
	public static DataSource initDataSourceIfNull(Context context, DataSource ds){
		if(ds==null){
	        try{
	         ds= (DataSource) context.lookup("java:openejb/Resource/"+TEST_OPENEJB_DS);
	          if(ds==null)
	        	  System.err.println("DataSource ds=lookup('java:openejb/Resource/MyDataBase_TestDS') for OpenEjb Test is null !!!");	        	  
			}catch(Exception ex){
				ex.printStackTrace();
			}
		   }
		return ds;
	}
	
	
	
	public static Context initializeOpenEjbEmbeddedContainer(String jdbcDriver,String jdbcUrl,
			String userName, String password) throws Exception {
		
		Context context=null; // jndi context for open-ejb
			
		System.out.println("jdbcDriver=" + jdbcDriver);
		System.out.println("jdbcUrl=" + jdbcUrl);
		System.out.println("userName=" + userName);
		System.out.println("password=" + password);
		
	    Properties properties = new Properties();
	    
	    properties.put(Context.INITIAL_CONTEXT_FACTORY,
	        "org.apache.openejb.client.LocalInitialContextFactory");

	    properties.put(TEST_OPENEJB_DS, "new://Resource?type=DataSource");
	    
	    properties.put(TEST_OPENEJB_DS+".JdbcDriver",jdbcDriver);
	    properties
	        .put(TEST_OPENEJB_DS+".JdbcUrl", jdbcUrl);
	    properties.put(TEST_OPENEJB_DS+".username", userName);
	    properties.put(TEST_OPENEJB_DS+".password",password);
	    //properties.put(TEST_OPENEJB_DS+".JtaManaged", "false");
	    

	    
	    properties.put("openejb.embedded.initialcontext.close", "destroy");

	    context = new InitialContext(properties);
	    return context;
	  }
	  
}
