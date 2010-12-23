package xmldb.criteria;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.junit.Test;
import xmldb.XmlDBUnitTest;
import xmldb.criteria.projections.Projection;
import xmldb.model.person.Persona;
import static org.junit.Assert.*;

/**
 *
 * @author GGabriele
 */
public class CriteriaTest extends XmlDBUnitTest{

    /**
     * Test of createCriteria method, of class Criteria.
     */
    @Test
    public void testCreateCriteria() {
        System.out.println("createCriteria");
        Class<? extends Object> classe = Persona.class;
        Criteria result = Criteria.createCriteria(classe);
        //result.add(Restrictions.like("Nome", "ciao"));
        result.add(Restrictions.OR(Restrictions.gt("anni", 3), Restrictions.lt("anni", 5)));
        Restrictions restrictions = Restrictions.OR(
                Restrictions.startWith("nome", "Nome"),
                Restrictions.AND(Restrictions.like("nome", "Cognome1"), Restrictions.like("nome", "Cognome2")));

        result.add(restrictions);
        try{
            validate(result);
        }catch(XPathExpressionException e){
            fail(e.getMessage());
        }

        result.setProjection(Projection.rowCount());

         try{
            validate(result);
        }catch(XPathExpressionException e){
            fail(e.getMessage());
        }
    }

    protected void validate(Criteria criteria) throws XPathExpressionException{
        System.out.println(criteria);
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr = xpath.compile(criteria.getXPathQuery());
    }

}