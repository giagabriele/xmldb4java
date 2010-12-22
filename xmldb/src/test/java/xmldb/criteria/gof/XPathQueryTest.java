package xmldb.criteria.gof;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import xmldb.XmlDBUnitTest;
import xmldb.model.person.Persona;
import static org.junit.Assert.*;

/**
 *
 * @author GGabriele
 */
public class XPathQueryTest extends XmlDBUnitTest{

    /**
     * Test of getQuery method, of class XPathQuery.
     */
    @Test
    public void testGetQuery() {
        System.out.println("like");
        Class classe = Persona.class;
        
//        XPathQuery query = XPathQueryFactory.createLike(classe, "nome", "Nome");
//        XPathQuery query1 = XPathQueryFactory.createLike(classe, "cognome", "Nome");
//
//        XPathQuery result = XPathQueryFactory.createAnd(classe, query, query1);
//        System.out.println("Query Like: "+result.getQuery());
        
    }
}