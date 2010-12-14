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

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

import xmldb.XmlDBConstants;
import xmldb.exception.XmlDBException;

/**
 * 
 * @author Giacomo Stefano Gabriele
 *
 */
public class ReflectionUtils {

    private static final Logger logger = Logger.getLogger(ReflectionUtils.class);
    /**
     * Ritorna il valore del field dell'oggetto obj
     * @param field
     * @param obj
     * @return the value
     */
    public static Object getValue(Field field,Object obj){
        try{
            field.setAccessible(true);

            Object o = field.get(obj);
            if (o instanceof Date) {
                Date date = (Date) o;
                return String.valueOf(date.getTime());
            }
            return o;
        }catch(Exception e){
            throw new XmlDBException(e);
        }
    }
    /**
     * Set the value into object's field
     * @param field
     * @param obj
     * @param value
     */
    public static void setValue(Field field,Object obj,Object value){
        try{
            field.setAccessible(true);
            value = getValue(field.getType(), value);
            field.set(obj, value);
        }catch(Exception e){
            throw new XmlDBException(e);
        }
    }
    /**
     * Converte il valore in tipo di
     * @param clazz
     * @param value
     * @return
     */
    protected static Object getValue(Class<?> clazz, Object value) {
        if (clazz.toString().equals("int")) {
            return Integer.parseInt(String.valueOf(value));
        }
        if (clazz.toString().equals("boolean")) {
            return Boolean.parseBoolean(String.valueOf(value));
        }
        if (clazz.toString().equals("double")) {
            return Double.parseDouble(String.valueOf(value));
        }
        if (clazz.toString().equals("float")) {
            return Float.parseFloat(String.valueOf(value));
        }if (clazz.equals(Date.class)) {
            Timestamp st = new Timestamp(Long.parseLong(String.valueOf(value)));
            return new Date(st.getTime());
        }
        try{
            return clazz.cast(String.valueOf(value));
        }catch(ClassCastException e){
            return clazz.cast(value);
        }
    }


    /**
     * Attempts to list all the classes in the specified package as determined
     * by the context class loader
     * 
     * @param pckgname the package name to search
     * @param all if annotated with annotation {@link Entity}
     * @return a list of classes that exist within that package
     * @throws ClassNotFoundException if something went wrong
     */
    public static List<Class<?>> getClassesForPackage(String pckgname, boolean all) throws ClassNotFoundException {
        // This will hold a list of directories matching the pckgname. There may be more than one if a package is split over multiple jars/paths
        ArrayList<File> directories = new ArrayList<File>();
        try {
            ClassLoader cld = Thread.currentThread().getContextClassLoader();
            if (cld == null) {
                throw new ClassNotFoundException("Can't get class loader.");
            }
            String path = pckgname.replace('.', '/');
            // Ask for all resources for the path
            Enumeration<URL> resources = cld.getResources(path);
            while (resources.hasMoreElements()) {
                directories.add(new File(URLDecoder.decode(resources.nextElement().getPath(), XmlDBConstants.UTF8)));
            }
        } catch (NullPointerException x) {
            throw new ClassNotFoundException(pckgname + " does not appear to be a valid package (Null pointer exception)");
        } catch (UnsupportedEncodingException encex) {
            throw new ClassNotFoundException(pckgname + " does not appear to be a valid package (Unsupported encoding)");
        } catch (IOException ioex) {
            throw new ClassNotFoundException("IOException was thrown when trying to get all resources for " + pckgname);
        }

        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        // For every directory identified capture all the .class files
        for (File directory : directories) {
            if (directory.exists()) {
                // Get the list of the files contained in the package
                String[] files = directory.list();
                for (String file : files) {
                    // we are only interested in .class files
                    if (file.endsWith(".class")) {
                        // removes the .class extension
                        Class<?> classe = Class.forName(pckgname + '.' + file.substring(0, file.length() - 6));
                        if (AnnotationHelper.hasAnnotationEntity(classe) || all) {
                            classes.add(classe);
                        }
                    }
                }
            } else {
                return getClassFromJar(pckgname, directory.getPath(), all);
            }
        }
        return classes;
    }

    public static List<Class<?>> getClassFromJar(String packname, String path, boolean all) throws ClassNotFoundException {
        if(logger.isDebugEnabled()){
            logger.debug("path\t:"+path);
            logger.debug("packname\t:"+packname);
            logger.debug("all\t:"+all);
        }
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        path = path.replace("!/"+packname.replace(".", "\\"), "");
        path = path.replace("!\\"+packname.replace(".", "\\"), "");//windows
        path = path.replace("file:/", "");
        path = path.replace("file:\\", "");//windows
        logger.debug("path---->"+path);
        try {
            File file = new File(path);
            JarFile currentFile = new JarFile(file, true, JarFile.OPEN_READ);
           
            ClassLoader cld = Thread.currentThread().getContextClassLoader();


            for (Enumeration e = currentFile.entries(); e.hasMoreElements();) {
                JarEntry current = (JarEntry) e.nextElement();
                if (!current.isDirectory() && current.getName().startsWith(packname.replace(".", "/"))) {
                    if(logger.isDebugEnabled()){
                        logger.debug("Jar Class: " +current);
                    }
                    Class<?> classe = Class.forName(current.getName().replaceAll("/", ".").replace(".class", ""),false, cld);
                    if (AnnotationHelper.hasAnnotationEntity(classe) || all) {
                        classes.add(classe);
                    }
                }

            }
        } catch (Exception e) {
            throw new ClassNotFoundException(packname + " (" + path + ") does not appear to be a valid package", e);
        }
        return classes;
    }


//    public static List<Class<? extends Object>> getClassesFromFileJarFile(String pckgname, String baseDirPath) throws ClassNotFoundException {
//        ArrayList<Class<? extends Object>> classes = new ArrayList<Class<? extends Object>>();
//        String path = pckgname.replace('.', '/') + "/";
//        File mF = new File(baseDirPath);
//        String[] files = mF.list();
//        ArrayList jars = new ArrayList();
//        for (int i = 0; i < files.length; i++) {
//            if (files[i].endsWith(".jar")) {
//                jars.add(files[i]);
//            }
//        }
//
//        for (int i = 0; i < jars.size(); i++) {
//            try {
//                JarFile currentFile = new JarFile(jars.get(i).toString());
//                for (Enumeration e = currentFile.entries(); e.hasMoreElements();) {
//                    JarEntry current = (JarEntry) e.nextElement();
//                    if (current.getName().length() > path.length() && current.getName().substring(0, path.length()).equals(path) && current.getName().endsWith(".class")) {
//                        classes.add(Class.forName(current.getName().replaceAll("/", ".").replace(".class", "")));
//                    }
//                }
//            } catch (IOException e) {
//                logger.error("Errore Inatteso", e);
//            }
//        }
//        return classes;
//    }
}
