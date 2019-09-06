/**
 * 
 */
package org.wclc.selenium.page.element.checker;

import java.lang.reflect.Method;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wclc.selenium.page.element.base.WclcPageElement;

/**
 * @author sarans
 *
 */
/*  TABLE CHECKERS
 * TableRawCount
 * TableColumnCount
 * RowValues
 * ColumnTitles
 * 
 * EXAMPLE
<WebElement name="controlHistoryTable"  action="check">
<Check name="TableRawCount" 		value="6"/>
<Check name="TableColumnCount" 	    value="7"/>
<Check name="RowValues" rowId="1">
	<Value id="1" value="Daily"/>
	<Value id="2" value="May 25, 2014"/>
	<Value id="3" value="May 25, 2014 02:00"/>
	<Value id="4" value="May 26, 2014 02:00"/>
	<Value id="5" value="sinsayr"/>
	<Value id="6" value="Completed"/>
	<Value id="7" value="Details"/>
</Check>
<Check name="ColumnTitles" >
	<Value id="1" value="Type"/>
	<Value id="2" value="Business Day"/>
	<Value id="3" value="From Time"/>
	<Value id="4" value="To Time"/>
	<Value id="5" value="UserId"/>
	<Value id="6" value="Status"/>
	<Value id="7" value="Actions"/>
</Check>
</WebElement>
*/
@SuppressWarnings("unused")
public class WclcTableChecker extends WclcCheckerBase{


	private void checkTableRowCount(Node node){
		int rowCount = this.getPageElement().getTableRawCount();
		String valueStr = node.getAttributes().getNamedItem("value").getNodeValue();
		int value = Integer.parseInt(valueStr);
		if(rowCount == value){
			System.out.println("Table Raw Count test passed.");
		}else{
			System.err.println("Table Raw Count test did not pass.");
		}
	}
	
	private void checkTableColumnCount(Node node){
		int columnCount = this.getPageElement().getTableColumnCount();
		String valueStr = node.getAttributes().getNamedItem("value").getNodeValue();
		int value = Integer.parseInt(valueStr);
		if(columnCount == value){
			System.out.println("Table Column Count test passed.");
		}else{
			System.err.println("Table Column Count test did not pass.");
		}
	}
	
	private void checkRowValues(Node node){
		int rowIndex = Integer.parseInt(node.getAttributes().getNamedItem("rowId").getNodeValue());
		NodeList inNodes = node.getChildNodes();
		int length = inNodes.getLength();
		for(int index=0; index<length; index++){
			Node inNode = inNodes.item(index);
			if(Node.ELEMENT_NODE == inNode.getNodeType()){
				NamedNodeMap nnMap =  inNode.getAttributes();
				int columnIndex = Integer.parseInt(nnMap.getNamedItem("id").getNodeValue());
				String value = nnMap.getNamedItem("value").getNodeValue();
				String cellValue = this.getPageElement().getTableColumnValue(rowIndex, columnIndex);
				if(value.equals(cellValue)){
					System.out.println("Table Column["+rowIndex+","+columnIndex+"] test passed.");
				}else{
					System.err.println("Table Column["+rowIndex+","+columnIndex+"] test did not pass.\n Requested value = "+value+" Column Value "+cellValue);
				}
			}
		}
	}
	
	private void checkColumnTitles(Node node){
		NodeList inNodes = node.getChildNodes();
		int length = inNodes.getLength();
		for(int index=0; index<length; index++){
			Node inNode = inNodes.item(index);
			if(Node.ELEMENT_NODE == inNode.getNodeType()){
				NamedNodeMap nnMap =  inNode.getAttributes();
				int columnIndex = Integer.parseInt(nnMap.getNamedItem("id").getNodeValue());
				String value = nnMap.getNamedItem("value").getNodeValue();
				String cellValue = this.getPageElement().getTableColumnHeader(columnIndex);
				if(cellValue.equals(value)){
					System.out.println("Table Column Header Text test passed.");
				}else{
					System.err.println("Table Column Header Text test did not pass.\n Requested value = "+value+" Header Value "+cellValue);
				}
			}
			
		}
	}

}
