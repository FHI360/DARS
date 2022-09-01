<%-- 
    Document   : OrganizationUnitExcelUpload
    Created on : Jun 20, 2019, 2:54:26 PM
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
        <title>Organization unit excel upload</title>
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

                <td style="background-color:#FFEBCD">
                <html:form enctype="multipart/form-data" action="/oudataimport" method="post">
                    <html:hidden property="actionName" styleId="actionName"/>
                    <table align="center">
                        <tr> <td align="center" colspan="2"><label style="font-size: 20px; font-weight: bold">Organization unit data upload</label> </td></tr>
                        <tr> <td align="center" height="100" colspan="2"> </td></tr>
                        <tr> <td align="center" colspan="2"><logic:present name="bruplmsg"> ${oumuplmsg} </logic:present> </td></tr>
                        
                        
        <tr>
            <td align="right"> 
                DHIS instance
                </td>
                
                <td> 
                <html:select property="dhisInstance" styleId="dhisInstance" style="width:200px;" > 
                    <logic:present name="ouimportinstanceList">
                        <logic:iterate name="ouimportinstanceList" id="instance">
                            <html:option value="${instance.dhisId}">${instance.dhisName} </html:option>
                        </logic:iterate>
                    </logic:present>
                </html:select>
                </td>
                
        </tr>
        
                        <tr><td height="100" colspan="2">
                              <center>
                                                                 
                                     <br/>
                                    <CENTER>
                                        <DIV style="border: 0px black solid; width: 700px; height: fit-content">
                                    <TABLE>
                                        <TR><TD colspan="3"> <html:errors /></TD></TR>
                                        <TR><TD align="center" colspan="3">Upload Organization unit import from MS Excel file </TD>
                                        <TR><TD align="left">Choose file to upload </TD><TD><html:file property="uploadedFile" /></TD><TD> </TD></TR>
                                        <TR><TD colspan="3">&nbsp;</TD></TR>
                                        <TR><TD colspan="3" align="center"><html:submit value="Submit" onclick="setActionName('import')" /></TD></TR>
                                    </TABLE>
                                    </DIV>
                                    </CENTER>
                                
                        </center>
                              </td>
                        </tr>

                        <tr><td colspan="2" style="width: 600px; font-weight: 800; color: green; text-align: right; vertical-align: bottom;">&nbsp;</td></tr>
                    </table>
                  </html:form>
                </td>
            </tr>

        </table>
        </center>
    </body>
</html>
