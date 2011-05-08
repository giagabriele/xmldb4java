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
package org.xmldb.core.xml;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xmldb.core.commons.log.LogHelper;
import org.xmldb.core.exceptions.XmlDBRuntimeException;

import static org.xmldb.core.XmlDBConstants.*;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class XmlHelper {

    protected static final String XPATH_SEQUENCES = "//" + ELEMENT_SEQUENCES;
    protected static final String XPATH_SEQUENCE = "//Sequence";
    protected static final String XPATH_ENTITIES = "//" + ELEMENT_ENTITIES;
    private String path;
    private File file;
    private Document document;
    private String codifica;

    public XmlHelper(String filePath) {
        this(filePath, UTF8);
    }

    public XmlHelper(String filePath, String codifica){
        this.path = filePath;
        this.codifica = codifica;

        load();
    }

    public Element selectSingleNode(String xpathQuery) {
        LogHelper.debug("Xpath [" + xpathQuery + "]");
        return (Element) document.selectSingleNode(xpathQuery);
    }

    public List<Element> selectNode(String xpathQuery) {
        LogHelper.debug("Xpath [" + xpathQuery + "]");
        return document.selectNodes(xpathQuery);
    }

    public boolean existElement(String xpathQuery){
        return selectSingleNode(xpathQuery)!=null;
    }

    public void mergeSequence(Element element) {
        Element rootSequeneces = selectSingleNode(XPATH_SEQUENCES);
        Element holdSequence = selectSingleNode("//Sequence[@id='" + element.attributeValue("id") + "']");
        if (holdSequence != null) {
            holdSequence.detach();
        }

        rootSequeneces.add(element);
    }

    public void addEntity(Element element) {
        if (element == null) {
            return;
        }
        Element rootSequeneces = selectSingleNode(XPATH_ENTITIES);
        rootSequeneces.add(element);
    }

    public boolean removeUnique(String xpathQuery) {
        Element element = selectSingleNode(xpathQuery);
        if(element != null){
            element.detach();
        }
        return true;
    }

    public void save() {
        try {
            OutputFormat outformat = OutputFormat.createPrettyPrint();
            outformat.setEncoding(codifica);

            XMLWriter writer = new XMLWriter(new FileWriter(file), outformat);
            writer.write(document);
            writer.close();

            LogHelper.info("Documento salvato correttamente");
        } catch (Exception e) {
            throw new XmlDBRuntimeException(e);
        }
    }

    public final void load() {
        try {
            SAXReader reader = new SAXReader();
            file = new File(path);
            if (!file.exists()) {
                LogHelper.info("Document not exist! Create...");
                document = DocumentHelper.createDocument();
                document.addElement(ROOT_ELEMENT);

                document.getRootElement().addElement(ELEMENT_SEQUENCES);
                document.getRootElement().addElement(ELEMENT_ENTITIES);

            } else {
                document = reader.read(file);
            }
        } catch (Exception e) {
            throw new XmlDBRuntimeException(e);
        }
    }

    public void close(){
        LogHelper.info("Closing ....",XmlHelper.class);
    }

//    protected String buildXpath(Element e) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("//");
//        sb.append(e.getName());
//        return sb.toString();
//    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PATH DB :").append(path).append("\n");
        sb.append("CODIFICA:").append(codifica).append("\n");
        sb.append("DOCUMENT:\n").append(document.asXML()).append("\n");

        return sb.toString();
    }


}
