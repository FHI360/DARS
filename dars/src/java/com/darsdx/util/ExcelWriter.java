/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.util;

import com.darsdx.business.BusinessRule;
import com.darsdx.business.DhisInstance;
import com.darsdx.business.DxOrganizationUnitMatch;
import com.darsdx.business.FinalFlatTable;
import com.darsdx.business.IntermediateFlatTable;
import com.darsdx.business.SourceData;
import com.darsdx.dao.DaoUtil;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 *
 * @author smomoh
 */
public class ExcelWriter implements Serializable
{
    private String inputFile;
    DaoUtil util=new DaoUtil();
  public void setInputFile(String inputFile)
  {
    this.inputFile = inputFile;
  }
public WritableWorkbook writeFinalFlatTableMatchingResultToExcel(OutputStream os,List list)
{
      WritableWorkbook wworkbook=null;
      WritableSheet wsheet =null;
      String sheetName="Report ";
      int sheetCount=0;
      Label label=null;
      Number number=null;

      try
      {
          //appUtil.createReportDirectory();
          wworkbook = Workbook.createWorkbook(os);
          wsheet = wworkbook.createSheet(sheetName+(sheetCount+1), sheetCount);
          String[] columnHeadings={"Organization unit id","Ou present flag", "Data element Id", "Dataelement present flag", "Category combo Id", "Category combo present flag", "Total match score"};
          int t=0;
          int row=1;
          int column=0;
          int total=0;
          FinalFlatTable fft=null;
          for(int k=0; k<columnHeadings.length; k++)
          {
              label = new Label(k, 0, columnHeadings[k]);
              wsheet.addCell(label);
          }
          for(int i=0; i<list.size(); i++)
          {
              column=0;
              fft=(FinalFlatTable)list.get(i);
              
              /*label = new Label(column, row, oum.getProducerInstanceId());
              wsheet.addCell(label);
              label = new Label(++column, row, oum.getConsumerInstanceId());
              wsheet.addCell(label);*/
              
              label = new Label(column, row, fft.getProducerOrganizationUnitId());
              wsheet.addCell(label);
              number = new Number(++column, row, fft.getOrgUnitFlag());
              wsheet.addCell(number);
              label = new Label(++column, row, fft.getProducerDataElementId());
              wsheet.addCell(label);
              number = new Number(++column, row, fft.getDataElementFlag());
              wsheet.addCell(number);
              
              label = new Label(column, row, fft.getProducerCategoryId());
              wsheet.addCell(label);
              number = new Number(++column, row, fft.getCategoryComboFlag());
              wsheet.addCell(number);
              total=fft.getOrgUnitFlag()+fft.getDataElementFlag()+fft.getCategoryComboFlag();
              number = new Number(++column, row, total);
              wsheet.addCell(number);
              
              /*If record gets to 50000, create new sheet*/
            if(t==(49900*(sheetCount+1)))
            {
                sheetCount++;
                wsheet=wworkbook.createSheet(sheetName+(sheetCount+1), sheetCount);
                row=0;
                for(int k=0; k<columnHeadings.length; k++)
                  {
                      label = new Label(k, 0, columnHeadings[k]);
                      wsheet.addCell(label);
                  }
            }
            row++;
            t++;
          }
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
      return wworkbook;
  }
public WritableWorkbook writeOrgUnitMatchToExcel(OutputStream os,List list,String producerInstanceName, String consumerInstanceName)
{
      WritableWorkbook wworkbook=null;
      WritableSheet wsheet =null;
      String sheetName="Report ";
      int sheetCount=0;
      Label label=null;
      Number number=null;

      try
      {
          //appUtil.createReportDirectory();
          wworkbook = Workbook.createWorkbook(os);
          wsheet = wworkbook.createSheet(sheetName+(sheetCount+1), sheetCount);
          String[] columnHeadings={producerInstanceName+" Organization unit name",consumerInstanceName+" Organization unit name", producerInstanceName+" Organization unit Id", consumerInstanceName+" Organization unit Id", producerInstanceName+" Organization unit match", consumerInstanceName+" Organization unit match", "Total match score"};
          int t=0;
          int row=1;
          int column=0;
          DxOrganizationUnitMatch oum=new DxOrganizationUnitMatch();
          for(int k=0; k<columnHeadings.length; k++)
          {
              label = new Label(k, 0, columnHeadings[k]);
              wsheet.addCell(label);
          }
          for(int i=0; i<list.size(); i++)
          {
              column=0;
              oum=(DxOrganizationUnitMatch)list.get(i);
              
              /*label = new Label(column, row, oum.getProducerInstanceId());
              wsheet.addCell(label);
              label = new Label(++column, row, oum.getConsumerInstanceId());
              wsheet.addCell(label);*/
              
              label = new Label(column, row, oum.getProducerOrgUnitName());
              wsheet.addCell(label);
              label = new Label(++column, row, oum.getConsumerOrgUnitName());
              wsheet.addCell(label);
              label = new Label(++column, row, oum.getProducerOrgUnitId());
              wsheet.addCell(label);
              label = new Label(++column, row, oum.getConsumerOrgUnitId());
              wsheet.addCell(label);
              
              number = new Number(++column, row, oum.getProducerouvalidated());
              wsheet.addCell(number);
              number = new Number(++column, row, oum.getConsumerouvalidated());
              wsheet.addCell(number);
              number = new Number(++column, row, oum.getTotalValidationScore());
              wsheet.addCell(number);
              
              /*If record gets to 50000, create new sheet*/
            if(t==(49900*(sheetCount+1)))
            {
                sheetCount++;
                wsheet=wworkbook.createSheet(sheetName+(sheetCount+1), sheetCount);
                row=0;
                for(int k=0; k<columnHeadings.length; k++)
                  {
                      label = new Label(k, 0, columnHeadings[k]);
                      wsheet.addCell(label);
                  }
            }
            row++;
            t++;
          }
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
      return wworkbook;
  }
public WritableWorkbook writeBusinessRulesToExcel(OutputStream os,List list,String producerInstanceName, String consumerInstanceName)
{
      WritableWorkbook wworkbook=null;
      WritableSheet wsheet =null;
      String sheetName="Report ";
      int sheetCount=0;
      Label label=null;
      Number number=null;

      try
      {
          //appUtil.createReportDirectory();
          wworkbook = Workbook.createWorkbook(os);
          wsheet = wworkbook.createSheet(sheetName+(sheetCount+1), sheetCount);
          String[] columnHeadings={"Dataset",producerInstanceName+" Data element", producerInstanceName+" Category combination",consumerInstanceName+" Data element", consumerInstanceName+" Category combination","Logic",producerInstanceName+" Data element Id", producerInstanceName+" Category combination Id",consumerInstanceName+" Data element Id", consumerInstanceName+" Category combination Id",producerInstanceName+" Data element match", producerInstanceName+" Category combination match",consumerInstanceName+" Data element match", consumerInstanceName+" Category combination match","Total match score"};
          int t=0;
          int row=1;
          int column=0;
          BusinessRule dbr=new BusinessRule();
          for(int k=0; k<columnHeadings.length; k++)
          {
              label = new Label(k, 0, columnHeadings[k]);
              wsheet.addCell(label);
          }
          for(int i=0; i<list.size(); i++)
          {
              column=0;
              dbr=(BusinessRule)list.get(i);
              label = new Label(column, row, dbr.getDataset());
              wsheet.addCell(label);
              label = new Label(++column, row, dbr.getProducerDeName());
              wsheet.addCell(label);
              label = new Label(++column, row, dbr.getProducerCatComboName());
              wsheet.addCell(label);
              label = new Label(++column, row, dbr.getConsumerDeName());
              wsheet.addCell(label);
              label = new Label(++column, row, dbr.getConsumerCatComboName());
              wsheet.addCell(label);
              label = new Label(++column, row, dbr.getBusinessLogicName());
              wsheet.addCell(label);
              
              label = new Label(++column, row, dbr.getProducerDeId());
              wsheet.addCell(label);
              label = new Label(++column, row, dbr.getProducerCatComboId());
              wsheet.addCell(label);
              label = new Label(++column, row, dbr.getConsumerDeId());
              wsheet.addCell(label);
              label = new Label(++column, row, dbr.getConsumerCatComboId());
              wsheet.addCell(label);
              number = new Number(++column, row, dbr.getPdevalidated());
              wsheet.addCell(number);
              number = new Number(++column, row, dbr.getPccvalidated());
              wsheet.addCell(number);
              number = new Number(++column, row, dbr.getCdevalidated());
              wsheet.addCell(number);
              number = new Number(++column, row, dbr.getCccvalidated());
              wsheet.addCell(number);
              number = new Number(++column, row, dbr.getValidationScore());
              wsheet.addCell(number);
              //number
              /*If record gets to 50000, create new sheet*/
            if(t==(49900*(sheetCount+1)))
            {
                sheetCount++;
                wsheet=wworkbook.createSheet(sheetName+(sheetCount+1), sheetCount);
                row=0;
                for(int k=0; k<columnHeadings.length; k++)
                  {
                      label = new Label(k, 0, columnHeadings[k]);
                      wsheet.addCell(label);
                  }
            }
            row++;
            t++;
          }
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
      return wworkbook;
  }
public WritableWorkbook writeSourceDataToExcel(OutputStream os,List list)
{
      WritableWorkbook wworkbook=null;
      WritableSheet wsheet =null;
      String sheetName="Report ";
      int sheetCount=0;
      Label label=null;
      Number number=null;

      try
      {
          wworkbook = Workbook.createWorkbook(os);
          wsheet = wworkbook.createSheet(sheetName+(sheetCount+1), sheetCount);
          String[] columnHeadings={"Organization unit","Data element","Category","start date","Value","Dataset","Orgunit type","Orgunit group","Orgunit ownership","Funding body","Instance"};
          int t=0;
          int row=1;
          SourceData sd=new SourceData();
          DaoUtil util=new DaoUtil();
          String instanceName=null;
          for(int k=0; k<columnHeadings.length; k++)
          {
              label = new Label(k, 0, columnHeadings[k]);
              wsheet.addCell(label);
          }
          for(int i=0; i<list.size(); i++)
          {
              instanceName=null;
                  sd=(SourceData)list.get(i);
                  if(util.getDhisInstanceDaoInstance().getDhisInstanceById(sd.getDhisInstance()) !=null)
                  instanceName=util.getDhisInstanceDaoInstance().getDhisInstanceById(sd.getDhisInstance()).getDhisName();
                  label = new Label(0, row, sd.getOrganizationUnit());
                  wsheet.addCell(label);
                  label = new Label(1, row, sd.getDataElementName());
                  wsheet.addCell(label);
                  label = new Label(2, row, sd.getCategoryOptionComboName());
                  wsheet.addCell(label);
                  label = new Label(3, row, sd.getStartDate());
                  wsheet.addCell(label);
                  number = new Number(4, row,sd.getValue());
                  wsheet.addCell(number);
                  label = new Label(5, row,sd.getDataset());
                  wsheet.addCell(label);
                  label = new Label(6, row, sd.getOrganizationUnitType());
                  wsheet.addCell(label);
                  label = new Label(7, row, sd.getOrganizationUnitGroup());
                  wsheet.addCell(label);
                  label = new Label(8, row, sd.getOrganizationUnitOwnership());
                  wsheet.addCell(label);
                  label = new Label(9, row, sd.getFundingBody());
                  wsheet.addCell(label);
                  label = new Label(10, row, instanceName);
                  wsheet.addCell(label);
                  
                  //If record gets to 50000, create new sheet
                if(t==(49900*(sheetCount+1)))
                {
                    sheetCount++;
                    wsheet=wworkbook.createSheet(sheetName+(sheetCount+1), sheetCount);
                    row=0;
                    for(int k=0; k<columnHeadings.length; k++)
                      {
                          label = new Label(k, 0, columnHeadings[k]);
                          wsheet.addCell(label);
                      }
                }
                row++;
                t++;
          }
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
      return wworkbook;
}
public WritableWorkbook writeIntermediateFlatTableToExcel(OutputStream os,List list)
  {
      DhisInstance producerInstance=null;
      DhisInstance consumerInstance=null;
      String producerInstanceName=null;
      String consumerInstanceName=null;
      WritableWorkbook wworkbook=null;
      WritableSheet wsheet =null;
      String sheetName="Report ";
      int sheetCount=0;
      Label label=null;
      Number number=null;

      try
      {
          wworkbook = Workbook.createWorkbook(os);
          wsheet = wworkbook.createSheet(sheetName+(sheetCount+1), sheetCount);
          String[] columnHeadings={"Producer instance","Facility","Data element","Category","Dataset","Orgunit type","Orgunit group","Orgunit ownership","Logic","Consumer instance","Facility","Data element","Category","Start date","value"};
          int t=0;
          int row=1;
          IntermediateFlatTable ift=null;//new IntermediateFlatTable();
          for(int k=0; k<columnHeadings.length; k++)
          {
              label = new Label(k, 0, columnHeadings[k]);
              wsheet.addCell(label);
          }
          for(int i=0; i<list.size(); i++)
          {
              ift=(IntermediateFlatTable)list.get(i);
              producerInstanceName=null;
              consumerInstanceName=null;
              producerInstance=util.getDhisInstanceDaoInstance().getDhisInstanceById(ift.getProducerInstance());
              consumerInstance=util.getDhisInstanceDaoInstance().getDhisInstanceById(ift.getConsumerInstance());
              if(producerInstance !=null)
              producerInstanceName=producerInstance.getDhisName();
              if(consumerInstance !=null)
              consumerInstanceName=consumerInstance.getDhisName();
                  
                  label = new Label(0, row, producerInstanceName);
                  wsheet.addCell(label);
                  label = new Label(1, row, ift.getProducerOrganizationUnit());
                  wsheet.addCell(label);
                  label = new Label(2, row, ift.getProducerDataElement());
                  wsheet.addCell(label);
                  label = new Label(3, row, ift.getProducerCategory());
                  wsheet.addCell(label);
                  
                  label = new Label(4, row, ift.getDataset());
                  wsheet.addCell(label);
                  label = new Label(5, row, ift.getOrgUnitType());
                  wsheet.addCell(label);
                  label = new Label(6, row, ift.getOrgUnitGroup());
                  wsheet.addCell(label);
                  label = new Label(7, row, ift.getOrganizationUnitOwnership());
                  wsheet.addCell(label);
                  label = new Label(8, row, ift.getLogic());
                  wsheet.addCell(label);
                  label = new Label(9, row, consumerInstanceName);
                  wsheet.addCell(label);
                  label = new Label(10, row, ift.getConsumerOrganizationUnitName());
                  wsheet.addCell(label);
                  label = new Label(11, row, ift.getConsumerDataElementName());
                  wsheet.addCell(label);
                  label = new Label(12, row, ift.getConsumerCategoryName());
                  wsheet.addCell(label);
                  label = new Label(13, row, ift.getStartDate());
                  wsheet.addCell(label);
                  number = new Number(14, row,ift.getValue());
                  wsheet.addCell(number);
                  
                  //If record gets to 50000, create new sheet
                if(t==(49900*(sheetCount+1)))
                {
                    sheetCount++;
                    wsheet=wworkbook.createSheet(sheetName+(sheetCount+1), sheetCount);
                    row=0;
                    for(int k=0; k<columnHeadings.length; k++)
                      {
                          label = new Label(k, 0, columnHeadings[k]);
                          wsheet.addCell(label);
                      }
                }
                row++;
                t++;
          }
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
      return wworkbook;
  }
public WritableWorkbook writeFinalFlatTableToExcel(OutputStream os,List list)
  {
      DhisInstance consumerInstance=null;
      String consumerInstanceName=null;
      WritableWorkbook wworkbook=null;
      WritableSheet wsheet =null;
      String sheetName="Report ";
      int sheetCount=0;
      Label label=null;
      Number number=null;

      try
      {
          //appUtil.createReportDirectory();
          wworkbook = Workbook.createWorkbook(os);
          wsheet = wworkbook.createSheet(sheetName+(sheetCount+1), sheetCount);
          String[] columnHeadings={"Consumer instance","Facility","Data element","Category","Start date","value"};
          int t=0;
          int row=1;
          FinalFlatTable fft=null;//new IntermediateFlatTable();
          for(int k=0; k<columnHeadings.length; k++)
          {
              label = new Label(k, 0, columnHeadings[k]);
              wsheet.addCell(label);
          }
          for(int i=0; i<list.size(); i++)
          {
                  fft=(FinalFlatTable)list.get(i);
                  
              
              consumerInstance=util.getDhisInstanceDaoInstance().getDhisInstanceById(fft.getConsumerInstance());
              if(consumerInstance !=null)
              consumerInstanceName=consumerInstance.getDhisName();
                  label = new Label(0, row, consumerInstanceName);
                  wsheet.addCell(label);
                  label = new Label(1, row, fft.getConsumerOrganizationUnitName());
                  wsheet.addCell(label);
                  label = new Label(2, row, fft.getConsumerDataElementName());
                  wsheet.addCell(label);
                  label = new Label(3, row, fft.getConsumerCategoryName());
                  wsheet.addCell(label);
                  label = new Label(4, row, fft.getStartDate());
                  wsheet.addCell(label);
                  number = new Number(5, row,fft.getValue());
                  wsheet.addCell(number);
                  
                  //If record gets to 50000, create new sheet
                if(t==(49900*(sheetCount+1)))
                {
                    sheetCount++;
                    wsheet=wworkbook.createSheet(sheetName+(sheetCount+1), sheetCount);
                    row=0;
                    for(int k=0; k<columnHeadings.length; k++)
                      {
                          label = new Label(k, 0, columnHeadings[k]);
                          wsheet.addCell(label);
                      }
                }
                row++;
                t++;
          }
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
      return wworkbook;
  }
}
