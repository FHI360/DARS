/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.test;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.net.*;
import java.io.*;
/**
 *
 * @author smomoh
 */
public class URLReader 
{
    public static void readData() throws Exception 
    {
        //URL oracle = new URL("https://dhis-backup.sidhas.org/fhi360");
        URL oracle = new URL("https://dhis-backup.sidhas.org/fhi360/api/dataElements.xml?");
        String userPass = "admin:district";
            String basicAuth = "Basic " + Base64.encode(userPass.getBytes(), Base64.BASE64DEFAULTLENGTH);//or
            HttpURLConnection urlConnection = (HttpURLConnection)oracle.openConnection();
            urlConnection.setRequestProperty("Authorization", basicAuth);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(oracle.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
}
