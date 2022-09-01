<%-- 
    Document   : HomePage
    Created on : Feb 14, 2016, 12:55:17 PM
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
        <title>Data Analysis and Reporting Software</title>
        <link href="images/sdmenu/sdmenu.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <center>
            <table border="1" style="vertical-align: top; width: 930px; height: 650px; border: 1px green solid; border-collapse: collapse;">
            <tr>
                <td style="width: 200px; "><img src="images/logo.jpg" width="200" height="92" /> </td>

                <td style="background-image: url(images/topbar.jpg);  width: 750px; height: 92px; text-align: center; "><label style="color:white; font-size: 30px; font-weight: bolder;">Data Analysis and Reporting System</label> </td>
            </tr>
            <tr>
                <td align="center" style="width: 200px; background-color:#528B8B; height: 500px; vertical-align: top">
                    <table>
                        <tr><td style="height:20px; width: 200px; text-align: center; font-weight: bolder; font-size: larger; background-image: url(images/smallbar.jpg)" > <label style="color:white; font-size: 20px; font-weight: bolder;">Tasks</label></td></tr>
                        <tr><td style="width: 200px; " >
                                <div style="float: left" id="my_menu" class="sdmenu">
                                    <div><jsp:include page="Navigation/MainMenu.jsp"/></div>
                                </div>
                            </td>
                        </tr>
                        <tr><td style="height:20px; width: 200px; text-align: center; font-weight: bolder; font-size: larger; background-image: url(images/smallbar.jpg)" > <label style="color:white; font-size: 20px; font-weight: bolder;">Reports</label></td></tr>
                        <tr><td style="width: 200px; " >
                                <div style="float: left" id="my_menu" class="sdmenu">
                                    <div><jsp:include page="Navigation/ReportMenu.jsp"/></div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </td>

                <td >
                    <table cellpadding="0" cellspacing="0">
                        <%--<img src="images/bgimage.jpg" width="800" height="600" /> --%>
                        <tr><td style="background-color:#FFEBCD;" align="right"><a href="login.do">Log off </a>&nbsp;&nbsp;&nbsp; </td></tr>
                        <tr><td style=" height: 540px; background-color:#FFEBCD;"> </td></tr>
                        <tr><td style="width: 700px; font-weight: bold; color: white; text-align: right; background-image: url(images/topbar.jpg)">&nbsp;</td></tr>
                    </table>

                </td>
            </tr>
            
        </table>
        </center>
    </body>
</html>
