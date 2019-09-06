/**
 * 
 */
package org.wclc.selenium.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.wclc.selenium.page.element.base.WclcPageElement;
import org.wclc.selenium.page.element.factory.WclcPageElementFactory;

/**
 * @author sarans
 *
 */
public class WclcGenericPage implements WclcPage {
	
	protected List<WclcPageElement> pageElements = new ArrayList<WclcPageElement>(0);
	protected WebDriver driver;
	protected String descriptorFileName;
	private Document document;
	protected String url;
	
	private String 		pageTitle;
	private WebElement 	pageBody;
	private List<WebElement>  pageForms;
	
	private List<String> tableLinks;
	private List<String> tableInputs;
	private List<String> tableSelekts;
	
	
	/* (non-Javadoc)
	 * @see org.wclc.selenium.page.WclcPage#executeEvent(java.lang.String, java.lang.String)
	 */
	@Override
	public void executeEvent(String webElementname, String eventName) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.wclc.selenium.page.WclcPage#findElement(org.openqa.selenium.By)
	 */
	@Override
	public WebElement findElement(By by) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.wclc.selenium.page.WclcPage#getDriver()
	 */
	@Override
	public WebDriver getDriver() {
		// TODO Auto-generated method stub
		return this.driver;
	}

	/* (non-Javadoc)
	 * @see org.wclc.selenium.page.WclcPage#getWclcPageElementByName(java.lang.String)
	 */
	@Override
	public WclcPageElement getWclcPageElementByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.wclc.selenium.page.WclcPage#navigate()
	 */
	@Override
	public void navigate() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.wclc.selenium.page.WclcPage#navigate(java.lang.String)
	 */
	@Override
	public void navigate(String url) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.wclc.selenium.page.WclcPage#populatSubMenu(java.lang.String)
	 */
	@Override
	public void populatSubMenu(String clickedWebElementName) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.wclc.selenium.page.WclcPage#populateFields()
	 */
	@Override
	public void populateFields() {
		this.pageTitle = driver.getTitle();
		this.pageBody = this.driver.findElement(By.tagName("body"));
		this.pageForms = this.pageBody.findElements(By.tagName("form"));
		if(this.pageBody != null && this.pageForms != null){
			this.populateTables();
			this.populateInputs();
			this.populateLinks();
			this.populateSelects();
		}
		
	}
	
	private boolean isInTable(WebElement childElement){
		boolean ret = false;
		 String childTag = childElement.getTagName();
		 if("td".equals(childTag.toLowerCase()) || "th".equals(childTag.toLowerCase()))return true;
		 if(childTag.equals("html"))return ret;
		 WebElement parentElement = childElement.findElement(By.xpath("..")); 
		 return isInTable(parentElement);
	}
	
	
	private String generateXPATH(WebElement childElement, String current) {
	    String childTag = childElement.getTagName();
	    String id = childElement.getAttribute("id");
	    if(id != null && !id.isEmpty()){
	    	StringBuffer xPath = new StringBuffer(".//*[@id='"+id+"']");
	    	xPath.append(current);
	    	return xPath.toString();
	    }
	    if(childTag.equals("html")) {
	        return "/html[1]"+current;
	    }
	    WebElement parentElement = childElement.findElement(By.xpath("..")); 
	    List<WebElement> childrenElements = parentElement.findElements(By.xpath("*"));
	    int count = 0;
	    for(int i=0;i<childrenElements.size(); i++) {
	        WebElement childrenElement = childrenElements.get(i);
	        String childrenElementTag = childrenElement.getTagName();
	        if(childTag.equals(childrenElementTag)) {
	            count++;
	        }
	        if(childElement.equals(childrenElement)) {
	            return generateXPATH(parentElement, "/" + childTag + "[" + count + "]"+current);
	        }
	    }
	    return null;
	}

	
	private void populateInputs(){
		for(WebElement form:this.pageForms){
			List<WebElement> inputs = form.findElements(By.tagName("input"));
			if(inputs == null || inputs.isEmpty())continue;
			for(WebElement input:inputs){
				String type = input.getAttribute("type");
				String elementType = "";
				
				if("text".equals(type)){
					String id = input.getAttribute("id");
					if(id.toLowerCase().contains("date")){
						elementType = "jq_datepicker";
					}else{
						elementType = "input";
					}
				}else if("submit".equals(type)){
					elementType = "submit";
				}else if( "password".equals(type)){
					elementType = "input";
				}
				
				if(!elementType.isEmpty()){
					String name = input.getAttribute("name");
					String id = input.getAttribute("id");
					WclcPageElement pageElement = WclcPageElementFactory.getElementInstance(elementType);
					pageElement.setDriver(this.driver);
					pageElement.setElementType(elementType);
					pageElement.setName(name);
					pageElement.setId(id);
					pageElement.setWebElement(input);
					pageElement.setTagName("input");
					String xPath = this.generateXPATH(input, "");
					pageElement.setXPath(xPath);
					//System.out.println(xPath);
					this.pageElements.add(pageElement);

				}
			}
		}
	}
	
