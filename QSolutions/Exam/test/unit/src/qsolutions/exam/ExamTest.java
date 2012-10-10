
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
import org.w3c.dom.NodeList;
import qsolutions.api.DocumentItemApi;
import qsolutions.api.FormatApi;

/**
 *
 * @author Michael, Jake
 */
public class ExamTest extends TestCase {
    
    Exam exam;
    MultipleChoice mcq;
    
    public ExamTest(String testName) 
    {
        super(testName);
    }
    
    @Override
    public void setUp() {
        mcq = new MultipleChoice();
        exam = new Exam();
    }

    /**
     * Test of getFormat method, of class Exam.
     */
    public void testGetFormat()
    {
        System.out.println("getFormat");
        FormatApi result = exam.getFormat();
    }
    
    /**
     * Test of getNumQuestions method, of class Exam.
     */
    public void testGetNumQuestions() 
    {
        System.out.println("getNumQuestions");
        Question q1 = new MultipleChoice();
        Question q2 = new MultipleChoice();
        DocumentItem q3 = new PageBreak();
        exam.addItem(q1);
        exam.addItem(q2);
        exam.addItem(q3);
        int expResult = 2;
        int result = exam.getNumQuestions();
        assertEquals(expResult, result);
    }

    /**
     * Test of getItemAt method, of class Exam.
     */
    public void testGetItemAt()
    {
        System.out.println("getItemAt");
        int index = 1;
        Question q1 = new MultipleChoice();
        Question q2 = new MultipleChoice();
        exam.addItem(q1);
        exam.addItem(q2);
        DocumentItemApi expResult = q2;
        DocumentItemApi result = exam.getItemAt(index);
        assertEquals(expResult, result);
    }

    /**
     * Test of addItemAt method, of class Exam.
     */
    public void testAddItemAt() 
    {
        System.out.println("addItemAt");
        DocumentItemApi item = new MultipleChoice();
        int index = 0;
        exam.addItemAt(item, index);
        DocumentItemApi result = exam.getItemAt(0);
        assertEquals(item, result);
    }

    /**
     * Test of addItem method, of class Exam.
     */
    public void testAddItem() 
    {
        System.out.println("addItem");
        DocumentItemApi item = new MultipleChoice();
        exam.addItem(item);
        int expResult = 1;
        int result = exam.getNumQuestions();
        assertEquals(result, expResult);
    }

    /**
     * Test of moveItem method, of class Exam.
     */
    public void testMoveItem() 
    {
        System.out.println("moveItem");
        int indexOld = 0;
        int indexNew = 1;
        Question q1 = new MultipleChoice();
        Question q2 = new MultipleChoice();
        exam.addItem(q1);
        exam.addItem(q2);
        exam.moveItem(indexOld, indexNew);
        DocumentItemApi expItem = exam.getItemAt(1);
        assertEquals(expItem, q1);
    }

    /**
     * Test of removeItem method, of class Exam.
     */
    public void testRemoveItem() 
    {
        System.out.println("removeItem");
        int index = 0;
        int expResult = 1;
        Question q1 = new MultipleChoice();
        Question q2 = new MultipleChoice();
        exam.addItem(q1);
        exam.addItem(q2);
        exam.removeItem(index);
        int result = exam.getNumQuestions();
        assertEquals(expResult, result);
    }

    /**
     * Test of replaceItem method, of class Exam.
     */
    public void testReplaceItem() 
    {
        System.out.println("replaceItem");
        DocumentItemApi toRemove = new MultipleChoice();
        DocumentItemApi replaceWith = new FreeResponse();
        toRemove.setActive(false);
        exam.addItem(toRemove);
        exam.replaceItem(toRemove, replaceWith);
        DocumentItemApi result = exam.getItemAt(0);
        assertEquals(result, replaceWith);
        exam.addItem(replaceWith);
        exam.addItem(replaceWith);
        exam.addItem(new MultipleChoice());
        exam.replaceItem(null, toRemove);
        exam = new Exam();
        exam.replaceItem(null, toRemove);
    }

    /**
     * Test of updateList method, of class Exam.
     */
    public void testUpdateList() 
    {
        System.out.println("updateList");
        int expResult = 1;
        int expResult2 = -1;
        Question q1 = new MultipleChoice();
        Question q2 = new MultipleChoice();
        DocumentItemApi item = new PageBreak();
        exam.addItem(q1);
        exam.addItem(q2);
        exam.addItem(item);
        ((Question)exam.getItemAt(1)).setQNum(6);
        q1.setActive(false);
        exam.updateList();
        int result = exam.getItemAt(1).getQNum();
        int result2 = exam.getItemAt(2).getQNum();
        item.setActive(true);
        item.setActive(false);
        assertEquals(expResult, result);
        assertEquals(expResult2, result2);
    }

    /**
     * Test of randomize method, of class Exam.
     */
    public void testRandomize() 
    {
       /* System.out.println("randomize");
        int[] indexes = {0,1,2};
        exam.addItem(new MultipleChoice());
        exam.addItem(new ShortAnswer());
        exam.addItem(new FreeResponse());
        exam.randomize(indexes);*/
    }
    
