/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.test;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.io.*;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import org.apache.catalina.util.Base64;
import sun.misc.BASE64Encoder;
/**
 *
 * @author smomoh
 */
public class HttpsURLConnectionExample 
{
    public static class MyHostnameVerifier implements HostnameVerifier {
public boolean verify(String hostname, SSLSession session) {
// verification of hostname is switched off
return true;
}
}
public static void connectToHttpsUrl() throws Exception 
{
// connection and authentication
Map paramNameToValue = new HashMap(); // parameter name to value map
String URL_BASE = "https://";
String method = "GET";
String userName = "admin";
String password = "distict";
String authentication = userName + ':' + password;
String host = "play.dhis2.org";
String port = "9443";
final String HTTP_MODE_POST = "POST";
// command
String xmlFile = "C:\\Nomis\\Reports\\dataElements.xml";
String command = "dataElements.xml?paging=false";
// construct URL
StringBuffer params = new StringBuffer();
if (paramNameToValue.keySet().size() > 0) {
boolean isFirstParam = true;
for (Iterator paramIter =paramNameToValue.keySet().iterator();paramIter.hasNext();) 
{
    String paramStr = (String)paramIter.next();
    if (isFirstParam) {
    params.append("?" + paramStr);
    isFirstParam = false;
} 
else
{
    params.append("&" + paramStr);
}
    params.append("=" +URLEncoder.encode((String)paramNameToValue.get(paramStr),"UTF-8"));
}
}
//https://play.dhis2.org/2.29/api/dataElements.xml?paging=false
URL url = null;
if (method.equals(HTTP_MODE_POST))
url = new URL(URL_BASE + host + "/2.29/api/" + command);
else
url = new URL(URL_BASE + host +
"/2.29/api/" + command);// + params.toString());
// open HTTPS connection
HttpsURLConnection connection = null;
connection = (HttpsURLConnection)url.openConnection();
((HttpsURLConnection) connection).setHostnameVerifier(new MyHostnameVerifier());

URL url2 = new URL("https://admin:district@play.dhis2.org/2.29/api");
HttpsURLConnection urlConnection = (HttpsURLConnection)url2.openConnection();
urlConnection.setRequestProperty("Content-Type", "application/xml; charset=\"utf8\"");
urlConnection.setRequestMethod(method);
urlConnection.setDoOutput(true);
if (url.getUserInfo() != null) {
    String basicAuth = "Basic " + new String(Base64.encode(url.getUserInfo().getBytes()));
    urlConnection.setRequestProperty("Authorization", basicAuth);
}

//InputStream inputStream = urlConnection.getInputStream();

/*BASE64Encoder encoder = new BASE64Encoder();
String encoded = encoder.encode((authentication).getBytes("UTF-8"));
connection.setRequestProperty("Authorization", "Basic " + encoded);*/
// insert XML file
if (xmlFile != null)
{

OutputStream out = urlConnection.getOutputStream();
FileInputStream fileIn = new FileInputStream(xmlFile);
byte[] buffer = new byte[1024];
int nbRead;
do
{
nbRead = fileIn.read(buffer);
if (nbRead>0) {
out.write(buffer, 0, nbRead);
}
} while (nbRead>=0);
out.close();
}
// execute HTTPS request
int returnCode = urlConnection.getResponseCode();
InputStream connectionIn = null;
if (returnCode==200)
{
    connectionIn = urlConnection.getInputStream();
    System.out.println("Connection has input stream");
}
else
{
    connectionIn = urlConnection.getErrorStream();
    System.out.println("Connection has error stream");
}
// print resulting stream
BufferedReader buffer = new BufferedReader(new InputStreamReader(connectionIn));
String inputLine;
//File file=new File(xmlFile);
while ((inputLine = buffer.readLine()) != null)
   
System.out.println(inputLine);
buffer.close();
}
}
