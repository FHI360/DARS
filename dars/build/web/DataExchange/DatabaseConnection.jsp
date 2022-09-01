<%-- 
    Document   : DatabaseConnection
    Created on : Apr 4, 2019, 8:00:18 PM
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
        <title>Database connection setup</title>
        <link href="images/sdmenu/sdmenu.css" rel="stylesheet" type="text/css" />
<script language="javascript">
function confirmAction(name)
{
     if(name=="save")
     {
            setActionName(name)
            return true
     }
     else if(confirm("Are you sure you want to "+name+" this record?"))
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
                                  <html:form action="/dbconnection" method="POST">
                                <html:hidden property="actionName" styleId="actionName" />
                                <html:hidden property="connectionId" styleId="connectionId" />
                                <html:hidden property="hiddenPassword" styleId="hiddenPassword" />
                            <center>
                                <table>
                                    <tr><td colspan="4" align="center">Connection parameters</td></tr>
                                    <tr><td colspan="4" align="center"><html:errors/></td></tr>
                                    <tr>
                                        <td><label class="labels">Database type </label></td>
                                        <td colspan="3">
                                            <html:select property="databaseType" styleId="databaseType" style="width:500px">
                                                <html:option value="mysql">MySQL</html:option>
                                                <html:option value="javadb">Java DB/Apache derby</html:option>
                                                <html:option value="h2">H2 database</html:option>
                                                <html:option value="mssql">MS SQL</html:option>
                                                <html:option value="psql">Postgres sql</html:option>
                                            </html:select>
                                        </td>                                         
                                    </tr>
                                    <tr>
                                        <td><label class="labels">Connection name </label></td><td colspan="3"><html:text property="connectionName" styleId="connectionName" style="width:500px"/> </td>                                         
                                    </tr>
                                    <tr>
                                        <td ><label class="labels">Host URL </label></td><td colspan="3"><html:text property="dbUrl" styleId="dbUrl" style="width:500px"/> </td>                                         
                                    </tr>
                                    <tr>
                                        <td><label class="labels">Database name </label></td><td colspan="3"><html:text property="dbName" styleId="dbName" style="width:500px"/> </td>                                       
                                    </tr>
                                    <tr>
                                        <td><label class="labels">Port number </label></td><td colspan="3"><html:text property="portNumber" styleId="portNumber" style="width:500px"/> </td>                                       
                                    </tr>
                                    <tr>
                                        <td><label class="labels">User name </label></td><td width="100"><html:text property="userName" styleId="userName" /> </td>
                                        
                                        <td>Password </td><td><html:password property="password" styleId="password" /></td>
                                    </tr>
                                    
                                    <tr><td colspan="4" align="center"><html:submit value="Save" onclick="setActionName('save')" disabled="${dbcSaveDisabled}"/>
                                            <html:submit value="Modify" onclick="return confirmAction('modify')" disabled="${dbcModifyDisabled}"/></td></tr>
                                    
                                    <tr>
                                          <td align="center" colspan="4">
                                              <logic:present name="dbConnections">
                                                  <div align="center" style="width:auto; max-height:250px; overflow:scroll">
                                                      <table width="650" border="1" bordercolor="#D7E5F2" style="background-color: white; border-collapse: collapse" class="regsitertable">
                                                  <tr style="background-color: lightgray"><td>Connection name </td><td>DB name </td><td>Connection URL </td><td>Port number</td>
                                                          <td> </td>
                                                      <td> </td></tr>
                                                  <logic:iterate name="dbConnections" id="dbc">
                                                      <tr><td>${dbc.connectionName} </td><td>${dbc.databaseName} </td><td>${dbc.databaseURL} </td><td>${dbc.portNumber} </td>
                                                          <td><a href="dbconnection.do?id=${dbc.id}&&p=ed">edit</a> </td>
                                                      <td><a href="dbconnection.do?id=${dbc.id}&&p=de" onclick="return confirmAction('delete')">delete</a> </td></tr>
                                                  </logic:iterate>

                                              </table>
                                            </div>
                                            </logic:present>
                                          </td>
                                    </tr>
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
