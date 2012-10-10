package qsolutions.exam;

import org.openide.util.lookup.ServiceProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import qsolutions.api.DividerApi;
import qsolutions.api.FormatApi;

/**
 * Represents a divider between document items.
 */
@ServiceProvider(service = DividerApi.class)
public class Divider extends DesignItem implements DividerApi
{
    /** The width of the space between two items */
    private static int width;
    /** The default width */
    public static final int kDefaultWidth = 100;
    /** The HTML tag associated with a divider */
    public static final String kDividerHTML = "<hr />";

    /**
     * Sets the width, abbreviation, and types of a Divider.
     */
    public Divider()
    {
        super();
        width = kDefaultWidth;
        setAbbreviation(kBundle.getString("DV"));
        setType(kBundle.getString("DIVIDER"));
    }

    /**
     * Creates a new Divider object and calls its parent's copyData method.
     *
     * @return Returns a new Diagram object that is identical to this
     */
    protected Divider copy()
    {
        Divider copy = new Divider();

        super.copyData(copy);

        return copy;
    }

    /**
     * An accessor method to retrieve and return the width.
     *
     * @return an int containing the width value
     */
    public static int getWidth()
    {
        return width;
    }

    /**
     * A mutator method to set the width.
     *
     * @param w an int value that the width will be set to
     */
    public static void setWidth(int w)
    {
        width = w;
    }

    /**
     * Gets the HTML representation of the divider
     * @param format a Format object used to set properties in the returned
     * string
     * @param isAnswerDoc determines whether or not the answer information is
     * built into the string
     * @return returns a string of HTML containing relevant member data
     */
    @Override
    public String getHTML(FormatApi format, boolean isAnswerDoc)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(kDividerHTML);
        return sb.toString();
    }
    
    /**
     * Generates a new instance of a Divider item.
     * @return the new Divider
     */
    @Override
    public Divider newItem()
    {
        return new Divider();
    }

    /**
     * Serialize this document item into a Moodle XML element
     * @param doc Root document
     * @return Moodle XML element representing this document item
     */
    @Override
    public Element serializeMoodle(Document doc)
    {
        return null;
    }
}
