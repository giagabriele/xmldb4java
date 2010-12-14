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

import xmldb.Session;

/**
 * Una Transizione &eacute; una serie di operazioni che la {@link Session} deve fare in sequenza e deve andare a buon fine se tutte le operazioni<br>
 * vanno a buon fine e deve fare il rolling back se una fallisce.<br>
 * 
 * Esempio di utilizzo:<br>
 * <pre>
 * 	Transaction tx = session.beginTransaction();
 * 	
 * 	try{
 * 		session.persist(...);
 * 		session.merge(...);
 * 		session.delete(...);
 * 			.
 * 			.
 * 			.
 * 
 * 		tx.commit();
 * 	}catch(Exception e){
 * 		if(tx!=null)
 * 			tx.rollback();
 * 	}
 * </pre>
 * @author Giacomo Stefano Gabriele
 * 
 */
public interface Transaction {

    /**
     * Enumerazione Status serve per indicare uno dei possibili stati della transazione<br>
     * <ul>
     * 		<li><b>ACTIVE</b> indica che la transizione &eacute; Attiva e sta registrando le modifiche</li>
     * 		<li><b>COMMITTED</b> indica che la transizione &eacute; stata committata correttamente</li>
     * 		<li><b>CANCELED</b> indica che lia transizione non &eacute; non andata a buon fine e ha fatto rolling back</li>
     * 		<li><b>COMMIT</b> stato che indica che la transizione sta committando le modifiche</li>
     * </ul>
     * @author Giacomo Stefano Gabriele
     *
     */
    public enum Status {

        ACTIVE, COMMITTED, CANCELED, COMMIT
    }

    /**
     * getID returns the internal ID of this Transaction.
     *
     * @return The Transaction ID
     */
    public long getID();

    /**
     * commit ends a transaction by commiting all data. This method, in turn,
     * should call the commit methods of all participating Collections'
     * Transaction Logs.
     */
    public void commit();

    /**
     * commit ends a transaction by rolling back all data. This method, in turn,
     * should call the rollback methods of all participating Collections'
     * Transaction Logs.
     */
    public void rollback();

    /**
     * addTransactionLog adds a TransactionLog to the partipation of this
     * Transaction. This method should invoke the start method of the
     * TransactionLog.
     */
    public void addTransactionLog(TransactionLog log);

    /**
     * getStatus returns the status of this Transaction.
     *
     * @return The Transaction status
     */
    public Status getStatus();

    /**
     * getStartTime returns the start time of this Transaction. The start time
     * is when this Transaction was instantiated, rather than when the first I/O
     * action took place.
     *
     * @return The start time
     */
    public long getStartedTime();

    /**
     * getEndTime returns the ending time of this Transaction. The end time may
     * return 0 if the Transaction is still active.
     *
     * @return The end time
     */
    public long getEndTime();
}
