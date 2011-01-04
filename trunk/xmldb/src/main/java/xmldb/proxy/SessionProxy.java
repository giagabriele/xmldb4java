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
package xmldb.proxy;

import java.util.List;
import xmldb.Session;
import xmldb.criteria.Criteria;
import xmldb.interceptor.Interceptor;
import xmldb.session.impl.SessionLazy;
import xmldb.transaction.Transaction;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class SessionProxy implements Session,SessionLazy{

    public void persist(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T merge(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T load(Class<?> classe, Object id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void delete(Class<?> classe, Object id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List findAll(Class<?> classe) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List find(Criteria criteria) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addInterceptor(Interceptor interceptor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Transaction beginTransaction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Transaction getCurrentTransaction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T load(Class<? extends Object> classe, Object id, boolean lazy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List findAll(Class<? extends Object> classe, boolean lazy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List find(Criteria criteria, boolean lazy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void persist(Object o, boolean lazy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T merge(Object o, boolean lazy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
