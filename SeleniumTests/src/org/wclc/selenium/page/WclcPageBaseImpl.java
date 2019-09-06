/**
 * 
 */
package org.wclc.selenium.page;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wclc.selenium.page.element.base.WclcPageElement;
import org.wclc.selenium.page.element.factory.WclcPageElementFactory;
import org.wclc.selenium.pages.bis.descriptors.DescriptorLocator;

/**
 * @author sarans
 *
 */
public abstract class WclcPageBaseImpl implements WclcPage{
	
	protected List<WclcPageElement> pageElements;
	protected WebDriver driver;
	protected String descriptorFileName;
	private Document document;
	protected String url;
	
    public List<WclcPageElement> getPageElements() {
		return pageElements;
	}

	public void setPageElements(List<WclcPageElement> pageElements) {
		this.pageElements = pageElements;
	}
	
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
	
	public WclcPageBaseImpl(WebDriver driver) {
		this();
		this.driver = driver;
	}
	public WclcPageBaseImpl(){
		String[] parts = this.getClass().getName().split("\\.");
    	StringBuffer descriptorFileNameBuffer = new StringBuffer();
    	descriptorFileNameBuffer.append(parts[parts.length -1]).append("Descriptor.xml");
    	this.descriptorFileName = descriptorFileNameBuffer.toString();
	}

	@Override
	public WebElement findElement(By by) {
		return this.driver.findElement(by);
	}
	
