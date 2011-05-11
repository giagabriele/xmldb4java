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
package org.xmldb.core.xml.sequence;

import org.dom4j.Element;
import org.xmldb.core.annotation.bean.PersistenceClass;
import org.xmldb.core.commons.log.LogHelper;
import org.xmldb.core.trasformers.SequenceTrasformers;
import org.xmldb.core.trasformers.Trasformers;
import org.xmldb.core.type.Sequence;
import org.xmldb.core.xml.XmlHelper;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class SequenceManager {

    private XmlHelper xmlHelper;

    public SequenceManager(XmlHelper xmlHelper) {
        this.xmlHelper = xmlHelper;
    }

    private Sequence getSequence(String id){
        
        Element element = xmlHelper.findSequence(id);
        if( element == null){
            return null;
        }
        Sequence s = null;
        Trasformers<Sequence> trasf = new SequenceTrasformers();
        s = trasf.trasformModel(element);

        return s;
    }

    public synchronized  int nextId(PersistenceClass persistenceClass){
        Sequence s = getSequence(persistenceClass.getClassName());
        if(s == null){
            s = new Sequence();
            s.setId(persistenceClass.getClassName());
            s.setSequence(1);
        }else{
            s.setSequence(s.getSequence()+1);
        }
        merge(s);
        return s.getSequence();
    }

    private void merge(Sequence s){
        Trasformers<Sequence> trasf = new SequenceTrasformers();
        Element element = trasf.trasformElement(s);

        LogHelper.debug("Element: --> "+element.asXML(),SequenceManager.class);

        xmlHelper.mergeSequence(element);
    }
}
