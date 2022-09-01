/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.User;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface UserDao 
{
    public void saveUser(User user) throws Exception;
    public User getUser(String username) throws Exception;
    public List getAllUsers() throws Exception;
    public List getUserInfo(String username) throws Exception;
    public void updateUser(User user) throws Exception;
    public void deleteUser(User user) throws Exception;
    public List getUserByUserRole(String userRole) throws Exception;
    public User getUser(String username,String password) throws Exception;
}
