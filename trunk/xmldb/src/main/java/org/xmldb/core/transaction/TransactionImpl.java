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
package org.xmldb.core.transaction;

import org.xmldb.core.xml.manager.XmldbManager;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class TransactionImpl implements Transaction {

    private int idTx = nextId();
    private boolean rollbackOnly = false;
    private boolean active = true;
    private XmldbManager xmldbManager;

    public TransactionImpl(XmldbManager xmldbManager) {
        this.xmldbManager = xmldbManager;
    }

    public void begin() {
        xmldbManager.rollback();
        active = false;
    }

    public synchronized void commit() {
        xmldbManager.commit();
        active = false;
    }

    public void rollback() {
        xmldbManager.rollback();
        active = false;
    }

    public void setRollbackOnly() {
        rollbackOnly = true;
        active = false;
    }

    public boolean getRollbackOnly() {
        return rollbackOnly;
    }

    public boolean isActive() {
        return active;
    }

    private static int nextId() {
        return nextId++;
    }
    private static int nextId = 0;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TransactionImpl other = (TransactionImpl) obj;
        if (this.idTx != other.idTx) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.idTx;
        return hash;
    }


    

    @Override
    public String toString() {
        return "Transaction{ " + "idTx=" + idTx + " }";
    }
}
