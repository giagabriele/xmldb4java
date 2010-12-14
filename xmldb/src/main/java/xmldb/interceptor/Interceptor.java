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
package xmldb.interceptor;

import java.util.List;

import xmldb.Session;

/**
 * Questa interfaccia &eacute; la rappresentazione di un intercettore.<br>
 * Viene utilizzata per intercettare gli eventi della {@link Session}
 * 
 * @author Giacomo Stefano Gabriele
 *
 */
public interface Interceptor {

    /**
     * Viene invocato prima del persist
     * @param obj
     */
    public void onBeforePersist(Object obj);

    /**
     * Viene invocato dopo aver fatto persist
     * @param obj
     */
    public void onAfterPersist(Object obj);

    /**
     * Viene invocato prima del merge
     * @param objHold
     * @param objNew
     */
    public void onBeforeMerge(Object objHold, Object objNew);

    /**
     * Viene invocato dopo del merge
     * @param objHold
     * @param objNew
     */
    public void onAfterMerge(Object objHold, Object objNew);

    /**
     * Viene invocato prima del delete
     * @param classe
     * @param id
     */
    public void onBeforeDelete(Class<? extends Object> classe, Object id);

    /**
     * Viene invocato dopo del delete
     * @param classe
     * @param id
     */
    public void onAfterDelete(Class<? extends Object> classe, Object id);

    /**
     * Viene invocato prima del load
     * @param classe
     * @param id
     */
    public void onBeforeLoad(Class<? extends Object> classe, Object id);

    /**
     * Viene invocato dopo del load
     * @param classe
     * @param id
     * @param objLoaded Oggetto caricato
     */
    public void onAfterLoad(Class<? extends Object> classe, Object id, Object objLoaded);

    /**
     * Viene invocato prima del find
     * @param classe
     * @param queryXpath
     */
    public void onBeforeFind(Class<? extends Object> classe, String queryXpath);

    /**
     * Viene invocato dopo del find
     * @param classe
     * @param queryXpath
     * @param result Risultato della ricerca
     */
    public void onAfterFind(Class<? extends Object> classe, String queryXpath, List<Object> result);

    /**
     * Viene invocato prima di salvare
     */
    public void onBeforeSave();

    /**
     * Viene invocato dopo aver salvato
     */
    public void onAfterSave();
}
