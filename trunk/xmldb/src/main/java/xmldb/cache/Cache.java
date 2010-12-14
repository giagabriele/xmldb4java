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
package xmldb.cache;

import xmldb.exception.XmlDBException;
import xmldb.util.ReflectionUtils;

/**
 * Interfaccia della cache<br>
 * @see CacheManager#getCache()
 * 
 * @author Giacomo Stefano Gabriele
 */
public interface Cache {

    /**
     * Aggiunge un elemento in cache
     * @param classe
     * @param key id dell'oggetto vedi: {@link ReflectionUtils#getIdObject(Object)}
     * @param obj
     */
    public void put(Class<?> classe, Object key, Object obj);

    /**
     * Ritorna l'oggetto salvato in cache
     * @param classe
     * @param key id dell'oggetto
     * @return obj
     * @throws XmlDBException
     */
    public Object get(Class<?> classe, final Object key);

    /**
     * Ritorna true se la cache contiene l'oggetto, false altrimenti
     * @param classe
     * @param key
     * @return boolean
     */
    public boolean contains(Class<?> classe, final Object key);

    /**
     * Rimuove un oggetto dalla cache
     * @param classe
     * @param key
     */
    public void remove(Class<?> classe, Object key);
}