	protected By getByInstance(String locator, String locatorValue){
		Method method = null;
		Object byObject = null;
		try {
			method = By.class.getMethod(locator, String.class);
			byObject = method.invoke(null, locatorValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return (By)byObject;
		
	}

	protected void getWclcPageElements(){
		DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
		this.pageElements = new ArrayList<WclcPageElement>(0);
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			this.document = builder.parse(DescriptorLocator.class.getResourceAsStream(this.descriptorFileName));
			if(this.hasManu())this.parseMenu(this.document.getDocumentElement().getElementsByTagName("Menu").item(0).getChildNodes());
			NodeList nodeList = this.document.getDocumentElement().getElementsByTagName("WebElements").item(0).getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				short nt = node.getNodeType();
				if(nt != Node.ELEMENT_NODE)continue;
				this.addWclcPageElementFromNode(node);
				if(this.isTabNode(node)){
					NodeList tabContent = node.getChildNodes();
					for(int j = 0; j< tabContent.getLength(); j++){
						Node tnd = tabContent.item(j);
						if(tnd.getNodeType() != Node.ELEMENT_NODE)continue;
						
						this.addWclcPageElementFromNode(tnd);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private boolean isTabNode(Node node){
		NamedNodeMap map = node.getAttributes();
		String elementType = map.getNamedItem("elementType").getNodeValue();
		return WclcPageElementFactory.isInstanceOf(WclcPageElementFactory.TABLINK, elementType);
	}
	
	private void addWclcPageElementFromNode(Node node){
		NamedNodeMap map = node.getAttributes();
		WclcPageElement pageElement = WclcPageElementFactory.getElementInstance(map.getNamedItem("elementType").getNodeValue());
		for(int j = 0; j < map.getLength(); j++){
			Node atr = map.item(j);
			String propertyName = atr.getNodeName();
			String propertyValue = atr.getNodeValue();
			this.setProperty(pageElement, propertyName, propertyValue);
		}
		
		this.pageElements.add(pageElement);
	}
	
	private boolean hasManu(){
		boolean ret = false;
		NodeList nodeList = this.document.getDocumentElement().getElementsByTagName("Menu");
		if(nodeList != null && nodeList.getLength() >= 1)ret = true;
		return ret;
	}
	private void parseMenu(NodeList nodeList){
		
		if(nodeList == null || nodeList.getLength() == 0)return;
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			short nt = node.getNodeType();
			if(nt != Node.ELEMENT_NODE)continue;
			NamedNodeMap map = node.getAttributes();
			WclcPageElement pageElement = WclcPageElementFactory.getElementInstance(map.getNamedItem("elementType").getNodeValue());
			for(int j = 0; j < map.getLength(); j++){
				Node atr = map.item(j);
				String propertyName = atr.getNodeName();
				String propertyValue = atr.getNodeValue();
				this.setProperty(pageElement, propertyName, propertyValue);
			}

			this.populateWebElement(pageElement);
			this.pageElements.add(pageElement);
		}
	}
	
	protected void setProperty(Object sorce,String propertyName, Object propertyValue){
		String methodName = this.getAccessorMethodName("set", propertyName);
		try {
			Method set = sorce.getClass().getMethod(methodName, propertyValue.getClass());
			if(set == null){
				set = sorce.getClass().getDeclaredMethod(methodName, propertyValue.getClass());
			}
			set.invoke(sorce, propertyValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected Object getProperty(Object sorce, String propertyName){
		String methodName = this.getAccessorMethodName("get", propertyName);
		Object ret = null;
		try {
			Method get = sorce.getClass().getMethod(methodName);
			ret = get.invoke(sorce);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	protected String getAccessorMethodName(String prefix, String propertyName){
		String first  = propertyName.substring(0, 1).toUpperCase();
		String second = propertyName.substring(1);
		StringBuffer ret = new StringBuffer(prefix).append(first).append(second);
		return ret.toString();
	}
	
	protected WclcPageElement getNamedWebElement(String webElementname){
		for(WclcPageElement wpe:this.pageElements){
			if(webElementname.equals(wpe.getName())){
				return wpe;
			}
		}
		return null;
	}
	
	@Override
	public void populateFields() {
		this.getWclcPageElements();
		for(WclcPageElement pageElement:pageElements){
			this.populateWebElement(pageElement);
		}
		
	}
	
	private void populateWebElement(WclcPageElement pageElement){
		
		String locator = pageElement.getLocator();
		String locatorValue = pageElement.getLocatorValue();
		pageElement.setDriver(this.driver);
		try {
			By by = this.getByInstance(locator, locatorValue);
			WebDriverWait wait = new WebDriverWait(this.driver, 1);
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			pageElement.setWebElement(element);
		} catch (Exception e) {

		}
		
	}

	@Override
	public void navigate() {
		if(this.getUrl() != null){
			driver.navigate().to(this.getUrl());
			this.populateFields();
		}else{
			throw new RuntimeException("Ilegal State: url is not set.");
		}
		
	}

	@Override
	public void navigate(String url) {
		this.url = url;
		this.navigate();
	}

	@Override
	public void sendKeys(String webElementname, String value) {
		WclcPageElement wpe = this.getNamedWebElement(webElementname);
		wpe.sendKeys(value);
	}

	@Override
	public void executeEvent(String webElementname, String eventName) {
		WclcPageElement wpe = this.getNamedWebElement(webElementname);
		System.err.println("PAGE = "+this.getClass().getName() + " ELEMENT NAME = "+webElementname + " EVENT NAME = "+eventName);
		wpe.executeEvent(eventName); 
	}
	
	@Override
	public void populatSubMenu(String clickedMenuElementName){
		
		String tagName = this.getMenuTagName(clickedMenuElementName);
		Element docElement = this.document.getDocumentElement();
		NodeList byTagName = docElement.getElementsByTagName(tagName);
		Node base = byTagName.item(0);
		NodeList nodes = (base == null)?null:base.getChildNodes();
		this.parseMenu(nodes);
	}
	
	public WclcPageElement getWclcPageElementByName(String name){
		for(WclcPageElement element:this.pageElements){
			if(element.getName().equals(name)){
				return element;
			}
		}
		return null;
		
	}
	
	private String getMenuTagName(String clickedMenuElementName){
		String first = clickedMenuElementName.substring(0,1).toUpperCase();
		String second = clickedMenuElementName.substring(1);
		String sufix = "Menu";
		StringBuffer ret = new StringBuffer(first)
		.append(second)
		.append(sufix);
		return ret.toString();
	}
	
	
}
