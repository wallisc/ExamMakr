/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.api;

/**
 * Handles the file saving and loading
 * @author Ryan Dollahon
 */
public interface NewOpenSaveHandlerApi
{
    /**
     * Opens a new Exam
     * @return True for new exam, false otherwise
     */
    public boolean newExam();
    
    /**
     * Opens a previously saved Exam
     * @return true if a previously saved exam was opened, otherwise false
     */
    public boolean open();
    
    /**
     * Saves the file to a already known location or as a new file
     * @return true if successfully saved, otherwise false
     */
    public boolean save();
    
    /**
     * Saves the exam as a new file
     * @return true if the file was successfully saved, otherwise false
     */
    public boolean saveAs();
}
