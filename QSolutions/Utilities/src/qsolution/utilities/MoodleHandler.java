package qsolution.utilities;

import java.io.File;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.windows.WindowManager;
import org.w3c.dom.Document;
import qsolutions.api.*;

/**
 * Handles the exporting of Moodle xml
 */
@ServiceProvider(service = MoodleHandlerApi.class)
public class MoodleHandler implements MoodleHandlerApi
{
    private final static String kTitle = " - Questionable ExamMakr";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle("qsolution/utilities/Bundle");

    /**
     * Exports the exam 
     */
    @Override
    public void export()
    {
        final JFileChooser fc = new JFileChooser();
        int returnVal, confirm = JOptionPane.YES_OPTION;
        File saveFile;
        String fileTitle, msg;

        ExamApi exam = Lookup.getDefault().lookup(ExamApi.class);
        fileTitle = WindowManager.getDefault().getMainWindow()
                .getTitle().replaceAll(kTitle, "");
        
        // If the exam title in the name is marked (to show it's been 
        // edited
        if (fileTitle.startsWith("*")) 
        {
            fileTitle = fileTitle.substring(1);
        }
        
        // Set file chooser defaults
        fc.setSelectedFile(new File(fileTitle));
        fc.setFileFilter(Lookup.getDefault().lookup(FilterApi.class).makeMoodleFilter());

        // Show file chooser dialog
        returnVal = fc.showDialog(null, kBundle.getString("EXPORT"));

        // If the user chose to save
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            saveFile = fc.getSelectedFile();

            // If the file doesn't end with .xml, append it to the filename
            if (!saveFile.getName().endsWith(".xml"))
            {
                saveFile = new File(saveFile.getAbsolutePath() + ".xml");
            }

            // If the file already exists, prompt a confirmation dialog
            if (saveFile.isFile())
            {
                msg = kBundle.getString(
                        "FILE ALREADY EXIST, WOULD YOU LIKE TO OVERWRITE?");
                confirm = JOptionPane.showConfirmDialog(null, msg,
                        kBundle.getString("CONFIRM OVERWRITE"), 
                        JOptionPane.YES_NO_OPTION);
            }

            //If the user wants to overwrite the exam, save the exam
            if (confirm == JOptionPane.YES_OPTION)
            {
                Document doc = createMoodleDoc();
                Lookup.getDefault().lookup(ExamApi.class).generateMoodle(doc);
                saveDocument(doc, saveFile);
            }
        }
    }
  
    /**
     * Creates a new DOM document
     * @return a new DOM document
     */
    private Document createMoodleDoc()
    {
        DocumentBuilderFactory docFactory; // DOM factory
        DocumentBuilder builder = null; // DOM builder

        docFactory = DocumentBuilderFactory.newInstance();
        try
        {
            builder = docFactory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e)
        {
            throw new RuntimeException("Parser config error");
        }
        return builder.newDocument();
    }

    /**
     * Saves a DOM document
     * @param doc document to be saved
     * @param filePath where to save document
     * @return a new DOM document
     */
    private void saveDocument(Document doc, File file)
    {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer;
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(file);

        try
        {
            transformer = factory.newTransformer();
        }
        catch (TransformerConfigurationException e)
        {
            throw new RuntimeException("transformer configuration error");
        }
        try
        {
            transformer.transform(source, result);
        }
        catch (TransformerException e)
        {
            throw new RuntimeException("transformer error");
        }
    }
}
