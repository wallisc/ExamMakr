/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.exam;

import java.util.ArrayList;
import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import qsolutions.api.FormatApi;
import qsolutions.api.QuestionApi;

/**
 *
 * @author michaelmease
 */
public class QuestionTest extends TestCase {
    
    public QuestionTest(String testName) {
        super(testName);
    }

    /**
     * Test of copyData method, of class Question.
     */
    public void testCopyData() 
    {
        System.out.println("copy");
        QuestionImpl instance = new QuestionImpl();
        QuestionImpl instance2 = new QuestionImpl();
        int expResult = 2;
        instance.setMarks(2);
        instance.copyData(instance2);
        assertEquals(expResult, instance2.getMarks());
    }

    /**
     * Test of equals method, of class Question.
     */
    public void testEquals() 
    {
        System.out.println("equals");
        QuestionImpl instance = new QuestionImpl();
        QuestionImpl instance2 = new QuestionImpl();
        ArrayList instance3 = new ArrayList();
        QuestionImpl instance4 = new QuestionImpl();
        instance.setAbbreviation("ML");
        instance2.setAbbreviation("LL");
        instance.setType("LL");
        instance2.setType("LL");
        assertEquals("Two Questions with the same data were not equal", 
                     instance, instance2);
        instance2.setLevel("high");
        instance.setLevel("low");
        assertTrue("The equals method returned true between an two Questions"
                 + " with different marks values", !instance.equals(instance2));
        assertTrue("The equals method returned true between an ArrayList and"
                 + "a Question", !instance.equals(instance3));
        instance4.setType("HARRR");
        assertTrue("Two Questions with different types were considered "
                 + "equal", !instance.equals(instance4));
        
        
    }

    /**
     * Test of getAnswer and setAnswer methods, of class Question.
     */
    public void testGetAndSetAnswer() 
    {
        System.out.println("getAnswer and setAnswer");
        Question instance = new QuestionImpl();
        String expResult = "weee";
        instance.setAnswer("weee");
        String result = instance.getAnswer();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMarks and setMarks methods, of class Question.
     */
    public void testGetAndSetMarks() 
    {
        System.out.println("getMarks and setMarks");
        Question instance = new QuestionImpl();
        int expResult = 4;
        instance.setMarks(4);
        int result = instance.getMarks();
        assertEquals(expResult, result);
    }

    /**
     * Test of setQNum and getQNum methods, of class Question.
     */
    public void testSetAndGetQNum() 
    {
        System.out.println("setQNum and getQNum");
        int num = 4;
        Question instance = new QuestionImpl();
        instance.setQNum(num);
        assertEquals(4, instance.getQNum());
    }

    /**
     * Test of setCategory and getCategory methods, of class Question.
     */
    public void testSetAndGetCategory() 
    {
        System.out.println("setCategory and getCategory");
        String category = "wer";
        Question instance = new QuestionImpl();
        instance.setCategory(category);
        assertEquals("wer", instance.getCategory());
    }

    /**
     * Test of setLevel method, of class Question.
     */
    public void testSetAndGetLevel() {
        System.out.println("setLevel and getLevel");
        String level = "Hard";
        Question instance = new QuestionImpl();
        instance.setLevel(level);
        assertEquals("Hard", instance.getLevel());
    }

    public class QuestionImpl extends Question {
        public DocumentItem copy()
        {
            return null;
        }
        
        public String getHTML(FormatApi f, boolean bad )
        {
            return "";
        }
        
        public QuestionApi newItem()
        {
            return (QuestionApi) new QuestionImpl();
        }

        @Override
        public Element serializeMoodle(Document doc)
        {
            return null;
        }
    }
}
