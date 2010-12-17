package xmldb.proxy;

import net.sf.cglib.proxy.Callback;
import org.junit.Test;
import xmldb.XmlDBUnitTest;
import xmldb.model.Cellulare;
import xmldb.model.Dettaglio;
import xmldb.session.impl.AbstractSession;
import static org.junit.Assert.*;

/**
 *
 * @author GGabriele
 */
public class EnhancerHelperTest extends XmlDBUnitTest{
    /**
     * Test of createProxy method, of class EnhancerHelper.
     */
    @Test
    public void testCreateProxy() {
        System.out.println("createProxy");
        Class<?> targetClass = Cellulare.class;
        Callback callback = new RelationResolvingCallback(targetClass, (AbstractSession)session);
        Object result = EnhancerHelper.createProxy(targetClass, callback);
        System.out.println("result:"+result);

        Dettaglio dettaglio = new Dettaglio();
        System.out.println("class to cast:"+dettaglio.getClass());

        Dettaglio obj = dettaglio.getClass().cast(result);
        System.out.println("obj:"+obj);

        

        //assertTrue(dettaglio.getClass().equals(obj.getClass()));
    }

}