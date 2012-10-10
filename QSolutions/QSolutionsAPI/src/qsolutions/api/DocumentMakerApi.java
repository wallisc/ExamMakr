package qsolutions.api;

import javax.swing.JEditorPane;

/**
 * The DocumentMaker interface for use with LookUp. Only contains methods needed
 * externally.
 * @author Ryan Dollahon
 */
public interface DocumentMakerApi
{
    /**
     * Returns the number of pages for textPane
     * @param editorPane the text pane to count pages for
     * @return Number of pages
     */
    int pageCount(JEditorPane editorPane);
}
