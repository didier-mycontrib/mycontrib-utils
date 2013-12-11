package generic.util.interaction.mail.rest;

import generic.util.interaction.mail.SendMailWebService;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/SendMailWSRest/")
public class SendMailWSRestAdapter {
	
	private SendMailWebService sendMailWebService;

	//for Spring injection
	public void setSendMailWebService(SendMailWebService sendMailWebService) {
		this.sendMailWebService = sendMailWebService;
	}
	
	@POST
	@Path("/sendNotification")
	public Response sendNotification(@FormParam("subject")String subject,@FormParam("message") String message,
			@FormParam("to")String to,@FormParam("from")String from){
		try {
			sendMailWebService.sendNotification(subject, message, to, from);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();			
		}
		return Response.status(Status.OK).build();
	}
	
	@POST
	@Path("/sendRequestMsg")
	public Response sendRequestMsg(@FormParam("subject")String subject, @FormParam("message")String message,
			@FormParam("requestAddress")String requestAddress, @FormParam("corrData")String corrData,
			@FormParam("responseAddress")String responseAddress,@FormParam("from")String from) {	
		try {
		sendMailWebService.sendRequestMsg(subject, message, requestAddress, corrData, responseAddress, from);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();			
		}
		return Response.status(Status.OK).build();
	}
	

}
