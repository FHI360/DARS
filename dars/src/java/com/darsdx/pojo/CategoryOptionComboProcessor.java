/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.pojo;

import com.darsdx.business.CategoryOptionCombo;
import com.darsdx.dao.CategoryOptionComboDao;
import com.darsdx.dao.CategoryOptionComboDaoImpl;
import com.darsdx.util.AppUtility;
import com.darsdx.util.DateManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class CategoryOptionComboProcessor 
{
    public static List extractCategoryOptionCombinationFromXml(List list, String dhisId)
    {
        List ccList=new ArrayList();
        try
        {
            CategoryOptionComboDao ccdao=new CategoryOptionComboDaoImpl();
            CategoryOptionCombo cc=new CategoryOptionCombo();
            List strList=XmlProcessor.splitXmlToString(list,"</categoryOptionCombo>");
            String id=null;
            AppUtility appUtil=new AppUtility();
            String displayName=null;
            for(int i=0; i<strList.size(); i++)
          {

              String str=strList.get(i).toString();

              //System.err.println("Data element list content at "+i+" is "+str);
              if(str !=null && (str.indexOf("categoryOptionCombo") !=-1 && str.indexOf("id") !=-1))
              {
                  //remove unwanted tag elements
                  str=str.replace("<displayName>", "");
                  str=str.replace("</displayName>", "");
                  str=str.replace("<categoryOptionCombo ", "");
                  str=str.replace("id=\"", "");
                  String orgUnitName=null;
                  String orgUnitId=null;

                  if(str.indexOf("<categoryOptionCombos>") !=-1)
                  {
                      String[] idandNameArray=str.split("<categoryOptionCombos>");
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
                          orgUnitName=displayName.trim();
                          orgUnitId=id.trim();
                          cc.setCategoryOptionComboName(orgUnitName);
                          cc.setCategoryOptionComboId(orgUnitId);
                          cc.setDhisId(dhisId);
                          cc.setLastModifiedDate(DateManager.getCurrentDateAsDateObject());
                          CategoryOptionCombo cc2=ccdao.getCategoryOptionCombination(orgUnitId);
                          if(cc2==null)
                          ccdao.saveCategoryOptionCombination(cc);
                          else
                          {
                            ccdao.updateCategoryOptionCombination(cc);
                          }
                          ccList.add(cc);
                          System.err.println("CategoryOptionCombo id is "+cc.getCategoryOptionComboId()+", name "+cc.getCategoryOptionComboName());
                     }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return ccList;
}
}
