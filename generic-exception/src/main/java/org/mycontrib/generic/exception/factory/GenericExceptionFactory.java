package org.mycontrib.generic.exception.factory;

import java.util.ArrayList;
import java.util.List;

import org.mycontrib.generic.classic.exception.ConflictException;
import org.mycontrib.generic.classic.exception.DeniedException;
import org.mycontrib.generic.classic.exception.InternalException;
import org.mycontrib.generic.classic.exception.InvalidValueException;
import org.mycontrib.generic.classic.exception.NoAccessException;
import org.mycontrib.generic.classic.exception.NotExistException;
import org.mycontrib.generic.classic.exception.TechnicalException;
import org.mycontrib.generic.exception.GenericException;
import org.mycontrib.generic.exception.conversion.helper.BasicGenericExceptionConverterHelper;
import org.mycontrib.generic.exception.conversion.helper.GenericExceptionConverterHelper;
import org.mycontrib.generic.exception.type.GenericExceptionType;

/**
 * 
 * GenericExceptionFactory (avec WithLogGenericExceptionFactory comme sous classe)
 * --> utilisation du design pattern "patron de methode" pour eventuelle pré-acion de log ou ... 
 *
 */

public class GenericExceptionFactory {
	
	private List<GenericExceptionConverterHelper> listeConverterHelper =
			new ArrayList<GenericExceptionConverterHelper>();
	
    public GenericExceptionFactory(){
    	addExceptionConverterHelper(new BasicGenericExceptionConverterHelper());
	}
    
    public void addExceptionConverterHelper(GenericExceptionConverterHelper helper){
    	helper.setGenericExceptionFactory(this);
    	listeConverterHelper.add(helper);
    }
    
    public GenericExceptionFactory withExConverterHelper(GenericExceptionConverterHelper helper){
    	addExceptionConverterHelper(helper);
    	return this; //pour pouvoir enchainer les appels (lors de la construction ou ...)
    }
	
	/*
	 * méthodes de fabrication des différentes variantes de GenericException
	 */
    
    /*-------------- generic with type ------------------------------------------ */
	
	public GenericException newGenericException(GenericExceptionType type,String msg){
		doPreAction("["+type.name()+ "] " +msg);
		return new GenericException(type,msg);
	}	
	
	public GenericException newGenericException(GenericExceptionType type,String msg,Throwable cause){
		doPreAction("["+type.name()+ "] " +msg,cause);
		return new GenericException(msg,cause);
	}	
	
	public GenericException newGenericException(GenericExceptionType type,Throwable cause){
		doPreAction(cause);
		return new GenericException(cause);
	}

	/*-------------- internal  ------------------------------------------ */
	
	public GenericException newInternalException(String msg){
		doPreAction("[INTERNAL] "+msg);
		return new InternalException(msg);
	}
	
	public GenericException newInternalException(String msg,Throwable cause){
		doPreAction("[INTERNAL] "+msg,cause);
		return new InternalException(msg,cause);
	}
	
	public GenericException newInternalException(Throwable cause){
		doPreAction(cause);
		return new InternalException(cause);
	}
	
/*-------------- technical  ------------------------------------------ */
	
	public GenericException newTechnicalException(String msg){
		doPreAction("[TECHNICAL] "+msg);
		return new TechnicalException(msg);
	}
	
	public GenericException newTechnicalException(String msg,Throwable cause){
		doPreAction("[TECHNICAL] "+msg,cause);
		return new TechnicalException(msg,cause);
	}
	
	public GenericException newTechnicalException(Throwable cause){
		doPreAction(cause);
		return new TechnicalException(cause);
	}

/*-------------- invalid value  ------------------------------------------ */
	
	public GenericException newInvalidValueException(String msg){
		doPreAction("[INVALID_VALUE] "+msg);
		return new InvalidValueException(msg);
	}
	
	public GenericException newInvalidValueException(String msg,Throwable cause){
		doPreAction("[INVALID_VALUE] "+msg,cause);
		return new InvalidValueException(msg,cause);
	}
	
	public GenericException newInvalidValueException(Throwable cause){
		doPreAction(cause);
		return new InvalidValueException(cause);
	}
	
/*-------------- no access  ------------------------------------------ */
	
	public GenericException newNoAccessException(String msg){
		doPreAction("[NO_ACCESS] "+msg);
		return new NoAccessException(msg);
	}
	
	public GenericException newNoAccessException(String msg,Throwable cause){
		doPreAction("[NO_ACCESS] "+msg,cause);
		return new NoAccessException(msg,cause);
	}
	
	public GenericException newNoAccessException(Throwable cause){
		doPreAction(cause);
		return new NoAccessException(cause);
	}
	
/*-------------- denied  ------------------------------------------ */
	
	public GenericException newDeniedException(String msg){
		doPreAction("[DENIED] "+msg);
		return new DeniedException(msg);
	}
	
	public GenericException newDeniedException(String msg,Throwable cause){
		doPreAction("[DENIED] "+msg,cause);
		return new DeniedException(msg,cause);
	}
	
	public GenericException newDeniedException(Throwable cause){
		doPreAction(cause);
		return new DeniedException(cause);
	}

/*-------------- conflict  ------------------------------------------ */
	
	public GenericException newConflictException(String msg){
		doPreAction("[CONFLICT] "+msg);
		return new ConflictException(msg);
	}
	
	public GenericException newConflictException(String msg,Throwable cause){
		doPreAction("[CONFLICT] "+msg,cause);
		return new ConflictException(msg,cause);
	}
	
	public GenericException newConflictException(Throwable cause){
		doPreAction(cause);
		return new ConflictException(cause);
	}
	
	
/*-------------- not_exist  ------------------------------------------ */
	
	public GenericException newNotExistException(String msg){
		doPreAction("[NOT_EXIST] "+msg);
		return new NotExistException(msg);
	}
	
	public GenericException newNotExistException(String msg,Throwable cause){
		doPreAction("[NOT_EXIST] "+msg,cause);
		return new NotExistException(msg,cause);
	}
	
	public GenericException newNotExistException(Throwable cause){
		doPreAction(cause);
		return new NotExistException(cause);
	}

	
	/*
	 * pré-actions eventuelles à redéfinir dans sous classe:
	 */
	public void doPreAction(String msg){
		//vide/rien ici , log ou ... dans sous classe
	}
	
	public void doPreAction(String msg,Throwable cause){
		//vide/rien ici , log ou ... dans sous classe
	}
	
	public void doPreAction(Throwable cause){
		//vide/rien ici , log ou ... dans sous classe
	}
	
	/*
	 *  convertion  d'exception java ordinaires en GenericException 
	 */

	public GenericException convertToGenericException(Exception ex){
		GenericException genEx =null;
		if(ex instanceof GenericException){
		    genEx=(GenericException)ex;
		}
		if(genEx==null)
		  for(GenericExceptionConverterHelper helper : this.listeConverterHelper){
			genEx = helper.convertToGenericException(ex);	
			if(genEx!=null) 
				break;//end of forEach loop
		  }
		if(genEx==null && ex instanceof RuntimeException){
			//default exception for Unknown type of runtime (unchecked) exception 
			genEx = this.newInternalException("[UNKNOWN INTERNAL UNCHECKED/RUNTIME EXCEPTION] ",ex);
		}
		if(genEx==null){
			//default exception for Unknown type of checked) exception 
			genEx = this.newInternalException("[UNKNOWN INTERNAL EXCEPTION] ",ex);
		}
		return genEx;
	}
	
}
