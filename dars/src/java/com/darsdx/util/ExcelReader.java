/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.util;

import com.darsdx.business.BusinessRule;
import com.darsdx.business.DataValue;
import com.darsdx.business.DhisDataExport;
import com.darsdx.business.DhisInstance;
import com.darsdx.business.DhisOperation;
import com.darsdx.business.DxOrganizationUnit;
import com.darsdx.business.DxOrganizationUnitMatch;
import com.darsdx.business.FinalFlatTable;
import com.darsdx.business.ImisDatavalue;
import com.darsdx.business.OrganizationUnit;
import com.darsdx.business.SourceData;
import com.darsdx.dao.BusinessRuleDao;
import com.darsdx.dao.BusinessRuleDaoImpl;
import com.darsdx.dao.DaoUtil;
import com.darsdx.dao.DaoUtility;
import com.darsdx.dao.DataValueDao;
import com.darsdx.dao.DxOrganizationUnitDao;
import com.darsdx.dao.DxOrganizationUnitDaoImpl;
import com.darsdx.dao.DxOrganizationUnitMatchDao;
import com.darsdx.dao.DxOrganizationUnitMatchDaoImpl;
import com.darsdx.dao.FinalFlatTableDao;
import com.darsdx.dao.FinalFlatTableDaoImpl;
import com.darsdx.dao.SourceDataDao;
import com.darsdx.dao.SourceDataDaoImpl;
import com.darsdx.operationsmanagement.BusinessRuleOperationsManager;
import com.darsdx.operationsmanagement.OrganizationUnitOperationsManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author smomoh
 */
