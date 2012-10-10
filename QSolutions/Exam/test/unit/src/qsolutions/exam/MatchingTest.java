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
import qsolutions.api.FormatApi.Property;

/**
 *
 * @author ryan
 */
public class MatchingTest extends TestCase {
    
    public MatchingTest(String testName) {
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
     * Test of getLeft method, of class Matching.
     */
    public void testGetLeft() {
        System.out.println("getLeft");
        Matching instance = new Matching();
        
        assertEquals("", instance.getLeft(0));
        instance.addMatchingPair("California", "Sacramento", true);
        assertEquals("California", instance.getLeft(0));
        instance.addMatchingPair("Alaska", "Juneau", true);
        assertEquals("Alaska", instance.getLeft(1));
    }

    /**
     * Test of getRight method, of class Matching.
     */
    public void testGetRight() {
        System.out.println("getRight");
        Matching instance = new Matching();
        
        assertEquals("", instance.getRight(0));
        instance.addMatchingPair("California", "Sacramento", true);
        assertEquals("Sacramento", instance.getRight(0));
        instance.addMatchingPair("Alaska", "Juneau", true);
        assertEquals("Juneau", instance.getRight(1));
    }

    /**
     * Test of isActive method, of class Matching.
     */
    public void testIsActive() {
        System.out.println("isActive");
        Matching instance = new Matching();
        
        assertFalse(instance.isActive(0));
        instance.addMatchingPair("California", "Sacramento", true);
        instance.addMatchingPair("Alaska", "Juneau", false);
        assertTrue(instance.isActive(0));
        assertFalse(instance.isActive(1));
    }

    /**
     * Test of getNumPairs method, of class Matching.
     */
    public void testGetNumPairs() {
        System.out.println("getNumPairs");
        Matching instance = new Matching();
        
        assertEquals(0, instance.getNumPairs());
        instance.addMatchingPair("California", "Sacramento", true);
        instance.addMatchingPair("Alaska", "Juneau", true);
        assertEquals(2, instance.getNumPairs());
    }

    /**
     * Test of setLeft method, of class Matching.
     */
    public void testSetLeft() {
        System.out.println("setLeft");
        Matching instance = new Matching();
        
        instance.setLeft(0, "noEffect");
        instance.addMatchingPair("California", "Sacramento", true);
        instance.setLeft(0, "Kalifornia");
        assertEquals("Kalifornia", instance.getLeft(0));
    }

    /**
     * Test of setRight method, of class Matching.
     */
    public void testSetRight() {
        System.out.println("setRight");
        int ndx = 0;
        String text = "";
        Matching instance = new Matching();
        instance.setRight(0, "noEffect");
        instance.addMatchingPair("California", "Sakramento", true);
        assertEquals("Sakramento", instance.getRight(0));
    }

    /**
     * Test of setActive method, of class Matching.
     */
    public void testSetActive() {
        System.out.println("setActive");
        int ndx = 0;
        Matching instance = new Matching();
        instance.setActive(0, true);
        instance.addMatchingPair("California", "Sacramento", true);
        instance.setActive(0, false);
        assertFalse(instance.isActive(0));
        instance.setActive(0, true);
        assertTrue(instance.isActive(0));
    }

    /**
     * Test of addMatchingPair method, of class Matching.
     */
    public void testAddMatchingPair() {
        System.out.println("addMatchingPair");
        Matching instance = new Matching();
        instance.addMatchingPair("California", "Sacramento", true);
        assertEquals("California", instance.getLeft(0));
        assertEquals("Sacramento", instance.getRight(0));
        assertTrue(instance.isActive(0));
    }

    /**
     * Test of newItem method, of class Matching.
     */
    public void testNewItem() {
        System.out.println("newItem");
        Matching instance = new Matching();
        instance.addMatchingPair("", "", true);
        instance.addMatchingPair("", "", true);
        instance.addMatchingPair("", "", true);
        instance.addMatchingPair("", "", true);
        assertTrue(instance.equals(instance.newItem()));
    }

    /**
     * Test of copy method, of class Matching.
     */
    public void testCopy() {
        System.out.println("copy");
        Matching instance = new Matching();
        
        instance.addMatchingPair("California", "Sacramento", true);
        instance.addMatchingPair("Oregon", "Salem", true);
        assertEquals(instance, instance.copy());
    }

    /**
     * Test of equals method, of class Matching.
     */
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        Matching instance = new Matching();
        Matching instanceCheck = new Matching();
        
        instance.addMatchingPair("Alaska", "Juneau", true);
        instanceCheck.addMatchingPair("Alaska", "Juneau", true);
        instance.addMatchingPair("Maine", "Augusta", true);
        instanceCheck.addMatchingPair("Maine", "Augusta", true);
        instance.addMatchingPair("Oregon", "Salem", true);
        instanceCheck.addMatchingPair("Oregon", "Salem", true);
        instance.addMatchingPair("Washington", "Olympia", true);
        instanceCheck.addMatchingPair("Washington", "Olympia", true);
        assertEquals(instance, instanceCheck);
        instance.setActive(false);
        assertFalse(instance.equals(instanceCheck));
        instance.setActive(true);
        instance.setActive(3, false);
        assertFalse(instance.equals(instanceCheck));
        assertFalse(instance.equals(new String("test")));
        instance.setActive(3, true);
        instance.setLeft(2, "nooo");
        assertFalse(instance.equals(instanceCheck));
        instance.setLeft(2, instanceCheck.getLeft(2));
        instance.setRight(0, "haha");
        assertFalse(instance.equals(instanceCheck));
        instance.setRight(0, instanceCheck.getRight(0));
        instance.addMatchingPair("California", "Sacramento", true);
        assertFalse(instance.equals(instanceCheck));
        instance.removeMatchingPair(4);
        while (instance.getDisplayIndex(0) == instanceCheck.getDisplayIndex(0))
            instance.randomizeAnswers();
        assertFalse(instance.equals(instanceCheck));
        
    }

