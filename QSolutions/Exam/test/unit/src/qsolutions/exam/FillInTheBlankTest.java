/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.exam;

import java.io.StringWriter;
import java.util.ArrayList;
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
import property.Marks;

/**
 *
 * @author ryan
 */
public class FillInTheBlankTest extends TestCase
{
    public FillInTheBlankTest(String testName)
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
     * Test of copy method, of class FillInTheBlank.
     */
    public void testCopy()
    {
        System.out.println("equals");
        FillInTheBlank instance = new FillInTheBlank();
        FillInTheBlank copy = instance.copy();
        assertEquals("A copy of a FITB was not equal to the original",
                      instance, copy);
    }

    /**
     * Test of equals method, of class FillInTheBlank.
     */
    public void testEquals() {
        System.out.println("equals");
        FillInTheBlank instance = new FillInTheBlank();
        FillInTheBlank instance2 = new FillInTheBlank();
        ArrayList instance3 = new ArrayList();
        FillInTheBlank instance4 = new FillInTheBlank();
        instance.setAbbreviation("ML");
        instance2.setAbbreviation("LL");
        instance.setType("LL");
        instance2.setType("LL");
        assertEquals("Two FillInTheBlanks with the same data were not equal", 
                     instance, instance2);
        assertTrue("The equals method returned true between an ArrayList and"
                 + "a FillInTheBlank", !instance.equals(instance3));
        instance4.setType("HARRR");
        assertTrue("Two FillInTheBlanks with different types were considered "
                 + "equal", !instance.equals(instance4));
    }

    /**
     * Test of setQuestion method, of class FillInTheBlank.
     */
    public void testgetHTML()
    {
        System.out.println("setQuestion");
        Format format = new Format();
        FillInTheBlank instance = new FillInTheBlank();
        
        instance.setText("<html>   <header> \n\n </header> <emptystringaaaa"
               + "eeeee> <\">       Testing regex replace [testOne] /[TestTwo] "
               + "[TestThree] /[testing     </p> </header></html>");
        String result = instance.getHTML(format, false);
        assertTrue("The string was not properly started", result != null);
        format.setProperty(Format.Property.MarksPosition, 
                Marks.MarksEnum.Left);
        result = instance.getHTML(format, true);
        assertTrue("The string was not properly started", result != null);
        instance.setActive(false);
        result = instance.getHTML(format,false);
        assertTrue("An inactive question returned a nonempty string"
                + "for getHTML()", result == "");
    }

    public void testMoodle()
    {
        final String kQuestion = "This question is multiple choice";
        
        Document document = newDocument();
        Element quizRoot = document.createElement("quiz");

        FillInTheBlank fitb = new FillInTheBlank();
        fitb.setText(kQuestion);

        quizRoot.appendChild(fitb.serializeMoodle(document));
        document.appendChild(quizRoot);

        String out = xmlToString(document);
        // Stores the question
        assertTrue("Question text does not exist in question.",out.contains(kQuestion));
        // Is the right type
        assertTrue("Incorrect question type",out.contains("type=\"cloze\""));
        System.out.println(xmlToString(document));
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
