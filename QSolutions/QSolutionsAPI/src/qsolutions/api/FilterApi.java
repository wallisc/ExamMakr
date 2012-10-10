package qsolutions.api;

import javax.swing.filechooser.FileFilter;

/**
 * API for the Filter class
 */
public interface FilterApi
{
    /**
     * Gives a FileFilter which allows .exm files and directories
     *
     * @return FileFilter which allows .exm files and directories
     */
    FileFilter makeExamFilter();

    /**
     * Gives a FileFilter which allows Moodle .xml files and directories
     *
     * @return FileFilter which allows Moodle .xml files and directories
     */
    public FileFilter makeMoodleFilter();
    
    /**
     * Gives a FileFilter which allows .pdf files and directories
     *
     * @return FileFilter which allows .pdf files and directories
     */
    public javax.swing.filechooser.FileFilter makePdfFilter();
}
