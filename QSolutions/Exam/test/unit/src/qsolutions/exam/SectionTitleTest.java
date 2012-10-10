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
public class SectionTitleTest extends TestCase
{
    public SectionTitle st;
    public SectionTitleTest(String testName)
    {
        super(testName);
    }

    @Override
    public void setUp()
    {
        st = new SectionTitle();
    }
    
    /**
     * Test of copy method, of class PageBreak.
     */
    public void testCopy()
    {
        System.out.println("copy");
        SectionTitle copy = st.copy();
        assertEquals(st, copy);
        assertTrue(st != copy);
    }

    /**
     * Test of getHTML method, of class PageBreak.
     */
    public void testGetHTML()
    {
        String titleText = "THIS IS A TITLE MOO!";
        st.setText(titleText);
        assertEquals(st.getHTML(null, true), SectionTitle.kStartHTML + titleText
           + SectionTitle.kEndHTML);
    }
    
    public void testNewItem()
    {
        assertTrue(new SectionTitle().newItem() instanceof SectionTitle);
    }

    public void testMoodle()
    {
        assertNull(st.serializeMoodle(newDocument()));
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
