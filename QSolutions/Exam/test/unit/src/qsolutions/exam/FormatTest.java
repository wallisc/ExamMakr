/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.exam;

import java.io.File;
import junit.framework.TestCase;
import property.AnswerIndexing;
import property.Marks;
import qsolutions.api.FormatApi.Property;

/**
 *
 * @author michaelmease
 */
public class FormatTest extends TestCase {
    
    public FormatTest(String testName) {
        super(testName);
    }

    /**
     * Test of getProperty method, of class Format.
     */
    public void testGetProperty() {
        System.out.println("getProperty");
        Property prop = Property.ShowAnswerLines;
        Format instance = new Format();
        Object expResult = false;
        Object result = instance.getProperty(prop);
        assertEquals(expResult, result);        
    }

    /**
     * Test of setProperty method, of class Format.
     */
    public void testSetProperty() {
        System.out.println("setProperty");
        Property prop = Property.ShowAnswerLines;
        Object ob = true;
        Format instance = new Format();
        instance.setProperty(prop, ob);
        Object expResult = true;
        Object result = instance.getProperty(prop);
        assertEquals(expResult, result);
    }
    
    /*
     * Test the various functions within the MCIndexing enum of class Format.
     */
    public void testMCIndexing()
    {
        AnswerIndexing.AnswerIndexingEnum mc = AnswerIndexing.AnswerIndexingEnum.CapLetters;
        assertEquals("getNdx returned the wrong int", 0, mc.getNdx());
        assertEquals(AnswerIndexing.AnswerIndexingEnum.CapLetters, 
                mc.newPropertyEnum(0));
        assertEquals(AnswerIndexing.AnswerIndexingEnum.LowLetters, 
                mc.newPropertyEnum(1));
        assertEquals(AnswerIndexing.AnswerIndexingEnum.CapRoman, 
                mc.newPropertyEnum(2));
        assertEquals(AnswerIndexing.AnswerIndexingEnum.LowRoman, 
                mc.newPropertyEnum(3));
        assertEquals(AnswerIndexing.AnswerIndexingEnum.Numbers, 
                mc.newPropertyEnum(4));
        assertEquals(AnswerIndexing.AnswerIndexingEnum.LowLetters, 
                mc.newPropertyEnum(-1));
    }
    
    /*
     * Test the various functions within the MarksPositions enum of class Format.
     */
    public void testMarksPositions()
    {
        Marks.MarksEnum mp = Marks.MarksEnum.Left;
        assertEquals("getNdx returned the wrong int", 0, mp.getNdx());
        assertEquals(Marks.MarksEnum.Left, 
                mp.newPropertyEnum(0));
        assertEquals(Marks.MarksEnum.Right, 
                mp.newPropertyEnum(1));
        assertEquals(Marks.MarksEnum.InLine, 
                mp.newPropertyEnum(2));
        assertEquals(Marks.MarksEnum.Left, mp.newPropertyEnum(-1));
    }
    

