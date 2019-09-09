/**
 * 
 */
package org.wclc.selenium.navigation.bis;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wclc.selenium.navigation.bis.descriptor.NavigatorLocator;
import org.wclc.selenium.page.WclcGenericPage;
import org.wclc.selenium.page.WclcPage;
import org.wclc.selenium.page.WclcPageBaseImpl;
import org.wclc.selenium.page.element.base.WclcPageElement;
import org.wclc.selenium.page.element.base.WclcPageElementBase;
import org.wclc.selenium.page.element.checker.WclcChecker;
import org.wclc.selenium.page.element.factory.WclcPageElementFactory;
import org.wclc.selenium.pages.bis.BISLoginPage;
import org.wclc.selenium.pages.bis.BISStartPage;
import org.wclc.selenium.pages.bis.BisPagesLocator;
import org.wclc.selenium.pages.factory.WclcPageFactory;
import org.wclc.selenium.tests.SeleniumDrivers;
import org.wclc.selenium.tests.bis.descriptors.TestDescriptorLocator;



/**
 * @author sarans
 *
 */

@SuppressWarnings({"unused","unchecked"})
public class BisSeleniumNavigator extends Thread{
	
//	private boolean byPassWebGate = false;
	private String url;
	private Map<String,WclcPageBaseImpl> pagesMap = new HashMap<String,WclcPageBaseImpl>(0);
	private WebDriver driver;
	private String webDriverClassName;
	private Document document;
	private static final String BIS_PAGES = BisPagesLocator.class.getPackage().getName();
	private boolean populateSubMenu = false;
	private String  perentMenuItemName;
	private WclcPage pageWithMenu;
	
	public void run() {
		String documentFileName = "Navigation.xml";
//		this.document = this.getDocument(NavigatorLocator.class, documentFileName);
		this.document = this.getDocument(documentFileName);
		this.webDriverClassName = this.document.getDocumentElement().getAttributes().getNamedItem("web-driver").getNodeValue();	
//		this.executeNavigations();//"navigateToLoginPage");//"navigateToMistrnPage");
		
		this.executeThreadNavigation();
	}
	
	public BisSeleniumNavigator(){
//		String documentFileName = "Navigation.xml";
//		this.document = this.getDocument(NavigatorLocator.class, documentFileName);
//		this.webDriverClassName = this.document.getDocumentElement().getAttributes().getNamedItem("web-driver").getNodeValue();	
	}
	
	public void destroy(){
		if(this.driver != null)this.driver.quit();
	}
	
