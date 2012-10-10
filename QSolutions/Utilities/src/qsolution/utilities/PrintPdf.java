package qsolution.utilities;

/**
 * Uses the PDFRenderer library to print a generated PDF file.
 * @author jpenalos
 */
import com.lowagie.text.DocumentException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;
import java.io.*;
import java.net.MalformedURLException;
import java.util.ResourceBundle;
import javax.print.PrintService;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.xhtmlrenderer.pdf.ITextRenderer;
import qsolutions.api.ExamApi;
import qsolutions.api.FilterApi;
import qsolutions.api.PrintPdfApi;

/**
 * Converts the PDF content into printable format
 */
@ServiceProvider(service = PrintPdfApi.class)
public class PrintPdf implements PrintPdfApi
{
    private PrinterJob pjob = null;
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle("qsolution/utilities/Bundle");
    
    /**
     * Empty constructor
     */
    public PrintPdf() 
    {
        
    }
    
    /**
     * Constructs the print job based on the input stream
     *
     * @param inputStream The stream to print to
     * @param jobName The name of the current job
     * @throws IOException If the stream is invalid or an invalid read occurs
     * @throws PrinterException If printer access attempts throw exceptions
     */
    public PrintPdf(InputStream inputStream, String jobName)
        throws IOException, PrinterException
    {
        byte[] pdfContent = new byte[inputStream.available()];
        inputStream.read(pdfContent, 0, inputStream.available());
        initialize(pdfContent, jobName);
    }

    /**
     * Constructs the print job based on the byte array content.
     *
     * @param content A byte array containing the content to print
     * @param jobName The name of the current job
     * @throws IOException If the stream is invalid or an invalid read occurs
     * @throws PrinterException If printer access attempts throw exceptions
     */
    public PrintPdf(byte[] content, String jobName) throws IOException,
            PrinterException
    {
        initialize(content, jobName);
    }

    /**
     * Initializes the job.
     *
     * @param pdfContent A byte array containing the content to print
     * @param jobName The name of the current job
     * @throws IOException If the stream is invalid or an invalid read occurs
     * @throws PrinterException If printer access attempts throw exceptions
     */
    private void initialize(byte[] pdfContent, String jobName) throws
            IOException, PrinterException
    {
        ByteBuffer bb = ByteBuffer.wrap(pdfContent);
        // Create PDF Print Page
        PDFFile pdfFile = new PDFFile(bb);
        PDFPrintPage pages = new PDFPrintPage(pdfFile);

        // Create Print Job
        pjob = PrinterJob.getPrinterJob();
        PageFormat pf = PrinterJob.getPrinterJob().defaultPage();
        pjob.setJobName(jobName);
        Book book = new Book();
        book.append(pages, pf, pdfFile.getNumPages());
        pjob.setPageable(book);
        // to remove margins
        Paper paper = new Paper();
        paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight());
        pf.setPaper(paper);

