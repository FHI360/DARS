/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.test;

/**
 *
 * @author smomoh
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
 
public class FtpUrlUpload {
 
    private static final int BUFFER_SIZE = 4096;
 
    public static void executeFtpUrlExample() {
        String ftpUrl = "ftp://%s:%s@%s/%s;type=i";
        String host = "www.myserver.com";
        String user = "tom";
        String pass = "secret";
        String filePath = "E:/Work/Project.zip";
        String uploadPath = "/MyProjects/archive/Project.zip";
 
        ftpUrl = String.format(ftpUrl, user, pass, host, uploadPath);
        System.out.println("Upload URL: " + ftpUrl);
 
        try {
            URL url = new URL(ftpUrl);
            URLConnection conn = url.openConnection();
            OutputStream outputStream = conn.getOutputStream();
            FileInputStream inputStream = new FileInputStream(filePath);
 
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
 
            inputStream.close();
            outputStream.close();
 
            System.out.println("File uploaded");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
