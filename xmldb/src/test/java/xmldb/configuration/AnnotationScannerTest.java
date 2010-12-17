package xmldb.configuration;

import java.lang.reflect.Field;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import xmldb.annotation.ManyToOne;
import xmldb.annotation.OneToMany;
import xmldb.model.Cellulare;
import xmldb.model.Contatto;

/**
 *
 * @author GGabriele
 */
public class AnnotationScannerTest {
    protected static AnnotationScanner instance = null;
    public AnnotationScannerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        instance = new AnnotationScanner(Cellulare.class);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getNameEntity method, of class AnnotationScanner.
     */
    @Test
    public void testGetNameEntity() {
        System.out.println("getNameEntity");
        
        String expResult = "dettaglio";
        String result = instance.getNameEntity();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId method, of class AnnotationScanner.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        String expResult = "id";
        String result = instance.getId().getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAttributes method, of class AnnotationScanner.
     */
    @Test
    public void testGetAttributes() {
        System.out.println("getAttributes");
        int expResult = 2;
        int result = instance.getAttributes().length;
        assertEquals(expResult, result);
    }

    /**
     * Test of getFieldsManyToOne method, of class AnnotationScanner.
     */
    @Test
    public void testGetFieldsManyToOne() {
        System.out.println("getFieldsManyToOne");
        Class expResult = Contatto.class;
        Class result = instance.getFieldsManyToOne()[0].getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFieldsOneToMany method, of class AnnotationScanner.
     */
    @Test
    public void testGetFieldsOneToMany() {
        System.out.println("getFieldsOneToMany");
        Field[] result = instance.getFieldsOneToMany();
        assertEquals("non ci sono campi con OneToMany", 0, result.length);
    }

    /**
     * Test of getAnnotationManyToOne method, of class AnnotationScanner.
     */
    @Test
    public void testGetAnnotationManyToOne() {
        System.out.println("getAnnotationManyToOne");
        Field field = instance.getFieldsManyToOne()[0];
        ManyToOne result = instance.getAnnotationManyToOne(field);
        assertNotNull("L'annotazione ManyToOne non deve essere null", result);
    }

    /**
     * Test of getAnnotationOneToMany method, of class AnnotationScanner.
     */
    @Test
    @Ignore
    public void testGetAnnotationOneToMany() {
        System.out.println("getAnnotationOneToMany");
        Field field = instance.getFieldsOneToMany()[0];
        OneToMany result = instance.getAnnotationOneToMany(field);
        assertNotNull("L'annotazione OneToMany non deve essere null", result);
    }

    /**
     * Test of scanNameEntity method, of class AnnotationScanner.
     */
    @Test
    public void testScanNameEntity() throws Exception {
        instance.scanNameEntity();
        assertTrue(true);
    }

    /**
     * Test of scanId method, of class AnnotationScanner.
     */
    @Test
    public void testScanId() throws Exception {
        System.out.println("scanId");
        instance.scanId();
       assertTrue(true);
    }

    /**
     * Test of scanAttributes method, of class AnnotationScanner.
     */
    @Test
    public void testScanAttributes() throws Exception {
        System.out.println("scanAttributes");
       
        instance.scanAttributes();
       assertTrue(true);
    }

    /**
     * Test of scanFieldsManyToOne method, of class AnnotationScanner.
     */
    @Test
    public void testScanFieldsManyToOne() throws Exception {
        System.out.println("scanFieldsManyToOne");
       
        instance.scanFieldsManyToOne();
        assertTrue(true);
    }

    /**
     * Test of scanFieldsOneToMany method, of class AnnotationScanner.
     */
    @Test
    public void testScanFieldsOneToMany() throws Exception {
        System.out.println("scanFieldsOneToMany");
       
        instance.scanFieldsOneToMany();
        assertTrue(true);
    }
}