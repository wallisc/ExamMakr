/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolution.utilities;

import com.sun.pdfview.PDFFile;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import junit.framework.TestCase;

/**
 *
 * @author Ryan Dollahon
 */
public class PDFPrintPageTest extends TestCase
{
    public PDFPrintPageTest(String testName)
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
     * Test of print method, of class PDFPrintPage.
     */
    public void testPrint() throws Exception
    {
        
        System.out.println("print");
        PageFormat format = new PageFormat();
        int width = ( int ) format.getWidth();
        int height = ( int ) format.getHeight();
        Image pageImage = new BufferedImage( 
                                width, height, BufferedImage.TYPE_INT_ARGB );
        Graphics g = pageImage.getGraphics();
        
        int pages = 0;
        File dir = new File(PDFPrintPageTest.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + "test" + File.separator + "unit" + File.separator + "src" + File.separator + "qsolution" + File.separator + "testfiles");
        FileInputStream fis = new FileInputStream(new File(dir.getCanonicalPath(), "testPdf.pdf"));
        byte[] byteArr = new byte[fis.available()];
        fis.read(byteArr, 0, fis.available());
        PDFFile file = new PDFFile(ByteBuffer.wrap(byteArr));
        PDFPrintPage instance = new PDFPrintPage(file);
        int result = instance.print(g, format, pages);
        assertEquals(0, result);
    }
}
