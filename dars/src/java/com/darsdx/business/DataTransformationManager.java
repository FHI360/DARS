/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.business;

import com.darsdx.dao.DaoUtil;
import com.darsdx.util.AppUtility;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author smomoh
 */
public class DataTransformationManager implements Serializable
{
    DaoUtil util=new DaoUtil();
    AppUtility appUtil=new AppUtility();
    public int applyBusinessRule(String producerInstanceId,String consumerInstanceId)
    {
        int numberSaved=0;
        try
        {
            List businessRulesList=util.getBusinessRuleDaoInstance().getBusinessRules(producerInstanceId, consumerInstanceId);
            if(businessRulesList !=null && !businessRulesList.isEmpty())
            {
                IntermediateFlatTable ift=null;
                int count=0;
                for(Object obj:businessRulesList)
                {
                    BusinessRule br=(BusinessRule)obj;
                    List sourceDataList=util.getSourceDataDaoInstance().getSourceDataByDataElementCategoryAndInstance(br.getProducerDeName(),br.getProducerCatComboName(), br.getProducerInstanceId());
                    if(sourceDataList !=null && !sourceDataList.isEmpty())
                    {
                        DxOrganizationUnitMatch oum=null;
                        for(int i=0; i<sourceDataList.size(); i++)
                        {
                            SourceData sd=(SourceData)sourceDataList.get(i);
                            ift=new IntermediateFlatTable();
                            ift.setConsumerInstance(consumerInstanceId);
                            ift.setConsumerCategoryId(br.getConsumerCatComboId());
                            ift.setConsumerCategoryName(br.getConsumerCatComboName());
                            ift.setConsumerDataElementId(br.getConsumerDeId());
                            ift.setConsumerDataElementName(br.getConsumerDeName());
                            ift.setConsumerOrganizationUnitId(consumerInstanceId);
                            ift.setConsumerOrganizationUnitName(sd.getOrganizationUnit());
                            ift.setDataset(br.getDataset());
                            ift.setFundingBody(sd.getFundingBody());
                            ift.setLastModifiedDate(appUtil.getCurrentDate());
                            ift.setLogic(br.getBusinessLogicName());
                            ift.setOrgUnitGroup(sd.getOrganizationUnitGroup());
                            ift.setOrgUnitType(sd.getOrganizationUnitType());
                            ift.setOrganizationUnitOwnership(sd.getOrganizationUnitOwnership());
                            
                            ift.setProducerDataElement(br.getProducerDeName());
                            ift.setProducerCategory(br.getProducerCatComboName());
                            ift.setProducerInstance(producerInstanceId);
                            ift.setProducerOrganizationUnit(sd.getOrganizationUnit());
                            ift.setStartDate(sd.getStartDate());
                            ift.setValue(sd.getValue());
                            oum=util.getDxOrganizationUnitMatchDaoInstance().getDxOrganizationUnitMatchByProducerOuNamesAndInstances(ift.getProducerOrganizationUnit(), producerInstanceId, consumerInstanceId);
                            if(oum !=null)
                            {
                                ift.setConsumerOrganizationUnitId(oum.getConsumerOrgUnitId());
                                ift.setConsumerOrganizationUnitName(oum.getConsumerOrgUnitName());
                            }
                            IntermediateFlatTable existingIft=util.getIntermediateFlatTableDaoInstance().getIntermediateFlatTable(ift);
                            if(existingIft==null)
                            util.getIntermediateFlatTableDaoInstance().saveIntermediateFlatTable(ift);
                            else
                            {
                               ift.setRecordId(existingIft.getRecordId());
                               util.getIntermediateFlatTableDaoInstance().updateIntermediateFlatTable(ift);
                            }
                            numberSaved++;
                            System.err.println(numberSaved+" IFT Records saved. ");
                            
                        }
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return numberSaved;
    }
    /*public int applyBusinessRule(String producerInstance)
    {
        int numberSaved=0;
        try
        {
            //List iftList=new ArrayList();
            List dataElementList=util.getSourceDataDaoInstance().getDistinctDataElements(producerInstance);
            if(dataElementList !=null && !dataElementList.isEmpty())
            {
                SourceData sd=null;
                DxOrganizationUnit producerOrgUnit=null;
                DxOrganizationUnit consumerOrgUnit=null;
                DxOrganizationUnitMatch orgUnitMatch=null;
                DxDataElement producerDe=null;
                DxCategoryCombination producerCatCombo=null;
                DxDataElement consumerDe=null;
                DxCategoryCombination consumerCatCombo=null;
                BusinessRule br=null;
                String consumerOrgUnitId=null;
                String consumerInstanceId=null;
                String consumerDeId=null;
                String consumerCatComboId=null;
                for(Object s: dataElementList)
                {
                    String dataElement=(String)s;
                    List sourceList=util.getSourceDataDaoInstance().getSourceDataByDataElementAndInstance(dataElement, producerInstance);
                    if(sourceList !=null && !sourceList.isEmpty())
                    {
                        for(int i=0; i<sourceList.size(); i++)
                        {
                            sd=(SourceData)sourceList.get(i);
                            //System.err.println("sd is "+sd.getOrganizationUnit()+"-"+sd.getDataElementName()+"-"+sd.getCategoryOptionComboName());
                            producerDe=(DxDataElement)util.getDxDataElementDaoInstance().getDataElementByNameAndDhisInstance(dataElement, producerInstance);
                            producerCatCombo=(DxCategoryCombination)util.getDxCategoryCombinationDaoInstance().getCategoryCombinationByNameAndDhisInstance(sd.getCategoryOptionComboName(), producerInstance);
                            producerOrgUnit=(DxOrganizationUnit)util.getDxOrganizationUnitDaoInstance().getOrganizationUnitByNameAndInstance(sd.getOrganizationUnit(), producerInstance);
                               
                            if(producerDe !=null && producerCatCombo !=null)
                            {
                                //get the business rule equivalent
                                br=(BusinessRule)util.getBusinessRuleDaoInstance().getBusinessRuleByProducerParameters(producerInstance, producerDe.getDataElementId(), producerCatCombo.getCatComboId());
                                if(br !=null)
                                {
                                    //System.err.println("br is "+br.getProducerInstanceId()+"-"+br.getProducerDeName()+"-"+br.getProducerCatComboName()+"-"+sd.getCategoryOptionComboName());
                                    System.err.println("br.getConsumerInstanceId(): "+br.getConsumerInstanceId()+" br.getConsumerDeId(): "+br.getConsumerDeId()+" br.getConsumerCatComboId(): "+br.getConsumerCatComboId());
                                    consumerInstanceId=br.getConsumerInstanceId();
                                    //get consumer data elements and category
                                    if(producerOrgUnit !=null)
                                    {
                                        orgUnitMatch=(DxOrganizationUnitMatch)util.getDxOrganizationUnitMatchDaoInstance().getDxOrganizationUnitMatchByIdAndMatchDescription(producerOrgUnit.getOrgunitId(), br.getProducerInstanceId(), br.getConsumerInstanceId());
                                        if(orgUnitMatch !=null)
                                        {
                                            consumerOrgUnitId=orgUnitMatch.getConsumerOrgUnitId();
                                            System.err.println("consumerOrgUnitId is "+consumerOrgUnitId);
                                            consumerOrgUnit=(DxOrganizationUnit)util.getDxOrganizationUnitDaoInstance().getOrganizationUnitByIdAndInstance(consumerOrgUnitId, consumerInstanceId);
                                            
                                            if(consumerOrgUnit !=null)
                                            System.err.println("consumerOrgUnit is "+consumerOrgUnit.getOrgunitName());
                                        }
                                        consumerDe=(DxDataElement)util.getDxDataElementDaoInstance().getDataElementByIdAndDhisInstance(br.getConsumerDeId(), br.getConsumerInstanceId());
                                        if(consumerOrgUnit !=null)
                                        System.err.println("consumerOrgUnit name is "+consumerOrgUnit.getOrgunitName());
                                        if(consumerDe !=null)
                                        System.err.println("consumerDe name is "+consumerDe.getDataElementName());
                                        consumerCatCombo=(DxCategoryCombination)util.getDxCategoryCombinationDaoInstance().getCategoryCombinationByIdAndDhisInstance(br.getConsumerCatComboId(), br.getConsumerInstanceId());
                                        if(consumerCatCombo !=null)
                                        System.err.println("consumerCatCombo name is "+consumerCatCombo.getCatComboName());
                                        if(consumerDe !=null && consumerCatCombo !=null && consumerOrgUnit !=null)
                                        {
                                            System.err.println("All are not null ");
                                            IntermediateFlatTable ift=getIntermediateFlatTable(sd,br,consumerOrgUnit,consumerDe,consumerCatCombo);
                                            if(ift !=null)
                                            {
                                                System.err.println("ift is "+ift.getConsumerOrganizationUnitName());
                                                util.getIntermediateFlatTableDaoInstance().saveIntermediateFlatTable(ift);
                                                numberSaved++;
                                            }
                                        }
                                    }
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
        return numberSaved;
    }*/
    public int createUniqueRecords(String producerInstanceId,String consumerInstanceId)
    {
        AppUtility appUtil=new AppUtility();
        int numberSaved=0;
        int numberUpdated=0;
        int totalNumberSaved=0;
        List recordParamList=new ArrayList();
        
        IntermediateFlatTable idft=null;
        
        FinalFlatTable fft;
        FinalFlatTable duplicateFft;
        List ddeList=null;
        List idftList=null;
        Object[] recordParameterArray=null;
        String consumerOrganizationUnitName="";
        
        String startDate="";
        int value=0;
        int totalValue=0;
        try
        {
            List deOrgUnitList=util.getIntermediateFlatTableDaoInstance().getDistinctConsumerOrgUnitNamesFromIntermediateFlatTable(producerInstanceId, consumerInstanceId);//.getDistinctConsumerOrgUnitIdsFromIntermediateFlatTable();//.getAllDistinctOrgUnitNamesFromDhisDataExport(dhisInstance);
            if(deOrgUnitList !=null)
            System.err.println("deOrgUnitList size is "+deOrgUnitList.size());
            for(Object s:deOrgUnitList)
            {
                consumerOrganizationUnitName=(String)s;
                recordParamList=util.getIntermediateFlatTableDaoInstance().aggregateRecordsWithSameOrgUnitDataElementAndCategory(consumerOrganizationUnitName, producerInstanceId, consumerInstanceId);
                System.err.println("recordParamList size is "+recordParamList.size());
                for(Object obj:recordParamList)
                {
                    recordParameterArray=(Object[])obj;
                    idftList=util.getIntermediateFlatTableDaoInstance().getIntermediateFlatTableByOrgUnitNameAndInstance(consumerOrganizationUnitName, producerInstanceId, consumerInstanceId);
                    if(idftList==null || idftList.isEmpty())
                    {
                        System.err.println("orgUnitName "+consumerOrganizationUnitName+" not found in IntermediateDhisFlatTable");
                        break;
                    }
                    //consumerDataElementName=recordParameterArray[1].toString();
                    //consumerCategoryName=recordParameterArray[2].toString();
                    startDate=recordParameterArray[3].toString();
                    value=Integer.parseInt(recordParameterArray[4].toString());
                    idft=(IntermediateFlatTable)idftList.get(0);
                    fft=new FinalFlatTable();
                    
                    fft.setConsumerCategoryId(idft.getConsumerCategoryId());
                    fft.setConsumerCategoryName(idft.getConsumerCategoryName());
                    fft.setConsumerDataElementId(idft.getConsumerDataElementId());
                    fft.setConsumerDataElementName(idft.getConsumerDataElementName());
                    fft.setConsumerOrganizationUnitId(idft.getConsumerOrganizationUnitId());
                    fft.setConsumerOrganizationUnitName(idft.getConsumerOrganizationUnitName());
                    fft.setConsumerInstance(idft.getConsumerInstance());
                    fft.setProducerInstance(producerInstanceId);
                    fft.setLastModifiedDate(appUtil.getCurrentDate());
                    fft.setStartDate(startDate);
                    fft.setValue(value);
                    totalValue+=value;
                    duplicateFft=util.getFinalFlatTableDaoInstance().getFinalFlatTableByNames(fft.getConsumerOrganizationUnitName(), fft.getConsumerDataElementName(), fft.getConsumerCategoryName(), fft.getStartDate(), consumerInstanceId);//.getFinalFlatTable(consumerOrganizationUnitId, consumerDataElementId, consumerCategoryId, startDate);
                    if(duplicateFft ==null)
                    {
                         util.getFinalFlatTableDaoInstance().saveFinalFlatTable(fft);
                         numberSaved++;
                        //System.err.println(numberSaved+" saved ");
                    }
                    /*else
                    {
                     * fft.setRecordId(duplicateFft.getRecordId());
                        //fft.setValue(value);
                        util.getFinalFlatTableDaoInstance().updateFinalFlatTable(fft);
                        numberUpdated++;
                        //System.err.println(numberUpdated+" updated ");
                       
                    }*/
                    //util.getFinalFlatTableDaoInstance().saveFinalFlatTable(fft);
                    //numberSaved++;
                    System.err.println(numberSaved+" saved, "+numberUpdated+" updated, total value is "+totalValue);
                }
            }
            totalNumberSaved=numberSaved+numberUpdated;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return totalNumberSaved;
    }
    public IntermediateFlatTable getIntermediateFlatTable(SourceData sd,BusinessRule br,DxOrganizationUnit consumerOrgUnit,DxDataElement consumerDe,DxCategoryCombination consumerCatCombo)
    {
        AppUtility appUtil=new AppUtility();
        IntermediateFlatTable ift=null;
        if(consumerOrgUnit !=null && consumerDe !=null && consumerCatCombo !=null)
        {
            ift=new IntermediateFlatTable();
            ift.setConsumerOrganizationUnitName(consumerOrgUnit.getOrgunitName());
            ift.setConsumerCategoryName(consumerCatCombo.getCatComboName());
            ift.setConsumerDataElementName(consumerDe.getDataElementName());
            ift.setConsumerOrganizationUnitId(consumerOrgUnit.getOrgunitId());
            ift.setConsumerCategoryId(consumerCatCombo.getCatComboId());
            ift.setConsumerDataElementId(consumerDe.getDataElementId());
            ift.setConsumerInstance(consumerDe.getDhisInstance());
            //ift.setConsumerInstance(br.getConsumerInstanceName());
            
            ift.setProducerInstance(sd.getDhisInstance());
            ift.setProducerOrganizationUnit(sd.getOrganizationUnit());
            ift.setProducerCategory(sd.getCategoryOptionComboName());
            ift.setProducerDataElement(sd.getDataElementName());
            ift.setDataset(sd.getDataset());
            ift.setOrgUnitType(sd.getOrganizationUnitType());
            ift.setOrgUnitGroup(sd.getOrganizationUnitGroup());
            ift.setOrganizationUnitOwnership(sd.getOrganizationUnitOwnership());
            ift.setFundingBody(sd.getFundingBody());
            ift.setStartDate(sd.getStartDate());
            ift.setValue(sd.getValue());
            ift.setLogic(br.getBusinessLogicName());
            ift.setLastModifiedDate(appUtil.getCurrentDate());
        }
        return ift;
    }
  
}
