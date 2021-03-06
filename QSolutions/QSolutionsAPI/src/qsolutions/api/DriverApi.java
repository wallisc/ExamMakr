package qsolutions.api;

import java.io.File;

/**
 * The Driver interface for use with LookUp. Only contains methods needed
 * externally.
 * @author Ryan Dollahon
 */
public interface DriverApi
{
    /**
     * Cleans a string generated by the getText() method from the text panels
     * using the HTMLEditorKit class.
     *
     * @param html The string from a text panel's getText() method.
     * @return A clean string containing only the text and its text format tags
     */
    public String cleanHTML(String html);
    /**
     * More strict clean html
     *
     * @param html The string from a text panel's getText() method.
     * @return A clean string containing only the text and its text format tags
     */
    public String cleanHTML2(String html);
    /**
     * Replaces all contiguous spaces in a String with an HTML friendly tag to
     * indicate that these spaces should be shown.
     *
     * @param text The HTML String to modify.
     * @return The string containing &nbsp tags to indicate extra spaces.
     */
    public String fixSpaces(String text);
    
    /**
     * Loads a test from a given file if the file exists. @pre File must exist
     * or else a NullPointException results
     *
     * @param file The File to load from
     * @return The test loaded from the file
     */
    public ExamApi loadTest(File file);
    
    /**
     * Removes indexes from an array that represent DesignItem indexes.
     * @param indexes An array of item indexes
     * @param exam The Exam containing items associated with these indexes
     * @return A new array containing now DesignItem indexes
     */
    public int[] removeDesignItemIndexes(int[] indexes, ExamApi exam);
    
     /**
     * Saves the test into a given file using serialization.
     *
     * @param testToSave The test to save into a file
     * @param file The File to save the Exam into
     */
    public void saveTest(ExamApi testToSave, File file);
    
    /**
     * Compares an item extracted from GUI with a stored exam item.
     * @param extracted The extracted fields placed a new DocumentItem
     * @param item The item to compare the text to
     * @return True if the fields are equal with the data in the item
     */
    public boolean compareExtracted(DocumentItemApi extracted, 
            DocumentItemApi item);

    /**
     * 
     * Compares a String extracted from GUI with a stored exam item's String.
     * @param extracted The extracted String from a new DocumentItem
     * @param item The item's String to compare the text to
     * @return True if the strings are virtually equal
     */
    public boolean compareExtractedStrings(String extracted, String item);
}