    public void testRandomizeAnswers()
    {
        System.out.println("randomizeAnswers");
        DocumentItemApi q1 = new MultipleChoice();
        DocumentItemApi q2 = new Matching();
        DocumentItemApi q3 = new Order();
        exam.addItem(q1);
        exam.addItem(q2);
        exam.addItem(q3);
        exam.randomizeAnswers(true, true, true);
        assertEquals(3, exam.getNumItems());
    }

    public void testRandomizeSections()
    {
        System.out.println("randomizeAnswers");
        DocumentItemApi q1 = new MultipleChoice();
        DocumentItemApi q2 = new MultipleChoice();
        DocumentItemApi q3 = new MultipleChoice();
        DocumentItemApi q4 = new SectionTitle();
        DocumentItemApi q5 = new FreeResponse();
        DocumentItemApi q6 = new FreeResponse();
        DocumentItemApi q7 = new FreeResponse();
        
        exam.addItem(q1);
        exam.addItem(q2);
        exam.addItem(q3);
        exam.addItem(q4);
        exam.addItem(q5);
        exam.addItem(q6);
        exam.addItem(q7);
        exam.randomizeAll(true, true, true);
        assertTrue(exam.getItemAt(0) instanceof MultipleChoice);
        assertTrue(exam.getItemAt(3) instanceof SectionTitle);
        assertTrue(exam.getItemAt(6) instanceof FreeResponse);
                
    }
    
    /**
     * Test of randomizeAll method, of class Exam.
     */
    public void testRandomizeAll() 
    {
        System.out.println("randomizeAll");
        DocumentItemApi q1 = new MultipleChoice();
        DocumentItemApi q2 = new SectionTitle();
        Question q3 = new MultipleChoice();
        Question q4 = new MultipleChoice();
        exam.addItem(q1);
        exam.addItem(q2);
        exam.addItem(q3);
        exam.addItem(q4);
        exam.randomizeAll(true, true, true);
        DocumentItemApi expResult = q2;
        DocumentItemApi result = exam.getItemAt(1);
        assertEquals(expResult, result);
        exam.randomizeAll(false, false, false);
        result = exam.getItemAt(1);
        assertEquals(expResult, result);

    }

    /**
     * Test of getItems method, of class Exam.
     */
    public void testGetItems() 
    {
        System.out.println("getItems");
        DocumentItemApi q1 = new MultipleChoice();
        exam.addItem(q1);
        ArrayList expResult = new ArrayList();
        expResult.add(q1);
        ArrayList result = exam.getItems();
        assertEquals(expResult, result);
    }

    /**
     * Test of getQuestions method, of class Exam.
     */
    public void testGetQuestions() 
    {
        System.out.println("getQuestions");
        DocumentItemApi q1 = new MultipleChoice();
        DocumentItemApi q2 = new Divider();
        exam.addItem(q1);
        exam.addItem(q2);
        ArrayList expResult = new ArrayList();
        expResult.add(q1);
        ArrayList result = exam.getQuestions();
        assertEquals(expResult, result);
    }

    /**
     * Test of setItemAt method, of class Exam.
     */
    public void testSetItemAt() 
    {
        System.out.println("setItemAt");
        DocumentItemApi item = new MultipleChoice();
        int index = 0;
        exam.addItem(new FreeResponse());
        exam.setItemAt(item, index);
        DocumentItemApi result = exam.getItemAt(0);
        assertEquals(item, result);
    }

    /**
     * Test of getTitleGroup method, of class Exam.
     */
    public void testGetTitleGroup() 
    {
        
        System.out.println("getTitleGroup");
        String expResult = "<table bor";
        String result = 
                exam.getTitleGroup().getTitleGroupHtml().substring(0,10);
        assertEquals(expResult, result);
        exam.getTitleGroup().setShowTitleGroup(false);
        assertEquals("", exam.getTitleGroup().getTitleGroupHtml());
        exam.getTitleGroup().setShowTitleGroup(true);
        exam.getTitleGroup().setTitleGroupFontGlobal(false);
        assertFalse(exam.getTitleGroup().getTitleGroupHtml().equals(""));
    }
    
