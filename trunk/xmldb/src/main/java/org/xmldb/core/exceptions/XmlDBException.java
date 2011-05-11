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
 * Eccezione Generale
 * 
 * @author Giacomo Stefano Gabriele
 */
public class XmlDBException extends Exception {

    private static final long serialVersionUID = -2296882273271393505L;

    private ErrorCode errorCode;

    public XmlDBException(ErrorCode errorCode,String ...args) {
        super(getMessage(errorCode.getDescription(), args));
        this.errorCode = errorCode;
    }

    public XmlDBException(Throwable cause) {
        super(ErrorCode.ERRORE_INTERNO.getDescription(), cause);
        this.errorCode = ErrorCode.ERRORE_INTERNO;
    }

    public XmlDBException(ErrorCode errorCode,Throwable cause) {
        super(errorCode.getDescription(), cause);
        this.errorCode = errorCode;
    }



    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    protected static String getMessage(final String template,String ...args){
        String message = template;
        int i=0;
        for(String a:args){
            message = message.replace("{"+i+"}", a);
            i++;
        }

        return message;
    }

}
