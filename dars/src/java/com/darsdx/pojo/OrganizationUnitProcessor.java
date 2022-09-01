/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.pojo;

import com.darsdx.business.OrganisationUnitGroup;
import com.darsdx.business.OrganizationUnit;
import com.darsdx.dao.DaoUtility;
import com.darsdx.dao.OrganizationUnitDao;
import com.darsdx.dao.OrganizationUnitDaoImpl;
import com.darsdx.dao.OrganizationUnitGroupDao;
import com.darsdx.dao.OrganizationUnitGroupDaoImpl;
import com.darsdx.util.AppUtility;
import com.darsdx.util.DateManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class OrganizationUnitProcessor 
{
    public static List extractOrganizationUnitFromXml(List list, String dhisId)
    {
        List deParamList=new ArrayList();
        try
        {
            OrganizationUnitDao oudao=new OrganizationUnitDaoImpl();
            OrganizationUnit ou=new OrganizationUnit();
            List strList=XmlProcessor.splitXmlToString(list,"</organisationUnit>");
            String id=null;
            AppUtility appUtil=new AppUtility();
            String displayName=null;
            for(int i=0; i<strList.size(); i++)
              {

                  String str=strList.get(i).toString();

                  System.err.println("Organization unit content at "+i+" is "+str);
                  if(str !=null && (str.indexOf("organisationUnit") !=-1 && str.indexOf("id") !=-1))
                  {
                      //remove unwanted tag elements
                      str=str.replace("<displayName>", "");
                      str=str.replace("</displayName>", "");
                      str=str.replace("<organisationUnit ", "");
                      //str=str.replace("id=\"", "");
                      String orgUnitName=null;
                      String orgUnitId=null;

                      if(str.indexOf("<organisationUnits>") !=-1)
                      {
                          String[] idandNameArray=str.split("<organisationUnits>");
                          if(idandNameArray !=null && idandNameArray.length>1)
                          {
                              //str=idandNameArray[1];
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
                         //System.err.println("str is "+str);
                          orgUnitName=getOrgUnitName(str);//displayName.trim();
                          orgUnitId=getOrgUnitId(str);//id.trim();
                          ou.setOrgunitName(orgUnitName);
                          ou.setOrgunitId(orgUnitId);
                          ou.setDhisId(dhisId);
                          ou.setOulevel(getLevel(str));
                          ou.setParentId(getParent(str));
                          ou.setPath(getPath(str));
                          ou.setShortName(getShortName(str));
                          //ou.setOuCode(getOrgUnitCode(str));
                          ou.setLastModifiedDate(DateManager.getCurrentDateAsDateObject());
                          OrganizationUnit ou2=oudao.getOrganizationUnit(orgUnitId);
                          if(ou2==null)
                          oudao.saveOrganizationUnit(ou);
                          else
                          {
                            ou.setParentId(ou2.getParentId());
                            if(ou.getOulevel()==0 && ou2.getOulevel()>0)
                            ou.setOulevel(ou2.getOulevel());
                            oudao.updateOrganizationUnit(ou);
                          }
                          deParamList.add(ou);
                          System.err.println("organization unit id is "+ou.getOrgunitId()+", name: "+ou.getOrgunitName()+", level: "+ou.getOulevel()+", parent: "+ou.getParentId()+", Path: "+ou.getPath()+", Code: "+ou.getOuCode());
                     }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return deParamList;
}
public static List extractOrganizationUnitGroupFromXml(List list, String dhisId)
    {
        List deParamList=new ArrayList();
        try
        {
            OrganizationUnitGroupDao oudao=new OrganizationUnitGroupDaoImpl();
            OrganisationUnitGroup oug=new OrganisationUnitGroup();
            List strList=XmlProcessor.splitXmlToString(list,"</organisationUnitGroup>");
            String id=null;
            AppUtility appUtil=new AppUtility();
            String displayName=null;
            for(int i=0; i<strList.size(); i++)
              {

                  String str=strList.get(i).toString();

                  System.err.println("OrganizationGroup unit content at "+i+" is "+str);
                  if(str !=null && (str.indexOf("organisationUnitGroup") !=-1 && str.indexOf("id") !=-1))
                  {
                      //remove unwanted tag elements
                      str=str.replace("<displayName>", "");
                      str=str.replace("</displayName>", "");
                      str=str.replace("<organisationUnitGroup ", "");
                      str=str.replace("id=\"", "");
                      String orgUnitGroupName=null;
                      String orgUnitGroupId=null;

                      if(str.indexOf("<organisationUnitGroups>") !=-1)
                      {
                          String[] idandNameArray=str.split("<organisationUnitGroups>");
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
                          orgUnitGroupName=displayName.trim();//displayName.trim();
                          orgUnitGroupId=id.trim();//id.trim();
                          oug.setOrgunitGroupName(orgUnitGroupName);
                          oug.setOrgunitGroupId(orgUnitGroupId);
                          oug.setDhisId(dhisId);
                          oug.setLastModifiedDate(DateManager.getCurrentDateAsDateObject());
                          OrganisationUnitGroup ou2=oudao.getOrganizationUnitGroup(orgUnitGroupId);
                          if(ou2==null)
                          oudao.saveOrganizationUnitGroup(oug);
                          else
                          {
                           oudao.updateOrganizationUnitGroup(oug);
                          }
                          deParamList.add(oug);
                          System.err.println("organization unit group id is "+oug.getOrgunitGroupId()+", name: "+oug.getOrgunitGroupName());
                     }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return deParamList;
}
public static List extractOrganizationUnitAttributesFromXml(List list, String dhisId)
    {
        List deParamList=new ArrayList();
        try
        {
            if(list !=null)
            {
                String xmlString=null;
                for(int i=0; i<list.size(); i++)
                {
                    xmlString=list.get(i).toString();
                    int level=getLevel(xmlString);
                    System.err.println("level: "+level);
                    String path=getPath(xmlString);
                    System.err.println("Path: "+path);
                    List children=getChildren(xmlString);
                    setOrgUnitAttributes(level, path,children, dhisId);
               }
         }
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
    return deParamList;
}
private static void setOrgUnitAttributes(int level,String path,List children,String dhisId)
{
    try
    {
    if(path !=null)
    {
        if(path.indexOf("/") !=-1)
        {
            String[] pathArray=path.split("/");
            if(pathArray.length>0)
            {
                String orgUnitId=pathArray[pathArray.length-1];
                String parentId=orgUnitId;
                if(pathArray.length>1)
                {
                    parentId=pathArray[pathArray.length-2];
                }
                OrganizationUnit ou=DaoUtility.getOrganizationUnitDaoInstance().getOrganizationUnit(dhisId, orgUnitId);
                if(ou !=null)
                {
                    ou.setOulevel(level);
                    ou.setParentId(parentId);
                    ou.setPath(path);
                    DaoUtility.getOrganizationUnitDaoInstance().updateOrganizationUnit(ou);
                }
                String childParent=orgUnitId;
                for(int i=0; i<children.size(); i++)
                {
                    String childId=children.get(i).toString();
                    String childIdPath=path+"/"+childId;
                    setOrgUnitChildrenAttributes(childId,level+1,childParent,childIdPath,dhisId);
                }
            }
        }
    }
    }
    catch(Exception ex)
    {
        System.err.println("Unable to set Organization unit attributes: "+ex.getMessage());
    }
}
private static void setOrgUnitChildrenAttributes(String childId,int level,String parentId,String path,String dhisId)
{
    try
    {
    if(path !=null)
    {
        OrganizationUnit ou=DaoUtility.getOrganizationUnitDaoInstance().getOrganizationUnit(dhisId, childId);
        if(ou !=null)
        {
            ou.setOulevel(level);
            ou.setParentId(parentId);
            ou.setPath(path);
            DaoUtility.getOrganizationUnitDaoInstance().updateOrganizationUnit(ou);
            System.err.println("Child path is: "+ou.getPath());
        }
    }
    }
    catch(Exception ex)
    {
        System.err.println("Unable to set Organization unit attributes: "+ex.getMessage());
    }
}
private static String getOrgUnitId(String xmlString)
{
    String parentId=null;
    if(xmlString !=null && xmlString.indexOf("id=\"") !=-1)
    {
        String[] xmlSplit=xmlString.split("id=\"");
        if(xmlSplit !=null && xmlSplit.length>1)
        {
            parentId=xmlSplit[1].substring(0,xmlSplit[1].indexOf("\""));
        }
    }
    return parentId;
}
private static String getOrgUnitName(String xmlString)
{
    String ouname=null;
    if(xmlString !=null && xmlString.indexOf("name=\"") !=-1)
    {
        String[] xmlSplit=xmlString.split("name=\"");
        if(xmlSplit !=null && xmlSplit.length>1)
        {
            ouname=xmlSplit[1].substring(0,xmlSplit[1].indexOf("\""));
        }
    }
    return ouname;
}
private static String getShortName(String xmlString)
{
    String oucode=null;
    if(xmlString !=null && xmlString.indexOf("shortName=\"") !=-1)
    {
        String[] xmlSplit=xmlString.split("shortName=\"");
        if(xmlSplit !=null && xmlSplit.length>1)
        {
            oucode=xmlSplit[1].substring(0,xmlSplit[1].indexOf("\""));
        }
    }
    return oucode;
}
private static String getOrgUnitCode(String xmlString)
{
    String oucode=null;
    if(xmlString !=null && xmlString.indexOf("code=\"") !=-1)
    {
        String[] xmlSplit=xmlString.split("code=\"");
        if(xmlSplit !=null && xmlSplit.length>1)
        {
            oucode=xmlSplit[1].substring(0,xmlSplit[1].indexOf("\""));
        }
    }
    return oucode;
}
/*private static String getOrgUnitName(String xmlString)
{
    String ouname=null;
    if(xmlString !=null && xmlString.indexOf("name=\"") !=-1)
    {
        String[] xmlSplit=xmlString.split("name=\"");
        if(xmlSplit !=null && xmlSplit.length>1)
        {
            ouname=xmlSplit[1].substring(0,xmlSplit[1].indexOf("\""));
        }
    }
    return ouname;
}*/
private static int getLevel(String xmlString)
{
    int oulevel=0;
    if(xmlString !=null && xmlString.indexOf("level=") !=-1)
    {
        String[] levelSplit=xmlString.split("level=\"");
        if(levelSplit !=null && levelSplit.length>1)
        {
            String levelstr=levelSplit[1].substring(0,1);
            if(levelstr !=null && AppUtility.isNumber(levelstr))
            oulevel=Integer.parseInt(levelstr);
        }
    }
    return oulevel;
}
private static String getPath(String xmlString)
{
    String path=null;
    if(xmlString !=null && xmlString.indexOf("<path>") !=-1)
    {
        String[] xmlSplit=xmlString.split("<path>");
        if(xmlSplit !=null && xmlSplit.length>1)
        {
            path=xmlSplit[1].substring(0,xmlSplit[1].indexOf("</path>"));
        }
    }
    return path;
}
private static String getParent(String xmlString)
{
    String parentId=null;
    if(xmlString !=null && xmlString.indexOf("<parent id=\"") !=-1)
    {
        String[] xmlSplit=xmlString.split("<parent id=\"");
        if(xmlSplit !=null && xmlSplit.length>1)
        {
            parentId=xmlSplit[1].substring(0,xmlSplit[1].indexOf("\""));
        }
    }
    return parentId;
}
private static List getChildren(String xmlString)
{
    List list=new ArrayList();
    if(xmlString !=null && xmlString.indexOf("<children>") !=-1)
    {
        String[] xmlSplit=xmlString.split("<children>");
        if(xmlSplit !=null && xmlSplit.length>1)
        {
            String xmlstr=xmlSplit[1].substring(0,xmlSplit[1].indexOf("</children>"));
            xmlstr=xmlstr.replaceAll("\"/><child ", "");
            xmlstr=xmlstr.replaceAll("<child ", "");
            xmlstr=xmlstr.replaceAll("\"/>", "");
            String[] childrenIds=xmlstr.split("id=\"");
            if(childrenIds !=null && childrenIds.length>0)
            {
                for(int i=0; i<childrenIds.length; i++)
                {
                    if(childrenIds[i].trim().length()==11)
                    {
                        list.add(childrenIds[i]);
                        //System.err.println("child: "+childrenIds[i]);
                    }
                }
            }
        }
    }
    return list;
}
}
