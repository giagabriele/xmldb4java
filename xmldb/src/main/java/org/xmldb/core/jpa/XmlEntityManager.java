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
package org.xmldb.core.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import org.xmldb.core.exceptions.TransactionNotActiveException;
import org.xmldb.core.session.Session;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class XmlEntityManager implements EntityManager{

    private Session session;

    
    public void persist(Object entity) {
        merge(entity);
    }

    public <T> T merge(T entity) {
        return (T)session.merge(entity);
    }

    public void remove(Object entity) {
//        session.delete(entity.getClass(),ReflectionUtils.getValueFieldId(entity));
    }

    public <T> T find(Class<T> entityClass, Object primaryKey) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T getReference(Class<T> entityClass, Object primaryKey) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void flush() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setFlushMode(FlushModeType flushMode) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public FlushModeType getFlushMode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void lock(Object entity, LockModeType lockMode) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void refresh(Object entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean contains(Object entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query createQuery(String qlString) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query createNamedQuery(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query createNativeQuery(String sqlString) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query createNativeQuery(String sqlString, Class resultClass) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query createNativeQuery(String sqlString, String resultSetMapping) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void joinTransaction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getDelegate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void close() {
        session.close();
    }

    public boolean isOpen() {
        return session!=null;
    }

    public EntityTransaction getTransaction() {
       try{
           return session.getCurrentTransaction();
       }catch(TransactionNotActiveException e){
           return session.beginTransaction();
       }
    }

   

}
