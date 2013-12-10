package org.mycontrib.generic.converter;

import java.util.Collection;
import java.util.List;

/**
 * 
 * @author Didier Defrance
 * 
 * GenericBeanConverter = interface abstraite d'un convertisseur générique de JavaBean
 * (ex: Entity_persistante <--> DTO )
 * Comportement des copies (identique à celui de BeanUtils.copyProperties()):
 *     les propriétés de mêmes noms seront automatiquement recopiées d'un bean à l'autre
 *     en effectuant si besoin des conversions (ex: String <---> Integer, ...)
 * NB: si les propriétés à recopier n'ont pas les mêmes noms --> config xml (dozer)
 * 
 * NB: si l'element à convertir est null alors le resulat est null
 * 
 * implémentation recommandée: MyDozerBeanConverter 
 */
public interface GenericBeanConverter {

	/**
	 * convert() permet de convertir d'un seul coup 
	 * un JavaBean ainsi que toutes ses sous parties (sous collection, ...)
	 * 
	 * @param o = objet source à convertir
	 * @param destC = type/classe destination (ex: p.XxxDto.class)
	 * @return nouvel objet (de type destC) = résultat de la conversion
	 */
	public abstract <T> T convert(Object o, Class<T> destC);

	/**
	 * convertCollection() permet de convertir d'un seul coup une collection
	 * de JavaBean de type <T1> en une autre collection de JavaBean de type
	 * destC/T2 .
	 * 
	 * @param col1 = collection source à convertir 
	 * @param destC = type/classe destination (ex: p.XxxDto.class)
	 *                des elements de la collection cible à fabriquer
	 * @return nouvelle collection (d'objets de type destC) = résultat de la conversion
	 */
	public abstract <T1,T2> Collection<T2> convertCollection(Collection<T1> col1,Class<T2> destC);
	
	/**
	 * convertList() = idem que convertCollection mais avec java.util.List explicitement
	 */
	public abstract <T1,T2> List<T2> convertList(List<T1> liste1,Class<T2> destC);

}