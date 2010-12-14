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

import java.util.List;
import xmldb.Session;

import xmldb.criteria.Criteria;

/**
 * Lazy Session
 * @author Giacomo Stefano Gabriele
 *
 */
@SuppressWarnings("unchecked")
public interface SessionLazy extends Session {

    public <T> T load(Class<? extends Object> classe, Object id, boolean lazy);

    public List findAll(Class<? extends Object> classe, boolean lazy);

    /**
     * Ritorna tutti gli oggetti che corrispondono al {@link Criteria}
     * @param criteria {@link Criteria}
     * @param lazy
     * @return una lista
     */
    public List find(Criteria criteria, boolean lazy);

    /**
     * Persiste l'oggetto passato e i suoi oggetti collegati se lazy vale false, altrimenti persiste solo
     * l'oggetto passato
     * @param o
     * @param lazy
     */
    public void persist(Object o, boolean lazy);

    /**
     * Modifica l'oggetto passato e i suoi oggetti collegati se lazy vale false, altrimenti solo l'oggetto passato
     * @param <T>
     * @param o
     * @param lazy
     * @return l'oggetto modificato
     */
    public <T> T merge(Object o, boolean lazy);
}
