package temp.test;

import java.util.Iterator;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

public class TestAppJAMon2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 Monitor mon=null;
	        for (int i=1; i<=5; i++) {
	            mon = MonitorFactory.start("main");
	            try {
					Thread.sleep(100 + i);
					m1(i);
				} catch (Exception e) {
					e.printStackTrace();
				}
				mon.stop();
	        }
	    System.out.println(MonitorFactory.getReport());
	}
	
	public static void m1(int n){
		Monitor mon,subMon=null;
		MonitorFactory.add("Free Memory", "MB", Runtime.getRuntime().freeMemory() / 1000000); 
		MonitorFactory.add("m1_nb_call","count", 1);//pour incrementer le nombre d'appel (qui sera dans les stats : getReport() 
		// nb : stat "active" = nombre d'appels concurents (souvent 0 ou 1 , sauf si plusieurs theads )
		//mon = MonitorFactory.startPrimary("m1"); //seulement utile pour future correlation entre un primary et les autres
		mon = MonitorFactory.start("m1");
		subMon = MonitorFactory.start("m1."+n);
            try {
				Thread.sleep(10 *n);
			} catch (Exception e) {
				e.printStackTrace();
			}
            subMon.stop();
			mon.stop();
        }

}
