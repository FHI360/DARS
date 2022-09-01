/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import java.net.UnknownHostException;

/**
 *
 * @author smomoh
 */
public class CurlProcessor 
{
    public static void executeCurlCommand()
    {
        //curl "https://dhis-backup.sidhas.org/fhi360/api/dataValueSets?dataSet=osER8udrhP9&period=201712&orgUnit=svTGo3g8wJZ" -H "Accept:application/xml" -u smomoh:cHerry100 -v
        String username="devuser";
        String password="D3vUs3r!";
        String ignoreCert="-k";
        String url="https://dhis-backup.sidhas.org/fhi360/api/dataValueSets?dataSet=osER8udrhP9&period=201712&orgUnit=svTGo3g8wJZ";
        //String url="https://dhis-backup.sidhas.org/fhi360/api/dataValueSets?dataSet=osER8udrhP9&period=201712&orgUnit=svTGo3g8wJZ"; //"Accept:application/json", 
        //String url="https://play.dhis2.org/2.29/api/dataElements.xml"; //"Accept:application/json", 
       String[] command = {"curl", "-H","Accept:application/xml", "-u" ,username, ":" , password ,ignoreCert, url};
        ProcessBuilder process = new ProcessBuilder(command); 
        Process p;
        try
        {
            String workingDirectory = System.getProperty("java.io.tmpdir");
            System.err.println("workingDirectory is "+workingDirectory);
            p = Runtime.getRuntime().exec(command);//process.start();
            System.err.println("p has started ");
            p.waitFor();
            System.err.println("after p.waitFor() ");
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:\\Nomis\\Reports\\dataElements.xml")));
             BufferedReader reader =  new BufferedReader(new InputStreamReader(p.getInputStream()));
             InputStream ir=p.getInputStream();
             PrintStream ps = new PrintStream(p.getOutputStream());
             ps.println();
             
             /*DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(p.getInputStream());

            TransformerFactory factory2 = TransformerFactory.newInstance();
            Transformer xform = factory2.newTransformer();
              xform.transform(new DOMSource(doc), new StreamResult(System.out));*/

            // that’s the default xform; use a stylesheet to get a real one
            
             //System.err.println("ps.println() "+ps.println());
             //ir.read();
                System.err.println("after buffered reader ");
                StringBuilder strbuilder = new StringBuilder();
                String line = null;
                System.err.println("after String line ");
                //System.err.println("reader.readLine() is "+reader.readLine());
                /*while (ir.read() !=-1) 
                {
                    ///line = reader.readLine();
                    System.err.println("result line is "+line);
                    //bw.write(line);
                        //builder.append(line);
                        //builder.append(System.getProperty("line.separator"));
                }*/
                while (reader.ready()) 
                {
                    line = reader.readLine();
                    System.err.println("result line is "+line);
                    bw.write(line);
                        strbuilder.append(line);
                        //builder.append(System.getProperty("line.separator"));
                }
                String result = strbuilder.toString();
                System.out.print(result);

        }
        catch(UnknownHostException uhe)
        {
            System.err.println("Unable to connect to server");
        }
        catch (IOException e)
        {   System.out.print("error");
            e.printStackTrace();
        }
        catch (Exception ex)
        {   System.out.print("error");
            ex.printStackTrace();
        }
    }
}
