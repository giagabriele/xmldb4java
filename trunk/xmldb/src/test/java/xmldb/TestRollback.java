package xmldb;

import java.io.File;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import xmldb.model.Cellulare;
import xmldb.model.Contatto;
import xmldb.transaction.Transaction;

/**
 *
 * @author GGabriele
 */
public class TestRollback extends XmlDBUnitTest{
    
    private File file = new File("xmldb.xml");

    @Before
    public void deleteFile(){
        System.out.println("deleteFile");        
        if(file.exists()){
            file.delete();
            System.out.println("delete ok");
        }
    }

    @Test

    public void rollback() {

        Transaction tx = session.beginTransaction();
        assertNotNull("Transaction is null!!!", tx);

        Cellulare cellulare = new Cellulare();
        cellulare.setDettaglio("12345666767882");

        Contatto contatto = new Contatto();
        contatto.setCognome("Cognome");
        contatto.setNome("Nome");
        contatto.setUltimaModifica(new Date());
        contatto.getDettagli().add(cellulare);

        session.persist(contatto);

        tx.rollback();

        assertTrue("Il file dbxml.xml non deve esistere", !file.exists());
    }
}
