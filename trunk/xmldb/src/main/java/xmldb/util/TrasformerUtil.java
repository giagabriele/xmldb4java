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

import java.lang.reflect.Field;
import org.apache.log4j.Logger;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import xmldb.Trasformers;
import xmldb.configuration.AnnotationScanner;
import xmldb.exception.XmlDBException;
import xmldb.proxy.EnhancerHelper;
import xmldb.proxy.RelationResolvingCallback;
import xmldb.session.impl.AbstractSession;
import xmldb.type.Sequence;

/**
 * 
 * @author Giacomo Stefano Gabriele
 * 
 */
public class TrasformerUtil {
    protected static final Logger logger = Logger.getLogger(TrasformerUtil.class);
   
    @SuppressWarnings("unchecked")
    public static Trasformers getAnnotationTrasformer(Class<?> classe, AbstractSession session) {
        return new AnnotationTrasformer(classe, session);
    }

    public static Trasformers<Object> getAnnotationTrasformer(Class<?> classe, AbstractSession session, boolean lazy) {
        return new AnnotationTrasformer(classe, session, lazy);
    }

    /**
     * Classe statica. Implementazione di un Trasformer con Reflection
     * @author Giacomo Stefano Gabriele
     */
    static final class AnnotationTrasformer implements Trasformers<Object> {

        private Class<? extends Object> classe;
        private AbstractSession session;
        private boolean lazy = false;

        /**
         * Costruttore deprecato
         * @param classe
         * @deprecated usa un altro costruttore
         * @see AnnotationTrasformer#AnnotationTrasformer(Class, Session)
         * @see AnnotationTrasformer#AnnotationTrasformer(Class, Session, boolean)
         */
        @Deprecated
        public AnnotationTrasformer(Class<? extends Object> classe) {
            this.classe = classe;
        }

        public AnnotationTrasformer(Class<? extends Object> classe, AbstractSession session) {
            this(classe, session, false);
        }

        public AnnotationTrasformer(Class<? extends Object> classe, AbstractSession session, boolean lazy) {
            this.classe = classe;
            this.session = session;
            this.lazy = lazy;
        }

        @Override
        public Element trasformElement(Object t) {
            if (t == null) {
                return null;
            }
            try {
                AnnotationScanner as = AnnotationHelper.get().get(classe);

                String nameElement = as.getNameEntity();
                if(logger.isDebugEnabled()){
                    logger.debug("nameElement\t"+nameElement);
                }
                DefaultElement element = new DefaultElement(nameElement);

                //add field id
                //FIX Setto la sequence nella classe TransactionSession nei metodi persist e merge
                String campoId = as.getId().getName();
                
                element.addAttribute(campoId, String.valueOf(ReflectionUtils.getValue(as.getId(), t)));

                //add other field
                for(Field field:as.getAttributes()){
                    String text = String.valueOf(ReflectionUtils.getValue(field, t));
                    switch (as.getAnnotationAttribute(field).tipo()) {
                        case ELEMENT:
                            DefaultElement n = new DefaultElement(field.getName());
                            n.setText(text);
                            element.add(n);
                            break;
                        case ELEMENT_CDATA:
                            DefaultElement n2 = new DefaultElement(field.getName());
                            n2.addCDATA(text);
                            element.add(n2);
                            break;
                        default:
                            element.addAttribute(field.getName(), text);
                            break;
                    }
                }

                //check relazioni ManyToOne
                //scrivo il padre con l'attributo di nome il valore dell'annotazione @Entity sul padre e come
                //valore il valore del campo id su quale è annotata con l'annotazione id
                for(Field field:as.getFieldsManyToOne()){
                    Object padre = ReflectionUtils.getValue(field, t);
                    if(padre !=null){
                        AnnotationScanner a = AnnotationHelper.get().get(padre.getClass());
                        Field fieldPadreId = a.getId();
                        element.addAttribute(a.getNameEntity(), String.valueOf(ReflectionUtils.getValue(fieldPadreId, padre)));
                    }

                }
                if(logger.isDebugEnabled()){
                    logger.debug("trasformElement success");
                }

                return element;
            } catch (Exception e) {
                throw new XmlDBException(e);
            } 

        }

        @SuppressWarnings("unchecked")
        @Override
        public Object trasformModel(Element element) {
            if (element == null) {
                return null;
            }
            try {
                AnnotationScanner as = AnnotationHelper.get().get(classe);

                //creo l'oggetto con un proxy

                if(logger.isDebugEnabled()){
                    logger.debug("trasformModel Class: "+classe);
                }

                Object obj = null;
                if(classe.equals(Sequence.class)){
                    obj = classe.newInstance();
                }else{
                    obj = EnhancerHelper.createProxy(classe, new RelationResolvingCallback(classe, session));
                    as.setEnhancerClass(obj.getClass());
                }


                //add field id
                ReflectionUtils.setValue(as.getId(), obj,element.attributeValue(as.getId().getName()));


                //add other field
                for(Field field:as.getAttributes()){
                    String valore = element.attributeValue(field.getName(), null);
                    if (valore == null) {
                        //vedo se è un element
                        valore = element.valueOf("./"+field.getName());
                    }
                    ReflectionUtils.setValue(field, obj, valore);
                }
                if(logger.isDebugEnabled()){
                    logger.debug("trasformModel success");
                }
                return obj;
            } catch (Exception e) {
                throw new XmlDBException(e);
            }
        }
    }
}
