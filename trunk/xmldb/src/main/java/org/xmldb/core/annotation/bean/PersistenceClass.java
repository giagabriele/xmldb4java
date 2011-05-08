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
package org.xmldb.core.annotation.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.xmldb.core.exceptions.AnnotationRequiredException;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class PersistenceClass {

    private Class clazz;
    private String className;

    private PersistenteField pkField;
    private List<PersistenteField> persistenteFields;

    private Object objWrap;

    public PersistenceClass(Object objWrap) {
        this(objWrap.getClass());
        this.objWrap = objWrap;
    }


    public PersistenceClass(Class clazz) {
        this.clazz = clazz;

        if(!clazz.isAnnotationPresent(Entity.class)){
            throw new AnnotationRequiredException(clazz, Entity.class);
        }

        className = clazz.getSimpleName();
        
//        if(clazz.isAnnotationPresent(Table.class)){
//            Table table = (Table) clazz.getAnnotation(Table.class);
//            if(table.name()!=null && table.name().trim().length()>0){
//                className = table.name();
//            }
//        }

        persistenteFields = new ArrayList<PersistenteField>();
        for(Field field:clazz.getDeclaredFields()){
            if(field.isAnnotationPresent(Id.class)){
                pkField = new PersistenteField(field.getName());
            }else if(!field.isAnnotationPresent(Transient.class) && field.getModifiers()!=Modifier.TRANSIENT){
                persistenteFields.add(new PersistenteField(field.getName()));
            }
        }
    }


    public List<PersistenteField> getPersistenteFields() {
        return persistenteFields;
    }

    public PersistenteField getPkField() {
        return pkField;
    }

    public String getClassName() {
        return className;
    }

    public Class getClazz() {
        return clazz;
    }

    public Object getObjWrap() {
        return objWrap;
    }



    @Override
    public String toString() {
        return "PersistenceClass{" + "\nclazz=" + clazz + "\nclassName=" + className + "\npkField=" + pkField + "\npersistenteFields=" + persistenteFields + '}';
    }


}
