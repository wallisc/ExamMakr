package qsolutions.api;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The DocumentItem interface for use with LookUp. Only contains methods needed
 * externally.
 * @author Ryan Dollahon
 */
public interface DocumentItemApi extends Serializable
{
    /**
     * Clones the DocumentItem.
     * @return a clone of the DocumentItem
     */
    DocumentItemApi clone();
    /**
     * Gets the abbreviation of the DocumentItem.
     * @return Abbreviation of the DocumentItem
     */
    String getAbbreviation();
    /**
     * Gets the question category from the current question.
     *
     * @return empty string for design items
     */
    String getCategory();
    /**
     * Gets the HTML representation of a document item.
     * @param format a Format object used to set properties in the returned
     * string
     * @param isAnswerDoc determines whether or not the answer information is
     * built into the string
     * @return returns a string of HTML containing relevant member data
     */
    String getHTML(FormatApi format, boolean isAnswerDoc);
    /**
     * Gets the question level from the current question.
     *
     * @return -1 for the design items
     */
    String getLevel();
    /**
     * Gets the marks from the current question.
     *
     * @return -1 for design items.
     */
    int getMarks();
    /**
     * Returns the question string of the current question.
     *
     * @return the document string
     */
    String getText();
    /**
     * Gets the the question type from the current question.
     *
     * @return the type of the current question
     */
    String getType();
    /**
     * Get question number in exam
     * @return question number
     */
    int getQNum();
    /**
     * Check for the status of the question, whether it is on the test or not.
     * @return true if the question is active, false otherwise
     */
    boolean isActive();
    /**
     * Determines whether the DocumentItem should be kept together.
     * @return whether the DocumentItem should be kept together
     */
    boolean isKeepTogether();
    /**
     * Generates a new instance of a DocumentItem item.
     * @return the new DocumentItem
     */
    DocumentItemApi newItem();
    /**
     * Sets the abbreviation of the DocumentItem.
     * @param nAbbreviation Abbreviation of the DocumentItem
     */
    void setAbbreviation(String nAbbreviation);
    /**
     * To change the status of the current question.
     *
     * @param nActive is true, the question will be on the test, if active is
     * false, the question won't be on the test.
     */
    void setActive(boolean nActive);
    /**
     * Sets whether the DocumentItem should be kept together.
     * @param nKeepTogether whether the DocumentItem should be kept together
     */
    void setKeepTogether(boolean nKeepTogether);
    /**
     * Edit the question using the parameter string.
     *
     * @param nText is the input string for the document item
     */
    void setText(String nText);
    /**
     * Sets the question type for the current question.
     *
     * @param nType the question type of the current question
     */
    void setType(String nType);
    /**
     * Sets the image file of this item to the specified file.
     * @param file The file to load
     */
    public void setImageFile(File file);
    /**
     * Returns the File link to the temporary file of this item.
     * @return Returns the image file for use in isDifferent()
     */
    public File getImageFile();
    /**
     * Returns the temporary file storing the current Item image.
     * @param file The file to set the image to.
     * @throws IOException If an error occurs reading the file or writing the 
     * temp file
     */
    public void setImage(File file) throws IOException;
    
    /**
     * Returns the link to the temporary file in File form.
     * @return The File containing the image
     */
    public File getImage();
    
    /**
     * Deletes the temporary file for the given DocumentItem.
     */
    public void deleteTemp();

    /**
     * Creates a temporary file for any image that isn't null.
     */
    public void createTempFile();

    /**
     * Serialize this document item into a Moodle XML element
     * @param doc Root document
     * @return Moodle XML element representing this document item
     */
    public Element serializeMoodle(Document doc);
    
        /**
     * Sets the scale of the image associated with this item.
     * @param scale the scale to set the image to
     */
    public void setScale(Float scale);
    
        /**
     * Accesses the scale of the image associated with this item.
     * @return a float value representing the scale where 1.0 is original size, 
     * 2.0 is double size, and so on
     */
    public Float getScale();
    
    /**
     * Sets the alignment of the document items image (if it has one)
     * @param nAlignment the alignment to apply to the document item
     */
    public void setImageAlignment(int nAlignment);
    
    /**
     * Returns the alignment of the image
     * @return th alignment for the image
     */
    public int getImageAlignmnet();
    
     /**
     * Sets the document item with shallow copies of the image
     * @param docItem the doc item to get the image data from
     */
    public void setShallowImage(DocumentItemApi docItem);
    
}
