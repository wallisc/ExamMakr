/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolution.utilities;

import java.io.File;
import javax.swing.filechooser.FileFilter;
import junit.framework.TestCase;
import qsolutions.api.FilterApi;

/**
 *
 * @author Chris
 */
public class FilterTest extends TestCase
{
    public FilterTest(String testName)
    {
        super(testName);
    }

    /**
     * Test of makeExamFilter method, of class Filter.
     */
    public void testMakeExamFilter()
    {
        FilterApi filter = new Filter();
        FileFilter fFilter = filter.makeExamFilter();
        File examFile = new File("blah.exm");
        File otherFile = new File("garbage.pdf");
        assertEquals(fFilter.getDescription(), Filter.kExamFileDescription);
        assertTrue(fFilter.accept(examFile));
        assertFalse(fFilter.accept(otherFile));
    }

    /**
     * Test of makePdfFilter method, of class Filter.
     */
    public void testMakePdfFilter()
    {
        FilterApi filter = new Filter();
        FileFilter fFilter = filter.makePdfFilter();
        File otherFile = new File("blah.exm");
        File pdfFile = new File("garbage.pdf");
        assertEquals(fFilter.getDescription(), Filter.kPDFFileDescription);
        assertTrue(fFilter.accept(pdfFile));
        assertFalse(fFilter.accept(otherFile));
    }
}
