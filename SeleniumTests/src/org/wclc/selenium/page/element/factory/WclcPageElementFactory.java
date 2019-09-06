/**
 * 
 */
package org.wclc.selenium.page.element.factory;


import java.lang.reflect.Field;
import org.wclc.selenium.page.element.WclcPageElementLocator;
import org.wclc.selenium.page.element.base.WclcPageElement;
import org.wclc.selenium.page.element.checker.WclcChecker;
import org.wclc.selenium.page.element.checker.WclcCheckerLocator;


/**
 * @author sarans
 *
 */
public enum WclcPageElementFactory {
	
	INPUT("WclcInputChecker","WclcInputPageElement"),
	SUBMIT("WclcSubmitChecker","WclcSubmitPageElement"),
	LINK("WclcLinkChecker","WclcLinkPageElement"),
	TABLINK("WclcLinkChecker","WclcLinkPageElement"),
	MENUITEM("WclcLinkChecker","WclcLinkPageElement"),
	SELECT("WclcSelectChcker","WclcSelectPageElement"),
	TABLE("WclcTableChecker","WclcTablePageElement"),
	JQ_DATEPICKER("WclcDateChecker","WclcDatePageElement");
	
	private String elementClass;
	private String checkerClass;

	
	public static WclcPageElement getElementInstance(String elementType){
		WclcPageElement instance = null;
		String elementPackage = WclcPageElementLocator.class.getPackage().getName();
		WclcPageElementFactory factory = WclcPageElementFactory.getByElementType(elementType);
		StringBuffer className = new StringBuffer(elementPackage).append(".").append(factory.elementClass);
		try {
			instance = (WclcPageElement) Class.forName(className.toString()).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return instance;
	}
	
	
	@SuppressWarnings("unchecked")
	public static WclcChecker getCheckerInstance(WclcPageElement pageElement){
		WclcChecker instance = null;
		String elementPackage = WclcCheckerLocator.class.getPackage().getName();
		WclcPageElementFactory factory = WclcPageElementFactory.getByElementType(pageElement.getElementType());
		StringBuffer className = new StringBuffer(elementPackage).append(".").append(factory.checkerClass);
		try {
			instance = (WclcChecker) Class.forName(className.toString()).newInstance();
			Class clazz = instance.getClass();
			Field field = clazz.getSuperclass().getDeclaredField("pageElement");
			boolean fieldIsAccessable = field.isAccessible();
			field.setAccessible(true);
			field.set(instance, pageElement);
			field.setAccessible(fieldIsAccessable);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return instance;
	}
	
	public static WclcChecker getCheckerInstance(String elementType){
		WclcChecker instance = null;
		String elementPackage = WclcCheckerLocator.class.getPackage().getName();
		WclcPageElementFactory factory = WclcPageElementFactory.getByElementType(elementType);
		StringBuffer className = new StringBuffer(elementPackage).append(".").append(factory.checkerClass);
		try {
			instance = (WclcChecker) Class.forName(className.toString()).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return instance;
	}
	
	
	public static boolean isInstanceOf(WclcPageElementFactory type,String elementType){
		return type.equals(WclcPageElementFactory.getByElementType(elementType));
	}
	
	public static WclcPageElementFactory getByElementType(String elementType){
		WclcPageElementFactory ret = WclcPageElementFactory.valueOf(elementType.toUpperCase());
		return ret;
	}
	
	private WclcPageElementFactory(String checkerClass,String elementClass){
		this.checkerClass 	= checkerClass;
		this.elementClass 	= elementClass;
	}
	

}
