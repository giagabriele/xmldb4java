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
package xmldb;

import org.dom4j.Element;

/**
 * Interfaccia parametrizzata utilizzata per trasformare una Classe in un Elemento dell'xml e viceversa
 * 
 * @author Giacomo Stefano Gabriele
 * @param <T> tipo
 */
public interface Trasformers<T> {
    /**
     * Trasforma un oggetto java in un oggetto Element
     * @param t
     * @return element
     */
    public Element trasformElement(T t);
    /**
     * Trasforma un Element in un oggetto java
     * @param element
     * @return
     */
    public T trasformModel(Element element);
}
