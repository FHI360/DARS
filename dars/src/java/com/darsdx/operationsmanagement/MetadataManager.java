/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.operationsmanagement;

import com.darsdx.business.DxCategoryCombination;
import com.darsdx.business.DxDataElement;
import com.darsdx.business.DxOrganizationUnit;
import com.darsdx.business.FinalFlatTable;
import com.darsdx.dao.DaoUtil;
import com.darsdx.dao.DxOrganizationUnitDao;
import com.darsdx.dao.DxOrganizationUnitDaoImpl;
import com.darsdx.pojo.XmlExtractor;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class MetadataManager implements Serializable
{
    XmlExtractor xmlExtractor=new XmlExtractor();
    public String processOrgUnit(String xmlFilePath,String dhisInstance)
    {
        xmlExtractor.readOrgUnitFromXml(xmlFilePath,dhisInstance);
        String msg="";
        
        return msg;
    }
    public String processDataElement(String xmlFilePath,String dhisInstance)
    {
        String msg="";
        xmlExtractor.readDataElementsFromXml(xmlFilePath,dhisInstance);
        return msg;
    }
    public String processCatCombo(String xmlFilePath,String dhisInstance)
    {
        String msg="";
        xmlExtractor.readCategoryCombinationsFromXml(xmlFilePath, dhisInstance);
        return msg;
    }
    public String assignMetadataId(String consumerInstance)
    {
        //String msg=assignCategoryOptionComboId(dhisInstance);
        //String msg=assignDataElementId(dhisInstance);
        
        DaoUtil util=new DaoUtil();
        int count=0;
        try
        {
            List list=util.getFinalFlatTableDaoInstance().getDistinctOrgUnitNamesFromFinalFlatTable(consumerInstance,false);
            
            if(list !=null && !list.isEmpty())
            {
                System.err.println("list.size() is "+list.size());
                DxOrganizationUnitDao oudao=new DxOrganizationUnitDaoImpl();
                for(int i=0; i<list.size(); i++)
                {
                    String ouName=(String)list.get(i);
                    System.err.println("ouName is "+ouName);
                    if(ouName !=null)
                    {
                        DxOrganizationUnit dou=oudao.getOrganizationUnitByNameAndInstance(ouName, consumerInstance);
                        if(dou !=null)
                        {
                            List fftList=util.getFinalFlatTableDaoInstance().getFinalFlatTableByOrgUnitNameAndDhisInstance(ouName, consumerInstance);
                            
                            if(fftList !=null)
                            {
                                System.err.println("fftList is "+fftList.size());
                                for(int j=0; j<fftList.size(); j++)
                                {
                                    FinalFlatTable fft=(FinalFlatTable)fftList.get(j);
                                    fft.setConsumerOrganizationUnitId(dou.getOrgunitId());
                                    DxCategoryCombination catCombo=util.getDxCategoryCombinationDaoInstance().getCategoryCombinationByNameAndDhisInstance(fft.getConsumerCategoryName(), consumerInstance);
                                    DxDataElement de=util.getDxDataElementDaoInstance().getDataElementByNameAndDhisInstance(fft.getConsumerDataElementName(), consumerInstance);
                                    if(de !=null)
                                    fft.setConsumerDataElementId(de.getDataElementId());
                                    if(catCombo !=null)
                                    fft.setConsumerCategoryId(catCombo.getCatComboId());
                                    util.getFinalFlatTableDaoInstance().updateFinalFlatTable(fft);
                                    count++;
                                    System.err.println("fft ou "+fft.getConsumerOrganizationUnitId()+" de "+fft.getConsumerDataElementId()+" cc "+fft.getConsumerCategoryId()+" at "+count+" updated");
                                }
                            }
                        }
                    }
                }
                assignCategoryOptionComboId(consumerInstance);
                assignDataElementId(consumerInstance);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        String msg=count+" records assigned metadata Ids";
        return msg;
    }
    public String assignDataElementId(String dhisInstance)
    {
        DaoUtil util=new DaoUtil();
        int count=0;
        try
        {
            List list=util.getFinalFlatTableDaoInstance().getDistinctDataElementNamesFromFinalFlatTable(true);//.getDistinctOrgUnitNamesFromFinalFlatTable();
            if(list !=null && !list.isEmpty())
            {
                FinalFlatTable fft=null;
                
                    for(int j=0; j<list.size(); j++)
                    {
                        String deName=(String)list.get(j);
                        DxDataElement de=util.getDxDataElementDaoInstance().getDataElementByNameAndDhisInstance(deName, dhisInstance);
                        if(de !=null)
                        {
                            List deList=util.getFinalFlatTableDaoInstance().getFinalFlatTableByDataElementNameAndDhisInstance(deName, dhisInstance);
                            if(deList !=null)
                            {
                                for(int k=0; k<deList.size(); k++)
                                {
                                    fft=(FinalFlatTable)deList.get(k);
                                    if(fft.getConsumerDataElementId()==null)
                                    {
                                        fft.setConsumerDataElementId(de.getDataElementId());
                                        util.getFinalFlatTableDaoInstance().updateFinalFlatTable(fft);
                                        count++;
                                        System.err.println("fft ou "+fft.getConsumerOrganizationUnitId()+" de "+fft.getConsumerDataElementId()+" cc "+fft.getConsumerCategoryId()+" at "+count+" updated");
                                    }
                                }
                            }
                        }
                    }               
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        String msg=count+" records assigned metadata Ids";
        return msg;
    }
    public String assignCategoryOptionComboId(String dhisInstance)
    {
        DaoUtil util=new DaoUtil();
        int count=0;
        try
        {
            List list=util.getFinalFlatTableDaoInstance().getDistinctCatComboNamesFromFinalFlatTable(true);//.getDistinctOrgUnitNamesFromFinalFlatTable();
            if(list !=null && !list.isEmpty())
            {
                FinalFlatTable fft=null;
                
                    for(int j=0; j<list.size(); j++)
                    {
                        String catComboName=(String)list.get(j);
                        DxCategoryCombination catCombo=util.getDxCategoryCombinationDaoInstance().getCategoryCombinationByNameAndDhisInstance(catComboName, dhisInstance);
                        if(catCombo !=null)
                        {
                            List catComboList=util.getFinalFlatTableDaoInstance().getFinalFlatTableByCatComboNameAndDhisInstance(catComboName, dhisInstance);
                            if(catComboList !=null)
                            {
                                for(int k=0; k<catComboList.size(); k++)
                                {
                                    fft=(FinalFlatTable)catComboList.get(k);
                                    if(fft.getConsumerCategoryId()==null)
                                    {
                                        fft.setConsumerCategoryId(catCombo.getCatComboId());
                                        util.getFinalFlatTableDaoInstance().updateFinalFlatTable(fft);
                                        count++;
                                        System.err.println("fft ou "+fft.getConsumerOrganizationUnitId()+" de "+fft.getConsumerDataElementId()+" cc "+fft.getConsumerCategoryId()+" at "+count+" updated");
                                    }
                                }
                            }
                        }
                    }
                
               
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        String msg=count+" records assigned metadata Ids";
        return msg;
    }
    
    public String matchDataElementId(String xmlFilePath,String dhisInstance)
    {
        String msg="";
        xmlExtractor.readCategoryCombinationsFromXml(xmlFilePath, dhisInstance);
        return msg;
    }
    public String matchCatComboId(String xmlFilePath,String dhisInstance)
    {
        String msg="";
        xmlExtractor.readCategoryCombinationsFromXml(xmlFilePath, dhisInstance);
        return msg;
    }
}
