package qsolutions.exam;

import org.openide.util.lookup.ServiceProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import qsolutions.api.FormatApi;
import qsolutions.api.SectionTitleApi;

/**
 * Serves as a title of a section indicating when a new section has begun
 * and allowing a user to preface a sequence of questions with specific 
 * instructions.
 * @author Ryan
 */
@ServiceProvider(service = SectionTitleApi.class)
public class SectionTitle extends DesignItem implements SectionTitleApi
{
    /** The beginning of a SectionTitle's HTML string */
    public static final String kStartHTML = "<p class=\"section_title\">";
    /** The end of a SectionTitle's HTML string */
    public static final String kEndHTML = "</p>";
    
    /**
     * Creates a new SectionTitle with default values for type and abbreviation.
     */
    public SectionTitle()
    {
        super();
        setType(kBundle.getString("SECTION TITLE"));
        setAbbreviation(kBundle.getString("ST"));
        
    }

    /**
     * Creates a new SectionTitle object and calls its parent's copyData method
     *
     * @return Returns a new SectionTitle object that is identical to "this"
     */
    protected SectionTitle copy()
    {
        SectionTitle copy = new SectionTitle();

        super.copyData(copy);

        return copy;
    }

    /**
     * Returns the HTML string representation of a SectionTitle.
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
        sb.append(kStartHTML);
        sb.append(this.getText());
        sb.append(kEndHTML);
        return sb.toString();
    }
    
    /**
     * Generates a new instance of a SectionTitle item.
     * @return the new SectionTitle
     */
    @Override
    public SectionTitle newItem()
    {
        return new SectionTitle();
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
