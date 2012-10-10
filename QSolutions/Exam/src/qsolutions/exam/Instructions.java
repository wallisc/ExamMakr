package qsolutions.exam;

import org.openide.util.lookup.ServiceProvider;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import qsolutions.api.FormatApi;
import qsolutions.api.InstructionsApi;

/**
 * A text block on the exam that usually gives instructions to the student on 
 * how answer the following next questions
 */
@ServiceProvider(service = InstructionsApi.class)
public class Instructions extends DesignItem implements InstructionsApi
{
    /** HTML tags that precede the instructions HTML */
    public static final String kStartHTML = "<p class=\"instructions\">";
    /** HTML tags that follow after the instructions HTML */
    public static final String kEndHTML = "</p>";
    
    /**
     * Construct an instruction item.
     */
    public Instructions()
    {
        super();
        setType(kBundle.getString("INSTRUCTIONS"));
        setAbbreviation(kBundle.getString("INS"));
    }

    /**
     * Creates a new Instructions object and calls its parent's copyData method.
     *
     * @return Returns a new Instructions object that is identical to "this"
     */
    protected Instructions copy()
    {
        Instructions copy = new Instructions();

        super.copyData(copy);

        return copy;
    }

    /**
     * Returns the HTML for an Instructions item.
     * @param format a Format object used to set properties in the returned
     * string
     * @param isAnswerDoc determines whether or not the answer information is
     * built into the string
     * @return returns a string of HTML containing relevant member data
     */
    @Override
    public String getHTML(FormatApi format, boolean isAnswerDoc)
    {
        
        return format.getImageHtml(this.getImage(), this.getScale(), 
                    this.getImageAlignmnet())
            + kStartHTML + this.getText() + kEndHTML;
        //return this.getText();
    }
    
    /**
     * Generates a new instance of a Instructions item.
     * @return the new Instructions
     */
    @Override
    public Instructions newItem()
    {
        return new Instructions();
    }
    
    /**
     * Serialize this document item into a Moodle XML element
     * @param doc Root document
     * @return Moodle XML element representing this document item
     */
    @Override
    public Element serializeMoodle(Document doc)
    {
        Element text, question = doc.createElement("question"), questionText;
        CDATASection cdataText;

        question.setAttribute("type", "description");

        // Question text
        questionText = doc.createElement("questiontext");
        questionText.setAttribute("format", "html");
        text = doc.createElement("text");
        cdataText = doc.createCDATASection(this.getText());
        text.appendChild(cdataText);
        questionText.appendChild(text);
        question.appendChild(questionText);
        
        return question;
    }
}
