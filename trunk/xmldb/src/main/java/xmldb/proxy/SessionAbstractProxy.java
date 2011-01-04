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

import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import xmldb.criteria.Criteria;
import xmldb.interceptor.Interceptor;
import xmldb.session.impl.AnnotationSession;
import xmldb.session.impl.TransactionSession;
import xmldb.transaction.Transaction;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public abstract class SessionAbstractProxy extends TransactionSession{
    
    protected static final Logger logger = Logger.getLogger(SessionAbstractProxy.class);

    protected AnnotationSession session = new TransactionSession();

    @Override
    public void setPathDb(String pathDb) throws DocumentException {
        session.setPathDb(pathDb);
    }

    @Override
    public void setIntercettori(List<Interceptor> intercettori) {
        session.setIntercettori(intercettori);
    }

    @Override
    public void setCodifica(String codifica) {
        session.setCodifica(codifica);
    }

    @Override
    public void setCaschable(boolean caschable) {
        session.setCaschable(caschable);
    }

    @Override
    public void setAutoSave(boolean autoSave) {
        session.setAutoSave(autoSave);
    }

    @Override
    public synchronized void persist(Object o, boolean lazy) {
        session.persist(o, lazy);
    }

    @Override
    public void persist(Object o) {
        persist(o,false);
    }

//    public final void openDocument() throws DocumentException {
//        session.openDocument();
//    }

    @Override
    public synchronized <T> T merge(Object o, boolean lazy) {
        return (T)session.merge(o, lazy);
    }

    @Override
    public <T> T merge(Object o) {
        return (T)merge(o,false);
    }

    @Override
    public <T> T load(Class<? extends Object> classe, Object id, boolean lazy) {
        return (T)session.load(classe, id, lazy);
    }

    @Override
    public <T> T load(Class<? extends Object> classe, Object id) {
        return (T)load(classe, id,false);
    }

    @Override
    public boolean isCaschable() {
        return session.isCaschable();
    }

    @Override
    public String getPathDb() {
        return session.getPathDb();
    }

    @Override
    public List<Interceptor> getIntercettori() {
        return session.getIntercettori();
    }

    @Override
    public Document getDocument() {
        return session.getDocument();
    }

    @Override
    public Transaction getCurrentTransaction() {
        return session.getCurrentTransaction();
    }

    @Override
    public String getCodifica() {
        return session.getCodifica();
    }

    @Override
    public List findAll(Class<? extends Object> classe, boolean lazy) {
        return session.findAll(classe, lazy);
    }

    @Override
    public List findAll(Class<? extends Object> classe) {
        return session.findAll(classe);
    }

    @Override
    public List find(Criteria criteria) {
        return session.find(criteria);
    }

    @Override
    public List find(Criteria criteria, boolean lazy) {
        return session.find(criteria, lazy);
    }

    @Override
    public synchronized void delete(Class<? extends Object> classe, Object id) {
        session.delete(classe, id);
    }

    @Override
    public synchronized Transaction beginTransaction() {
        return session.beginTransaction();
    }

    @Override
    public void addInterceptor(Interceptor interceptor) {
        session.addInterceptor(interceptor);
    }

    @Override
    public void setMapping(Set<Class<?>> mapping) {
        session.setMapping(mapping);
    }

    @Override
    public Element findElement(Class<?> clazz, Object id) {
        return session.findElement(clazz, id);
    }


    

    
}