public class ExcelReader implements Serializable
{
    List fftList=new ArrayList();
    List fftListForXmlExport=new ArrayList();
    int fileCount=0;
  public List createAfyaNyotaDataStructureFromDHIS2Datavalue(String imisInstanceId) throws IOException  
  {
      List idvList=new ArrayList();
      try
      {
          DaoUtility util=new DaoUtility();
          String period=null;
          String dataElement=null;
          String orgUnitId=null;
          List distinctPeriodList=DaoUtility.getDatavalueDaoInstance().getDistinctPeriodsFromDataValues();
          List distinctDataElementList=null;
          List distinctOrgunitList=null;
          List dataValueList=null;
          DataValue dv=null;
          ImisDatavalue idv=null;
          DxOrganizationUnitMatch oum=null;
          if(distinctPeriodList !=null)
          {
              //System.err.println("distinctPeriodList is not null");
              for(int i=0; i<distinctPeriodList.size(); i++)
              {
                  if(distinctPeriodList.get(i) !=null)
                  {
                      
                    period=distinctPeriodList.get(i).toString();
                    distinctDataElementList=DaoUtility.getDatavalueDaoInstance().getDistinctDataElementsByPeriodsFromDataValues(period);
                    if(distinctDataElementList !=null)
                    {
                        //System.err.println("distinctDataElementList is not null");
                        for(int j=0; j<distinctDataElementList.size(); j++)
                        {
                            dataElement=distinctDataElementList.get(j).toString();
                            distinctOrgunitList=DaoUtility.getDatavalueDaoInstance().getDistinctOrganizationUnitsByDataElementAndPeriodFromDataValues(dataElement, period);
                            if(distinctOrgunitList !=null)
                            {
                                //System.err.println("distinctOrgunitList is not null");
                                for(int k=0; k<distinctOrgunitList.size(); k++)
                                {
                                    orgUnitId=distinctOrgunitList.get(k).toString();
                                    oum=getOrganizationUnitMappingByConsumerOrgUnitIdAndProducerInstanceId(orgUnitId,imisInstanceId);
                                    dataValueList=DaoUtility.getDatavalueDaoInstance().getDatavaluesByOrganizationUnitsDataElementAndPeriod(orgUnitId, dataElement, period);
                                    if(dataValueList !=null)
                                    {
                                        //System.err.println("dataValueList is not null");
                                        idv=null;
                                        for(int l=0; l<dataValueList.size(); l++)
                                        {
                                            dv=(DataValue)dataValueList.get(l);
                                            BusinessRule dbr=DaoUtility.getBusinessRuleDaoInstance().getBusinessRuleByConsumerDataElementIdAndConsumerCatComboIdAndConsumerInstanceId(dataElement,dv.getCategoryOptionComboId(),dv.getDhisId());
                                            if(dbr !=null)
                                            {
                                                //System.err.println("dbr is not null");
                                                idv=new ImisDatavalue();
                                                idv.setDhisDataElementId(dv.getDataElementId());
                                                idv.setDhisFacilityId(dv.getOrgUnitId());
                                                if(oum !=null)
                                                idv.setImisFacilityId(oum.getProducerOrgUnitId());
                                                
                                                idv.setYearMonth(period);
                                                
                                                if(dbr.getProducerCatComboName() !=null)
                                                {
                                                    //System.err.println("dbr.getProducerCatComboName() is not null");
                                                    idv.setImisDataElementId(dbr.getProducerDeId());
                                                    if(dbr.getProducerCatComboName().equals("f_1"))
                                                    idv.setF_1(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("f_4"))
                                                    idv.setF_4(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("f_9"))
                                                    idv.setF_9(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("f_14"))
                                                    idv.setF_14(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("f_19"))
                                                    idv.setF_19(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("f_24"))
                                                    idv.setF_24(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("f_29"))
                                                    idv.setF_29(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("f_34"))
                                                    idv.setF_34(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("f_39"))
                                                    idv.setF_39(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("f_44"))
                                                    idv.setF_44(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("f_49"))
                                                    idv.setF_49(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("f_50"))
                                                    idv.setF_50(Integer.parseInt(dv.getDvalue()));
                                                    
                                                    //Do same for male
                                                    else if(dbr.getProducerCatComboName().equals("m_1"))
                                                    idv.setM_1(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("m_4"))
                                                    idv.setM_4(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("m_9"))
                                                    idv.setM_9(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("m_14"))
                                                    idv.setM_14(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("m_19"))
                                                    idv.setM_19(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("m_24"))
                                                    idv.setM_24(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("m_29"))
                                                    idv.setM_29(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("m_34"))
                                                    idv.setM_34(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("m_39"))
                                                    idv.setM_39(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("m_44"))
                                                    idv.setM_44(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("m_49"))
                                                    idv.setM_49(Integer.parseInt(dv.getDvalue()));
                                                    else if(dbr.getProducerCatComboName().equals("m_50"))
                                                    idv.setM_50(Integer.parseInt(dv.getDvalue()));
                                                    System.err.println("dbr.getProducerCatComboName() is "+dbr.getProducerCatComboName());
                                                }
                                            }
                                        }
                                        if(idv !=null)
                                        {
                                            idv.setRecordId(idv.getYearMonth()+"_"+idv.getImisFacilityId()+"_"+idv.getImisDataElementId());
                                            idvList.add(idv);
                                            DaoUtility.getImisDatavalueDaoInstance().saveOrUpdateImisDatavalue(idv);
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
      return idvList;
  }
  public List readAfyaNyotaDataToFinalFlatTable(InputStream inputStream,String dhisInstance) throws IOException  
  {
      int numberSaved=0;
    Workbook w;
    try
    {
        AppUtility appUtil=new AppUtility();
      w = Workbook.getWorkbook(inputStream);
      int count=w.getNumberOfSheets();
      String date=null;
      DxOrganizationUnitMatch oum=null;
      String facility=null;
      String producerDataElementId=null;
      String producerCatComboElementId=null;
      String calculated=null;
      String sheetName=null;
      BusinessRule br=null;
      FinalFlatTableDao fftdao=new FinalFlatTableDaoImpl();
      FinalFlatTable fft=null;
      int processedRowCount=0;
      fftList.clear();
      for(int a=0; a<count; a++)
      {
      Sheet sheet = w.getSheet(a);
      
      String dataset=sheet.getName();
      sheetName=sheet.getName();
      int rowCount=sheet.getRows();
      // Loop over first 10 column and lines
      System.err.println("Sheet name is "+sheetName);
      for (int j = 1; j < rowCount; j++)
      {
          fft=new FinalFlatTable();
          fft.setConsumerInstance(dhisInstance);
          fft.setLastModifiedDate(DateManager.getCurrentDate());
          fft.setProducerInstance("xxxxxxxxxxx");
          try
          {
            for (int i = 0; i < sheet.getColumns(); i++)
            {
              Cell cell = sheet.getCell(i, j);
              String cellContent=cell.getContents();
              if(i==0)
              {

              }
              else if(i==1)
              {
                  facility=null;
                  if(!isEmpty(cell.getContents()))
                  facility=cell.getContents().trim();
                  oum=getOrganizationUnitMapping(facility,dhisInstance);
                  if(oum !=null)
                  {
                    fft.setConsumerOrganizationUnitId(oum.getConsumerOrgUnitId());
                    fft.setConsumerOrganizationUnitName(oum.getConsumerOrgUnitName());
                  }
                  else
                  {
                      fft.setOrgUnitFlag(1);
                  }
                  fft.setProducerOrganizationUnitId(cell.getContents());
              }
              else if(i==2)
              {
                  producerDataElementId=null;
                  if(!isEmpty(cell.getContents()))
                  producerDataElementId=cell.getContents().trim();
                  else
                  fft.setDataElementFlag(1);
                  fft.setProducerDataElementId(cell.getContents());
                  fftList.add(fft);
              }
              else if(i==3)
              {
                  String startDate=getStartDate(cell.getContents());
                  fft.setPeriod(cell.getContents());
                  fft.setStartDate(startDate);
              }
              else if(i==4)
              {
                  if(!isEmpty(cell.getContents()))
                  {

                  }
              }
              else if(i==5)
              {

              }
              else if(i==6)
              {
                producerCatComboElementId="m_1";
                br=getBusinessRule(producerDataElementId,"m_1", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setProducerInstance(br.getProducerInstanceId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==7)
              {
                  producerCatComboElementId="f_1";
                br=this.getBusinessRule(producerDataElementId,"f_1", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==8)
              {
                  producerCatComboElementId="m_4";
                br=this.getBusinessRule(producerDataElementId,"m_4", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==9)
              {
                  producerCatComboElementId="f_4";
                br=this.getBusinessRule(producerDataElementId,"f_4", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==10)
              {
                  producerCatComboElementId="m_9";
                br=this.getBusinessRule(producerDataElementId,"m_9", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==11)
              {
                  producerCatComboElementId="f_9";
                br=this.getBusinessRule(producerDataElementId,"f_9", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==12)
              {
                  producerCatComboElementId="m_14";
                br=this.getBusinessRule(producerDataElementId,"m_14", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==13)
              {
                  producerCatComboElementId="f_14";
                br=this.getBusinessRule(producerDataElementId,"f_14", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==14)
              {
                  producerCatComboElementId="m_19";
                br=this.getBusinessRule(producerDataElementId,"m_19", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==15)
              {
                  producerCatComboElementId="f_19";
                br=this.getBusinessRule(producerDataElementId,"f_19", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==16)
              {
                  producerCatComboElementId="m_24";
                br=this.getBusinessRule(producerDataElementId,"m_24", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==17)
              {
                  producerCatComboElementId="f_24";
                br=this.getBusinessRule(producerDataElementId,"f_24", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==18)
              {
                  producerCatComboElementId="m_29";
                br=this.getBusinessRule(producerDataElementId,"m_29", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==19)
              {
                  producerCatComboElementId="f_29";
                br=this.getBusinessRule(producerDataElementId,"f_29", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==20)
              {
                  producerCatComboElementId="m_34";
                br=this.getBusinessRule(producerDataElementId,"m_34", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==21)
              {
                  producerCatComboElementId="f_34";
                br=this.getBusinessRule(producerDataElementId,"f_34", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==22)
              {
                producerCatComboElementId="m_39";
                br=this.getBusinessRule(producerDataElementId,"m_39", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==23)
              {
                producerCatComboElementId="f_39";
                br=this.getBusinessRule(producerDataElementId,"f_39", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==24)
              {
                  producerCatComboElementId="m_44";
                br=this.getBusinessRule(producerDataElementId,"m_44", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==25)
              {
                  producerCatComboElementId="f_44";
                br=this.getBusinessRule(producerDataElementId,"f_44", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==26)
              {
                  producerCatComboElementId="m_49";
                br=this.getBusinessRule(producerDataElementId,"m_49", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==27)
              {
                producerCatComboElementId="f_49";
                br=this.getBusinessRule(producerDataElementId,"f_49", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==28)
              {
                  producerCatComboElementId="m_50";
                br=this.getBusinessRule(producerDataElementId,"m_50", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
              else if(i==29)
              {
                  producerCatComboElementId="f_50";
                br=this.getBusinessRule(producerDataElementId,"f_50", dhisInstance);
                if(br !=null)
                {
                    fft.setConsumerCategoryName(br.getConsumerCatComboName());
                    fft.setConsumerDataElementName(br.getConsumerDeName());
                    fft.setConsumerDataElementId(br.getConsumerDeId());
                    fft.setConsumerCategoryId(br.getConsumerCatComboId());
                    fft.setAttributeOptionCombo(br.getAttributeOptionComboId());
                    fft.setValue(0);
                    if(!isEmpty(cell.getContents()))
                    {
                        fft.setValue(Integer.parseInt(cell.getContents()));
                        numberSaved=saveFinalFlatTable(fftdao,fft,numberSaved);
                    }
                }
                fft.setProducerCategoryId(producerCatComboElementId);
                fftList.add(fft);
              }
            }
          }
          catch(NumberFormatException ex)
          {
              continue;
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
        if(fftListForXmlExport.size()>=40000 || j==rowCount-1)
        {
            createDhisExportInXml(fftListForXmlExport,"smomoh","dhisXmlExport");
            fftListForXmlExport.clear();
        }
        processedRowCount++;
        System.err.println(processedRowCount+" of "+rowCount+" rows processed");
      }
      
//System.err.println("Sheet name is "+sheetName);
      }
    }
    catch (BiffException be)
    {
      be.printStackTrace();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return fftList;
    //return numberSaved;
  }
  private int saveFinalFlatTable(FinalFlatTableDao fftdao,FinalFlatTable fft,int numberSaved)
  {
      try
      {
          if(fft.getConsumerOrganizationUnitId() !=null && fft.getConsumerDataElementId() !=null && fft.getConsumerCategoryId() !=null && fft.getPeriod() !=null && fft.getStartDate() !=null)
          {
              /*if(fftdao.getFinalFlatTable(fft.getConsumerOrganizationUnitId(), fft.getConsumerDataElementId(), fft.getConsumerCategoryId(),fft.getAttributeOptionCombo(), fft.getStartDate(), fft.getConsumerInstance()) ==null)
              fftdao.saveFinalFlatTable(fft);
              else
              fftdao.updateFinalFlatTable(fft);*/
              fftListForXmlExport.add(fft);
              numberSaved++;
              //System.err.println("number of columns processed and saved is "+numberSaved);
          }
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
      return numberSaved;      
  }
  public void createDhisExportInXml(List fftList,String userName,String fileName)
  {
      DhisDataExport dde=null;
      FinalFlatTable fft=null;
      fileCount++;
      fileName=fileName+fileCount+"_";
      int exportSize=fftList.size();
      if(fftList !=null && !fftList.isEmpty())
      {
         DhisOperation dop=new DhisOperation();
         List list=new ArrayList();
         String exportFileName=null;
         List mainList=new ArrayList();
         List subList=new ArrayList();
         int startSize=0;
         int endSize=0;
         int loopCounter=0;
         for(int i=0; i<fftList.size(); i++)
         {
             fft=(FinalFlatTable)fftList.get(i);
             dde=new DhisDataExport();
             dde.setAttributeOptionCombinationId(fft.getAttributeOptionCombo());
             dde.setCategoryCombinationId(fft.getConsumerCategoryId());
             dde.setDataElementId(fft.getConsumerDataElementId());
             dde.setMonth(fft.getPeriod().substring(4,6));
             dde.setYear(fft.getPeriod().substring(0, 4));
             dde.setOrganizationUnitId(fft.getConsumerOrganizationUnitId());
             dde.setValue(fft.getValue());
             subList.add(dde);
             if(subList.size()==exportSize || (i==fftList.size()-1))
             {
                loopCounter++;
                mainList.add(subList);
                subList=new ArrayList();
             }
             //System.err.println("dde.getMonth() is "+dde.getMonth()+" and dde.getYear() is "+dde.getYear());
         }
         
         
         for(int k=0; k<mainList.size(); k++)
         {
           startSize=(k*exportSize)+1;
           list=(List)mainList.get(k);
           endSize=(list.size()+(k*exportSize));
           exportFileName=fileName+startSize+"-"+endSize;
           dop.createDhisExportFileInXml(list, userName, exportFileName);
         }
      }
  }
  /*public int readAfyaNyotaDataToDhisDataValue(InputStream inputStream,String dhisInstance) throws IOException  
  {
      int numberSaved=0;
    Workbook w;
    try
    {
        AppUtility appUtil=new AppUtility();
      w = Workbook.getWorkbook(inputStream);
      int count=w.getNumberOfSheets();
      String date=null;
      DxOrganizationUnitMatch oum=null;
      String facility=null;
      String dataElement=null;
      String calculated=null;
      String sheetName=null;
      BusinessRule br=null;
      DataValueDao ddeDao=new DataValueDaoImpl();
      DataValue dv=null;
      int rowCount=0;
      
      for(int a=0; a<count; a++)
      {
      Sheet sheet = w.getSheet(a);
      
      String dataset=sheet.getName();
      sheetName=sheet.getName();
      // Loop over first 10 column and lines
      System.err.println("Sheet name is "+sheetName);
      for (int j = 1; j < sheet.getRows(); j++)
      {
          dv=new DataValue();
          dv.setDhisId(dhisInstance);
          dv.setDatasetId("xxxxxxxxxxx");
          dv.setLastModifiedDate(DateManager.getCurrentDateAsDateObject());
        for (int i = 0; i < sheet.getColumns(); i++)
        {
          Cell cell = sheet.getCell(i, j);
          String cellContent=cell.getContents();
          if(i==0)
          {
              
          }
          else if(i==1)
          {
              facility=null;
              if(!isEmpty(cell.getContents()))
              facility=cell.getContents().trim();
              oum=getOrganizationUnitMapping(facility,dhisInstance);
              if(oum !=null)
              dv.setOrgUnitId(oum.getConsumerOrgUnitId());
          }
          else if(i==2)
          {
              dataElement=null;
              if(!isEmpty(cell.getContents()))
              dataElement=cell.getContents().trim();
              dv.setDataElementId(dataElement);
          }
          else if(i==3)
          {
              String startDate=getStartDate(cell.getContents());
              dv.setPeriod(cell.getContents());
              dv.setLastUpdated(DateManager.getCurrentDateAndTime(DateManager.DD_MM_YYYY_SLASH));
              dv.setStartDate(DateManager.getDateInstance(startDate));
          }
          else if(i==4)
          {
              if(!isEmpty(cell.getContents()))
              {
                
              }
          }
          else if(i==5)
          {
              
          }
          else if(i==6)
          {
            dv.setCategoryOptionComboName("m_1");
            br=getBusinessRule("m_1", dhisInstance);
            if(br !=null)
            {
                dv.setDataElementId(br.getConsumerDeId());
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==7)
          {
            dv.setCategoryOptionComboName("f_1");
            br=this.getBusinessRule("f_1", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==8)
          {
            dv.setCategoryOptionComboName("m_4");
            br=this.getBusinessRule("m_4", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==9)
          {
            dv.setCategoryOptionComboName("f_4");
            br=this.getBusinessRule("f_4", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==10)
          {
            dv.setCategoryOptionComboName("m_9");
            br=this.getBusinessRule("m_9", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==11)
          {
            dv.setCategoryOptionComboName("f_9");
            br=this.getBusinessRule("f_1", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==12)
          {
            dv.setCategoryOptionComboName("m_14");
            br=this.getBusinessRule("m_14", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==13)
          {
            dv.setCategoryOptionComboName("f_14");
            br=this.getBusinessRule("f_14", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==14)
          {
            dv.setCategoryOptionComboName("m_19");
            br=this.getBusinessRule("m_19", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==15)
          {
            dv.setCategoryOptionComboName("f_19");
            br=this.getBusinessRule("f_19", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==16)
          {
            dv.setCategoryOptionComboName("m_24");
            br=this.getBusinessRule("m_24", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==17)
          {
            dv.setCategoryOptionComboName("f_24");
            br=this.getBusinessRule("f_24", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==18)
          {
            dv.setCategoryOptionComboName("m_29");
            br=this.getBusinessRule("m_29", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==19)
          {
            dv.setCategoryOptionComboName("f_29");
            br=this.getBusinessRule("f_29", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==20)
          {
            dv.setCategoryOptionComboName("m_34");
            br=this.getBusinessRule("m_34", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==21)
          {
            dv.setCategoryOptionComboName("f_34");
            br=this.getBusinessRule("f_34", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==22)
          {
            dv.setCategoryOptionComboName("m_39");
            br=this.getBusinessRule("m_39", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==23)
          {
            dv.setCategoryOptionComboName("f_39");
            br=this.getBusinessRule("f_39", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==24)
          {
            dv.setCategoryOptionComboName("m_44");
            br=this.getBusinessRule("m_44", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==25)
          {
            dv.setCategoryOptionComboName("f_44");
            br=this.getBusinessRule("f_44", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==26)
          {
            dv.setCategoryOptionComboName("m_49");
            br=this.getBusinessRule("m_49", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==27)
          {
            dv.setCategoryOptionComboName("f_49");
            br=this.getBusinessRule("f_49", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==28)
          {
            dv.setCategoryOptionComboName("m_50");
            br=this.getBusinessRule("m_50", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                //dv.setCategoryOptionComboName(br.getConsumerCatComboName());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
          else if(i==29)
          {
            dv.setCategoryOptionComboName("f_50");
            br=this.getBusinessRule("f_50", dhisInstance);
            if(br !=null)
            {
                dv.setCategoryOptionComboId(br.getConsumerCatComboId());
                dv.setAttributeOptionCombo(br.getAttributeOptionComboId());
                //dv.setCategoryOptionComboName(br.getConsumerCatComboName());
                //dv.setDatasetId(br.getDatasetName());
                dv.setDvalue(cell.getContents());
                numberSaved=saveDhisDataExport(ddeDao,dv,numberSaved);
            }
          }
        }
        rowCount++;
        System.err.println("number of rows saved is "+rowCount);
      }
//System.err.println("Sheet name is "+sheetName);
      }
    }
    catch (BiffException be)
    {
      be.printStackTrace();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return numberSaved;
  }*/
  private String getStartDate(String yyyymm)
{
    String period="";
    if(yyyymm !=null && yyyymm.length()==6)
    {
        String year=yyyymm.substring(0,4);
        String mth=yyyymm.substring(4,6);
        period=year+"-"+mth+"-01";
    }
   return period; 
}
  private int saveDhisDataExport(DataValueDao dvdao,DataValue dv,int numberSaved)
  {
      try
      {
          if(dv.getDvalue() !=null && dv.getDvalue().trim().length()>0 && dv.getOrgUnitId() !=null && dv.getDataElementId() !=null && dv.getCategoryOptionComboId() !=null && dv.getPeriod() !=null && dv.getStartDate() !=null)
          {
              if(dvdao.getDataValue(dv.getDatasetId(), dv.getDataElementId(), dv.getOrgUnitId(), dv.getCategoryOptionComboId(), dv.getPeriod())==null)
              dvdao.saveDataValue(dv);
              else
              dvdao.updateDataValue(dv);
              numberSaved++;
              System.err.println("number of columns processed and saved is "+numberSaved);
          }
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
      return numberSaved;      
  }
  private BusinessRule getBusinessRule(String producerDataElementId,String producerCategoryComboName,String producerDhisInstance)
  {
      BusinessRule br=null;
      try
      {
          BusinessRuleDao brdao=new BusinessRuleDaoImpl();
          br=brdao.getBusinessRuleByProducerCatComboAndProducerInstance(producerDataElementId,producerCategoryComboName, producerDhisInstance);
          //if(br !=null)
          //System.err.println("categoryComboId is "+br.getConsumerCatComboId());
          //categoryComboId=br.getConsumerCatComboId();
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
      
      return br;
  }
  private DxOrganizationUnitMatch getOrganizationUnitMapping(String producerOrgUnitId,String producerDhisInstance)
  {
      DxOrganizationUnitMatch oum=null;
      try
      {
          DxOrganizationUnitMatchDao oumdao=new DxOrganizationUnitMatchDaoImpl();
          oum=oumdao.getDxOrganizationUnitMatchByProducerOrgUnitIdAndProducerInstance(producerOrgUnitId, producerDhisInstance);
          //if(oum !=null)
          //System.err.println("oum.getConsumerOrgUnitId() is "+oum.getConsumerOrgUnitId());
          //categoryComboId=br.getConsumerCatComboId();
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
      
      return oum;
  }
  private DxOrganizationUnitMatch getOrganizationUnitMappingByConsumerOrgUnitIdAndProducerInstanceId(String consumerOrgUnitId,String producerInstanceId)
  {
      DxOrganizationUnitMatch oum=null;
      try
      {
          DxOrganizationUnitMatchDao oumdao=new DxOrganizationUnitMatchDaoImpl();
          oum=oumdao.getDxOrganizationUnitMatchByConsumerOrgUnitIdAndProducerInstance(consumerOrgUnitId, producerInstanceId);
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
      
      return oum;
  }
  public List readOrganizationUnitFromExcel(InputStream inputStream,String dhisIstance)
  {
      List list=new ArrayList();
      int numberSaved=0;
    Workbook w;
    try 
    {
        DaoUtil util=new DaoUtil();
       
      //AppUtility appUtil=new AppUtility();
      w = Workbook.getWorkbook(inputStream); 
      int count=w.getNumberOfSheets();
      
      String cellContent=null;
      
      OrganizationUnit ou=null;
      
      int columns=0;
      for(int a=0; a<count; a++)
      {
        Sheet sheet = w.getSheet(a);
        
        for (int j = 1; j < sheet.getRows(); j++)
        {
            columns=sheet.getColumns();
            ou=new OrganizationUnit();
           
            for (int i = 0; i < sheet.getColumns(); i++)
            {
                Cell cell = sheet.getCell(i, j);
                if(i==0)
                {
                    cellContent=cell.getContents();
                    if(!isEmpty(cellContent))
                    {
                        cellContent=cellContent.trim();
                        ou=DaoUtility.getOrganizationUnitDaoInstance().getOrganizationUnit(dhisIstance, cellContent);
                        if(ou ==null)
                        break;                        
                        
                            System.err.println(" Ou Id at "+j+" "+i+" is "+ cellContent);
                        
                        //ou.setOrgunitId(cellContent);
                    }
                    else
                    break;
                    
                }
                if(i==1)
                {
                    cellContent=cell.getContents();
                    if(!isEmpty(cellContent))
                    {
                        if(ou !=null)
                        {
                            cellContent=cellContent.trim();
                            ou.setOuCode(cellContent);
                            DaoUtility.getOrganizationUnitDaoInstance().updateOrganizationUnit(ou);
                            System.err.println(" Oucode at "+j+" "+i+" is "+ cellContent);  
                        }  
                    }
                    else
                    break;
                }
                
         }
         list.add(ou);
      }
      }
    } 
    catch (BiffException be) 
    {
      be.printStackTrace();
    }
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
      return list;
  }
  public int readOrgunitMatchFromExcelTable(InputStream inputStream,String producerInstance,String consumerInstance) throws IOException  
  {   
      int numberSaved=0;
    Workbook w;
    try 
    {
        DaoUtil util=new DaoUtil();
       DhisInstance pins=util.getDhisInstance(producerInstance);
       DhisInstance cins=util.getDhisInstance(consumerInstance);
       
       String cinstName="";
       String pinstName="";
       
       if(cins !=null)
       cinstName=cins.getDhisName();
       if(pins !=null)
       pinstName=pins.getDhisName();    
       
      AppUtility appUtil=new AppUtility();
      w = Workbook.getWorkbook(inputStream); 
      int count=w.getNumberOfSheets();
      
      String producerOrgunitName=null;
      String consumerOrgunitName=null;  
      String producerOrgunitId=null;
      String consumerOrgunitId=null;
      
      DxOrganizationUnitMatch oum=null;
      DxOrganizationUnitMatch duplicateoum=null;
      
      String uniqueId=null;
      int columns=0;
      for(int a=0; a<count; a++)
      {
        Sheet sheet = w.getSheet(a);
        
        for (int j = 1; j < sheet.getRows(); j++)
        {
            columns=sheet.getColumns();
            oum=new DxOrganizationUnitMatch();
            oum.setProducerInstanceId(producerInstance);
            oum.setConsumerInstanceId(consumerInstance);
            uniqueId=appUtil.generateUniqueId(9);
            uniqueId=uniqueId+"--";
            for (int i = 0; i < sheet.getColumns(); i++)
            {
                //dataset=null;
                producerOrgunitName=null;
                consumerOrgunitName=null; 
                producerOrgunitId=null;
                consumerOrgunitId=null;
                Cell cell = sheet.getCell(i, j);
                if(i==0)
                {
                    producerOrgunitName=cell.getContents();
                    if(producerOrgunitName ==null || isEmpty(producerOrgunitName))
                    break;
                    producerOrgunitName=producerOrgunitName.trim();
                    oum.setProducerOrgUnitName(producerOrgunitName);
                    System.err.println(cinstName+" producerOrgunitName at "+j+" "+i+" is "+ producerOrgunitName);
                }
                if(i==1)
                {
                    consumerOrgunitName=cell.getContents();
                    if(consumerOrgunitName ==null || isEmpty(consumerOrgunitName))
                    break;
                    consumerOrgunitName=consumerOrgunitName.trim();
                    oum.setConsumerOrgUnitName(consumerOrgunitName);
                    System.err.println(cinstName+" consumerOrgunitName at "+j+" "+i+" is "+ consumerOrgunitName);
                    
                }
                else if(i==2)
                {
                    producerOrgunitId=cell.getContents();
                    if(producerOrgunitId !=null && !isEmpty(producerOrgunitId))
                    {
                        producerOrgunitId=producerOrgunitId.trim();
                    }
                    else
                    producerOrgunitId=uniqueId;
                    oum.setProducerOrgUnitId(producerOrgunitId);
                    System.err.println(cinstName+" producerOrgunitId at "+j+" "+i+" is "+ producerOrgunitId);
                }
                else if(i==3)
                {
                    consumerOrgunitId=cell.getContents();
                    if(consumerOrgunitId !=null && !isEmpty(consumerOrgunitId))
                    {
                        consumerOrgunitId=consumerOrgunitId.trim();
                    }
                    else
                    consumerOrgunitId=uniqueId;
                    oum.setConsumerOrgUnitId(consumerOrgunitId);
                    System.err.println(cinstName+" consumerOrgunitId at "+j+" "+i+" is "+ consumerOrgunitId);
                    
                }
                
          
          try
          {
            if(i==columns-1)
            {
                System.err.println("I is "+i);
                duplicateoum=util.getDxOrganizationUnitMatchDaoInstance().getDxOrganizationUnitMatchByOuNamesAndInstances(oum.getConsumerOrgUnitName(), oum.getProducerOrgUnitName(), oum.getConsumerInstanceId(), oum.getProducerInstanceId());
                if(duplicateoum ==null)
                {
                    if(oum.getConsumerOrgUnitId()==null)
                    oum.setConsumerOrgUnitId(uniqueId);
                    if(oum.getProducerOrgUnitId() ==null)
                    oum.setProducerOrgUnitId(uniqueId);
                    oum.setLastModifiedDate(appUtil.getCurrentDate());
                    OrganizationUnitOperationsManager ouom=new OrganizationUnitOperationsManager();
                    ouom.validateAndSaveOrganizationUnitMatch(oum);
                    numberSaved++;
                }
                
                }
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
         }
      }
      }
    } 
    catch (BiffException be) 
    {
      be.printStackTrace();
    }
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return numberSaved;
  }
  public int readBusinessRuleFromExcelTable(InputStream inputStream,String producerInstance,String consumerInstance) throws IOException  
  {   
      int numberSaved=0;
    Workbook w;
    try 
    {
        DaoUtil util=new DaoUtil();
       DhisInstance pins=util.getDhisInstance(producerInstance);
       DhisInstance cins=util.getDhisInstance(consumerInstance);
       
       String cinstName="";
       String pinstName="";
       
       if(cins !=null)
       cinstName=cins.getDhisName();
       if(pins !=null)
       pinstName=pins.getDhisName();    
       
      AppUtility appUtil=new AppUtility();
      w = Workbook.getWorkbook(inputStream); 
      int count=w.getNumberOfSheets();
      String dataset=null;
      String producerDeName=null;
      String consumerDeName=null;
      String producerCatComboName=null;
      String consumerCatComboName=null;
      
      String producerDeId=null;
      String consumerDeId=null;
      String producerCatComboId=null;
      String consumerCatComboId=null;
      
      String attributeOptionComboId=null;
      String attributeOptionComboName=null;
      
      String logic=null;
      BusinessRule br=null;
      BusinessRule duplicatebr=null;
      BusinessRuleDao brDao=new BusinessRuleDaoImpl();
      String uniqueId=null;
      int columns=0;
      for(int a=0; a<count; a++)
      {
        Sheet sheet = w.getSheet(a);
        
        for (int j = 1; j < sheet.getRows(); j++)
        {
            columns=sheet.getColumns();
            br=new BusinessRule();
            br.setProducerInstanceId(producerInstance);
            br.setConsumerInstanceId(consumerInstance);
            br.setProducerInstanceName(pinstName);
            br.setConsumerInstanceName(cinstName);
            uniqueId=appUtil.generateUniqueId(9);
            uniqueId=uniqueId+"xx";
            for (int i = 0; i < sheet.getColumns(); i++)
            {
                dataset=null;
                producerDeName=null;
                consumerDeName=null;
                logic=null;
                Cell cell = sheet.getCell(i, j);
                if(i==0)
                {
                    dataset=cell.getContents();
                    if(dataset !=null)
                    dataset=dataset.trim();
                    br.setDataset(dataset);
                    System.err.println("dataset at "+j+" "+i+" is "+ dataset);
                }
                if(i==1)
                {
                    consumerDeName=cell.getContents();
                    if(consumerDeName ==null || isEmpty(consumerDeName))
                    break;
                    consumerDeName=consumerDeName.trim();
                    br.setConsumerDeName(consumerDeName);
                    System.err.println(cinstName+" data element at "+j+" "+i+" is "+ consumerDeName);
                }
                else if(i==2)
                {
                    consumerCatComboName=cell.getContents();
                    if(consumerCatComboName ==null || isEmpty(consumerCatComboName))
                    break;
                    consumerCatComboName=consumerCatComboName.trim();
                    System.err.println(cinstName+" catcombo at "+j+" "+i+" is "+ consumerCatComboName);
                    br.setConsumerCatComboName(consumerCatComboName);
                }
                else if(i==3)
                {
                    producerDeName=cell.getContents();
                    if(producerDeName ==null || isEmpty(producerDeName))
                    break;
                    producerDeName=producerDeName.trim();
                    System.err.println(pinstName+" data element at "+j+" "+i+" is "+ producerDeName);
                    br.setProducerDeName(producerDeName);
                }
                else if(i==4)
                {
                    producerCatComboName=cell.getContents();
                    if(producerCatComboName ==null || isEmpty(producerCatComboName))
                    break;
                    producerCatComboName=producerCatComboName.trim();
                    System.err.println(pinstName+" catcombo at "+j+" "+i+" is "+ producerCatComboName);
                    br.setProducerCatComboName(producerCatComboName);
                }
                else if(i==5)
                {
                    logic=cell.getContents();
                    if(logic !=null)
                    {
                        logic=logic.trim();
                        if(logic.length() >99)
                        break;
                    }
                    br.setBusinessLogicName(logic);
                    System.err.println("Logic at "+j+" "+i+" is "+ logic);
                }
                else if(i==6)
                {
                    consumerDeId=cell.getContents();
                    if(consumerDeId !=null && !isEmpty(consumerDeId))
                    {
                        consumerDeId=consumerDeId.trim();
                    }
                    else
                    consumerDeId=uniqueId;
                    br.setConsumerDeId(consumerDeId);
                    System.err.println("consumerDeId at "+j+" "+i+" is "+ consumerDeId);
                }
                else if(i==7)
                {
                    consumerCatComboId=cell.getContents();
                    if(consumerCatComboId !=null && !isEmpty(consumerCatComboId))
                    {
                        consumerCatComboId=consumerCatComboId.trim();
                    }
                    else
                    consumerCatComboId=uniqueId;
                    br.setConsumerCatComboId(consumerCatComboId);
                    System.err.println("consumerCatComboId at "+j+" "+i+" is "+ consumerCatComboId);
                }
                else if(i==8)
                {
                    producerDeId=cell.getContents();
                    if(producerDeId !=null && !isEmpty(producerDeId))
                    {
                        producerDeId=producerDeId.trim();
                    }
                    else
                    producerDeId=uniqueId;
                    br.setProducerDeId(producerDeId);
                    System.err.println("producerDeId at "+j+" "+i+" is "+ producerDeId);
                }
                else if(i==9)
                {
                    producerCatComboId=cell.getContents();
                    if(producerCatComboId !=null && !isEmpty(producerCatComboId))
                    {
                        producerCatComboId=producerCatComboId.trim();
                    }
                    else
                    producerCatComboId=uniqueId;
                    br.setProducerCatComboId(producerCatComboId);
                    System.err.println("producerCatComboId at "+j+" "+i+" is "+ producerCatComboId);
                }
                else if(i==10)
                {
                    attributeOptionComboName=cell.getContents();
                    if(attributeOptionComboName !=null && !isEmpty(attributeOptionComboName))
                    {
                        attributeOptionComboName=attributeOptionComboName.trim();
                    }
                    br.setAttributeOptionComboName(attributeOptionComboName);
                    System.err.println("attributeOptionComboName at "+j+" "+i+" is "+ attributeOptionComboName);
                }
                else if(i==11)
                {
                    attributeOptionComboId=cell.getContents();
                    if(attributeOptionComboId !=null && !isEmpty(attributeOptionComboId))
                    {
                        attributeOptionComboId=attributeOptionComboId.trim();
                    }
                    br.setAttributeOptionComboId(attributeOptionComboId);
                    System.err.println("attributeOptionComboId at "+j+" "+i+" is "+ attributeOptionComboId);
                }
                
          
          try
          {
            if(i==columns-1)
            {
                System.err.println("I is "+i);
                duplicatebr=brDao.getBusinessRuleByConsumerAndProducerParameters(br.getProducerDeName(),br.getProducerCatComboName(),br.getConsumerDeName(),br.getConsumerCatComboName());
                if(duplicatebr ==null)
                {
                    if(br.getProducerCatComboId()==null)
                    br.setProducerCatComboId(uniqueId);
                    if(br.getProducerDeId()==null)
                    br.setProducerDeId(uniqueId);
                    if(br.getConsumerCatComboId()==null)
                    br.setConsumerCatComboId(uniqueId);
                    if(br.getConsumerDeId()==null)
                    br.setConsumerDeId(uniqueId);
                    if(br.getRecordGrpId()==null)
                    br.setRecordGrpId(uniqueId);
                    br.setLastModifiedDate(appUtil.getCurrentDate());
                    BusinessRuleOperationsManager brom=new BusinessRuleOperationsManager();
                    brom.validateAndSaveBusinessRule(br);
                    //brDao.saveBusinessRule(br);
                    numberSaved++;
                } 
             }
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
         }
      }
      }
    } 
    catch (BiffException be) 
    {
      be.printStackTrace();
    }
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return numberSaved;
  }
  public List readSourceDataFromExcel(InputStream inputStream,String dhisInstance) throws IOException
  {
      List errorList=new ArrayList();
    Workbook w;
    try
    {
        AppUtility appUtil=new AppUtility();
      w = Workbook.getWorkbook(inputStream);
      int count=w.getNumberOfSheets();
      String date=null;
      String cellContent=null;
      String organizationUnit=null;
      String orgUnitType=null;
      String orgUnitGroup=null;
      String fundingBody=null;
      String orgUnitOwnership=null;
      String dataElement=null;
      String categoryCombination=null;
      String dataValue=null;
      
      for(int a=0; a<count; a++)
      {
      Sheet sheet = w.getSheet(a);
      SourceData sd=null;
      SourceData duplicateSd=null;
      SourceDataDao sddao=new SourceDataDaoImpl();
      
      String dataset=null;//sheet.getName();
      // Loop over first 10 column and lines
      System.err.println("Sheet name is "+sheet.getName());
      for (int j = 1; j < sheet.getRows(); j++)
      {
          sd=new SourceData();
        for (int i = 0; i < sheet.getColumns(); i++)
        {
          Cell cell = sheet.getCell(i, j);
          if(i==3)
          {
              cellContent=cell.getContents();
              System.err.println("Start date at "+cellContent);
              if(cellContent !=null && !isEmpty(cell.getContents()))
              {
                  String[] dateArray=null;
                if(cellContent.indexOf("-") !=-1 )
                {
                    dateArray=cell.getContents().split("-");
                    int mth=appUtil.getMonthAsNumber(dateArray[0]);
                    System.err.println("Start date at "+j+" "+i+" is "
                    +"20"+dateArray[1]+"-"+mth+"-01");
                    date="20"+dateArray[1]+"-"+mth+"-01";
                }
                else if(cellContent.indexOf("/") !=-1)
                {
                    dateArray=cell.getContents().split("/");
                    int mth=appUtil.getMonthAsNumber(dateArray[0]);
                    System.err.println("Start date at "+j+" "+i+" is "
                    +"20"+dateArray[1]+"-"+mth+"-01");
                    date="20"+dateArray[1]+"-"+mth+"-01";
                }
              }
          }
          else
              System.err.println("Cell data at "+j+" "+i+" is "
                + cell.getContents());
          if(i==0)
          {
                if(!isEmpty(cell.getContents()))
                organizationUnit=cell.getContents().trim();
                sd.setOrganizationUnit(organizationUnit);
          }
          else if(i==1)
          {
              if(!isEmpty(cell.getContents()))
              dataElement=cell.getContents().trim();
              sd.setDataElementName(dataElement);
          }
          else if(i==2)
          {
              if(!isEmpty(cell.getContents()))
              categoryCombination=cell.getContents().trim();
            sd.setCategoryOptionComboName(categoryCombination);
          }
          else if(i==3)
          {
            //errorList.add("invalid date at line "+j);
            sd.setStartDate(date);
          }
          else if(i==4)
          {
              if(isEmpty(cell.getContents()) || appUtil.isString(cell.getContents()))
              {
                errorList.add("The value at line "+j+" is not a number");
                continue;
              }
              dataValue=cell.getContents().trim();
              sd.setValue(Integer.parseInt(dataValue));
          }
          else if(i==5)
          {
              dataset=null;
              if(!isEmpty(cell.getContents()))
                dataset=cell.getContents().trim();
              sd.setDataset(dataset);
          }
          else if(i==6)
          {
              orgUnitType=null;
              if(!isEmpty(cell.getContents()))
                orgUnitType=cell.getContents().trim();
              sd.setOrganizationUnitType(orgUnitType);
          }
          else if(i==7)
          {
            orgUnitGroup=null;
            if(!isEmpty(cell.getContents()))
            orgUnitGroup=cell.getContents().trim();
            sd.setOrganizationUnitGroup(orgUnitGroup);
          }
          else if(i==8)
          {
            orgUnitOwnership=null;
            if(!isEmpty(cell.getContents()))
            orgUnitOwnership=cell.getContents().trim();
            sd.setOrganizationUnitOwnership(orgUnitOwnership);
          }
          else if(i==9)
          {
            fundingBody=null;
            if(!isEmpty(cell.getContents()))
            fundingBody=cell.getContents().trim();
            sd.setFundingBody(fundingBody); 
          }
          sd.setDhisInstance(dhisInstance);
          sd.setLastModifiedDate(appUtil.getCurrentDate());
        }
          try
          {
            duplicateSd=sddao.getSourceData(sd.getOrganizationUnit(), sd.getDataElementName(), sd.getCategoryOptionComboName(),sd.getStartDate());
            if(duplicateSd !=null)
            {
                sd.setRecordId(duplicateSd.getRecordId());
                sddao.updateSourceData(sd);
            }
            else
            sddao.saveSourceData(sd);
            System.err.println("Source data with org unit "+sd.getOrganizationUnit()+" saved");
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
      }
      }
    }
    catch (BiffException be)
    {
      be.printStackTrace();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return errorList;
  }
  public List readFinalFlatTableFromExcel(InputStream inputStream,String dhisInstance) throws IOException
  {
      List errorList=new ArrayList();
        Workbook w;
    try
    {
        AppUtility appUtil=new AppUtility();
      w = Workbook.getWorkbook(inputStream);
      int count=w.getNumberOfSheets();
      String date=null;
      String cellContent=null;
      String organizationUnit=null;
      String dataElement=null;
      String categoryCombination=null;
      String dataValue=null;
      String orgUnitId=null;
      String dataElementId=null;
      String categoryComboId=null;
            
      for(int a=0; a<count; a++)
      {
      Sheet sheet = w.getSheet(a);
      FinalFlatTable fft=null;
      FinalFlatTable duplicateFft=null;
      FinalFlatTableDao fftdao=new FinalFlatTableDaoImpl();
      
      String dataset=null;//sheet.getName();
      // Loop over first 10 column and lines
      System.err.println("Sheet name is "+sheet.getName());
      for (int j = 1; j < sheet.getRows(); j++)
      {
          fft=new FinalFlatTable();
        for (int i = 0; i < sheet.getColumns(); i++)
        {
          Cell cell = sheet.getCell(i, j);
          if(i==3)
          {
              cellContent=cell.getContents();
              System.err.println("Start date at "+cellContent);
              if(cellContent !=null && !isEmpty(cell.getContents()))
              {
                  String[] dateArray=null;
                if(cellContent.indexOf("-") !=-1 )
                {
                    dateArray=cell.getContents().split("-");
                    if(dateArray[0].length()==4)
                    date=cellContent.trim();
                    else
                    {
                        int mth=appUtil.getMonthAsNumber(dateArray[0]);
                        System.err.println("Start date at "+j+" "+i+" is "
                        +"20"+dateArray[1]+"-"+mth+"-01");
                        date="20"+dateArray[1]+"-"+mth+"-01";
                    }
                }
                else if(cellContent.indexOf("/") !=-1)
                {
                    dateArray=cell.getContents().split("/");
                    int mth=appUtil.getMonthAsNumber(dateArray[0]);
                    System.err.println("Start date at "+j+" "+i+" is "
                    +"20"+dateArray[1]+"-"+mth+"-01");
                    date="20"+dateArray[1]+"-"+mth+"-01";
                }
              }
          }
          else
              System.err.println("Cell data at "+j+" "+i+" is "
                + cell.getContents());
          if(i==0)
          {
                if(!isEmpty(cell.getContents()))
                organizationUnit=cell.getContents().trim();
                fft.setConsumerOrganizationUnitName(organizationUnit);
          }
          else if(i==1)
          {
              if(!isEmpty(cell.getContents()))
              {
                dataElement=cell.getContents().trim();
                fft.setConsumerDataElementName(dataElement);
                /*if(dataElement.endsWith(")"))
                {
                    categoryCombination=dataElement.substring(dataElement.lastIndexOf("(")+1,dataElement.lastIndexOf(")"));
                    fft.setConsumerCategoryName(categoryCombination);
                    dataElement=dataElement.substring(0,dataElement.lastIndexOf("("));
                    fft.setConsumerDataElementName(dataElement);
                    i++;
                    System.err.println("categoryCombination name is "+categoryCombination);
                }*/
              }
          }
          else if(i==2)
          {
              if(!isEmpty(cell.getContents()))
              categoryCombination=cell.getContents().trim();
                fft.setConsumerCategoryName(categoryCombination);
          }
          else if(i==3)
          {
            //errorList.add("invalid date at line "+j);
            fft.setStartDate(date);
          }
          else if(i==4)
          {
              if(isEmpty(cell.getContents()) || appUtil.isString(cell.getContents()))
              {
                errorList.add("The value at line "+j+" is not a number");
                break;
              }
              dataValue=cell.getContents().trim();
              fft.setValue(Integer.parseInt(dataValue));
          }
          else if(i==5)
          {
              orgUnitId=null;
              if(!isEmpty(cell.getContents()))
                orgUnitId=cell.getContents().trim();
                fft.setConsumerOrganizationUnitId(orgUnitId);
          }
          else if(i==6)
          {
              dataElementId=null;
              if(!isEmpty(cell.getContents()))
                dataElementId=cell.getContents().trim();
                fft.setConsumerDataElementId(dataElementId);
          }
          else if(i==7)
          {
            categoryComboId=null;
            if(!isEmpty(cell.getContents()))
            categoryComboId=cell.getContents().trim();
            fft.setConsumerCategoryId(categoryComboId);
          }
          
          fft.setConsumerInstance(dhisInstance);
          fft.setLastModifiedDate(appUtil.getCurrentDate());
        }
          try
          {
            //if(orgUnitId !=null && dataElementId !=null && categoryComboId !=null)
            //duplicateFft=fftdao.getFinalFlatTable(orgUnitId, dataElementId, categoryComboId, date);
            //else
            //duplicateFft=fftdao.getFinalFlatTableByNames(fft.getConsumerOrganizationUnitName(), fft.getConsumerDataElementName(), fft.getConsumerCategoryName(), date);
            if(duplicateFft !=null)
            {
                fft.setRecordId(duplicateFft.getRecordId());
                fftdao.updateFinalFlatTable(fft);
            }
            else
            fftdao.saveFinalFlatTable(fft);
            System.err.println("FinalFlatTable with org unit "+fft.getConsumerOrganizationUnitName()+" saved");
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
      }
      }
    }
    catch (BiffException be)
    {
      be.printStackTrace();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return errorList;
  }
  public List readOrgUnitMatchRecordFromExcel(InputStream inputStream,String producerInstance,String consumerInstance) throws IOException
  {
      List errorList=new ArrayList();
        Workbook w;
    try
    {
        AppUtility appUtil=new AppUtility();
      w = Workbook.getWorkbook(inputStream);
      int count=w.getNumberOfSheets();
      String cellContent=null;
      String producerOrgUnitName=null;
      String consumerOrgUnitName=null;
      String producerOrgUnitId=null;
      String consumerOrgUnitId=null;
      boolean producerInstanceUsed=false;
      boolean updated=false;          
      for(int a=0; a<count; a++)
      {
      Sheet sheet = w.getSheet(a);
      DxOrganizationUnitMatch oum=null;
      DxOrganizationUnitMatch duplicateOum=null;
      DxOrganizationUnit producerOu=null;
      DxOrganizationUnit consumerOu=null;
      DxOrganizationUnitMatchDao oumdao=new DxOrganizationUnitMatchDaoImpl();
      DxOrganizationUnitDao oudao=new DxOrganizationUnitDaoImpl();
      
      
      // Loop over first 10 column and lines
      System.err.println("Sheet name is "+sheet.getName());
      for (int j = 1; j < sheet.getRows(); j++)
      {
          oum=new DxOrganizationUnitMatch();
        for (int i = 0; i < sheet.getColumns(); i++)
        {
            producerOrgUnitId=null;
            consumerOrgUnitId=null;
            producerOrgUnitName=null;
            consumerOrgUnitName=null;
            producerOu=null;
            consumerOu=null;
          Cell cell = sheet.getCell(i, j);
          if(i==0)
          {
                if(!isEmpty(cell.getContents()))
                {
                    producerOrgUnitName=cell.getContents().trim();
                    oum.setProducerOrgUnitName(producerOrgUnitName);
                }
          }
          else if(i==1)
          {
              if(!isEmpty(cell.getContents()))
                consumerOrgUnitName=cell.getContents().trim();
                oum.setConsumerOrgUnitName(consumerOrgUnitName);
          }
          else if(i==2)
          {
              if(!isEmpty(cell.getContents()))
              producerOrgUnitId=cell.getContents().trim();
              producerOu=oudao.getOrganizationUnitByNameAndInstance(producerOrgUnitName, producerInstance);
              if(producerOu !=null)
              {
                  producerOrgUnitId=producerOu.getOrgunitId();
                  producerInstanceUsed=true;
              }
              else
              {
                  producerOu=oudao.getOrganizationUnitByNameAndInstance(producerOrgUnitName, consumerInstance);
                  producerOrgUnitId=producerOu.getOrgunitId();
                  producerInstanceUsed=false;
              }
              oum.setProducerOrgUnitId(producerOrgUnitId);
          }
          else if(i==3)
          {
              if(!isEmpty(cell.getContents()))
              consumerOrgUnitId=cell.getContents().trim();
              if(producerInstanceUsed)
              {
                  consumerOu=oudao.getOrganizationUnitByNameAndInstance(consumerOrgUnitName, consumerInstance);
                  consumerOrgUnitId=consumerOu.getOrgunitId();
                  producerInstanceUsed=false;
              }
              else
              {
                  if(producerInstanceUsed)
                  {
                      consumerOu=oudao.getOrganizationUnitByNameAndInstance(consumerOrgUnitName, producerInstance);
                      consumerOrgUnitId=consumerOu.getOrgunitId();
                      producerInstanceUsed=true;
                  }
              }
              oum.setConsumerOrgUnitId(consumerOrgUnitId);
          }
          oum.setProducerInstanceId(producerInstance);      
          oum.setConsumerInstanceId(consumerInstance);
          oum.setLastModifiedDate(appUtil.getCurrentDate());
        }
          try
          {
            updated=false;
            if(oum.getProducerOrgUnitId() !=null && oum.getConsumerOrgUnitId() !=null)
            {
                duplicateOum=oumdao.getDxOrganizationUnitMatchByIdAndMatchDescription(producerOrgUnitId, producerInstance, consumerInstance);
                if(duplicateOum !=null)
                {
                    if(duplicateOum.getProducerOrgUnitId().trim().equalsIgnoreCase(oum.getProducerOrgUnitId().trim()) || duplicateOum.getProducerOrgUnitId().trim().equalsIgnoreCase(oum.getConsumerOrgUnitId().trim()))
                    {
                        if(duplicateOum.getConsumerOrgUnitId().trim().equalsIgnoreCase(oum.getConsumerOrgUnitId().trim()) || duplicateOum.getConsumerOrgUnitId().trim().equalsIgnoreCase(oum.getProducerOrgUnitId().trim()))
                        {
                           oum.setRecordId(duplicateOum.getRecordId());
                           oumdao.updateOrganizationUnit(oum);
                           updated=true;
                        }
                    }

                }
                else if(!updated)
                oumdao.saveOrganizationUnit(oum);
                System.err.println("OrganizationUnitMatche with org unit "+oum.getProducerOrgUnitName()+" and consumer orgunit "+oum.getConsumerOrgUnitName()+" saved");
            }
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
      }
      }
    }
    catch (BiffException be)
    {
      be.printStackTrace();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return errorList;
  }
  public boolean isEmpty(String value)
  {
      if(value !=null)
      {
          value=value.trim();
          if(value !=null && !value.equalsIgnoreCase("") && !value.equalsIgnoreCase(" ") && !value.equalsIgnoreCase("  ") && value.trim().length()>0)
          return false;
      }
      return true;
  }
}
