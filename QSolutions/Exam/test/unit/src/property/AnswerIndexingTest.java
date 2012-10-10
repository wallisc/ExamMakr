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
public class AnswerIndexingTest extends TestCase
{
    public AnswerIndexingTest(String testName)
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
     * Test of getName method, of class AnswerIndexing.
     */
    public void testGetName()
    {
        System.out.println("getName");
        AnswerIndexing instance = new AnswerIndexing();
        
        assertEquals("Answer Indexing", instance.getName());
    }

    /**
     * Test of getProperty method, of class AnswerIndexing.
     */
    public void testGetProperty()
    {
        System.out.println("getProperty");
        AnswerIndexing instance = new AnswerIndexing();
        
        assertEquals(AnswerIndexing.AnswerIndexingEnum.CapLetters,
                instance.getProperty());
    }

    /**
     * Test of setProperty method, of class AnswerIndexing.
     */
    public void testSetProperty_Object()
    {
        System.out.println("setProperty");
        AnswerIndexing instance = new AnswerIndexing();
        instance.setProperty((Object) AnswerIndexing.AnswerIndexingEnum.LowRoman);
        
        assertEquals(AnswerIndexing.AnswerIndexingEnum.LowRoman,
                instance.getProperty());
    }

    /**
     * Test of setProperty method, of class AnswerIndexing.
     */
    public void testSetProperty_AnswerIndexingAnswerIndexingEnum()
    {
        System.out.println("setProperty");
        AnswerIndexing instance = new AnswerIndexing();
        instance.setProperty(AnswerIndexing.AnswerIndexingEnum.LowRoman);
        
        assertEquals(AnswerIndexing.AnswerIndexingEnum.LowRoman,
                instance.getProperty());
    }

    /**
     * Test of getPropertyClass method, of class AnswerIndexing.
     */
    public void testGetPropertyClass()
    {
        System.out.println("getPropertyClass");
        AnswerIndexing instance = new AnswerIndexing();
        
        assertEquals(AnswerIndexing.AnswerIndexingEnum.class,
                instance.getPropertyClass());
    }

    /**
     * Test of getDefaultProperty method, of class AnswerIndexing.
     */
    public void testGetDefaultProperty()
    {
        System.out.println("getDefaultProperty");
        AnswerIndexing instance = new AnswerIndexing();
        
        assertEquals(AnswerIndexing.AnswerIndexingEnum.CapLetters,
                instance.getDefaultProperty());
    }
    
    public void testToString()
    {
        System.out.println("toString");
        
        assertEquals("1 2 3 4",
                AnswerIndexing.AnswerIndexingEnum.Numbers.toString());
    }
    
    public void testNewPropertyEnum_String()
    {
        System.out.println("newPropertyEnum: String");
        
        assertEquals(AnswerIndexing.AnswerIndexingEnum.CapLetters,
                AnswerIndexing.AnswerIndexingEnum.newPropertyEnum("A B C D"));
        
        assertEquals(AnswerIndexing.AnswerIndexingEnum.LowLetters,
                AnswerIndexing.AnswerIndexingEnum.newPropertyEnum("a b c d"));
        
        assertEquals(AnswerIndexing.AnswerIndexingEnum.CapRoman,
                AnswerIndexing.AnswerIndexingEnum.newPropertyEnum("I II III IV"));
        
        assertEquals(AnswerIndexing.AnswerIndexingEnum.LowRoman,
                AnswerIndexing.AnswerIndexingEnum.newPropertyEnum("i ii iii iv"));
        
        assertEquals(AnswerIndexing.AnswerIndexingEnum.Numbers,
                AnswerIndexing.AnswerIndexingEnum.newPropertyEnum("1 2 3 4"));
    }
    
    public void testNewPropertyEnum_Int()
    {
        System.out.println("newPropertyEnum: int");
        
        assertEquals(AnswerIndexing.AnswerIndexingEnum.CapLetters,
                AnswerIndexing.AnswerIndexingEnum.newPropertyEnum(0));
        
        assertEquals(AnswerIndexing.AnswerIndexingEnum.LowLetters,
                AnswerIndexing.AnswerIndexingEnum.newPropertyEnum(1));
        
        assertEquals(AnswerIndexing.AnswerIndexingEnum.CapRoman,
                AnswerIndexing.AnswerIndexingEnum.newPropertyEnum(2));
        
        assertEquals(AnswerIndexing.AnswerIndexingEnum.LowRoman,
                AnswerIndexing.AnswerIndexingEnum.newPropertyEnum(3));
        
        assertEquals(AnswerIndexing.AnswerIndexingEnum.Numbers,
                AnswerIndexing.AnswerIndexingEnum.newPropertyEnum(4));
    }
}
