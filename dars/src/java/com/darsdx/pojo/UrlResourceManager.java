/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.pojo;

import com.darsdx.business.DhisInstance;
import com.darsdx.dao.DhisInstanceDao;
import com.darsdx.dao.DhisInstanceDaoImpl;
import java.io.Serializable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLException;
/**
 *
 * @author smomoh
 */
public class UrlResourceManager implements Serializable
{
    public static List getUrlResource(String urlString,String userName,String password) 
    {
        
        List resultList=new ArrayList();
        //String stringUrl = "https://who-dev.baosystems.com/api/dataElements.xml";
        URL url;
        try 
        {
            /*// Create a trust manager that does not validate certificate chains
                TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() 
                {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
                };

                // Install the all-trusting trust manager
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

                // Create all-trusting host name verifier
                HostnameVerifier allHostsValid = new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };

                // Install the all-trusting host verifier
                HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
                System.err.println(" "+userName+" "+password);
                url = new URL(urlString);
            
            //HttpsURLConnection*/
            url = new URL(urlString);
                URLConnection uc;
             uc =url.openConnection();
            uc.setRequestProperty("X-Requested-With", "Curl");
            String userpass = userName+ ":" + password;
            String basicAuth = "Basic " + new String(Base64.encode(userpass.getBytes()));
            if(userName !=null && password !=null)
            uc.setRequestProperty("Authorization", basicAuth);
            BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) 
            {
                //System.err.println(line);
                resultList.add(line);
            }
        }
        catch (UnknownHostException unex) 
        {
                System.err.println("Unable to connect: "+unex.getMessage());
        }
        catch (ProtocolException pex) 
        {
                System.err.println("Too many redirects, Unable to pull data: "+pex.getMessage());
        }//
        catch (SocketException sex) 
        {
                System.err.println("Unable to connect: "+sex.getMessage());
        }
        catch (SSLException sslex) 
        {
                System.err.println("SSL Handshake error: "+sslex.getMessage());
        }
        catch (IOException e) 
        {
                e.printStackTrace();
        }
        catch (Exception e) 
        {
               System.err.println("Error: "+e.getMessage());
        }
        return resultList;
    }
    public String getOrgUnitQueryString(List orgUnitList)
    {
        String orgUnitStr="&";
        String orgUnit="orgUnit=";
        if(orgUnitList !=null)
        {
            String str=null;
            for(int i=0; i<orgUnitList.size(); i++)
            {
                str=(String)orgUnitList.get(i);
                if(str !=null && str.length()>1)
                {
                    if(i==orgUnitList.size()-1)
                    orgUnitStr+=orgUnit+str;
                    else
                    orgUnitStr+=orgUnit+str+"&";
                }
            }
        }
        if(orgUnitStr.equalsIgnoreCase("&"))
        orgUnitStr=null;
        return orgUnitStr;
    }
    public String getStartDateQueryString(String startDate)
    {
        String periodStr=null;
        if(startDate !=null)
        {
            periodStr="&startDate="+startDate;
        }
        return periodStr;
    }
    public String getPeriodQueryString(String startDate,String endDate)
    {
        String periodStr=null;
        if(startDate !=null && endDate !=null)
        {
            periodStr="&startDate="+startDate+"&endDate="+endDate;
        }
        return periodStr;
    }
    public static String getDataValueDatasetUrl(String dhisId,String dataSetId,String orgUnitGroupId,String startDate,String endDate) 
    {
        String datavalueUrl=null;
        try
        {
            String seperator=DHISPropertyManager.getSeperator();
            String firstQueryParameterSeperator=DHISPropertyManager.getFirstQueryParamSeperator();
            String additionalQueryParameterSeperator=DHISPropertyManager.getAdditionalQueryParamSeperator();
            String links=DHISPropertyManager.getLinks();
            String paging=DHISPropertyManager.getPaging();
            DhisInstanceDao dsdao=new DhisInstanceDaoImpl();
            DhisInstance ds=dsdao.getDhisInstanceById(dhisId);
            if(ds !=null)
            datavalueUrl=ds.getDhisHomeUrl()+seperator+ds.getApiUrl()+seperator+DHISPropertyManager.getDataValueSets()+DHISPropertyManager.getXmlExt()+firstQueryParameterSeperator+DHISPropertyManager.getDataset()+dataSetId+additionalQueryParameterSeperator+DHISPropertyManager.getOrgUnitGroup()+orgUnitGroupId+additionalQueryParameterSeperator+links+additionalQueryParameterSeperator+paging+additionalQueryParameterSeperator+DHISPropertyManager.getStartDate()+startDate+"&"+DHISPropertyManager.getEndDate()+endDate;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return datavalueUrl;
    }
    public static String getDatavalueAnalyticsUrl(String apiUrl,String state,String selectedDataElements) 
    {
        String seperator=DHISPropertyManager.getSeperator();
        String links=DHISPropertyManager.getLinks();
        String analytics=DHISPropertyManager.getAnalytics()+seperator;
        String dataValueSet=DHISPropertyManager.getDataValueSet()+".xml"+seperator;
        String dataValueSets=DHISPropertyManager.getDataValueSets();
        String dataset="dataset";
        String orgUnit=DHISPropertyManager.getOrgUnit();
        String startDate=DHISPropertyManager.getStartDate();
        String endDate=DHISPropertyManager.getEndDate();
        String dimension=DHISPropertyManager.getDimension()+"=";
        String deDimension=DHISPropertyManager.getDeDimension()+":";
        String ouDimension=DHISPropertyManager.getLevel()+"=";
        
        String datavalueUrl=apiUrl;
        if(apiUrl !=null)
        {
            datavalueUrl=apiUrl+seperator+analytics+dataValueSet+dimension+deDimension+selectedDataElements;
        }
        return datavalueUrl;
    }
    /*
    private static String links="links=false";
    private static String analytics="analytics";
    private static String dataValueSet="dataValueSet";
    private static String dataValueSets="dataValueSets";
    private static String dataset="dataset";
    private static String orgUnit="orgUnit";
    private static String startDate="startDate";
    private static String endDate="endDate";
    private static String dimension="dimension";
     */
}
