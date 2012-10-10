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
public class AnswerAlignmentTest extends TestCase
{
    public AnswerAlignmentTest(String testName)
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
     * Test of getName method, of class AnswerAlignment.
     */
    public void testGetName()
    {
        System.out.println("getName");
        AnswerAlignment instance = new AnswerAlignment();
        
        assertEquals("Answer Alignment", instance.getName());
    }

    /**
     * Test of getProperty method, of class AnswerAlignment.
     */
    public void testGetProperty()
    {
        System.out.println("getProperty");
        AnswerAlignment instance = new AnswerAlignment();
        
        assertEquals(AnswerAlignment.AnswerAlignmentEnum.Horizontal,
                instance.getProperty());
    }

    /**
     * Test of setProperty method, of class AnswerAlignment.
     */
    public void testSetProperty_Object()
    {
        System.out.println("setProperty");
        AnswerAlignment instance = new AnswerAlignment();
        instance.setProperty(
                (Object) AnswerAlignment.AnswerAlignmentEnum.Horizontal);
        
        assertEquals(AnswerAlignment.AnswerAlignmentEnum.Horizontal,
                instance.getProperty());
    }

    /**
     * Test of setProperty method, of class AnswerAlignment.
     */
    public void testSetProperty_AnswerAlignmentAnswerAlignmentEnum()
    {
        System.out.println("setProperty");
        AnswerAlignment instance = new AnswerAlignment();
        instance.setProperty(AnswerAlignment.AnswerAlignmentEnum.Horizontal);
        
        assertEquals(AnswerAlignment.AnswerAlignmentEnum.Horizontal,
                instance.getProperty());
    }

    /**
     * Test of getPropertyClass method, of class AnswerAlignment.
     */
    public void testGetPropertyClass()
    {
        System.out.println("getPropertyClass");
        AnswerAlignment instance = new AnswerAlignment();
        
        assertEquals(AnswerAlignment.AnswerAlignmentEnum.class,
                instance.getPropertyClass());
    }

    /**
     * Test of getDefaultProperty method, of class AnswerAlignment.
     */
    public void testGetDefaultProperty()
    {
        System.out.println("getDefaultProperty");
        AnswerAlignment instance = new AnswerAlignment();
        
        assertEquals(AnswerAlignment.AnswerAlignmentEnum.Horizontal,
                instance.getDefaultProperty());
    }
    
    public void testToString()
    {
        System.out.println("toString");
        
        assertEquals("Vertical",
                AnswerAlignment.AnswerAlignmentEnum.Vertical.toString());
    }
    
    public void testNewPropertyEnum()
    {
        System.out.println("newPropertyEnum");
        
        assertEquals(AnswerAlignment.AnswerAlignmentEnum.Vertical,
                AnswerAlignment.AnswerAlignmentEnum.newPropertyEnum("Vertical"));
        
        assertEquals(AnswerAlignment.AnswerAlignmentEnum.Horizontal,
                AnswerAlignment.AnswerAlignmentEnum.newPropertyEnum("Horizontal"));
    }
}
