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
package org.xmldb.core.trasformers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.tree.DefaultAttribute;
import org.dom4j.tree.DefaultElement;
import org.xmldb.core.annotation.bean.PersistenceClass;
import org.xmldb.core.annotation.bean.PersistenteField;
import org.xmldb.core.commons.log.LogHelper;
import org.xmldb.core.commons.util.BeanConvert;
import org.xmldb.core.exceptions.XmlDBRuntimeException;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class ObjectTrasformers<T> implements Trasformers<T> {

    private PersistenceClass persistenceClass;

    public ObjectTrasformers(PersistenceClass persistenceClass) {
        this.persistenceClass = persistenceClass;
        BeanConvert.configure();
    }

    public Element trasformElement(T t) {
        try {
            DefaultElement element = new DefaultElement(persistenceClass.getClassName());

            //ID
            String nameFieldId = persistenceClass.getPkField().getFieldName();
            DefaultAttribute attrId = new DefaultAttribute(nameFieldId, BeanUtils.getProperty(t, nameFieldId));
            element.add(attrId);

            //ELEMENT
            for (PersistenteField pf : persistenceClass.getPersistenteFields()) {
                DefaultElement e = new DefaultElement(pf.getFieldName());

                e.setText(BeanUtils.getProperty(t, pf.getFieldName()));

                element.add(e);
            }

            return element;

        } catch (Exception ex) {
            throw new XmlDBRuntimeException(ex);
        }
    }

    public T trasformModel(Element element) {
        try {
            T t = (T) persistenceClass.getClazz().newInstance();

            Iterator<Attribute> itAttr = element.attributeIterator();
            Map<String, String> bean = new HashMap<String, String>();
            while (itAttr.hasNext()) {
                Attribute a = itAttr.next();
                bean.put(a.getName(), a.getValue());
            }

            Iterator<Element> it = element.elementIterator();
            while (it.hasNext()) {
                Element e = it.next();
                bean.put(e.getName(), e.getText());
            }
            //TODO settare le collection in automatico
            Map<String,String> properties = new HashMap<String, String>();
            for(String property:bean.keySet()){
                Class c = PropertyUtils.getPropertyType(t, property);
                LogHelper.debug("---------------the field "+property +" is "+c,this.getClass());
                if(java.util.Collection.class.isAssignableFrom(c)){
                    LogHelper.debug("---------------the field "+property +" is collection---------",this.getClass());
                    //bean.remove(property);
                }else{
                    properties.put(property, bean.get(property));
                }
            }
            BeanUtils.populate(t, properties);

            return t;
        } catch (Exception e) {
            throw new XmlDBRuntimeException(e);
        }
    }
}
