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

import org.apache.log4j.Logger;


/**
 * Intercettore di utility che serve per i log dei metodi prima e dopo
 * <br>
 * @author Giacomo Stefano Gabriele
 * 
 */
public class LogInterceptors extends SimpleInterceptor {

    private static final Logger logger = Logger.getLogger(LogInterceptors.class);

    @Override
    public void onAfterDelete(Class<? extends Object> classe, Object id) {
        logger.info("onAfterDelete(" + classe + "," + id + ")");
    }

    @Override
    public void onAfterFind(Class<? extends Object> classe, String queryXpath,
            List<Object> result) {
        logger.info("onAfterFind(" + classe + "," + queryXpath + "," + result
                + ")");
    }

    @Override
    public void onAfterLoad(Class<? extends Object> classe, Object id,
            Object objLoaded) {
        logger.info("onAfterLoad(" + classe + "," + id + "," + objLoaded + ")");
    }

    @Override
    public void onAfterMerge(Object objHold, Object objNew) {
        logger.info("onAfterMerge(" + objHold + "," + objNew + ")");
    }

    @Override
    public void onAfterPersist(Object obj) {
        logger.info("onAfterPersist(" + obj + ")");
    }

    @Override
    public void onAfterSave() {
        logger.info("onAfterSave()");
    }

    @Override
    public void onBeforeDelete(Class<? extends Object> classe, Object id) {
        logger.info("onBeforeDelete(" + classe + "," + id + ")");
    }

    @Override
    public void onBeforeFind(Class<? extends Object> classe, String queryXpath) {
        logger.info("onBeforeFind(" + classe + "," + queryXpath + ")");
    }

    @Override
    public void onBeforeLoad(Class<? extends Object> classe, Object id) {
        logger.info("onBeforeLoad(" + classe + "," + id + ")");
    }

    @Override
    public void onBeforeMerge(Object objHold, Object objNew) {
        logger.info("onBeforeMerge(" + objHold + "," + objNew + ")");
    }

    @Override
    public void onBeforePersist(Object obj) {
        logger.info("onBeforePersist(" + obj + ")");
    }

    @Override
    public void onBeforeSave() {
        logger.info("onBeforeSave()");
    }
}
