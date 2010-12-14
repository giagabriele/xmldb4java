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
package xmldb.transaction;

import xmldb.session.impl.AbstractSession;
import xmldb.transaction.TransactionLog.Operation;
import xmldb.transaction.impl.TransactionImpl;
import xmldb.transaction.impl.TransactionLogImpl;

/**
 * classe utility per creare transazioni
 * @author Giacomo Stefano Gabriele
 *
 */
public class TransactionHelper {

    public static Transaction createTransaction(AbstractSession session) {
        return new TransactionImpl(session);
    }

    public static TransactionLog getTransactionLogDelete(Class<? extends Object> classe, Object id, Object obj) {
        TransactionLog tx = new TransactionLogImpl();
        tx.setOperation(Operation.DELETE);
        tx.setClasse(classe);
        tx.setId(id);
        tx.setObject(obj);

        return tx;
    }

    public static TransactionLog getTransactionLogMerge(Object objNew, Object objHold) {
        TransactionLog tx = new TransactionLogImpl();
        tx.setOperation(Operation.MERGE);
        tx.setObject(objNew);
        tx.setObjectHold(objHold);
        return tx;
    }

    public static TransactionLog getTransactionLogPersist(Class<? extends Object> classe, Object id, Object objNew) {
        TransactionLog tx = new TransactionLogImpl();
        tx.setOperation(Operation.PERSIST);
        tx.setObject(objNew);
        tx.setClasse(classe);
        tx.setId(id);

        return tx;
    }
}
