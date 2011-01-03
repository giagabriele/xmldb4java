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

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import xmldb.sessionfactory.AnnotationSessionFactory;
import xmldb.Session;
import xmldb.SessionFactory;
import xmldb.cache.Cache;
import xmldb.exception.XmlDBException;
import xmldb.interceptor.Interceptor;
import xmldb.util.ReflectionUtils;

/**
 * Classe di configurazione della {@link SessionFactory}<br>
 * Questa classe permette sia di impostare le propriet&aacute; a mano o di caricarle dal file<br>
 * <b>dbxml.properties</b> contenuto nel classpath<br>
 * 
 * Esempio:
 * <p>
 * 		<pre>
 * Configuration cfg = new Configuration();
 * cfg.buildConfiguration();
 * SessionFactory sessionFactory  = cfg.getSessionFactory();
 *
 * Session session  = sessionFactory.getSession();
 * </pre>
 * </p>
 *@author Giacomo Stefano Gabriele
 */
public class Configuration {

    /**
     * <b>xmldb.pathdb</b> Path del dbxml. Obbligatorio
     */
    public static final String PATH_DB_XML = "xmldb.pathdb";
    /**
     * <b>xmldb.encoding</b>Encoding del dbxml. Facoltativo default UTF-8
     */
    public static final String ENCODING_DB_XML = "xmldb.encoding";
    /**
     *<b>xmldb.package.mapping.model</b> Package dei model<br>
     * Possono essere più di uno separati da virgola
     */
    public static final String PACKAGE_MAPPING_MODEL = "xmldb.package.mapping.model";
    /**
     * <b>xmldb.interceptors</b> usata per aggiungere alla {@link Session} gli intercettori<br>
     * Devono essere mappati con package.nomeClasse separati da virgola
     */
    public static final String XML_DB_INTERCEPTORS = "xmldb.interceptors";
    /**
     * <b>xmldb.enable.cache</b> abilita la cache del db xml<br>
     * Se non presente la cache non sarà presente<br>
     * Se viene abilitata allora il sistema abiliter&acute; di default le transazioni
     * @see Cache
     * @deprecated 
     */
    public static final String XMLDB_ENABLE_CACHE = "xmldb.enable.cache";
    protected static final Logger logger = Logger.getLogger(Configuration.class);
    private Map<String, String> configuration = new HashMap<String, String>();

    /**
     * Costruttore di default
     */
    public Configuration() {
        super();
    }

    /**
     * Setta le property alla {@link Configuration}
     * @param propertyName nome della property (sono le costanti della classe)
     * @param value valore della property
     * @return {@link Configuration}
     */
    public Configuration setProperty(String propertyName, String value) {
        configuration.put(propertyName, value);
        return this;
    }

    /**
     * Ritorna la {@link SessionFactory} a seconda delle propiet&aacute;
     *
     * @return {@link SessionFactory}
     * @throws XmlDBException se qualche propiet&aacute;  obbligatoria non viene dichiarata oppure ci sono degli errori di configurazione
     */
    public SessionFactory getSessionFactory() {
        if (!configuration.containsKey(PATH_DB_XML)) {
            throw new XmlDBException("La proprieta' " + PATH_DB_XML + " non e' stata configurata");
        }
        try {

            //AnnotatedSessionFacotory
            AnnotationSessionFactory sessionFactory = null;
            //Creo la sessionFactory
            sessionFactory = (AnnotationSessionFactory) getAnnotatedSessionFactory(configuration.get(PATH_DB_XML));

            //imposto l'encoding se c'è
            setEncoding(sessionFactory);

            //setto i mapping
            setMappingModel(sessionFactory);

            //imposto l'autosave
            sessionFactory.setAutoSave(true);

            //setto gli intercettori
            setInterceptors(sessionFactory);

            //imposto la transazione
            sessionFactory.setTransaction(true);

            
            if (configuration.containsKey(XMLDB_ENABLE_CACHE)) {
                sessionFactory.setCache(Boolean.parseBoolean(configuration.get(XMLDB_ENABLE_CACHE)));
            }

            return sessionFactory;

        } catch (Exception e) {
            throw new XmlDBException("Errore configurazione", e);
        }
    }

