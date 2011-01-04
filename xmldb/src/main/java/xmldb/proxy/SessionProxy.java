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
package xmldb.proxy;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import xmldb.configuration.AnnotationScanner;
import xmldb.util.AnnotationHelper;
import xmldb.util.ClassHelper;
import xmldb.util.ReflectionUtils;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class SessionProxy extends SessionAbstractProxy {

    

    @Override
    public synchronized <T> T merge(Object o, boolean lazy) {
        controllaRelazioni(o, lazy, true);
        return (T)super.merge(o, lazy);
    }

    @Override
    public synchronized void persist(Object o, boolean lazy) {
        controllaRelazioni(o, lazy, false);
        super.persist(o, lazy);
    }
    
    protected void controllaRelazioni(Object o,boolean lazy,boolean merge){
        try {
            if (!lazy) {
                checkRelationsManyToOne(o, false);
                checkRelationsOneToMany(o, false);
            }
        } catch (ClassNotFoundException e) {
            logger.error("Errore Classe non trovata", e);
        }
    }

    protected void checkRelationsManyToOne(Object obj,boolean merge) {

        AnnotationScanner as = AnnotationHelper.get().get(obj.getClass());
        for (Field field : as.getFieldsManyToOne()) {
            Object padre = ReflectionUtils.getValue(field, obj);
            if(padre!=null){
                if(merge){
                    session.merge(padre, true);
                }else{
                    session.persist(padre, true);
                }
                ReflectionUtils.setValue(field, obj, padre);
            }
        }
    }

    protected void checkRelationsOneToMany(Object obj, boolean merge) throws ClassNotFoundException {
        if (obj == null) {
            return;
        }
        AnnotationScanner as = AnnotationHelper.get().get(obj.getClass());
        if (logger.isDebugEnabled()) {
            logger.debug("checkRelationsOneToMany class " + obj.getClass());
        }

        for (Field field : as.getFieldsOneToMany()) {
            Collection<?> children = (Collection<?>) ReflectionUtils.getValue(field, obj);
            for (Iterator it = children.iterator(); it.hasNext();) {
                Object child = it.next();
                if (logger.isDebugEnabled()) {
                    logger.debug("Class child " + child.getClass());
                }
                AnnotationScanner a = AnnotationHelper.get().get(child.getClass());
                for (Field fChild : a.getFieldsManyToOne()) {
                    if (fChild.getType().equals(ClassHelper.getClass(obj))) {
                        logger.info("Setto al figlio ["+fChild+"] il padre ["+ClassHelper.getClass(obj)+"]");
                        ReflectionUtils.setValue(fChild, child, obj);
                        break;
                    }
                }
                if(merge){
                    session.merge(child, true);
                }else{
                    session.persist(child, true);
                }
            }
            ReflectionUtils.setValue(field, obj, children);
        }
    }
}
