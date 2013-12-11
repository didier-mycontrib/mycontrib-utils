package generic.util.auth.ws;

import javax.jws.WebService;

@WebService(endpointInterface="generic.util.auth.ws.AuthManagerService")
public class AuthManagerServiceImpl implements AuthManagerService{
	
	//implémentation réelle via dao (ldap,db , xmlFile , ...)

	@Override
	public Boolean verifyAuth(String username, String password) {
		Boolean res = null;
		String validPwd = getValidPasswordForUser(username);
		if(validPwd==null)
			res=false;
		else if(validPwd.equals(password))
			res=true;
		else res=false;
		return res;
	}

	@Override
	public String getValidPasswordForUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
