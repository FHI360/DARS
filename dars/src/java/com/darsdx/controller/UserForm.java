/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author smomoh
 */
public class UserForm extends org.apache.struts.action.ActionForm {
    
   private String userrole;
    private String firstname;
    private String lastname;
    private String username;
    private String userId;
    private String pwd;
    private String oldPwd;
    private String confirmPwd;
    private String userList;
    private String actionName;
   
    public UserForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserList() {
        return userList;
    }

    public void setUserList(String userList) {
        this.userList = userList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(getActionName() !=null && (getActionName().equals("save") || getActionName().equals("modify")))
     {
        if(getUserrole().trim()==null || getUserrole().trim().length()<1)
            errors.add("user_role", new ActionMessage("error.user_role.required"));
        else if(getFirstname()==null || getFirstname().length()<1)
            errors.add("surname", new ActionMessage("error.surname.required"));
        else if(this.getLastname().trim()==null || getLastname().trim().length() < 1)
            errors.add("othernames", new ActionMessage("error.othernames.required"));
        else if(getUsername().trim()==null || getUsername().trim().length()<3)
            errors.add("username", new ActionMessage("error.username.required"));
        else if(getPwd().trim()==null || getPwd().trim().length()<5)
            errors.add("pwd", new ActionMessage("error.pwd.required"));
        else if(getConfirmPwd().trim()==null || !getConfirmPwd().trim().equals(getPwd().trim()))
            errors.add("confirm_pwd", new ActionMessage("error.confirm_pwd.required"));
     }
        return errors;
    }
}
