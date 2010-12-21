package xmldb;

import java.util.List;
import org.junit.Test;
import xmldb.criteria.Criteria;
import xmldb.criteria.Restrictions;
import static org.junit.Assert.*;
import xmldb.model.person.Persona;
import xmldb.transaction.Transaction;

/**
 *
 * @author GGabriele
 */
public class SessionEntityTypeElementTest extends XmlDBUnitTest {

    private static int id;
    private static int idContatto;

    @Test
    public void test() {
        System.out.println("persist");

        Transaction tx = session.beginTransaction();
        assertNotNull("Transaction is null!!!", tx);

        Persona persona = new Persona();
        persona.setNome("Nome della persona");
        persona.setCognome("Cognnome della persona");
        session.persist(persona);

        id = persona.getId();
        System.out.println("ID:" + id);
        assertTrue("Id deve essere maggiore di zero", id > 0);

        tx.commit();

        System.out.println("load");
        Persona p2 = session.load(Persona.class, id);

        assertEquals(p2.getNome(), persona.getNome());
        assertEquals(p2.getCognome(), persona.getCognome());

        System.out.println("find Criteria");
        Criteria criteria = Criteria.createCriteria(Persona.class);
        criteria.add(Restrictions.like("nome", "Nome"));

        List lista = session.find(criteria);

        assertFalse(lista.isEmpty());
    }
}
