<%-- 
    Document   : BusinessRulesMgt
    Created on : Jun 18, 2017, 8:57:38 PM
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
        <title>Source data</title>
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
                                    <div><jsp:include page="../Navigation/MainMenu.jsp"/></div>
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
<html:form action="/businessrulereport">
    <html:hidden property="actionName" styleId="actionName" />
    
    <br/><br/>
    <html:errors />
<center>
    
    <table align="center" >
        
        <tr><td colspan="4" align="center" style="margin-left: 350px;">&nbsp;</td></tr>
       
        <tr><td colspan="4" align="center" style="margin-left: 350px;">&nbsp;</td></tr>
        <tr><td colspan="4" align="center" style="margin-left: 350px;">
            <center>

    
    
          </center>
 </td></tr>
</table>
          <table>
              <tr><td>Producer instance </td><td>&nbsp; </td><td>Consumer instance </td><td> &nbsp;</td></tr>
        <tr><td> 
                <html:select property="producerInstance" onchange="setActionName('fetchconsumerList');forms[0].submit()">
                    <html:optionsCollection property="producerInstanceList" value="id" label="instanceName" />
                </html:select>
            </td>
            <td>&nbsp;</td>
            <td> 
                <html:select property="consumerInstance">
                    <html:optionsCollection property="consumerInstanceList" value="id" label="instanceName" />
                </html:select>
            </td>
            <td> </td>
        </tr>
        <tr><td colspan="8"><html:submit style="width:120px;height:30px;" value="Load Business rules" onclick="return setActionName('loadbizrule')"/></td></tr>
          </table>
    <logic:present name="bizrulList">
          <div style="width:720px; height:600px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td height="102">
                          <table width="1200" border="1" cellspacing="0" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:iterate name="bizrulList" id="bz">
                                  <tr><td>${bz.recordId} </td><td>${bz.datasetName} </td> <td>${bz.consumerInstanceName}</td><td>${bz.consumerDeName}</td><td>${bz.consumerCatComboName}</td>
                                      <td>${bz.producerInstanceName}</td><td>${bz.producerDeName}</td><td>${bz.producerCatComboName}</td>
                                      <td>${bz.businessLogicName}</td>
                                      <td>Enable<html:radio property="enable" styleId="enable" value="enable" />Enable</td>
                                      <td>Disable<html:radio property="enable" styleId="disable" value="disable" />Disable</td>
                                      <td>Delete<html:radio property="delete" styleId="delete" value="delete" />Delete</td>
                                  </tr>
                              </logic:iterate>
                              
                        </table>

                      </td>
                      </tr>
                  </table>
                </div>
     </logic:present>
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
