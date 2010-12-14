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

/**
 * Classe astratta che implementa i metodi dell'interfaccia {@link Interceptor}<br>
 * Se si vuole creare un intercettore e non si vogliono implementare tutti i metodi dell'interfaccia si puo&oacute;<br>
 * estendere questa classe.
 * @author Giacomo Stefano Gabriele
 *
 */
public abstract class SimpleInterceptor implements Interceptor {

    @Override
    public void onAfterDelete(Class<? extends Object> classe, Object id) {
    }

    @Override
    public void onAfterFind(Class<? extends Object> classe, String queryXpath, List<Object> result) {
    }

    @Override
    public void onAfterLoad(Class<? extends Object> classe, Object id, Object objLoaded) {
    }

    @Override
    public void onAfterMerge(Object objHold, Object objNew) {
    }

    @Override
    public void onAfterPersist(Object obj) {
    }

    @Override
    public void onAfterSave() {
    }

    @Override
    public void onBeforeDelete(Class<? extends Object> classe, Object id) {
    }

    @Override
    public void onBeforeFind(Class<? extends Object> classe, String queryXpath) {
    }

    @Override
    public void onBeforeLoad(Class<? extends Object> classe, Object id) {
    }

    @Override
    public void onBeforeMerge(Object objHold, Object objNew) {
    }

    @Override
    public void onBeforePersist(Object obj) {
    }

    @Override
    public void onBeforeSave() {
    }
}
