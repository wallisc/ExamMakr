/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.action;

import com.lowagie.text.DocumentException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import javax.swing.JTabbedPane;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.xhtmlrenderer.pdf.ITextRenderer;
import qsolutions.api.ExamApi;
import qsolutions.api.FilterApi;
import qsolutions.api.PrintPdfApi;

/**
 * ActionListener for printing Exams
 */
@ActionID(category = "MyFile",
id = "qsolutions.action.PrintAction")
@ActionRegistration(iconBase = "img/print.png",
displayName = "qsolutions.action.Bundle#PRINT")
@ActionReferences(
{
    @ActionReference(path = "Menu/MyFile", position = PrintAction.kMenuPos),
    @ActionReference(path = "Toolbars/File", position = PrintAction.kToolbarPos),
    @ActionReference(path = "Shortcuts", name = "D-P")
})
@Messages("CTL_PrintAction=qsolutions.action.Bundle#PRINT")
public final class PrintAction implements ActionListener
{
    /** Position of the action in the menu */
    public static final int kMenuPos = 2000;
    /** Position of the action in the tool bar */
    public static final int kToolbarPos = 350;
    
    /**
     * Action handler for the print action
     * @param e unused
     */
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            ExamApi exam = Lookup.getDefault().lookup(ExamApi.class);
            File xmltemp = File.createTempFile("exam-xhtml-generator", ".tmp");
            File pdftemp = File.createTempFile("temppdf", ".pdf");

            xmltemp.deleteOnExit();
            pdftemp.deleteOnExit();
            
            //Write xhtml to temporary file
            FileWriter fstream = new FileWriter(xmltemp);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(exam.generateXhtmlExam());
            out.close();

            //Setup renderer input ( xhtml ) and output ( pdf )
            String xmlstring = xmltemp.toURI().toURL().toString();
            OutputStream os = new FileOutputStream(pdftemp);

            //Setup Renderer
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(xmlstring);
            renderer.layout();

            //Render PDF and close
            renderer.createPDF(os);
            os.close();

            File file = new File(pdftemp.getAbsolutePath());
            FileInputStream fis = new FileInputStream(file);
            PrintPdfApi printPDFFile =
                    Lookup.getDefault().lookup(PrintPdfApi.class).newItem(fis,
                    exam.getTitleGroup().getTitle());
            printPDFFile.print();
        }
        catch (Exception excep)
        {
            return;
        }
    }
}
