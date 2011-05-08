
import java.util.Date;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.xml.entity.Contatto;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class Test {
    @org.junit.Test
    public void test(){

    }

    public static void main(String a[]) throws Exception {
       
        ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
        convertUtilsBean.deregister(Date.class);


        DateConverter dateConverter = new DateConverter();
        dateConverter.setPattern("dd/MM/yyyy");

        convertUtilsBean.deregister(Date.class);
        convertUtilsBean.register(dateConverter, Date.class);

        BeanUtilsBean beanUtilsBean = new BeanUtilsBean(convertUtilsBean, new PropertyUtilsBean());

        Contatto c = new Contatto();

        Map<String,String> x = beanUtilsBean.describe(c);

        System.out.println("DESCRIBE--------->"+x);

//        x.remove("ultimaModifica");
//        x.put("ultimaModifica", "03/05/2011");
        beanUtilsBean.populate(c, x);
    }
}
