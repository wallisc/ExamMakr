/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package property;

import junit.framework.TestCase;
import property.FontFace.FontFaceEnum;

/**
 *
 * @author Ryan Dollahon
 */
public class FontFaceTest extends TestCase
{
    public FontFaceTest(String testName)
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
     * Test of getName method, of class FontFace.
     */
    public void testGetName()
    {
        System.out.println("getName");
        FontFace instance = new FontFace();
        
        assertEquals("Font Face", instance.getName());
    }

    /**
     * Test of getProperty method, of class FontFace.
     */
    public void testGetProperty()
    {
        System.out.println("getProperty");
        FontFace instance = new FontFace();
        
        assertEquals(FontFaceEnum.Serif, instance.getProperty());
    }

    /**
     * Test of setProperty method, of class FontFace.
     */
    public void testSetProperty_Object()
    {
        System.out.println("setProperty");
        FontFace instance = new FontFace();
        instance.setProperty((Object) FontFace.FontFaceEnum.SansSerif);
        
        assertEquals(FontFaceEnum.SansSerif, instance.getProperty());
    }

    /**
     * Test of setProperty method, of class FontFace.
     */
    public void testSetProperty_FontFaceFontFaceEnum()
    {
        System.out.println("setProperty");
        FontFace instance = new FontFace();
        instance.setProperty(FontFace.FontFaceEnum.SansSerif);
        
        assertEquals(FontFaceEnum.SansSerif, instance.getProperty());
    }

    /**
     * Test of getPropertyClass method, of class FontFace.
     */
    public void testGetPropertyClass()
    {
        System.out.println("getPropertyClass");
        FontFace instance = new FontFace();
        
        assertEquals(FontFace.FontFaceEnum.class, instance.getPropertyClass());
    }

    /**
     * Test of getDefaultProperty method, of class FontFace.
     */
    public void testGetDefaultProperty()
    {
        System.out.println("getDefaultProperty");
        FontFace instance = new FontFace();
        
        assertEquals(FontFace.FontFaceEnum.Serif, instance.getDefaultProperty());
    }
    
    public void testNewPropertyEnum()
    {
        System.out.println("newPropertyEnum");
        
        assertEquals(FontFace.FontFaceEnum.Serif,
                FontFace.FontFaceEnum.newPropertyEnum("Serif"));
        
        assertEquals(FontFace.FontFaceEnum.SansSerif,
                FontFace.FontFaceEnum.newPropertyEnum("sans-serif"));
    }
}