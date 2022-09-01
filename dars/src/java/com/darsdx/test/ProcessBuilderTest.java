/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.test;

/**
 *
 * @author smomoh
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

  public class ProcessBuilderTest {

public static void executeCurlCommand() throws IOException {

    ProcessBuilder pb = new ProcessBuilder(
            "curl",
            "-d",
            "https://play.dhis2.org/demo/api/26/dataValueSets ");

    pb.directory(new File("/home/your_user_name/Pictures"));
    pb.redirectErrorStream(true);
    Process p = pb.start();
    InputStream is = p.getInputStream();

    FileOutputStream outputStream = new FileOutputStream(
            "/home/your_user_name/Pictures/simpson_download.jpg");
    /*ProcessBuilder pb = new ProcessBuilder(
            "curl",
            "-d",
            "http://static.tumblr.com/cszmzik/RUTlyrplz/the-simpsons-season-22-episode-13-the-blue-and-the-gray.jpg ");

    pb.directory(new File("/home/your_user_name/Pictures"));
    pb.redirectErrorStream(true);
    Process p = pb.start();
    InputStream is = p.getInputStream();

    FileOutputStream outputStream = new FileOutputStream(
            "/home/your_user_name/Pictures/simpson_download.jpg");*/

    BufferedInputStream bis = new BufferedInputStream(is);
    byte[] bytes = new byte[100];
    int numberByteReaded;
    while ((numberByteReaded = bis.read(bytes, 0, 100)) != -1) {

        outputStream.write(bytes, 0, numberByteReaded);
        Arrays.fill(bytes, (byte) 0);

    }

    outputStream.flush();
    outputStream.close();

}
 }
