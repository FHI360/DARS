/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.pojo;

import com.darsdx.util.AppUtility;
import com.darsdx.business.DxCategoryCombination;
import com.darsdx.business.DxDataElement;
import com.darsdx.business.DxOrganizationUnit;
import com.darsdx.dao.DxCategoryCombinationDao;
import com.darsdx.dao.DxCategoryCombinationDaoImpl;
import com.darsdx.dao.DxDataElementDao;
import com.darsdx.dao.DxDataElementDaoImpl;
import com.darsdx.dao.DxOrganizationUnitDao;
import com.darsdx.dao.DxOrganizationUnitDaoImpl;
import java.util.List;
/**
 *
 * @author smomoh
 */
public class XmlExtractor 
{
   public void readOrgUnitFromXml(String xmlFilePath,String dhisInstance) 
  {
      AppUtility appUtil=new AppUtility();
      List list=AppUtility.readFiles(xmlFilePath);
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
          DxOrganizationUnitDao oudao=new DxOrganizationUnitDaoImpl();
          DxOrganizationUnit ou=new DxOrganizationUnit();
          ou.setInstanceName(dhisInstance);
          ou.setLastModifiedDate(appUtil.getCurrentDate());
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
                              ou.setLevel(level);
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
                          ou.setLastModifiedDate(appUtil.getCurrentDate());
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
                                  ou.setLevel(level);
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
  
  public void readDataElementsFromXml(String xmlFilePath,String dhisInstance) 
  {
      AppUtility appUtil=new AppUtility();
      List list=AppUtility.readFiles(xmlFilePath);
      if(list !=null)
      {
          String dataElementName=null;
          String dataElementId=null;
          String lastModifiedDate=appUtil.getCurrentDate();
          DxDataElementDao dedao=new DxDataElementDaoImpl();
          DxDataElement de=new DxDataElement();
          de.setDhisInstance(dhisInstance);
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
                          de=new DxDataElement();
                          de.setDhisInstance(dhisInstance);
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
                                      dedao.saveOrUpdateDataElement(de);
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
                                  dedao.saveOrUpdateDataElement(de);
                                  System.err.println("dataElement id is "+de.getDataElementId()+", name "+de.getDataElementName());
                             }
                             
                             //i++;
                          }
                          //counter++;
                                              
                        }
                      /*str=list.get(i).toString();
                      if(str !=null && str.indexOf("<dataElement") !=-1 && str.indexOf("id=") !=-1)
                      {
                          dataElementName=null;
                          dataElementId=null;
                          String strIdPart=str.substring(str.indexOf("id=")+4, str.length()-2);
                          id=strIdPart;
                          counter++;
                                                                         
                        }
                      if(str !=null && str.indexOf("<displayName>") !=-1)
                      {
                          counter++;
                         String strNamePart=str.replace("<displayName>", "");
                         displayName=strNamePart.replace("</displayName>", "");
                         
                          //System.err.println("strIdPart id is "+displayName); 
                      }
                      if(counter >1)
                      {
                          counter=0;
                          if((id !=null && appUtil.isString(id)) && (displayName !=null && appUtil.isString(displayName)))
                          {
                              dataElementName=displayName.trim();
                              dataElementId=id.trim();
                              de.setDataElementName(dataElementName);
                              de.setDataElementId(dataElementId);
                              dedao.saveOrUpdateDataElement(de);
                          }
                          System.err.println("dataElement id is "+de.getDataElementId()+", name "+de.getDataElementName());
                      }*/
                    }
              }
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
      }
}
public void readCategoryCombinationsFromXml(String xmlFilePath,String dhisInstance) 
  {
      AppUtility appUtil=new AppUtility();
      List list=AppUtility.readFiles(xmlFilePath);
      if(list !=null)
      {
          String catComboName=null;
          String catComboId=null;
          String lastModifiedDate=appUtil.getCurrentDate();
          int savedCount=0;
          DxCategoryCombinationDao ccdao=new DxCategoryCombinationDaoImpl();
          DxCategoryCombination ccombo=new DxCategoryCombination();
          ccombo.setDhisInstance(dhisInstance);
          ccombo.setLastModifiedDate(lastModifiedDate);
          //System.out.println("Number of organization unit is "+list.size());
          String str=null;
          try
          {
              int loopCount=0;
              for(int i=0; i<list.size(); i++)
              {
                  str=list.get(i).toString();
                  if(str !=null && str.indexOf("<categoryOptionCombo") !=-1)
                  {
                      catComboName=null;
                      catComboId=null;
                      
                      String[] strArray=str.split("<categoryOptionCombo");
                      for(int j=0; j<strArray.length; j++)
                      {
                          loopCount++;
                          ccombo=new DxCategoryCombination();
                          ccombo.setDhisInstance(dhisInstance);
                          ccombo.setLastModifiedDate(lastModifiedDate);
                          //System.out.println("strArray at "+j+" is "+strArray[j]);
                          if(strArray[j] !=null)
                          {
                              try
                              {
                                  if(loopCount > 14 && savedCount==0)
                                      break;
                                  //this contains the data element name and id
                                  if(strArray[j].indexOf("name=") !=-1 && strArray[j].indexOf("lastUpdated=") !=-1)
                                  {
                                      catComboName=strArray[j].substring(strArray[j].indexOf("name=")+6, strArray[j].indexOf("lastUpdated=")-2);
                                      catComboId=strArray[j].substring(strArray[j].indexOf("id=")+4, strArray[j].indexOf("created=")-2);
                                      
                                      catComboName=catComboName.trim();
                                      catComboId=catComboId.trim();
                                      catComboId=catComboId.replace("'", "");
                                      catComboId=catComboId.replace("\"", "");
                                      if(catComboId.length()>11)
                                      catComboId=catComboId.substring(0, 10);
                                      
                                      ccombo.setCatComboName(catComboName);
                                      ccombo.setCatComboId(catComboId);
                                      ccdao.saveOrUpdateCategoryCombination(ccombo);
                                      savedCount++;
                                      //System.out.println("catComboName at "+j+" is "+ccombo.getCatComboName());
                                      System.out.println("Category combo id at "+j+" is "+ccombo.getCatComboId());
                                      
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
                  int counter=0;
                  for(int i=0; i<list.size(); i++)
                  {
                      str=list.get(i).toString();
                      if(str !=null && str.indexOf("<categoryOptionCombo") !=-1 && str.indexOf("id=") !=-1)
                      {
                          catComboName=null;
                          catComboId=null;
                          String strIdPart=str.substring(str.indexOf("id=")+4, str.length()-2);
                          id=strIdPart;
                          str=list.get(i+1).toString();
                          if(str !=null && str.indexOf("<displayName>") !=-1)
                          {
                              String strNamePart="";
                              if(str.indexOf("</displayName>") ==-1)
                              {
                                  //the cat combo name is on a seperate line
                                  displayName=list.get(i+2).toString();
                              }
                              else
                              {
                                 //the cat combo name is on the same line as the display tags
                                 strNamePart=str.replace("<displayName>", "");
                                 displayName=strNamePart.replace("</displayName>", "");
                              }
                             if((id !=null && appUtil.isString(id)) && (displayName !=null && appUtil.isString(displayName)))
                             {
                                  catComboName=displayName.trim();
                                  catComboId=id.trim();
                                  ccombo.setCatComboName(catComboName);
                                  ccombo.setCatComboId(catComboId);
                                  ccdao.saveOrUpdateCategoryCombination(ccombo);
                             }
                             System.err.println("ccombo id is "+ccombo.getCatComboId()+", name "+ccombo.getCatComboName());
                             i++;
                          }
                          counter++;
                                              
                        }
                      /*else if(str !=null && str.indexOf("<displayName>") !=-1)
                      {
                          counter++;
                         String strNamePart=str.replace("<displayName>", "");
                         displayName=strNamePart.replace("</displayName>", "");
                         
                      }
                      if(counter >1)
                      {
                          counter=0;
                          if((id !=null && appUtil.isString(id)) && (displayName !=null && appUtil.isString(displayName)))
                          {
                              catComboName=displayName.trim();
                              catComboId=id.trim();
                              ccombo.setCatComboName(catComboName);
                              ccombo.setCatComboId(catComboId);
                              ccdao.saveOrUpdateCategoryCombination(ccombo);
                          }
                          System.err.println("ccombo id is "+ccombo.getCatComboId()+", name "+ccombo.getCatComboName());
                      }*/
                    }
              }
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
      }
}

    /*try {
        //xmlFilePath="C:\\test\\metaData.xml";
	File fXmlFile = new File(xmlFilePath);
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);

	//optional, but recommended
	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	doc.getDocumentElement().normalize();

	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

	NodeList nList = doc.getElementsByTagName("organisationUnit");

	System.out.println("----------------------------");

	for (int temp = 0; temp < nList.getLength(); temp++) {

		Node nNode = nList.item(temp);
                NodeList childList=nNode.getChildNodes();
		System.out.println("\nCurrent Element :" + nNode.getNodeName());

		if (nNode.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element eElement = (Element) nNode;

			System.out.println("Name : " + eElement.getAttribute("name"));
			System.out.println("Created : " + eElement.getAttribute("created"));
                        System.out.println("Level : " + eElement.getAttribute("level"));
                        System.out.println("Id : " + eElement.getAttribute("id"));
                        
                        //NodeList pList = doc.getElementsByTagName("parent");
                        for(int i=0; i<childList.getLength(); i++)
                        {
                           Node pNode = childList.item(i); 
                           if (pNode.getNodeType() == Node.ELEMENT_NODE) 
                            {
                                if(pNode.getNodeName() !=null && pNode.getNodeName().equalsIgnoreCase("parent"))
                                {
                                    Element pElement = (Element) pNode;
                                    System.out.println("Parent Name : " + pElement.getAttribute("name"));
                                    System.out.println("Parent Created : " + pElement.getAttribute("created"));                                  
                                    System.out.println("Parent Id : " + pElement.getAttribute("id"));
                                }
                            }
                        }
			
		}
	}
    } catch (Exception e) {
	e.printStackTrace();
    }
  
public void readDataElementFromXml(String xmlFilePath,String dhisInstance) {

    try {
        File fXmlFile = new File(xmlFilePath);
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);

	
	doc.getDocumentElement().normalize();

	
	NodeList nList = doc.getElementsByTagName("dataElement");
        int count=0;
	System.out.println("----------------------------");

	for (int temp = 0; temp < nList.getLength(); temp++) 
        {
		Node nNode = nList.item(temp);
                
		System.out.println("\nCurrent Element :" + nNode.getNodeName());

		if (nNode.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element eElement = (Element) nNode;
			System.out.println("Name : " + eElement.getAttribute("name"));
			System.out.println("Created : " + eElement.getAttribute("created"));
                        System.out.println("Id : " + eElement.getAttribute("id"));	
		}
                count++;
	}
        System.out.println(count+" Data elements imported");
    } 
    catch (Exception e) 
    {
	e.printStackTrace();
    }
  }
public void readCategoryComboFromXml(String xmlFilePath,String dhisInstance) {

    try {
        File fXmlFile = new File(xmlFilePath);
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        //File file = new File("c:\\file.xml");
        InputStream inputStream= new FileInputStream(fXmlFile);
        //dBuilder.s
	Document doc = dBuilder.parse(inputStream);
        
	
	doc.getDocumentElement().normalize();

	
	NodeList nList = doc.getElementsByTagName("categoryOptionCombo");
        int count=0;
	System.out.println("----------------------------");

	for (int temp = 0; temp < nList.getLength(); temp++) 
        {
		Node nNode = nList.item(temp);
                
		System.out.println("\nCurrent Element :" + nNode.getNodeName());

		if (nNode.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element eElement = (Element) nNode;
			System.out.println("Name : " + eElement.getAttribute("name"));
			System.out.println("lastUpdated : " + eElement.getAttribute("lastUpdated"));
                        System.out.println("Id : " + eElement.getAttribute("id"));	
		}
                count++;
	}
        System.out.println(count+" categoryOptionCombos imported");
    } 
    catch (Exception e) 
    {
	e.printStackTrace();
    }
  }*/
}
