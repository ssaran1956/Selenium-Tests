/**
 * 
 */
package org.wclc.selenium.tests;

import java.io.File;

import org.openqa.selenium.WebDriver;

/**
 * @author sarans
 *
 */
public enum SeleniumDrivers {
	IE("org.openqa.selenium.ie.InternetExplorerDriver","IE","IEDriverServer.exe","webdriver.ie.driver"),
	CHROME("org.openqa.selenium.chrome.ChromeDriver","Chrome","chromedriver.exe","webdriver.chrome.driver"),
	OPERA("org.openqa.selenium.opera.OperaDriver","Opera","operadriver.exe","webdriver.opera.driver"),
	EDGE("org.openqa.selenium.edge.EdgeDriver","Edge","msedgedriver.exe","webdriver.edge.driver"),
	FIREFOX("org.openqa.selenium.firefox.FirefoxDriver","Gecko","geckodriver.exe","webdriver.gecko.driver"),
	GECKO("","Gecko","geckodriver.exe",""),
	SAFARI("org.openqa.selenium.safari.SafariDriver","Safari","","webdriver.safari.driver");
	
	private String driverClass;
	private String webDriverServerLocation;
	private final String locationase = "D:/Development/Selenium/DriverServers/";
	private String webDriverServerName;
	private String systemProperty;
	
	private SeleniumDrivers(String driverClass,String webDriverServerLocation,String webDriverServerName,String systemProperty) {
		this.driverClass = driverClass;
		this.webDriverServerLocation = webDriverServerLocation;
		this.webDriverServerName = webDriverServerName;
		this.systemProperty = systemProperty;
	}
	
	public WebDriver getWebDriver(String name) throws Throwable {
		WebDriver driver = this.valueOf(name).getWebDriver();
		return driver;
		
	}
	
	public WebDriver getWebDriver() throws Throwable {
		
		StringBuffer wdLocation = new StringBuffer();
		wdLocation.append(locationase)
		.append(webDriverServerLocation).append("/")
		.append(this.webDriverServerName);
		File file = new File(wdLocation.toString());
		System.setProperty(this.systemProperty, file.getAbsolutePath());
		
		Class driverClass = Class.forName(this.driverClass);
		WebDriver driver = (WebDriver) driverClass.newInstance();
		return driver;
	}
}
