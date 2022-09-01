/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.operationsmanagement;

import com.darsdx.business.User;
import com.darsdx.dao.DaoUtil;

/**
 *
 * @author smomoh
 */
public class UserManager 
{
    public static User getUser(String username, String pwd)
    {
        User user=null;
        try
        {
            DaoUtil util=new DaoUtil();
            user=util.getUserDaoInstance().getUser(username, pwd);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return user;
    }
}
