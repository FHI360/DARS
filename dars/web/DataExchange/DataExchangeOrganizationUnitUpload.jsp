<%-- 
    Document   : DataExchangeOrganizationUnitUpload
    Created on : May 7, 2017, 1:46:39 PM
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
function setBtnName(name)
{
     if(name=="save")
     {
            setActionName(name)
            return true
     }
     else(confirm("Are you sure you want to "+name+" this record?"))
     {
            setActionName(name)
            return true
     }
       return false
}
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
                    <table align="center">
                        <tr><td height="400">
                              <center>
<html:form enctype="multipart/form-data" action="/dxorgunit" method="post">
             <html:hidden property="actionName" styleId="actionName"/>
            <br/>
            <center>
                <div style="width: auto; height: 200px;">
            <table>
                <tr><td colspan="3"> <html:errors /></td></tr>
                <tr><td align="center" colspan="3">Upload file </td>
                <tr><td >DHIS Instance </td>
                    <td > 
                        <html:select property="softwareInstance">
                            <html:option value=""> </html:option>
                            <logic:present name="instanceList">
                                <logic:iterate name="instanceList" id="ds">
                                    <html:option value="${ds.dhisId}">${ds.dhisName}</html:option>
                                </logic:iterate>
                            </logic:present>
                        </html:select>
                    </td>
                    <td >Meta data type </td><td>
                        <html:select property="metadataType">
                            <html:option value="orgunit">Organization unit</html:option>
                            <html:option value="dataelement">Data element</html:option>
                            <html:option value="catcombo">Category combination</html:option>
                        </html:select></td>
                </tr>
                <tr><td align="left">Choose file to upload </td><td><html:file property="upload" /></td><td> </td></tr>
                <tr><td colspan="3">&nbsp;</td></tr>
                <tr><td colspan="3" align="center"><html:submit value="Submit" style="width:75px; height:20px; margin-left:40px;" onclick="setActionName('import')" /></td></tr>
            </table>
                </div>
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
