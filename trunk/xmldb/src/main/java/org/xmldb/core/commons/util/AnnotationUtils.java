/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.xmldb.core.commons.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class AnnotationUtils {

    public static List<Field> getAnnotatedMethods(Class clazz, Class<? extends Annotation>... annotation) {
        List<Field> fields = new ArrayList<Field>();

        for(Class<? extends Annotation> x:annotation){
            fields.addAll(getAnnotatedMethods(clazz, x));
        }

        return fields;
    }

    public boolean isAnnotatedBy(AnnotatedElement annotatedElement,Class<? extends Annotation> annotation){
        return annotatedElement.isAnnotationPresent(annotation);
    }

    protected static List<Field> getAnnotatedMethods(Class clazz, Class<? extends Annotation>annotation){
        List<Field> fields = new ArrayList<Field>();
        for(Field f:clazz.getDeclaredFields()){
            if(f.isAnnotationPresent(annotation)){
                fields.add(f);
            }
        }
        return fields;
    }
}
