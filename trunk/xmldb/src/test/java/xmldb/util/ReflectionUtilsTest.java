package xmldb.util;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author GGabriele
 */
public class ReflectionUtilsTest {

    /**
     * Test of getClassesForPackage method, of class ReflectionUtils.
     */
    @Test
    public void testGetClassesForPackage() throws Exception {
        System.out.println("getClassesForPackage Positivo");
        String pckgname = "xmldb";
        boolean all = false;
        List result = ReflectionUtils.getClassesForPackage(pckgname, all);
        assertTrue(result.size()>0);
    }

    @Test
    public void testGetClassesForPackageNeg() throws Exception {
        System.out.println("getClassesForPackage Negativo");
        String pckgname = "xmldbv.gkjdhgaf";
        boolean all = false;
        List result = ReflectionUtils.getClassesForPackage(pckgname, all);
        assertTrue(result.isEmpty());
    }
}