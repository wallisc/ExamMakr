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
public class MarksTest extends TestCase
{
    public MarksTest(String testName)
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
     * Test of getName method, of class Marks.
     */
    public void testGetName()
    {
        System.out.println("getName");
        Marks instance = new Marks();
        
        assertEquals("Points", instance.getName());
    }

    /**
     * Test of getProperty method, of class Marks.
     */
    public void testGetProperty()
    {
        System.out.println("getProperty");
        Marks instance = new Marks();
        
        assertEquals(Marks.MarksEnum.InLine, instance.getProperty());
    }

    /**
     * Test of setProperty method, of class Marks.
     */
    public void testSetProperty_Object()
    {
        System.out.println("setProperty");
        Marks instance = new Marks();
        instance.setProperty((Object) Marks.MarksEnum.Right);
        
        assertEquals(Marks.MarksEnum.Right, instance.getProperty());
    }

    /**
     * Test of setProperty method, of class Marks.
     */
    public void testSetProperty_MarksMarksEnum()
    {
        System.out.println("setProperty");
        Marks instance = new Marks();
        instance.setProperty(Marks.MarksEnum.Right);
        
        assertEquals(Marks.MarksEnum.Right, instance.getProperty());
    }

    /**
     * Test of getPropertyClass method, of class Marks.
     */
    public void testGetPropertyClass()
    {
        System.out.println("getPropertyClass");
        Marks instance = new Marks();
        
        assertEquals(Marks.MarksEnum.class, instance.getPropertyClass());
    }

    /**
     * Test of getDefaultProperty method, of class Marks.
     */
    public void testGetDefaultProperty()
    {
        System.out.println("getDefaultProperty");
        Marks instance = new Marks();
        
        assertEquals(Marks.MarksEnum.InLine, instance.getDefaultProperty());
    }
    
    public void testToString()
    {
        System.out.println("toString");
        
        assertEquals("InLine", Marks.MarksEnum.InLine.toString());
    }
    
    public void testNewPropertyEnum_String()
    {
        System.out.println("newPropertyEnum: String");
        
        assertEquals(Marks.MarksEnum.Right, Marks.MarksEnum.newPropertyEnum("Right"));
        
        assertEquals(Marks.MarksEnum.InLine, Marks.MarksEnum.newPropertyEnum("InLine"));
        
        assertEquals(Marks.MarksEnum.Left, Marks.MarksEnum.newPropertyEnum("Left"));
    }
    
    public void testNewPropertyEnum_Int()
    {
        System.out.println("newPropertyEnum: int");
        
        assertEquals(Marks.MarksEnum.Right, Marks.MarksEnum.newPropertyEnum(1));
        
        assertEquals(Marks.MarksEnum.InLine, Marks.MarksEnum.newPropertyEnum(2));
        
        assertEquals(Marks.MarksEnum.Left, Marks.MarksEnum.newPropertyEnum(0));
    }
}
