/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.operationsmanagement;

import com.darsdx.business.DataValue;
import com.darsdx.dao.DataValueDao;
import com.darsdx.dao.DataValueDaoImpl;
import com.darsdx.pojo.XmlProcessor;
import com.darsdx.util.DateManager;
import com.darsdx.util.ExcelReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class DatavalueManager implements Serializable
{
    DataValueDao dvdao=new DataValueDaoImpl();
    public int processImisDatavalue(String dhisId)
    {
        int numberOfRecords=0;
        try
        {
            ExcelReader er=new ExcelReader();
            List idvlist=er.createAfyaNyotaDataStructureFromDHIS2Datavalue(dhisId);
            if(idvlist !=null)
            {
                numberOfRecords=idvlist.size();
            }
            System.err.println("numberOfRecords is "+numberOfRecords);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return numberOfRecords;
    }
    public String processDatavalue(List list,String datasetId,String dhisId)
    {
        String msg="Data saved successfully";
        try
        {
        List splitList=XmlProcessor.splitXmlToString(list, "<dataValue");
        if(splitList !=null)
        {
            int count=0;
            List dvList=new ArrayList();
            for(int i=0; i<splitList.size(); i++)
            {
                //System.err.println(splitList.get(i).toString());
                DataValue dv=getDatavalueFromXml(splitList.get(i).toString(),datasetId,dhisId);
                if(dv !=null && dv.getDataElementId() !=null && dv.getOrgUnitId() !=null)
                {
                    dvList.add(dv);
                    saveDataValue(dv);
                    count++;
                }
            }
            //msg=count+" records processed";
            msg=saveDataValues(dvList)+" records processed";;
        }
        }
        catch(Exception ex)
        {
            msg="Unable to obtain data "+ex.getMessage();
        }
        return msg;
    }
    public DataValue getDatavalueFromXml(String xmlData,String datasetId,String dhisId) throws Exception
    {
        DataValue dv=null;
        //xmlData="dataElement=\"Jc1WjNKrObY\" period=\"201712\" orgUnit=\"r1HkUFAU2UQ\" value=\"45\" storedBy=\"[aggregated]\" created=\"2018-07-05\" lastUpdated=\"2018-07-05\" comment=\"[aggregated]\"/>";
        String dataElementId=getAttributeValue(xmlData,"dataElement");
        String categoryOptionCombo=getAttributeValue(xmlData,"categoryOptionCombo");
        String attributeOptionCombo=getAttributeValue(xmlData,"attributeOptionCombo");
        String period=getAttributeValue(xmlData,"period");
        String orgUnitId=getAttributeValue(xmlData,"orgUnit");
        String value=getAttributeValue(xmlData,"value");
        String lastUpdated=getAttributeValue(xmlData,"lastUpdated");
        String dateCreated=getAttributeValue(xmlData,"created");
        dv=new DataValue();
        dv.setCategoryOptionComboId("default");
        dv.setAttributeOptionCombo(attributeOptionCombo);
        if(categoryOptionCombo !=null)
        dv.setCategoryOptionComboId(categoryOptionCombo);
        dv.setDhisId(dhisId);
        dv.setDatasetId(datasetId);
        dv.setLastModifiedDate(DateManager.getCurrentDateAsDateObject());
        dv.setDataElementId(dataElementId);
        dv.setPeriod(period);
        dv.setOrgUnitId(orgUnitId);
        dv.setDvalue(value);
        dv.setLastUpdated(lastUpdated);
        dv.setStartDate(DateManager.getDateInstance(dateCreated));
        System.err.println("dv.deId:"+dv.getDataElementId()+" dv.getCategoryOptionComboId(): "+dv.getCategoryOptionComboId()+" dv.getOrgUnitId:"+dv.getOrgUnitId()+" period:"+dv.getPeriod()+" value:"+dv.getDvalue()+" lastUpdated:"+dv.getLastUpdated()+" dateCreated:"+dv.getStartDate());
        
        return dv;
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
                    String str2=str1.replace("=\"", "");
                    attributeValue=str2.substring(0, str2.indexOf("\""));
                    //System.err.println(attributeName+" value is "+attributeValue);
                }
            }
        }
        return attributeValue;
    }
    public int saveDataValues(List dvList)
    {
        int count=0;
        try
        {
            if(dvList !=null)
            {
                DataValueDao dvdao=new DataValueDaoImpl();
                for(int i=0; i<dvList.size(); i++)
                {
                    DataValue dv=(DataValue)dvList.get(i);
                    saveDataValue(dv);
                    //dvdao.saveOrUpdateDataValue(dv);
                    count++;
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return count;
    }
    public int saveDataValue(DataValue dv)
    {
        int count=0;
        try
        {
            if(dv !=null)
            {
                dvdao.saveOrUpdateDataValue(dv);
                //count++;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return count;
    }
}
