/**
 * 
 */
package org.wclc.selenium.page.element;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.wclc.selenium.page.element.base.WclcPageElementBase;

/**
 * @author sarans
 *
 */
public class WclcTablePageElement extends WclcPageElementBase {
	
	 private String 	thXPath;
	 private String 	tbodyXPath;
	 
	 public String getThXPath() {
			return thXPath;
		}

		public void setThXPath(String thXPath) {
			this.thXPath = thXPath;
		}

		public String getTbodyXPath() {
			return tbodyXPath;
		}

		public void setTbodyXPath(String tbodyXPath) {
			this.tbodyXPath = tbodyXPath;
		}
	 
	public List<WebElement> getColumns(int rowIndex){//WebDriver driver,
		List<WebElement> ret = null;
		StringBuffer xPathBuf = new StringBuffer(this.locatorValue);
		xPathBuf.append(this.tbodyXPath).append("/tr");
		By by = By.ByXPath.xpath(xPathBuf.toString());
		List<WebElement> tableRows = driver.findElements(by);
		if(tableRows != null) {// && tableRows.size() >= rowIndex + 1){
			xPathBuf = new StringBuffer(this.locatorValue);
			xPathBuf.append(this.tbodyXPath).append("/tr[").append(rowIndex).append("]/td");
			by = By.ByXPath.xpath(xPathBuf.toString());
			ret = driver.findElements(by);
			
		}
		return ret;
	}

	public int getTableRawCount(){
		int ret = 0;
		List<WebElement> tableRows = this.getBodyRows();
		ret = (tableRows != null)?tableRows.size():0;
		return ret;
	}
	

	public int getTableColumnCount(){
		int ret = 0;
		StringBuffer xPathBuf = new StringBuffer(this.locatorValue);
		xPathBuf.append(this.thXPath).append("/tr[1]/th");
		By by = By.ByXPath.xpath(xPathBuf.toString());
		List<WebElement> headerColumns = driver.findElements(by);
		ret = (headerColumns != null)?headerColumns.size():0;
		return ret;
	}

	public void sortTable(String sortButtonText){
		StringBuffer xPathBuf = new StringBuffer(this.locatorValue);
		xPathBuf.append(this.thXPath);
		By by = By.ByXPath.xpath(xPathBuf.toString());
		WebElement header = driver.findElement(by);
		WebElement sortLink = header.findElement(By.ByLinkText.linkText(sortButtonText));
		sortLink.click();
	}
	
	public String getTableColumnHeader(int columnIndex){
		WebElement header = this.getHeaderRow();
		List<WebElement> headerCells = header.findElements(By.tagName("th"));
		String columnHeaderText = headerCells.get(columnIndex).getText();
		return columnHeaderText;
	}

	public WebElement getTableRow(int rowIndex){
		List<WebElement> tableRows = this.getBodyRows();
		return (tableRows != null && tableRows.size() >= rowIndex + 1 )?tableRows.get(rowIndex):null;
	}

	public String getTableColumnValue(int rowIndex,int columnIndex){
		List<WebElement> rowCells = this.getColumns(rowIndex);
		if(rowCells != null && rowCells.size() >= columnIndex + 1){
			return rowCells.get(columnIndex).getText();
		}else{
			return null;
		}
	}

	public void clickTableButton(int rowIndex, String buttonText){
		
		StringBuffer xPathBuf = new StringBuffer(this.locatorValue);
		xPathBuf.append(this.tbodyXPath);
		By by = By.ByXPath.xpath(xPathBuf.toString());
		WebElement table = driver.findElement(by);
		List<WebElement> buttons = table.findElements(By.ByLinkText.linkText(buttonText));
		WebElement link = buttons.get(rowIndex - 1);
		link.click();
		
	}
	
	private List<WebElement> getBodyRows(){
		StringBuffer xPathBuf = new StringBuffer(this.locatorValue);
		xPathBuf.append(this.tbodyXPath).append("/tr");
		By by = By.ByXPath.xpath(xPathBuf.toString());
		List<WebElement> tableRows = driver.findElements(by);
		return tableRows;
	}
	
	private WebElement getHeaderRow(){
		StringBuffer xPathBuf = new StringBuffer(this.locatorValue);
		xPathBuf.append(this.thXPath).append("/tr");
		By by = By.ByXPath.xpath(xPathBuf.toString());
		WebElement headerRow = driver.findElement(by);
		return headerRow;
	}
}
