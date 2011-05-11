/*
 * Copyright 2011 Giacomo Stefano Gabriele
 *
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

package org.xmldb.core.configuration;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.xmldb.core.ErrorCode;
import org.xmldb.core.XmlDBConstants;
import org.xmldb.core.commons.log.LogHelper;
import org.xmldb.core.exceptions.XmlDBRuntimeException;
import org.xmldb.core.sessionfactory.SessionFactory;
import org.xmldb.core.sessionfactory.SessionFactoryImpl;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class Configuration {
    private static final String NAME_FILE_PROPERTIES = "dbxml";
    private Map<String, String> configuration = new HashMap<String, String>();

    private boolean withoutProperties = false;

    public Configuration() {
        
    }

    public Configuration(String connectionUrl) {
        this.withoutProperties = true;
        configuration.put(CONNECTION_URL, connectionUrl);
    }



    public SessionFactory getSessionFactory(){
        checkPropertyRequired(CONNECTION_URL);

        String connectionUrl = configuration.get(CONNECTION_URL);
        String codifica = getEncoding();
        
        SessionFactory sessionFactory = new SessionFactoryImpl(connectionUrl, codifica);
        return sessionFactory;
    }

    /**
     * Carica il file di properties 'dbxml.properties' che si trova nel classpath<br>
     * @return la {@link Configuration}
     * @throws XmlDBException se non trova il file di properties
     */
    public Configuration buildConfiguration() {
        if(withoutProperties){
            return this;
        }
        
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(NAME_FILE_PROPERTIES);
            Enumeration<String> keys = resourceBundle.getKeys();
            LogHelper.info("Configuration properties....................",Configuration.class);
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                String value = resourceBundle.getString(key);
                LogHelper.debug("["+key+"]-->["+value+"]");
                setProperty(key, value);
            }

            LogHelper.info("...............................................",Configuration.class);

        } catch (MissingResourceException e) {
            throw new XmlDBRuntimeException(ErrorCode.RESOURCE_BUNDLE_NON_TROVATO,NAME_FILE_PROPERTIES);
        }

        return this;
    }



    public Configuration setProperty(String key, String value) {
        configuration.put(key, value);
        return this;
    }


    protected void checkPropertyRequired(String key){
        if(!configuration.containsKey(key)){
            throw new XmlDBRuntimeException(ErrorCode.PROPRIETA_MANCANTE,key);
        }
    }

    private String getEncoding() {

        String codifica = XmlDBConstants.UTF8;
        try {
            if (configuration.containsKey(ENCODING_DB_XML)) {
                codifica = configuration.get(ENCODING_DB_XML);
                Charset.forName(codifica);
            }
        } catch (UnsupportedCharsetException e) {
            LogHelper.warn("La codifica ["+codifica+"] non è valida. Verrà utilizzata UTF-8!");
        }
        return codifica;
    }

    /**
     * <b>xmldb.pathdb</b> Path del dbxml. Obbligatorio
     */
    public static final String CONNECTION_URL = "xmldb.connection";

    /**
     * <b>xmldb.encoding</b>Encoding del dbxml. Facoltativo default UTF-8
     */
    public static final String ENCODING_DB_XML = "xmldb.encoding";
}
