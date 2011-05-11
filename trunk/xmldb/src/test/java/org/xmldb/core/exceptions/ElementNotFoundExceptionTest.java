/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.xmldb.core.exceptions;

import org.junit.Test;
import org.xml.entity.Contatto;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class ElementNotFoundExceptionTest {

    

    @Test
    public void testSomeMethod() {
        ElementNotFoundException ex = new ElementNotFoundException(Contatto.class,4, "//Contatto[@id=4]") ;

        System.out.println(ex.getMessage());
    }

}