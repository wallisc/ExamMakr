package qsolutions.exam;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import qsolutions.api.DocumentItemApi;
import qsolutions.api.FormatApi;

/**
 * Represents a document item.
 */
public abstract class DocumentItem implements Serializable, DocumentItemApi
{
    private boolean active;
    private boolean keepTogether;
    private String text;
    private String type;
    private String abbreviation;
    private ItemImage image;
    private File imageFile;
    private Float scale;
    private int alignment;
    /** Image left position constant */
    public static final int kImageLeft = 0;
    /** Image center position constant */
    public static final int kImageCenter = 1;
    /** Image right position constant */
    public static final int kImageRight = 2;
    
    private static final String kBundleName = "qsolutions/exam/Bundle";
    protected static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);

    
    abstract DocumentItem copy();

    /**
     * Serialize this document item into a Moodle XML element
     * @param doc Root document
     * @return Moodle XML element representing this document item
     */
    public abstract Element serializeMoodle(Document doc);

    /**
     * Creates a new DocumentItem with the default values.
     */
    public DocumentItem()
    {
        active = true;
        text = "";
        type = "";
        keepTogether = true;
        scale = 1.0f;
    }
    
    /**
     * Clones the DocumentItem.
     * @return a clone of the DocumentItem
     */
    public DocumentItem clone()
    {
        return copy();
    }

    /**
     * Sets the abbreviation of the DocumentItem.
     * @param abbreviation Abbreviation of the DocumentItem
     */
    public void setAbbreviation(String abbreviation)
    {
        this.abbreviation = abbreviation;
    }

    /**
     * Gets the abbreviation of the DocumentItem.
     * @return Abbreviation of the DocumentItem
     */
    public String getAbbreviation()
    {
        return abbreviation;
    }

    /**
     * Sets whether the DocumentItem should be kept together.
     * @param nKeepTogether whether the DocumentItem should be kept together
     */
    public void setKeepTogether(boolean nKeepTogether)
    {
        keepTogether = nKeepTogether;
    }

    /**
     * Determines whether the DocumentItem should be kept together.
     * @return whether the DocumentItem should be kept together
     */
    public boolean isKeepTogether()
    {
        return keepTogether;
    }

    /**
     * Copies over the attributes to a given DocumentItem.
     * @param copy DocumentItem to be copied to
     */
    protected void copyData(DocumentItem copy)
    {
        copy.active = this.active;
        copy.text = this.text;
        copy.type = this.type;
        copy.abbreviation = this.abbreviation;
    }

    /**
     * constructs an instance of the document item the status of the document
     * item is active by default the text field is initialized to be an empty
     * string.
     * @param obj Other DocumentItem to be compared
     * @return Whether the two DocumentItems are equal
     */
    @Override
    public boolean equals(Object obj)
    {
        DocumentItem temp;
        
        // Ensure that o is a DocumentItem
        if (!(obj instanceof DocumentItem))
        {
            return false;
        }
        temp = (DocumentItem) obj;
        
        String itemText = text.replaceAll("(\\s)", "");
        String tempText = temp.getText().replaceAll("(\\s)", "");

        // If the strings (with all white space removed) are not equal
        if (!itemText.equals(tempText))
        {
            return false;
        }
        // If any of the properties are not equal, return false
        if (active != temp.isActive() || 
                !type.equals(temp.getType()) ||
                keepTogether != temp.isKeepTogether())
        {
            return false;
        }
        
        // If the images are inequal
        if (!equalImage(temp))
        {
            return false;
        }
        
        return true;
    }
    
    private boolean equalImage(DocumentItem temp)
    {
        // If the image and temp.image are not equivalent
        if (!equivalent(image, temp.image))
        {
            return false;
        }
        
        // If the imageFile and temp.imageFile are not equivalent
        if (!equivalent(imageFile, temp.imageFile))
        {
            return false;
        }
        
        //If the scales or alignments don't match
        if (!scale.equals(temp.scale) ||
                alignment != temp.alignment)
        {
            return false;
        }
        
        return true;
    }
    
    private boolean equivalent(Object lhs, Object rhs)
    {
        // If the lhs or rhs is null
        if (lhs == null || rhs == null)
        {
            // If the lhs and rhs don't match
            if (lhs != rhs)
            {
                return false;
            }
        }
        // If the lhs doesn't equal rhs
        else if (!lhs.equals(rhs))
        {
            return false;
        }
        
        return true;
    }

    /**
     * Check for the status of the question, whether it is on the test or not.
     *
     * @return true if the question is active, false otherwise
     */
    @Override
    public boolean isActive()
    {
        return active;
    }

    /**
     * To change the status of the current question.
     *
     * @param active is true, the question will be on the test, if active is
     * false, the question won't be on the test.
     */
    @Override
    public void setActive(boolean active)
    {
        this.active = active;
    }

    /**
     * Returns the question string of the current question.
     *
     * @return the document string
     */
    @Override
    public String getText()
    {
        return text;
    }

    /**
     * Edit the question using the parameter string.
     *
     * @param text is the input string for the document item
     */
    @Override
    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * Gets the marks from the current question.
     *
     * @return -1 for design items.
     */
    @Override
    public int getMarks()
    {
        return -1;
    }

    /**
     * Gets the question number of the current question.
     *
     * @return -1 for design items.
     */
    public int getQNum()
    {
        return -1;
    }

    /**
     * Gets the question category from the current question.
     *
     * @return empty string for design items
     */
    @Override
    public String getCategory()
    {
        return "";
    }

    /**
     * Gets the question level from the current question.
     *
     * @return -1 for the design items
     */
    @Override
    public String getLevel()
    {
        return "";
    }

    /**
     * Sets the question type for the current question.
     *
     * @param type the question type of the current question
     */
    @Override
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * Gets the the question type from the current question.
     *
     * @return the type of the current question
     */
    @Override
    public String getType()
    {
        return type;
    }

    /**
     * Gets the HTML representation of a document item.
     * @param format a Format object used to set properties in the returned
     * string
     * @param isAnswerDoc determines whether or not the answer information is
     * built into the string
     * @return returns a string of HTML containing relevant member data
     */
    @Override
    public abstract String getHTML(FormatApi format, boolean isAnswerDoc);
    
    /**
     * Sets the image file of this item to the specified file.
     * @param file The file to load
     */
    public void setImageFile(File file)
    {
        imageFile = file;
    }
    
    /**
     * Returns the File link to the temporary file of this item.
     * @return Returns the image file for use in isDifferent()
     */
    public File getImageFile()
    {
        return imageFile;
    }
    
    /**
     * Returns the temporary file storing the current Item image.
     * @param file The file to set the image to.
     * @throws IOException If an error occurs reading the file or writing the 
     * temp file
     */
    public void setImage(File file) throws IOException
    {
        image = new ItemImage(file);
        image.writeToTempFile();
    }
    
    /**
     * Sets the document item with shallow copies of the image
     * @param inDocItem the doc item to get the image data from
     */
    public void setShallowImage(DocumentItemApi inDocItem)
    {
        DocumentItem docItem = (DocumentItem)inDocItem;
        image = docItem.image;
        imageFile = docItem.imageFile;
    }
    
    /**
     * Returns the link to the temporary file in File form.
     * @return The File containing the image or null if no image has been loaded
     */
    public File getImage()
    {
        // If the item isn't null
        if(image != null)
        {
            return image.getImageFile();
        }
        return null;
    }
    
    /**
     * Deletes the temporary file for the given DocumentItem.
     */
    public void deleteTemp()
    {
        // If the item has an image
        if (image != null)
        {
            image.deleteTempFile();
        }
    }
    
    /**
     * Creates a temporary file for any image that isn't null.
     */
    public void createTempFile()
    {
        try
        {
            // If the item isn't null
            if(image != null)
            {
                image.writeToTempFile();
                imageFile = image.getImageFile();
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    /**
     * Accesses the scale of the image associated with this item.
     * @return a float value representing the scale where 1.0 is original size, 
     * 2.0 is double size, and so on
     */
    public Float getScale()
    {
        return scale;
    }
    
    /**
     * Sets the scale of the image associated with this item.
     * @param scale the scale to set the image to
     */
    public void setScale(Float scale)
    {
        this.scale = scale;
    }
    
    /**
     * Sets the alignment of the document items image (if it has one)
     * @param nAlignment the alignment to apply to the document item
     */
    public void setImageAlignment(int nAlignment)
    {
        alignment = nAlignment;
    }
    
    /**
     * Returns the alignment of the image
     * @return th alignment for the image
     */
    public int getImageAlignmnet()
    {
        return alignment;
    }

}