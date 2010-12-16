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
package xmldb.session.impl;

import java.util.Set;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.Node;
import xmldb.Trasformers;
import xmldb.configuration.AnnotationScanner;
import xmldb.exception.XmlDBException;
import xmldb.util.AnnotationHelper;
import xmldb.util.ClassHelper;
import xmldb.util.ReflectionUtils;
import xmldb.util.TrasformerUtil;

/**
 *
 * @author Giacomo Stefamo Gabriele
 */
public class AnnotationSession extends AbstractSession {

    private static final Logger logger = Logger.getLogger(AnnotationSession.class);
    private Set<Class<?>> mapping;

    public AnnotationSession() {
        
    }
    

    public void setMapping(Set<Class<?>> mapping) {
        this.mapping = mapping;
        if (logger.isDebugEnabled()) {
            logger.debug("Mapping " + mapping);
        }
    }

    @Override
    public Element findElement(Class<?> clazz, Object id) {

        if(logger.isDebugEnabled()){
            logger.debug("input classe\t"+clazz);
            logger.debug("input Id\t"+id);
        }

        String xPath = getXpathByID(clazz, id);
        Node node = document.selectSingleNode(xPath);
        if (logger.isDebugEnabled()) {
            logger.debug("Xpath [" + xPath + "]");
        }
        if(node == null){
            return null;
        }
        return (Element) node;
    }

    @Override
    protected Object getObjectId(Object obj) {
        if(logger.isDebugEnabled()){
            logger.debug("input Obj\t"+obj);
        }

        try{
            Class classe = ClassHelper.getClass(obj);
            AnnotationScanner as = AnnotationHelper.get().get(classe);
            return ReflectionUtils.getValue(as.getId(), obj);
        }catch(ClassNotFoundException e){
            logger.error("La classe [" + obj.getClass() + "] non e' stata trovata!!!!", e);
            throw new XmlDBException("La classe [" +  obj.getClass() + "] non e' stata trovata!!!!", e);
        }
    }


     @Override
    protected String getXpathByID(Class<?> clazz, Object id) {

         if(logger.isDebugEnabled()){
            logger.debug("input classe\t"+clazz);
            logger.debug("input Id\t"+id);
        }
        try{
            clazz = ClassHelper.getClass(clazz.getName());
            AnnotationScanner as = AnnotationHelper.get().get(clazz);
            StringBuilder sb = new StringBuilder();
            sb.append("//");
            sb.append(as.getNameEntity());
            sb.append("[@").append(as.getId().getName()).append("='");
            sb.append(String.valueOf(id)).append("']");

            String xPath = sb.toString();

            return xPath;
         }catch(ClassNotFoundException e){
            logger.error("La classe [" + clazz+ "] non e' stata trovata!!!!", e);
            throw new XmlDBException("La classe [" +  clazz+ "] non e' stata trovata!!!!", e);
        }
    }

    @Override
    protected void controllaClasse(Class<?> classe) {
        if(logger.isDebugEnabled()){
            logger.debug("input classe\t"+classe);
        }
        try{
            classe = ClassHelper.getClass(classe.getName());
            if (!mapping.contains(classe)) {
                throw new XmlDBException("La classe [" + classe + "] non e' mappata!!!!");
            }
        }catch(ClassNotFoundException e){
            throw new XmlDBException("La classe [" + classe + "] non e' mappata!!!!",e);
        }
    }

    @Override
    protected Trasformers<?> getTrasformers(Class<?> clazz, boolean lazy) {
        return TrasformerUtil.getAnnotationTrasformer(clazz, this, lazy);
    }

    @Override
    protected String getNameEntity(Class<?> clazz) {
        if(logger.isDebugEnabled()){
            logger.debug("input classe\t"+clazz);
        }
       try{
           clazz = ClassHelper.getClass(clazz.getName());
            AnnotationScanner as = AnnotationHelper.get().get(clazz);
            return as.getNameEntity();
        }catch(ClassNotFoundException e){
            logger.error("La classe [" + clazz + "] non e' stata trovata!!!!", e);
            throw new XmlDBException("La classe [" + clazz + "] non e' stata trovata!!!!", e);
        }
    }
}
