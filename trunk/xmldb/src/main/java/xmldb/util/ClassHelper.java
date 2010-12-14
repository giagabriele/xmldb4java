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
package xmldb.util;

import java.util.StringTokenizer;
import org.apache.log4j.Logger;

/**
 *
 * @author giacomo
 */
public class ClassHelper {

    private static final Logger logger = Logger.getLogger(ClassHelper.class);
    /**
     * Ritorna la classe dato il nome della classe
     * @param className
     * @return the class
     * @throws ClassNotFoundException
     */
    public static Class<?> getClass(String className) throws ClassNotFoundException {
        if (logger.isDebugEnabled()) {
            logger.debug("Classe richiesta\t" + className);
        }

        if (className.contains("$EnhancerByCGLIB$")) {
            StringTokenizer st = new StringTokenizer(className, "$");
            String x = st.nextToken();
            if (logger.isDebugEnabled()) {
                logger.debug("Classe ritornata\t" + x);
            }
            return Class.forName(x);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Classe ritornata\t" + className);
            }
            return Class.forName(className);
        }
    }
    /**
     *
     * @param obj
     * @return
     * @throws ClassNotFoundException
     */
    public static Class<?> getClass(Object obj) throws ClassNotFoundException{
        return getClass(obj.getClass().getName());
    }
}