    /**
     * Test of randomize method, of class Matching.
     */
    public void testRandomize() {
        System.out.println("randomize");
        Matching instance = new Matching();
        instance.randomizeAnswers();
    }
    
    /**
     * Test of getDisplayIndex method, of class Matching
     */
    public void testGetDisplayIndex() {
        Matching instance = new Matching();
        
        assertEquals(-1, instance.getDisplayIndex(0));
        instance.addMatchingPair("California", "Sacramento", true);
        instance.addMatchingPair("Oregon", "Salem", true);
        assertEquals(0, instance.getDisplayIndex(0));
        assertEquals(1, instance.getDisplayIndex(1));
    }

    /**
     * Test of removeMatchingPair method, of class Matching
     */
    public void testRemoveMatchingPair() {
        Matching instance = new Matching();
        
        instance.removeMatchingPair(0);
        instance.addMatchingPair("California", "Sacramento", true);
        instance.removeMatchingPair(0);
        assertEquals("", instance.getLeft(0));
        assertEquals(0, instance.getNumPairs());
    }
    
    /**
     * Test of getHTML method, of class Matching.
     */
    public void testGetHTML() {
        System.out.println("getHTML");
        Format format = new Format();
        boolean isAnswerDoc = false;
        Matching instance = new Matching();
        
        instance.addMatchingPair("Alaska", "Juneau", true);
        instance.addMatchingPair("Maine", "Agusta", true);
        instance.addMatchingPair("Oregon", "Salem", true);
        instance.addMatchingPair("Washington", "Olympia", isAnswerDoc);
        System.out.println(instance.getHTML(format, false));
        System.out.println(instance.getHTML(format, true));
        assertFalse(instance.getHTML(format, false)
                .equals(instance.getHTML(format, true)));
        format.setProperty(Property.MarksPosition, Marks.MarksEnum.Left);
        assertFalse(instance.getHTML(format, false)
                .equals(instance.getHTML(format, true)));
        instance.setActive(false);
        assertTrue(instance.getHTML(format, false).equals(""));
    }
    
    /**
     * Test of getLeftText method, of class Matching.
     */
    public void testGetLeftText() {
        System.out.println("testGetLeftText");
        boolean isAnswerDoc = false;
        Matching instance = new Matching();
        ArrayList<String> s = new ArrayList<String>();
        
        instance.addMatchingPair("Alaska", "Juneau", true);
        instance.addMatchingPair("Maine", "Agusta", true);
        instance.addMatchingPair("Oregon", "Salem", true);
        //instance.addMatchingPair("Washington", "Olympia", isAnswerDoc);
        s = instance.getLeftText();
        assertEquals("Alaska", s.get(0));
        assertEquals("Maine", s.get(1));
        assertEquals("Oregon", s.get(2));
        
        System.out.println("Output:"+s.toString());
        s = instance.getRightText();
        System.out.println("OutputR:"+s.toString());
        
        instance.randomizeAnswers();
        
        s = instance.getLeftText();
        System.out.println("Output Rand:"+s.toString());
        s = instance.getRightText();
        System.out.println("OutputR Rand:"+s.toString());
        
    }
   
    
    
    /**
     * Test of getLeftText method, of class Matching.
     */
    public void testGetRightText() {
        System.out.println("testGetLeftText");
        boolean isAnswerDoc = false;
        Matching instance = new Matching();
        ArrayList<String> s = new ArrayList<String>();
        
        instance.addMatchingPair("Alaska", "Juneau", true);
        instance.addMatchingPair("Maine", "Agusta", true);
        instance.addMatchingPair("Oregon", "Salem", true);
        //instance.addMatchingPair("Washington", "Olympia", isAnswerDoc);
        s = instance.getRightText();
        assertEquals("Juneau", s.get(0));
        assertEquals("Agusta", s.get(1));
        assertEquals("Salem", s.get(2));
    }
    
    /**
     * Test of remove
     */
    public void testRemovePairs()
    {
        Matching instance = new Matching();
        
        instance.addMatchingPair("California", "Sacramento", true);
        instance.removePairs();
        assertEquals(0, instance.getNumPairs());
    }

    public void testMoodle()
    {
        final String kQuestion = "This question is multiple choice";
        final String kAnswer1 = "ABC";
        final String kAnswer2 = "DEFGHI";
        final String kAnswer3 = "JKLM";
        final String kAnswer4 = "NOPQRSTUV";
        
        Document document = newDocument();
        Element quizRoot = document.createElement("quiz");

        Matching mc = new Matching();
        mc.setText(kQuestion);
        mc.addMatchingPair(kAnswer1, kAnswer2, true);
        mc.addMatchingPair(kAnswer3, kAnswer4, true);

        quizRoot.appendChild(mc.serializeMoodle(document));
        document.appendChild(quizRoot);

        String out = xmlToString(document);
        // Stores the question
        assertTrue("Question text does not exist in question.",out.contains(kQuestion));
        // Is the right type
        assertTrue("Incorrect question type",out.contains("type=\"matching\""));
        // Has the correct true / false fields (case matters!)
        assertTrue("Matching choice Text1",out.contains(kAnswer1));
        assertTrue("Matching choice Text1",out.contains(kAnswer2));
        assertTrue("Matching choice Text1",out.contains(kAnswer3));
        assertTrue("Matching choice Text1",out.contains(kAnswer4));
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