	private void populateLinks(){
		for(WebElement form:this.pageForms){
			String formName = form.getAttribute("name");
			if(formName ==null || formName.isEmpty())formName = form.getAttribute("id");
			//System.out.println("FORM = "+formName);
			List<WebElement> links = form.findElements(By.tagName("a"));
			if(links == null || links.isEmpty())continue;
			for(WebElement link:links){
				if(link.getText().isEmpty())continue;
				String id = link.getAttribute("id");
				String name = link.getText();//.getAttribute("name");
				WclcPageElement pageElement = WclcPageElementFactory.getElementInstance("link");
				if(formName.toLowerCase().endsWith("menuform")){
					pageElement.setElementType("menuItem");
				}else{
					pageElement.setElementType("link");
				}
				pageElement.setTagName("a");
				pageElement.setDriver(this.driver);
				pageElement.setName(name);
				pageElement.setId(id);
				pageElement.setWebElement(link);
				
				if(!this.isInTable(pageElement.getWebElement())){
					String xPath = this.generateXPATH(link, "");
					pageElement.setXPath(xPath);
//					System.out.println(xPath);
					this.pageElements.add(pageElement);
				}
			}
		}
		
		
	}
	private boolean isLinkInTable(WclcPageElement pageElement){
		return (this.tableLinks != null)?this.tableLinks.contains(pageElement.getWebElement().getText()):false;
	}
	
	private void populateSelects(){
		for(WebElement form:this.pageForms){
			List<WebElement> selects = form.findElements(By.tagName("select"));
			if(selects == null || selects.isEmpty())continue;
			for(WebElement select:selects){
				//String text = selects.getText();
				WclcPageElement pageElement = WclcPageElementFactory.getElementInstance("select");
				pageElement.setElementType("link");
				pageElement.setDriver(this.driver);
				pageElement.setTagName("select");
				pageElement.setWebElement(select);
				String xPath = this.generateXPATH(select, "");
				pageElement.setXPath(xPath);
//				System.out.println(xPath);
				this.pageElements.add(pageElement);
				
			}
		}
		
		
	}
	private void populateTables(){
		for(WebElement form:this.pageForms){
			List<WebElement> tables = form.findElements(By.tagName("table"));
			if(tables == null || tables.isEmpty())continue;
			for(WebElement table:tables){
				String text = table.getText();
				String xPath = this.generateXPATH(table, "");
				WclcPageElement pageElement = WclcPageElementFactory.getElementInstance("table");
				pageElement.setTagName("table");
				pageElement.setElementType("table");
				pageElement.setDriver(this.driver);
				pageElement.setName(text);
				pageElement.setWebElement(table);
				pageElement.setXPath(xPath);

				this.pageElements.add(pageElement);
				
//				System.out.println(xPath);
				//this.addToTableLinks(table);
			}
		}
	}
	

	private void addToTableLinks(WebElement table){
		List<WebElement> tableLinks = table.findElements(By.tagName("a"));
		for(WebElement tableLink:tableLinks){
			if(this.tableLinks == null)this.tableLinks = new ArrayList<String>(0);
			this.tableLinks.add(tableLink.getText());
		}
	}

	/* (non-Javadoc)
	 * @see org.wclc.selenium.page.WclcPage#sendKeys(java.lang.String, java.lang.String)
	 */
	@Override
	public void sendKeys(String webElementname, String value) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.wclc.selenium.page.WclcPage#setDriver(org.openqa.selenium.WebDriver)
	 */
	@Override
	public void setDriver(WebDriver driver) {
		// TODO Auto-generated method stub
		this.driver=driver;
	}

}
