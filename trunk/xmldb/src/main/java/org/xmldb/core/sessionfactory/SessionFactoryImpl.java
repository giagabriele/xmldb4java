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
package org.xmldb.core.sessionfactory;

import java.util.LinkedList;
import org.xmldb.core.session.Session;
import org.xmldb.core.session.SessionImpl;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class SessionFactoryImpl implements SessionFactory{

    private LinkedList<Session> sessions  = new LinkedList<Session>();
    private String connectionUrl;
    private String codifica;

    public SessionFactoryImpl(String connectionUrl, String codifica) {
        this.connectionUrl = connectionUrl;
        this.codifica = codifica;
    }
    
    public Session openSession() {
        if(sessions.peek()==null){
            sessions.add(new SessionImpl(connectionUrl, codifica));
        }
        
        return getCurrentSession();
    }

//    public void addInterceptor(Interceptor interceptor) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

    private Session getCurrentSession(){
        return sessions.peek();
    }

//    public void addMapping(Class clazz) {
//        AnnotationProcessorManager.getInstance().getAnnotationProcessor(clazz);
//    }

    public void close() {
        for(Session session:sessions){
            session.close();
        }
    }

}
