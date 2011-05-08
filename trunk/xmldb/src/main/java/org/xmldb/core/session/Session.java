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
import org.xmldb.core.criteria.Criteria;
import org.xmldb.core.exceptions.TransactionNotActiveException;
import org.xmldb.core.transaction.Transaction;


/**
 *
 * @author Giacomo Stefano Gabriele
 * @version 2
 */
public interface Session {
    /**
     * Modifica l'oggetto passato
     * @param <T>
     * @param o
     * @return l'oggetto modificato
     */
    public <T> T merge(Object o);

    /**
     * Carica l'oggetto con quell'id
     * @param <T>
     * @param classe
     * @param id
     * @return l'oggetto
     * @throws ObjectNotFound se non lo trova
     */
    public <T> T load(Class<?> classe, Object id);

    /**
     * Rimuove l'oggetto che ha quell'id
     * @param classe
     * @param id
     */
    public void delete(Class<?> classe, Object id);

    /**
     * Ritorna tutti gli oggetti di quella classe
     * @param classe
     * @return una lista
     */
    @SuppressWarnings("rawtypes")
    public List findAll(Class<?> classe);

    /**
     * Ritorna tutti gli oggetti che corrispondono al {@link Criteria}
     * @param criteria {@link Criteria}
     * @return una lista
     */
    @SuppressWarnings("rawtypes")
    public List find(Criteria criteria);


    /**
     * Inizia una nuova {@link Transaction}
     * @return tx transaction
     */
    public Transaction beginTransaction();

    /**
     * Ritorna la Transazione corrente
     * @return tx transaction
     * @throws TransactionNotActiveException se la transazione è chiusa
     */
    public Transaction getCurrentTransaction();


    public void close();
}
