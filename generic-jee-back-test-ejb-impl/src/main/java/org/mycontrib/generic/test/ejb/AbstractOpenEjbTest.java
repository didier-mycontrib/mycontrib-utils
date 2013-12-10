package org.mycontrib.generic.test.ejb;

import java.util.Properties;

import javax.naming.Context;

import org.junit.BeforeClass;
import org.mycontrib.generic.test.ejb.OpenEjbTestUtil;

public abstract class AbstractOpenEjbTest  {
	
	protected static Context context=null;
	
	/*
	 * initialisation du context de test openEjb avec testDataSource.properties recherché dans le classpath
	 */
	
	@BeforeClass
	public static void initJdbcProperties() {
		try {
			java.io.InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("testDataSource.properties");
			Properties props = new Properties(); props.load(is); is.close();
			String username = props.getProperty("username");
			String password = props.getProperty("password");
			String url = props.getProperty("url");
			String driverClassName = props.getProperty("driverClassName");
			context = OpenEjbTestUtil.initializeOpenEjbEmbeddedContainer(
					driverClassName,
					url,
					username,
					password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Context getContext() {
		return context;
	}

	public static void setContext(Context context) {
		AbstractOpenEjbTest.context = context;
	}
	
	public static <T> T initEjbServiceIfNull(T service,String ejbJndiName){
		return OpenEjbTestUtil.initEjbServiceIfNull(context, service, ejbJndiName);
	}

}
