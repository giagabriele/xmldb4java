package xmldb;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import xmldb.configuration.Configuration;
import xmldb.criteria.Criteria;
import xmldb.criteria.Restrictions;
import xmldb.exception.XmlDBException;
import xmldb.model.Cellulare;
import xmldb.model.Contatto;
import xmldb.transaction.Transaction;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class SessionTest extends TestCase {

    private Session session;

    public SessionTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        Configuration cfg = new Configuration();
        cfg.buildConfiguration();
        SessionFactory sessionFactory = cfg.getSessionFactory();

        session = sessionFactory.getSession();
    }

    @Override
    protected void tearDown() throws Exception {
    }
    

    public void testPersist() {
        assertNotNull("Session is null!!!", session);

        Transaction tx = session.beginTransaction();
        assertNotNull("Transaction is null!!!", tx);

        Cellulare cellulare = new Cellulare();
        cellulare.setDettaglio("12345666767882");
        
        Contatto contatto = new Contatto();
        contatto.setCognome("Merolli");
        contatto.setNome("Flavio");
        contatto.setUltimaModifica(new Date());
        contatto.getDettagli().add(cellulare);

        session.persist(contatto);

        tx.commit();  
    }
    
    public void testFindAllMerge() {
        assertNotNull("Session is null!!!", session);

        Transaction tx = session.beginTransaction();
        assertNotNull("Transaction is null!!!", tx);

        List<Contatto> contatti = session.findAll(Contatto.class);
        for(Contatto c:contatti){
            c.setNome("fffffffffffffffffffffffffff");
            Contatto tmp = session.merge(c);
            
           assertEquals("L'id deve essere uguale", c.getId(), tmp.getId());
        }

        tx.commit();
        
    }

    public void testDelete(){
         assertNotNull("Session is null!!!", session);

        Transaction tx = session.beginTransaction();
        assertNotNull("Transaction is null!!!", tx);

        List<Contatto> contatti = session.findAll(Contatto.class);
        for(Contatto c:contatti){
            session.delete(Contatto.class, c.getId());

           Contatto tmp = null;
           try{
               session.load(Contatto.class, c.getId());
           }catch(XmlDBException e){
               
           }
           assertNull("L'oggetto non deve esistere", tmp);
        }

        tx.commit();
    }



    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}
}
