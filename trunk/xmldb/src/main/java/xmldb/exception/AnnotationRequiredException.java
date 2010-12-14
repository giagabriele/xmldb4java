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
package xmldb.exception;

import java.lang.annotation.Annotation;

/**
 * 
 * @author Giacomo Stefano Gabriele
 *
 */
public class AnnotationRequiredException extends Exception {

    private static final long serialVersionUID = 5270295477422168981L;

    public AnnotationRequiredException(Class<? extends Object> clazz, Class<? extends Annotation> annotation, Throwable e) {
        super("La classe ".concat(clazz.getName()).concat(" non ha l'annotation " + annotation.getName()), e);
    }

    public AnnotationRequiredException(Class<? extends Object> clazz, Class<? extends Annotation> annotation) {
        super("La classe ".concat(clazz.getName()).concat(" non ha l'annotation " + annotation.getName()));
    }
}
