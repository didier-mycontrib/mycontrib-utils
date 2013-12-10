package org.mycontrib.generic.test.dbunit.util;

import java.io.File;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

public class BasicDBUnitHelper {
	
	protected String initialDataSetFileName="initialDataSet.xml";//by default
	protected String dataSetDirectory="src/test/resources/dataset";//by default for maven
	protected FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
	protected IDatabaseTester databaseTester;
	
	protected IDataSet initialDataSet=null;
	
	protected DataSource dataSource;
	

	public DataSource getDataSource() {
		return dataSource;
	}
	
	//@Autowired à placer sur le setDataSource à redéfinir
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}


	
	public void reInitDataBaseFromInitialDataSet() throws Exception{
			//databaseTester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver","jdbc:hsqldb:sample", "sa", "");
		   	this.databaseTester = new DataSourceDatabaseTester(this.dataSource);
        	
			// initialize your dataset here
		   	String initialDataSetPathName = this.dataSetDirectory+"/"+this.initialDataSetFileName;
			this.initialDataSet = flatXmlDataSetBuilder.build(new File(initialDataSetPathName));
			databaseTester.setDataSet( this.initialDataSet );
			
			//Connection cn = databaseTester.getConnection().getConnection();
			//Statement s = cn.createStatement();			 
			  //s.execute("set global foreign_key_checks=0"); 
			  // fonctionne avec Mysql , mais peut etre pas avec les autres bases
			                          
			  //on desactive temporairement la prise en compte des contraintes 
			  // d'integrite en base car le clean_insert de dbunit pose quelquefois
			  // des problemes (selon ordre des element dans initialDataSet.xml)
			
			     databaseTester.onSetup();// will call default setUpOperation	(clean_insert by default)
			     
			  //s.execute("set global foreign_key_checks=1"); // reactivation des contraintes
           //s.close();
		   //cn.close();	
	}
	
	/*
	 public void end(){
		try {
			// will call default tearDownOperation
			databaseTester.onTearDown(); //nothing by default
		} catch (Exception e) {
			e.printStackTrace();
			TestCase.fail(e.getMessage());			
		}
	}
	*/
	
	
	public String getInitialDataSetFileName() {
		return initialDataSetFileName;
	}

	public void setInitialDataSetFileName(String initialDataSetFileName) {
		this.initialDataSetFileName = initialDataSetFileName;
	}

	public String getDataSetDirectory() {
		return dataSetDirectory;
	}

	public void setDataSetDirectory(String dataSetDirectory) {
		this.dataSetDirectory = dataSetDirectory;
	}

	

}
