package generic.util.habilitation.ws;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

/*même interface que HabilitationManager mais avec @WebService et @WebParam */
@WebService
public interface HabilitationManagerService {
	public List<String> getUserRoleList(@WebParam(name="username")String userName,
			                            @WebParam(name="contextHint")String contextHint);
	public boolean hasRole(@WebParam(name="username")String username,
			               @WebParam(name="role")String role,
			               @WebParam(name="contextHint")String contextHint);
}
