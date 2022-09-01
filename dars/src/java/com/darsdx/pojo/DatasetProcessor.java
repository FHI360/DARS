/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.pojo;

import com.darsdx.business.DataSet;
import com.darsdx.dao.DataSetDao;
import com.darsdx.dao.DataSetDaoImpl;
import com.darsdx.util.AppUtility;
import com.darsdx.util.DateManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class DatasetProcessor 
{
    public static List extractDataSetFromXml(List list, String dhisId)
    {
        List dstList=new ArrayList();
        try
        {
            DataSetDao dstdao=new DataSetDaoImpl();
            DataSet dst=new DataSet();
            List strList=XmlProcessor.splitXmlToString(list,"</dataSet>");
            String id=null;
            //AppUtility appUtil=new AppUtility();
            String displayName=null;
            for(int i=0; i<strList.size(); i++)
          {

              String str=strList.get(i).toString();

              //System.err.println("Data element list content at "+i+" is "+str);
              if(str !=null && (str.indexOf("dataSet") !=-1 && str.indexOf("id") !=-1))
              {
                  //remove unwanted tag elements
                  str=str.replace("<displayName>", "");
                  str=str.replace("</displayName>", "");
                  str=str.replace("<dataSet ", "");
                  str=str.replace("id=\"", "");
                  String name=null;
                  String datasetId=null;

                  if(str.indexOf("<dataSets>") !=-1)
                  {
                      String[] idandNameArray=str.split("<dataSets>");
                      if(idandNameArray !=null && idandNameArray.length>1)
                      {
                          str=idandNameArray[1];
                      }
                  }
                  if(str.indexOf("\">") !=-1)
                  {
                      String[] idandNameArray=str.split("\">");
                      if(idandNameArray !=null && idandNameArray.length>1)
                      {
                          id=idandNameArray[0];
                          displayName=idandNameArray[1];
                      }
                  }
                     if((id !=null && AppUtility.isString(id)) && (displayName !=null && AppUtility.isString(displayName)))
                     {
                          name=displayName.trim();
                          datasetId=id.trim();
                          dst.setDatasetName(name);
                          dst.setDatasetId(datasetId);
                          dst.setDhisId(dhisId);
                          dst.setLastModifiedDate(DateManager.getCurrentDateAsDateObject());
                          DataSet dst2=dstdao.getDataSet(datasetId,dst.getDhisId());
                          if(dst2==null)
                          dstdao.saveDataSet(dst);
                          else
                          {
                            dstdao.updateDataSet(dst);
                          }
                          dstList.add(dst);
                          System.err.println("Dataset id is "+dst.getDatasetId()+", name "+dst.getDatasetName());
                     }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return dstList;
}
}
