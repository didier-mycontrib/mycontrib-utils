package org.mycontrib.generic.converter.common;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Named;
import javax.inject.Qualifier;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.mycontrib.generic.converter.GenericBeanConverter;
//import javax.inject.Named;



/* exemple de fichier src/dozerBeanMapping.xml
   
<?xml version="1.0" encoding="UTF-8"?>
<mappings xmlns="http://dozer.sourceforge.net"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://dozer.sourceforge.net
          http://dozer.sourceforge.net/schema/beanmapping.xsd">
          
  <mapping> 
    <class-a>tp.entity.Compte</class-a>
    <class-b>tp.dto.CompteDto</class-b>   
    <field>
      <a>numCpt</a>
      <b>numero</b>
    </field>
  </mapping>  
  
  <mapping> 
    <class-a>tp.entity.Operation</class-a>
    <class-b>tp.dto.OperationDto</class-b>   
    <field>
      <a>date</a>
      <b>dateOp</b>
    </field>
  </mapping> 
  

</mappings>
*/

/** 
 * @author Didier
 *
 * MyDozerBeanConverter = classe d'implementation de GenericBeanConverter
 * basée sur la technologie "Dozer" (necessite "dozer...jar" + ...)
 * ---
 * NB: cette classe peut s'utiliser avec ou sans CDI:
 * 
 * Sans CDI  et: 
 *     GenericBeanConverter beanConverter = new MyDozerBeanConverter();
 *     avec  "dozerBeanMapping.xml" comme nom par défaut de fichier de config "dozer" facultatif
 *          ou bien
 *     GenericBeanConverter beanConverter = new MyDozerBeanConverter({"dozer1.xml","dozer2.xml"});     
 *     puis:
 *     XxxDto xDto = beanConverter.convert(objX,XxxDto.class);
 * 
 * Avec CDI:
 * 
 *    @Inject
 *    private GenericBeanConverter beanConverter;
 *    puis directement:
 *     XxxDto xDto = beanConverter.convert(objX,XxxDto.class);
 **/

//@Named here is not a good idea --> <bean id="..." class="..." /> is better
public class DozerGenericBeanConverter implements GenericBeanConverter {
	
	private Mapper mapper = null;
	private String[] myMappingFiles = { };//{} ou {"dozerBeanMapping.xml" } ; //par defaut
	
	/**
	 * constructeur par defaut (avec "dozerBeanMapping.xml" par defaut)
	 */
	public DozerGenericBeanConverter(){
		initDozer();
	}
	
	/**
	 * constructeur avec fichier(s) de mapping "dozer" paramétrable
	 * @param myMappingFiles = tableau des noms de fichiers '.xml' pour "dozer"
	 */
	public DozerGenericBeanConverter(String[] myMappingFiles){
		this.myMappingFiles=myMappingFiles;
		initDozer();
	}
	
	/**
	 * tableau des fichiers xml configurant le mapping pour dozer
	 * valeur par defaut = 	{ "dozerBeanMapping.xml" }
	 * @return tableau de noms de fichiers xml
	 */
	public String[] getMyMappingFiles() {
		return myMappingFiles;
	}

    /**
     * @param myMappingFiles = nouveau tableau de fichiers de config xml (dozer)
     * NB: appeler ensuite initDozer() pour (re)-initialiser Dozer. 
     */
	public void setMyMappingFiles(String[] myMappingFiles) {
		this.myMappingFiles = myMappingFiles;		
	}



	public void initDozer(){
		mapper = new DozerBeanMapper();				
		List<String> myMappingFilesList = new ArrayList<String>();
		for(String mf: this.myMappingFiles){
			myMappingFilesList.add(mf);
		}			
		if(myMappingFilesList.isEmpty()){
			//test if "dozerBeanMapping.xml" exists in classpath
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("dozerBeanMapping.xml");
			if(is!=null)
				myMappingFilesList.add("dozerBeanMapping.xml");//default dozer mapping file (if exists)
		}
		((DozerBeanMapper)mapper).setMappingFiles(myMappingFilesList);	
	}
	
	@Override
	public <T> T convert(Object o,Class<T> destC){
		return (o==null)?null:mapper.map(o, destC);
	}
	
	@Override
	public  <T1,T2> Collection<T2> convertCollection(Collection<T1> col1,Class<T2> destC){
	   if(col1==null)
		   return null;
	 
		java.util.ArrayList<T2> col2= new java.util.ArrayList<T2>();
 	   for(T1 o1: col1){
 		   col2.add(mapper.map(o1,destC));
 	   }
 	   return col2;
    }

	@Override
	public <T1, T2> List<T2> convertList(List<T1> liste1, Class<T2> destC) {
		if(liste1==null)
			   return null;
		
		java.util.ArrayList<T2> liste2= new java.util.ArrayList<T2>();
	 	   for(T1 o1: liste1){
	 		  liste2.add(mapper.map(o1,destC));
	 	   }
	 	   return liste2;
	}


}
