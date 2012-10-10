/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.exam;

import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import junit.framework.TestCase;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Chris
 */
public class InstructionsTest extends TestCase
{
    private Instructions ins;
    public InstructionsTest(String testName)
    {
        super(testName);
    }

    @Override
    public void setUp()
    {
        ins = new Instructions();
    }
    
    /**
     * Test of copy method, of class Instructions.
     */
    public void testCopy()
    {
        System.out.println("copy");
        Instructions copy = ins.copy();
        assertEquals(ins, copy);
        assertTrue(ins != copy);
    }

    /**
     * Test of getHTML method, of class Instructions.
     */
    public void testGetHTML()
    {
        String instructionText = "Test more tests";
        //String exp = "<p class=\"instructions\">Test more tests</p>";
        ins.setText(instructionText);
        assertEquals(ins.getHTML(new Format(), true), Instructions.kStartHTML + 
         instructionText + Instructions.kEndHTML);
    }
    
    public void testNewItem()
    {
        assertTrue(new Instructions().newItem() instanceof Instructions);
    }
    public void testMoodle()
    {
        final String kText = "This is our instructions set.";
        String root = "quiz";
        DocumentBuilderFactory docBuilder = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try{
            db = docBuilder.newDocumentBuilder();
        }catch(Exception e){
            Exceptions.printStackTrace(e);
        }
        
        Document document = db.newDocument();
        Element quizRoot = document.createElement(root);
        Instructions qs = new Instructions();
        qs.setText(kText);
        quizRoot.appendChild(qs.serializeMoodle(document));
        
        document.appendChild(quizRoot);
        String out = xmlToString(document);
        // Stores the question
        assertTrue("Assigned item text does not exist in item.",out.contains(kText));
        // Is the right type
        assertTrue("Incorrect item type",out.contains("type=\"description\""));
        
        System.out.println(xmlToString(document));
    }
    public static String xmlToString(Node node) {
        try {
            Source source = new DOMSource(node);
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, result);
            return stringWriter.getBuffer().toString();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
