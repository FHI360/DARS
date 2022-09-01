<%-- 
    Document   : MetadataMgt
    Created on : May 27, 2018, 6:06:05 PM
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
        <title>Data Analysis and Reporting Software</title>
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
            <table border="1" style="vertical-align: top; width: 930px; height: 650px; border: 1px green solid; border-collapse: collapse;">
            <tr>
                <td style="width: 200px; "><img src="images/logo.jpg" width="200" height="92" /> </td>

                <td style="background-image: url(images/topbar.jpg);  width: 750px; height: 92px; text-align: center; "><label style="color:white; font-size: 30px; font-weight: bolder;">Data Analysis and Reporting System</label></td>
            </tr>
            <tr>
                <td align="center" style="width: 200px; background-color:#528B8B; height: 500px; vertical-align: top">
                    <table>
                        <tr><td style="height:20px; width: 200px; text-align: center; font-weight: bolder; font-size: larger; background-image: url(images/smallbar.jpg)" > <label style="color:white; font-size: 20px; font-weight: bolder;">Tasks</label></td></tr>
                        <tr><td style="width: 200px; " >
                                <div style="float: left" id="my_menu" class="sdmenu">
                                    <div><jsp:include page="../Navigation/MetadataMgtLink.jsp"/></div>
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

                <td >
                    <table cellpadding="0" cellspacing="0">
                        <%--<img src="images/bgimage.jpg" width="800" height="600" /> --%>
                        <tr><td style="background-color:#FFEBCD;" align="right"><a href="login.do">Log off </a>&nbsp;&nbsp;&nbsp; </td></tr>
                        <tr><td style=" height: 540px; background-color:#FFEBCD;"> 
                            <html:form action="/metadataaction" method="POST">
                                <html:hidden property="actionName" styleId="actionName"/>
                            <center>
                                <table>
                                    <tr><td colspan="2" align="center"><label style="font-size: 14px">DHIS2 meta data download</label></td>
                                    <tr><td colspan="2">&nbsp;</td>
                                    </tr>
                                    <tr><td>Select DHIS</td><td>
                                            <html:select property="dhisId" style="width:480px;" onchange="setActionName('apiurl'); forms[0].submit()">
                                                <logic:present name="DhisInstanceList">
                                                    <html:option value="select"> Select...</html:option>
                                                    <logic:iterate name="DhisInstanceList" id="ds">
                                                        <html:option value="${ds.dhisId}">${ds.dhisName}</html:option>
                                                    </logic:iterate>
                                                </logic:present>
                                            </html:select>
                                        </td>
                                    </tr>
                                    <tr><td>Meta data</td><td>
                                            <html:select property="selectedTask" style="width:480px;" onchange="setActionName('apiurl'); forms[0].submit()" >
                                            <html:option value="cc">Category option combinations</html:option>
                                            <html:option value="de">Data elements</html:option>
                                            <html:option value="dst">Dataset</html:option>
                                            <html:option value="ou">Organization units</html:option>
                                            <html:option value="ougroup">Organization unit groups</html:option>
                                            
                                            <%--<html:option value="loaddv">Load data values</html:option>
                                            <html:option value="downloadReport">Download report</html:option>
                                            <html:option value="loadchart">Load chart</html:option>--%>
                                        </html:select>
                                            </td></tr>
                                    
                                    <tr><td>URL</td><td>
                                            <html:text property="apiUrl" styleId="apiUrl" style="width:475px;"/>
                                                
                                        </td>
                                    </tr>
                                    <tr><td colspan="2" align="center"> </td></tr>
                                    <tr><td colspan="2" align="center"><html:submit value="Pull metadata from DHIS" onclick="setActionName('pulldata')"/></td></tr>
                                </table>
                            </center>
                            </html:form>
                            </td></tr>
                        <tr><td style="width: 700px; font-weight: bold; color: white; text-align: right; background-image: url(images/topbar.jpg)">&nbsp;</td></tr>
                    </table>

                </td>
            </tr>
            
        </table>
        </center>
    </body>
</html>
