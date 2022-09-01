/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.pojo;

import com.darsdx.business.DataElement;
import com.darsdx.dao.DataElementDao;
import com.darsdx.dao.DataElementDaoImpl;
import com.darsdx.util.AppUtility;
import com.darsdx.util.DateManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class DataElementProcessor 
{

public static List extractDataElementFromXml(List list, String dhisId)
{
    List deParamList=new ArrayList();
    try
    {
        DataElementDao dedao=new DataElementDaoImpl();
        DataElement de=new DataElement();
        List strList=XmlProcessor.splitXmlToString(list,"</dataElement>");
        String id=null;
        AppUtility appUtil=new AppUtility();
        String displayName=null;
      //int counter=0;
      for(int i=0; i<strList.size(); i++)
      {

          String str=strList.get(i).toString();
          
          //System.err.println("Data element list content at "+i+" is "+str);
          if(str !=null && (str.indexOf("dataElement") !=-1 && str.indexOf("id") !=-1))
          {
              //remove unwanted tag elements
              str=str.replace("<displayName>", "");
              str=str.replace("</displayName>", "");
              str=str.replace("<dataElement ", "");
              str=str.replace("id=\"", "");
              String dataElementName=null;
              String dataElementId=null;
              
              if(str.indexOf("<dataElements>") !=-1)
              {
                  String[] idandNameArray=str.split("<dataElements>");
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
                 if((id !=null && appUtil.isString(id)) && (displayName !=null && appUtil.isString(displayName)))
                 {
                      dataElementName=displayName.trim();
                      dataElementId=id.trim();
                      de.setDataElementName(dataElementName);
                      de.setDataElementId(dataElementId);
                      de.setDhisId(dhisId);
                      de.setLastModifiedDate(DateManager.getCurrentDateAsDateObject());
                      if(dedao.getDataElement(dataElementId)==null)
                      dedao.saveDataElement(de);
                      else
                      dedao.updateDataElement(de);
                      System.err.println("dataElement id is "+de.getDataElementId()+", name "+de.getDataElementName());
                 }
            }
        }//
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
    return deParamList;
}
}
