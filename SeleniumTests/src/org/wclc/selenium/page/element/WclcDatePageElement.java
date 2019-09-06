/**
 * 
 */
package org.wclc.selenium.page.element;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.wclc.selenium.page.element.base.WclcPageElementBase;

/**
 * @author sarans
 *
 */
@SuppressWarnings("unused")
public class WclcDatePageElement extends WclcPageElementBase {
	
	public void sendKeys(String value) {
		this.setDate(value);
	}
	
	public String getTextValue() {
		String textValue = null;
		textValue = this.getWebElement().getAttribute("value");
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.sendKeys(textValue);
	}
	
	private void setDate(String value){
		
		this.webElement.sendKeys(value);
		
		String month 	= value.split(",")[0].split(" ")[0];
		String day 		= value.split(",")[0].split(" ")[1];
		String year 	= value.split(",")[1].trim();
		
		this.selectYear(year);
		this.selectMonth(month);
		this.selectDay(day);
		

	}
	private void selectYear(String year){
		WebElement mainDiv = this.driver.findElement(By.id("datepick-div"));
		List<WebElement> dpSelects = mainDiv.findElements(By.tagName("select"));
		Select select1 = new Select(dpSelects.get(0));
		Select select2 = new Select(dpSelects.get(1));
		if(this.isNumber(select1.getFirstSelectedOption().getText())){
			select1.selectByValue(year);
		}else{
			select2.selectByValue(year);
		}
		
	}
	private void selectDay(String day){
		WebElement mainDiv = this.driver.findElement(By.id("datepick-div"));
		mainDiv.findElement(By.linkText(day)).click();
	}
	private void selectMonth(String month){
		WebElement mainDiv = this.driver.findElement(By.id("datepick-div"));
		List<WebElement> dpSelects = mainDiv.findElements(By.tagName("select"));
		Select select1 = new Select(dpSelects.get(0));
		Select select2 = new Select(dpSelects.get(1));
		if(this.isNumber(select1.getFirstSelectedOption().getText())){
			select2.selectByVisibleText(month);;
		}else{
			select1.selectByVisibleText(month);
		}
	}

	private void clickPreviousMonth(){
		WebElement prevMonth = this.driver.findElement(By.linkText("<Prev"));
		prevMonth.click();
	}
	private void clickNextMonth(){
		WebElement nextMonth = this.driver.findElement(By.linkText("Next>"));
		nextMonth.click();
	}
	private void closeDatePicker(){
		WebElement close = this.driver.findElement(By.linkText("Close"));
		close.click();
	}
	private void clearDatePicker(){
		WebElement clear = this.driver.findElement(By.linkText("Clear"));
		clear.click();
	}
	
}
