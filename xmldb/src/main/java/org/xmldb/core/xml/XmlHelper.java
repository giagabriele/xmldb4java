/*
 * Copyright 2011 Giacomo Stefano Gabriele
 *
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
import org.dom4j.tree.DefaultElement;
import org.xmldb.core.commons.log.LogHelper;
import org.xmldb.core.exceptions.XmlDBRuntimeException;

import static org.xmldb.core.XmlDBConstants.*;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class XmlHelper {

    private String path;
    private File file;
    private Document document;

    private Element rootSequences;
    private Element rootEntities;

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
        Element holdSequence = (Element) findSequence(element.attributeValue("id"));
        if (holdSequence != null) {
            holdSequence.detach();
        }

        rootSequences.add(element);
    }

    public Element findSequence(String id){
        return (Element) rootSequences.selectSingleNode("//Sequence[@id='" + id+ "']");
    }

    public void addEntity(Element element) {
        if (element == null) {
            return;
        }
        rootEntities.add(element);
    }

    public boolean removeUnique(String xpathQuery) {
        Element element = selectSingleNode(xpathQuery);
        if(element != null){
            element.detach();
        }
        return true;
    }

    public synchronized void save() {
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

    public synchronized final void load() {
        try {
            SAXReader reader = new SAXReader();
            file = new File(path);
            if (!file.exists()) {
                LogHelper.info("Document not exist! Create...");
                document = DocumentHelper.createDocument();
                document.addElement(ROOT_ELEMENT);

                rootSequences = new DefaultElement(ELEMENT_SEQUENCES);
                rootEntities = new DefaultElement(ELEMENT_ENTITIES);

                document.getRootElement().add(rootSequences);
                document.getRootElement().add(rootEntities);

            } else {
                document = reader.read(file);
                rootSequences = (Element) document.selectSingleNode("//"+ELEMENT_SEQUENCES);
                rootEntities  = (Element) document.selectSingleNode("//"+ELEMENT_ENTITIES);
            }
        } catch (Exception e) {
            throw new XmlDBRuntimeException(e);
        }
    }

    public void close(){
        LogHelper.info("Closing ....",XmlHelper.class);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PATH DB :").append(path).append("\n");
        sb.append("CODIFICA:").append(codifica).append("\n");
        sb.append("DOCUMENT:\n").append(document.asXML()).append("\n");

        return sb.toString();
    }


}
