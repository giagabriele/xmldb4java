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
package xmldb.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @author Giacomo Stefano Gabriele<br>
 * Questa annotazione si usa quando per un oggetto si vuole trasformarlo in string a seguito di un pattern.<br>
 * Per esempio prendiamo un campo di un classe java.util.Date:<br>
 * <pre>
 * 		'@Attribute'
 *  	'@Pattern(pattern="dd/MM/yyyy")'
 *  	private Date data;
 *  </pre>
 *  <br>
 *  Il valore di dafault di pattern per questa annotazione e' <b>dd/MM/yyyy</b> 
 */
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface Pattern {

    public String pattern() default "dd/MM/yyyy";
}
