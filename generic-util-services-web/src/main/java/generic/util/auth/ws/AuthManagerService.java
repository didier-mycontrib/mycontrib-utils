package generic.util.auth.ws;

import javax.jws.WebParam;
import javax.jws.WebService;

/*interface identique à AuthManager mais avec @WebService et @WebParam*/
@WebService
public interface AuthManagerService {
	public Boolean verifyAuth(@WebParam(name="username")String username,@WebParam(name="password")String password);
	public String getValidPasswordForUser(@WebParam(name="username")String username);
}