    /**
     * Test of getStyleBlock method, of class Format.
     */
    public void testGetStyleBlock() {
        System.out.println("getStyleBlock");
        Format format = new Format();
        format.setProperty(Property.MarksPosition, Marks.MarksEnum.Left);
        String expResult = "<style type=\"text/css\">"
                + ".section_title{margin-left:-40px;}"
                + ".page_break{page-break-after:always;}"
                + ".question_item{margin-bottom:20px;"
                + "page-break-inside:avoid;}"
                + ".multiple_choice-choices{list-style-type:lower-alpha}"
				+ "@page {"
                + "margin: 1in;"
                + "@bottom-center { content: element(footer); }"
 				+"	@top-center { content: element(header); }"
                +" }"
				+"#footer {font-size: 90%; "
                + "font-style: italic; "
                + "position: running(footer); }"
				+"#header {font-size: 90%; "
                + "font-style: italic; "
                + "position: running(header); } "
                + "#pagenumber:before {content: counter(page); }"
                + "#pagecount:before {content: counter(pages);  }"
                + "hr{margin-left:-40px}"
                + "</style>";
        String result = format.getStyleBlock();
        
        assertTrue(format.getHorizontalAlignment());
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getHtmlHeader method, of class Format.
     */
    public void testGetHtmlHeader() {
        System.out.println("getHtmlHeader");
        String expResult = "<div id=\"header\">Header contents</div>";
        Format format = new Format();
        String result = format.getHtmlHeader("", "", "");
        //assertEquals(expResult, result);
    }

    /**
     * Test of getHtmlFooter method, of class Format.
     */
    public void testGetHtmlFooter() {
        System.out.println("getHtmlFooter");
        Format format = new Format();
        String expResult = "<div id=\"footer\">Footer contents: "
                + "Example - Page <span id=\"pagenumber\"/> "
                + "of <span id=\"pagecount\"/> </div>";
        String result = format.getHtmlFooter("", "", "");
        //assertEquals(expResult, result);
    }

    /**
     * Test of getHtmlBodyStart method, of class Format.
     */
    public void testGetHtmlBodyStart() {
        System.out.println("getHtmlBodyStart");
        Format format = new Format();
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
                + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
                + "<head>"
                + "<title>Questionable TestMakr</title>"
                + format.getStyleBlock()
                + "</head>"
                + "<body>";
        String result = format.getHtmlBodyStart();
        assertEquals(expResult, result);
    }

    /**
     * Test of getHtmlBodyEnd method, of class Format.
     */
    public void testGetHtmlBodyEnd() {
        System.out.println("getHtmlBodyEnd");
        String expResult = "</body></html>";
        Format format = new Format();
        String result = format.getHtmlBodyEnd();
        assertEquals(expResult, result);
    }
    
    public void testGetMarksHTML()
    {
        System.out.println("getMarksHTML");
        Format format = new Format();
        String expResult = "<div class=\"marks-left\">(3)  </div> " ;
        String result = format.getMarksHtml(3, Marks.MarksEnum.Left, 
                true);
        assertEquals(expResult, result);
        
        expResult = "<div class=\"marks-right\">(3)  </div> " ;
        result = format.getMarksHtml(3, Marks.MarksEnum.Right, true);
        assertEquals(expResult, result);
        
        expResult = "<p style=\"margin-top: 0\">(3)  ";
        result = format.getMarksHtml(3, Marks.MarksEnum.InLine, 
                false);
        assertEquals(expResult, result);
    }
    
    public void testGetMarksPositionIndex()
    {
        System.out.println("getMarksPositionIndex");
        Format format = new Format();
        assertEquals(2, format.getMarksPositionIndex(
                Marks.MarksEnum.InLine));
        assertEquals(0, format.getMarksPositionIndex(new Object()));
    }
    
    public void testGetMcNumberingTypeIndex()
    {
        System.out.println("getMcNumberingTypeIndex");
        Format format = new Format();
        assertEquals(2, format.getMcNumberingTypeIndex(
                AnswerIndexing.AnswerIndexingEnum.CapRoman));
        assertEquals(0, format.getMcNumberingTypeIndex(new Object()));
    }
    
    public void getHorizontalAlignment()
    {
        System.out.println("getHorizontalAlignment");
        Format format = new Format();
        format.getHorizontalAlignment();
        assertTrue(format.getHorizontalAlignment());
    }
    
    public void testGetMarksPositionFromInt()
    {
        System.out.println("getMarksPositionFromInt");
        Format format = new Format();
        assertEquals(Marks.MarksEnum.Left, 
                format.getMarksPositionFromInt(0));
        assertEquals(Marks.MarksEnum.Right, 
                format.getMarksPositionFromInt(1));
        assertEquals(Marks.MarksEnum.InLine, 
                format.getMarksPositionFromInt(2));
        assertEquals(Marks.MarksEnum.Left, 
                format.getMarksPositionFromInt(-1));
    }
    
    public void testGetMcNumberingTypeFromInt()
    {
        System.out.println("getMcNumberingTypeFromInt");
        Format format = new Format();
        assertEquals(AnswerIndexing.AnswerIndexingEnum.CapLetters, 
                format.getMcNumberingTypeFromInt(0));
        assertEquals(AnswerIndexing.AnswerIndexingEnum.LowLetters, 
                format.getMcNumberingTypeFromInt(1));
        assertEquals(AnswerIndexing.AnswerIndexingEnum.CapRoman, 
                format.getMcNumberingTypeFromInt(2));
        assertEquals(AnswerIndexing.AnswerIndexingEnum.LowRoman, 
                format.getMcNumberingTypeFromInt(3));
        assertEquals(AnswerIndexing.AnswerIndexingEnum.Numbers, 
                format.getMcNumberingTypeFromInt(4));
        assertEquals(AnswerIndexing.AnswerIndexingEnum.LowLetters, 
                format.getMcNumberingTypeFromInt(-1));
    }
    
    public void testGetImageHtml()
    {
        System.out.println("getImageHtml");
        Format format = new Format();
        File file = new File(FormatTest.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + "GUI" + File.separator + "src" + File.separator + "img" + File.separator + "bold.png");
        try
        {
            ItemImage image = new ItemImage(file);
            format.getImageHtml(image.getImageFile(), 1F, 0);
            format.getImageHtml(image.getImageFile(), 1F, 1);
            format.getImageHtml(image.getImageFile(), 1F, 2);
            format.getImageHtml(image.getImageFile(), 1F, 3);
            format.getImageHtml(image.getImageFile(), 1F, 500);
        }
        catch(Exception ex)
        {
            fail("Could not find file bold.png");
        }
    }
    
    public void testGetPageScale()
    {
        System.out.println("getPageScale");
        Format format = new Format();
        assertEquals(2.0, format.getPageScale(575*2), .01);
    }
}
