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
public class PageBreakTest extends TestCase
{
    private PageBreak pb;
    public PageBreakTest(String testName)
    {
        super(testName);
    }

    @Override
    public void setUp()
    {
        pb = new PageBreak();
    }
    
    /**
     * Test of copy method, of class PageBreak.
     */
    public void testCopy()
    {
        System.out.println("copy");
        PageBreak copy = pb.copy();
        assertEquals(pb, copy);
        assertTrue(pb != copy);
    }

    /**
     * Test of getHTML method, of class PageBreak.
     */
    public void testGetHTML()
    {
        assertEquals(pb.getHTML(null, true), PageBreak.kPageBreakHTML);
    }
    
    public void testNewItem()
    {
        assertTrue(new PageBreak().newItem() instanceof PageBreak);
    }

    public void testMoodle()
    {
        assertNull(pb.serializeMoodle(newDocument()));
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
