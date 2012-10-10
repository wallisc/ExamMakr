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
public class FontSizeTest extends TestCase
{
    public FontSizeTest(String testName)
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
     * Test of getName method, of class FontSize.
     */
    public void testGetName()
    {
        System.out.println("getName");
        FontSize instance = new FontSize();
        
        assertEquals("Font Size", instance.getName());
    }

    /**
     * Test of getProperty method, of class FontSize.
     */
    public void testGetProperty()
    {
        System.out.println("getProperty");
        FontSize instance = new FontSize();
        
        assertEquals(FontSize.FontSizeEnum.Twelve, instance.getProperty());
    }

    /**
     * Test of setProperty method, of class FontSize.
     */
    public void testSetProperty_Object()
    {
        System.out.println("setProperty");
        FontSize instance = new FontSize();
        instance.setProperty((Object) FontSize.FontSizeEnum.TwentyFour);
        
        assertEquals(FontSize.FontSizeEnum.TwentyFour, instance.getProperty());
    }

    /**
     * Test of setProperty method, of class FontSize.
     */
    public void testSetProperty_FontSizeFontSizeEnum()
    {
        System.out.println("setProperty");
        FontSize instance = new FontSize();
        instance.setProperty(FontSize.FontSizeEnum.TwentyFour);
        
        assertEquals(FontSize.FontSizeEnum.TwentyFour, instance.getProperty());
    }

    /**
     * Test of getPropertyClass method, of class FontSize.
     */
    public void testGetPropertyClass()
    {
        System.out.println("getPropertyClass");
        FontSize instance = new FontSize();
        
        assertEquals(FontSize.FontSizeEnum.class, instance.getPropertyClass());
    }

    /**
     * Test of getDefaultProperty method, of class FontSize.
     */
    public void testGetDefaultProperty()
    {
        System.out.println("getDefaultProperty");
        FontSize instance = new FontSize();
        
        assertEquals(FontSize.FontSizeEnum.Twelve, instance.getDefaultProperty());
    }
    
    public void testToString()
    {
        System.out.println("toString");
        
        assertEquals("14", FontSize.FontSizeEnum.Fourteen.toString());
    }
    
    public void testNewPropertyEnum()
    {
        System.out.println("newPropertyEnum");
        
        assertEquals(FontSize.FontSizeEnum.Eight,
                FontSize.FontSizeEnum.newPropertyEnum(8));
        
        assertEquals(FontSize.FontSizeEnum.Nine,
                FontSize.FontSizeEnum.newPropertyEnum(9));
        
        assertEquals(FontSize.FontSizeEnum.Ten,
                FontSize.FontSizeEnum.newPropertyEnum(10));
        
        assertEquals(FontSize.FontSizeEnum.Eleven,
                FontSize.FontSizeEnum.newPropertyEnum(11));
        
        assertEquals(FontSize.FontSizeEnum.Twelve,
                FontSize.FontSizeEnum.newPropertyEnum(12));
        
        assertEquals(FontSize.FontSizeEnum.Fourteen,
                FontSize.FontSizeEnum.newPropertyEnum(14));
        
        assertEquals(FontSize.FontSizeEnum.Eighteen,
                FontSize.FontSizeEnum.newPropertyEnum(18));
        
        assertEquals(FontSize.FontSizeEnum.TwentyFour,
                FontSize.FontSizeEnum.newPropertyEnum(24));
    }
}