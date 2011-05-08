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
package org.xmldb.core.sessionfactory;

import org.xmldb.core.session.Session;

/**
 * Interfaccia della sessionFactory
 * @author Giacomo Stefano Gabriele
 */
public interface SessionFactory {

    /**
     * Ritorna la current Session aperta
     * @return {@link Session}
     */
    public Session openSession();

//    /**
//     * Aggiunge un intercettore alla {@link SessionFactory}
//     * @param interceptor
//     */
//    public void addInterceptor(Interceptor interceptor);
    /**
     * Aggiunge le classi da mappare
     * @param clazz
     */
//    public void addMapping(Class clazz);

    public void close();
}
