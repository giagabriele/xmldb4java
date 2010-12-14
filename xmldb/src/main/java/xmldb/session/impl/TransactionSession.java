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

import java.util.LinkedList;

import org.apache.log4j.Logger;
import xmldb.configuration.AnnotationScanner;

import xmldb.exception.ObjectNotFound;
import xmldb.exception.XmlDBException;
import xmldb.transaction.Transaction;
import xmldb.transaction.TransactionHelper;
import xmldb.transaction.TransactionLog;
import xmldb.transaction.TransactionNotActiveException;
import xmldb.transaction.Transaction.Status;
import xmldb.util.AnnotationHelper;
import xmldb.util.ClassHelper;
import xmldb.util.ReflectionUtils;
import xmldb.util.SequenceUtil;

/**
 * Implementazione Session con Annotazioni e Transazioni
 * @author Giacomo Stefano Gabriele
 *
 */
public class TransactionSession extends AnnotationSession {

    private static final Logger logger = Logger.getLogger(TransactionSession.class);
    protected LinkedList<Transaction> transactions = new LinkedList<Transaction>();

    public TransactionSession() {
    }

    @Override
    public synchronized Transaction beginTransaction() {
        Transaction transaction = TransactionHelper.createTransaction(this);
        transactions.addFirst(transaction);
        return getCurrentTransaction();
    }

    @Override
    public Transaction getCurrentTransaction() {
        if (transactions.peek() != null && transactionIsClose(transactions.peek())) {
            Transaction tx = transactions.removeFirst();
            if (logger.isDebugEnabled()) {
                logger.debug("Transaction has been closed: " + tx);
            }
            return getCurrentTransaction();
        }
        Transaction tx = transactions.peek();
        if (tx == null) {
            throw new TransactionNotActiveException("No active transaction");
        }
        return tx;
    }

    @Override
    public synchronized void delete(Class<? extends Object> classe, Object id) {
        if (getCurrentTransaction() != null && getCurrentTransaction().getStatus().equals(Status.ACTIVE)) {

            Object obj = null;
            try {
                obj = load(classe, id, true);
            } catch (ObjectNotFound e) {
            }

            TransactionLog txLog = TransactionHelper.getTransactionLogDelete(classe, id, obj);

            getCurrentTransaction().addTransactionLog(txLog);
        } else {
            super.delete(classe, id);
        }

    }

    protected boolean transactionIsClose(Transaction transaction) {
        if (transaction == null) {
            return true;
        }
        return transaction.getStatus().equals(Status.CANCELED) || transaction.getStatus().equals(Status.COMMITTED);
    }

    @SuppressWarnings("unchecked")
    @Override
    public synchronized <T> T merge(Object o, boolean lazy) {
        if (o == null) {
            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Classe:\t" + o.getClass());
            logger.debug("Oggetto:\t" + o);
        }

        if (getCurrentTransaction() != null && getCurrentTransaction().getStatus().equals(Status.ACTIVE)) {
            AnnotationScanner as = null;
            try {
                as = AnnotationHelper.get().get(ClassHelper.getClass(o));
            } catch (ClassNotFoundException e) {
                throw new XmlDBException("Classe non trovata!!!",e);
            }
            Object id = ReflectionUtils.getValue(as.getId(), o);

            Object obj = null;
            try {
                obj = load(o.getClass(), id, true);
            } catch (ObjectNotFound e) {

                if (as.isSequence()) {
                    int nextId = SequenceUtil.nextSequence(o.getClass(), this);
                    ReflectionUtils.setValue(as.getId(), o, nextId);
                }
            }

            TransactionLog txLog = TransactionHelper.getTransactionLogMerge(o, obj);
            getCurrentTransaction().addTransactionLog(txLog);

            return (T) o;
        } else {
            return (T) super.merge(o, lazy);
        }
    }

    @Override
    public synchronized void persist(Object o, boolean lazy) {
        if (getCurrentTransaction() != null && getCurrentTransaction().getStatus().equals(Status.ACTIVE)) {
            AnnotationScanner as = AnnotationHelper.get().get(o.getClass());

            if (as.isSequence()) {
                int nextId = SequenceUtil.nextSequence(o.getClass(), this);
                ReflectionUtils.setValue(as.getId(), o, nextId);
            }

            Object id = ReflectionUtils.getValue(as.getId(), o);

            TransactionLog txLog = TransactionHelper.getTransactionLogPersist(o.getClass(), id, o);
            getCurrentTransaction().addTransactionLog(txLog);



        } else {
            super.persist(o, lazy);
        }
    }
}
