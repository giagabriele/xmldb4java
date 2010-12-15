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
package xmldb.transaction.impl;

import org.apache.log4j.Logger;
import xmldb.cache.Cache;
import xmldb.cache.CacheManager;
import xmldb.session.impl.SessionLazy;
import xmldb.transaction.TransactionLog;

/**
 * 
 * @author Giacomo Stefano Gabriele
 * 
 */
public class TransactionLogImpl implements TransactionLog, Comparable<TransactionLog> {

    private static final Logger logger = Logger.getLogger(TransactionLogImpl.class);
    
    private static int nextID = 0;

    private static int getNextID() {
        return nextID++;
    }
    private int idTXLog = getNextID();
    private Operation operation;
    private Object obj = null;
    private Object objHold = null;
    private Class<? extends Object> classe;
    private Object id = null;

    @Override
    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public void commit(SessionLazy session) {
        switch (operation) {
            case PERSIST:
                session.persist(obj,true);
                break;
            case MERGE:
                session.merge(obj,true);
                break;
            case DELETE:
                session.delete(classe, id);
                break;
            default:
                break;
        }
    }

    @Override
    public void rollback(SessionLazy session) {
        Cache cache = CacheManager.getCache();
        switch (operation) {
            case PERSIST:
                cache.remove(classe, id);
                break;
            case MERGE:
                cache.put(classe, id, objHold);
            case DELETE:
                cache.put(classe, id, obj);
                break;
            default:
                break;
        }
    }

    @Override
    public void setObjectHold(Object obj) {
        objHold = obj;
    }

    @Override
    public void setClasse(Class<? extends Object> classe) {
        this.classe = classe;
    }

    @Override
    public void setId(Object id) {
        this.id = id;
    }

    @Override
    public void setObject(Object obj) {
        this.obj = obj;
    }

    @Override
    public int getID() {
        return idTXLog;
    }

    @Override
    public int compareTo(TransactionLog o) {
        return idTXLog - o.getID();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n\tTransactionLog [Operation ID=");
        builder.append(idTXLog);
        builder.append(", ");
        if (classe != null) {
            builder.append("classe=");
            builder.append(classe);
            builder.append(", ");
        }
        if (operation != null) {
            builder.append("operation=");
            builder.append(operation);
            builder.append(", ");
        }
        if (id != null) {
            builder.append("id=");
            builder.append(id);
            builder.append(", ");
        }
        if (obj != null) {
            builder.append("obj=");
            builder.append(obj);
            builder.append(", ");
        }
        if (objHold != null) {
            builder.append("objHold=");
            builder.append(objHold);
        }
        builder.append("]");
        return builder.toString();
    }
}
