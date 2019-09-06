/**
 * 
 */
package org.wclc.selenium.page.element;

import org.wclc.selenium.page.element.base.WclcPageElementBase;

/**
 * @author sarans
 *
 */
public class WclcInputPageElement extends WclcPageElementBase {
	public String getTextValue() {
		String textValue = null;
		textValue = this.getWebElement().getAttribute("value");
		return textValue;
	}
	public void setTextValue(String textValue) {
		this.sendKeys(textValue);
	}
	
	public void sendKeys(String value) {
		this.webElement.sendKeys(value);
	}
}
