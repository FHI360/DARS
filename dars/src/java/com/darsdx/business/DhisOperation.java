/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.business;

import com.darsdx.dao.DaoUtil;
import com.darsdx.dao.DaoUtility;
import com.darsdx.util.AppUtility;
import com.darsdx.util.DateManager;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.BufferedReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 *
 * @author smomoh
 */
public class DhisOperation implements Serializable
{
    DaoUtil util=new DaoUtil();
    public void createDhisExport(String dhisInstance,String user)
    {
        List mainList=new ArrayList();
        List subList=new ArrayList();
        FinalFlatTable fft=null;
        //DatabaseExport dbe=new DatabaseExport();
        DhisDataExport dde=null;
        String startDate=null;
        String[] startDateArray=null;
        String exportFileName="Dhisexport";
        Object[] objArray=null;
        //String user="sidhas";
        
        String orgUnitId=null;
        int start=0;
        int end=0;
        int count=0;
        int exportSize=10000;
        int exportFileNameCount=0;
        try
        {
            FinalFlatTable fft2=null;
            
            List orgUnitIdList=util.getFinalFlatTableDaoInstance().getDistinctOrgUnitIdsFromFinalFlatTable();
            System.err.println("orgUnitIdList size is "+orgUnitIdList.size());
            for(int i=0; i<orgUnitIdList.size(); i++)
            {
                orgUnitId=(String)orgUnitIdList.get(i); 
               
                List dhisDataExportList=util.getFinalFlatTableDaoInstance().getNonDuplicateRecords(orgUnitId);
                for(int j=0; j<dhisDataExportList.size(); j++)
                { 
                    objArray=(Object[])dhisDataExportList.get(j);
                    //System.err.println("record number is "+count);
                    dde=new DhisDataExport();
                    dde.setOrganizationUnitId(objArray[0].toString());
                    dde.setDataElementId(objArray[1].toString());
                    dde.setCategoryCombinationId(objArray[2].toString());
                    startDate=objArray[3].toString();
                    
                    if(startDate ==null || startDate.indexOf("-") ==-1 )
                    continue;
                    startDateArray=startDate.split("-");
                    dde.setMonth(startDateArray[1]);
                    dde.setYear(startDateArray[0]);
                    
                    dde.setValue(Integer.parseInt(objArray[4].toString()));
                    dde.setAttributeOptionCombinationId((String)objArray[5]);
                    subList.add(dde);
                    if(subList.size()==exportSize)
                    {
                        mainList.add(subList);
                        subList=new ArrayList();
                    }
                    else if(i==orgUnitIdList.size()-1 && j==dhisDataExportList.size()-1)
                    {
                        mainList.add(subList);
                    }
                    count++;
                    //System.err.println("OrgUnit: "+dde.getOrganizationUnitId()+" DataElement:"+dde.getDataElementId()+" CatCombo:"+dde.getCategoryCombinationId()+" period:"+dde.getYear()+dde.getMonth()+" Value:"+dde.getValue());
                 }
            }
            List list=null;
            String fileName=exportFileName;
            for(int k=0; k<mainList.size(); k++)
            {
               start=(k*exportSize)+1;
               list=(List)mainList.get(k);
               end=(list.size()+(k*exportSize));
               exportFileName=fileName+start+"-"+end;
               createDhisExportFileInXml(list, user, exportFileName);
            }
            //XMLTest.writeXml(); //xmlTest=new XMLTest();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void createDhisExportFileInXml(List list,String user,String fileName) 
    {
        AppUtility appUtil=new AppUtility();
        List listOfFilesToBeZipped=new ArrayList();
        String destinationDirectory=appUtil.getDhisExportPath();//"C:/Nomisdhis/Transfer/dhis";
        appUtil.createDirectories(destinationDirectory);
	  try 
          {  
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
               	// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("dataValueSet");
		doc.appendChild(rootElement);

		// set attribute to start element
		Attr attr = doc.createAttribute("xmlns");
		attr.setValue("http://dhis2.org/schema/dxf/2.0");
		rootElement.setAttributeNode(attr);
                for(int i=0; i<list.size(); i++)
                {
                    DhisDataExport dde=(DhisDataExport)list.get(i);
                      String orgUnitId=dde.getOrganizationUnitId();
                      String dataElementId=dde.getDataElementId();
                      String categoryOptionComboId=dde.getCategoryCombinationId();
                      String attributeOptionComboId=dde.getAttributeOptionCombinationId();
                      String value=new Integer(dde.getValue()).toString();
                      if(dde.getMonth()==null || dde.getMonth().trim().length()==0 || dde.getYear()==null || dde.getYear().trim().length()==0)
                      continue;
                      //fileDateName=appUtil.getMonthAsString(Integer.parseInt(dde.getMonth()))+" "+dde.getYear();
                      int month=(Integer.parseInt(dde.getMonth()));
                      String mth=""+month;
                      if(month <10)
                      mth="0"+month;
                      String period=dde.getYear()+mth;
                      String timestamp=appUtil.getCurrentDate();
                      
                      if(orgUnitId==null || orgUnitId.endsWith("--") || dataElementId==null || dataElementId.endsWith("--") || categoryOptionComboId==null || categoryOptionComboId.endsWith("--") || attributeOptionComboId==null || attributeOptionComboId.endsWith("--"))
                      continue;
                      
                        Element dataValue = doc.createElement("dataValue");
                        rootElement.appendChild(dataValue);

                        Attr deattr = doc.createAttribute("dataElement");
                        deattr.setValue(dataElementId);
                        dataValue.setAttributeNode(deattr);
                        
                        dataValue.normalize();

                        Attr perdattr = doc.createAttribute("period");
                        perdattr.setValue(period);
                        dataValue.setAttributeNode(perdattr);

                        Attr ouattr = doc.createAttribute("orgUnit");
                        ouattr.setValue(orgUnitId);
                        dataValue.setAttributeNode(ouattr);
                                              
                        Attr ccattr = doc.createAttribute("categoryOptionCombo");
                        ccattr.setValue(categoryOptionComboId);
                        dataValue.setAttributeNode(ccattr);
                        
                        Attr optionComboattr = doc.createAttribute("attributeOptionCombo");
                        optionComboattr.setValue(attributeOptionComboId);
                        dataValue.setAttributeNode(optionComboattr);

                        Attr valueattr = doc.createAttribute("value");
                        valueattr.setValue(value+"");
                        dataValue.setAttributeNode(valueattr);

                        Attr strbyattr = doc.createAttribute("storedBy");
                        strbyattr.setValue(user);
                        dataValue.setAttributeNode(strbyattr);

                        Attr tmstpattr = doc.createAttribute("lastUpdated");
                        tmstpattr.setValue(timestamp);
                        dataValue.setAttributeNode(tmstpattr);

                        Attr fupattr = doc.createAttribute("followUp");
                        fupattr.setValue("false");
                        dataValue.setAttributeNode(fupattr);
                }

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
                fileName=fileName.replace("\\", "-");
                fileName=fileName.replace("/", "-");
                String xmlFileName=fileName+".xml";
                String filePath=destinationDirectory+"/"+xmlFileName;
		StreamResult result = new StreamResult(new File(filePath));
                listOfFilesToBeZipped.add(filePath);
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		transformer.transform(source, result);

		System.out.println("File saved!");

	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
          ZipHandler zipHandler=new ZipHandler();
            zipHandler.zipFile(listOfFilesToBeZipped, destinationDirectory, fileName);
            zipHandler=null;
    }
    public void createDhisExportFileInXmlFromDataValueList(List dataValueList,String user,String fileName) 
    {
        System.err.println("dataValueList.size() is "+dataValueList.size());
        AppUtility appUtil=new AppUtility();
        List listOfFilesToBeZipped=new ArrayList();
        String destinationDirectory=appUtil.getDhisExportPath();//"C:/Nomisdhis/Transfer/dhis";
        appUtil.createDirectories(destinationDirectory);
	  try 
          {  
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
               	// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("dataValueSet");
		doc.appendChild(rootElement);

		// set attribute to staff element
		Attr attr = doc.createAttribute("xmlns");
		attr.setValue("http://dhis2.org/schema/dxf/2.0");
		rootElement.setAttributeNode(attr);
                for(int i=0; i<dataValueList.size(); i++)
                {
                      DataValue dv=(DataValue)dataValueList.get(i);
                      String orgUnitId=dv.getOrgUnitId();
                      String dataElementId=dv.getDataElementId();
                      String categoryOptionComboId=dv.getCategoryOptionComboId();
                      String period=dv.getPeriod();
                      String value=new Integer(dv.getDvalue()).toString();
                      
                      if(orgUnitId==null || orgUnitId.trim().length()<11)
                      continue;
                      else if(dataElementId==null || dataElementId.trim().length()<11)
                      continue;
                      else if(categoryOptionComboId==null || categoryOptionComboId.trim().length()<11)
                      continue;
                      else if(period==null || period.trim().length()<6)
                      continue;
                      //fileDateName=appUtil.getMonthAsString(Integer.parseInt(dde.getMonth()))+" "+dde.getYear();
                      /*int month=(Integer.parseInt(dde.getMonth()));
                      String mth=""+month;
                      if(month <10)
                      mth="0"+month;*/
                      
                      String timestamp=appUtil.getCurrentDate();
          
                        Element dataValue = doc.createElement("dataValue");
                        rootElement.appendChild(dataValue);

                        Attr deattr = doc.createAttribute("dataElement");
                        deattr.setValue(dataElementId);
                        dataValue.setAttributeNode(deattr);

                        Attr perdattr = doc.createAttribute("period");
                        perdattr.setValue(period);
                        dataValue.setAttributeNode(perdattr);

                        Attr ouattr = doc.createAttribute("orgUnit");
                        ouattr.setValue(orgUnitId);
                        dataValue.setAttributeNode(ouattr);
                        Attr ccattr = doc.createAttribute("categoryOptionCombo");
                        ccattr.setValue(categoryOptionComboId);
                        dataValue.setAttributeNode(ccattr);

                        Attr valueattr = doc.createAttribute("value");
                        valueattr.setValue(value+"");
                        dataValue.setAttributeNode(valueattr);

                        Attr strbyattr = doc.createAttribute("storedBy");
                        strbyattr.setValue(user);
                        dataValue.setAttributeNode(strbyattr);

                        Attr tmstpattr = doc.createAttribute("timestamp");
                        tmstpattr.setValue(timestamp);
                        dataValue.setAttributeNode(tmstpattr);

                        Attr fupattr = doc.createAttribute("followUp");
                        fupattr.setValue("false");
                        dataValue.setAttributeNode(fupattr);
                }

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
                fileName=fileName.replace("\\", "-");
                fileName=fileName.replace("/", "-");
                String xmlFileName=fileName+".xml";
                String filePath=destinationDirectory+appUtil.getFileSeperator()+xmlFileName;
		StreamResult result = new StreamResult(new File(filePath));
                listOfFilesToBeZipped.add(filePath);
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
                System.err.println("filePath is "+filePath);
		transformer.transform(source, result);

		System.out.println("File saved!");

	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
          catch (Exception ex) {
		ex.printStackTrace();
	  }
          ZipHandler zipHandler=new ZipHandler();
            zipHandler.zipFile(listOfFilesToBeZipped, destinationDirectory, fileName);
    }
    public void createDhisOrganizationUnitExportFileInXml(List list,String user,String fileName) 
    {
        AppUtility appUtil=new AppUtility();
        List listOfFilesToBeZipped=new ArrayList();
        String destinationDirectory=appUtil.getDhisExportPath();//"C:/Nomisdhis/Transfer/dhis";
        appUtil.createDirectories(destinationDirectory);
	  try 
          {  
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
               	// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("metadata");//
		doc.appendChild(rootElement);
                Date currentDate=DateManager.getDateInstance(DateManager.DB_DATE_FORMAT);
                String currentTime="2019-05-24T12:35:36.386";
                DateManager.getCurrentDateAndTime(DateManager.DB_DATE_FORMAT+" "+DateManager.TIMEFORMAT);
		// set attribute to staff element
		Attr attr = doc.createAttribute("xmlns");
		attr.setValue("http://dhis2.org/schema/dxf/2.0");
		rootElement.setAttributeNode(attr);
                Element system = doc.createElement("system");
                rootElement.appendChild(system);

                Element id = doc.createElement("id");
                system.appendChild(id);
                id.setTextContent("acda1f0f-33eb-4c5a-bf65-bb8806d697a3");

                Element rev = doc.createElement("rev");
                system.appendChild(rev);
                rev.setTextContent("2cdba60");
                
                Element version = doc.createElement("version");
                system.appendChild(version);
                version.setTextContent("2.29");
                
                Element date = doc.createElement("date");
                system.appendChild(date);
                date.setTextContent(currentTime);
                
                //
                List duplicateList=new ArrayList();
                Element organisationUnits = doc.createElement("organisationUnits");
                rootElement.appendChild(organisationUnits); 
                
                for(int i=0; i<list.size(); i++)
                {
                       OrganizationUnit ou=(OrganizationUnit)list.get(i);  
                       if(duplicateList.contains(ou.getOrgunitId()))
                       continue;
                       duplicateList.add(ou.getOrgunitId());
                       Element organisationUnit = doc.createElement("organisationUnit");
                       organisationUnits.appendChild(organisationUnit); 

                        Attr created = doc.createAttribute("created");
                        created.setValue(currentTime);
                        organisationUnit.setAttributeNode(created);
                        
                        Attr lastUpdated = doc.createAttribute("lastUpdated");
                        lastUpdated.setValue(currentTime);
                        organisationUnit.setAttributeNode(lastUpdated);
                        
                        Attr name = doc.createAttribute("name");
                        name.setValue(ou.getOrgunitName());
                        organisationUnit.setAttributeNode(name);
                        
                        Attr ouId = doc.createAttribute("id");
                        ouId.setValue(ou.getOrgunitId());
                        organisationUnit.setAttributeNode(ouId);
                        
                        Attr shortName = doc.createAttribute("shortName");
                        shortName.setValue(ou.getShortName());
                        organisationUnit.setAttributeNode(shortName);
                        
                        
                        if(ou.getOuCode() !=null)
                        {
                            Attr code = doc.createAttribute("code");
                            code.setValue(ou.getOuCode());
                            organisationUnit.setAttributeNode(code);
                        }
                                                
                        Element path = doc.createElement("path");
                        organisationUnit.appendChild(path); 
                        path.setTextContent(ou.getPath());
                        
                        
                        Element featureType = doc.createElement("featureType");
                        organisationUnit.appendChild(featureType); 
                        featureType.setTextContent("NONE");
                        
                        Element openingDate = doc.createElement("openingDate");
                        organisationUnit.appendChild(openingDate); 
                        openingDate.setTextContent("2018-10-01T00:00:00.000");
                        
                        if(ou.getParentId() !=null && ou.getParentId().trim().length()==11)
                        {
                            Element parent = doc.createElement("parent");
                            organisationUnit.appendChild(parent); 
                            Attr parentId = doc.createAttribute("id");
                            parentId.setValue(ou.getParentId());
                            parent.setAttributeNode(parentId);
                        }
                        
                        Element lastUpdatedBy = doc.createElement("lastUpdatedBy");
                        organisationUnit.appendChild(lastUpdatedBy); 
                                                
                        Attr lastUpdatedById = doc.createAttribute("id");
                        lastUpdatedById.setValue("PJOS1aBbQqQ");
                        lastUpdatedBy.setAttributeNode(lastUpdatedById);
                        
                        Element userElement = doc.createElement("user");
                        organisationUnit.appendChild(userElement); 
                                                
                        Attr userId = doc.createAttribute("id");
                        userId.setValue("PJOS1aBbQqQ");
                        userElement.setAttributeNode(userId);
                }

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
                fileName=fileName.replace("\\", "-");
                fileName=fileName.replace("/", "-");
                String xmlFileName=fileName+".xml";
                String filePath=destinationDirectory+"/"+xmlFileName;
		StreamResult result = new StreamResult(new File(filePath));
                listOfFilesToBeZipped.add(filePath);
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		transformer.transform(source, result);

		System.out.println("File saved!");

	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
          ZipHandler zipHandler=new ZipHandler();
            zipHandler.zipFile(listOfFilesToBeZipped, destinationDirectory, fileName);
    }
    public String uploadDataToDhis2(List dataList,String dhisUrl,String userName,String password,boolean saveZeroValues)
    {
        String dhisOutput="";
        List errorList=new ArrayList();
            String msg=null;
        
            if(dhisUrl !=null)
            dhisUrl=dhisUrl.trim();
            if(userName !=null)
            userName=userName.trim();
            if(password !=null)
            password=password.trim();
            System.err.println("About to write to URL");
            try
            {
                if(dataList !=null)
                {
                    int totalRecords=dataList.size();
                    String urlParameters=null;
                    String startDate=null;
                    String period=null;
                    String userPass = userName+":"+password;
                    String basicAuth = "Basic " + Base64.encode(userPass.getBytes(), Base64.BASE64DEFAULTLENGTH);//or
                    for(int i=0; i<dataList.size(); i++)
                    {
                        try
                        {
                            //if(i==1)
                            //break;
                            period=null;
                            FinalFlatTable fft=(FinalFlatTable)dataList.get(i);
                            if((fft.getValue()==0 && !saveZeroValues))// || !fft.getConsumerOrganizationUnitId().trim().equalsIgnoreCase("C5xnUGyGUPy"))
                            continue;
                            System.err.println("fft.getConsumerOrganizationUnitId() is "+fft.getConsumerOrganizationUnitId());
                            //System.err.println("dhisUrl is "+dhisUrl);
                            //System.err.println("userPass is "+userPass);
                            startDate=fft.getStartDate();
                            if(startDate !=null && startDate.indexOf("-") !=-1)
                            {
                                String[] startDateArray=startDate.split("-");
                                period=startDateArray[0]+startDateArray[1];
                            }
                            else
                            //period="2019W1";
                            period=fft.getPeriod();
                            urlParameters="/api/dataValues?de="+fft.getConsumerDataElementId()+"&ou="+fft.getConsumerOrganizationUnitId()+"&co="+fft.getConsumerCategoryId()+"&pe="+period+"&value="+fft.getValue();
                            String urlPath=dhisUrl+urlParameters;
                            URL url = new URL(urlPath);
                            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                            urlConnection.setRequestProperty("Authorization", basicAuth);
                            urlConnection.setDoOutput(true);
                            urlConnection.connect();
                            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                            out.close();
                            msg=urlConnection.getResponseMessage();
                            System.out.println("Record "+i+" of "+totalRecords+" "+urlConnection.getResponseMessage());
                            //System.out.println(urlConnection.getResponseMessage());
                            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                            String decodedString;
                            while ((decodedString = in.readLine()) != null) 
                            {
                                dhisOutput+=decodedString;
                                System.out.println("decodedString is --"+decodedString+"--");
                                if(decodedString !=null && decodedString.equalsIgnoreCase("Created"))
                                {
                                    System.out.println("Record updated in DHIS");
                                    break;
                                }
                            }
                            in.close();
                        }
                        catch(MalformedURLException mue)
                        {
                            msg="Error occured. Check details "+mue.getMessage();
                            errorList.add("Bad URL");
                            break;
                        }
                        catch(Exception ex)
                        {
                            errorList.add("Unable to save data at line "+i+". possible meta data mismatch occured "+ex.getMessage());
                            msg="Error occured. Check details "+ex.getMessage();
                            System.err.println(ex.getMessage());//ex.printStackTrace();
                            continue;
                        } 
                    }
                }
            }
            catch(Exception ex)
            {
                errorList.add("Error occured while reading data");
                msg="Error occured. Check details";
                ex.printStackTrace();
            }
            for(int j=0; j<errorList.size(); j++)
            {
                System.err.println(errorList.get(j).toString());
            }
        return msg;
    }
    public String uploadFromDataValuesToDhis2(String sourceId,String dhisUrl,String userName,String password,boolean saveZeroValues)
    {
        String dhisOutput="";
        List errorList=new ArrayList();
            String msg=null;
            
            if(dhisUrl !=null)
            dhisUrl=dhisUrl.trim();
            if(userName !=null)
            userName=userName.trim();
            if(password !=null)
            password=password.trim();
            System.err.println("About to write to URL");
            
            try
            {
                List dataList=DaoUtility.getDatavalueDaoInstance().getDataValuesByDhisId(sourceId);
                if(dataList !=null)
                {
                    String urlParameters=null;
                    int totalRecords=dataList.size();
                    //String startDate=null;
                    String period=null;
                    String userPass = userName+":"+password;
                    String basicAuth = "Basic " + Base64.encode(userPass.getBytes(), Base64.BASE64DEFAULTLENGTH);//or
                    for(int i=0; i<dataList.size(); i++)
                    {
                        try
                        {
                            //if(i>0)
                            //break;
                            period=null;
                            DataValue dv=(DataValue)dataList.get(i);
                            if((dv.getDvalue().equalsIgnoreCase("0") && !saveZeroValues))// || !fft.getConsumerOrganizationUnitId().trim().equalsIgnoreCase("C5xnUGyGUPy"))
                            continue;
                            if(dv.getOrgUnitId()==null || dv.getOrgUnitId().trim().length()<11)
                              continue;
                              else if(dv.getDataElementId()==null || dv.getDataElementId().trim().length()<11)
                              continue;
                              else if(dv.getCategoryOptionComboId()==null || dv.getCategoryOptionComboId().trim().length()<11)
                              continue;
                              else if(dv.getPeriod()==null || dv.getPeriod().trim().length()<7)
                              continue;
                            //System.err.println("dv.getOrgUnitName() is "+dv.getOrgUnitName()+" dv.getDataElementName(): "+dv.getDataElementName()+" dv.getCategoryOptionComboName(): "+dv.getCategoryOptionComboName()+" dv.getPeriod(): "+dv.getPeriod()+" dv.getDvalue(): "+dv.getDvalue());
                            
                            period=dv.getPeriod();
                            urlParameters="/api/dataValues?de="+dv.getDataElementId()+"&ou="+dv.getOrgUnitId()+"&co="+dv.getCategoryOptionComboId()+"&pe="+period+"&value="+dv.getDvalue();
                            String urlPath=dhisUrl+urlParameters;
                            URL url = new URL(urlPath);
                            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                            urlConnection.setRequestProperty("Authorization", basicAuth);
                            urlConnection.setDoOutput(true);
                            urlConnection.connect();
                            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                            out.close();
                            msg=urlConnection.getResponseMessage();
                            System.out.println("Record "+i+" of "+totalRecords+" "+msg);
                            updateDatavalue(dv);
                            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                            /*String decodedString;
                            while ((decodedString = in.readLine()) != null) 
                            {
                                dhisOutput+=decodedString;
                                System.out.println("decodedString is --"+decodedString+"--");
                                if(decodedString !=null && decodedString.equalsIgnoreCase("Created"))
                                {
                                    System.out.println("Record updated in DHIS");
                                    break;
                                }
                            }*/
                            in.close();
                        }
                        catch(MalformedURLException mue)
                        {
                            msg="Error occured. Check details "+mue.getMessage();
                            errorList.add("Bad URL");
                            break;
                        }
                        catch(Exception ex)
                        {
                            errorList.add("Unable to save data at line "+i+". possible meta data mismatch occured "+ex.getMessage());
                            msg="Error occured. Check details "+ex.getMessage();
                            System.err.println(ex.getMessage());//ex.printStackTrace();
                            continue;
                        } 
                    }
                }
            }
            catch(Exception ex)
            {
                errorList.add("Error occured while reading data");
                msg="Error occured. Check details";
                ex.printStackTrace();
            }
            for(int j=0; j<errorList.size(); j++)
            {
                System.err.println(errorList.get(j).toString());
            }
        return msg;
    }
    public String uploadDataToDhis2(String dhisUrl,String userName,String password)
    {
       List errorList=new ArrayList();
       String dhisOutput="";
       String msg=null;
        
            if(dhisUrl !=null)
            dhisUrl=dhisUrl.trim();
            if(userName !=null)
            userName=userName.trim();
            if(password !=null)
            password=password.trim();
            System.err.println("About to write to URL");
            try
            {
                List dataList=util.getFinalFlatTableDaoInstance().getAllRecords();
                if(dataList !=null)
                {
                    String urlParameters=null;
                    String startDate=null;
                    String period=null;
                    //String userPass = "smomoh:cHerry100";
                    String userPass = userName+":"+password;
                    String basicAuth = "Basic " + Base64.encode(userPass.getBytes(), Base64.BASE64DEFAULTLENGTH);//or
                    for(int i=0; i<dataList.size(); i++)
                    {
                        try
                        {
                            if(i==1)
                            break;
                            period=null;
                            FinalFlatTable fft=(FinalFlatTable)dataList.get(i);
                            startDate=fft.getStartDate();
                            String[] startDateArray=startDate.split("-");
                            period=startDateArray[0]+startDateArray[1];
                            //dhisUrl="https://dhis-backup.sidhas.org/fhi360/api/dataValues?de=UPmy66vapEu&ou=XjYc9K3Gf3I&co=YDI6IonLTQe&pe=2019w15&value=28";//Weekly dataset
                            //dhisUrl="https://dhis-backup.sidhas.org/fhi360/api/dataValues?de=A7QFqc44tlD&ou=lgXOBlbenNb&co=B6FaFaoS9cC&pe=201903&value=28";
                            urlParameters="/dataValues?de="+fft.getConsumerDataElementId()+"&ou="+fft.getConsumerOrganizationUnitId()+"&co="+fft.getConsumerCategoryId()+"&pe="+period+"&value="+fft.getValue();
                            String urlPath=dhisUrl+urlParameters;//"https://play.dhis2.org/demo/api/26/dataValues?de=u6yhzgWPIsj&ou=DiszpKrYNg8&pe=201704&co=YBhrfw1dP2J&value=78";  
                            //de=BOSZApCrBni&ou=DiszpKrYNg8&pe=201704&co=YBhrfw1dP2J&value=26
                            URL url = new URL(urlPath);
                            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                            urlConnection.setRequestProperty("Authorization", basicAuth);
                            urlConnection.setDoOutput(true);
                            urlConnection.connect();
                            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                            out.close();
                            msg=urlConnection.getResponseMessage();
                            System.out.println(urlConnection.getResponseMessage());
                            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                            String decodedString;
                            while ((decodedString = in.readLine()) != null) 
                            {
                                dhisOutput+=decodedString;
                                System.out.println("decodedString is --"+decodedString+"--");
                                if(decodedString !=null && decodedString.equalsIgnoreCase("Created"))
                                {
                                    System.out.println("Record updated in DHIS");
                                    break;
                                }
                            }
                            in.close();
                        }
                        catch(MalformedURLException mue)
                        {
                            msg="Error occured. Check details ";
                            errorList.add("Bad URL");
                            break;
                        }
                        catch(Exception ex)
                        {
                            errorList.add("Unable to save data at line "+i+". possible meta data mismatch occured");
                            msg="Error occured. Check details";
                            System.err.println(ex.getMessage());//ex.printStackTrace();
                            continue;
                        } 
                    }
                }
            }
            catch(Exception ex)
            {
                errorList.add("Error occured while reading data");
                msg="Error occured. Check details";
                ex.printStackTrace();
            }
            for(int j=0; j<errorList.size(); j++)
            {
                System.err.println(errorList.get(j).toString());
            }
        return msg;
    }
    public String testDataUploadToDhis2(String dhisUrl,String userName,String password)
    {
       List errorList=new ArrayList();
       String dhisOutput="";
       String msg=null;
        
            if(dhisUrl !=null)
            dhisUrl=dhisUrl.trim();
            if(userName !=null)
            userName=userName.trim();
            if(password !=null)
            password=password.trim();
            System.err.println("About to write to URL");
            try
            {
                List dataList=util.getFinalFlatTableDaoInstance().getAllRecords();
                if(dataList !=null)
                {
                    String urlParameters=null;
                    String startDate=null;
                    String period=null;
                    //String userPass = "smomoh:cHerry100";
                    String userPass = userName+":"+password;
                    String basicAuth = "Basic " + Base64.encode(userPass.getBytes(), Base64.BASE64DEFAULTLENGTH);//or
                    for(int i=0; i<dataList.size(); i++)
                    {
                        try
                        {
                            if(i==1)
                            break;
                            period=null;
                            FinalFlatTable fft=(FinalFlatTable)dataList.get(i);
                            startDate=fft.getStartDate();
                            String[] startDateArray=startDate.split("-");
                            period=startDateArray[0]+startDateArray[1];
                            dhisUrl="https://dhis-backup.sidhas.org/fhi360/api/dataValues?de=UPmy66vapEu&ou=XjYc9K3Gf3I&co=YDI6IonLTQe&pe=2019W01&value=388";//Weekly dataset
                            //dhisUrl="https://dhis-backup.sidhas.org/fhi360/api/dataValues?de=A7QFqc44tlD&ou=lgXOBlbenNb&co=B6FaFaoS9cC&pe=201903&value=28";
                            urlParameters="/dataValues?de="+fft.getConsumerDataElementId()+"&ou="+fft.getConsumerOrganizationUnitId()+"&co="+fft.getConsumerCategoryId()+"&pe="+period+"&value="+fft.getValue();
                            String urlPath=dhisUrl;//+urlParameters;//"https://play.dhis2.org/demo/api/26/dataValues?de=u6yhzgWPIsj&ou=DiszpKrYNg8&pe=201704&co=YBhrfw1dP2J&value=78";  
                            //de=BOSZApCrBni&ou=DiszpKrYNg8&pe=201704&co=YBhrfw1dP2J&value=26
                            URL url = new URL(urlPath);
                            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                            urlConnection.setRequestProperty("Authorization", basicAuth);
                            urlConnection.setDoOutput(true);
                            urlConnection.connect();
                            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                            out.close();
                            msg=urlConnection.getResponseMessage();
                            System.out.println(urlConnection.getResponseMessage());
                            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                            String decodedString;
                            while ((decodedString = in.readLine()) != null) 
                            {
                                dhisOutput+=decodedString;
                                System.out.println("decodedString is --"+decodedString+"--");
                                if(decodedString !=null && decodedString.equalsIgnoreCase("Created"))
                                {
                                    System.out.println("Record updated in DHIS");
                                    break;
                                }
                            }
                            in.close();
                        }
                        catch(MalformedURLException mue)
                        {
                            msg="Error occured. Check details ";
                            errorList.add("Bad URL");
                            break;
                        }
                        catch(Exception ex)
                        {
                            errorList.add("Unable to save data at line "+i+". possible meta data mismatch occured");
                            msg="Error occured. Check details";
                            System.err.println(ex.getMessage());//ex.printStackTrace();
                            continue;
                        } 
                    }
                }
            }
            catch(Exception ex)
            {
                errorList.add("Error occured while reading data");
                msg="Error occured. Check details";
                ex.printStackTrace();
            }
            for(int j=0; j<errorList.size(); j++)
            {
                System.err.println(errorList.get(j).toString());
            }
        return msg;
    }
    public void updateDatavalue(DataValue dv)
{
    try
    {
        if(dv !=null)
        {
            dv.setUploadedToDhis2(1);
            DaoUtility.getDatavalueDaoInstance().updateDataValue(dv);
            //System.err.println("dv at is dv.getOrgUnitName(): "+dv.getOrgUnitName()+" dv.getDataElementName(): "+dv.getDataElementName()+" dv.getCategoryOptionComboName(): "+dv.getCategoryOptionComboName()+" dv.getPeriod(): "+dv.getPeriod()+" dv.getDvalue(): "+dv.getDvalue()+" updated");
        }
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
}
}
