<%-- 
    Document   : DataProcessingHomePage
    Created on : May 28, 2017, 10:32:47 AM
    Author     : smomoh
--%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data processing</title>
        <link href="images/sdmenu/sdmenu.css" rel="stylesheet" type="text/css" />
<script language="javascript">
function setActionName(val)
{
    document.getElementById("actionName").value=val
}
</script>
    </head>
    <body>
        <center>
            <table border="1" style="vertical-align: top; width: 950px; height: 650px; border: 1px green solid; border-collapse: collapse;">
            <tr>
                <td style="width: 200px; "><img src="images/logo.jpg" width="200" height="92" /> </td>

                <td style="background-image: url(images/topbar.jpg); width: 750px; height: 92px; text-align: center;"><label style="color:white; font-size: 30px; font-weight: bolder;"> </label> </td>
            </tr>
            <tr>
                <td align="center" style="width: 200px; background-color:#528B8B; height: 500px; vertical-align: top">
                    <table>
                        <tr><td style="height:20px; width: 200px; text-align: center; font-weight: bolder; font-size: larger; background-image: url(images/smallbar.jpg)" > <label style="color:white; font-size: 20px; font-weight: bolder;">Tasks</label></td></tr>
                        <tr><td style="width: 200px; " >
                                <div style="float: left" id="my_menu" class="sdmenu">
                                    <div><jsp:include page="../Navigation/DataTransformationLink.jsp"/></div>
                                </div>
                            </td>
                        </tr>
                        <tr><td style="height:20px; width: 200px; text-align: center; font-weight: bolder; font-size: larger; background-image: url(images/smallbar.jpg)" > <label style="color:white; font-size: 20px; font-weight: bolder;">Reports</label></td></tr>
                        <tr><td style="width: 200px; " >
                                <div style="float: left" id="my_menu" class="sdmenu">
                                    <div><jsp:include page="../Navigation/ReportMenu.jsp"/></div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </td>

                <td style="background-color:#FFEBCD">
                    <table align="center">
                        <tr><td height="400">
                              <center>
                              <html:form action="/dataprocessor" method="POST">
                                  <html:hidden property="actionName" styleId="actionName"/>
            <center>
        <table>
            <tr> <td> </td><td colspan="3" align="center"><logic:present name="dbrmsg"> ${dbrmsg} </logic:present> </td></tr>
            <tr> <td colspan="4" style="height: 40px;"><html:errors/> </td></tr>
            <tr> <td colspan="4" style="height: 40px;">Select tasks </td></tr>
            <%--<tr><td>Dhis Instance</td><td colspan="3">
                    <html:select property="dhisInstance" styleId="dhisInstance" >
                        <html:optionsCollection property="instanceList" value="id" label="instanceName" />
                    </html:select>
                    </td></tr>--%>
            <tr><td>Business rule</td><td colspan="3"><html:select property="brdesc" styleId="brdesc"> 
                        <logic:present name="ruleDescriptionList">
                            <logic:iterate name="ruleDescriptionList" id="md">
                                <html:option value="${md.producerInstance.dhisId}-${md.consumerInstance.dhisId}">${md.producerInstance.dhisName}-${md.consumerInstance.dhisName}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select></td></tr>
            <tr><td colspan="4"><html:checkbox property="businessRuleIndicator" value="applyBusinessRules" />Apply business rules to data</td></tr>
            <tr><td colspan="4"><html:checkbox property="uniqueRecordIndicator" value="createUniqueRecords" />Aggregate records</td></tr>
            <tr><td colspan="4"><html:checkbox property="metadataIdAssignment" value="metadataIdAssignment" />Assign meta data Ids</td></tr>
            <tr><td colspan="4"><html:checkbox property="dhis2ExportIndicator" value="createDhisExportFiles" />Create DHIS2 export</td></tr>
            <tr><td colspan="4"><html:checkbox property="dhisDataUpload" value="uploadToDhis" />Load data to DHIS</td></tr>
            
            <tr><td colspan="4"><html:checkbox property="deleteRecord" value="deleteAll" />Delete all existing records</td></tr>
            <tr><td colspan="4"><html:submit value="Submit" onclick="setActionName('process')"/></td></tr>
        </table>
            </center>
        </html:form>
                        </center>
                              </td></tr>

                        <tr><td style="width: 600px; font-weight: 800; color: green; text-align: right; vertical-align: bottom;">&nbsp;</td></tr>
                    </table>

                </td>
            </tr>

        </table>
        </center>
    </body>
</html>