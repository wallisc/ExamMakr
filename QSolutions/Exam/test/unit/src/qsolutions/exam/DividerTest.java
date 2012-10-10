/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.exam;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import junit.framework.TestCase;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;

/**
 *
 * @author Chris
 */
public class DividerTest extends TestCase
{
    Divider div;
    public DividerTest(String testName)
    {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        div = new Divider();
    }
    
    /**
     * Test of copy method, of class Divider.
     */
    public void testCopy()
    {
        System.out.println("copy");
        Divider copy = div.copy();
        assertEquals(copy, div);
        assertFalse(copy == div);
    }

    /**
     * Test of getWidth method, of class Divider.
     */
    public void testGetWidth()
    {
        assertEquals(div.getWidth(), Divider.kDefaultWidth);
    }

    /**
     * Test of setWidth method, of class Divider.
     */
    public void testSetWidth()
    {
        System.out.println("setWidth");
        int randomNum = 13;
        div.setWidth(randomNum);
        assertEquals(div.getWidth(), randomNum);
    }

    /**
     * Test of getHTML method, of class Divider.
     */
    public void testGetHTML()
    {
        System.out.println("getHTML");
        assertTrue(div.getHTML(null, true).matches(".*" + Divider.kDividerHTML
         + ".*"));
    }
    
    public void testNewItem()
    {
        assertTrue(new Divider().newItem() instanceof Divider);
    }

    public void testMoodle()
    {
        assertNull(div.serializeMoodle(newDocument()));
    }

    public static Document newDocument() {
        DocumentBuilderFactory docBuilder = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try{
            db = docBuilder.newDocumentBuilder();
        }catch(Exception e){
            Exceptions.printStackTrace(e);
        }
        return db.newDocument();
    }
}
