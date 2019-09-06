/**
 * 
 */
package org.wclc.selenium.page.element;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.wclc.selenium.page.element.base.WclcPageElementBase;

/**
 * @author sarans
 *
 */
public class WclcSelectPageElement extends WclcPageElementBase {
	
	private Select 	selectElement;
	
	public String getTextValue() {
		 String textValue = null;
		textValue =  this.selectElement.getFirstSelectedOption().getText();
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.selectElement.selectByVisibleText(textValue);
	}
	
	public void setSelectionByIndex(int index) {
		this.selectElement.selectByIndex(index);
	}

	public void setSelectionByValue(String value) {
		this.selectElement.selectByValue(value);
	}
	
	public void setWebElement(WebElement webElement) {
		this.selectElement = new Select(webElement);
		this.webElement = webElement;
	}
	
}
