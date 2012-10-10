/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolution.utilities;

import java.io.File;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.windows.WindowManager;
import qsolutions.api.*;

/**
 * Handles save and saveAs for the file system
 * @author Ryan Dollahon
 */
@ServiceProvider(service = NewOpenSaveHandlerApi.class)
public class NewOpenSaveHandler implements NewOpenSaveHandlerApi
{
    private final static String kTitle = " - Questionable ExamMakr";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle("qsolution/utilities/Bundle");
    
    /**
     * Opens a new Exam
     * @return if a new exam is selected
     */
    @Override
    public boolean newExam()
    {
        int confirm = javax.swing.JOptionPane.YES_OPTION;
        boolean ret = false;
        // If the exam has been edited
        if (Lookup.getDefault().lookup(ExamHandlerApi.class).getEdited())
        {
            //TODO change to a normal save, discard, cancel
            confirm = javax.swing.JOptionPane.showConfirmDialog(null,
                    kBundle.getString(" THE CURRENT EXAM HAS BEEN EDITED BUT "
                    + "NOT SAVED, WOULD YOU TO LIKE TO START A NEW EXAM "
                    + "ANYWAYS?"),
                    kBundle.getString("CONFIRM CLOSE"),
                    javax.swing.JOptionPane.YES_NO_OPTION);             
        }
        
        // If the user does want to make a new exam
        if ( confirm == javax.swing.JOptionPane.YES_OPTION )
        {
            //Cleanup the last exam
            Lookup.getDefault().lookup(ExamApi.class).cleanUp();
            
            Lookup.getDefault().lookup(ExamApi.class).newExam();
            Lookup.getDefault().lookup(ExamHandlerApi.class).setEdited( false );
            WindowManager.getDefault().getMainWindow().setTitle(
                    kBundle.getString("NEWEXAM") + kTitle);
            ret = true;
            
            WindowManager.getDefault().findTopComponent(
                    "PropertiesTopComponent").updateUI();
        }
        return ret;
    }
    
    /**
     * Opens a previously saved Exam
     * @return true if a previously saved exam was opened, otherwise false
     */
    @Override
    public boolean open()
    {
        boolean ret = false;
        int confirm = javax.swing.JOptionPane.YES_OPTION;
        // If the exam has been edited
        if (Lookup.getDefault().lookup(ExamHandlerApi.class).getEdited())
        {
            //TODO change to a normal save, discard, cancel
            confirm = javax.swing.JOptionPane.showConfirmDialog(null,
                    kBundle.getString(" THE CURRENT EXAM HAS BEEN EDITED BUT "
                    + "NOT SAVED, WOULD YOU TO LIKE TO OPEN A NEW EXAM "
                    + "ANYWAYS?"),
                    kBundle.getString("CONFIRM CLOSE"),
                    javax.swing.JOptionPane.YES_NO_OPTION);             
        }
        
        // If the user does want to make a new exam
        if ( confirm == javax.swing.JOptionPane.YES_OPTION )
        {
            //DocumentViewTopComponent main =
            //        Lookup.getDefault().lookup(DocumentViewTopComponent.class);
            final javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
            int returnVal;
            javax.swing.filechooser.FileFilter examFilter = 
                    Lookup.getDefault().lookup(FilterApi.class).makeExamFilter();
            File file;

            fc.setFileFilter(examFilter);
            returnVal = fc.showOpenDialog(null);
            // If the user chose a file
            if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION)
            {
                file = fc.getSelectedFile();

                //if the file already exists
                if (!file.isFile())
                {
                    javax.swing.JOptionPane.showMessageDialog(null,
                            kBundle.getString("FILE DOES NOT EXIST"));
                }
                else
                {
                    //Cleanup the last exam
                    Lookup.getDefault().lookup(ExamApi.class).cleanUp();

                    Lookup.getDefault().lookup(ExamApi.class)
                            .newExam(Lookup.getDefault().lookup(DriverApi.class)
                            .loadTest(file));
                    Lookup.getDefault().lookup(ExamApi.class)
                            .instantiateImages();
                    Lookup.getDefault().lookup(ExamHandlerApi.class).setEdited( 
                            false );
                    WindowManager.getDefault().getMainWindow()
                            .setTitle(file.getName().toString()
                            .replaceAll(".exm", "") + kTitle);
                    ret = true;
                    
                    WindowManager.getDefault().findTopComponent(
                            "PropertiesTopComponent").updateUI();
                }
            }
        }
        return ret;
    }
    
    /**
     * Saves the file to a already known location or as a new file
     * @return true if successfully saved, otherwise false
     */
    @Override
    public boolean save()
    {
        boolean ret = false;
        File file;
        ExamApi exam = Lookup.getDefault().lookup(ExamApi.class);
        String path = exam.getPath();
        
        //If selected exam has not been saved ever before
        if (path == null)
        {
            ret = saveAs();
        }
        else
        {
            file = new File(path);
            //if the file already exists
            if (file.isFile())
            {
                Lookup.getDefault().lookup(DriverApi.class).saveTest(exam, file);
                Lookup.getDefault().lookup(ExamHandlerApi.class).setEdited( false );
                WindowManager.getDefault().getMainWindow().setTitle(
                        file.getName().toString().replaceAll(".exm", "") +
                        kTitle);
                ret = true;
            }
            else
            {
                javax.swing.JOptionPane.showMessageDialog(null,
                        kBundle.getString("FILEPATH") + ":\n" + path
                        + "\n" + kBundle.getString("NOT FOUND. FILE HAS EITHER "
                        + "BEEN MOVED OR DELETED. USE SAVE AS TO CREATE A NEW "
                        + "FILE"));
            }        
        }
        
        return ret;
    }
    
    /**
     * Saves the exam as a new file
     * @return true if the file was successfully saved, otherwise false
     */
    @Override
    public boolean saveAs()
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
        fc.setFileFilter(Lookup.getDefault().lookup(FilterApi.class).makeExamFilter());

        // Show file chooser dialog
        returnVal = fc.showDialog(null, kBundle.getString("SAVE"));

        // If the user chose to save
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            saveFile = fc.getSelectedFile();

            // If the file doesn't end with .exm, append it to the filename
            if (!saveFile.getName().endsWith(".exm"))
            {
                saveFile = new File(saveFile.getAbsolutePath() + ".exm");
            }

            // If the file already exists, prompt a confirmation dialog
            if (saveFile.isFile())
            {
                msg = kBundle.getString("FILE ALREADY EXIST, WOULD YOU LIKE TO "
                        + "OVERWRITE?");
                confirm = JOptionPane.showConfirmDialog(null, msg,
                        kBundle.getString("CONFIRM OVERWRITE"), 
                        JOptionPane.YES_NO_OPTION);
            }

            //If the user wants to overwrite the exam, save the exam
            if (confirm == JOptionPane.YES_OPTION)
            {
                Lookup.getDefault().lookup(DriverApi.class).saveTest(exam,
                    saveFile);
                Lookup.getDefault().lookup(ExamHandlerApi.class).setEdited( 
                    false );
                
                exam.setPath(saveFile.getAbsolutePath());
                WindowManager.getDefault().getMainWindow().setTitle(
                        saveFile.getName().toString().replaceAll(".exm", "") + 
                        kTitle);
            }
        }
        
        return returnVal == JFileChooser.APPROVE_OPTION &&
                confirm == JOptionPane.YES_OPTION;
    }
    
}
