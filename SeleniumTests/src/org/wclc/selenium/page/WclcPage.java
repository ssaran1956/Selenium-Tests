/**
 * 
 */
package org.wclc.selenium.page;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.wclc.selenium.page.element.base.WclcPageElement;

/**
 * @author sarans
 *
 */
public interface WclcPage {
	WebElement findElement(By by);
	void populateFields();
	void navigate();
	void navigate(String url);
	void sendKeys(String webElementname,String value);
	void executeEvent(String webElementname,String eventName);
	void setDriver(WebDriver driver);
	WebDriver getDriver();
	void populatSubMenu(String clickedWebElementName);
	WclcPageElement getWclcPageElementByName(String name);
}
