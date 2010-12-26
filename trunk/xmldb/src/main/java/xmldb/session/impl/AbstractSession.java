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
package xmldb.session.impl;

import xmldb.type.Sequence;
import java.util.ArrayList;
import xmldb.exception.XmlDBException;
import org.dom4j.Node;
import xmldb.criteria.Criteria;
import java.io.IOException;
import xmldb.util.DocumentUtil;
import java.util.LinkedList;
import java.util.List;
import xmldb.interceptor.Interceptor;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import xmldb.transaction.Transaction;
import org.dom4j.io.SAXReader;
import java.io.File;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import xmldb.Session;
import xmldb.Trasformers;
import xmldb.configuration.AnnotationScanner;
import xmldb.util.AnnotationHelper;
import xmldb.util.ClassHelper;
import static xmldb.XmlDBConstants.UTF8;
import static xmldb.XmlDBConstants.ELEMENT_ENTITIES;
import static xmldb.XmlDBConstants.ELEMENT_SEQUENCES;
import static xmldb.XmlDBConstants.ROOT_ELEMENT;

/**
 *
 * @author Giacomo Stefamo Gabriele
 */
public abstract class AbstractSession implements SessionLazy {

    private static final Logger logger = Logger.getLogger(AbstractSession.class);
    protected String pathDb;
    protected Document document;
    protected boolean autoSave = Boolean.FALSE;
    protected String codifica = UTF8;
    protected boolean caschable;
    protected List<Interceptor> intercettori = new LinkedList<Interceptor>();

    public AbstractSession() {
    }

    public String getPathDb() {
        return pathDb;
    }

    public void setPathDb(String pathDb) throws DocumentException {
        this.pathDb = pathDb;
        logger.info("Path xmldb: " + pathDb);
        openDocument();
    }

    /**
     * Ritorna un Element dato la classe e l'id
     * @param clazz
     * @param id
     * @return element or null if not exists
     */
    protected abstract Element findElement(Class<?> clazz, Object id);

    /**
     * Ritorna se esite l'elemento data la classe e l'id
     * @param clazz
     * @param id
     * @return true se esiste, false altrimenti
     */
    protected boolean existElement(Class<?> clazz, Object id) {
        Element e = findElement(clazz, id);
        return e != null;
    }

    /**
     * Controlla la classe se è mappata
     * @param classe
     */
    protected abstract void controllaClasse(Class<?> classe);

    /**
     * Ritorna the trasformers
     * @param clazz
     * @param lazy 
     * @return the trasformers or null
     */
    protected abstract Trasformers<?> getTrasformers(Class<?> clazz, boolean lazy);

    /**
     * Ritorna il nome dell'element data la classe
     * @param clazz
     * @return the name of the element
     */
    protected abstract String getNameEntity(Class<?> clazz);

    /**
     * Ritorna il valore dell'id dato l'oggetto
     * @param obj
     * @return id dell'oggetto
     */
    protected abstract Object getObjectId(Object obj);

    /**
     * Ritorna l'xpath by id data la classe
     * @param clazz
     * @param id
     * @return xpath
     */
    protected abstract String getXpathByID(Class<?> clazz, Object id);

    /**
     * Implementazione del metodo delete dell'interfaccia {@link Session}
     * @param classe
     * @param id
     */
    @Override
    public synchronized void delete(Class<? extends Object> classe, Object id) {
        controllaClasse(classe);

        if (logger.isDebugEnabled()) {
            logger.debug("input Classe:" + classe);
            logger.debug("input Id:" + id);
        }
        Element element = findElement(classe, id);

        //Intercettori onBefore
        for (Interceptor interceptor : intercettori) {
            interceptor.onBeforeDelete(classe, id);
        }

        boolean eliminato = ((Element) document.selectSingleNode("//" + ELEMENT_ENTITIES)).remove(element);

        //Intercettori onAfter
        for (Interceptor interceptor : intercettori) {
            interceptor.onAfterDelete(classe, id);
        }


        logger.info("delete [" + classe + "] con [" + id + "] rimosso? [" + eliminato + "]");

        checkAutoSave();

    }

