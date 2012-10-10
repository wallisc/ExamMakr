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
public class ShowParenthesisTest extends TestCase
{
    public ShowParenthesisTest(String testName)
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
     * Test of getName method, of class ShowParenthesis.
     */
    public void testGetName()
    {
        System.out.println("getName");
        ShowParenthesis instance = new ShowParenthesis();
        
        assertEquals("Show Parenthesis", instance.getName());
    }

    /**
     * Test of getProperty method, of class ShowParenthesis.
     */
    public void testGetProperty()
    {
        System.out.println("getProperty");
        ShowParenthesis instance = new ShowParenthesis();
        
        assertEquals(true, instance.getProperty());
    }

    /**
     * Test of setProperty method, of class ShowParenthesis.
     */
    public void testSetProperty_Object()
    {
        System.out.println("setProperty");
        ShowParenthesis instance = new ShowParenthesis();
        instance.setProperty((Object) false);
        
        assertEquals(false, instance.getProperty());
    }

    /**
     * Test of setProperty method, of class ShowParenthesis.
     */
    public void testSetProperty_Boolean()
    {
        System.out.println("setProperty");
        ShowParenthesis instance = new ShowParenthesis();
        instance.setProperty(false);
        
        assertEquals(false, instance.getProperty());
    }

    /**
     * Test of getPropertyClass method, of class ShowParenthesis.
     */
    public void testGetPropertyClass()
    {
        System.out.println("getPropertyClass");
        ShowParenthesis instance = new ShowParenthesis();
        
        assertEquals(Boolean.class, instance.getPropertyClass());
    }

    /**
     * Test of getDefaultProperty method, of class ShowParenthesis.
     */
    public void testGetDefaultProperty()
    {
        System.out.println("getDefaultProperty");
        ShowParenthesis instance = new ShowParenthesis();
        
        assertEquals(new Boolean(true), instance.getDefaultProperty());
    }
}