        /*
         * if ( pjob.printDialog( )) try { pjob.print( ); } catch(
         * PrinterException pe) { System.out.println( "Error printing: " + pe);
         * }
         */
    }

    /**
     * Prints the current job to the current interface.
     * @throws PrinterException
     */
    @Override
    public void print() throws PrinterException
    {
        // Send print job to default printer
        if (pjob.printDialog())
        {
            try
            {
                pjob.print();
            }
            catch (PrinterException pe)
            {
                System.out.println(kBundle.getString("ERRORPRINTING") + ": " 
                        + pe);
            }
        }
    }

    /**
     * Sets the printer service to be used for printing.
     *
     * @param argPrintServiceName The name of the print service
     * @throws PrinterException If printer access attempts throw exceptions 
     */
    public void setPrintService(String argPrintServiceName) throws
            PrinterException
    {
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        int ndx;
        // Loop through all the print services
        for (ndx = 0; ndx < printServices.length; ndx++)
        {
            // If the name of the print service is the same as the argument
            if (printServices[ndx].getName().equalsIgnoreCase(
                    argPrintServiceName))
            {
                pjob.setPrintService(printServices[ndx]);
                break;
            }
        }
        // If the index indicates it's an invalid print service name
        if (ndx == printServices.length)
        {
            throw new PrinterException("Invalid print service name: "
                    + argPrintServiceName);
        }
    }
    
    /**
     * Constructs the print job based on the input stream
     *
     * @param inputStream The stream to print to
     * @param jobName The name of the current job
     * @throws IOException If the stream is invalid or an invalid read occurs
     * @throws PrinterException If printer access attempts throw exceptions
     * @return Returns the API
     */
    @Override
    public PrintPdfApi newItem(InputStream inputStream, String jobName)
        throws IOException, PrinterException
    {
        return new PrintPdf(inputStream, jobName);
    }
    
    /**
     * Saves the exam as a PDF
     */
    public void generatePDF()
    {
        ExamApi exam = Lookup.getDefault().lookup(ExamApi.class);
        final javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        int returnVal, confirm = javax.swing.JOptionPane.YES_OPTION;
        javax.swing.filechooser.FileFilter pdfFilter =
                Lookup.getDefault().lookup(FilterApi.class).makePdfFilter();
        File file;

        fc.setFileFilter(pdfFilter);
        returnVal = fc.showDialog(null, kBundle.getString("EXPORT"));

        // If the user chose to save
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION)
        {
            file = fc.getSelectedFile();
            // If the file doesn't end with .exm
            if (!file.getName().endsWith(".pdf"))
            {
                file = new File(file.getAbsolutePath() + ".pdf");
            }
            //if the file already exists
            if (file.isFile())
            {
                confirm = javax.swing.JOptionPane.showConfirmDialog(null,
                        kBundle.getString("FILE ALREADY EXIST, WOULD YOU LIKE "
                        + "TO OVERWRITE?"),
                        kBundle.getString("CONFIRM SAVE AS"),
                        javax.swing.JOptionPane.YES_NO_OPTION);
            }

            //If the user does not want to overwrite the file
            if (confirm != javax.swing.JOptionPane.NO_OPTION)
            {
                generateExamFromFile(exam, file);
            }
        }
    }
    
    private void generateExamFromFile(ExamApi exam, File file)
    {
        //Create temporary file
        File temp = null;
        try
        {
            temp = File.createTempFile("exam-xhtml-generator", ".tmp");
            temp.deleteOnExit();
            //Write xhtml to temporary file
            FileWriter fstream = null;
            fstream = new FileWriter(temp);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(exam.generateXhtmlExam());
            out.close();
        }
        catch (IOException ex)
        {
            System.out.print(ex);
        }

        //Setup renderer input ( xhtml ) and output ( pdf )
        String examStr = null;
        try
        {
            examStr = temp.toURI().toURL().toString();
        }
        catch (MalformedURLException ex)
        {
            System.out.print(ex);
        }
        OutputStream os = null;
        try
        {
            os = new FileOutputStream(file);
        }
        catch (FileNotFoundException ex)
        {
            System.out.print(ex);
        }

        //Setup Renderer
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(examStr);
        renderer.layout();
        try
        {
            //Render PDF and close
            renderer.createPDF(os);
            os.close();
        }
        catch (Exception ex)
        {
            System.out.print(ex);
        }
    }
}



/**
 * Class that actually converts the PDF file into Printable format.
 */
class PDFPrintPage implements Printable
{
    private PDFFile file;
    private boolean interrupted = false;

    PDFPrintPage(PDFFile file)
    {
        this.file = file;
    }

    @Override
    public int print(Graphics g, PageFormat format, int index) throws
            PrinterException
    {
        int pagenum = index + 1;
        //If we have room for another page and it is not the first page, print it
        if ((pagenum >= 1) && (pagenum <= file.getNumPages()))
        {
            Graphics2D g2 = (Graphics2D) g;
            PDFPage page = file.getPage(pagenum);

            // fit the PDFPage into the printing area
            Rectangle imageArea = new Rectangle((int) format.getImageableX(),
                    (int) format.getImageableY(),
                    (int) format.getImageableWidth(), 
                    (int) format.getImageableHeight());
            g2.translate(0, 0);
            PDFRenderer pgs = new PDFRenderer(page, g2, imageArea, null, null);
            try
            {
                page.waitForFinish();
                pgs.run();
            }
            catch (InterruptedException ie)
            {
                interrupted = true;
            }
            return PAGE_EXISTS;
        }
        else
        {
            return NO_SUCH_PAGE;
        }
    }
}