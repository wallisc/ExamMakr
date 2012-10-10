/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package property;

import junit.framework.TestCase;

/**
 *
 * @author Ryan Dollahon
 */
public class ShowAnswerLinesTest extends TestCase
{
    public ShowAnswerLinesTest(String testName)
    {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /**
     * Test of getName method, of class ShowAnswerLines.
     */
    public void testGetName()
    {
        System.out.println("getName");
        ShowAnswerLines instance = new ShowAnswerLines();
        
        assertEquals("Show Answer Lines", instance.getName());
    }

    /**
     * Test of getProperty method, of class ShowAnswerLines.
     */
    public void testGetProperty()
    {
        System.out.println("getProperty");
        ShowAnswerLines instance = new ShowAnswerLines();
        
        assertEquals(false, instance.getProperty());
    }

    /**
     * Test of setProperty method, of class ShowAnswerLines.
     */
    public void testSetProperty_Object()
    {
        System.out.println("setProperty");
        ShowAnswerLines instance = new ShowAnswerLines();
        instance.setProperty((Object) true);
        
        assertEquals(true, instance.getProperty());
    }

    /**
     * Test of setProperty method, of class ShowAnswerLines.
     */
    public void testSetProperty_Boolean()
    {
        System.out.println("setProperty");
        ShowAnswerLines instance = new ShowAnswerLines();
        instance.setProperty(true);
        
        assertEquals(true, instance.getProperty());
    }

    /**
     * Test of getPropertyClass method, of class ShowAnswerLines.
     */
    public void testGetPropertyClass()
    {
        System.out.println("getPropertyClass");
        ShowAnswerLines instance = new ShowAnswerLines();
        
        assertEquals(Boolean.class, instance.getPropertyClass());
    }

    /**
     * Test of getDefaultProperty method, of class ShowAnswerLines.
     */
    public void testGetDefaultProperty()
    {
        System.out.println("getDefaultProperty");
        ShowAnswerLines instance = new ShowAnswerLines();
        
        assertEquals(new Boolean(false), instance.getDefaultProperty());
    }
}
