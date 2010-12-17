package xmldb;

import java.util.Date;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import xmldb.criteria.Criteria;
import xmldb.interceptor.Interceptor;
import xmldb.model.Cellulare;
import xmldb.model.Contatto;
import xmldb.transaction.Transaction;

/**
 *
 * @author GGabriele
 */
public class SessionTest extends XmlDBUnitTest{

    private static int id;
    private static int idContatto;

    /**
     * Test of persist method, of class Session.
     */
    @Test
    public void testPersist() {
        System.out.println("persist");

        Transaction tx = session.beginTransaction();
        assertNotNull("Transaction is null!!!", tx);

        Cellulare cellulare = new Cellulare();
        cellulare.setDettaglio("jhasgdkhsgjahdgkashfgdhgashfg");
        session.persist(cellulare);

        id = cellulare.getId();
        System.out.println("ID:"+id);
        assertTrue("Id deve essere maggiore di zero", id>0);

        tx.commit();
    }

    /**
     * Test of merge method, of class Session.
     */
    @Test
    public void testMerge() {
        System.out.println("merge");

        Transaction tx = session.beginTransaction();
        assertNotNull("Transaction is null!!!", tx);

        System.out.println("ID:"+id);
        Cellulare cellulare = new Cellulare();
        cellulare.setId(id);
        cellulare.setDettaglio("merge");

        Contatto contatto = new Contatto();
        contatto.setNome("nome");
        contatto.setCognome("cognome");
        contatto.setUltimaModifica(new Date());

        cellulare.setContatto(contatto);

        cellulare = session.merge(cellulare);

        tx.commit();

        assertTrue("Id deve essere maggiore di zero", cellulare.getId()>0);
    }

    /**
     * Test of load method, of class Session.
     */
    @Test
    public void testLoad() {
        System.out.println("load");
        Class<?> classe = Cellulare.class;
        Cellulare result = session.load(classe, id);

        assertNotNull("L'oggetto non deve essere null",result);

        idContatto = result.getContatto().getId();

        Contatto contatto = session.load(Contatto.class, idContatto);
        assertFalse("Deve esserci un cellulare:",contatto.getDettagli().isEmpty());

        assertEquals(result.getId(), contatto.getDettagli().get(0).getId());
    }


    /**
     * Test of delete method, of class Session.
     */
    @Test
    @Ignore
    public void testDelete() {
        System.out.println("delete");
        Class<?> classe = null;
        session.delete(classe, id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAll method, of class Session.
     */
    @Test
    @Ignore
    public void testFindAll() {
        System.out.println("findAll");
        Class<?> classe = null;
        List expResult = null;
        List result = session.findAll(classe);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of find method, of class Session.
     */
    @Test
    @Ignore
    public void testFind() {
        System.out.println("find");
        Criteria criteria = null;
        List expResult = null;
        List result = session.find(criteria);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addInterceptor method, of class Session.
     */
    @Test
    @Ignore
    public void testAddInterceptor() {
        System.out.println("addInterceptor");
        Interceptor interceptor = null;
        session.addInterceptor(interceptor);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of beginTransaction method, of class Session.
     */
    @Test
    @Ignore
    public void testBeginTransaction() {
        System.out.println("beginTransaction");
        Transaction expResult = null;
        Transaction result = session.beginTransaction();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentTransaction method, of class Session.
     */
    @Test
    @Ignore
    public void testGetCurrentTransaction() {
        System.out.println("getCurrentTransaction");
        Transaction expResult = null;
        Transaction result = session.getCurrentTransaction();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}