    protected void setEncoding(AnnotationSessionFactory sessionFactory) {
        try {
            if (configuration.containsKey(ENCODING_DB_XML)) {
                Charset.forName(configuration.get(ENCODING_DB_XML));
                sessionFactory.setCodifica(configuration.get(ENCODING_DB_XML));
            }
        } catch (UnsupportedCharsetException e) {
            logger.warn("La codifica ["+configuration.get(ENCODING_DB_XML)+"] non è valida. Verrà utilizzata UTF-8!");
        }
    }

    /**
     * Carica il file di properties 'dbxml.properties' che si trova nel classpath<br>
     * @return la {@link Configuration}
     * @throws XmlDBException se non trova il file di properties
     */
    public Configuration buildConfiguration() {

        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("dbxml");
            Enumeration<String> keys = resourceBundle.getKeys();
//            StringBuilder sb = new StringBuilder();
            logger.info("Configuration properties......................................................................");
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                String value = resourceBundle.getString(key);
//                sb.append("[").append(key).append("]").append("->[").append(value).append("]\n");
                logger.info("["+key+"]-->["+value+"]");
                setProperty(key, value);
            }

            logger.info("..............................................................................................");

        } catch (MissingResourceException e) {
            throw new XmlDBException(e);
        }

        return this;
    }

    /**
     * setta i mapping alla sessionFactory<br>
     * La sessionFactory deve essere solo di tipo {@link AnnotatedSessionFactory}
     * @param sessionFactory {@link AnnotatedSessionFactory}
     */
    protected void setMappingModel(AnnotationSessionFactory sessionFactory) {
        if (!configuration.containsKey(PACKAGE_MAPPING_MODEL)) {
            throw new XmlDBException("La proprietà " + PACKAGE_MAPPING_MODEL + " non e' stata configurata");
        }
        try {
            String[] packages = configuration.get(PACKAGE_MAPPING_MODEL).split(",");
            List<Class<?>> classi = new LinkedList<Class<?>>();
            for (String pks : packages) {
                /*
                 * Deve essere true per le relazione padre figlio (extends)
                 */
                classi.addAll(ReflectionUtils.getClassesForPackage(pks, true));

                for (Class<? extends Object> classe : classi) {
                    sessionFactory.addMapping(classe);
                }
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Mappings:" + classi);
            }
        } catch (ClassNotFoundException e) {
            throw new XmlDBException(e);
        }
    }

    /**
     * Setta gli intercettori nella {@link SessionFactory} passata
     * @param sessionFactory
     */
    protected void setInterceptors(SessionFactory sessionFactory) {
        if (configuration.containsKey(XML_DB_INTERCEPTORS)) {
            String[] intercettori = configuration.get(XML_DB_INTERCEPTORS).split(",");
            for (String nameIntercettore : intercettori) {
                try {
                    Class<? extends Object> intercettoreClass = Class.forName(nameIntercettore);
                    Interceptor interceptor = (Interceptor) intercettoreClass.newInstance();
                    sessionFactory.addInterceptor(interceptor);
                } catch (ClassNotFoundException e) {
                    logger.error("Errore Classe non trovata", e);
                    throw new XmlDBException(nameIntercettore + " non e' stata trovata");
                } catch (IllegalAccessException e) {
                    logger.error("Errore nell'istanziare la classe", e);
                    throw new XmlDBException("Impossibile instanziare " + nameIntercettore);
                } catch (InstantiationException e) {
                    logger.error("Errore nell'istanziare la classe", e);
                    throw new XmlDBException("Impossibile instanziare " + nameIntercettore);
                }
            }
        }
    }

    /**
     * Ritorna la sessionFactory con annotazioni
     * @param pathXml
     * @return {@link SessionFactory}
     */
    protected SessionFactory getAnnotatedSessionFactory(String pathXml) {
        return AnnotationSessionFactory.getInstance(pathXml);
    }
}
