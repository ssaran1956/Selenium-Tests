package org.wclc.selenium.page.element.base;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface WclcPageElement {

	
	public abstract void 		clickTableButton(int rowIndex,String buttonText);
	public abstract void 		executeEvent(String eventName);
	
	public abstract WebDriver 	getDriver();
	public abstract String 		getElementType();
	public abstract String 		getLocator();
	public abstract String 		getLocatorValue();
	public abstract String 		getName();
	public abstract int 		getTableColumnCount();
	public abstract String 		getTableColumnHeader(int columnIndex);
	public abstract String 		getTableColumnValue(int rowIndex,int columnIndex);
	public abstract int 		getTableRawCount();
	public abstract WebElement 	getTableRow(int rowIndex);
	public abstract String 		getTbodyXPath();
	public abstract String 		getTextValue();
	public abstract String 		getThXPath();
	public abstract WebElement 	getWebElement();
	public abstract String 		getId();
	public abstract String 		getTagName();
	public abstract String  	getXPath();
	
	public abstract void 		sendKeys(String value);
	public abstract void 		setDriver(WebDriver driver);
	public abstract void 		setElementType(String elementType);
	public abstract void 		setLocator(String locator);
	public abstract void 		setLocatorValue(String locatorValue);
	public abstract void 		setName(String name);
	public abstract void 		setSelectionByIndex(int index);
	public abstract void 		setSelectionByValue(String value);
	public abstract void 		setTbodyXPath(String tbodyXPath); 
	public abstract void 		setTextValue();
	public abstract void 		setTextValue(String textValue);
	public abstract void 		setThXPath(String thXPath);
	public abstract void 		setWebElement(WebElement webElement);
	public abstract void 		sortTable(String sortButtonText);
	public abstract void 		setId(String id);
	public abstract void 		setTagName(String tagName);
	public abstract void 		setXPath(String path);

}