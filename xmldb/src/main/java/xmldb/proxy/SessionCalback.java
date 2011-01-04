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
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.log4j.Logger;
import xmldb.Session;
import xmldb.configuration.AnnotationScanner;
import xmldb.util.AnnotationHelper;
import xmldb.util.ClassHelper;
import xmldb.util.ReflectionUtils;

/**
 * Intercettore sulla {@link Session}
 * @author Giacomo Stefamo Gabriele
 * @deprecated 
 */
public class SessionCalback implements MethodInterceptor {

    private static final Logger logger = Logger.getLogger(SessionCalback.class);

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        if ((method.getName().equals("persist") || method.getName().equals("merge")) && args.length == 2) {
            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("input Method " + method);
                    logger.debug("input Args " + Arrays.toString(args));
                }
                if (Boolean.FALSE.equals(args[1])) {
                    checkRelationsManyToOne(obj, args[0], proxy);
                    checkRelationsOneToMany(obj, args[0], proxy);
                }
            } catch (Exception e) {
                logger.error("Errore", e);
            }
        }
        if (method.getName().equalsIgnoreCase("load") && args.length == 2) {
            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("args " + Arrays.toString(args));
                    logger.debug("metodh " + method);
                }


            } catch (Exception e) {
                logger.error("Error", e);
            }
        }
        return proxy.invokeSuper(obj, args);
    }

    protected void checkRelationsManyToOne(Object session, Object obj, MethodProxy proxy) throws Throwable {

        AnnotationScanner as = AnnotationHelper.get().get(obj.getClass());
        for (Field field : as.getFieldsManyToOne()) {
            Object padre = ReflectionUtils.getValue(field, obj);
            if(padre!=null){
                proxy.invokeSuper(session, new Object[]{padre, true});
                ReflectionUtils.setValue(field, obj, padre);
            }
        }
    }

    protected void checkRelationsOneToMany(Object session, Object obj, MethodProxy proxy) throws Throwable {
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
                proxy.invokeSuper(session, new Object[]{child, true});
            }
            ReflectionUtils.setValue(field, obj, children);
        }
    }
}
