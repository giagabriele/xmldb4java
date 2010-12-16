/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xmldb.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

import xmldb.criteria.Criteria;
import xmldb.exception.ElementNotFoundException;
import xmldb.exception.ValidationException;
import xmldb.validation.SimpleErrorHandler;

/**
 * Classe Helper di un {@link Document}
 * @author Giacomo Stefano Gabriele
 *
 */
public class DocumentUtil {

    private static final Logger logger = Logger.getLogger(DocumentUtil.class);

    /**
     * Ritorna un {@link Element} dato un {@link Criteria}
     * @param document
     * @param criteria
     * @return un element
     * @throws ElementNotFoundException se non lo trova
     */
    public static Element findElementById(Document document, Criteria criteria) throws ElementNotFoundException {
        Element element = (Element) document.selectSingleNode(criteria.getXPathQuery());
        if (element == null) {
            throw new ElementNotFoundException("L'elemento non e' stato trovato. Query :[" + criteria.getXPathQuery() + "]");
        }
        return element;
    }

    @Deprecated
    public static void write(Document document, String file) throws IOException {
        // lets write to a file
        OutputFormat outformat = OutputFormat.createPrettyPrint();
        //outformat.setEncoding("ISO-8859-1");

        XMLWriter writer = new XMLWriter(new FileWriter(file), outformat);
        writer.write(document);
        writer.close();
    }

    public static void write(Document document, String file, String codifica) throws IOException {
        // lets write to a file
        OutputFormat outformat = OutputFormat.createPrettyPrint();
        outformat.setEncoding(codifica);

        XMLWriter writer = new XMLWriter(new FileWriter(file), outformat);
        writer.write(document);
        writer.close();

        logger.info("Documento salvato correttamente");
    }

//    public static void main(String a[]){
//        URL url = ClassLoader.getSystemResource("xmldb.xsd");
//
//
//         //URL urlXml = ClassLoader.getSystemResource("xmldb.xml");
//        System.out.println("url xsd\t"+url.getFile());
//       // System.out.println("url xml\t"+urlXml.getPath());
//
//        validate("C:\\Programmazione\\NetBeansProjects\\xmldb\\xmldb.xml", ClassLoader.getSystemResourceAsStream("xmldb.xsd"));
//    }

  /**
   * Validate the file with xsd file
   * @param file
   * @param xsd
   * @throws ValidationException
   * @see http://www.edankert.com/validate.html
   */
    public static void validate(String file,InputStream xsd) throws ValidationException {
        if (logger.isDebugEnabled()) {
            logger.debug("validate\t" + file);
            logger.debug("xsd \t" + xsd);
        }
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();

            SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            factory.setSchema(schemaFactory.newSchema(new Source[]{new StreamSource(xsd)}));
            SAXParser parser = factory.newSAXParser();
            SAXReader reader = new SAXReader(parser.getXMLReader());
            reader.setValidation(false);
            reader.setErrorHandler(new SimpleErrorHandler());
            reader.read(file);


            logger.info("Validation xml success!!!");
        } catch (ParserConfigurationException e) {
            logger.error("Errore ParserConfigurationException", e);
            throw new ValidationException("Errore ParserConfigurationException", e);
        } catch (SAXException e) {
            logger.error("Errore SAXException", e);
            throw new ValidationException("Errore SAXException", e);
        } catch (DocumentException e) {
            logger.error("Errore DocumentException", e);
            throw new ValidationException("Errore DocumentException", e);
        }


    }
}
