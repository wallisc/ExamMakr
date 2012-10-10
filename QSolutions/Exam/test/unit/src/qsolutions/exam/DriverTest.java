/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.exam;

import java.io.File;
import java.io.IOException;
import junit.framework.TestCase;
import qsolutions.api.DocumentItemApi;
import qsolutions.api.ExamApi;

/**
 *
 * @author michaelmease
 */
public class DriverTest extends TestCase
{
    
    public DriverTest(String testName) 
    {
        super(testName);
    }

    /**
     * Test of removeDesignItemIndexes method, of class Driver.
     */
    public void testRemoveDesignItemIndexes() 
    {
        System.out.println("removeDesignItemIndexes");
        Driver driver = new Driver();
        int[] indexes = new int[3];
        indexes[0] = 0;
        indexes[1] = 1;
        indexes[2] = 3;
        Exam instance = new Exam();
        DocumentItem q1 = new SectionTitle();
        DocumentItem q2 = new MultipleChoice();
        Question q3 = new MultipleChoice();
        Question q4 = new MultipleChoice();
        instance.addItem(q1);
        instance.addItem(q2);
        instance.addItem(q3);
        instance.addItem(q4);
        int expResult = 1;
        int result = driver.removeDesignItemIndexes(indexes, instance)[0];
        assertEquals(expResult, result);
        
    }

    /**
     * Test of saveTest method, of class Driver.
     */
    public void testSaveTest() 
    {
        System.out.println("saveTest");
        Driver driver = new Driver();
        Exam testToSave = new Exam();
        File file = null; // = new File("eFile.tst");
        
        try
        {
            file = File.createTempFile("eFile", ".tst");
            file.deleteOnExit();
        }
        catch(IOException e)
        {
            fail("IOException when creating files.");
        }
        driver.saveTest(testToSave, file);
        assertTrue(file.exists());
        boolean success = file.delete();
        if(!success)
        {
            fail("File could not be found, and thus not deleted");
        }        
        driver.saveTest(testToSave, null);
        driver.saveTest(null, new File("fakeFileName"));
    }

    /**
     * Test of loadTest method, of class Driver.
     */
    public void testLoadTest() 
    {
        System.out.println("loadTest");
        Exam testToSave = new Exam();
        testToSave.addItem(new MultipleChoice());
        ExamApi testToLoad;
       //File file = new File("eFile.tst");
                File file = null; // = new File("eFile.tst");
        
        try
        {
            file = File.createTempFile("eFile", ".tst");
            file.deleteOnExit();
        }
        catch(IOException e)
        {
            fail("IOException when creating files.");
        }
        Driver driver = new Driver();
        driver.saveTest(testToSave, file);
        assertTrue(file.exists());
        testToLoad = driver.loadTest(file);
        DocumentItemApi expResult = testToSave.getItemAt(0);
        DocumentItemApi result = testToLoad.getItemAt(0);
        assertEquals(expResult, result);
        boolean success = file.delete();
        if(!success)
        {
            fail("File could not be found, and thus not deleted");
        }
        //attempt to cause a NullPointerException
        driver.loadTest(null);
        //attempt to cause an IOException
        driver.loadTest(new File("notafile"));
        //attempt to cause a ClassNotFoundException
        driver.loadTest(new File("Driver.java"));
    }
    
    public void testCleanHTML()
    {
        System.out.println("cleanHtml");
        String badString = "<html><head></head><body><p style=\"margin-top: 0\">Herpa Derpa <b class=\"answer_line\">Tag'd</b></p>";
        Driver driver = new Driver();
        String result = driver.cleanHTML(badString);
        String expResult = "<p style=\"margin-top: 0\">Herpa Derpa <b class=\"answer_line\">Tag'd</b></p>";
        assertEquals(expResult, result);
        driver.cleanHTML(badString);
    }
    
    public void testRemoveAllTags()
    {
        System.out.println("removeAllTags");
        String badString = "<b>efaw</b><i>fwaf<b></i>ee</b>";
        String result = Driver.removeAllTags(badString);
        String expResult = "efawfwafee";
        assertEquals(expResult, result);
    }
    
    public void testFixSpaces()
    {
        System.out.println("fixSpaces");
        String htmlString = "     hi";
        String expResult = "&nbsp;&nbsp;&nbsp;&nbsp; hi";
        String result = new Driver().fixSpaces(htmlString);
        assertEquals(expResult, result);
    }
    
    public void testCompareExtracted()
    {
        System.out.println("compareExtracted");
        DocumentItemApi item1 = new MultipleChoice();
        item1.setText("<body><pstyle=\"margin-top:0\">sdf</p></body>");
        DocumentItemApi item2 = new MultipleChoice();
        item2.setText("<body><pstyle=\"margin-top:0\">wfe</p></body>");
        assertFalse(new Driver().compareExtracted(item1, item2));
        item1.setText("<pstyle=\"margin-top:0\"></p><body></body>");
        assertTrue(new Driver().compareExtracted(item1, item1));
        item1.setText("fake");
        assertTrue(new Driver().compareExtracted(item1, item1));
    }
}
