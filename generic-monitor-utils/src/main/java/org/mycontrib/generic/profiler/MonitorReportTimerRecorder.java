package org.mycontrib.generic.profiler;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import com.jamonapi.MonitorFactory;


/*exemple de parametrage spring:
 * 
  <!-- composant qui enregistre regulierement les rapports "jamon" dans un fichier sur le disk -->
	<bean id="monitorReportTimerRecorder" class="org.mycontrib.generic.profiler.MonitorReportTimerRecorder" >
			<property name="directory" value="c:/temp" />
			<property name="reportBaseFileName" value="jamon-report" /> <!--  xxx-horodatage.html -->
			<!--  <property name="max" value="????" /> --> <!-- futur param pour limiter le nombre de fichiers -->
	</bean>
	
	<bean id="scheduledTask" class="org.springframework.scheduling.timer.ScheduledTimerTask">
		<!-- wait 10 seconds before starting repeated execution -->
		<property name="delay" value="10000" />
		<!-- run every 60 seconds -->
		<property name="period" value="60000" />
		<property name="timerTask" ref="monitorReportTimerRecorder" />
    </bean>
    
    <bean id="timerFactory" class="org.springframework.scheduling.timer.TimerFactoryBean">
		<property name="scheduledTimerTasks">
		   <list>
				<ref bean="scheduledTask" />
		  </list>
		</property>
	</bean>
 */

public class MonitorReportTimerRecorder extends TimerTask {
	
	private String directory ;
	private String reportBaseFileName;
	private Boolean periodicReset;
	
	
	//methode qui sera regulierement declenchee par spring:
	public void run() {		
	try {
		if(directory==null || reportBaseFileName==null) {
				System.out.println("MonitorReportTimerRecorder: parametre directory ou reportBaseFileName manquant ");
				return;
			}
		Calendar cal = Calendar.getInstance();
		String horodatage = String.format("%4d_%2d_%2d-%2d-%2d", cal.get(Calendar.YEAR) , cal.get(Calendar.MONTH) +1 , cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));
		String pathName= directory + "/" + reportBaseFileName + "_" + horodatage + ".html";
		FileOutputStream fos = new FileOutputStream(pathName);
		java.io.DataOutputStream dos =  new DataOutputStream(fos);
		String report = MonitorFactory.getReport();
		if(this.periodicReset!=null && this.periodicReset == true){
        MonitorFactory.reset();
        //RAZ de tout les compteurs pour prochain report = prochaine periode
    }
		if(report!=null){
		dos.writeChars(report);
		System.out.println("jamon html report was stored in " + pathName + " by MonitorReportTimerRecorder");
		}
		dos.close(); fos.close();
	} catch (Exception e) {
		e.printStackTrace();
	 }
	}

	public String getDirectory() {
		return directory;
	}



	public void setDirectory(String directory) {
		this.directory = directory;
	}



	public String getReportBaseFileName() {
		return reportBaseFileName;
	}



	public void setReportBaseFileName(String reportBaseFileName) {
		this.reportBaseFileName = reportBaseFileName;
	}

	public Boolean getPeriodicReset() {
		return periodicReset;
	}

	public void setPeriodicReset(Boolean periodicReset) {
		this.periodicReset = periodicReset;
	}

  
	

}
