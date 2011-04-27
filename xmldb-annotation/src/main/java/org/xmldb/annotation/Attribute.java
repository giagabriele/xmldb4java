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
 * Annotazione usata per dire quali campi devono essere trascritti sull'xml<br>
 * <p>
 * 		Se un campo non ha questo attributo allora non verrà mappato.<br>
 * 		L'attibuto tipo serve per specificare come deve essere mappato quel campo.<br>
 * 		L'attibuto <b>tipo</b> puo' essere:<br>
 *		<ul>
 *			<li><b>ATTRIBUTE</b> valore di default: il campo viene mappato come attributo dell'elemento principale</li>
 *			<li><b>ELEMENT</b> : il campo viene memorizzato come un nuovo elemento dentro l'elemento principale</li>
 *			<li><b>ELEMENT_CDATA</b> : il campo oltre a essere memorizzato come 'ELEMENT' viene messo dentro un blocco CDATA</li>
 *		</ul>
 *
 *		<br>
 *
 *		L'attributo <b>nullable</b> (default false) stabilisce anche se il campo è null verrà comunque scritto dentro l'xml;<br>
 *		Viceversa se il campo &eacute; null allora questo non verrà scritto dentro l'xml
 * </p>
 * 
 * @author Giacomo Stefano Gabriele
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Attribute {

    enum TIPO {

        ATTRIBUTE, ELEMENT, ELEMENT_CDATA
    }

    public TIPO tipo() default TIPO.ATTRIBUTE;

    public boolean nullable() default true;
}
