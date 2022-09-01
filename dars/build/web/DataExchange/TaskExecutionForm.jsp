<%-- 
    Document   : TaskExecutionForm
    Created on : May 4, 2019, 10:18:52 PM
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
        <title>Task execution manager</title>
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
                                  <html:form action="/taskexecution" method="POST">
                                <html:hidden property="actionName" styleId="actionName" />
                                <html:hidden property="hiddenTaskId" styleId="hiddenTaskId" />
                                
                            <center>
                                <table>
                                    <tr><td colspan="4" align="center">Task execution</td></tr>
                                    <tr><td colspan="4" align="center"><html:errors/></td></tr>
                                    <logic:present name="dtm">
                                    <tr><td>Name:</td><td>${$dtm.uid} </td><td colspan="2">${dtm.name} </td></tr>
                                    </logic:present>   
                                    
                                    <logic:present name="periodList">
                                    <tr>
                                      <td height="123" valign="top" colspan="4">
                                          <fieldset>
                                            <legend class="fieldset">Period </legend>
                                            <div style="width:770px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                                        <!--DWLayoutTable-->
                                         <tr>
                                          <td width="760" height="102">
                                              
                                              <table width="750" border="1" bordercolor="#D7E5F2" class="regsitertable">
                                                  <logic:iterate name="periodList" id="period">
                                                          <tr>
                                                               <td><html:multibox property='periods' styleId="period" value="${period}" styleClass='smallfieldcellselect'/> </td><td>${period} </td> 
                                                           </tr>
                                                 </logic:iterate>
                                              </table>

                                          </td>
                                          </tr>
                                          
                                      </table>
                                    </div>
                                      </fieldset></td>
                                    </tr>
                                    
                                    </logic:present>
                                    
                                    <%--<tr><td colspan="4" align="center">
                                            <logic:present name="periodList">
                                                <logic:iterate name="periodList" id="period">
                                                    <html:option value="${period}">${period}</html:option>
                                                             </logic:iterate>
                                            </logic:present>
                                            </td>
                                    </tr>
                                    <tr><td colspan="4" align="center">&nbsp; 
                                            </td></tr>--%>
                                    <tr><td width="150">Save Zero values</td>
                                        <td colspan="3" >
                                              <html:select property="allowZeroValue" styleId="allowZeroValue">
                                                  <html:option value="1">Yes</html:option>
                                                  <html:option value="0">No</html:option>
                                              </html:select>                                              
                                          </td>
                                          </tr>
                                    <tr><td colspan="4" align="center"><html:submit value="Execute" onclick="setActionName('execute')" disabled="${dbcSaveDisabled}"/>
                                            </td></tr>
                                    
                                    
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
