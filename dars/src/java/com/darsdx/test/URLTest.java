/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.test;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author smomoh
 */
public class URLTest implements Serializable
{
    public static String testUrl()  
    {
        String dhisOutput="";
        try
        {
            System.err.println("About to write to URL");
            //String urlPath="https://dhis-backup.sidhas.org/fhi360/api/dataElements.xml";  
            //String urlPath="https://play.dhis2.org/demo/api/26/dataValues?de=u6yhzgWPIsj&ou=DiszpKrYNg8&pe=201704&co=YBhrfw1dP2J&value=78";  
            String urlPath="https://www.google.com/";  
            //de=BOSZApCrBni&ou=DiszpKrYNg8&pe=201704&co=YBhrfw1dP2J&value=26
            URL url = new URL(urlPath);
            String userPass = "admin:district";
            String basicAuth = "Basic " + Base64.encode(userPass.getBytes(), Base64.BASE64DEFAULTLENGTH);//or
            HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
            urlConnection.setRequestProperty("Authorization", basicAuth);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.close();
            System.out.println(urlConnection.getResponseMessage());
            BufferedReader in = new BufferedReader(
                                        new InputStreamReader(
                                        urlConnection.getInputStream()));

            String decodedString;
            while ((decodedString = in.readLine()) != null) 
            {
                dhisOutput+=decodedString;
                System.out.println(decodedString);
            }
            in.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return dhisOutput;
    }
}
