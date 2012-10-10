package qsolutions.exam;

import org.openide.util.lookup.ServiceProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import qsolutions.api.FormatApi;
import qsolutions.api.NumberingRestartApi;

/**
 * Represents a divider between document items.
 */
@ServiceProvider(service = NumberingRestartApi.class)
public class NumberingRestart extends DesignItem implements NumberingRestartApi
{
    /** The width of the space between two items */
    private static int width;
    /** The HTML tag associated with a numbering restart */
    public static final String kNumberingRestartHTML = "</ol><ol>";

    /**
     * Sets the width, abbreviation, and types of a Divider.
     */
    public NumberingRestart()
    {
        super();
        setAbbreviation(kBundle.getString("NR"));
        setType(kBundle.getString("NUMBERING RESTART"));
    }

    /**
     * Creates a new Divider object and calls its parent's copyData method.
     *
     * @return Returns a new Diagram object that is identical to this
     */
    protected NumberingRestart copy()
    {
        NumberingRestart copy = new NumberingRestart();

        super.copyData(copy);

        return copy;
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
        sb.append(kNumberingRestartHTML);
        return sb.toString();
    }
    
    /**
     * Generates a new instance of a Divider item.
     * @return the new Divider
     */
    @Override
    public NumberingRestartApi newItem()
    {
        return new NumberingRestart();
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
