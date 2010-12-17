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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import xmldb.annotation.Entity;

import xmldb.configuration.AnnotationScanner;
import xmldb.exception.AnnotationRequiredException;
import xmldb.exception.XmlDBException;

/**
 * 
 * @author Giacomo Stefano Gabriele
 *
 */
public class AnnotationHelper {

    private static final Logger logger = Logger.getLogger(AnnotationHelper.class);

    private Map<Class<?>,AnnotationScanner> classi = new HashMap<Class<?>, AnnotationScanner>();

    private static AnnotationHelper instance;

    private AnnotationHelper(){
    }

    public synchronized  static AnnotationHelper get(){
        if(instance == null){
            instance = new AnnotationHelper();
        }
        return instance;
    }

    public void add(Class<?> classe){
        try {
            classi.put(classe, new AnnotationScanner(classe));
        } catch (AnnotationRequiredException ex) {
            logger.error("Errore add Mappings",ex);
            throw new XmlDBException(ex);
        }
    }

    public AnnotationScanner get(Class<?>classe){
        try{
            return classi.get(ClassHelper.getClass(classe.getName()));
        }catch(ClassNotFoundException e){
            logger.error("La classe [" + classe + "] non e' stata trovata!!!!", e);
            throw new XmlDBException("La classe [" + classe + "] non e' stata trovata!!!!", e);
        }
    }

    /**
     * Verifica se la classe passata ha l'annotazione {@link Entity}
     * @param clazz
     * @return
     */
    public static boolean hasAnnotationEntity(Class<?> clazz) {
        return clazz.isAnnotationPresent(Entity.class);

    }

    public Set<Class<?>> getClasses(){
        return classi.keySet();
    }

    public static Class<?> getClass(Object o){
        
//        for(AnnotationScanner as :instance.classi.values()){
//            if(as.getEnhancerClass()!=null && as.getEnhancerClass().equals(o.getClass())){
//                return as.getClasse();
//            }
//        }
        for(Class<?> clazz:instance.getClasses()){
            if(o.getClass().isInstance(clazz)){
                return clazz;
            }
        }
        
        return o.getClass();
    }
}
