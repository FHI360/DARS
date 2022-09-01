<%-- 
    Document   : SourceDataUpload
    Created on : May 26, 2017, 9:27:15 PM
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
        <title>Source data upload</title>
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

                <td style="background-image: url(images/topbar.jpg); width: 750px; height: 92px; text-align: center;"><label style="color:white; font-size: 30px; font-weight: bolder;">Sample Tracker</label> </td>
            </tr>
            <tr>
                <td align="center" style="width: 200px; background-color:#528B8B; height: 500px; vertical-align: top">
                    <table>
                        <tr><td style="height:20px; width: 200px; text-align: center; font-weight: bolder; font-size: larger; background-image: url(images/smallbar.jpg)" > <label style="color:white; font-size: 20px; font-weight: bolder;">Tasks</label></td></tr>
                        <tr><td style="width: 200px; " >
                                <div style="float: left" id="my_menu" class="sdmenu">
                                    <div><jsp:include page="../Navigation/DataUploadLink.jsp"/></div>
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
                        <tr> <td align="center"><logic:present name="dtumsg"> ${dtumsg} </logic:present> </td></tr>
                        <tr><td height="400">
                              <center>
                              <html:form enctype="multipart/form-data" action="/dataupload" method="post">
                                    
                                    <br/><br/><br/>
                                    <CENTER>
                                        <DIV style="border: 0px black solid; width: 700px; height: fit-content">
                                    <TABLE>
                                        <TR><TD colspan="3"> <html:errors /></TD></TR>
                                        <TR><TD align="center" colspan="3">Upload data from MS Excel file </TD>
                                        <TR><TD align="left">Type </TD>
                                            <TD colspan="2"> 
                                                <html:select property="actionName" styleId="actionName" styleClass="textField" style="width:250px;"> 
                                                    <html:option value="select">select... </html:option>
                                                    <html:option value="sourceDataExcelUpload">Source data </html:option>
                                                    <html:option value="IMISSourceExcelUpload">IMIS Source data </html:option>
                                                    <html:option value="flatTableExcelUpload">Flat table </html:option>
                                                </html:select>
                                            </TD>
                                        </TR>
                                        <TR><TD align="left">DHIS instance </TD>
                                            <TD colspan="2"> 
                                                <html:select property="dhisInstance" styleId="dhisInstance" styleClass="textField" style="width:250px;"> 
                                                    <logic:present name="DhisInstanceListForDataUpload">
                                                        <logic:iterate name="DhisInstanceListForDataUpload" id="ds">
                                                            <html:option value="${ds.dhisId}">${ds.dhisName}</html:option>
                                                        </logic:iterate>
                                                    </logic:present>
                                                </html:select>
                                            </TD>
                                        </TR>
                                        <TR><TD align="left">Choose file to upload </TD><TD><html:file property="uploadedFile" /></TD><TD> </TD></TR>
                                        <TR><TD colspan="3">&nbsp;</TD></TR>
                                        <TR><TD colspan="3" align="center"><html:submit value="Submit" /></TD></TR>
                                    </TABLE>
                                    </DIV>
                                    </CENTER>
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
