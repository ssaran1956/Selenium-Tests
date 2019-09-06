/**
 * 
 */
package org.wclc.selenium.pages.factory;

import java.lang.reflect.Field;

import org.openqa.selenium.WebDriver;

/**
 * @author sarans
 *
 */
@SuppressWarnings("unchecked")
public abstract class WclcPageFactory {

	public static Object newInstance(Class locator, String pageName, WebDriver driver){
		Object ret = null;
		String pagePackage  = locator.getPackage().getName();
		StringBuffer strbuf = new StringBuffer(pagePackage).append(".").append(pageName);
		try {
			ret = Class.forName(strbuf.toString()).newInstance();
			Class clazz = ret.getClass();
			Field field = clazz.getSuperclass().getDeclaredField("driver");
			boolean fieldIsAccessable = field.isAccessible();
			field.setAccessible(true);
			field.set(ret, driver);
			field.setAccessible(fieldIsAccessable);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return ret;
	}
	
}
