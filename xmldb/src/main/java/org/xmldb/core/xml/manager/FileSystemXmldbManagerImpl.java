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
package org.xmldb.core.xml.manager;

import java.util.LinkedList;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;
import org.dom4j.Element;
import org.xmldb.core.XmlDBConstants;
import org.xmldb.core.annotation.bean.PersistenceClass;
import org.xmldb.core.commons.log.LogHelper;
import org.xmldb.core.criteria.Criteria;
import org.xmldb.core.criteria.Restrictions;
import org.xmldb.core.exceptions.ElementNotFoundException;
import org.xmldb.core.exceptions.XmlDBRuntimeException;
import org.xmldb.core.trasformers.ObjectTrasformers;
import org.xmldb.core.trasformers.Trasformers;
import org.xmldb.core.xml.sequence.SequenceManager;
import org.xmldb.core.xml.XmlHelper;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class FileSystemXmldbManagerImpl implements  XmldbManager {

    private XmlHelper xmlHelper;
    private SequenceManager sequenceManager;
    
    public FileSystemXmldbManagerImpl(String path) {
        xmlHelper = new XmlHelper(path, XmlDBConstants.UTF8);
        sequenceManager = new SequenceManager(xmlHelper);
    }

    public FileSystemXmldbManagerImpl(String path, String codifica) {
        xmlHelper = new XmlHelper(path, codifica);
        sequenceManager = new SequenceManager(xmlHelper);
    }

    public <T> T merge(PersistenceClass persistenceClass) {
        try {
            Object o = persistenceClass.getObjWrap();

            Object value = PropertyUtils.getProperty(o, persistenceClass.getPkField().getFieldName());
            String xpath = createXpathQuery(o.getClass(), value);

            
            if(!xmlHelper.existElement(xpath)){
                value = sequenceManager.nextId(persistenceClass);
            }

            xmlHelper.removeUnique(xpath);

            PropertyUtils.setSimpleProperty(o, persistenceClass.getPkField().getFieldName(), value);

            Trasformers trasf = new ObjectTrasformers(persistenceClass);
            Element element = trasf.trasformElement(o);

            xmlHelper.addEntity(element);

            return (T) o;
        } catch (Exception e) {
            throw new XmlDBRuntimeException(e);
        }
    }

    public List findList(Criteria criteria) {
        Trasformers trasf = new ObjectTrasformers(criteria.getPersistenceClass());

        List result = new LinkedList();
        for(Element element:xmlHelper.selectNode(criteria.getXPathQuery())){
            result.add(trasf.trasformModel(element));
        }

        return result;
    }
//    /**
//     *
//     * @param <T>
//     * @param criteria
//     * @return
//     * @throws NonUniqueResultException
//     */
//    public <T>T findUnique(Criteria criteria) {
//        List result = findList(criteria);
//
//        if(result.size()>1){
//            throw  new NonUniqueResultException();
//        }
//        return (T) result.get(0);
//    }

    public void remove(PersistenceClass persistenceClass, Object id) {
        String xpath = createXpathQuery(persistenceClass.getClazz(), id);
        xmlHelper.removeUnique(xpath);
    }

    public <T> T load(PersistenceClass persistenceClass, Object id) {
        String xpath = createXpathQuery(persistenceClass.getClazz(), id);
        Element element = xmlHelper.selectSingleNode(xpath);
        if (element == null) {
            throw new ElementNotFoundException(persistenceClass.getClazz(), id, xpath);
        }
        Trasformers trasf = new ObjectTrasformers(persistenceClass);
        return (T) trasf.trasformModel(element);
    }

    public void commit() {
        xmlHelper.save();
    }

    public void rollback() {
        xmlHelper.load();
    }

    private String createXpathQuery(Class clazz, Object id) {
        Criteria c = Criteria.createCriteria(clazz);
        c.add(Restrictions.idEq(id));
        
        LogHelper.debug("XmldbManager.createXpathQuery --->"+c.getXPathQuery(),FileSystemXmldbManagerImpl.class);
        return c.getXPathQuery();
    }

    public void close() {
        xmlHelper.close();
    }



    @Override
    public String toString() {
        return xmlHelper.toString();
    }


}
