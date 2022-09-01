/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.util;

/**
 *
 * @author smomoh
 */
public class InputValidator 
{
    public static boolean isValidUrl(String url)
    {
        String[] exceptions={".",":","/","-","_"};
        boolean isValidUrl=true;
        if(url==null)
        isValidUrl=false;
        else if(AppUtility.hasSpecialCharacters(url, exceptions))
        isValidUrl=false;
        else
        {
            url=url.trim();
            if(url.indexOf("http")==-1 || url.indexOf("://")==-1)
            isValidUrl=false;
            else if(url.indexOf(".")==-1)
            isValidUrl=false;
            else if(url.indexOf("//.")!=-1 || url.indexOf("..")!=-1 || url.indexOf("::")!=-1 || url.indexOf("//:")!=-1 || url.indexOf("///")!=-1)
            isValidUrl=false;
            else
            {
                String[] urlPart=url.split("//");
                if(urlPart.length>2)
                isValidUrl=false;
                /*else
                {
                    String lastEmailPart=url.substring(url.lastIndexOf(".")+1);
                    if(lastEmailPart.length() !=3)
                    isValidUrl=false;
                }*/
            }
        }
        return isValidUrl;
    }
    public static boolean isValidPhoneNumber(String phoneNumber)
    {
        String[] exception={"+"};
        boolean validPhoneNumber=true;
        if(phoneNumber ==null)
        validPhoneNumber=false;
        else
        {
            phoneNumber=phoneNumber.trim();
            if(phoneNumber.length()>15)
            validPhoneNumber=false;
            else if(phoneNumber.indexOf("+")>0)
            validPhoneNumber=false;
            else if(AppUtility.hasLetters(phoneNumber) || (AppUtility.hasSpecialCharacters(phoneNumber, exception)))
            validPhoneNumber=false;
        }
        return validPhoneNumber;
    }
    public static boolean isValidEmail(String emailAddress)
    {
        String[] exceptions={".","@"};
        boolean isValidEmail=true;
        if(emailAddress==null)
        isValidEmail=false;
        else if(AppUtility.hasSpecialCharacters(emailAddress, exceptions))
        isValidEmail=false;
        else
        {
            emailAddress=emailAddress.trim();
            if(emailAddress.indexOf("@")==-1)
            isValidEmail=false;
            else if(emailAddress.indexOf(".")==-1)
            isValidEmail=false;
            else if(emailAddress.indexOf("@.")!=-1 || emailAddress.indexOf(".@")!=-1|| emailAddress.indexOf("..")!=-1 || emailAddress.indexOf("@@")!=-1)
            isValidEmail=false;
            else
            {
                String[] emailPart=emailAddress.split("@");
                if(emailPart.length>2)
                isValidEmail=false;
                else
                {
                    String lastEmailPart=emailAddress.substring(emailAddress.lastIndexOf(".")+1);
                    if(lastEmailPart.length() !=3)
                    isValidEmail=false;
                }
            }
        }
        return isValidEmail;
    }
    
    public static boolean hasSpecialCharacters(String groupName,String[] exceptions)
    {
        boolean hasSpecialCharacters=false;
        if(AppUtility.hasSpecialCharacters(groupName, exceptions));
        hasSpecialCharacters=true;
        return hasSpecialCharacters;
    }
}
