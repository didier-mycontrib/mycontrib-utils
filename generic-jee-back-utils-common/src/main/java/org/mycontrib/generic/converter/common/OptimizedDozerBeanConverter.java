package org.mycontrib.generic.converter.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Named;

/*
 * OptimizedDozerBeanConverter est une version optimisée de DozerGenericBeanConverter
 * qui retourne l'objet original tel quel dans le cas ou la classe cible est la classe parente de l'objet source
 * Ceci peut être utile dans le cas ou une classe d'entité persistante hérite d'une classe de DTO
 * 
 * Attention, attention : dans le cas où la classe cible est la classe parente de l'objet original
 * la conversion effectuée n'est qu'au niveau du "type apparent" et pas de copie en profondeur
 * ---> certains effets de bords peuvent éventuellement survenir (selon le contexte d'utilisation) !!! 
 */
//No @Named , No @OptimizedForInheritance here , --> choice with xml config or ...
public class OptimizedDozerBeanConverter extends DozerGenericBeanConverter{
	
	//+ eventuelle future optimisation via copy constructor
	// ex: new XxxEntity(xxxDto); to called dynamically if exists to perform conversion

	@Override
	public <T> T convert(Object o, Class<T> destC) {
		if(o==null) return null;
		Class origClass = o.getClass();
		if(origClass.getSuperclass().equals(destC)){
			return (T) o;
		}
		else return super.convert(o, destC);
	}

	@Override
	public <T1, T2> Collection<T2> convertCollection(Collection<T1> col1,
			Class<T2> destC) {
		if(col1==null) return null;
		/* Remarque : l'appel à ".size()" dans le test peut (selon le contexte)
		 *            quelquefois permettre d'éviter un lazyInitialisationException */
		if(col1.size()==0) return new ArrayList<T2>();
		
		Class origClass =  col1.iterator().next().getClass(); 
		if(origClass.getSuperclass().equals(destC)){
			return (Collection) col1;
		}
		return super.convertCollection(col1, destC);
	}

	@Override
	public <T1, T2> List<T2> convertList(List<T1> liste1, Class<T2> destC) {
		
		if(liste1==null) return null;
		if(liste1.size()==0) return new ArrayList<T2>();
		/* Remarque : l'appel à ".size()" dans le test peut (selon le contexte)
		 *            quelquefois permettre d'éviter un lazyInitialisationException */
		Class origClass = liste1.get(0).getClass();
		if(origClass.getSuperclass().equals(destC)){
			return (List) liste1;
		}
		else return super.convertList(liste1, destC);
	}

}
