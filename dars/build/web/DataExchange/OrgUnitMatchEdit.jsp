<%-- 
    Document   : OrgUnitMatchEdit
    Created on : Jun 19, 2017, 9:30:22 AM
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
        <title>JSP Page</title>
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
function unCheckDeleteDisableControls(id,sender)
{
    if(sender=="deletebtn")
    {
        if(document.getElementById(id+"_delete").checked==true)
        document.getElementById(id+"_disable").checked=false
    }
    else if(sender=="disablebtn")
    {
        if(document.getElementById(id+"_disable").checked==true)
        document.getElementById(id+"_delete").checked=false
    }
}
</script>
    </head>
    <body>
        <table align="center">
                        <tr><td height="400">
                              <center>
<html:form action="/orgunitmatchedit">
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
        <tr><td> </td><td>&nbsp; </td><td> </td><td> &nbsp;</td></tr>
        <tr><td><html:submit style="width:140px;height:30px;" value="Load records" onclick="return setActionName('loadrecord')"/></td>
            <td></td>
        <td ><html:submit style="width:100px;height:30px;" value="Submit" onclick="return setActionName('deleteOrdisable')"/></td>
        <td></td></tr>
          </table>
    <logic:present name="oumList">
          
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td height="102">
                          <table width="1200" border="1" cellspacing="0" bordercolor="#D7E5F2" class="regsitertable">
                              <tr><td>Id </td><td>Producer instance </td><td>Organization unit Id </td> <td>Organization unit name</td>
                                  <td>Consumer instance</td><td>Organization unit Id </td> <td>Organization unit name</td>
                                      
                                      <td>Disable</td>
                                      <td>Delete</td>
                                  </tr>
                              <logic:iterate name="oumList" id="oum">
                                  <tr><td>${oum.recordId}</td><td>${oum.producerOrgUnit.dhisInstance.instanceName}</td><td>${oum.producerOrgUnit.orgunitId} </td><td>${oum.producerOrgUnit.orgunitName} </td> 
                                      <td>${oum.consumerOrgUnit.dhisInstance.instanceName}</td><td>${oum.consumerOrgUnit.orgunitId} </td><td>${oum.consumerOrgUnit.orgunitName} </td> 
                                                                            
                                      <td><html:multibox property="disable" styleId="${oum.recordId}_disable" value="${oum.recordId}" onclick="unCheckDeleteDisableControls(this.value,'disablebtn')" />Disable</td>
                                      <td><html:multibox property="delete" styleId="${oum.recordId}_delete" value="${oum.recordId}" onclick="unCheckDeleteDisableControls(this.value,'deletebtn')" />Delete</td>
                                  </tr>
                              </logic:iterate>
                              
                        </table>

                      </td>
                      </tr>
                  </table>
                
     </logic:present>
</center>
</html:form>
                        </center>
                              </td></tr>

                        <tr><td style="width: 600px; font-weight: 800; color: green; text-align: right; vertical-align: bottom;">&nbsp;</td></tr>
                    </table>
    </body>
</html>
