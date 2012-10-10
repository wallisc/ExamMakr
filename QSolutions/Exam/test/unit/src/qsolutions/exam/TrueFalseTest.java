/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.exam;

import javax.swing.text.PlainDocument;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
import org.openide.util.Exceptions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import property.Marks;
import qsolutions.api.FormatApi.Property;
/**
 *
 * @author schooWork
 */
public class TrueFalseTest extends TestCase {
    
    public TrueFalseTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of equals method, of class TrueFalse.
     */
   
        /**
     * Test of setChoice1 method, of class TrueFalse.
     */
    public void testSetChoice1() 
    {
        TrueFalse tf1 = new TrueFalse();
        tf1.setChoice1("so true", false);
        assertEquals(true, tf1.getChoice1().equals("so true"));
    }

    /**
     * Test of setChoice2 method, of class TrueFalse.
     */
    public void testSetChoice2() 
    {
        TrueFalse tf2 = new TrueFalse();
        tf2.setChoice2("so wrong", false);
        assertEquals(true, tf2.getChoice2().equals("so wrong"));

    }

    /**
     * Test of getChoice1 method, of class TrueFalse.
     */
    public void testGetChoice1() 
    {
        TrueFalse tf1 = new TrueFalse();
        tf1.setChoice1("so right!!!", false);
        assertEquals(true, (tf1.getChoice1().equals("so right!!!")));

    }

    /**
     * Test of getChoice2 method, of class TrueFalse.
     */
    public void testGetChoice2() 
    {
        TrueFalse tf2 = new TrueFalse();
        tf2.setChoice2("so wrong!!!", false);
        assertEquals(true, !(tf2.getChoice2().equals("so wrong!!")));

    }
    /**
     * Test of getHTML method, of class TrueFalse.
     */
    public void testGetHTML() 
    {
        System.out.println("getHTML");
        Exam exam = new Exam();
        TrueFalse instance = new TrueFalse();
        instance.setChoice1("a", true);
        instance.setChoice2("b", false);
        instance.setActive(false);
        assertEquals(true, (instance.getHTML(exam.getFormat(), false)
                .equals("")));

        instance.setActive(true);
        
        instance.setText("<html>   <header> \n\n </header> <emptystringaaaa"
                + "eeeee> <\">       <b>textToExtract</b>     </p> </header>"
                + "</html>");
        String expResult = "";
        String result = instance.getHTML(exam.getFormat(), false);
        assertTrue(result != null);
        result = instance.getHTML(exam.getFormat(), true);
        assertTrue(result != null);
        exam.getFormat().setProperty(Property.MarksPosition, 
                Marks.MarksEnum.Left);
        
        instance.setChoice1("a", false);
        instance.setChoice2("b", true);
        result = instance.getHTML(exam.getFormat(), true);
    }
    
    public void testEquals()
    {
        TrueFalse tf = new TrueFalse();
        TrueFalse tf2 = new TrueFalse();
        TrueFalse tf3 = new TrueFalse();
        MultipleChoice mc = new MultipleChoice();
        assertFalse(tf.equals(mc));
        assertEquals(tf, tf2);
        tf2.setChoice1("Nah nah", true);
        tf2.setChoice2("Nah yeah", false);
        assertFalse(tf.equals(tf2));
        tf3.setAnswer("ok");
        assertFalse(tf.equals(tf3));
    }
    
    public void testNewItem()
    {
        TrueFalse tf = new TrueFalse();
        TrueFalse newItem = tf.newItem();
        assertEquals(tf, newItem);
        assertTrue( tf != newItem );
    }

    public void testMoodleTrue()
    {
        final String kQuestion = "This question is either True or False";
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
        TrueFalse tf = new TrueFalse();
        tf.setText(kQuestion);
        quizRoot.appendChild(tf.serializeMoodle(document));
        /*
        Element tag = document.createElement("question");
        tag.setTextContent("qtext");
        quizRoot.appendChild(tag);*/
        
        document.appendChild(quizRoot);
        String out = xmlToString(document);
        // Stores the question
        assertTrue("Question text does not exist in question.",out.contains(kQuestion));
        // Is the right type
        assertTrue("Incorrect question type",out.contains("type=\"truefalse\""));
        // Has the correct true / false fields (case matters!)
        assertTrue("True False Answer Text(true)",out.contains("<text>true</text>"));
        assertTrue("True False Answer Text(false)",out.contains("<text>false</text>"));
        System.out.println(xmlToString(document));
        
        /*
         *         String root = "quiz";
        DocumentBuilderFactory documentBuilderFactory = 
        DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        }
        Document document = documentBuilder.newDocument();
        Element rootElement = document.createElement(root);
        Element quizRoot;
        TrueFalse tf = new TrueFalse();
        quizRoot = tf.serializeMoodle(document);
        
        document.appendChild(quizRoot);
        System.out.println(document.toString());
         */
    }

    public void testMoodleFalse()
    {
        final String kQuestion = "This question is either True or False";
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
        TrueFalse tf = new TrueFalse();
        tf.setText(kQuestion);
        tf.setChoice2("meow", true);
        quizRoot.appendChild(tf.serializeMoodle(document));
        /*
        Element tag = document.createElement("question");
        tag.setTextContent("qtext");
        quizRoot.appendChild(tag);*/
        
        document.appendChild(quizRoot);
        String out = xmlToString(document);
        // Stores the question
        assertTrue("Question text does not exist in question.",out.contains(kQuestion));
        // Is the right type
        assertTrue("Incorrect question type",out.contains("type=\"truefalse\""));
        // Has the correct true / false fields (case matters!)
        assertTrue("True False Answer Text(true)",out.contains("<text>true</text>"));
        assertTrue("True False Answer Text(false)",out.contains("<text>false</text>"));
        System.out.println(xmlToString(document));
        
        /*
         *         String root = "quiz";
        DocumentBuilderFactory documentBuilderFactory = 
        DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        }
        Document document = documentBuilder.newDocument();
        Element rootElement = document.createElement(root);
        Element quizRoot;
        TrueFalse tf = new TrueFalse();
        quizRoot = tf.serializeMoodle(document);
        
        document.appendChild(quizRoot);
        System.out.println(document.toString());
         */
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
