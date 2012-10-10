/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolution.utilities;

import junit.framework.TestCase;
import qsolutions.api.ExamHandlerApi;

/**
 *
 * @author Chris
 */
public class ExamHandlerTest extends TestCase
{
    public ExamHandlerTest(String testName)
    {
        super(testName);
    }

    public void testConstructor()
    {
        ExamHandler examHandler = new ExamHandler();
        assertFalse(examHandler.getEdited());
    }

    /**
     * Test of getEdited method, of class ExamHandler.
     */
    public void testGetEdited()
    {
        ExamHandlerApi examHandler = new ExamHandler();
        examHandler.setEdited(true);
        assertTrue(examHandler.getEdited());
    }
}
