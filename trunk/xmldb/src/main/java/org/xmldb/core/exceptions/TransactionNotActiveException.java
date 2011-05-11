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
package org.xmldb.core.exceptions;

import org.xmldb.core.ErrorCode;

/**
 * A TransactionNotActiveException is thrown by a Filer if a specified
 * Transaction is no longer active to be acted upon.
 */
public class TransactionNotActiveException extends XmlDBRuntimeException {

    public TransactionNotActiveException() {
        super(ErrorCode.ERRORE_TRANSACTION_NOT_ACTIVE);
    }


}
