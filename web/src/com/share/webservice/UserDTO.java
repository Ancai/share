/**
 * 
 */
package com.share.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * WebService试验：传输User信息的DTO
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-12-5 下午4:31:41
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "User")
public class UserDTO  
{  
  
    protected Integer id;  
  
    protected String name;  
  
    public Integer getId()  
    {  
        return id;  
    }  
  
    public void setId(Integer value)  
    {  
        id = value;  
    }  
  
    public String getName()  
    {  
        return name;  
    }  
  
    public void setName(String value)  
    {  
        name = value;  
    }  
}
