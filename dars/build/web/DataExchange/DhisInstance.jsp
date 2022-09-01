<%-- 
    Document   : DatimFormPage
    Created on : Apr 21, 2016, 1:58:37 PM
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
        <title>DHIS2 Setup</title>
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
                                    <div><jsp:include page="../Navigation/ConnectionsLink.jsp"/></div>
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
                                  <html:form action="/dhisinstance" method="POST">
                                <html:hidden property="actionName" styleId="actionName" />
                                <html:hidden property="dhisId" styleId="dhisId" />
                                <html:hidden property="hiddenPassword" styleId="hiddenPassword" />
                            <center>
                                <table>
                                    <tr><td colspan="4" align="center">API connection setup</td></tr>
                                    <tr><td colspan="4" align="center" style="color: red; font-weight: bold; font-weight: 14px"><html:errors/></td></tr>
                                    <tr>
                                        <td><label class="labels">Connection name </label></td><td colspan="3"><html:text property="dhisName" styleId="dhisName" style="width:500px"/> </td>                                         
                                    </tr>
                                    <tr>
                                        <td ><label class="labels">Home URL </label></td><td colspan="3"><html:text property="dhisHomeUrl" styleId="dhisHomeUrl" style="width:500px"/> </td>                                         
                                    </tr>
                                    <tr>
                                        <td><label class="labels">User name </label></td><td width="100"><html:text property="userName" styleId="userName" /> </td>
                                        
                                        <td>Password </td><td><html:password property="password" styleId="password" /></td>
                                    </tr>
                                    <tr>
                                        <td><label class="labels">API URL </label></td><td colspan="3"><html:text property="apiUrl" styleId="apiUrl" style="width:500px"/> </td>                                       
                                    </tr>
                                    <tr>
                                        <td><label class="labels">Connection type </label></td>
                                        <td colspan="3"><html:select property="connectionType" styleId="apiUrl" style="width:500px"> 
                                                <html:option value="dhis">DHIS connection</html:option>
                                                <html:option value="lamis">LAMIS connection</html:option>
                                                <html:option value="nondhis">Other non DHIS connection</html:option>
                                                        </html:select> 
                                        </td>                                       
                                    </tr>
                                    <tr><td colspan="4" align="center"><html:submit value="Save" onclick="setActionName('save')" disabled="${dsSaveDisabled}"/>
                                            <html:submit value="Modify" onclick="return setBtnName('modify')" disabled="${dsModifyDisabled}"/></td></tr>
                                    
                                    <tr>
                                          <td align="center" colspan="4">
                                              <logic:present name="DhisInstanceList">
                                                  <div align="center" style="width:auto; max-height:250px; overflow:scroll">
                                                      <table width="650" border="1" bordercolor="#D7E5F2" style="background-color: white; border-collapse: collapse" class="regsitertable">
                                                  <tr style="background-color: lightgray"><td>DHIS name </td><td>Home page URL </td><td>API URL </td><td>User name</td>
                                                          <td> </td>
                                                      <td> </td></tr>
                                                  <logic:iterate name="DhisInstanceList" id="ds">
                                                      <tr><td>${ds.dhisName} </td><td>${ds.dhisHomeUrl} </td><td>${ds.apiUrl} </td><td>${ds.userName} </td>
                                                          <td><a href="dhisinstance.do?id=${ds.dhisId}&&p=ed">edit</a> </td>
                                                      <td><a href="dhisinstance.do?id=${ds.dhisId}&&p=de" onclick="return confirmAction('delete')">delete</a> </td></tr>
                                                  </logic:iterate>

                                              </table>
                                            </div>
                                            </logic:present>
                                          </td>
                                    </tr>
                                </table>
                            </center>
                            </html:form>
<%--<html:form action="/dhisinstance">
    <html:hidden property="actionName" styleId="actionName" />
    <html:hidden property="hiddenId" styleId="hiddenId" />
    <br/><br/>
    <html:errors />
<center>
    <div style=" width: 600px; height:450px; border: 1px black solid; alignment-adjust: central">
    <table align="center" >
        <tr><td colspan="4" align="center"><label class="labels" style="margin-left: 30px; font-size: 14px;">DHIS instance setup  </label></td></tr>
        <tr><td colspan="4" >&nbsp; </td></tr>

        <tr><td><label class="labels">DHIS instance name</label></td><td colspan="3"> <html:text property="instanceName" styleId="instanceName" styleClass="textField" style="margin-left: 10px; width:430px;" readonly="false"/> </td></tr>
        <tr><td><label class="labels">Home page URL</label></td><td colspan="3"> <html:text property="url" styleId="url" styleClass="textField" style="margin-left: 10px; width:430px;" readonly="false"/> </td></tr>
    <logic:present name="availableInstanceList">       
    <tr><td> Available instances </td>
        <td colspan="3">
            
            <html:select property="availableInstance" styleId="availableInstance" style="height: 150px; width: 430px; margin-left: 10px; font-size:9pt;" multiple="true" ondblclick="setActionName('details');forms[0].submit()">
                <logic:iterate id="availableInstance" name="availableInstanceList">
                <html:option value="${availableInstance.id}">${availableInstance.instanceName}</html:option>
                </logic:iterate></html:select>
            
        </td>
    </tr>
    </logic:present>
    <tr><td colspan="4" align="center" style="margin-left: 350px;">
            <center>
<fieldset style="width:250px;">
    <html:submit style="width:75px;height:30px;" value="Save" styleId="save" disabled="${dhisInstanceSaveDisabled}" onclick="return setBtnName('save')"/>
    <html:submit style="width:75px;height:30px;" value="Modify..." styleId="modify" disabled="${dhisInstanceModifyDisabled}" onclick="return setBtnName('modify')" />
    <html:submit style="width:75px;height:30px;" value="Delete..." styleId="delete" disabled="${dhisInstanceModifyDisabled}" onclick="return setBtnName('delete')" />

</fieldset>
            </center>
 </td></tr>
</table>
    </div>
</center>
</html:form>--%>
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
