/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.controller;

import com.darsdx.business.DatavalueResource;
import com.darsdx.business.DhisInstance;
import com.darsdx.business.OrganizationUnit;
import com.darsdx.dao.DaoUtil;
import com.darsdx.dao.DaoUtility;
import com.darsdx.dao.DatavalueResourceDao;
import com.darsdx.dao.DatavalueResourceDaoImpl;
import com.darsdx.dao.DhisInstanceDao;
import com.darsdx.dao.DhisInstanceDaoImpl;
import com.darsdx.operationsmanagement.DatavalueManager;
import com.darsdx.pojo.UrlResourceManager;
import com.darsdx.util.AppUtility;
import com.darsdx.util.DateManager;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class DatavalueDownloadAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
        DatavalueDownloadForm dvuf=(DatavalueDownloadForm)form;
        DaoUtil util=new DaoUtil();
        AppUtility appUtil=new AppUtility();
        HttpSession session=request.getSession();
        String actionName=dvuf.getActionName();
        String dhisId=dvuf.getDhisId();
        //String datasetId="lyVV9bPLlVy";
        int startYear=dvuf.getStartYear();
        int endYear=dvuf.getEndYear();
        String[] stateCodes=dvuf.getStateCodes();
        String[] selectedMths=dvuf.getSelectedMths();
        String dataSetId=dvuf.getDataSetId();
        String orgUnitGroupId=dvuf.getOrgunitgroup();
        String dvurl=dvuf.getDvurl();
        String startDate=dvuf.getStartDate()+"-01";
        String endDate=dvuf.getEndDate()+"-30";
        List yearList=appUtil.generateYearsTillCurrentDate(2000);
        List monthList=DateManager.generateMonthAndYear(startYear,endYear);
        List ougList=util.getOrganizationUnitGroupDaoInstance().getOrganizationUnitGroups(dhisId);
        if(ougList==null)
        ougList=new ArrayList();
        session.setAttribute("ougList", ougList);
        dvurl=UrlResourceManager.getDataValueDatasetUrl(dhisId, dataSetId, orgUnitGroupId,startDate,endDate);
        System.err.println(dvurl);
        dvuf.setDvurl(dvurl);
        setSessionObjects(session, dhisId);
        
        session.setAttribute("yearList", yearList);
        session.setAttribute("monthList", monthList);
        if(actionName==null)
        {
            dvurl=null;
            dvuf.reset(mapping, request);
            return mapping.findForward("paramPage");
        }
        else if(actionName.equalsIgnoreCase("generateMths"))
        {
            monthList=DateManager.generateMonthAndYear(startYear,endYear);
            session.setAttribute("monthList", monthList);
            return mapping.findForward("paramPage");
        }
        else if(actionName.equalsIgnoreCase("apiurl"))
        {
            dvurl=UrlResourceManager.getDataValueDatasetUrl(dhisId, dataSetId, orgUnitGroupId,startDate,endDate);
            dvuf.setDvurl(dvurl);
            return mapping.findForward("paramPage");
        }
        else if(actionName.equalsIgnoreCase("save"))
        {
            if(startDate !=null && endDate !=null)
            {
                String[] startDateArray=startDate.split("-");
                String[] endDateArray=startDate.split("-");
                if(startDateArray !=null && startDateArray.length>1)
                {
                    DatavalueResource dv=new DatavalueResource();
                    DatavalueResourceDao dvdao=new DatavalueResourceDaoImpl();
                    int startMth=Integer.parseInt(startDateArray[1]);
                    int startYr=Integer.parseInt(startDateArray[0]);
                    int endMth=Integer.parseInt(endDateArray[1]);
                    int endYr=Integer.parseInt(endDateArray[0]);
                    if(dvurl !=null && dvurl.indexOf("&startDate=") !=-1)
                    dvurl=dvurl.substring(0, dvurl.indexOf("&startDate="));
                    dv.setDateCreated(DateManager.getCurrentDateAsDateObject());
                    dv.setDhisId(dhisId);
                    dv.setStartMonth(startMth);
                    dv.setStartYear(startYr);
                    dv.setEndMonth(endMth);
                    dv.setEndYear(endYr);
                    dv.setLastModifiedDate(DateManager.getCurrentDateAsDateObject());
                    dv.setUrl(dvurl);
                    dvdao.saveOrUpdateDatavalueResource(dv);
                }
            }
            return mapping.findForward("paramPage");
        }
        else if(actionName.equalsIgnoreCase("downloadData"))
        {
            //System.err.println("action name is "+actionName);
            UrlResourceManager urm=new UrlResourceManager();
            DhisInstanceDao dsdao=new DhisInstanceDaoImpl();
            DhisInstance ds=dsdao.getDhisInstanceById(dhisId);
            if(ds !=null)
            {
                List list=UrlResourceManager.getUrlResource(dvurl, ds.getUserName(), ds.getPassword());
                DatavalueManager dvManager=new DatavalueManager();
                //System.err.println(list.size()+" records downloaded ");
                String message=dvManager.processDatavalue(list,dataSetId,dhisId);
                System.err.println("message is "+message);
            }
            //dvurl
            /*if((stateCodes !=null && stateCodes.length>0) && (selectedMths !=null && selectedMths.length>0))
            {
                for(int i=0; i<stateCodes.length; i++)
                {
                    System.err.println("stateCodes[i] is "+stateCodes[i]);
                    System.err.println(selectedMths[0]);
                    DhisInstanceDao dsdao=new DhisInstanceDaoImpl();
                    DhisInstance ds=dsdao.getDhisInstanceById(dhisId);
                    if(ds !=null)
                    {
                        OrganizationUnitDao oudao=new OrganizationUnitDaoImpl();
                        String apiUrl=ds.getDhisHomeUrl()+"/"+ds.getApiUrl()+"/"+DHISPropertyManager.getDataValueDatasetQuery()+dataSetId;
                        String orgUnitId=stateCodes[i];
                        OrganizationUnit ou=oudao.getOrganizationUnit(orgUnitId);
                        if(ou !=null)
                        {
                            String orgUnitName=ou.getOrgunitName();
                            String prefix=null;
                            if(orgUnitName !=null && orgUnitName.length()>2)
                            {
                                prefix=orgUnitName.substring(0, 2);
                                System.err.println("prefix is "+prefix);
                            }

                            List oulist=oudao.getOrganizationUnitsByOuLevelAndPrefix(prefix, dhisId, 5);//.getOrganizationUnitsByOuLevel(dhisId,5);
                            if(oulist !=null)
                            {
                                System.err.println("oulist.size() is "+oulist.size());
                                DatavalueManager dvManager=new DatavalueManager();
                                List orgUnitQueryList=getOrgUnitQuery(oulist);
                                List startDateQueryList=getStartDateQuery(selectedMths);
                                List dataValueQueryList=getDataValueQueryList(apiUrl,startDateQueryList, orgUnitQueryList);
                                for(int j=0; j<dataValueQueryList.size(); j++)
                                {
                                    List list=UrlResourceManager.getUrlResource(dataValueQueryList.get(j).toString(), ds.getUserName(), ds.getPassword());
                                    String message=dvManager.processDatavalue(list,dataSetId,dhisId);
                                    System.err.println("message is "+message);
                                    //System.err.println("dataValueQuery at "+i+" is "+dataValueQueryList.get(i).toString());
                                }
                            }
                        }
                   }
                }
            }*/
            
            return mapping.findForward("paramPage");
        }
        else if(actionName.equalsIgnoreCase("convertToImisStructure"))
        {
            DatavalueManager dvm=new DatavalueManager();
            dvm.processImisDatavalue("tZoEMxCkapi");
        }
        dvuf.reset(mapping, request);
        return mapping.findForward("paramPage");
        
    }
    
    private void setSessionObjects(HttpSession session, String dhisId)
    {
        try
        {
            session.removeAttribute("stateList");
            session.removeAttribute("dstList");
            DhisInstanceDao dsdao=new DhisInstanceDaoImpl();
            List list=new ArrayList();
            List dstList=new ArrayList();
            List dsList=dsdao.getAllDhisInstances();
            System.err.println("dhisId is "+dhisId);
            if(dhisId !=null && dhisId.trim().length()>3 && !dhisId.equalsIgnoreCase("select"))
            {
                list=DaoUtility.getOrganizationUnitDaoInstance().getOrganizationUnitsByOuLevel(dhisId,2);
                dstList=DaoUtility.getDataSetDaoInstance().getDataSetsByDhisId(dhisId);
            }
            else
            {
                //System.err.println("dhisId is null or less than 4");
                if(dsList !=null && !dsList.isEmpty())
                {
                    //System.err.println("dsList is not null or empty");
                    DhisInstance ds=(DhisInstance)dsList.get(0);
                    dhisId=ds.getDhisId();
                    //System.err.println("ds.getDhisId() is "+dhisId);
                    list=DaoUtility.getOrganizationUnitDaoInstance().getOrganizationUnitsByOuLevel(dhisId,2);
                    dstList=DaoUtility.getDataSetDaoInstance().getDataSetsByDhisId(dhisId);
                }
            }
            session.setAttribute("stateList", list);
            session.setAttribute("dstList", dstList);
            session.setAttribute("dhisSetupList", dsList);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private List getOrgUnitQuery(List oulist)
    {
        List orgUnitQueryList=new ArrayList();
        if(oulist !=null)
        {
            int count=0;
            UrlResourceManager urm=new UrlResourceManager();
            List list=new ArrayList();
            for(int i=0; i<oulist.size(); i++)
            {
                OrganizationUnit ou=(OrganizationUnit)oulist.get(i);
                list.add(ou.getOrgunitId());
                count++;
                if(count==50 || i==oulist.size()-1)
                {
                   String orgUnitStr=urm.getOrgUnitQueryString(list);
                   orgUnitQueryList.add(orgUnitStr);
                   count=0; 
                   
                }
            }
            
        }
        return orgUnitQueryList;
    }
    private List getStartDateQuery(String[] selectedMths)
    {
        List list=new ArrayList();
        if(selectedMths !=null)
        {
            String startDate=null;
            String endDate=null;
            //String startDateQueryString="&startDate=";
            //String endDateQueryString="&endDate=";
            int mth=0;
            int year=0;
            DateManager dm=new DateManager();
            UrlResourceManager urlm=new UrlResourceManager();
            for(int j=0; j<selectedMths.length; j++)
            {
              //The value of selectedMths is in the form yyyy-mm, when splitted selectedMthArray[1] is dd while selectedMthArray[0] is yyyy
              String[] selectedMthArray=selectedMths[j].split("-");
              if(selectedMthArray !=null && selectedMthArray.length>1)
              {
                  mth=Integer.parseInt(selectedMthArray[1]);
                  year=Integer.parseInt(selectedMthArray[0]);
                  System.err.println("Month:"+mth+", Year:"+year);
                  startDate=dm.getStartDate(mth, year);//.getselectedMths[j]+"-01";
                  endDate=dm.getEndDate(mth, year);
                  list.add(urlm.getPeriodQueryString(startDate,endDate));
                  //list.add(startDateQueryString+startDate+endDateQueryString+endDate); 
              }
            }
        }
        return list;
    }
    private List getDataValueQueryList(String apiUrl,List startDateQueryList, List orgUnitQueryList)
    {
        
        List dataValueQueryList=new ArrayList();
        if(startDateQueryList !=null)
        {
            String startDateQuery=null;
            for(int i=0; i<startDateQueryList.size(); i++)
            {
                startDateQuery=startDateQueryList.get(i).toString();
                for(int j=0; j<orgUnitQueryList.size(); j++)
                {
                    dataValueQueryList.add(apiUrl+startDateQuery+orgUnitQueryList.get(j).toString());
                }
            }
        }
        
        return dataValueQueryList;
    }
        
}
