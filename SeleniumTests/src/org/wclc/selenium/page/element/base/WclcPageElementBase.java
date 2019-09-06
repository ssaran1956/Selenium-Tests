/**
 * 
 */
package org.wclc.selenium.page.element.base;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author sarans
 *
 */
public abstract class WclcPageElementBase implements WclcPageElement {
	
	 protected String 		name;
	 protected String 		locator;
	 protected String 		locatorValue;
	 protected String 		elementType;
	 protected WebElement 	webElement;
	 protected WebDriver 	driver;
	 protected String 		id;
	 protected String 		tagName;
	 protected String		xPath;

	

	/**
		 * @param name
		 * @param locator
		 * @param locatorValue
		 * @param elementType
		 */
		public WclcPageElementBase() {}
		
		public String getId() {return id;}
		public void setId(String id) {this.id = id;}
		public String getTagName() {return tagName;}
		public void setTagName(String tagName) {this.tagName = tagName;}
		public String getXPath() {return xPath;}
		public void setXPath(String path) {xPath = path;}
	@Override
	public void 		clickTableButton(int rowIndex, String buttonText){}
	@Override
	public WebDriver 	getDriver() {return driver;}
	@Override
	public String 		getElementType() {return elementType;}
	@Override
	public String 		getLocator() {return locator;}
	@Override
	public String 		getLocatorValue() {return locatorValue;}
	@Override
	public String 		getName() {return name;}
	@Override
	public int 			getTableColumnCount(){return -1;}
	@Override
	public String 		getTableColumnHeader(int columnIndex) {return null;}
	@Override
	public String 		getTableColumnValue(int rowIndex,int columnIndex){return null;}
	@Override
	public int 			getTableRawCount(){return -1;}
	@Override
	public WebElement 	getTableRow(int rowIndex){return null;}
	@Override
	public String 		getTbodyXPath() {return null;}
	@Override
	public String 		getTextValue() {return null;}
	@Override
	public String 		getThXPath() {return null;}
	@Override
	public WebElement 	getWebElement() {return webElement;}
	@Override
	public void 		sendKeys(String value) {}
	@Override
	public void 		setDriver(WebDriver driver) {this.driver = driver;}
	@Override
	public void 		setElementType(String elementType) {this.elementType = elementType;}
	@Override
	public void 		setLocator(String locator) {this.locator = locator;}
	@Override
	public void 		setLocatorValue(String locatorValue) {this.locatorValue = locatorValue;}
	@Override
	public void 		setName(String name) {this.name = name;}
	@Override
	public void 		setSelectionByIndex(int index) {}
	@Override
	public void 		setSelectionByValue(String value) {}
	@Override
	public void 		setTbodyXPath(String tbodyXPath) {}
	@Override
	public void 		setTextValue(){}
	@Override
	public void 		setTextValue(String textValue) {}
	@Override
	public void 		setThXPath(String thXPath) {}
	@Override
	public void 		setWebElement(WebElement webElement) {this.webElement = webElement;}
	@Override
	public void 		sortTable(String sortButtonText){}
	
	@Override
	public void 		executeEvent(String eventName) {
		try {
			Method method = this.webElement.getClass().getMethod(eventName);
			method.invoke(this.webElement);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	protected boolean isNumber(String value){
		boolean ret = false;
		try {
			Integer.parseInt(value);
			ret = true;
		} catch (NumberFormatException e) {
			
		}
		return ret;
	}

}
