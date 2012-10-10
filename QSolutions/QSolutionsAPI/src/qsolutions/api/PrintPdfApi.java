package qsolutions.api;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.io.InputStream;

/**
 * The Question interface for use with LookUp. Only contains methods needed
 * externally.
 */
public interface PrintPdfApi
{
    /**
     * Saves the exam as a pdf
     */
    public void generatePDF();
    
    /**
     * Prints the current job to the current interface.w
     * @throws PrinterException 
     */
    public void print() throws PrinterException;
    
    /**
     * Constructs the print job based on the input stream
     * 
     * @param inputStream pdf input stream
     * @param jobName Name of the job
     * @return new printerPdfApi
     * @throws IOException
     * @throws PrinterException 
     */
    public PrintPdfApi newItem(InputStream inputStream, String jobName)
        throws IOException, PrinterException;
    
    
}
