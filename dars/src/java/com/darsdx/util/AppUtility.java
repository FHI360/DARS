/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.darsdx.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import javax.servlet.http.HttpSession;

/**
 *
 * @author smomoh
 */
public class AppUtility implements Serializable
{
    
    private static String seperator="\\";
    private static String dbRootDirectory="C:"+seperator+"Darsdx";
    static String[] validLetters={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    int[] validNumbers={0,1,2,3,4,5,6,7,8,9};
    private static String[] specialCharacters={"@","#","$","%","^","&","*","!","~","`","+","=","\\","/","?","'","\"",".","[","]","{","}",","};
    private String metadataDirName="Metadata";
    //private String dhisExportPath=AppUtility.dbRootDirectory+"\\Transfer\\dhis";
    //private String databaseDirectory=AppUtility.dbRootDirectory+"\\dbs";
    //private static final String exportFilePath=AppUtility.dbRootDirectory+"\\Transfer/Export";
    //private static final String importFilePath=AppUtility.dbRootDirectory+"\\Transfer\\Import";
    public AppUtility()
    {
        //setDbRootDirectory(getUserHomeDirectory());
    }
    
    public String getExportFilePath()
    {
        return getDbRootDirectory()+seperator+"Transfer"+seperator+"Export";
    }
    public String getDxExportFilePath()
    {
        return getDbRootDirectory()+seperator+"Transfer"+seperator+"DxExport";
    }
    public String getDxImportFilePath()
    {
        return dbRootDirectory+seperator+"Transfer"+seperator+"DxImport";
    }
    public String getDbRootDirectory()
    {
        return dbRootDirectory;
    }
    public void setDbRootDirectory(String newDirectory)
    {
        dbRootDirectory=newDirectory+seperator+"Nomis";
    }
    
    public String getDatabaseDirectory()
    {
        return dbRootDirectory+seperator+"dbs";
    }
    public String getFileSeperator()
    {
        return seperator;
    }
    public void setFileSeperator(String seperator)
    {
        this.seperator=seperator;
    }
    public boolean hasSpecialCharacters(String name)
    {
        
        if(name==null)
        return true;
        boolean hasSpecialCharacter=false;
        for(int i=0; i<specialCharacters.length; i++)
        {
            if(name.indexOf(specialCharacters[i]) != -1)
            {
                hasSpecialCharacter=true;
                break;
            }
        }
        return hasSpecialCharacter;
    }
    public static boolean hasLetters(String input)
    {
        boolean hasLetters=false;
        if(input==null)
        return true;
        List letterList=getValidLetters();
        if(input !=null)
        {
            for(int i=0; i<input.length(); i++)
            { 
                if(letterList.contains(input.charAt(i)))
                {
                    hasLetters=true;
                    break;
                }
            }
        }
        return hasLetters;
    }
    public static boolean hasSpecialCharacters(String name,String[] exceptions)
    {
        boolean contIterate=true;
        if(name==null)
        return true;
        boolean hasSpecialCharacter=false;
        for(int i=0; i<specialCharacters.length; i++)
        {
            contIterate=true;
            if(exceptions !=null && exceptions.length >0)
            {
                for(int j=0; j<exceptions.length; j++)
                {
                    if(exceptions[j].equalsIgnoreCase(specialCharacters[i]))
                    contIterate=false;
                }
                if(!contIterate)
                continue;
            }
            
            if(name.indexOf(specialCharacters[i]) != -1)
            {
                hasSpecialCharacter=true;
                break;
            }
        }
        return hasSpecialCharacter;
    }
    
    public static boolean isString(String input)
    {
        boolean isLetter=false;
        if(input !=null)
        {
            for(int i=0; i<input.length(); i++)
            {
                if(Character.isLetter(input.charAt(i)))
                {
                    isLetter=true;
                    break;
                }
                
            }
        }
        return isLetter;
    }
    public List getValidCharacters()
    {
        List validList=new ArrayList();
        for(int i=0; i<validLetters.length; i++)
        {
            validList.add(validLetters[i]);
        }
        for(int i=0; i<validNumbers.length; i++)
        {
            validList.add(validNumbers[i]);
        }
        return validList;
    }
    public static List getValidLetters()
    {
        List validLettersList=new ArrayList();
        for(int i=0; i<validLetters.length; i++)
        {
            validLettersList.add(validLetters[i]);
        }
        return validLettersList;
    }
    public List getNumber0to9()
    {
        List list=new ArrayList<String>();
        for(int i=0; i<10; i++)
        {
            Integer in=new Integer(i);
            list.add(in.toString());
        }
        return list;
    }
    public static boolean isNumber(String input)
    {
        boolean isNumber=false;
        try
        {
            Integer.parseInt(input);
            isNumber=true;
        }
        catch(NumberFormatException nex)
        {
            System.err.println("A NumberFormatException has occured: "+nex.getMessage());
            isNumber=false;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            isNumber=false;
        }
        return isNumber;
    }
    public boolean isNumberGreaterThanZero(String input)
    {
        boolean numberGreaterThanZero=false;
        String strNum=removeCharactersFromNumbers(input);
        try
        {
            //System.err.println("Serial number is "+strNum);
            if(strNum !=null)
            {
                int number=Integer.parseInt(strNum);
                //System.err.println(" number is "+number);
                if(number > 0)
                numberGreaterThanZero=true;
            }
        }
        catch(NumberFormatException nex)
        {
            System.err.println("A NumberFormatException has occured: "+nex.getMessage());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return numberGreaterThanZero;
    }
    /*public boolean hasSpecialCharacters(String name)
    {
        
        if(name==null)
        return true;
        boolean hasSpecialCharacter=false;
        for(int i=0; i<specialCharacters.length; i++)
        {
            if(name.indexOf(specialCharacters[i]) != -1)
            {
                hasSpecialCharacter=true;
                break;
            }
        }
        return hasSpecialCharacter;
    }
    public boolean hasSpecialCharacters(String name,String[] exceptions)
    {
        boolean contIterate=true;
        if(name==null)
        return true;
        boolean hasSpecialCharacter=false;
        for(int i=0; i<specialCharacters.length; i++)
        {
            contIterate=true;
            if(exceptions !=null && exceptions.length >0)
            {
                for(int j=0; j<exceptions.length; j++)
                {
                    if(exceptions[j].equalsIgnoreCase(specialCharacters[i]))
                    contIterate=false;
                }
                if(!contIterate)
                continue;
            }
            
            if(name.indexOf(specialCharacters[i]) != -1)
            {
                hasSpecialCharacter=true;
                break;
            }
        }
        return hasSpecialCharacter;
    }*/
    public String removeCharactersFromNumbers(String input)
    {
        //System.err.println("input is "+input);
        String strValue=new String();
        if(input !=null)
        {
            for(int i=0; i<input.length(); i++)
            {
                
                if(!Character.isDigit(input.charAt(i)))
                {
                    //System.err.println(input.charAt(i)+" is not a number ");
                    continue;
                }
                strValue+=input.charAt(i);
            }
        }
        //System.err.println("strValue is "+strValue.trim());
        if(strValue.length()<1)
        return null;
        return strValue.trim();
    }
    public String removeCharactersFromNumbers(String input,String[] exceptions)
    {
        //System.err.println("input is "+input);
        String strValue=new String();
        if(input !=null)
        {
            boolean contIterate=true;
            for(int i=0; i<input.length(); i++)
            {
                contIterate=true;
                if(exceptions !=null && exceptions.length >0)
                {
                    for(int j=0; j<exceptions.length; j++)
                    {
                        if(input.contains(exceptions[j]))
                        strValue+=input.charAt(i);
                        contIterate=false;
                    }
                    if(!contIterate)
                    continue;
                }
                if(!Character.isDigit(input.charAt(i)))
                {
                    //System.err.println(input.charAt(i)+" is not a number ");
                    continue;
                }
                strValue+=input.charAt(i);
            }
        }
        //System.err.println("strValue is "+strValue.trim());
        if(strValue.length()<1)
        return null;
        return strValue.trim();
    }
    public AppUtility getInstance()
    {
        AppUtility appUtil=new AppUtility();
        appUtil.setFileSeperator("/");
        return appUtil;
    }
    public String getUserHomeDirectory()
    {
        String userHomeDirectory=System.getProperty("user.home");
        if(userHomeDirectory !=null && userHomeDirectory.indexOf("/") !=-1)
        seperator="/";
        return userHomeDirectory;
    }
    public String getDatabaseURL()
    {
        return "jdbc:derby:"+getDatabaseDirectory()+seperator+"nomisdb";
    }
    public void createReportDirectory()
    {
        createDirectories(getDbRootDirectory()+"\\Reports");
    }
    public String getReportDirectory()
    {
        return getDbRootDirectory()+"\\Reports";
    }
    public String getImportFilePath()
    {
        return dbRootDirectory+seperator+"Transfer"+seperator+"Import";
    }
    public String getAccessDeniedMessage()
    {
        return "Access denied. You do not have enough privilege to access this page";
    }
    public String getSuperUserRoleId()
    {
        return "superuser";
    }
    
    public void createExportImportDirectories()
    {
        //System.err.println("Inside createExportImportDirectories()");
      createDirectories(getImportFilePath());
      createDirectories(getDxImportFilePath());
      createDirectories(getDxExportFilePath());
      createDirectories(dbRootDirectory+seperator+"Transfer"+seperator+"Zips");
      createDirectories(getConfigurationDirectory());
      createDirectories(getTrainingDataXmlFolderPathFile());
      createDirectories(getTrainingMetadataXmlFolderPathFile());
      createMetadataExportImportDirectories();
      System.err.println("ExportImportDirectories created");
    }
    public String getMetadataDirectoryPath(String parentFolderName)
    {
        return getExportFilePath()+seperator+metadataDirName+seperator+parentFolderName+seperator;
    }
    
    public void createMetadataExportImportDirectories()
    {
       
      createDirectories(getExportFilePath()+seperator+metadataDirName+seperator+"Training"+seperator);   
      createDirectories(getExportFilePath()+seperator+metadataDirName+seperator+"ReferralDirectory"+seperator);
      createDirectories(getExportFilePath()+seperator+metadataDirName+seperator+"OrganizationRecords"+seperator);
      createDirectories(getExportFilePath()+seperator+metadataDirName+seperator+"Wards"+seperator);
      createDirectories(getImportFilePath());
      createDirectories(getTrainingDataXmlFolderPathFile());
      createDirectories(getTrainingMetadataXmlFolderPathFile());
      System.err.println("MetadataExportImportDirectories created");
    }
    public String getTrainingDataXmlFolderPathFile()
    {
        return getExportFilePath()+seperator+"Training"+seperator;
    }
    public String getTrainingMetadataXmlFolderPathFile()
    {
        return getExportFilePath()+seperator+"Metadata"+seperator+"Training"+seperator;
    }
    
    public List getAgeListAbove18(int startAge)
    {
        List endAgeList=getAgeList(startAge,18);
        endAgeList.add("18+");
        return endAgeList;
     }
    public int getVersion()
    {
        int version=1;
        File file=new File(getXmlConfigurationFile());
        if(!file.exists())
        return 0;
        return version;
    }
    public static String[] getCharacterArray()
    {
        String[] characterArray={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
        return characterArray;
    }
    public int getRandomNumberForId()
    {
        Random random=new Random();
        int randomNum=random.nextInt(62);
        return randomNum;
    }
    public List getCharactersInList()
    {
        String[] characterArray=getCharacterArray();
        List list=new ArrayList();
        for(int i=0; i<characterArray.length; i++)
        {
            list.add(characterArray[i]);
        }
        return list;
    }
    public String generateUniqueId()
    {
        List characterList=getCharactersInList();
        String uniqueId="";
        for(int i=0; i<11; i++)
        {
            Collections.shuffle(characterList);
            uniqueId+=characterList.get(getRandomNumberForId());
        }
    //    System.err.println("recId is "+uniqueId);
        return uniqueId;
    }
    public String generateUniqueId(int len)
    {
        List characterList=getCharactersInList();
        String uniqueId="";
        for(int i=0; i<len; i++)
        {
            Collections.shuffle(characterList);
            uniqueId+=characterList.get(getRandomNumberForId());
        }
       return uniqueId;
    }
    public String getXmlConfigurationFile()
    {
        return getConfigurationDirectory()+seperator+"configurationInfo.xml";
    }
    public String getDhisExportPath()
    {
        return AppUtility.dbRootDirectory+seperator+"Transfer"+seperator+"dhis";
    }
    public String getDefaultDatabaseDirectory()
    {
        return AppUtility.dbRootDirectory+seperator+"dbs";
    }
    public String getDbExportZipDirectory()
    {
        return dbRootDirectory+seperator+"Transfer"+seperator+"Zips";
    }
    public String changeFirstLetterToUpperCase(String str)
    {
        String finalStr=null;
        if(str !=null)
        {
            String firstLetter=str.substring(0,1);
            finalStr=firstLetter.toUpperCase();
            if(str.length()>1)
            finalStr=finalStr+str.substring(1);
        }
        return finalStr;
    }
public boolean compareDates(String enrollmentDate, String serviceDate)
{
    String[] enrollmentDateArray;
    String[] serviceDateArray;
    boolean dateFlag=false;
    if(enrollmentDate !=null && serviceDate !=null)
    {
        //if(enrollmentDate.equalsIgnoreCase(serviceDate))
        //return true;
        enrollmentDateArray=enrollmentDate.split("-");
        serviceDateArray=serviceDate.split("-");
        if(enrollmentDateArray.length !=3 || serviceDateArray.length !=3)
        return false;
        if(enrollmentDate.equalsIgnoreCase(serviceDate))
        dateFlag=true;
        else
        {
            int year1=Integer.parseInt(enrollmentDateArray[0]);
            int mth1=Integer.parseInt(enrollmentDateArray[1]);
            int day1=Integer.parseInt(enrollmentDateArray[2]);

            int year2=Integer.parseInt(serviceDateArray[0]);
            int mth2=Integer.parseInt(serviceDateArray[1]);
            int day2=Integer.parseInt(serviceDateArray[2]);
            dateFlag=compareDate(year1, mth1, day1,year2, mth2, day2);
        }
    }
    return dateFlag;
}
    public String getLoginConfigFile()
    {
        return getConfigurationDirectory()+seperator+"loginconfig.txt";
    }
    public String getConfigurationDirectory()
    {
        return getDbRootDirectory()+seperator+"conf";
    }
    public String changeToUppercase(String word)
    {
        if(word !=null)
        word=word.toUpperCase();
        return word;
    }
    public String getUserAssignedOrgCode(String orgCode)
    {
        if(orgCode !=null && orgCode.indexOf("/") !=1)
        orgCode=orgCode.substring(orgCode.lastIndexOf("/")+1);
        return orgCode;
    }
    public String capitalizeFirstLetter(String word)
    {
        if(word !=null && word.length()>0)
        {
            if(word.length()>1)
            {
                String firstLetter=word.substring(0, 1);
                firstLetter=firstLetter.toUpperCase();
                word=firstLetter+word.substring(1);
            }
            else
            word=word.toUpperCase();    
        }
        return word;
    }
    public String padNumberWithZeros(String num,int len)
    {
        if(num !=null)
        {
            while(num.length() < len)
            {
                num="0"+num;
            }
        }
        return num;
    }
    public String getDbDirectoryFromConfigFile()
    {
        String result=readFiles(getLoginConfigFile(), "");
        if(result==null || result.equalsIgnoreCase("filedoesnotexist"))
        return null;
        else
        return result;
    }
    public boolean isDefaultAccountEnabled()
    {
        String result=readFiles(getLoginConfigFile(), "");
        if(result==null)
        return false;
        else if(result !=null && result.equalsIgnoreCase("filedoesnotexist"))
        return true;
        else
        {
            String[] defaultParameter=result.split(":");
            if(defaultParameter.length >1 && defaultParameter[1].equalsIgnoreCase("on"))
            return true;
        }
        return false;
    }
    public void disableDefaultAccount()
    {
        createDirectories(getConfigurationDirectory());
        writeFile(getLoginConfigFile(), "DefaultLogin:off");
    }
    public boolean hasPriviledgeToAccessPage(HttpSession session)
    {
        if(!isUserInRole(session, "Administrator") && !isUserInRole(session, "DataEntry"))
        return false;
        return true;
    }
    public String replaceRegex(String parentString,String target,String replacement)
    {
        parentString=parentString.replaceAll(target,replacement);
        return parentString;
    }
    public void createDatabase(String source,String destination)
    {
        createDirectories(destination);
        copyAndPasteFiles(source, destination);
    }
    public boolean copyAndPasteFiles(String source,String destination)
    {
        String seperator="\\";
        if(source !=null && source.indexOf("/") !=-1)
        seperator="/";
        String[] files=null;
        File sourceDirectory=new File(source);
        File destinationFile=new File(destination);
        if(!destinationFile.exists())
        destinationFile.mkdirs();
        File sourceFile;
        files=sourceDirectory.list();
        for(int i=0; i<files.length; i++)
        {
           sourceFile =new File(source+seperator+files[i]);
           if(sourceFile.isDirectory())
           {
               destinationFile=new File(destination+seperator+files[i]);
               destinationFile.mkdirs();
               copyAndPasteFiles(sourceFile.getAbsolutePath(),destinationFile.getAbsolutePath());
           }
           else
           {
               String parent=sourceFile.getParent();
               
               String immediateParentFolder=parent.substring(parent.lastIndexOf(seperator)+1, parent.length());
               File file=new File(immediateParentFolder);
               String destPath=destinationFile.getAbsolutePath();
               System.err.println("immediateParentFolder is "+immediateParentFolder);
               System.err.println("destinationFile is "+destinationFile);
               //if(file !=null && file.isDirectory())
               //{
                    if(destPath.indexOf(immediateParentFolder) !=-1)
                   destPath=destinationFile.getAbsolutePath().substring(0,destinationFile.getAbsolutePath().indexOf(immediateParentFolder));
                   destPath=destPath+immediateParentFolder+seperator+files[i];
                   System.err.println("destPath is "+destPath);
               //}
               //System.err.println("destPath2 is "+destPath);
               writeFilesAsStream(sourceFile.getPath(), destPath);
           }
        }
        return true;
    }
    public List copyFilePathsIntoList(String source)
    {
        String[] files=null;
        File sourceDirectory=new File(source);
        List pathList=new ArrayList();
        String seperator="\\";
        if(source !=null && source.indexOf("/") !=-1)
        seperator="/";
        File sourceFile;
        files=sourceDirectory.list();
        for(int i=0; i<files.length; i++)
        {
           sourceFile =new File(source+seperator+files[i]);
           if(sourceFile.isDirectory())
           {
               pathList.addAll(copyFilePathsIntoList(sourceFile.getAbsolutePath()));
           }
           else
           {
               pathList.add(source+seperator+files[i]);
           }
        }
        return pathList;
    }
    public static List readCategoryOptionComboFiles(String filePath)
    {
        //DxCategoryCombination ccombo=new DxCategoryCombination();
	  BufferedReader reader;
          List list=new ArrayList();
          File file=new File(filePath);
          String str=null;
      try
      {
           if(file.exists())
           {
		int i=0;
		reader=new BufferedReader(new FileReader(file));
		while ((str=reader.readLine())!=null)
		{
			list.add(str);
			i++;
		}
		reader.close();
		if(i==0)
		return null;
           }
          else
	  {
		return null;
	  }
     }
     catch(Exception e)
     {
	System.out.println("Could not read file");
	e.printStackTrace();
	return null;
     }
     return list;
}
    public static List readFiles(String filePath)
    {
	  BufferedReader reader;
          List list=new ArrayList();
          File file=new File(filePath);
          String str=null;
      try
      {
           if(file.exists())
           {
		int i=0;
		reader=new BufferedReader(new FileReader(file));
		while ((str=reader.readLine())!=null)
		{
			list.add(str);
			i++;
		}
		reader.close();
		if(i==0)
		return null;
           }
          else
	  {
		return null;
	  }
     }
     catch(Exception e)
     {
	System.out.println("Could not read file");
	e.printStackTrace();
	return null;
     }
     return list;
}
    public static String readFiles(String filePath, String delimiter)
    {
	  BufferedReader reader;
          String buffer=new String();
	  File file=new File(filePath);
          String str=null;
      try
      {
           if(file.exists())
           {
		int i=0;
		reader=new BufferedReader(new FileReader(file));
		while ((str=reader.readLine())!=null)
		{
                    buffer+=str;
                    if(delimiter !=null)
                    buffer+=delimiter;
			i++;
		}
		reader.close();
		if(i==0)
		return null;
           }
          else
	  {
		return "filedoesnotexist";
	  }
     }
     catch(Exception e)
     {
	System.out.println("Could not read file");
	e.printStackTrace();
	return null;
     }
     return buffer;
}
    public void writeFile(String fileName,List list)
    {
        OutputStreamWriter out;
        PrintWriter pw=null;
        try
        {
            //out = new OutputStreamWriter(new FileOutputStream(fileName));
            pw=new PrintWriter(fileName,"UTF-8");
            for(int i=0; i<list.size(); i++)
            {
                //out.write("\n");
                pw.println((String)list.get(i));
            }
            pw.close();
        }
        catch(FileNotFoundException fnfe)
        {
            try
            {
                fnfe.printStackTrace();
                //out.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        catch(Exception ex)
        {
            System.err.println("Other exception occured "+ex.getMessage());
        }
    }
    public void writeFile(String fileName,String content)
    {
        OutputStreamWriter out;
        try
        {
            out = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
            out.write(content);
            out.close();
        }
        catch(FileNotFoundException fnfe)
        {
            try
            {
                fnfe.printStackTrace();
                //out.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        catch(Exception ex)
        {
            System.err.println("Other exception occured "+ex.getMessage());
        }
    }
    public Object readObjectsFromFile(String fileName)
    {
        ObjectInputStream ois=null;
        Object obj=null;
        try
        {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            obj=ois.readObject();
            ois.close();
        }
        catch(FileNotFoundException fnfe)
        {
            try
            {
                fnfe.printStackTrace();
                ois.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        catch(Exception ex)
        {
            System.err.println("Other exception occured "+ex.getMessage());
        }
        return obj;
    }
    public void writeFilesAsStream(String sourceFileName,String destinationFileName)
    {
        FileOutputStream fos=null;
        try
        {
            File file=new File(sourceFileName);
            byte[] outputByte=new byte[(int)file.length()];
            FileInputStream fileInputStream=new FileInputStream(file);
            fileInputStream.read(outputByte);
            fileInputStream.close();

            fos=new FileOutputStream(destinationFileName);
            fos.write(outputByte);
            fos.close();
        }
        catch(FileNotFoundException fnfe)
        {
            try
            {
                fnfe.printStackTrace();
                if(fos !=null)
                fos.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        catch(Exception ex)
        {
            System.err.println("Other exception occured "+ex.getMessage());
        }
    }
    public void writeObjectsToFile(String fileName,Object content)
    {
        ObjectOutputStream out=null;
        try
        {
            out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(content);

            out.close();
        }
        catch(FileNotFoundException fnfe)
        {
            try
            {
                fnfe.printStackTrace();
                out.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        catch(Exception ex)
        {
            System.err.println("Other exception occured "+ex.getMessage());
        }
    }
    public void deleteFiles(String directoryPath)
    {
        File file=new File(directoryPath);
        String[] files=null;
        if(file.isDirectory())
        {
         files=file.list();
         File file2=null;
         String filePath=null;
         for(int i=0; i<files.length; i++)
         {
             filePath=directoryPath+"\\"+files[i];
             file2=new File(filePath);      
             if(file2.isDirectory())
             deleteFiles(file2.getPath());
             file2.delete();
         }
        }
        else
        file.delete();
    }
    public boolean isUserInRole(HttpSession session,String role)
    {
        boolean userInRole=false;
        String userRole=(String)session.getAttribute("userrole");
        if(userRole !=null && userRole.equalsIgnoreCase(role))
        userInRole=true;
        return userInRole;
    }
    public String getUserRole(HttpSession session)
    {
        String userRole=(String)session.getAttribute("userrole");
        return userRole;
    }
    public String getCurrentUser(HttpSession session)
    {
        String user=(String)session.getAttribute("USER");
        return user;
    }
    public String removeTags(String str)
    {
        String str2="";
        if(str !=null)
        {
            str2=str.replace("<b>", " ");
            str2=str2.replace("</b>", " ");
        }
        return str2;
    }
    public String getCVIStatus(int score)
    {
        String vulStatus="";
        if(score >0 && score <9)
        vulStatus="Vunerable";
        else if(score >8 && score <13)
        vulStatus="More vunerable";
        else if(score >12)
        vulStatus="Most vulnerable";

        return vulStatus;
    }
    public String getHVIStatus(int score)
    {
        String vulStatus="";
        if(score <6)
        vulStatus="Not Vunerable";
        if(score >6 && score <14)
        vulStatus="Vunerable";
        else if(score >13 && score <22)
        vulStatus="More vunerable";
        else if(score >21)
        vulStatus="Most vulnerable";
        return vulStatus;
    }
    public String[] listFiles(String directoryPath)
    {
        File file=new File(directoryPath);
        String[] files=null;
        StringBuilder s=new StringBuilder();

        if(file.isDirectory())
        {
            files=file.list();
        }
        //if(files==null)
        
        return files;
    }
    public String padSerialNumber(String number)
    {
        if(number !=null)
        {
            if(number.length() ==1)
            number="0000"+number;
            else if(number.length() ==2)
            number="000"+number;
            else if(number.length() ==3)
            number="00"+number;
            else if(number.length() ==4)
            number="0"+number;
        }

        return number;
    }
    public int getDateDifference(int mth, int year)
    {
        Calendar calendar = Calendar.getInstance();
        int currentYear=calendar.get(Calendar.YEAR);
        int currentMonth=calendar.get(Calendar.MONTH)+1;
        //System.err.println("currentMonth is "+currentMonth);
        int yearDiff=currentYear-year;
        int mthDiff=0;
        if(yearDiff ==0 && (currentMonth >= mth))
        mthDiff=currentMonth-mth;
        else if(yearDiff==1)
        mthDiff=currentMonth+(12-mth);
        else if(yearDiff>1)
        mthDiff=(currentMonth+(12-mth))+((yearDiff-1)*12);
        return mthDiff;
    }
    public String getCurrentDate()
    {
        String dateFormat="yyyy-MM-dd";
        SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
        String currentDate=sdf.format(new java.util.Date());
        return currentDate;
    }
    public List generatDate(int startYear)
    {
        List listOfDates=new ArrayList();
        for(int i=startYear; i<=2020; i++)
        {
            listOfDates.add(i);
        }
        return listOfDates;
    }
    public List generateMonths(int startMonth)
    {
        List listOfMonths=new ArrayList();
        String[] months={"January","February","March","April","May","June","July","August","September","October","November","December"};
        for(int i=startMonth-1; i<months.length; i++)
        {
            listOfMonths.add(months[i]);
        }
        return listOfMonths;
    }
    
    public static List generateYears(int firstYr,int lastYr)
    {
         List yearList=new ArrayList();
         for(int i=firstYr; i<lastYr+1; i++)
         {
             yearList.add(i);
         }
         return yearList;
    }
    public List generateYearsTillCurrentDate(int startYr)
    {
        List yearList=new ArrayList();
         String currentDate=getCurrentDate();
         
         if(currentDate !=null && currentDate.indexOf("-") !=-1)
         {
             String[] currentDateArray=currentDate.split("-");
             int currentYear=Integer.parseInt(currentDateArray[0]);
             for(int i=startYr; i<currentYear+1; i++)
             {
                 yearList.add(i);
             }
         }
         return yearList;
    }
    public boolean compareDate(int year1,int mth1,int day1,int year2,int mth2,int day2)
    {
        Calendar c1=new GregorianCalendar(year1,mth1,day1);
        Calendar c2=new GregorianCalendar(year2,mth2,day2);
        if(c1.before(c2))
        return true;
        return false;
    }
    /*public String compareDate(int year,int mth,int day,int diff)
    {
        Calendar c=new GregorianCalendar(2012,8,12);
        Calendar c2=new GregorianCalendar(2012,8,13);
        if(c.before(c2))
        System.out.println("c is before c2");
        else
        System.out.println("c2 is before c");
	//c.add(Calendar.MONTH,diff);
        String previousMonth=null;//sdf.format(c.getTime());
        return previousMonth;
    }*/
    public String getPreviousDate(int year,int mth,int day,int diff)
    {
        String dateFormat="yyyy/MM/dd  hh:mm:ss";
      SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
	Calendar c=new GregorianCalendar(year,mth,day);
	c.add(Calendar.MONTH,diff);
        String previousMonth=sdf.format(c.getTime());
        return previousMonth;
    }
    public String getCurrentMthAndDate()
    {
        String currentDate=getCurrentDate();
        String[] dateArray=currentDate.split("-");
        String month=getFullMonthAsString(Integer.parseInt(dateArray[1]));
        String today=month+" "+dateArray[2]+", "+dateArray[0];
        return today;
    }
    public String getDisplayDate(String mysqlDate)
    {
        String displayDate=null;
        if(mysqlDate !=null && mysqlDate.indexOf("-") !=-1)
        {
            String[] dateArray=mysqlDate.split("-");
            displayDate=dateArray[1]+"/"+dateArray[2]+"/"+dateArray[0];
        }
        return displayDate;
    }
    public List getAgeList(int startAge,int endAge)
    {
        List ageList=new ArrayList();
        for(int i=startAge; i<endAge; i++)
        {
            ageList.add(i);
        }
        return ageList;
    }
    public String monthDayYear(String mysqlDate)
    {
        String displayDate=null;
        if(mysqlDate !=null && mysqlDate.indexOf("-") !=-1)
        {
            String[] dateArray=mysqlDate.split("-");
            displayDate=dateArray[1]+"/"+dateArray[2]+"/"+dateArray[0];
        }
        return displayDate;
    }
    public String convertMysqlDateTomonthDayYear(String mysqlDate)
    {
        String displayDate=null;
        if(mysqlDate !=null && mysqlDate.indexOf("-") !=-1)
        {
            String[] dateArray=mysqlDate.split("-");
            displayDate=dateArray[1]+"/"+dateArray[2]+"/"+dateArray[0];
        }
        return displayDate;
    }
    public String processMthDayYearToMysqlFormat(String date)
    {
        String[] dateArray=null;
        String mysqlDate=date;
        if(date !=null && !date.equalsIgnoreCase("All"))
        {
            if(date.indexOf("/") !=-1)
            {
                dateArray=date.split("/");
                if(dateArray !=null && dateArray.length>2)
                mysqlDate=getMySqlDate(dateArray[1],dateArray[0],dateArray[2]);
            }
        }
        return mysqlDate;
    }
    public long monthsBetween(Calendar startDate, Calendar endDate) {
        Calendar date = (Calendar) startDate.clone();
        long monthsBetween = 0;
        while (date.before(endDate)) {
            date.add(Calendar.MONTH, 1);
            monthsBetween++;
        }
        return monthsBetween;
    }
    public String getMySqlDate(String day,String month,String year)
    {
        String mysqlDate=year+"-"+month+"-"+day;
        return mysqlDate;
    }
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
    public String getFullMonthAsString(int mth)
    {
        String month=null;
        if(mth==1 || mth==01)
        month="January";
        else if(mth==2 || mth==02)
        month="February";
        else if(mth==3 || mth==03)
        month="March";
        else if(mth==4 || mth==04)
        month="April";
        else if(mth==5 || mth==05)
        month="May";
        else if(mth==6 || mth==06)
        month="June";
        else if(mth==7 || mth==07)
        month="July";
        else if(mth==8)
        month="August";
        else if(mth==9)
        month="September";
        else if(mth==10)
        month="October";
        else if(mth==11)
        month="November";
        else if(mth==12)
        month="December";
        return month;
    }
    public String getMonthAsString(int mth)
    {
        String month=null;
        if(mth==1 || mth==01)
        month="Jan";
        else if(mth==2 || mth==02)
        month="Feb";
        else if(mth==3 || mth==03)
        month="Mar";
        else if(mth==4 || mth==04)
        month="Apr";
        else if(mth==5 || mth==05)
        month="May";
        else if(mth==6 || mth==06)
        month="Jun";
        else if(mth==7 || mth==07)
        month="Jul";
        else if(mth==8)
        month="Aug";
        else if(mth==9)
        month="Sep";
        else if(mth==10)
        month="Oct";
        else if(mth==11)
        month="Nov";
        else if(mth==12)
        month="Dec";
        return month;
    }
    public int getMonthAsNumber(String mth)
    {
        int month=1;
        if (mth !=null)
        {
            if(mth.equalsIgnoreCase("Jan") || mth.equalsIgnoreCase("January"))
            month=1;
            else if(mth.equalsIgnoreCase("feb") || mth.equalsIgnoreCase("february"))
            month=2;
            else if(mth.equalsIgnoreCase("mar") || mth.equalsIgnoreCase("march"))
            month=3;
            else if(mth.equalsIgnoreCase("apr") || mth.equalsIgnoreCase("april"))
            month=4;
            else if(mth.equalsIgnoreCase("may") || mth.equalsIgnoreCase("may"))
            month=5;
            else if(mth.equalsIgnoreCase("jun") || mth.equalsIgnoreCase("june"))
            month=6;
            else if(mth.equalsIgnoreCase("jul") || mth.equalsIgnoreCase("july"))
            month=7;
            else if(mth.equalsIgnoreCase("aug") || mth.equalsIgnoreCase("august"))
            month=8;
            else if(mth.equalsIgnoreCase("sep") || mth.equalsIgnoreCase("september"))
            month=9;
            else if(mth.equalsIgnoreCase("oct") || mth.equalsIgnoreCase("october"))
            month=10;
            else if(mth.equalsIgnoreCase("nov") || mth.equalsIgnoreCase("november"))
            month=11;
            else if(mth.equalsIgnoreCase("dec") || mth.equalsIgnoreCase("december"))
            month=12;
        }
        return month;
    }
    public List tokenizeStr(String str, String delim)
    {
        List tokenList = new ArrayList();
        StringTokenizer st = new StringTokenizer(str, delim);
        while (st.hasMoreTokens())
        {
            tokenList.add(st.nextToken());
        }
        return tokenList;
    }
public String[] splitServices(String service)
{
   String[] services=null;
   if(service !=null)
   {
        if(service.indexOf(",") != -1)
        services=service.split(",");
        else
        {
            services=new String[1];
            services[0]=service;
        }
        services=trimString(services);
   }
   return services;
}
public String[] splitString(String service,String seperator)
{
   String[] services=null;
   if(service !=null)
   {
        if(service.indexOf(seperator) != -1 || service.indexOf(";") != -1)
        services=service.split(seperator);
        else
        {
            services=new String[1];
            services[0]=service;
        }
        services=trimString(services);
   }
   return services;
}
public String[] trimString(String[] array)
{
   String[] trimmedArray=null;
   String str="";
   if(array !=null)
   {
       if(array.length==1)
       {
           trimmedArray=new String[1];
           trimmedArray[0]=array[0].trim();
       }
       else
       {
            for(int i=0; i<array.length; i++)
            {
                if(i==array.length-1)
                str+=array[i].trim();
                else
                str+=array[i].trim()+";";
            }
        trimmedArray=str.split(";");
       }
   }
   return trimmedArray;
}
    public String concatStr(String[] strArray,String additionalString)
    {
        String str="";
        if(strArray !=null && strArray.length>0)
        {
            for(int i=0; i<strArray.length; i++)
            {
                if(i==strArray.length-1)
                str+=strArray[i];
                else
                str+=strArray[i]+",";
            }
        }
        if(additionalString !=null && !additionalString.isEmpty() && !additionalString.equals(" ") && !additionalString.equals("") && !additionalString.equals("  "))
          str+=";"+additionalString;
        return str;
    }

}
