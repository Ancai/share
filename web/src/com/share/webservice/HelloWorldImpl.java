/**
 * 
 */
package com.share.webservice;

import java.util.ArrayList;

import javax.jws.WebService;
/**
 * WebService试验：简单的服务接口的实现类
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-12-5 下午4:20:02
 * @version 1.0
 */
@WebService(endpointInterface = "com.share.webservice.HelloWorld")
public class HelloWorldImpl implements HelloWorld  
{  
    public String sayUserName(UserDTO user)  
    {  
        return "hello " + user.getName();  
    }  
  
    public String say(String hello)  
    {  
        return "hello " + hello;  
    }  
  
    public ListObject findUsers()  
    {  
        ArrayList<Object> list = new ArrayList<Object>();  
  
        list.add(instancUser(1, "lib"));  
        list.add(instancUser(2, "mld"));  
        list.add(instancUser(3, "lq"));  
        list.add(instancUser(4, "gj"));  
        ListObject o = new ListObject();  
        o.setList(list);  
        return o;  
    }  
  
    private UserDTO instancUser(Integer id, String name)  
    {  
        UserDTO user = new UserDTO();  
        user.setId(id);  
        user.setName(name);  
        return user;  
    }  
}
