package com.share.component;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * Demo：Dom4j
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-12-17 下午3:03:40
 * @version 1.0
 */
public class Dom4jComp {
	/**
	 * 创建一个xml文档
	 * 
	 * @param filename 文档名
	 * @return 返回操作结果, 0表失败, 1表成功
	 */
	public int createXMLFile(String filename) {
		int returnValue = 0;
		//建立document对象
		Document doc = DocumentHelper.createDocument();
		//建立XML文档的根books
		Element booksEle = doc.addElement("books");
		//加入一行注释
		booksEle.addComment("This is a test for dom4j, holen, 2004.9.11");
		//加入第一个book节点
		Element bookElement = booksEle.addElement("book");
		//加入show参数内容
		bookElement.addAttribute("show", "yes");
		//加入title节点
		Element titleElement = bookElement.addElement("title");
		//为title设置内容
		titleElement.setText("Dom4j Tutorials");
		
		/** 类似的完成后两个book */
		 bookElement = booksEle.addElement("book");
		 bookElement.addAttribute("show","yes");
		 titleElement = bookElement.addElement("title");
		 titleElement.setText("Lucene Studing");
		 bookElement = booksEle.addElement("book");
		 bookElement.addAttribute("show","no");
		 titleElement = bookElement.addElement("title");
		 titleElement.setText("Lucene in Action");
		 
		 /** 加入owner节点 */
		 Element ownerElement = booksEle.addElement("owner");
		 ownerElement.setText("O'Reilly");
		 
		 try{
			 /** 将document中的内容写入文件中 */
			 XMLWriter writer = new XMLWriter(new FileWriter(new File(filename)));
			 writer.write(doc);
			 writer.close();
			 /** 执行成功,需返回1 */
			 returnValue = 1;
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		
		 return returnValue;
	}
	
	/**
	 * 修改XML文件中内容,并另存为一个新文件
	 * 重点掌握dom4j中如何添加节点,修改节点,删除节点
	 * @param filename 修改对象文件
	 * @param newfilename 修改后另存为该文件
	 * @return 返回操作结果, 0表失败, 1表成功
	 */
	public int modifyXMLFile(String filename, String newfilename) {
		int returnValue = 0;
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		try {
			/** 修改内容之一:如果book节点中show参数的内容为yes,则修改成no */
			/** 先用xpath查找对象 */
			doc = saxReader.read(new File(filename));
			List list = doc.selectNodes("/books/book/@show");
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Attribute attr = (Attribute) iter.next();
				if ("yes".equals(attr.getValue())) {
					attr.setValue("no");
				}
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**
		 * 修改内容之二:把owner项内容改为Tshinghua
		 * 并在owner节点中加入date节点,date节点的内容为2004-09-11,还为date节点添加一个参数type
		 */
		List list = doc.selectNodes("/books/owner");
		Iterator iter = list.iterator();
		if (iter.hasNext()) {
			Element ownEle = (Element) iter.next();
			ownEle.setText("Tshinghua");
			Element dateEle = ownEle.addElement("date");
			dateEle.setText("2012-12-17");
			dateEle.addAttribute("type","Gregorian calendar");
		}
		
		/** 修改内容之三:若title内容为Dom4j Tutorials,则删除该节点 */
		List list2 = doc.selectNodes("/books/book");
		Iterator iter2 = list2.iterator();
		while (iter2.hasNext()) {
			Element bookEle = (Element) iter2.next();
			Iterator iterator = bookEle.elementIterator("title");
			while (iterator.hasNext()) {
				 Element titleElement=(Element)iterator.next();
				 if(titleElement.getText().equals("Dom4j Tutorials")){
					 bookEle.remove(titleElement);
				 }
			}
		}
		
		try{
			/** 将document中的内容写入文件中 */
			XMLWriter writer = new XMLWriter(new FileWriter(new File(newfilename)));
			writer.write(doc);
			writer.close();
			/** 执行成功,需返回1 */
			returnValue = 1;
			}catch(Exception ex){
			ex.printStackTrace();
			}
		
		return returnValue;
	}
	
}
