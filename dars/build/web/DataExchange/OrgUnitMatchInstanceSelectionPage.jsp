<%-- 
    Document   : OrgUnitMatchInstanceSelectionPage1
    Created on : May 11, 2017, 5:21:09 PM
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
<html:form action="/orgunitmatch">
    <html:hidden property="actionName" styleId="actionName" />
    
    <br/><br/>
    <html:errors />
<center>
    <a href="orgunitmatchedit.do" target="_blank">Edit Records</a>
    <div style=" width: 600px; height:450px; border: 1px black solid; alignment-adjust: central">
    <table align="center" >
        <tr><td colspan="4" align="center"><label class="labels">Organization unit mapping  </label></td></tr>
        <tr><td colspan="4" >&nbsp; </td></tr>
        
        <tr><td colspan="2"><label class="labels">Producer instance </label></td>
            
                <td colspan="2"><label class="labels">Consumer instance</label></td>
                
        </tr>
        <tr>
            <td colspan="2"> 
                <html:select property="producerInstance" styleId="producerInstance" styleClass="textField" style="width:250px;" onchange="setActionName('producerChanged'); forms[0].submit()"> 
                    <logic:present name="producerInstanceList">
                        <logic:iterate name="producerInstanceList" id="instance">
                            <html:option value="${instance.dhisId}">${instance.dhisName} </html:option>
                        </logic:iterate>
                    </logic:present>
                </html:select>
                </td>
                
                <td colspan="2"> 
                <html:select property="consumerInstance" styleId="consumerInstance" styleClass="textField" style="width:250px;" onchange="setActionName('consumerChanged'); forms[0].submit()"> 
                    <logic:present name="consumerInstanceList">
                        <logic:iterate name="consumerInstanceList" id="instance">
                            <html:option value="${instance.dhisId}">${instance.dhisName} </html:option>
                        </logic:iterate>
                    </logic:present>
                </html:select>
                </td>
                
        </tr>
        <tr><td colspan="4">&nbsp;</td></tr>
        <tr><td colspan="2"><label class="labels">Producer Organization unit level</label></td>
            <td colspan="2"><label class="labels">Consumer Organization unit level</label></td>                       
        </tr>
    <tr>
            <td colspan="2"> 
                <html:select property="producerOrgUnitLevel" styleId="producerOrgUnitLevel" styleClass="textField" style=" width:250px;"> 
                    <logic:present name="producerOrgUnitLevelList">
                        <logic:iterate name="producerOrgUnitLevelList" id="level">
                            <html:option value="${level}">${level} </html:option>
                        </logic:iterate>
                    </logic:present>
                </html:select>
                </td>
                
                <td colspan="2"> 
                <html:select property="consumerOrgUnitLevel" styleId="consumerOrgUnitLevel" styleClass="textField" style="width:250px;"> 
                    
                    <logic:present name="consumerOrgUnitLevelList">
                        <logic:iterate name="consumerOrgUnitLevelList" id="level">
                            <html:option value="${level}">${level} </html:option>
                        </logic:iterate>
                    </logic:present>
                </html:select>
                </td>
                
        </tr>
        <tr><td colspan="4">&nbsp;</td></tr>
    <tr><td colspan="4" align="center" style="margin-left: 350px;">
            <center>
<fieldset style="width:250px;">
    <html:submit style="width:75px;height:30px;" value="Next >>" styleId="nextbtn" onclick="return setActionName('next')"/>
    

</fieldset>
            </center>
 </td></tr>
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


