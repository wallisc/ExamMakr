package qsolutions.exam;

import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import property.Marks;
import qsolutions.api.DriverApi;
import qsolutions.api.FormatApi;
import qsolutions.api.FreeResponseApi;

/**
 * Represents a free response question.
 */
@ServiceProvider(service = FreeResponseApi.class)
public class FreeResponse extends Question implements FreeResponseApi
{
    private int extraLines; //Extra lines to be added in test display

    /**
     * Creates a new FreeResponse question.
     */
    public FreeResponse()
    {
        super();
        extraLines = 0;
        setType(kBundle.getString("FREE RESPONSE"));
        setAbbreviation(kBundle.getString("FR"));
    }

    /**
     * Creates a new FreeResponse object and calls its parent's 
     * copyData method.
     *
     * @return Returns a new FreeResponse object that is identical to "this"
     */
    protected FreeResponse copy()
    {
        FreeResponse copy = new FreeResponse();

        copy.extraLines = this.extraLines;
        super.copyData(copy);

        return copy;
    }

    /**
     * Performs a deep compare with "this" and another Object.
     *
     * @param obj An Object to compare
     * @return A boolean that indicates whether or not Object o is equal to
     * "this"
     */
    @Override
    public boolean equals(Object obj)
    {
        FreeResponse la;

        // Make sure the other object is a FreeResponse
        if (!(obj instanceof FreeResponse))
        {
            return false;
        }
        la = (FreeResponse) obj;
        
        // Ensure the parent's equals method is true
        if (!super.equals(la))
        {
            return false;
        }

        // If the properties of the other FreeResponse don't match, return false
        if (this.extraLines != la.extraLines)
        {
            return false;
        }
        return true;
    }

    /**
     * Gets the extra lines set for the question.
     *
     * @return The number of extra lines
     */
    public int getExtraLines()
    {
        return extraLines;
    }

    /**
     * Set the number of extra lines for the given question.
     *
     * @param lines The number of extra lines
     */
    public void setExtraLines(int lines)
    {
        extraLines = lines;
    }

    /**
     * Gets the HTML representation of the free response question.
     * @param format a Format object used to set properties in the returned
     * string
     * @param isAnswerDoc determines whether or not the answer information is
     * built into the string
     * @return returns a string of HTML containing relevant member data
     */
    @Override
    public String getHTML(FormatApi format, boolean isAnswerDoc)
    {
        //Indicates if the extra lines should be drawn in HTML
        boolean showLines = (Boolean) format.getProperty(
                Format.Property.ShowAnswerLines);
        //Indicates if parenthesis should be showns
        boolean showParen = ((Boolean) format.getProperty(
                    Format.Property.ShowParen)).booleanValue();
        //Indicates where the marks should be located
        Marks.MarksEnum pos =
                (Marks.MarksEnum) format.getProperty(
                    Format.Property.MarksPosition);

        // If the item is active
        if (isActive())
        {
            int lineCount = 0;
            StringBuilder sb = new StringBuilder();
            //Checks where the marks should be located
            String mfix;
            sb.append(format.getMarksHtml(this.getMarks(), 
                    pos, showParen));
            
            sb.append(format.getImageHtml(this.getImage(), this.getScale(), 
                    this.getImageAlignmnet()));
            mfix = Lookup.getDefault().lookup(DriverApi.class).cleanHTML(
                        this.getText());
            // If it contains an inline mark
            if(pos == Marks.MarksEnum.InLine)
            {
                // If it contains an empty top margin
                if(mfix.contains("<p style=\"margin-top: 0\">"))
                {
                    mfix = mfix.replaceFirst("<p style=\"margin-top: 0\">", "");
                }
                else
                {
                    mfix = mfix+"</p>";
                }
            }
            sb.append(mfix);
            
            generateExtraLines(sb, lineCount, isAnswerDoc, showLines);
            
            // If exam is set to be an answer key and the question has an answer
            if (isAnswerDoc && getAnswer().length() > 0)
            {
                sb.append("<br/><b class=\"answer\">" + getAnswer() + "</b>");
            }
            return sb.toString();
        }
        else
        {
            return "";
        }
    }

    private void generateExtraLines(StringBuilder sb, int lineCount, 
            boolean isAnswerDoc, boolean showLines)
    {
        // Generate extra lines
        for (lineCount = -1; !isAnswerDoc && lineCount
                < this.getExtraLines(); lineCount++)
        {
            // If the user wants to show the answer line
            if (showLines)
            {
                sb.append("<p class=\"answer_line\">&nbsp;</p>");
            }
            else
            {
                sb.append("<p>&nbsp;</p>");
            }
        }
    }
    
    /**
     * Generates a new instance of a FreeResponse question.
     * @return the new FreeResponse
     */
    public FreeResponse newItem()
    {
        return new FreeResponse();
    }

    /**
     * Serialize this document item into a Moodle XML element
     * @param doc Root document
     * @return Moodle XML element representing this document item
     */
    @Override
    public Element serializeMoodle(Document doc)
    {
        Element question = doc.createElement("question"),
                subQuestion, subQuestionText, subAnswer, subAnswerText,
                questionText, text, answer;
        CDATASection cdataText, cdataSubAnswerText, cdataSubQuestionText;

        question.setAttribute("type", "essay");

        // Question text
        questionText = doc.createElement("questiontext");
        questionText.setAttribute("format", "html");
        text = doc.createElement("text");
        cdataText = doc.createCDATASection(this.getText());
        text.appendChild(cdataText);
        questionText.appendChild(text);
        question.appendChild(questionText);
        
        // Default grade
        Element defaultgrade = doc.createElement("defaultgrade");
        defaultgrade.setTextContent(((Integer)this.getMarks()).toString());
        question.appendChild(defaultgrade);

        // Penalty
        Element penalty = doc.createElement("penalty");
        penalty.setTextContent("1");
        question.appendChild(penalty);

        // Add the answer
        answer = doc.createElement("graderinfo");
        cdataSubAnswerText = doc.createCDATASection(this.getAnswer());
        text = doc.createElement("text");
        text.appendChild(cdataSubAnswerText);
        answer.appendChild(text);

        question.appendChild(answer);

        return question;
    }
}
