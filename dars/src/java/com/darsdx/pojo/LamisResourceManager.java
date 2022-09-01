/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.pojo;

import com.darsdx.business.CategoryOptionCombo;
import com.darsdx.business.DataElement;
import com.darsdx.business.DataValue;
import com.darsdx.business.DhisOperation;
import com.darsdx.business.FinalFlatTable;
import com.darsdx.business.OrganizationUnit;
import com.darsdx.dao.DaoUtility;
import com.darsdx.util.DateManager;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author smomoh
 */
public class LamisResourceManager 
{
public void moveLamisDataToDhis2(String dhisUrl,String dhisUserName,String dhisPassword,String period,boolean saveZeroValues)
{
    DhisOperation dop=new DhisOperation();
    dop.uploadFromDataValuesToDhis2("lamisId",dhisUrl,dhisUserName,dhisPassword,saveZeroValues);
     
}
public void getDataFromLamis(String period)
{
    String periodQuery="";
    if(period !=null)
    periodQuery="/"+period;
    String urlString="http://lamis3.sidhas.org:8080/resources/webservice/download/dhisvalue"+periodQuery;
    System.err.println("urlString is "+urlString);
    List xmlDataValueList=getDataValueFromLamis(urlString,null,null); 
    if(xmlDataValueList !=null)
    {
        String xmlDoc="";
        String startElement="";
        String endElement="";
        String node="";
        String nodes="";
        int count=0;
        List xmlList=new ArrayList();
        for(int i=0; i<xmlDataValueList.size(); i++)
        {
            node=xmlDataValueList.get(i).toString();
            if(node !=null)
            {
                node=node.trim();
                if(node.equalsIgnoreCase("<dhisvalue>"))
                startElement=node;
                else if(node.startsWith("<data_element_id_dhis>") || node.startsWith("<category_id_dhis>") || node.startsWith("<facility_id_dhis>") || node.startsWith("<period>") || node.startsWith("<value>"))
                nodes+=node;
                else if(node.equalsIgnoreCase("</dhisvalue>"))
                {
                    endElement=node;
                    xmlDoc=startElement+nodes+endElement;
                    xmlList.add(xmlDoc);
                    nodes="";
                    count++;
                    //System.err.println("xmlDoc at "+count+" is "+xmlDoc);
                    System.err.println("xmlList.size() is "+xmlList.size());
                }
            }
        }
        
        List dataValueList=processDatavalue(xmlList,"datasetId","lamisId");
        if(dataValueList !=null)
        System.err.println(dataValueList.size()+" datavalues extracted");
        //dataValueList=getDataValueListFromDhisFlatTable(fftValueList);
        saveDatavalues(dataValueList);
        
        //dop.uploadDataToDhis2(fftValueList,dhisUrl, dhisUserName, dhisPassword,true);
    }  
}
public List processDatavalue(List list,String datasetId,String dhisId)
{
    List dvList=new ArrayList();
    try
    {
        
        //List splitList=XmlProcessor.splitXmlToString(list, "<dhisvalue>");
        
        if(list !=null)
        {
            System.err.println("splitList.size() is "+list.size()+"str");
            int count=0;
            for(int i=0; i<list.size(); i++)
            {
                //System.err.println(list.get(i).toString());
                DataValue dv=getLamisDatavalueFromXml(list.get(i).toString(),datasetId,dhisId);
                if(dv !=null && dv.getDataElementId() !=null && dv.getOrgUnitId() !=null)
                dvList.add(dv);
                //System.err.println("Datavalue at "+i+" is DataElementId:"+dv.getDataElementId()+" CategoryOptionComboId: "+dv.getCategoryOptionComboId()+" dv.getOrgUnitId():"+dv.getOrgUnitId()+" period:"+dv.getPeriod()+" value:"+dv.getDvalue());
                count++;
            }
            
        }
    }
    catch(Exception ex)
    {
        System.err.println("Unable to obtain data "+ex.getMessage());
        ex.printStackTrace();
    }
    return dvList;
}
public static List getDataValueFromLamis(String urlString,String userName,String password) 
{
    //System.err.println("urlString is "+urlString+"str");
    List resultList=UrlResourceManager.getUrlResource(urlString, userName, password);
    return resultList;
}
public DataValue getLamisDatavalueFromXml(String xmlData,String datasetId,String dhisId) throws Exception
{
    //FinalFlatTable fft=null;
    DataValue dv=null;
    
    String dataElementId=getAttributeValue(xmlData,"<data_element_id_dhis>");
    String categoryOptionCombo=getAttributeValue(xmlData,"<category_id_dhis>");
    String attributeOptionCombo="lamis";//getAttributeValue(xmlData,"attributeOptionCombo");
    String period=getAttributeValue(xmlData,"<period>");
    String orgUnitId=getAttributeValue(xmlData,"<facility_id_dhis>");
    String value=getAttributeValue(xmlData,"<value>");
    String lastUpdated=DateManager.getCurrentDate();//getAttributeValue(xmlData,"lastUpdated");
    String dateCreated=DateManager.getCurrentDate();//getAttributeValue(xmlData,"created");
    
    dv=new DataValue();
    dv.setDatasetId(datasetId);
    dv.setCategoryOptionComboId("default");
    dv.setAttributeOptionCombo(attributeOptionCombo);
    if(categoryOptionCombo !=null)
    dv.setCategoryOptionComboId(categoryOptionCombo);
    dv.setDhisId(dhisId);
    
    dv.setLastModifiedDate(DateManager.getCurrentDateAsDateObject());
    dv.setDataElementId(dataElementId);
    dv.setPeriod(period);
    dv.setOrgUnitId(orgUnitId);
    if(value !=null)
    dv.setDvalue(value);
    dv.setLastUpdated(lastUpdated);
    dv.setStartDate(DateManager.getDateInstance(dateCreated));
    System.err.println("dv.deId:"+dv.getDataElementId()+" dv.getCategoryOptionComboId(): "+dv.getCategoryOptionComboId()+" dv.getOrgUnitId:"+dv.getOrgUnitId()+" period:"+dv.getPeriod()+" value:"+dv.getDvalue()+" lastUpdated:"+dv.getLastUpdated()+" dateCreated:"+dv.getStartDate());
    

    return dv;
}
public FinalFlatTable getLamisDataFromXmlAsFlatTableObject(String xmlData,String datasetId,String dhisId) throws Exception
{
    //DataValue dv=null;
    FinalFlatTable fft=null;
    String dataElementId=getAttributeValue(xmlData,"<data_element_id_dhis>");
    String categoryOptionCombo=getAttributeValue(xmlData,"<category_id_dhis>");
    String attributeOptionCombo="lamis";//getAttributeValue(xmlData,"attributeOptionCombo");
    String period=getAttributeValue(xmlData,"<period>");
    String orgUnitId=getAttributeValue(xmlData,"<facility_id_dhis>");
    String value=getAttributeValue(xmlData,"<value>");
    String lastUpdated=DateManager.getCurrentDate();//getAttributeValue(xmlData,"lastUpdated");
    String dateCreated=DateManager.getCurrentDate();//getAttributeValue(xmlData,"created");
    fft=new FinalFlatTable();
    //dv=new DataValue();
    fft.setDatasetId(datasetId);
    fft.setConsumerCategoryId("default");
    fft.setAttributeOptionCombo(attributeOptionCombo);
    if(categoryOptionCombo !=null)
    fft.setConsumerCategoryId(categoryOptionCombo);
    fft.setConsumerInstance(dhisId);
    //fft.set.setDatasetId(datasetId);
    //dv.setLastModifiedDate(DateManager.getCurrentDateAsDateObject());
    fft.setConsumerDataElementId(dataElementId);
    fft.setPeriod(period);
    fft.setConsumerOrganizationUnitId(orgUnitId);
    if(value !=null)
    fft.setValue(Integer.parseInt(value));
    //dv.setLastUpdated(lastUpdated);
    //fft.setStartDate(DateManager.getDateInstance(dateCreated));
    //System.err.println("dv.deId:"+dv.getDataElementId()+" dv.getCategoryOptionComboId(): "+dv.getCategoryOptionComboId()+" dv.getOrgUnitId:"+dv.getOrgUnitId()+" period:"+dv.getPeriod()+" value:"+dv.getDvalue()+" lastUpdated:"+dv.getLastUpdated()+" dateCreated:"+dv.getStartDate());
    //System.err.println("fft.getConsumerDataElementId():"+fft.getConsumerDataElementId()+" fft.getConsumerCategoryId(): "+fft.getConsumerCategoryId()+" fft.getConsumerOrganizationUnitId():"+fft.getConsumerOrganizationUnitId()+" period:"+fft.getPeriod()+" value:"+fft.getValue());

    return fft;
}
public String getAttributeValue(String xmlStr,String attributeName) throws Exception
{
    String attributeValue=null;
    if(xmlStr !=null && attributeName !=null)
    {
        if(xmlStr.indexOf(attributeName) !=-1)
        {
            String[] strArray=xmlStr.split(attributeName);
            if(strArray !=null && strArray.length>1)
            {
                String str1=strArray[1];
                //String str2=str1.replace("=\"", "");
                attributeValue=str1.substring(0,str1.indexOf("</"));
                //System.err.println(attributeName+" value is "+attributeValue);
            }
        }
    }
    return attributeValue;
}
private List getDataValueListFromDhisFlatTable(List dhisFlatTableList)
{
    List dataValueList=new ArrayList();
    try
    {
        if(dhisFlatTableList !=null)
        {
            DaoUtility util=new DaoUtility();
            OrganizationUnit ou=null;
            DataElement de=null;
            CategoryOptionCombo cc=null;
            FinalFlatTable fft=null;
            DataValue dv=null;
            for(int i=0; i<dhisFlatTableList.size(); i++)
            {
                fft=(FinalFlatTable)dhisFlatTableList.get(i);
                ou=DaoUtility.getOrganizationUnitDaoInstance().getOrganizationUnit(fft.getConsumerOrganizationUnitId());
                de=DaoUtility.getDataElementDaoInstance().getDataElement(fft.getConsumerDataElementId());
                cc=DaoUtility.getCategoryOptionComboDaoInstance().getCategoryOptionCombination(fft.getConsumerCategoryId());
                dv=new DataValue();
                dv.setAttributeOptionCombo("xxxxxxxxxxx");
                dv.setCategoryOptionComboId(fft.getConsumerCategoryId());
                dv.setDataElementId(fft.getConsumerDataElementId());
                if(fft.getDatasetId()==null)
                dv.setDatasetId("xxxxxxxxxxx");
                else
                dv.setDatasetId(fft.getDatasetId());
                dv.setDhisId(fft.getConsumerInstance());
                dv.setOrgUnitId(fft.getConsumerOrganizationUnitId());
                dv.setDvalue(fft.getValue()+"");
                dv.setLastModifiedDate(DateManager.getCurrentDateAsDateObject());
                dv.setLastUpdated(DateManager.getCurrentDate());
                dv.setStartDate(DateManager.getCurrentDateAsDateObject());
                dv.setPeriod(fft.getPeriod());
                if(de !=null)
                dv.setDataElementName(de.getDataElementName());
                if(cc !=null)
                dv.setCategoryOptionComboName(cc.getCategoryOptionComboName());
                if(ou !=null)
                dv.setOrgUnitName(ou.getOrgunitName());
                dataValueList.add(dv);
                System.err.println("dv at "+i+" is dv.getOrgUnitName(): "+dv.getOrgUnitName()+" dv.getDataElementName(): "+dv.getDataElementName()+" dv.getCategoryOptionComboName(): "+dv.getCategoryOptionComboName()+" dv.getPeriod(): "+dv.getPeriod()+" dv.getDvalue(): "+dv.getDvalue()+" added to dvList");
            }
        }
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
    return dataValueList;
}
public void saveDatavalues(List dataValueList)
{
    try
    {
        if(dataValueList !=null)
        {
            DataValue dv=null;
            for(int i=0; i<dataValueList.size(); i++)
            {
                dv=(DataValue)dataValueList.get(i);
                if(DaoUtility.getDatavalueDaoInstance().getDataValue(dv.getDatasetId(), dv.getDataElementId(), dv.getOrgUnitId(),dv.getCategoryOptionComboId(), dv.getPeriod()) ==null)
                {
                    DaoUtility.getDatavalueDaoInstance().saveDataValue(dv);
                    System.err.println("dv at "+i+" is dv.getOrgUnitName(): "+dv.getOrgUnitName()+" dv.getDataElementName(): "+dv.getDataElementName()+" dv.getCategoryOptionComboName(): "+dv.getCategoryOptionComboName()+" dv.getPeriod(): "+dv.getPeriod()+" dv.getDvalue(): "+dv.getDvalue()+" saved");
                }
                else
                {
                    DaoUtility.getDatavalueDaoInstance().updateDataValue(dv);
                    System.err.println("dv at "+i+" is dv.getOrgUnitName(): "+dv.getOrgUnitName()+" dv.getDataElementName(): "+dv.getDataElementName()+" dv.getCategoryOptionComboName(): "+dv.getCategoryOptionComboName()+" dv.getPeriod(): "+dv.getPeriod()+" dv.getDvalue(): "+dv.getDvalue()+" updated");
                }
            }
        }
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
}
/*public void createFile(String itemToWriteToFile)
{
    String exportDestination="C:/Odm/Transfer/lamisDatavalues.xml";
    try
    {
        System.err.println("itemToWriteToFile is "+itemToWriteToFile);
        StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
        String xmlString = itemToWriteToFile;
        File file = new File(exportDestination);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        bw.write(xmlString);
        bw.flush();
        bw.close();
    }
    catch(Exception ex)
    {
        ex.equals(ex);
    }
}
public List getLamisDatavalueFromXml(File xmlFile)
{
    List list=new ArrayList();
       
    try
	{
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            File file=null;
            Document doc = null;
            String fileName=".xml";

             file=new File(fileName);
             if(xmlFile !=null)
             {
                doc = docBuilder.parse (file);
                // normalize text representation
                doc.getDocumentElement().normalize();
                NodeList listOfObjects = doc.getElementsByTagName("dhisvalue");
                DataValue dv=null;
                for(int s=0; s<listOfObjects.getLength() ; s++)
                {
                     Node firstNode = listOfObjects.item(s);
                     if(firstNode.getNodeType() == Node.ELEMENT_NODE)
                     {
                        String dataElementId=getNodeValue("data_element_id",s,listOfObjects);
                        String categoryOptionCombo=getNodeValue("category_id",s,listOfObjects);
                        String attributeOptionCombo="lamis";
                        attributeOptionCombo=getNodeValue("attributeOptionCombo",s,listOfObjects);
                        String period=getNodeValue("period",s,listOfObjects);
                        String orgUnitId=getNodeValue("facility_id",s,listOfObjects);
                        String value=getNodeValue("value",s,listOfObjects);
                        String lastUpdated=DateManager.getCurrentDate();
                        String dateCreated=DateManager.getCurrentDate();
                        dv=new DataValue();
                        dv.setCategoryOptionComboId("default");
                        dv.setAttributeOptionCombo(attributeOptionCombo);
                        if(categoryOptionCombo !=null)
                        dv.setCategoryOptionComboId(categoryOptionCombo);
                        dv.setDhisId("dhisId");
                        dv.setDatasetId("datasetId");
                        dv.setLastModifiedDate(DateManager.getCurrentDateAsDateObject());
                        dv.setDataElementId(dataElementId);
                        dv.setPeriod(period);
                        dv.setOrgUnitId(orgUnitId);
                        dv.setDvalue(value);
                        dv.setLastUpdated(lastUpdated);
                        dv.setStartDate(DateManager.getDateInstance(dateCreated));
                        System.err.println("dv.deId:"+dv.getDataElementId()+" dv.getCategoryOptionComboId(): "+dv.getCategoryOptionComboId()+" dv.getOrgUnitId:"+dv.getOrgUnitId()+" period:"+dv.getPeriod()+" value:"+dv.getDvalue()+" lastUpdated:"+dv.getLastUpdated()+" dateCreated:"+dv.getStartDate());
                     }
                }
            }
        }
	catch (SAXParseException err)
	{
		err.printStackTrace();
        }
        catch (SAXException e)
        {
		Exception x = e.getException ();
		((x == null) ? e : x).printStackTrace ();
	}
	catch (Exception ex)
	{
		ex.printStackTrace ();
	}
    return list;
}
private int getIntegerNodeValue(String value)
{
    int intValue=0;
    try
    {
        if(value !=null)
        intValue=Integer.parseInt(value);
    }
    catch(NumberFormatException nfe)
    {
        intValue=0;
    }
    
    return intValue;
}
private static String getNodeValue(String value,int s,NodeList listOfObjects)
{
    String elementValue=" ";
    Node firstPersonNode = listOfObjects.item(s);
    Element firstPersonElement = (Element)firstPersonNode;
    if(firstPersonElement !=null)
    {
        NodeList firstNameList = firstPersonElement.getElementsByTagName(value);
        Element firstNameElement = (Element)firstNameList.item(0);

        NodeList textFNList =null;
        if(firstNameElement !=null)
        {
            textFNList =firstNameElement.getChildNodes();
        }
        try
        {
            if((Node)textFNList==null)
            elementValue="";
            else if((Node)textFNList.item(0)==null)
            elementValue="";
            else if(((Node)textFNList.item(0)).getNodeValue()==null || (((Node)textFNList.item(0)).getNodeValue()).equals("") || (((Node)textFNList.item(0)).getNodeValue()).equals(" ") || (((Node)textFNList.item(0)).getNodeValue()).equals("none"))
            elementValue="";
            else
            elementValue=((Node)textFNList.item(0)).getNodeValue();
        }
        catch(NullPointerException npe)
        {
            elementValue="";
        }
    }
    return elementValue;
}*/
}
