package org.mycontrib.generic.import_export.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class XmlDbImportExport {
	
	protected DataSource dataSource;
	private IDataSet databaseDataSet=null;
	private IDataSet importXmlDataSet=null;
	
	
	public void extractActualDataSetInDatabase() throws Exception{
		 //databaseTester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver","jdbc:hsqldb:sample", "sa", "");
		IDatabaseTester databaseTester = new DataSourceDatabaseTester(this.dataSource);    	
		this.databaseDataSet = databaseTester.getConnection().createDataSet();	
	}
	
	public void injectDataSetInDatabase() throws Exception{
		if(importXmlDataSet!=null){
		IDatabaseTester databaseTester = new DataSourceDatabaseTester(this.dataSource);    	
		databaseTester.setDataSet( this.importXmlDataSet );
		databaseTester.onSetup();// will call default setUpOperation	(clean_insert by default)
		}
		else{
			System.err.println("importXmlDataSet is null in injectDataSetInDatabase() , importDatabaseDataSet() must be called before ");
		}

	}
	

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	//pour test/debug
	public void displayDatabaseDataSet(){
		try {			
			for(String s : this.databaseDataSet.getTableNames())
				System.out.println("table:" +s);
		} catch (DataSetException e) {
			e.printStackTrace();
		}
	}
	
	public void exportDatabaseDataSet(String fileName){
		try {
			File outputFile = new File(fileName);
			OutputStream outputFileStream = new FileOutputStream(outputFile);
			this.exportDatabaseDataSet(outputFileStream);
			outputFileStream.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exportDatabaseDataSet(OutputStream os){
		if(databaseDataSet==null)
			System.err.println("databaseDataSet is null in exportDatabaseDataSet() , extractActualDataSetInDatabase() must be called before ");
		try {						
			FlatXmlDataSet.write(databaseDataSet, os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void importDatabaseDataSet(InputStream is){
		try {			
			//Bug : ok ou pas selon ordre des lignes du xml 
			//      integrite (foreign key)
			this.importXmlDataSet = new FlatXmlDataSet(is);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void importDatabaseDataSet(String fileName){
		try {
			File inputFile = new File(fileName);
			InputStream inputFileStream = new FileInputStream(inputFile);
			this.importDatabaseDataSet(inputFileStream);
			inputFileStream.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//ulterieurement import d'une partie supplementaire (fusion) ???
	
	

}
