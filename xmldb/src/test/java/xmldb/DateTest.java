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

import java.util.Date;
import org.junit.Test;
import xmldb.model.Contatto;
import xmldb.transaction.Transaction;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class DateTest extends XmlDBUnitTest{

    private static int id;
    /**
     * Test of persist method, of class Session.
     */
    @Test
    public void testPersist() {
        System.out.println("persist");

        Transaction tx = session.beginTransaction();
        assertNotNull("Transaction is null!!!", tx);

        Contatto contatto = new Contatto();
        contatto.setCognome("Cognome");
        contatto.setNome("Nome");
        contatto.setUltimaModifica(new Date());

        session.persist(contatto);

        id = contatto.getId();
        System.out.println("ID:"+id);
        assertTrue("Id deve essere maggiore di zero", id>0);

        tx.commit();
    }
    @Test
    public void testDate(){
        System.out.println("Test Data");
        Contatto c = session.load(Contatto.class, id);
        System.out.println("Data:"+c.getUltimaModifica());
        assertTrue(c.getUltimaModifica()!=null);
    }
}
