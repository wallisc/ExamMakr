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
import org.openide.util.Lookup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import property.AnswerIndexing;
import property.Marks;
import qsolutions.api.DriverApi;
import qsolutions.exam.MultipleChoice.ChoiceEntry;

/**
 *
 * @author ryan
 */
public class MultipleChoiceTest extends TestCase
{
    public MultipleChoiceTest(String testName)
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
     * Test of clone method, of class MultipleChoice.
     */
    public void testClone()
    {
        System.out.println("clone");
        
        /*Question test variables*/
        String answer = "ans";
        String category = "cat";
        String level = "medium";
        int marks = 3;
        
        /*DocumentItem test variables*/
        String text = "text";
        String type = "type";
        String abbreviation = "abbr";
        boolean active = true;
        
        MultipleChoice instance = new MultipleChoice();
        
        /*Question data*/
        instance.setAnswer(answer);;
        instance.setMarks(marks);
        instance.setCategory(category);
        instance.setLevel(level);
        
        /*Document Item data*/
        instance.setActive(active);
        instance.setText(text);
        instance.setType(type);
        instance.setAbbreviation(abbreviation);
        
        instance.addChoice("test1!", true, false);
        instance.addChoice("test2!", true, false);
        instance.addChoice("test3!", true, false);
        instance.addChoice("test4!", true, true);
        
        MultipleChoice result = (MultipleChoice) instance.clone();
        
        /*MultipleChoice comparisons*/
        assertNotSame(instance, result);
        assertTrue(instance.equals(result));
    }

    /**
     * Test of equals method, of class MultipleChoice.
     */
    public void testEquals()
    {
        System.out.println("equals");
        Object o = null;
        MultipleChoice instance = new MultipleChoice();
        MultipleChoice instanceB = new MultipleChoice();
        instance.clearChoices();
        instanceB.clearChoices();
        
        assertFalse(instance.equals(o));
        
        instance.addChoice("test", true, true);
        instanceB.addChoice("test2", true, false);
        assertTrue(!instance.equals(instanceB));
        instanceB.addChoice("ok", true, true);
        instance.setAnswer("ok");
        assertTrue(!instance.equals(instanceB));
        
        instance = new MultipleChoice();
        instance.addChoice("ok", false, true);
        instanceB = new MultipleChoice();
        instanceB.addChoice("test", false, true);     
        assertTrue(!instance.equals(instanceB));
        
        instance = new MultipleChoice();
        instance.addChoice("ok", true, true);
        instanceB = instance.copy();
        instance.setAnswer("test");
        assertTrue(!instance.equals(instanceB));
        
    }
    
    /**
     * Tests the checkChoice method
     */
    public void testCheckChoice()
    {
        MultipleChoice instance = new MultipleChoice();
        assertTrue(!instance.checkChoice(-1, "test", true));
        instance.addChoice("test", true, true);
        assertTrue(instance.checkChoice(4, "test", true));
    }

    /**
     * Test of randomizeAnswers method, of class MultipleChoice.
     */
    public void testRandomizeAnswers()
    {
        System.out.println("randomizeAnswers");
        MultipleChoice instance = new MultipleChoice();
        instance.randomizeAnswers();
    }

    /**
     * Test of setActive method, of class MultipleChoice.
     */
    public void testSetActive()
    {
        System.out.println("setActive");
        int ndx = 0;
        boolean active = false;
        MultipleChoice instance = new MultipleChoice();
        instance.setActive(ndx, active);
    }