    protected synchronized void saveDocument() {
        try {
            //Intercettori onBefore
            for (Interceptor interceptor : intercettori) {
                interceptor.onBeforeSave();
            }

            DocumentUtil.write(document, pathDb, codifica);

            //Intercettori onAfter
            for (Interceptor interceptor : intercettori) {
                interceptor.onAfterSave();
            }
        } catch (IOException ex) {
            logger.error("Errore durante la scrittura del file", ex);
        }
    }

    public void persist(Object o) {
        persist(o, false);
    }

    @Override
    public synchronized void persist(Object o, boolean lazy) {
        if (o == null) {
            return;
        }

        Class classe = null;
        try {
            classe = ClassHelper.getClass(o);
        } catch (ClassNotFoundException e) {
            logger.error("La classe [" + classe + "] non e' stata trovata!!!!", e);
            throw new XmlDBException("La classe [" + classe + "] non e' stata trovata!!!!", e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("input Classe:" + classe);
            logger.debug("input Lazy:" + lazy);
        }

        controllaClasse(classe);

        AnnotationScanner as = AnnotationHelper.get().get(classe);
        Trasformers trasformers = getTrasformers(classe, lazy);

        if (trasformers == null) {
            logger.error("Errore the trasformer is null");
            throw new XmlDBException("Errore the trasformer is null");
        }
        Element element = trasformers.trasformElement(o);
        boolean exist = existElement(classe, element.attributeValue(as.getId().getName()));

        if (!exist) {
            //Intercettori onBefore
            for (Interceptor interceptor : intercettori) {
                interceptor.onBeforePersist(o);
            }

            //Case Sequences
            if (classe.equals(Sequence.class)) {

                ((Element) document.selectSingleNode("//" + ELEMENT_SEQUENCES)).add(element);
            } else {
                ((Element) document.selectSingleNode("//" + ELEMENT_ENTITIES)).add(element);

                logger.info("Il record " + classe + " e' stato inserito correttamente");
            }

            //Intercettori onAfter
            for (Interceptor interceptor : intercettori) {
                interceptor.onAfterPersist(o);
            }

            checkAutoSave();
        } else {
            logger.warn("L'oggetto esiste!!!");
        }
    }

    /**
     * Implementazione del metodo find dell'interfaccia {@link SessionLazy}
     * @param criteria {@link Criteria}
     * @param lazy boolean
     * @return {@link List}
     */
    public List find(Criteria criteria, boolean lazy) {

        Class classe = null;
        try {
            classe = ClassHelper.getClass(criteria.getClass());
        } catch (ClassNotFoundException e) {
            logger.error("La classe [" + classe + "] non e' stata trovata!!!!", e);
            throw new XmlDBException("La classe [" + classe + "] non e' stata trovata!!!!", e);
        }

        controllaClasse(criteria.getClasse());
        String xPath = criteria.getXPathQuery();

        if (logger.isDebugEnabled()) {
            logger.debug("input Criteria:" + criteria);
            logger.debug("input Lazy:" + lazy);
        }

        //Intercettori onBefore
        for (Interceptor interceptor : intercettori) {
            interceptor.onBeforeFind(criteria.getClasse(), criteria.toString());
        }


        List<Node> listaNodi = document.selectNodes(xPath);

        Trasformers trasformers = getTrasformers(criteria.getClasse(), lazy);

        if (trasformers == null) {
            logger.error("Errore the trasformer is null");
            throw new XmlDBException("Errore the trasformer is null");
        }

        List lista = new ArrayList();
        for (Node node : listaNodi) {
            Object obj = trasformers.trasformModel((Element) node);
            lista.add(obj);
        }

        //Intercettori onAfter
        for (Interceptor interceptor : intercettori) {
            interceptor.onAfterFind(criteria.getClasse(), criteria.toString(), lista);
        }

        return lista;
    }

    /**
     * Implementazione metodo {@link Session}
     * @param classe
     * @return {@link List}
     * @see Session#findAll(Class)
     */
    @Override
    public List findAll(Class<? extends Object> classe) {
        return findAll(classe, false);
    }

    /**
     * Implementazione metodo {@link SessionLazy}
     * @param classe
     * @return {@link List}
     * @see SessionLazy#findAll(Class, boolean)
     */
    @Override
    public List findAll(Class<? extends Object> classe, boolean lazy) {
        try {
            classe = ClassHelper.getClass(classe.getName());
        } catch (ClassNotFoundException e) {
            logger.error("La classe [" + classe + "] non e' stata trovata!!!!", e);
            throw new XmlDBException("La classe [" + classe + "] non e' stata trovata!!!!", e);
        }

        return find(Criteria.createCriteria(classe), lazy);
    }

    /**
     * Implementazione del metodo load della {@link Session}<br>
     * chiama il metodo della {@link SessionLazy} con lazy=false
     * @param <T>
     * @param classe
     * @param id
     * @return Un oggetto parametrizzato
     */
    @Override
    public <T> T load(Class<? extends Object> classe, Object id) {
        return (T) load(classe, id, false);
    }

    /**
     * Implementazione del metodo load della {@link SessionLazy}
     * @param <T>
     * @param classe
     * @param id
     * @param lazy
     * @return Un oggetto parametrizzato
     */
    public <T> T load(Class<? extends Object> classe, Object id, boolean lazy) {

        try {
            classe = ClassHelper.getClass(classe.getName());
        } catch (ClassNotFoundException e) {
            logger.error("La classe [" + classe + "] non e' stata trovata!!!!", e);
            throw new XmlDBException("La classe [" + classe + "] non e' stata trovata!!!!", e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("input classe:" + classe);
            logger.debug("input Id:" + id);
            logger.debug("input Lazy:" + lazy);
        }
        controllaClasse(classe);
        try {
            String xPath = getXpathByID(classe, id);
            Node node = document.selectSingleNode(xPath);


            Trasformers trasformers = getTrasformers(classe, lazy);

            if (trasformers == null) {
                logger.error("Errore the trasformer is null");
                throw new XmlDBException("Errore the trasformer is null");
            }

            //Intercettori onBefore
            for (Interceptor interceptor : intercettori) {
                interceptor.onBeforeLoad(classe, id);
            }

            T obj = (T) trasformers.trasformModel((Element) node);
            if (obj == null) {
                return null;
                //throw new ObjectNotFound("Oggetto non trovato [" + classe + "] [" + id + "]");
            }

            //Intercettori onAfter
            for (Interceptor interceptor : intercettori) {
                interceptor.onAfterLoad(classe, id, obj);
            }

            return obj;
        } catch (XmlDBException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Errore inaspettato", e);
            throw new XmlDBException("Errore inaspettato", e);
        }
    }

    public <T> T merge(Object o) {
        return (T) merge(o, false);
    }

    @Override
    public synchronized <T> T merge(Object o, boolean lazy) {
        if (o == null) {
            return null;
        }

        Class classe = null;
        try {
            classe = ClassHelper.getClass(o);
        } catch (ClassNotFoundException e) {
            logger.error("La classe [" + classe + "] non e' stata trovata!!!!", e);
            throw new XmlDBException("La classe [" + classe + "] non e' stata trovata!!!!", e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("input classe:" + classe);
            logger.debug("input Lazy:" + lazy);
        }

        controllaClasse(o.getClass());
        Trasformers trasformers = getTrasformers(classe, true);

        if (trasformers == null) {
            logger.error("Errore the trasformer is null");
            throw new XmlDBException("Errore the trasformer is null");
        }
        try {

            Element element = trasformers.trasformElement(o);
            Object id = getObjectId(o);
            if (logger.isDebugEnabled()) {
                logger.debug("ID\t" + id);
            }

            Element hold = findElement(classe, id);
            if (logger.isDebugEnabled()) {
                logger.debug("Element hold\t" + hold);
            }

            //Intercettori onBefore
            for (Interceptor interceptor : intercettori) {
                interceptor.onBeforeMerge(hold, element);
            }

            //Case Sequences
            if (o.getClass().equals(Sequence.class)) {
                DefaultElement sequenceElement = ((DefaultElement) document.getRootElement().selectSingleNode("//" + ELEMENT_SEQUENCES));
                if (hold != null) {
                    hold.detach();
                }
                sequenceElement.add(element);
            } else {
                if (hold != null) {
                    hold.detach();
                    //boolean result = ((Element) document.selectSingleNode("//" + ELEMENT_ENTITIES)).remove(hold);
                }
                ((Element) document.selectSingleNode("//" + ELEMENT_ENTITIES)).add(element);

                o = trasformers.trasformModel(element);
            }

            //Intercettori onAfter
            for (Interceptor interceptor : intercettori) {
                interceptor.onAfterMerge(hold, element);
            }

            logger.info("Il Record " + classe + "e' stato modificato");

            checkAutoSave();


        } catch (Exception e) {
            logger.error("Errore inatteso", e);
            throw new XmlDBException(e);
        }

        return (T) o;
    }

    /**
     * Implementazione del metodo dell'interfaccia {@link Session}<br>
     * chiama il metodo find(Criteria criteria,boolean lazy) con lazy = false
     * @param criteria {@link Criteria}
     * @return {@link List}
     *
     */
    @Override
    public List find(Criteria criteria) {
        return find(criteria, false);
    }

    @Override
    public void addInterceptor(Interceptor interceptor) {
        if (interceptor != null) {
            intercettori.add(interceptor);
        }
    }

    @Override
    public synchronized Transaction beginTransaction() {
        throw new XmlDBException("Transazioni Gestite dal Sistema!!! Abilitarle dalla sessionFactory");
    }

    @Override
    public Transaction getCurrentTransaction() {
        throw new XmlDBException("Transazioni Gestite dal Sistema!!! Abilitarle dalla sessionFactory");
    }

    public Document getDocument() {
        return document;
    }

    /**
     * Metodo che ritorna se la session autosalva
     * @return true se autosalva, false altrimenti
     */
    boolean isAutoSave() {
        return autoSave;
    }

    /**
     * Setta se la session deve salvare in automatico
     * @param autoSave
     * @see Transaction
     */
    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    private void checkAutoSave() {
        if (isAutoSave()) {
            saveDocument();
        }
    }

    public String getCodifica() {
        return codifica;
    }

    public void setCodifica(String codifica) {
        this.codifica = codifica;
    }

    public final void openDocument() throws DocumentException {
        try {
            SAXReader reader = new SAXReader();
            reader.setEncoding(codifica);
            File file = new File(pathDb);
            if (!file.exists()) {
                logger.info("Document not exist! Create...");
                document = DocumentHelper.createDocument();
                document.addElement(ROOT_ELEMENT);


                document.getRootElement().addElement(ELEMENT_SEQUENCES);
                document.getRootElement().addElement(ELEMENT_ENTITIES);

            } else {
                document = reader.read(file);
            }
        } catch (DocumentException ex) {
            logger.error("Errore durante la lettura del file", ex);
            throw ex;
        }
    }

    public boolean isCaschable() {
        return caschable;
    }

    public void setCaschable(boolean caschable) {
        this.caschable = caschable;
    }

    public List<Interceptor> getIntercettori() {
        return intercettori;
    }

    public void setIntercettori(List<Interceptor> intercettori) {
        this.intercettori = intercettori;
    }
}
