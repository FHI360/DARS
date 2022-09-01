<%-- 
    Document   : TaskManager
    Created on : Apr 6, 2019, 1:42:28 PM
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
        <title>Task setup</title>
        <link href="images/sdmenu/sdmenu.css" rel="stylesheet" type="text/css" />
        <link type="text/css" href="css/ui-darkness/jquery-ui-1.8.custom.css" rel="Stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script language="javascript">
$(function() {
    $("#startDate").datepicker();
    $("#endDate").datepicker();
    });
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
                                    <div><jsp:include page="../Navigation/TaskManagementLink.jsp"/></div>
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
                                  <html:form action="/taskmanager" method="POST">
                                <html:hidden property="actionName" styleId="actionName" />
                                <html:hidden property="hiddenTaskId" styleId="hiddenTaskId" />
                                
                            <center>
                                <table>
                                    <tr><td colspan="4" align="center">Task setup</td></tr>
                                    <tr><td colspan="4" align="center"><html:errors/></td></tr>
                                    <tr><td>Name</td><td colspan="3"><html:text property="name" styleId="name" style="width:250px;"/> </td></tr>
                                    <tr>
                                        <td><label class="labels">Source </label></td>
                                        <td>
                                            <html:select property="dataSource" styleId="dataSource" style="width:250px">
                                                <logic:present name="connectionsfortasks">
                                                    <logic:iterate name="connectionsfortasks" id="connection">
                                                        <html:option value="${connection.connectionId}">${connection.connectionName}</html:option>
                                                    </logic:iterate>
                                                </logic:present>
                                            </html:select>
                                        </td>  
                                        <td><label class="labels">Sink </label></td>
                                        <td >
                                            <html:select property="dataSink" styleId="dataSink" style="width:250px">
                                                <logic:present name="connectionsfortasks">
                                                    <logic:iterate name="connectionsfortasks" id="connection">
                                                <html:option value="${connection.connectionId}">${connection.connectionName}</html:option>
                                                
                                                </logic:iterate>
                                                </logic:present>
                                            </html:select>
                                        </td>
                                    </tr>
                                    
                                    <tr>
                                        <td><label class="labels">Mapping</label></td>
                                        <td>
                                            <html:select property="deCatComboMapping" styleId="deCatComboMapping" style="width:250px">
                                                <html:option value="notrequired">Mapping not required</html:option>
                                                <logic:present name="deCatComboMappingList">
                                                    <logic:iterate name="deCatComboMappingList" id="decat">
                                                        <html:option value="${decat.recordId}">${decat.producerInstanceName} ${decat.consumerInstanceName}</html:option>
                                                    </logic:iterate>
                                                </logic:present>
                                            </html:select>
                                        </td> 
                                        <td>&nbsp;</td>
                                        <td>
                                            <html:select property="orgUnitMapping" styleId="orgUnitMapping" style="width:250px">
                                                <html:option value="notrequired">Mapping not required</html:option>
                                                <logic:present name="orgUnitMappingList">
                                                    <logic:iterate name="orgUnitMappingList" id="oumap">
                                                        <html:option value="${oumap.matchDescription}">${oumap.producerOrgUnitName} ${oumap.consumerOrgUnitName}</html:option>
                                                    </logic:iterate>
                                                </logic:present>
                                            </html:select>
                                        </td> 
                                    </tr>
                                    <tr>
                                        <td><label class="labels">Frequency</label></td>
                                        <td>
                                            <html:select property="frequency" styleId="frequency" style="width:250px">
                                                <html:option value="daily">Daily</html:option>
                                                <html:option value="weekly">Weekly</html:option>
                                                <html:option value="monthly">Monthly</html:option>
                                                <html:option value="manual">Manual execution</html:option>
                                            </html:select>
                                        </td> 
                                        <td>Start date</td>
                                        <td>
                                            <html:text property="startDate" styleId="startDate" readonly="true"/>
                                            End date&nbsp;<html:text property="endDate" styleId="endDate" readonly="true"/>
                                        </td> 
                                    </tr>
                                                                        
                                    <tr><td colspan="4" align="center"><html:submit value="Save" onclick="setActionName('save')" disabled="${dbcSaveDisabled}"/>
                                            <html:submit value="Modify" onclick="return confirmAction('modify')" disabled="${dbcModifyDisabled}"/></td></tr>
                                    
                                    <tr>
                                          <td align="center" colspan="4">
                                              <logic:present name="dataTransferTaskList">
                                                  <div align="center" style="width:auto; max-height:250px; overflow:scroll">
                                                      <table width="650" border="1" bordercolor="#D7E5F2" style="background-color: white; border-collapse: collapse" class="regsitertable">
                                                  <tr style="background-color: lightgray"><td>ID </td><td>name </td><td>Source </td><td>Sink </td><td>DE/Category mapping</td>
                                                          <td>Org unit mapping </td><td>Frequency </td><td>Start date </td><td>End date </td>
                                                      <td> </td><td> </td><td> </td></tr>
                                                  <logic:iterate name="dataTransferTaskList" id="dt">
                                                      <tr><td>${dt.uid}</td><td>${dt.name}</td><td>${dt.dataSourceName}</td><td>${dt.dataSinkName} </td><td> ${dt.dataElementMapping}</td>
                                                          <td> ${dt.dataElementMapping}</td><td> ${dt.frequency}</td><td> ${dt.startDate}</td><td> ${dt.endDate}</td><td><a href="taskmanager.do?id=${dt.uid}&p=ed">edit</a> </td>
                                                      <td><a href="taskmanager.do?id=${dt.uid}&&p=de" onclick="return confirmAction('delete')">delete</a> </td>
                                                      <td><a href="taskexecution.do?id=${dt.uid}&p=exec">Execute</a> </td></tr>
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