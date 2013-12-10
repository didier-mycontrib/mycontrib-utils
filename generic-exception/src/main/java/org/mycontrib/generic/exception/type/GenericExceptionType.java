package org.mycontrib.generic.exception.type;

public enum GenericExceptionType {
	
	INTERNAL /* generic internal (unknown) without precision , unknown null pointer , ....*/,
	TECHNICAL /* class not found , LazyException , Syntax error in SQL , ...  , 
	              bug , incompatible cast , ...*/ /*  stack overflow , no more memory cannot be catch / Throwable / NotException */,
	NO_ACCESS /* no file , no database connexion , no web service connexion ,
	            timeout , (temporary) unavailable , ... */ ,
	DENIED /* wrong username or password , no privilege , no permission, forbidden , .... */, 
	CONFLICT /* duplicate primary key during insert , ... */ ,
	NOT_EXIST  /* entity not found with this id , not exist during deletion, null , .... */ , 
	INVALID_VALUE /* invalid value (wrong type , out of bounded , rejected , ...  )  , ... */,
    OTHER  /* other type of exception (other category)  */
}
