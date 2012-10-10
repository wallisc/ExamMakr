/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.exam;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import qsolutions.api.DocumentItemApi;
import qsolutions.api.FormatApi;

/**
 *
 * @author michaelmease
 */
public class DocumentItemTest extends TestCase {
    
    public DocumentItemTest(String testName) {
        super(testName);
    }
    
    File file = new File(ItemImageTest.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + "GUI" + File.separator + "src" + File.separator + "img" + File.separator + "bold.png");
    File file2 = new File(ItemImageTest.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + "GUI" + File.separator + "src" + File.separator + "img" + File.separator + "bold24.png");

    /**
     * Test of copy method, of class DocumentItem.
     
    public void testCopy() {
        System.out.println("copy");
        DocumentItem instance = new DocumentItemImpl();
        DocumentItem expResult = null;
        DocumentItem result = instance.copy();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of clone method, of class DocumentItem.
     */
    public void testClone() {
        System.out.println("clone");
        DocumentItem instance = new DocumentItemImpl();
        DocumentItem expResult = null;
        instance.setActive(true);
        instance.setAbbreviation("MC");
        DocumentItem result = instance.clone();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAbbreviation method, of class DocumentItem.
     */
    public void testSetAndGetAbbreviation() {
        System.out.println("setAbbreviation and getAbbreviation");
        DocumentItem instance = new DocumentItemImpl();
        String expResult = "truez";
        instance.setAbbreviation("truez");
        String result = instance.getAbbreviation();
        assertEquals(expResult, result);
    }

    /**
     * Test of setKeepTogether method, of class DocumentItem.
     */
    public void testSetAndsKeepTogether() {
        System.out.println("setKeepTogether and isTogether");
        boolean nKeepTogether = false;
        DocumentItem instance = new DocumentItemImpl();
        instance.setKeepTogether(nKeepTogether);
        assertEquals(false, instance.isKeepTogether());
        instance.setKeepTogether(!nKeepTogether);
        assertEquals(true, instance.isKeepTogether());
    }

    /**
     * Test of copyData method, of class DocumentItem.
     */
    public void testCopyData() {
        System.out.println("copyData");
        DocumentItem copy = new DocumentItemImpl();
        DocumentItem instance = new DocumentItemImpl();
        instance.setActive(true);
        instance.setAbbreviation("MC");
        instance.copyData(copy);
        assertEquals(true, copy.isActive());
        assertEquals("MC", copy.getAbbreviation());
    }

    /**
     * Test of equals method, of class DocumentItem.
     */
    public void testEquals() {
        System.out.println("equals");
        DocumentItem instance = new DocumentItemImpl();
        DocumentItem instance2 = new DocumentItemImpl();
        ArrayList instance3 = new ArrayList();
        DocumentItem instance4 = new DocumentItemImpl();
        DocumentItem instance5 = new DocumentItemImpl();
        DocumentItem instance6 = new DocumentItemImpl();
        instance.setAbbreviation("ML");
        instance2.setAbbreviation("LL");
        instance.setType("LL");
        instance2.setType("LL");
        instance5.setText("I'm different!");
        instance5.setActive(false);
        instance5.setAbbreviation("NO!");
        instance6.setActive(false);
        
        assertEquals("Two DocumentItems with the same data were not equal", 
                     instance, instance2);
        assertFalse("Two DocumentItems with different data were equal", 
                     instance.equals(instance5));
        assertTrue("The equals method returned true between an ArrayList and"
                 + "a DocumentItem", !instance.equals(instance3));
        instance4.setType("HARRR");
        assertTrue("Two document items with different types were considered "
                 + "equal", !instance.equals(instance4));
        assertTrue("Two document items with different active states were considered "
                 + "equal", !instance.equals(instance6));
    }

    /**
     * Test of setActive method, of class DocumentItem.
     */
    public void testSetActive() {
        System.out.println("setActive");
        boolean active = false;
        DocumentItem instance = new DocumentItemImpl();
        instance.setActive(active);
        assertEquals(false, instance.isActive());
    }

    /**
     * Test of getText method, of class DocumentItem.
     
    public void testGetText() {
        System.out.println("getText");
        DocumentItem instance = new DocumentItemImpl();
        String expResult = "";
        String result = instance.getText();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of setText method, of class DocumentItem.
     */
    public void testSetText() {
        System.out.println("setText");
        String text = "erw";
        DocumentItem instance = new DocumentItemImpl();
        instance.setText(text);
        assertEquals("erw", instance.getText());
    }

    /**
     * Test of getMarks method, of class DocumentItem.
     */
    public void testGetMarks() {
        System.out.println("getMarks");
        DocumentItem instance = new DocumentItemImpl();
        int expResult = -1;
        int result = instance.getMarks();
        assertEquals(expResult, result);
    }

    /**
     * Test of getQNum method, of class DocumentItem.
     */
    public void testGetQNum() {
        System.out.println("getQNum");
        DocumentItem instance = new DocumentItemImpl();
        int expResult = -1;
        int result = instance.getQNum();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCategory method, of class DocumentItem.
     */
    public void testGetCategory() {
        System.out.println("getCategory");
        DocumentItem instance = new DocumentItemImpl();
        String expResult = "";
        String result = instance.getCategory();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLevel method, of class DocumentItem.
     */
    public void testGetLevel() {
        System.out.println("getLevel");
        DocumentItem instance = new DocumentItemImpl();
        String expResult = "";
        String result = instance.getLevel();
        assertEquals(expResult, result);
    }

    /**
     * Test of setType method, of class DocumentItem.
     */
    public void testSetType() {
        System.out.println("setType");
        String type = "medium";
        DocumentItem instance = new DocumentItemImpl();
        instance.setType(type);
        assertEquals("medium", instance.getType());
    }

    /**
     * Test of getType method, of class DocumentItem.
     */
    public void testGetType() {
        System.out.println("getType");
        DocumentItem instance = new DocumentItemImpl();
        String expResult = "";
        String result = instance.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getHTML method, of class DocumentItem.
     */
    public void testGetHTML() {
        System.out.println("getHTML");
        Format format = new Format();
        boolean isAnswerDoc = false;
        DocumentItem instance = new DocumentItemImpl();
        instance.setActive(false);
        String expResult = "";
        String result = instance.getHTML(format, isAnswerDoc);
        assertEquals("Test of inactive branch failed", expResult, result);
        instance.setActive(true);
        expResult = "";
        result = instance.getHTML(format, isAnswerDoc);
        assertEquals("Test of active branch failed", expResult, result);
    }
    
    public void testSetAndGetScale()
    {
        System.out.println("setScale and getScale");
        DocumentItem instance = new DocumentItemImpl();
        try
        {
            instance.setImage(new File(DocumentItemTest.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + "GUI" + File.separator + "src" + File.separator + "img" + File.separator + "underline.png"));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        instance.setScale(2F);
        assertEquals(2F, instance.getScale());
    }
    
    public void testGetImage()
    {
        System.out.println("setImageFile and getImageFile");
        File file = new File(DocumentItemTest.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + "GUI" + File.separator + "src" + File.separator + "img" + File.separator + "underline.png");
        DocumentItem instance = new DocumentItemImpl();
        try
        {
            instance.setImage(file);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        assertTrue(null != instance.getImage());
        instance = new DocumentItemImpl();
        assertEquals(null, instance.getImage());
    }
    
    public void testSetAndGetImageFile()
    {
        System.out.println("setImageFile and getImageFile");
        File file = new File(DocumentItemTest.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + "GUI" + File.separator + "src" + File.separator + "img" + File.separator + "underline.png");
        DocumentItem instance = new DocumentItemImpl();
        instance.setImageFile(file);
        assertEquals(file, instance.getImageFile());
    }
    
    public void testEqualImage()
    {
        System.out.println("equalImage");
        MultipleChoice one = new MultipleChoice();
        MultipleChoice two = new MultipleChoice();
        try
        {
            one.setImage(file);
            two.setImage(file2);
            assertFalse(one.equals(two));
        }
        catch(Exception ex)
        {
            fail("Image files could not be loaded for test.");
        }
        one = new MultipleChoice();
        two = new MultipleChoice();
        one.setScale(300F);
        assertFalse(one.equals(two));
        one = new MultipleChoice();
        two = new MultipleChoice();
        one.setImageAlignment(2);
        assertFalse(one.equals(two));
    }
    
    public void testSetShallowImage()
    {
        System.out.println("setShallowImage");
        MultipleChoice mc = new MultipleChoice();
        MultipleChoice mc2 = new MultipleChoice();
        mc.setImageFile(file);
        
        mc2.setShallowImage(mc);
        assertEquals(file, mc2.getImageFile());
    }
    
    public void testTempFile()
    {
        System.out.println("createTempFile and deleteTempFile");
        File file = new File(DocumentItemTest.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + "GUI" + File.separator + "src" + File.separator + "img" + File.separator + "underline.png");
        DocumentItem instance = new DocumentItemImpl();
        try
        {
            instance.setImage(file);
        }
        catch(IOException ex)
        {
            instance.createTempFile();
        }
        instance.createTempFile();
        instance.deleteTemp();
    }

    public class DocumentItemImpl extends DocumentItem {

        public DocumentItem copy() {
            return null;
        }
        
        public String getHTML(FormatApi f, boolean bad )
        {
            return "";
        }
        
        public DocumentItemApi newItem()
        {
            return (DocumentItemApi)new DocumentItemImpl();
        }

        @Override
        public Element serializeMoodle(Document doc)
        {
            return null;
        }
    }
}
