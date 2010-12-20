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

import org.apache.log4j.Logger;
import xmldb.Session;
import xmldb.configuration.AnnotationScanner;
import xmldb.exception.XmlDBException;
import xmldb.session.impl.SessionLazy;
import xmldb.type.Sequence;

/**
 * 
 * @author Giacomo Stefano Gabriele<br>
 * Classe Utility per la sequence
 */
public class SequenceUtil {

    private static final Logger logger = Logger.getLogger(SequenceUtil.class);

    /**
     * Ritorna il prossimo valore di sequence per la classe passata <br>
     * Questa metodo usa la classe {@link Sequence} del sistema
     * @param classe
     * @param session
     * @return int next sequence
     */
    public static int nextSequence(Class<?> classe, Session session) {
        int nextValue = 1;
        try {

            if (logger.isDebugEnabled()) {
                logger.debug("input Classe: " + classe);
                logger.debug("input Session: "+session);
            }
            AnnotationScanner as = AnnotationHelper.get().get(classe);
            String nomeEntity = as.getNameEntity();
            Sequence s = null;
            
            if (session instanceof SessionLazy) {
                s = ((SessionLazy) session).load(Sequence.class, nomeEntity, true);
            } else {
                s = session.load(Sequence.class, nomeEntity);
            }

            if (s == null) {
                s = new Sequence();
                s.setId(nomeEntity);
            } else {
                nextValue = s.getSequence() + 1;
            }

            s.setSequence(nextValue);
            ((SessionLazy) session).merge(s, false);
        } catch (Exception e) {
            logger.error("Errore inatteso", e);
            throw new XmlDBException(e);
        }
        if(logger.isDebugEnabled()){
            logger.debug("output NextSequence: "+nextValue);
        }
        return nextValue;
    }
}
