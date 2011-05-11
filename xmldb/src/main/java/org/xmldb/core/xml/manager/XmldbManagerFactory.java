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
package org.xmldb.core.xml.manager;

import java.util.StringTokenizer;
import org.xmldb.core.ErrorCode;
import org.xmldb.core.XmlDBConstants;
import org.xmldb.core.exceptions.XmlDBRuntimeException;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class XmldbManagerFactory {

    public static XmldbManager createXmldbManager(String connectionUrl){
        return createXmldbManager(connectionUrl, XmlDBConstants.UTF8);
    }

    /**
     * Crea un XmldbManager data la connectionUrl:<br>
     * connectionUrl deve essere composto da ENGINE:NOME_DB_O_PATH_DB<br>
     * ENGINE può assumere i sequenti valori: locale o memory
     *
     * @param connectionUrl
     * @param codifica
     * @return una istanza di XmldbManager
     * @throws XmlDBRuntimeException se connectionUrl non è valido
     */
    public static XmldbManager createXmldbManager(String connectionUrl,String codifica){
        if(connectionUrl.lastIndexOf(":")<0){
            throw new XmlDBRuntimeException(ErrorCode.ERRORE_DATABASE_URL);
        }
        StringTokenizer st = new StringTokenizer(connectionUrl,":");

        if(st.countTokens()!=2){
            throw new XmlDBRuntimeException(ErrorCode.ERRORE_DATABASE_URL);
        }

        String engine   = st.nextToken();
        String path     = st.nextToken();

        return creteConcreteXmldbManager(engine, path,codifica);
    }

    private static XmldbManager creteConcreteXmldbManager(String type,String path,String codifica){
        if(XmlDBConstants.ENGINE_LOCALE.equals(type)){
            return new FileSystemXmldbManagerImpl(path);
        }

        if(XmlDBConstants.ENGINE_MEMORY.equals(type)){
            return new MemoryXmldbManagerImpl(path);
        }

        throw new XmlDBRuntimeException(ErrorCode.ERRORE_ENGINE,type);
    }
}
