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
package xmldb.configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import xmldb.annotation.Attribute;
import xmldb.annotation.Entity;
import xmldb.annotation.ID;
import xmldb.annotation.ManyToOne;
import xmldb.annotation.OneToMany;
import xmldb.exception.AnnotationRequiredException;
import xmldb.exception.XmlDBException;

/**
 *
 * @author Giacomo Stefamo Gabriele
 */
public class AnnotationScanner {

    private static final Logger logger = Logger.getLogger(AnnotationScanner.class);
    private Class<?> classe;
    private String nameEntity = null;
    private PairAnnotationField id;
    private boolean sequence = false;
    private List<PairAnnotationField> attributes = new LinkedList<PairAnnotationField>();
    private List<PairAnnotationField> attributesManyToOne = new LinkedList<PairAnnotationField>();
    private List<PairAnnotationField> attributesOneToMany = new LinkedList<PairAnnotationField>();
    private Field[] fields;
    private Field[] fieldsManyToOne;
    private Field[] fieldsOneToMany;
    private Class<?> enhancerClass;
    

    public AnnotationScanner(Class<?> classe) throws AnnotationRequiredException {
        this.classe = classe;
        scan();
        logger.info("AnnatationScanner\n" + this);
    }

    public String getNameEntity() {
        return nameEntity;
    }

    public Field getId() {
        return id.getField();
    }

    public Class<?> getClasse() {
        return classe;
    }

    public boolean isSequence() {
        return sequence;
    }

    public Field[] getAttributes() {
        if (fields == null) {
            fields = new Field[attributes.size()];
            int i = 0;
            for (PairAnnotationField pair : attributes) {
                fields[i++] = pair.getField();
            }
        }
        return fields;
    }

    public Field[] getFieldsManyToOne() {
        if (fieldsManyToOne == null) {
            fieldsManyToOne = new Field[attributesManyToOne.size()];
            int i = 0;
            for (PairAnnotationField pair : attributesManyToOne) {
                fieldsManyToOne[i++] = pair.getField();
            }
        }
        return fieldsManyToOne;
    }

    public Field[] getFieldsOneToMany() {
        if (fieldsOneToMany == null) {
            fieldsOneToMany = new Field[attributesOneToMany.size()];
            int i = 0;
            for (PairAnnotationField pair : attributesOneToMany) {
                fieldsOneToMany[i++] = pair.getField();
            }
        }
        return fieldsOneToMany;
    }

    public Attribute getAnnotationAttribute(Field field) {
        for (PairAnnotationField pair : attributes) {
            if (pair.getField().equals(field)) {
                return (Attribute) pair.getAnnotation();
            }
        }
        return null;
    }

    public boolean isAnnotatedWithAttribute(String property){
        for(Field field:getAttributes()){
            if(field.getName().equalsIgnoreCase(property)){
                return true;
            }
        }
        return false;
    }

    public boolean isAnnotatedWithID(String property){
        return id.getField().getName().equalsIgnoreCase(property);
    }

    public Attribute getAnnotation(String property){
        for(Field field:getAttributes()){
            if(field.getName().equalsIgnoreCase(property)){
                return getAnnotationAttribute(field);
            }
        }
        return null;
    }

    public Field getField(String property){
        for(Field field:getAttributes()){
            if(field.getName().equalsIgnoreCase(property)){
                return field;
            }
        }
        throw new XmlDBException("Il campo ["+property+"] non esiste");
    }

    public ManyToOne getAnnotationManyToOne(Field field) {
        for (PairAnnotationField pair : attributesManyToOne) {
            if (pair.getField().equals(field)) {
                return (ManyToOne) pair.getAnnotation();
            }
        }
        return null;
    }

    public OneToMany getAnnotationOneToMany(Field field) {
        for (PairAnnotationField pair : attributesOneToMany) {
            if (pair.getField().equals(field)) {
                return (OneToMany) pair.getAnnotation();
            }
        }
        return null;
    }

    protected final void scan() throws AnnotationRequiredException {
        scanNameEntity();
        scanId();
        scanAttributes();
        scanFieldsManyToOne();
        scanFieldsOneToMany();
    }

