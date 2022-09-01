/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.report.controller;

import com.darsdx.dao.DaoUtil;
import com.darsdx.util.ExcelWriter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.write.WritableWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author smomoh
 */
public class ReportDownloadAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String PARAMPAGE="paramPage";

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
        ReportDownloadForm rdf=(ReportDownloadForm)form;
        String requiredAction=rdf.getActionName();
        String reportType=rdf.getReportType();
        
        if(requiredAction==null)
        {
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("viewreport"))
        {
            DaoUtil util=new DaoUtil();//sourceDataList
            List list=util.getSourceDataDaoInstance().getAllSourceData();
            request.setAttribute("sourceDataList", list);
            return mapping.findForward(PARAMPAGE);
        }
        else if(requiredAction.equalsIgnoreCase("downloadInExcel"))
        {
            List list=new ArrayList();
            DaoUtil util=new DaoUtil();
            String fileName="Source_data_";
            
            OutputStream os=response.getOutputStream();
            ExcelWriter ew=new ExcelWriter();
            WritableWorkbook wworkbook=null;
            if(reportType.equalsIgnoreCase("sourcedata"))
            {
                list=util.getSourceDataDaoInstance().getAllSourceData();
                wworkbook=ew.writeSourceDataToExcel(os, list);
            }
            if(reportType.equalsIgnoreCase("intermediateft"))
            {
                fileName="Intermediate_Flat_table_";
                list=util.getIntermediateFlatTableDaoInstance().getAllRecords();
                wworkbook=ew.writeIntermediateFlatTableToExcel(os, list);
            }
            if(reportType.equalsIgnoreCase("flattable"))
            {
                fileName="Final_flat_table_";
                list=util.getFinalFlatTableDaoInstance().getAllRecords();
                wworkbook=ew.writeFinalFlatTableToExcel(os, list);
            }
            if(reportType.equalsIgnoreCase("oumatch"))
            {
                fileName="Ou matching template_";
                list=util.getDxOrganizationUnitMatchDaoInstance().getAllDxOrganizationUnitMatch();
                wworkbook=ew.writeOrgUnitMatchToExcel(os, list,"","");
            }
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
            if(wworkbook !=null)
            {
                wworkbook.write();
                wworkbook.close();
            }

            os.close();
            return null;
        }
        return mapping.findForward(PARAMPAGE);
    }
}
