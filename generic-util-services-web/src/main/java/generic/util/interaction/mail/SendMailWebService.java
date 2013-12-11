package generic.util.interaction.mail;

import javax.jws.Oneway;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface SendMailWebService {

	@Oneway
	public void sendNotification(@WebParam(name="subject")String subject,
			                     @WebParam(name="message")String message,
			                     @WebParam(name="to")String to,
			                     @WebParam(name="from")String from);
	@Oneway
	public void sendRequestMsg(@WebParam(name="subject")String subject,
								@WebParam(name="message")String message,
								@WebParam(name="requestAddress")String requestAddress,
								@WebParam(name="corrData")String corrData,
								@WebParam(name="responseAddress")String responseAddress,
								@WebParam(name="from")String from);
	
}
