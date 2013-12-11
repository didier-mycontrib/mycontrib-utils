package generic.util.habilitation.ws;

import java.util.List;

import javax.jws.WebService;

@WebService(endpointInterface="generic.util.habilitation.ws.HabilitationManagerService")
public class HabilitationManagerServiceImpl implements
		HabilitationManagerService {

	@Override
	public List<String> getUserRoleList(String userName, String contextHint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasRole(String username, String role, String contextHint) {
		boolean res=false;
		List<String> roleListOfUser = getUserRoleList(username,contextHint);
		if(roleListOfUser!=null)
			for(String validRole : roleListOfUser){
				if(validRole.equals(role)){
					res=true; break;
					}
			}
		return res;
	}

}