    public void testGenerateXhtmlExam()
    {
        System.out.println("generateXhtmlExam");
        exam.addItem(new MultipleChoice());
        exam.addItem(new MultipleChoice());
        exam.addItem(new NumberingRestart());
        exam.addItem(new Instructions());
        exam.getItemAt(0).setActive(false);
        exam.getItemAt(1).setActive(true);
        exam.getItemAt(2).setActive(true);
        exam.getItemAt(1).setKeepTogether(true);
        exam.generateXhtmlExam();
        exam.getItemAt(1).setKeepTogether(false);
        exam.bulkUpdate(true);
        exam.bulkUpdate(false);
        
        assertEquals("<?xml version=\"1.0\"", 
                exam.generateXhtmlExam().substring(0, 19));
    }

    
    /*
     * Test of generateExamPageBreak method, of class Exam.
     */
    /*public void testGenerateExamPageBreak() 
    {
        System.out.println("generateExam()");
        Exam exam = new Exam();
        String startTags = "<ol style=\"margin:10px; padding:10px; "
                + "list-style: decimal;\">";
        String questionTags = "<li style=\"margin-bottom:15px;\">";
        String endTags = "</ol>";
        MultipleChoice mc = new MultipleChoice();
        FreeResponse la = new FreeResponse();
        la.setExtraLines(50);
        PageBreak pb = new PageBreak();
        Divider dv = new Divider();
        
        
        
        exam.addItem(mc);
        exam.addItem(la);
        exam.addItem(pb);
        exam.addItem(dv);
        
        String test = exam.generateExam(true);
        assertTrue(test.matches(".*" + startTags + ".*" + questionTags + ".*"
         + endTags));
        test = exam.generateExam(false);
        assertTrue(test.matches(".*" + startTags + ".*" + questionTags + ".*"
         + endTags));
    }*/
    public void testGenerateExamPreviewPageBreak() 
    {
        /*
        exam.addItem(new PageBreak());
        insexamtance.addItem(new PageBreak());
        System.out.println("generateExam(false) with Page Breaks");
        String expResult = "<ol "
                + "style=\"margin:10px; padding:10px; list-style: decimal;"
                + "\">"
                + "<p>[Page Break]__________________________"
                + "___________________________</p><p></p>"
                + "<p>[Page Break]__________________________"
                + "___________________________</p><p></p>"
                + "</ol>";
        assertEquals(expResult, exam.generateExam(false));
        * */
        //TODO Jake
    }

