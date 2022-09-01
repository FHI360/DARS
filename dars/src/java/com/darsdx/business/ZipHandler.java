/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.darsdx.business;

import com.darsdx.util.AppUtility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author COMPAQ USER
 */
public class ZipHandler implements Serializable
{
    Enumeration enumEntries;
    ZipFile zip;
    AppUtility appUtil;
public void createDirectories(String directoryPath)
{
    try
    {
        boolean success = (new File(directoryPath)).mkdirs();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
}
public void createDirectory(String directoryPath)
{
    try
    {
        boolean success = (new File(directoryPath)).mkdir();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
}
public void deleteDirectory(String directoryPath)
{
    try
    {
        boolean success = (new File(directoryPath)).delete();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
}
public void unZipFile(String zipFilePath)
{
	OutputStream myOut= null;
	try
	{
            zip = new ZipFile(zipFilePath);
            enumEntries = zip.entries();
      	while (enumEntries.hasMoreElements())
	{
                    ZipEntry zipentry = (ZipEntry) enumEntries.nextElement();
                    myOut= new FileOutputStream(zipentry.getName());
                    System.err.println("zipentry.getName() is "+zipentry.getName());
                    extractFile(zip.getInputStream(zipentry), new FileOutputStream(zipentry.getName()));
	}
	}
	catch (IOException ioe)
	{
            //ioe.printStackTrace();
            return;
        }
}
public void unZipFile(InputStream iStream)
{
	OutputStream myOut= null;
	try
	{
            //zip.getClass().
            //zip = new ZipFile(iStream.);
            enumEntries = zip.entries();
      	while (enumEntries.hasMoreElements())
	{
                    ZipEntry zipentry = (ZipEntry) enumEntries.nextElement();
                    myOut= new FileOutputStream(zipentry.getName());
                    extractFile(zip.getInputStream(zipentry), new FileOutputStream(zipentry.getName()));
	}
	}
	catch (IOException ioe)
	{
            //ioe.printStackTrace();
            return;
        }
}
public void zipFile(List listOfFilesToBeZipped,String destinationDirectory,String zipFileName)
{
    byte[] buf = new byte[1024];
    try
    {
        if(zipFileName==null || listOfFilesToBeZipped==null)
        {
            System.err.println("Enter valid name for the zip file and the Files to be zipped");
            return;
        }
        if(destinationDirectory !=null)
        {
            createDirectories(destinationDirectory);
        }
        else
        destinationDirectory=" ";
        
        String outFilename = destinationDirectory+"/"+zipFileName+".zip";
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFilename));
        String fileName="";
        File file = null;
        for(int count=0; count<listOfFilesToBeZipped.size(); count++)
        {
            fileName =(String)listOfFilesToBeZipped.get(count);
            file = new File(fileName);
            if (!file.exists())
            {
                break;
            }
            FileInputStream in = new FileInputStream(fileName);
            out.putNextEntry(new ZipEntry(fileName));
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.closeEntry();
            in.close();
        }
        out.close();
    }
    catch (IOException e)
    {
        e.printStackTrace();
    }
}

private void startZip(String filePath,String fileName,File file,ZipOutputStream out,byte[] buf)
{
    int t = 0;
        int i = 1;
        //File file=null;

        fileName = filePath + fileName;
        try
        {
        while (i > 0)
        {
            if (t > 0)
            {
                fileName = filePath + fileName + t + ".xml";
            }
            file = new File(fileName);
            if (!file.exists()) {
                i = 0;
                break;
            }
            FileInputStream in = new FileInputStream(fileName);
            out.putNextEntry(new ZipEntry(fileName));
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            t++;
            out.closeEntry();
            in.close();
        }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
}
public static void extractFile(InputStream inStream,OutputStream outStream)
  throws IOException
  {
    byte[] buf = new byte[1024];
    int l;
    while ((l = inStream.read(buf)) >= 0)
	{
      outStream.write(buf, 0, l);
    }
    inStream.close();
    outStream.close();
  }
}