    /**
     * Test of addChoice method, of class MultipleChoice.
     */
    public void testAddChoice()
    {
        System.out.println("addChoice");
        String text = "";
        boolean isActive = false;
        boolean isAnswer = false;
        MultipleChoice instance = new MultipleChoice();
        instance.addChoice(text, isActive, isAnswer);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of editChoice method, of class MultipleChoice.
     */
    public void testEditChoice()
    {
        MultipleChoice instance = new MultipleChoice();
        MultipleChoice check = new MultipleChoice();
        
        instance.addChoice("text", true, true);
        check.addChoice("test", true, true);
        instance.editChoice(4, "test", true, true);
        
        assertEquals(check, instance);
    }

    /**
     * Test of getAnswerNdx method, of class MultipleChoice.
     */
    public void testGetAnswerNdx()
    {
        System.out.println("getAnswerNdx");
        MultipleChoice instance = new MultipleChoice();
        int expResult = 0;
        int result = instance.getAnswerNdx();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    public void testGetChoiceActive()
    {
        MultipleChoice instance = new MultipleChoice();
        
        assertTrue(!instance.getChoiceActive(-1));
        instance.addChoice("txt", true, true);
        assertTrue(instance.getChoiceActive(0));
    }
    
    public void testGetChoiceText()
    {
        MultipleChoice instance = new MultipleChoice();
        assertEquals("", instance.getChoiceText(-1));
        instance.addChoice("txt", true, true);
        assertEquals("txt", instance.getChoiceText(4));
    }
    
    /**
     * Test of getChoices method, of class MultipleChoice.
     */
    public void testGetChoices()
    {
        System.out.println("getChoices");
        MultipleChoice instance = new MultipleChoice();
        ChoiceEntry[] expResult = null;
        ChoiceEntry[] result = instance.getChoices();
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    public void testChoiceEntry()
    {
        MultipleChoice instance = new MultipleChoice();
        instance.addChoice("ok", true, true);
        MultipleChoice.ChoiceEntry[] s = instance.getChoices();
        assertTrue(s[0].isActive());
    }
    
    public void testNumChoices()
    {
        MultipleChoice instance = new MultipleChoice();
        assertEquals(MultipleChoice.kDefaultChoices, instance.getNumChoices());
    }


    /**
     * Test of getHTML method, of class MultipleChoice.
     */
    public void testGetHTML()
    {
        System.out.println("getHTML");
        Format format = new Format();
        MultipleChoice instance = new MultipleChoice();
        instance.setText("<html>   <header> \n\n </header> <emptystringaaaa"
                + "eeeee> <\">       <b>textToExtract</b>     </p> </header>"
                + "</html>");
        String expResult = "";
        
        instance.addChoice("ok", true, true);
        MultipleChoice.ChoiceEntry[] s = instance.getChoices();
        assertTrue(s[0].isActive());
        instance.addChoice("test", true, false);
        assertTrue(instance.getHTML(format, false) != null);
        System.out.println(instance.getHTML(format, false));
        System.out.println(Lookup.getDefault().lookup(DriverApi.class).cleanHTML(instance.getHTML(format, false)));
        format.setProperty(Format.Property.MCNumberingType, AnswerIndexing.AnswerIndexingEnum.CapRoman);
        format.setProperty(Format.Property.MarksPosition, Marks.MarksEnum.Left);
        format.setProperty(Format.Property.MCPosition, true);
        assertTrue(instance.getHTML(format, true) != null);
        format.setProperty(Format.Property.MCNumberingType, AnswerIndexing.AnswerIndexingEnum.LowLetters);
        format.setProperty(Format.Property.MarksPosition, Marks.MarksEnum.Right);
        assertTrue(instance.getHTML(format, false) != null);
        format.setProperty(Format.Property.MCNumberingType, AnswerIndexing.AnswerIndexingEnum.LowRoman);
        assertTrue(instance.getHTML(format, false) != null);
        format.setProperty(Format.Property.MCNumberingType, AnswerIndexing.AnswerIndexingEnum.Numbers);
        format.setProperty(Format.Property.MCPosition, false);
        assertTrue(instance.getHTML(format, true) != null);
        instance.setActive(false);
        assertTrue(instance.getHTML(format, false) != null);
    }

    public void testMoodle()
    {
        final String kQuestion = "This question is multiple choice";
        final String kAnswer1 = "ABC";
        final String kAnswer2 = "DEFGHI";
        
        Document document = newDocument();
        Element quizRoot = document.createElement("quiz");

        MultipleChoice mc = new MultipleChoice();
        mc.setText(kQuestion);
        mc.setText(1, kAnswer1);
        mc.setText(2, kAnswer2);

        quizRoot.appendChild(mc.serializeMoodle(document));
        document.appendChild(quizRoot);

        String out = xmlToString(document);
        // Stores the question
        assertTrue("Question text does not exist in question.",out.contains(kQuestion));
        // Is the right type
        assertTrue("Incorrect question type",out.contains("type=\"multichoice\""));
        // Has the correct true / false fields (case matters!)
        assertTrue("Multiple choice Text1",out.contains(kAnswer1));
        assertTrue("Multiple choice Text2",out.contains(kAnswer2));
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
    
    public void testNewItem()
    {
        MultipleChoice instance = new MultipleChoice();
        
        assertTrue(instance.newItem() instanceof MultipleChoice);
    }
}
