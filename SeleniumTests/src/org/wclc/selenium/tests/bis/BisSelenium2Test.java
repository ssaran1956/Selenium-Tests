/**
 * 
 */
package org.wclc.selenium.tests.bis;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.wclc.selenium.navigation.bis.BisSeleniumNavigator;


/**
 * @author sarans
 *
 */
public class BisSelenium2Test {
	
	private static final String TASKLIST = "tasklist";
	private static final String KILL = "taskkill /F /IM ";

	/**
	 * @param args
	 */
	
	
	

	public static void main(String[] args) {
		BisSelenium2Test.kilDriverProcesses();
		//BisSeleniumNavigator navigator1 = new BisSeleniumNavigator();
		//BisSeleniumNavigator navigator2 = new BisSeleniumNavigator();
		//BisSeleniumNavigator navigator3 = new BisSeleniumNavigator();
		
		//navigator1.start();
		//navigator2.start();
		//navigator3.start();
		//navigator.executeNavigation("navigateToMistrnPage");
		//navigator.destroy();
		//List<BisSeleniumNavigator> navigatorlist = new ArrayList<BisSeleniumNavigator>(0);
		int threadNum = 0;
		for(int index = 1;index <= 2; index++) {
			BisSeleniumNavigator bsnThread = new BisSeleniumNavigator();
			bsnThread.setName("thread"+index);
			bsnThread.start();
		}
	}
	
	private static void kilDriverProcesses(){

		try {
			if(BisSelenium2Test.isProcessrunning("IEDriverServer.exe")){
				BisSelenium2Test.killProcess("IEDriverServer.exe");
			}
			if(BisSelenium2Test.isProcessrunning("chromedriver.exe")){
				BisSelenium2Test.killProcess("chromedriver.exe");
			}
			if(BisSelenium2Test.isProcessrunning("operadriver.exe")){
				BisSelenium2Test.killProcess("operadriver.exe");
			}
			if(BisSelenium2Test.isProcessrunning("geckodriver.exe")){
				BisSelenium2Test.killProcess("geckodriver.exe");
			}
			if(BisSelenium2Test.isProcessrunning("conhost.exe")){
				BisSelenium2Test.killProcess("conhost.exe");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static boolean isProcessrunning(String processName) throws Exception{
		boolean ret = false;
		Process p = Runtime.getRuntime().exec(TASKLIST);
		 BufferedReader reader = new BufferedReader(new InputStreamReader(
		   p.getInputStream()));
		 String line;
		 while ((line = reader.readLine()) != null) {
			 if (line.contains(processName)) {
				 ret = true;
			 }
		 }
		return ret;
	}
	
	private static void killProcess(String processName) throws Exception{
		Process p = Runtime.getRuntime().exec(KILL + processName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				   p.getInputStream()));
				 String line;
				 while ((line = reader.readLine()) != null) {
					 if (line.contains(processName)) {
						 System.out.println(line);
					 }
				 }
	}

}
