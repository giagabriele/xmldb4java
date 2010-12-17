package xmldb;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import xmldb.configuration.Configuration;

/**
 *
 * @author GGabriele
 */
public class XmlDBUnitTest extends Assert{

    public static Session session;

    @BeforeClass
    public static void setUp() throws Exception {
        System.out.println("-----Inizio-----");
        Configuration cfg = new Configuration();
        cfg.buildConfiguration();
        SessionFactory sessionFactory = cfg.getSessionFactory();

        session = sessionFactory.getSession();
    }

    @AfterClass
    public static void shotdown() {
        System.out.println("-----Fine-----");
    }

    @Test
    public void testFarlocco(){

    }
}
