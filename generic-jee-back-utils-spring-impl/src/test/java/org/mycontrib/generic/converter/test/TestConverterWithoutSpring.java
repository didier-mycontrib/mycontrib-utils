package org.mycontrib.generic.converter.test;

import java.util.Collection;

import org.junit.Test;
import org.mycontrib.generic.converter.GenericBeanConverter;
import org.mycontrib.generic.converter.common.DozerGenericBeanConverter;
import org.mycontrib.generic.converter.test.dto.CompteDto;
import org.mycontrib.generic.converter.test.dto.OperationDto;
import org.mycontrib.generic.converter.test.entity.CompteEntity;
import org.mycontrib.generic.converter.test.entity.OperationEntity;


public class TestConverterWithoutSpring {
	

	private GenericBeanConverter converter = null;
	
	public TestConverterWithoutSpring(){
		setUp();
	}
	
	public void setUp() {
		converter = new DozerGenericBeanConverter();
	}
	
	
	@Test
	public void test_converter(){
		CompteEntity cpt = new CompteEntity (1,"compte 1",150.50);
		    cpt.addOperation(new OperationEntity(1,"achat 1",-45.0));
		    cpt.addOperation(new OperationEntity(2,"achat 2",-5.0));
		CompteDto cptDto=null;
		cptDto = converter.convert(cpt, CompteDto.class);
		System.out.println(cptDto);
		    for(OperationDto opDto : cptDto.getOperations()){
		    	System.out.println("\t"+opDto.toString());
		    }
        System.out.println("*****************");	
       Collection<OperationDto> listeOpDto = converter.convertCollection(cpt.getOperations(),OperationDto.class);
       for(OperationDto opDto : listeOpDto){
	    	System.out.println("\t"+opDto.toString());
	    }
       
       
        
	}
	
	



}
