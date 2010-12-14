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
package xmldb.sessionfactory;

import static xmldb.XmlDBConstants.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.dom4j.DocumentException;
import xmldb.Session;
import xmldb.SessionFactory;

import xmldb.configuration.Configuration;
import xmldb.exception.XmlDBException;
import xmldb.interceptor.Interceptor;
import xmldb.proxy.EnhancerHelper;
import xmldb.proxy.SessionCalback;
import xmldb.session.impl.TransactionSession;
import xmldb.session.impl.AnnotationSession;
import xmldb.type.Sequence;
import xmldb.util.AnnotationHelper;

/**
 * Questa classe rappresenta la sessionFactory con annotazione<br>
 * E' la classe principale per creare la {@link Session}<br>
 * 
 * <b>Esempio:</b><br>
 * <pre>
 * 
 * 		AnnotationSessionFactory sessionFactory = AnnotationSessionFactory.getInstance(String pathXml);
 * 		sessionFactory.addMapping(Model.class);
 * 		Session session = sessionFactory.getSession();
 * 
 * </pre>
 * @see Configuration
 * 
 * @author Giacomo Stefano Gabriele
 */
public class AnnotationSessionFactory implements SessionFactory {

    private Set<Class<?>> mapping;
    private Session session;
    private static AnnotationSessionFactory sessionFactory;
    private List<Interceptor> intercettori = new LinkedList<Interceptor>();
    private String pathDb;
    private boolean transaction = Boolean.FALSE;
    private boolean cachable = Boolean.FALSE;
    private boolean autoSave = Boolean.FALSE;
    private String codifica = UTF8;

    /**
     * Costruttore 
     * @param pathDb path del db
     */
    private AnnotationSessionFactory(String pathDb) {
        if(pathDb == null){
            throw new XmlDBException("PathDb non puo' essere null!!!");
        }
        mapping = new HashSet<Class<?>>();
        this.pathDb = pathDb;

    }

    /**
     * Get Instance 
     * @param pathXml
     * @return {@link AnnotatedSessionFactory}
     */
    public synchronized  static AnnotationSessionFactory getInstance(String pathXml) {
        if (sessionFactory == null) {
            sessionFactory = new AnnotationSessionFactory(pathXml);
        }
        
        return sessionFactory;
    }

    /**
     * Add Mapping class with trasformer
     * @param classe
     * @param trasformers
     */
    public void addMapping(Class<?> classe) {
        mapping.add(classe);
        AnnotationHelper.get().add(classe);
    }

    /**
     * Metodo che ritorna se la sessionFactory autosalva
     * @return true se autosalva, false altrimenti
     */
    public boolean isAutoSave() {
        return autoSave;
    }

    /**
     * Setta se la sessionFactory deve salvare in automatico
     * @param autoSave
     */
    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    /**
     * Ritorna la Session
     * @return {@link Session} la sessione
     */
    public Session getSession() {
        if (session == null) {
            //add the sequence di default
            addMapping(Sequence.class);
            try {
                if (transaction) {
                    //con le transazioni
                    session = EnhancerHelper.createProxy(TransactionSession.class, new SessionCalback());
                } else {
                    session = EnhancerHelper.createProxy(AnnotationSession.class, new SessionCalback());
                }
                AnnotationSession annotationSession = (AnnotationSession) session;
                annotationSession.setCodifica(getCodifica());
                annotationSession.setPathDb(pathDb);
                annotationSession.setMapping(mapping);
                annotationSession.setAutoSave(isAutoSave());

                //imposto se la cache
                annotationSession.setCaschable(cachable);

                //imposto gli intercettori
                annotationSession.setIntercettori(intercettori);

            } catch (DocumentException e) {
                throw new XmlDBException(e);
            }
        }
        return session;
    }

    public String getCodifica() {
        return codifica;
    }

    public void setCodifica(String codifica) {
        this.codifica = codifica;
    }

    @Override
    public void addInterceptor(Interceptor interceptor) {
        if (interceptor == null) {
            throw new XmlDBException("Intercettore is null!!!");
        }
        intercettori.add(interceptor);
    }

    @Override
    public void setTransaction(boolean transaction) {
        this.transaction = transaction;
    }

    @Override
    public void setCache(boolean cache) {
        this.cachable = true;
    }
}
