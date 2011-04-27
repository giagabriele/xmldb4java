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
package org.xmldb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * @author Giacomo Stefano Gabriele<br>
 * Annotazione usata per dire quale campo deve essere usato come ID <br>
 * Questa annotazione deve essere presente una volta per ogni classe<br>
 * 
 * Questa annotazione ha come attributo sequence che di default è false.<br>
 * Se viene messo a true allora il sistema provvederà lui stesso a creare una sequence prima di <br>
 * persisterlo.
 * 
 * Il modello della sequence è la classe {@link Sequence}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ID {

    public boolean sequence() default true;
}
