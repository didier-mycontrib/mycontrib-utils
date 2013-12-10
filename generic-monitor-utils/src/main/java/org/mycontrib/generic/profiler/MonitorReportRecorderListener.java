package org.mycontrib.generic.profiler;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.jamonapi.MonitorFactory;

public class MonitorReportRecorderListener  implements ServletContextListener {
	
	private String reportFileName="jamonReport.html";//default file name to generate in web context root

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// webApp stop , enregistrer MonitorFactory.getReport() de jamon dans un fichier
		ServletContext ctx = (ServletContext) arg0.getServletContext();
		String reportPathName = null;
		try {
			//essayer de lire la valeur du paramètre jamonReportLocation (si ça existe)
			/*
			 <context-param>
		        <param-name>jamonReportLocation</param-name>
		        <param-value>c:/temp/jamonReport.html</param-value>
	        </context-param>
			 */
			reportPathName = ctx.getInitParameter("jamonReportLocation");
			if(reportPathName==null)
				ctx.getRealPath(reportFileName);
			FileOutputStream fos = new FileOutputStream(reportPathName);
			java.io.DataOutputStream dos =  new DataOutputStream(fos);
			String report = MonitorFactory.getReport();
			if(report!=null){
			dos.writeChars(report);
			System.out.println("jamon html report was stored in jamonReport.html by MonitorReportRecorderListener");
			System.out.println("generated file path:" + reportPathName );
			}
			dos.close(); fos.close();
		} catch (Exception e) {	
			System.err.println("erreur MonitorReportRecorderListener:" + e.getMessage());
			//e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// webApp starting ....
		//tenir eventuellement compte de certains paramètres (context-param) pour initialiser MonitorFactory?
	}

}
