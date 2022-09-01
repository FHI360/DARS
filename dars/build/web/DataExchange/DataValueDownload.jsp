<%-- 
    Document   : DataValueUpload
    Created on : Jun 2, 2018, 6:03:41 PM
    Author     : smomoh
--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data value URL setup</title>
        <link href="images/sdmenu/sdmenu.css" rel="stylesheet" type="text/css" />
        <script language="javascript">
        function setActionName(val)
        {
            document.getElementById("actionName").value=val
        }
        function selectChkBoxes(chkname)
{
   var elements=document.getElementsByName(chkname)
    for(var i=0; i<elements.length; i++)
    {
        elements[i].checked=true
    }
}
function unselectChkBoxes(chkname)
{
   var elements=document.getElementsByName(chkname)
    for(var i=0; i<elements.length; i++)
    {
        elements[i].checked=false
    }
}
        </script>
    </head>
    <body><%--<jsp:include page="../includes/TopLabel.jsp"/>--%>
        <center>
            <table border="1" style="vertical-align: top; width: 930px; height: 650px; border: 1px green solid; border-collapse: collapse;">
            <tr>
                <td style="width: 200px; "><img src="images/logo.jpg" width="200" height="92" /> </td>

                <td style="background-image: url(images/topbar.jpg);  width: 750px; height: 92px; text-align: center; ">DARS</td>
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

                <td >
                    <table cellpadding="0" cellspacing="0">
                        <%--<img src="images/bgimage.jpg" width="800" height="600" /> --%>
                        <tr><td style="background-color:#FFEBCD;" align="right"><a href="login.do">Log off </a>&nbsp;&nbsp;&nbsp; </td></tr>
                        <tr><td style=" height: 540px; background-color:#FFEBCD;"> 
                            <html:form action="/datavaluedownload" method="POST">
                                <html:hidden property="actionName" styleId="actionName" />
                            <center>
                                <table>
                                    <tr><td>Select DHIS</td><td>
                                            <html:select property="dhisId" styleId="dhisId" style="width:510px; margin-left: 10px" onchange="setActionName('apiurl'); forms[0].submit()">
                                                <logic:present name="dhisSetupList">
                                                    <logic:iterate name="dhisSetupList" id="ds">
                                                        <html:option value="${ds.dhisId}">${ds.dhisName}</html:option>
                                                    </logic:iterate>
                                                </logic:present>
                                            </html:select>
                                        </td>
                                    </tr>
                                    <tr><td>Organization unit group</td><td>
                                            <html:select property="orgunitgroup" styleId="orgunitgroup" style="width:510px; margin-left: 10px" onchange="setActionName('apiurl'); forms[0].submit()">
                                                <logic:present name="ougList">
                                                    <logic:iterate name="ougList" id="oug">
                                                        <html:option value="${oug.orgunitGroupId}">${oug.orgunitGroupName}</html:option>
                                                    </logic:iterate>
                                                </logic:present>
                                            </html:select>
                                        </td>
                                    </tr>
                                    <tr><td><label class="labels">DataSet </label></td><td colspan="3">
                                            <html:select property="dataSetId" styleId="dataSetId" style="width:510px; margin-left: 10px" onchange="setActionName('apiurl'); forms[0].submit()">
                                                <logic:present name="dstList">
                                                    <logic:iterate name="dstList" id="dst">
                                                        <html:option value="${dst.datasetId}">${dst.datasetName}</html:option>
                                                    </logic:iterate>
                                                </logic:present>
                                            </html:select>
                                        </td>
                                    </tr>
                                    <%--<tr><td><label class="labels">States </label></td><td colspan="3">
                                        <div style="width:510px; height:140px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF; margin-left: 10px">
                                                  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                                                <!--DWLayoutTable  bordercolor="#D7E5F2"-->
                                                <tr>
                                                  <td >
                                                      <table width="500" border="1" bordercolor="green" cellpadding="0" cellspacing="0">
                                                          <logic:present name="stateList">
                                                              <logic:iterate id="ou" name="stateList">
                                                                  <tr><td><html:multibox property="stateCodes" styleId="stateCodes" value="${ou.orgunitId}" styleClass='smallfieldcellselect'/> </td><td>${ou.orgunitName} </td> </tr>
                                                              </logic:iterate>
                                                          </logic:present>
                                                    </table>

                                                  </td>
                                                  </tr>
                                              </table>
                                            </div>
                                        </td>
                                    </tr>
                                    
                                    <tr><td> </td><td colspan="3"><input type="button" value="Select all " style="margin-left: 10px;" onclick="selectChkBoxes('stateCodes')" />
                    <input type="button" value="Unselect all " onclick="unselectChkBoxes('stateCodes')" /></td></tr>--%>
                                    
                                        
                                    <%--<tr><td><label class="labels"> </label></td><td colspan="3">
                                        <div style="width:510px; height:140px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF; margin-left: 10px">
                                                  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                                                <!--DWLayoutTable  bordercolor="#D7E5F2"-->
                                                <tr>
                                                  <td >
                                                      <table width="500" border="1" bordercolor="green" cellpadding="0" cellspacing="0">
                                                          <logic:present name="dstList">
                                                              <logic:iterate id="dst" name="dstList">
                                                                  <tr><td><html:multibox property="dataSetIds" styleId="dataSetIds" value="${dst.dataSetId}" styleClass='smallfieldcellselect'/> </td><td>${de.dataSetName} </td> </tr>
                                                              </logic:iterate>
                                                          </logic:present>
                                                    </table>

                                                  </td>
                                                  </tr>
                                              </table>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr><td> </td><td colspan="3"><input type="button" value="Select all " onclick="selectChkBoxes('dataSetIds')" />
                    <input type="button" value="Unselect all " onclick="unselectChkBoxes('dataSetIds')" /></td></tr>--%>                                    
                                    <tr><td>Start date </td><td colspan="3">
                                            <html:select property="startDate" styleId="startDate" style="margin-left: 10px;" onchange="setActionName('generateMths'); forms[0].submit()">
                                                <logic:present name="monthList">
                                                              <logic:iterate id="mth" name="monthList">
                                                        <html:option value="${mth.year}-${mth.value}">${mth.shortName} ${mth.year}</html:option>
                                                    </logic:iterate>
                                                </logic:present>
                                            </html:select> 
                                            End date &nbsp;&nbsp;
                                            <html:select property="endDate" styleId="endDate" style="margin-left: 10px;" onchange="setActionName('generateMths'); forms[0].submit()">
                                                <logic:present name="monthList">
                                                              <logic:iterate id="mth" name="monthList">
                                                        <html:option value="${mth.year}-${mth.value}">${mth.shortName} ${mth.year}</html:option>
                                                    </logic:iterate>
                                                </logic:present>
                                            </html:select> 
                                            </td>
                                    </tr>
                                    <%--<tr><td >Period </td><td colspan="3" >
                                            <html:select property="startYear" styleId="startYear" style="margin-left: 10px;" onchange="setActionName('generateMths'); forms[0].submit()">
                                                <logic:present name="yearList">
                                                    <logic:iterate name="yearList" id="yr">
                                                        <html:option value="${yr}">${yr}</html:option>
                                                    </logic:iterate>
                                                </logic:present>
                                            </html:select> To&nbsp;<html:select property="endYear" styleId="endYear" onchange="setActionName('generateMths'); forms[0].submit()">
                                                <logic:present name="yearList">
                                                    <logic:iterate name="yearList" id="yr">
                                                        <html:option value="${yr}">${yr}</html:option>
                                                    </logic:iterate>
                                                </logic:present>
                                            </html:select> </td>
                                    </tr>
                                    
                                    <tr><td><label class="labels">Months </label></td><td colspan="3">
                                        <div style="width:510px; height:140px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF; margin-left: 10px">
                                                  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                                                <!--DWLayoutTable  bordercolor="#D7E5F2"-->
                                                <tr>
                                                  <td >
                                                      <table width="500" border="1" bordercolor="green" cellpadding="0" cellspacing="0">
                                                          <logic:present name="monthList">
                                                              <logic:iterate id="mth" name="monthList">
                                                                  <tr><td><html:multibox property="selectedMths" styleId="selectedMths" value="${mth.year}-${mth.value}" styleClass='smallfieldcellselect'/> </td><td>${mth.shortName} ${mth.year} </td> </tr>
                                                              </logic:iterate>
                                                          </logic:present>
                                                    </table>

                                                  </td>
                                                  </tr>
                                                  
                                              </table>
                                            </div>
                                        </td>
                                    </tr>--%>
                                    <tr><td >Data value set URL </td>
                                        <td colspan="3">
                                            <html:text property="dvurl" styleId="dvurl" style="margin-left: 10px; width: 507px" />
                                            </td>
                                    </tr>
                                    <tr><td colspan="4" > </td></tr>
                                    <tr><td colspan="4" align="center"><html:submit value="Save" onclick="setActionName('save')" />
                                            &nbsp;&nbsp;&nbsp;<html:submit value="Get Data from DHIS2" onclick="setActionName('downloadData')" />
                                        <html:submit value="Convert Data to IMIS format" onclick="setActionName('convertToImisStructure')" style="margin-left:10px"/></td></tr>
                                </table>
                            </center>
                            </html:form>
                            </td></tr>
                        <tr><td style="width: 700px; font-weight: bold; color: white; text-align: right; background-image: url(images/topbar.jpg)">&nbsp;</td></tr>
                    </table>

                </td>
            </tr>
            
        </table>
        </center>
    </body>
</html>
