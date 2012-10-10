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
import qsolutions.api.FormatApi;
import qsolutions.api.NumberingRestartApi;

/**
 *
 * @author Jake
 */
public class NumberingRestartTest extends TestCase {
    
    public NumberingRestartTest(String testName) {
        super(testName);
    }

    /**
     * Test of copy method, of class NumberingRestart.
     */
    public void testCopy() {
        System.out.println("Numbering Restart copy");
        NumberingRestart instance = new NumberingRestart();
        NumberingRestart instance2 = instance.copy();
        assertEquals(instance.getText(), instance2.getText());
        assertEquals(instance.getLevel(), instance2.getLevel());
        assertEquals(instance.getType(), instance2.getType());
        assertEquals(instance.isKeepTogether(), instance2.isKeepTogether());
        assertEquals(instance.getMarks(), instance2.getMarks());
        assertEquals(instance.isActive(), instance2.isActive());
        
    }

    /**
     * Test of getHTML method, of class NumberingRestart.
     */
    public void testGetHTML() {
        System.out.println("Numbering Restart getHTML");
        FormatApi format = null;
        boolean isAnswerDoc = false;
        NumberingRestart instance = new NumberingRestart();
        String expResult = "</ol><ol>";
        String result = instance.getHTML(format, isAnswerDoc);
        assertEquals(expResult, result);
    }

    /**
     * Test of newItem method, of class NumberingRestart.
     */
    public void testNewItem() 
    {
        System.out.println("Numbering Restart New Item");
        NumberingRestart instance = new NumberingRestart();
        NumberingRestartApi instance2 = instance.newItem();
        assertTrue(instance instanceof NumberingRestartApi);
    }  

    public void testMoodle()
    {
        NumberingRestart instance = new NumberingRestart();
        assertNull(instance.serializeMoodle(newDocument()));
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
