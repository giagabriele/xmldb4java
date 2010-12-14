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

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import xmldb.session.impl.AbstractSession;
import xmldb.transaction.Transaction;
import xmldb.transaction.TransactionLog;
import xmldb.transaction.TransactionNotActiveException;

/**
 * 
 * @author Giacomo Stefano Gabriele
 * 
 */
public class TransactionImpl implements Transaction {

    private static final Logger logger = Logger.getLogger(Transaction.class);
    private static long nextID = 0;

    private synchronized static long getNextId() {
        return nextID++;
    }
    private Status status = Status.ACTIVE;
    private long id = getNextId();
    private long startTime = System.currentTimeMillis();
    private long endTime = 0L;
    private AbstractSession session;
    private SortedSet<TransactionLog> logs = Collections.synchronizedSortedSet(new TreeSet<TransactionLog>());

    public TransactionImpl(AbstractSession session) {
        this.session = session;
    }

    @Override
    public synchronized void addTransactionLog(TransactionLog log) {
        checkTransactionActive();
        if (log != null && !logs.contains(log)) {
            logs.add(log);
        }
    }

    @Override
    public void commit() {
        checkTransactionActive();

        status = Status.COMMIT;
        for (TransactionLog log : logs) {
            log.commit(session);
        }
        status = Status.COMMITTED;
        endTime = System.currentTimeMillis();

        if (logger.isDebugEnabled()) {
            logger.debug(this);
        }
    }

    @Override
    public long getID() {
        return id;
    }

    @Override
    public synchronized Status getStatus() {
        return status;
    }

    @Override
    public void rollback() {
        checkTransactionActive();

        for (TransactionLog log : logs) {
            log.rollback(session);
        }
        status = Status.CANCELED;
        endTime = System.currentTimeMillis();

        if (logger.isDebugEnabled()) {
            logger.debug(this);
        }
    }

    protected void checkTransactionActive() {
        if (!status.equals(Status.ACTIVE)) {
            throw new TransactionNotActiveException(
                    "Transaction has already been " + status.name());
        }
    }

    @Override
    public long getEndTime() {
        return endTime;
    }

    @Override
    public long getStartedTime() {
        return startTime;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nTransaction [id=");
        builder.append(id);
        builder.append(", startTime=");
        builder.append(startTime);
        builder.append(", endTime=");
        builder.append(endTime);
        builder.append(", ");
        if (status != null) {
            builder.append("status=");
            builder.append(status);
            builder.append(", ");
        }
        if (logs != null) {
            builder.append("\nlogs=");
            builder.append(logs);
            builder.append("\n");
        }
        builder.append("]");
        return builder.toString();
    }
}
