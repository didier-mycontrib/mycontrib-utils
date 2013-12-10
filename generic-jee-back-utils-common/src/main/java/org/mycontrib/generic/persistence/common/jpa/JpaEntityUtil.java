package org.mycontrib.generic.persistence.common.jpa;

import java.lang.reflect.Field;

import javax.persistence.Id;

public class JpaEntityUtil<ID extends java.io.Serializable> {
	
	//return id of jpa entity (with introspection of @Id)
	public  ID getIdOfJpaEntity(Object entity){
		ID id=null;
		Class c= entity.getClass();
		Field[] fields = c.getDeclaredFields();
		for(Field f : fields){
			//String fName= f.getName();
			Id idAnnot = f.getAnnotation(Id.class);
			if(idAnnot != null){
				try {
					f.setAccessible(true); //pour pouvoir acceder au champ "private"
					id=(ID) f.get(entity);
					f.setAccessible(false);//utile ou inutile ?
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}		
		return id;
	}

}
