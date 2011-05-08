/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmldb.core.commons.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.DOMReader;
import org.dom4j.io.DOMWriter;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class ElementUtils {

    /**
     * Convert dom4j element to w3c element
     **/
    public static org.w3c.dom.Element convert(org.dom4j.Element element) throws DocumentException {
        org.dom4j.Document doc1 = DocumentHelper.createDocument();
        doc1.setRootElement(element);

        // Convert dom4j document to w3c document
        DOMWriter writer = new DOMWriter();
        org.w3c.dom.Document doc2 = writer.write(doc1);

        return doc2.getDocumentElement();
    }

    /**
     * Convert w3c element to dom4j element
     **/
    public static org.dom4j.Element convert(org.w3c.dom.Element element) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        org.w3c.dom.Document doc1 = builder.newDocument();
        doc1.appendChild(element);

        // Convert w3c document to dom4j document
        DOMReader reader = new DOMReader();
        org.dom4j.Document doc2 = reader.read(doc1);

        return doc2.getRootElement();
    }
}
