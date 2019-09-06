/**
 * 
 */
package org.wclc.selenium.page.element.checker;

import org.w3c.dom.NodeList;
import org.wclc.selenium.page.element.base.WclcPageElement;

/**
 * @author sarans
 *
 */
public interface WclcChecker {
	public abstract void check(NodeList nodes);
	public abstract void setPageElement(WclcPageElement pageElement);
}
