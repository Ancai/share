/**
 * 
 */
package com.share.webservice;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
/**
 * WebService试验：简单的服务接口
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-12-5 下午4:13:42
 * @version 1.0
 */
@WebService  
public interface HelloWorld  
{  
    /** 
     * 一个简单的方法,返回一个字符串 
     *  
     * @param hello 
     *  
     * @return 
     */  
    String say(String hello);  
  
    /** 
     * 稍微复杂一些的方法,传递一个对象给服务端处理 
     *  
     * @param user 
     * @return 
     */  
    String sayUserName(@WebParam(name = "user") UserDTO user);  
  
    /** 
     * 最复杂的方法,返回一个List封装的对象集合 
     *  
     * @return 
     */  
    public @WebResult(partName = "o") ListObject findUsers();  
}
