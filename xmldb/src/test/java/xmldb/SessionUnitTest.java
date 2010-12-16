package xmldb;

import java.util.Date;
import java.util.List;
import org.junit.Test;

import xmldb.model.Cellulare;
import xmldb.model.Contatto;
import xmldb.model.Dettaglio;
import xmldb.transaction.Transaction;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class SessionUnitTest extends XmlDBUnitTest{

    @Test
    public void testPersist() {

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

        tx.commit();
    }

    @Test
    public void testFindAllMerge() {
        assertNotNull("Session is null!!!", session);

        Transaction tx = session.beginTransaction();
        assertNotNull("Transaction is null!!!", tx);

        List<Contatto> contatti = session.findAll(Contatto.class);
        for (Contatto c : contatti) {
            c.setNome("fffffffffffffffffffffffffff");
            Contatto tmp = session.merge(c);

            assertEquals("L'id deve essere uguale", c.getId(), tmp.getId());
        }

        tx.commit();

    }

    @Test
    public void testDelete() {
        assertNotNull("Session is null!!!", session);

        Transaction tx = session.beginTransaction();
        assertNotNull("Transaction is null!!!", tx);

        List<Contatto> contatti = session.findAll(Contatto.class);
        int ids[] = new int [contatti.size()];
        int index=0;
        for (Contatto c : contatti) {
            session.delete(Contatto.class, c.getId());

            ids[index]=c.getId();
            index++;

            Contatto tmp = session.load(Contatto.class, c.getId());

            assertNotNull("L'oggetto deve esistere poichè non ho fatto commit", tmp);

            deleteAllDettaglio(c);
        }

        tx.commit();

        for(int id:ids){
            Contatto tmp = session.load(Contatto.class, id);
            assertNull("L'oggetto non deve esistere poichè non ho fatto commit", tmp);
        }
    }

    protected void deleteAllDettaglio(Contatto c){
        for(Dettaglio dettaglio:c.getDettagli()){
            System.out.println("Dettaglio---->"+dettaglio);
            assertTrue("Id del dattaglio non deve essere null o zero", dettaglio.getId()>0);
            session.delete(Dettaglio.class, dettaglio.getId());
        }        
    }
}
