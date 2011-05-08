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
package org.xmldb.core.session;

import java.util.List;
import org.xmldb.core.annotation.bean.PersistenceClass;
import org.xmldb.core.criteria.Criteria;
import org.xmldb.core.exceptions.TransactionNotActiveException;
import org.xmldb.core.transaction.Transaction;
import org.xmldb.core.transaction.TransactionImpl;
import org.xmldb.core.xml.manager.XmldbManager;
import org.xmldb.core.xml.manager.XmldbManagerFactory;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class SessionImpl implements Session {

    private XmldbManager xmldbManager;
    private Transaction tx;

    public SessionImpl(String connectionUrl, String codifica) {
        xmldbManager = XmldbManagerFactory.createXmldbManager(connectionUrl, codifica);
    }

    public SessionImpl(String connectionUrl) {
        xmldbManager = XmldbManagerFactory.createXmldbManager(connectionUrl);
    }

    public <T> T merge(Object o) {
        T t = (T) xmldbManager.merge(new PersistenceClass(o));
        checkTransactionIsNotUsed();
        return t;
    }

    public <T> T load(Class<?> classe, Object id) {
        return (T) xmldbManager.load(new PersistenceClass(classe), id);
    }

    public void delete(Class<?> classe, Object id) {
        xmldbManager.remove(new PersistenceClass(classe), id);
        checkTransactionIsNotUsed();
    }

    public List findAll(Class<?> classe) {
        Criteria criteria = Criteria.createCriteria(classe);
        return find(criteria);
    }

    public List find(Criteria criteria) {
        return xmldbManager.findList(criteria);
    }

    public Transaction beginTransaction() {
        if (tx == null || !tx.isActive()) {
            tx = new TransactionImpl(xmldbManager);
        }
        return tx;
    }

    public Transaction getCurrentTransaction() {
        if(tx == null || !tx.isActive()){
            throw new TransactionNotActiveException();
        }
        return tx;
    }

    private void checkTransactionIsNotUsed(){
        if(tx == null){
            xmldbManager.commit();
        }
    }

    public void close() {
       xmldbManager.close();
    }
}
