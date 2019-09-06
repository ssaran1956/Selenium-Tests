/**
 * 
 */
package org.wclc.selenium.page.element;

import org.wclc.selenium.page.element.base.WclcPageElementBase;

/**
 * @author sarans
 *
 */
public class WclcSubmitPageElement extends WclcPageElementBase {
	
	public String getTextValue() {
		String textValue = null;
		textValue = this.getWebElement().getText();
		return textValue;
	}
	
}
