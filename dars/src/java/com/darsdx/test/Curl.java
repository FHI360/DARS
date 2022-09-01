/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.test;

import java.io.InputStreamReader;
import java.net.URL;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.UnknownHostException;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author smomoh
 */
public class Curl 
{
     public static void executeCommand() {
     
        try 
        {
     
            String url = "https://who-dev.baosystems.com/api/dataElements.json";

            URL obj = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            conn.setRequestMethod("PUT");

            String userpass = "devuser" + ":" + "D3vUs3r!";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8"));
            conn.setRequestProperty ("Authorization", basicAuth);

            String data =  "{\"format\":\"json\",\"pattern\":\"#\"}";
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(data);
            out.close();
            new InputStreamReader(conn.getInputStream());   

        } //
        catch(ConnectException ce)
        {
            System.err.println("Unable to connect to server");
        }
        catch(UnknownHostException uhe)
        {
            System.err.println("Unknown host");
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
      }
     
    
}
