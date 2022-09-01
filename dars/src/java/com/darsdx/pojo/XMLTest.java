/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.pojo;

/**
 *
 * @author smomoh
 */
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLTest {

	public static void writeXml() 
        {

	  try {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
               
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("dataValueSet");
		doc.appendChild(rootElement);

		// set attribute to staff element
		Attr attr = doc.createAttribute("xmlns");
		attr.setValue("http://dhis2.org/schema/dxf/2.0");
		rootElement.setAttributeNode(attr);
                for(int i=0; i<10; i++)
                {
                    Element dataValue = doc.createElement("dataValue");
                    rootElement.appendChild(dataValue);

                    Attr deattr = doc.createAttribute("dataElement");
                    deattr.setValue("tehgafdhj");
                    dataValue.setAttributeNode(deattr);

                    Attr perdattr = doc.createAttribute("period");
                    perdattr.setValue("201703");
                    dataValue.setAttributeNode(perdattr);

                    Attr ouattr = doc.createAttribute("orgUnit");
                    ouattr.setValue("tehgafdhj");
                    dataValue.setAttributeNode(ouattr);


                    Attr ccattr = doc.createAttribute("categoryOptionCombo");
                    ccattr.setValue("xfcgjuyt");
                    dataValue.setAttributeNode(ccattr);

                    Attr valueattr = doc.createAttribute("value");
                    valueattr.setValue(i+"");
                    dataValue.setAttributeNode(valueattr);

                    Attr strbyattr = doc.createAttribute("storedBy");
                    strbyattr.setValue("smomoh");
                    dataValue.setAttributeNode(strbyattr);

                    Attr tmstpattr = doc.createAttribute("timestamp");
                    tmstpattr.setValue("2017-05-30");
                    dataValue.setAttributeNode(tmstpattr);

                    Attr fupattr = doc.createAttribute("followUp");
                    fupattr.setValue("false");
                    dataValue.setAttributeNode(fupattr);
                }

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("C:\\Dars\\file.xml"));

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		transformer.transform(source, result);

		System.out.println("File saved!");

	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
	}
}
