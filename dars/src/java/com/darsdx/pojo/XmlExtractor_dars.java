/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.pojo;


import com.darsdx.business.DataElement;
import com.darsdx.business.OrganizationUnit;
import com.darsdx.dao.DataElementDao;
import com.darsdx.dao.DataElementDaoImpl;
import com.darsdx.dao.OrganizationUnitDao;
import com.darsdx.dao.OrganizationUnitDaoImpl;
import com.darsdx.util.AppUtility;
import com.darsdx.util.DateManager;
import java.util.Date;
import java.util.List;
/**
 *
 * @author smomoh
 */
public class XmlExtractor_dars 
{
   public static void readOrgUnitFromXml(List list,String dhisInstance) 
  {
      AppUtility appUtil=new AppUtility();
      
      int savedCount=0;
      int loopCount=0;
      if(list !=null)
      {
          String str=null;
          String str2=null;
          String orgunitName=null;
          String orgunitId=null;
          String parentId=null;
          int level=0;
          OrganizationUnitDao oudao=new OrganizationUnitDaoImpl();
          OrganizationUnit ou=new OrganizationUnit();
          
          ou.setLastModifiedDate(DateManager.getCurrentDateAsDateObject());
          //System.out.println("Number of organization unit is "+list.size());
          String xmlstr=null;
          try
          {
              for(int i=0; i<list.size(); i++)
              {
                  xmlstr=list.get(i).toString();
                  if(xmlstr !=null && xmlstr.indexOf("<organisationUnit") !=-1)
                  {
                      orgunitName=null;
                      orgunitId=null;
                      parentId=null;
                      level=0;
                      str=null;
                      //System.out.println("str at "+i+" is "+str);
                      String[] strArray=xmlstr.split("<organisationUnit");
                      for(int j=0; j<strArray.length; j++)
                      {
                          if(loopCount > 14 && savedCount==0)
                          break;
                          loopCount++;
                          if(strArray[j].indexOf("id=") !=-1)
                          {
                              str=strArray[j].substring(strArray[j].indexOf("id=")+4);
                              orgunitId=str.substring(0, str.indexOf("\""));
                              ou.setOrgunitId(orgunitId);
                              //System.out.println("orgunitId is "+orgunitId);
                          }
                          if(strArray[j].indexOf("name=") !=-1)
                          {
                              str=strArray[j].substring(strArray[j].indexOf("name=")+6);
                              orgunitName=str.substring(0, str.indexOf("\""));
                              if(orgunitName !=null && orgunitName.length() > 199)
                              orgunitName=orgunitName.substring(0, 199);
                              ou.setOrgunitName(orgunitName);
                              //System.out.println("orgunitName is "+orgunitName);
                          }
                          if(strArray[j].indexOf("level=") !=-1)
                          {
                              str=strArray[j].substring(strArray[j].indexOf("level=")+7);
                              String strlevel=str.substring(0, str.indexOf("\""));
                              if(strlevel !=null)
                              level=Integer.parseInt(strlevel);
                              ou.setOulevel(level);
                              //System.out.println("level is "+level);
                          }
                          if(strArray[j].indexOf("<parent") !=-1)
                          {
                              str=strArray[j].substring(strArray[j].indexOf("<parent")+8);
                              //System.out.println("str in parent is "+str);
                              str2=str.substring(str.indexOf("id=")+4);
                              if(str2.indexOf("\"") !=-1)
                              {
                                parentId=str2.substring(0, str2.indexOf("\""));
                                ou.setParentId(parentId);
                              }
                          }
                          if(ou.getParentId()==null)
                          ou.setParentId("parentId");
                          ou.setLastModifiedDate(DateManager.getCurrentDateAsDateObject());
                          if(ou.getOrgunitId() !=null && ou.getOrgunitName() !=null)
                          {
                              oudao.saveOrganizationUnit(ou);
                              savedCount++;
                          }
                          
                      }
                  }       
              }
              if(savedCount==0)
              {
                  String id=null;
                  String displayName=null;
                  int counter=0;
                  for(int i=0; i<list.size(); i++)
                  {
                      str=list.get(i).toString();
                      if(str !=null && str.indexOf("<organisationUnit") !=-1 && str.indexOf("id=") !=-1)
                      {
                          orgunitName=null;
                          orgunitId=null;
                          String strIdPart=str.substring(str.indexOf("id=")+4, str.length()-2);
                          id=strIdPart;
                          str=list.get(i+1).toString();
                          if(str !=null && str.indexOf("<displayName>") !=-1)
                          {
                              String strNamePart="";
                              if(str.indexOf("</displayName>") ==-1)
                              {
                                  //the org unit name is on a seperate line
                                  displayName=list.get(i+2).toString();
                              }
                              else
                              {
                                 //the org unit name is on the same line as the display tags
                                 strNamePart=str.replace("<displayName>", "");
                                 displayName=strNamePart.replace("</displayName>", "");
                              }
                             if((id !=null && appUtil.isString(id)) && (displayName !=null && appUtil.isString(displayName)))
                             {
                                  orgunitName=displayName.trim();
                                  orgunitId=id.trim();
                                  ou.setParentId("parentId");
                                  ou.setOrgunitName(orgunitName);
                                  ou.setOrgunitId(orgunitId);
                                  ou.setOulevel(level);
                                  oudao.saveOrganizationUnit(ou);
                             }
                             System.err.println("DxOrganizationUnit id is "+ou.getOrgunitId()+", name "+ou.getOrgunitName());
                             i++;
                          }
                          counter++;
                                              
                        }
                      
                    }
              }
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
      }
  }
  
  public static void readDataElementsFromXml(List list,String dhisInstance) 
  {
      AppUtility appUtil=new AppUtility();
      
      if(list !=null)
      {
          String dataElementName=null;
          String dataElementId=null;
          Date lastModifiedDate=DateManager.getCurrentDateAsDateObject();
          DataElementDao dedao=new DataElementDaoImpl();
          DataElement de=new DataElement();
          
          de.setLastModifiedDate(lastModifiedDate);
          int savedCount=0;
          int loopCount=0;
          //System.out.println("Number of organization unit is "+list.size());
          String str=null;
          String idstrpart=null;
          try
          {
              for(int i=0; i<list.size(); i++)
              {
                  str=list.get(i).toString();
                  if(str !=null && str.indexOf("<dataElement") !=-1)
                  {
                      dataElementName=null;
                      dataElementId=null;
                      
                      String[] strArray=str.split("<dataElement");
                      for(int j=0; j<strArray.length; j++)
                      {
                          loopCount++;
                          de=new DataElement();
                          //de.setDhisInstance(dhisInstance);
                          de.setLastModifiedDate(lastModifiedDate);
                          //System.out.println("strArray at "+j+" is "+strArray[j]);
                          if(strArray[j] !=null)
                          {
                              if(loopCount > 14 && savedCount==0)
                                      break;
                              try
                              {
                                  //this contains the data element name and id
                                  if(strArray[j].indexOf("id=") !=-1)
                                  {
                                      idstrpart=strArray[j].substring(strArray[j].indexOf("id=")+4);
                                      dataElementId=idstrpart.substring(0, idstrpart.indexOf("\""));
                                      dataElementId=dataElementId.replace("'", "");
                                      dataElementId=dataElementId.replace("\"", "");
                                      if(dataElementId.length()>11)
                                      dataElementId=dataElementId.substring(0, 10);
                                      
                                      de.setDataElementId(dataElementId);
                                      //System.out.println("idstrpart for Data element id at "+j+" is "+idstrpart);
                                      System.out.println("Data element id at "+j+" is "+de.getDataElementId());
                                  }
                                  if(strArray[j].indexOf("name=") !=-1)
                                  {
                                      idstrpart=strArray[j].substring(strArray[j].indexOf("name=")+6);
                                      dataElementName=idstrpart.substring(0, idstrpart.indexOf("\""));
                                      de.setDataElementName(dataElementName);
                                      //System.out.println("idstrpart for Data element name at "+j+" is "+idstrpart);
                                      System.out.println("Data element name at "+j+" is "+de.getDataElementName());
                                  }
                                  if(de.getDataElementId() !=null && de.getDataElementName() !=null)
                                  {
                                      dedao.saveDataElement(de);
                                      savedCount++;
                                  }
                              }
                              catch(Exception ex)
                              {
                                  System.err.println(ex.getMessage());
                                  continue;
                              }
                          }         
                      }
                  }
              }
              if(savedCount==0)
              {
                  String id=null;
                  String displayName=null;
                  //int counter=0;
                  for(int i=0; i<list.size(); i++)
                  {
                      
                      str=list.get(i).toString();
                      System.err.println("Data element list content at "+i+" is "+str);
                      if(str !=null && (str.indexOf("dataElement") !=-1 && str.indexOf("id") !=-1))
                      {
                          dataElementName=null;
                          dataElementId=null;
                          String strIdPart=str.substring(str.indexOf("id=")+4, str.length()-2);
                          id=strIdPart;
                          str=list.get(i+1).toString();
                          System.err.println("Id Data element list content at "+i+" is "+id );
                          if(str !=null && str.indexOf("<displayName>") !=-1)
                          {
                              System.err.println("displayName in Data element list content at "+i+" is "+str );
                              String strNamePart="";
                              if(str.indexOf("</displayName>") ==-1)
                              {
                                  //the data element name is on a seperate line
                                  displayName=list.get(i+2).toString();
                              }
                              else
                              {
                                 //the data element name is on the same line as the display tags
                                 strNamePart=str.replace("<displayName>", "");
                                 displayName=strNamePart.replace("</displayName>", "");
                              }
                             if((id !=null && appUtil.isString(id)) && (displayName !=null && appUtil.isString(displayName)))
                             {
                                  dataElementName=displayName.trim();
                                  dataElementId=id.trim();
                                  de.setDataElementName(dataElementName);
                                  de.setDataElementId(dataElementId);
                                  dedao.saveDataElement(de);
                                  System.err.println("dataElement id is "+de.getDataElementId()+", name "+de.getDataElementName());
                             }
                           }           
                        }
                    }//
              }
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
      }
}
}
