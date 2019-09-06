/**
 * 
 */
package org.wclc.selenium.page.element;

import org.wclc.selenium.page.element.base.WclcPageElementBase;

/**
 * @author sarans
 *
 */
public class WclcLinkPageElement extends WclcPageElementBase {
	public String getTextValue() {
		String textValue = null;
		textValue = this.getWebElement().getText();
		return textValue;
	}

}
