/**
 * 
 */
package org.wclc.selenium.page.element.checker;

import java.lang.reflect.Method;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wclc.selenium.page.element.base.WclcPageElement;

/**
 * @author sarans
 *
 */

@SuppressWarnings("unchecked")
public abstract class WclcCheckerBase implements WclcChecker {
	
	public WclcCheckerBase(){}
	public WclcCheckerBase(WclcPageElement pageElement){
		this.pageElement = pageElement;
	}
	
	private WclcPageElement pageElement;
	
	public  void setPageElement(WclcPageElement pageElement){
		this.pageElement = pageElement;
	}
	
	protected WclcPageElement getPageElement(){
		return this.pageElement;
	}
	

	@Override
	public void check(NodeList nodes) {
		int length = nodes.getLength();
		for(int index = 0;index<length; index++){
			Node node = nodes.item(index);
			if(Node.ELEMENT_NODE == node.getNodeType()){
				StringBuffer methodNameBuf = new StringBuffer("check");
				methodNameBuf.append(node.getAttributes().getNamedItem("name").getNodeValue());
				try {
					Class clazz = this.getClass();
					Class[] args = new Class[1];
					args[0] = Node.class;
					Method method = clazz.getDeclaredMethod(methodNameBuf.toString(), args);
					boolean accessible = method.isAccessible();
					method.setAccessible(true);
					method.invoke(this, node);
					method.setAccessible(accessible);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