    protected void scanNameEntity() throws AnnotationRequiredException {
        boolean present = classe.isAnnotationPresent(Entity.class);
        if (!present && !classe.getSuperclass().equals(Object.class)) {
            present = classe.getSuperclass().isAnnotationPresent(Entity.class);
        }
        if (!present) {
            throw new AnnotationRequiredException(classe, Entity.class);
        }
        Entity entity = classe.getAnnotation(Entity.class);
        if (entity == null) {
            entity = classe.getSuperclass().getAnnotation(Entity.class);
        }
        nameEntity = entity.name();
    }

    protected void scanId() throws AnnotationRequiredException {
        boolean find = false;
        for (Field field : classe.getDeclaredFields()) {
            if (field.isAnnotationPresent(ID.class)) {
                find = true;
                ID idx = field.getAnnotation(ID.class);
                id = new PairAnnotationField(idx, field);

                sequence = idx.sequence();
                break;
            }
        }
        if (!find) {
            for (Field field : classe.getSuperclass().getDeclaredFields()) {
                if (field.isAnnotationPresent(ID.class)) {
                    find = true;
                    ID idx = field.getAnnotation(ID.class);
                    id = new PairAnnotationField(idx, field);

                    sequence = idx.sequence();
                    break;
                }
            }
        }
        if (!find) {
            throw new AnnotationRequiredException(classe, ID.class);
        }
    }

    protected void scanAttributes() throws AnnotationRequiredException {
        for (Field field : classe.getDeclaredFields()) {
            if (field.isAnnotationPresent(Attribute.class)) {
                Attribute att = field.getAnnotation(Attribute.class);
                PairAnnotationField pair = new PairAnnotationField(att, field);
                attributes.add(pair);
            }
        }
        //cerco gli attributi della super classe
        for (Field field : classe.getSuperclass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Attribute.class)) {
                Attribute att = field.getAnnotation(Attribute.class);
                PairAnnotationField pair = new PairAnnotationField(att, field);
                attributes.add(pair);
            }
        }
    }

    protected void scanFieldsManyToOne() throws AnnotationRequiredException {
        for (Field field : classe.getDeclaredFields()) {
            if (field.isAnnotationPresent(ManyToOne.class)) {
                ManyToOne att = field.getAnnotation(ManyToOne.class);
                PairAnnotationField pair = new PairAnnotationField(att, field);
                attributesManyToOne.add(pair);
            }
        }

        //cerco gli attributi della super classe
        for (Field field : classe.getSuperclass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ManyToOne.class)) {
                ManyToOne att = field.getAnnotation(ManyToOne.class);
                PairAnnotationField pair = new PairAnnotationField(att, field);
                attributesManyToOne.add(pair);
            }
        }
    }

    protected void scanFieldsOneToMany() throws AnnotationRequiredException {
        for (Field field : classe.getDeclaredFields()) {
            if (field.isAnnotationPresent(OneToMany.class)) {
                OneToMany att = field.getAnnotation(OneToMany.class);
                PairAnnotationField pair = new PairAnnotationField(att, field);
                attributesOneToMany.add(pair);
            }
        }

        //cerco gli attributi della super classe
        for (Field field : classe.getSuperclass().getDeclaredFields()) {
            if (field.isAnnotationPresent(OneToMany.class)) {
                OneToMany att = field.getAnnotation(OneToMany.class);
                PairAnnotationField pair = new PairAnnotationField(att, field);
                attributesOneToMany.add(pair);
            }
        }
    }

    public Class<?> getEnhancerClass() {
        return enhancerClass;
    }

    public void setEnhancerClass(Class<?> enhancerClass) {
        this.enhancerClass = enhancerClass;
    }

    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Classe:").append(classe);
        sb.append("\nNome Entity:").append(nameEntity);
        sb.append("\n\tID:").append(id);
        for (PairAnnotationField pair : attributes) {
            sb.append("\n\tAttribute:").append(pair);
        }
        for (PairAnnotationField pair : attributesManyToOne) {
            sb.append("\n\tAttribute Many To One:").append(pair);
        }
        for (PairAnnotationField pair : attributesOneToMany) {
            sb.append("\n\tAttribute One To Many:").append(pair);
        }
        return sb.toString();
    }

    protected final class PairAnnotationField {

        private Annotation annotation;
        private Field field;

        public PairAnnotationField(Annotation annotation, Field field) {
            this.annotation = annotation;
            this.field = field;
        }

        public Annotation getAnnotation() {
            return annotation;
        }

        public void setAnnotation(Annotation annotation) {
            this.annotation = annotation;
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }

        @Override
        public String toString() {
            return "{" + "annotation=" + annotation + ",field=" + field + '}';
        }
    }
}
