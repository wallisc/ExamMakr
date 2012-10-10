/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolution.utilities;

import java.io.File;
import java.util.ResourceBundle;
import javax.swing.filechooser.FileFilter;
import org.openide.util.lookup.ServiceProvider;
import qsolutions.api.FilterApi;

/**
 * File chooser filter
 */
@ServiceProvider(service = FilterApi.class)
public class Filter implements FilterApi
{
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle("qsolution/utilities/Bundle");
    /** The default exam descriptor */
    public static final String kExamFileDescription = kBundle.getString(
            "EXAM") + " ( *.exm )";
    /** The default PDF descriptor */
    public static final String kMoodleFileDescription = "Moodle XML ( *.xml )";
    /** The default PDF descriptor */
    public static final String kPDFFileDescription = "PDF ( *.pdf )";

    /**
     * Gives a FileFilter which allows .exm files and directories
     *
     * @return FileFilter which allows .exm files and directories
     */
    @Override
    public FileFilter makeExamFilter()
    {
        return new FileFilter()
        {
            // Checks to see if the file extention is a .exm file
            @Override
            public boolean accept(File f)
            {
                boolean ret = false;
                // returns true if the file is a directory
                if (f.isDirectory())
                {
                    ret = true;
                }
                String fileName = f.getName();
                int extIdx = fileName.lastIndexOf('.');
                return ret || extIdx >= 0 && fileName.substring(
                        extIdx + 1).equals("exm");
            }

            @Override
            public String getDescription()
            {
                return kExamFileDescription;
            }
        };
    }

    /**
     * Gives a FileFilter which allows Moodle .xml files and directories
     *
     * @return FileFilter which allows Moodle .xml files and directories
     */
    @Override
    public FileFilter makeMoodleFilter()
    {
        return new FileFilter()
        {
            // Checks to see if the file extention is a .exm file
            @Override
            public boolean accept(File f)
            {
                boolean ret = false;
                // returns true if the file is a directory
                if (f.isDirectory())
                {
                    ret = true;
                }
                String fileName = f.getName();
                int extIdx = fileName.lastIndexOf('.');
                return ret || extIdx >= 0 && fileName.substring(
                        extIdx + 1).equals("xml");
            }

            @Override
            public String getDescription()
            {
                return kMoodleFileDescription;
            }
        };
    }
    
    /**
     * Gives a FileFilter which allows .pdf files and directories
     *
     * @return FileFilter which allows .pdf files and directories
     */
    public javax.swing.filechooser.FileFilter makePdfFilter()
    {
        return new javax.swing.filechooser.FileFilter()
        {
            // Checks to see if the file extention is a .exm file
            @Override
            public boolean accept(File f)
            {
                boolean ret = false;
                // returns true if the file is a directory
                if (f.isDirectory())
                {
                    ret = true;
                }
                String fileName = f.getName();
                int extIdx = fileName.lastIndexOf('.');
                return ret || extIdx >= 0 && fileName.substring(
                        extIdx + 1).equals("pdf");
            }

            @Override
            public String getDescription()
            {
                return kPDFFileDescription;
            }
        };
    }
}