	public void executeNavigation(String navigationId){

		try {
			Element documentElement = document.getDocumentElement();
			if(this.driver == null)this.createWebDriver();
			NodeList nodeList = documentElement.getElementsByTagName("Navigation");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				String id = node.getAttributes().getNamedItem("id").getNodeValue();
				if(navigationId.equals(id)){
					this.executeNavigation(node);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void executeThreadNavigation() {

		try {
			
			String threadName = this.getName();
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expr = xpath.compile("//ThreadNavigations[@name=\""+threadName+"\"]/Navigation");
			
			NodeList nodeListThread = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
			if(nodeListThread == null || nodeListThread.getLength() == 0)return;
			if(this.driver == null)this.createWebDriver();
			NodeList nodeList = nodeListThread;//.item(0).getChildNodes();
			int sze = nodeList.getLength();
			for (int i = 0; i < sze; i++) {
				Node node = nodeList.item(i);
//				if(!"Navigation".equals(node.getNodeName()))continue;
				if(node != null && node.getNodeType() == Node.ELEMENT_NODE){
					//String id = node.getAttributes().getNamedItem("id").getNodeValue();
					this.executeThreadNavigation(node);
				}
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void executeNavigations(){

		try {
			Element documentElement = document.getDocumentElement();
			
			
//			String threadName = this.getName();
//			XPathFactory xPathfactory = XPathFactory.newInstance();
//			XPath xpath = xPathfactory.newXPath();
//			XPathExpression expr = xpath.compile("//ThreadNavigations[@name=\""+threadName+"\"]");
//			
//			NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
//			if(nodeList == null || nodeList.getLength() == 0)return;
			if(this.driver == null)this.createWebDriver();
//			this.loginAndGoToWelcomePage();
			NodeList nodeList = documentElement.getElementsByTagName("Navigation");
//			nodeList = nodeList.item(0).getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				String id = node.getAttributes().getNamedItem("id").getNodeValue();
					this.executeNavigation(node);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
	
//	private void loginAndGoToWelcomePage() throws Throwable {
//		XPathFactory xPathfactory = XPathFactory.newInstance();
//		XPath xpath = xPathfactory.newXPath();
//		XPathExpression expr = xpath.compile("//Navigation[@id=\"navigateToLoginPageThenToWelcomePage\"]");
//		NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
//		for (int i = 0; i < nodeList.getLength(); i++) {
//			Node node = nodeList.item(i);
//			String id = node.getAttributes().getNamedItem("id").getNodeValue();
//				this.executeNavigation(node);
//		}
//	}
	
//	private void navigateToThreadTests(NodeList nodeList) throws Throwable {
//		String threadName = this.getName();
//		XPathFactory xPathfactory = XPathFactory.newInstance();
//		XPath xpath = xPathfactory.newXPath();
//		XPathExpression expr = xpath.compile("//ThreadNavigations[@name=\""+threadName+"\"]/Navigation");
//		
//		NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
//		if(nodeList == null || nl.getLength() == 0)return;
//		if(this.driver == null)this.createWebDriver();
//	}
	
	public void navigate(String navigationId){
		DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
		List<WclcPageElementBase> pageElements = new ArrayList<WclcPageElementBase>(0);
		try {
			Element documentElement = this.document.getDocumentElement();
			NodeList nodeList = documentElement.getElementsByTagName("Navigate");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if(!this.getNodeId(node).equals(navigationId)){
					continue;
				}else{
					this.navigate(node);
					Node endPoint = this.getNode("EndPoint", node);
					if(endPoint != null) {
						this.executeTest(endPoint);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void checkResults(Node test){
		Node otwes = this.getNode("Outputs", test);
		if(otwes == null)return;
		NodeList outputs = otwes.getChildNodes();
		for(int i=0;i<outputs.getLength();i++){
			Node nde = outputs.item(i);
			if(Node.ELEMENT_NODE == nde.getNodeType()){
				
				String nodeName = nde.getNodeName();
				if("WebElement".equals(nodeName)){
					String webElementName = nde.getAttributes().getNamedItem("name").getNodeValue();
					WclcPageElement wpe = this.pagesMap.get(this.pagesMap.keySet().iterator().next()).getWclcPageElementByName(webElementName);
					WclcChecker checker = WclcPageElementFactory.getCheckerInstance(wpe);
					checker.check(nde.getChildNodes());
				}
			}
		}
	}
	
	private void clickTableButton(Node webElementNode){
		NodeList checks = webElementNode.getChildNodes();
		String webElementName = webElementNode.getAttributes().getNamedItem("name").getNodeValue();
		String rowId = webElementNode.getAttributes().getNamedItem("rowId").getNodeValue();
		String buttonText = webElementNode.getAttributes().getNamedItem("buttonText").getNodeValue();
		int rowIndex = Integer.parseInt(rowId);
		WclcPageElement wpe = this.pagesMap.get(this.pagesMap.keySet().iterator().next()).getWclcPageElementByName(webElementName);
		wpe.clickTableButton(rowIndex, buttonText);//driver,
	}
	
	private String createUrl(Node node){
		NodeList children = node.getChildNodes();
		String port = null;
		String host = null;
		String protocol = null;
		String path = null;
		String arguments = null;
		Node urlNode = null;
		
		for (int index = 0; index < children.getLength(); index++) {
			Node nde = children.item(index);
			String name = nde.getNodeName();
			//if(name.equals("Url") && !this.byPassWebGate){
			//	urlNode = nde;
			//}else if(name.equals("UrlByPass") && this.byPassWebGate){
//				urlNode = nde;
			//}
			if(name.equals("Url")){
				urlNode = nde;
				break;
			}
		}
		
		if(urlNode == null)return null;
		NamedNodeMap atrMap = urlNode.getAttributes();

		protocol 	= atrMap.getNamedItem("protocol").getNodeValue();
		host 		= atrMap.getNamedItem("host").getNodeValue();
		port 		= atrMap.getNamedItem("port").getNodeValue();
		path 		= atrMap.getNamedItem("path").getNodeValue();
		arguments = atrMap.getNamedItem("arguments").getNodeValue();
		
		StringBuffer url = new StringBuffer(protocol).append("://");
		url.append(host)
		.append(((port != null && !port.isEmpty())?":":""))
		.append((port  != null && !port.isEmpty())?port:"")
		.append("/")
		.append(path)
		.append((arguments != null && !arguments.isEmpty())?"?":"")
		.append((arguments != null && !arguments.isEmpty())?arguments:"");
		return url.toString();
	}
	
	private void createWebDriver(){
		
		try {
//			String driverServer = "D:\\JavaTools\\SELENIUM2\\IEDriverServer_x64_2.45.0\\IEDriverServer.exe";
//			//"C:/Selenium/IEDriverServer.exe"
//			File file = new File(driverServer);
//			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
//			
//			Class driverClass = Class.forName(this.webDriverClassName);
//			this.driver = (WebDriver) driverClass.newInstance();
			
			this.driver = SeleniumDrivers.valueOf(this.webDriverClassName).getWebDriver();
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	
	private void executeActions(Node test){

		Node acwes = this.getNode("Actions", test);
		if(acwes == null)return;
		NodeList actions = acwes.getChildNodes();
		for (int index = 0; index < actions.getLength(); index++) {
			Node action = actions.item(index);
			if(Node.ELEMENT_NODE == action.getNodeType() && "WebElement".equals(action.getNodeName())){
				String weName  = action.getAttributes().getNamedItem("name").getNodeValue();
				String weEventName = action.getAttributes().getNamedItem("event").getNodeValue();
				WclcPageElement wpe = this.pagesMap.get(this.pagesMap.keySet().iterator().next()).getWclcPageElementByName(weName);
				if("sortTable".equals(weEventName)){
					String sortButtonText  = action.getAttributes().getNamedItem("sortButtonText").getNodeValue();
					wpe.sortTable(sortButtonText);
				}else{
					wpe.executeEvent(weEventName);
				}
			}
		}
	}
	
	private void executeEvent(Node eventNode,String bisPageName){
		
		if("Event".equals(eventNode.getNodeName())){
			String eventName = eventNode.getAttributes().getNamedItem("name").getNodeValue();
			if("clickMenuItem".equals(eventName)){
				populateSubMenu = true;
				eventName = "click";
			}
			String webElementName = null;
			NodeList welms = eventNode.getChildNodes();
			for(int k=0; k<welms.getLength();k++){
				Node n = welms.item(k);
				if("WebElement".equals(n.getNodeName())){
					webElementName = n.getAttributes().getNamedItem("name").getNodeValue();
				}
			}
			WclcPage page = this.pagesMap.get(this.getPageName(bisPageName));
			page.executeEvent(webElementName, eventName);
			if(this.populateSubMenu){
				this.perentMenuItemName = webElementName;
				this.pageWithMenu = page;
				this.processSubMenu();
			}
			
		}
	}
	
	private void executeEvents(Node node,String bisPageName){
		NodeList children = node.getChildNodes();
		for (int index = 0; index < children.getLength(); index++) {
			Node nde = children.item(index);
			String name = nde.getNodeName();
			if("Events".equals(name)){
				NodeList nds = nde.getChildNodes();
				for(int j=0; j < nds.getLength();j++){
					this.executeEvent(nds.item(j), bisPageName);
				}
			}
		}
	}
	
	private void executeInputs(Node test){
		Node inwes = this.getNode("Inputs", test);
		if(inwes == null)return;
		NodeList inputs = inwes.getChildNodes();
		for (int index = 0; index < inputs.getLength(); index++) {
			Node input = inputs.item(index);
			if(Node.ELEMENT_NODE == input.getNodeType() && "WebElement".equals(input.getNodeName())){
				String weName  = input.getAttributes().getNamedItem("name").getNodeValue();
				String weValue = input.getAttributes().getNamedItem("in-value").getNodeValue();
				this.setWebElementValue(weName, weValue);
			}
		}
	}
	
	private void executeNavigation(Node navigationNode){
		NodeList navigations = navigationNode.getChildNodes();
		for(int j=0; j<navigations.getLength(); j++ ){
			Node navigation = navigations.item(j);
			if(navigation != null && navigation.getNodeType() == Node.ELEMENT_NODE){
				String navigateId = navigation.getAttributes().getNamedItem("id").getNodeValue();
				this.navigate(navigateId);
			}
		}
	}
	
	private void executeThreadNavigation(Node navigationNode){
		NodeList navigations = navigationNode.getChildNodes();
		for(int j=0; j<navigations.getLength(); j++ ){
			Node navigation = navigations.item(j);
			if(navigation != null && navigation.getNodeType() == Node.ELEMENT_NODE){
				this.navigate(navigation);
				Node endPoint = this.getNode("EndPoint", navigation);
				if(endPoint != null) {
					this.executeTest(endPoint);
				}
			}
		}
	}
	

	private void executeTest(Node endPointNode){
		NodeList children = endPointNode.getChildNodes();
		for (int index = 0; index < children.getLength(); index++) {
			Node nde = children.item(index);
			String name = nde.getNodeName();
			if("Test".equals(name)){
				String pageName = endPointNode.getAttributes().getNamedItem("pageName").getNodeValue();
				String testId   = nde.getAttributes().getNamedItem("testId").getNodeValue();
				StringBuffer fileName = new StringBuffer(pageName).append("TestDescriptor.xml");
				Document document = this.getDocument(TestDescriptorLocator.class, fileName.toString());
				NodeList tests = document.getDocumentElement().getElementsByTagName("Test");
				for (int i = 0; i < tests.getLength(); i++) {
					Node test = tests.item(i);
					String id = test.getAttributes().getNamedItem("id").getNodeValue();
					if(testId.equals(id)){
						this.test(test);
					}
				}
				this.populateTargetPage(endPointNode);
			}
		}
		
		
		
	}
	
	
	private Document getDocument(Class fileLocator, String fileName){
		Document document = null;
		try {
			DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream instr = fileLocator.getResourceAsStream(fileName);
			document = builder.parse(instr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}
	
	private Document getDocument(String fileName){
		Document document = null;
		try {
			DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			File initialFile = new File(fileName);
			InputStream instr = new FileInputStream(initialFile);
			document = builder.parse(instr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}
	
	
	private Node getNode(String nodeName,Node test){
		NodeList testNodes = test.getChildNodes();
		Node ret = null;
		for (int index = 0; index < testNodes.getLength(); index++) {
			ret = testNodes.item(index);
			String tag = ret.getNodeName();
			if(tag.equals(nodeName)){
				break;
			}
		}
		return ret;
	}

	
	private String getNodeId(Node node){
		NamedNodeMap map = node.getAttributes();
		Node id = map.getNamedItem("id");
		return id.getNodeValue();
	}
	private String getPageName(String name){
		StringBuffer fName = new StringBuffer(BisSeleniumNavigator.BIS_PAGES).append(".").append(name);
		return fName.toString();
	}
	
	private void navigate(Node node){
		String nodeName = node.getNodeName();
		NamedNodeMap map = node.getAttributes();
//		 Node mapNode = map.getNamedItem("bypassWebGate");
//		String byPassWebGate = (mapNode == null)?null:mapNode.getNodeValue();
		String bisPageName = map.getNamedItem("pageName").getNodeValue();
//		this.byPassWebGate = (byPassWebGate != null && byPassWebGate.equals("true"))?true:false;
		String url = this.createUrl(node);
		//WclcPageBaseImpl page =  (WclcPageBaseImpl) WclcPageFactory.newInstance(BisPagesLocator.class, bisPageName,this.driver);
		
//		if(url != null && (page instanceof BISLoginPage)){
//			BISLoginPage login = (BISLoginPage)page;
//		if(url != null && (page instanceof BISStartPage)){
		if(url != null && "BISStartPage".equals(bisPageName)){
//			BISStartPage login = (BISStartPage)page;
			BISStartPage login = (BISStartPage)WclcPageFactory.newInstance(BisPagesLocator.class, bisPageName,this.driver);
			login.navigate(url);
			String pageName = this.getPageName(bisPageName);
			this.pagesMap.remove(pageName);
			this.pagesMap.put(pageName,login);
		}

		
		this.sendKeys(node, bisPageName);
		this.executeEvents(node, bisPageName);
		this.populateTargetPage(node);
		
		
	}
	
	private void populateTargetPage(Node node){
		
		// TESTING NEW APROACH
		//WclcGenericPage genericPage = new WclcGenericPage();
		//genericPage.setDriver(this.driver);
		//genericPage.populateFields();
		// TESTING NEW APROACH
		String nodeName = node.getNodeName();
		if("EndPoint".equals(nodeName)) {
			String pageName = node.getAttributes().getNamedItem("pageName").getNodeValue();
			WclcPageBaseImpl target = (WclcPageBaseImpl)WclcPageFactory.newInstance(BisPagesLocator.class,pageName,this.driver);
			target.populateFields();
			this.pagesMap.clear();
			this.pagesMap.put(target.getClass().getName(), target);
			return;
		}
			
		
		NodeList children = node.getChildNodes();
		for (int index = 0; index < children.getLength(); index++) {
			Node nde = children.item(index);
			String name = nde.getNodeName();
			if("EndPoint".equals(name)){
				String pageName = nde.getAttributes().getNamedItem("pageName").getNodeValue();
				WclcPageBaseImpl target = (WclcPageBaseImpl)WclcPageFactory.newInstance(BisPagesLocator.class,pageName,this.driver);
					target.populateFields();
					this.pagesMap.clear();
					this.pagesMap.put(target.getClass().getName(), target);
			}
		}
	}
	
	private void processSubMenu(){
		this.pageWithMenu.populatSubMenu(this.perentMenuItemName);
		this.populateSubMenu = false;
		this.perentMenuItemName = null;
		this.pageWithMenu = null;

	}
	
	
	
	private void refreshCurrentPage(){
		String parts[] = this.pagesMap.keySet().iterator().next().split("\\.");
		String currentPageName = parts[parts.length -1];
		WclcPageBaseImpl target = (WclcPageBaseImpl)WclcPageFactory.newInstance(BisPagesLocator.class,currentPageName,this.driver);
		target.populateFields();
		this.pagesMap.clear();
		this.pagesMap.put(target.getClass().getName(), target);
	}
	
	private void sendKeys(Node node,String bisPageName){
		NodeList children = node.getChildNodes();
		for (int index = 0; index < children.getLength(); index++) {
			Node nde = children.item(index);
			String name = nde.getNodeName();
			if("SendKeys".equals(name)){
				NodeList nds = nde.getChildNodes();
				for(int j=0; j < nds.getLength();j++){
					Node nd = nds.item(j);
					if("WebElement".equals(nd.getNodeName())){
						String webElementName  = nd.getAttributes().getNamedItem("name").getNodeValue();
						String webElementValue = nd.getAttributes().getNamedItem("value").getNodeValue();
						this.pagesMap.get(this.getPageName(bisPageName)).sendKeys(webElementName, webElementValue);
					}
					
				}
			}
		}
	}
	
	private void setWebElementValue(String weName,String weValue){
		List<WclcPageElement> pageelements = this.pagesMap.get(this.pagesMap.keySet().iterator().next()).getPageElements();
		for(WclcPageElement pelement:pageelements){
			if(weName.equals(pelement.getName())){
				pelement.setTextValue(weValue);
			}
		}
	}

	private void test(Node test){
		this.executeInputs(test);
		this.executeActions(test);
		this.checkResults(test);
//		this.destroy();
		// Return to Welcome Page
//		WclcPageBaseImpl page = this.pagesMap.get("org.wclc.selenium.pages.bis.BISMistrnControlPage");
//		WclcPageElement wpe = page.getWclcPageElementByName("canceltButton");
//		wpe.executeEvent("click");
	}
	
	
}
