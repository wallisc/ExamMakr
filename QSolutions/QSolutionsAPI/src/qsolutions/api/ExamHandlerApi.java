/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.api;

/**
 * Determines if the exam has been edited and saves these values.
 */
public interface ExamHandlerApi
{
    /**
     * Set the exam to be edited/unedited
     * @param edited whether the exam is edited
     */
    public void setEdited( boolean edited );
    /**
     * Return whether the exam has been edited
     * @return whether the exam has been edited
     */
    public boolean getEdited();
}