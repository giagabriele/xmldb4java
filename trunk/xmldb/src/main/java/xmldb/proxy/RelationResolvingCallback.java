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
import org.dom4j.Element;
import xmldb.annotation.ManyToOne;
import xmldb.annotation.OneToMany;
import xmldb.configuration.AnnotationScanner;
import xmldb.criteria.Criteria;
import xmldb.criteria.Restrictions;
import xmldb.exception.ElementNotFoundException;
import xmldb.exception.XmlDBException;
import xmldb.session.impl.AbstractSession;
import xmldb.util.AnnotationHelper;
import xmldb.util.DocumentUtil;
import xmldb.util.ReflectionUtils;

/**
 *
 * @author Giacomo Stefamo Gabriele
 */
public class RelationResolvingCallback extends AbstractCallback {

    private AbstractSession session;

    public RelationResolvingCallback(Class<?> targetClass, AbstractSession session) {
        super(targetClass);
        this.session = session;
    }

    @Override
    public <T> T intercept(Object obj, Field field, Object value) throws Throwable {

        AnnotationScanner as = AnnotationHelper.get().get(obj.getClass());
        OneToMany oneToMany = as.getAnnotationOneToMany(field);
        if(oneToMany!=null){
            if(logger.isDebugEnabled()){
                logger.debug("Annotation OneToMany " + oneToMany);
            }
            return (T)checkOneToMany(obj, oneToMany);
        }
        ManyToOne manyToOne = as.getAnnotationManyToOne(field);
        if(manyToOne!=null){
            if(logger.isDebugEnabled()){
                logger.debug("Annotation ManyToOne " + manyToOne);
            }
            return (T)checkManyToOne(obj);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    protected <T> T checkOneToMany(Object obj, OneToMany oneToMany) {

        AnnotationScanner as = AnnotationHelper.get().get(targetClass);

        Class<?> classeFiglio = oneToMany.classe();
        Criteria criteria = Criteria.createCriteria(classeFiglio);
        Object idPadre = ReflectionUtils.getValue(as.getId(), obj);
        criteria.add(Restrictions.propertyEq(as.getNameEntity(), idPadre));
        return (T) session.find(criteria);
    }

    protected <T> T checkManyToOne(T t) {
        //carico il padre
        //relazioni ManyToOne
        if(logger.isDebugEnabled()){
            logger.debug("input T:"+t);
        }

        try {
            AnnotationScanner as = AnnotationHelper.get().get(targetClass);
            Criteria criteria = Criteria.createCriteria(t.getClass());
            Object id = ReflectionUtils.getValue(as.getId(), t);
            criteria.add(Restrictions.idEq(id));

            Element element = DocumentUtil.findElementById(session.getDocument(), criteria);
            for(Field field:as.getFieldsManyToOne()){
                Class<?> classePadre = field.getType();

                if(logger.isDebugEnabled()){
                    logger.debug("classe padre:"+classePadre);
                }
                AnnotationScanner asPadre = AnnotationHelper.get().get(classePadre);
                Object o = session.load(classePadre, element.attributeValue(asPadre.getNameEntity()), true);
                 if(logger.isDebugEnabled()){
                    logger.debug("object padre:"+o);
                }
                return (T)o;
//                field.setAccessible(true);
//                field.set(t, o);
            }
        } catch (ElementNotFoundException e) {
            throw new XmlDBException(e);
//        } catch (IllegalAccessException e) {
//            throw new XmlDBException(e);
        } catch (IllegalArgumentException e) {
            throw new XmlDBException(e);
        }
        return null;
    }
}