    /**
     * Test of getTitle method, of class Exam.
     */
    public void testGetTitle() 
    {
        System.out.println("getTitle");
        exam.getTitleGroup().setTitle("thetest");
        String expResult = "thetest";
        String result = exam.getTitleGroup().getTitle();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTitle method, of class Exam.
     */
    public void testSetTitle() 
    {
        System.out.println("setTitle");
        String title = "setTitle";
        exam.getTitleGroup().setTitle(title);
        String result = exam.getTitleGroup().getTitle();
        assertEquals(result, title);
    }

    /**
     * Test of getTotalMarks method, of class Exam.
     */
    public void testGetTotalMarks()
    {
        System.out.println("getTotalMarks");
        Question question = new MultipleChoice();
        Question question2 = new MultipleChoice();
        question.setMarks(5);
        question2.setMarks(10);
        exam.addItem(question);
        exam.addItem(question2);
        int expResult = 15;
        int result = exam.getTotalMarks();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of setDescription method, of class Exam.
     */
    public void testSetDescription() 
    {
        System.out.println("setDescription");
        String desc = "BEST TEST EVER";
        exam.getTitleGroup().setDescription(desc);
        String expResult = exam.getTitleGroup().getDescription();
        assertEquals(expResult, desc);
    }

    /**
     * Test of getDescription method, of class Exam.
     */
    public void testGetDescription() 
    {
        System.out.println("getDescription");
        exam.getTitleGroup().setDescription("SUPER TEST");
        String expResult = "SUPER TEST";
        String result = exam.getTitleGroup().getDescription();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getTotalMarks method, of class Exam.
     */
    public void getTotalMarks()
    {
        System.out.println("getTotalMarks");
        int expResult = 4;
        DocumentItemApi q1 = new MultipleChoice();
        DocumentItemApi q2 = new Divider();
        DocumentItemApi q3 = new MultipleChoice();
        ((Question) q1).setMarks(3);
        ((Question) q3).setMarks(1);
        exam.addItem(q1);
        exam.addItem(q2);
        exam.addItem(q3);
        int result = exam.getTotalMarks();
        assertEquals(expResult, result);
    }

    /**
     * Test of moveGroup method, of class Exam.
     */
    public void testMoveGroup() 
    {
        System.out.println("moveGroup");
        int startIndex = 1;
        int endIndex = 2;
        int destination = 3;
        DocumentItemApi q1 = new MultipleChoice();
        DocumentItemApi q2 = new Divider();
        DocumentItemApi q3 = new MultipleChoice();
        DocumentItemApi q4 = new MultipleChoice();
        exam.addItem(q1);
        exam.addItem(q2);
        exam.addItem(q3);
        exam.addItem(q4);
        exam.moveGroup(startIndex, endIndex, destination);
        assertEquals(q2, exam.getItemAt(2));
        //Second test for lower destination index
        startIndex = 1;
        endIndex = 2;
        destination = 0;
        q1 = new MultipleChoice();
        q2 = new Divider();
        q3 = new MultipleChoice();
        q4 = new MultipleChoice();
        exam.addItem(q1);
        exam.addItem(q2);
        exam.addItem(q3);
        exam.addItem(q4);
        exam.moveGroup(startIndex, endIndex, destination);
        assertEquals(q1, exam.getItemAt(0));
        //Third test to break assert
        boolean exceptionCaught = false;
        try
        {
            exam.moveGroup(2,2,2);
            fail("Assertion not triggered.");
        }
        catch (AssertionError err)
        {
            exceptionCaught = true;
        }
    }
    
    /*
     * Test of generateSectionIndexes, of class Exam.
     */
    public void testGenerateSectionIndexes()
    {
        System.out.println("moveGroup");
        int index = 1;
        int index2 = 4;
        DocumentItemApi q1 = new MultipleChoice();
        DocumentItemApi q2 = new SectionTitle();
        DocumentItemApi q3 = new MultipleChoice();
        DocumentItemApi q4 = new MultipleChoice();
        exam.addItem(q1);
        exam.addItem(q2);
        exam.addItem(q3);
        exam.addItem(q4);
        int expResult = 3;
        int result = exam.generateSectionIndexes(index)[1];
        assertEquals(result, expResult);
        //Test null return
        exam.addItem(q2);
        int[] a = exam.generateSectionIndexes(index2);
        assertEquals(a, null);
        //test between two SectionTitles
        result = exam.generateSectionIndexes(index)[1];
        assertEquals(result, expResult);
    }
    
    /*
     * Test of randomizeSection, of class Exam.
     */
    public void testRandomizeSection()
    {
        System.out.println("randomizeSection");
        int titleIndex = 1;
        DocumentItemApi q1 = new MultipleChoice();
        DocumentItemApi q2 = new SectionTitle();
        Question q3 = new MultipleChoice();
        Question q4 = new MultipleChoice();
        q3.setText("a");
        q4.setText("b");
        exam.addItem(q1);
        exam.addItem(q2);
        exam.addItem(q3);
        exam.addItem(q4);
        exam.randomizeSection(titleIndex);
        DocumentItemApi expResult = exam.getItemAt(titleIndex);
        assertEquals(q2, expResult);
    }
    
    /*
     * Test of generateQuestionIndexes, of class Exam.
     */
    public void testGenerateQuestionIndexes()
    {
        System.out.println("randomizeQuestionIndexes");
        DocumentItemApi q1 = new SectionTitle();
        DocumentItemApi q2 = new MultipleChoice();
        Question q3 = new MultipleChoice();
        Question q4 = new MultipleChoice();
        exam.addItem(q1);
        exam.addItem(q2);
        exam.addItem(q3);
        exam.addItem(q4);
        DocumentItemApi expResult = q1;
        exam.randomizeAll(true, true, true);
        DocumentItemApi result = exam.getItemAt(0);
        assertEquals(expResult, result);
    }
    
    /*
     * Test of swap, of class Exam.
     */
    public void testSwap()
    {
        System.out.println("swap");
        int index1 = 0;
        int index2 = 2;
        DocumentItemApi q1 = new SectionTitle();
        DocumentItemApi q2 = new MultipleChoice();
        Question q3 = new MultipleChoice();
        exam.addItem(q1);
        exam.addItem(q2);
        exam.addItem(q3);
        exam.swap(index1, index2);
        DocumentItemApi expResult = q1;
        DocumentItemApi result = exam.getItemAt(2);
        assertEquals(expResult, result);
        exam.swap(index1,index2);
        result = exam.getItemAt(0);
        assertEquals(expResult, result);
    }
    
    /*
     * Test of removeItems, of class Exam. 
     */
    public void testRemoveItems()
    {
        System.out.println("swap");
        int[] idx = {2, 0};
        DocumentItemApi q1 = new SectionTitle();
        DocumentItemApi q2 = new MultipleChoice();
        Question q3 = new MultipleChoice();
        exam.addItem(q1);
        exam.addItem(q2);
        exam.addItem(q3);
        exam.removeItems(idx);
        int expResult = 1;
        int result = exam.getNumQuestions();
        assertEquals(expResult, result);
    }
    
    /*
     * Tests both setShowTitleGroup and getShowTitleGroup of class Exam.
     */
    public void testShowTitleGroupMethods()
    {
        System.out.println("setShowTitleGroup/getShowTitleGroup");
        DocumentItemApi q1 = new SectionTitle();
        DocumentItemApi q2 = new MultipleChoice();
        exam.addItem(q1);
        exam.addItem(q2);
        exam.getTitleGroup().setShowTitleGroup(true);
        boolean result = exam.getTitleGroup().getShowTitleGroup();
        boolean expResult = true;
        assertEquals(expResult, result);
    }
    
    public void testCellEditors()
    {
        System.out.println("cellEditors");
        exam.getTitleGroup().setTopLeftCell("magic");
        String result = exam.getTitleGroup().getTopLeftCell();
        String expResult = "magic";
        assertEquals(expResult, result);
        exam.getTitleGroup().setTopRightCell("illusion");
        result = exam.getTitleGroup().getTopRightCell();
        expResult = "illusion";
        assertEquals(expResult, result);
        exam.getTitleGroup().setBottomLeftCell("illusions");
        result = exam.getTitleGroup().getBottomLeftCell();
        expResult = "illusions";
        assertEquals(expResult, result);
    }
    
    public void testBottomRightCell()
    {
        System.out.println("(set/get) Bottom Right Cell");
        exam.getTitleGroup().setBottomRightCell("Total Marks: &lt;m&gt;");
        String result = exam.getTitleGroup().getBottomRightCell();
        String expResult = "Total Marks: &lt;m&gt;";
        assertEquals(expResult, result);   
    }
    
  
    public void testCopy() 
    {
        MultipleChoice mcq = new MultipleChoice();
        FreeResponse frq = new FreeResponse();
        TrueFalse tfq = new TrueFalse();
        int[] selectedRows = {1, 2};
        
        exam.addItem(mcq);
        exam.addItem(frq);
        exam.addItem(tfq);
        
        exam.copy(selectedRows);
        exam.paste(0);

        assertEquals(exam.getItemAt(1), exam.getItemAt(3));
        assertEquals(exam.getItemAt(2), exam.getItemAt(4));       
        
        //empty test
        int[] empty = {};
        exam.copy(empty);
    }
    
    public void testPaste()
    {
        MultipleChoice mcq = new MultipleChoice();
        FreeResponse frq = new FreeResponse();
        TrueFalse tfq = new TrueFalse();
        int[] selectedRows = {0, 1, 2};
        int[] pastedTo;
        
        exam.addItem(mcq);
        exam.addItem(frq);
        exam.addItem(tfq);
        
        exam.copy(selectedRows);
        exam.removeItem(2);
        exam.removeItem(1);
        exam.removeItem(0);
        pastedTo = exam.paste(0);
        
        
        assertEquals(0, pastedTo[0]);
        assertEquals(1, pastedTo[1]);
        assertEquals(2, pastedTo[2]);
        assertEquals(mcq, exam.getItemAt(0));
        assertEquals(tfq, exam.getItemAt(2));
        
        exam.removeItem(2);
        exam.removeItem(1);
        exam.removeItem(0);
        pastedTo = exam.paste(-1);
        
        
        assertEquals(0, pastedTo[0]);
        assertEquals(1, pastedTo[1]);
        assertEquals(2, pastedTo[2]);
        assertEquals(mcq, exam.getItemAt(0));
        assertEquals(tfq, exam.getItemAt(2));
        
        exam.paste(Integer.MAX_VALUE);
    }
    
    public void testCut()
    {
        MultipleChoice mcq = new MultipleChoice();
        FreeResponse frq = new FreeResponse();
        TrueFalse tfq = new TrueFalse();
        int[] selectedRows = {0, 1, 2};
        int[] selectedRows2 = {1, 2};
        int[] pastedTo;
        
        exam.addItem(mcq);
        exam.addItem(frq);
        exam.addItem(tfq);
        
        exam.cut(selectedRows);
        pastedTo = exam.paste(0);
        
        
        assertEquals(0, pastedTo[0]);
        assertEquals(1, pastedTo[1]);
        assertEquals(2, pastedTo[2]);
        assertEquals(mcq, exam.getItemAt(0));
        assertEquals(tfq, exam.getItemAt(2));
        
        exam.cut(selectedRows2);
        assertEquals(1, exam.getItems().size());
        assertEquals(mcq, exam.getItemAt(0));
        
        exam.paste(0);
        assertEquals(frq, exam.getItemAt(1));
        assertEquals(tfq, exam.getItemAt(2));
        
        //empty test
        int[] empty = {};
        exam.cut(empty);
    }
    
    public void testToggleAnswerDoc()
    {
       Exam exam = new Exam();
       
       assertFalse(exam.getAnswerDoc());
       exam.toggleAnswerDoc();
       assertTrue(exam.getAnswerDoc());
       exam.toggleAnswerDoc();
       assertFalse(exam.getAnswerDoc());
    }
    
    public void testHeaderContent()
    {
        String left = "moo";
        String middle = "cow";
        String right = "blah";
        Exam exam = new Exam();
        
        exam.getHeaderFooter().setHeaderContentLeft(left);
        exam.getHeaderFooter().setHeaderContentMiddle(middle);
        exam.getHeaderFooter().setHeaderContentRight(right);
        exam.getHeaderFooter().setShowHeader(true);
        
        assertEquals("Exam - getHeaderContentLeft failed",
         exam.getHeaderFooter().getHeaderContentLeft(), left);
        assertEquals("Exam - getHeaderContentMiddle failed",
         exam.getHeaderFooter().getHeaderContentMiddle(), middle);
        assertEquals("Exam - getHeaderContentRight failed",
         exam.getHeaderFooter().getHeaderContentRight(), right);
        assertTrue("Exam - getShowHeader failed", 
                exam.getHeaderFooter().getShowHeader());
    }
    
    public void testFooterContent()
    {
        String left = "moo";
        String middle = "cow";
        String right = "blah";
        Exam exam = new Exam();
        
        exam.getHeaderFooter().setFooterContentLeft(left);
        exam.getHeaderFooter().setFooterContentMiddle(middle);
        exam.getHeaderFooter().setFooterContentRight(right);
        exam.getHeaderFooter().setShowFooter(true);
        
        assertEquals("Exam - getHeaderContentLeft failed",
         exam.getHeaderFooter().getFooterContentLeft(), left);
        assertEquals("Exam - getHeaderContentMiddle failed",
         exam.getHeaderFooter().getFooterContentMiddle(), middle);
        assertEquals("Exam - getHeaderContentRight failed",
         exam.getHeaderFooter().getFooterContentRight(), right);
        assertTrue("Exam - getShowHeader failed", 
                exam.getHeaderFooter().getShowFooter());
    }
    
    public void testGetNumItems()
    {
        System.out.println("getNumItems");
        Exam exam = new Exam();
        assertEquals(0, exam.getNumItems());
        exam.addItem(new MultipleChoice());
        exam.addItem(new MultipleChoice());
        assertEquals(2, exam.getNumItems());
    }
    
    public void testSetPath()
    {
        System.out.println("setPath");
        exam.setPath("rara");
        assertEquals("rara", exam.getPath());
    }
    
    public void testNewExam()
    {
        System.out.println("newExam");
        exam.newExam();
        Exam exam2 = new Exam();
        exam.newExam(exam2);
        exam.newExam(null);
    }
    
    public void testTitleGroupConstructor()
    {
        TitleGroup tg = new TitleGroup();
        
        assertEquals("Sample Title", tg.getTitle());
    }
    
    public void testGetSetTitleGroupFontGlobal()
    {
        System.out.println("Get / SetTitleGroupFontGlobal");
        assertTrue(exam.getTitleGroup().getTitleGroupFontGlobal());
        exam.getTitleGroup().setTitleGroupFontGlobal(false);
        assertFalse(exam.getTitleGroup().getTitleGroupFontGlobal());
    }
    
    
    public void testSetTitleGroupFontSize()
    {
        System.out.println("setTitleGroupFontSize");
        exam.getTitleGroup().setTitleGroupFontSize(14);
        assertEquals(14, exam.getTitleGroup().getTitleGroupFontSize());
    }
    
    public void testSetTitleFontSize()
    {
        System.out.println("setTitleFontSize");
        exam.getTitleGroup().setTitleFontSize(14);
        assertEquals(14, exam.getTitleGroup().getTitleFontSize());
    }
    
    public void testGetLevels()
    {
        System.out.println("getLevels");
        Question item1 = new FreeResponse();
        DocumentItem item2 = new NumberingRestart();
        Question item3 = new MultipleChoice();
        Question item4 = new FreeResponse();
        Question item5 = new FreeResponse();
        
        item1.setLevel("Too Difficult");
        item3.setLevel("Too Easy");
        item4.setLevel("");
        item5.setLevel("Too Difficult");
        
        exam.addItem(item1);
        exam.addItem(item2);
        exam.addItem(item3);
        exam.addItem(item4);
        exam.addItem(item5);
        //test basic category grouping
        assertEquals(3, exam.getLevels().length);
        assertEquals("Too Difficult", exam.getLevels()[0]);
        assertEquals("", exam.getLevels()[1]);
        assertEquals("Too Easy", exam.getLevels()[2]);
        
        //Test empty exam
        exam = new Exam();
        assertEquals(0, exam.getLevels().length);
    }
    
    public void testGetCatgeories()
    {
        System.out.println("getCategories");
        Question item1 = new FreeResponse();
        DocumentItem item2 = new NumberingRestart();
        Question item3 = new MultipleChoice();
        Question item4 = new FreeResponse();
        Question item5 = new FreeResponse();
        
        item1.setCategory("Cool Questions");
        item3.setCategory("Lame Questions");
        item4.setCategory("");
        item5.setCategory("Cool Questions");
        
        exam.addItem(item1);
        exam.addItem(item2);
        exam.addItem(item3);
        exam.addItem(item4);
        exam.addItem(item5);
        //test basic category grouping
        assertEquals(3, exam.getCategories().length);
        assertEquals("Cool Questions", exam.getCategories()[0]);
        assertEquals("", exam.getCategories()[1]);
        assertEquals("Lame Questions", exam.getCategories()[2]);
        
        //Test empty exam
        exam = new Exam();
        assertEquals(0, exam.getCategories().length);
    }
    
    public void testGetCategoryIndexes()
    {
        System.out.println("getCategoryIndexes");
        Question item1 = new FreeResponse();
        DocumentItem item2 = new NumberingRestart();
        Question item3 = new MultipleChoice();
        Question item4 = new FreeResponse();
        Question item5 = new FreeResponse();
        
        item1.setCategory("Cool Questions");
        item3.setCategory("Lame Questions");
        item4.setCategory("");
        item5.setCategory("Cool Questions");
        
        exam.addItem(item1);
        exam.addItem(item2);
        exam.addItem(item3);
        exam.addItem(item4);
        exam.addItem(item5);
        //test category grouping
        assertEquals(2, exam.getCategoryIndexes("Cool Questions").length);
        assertEquals(1, exam.getCategoryIndexes("Lame Questions").length);
    }
    
    public void testGetLevelIndexes()
    {
        System.out.println("getLevelIndexes");
        Question item1 = new FreeResponse();
        DocumentItem item2 = new NumberingRestart();
        Question item3 = new MultipleChoice();
        Question item4 = new FreeResponse();
        Question item5 = new FreeResponse();
        
        item1.setLevel("Too Difficult");
        item3.setLevel("Too Easy");
        item4.setLevel("");
        item5.setLevel("Too Difficult");
        
        exam.addItem(item1);
        exam.addItem(item2);
        exam.addItem(item3);
        exam.addItem(item4);
        exam.addItem(item5);
        //test basic level grouping
        assertEquals(1, exam.getLevelIndexes("Too Easy").length);
        assertEquals(2, exam.getLevelIndexes("").length);
        assertEquals(2, exam.getLevelIndexes("Too Difficult").length);
        
    }
    
    public void testGetLevelAndCategoryIndexes()
    {
        System.out.println("getLevelAndCategoryIndexes");
        Question item1 = new FreeResponse();
        DocumentItem item2 = new NumberingRestart();
        Question item3 = new MultipleChoice();
        Question item4 = new FreeResponse();
        Question item5 = new FreeResponse();
        
        item1.setLevel("Too Difficult");
        item1.setCategory("Too Easy");
        item3.setLevel("Too Easy");
        item3.setCategory("Too Easy");
        item4.setLevel("");
        item4.setCategory("Too Easy");
        item5.setLevel("Too Difficult");
        item5.setCategory("Too Difficult");
        
        exam.addItem(item1);
        exam.addItem(item2);
        exam.addItem(item3);
        exam.addItem(item4);
        exam.addItem(item5);
        String[] first = {"Too Easy"};
        String[] second = {"Too Difficult"};
        //test basic category and level grouping
        assertEquals(0, exam.getLevelAndCategoryIndexes(first, second, true).length);
        first[0] = "Too Difficult";
        second[0] = "Too Easy";
        assertEquals(1, exam.getLevelAndCategoryIndexes(first, second, true).length);
        first[0] = "";
        second[0] = "Too Difficult";
        assertEquals(0, exam.getLevelAndCategoryIndexes(first, second, true).length);
        first[0] = "Too Difficult";
        second[0] = "Too Difficult";
        assertEquals(1, exam.getLevelAndCategoryIndexes(first, second, true).length);
    }
    
    public void testGetLevelAndCategoryIndexesMulti()
    {
        System.out.println("getLevelAndCategoryIndexes");
        Question item1 = new FreeResponse();
        DocumentItem item2 = new NumberingRestart();
        Question item3 = new MultipleChoice();
        Question item4 = new FreeResponse();
        Question item5 = new FreeResponse();
        
        item1.setLevel("Too Difficult");
        item1.setCategory("Too Easy");
        item3.setLevel("Too Easy");
        item3.setCategory("Too Easy");
        item4.setLevel("");
        item4.setCategory("Too Easy");
        item5.setLevel("Too Difficult");
        item5.setCategory("Too Difficult");
        
        exam.addItem(item1);
        exam.addItem(item2);
        exam.addItem(item3);
        exam.addItem(item4);
        exam.addItem(item5);
        String[] first = {"Too Easy", "Too Difficult", ""};
        String[] second = {"Too Difficult"};
        //test basic category and level grouping
        assertEquals(1, exam.getLevelAndCategoryIndexes(first, second, true).length);
        assertEquals(2, exam.getLevelAndCategoryIndexes(second, first, true).length);
        first[0] = "";
        second[0] = "Too Easy";
        assertEquals(3, exam.getLevelAndCategoryIndexes(first, second, true).length);
        first[0] = "Too Easy";
        assertEquals(5, exam.getLevelAndCategoryIndexes(first, first, true).length);
    }
    
    public void testGetEitherLevelAndCategoryIndexesMulti()
    {
        System.out.println("getLevelAndCategoryIndexes");
        Question item1 = new FreeResponse();
        DocumentItem item2 = new NumberingRestart();
        Question item3 = new MultipleChoice();
        Question item4 = new FreeResponse();
        Question item5 = new FreeResponse();
        
        item1.setLevel("Too Difficult");
        item1.setCategory("Too Easy");
        item3.setLevel("Too Easy");
        item3.setCategory("Too Easy");
        item4.setLevel("");
        item4.setCategory("Too Easy");
        item5.setLevel("Too Difficult");
        item5.setCategory("Too Difficult");
        
        exam.addItem(item1);
        exam.addItem(item2);
        exam.addItem(item3);
        exam.addItem(item4);
        exam.addItem(item5);
        String[] first = {"Too Easy", "Too Difficult", ""};
        String[] second = {"Too Difficult"};
        //test basic category and level grouping
        assertEquals(5, exam.getLevelAndCategoryIndexes(first, second, false).length);
        assertEquals(5, exam.getLevelAndCategoryIndexes(second, first, false).length);
        first[0] = "";
        first[1] = "";
        second[0] = "";
        assertEquals(2, exam.getLevelAndCategoryIndexes(first, second, false).length);
        first[0] = "Dumb";
        assertEquals(2, exam.getLevelAndCategoryIndexes(first, second, false).length);
    }
    
    public void testFilterActive()
    {
        System.out.println("getLevelAndCategoryIndexesMulti");
        Question item1 = new FreeResponse();
        DocumentItem item2 = new NumberingRestart();
        Question item3 = new MultipleChoice();
        Question item4 = new FreeResponse();
        Question item5 = new FreeResponse();
        
        item1.setCategory("Too Difficult");
        item1.setLevel("Too Easy");
        item3.setLevel("Too Easy");
        item4.setLevel("Too Easy");
        item4.setCategory("Too Difficult");
        item5.setCategory("Too Difficult");
        
        exam.addItem(item1);
        exam.addItem(item2);
        exam.addItem(item3);
        exam.addItem(item4);
        exam.addItem(item5);
        
        String[] first = {"Too Easy"};
        String[] second = {"Too Difficult"};
        
        int[] actArray = exam.getLevelAndCategoryIndexes(first, second, true);
        //actArray is now {0, 3}
        exam.filterActive(actArray);
        //test activity of each item
        assertTrue(exam.getItemAt(0).isActive());
        assertTrue(!exam.getItemAt(1).isActive());
        assertTrue(!exam.getItemAt(2).isActive());
        assertTrue(exam.getItemAt(3).isActive());
        assertTrue(!exam.getItemAt(4).isActive());
        
        first[0] = "";
        second[0] = "";
        
        actArray = exam.getLevelAndCategoryIndexes(first, second, true);
        //actArray is now {1}
        exam.filterActive(actArray);
        //ensure that only DocItems are set
        assertTrue(!exam.getItemAt(0).isActive());
        assertTrue(exam.getItemAt(1).isActive());
        assertTrue(!exam.getItemAt(2).isActive());
        assertTrue(!exam.getItemAt(3).isActive());
        assertTrue(!exam.getItemAt(4).isActive());
        
        //Test multiple strings for each array
        String[] levels = {"Too Easy"};
        String[] categories = {"Too Easy", "Too Difficult"};
        
        actArray = exam.getLevelAndCategoryIndexes(levels, categories, true);
        //actArray is now {0, 3}
        exam.filterActive(actArray);
        //ensure that only appropriate items
        assertTrue(exam.getItemAt(0).isActive());
        assertTrue(!exam.getItemAt(1).isActive());
        assertTrue(!exam.getItemAt(2).isActive());
        assertTrue(exam.getItemAt(3).isActive());
        assertTrue(!exam.getItemAt(4).isActive());
    }
    
    public void testIntersectIntArrays()
    {
        System.out.println("intersectIntArrays");
        int[] first = {1, 2, 3};
        int[] second = {1, 3};
        //Test basic two matches
        assertEquals(2, exam.intersectIntArrays(first, second).length);
        assertEquals(1, exam.intersectIntArrays(first, second)[0]);
        assertEquals(3, exam.intersectIntArrays(first, second)[1]);
        
        //test no matches
        second[0] = 4;
        second[1] = 5;
        assertEquals(0, exam.intersectIntArrays(first, second).length);
        
        //test empty arrays
        first = new int[0];
        second = new int[0];
        assertEquals(0, exam.intersectIntArrays(first, second).length);
    }
    
    public void testFillArray()
    {
        System.out.println("fillArray");
        exam.addItem(new SectionTitle());
        exam.addItem(new MultipleChoice());
        exam.addItem(new MultipleChoice());
        exam.addItem(new SectionTitle());
        exam.addItem(new MultipleChoice());
        exam.addItem(new MultipleChoice());
        int[] tooSmall = {0, 1, 2, 3, 4};
        exam.randomize(tooSmall, false, false, false);
    }
    
    public void testUnionIntArray()
    {
        System.out.println("unionIntArrays");
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 3, 4};
        assertEquals(4, exam.unionIntArrays(array1, array2).length);
        
        array1[1] = 1;
        array1[2] = 1;
        assertEquals(3, exam.unionIntArrays(array1, array2).length);  
        
        array2[0] = 3;
        array2[2] = 3;
        assertEquals(2, exam.unionIntArrays(array1, array2).length); 
       
        array2[0] = 1;
        array2[1] = 1;
        array2[2] = 1;
        assertEquals(1, exam.unionIntArrays(array1, array2).length);
    }
    
    public void testInstantiateImages()
    {
        System.out.println("instantiateImages");
        exam.addItem(new MultipleChoice());
        exam.instantiateImages();
    }
    
    public void testContainsReference()
    {
        System.out.println("awr");
        exam.addItem(new MultipleChoice());
        assertFalse(exam.containsReference(new MultipleChoice()));
        Order order = new Order();
        exam.addItem(order);
        assertTrue(exam.containsReference(order));
    }
    
    public void testGetNumType()
    {
        System.out.println("getNumType");
        exam.addItem(new FillInTheBlank());
        exam.addItem(new MultipleChoice());
        exam.addItem(new FillInTheBlank());
        
        assertEquals(2, exam.getNumType(new FillInTheBlank()));
        assertEquals(1, exam.getNumType(new MultipleChoice()));
        assertEquals(0, exam.getNumType(new Matching()));
    }
    
    public void testCleanUp()
    {
        System.out.println("getNumType");
        exam.addItem(new FillInTheBlank());
        exam.addItem(new Instructions());
        exam.cleanUp();
        exam.getItemAt(0).setActive(false);
        exam.updateList();
    }

    public void testMoodleEmpty()
    {
        Document doc = newDocument();
        exam.generateMoodle(doc);
        Node quiz = doc.getFirstChild();
        NodeList nl = quiz.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++)
        {
            assertFalse(nl.item(i).getNodeName().equals("question"));
        }
    }

    public void testMoodlePopulated()
    {
        Document doc = newDocument();
        exam.addItem(new MultipleChoice());
        exam.generateMoodle(doc);
        Node quiz = doc.getFirstChild();
        NodeList nl = quiz.getChildNodes();
        assertTrue(nl.item(1).getNodeName().equals("question"));
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
