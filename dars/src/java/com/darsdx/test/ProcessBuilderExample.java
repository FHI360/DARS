/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.test;

/**
 *
 * @author smomoh
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessBuilderExample 
{
    public static void executeNativeCommand() throws InterruptedException,
         IOException {
        ProcessBuilder pb = new ProcessBuilder("curl", "-d @C:\\Dars\\Transfer\\dhis\\DHIS2TestExport_PMTCT.xml https://play.dhis2.org/api/26/dataValueSets -H Content-Type:application/xml -u admin:district -k");
        System.out.println("Run echo command");
        Process process = pb.start();
       
        //int errCode = process.waitFor();
        //System.out.println("Echo command executed, any errors? " + (errCode == 0 ? "No" : "Yes"));
        System.out.println("Echo Output:\n" + output(process.getInputStream()));   
    }
 
    private static String output(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
       BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
           while ((line = br.readLine()) != null) {
                sb.append(line + System.getProperty("line.separator"));
           }
        } finally {
            br.close();
        }
        return sb.toString();
    }
}

