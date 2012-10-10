package qsolutions.exam;

import org.openide.util.lookup.ServiceProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import qsolutions.api.FormatApi;
import qsolutions.api.PageBreakApi;

/**
 * Serves as an indicator of the request for a page break after other items.
 * @author Ryan
 */
@ServiceProvider(service = PageBreakApi.class)
public class PageBreak extends DesignItem implements PageBreakApi
{
    /** The HTML string that causes a page-break */
    public static final String kPageBreakHTML =
        "<div class=\"page_break\"></div>";
            
    /**
     * Creates a new PageBreak with default type and abbreviation.
     */
    public PageBreak()
    {
        super();
        setAbbreviation(kBundle.getString("PB"));
        setType(kBundle.getString("PAGE BREAK"));
    }

    /**
     * Creates a new PageBreak object and calls its parent's copyData method
     *
     * @return Returns a new PageBreak object that is identical to "this"
     */
    protected PageBreak copy()
    {
        PageBreak copy = new PageBreak();

        super.copyData(copy);

        return copy;
    }

    /**
     * Returns the HTML version of the PageBreak item.
     * @param format a Format object used to set properties in the returned
     * string
     * @param isAnswerDoc determines whether or not the answer information is
     * built into the string
     * @return returns a string of HTML containing relevant member data
     */
    @Override
    public String getHTML(FormatApi format, boolean isAnswerDoc)
    {
        return kPageBreakHTML;
    }
    
    /**
     * Generates a new instance of a PageBreak item.
     * @return the new PageBreak
     */
    @Override
    public PageBreak newItem()
    {
        return new PageBreak();
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
