<%-- 
    Document   : OrganizationUnitExportParamPage
    Created on : May 23, 2019, 12:23:19 PM
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
        <title>Organization unit export</title>
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
                                    <div><jsp:include page="../Navigation/BusinessRuleLink.jsp"/></div>
                                </div>
                            </td>
                        </tr>
                        <tr><td style="height:20px; width: 200px; text-align: center; font-weight: bolder; font-size: larger; background-image: url(images/smallbar.jpg)" > <label style="color:white; font-size: 20px; font-weight: bolder;">Reports</label></td></tr>
                        <tr><td style="width: 200px; " >
                                <div style="float: left" id="my_menu" class="sdmenu">
                                    <div><jsp:include page="../Navigation/MetadataMgtLink.jsp"/></div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </td>

                <td style="background-color:#FFEBCD">
                    <table align="center">
                        <tr><td height="400">
                              <center>
                                  <html:form action="/ouexport" method="POST">
                                <html:hidden property="actionName" styleId="actionName" />
                                
                            <center>
                                <table>
                                    <tr><td colspan="4" align="center">Organization unit export</td></tr>
                                    <tr><td colspan="4" align="center" style="color: red; font-weight: bold; font-weight: 14px"><html:errors/></td></tr>
                                    <tr>
                                        <td><label class="labels">Connection</label></td>
                                        <td colspan="3">
                                            <html:select property="connectionId" styleId="connectionId" style="width:500px" onchange="setActionName('level1OuList'); forms[0].submit()">
                                                <html:option value="select">select...</html:option>
                                                <logic:present name="dhisForOuExportList">
                                                    <logic:iterate name="dhisForOuExportList" id="conn">
                                                        <html:option value="${conn.dhisId}">${conn.dhisName}</html:option>
                                                    </logic:iterate>
                                                </logic:present>
                                            </html:select>
                                        </td>                                         
                                    </tr>
                                    <tr>
                                        <td ><label class="labels">OU Level 1 </label></td>
                                        <td colspan="3">
                                            <html:select property="ouLevel1Id" styleId="ouLevel1Id" style="width:500px" onchange="setActionName('level2OuList'); forms[0].submit()">
                                                <html:option value="select">select...</html:option>
                                                <logic:present name="level1OuListForExport">
                                                    <logic:iterate name="level1OuListForExport" id="ou">
                                                        <html:option value="${ou.orgunitId}">${ou.orgunitName}</html:option>
                                                    </logic:iterate>
                                                </logic:present>
                                            </html:select>
                                        </td>                                         
                                    </tr>
                                    <tr>
                                        <td ><label class="labels">OU Level 2 </label></td>
                                        <td colspan="3">
                                            <html:select property="ouLevel2Id" styleId="ouLevel2Id" style="width:500px" onchange="setActionName('level3OuList'); forms[0].submit()">
                                                <html:option value="select">select...</html:option>
                                                <logic:present name="level2OuListForExport">
                                                    <logic:iterate name="level2OuListForExport" id="ou">
                                                        <html:option value="${ou.orgunitId}">${ou.orgunitName}</html:option>
                                                    </logic:iterate>
                                                </logic:present>
                                            </html:select>
                                        </td>                                         
                                    </tr>
                                    <tr>
                                        <td ><label class="labels">OU Level 3 </label></td>
                                        <td colspan="3">
                                            <html:select property="ouLevel3Id" styleId="ouLevel3Id" style="width:500px" onchange="setActionName('level4OuList'); forms[0].submit()">
                                                <html:option value="select">select...</html:option>
                                                <logic:present name="level3OuListForExport">
                                                    <logic:iterate name="level3OuListForExport" id="ou">
                                                        <html:option value="${ou.orgunitId}">${ou.orgunitName}</html:option>
                                                    </logic:iterate>
                                                </logic:present>
                                            </html:select>
                                        </td>                                         
                                    </tr>
                                    <tr>
                                        <td ><label class="labels">OU Level 4 </label></td>
                                        <td colspan="3">
                                            <html:select property="ouLevel4Id" styleId="ouLevel4Id" style="width:500px" onchange="setActionName('level5OuList'); forms[0].submit()">
                                                <html:option value="select">select...</html:option>
                                                <logic:present name="level4OuListForExport">
                                                    <logic:iterate name="level4OuListForExport" id="ou">
                                                        <html:option value="${ou.orgunitId}">${ou.orgunitName}</html:option>
                                                    </logic:iterate>
                                                </logic:present>
                                            </html:select>
                                        </td>                                         
                                    </tr>
                                    <tr>
                                        <td ><label class="labels">OU Level 5 </label></td>
                                        <td colspan="3">
                                            <html:select property="ouLevel5Id" styleId="ouLevel5Id" style="width:500px" onchange="setActionName('level6OuList'); forms[0].submit()">
                                                <html:option value="select">select...</html:option>
                                                <logic:present name="level5OuListForExport">
                                                    <logic:iterate name="level5OuListForExport" id="ou">
                                                        <html:option value="${ou.orgunitId}">${ou.orgunitName}</html:option>
                                                    </logic:iterate>
                                                </logic:present>
                                            </html:select>
                                        </td>                                         
                                    </tr>
                                    <tr>
                                        <td ><label class="labels">OU Level 6 </label></td>
                                        <td colspan="3">
                                            <html:select property="ouLevel6Id" styleId="ouLevel6Id" style="width:500px">
                                                <html:option value="select">select...</html:option>
                                                <logic:present name="level6OuListForExport">
                                                    <logic:iterate name="level6OuListForExport" id="ou">
                                                        <html:option value="${ou.orgunitId}">${ou.orgunitName}</html:option>
                                                    </logic:iterate>
                                                </logic:present>
                                            </html:select>
                                        </td>                                         
                                    </tr>
                                    
                                    <logic:present name="ouListForExport">
                                    <tr>
                                      <td height="123" valign="top" colspan="4">
                                          <fieldset>
                                            <legend class="fieldset">Select organization unit to be exported </legend>
                                            <div style="width:770px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                                        <!--DWLayoutTable-->
                                         <tr>
                                          <td width="760" height="102">
                                              
                                              <table width="750" border="1" bordercolor="#D7E5F2" class="regsitertable">
                                                  <logic:iterate name="ouListForExport" id="ou">
                                                          <tr>
                                                               <td><html:multibox property='selectedOuForExport' styleId="period" value="${ou.orgunitId}" styleClass='smallfieldcellselect'/> </td><td>${ou.orgunitName} </td> 
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
                                    <tr>
                                          <td>Export options</td>
                                          <td colspan="3">
                                              <html:select property="exportOption" styleId="exportOption">
                                                  <html:option value="selectedOrgUnitsOnly">Export selected organization units only</html:option>
                                                  <html:option value="immediateChildrenOnly">Export selected organization units and only immediate children</html:option>
                                                  <html:option value="allDescebndants">Export selected organization units and include children and all descendants</html:option>
                                              </html:select> 
                                          </td>
                                          
                                   </tr>
                                    <tr><td colspan="4" align="center"><html:submit value="Gemerate export file" onclick="setActionName('createExport')" disabled="${dsSaveDisabled}"/>
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
