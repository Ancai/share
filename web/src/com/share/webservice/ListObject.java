/**
 * 
 */
package com.share.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * WebService试验类：参数对象定义文件
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-12-5 下午4:16:28
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(name = "listObject", propOrder ={ "list" })  
public class ListObject  
{  
    @XmlElement(nillable = true)  
    protected List<Object> list;  
  
    /** 
     * Gets the value of the list property. 
     *  
     * <p> 
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be 
     * present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the list property. 
     *  
     * <p> 
     * For example, to add a new item, do as follows: 
     *  
     * <pre> 
     * getList().add(newItem); 
     * </pre> 
     *  
     *  
     * <p> 
     * Objects of the following type(s) are allowed in the list {@link Object } 
     *  
     *  
     */  
    public List<Object> getList()  
    {  
        if (list == null)  
        {  
            list = new ArrayList<Object>();  
        }  
        return this.list;  
    }  
  
    public void setList(ArrayList<Object> list)  
    {  
        this.list = list;  
    }  
  
}
