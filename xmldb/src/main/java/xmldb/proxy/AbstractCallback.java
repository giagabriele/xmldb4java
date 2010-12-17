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
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.log4j.Logger;
import xmldb.util.ReflectionUtils;

/**
 * Intercettore astratto dei Model Bean
 * @author Giacomo Stefamo Gabriele
 */
public abstract class AbstractCallback implements MethodInterceptor {

    protected static final Logger logger = Logger.getLogger(AbstractCallback.class);
    protected Class<?> targetClass;

    public AbstractCallback(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (method.getName().startsWith("get") && method.getReturnType() != Void.TYPE) {
            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("Method " + method);
                    logger.debug("Class  " + obj.getClass());
                }
                String name = getNameField(method.getName());
                Field field = getField(obj, name);
                Object value = intercept(obj, field, obj);
                logger.info("Set to field ["+field+"] value ["+value+"]");
                ReflectionUtils.setValue(field, obj, value);
//                if (value != null) {
//                    field.setAccessible(true);
//                    field.set(obj, value);
//                }
            } catch (Exception e) {
                logger.error("Errore", e);
            }
        }
        return proxy.invokeSuper(obj, args);
    }

    protected String getNameField(String methodName) {
        String name = methodName.substring(3);
        name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
        if(logger.isDebugEnabled()){
            logger.debug("Field name " + name);
        }
        return name;
    }

    protected Field getField(Object obj, String name) throws NoSuchFieldException {
        try{
            return targetClass.getDeclaredField(name);
        }catch(NoSuchFieldException e){
            return targetClass.getSuperclass().getDeclaredField(name);
        }
    }
    /**
     * Metodo da implementare
     * @param <T>
     * @param obj
     * @param field
     * @param value
     * @return the object result
     * @throws Throwable
     */
    public abstract <T> T intercept(Object obj, Field field, Object value) throws Throwable;
}
