package qsolutions.exam;

import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import property.Marks;
import qsolutions.api.DriverApi;
import qsolutions.api.FormatApi;
import qsolutions.api.TrueFalseApi;

/**
 * Represents a true/false question
 */
@ServiceProvider(service = TrueFalseApi.class)
public class TrueFalse extends Question implements TrueFalseApi
{
    private String choice1;
    private String choice2;
    private static final String kMoodleTrue = "true";
    private static final String kMoodleFalse = "false";
    private static final int kFracMax = 100;
    private static final int kFracMin = 0;

    /**
     * Creates a new true/false question with default values
     */
    public TrueFalse()
    {
        super();
        setAbbreviation(kBundle.getString("TF"));
        setType(kBundle.getString("TRUE FALSE"));
        setAnswer(kBundle.getString("TRUE"));
        choice1 = kBundle.getString("TRUE");
        choice2 = kBundle.getString("FALSE");
    }

    /**
     * Performs a deep compare with "this" and another Object
     *
     * @param o TrueFalse question to be compared
     * @return A boolean that indicates whether or not Object o is equal to
     * "this"
     */
    @Override
    public boolean equals(Object o)
    {
        TrueFalse tf;

        // Make sure o is a TrueFalse question
        if (!(o instanceof TrueFalse))
        {
            return false;
        }
        tf = (TrueFalse) o;

        // Ensure the parent's equals method is true
        if (!super.equals(tf))
        {
            return false;
        }

        // If the TrueFalse values are the same
        if (this.choice1.equals(tf.choice1) && this.choice2.equals(tf.choice2))
        {
            return true;
        }
        return false;
    }

    /**
     * Creates a new TrueFalse object and calls its parent's copyData method
     *
     * @return Returns a new TrueFalse object that is identical to "this"
     */
    protected TrueFalse copy()
    {
        TrueFalse copy = new TrueFalse();
        super.copyData(copy);

        copy.choice1 = this.choice1;
        copy.choice2 = this.choice2;

        return copy;
    }

    /**
     * Sets the properties of the first choice.
     *
     * @param newChoice Text of the first choice
     * @param isAnswer Whether the first choice is an answer
     */
    public void setChoice1(String newChoice, boolean isAnswer)
    {
        this.choice1 = newChoice;
        // If we are setting the answer, so set the current answer to newChoice
        if (isAnswer)
        {
            setAnswer(newChoice);
        }
    }

    /**
     * Sets the properties of the second choice.
     *
     * @param newChoice Text of the second choice
     * @param isAnswer Whether the second choice is an answer
     */
    public void setChoice2(String newChoice, boolean isAnswer)
    {
        this.choice2 = newChoice;

        // Set the answer if specified to be an anwer
        if (isAnswer)
        {
            setAnswer(newChoice);
        }
    }

    /**
     * Gets the text of the first choice.
     *
     * @return Text of the first choice
     *
     */
    public String getChoice1()
    {
        return choice1;
    }

    /**
     * Gets the text of the second choice.
     *
     * @return Text of the second choice
     */
    public String getChoice2()
    {
        return choice2;
    }

    /**
     * Gets the HTML representation of a TrueFalse question.
     *
     * @param format a Format object used to set properties in the returned
     * string
     * @param isAnswerDoc determines whether or not the answer information is
     * built into the string
     * @return returns a string of HTML containing relevant member data
     */
    @Override
    public String getHTML(FormatApi format, boolean isAnswerDoc)
    {
        //Indicates where the marks should be located
        Marks.MarksEnum pos = (Marks.MarksEnum) format.getProperty(
                Format.Property.MarksPosition);
        //Indicates whether parenthesis will be shown
        boolean showParen = ((Boolean) format.getProperty(
                    Format.Property.ShowParen)).booleanValue();
        //If the current item is active, generate a nonempty HTML string
        if (isActive())
        {
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
            sb.append("<ol class=\"true_false\"><li>");
            //If this is the answerDoc, mark the answer with a tag
            if (isAnswerDoc && this.getChoice1().equals(getAnswer()))
            {
                sb.append("<b class=\"answer\">" + this.getChoice1() + "</b>");
            }
            else
            {
                sb.append(this.getChoice1());
            }
            sb.append("</li><li>");
            //If this is the answerDoc, mark the answer with a tag
            if (isAnswerDoc && this.getChoice2().equals(getAnswer()))
            {
                sb.append("<b class = \"answer\">" + this.getChoice2() + "</b>");
            }
            else
            {
                sb.append(this.getChoice2());
            }
            sb.append("</li></ol>");
            return sb.toString();
        }
        else
        {
            return "";
        }
    }
    
    /**
     * Generates a new instance of a TrueFalse question.
     * @return the new TrueFalse
     */
    @Override
    public TrueFalse newItem()
    {
        return new TrueFalse();
    }

    /**
     * Serialize this document item into a Moodle XML element
     * @param doc Root document
     * @return Moodle XML element representing this document item
     */
    @Override
    public Element serializeMoodle(Document doc)
    {
        Element question = doc.createElement("question"), text, questiontext;
        CDATASection cdataText;

        question.setAttribute("type", "truefalse");

        // Question text
        questiontext = doc.createElement("questiontext");
        questiontext.setAttribute("format", "html");
        text = doc.createElement("text");
        cdataText = doc.createCDATASection(this.getText());
        text.appendChild(cdataText);
        questiontext.appendChild(text);
        question.appendChild(questiontext);
        
        // Default grade
        Element defaultgrade = doc.createElement("defaultgrade");
        defaultgrade.setTextContent(((Integer)this.getMarks()).toString());
        question.appendChild(defaultgrade);

        // Penalty
        Element penalty = doc.createElement("penalty");
        penalty.setTextContent("1");
        question.appendChild(penalty);
        
        // Determine which option is the correct answer
        if(this.getChoice1().equals(getAnswer()))
        {
            question.appendChild(tfAnswers(doc, kMoodleTrue, kFracMax));
            question.appendChild(tfAnswers(doc, kMoodleFalse, kFracMin));
        }
        else
        {
            question.appendChild(tfAnswers(doc, kMoodleTrue, kFracMin));
            question.appendChild(tfAnswers(doc, kMoodleFalse, kFracMax));
        }
        
        return question;
    }
    
    private Element tfAnswers(Document doc, String typetext, Integer frac)
    {
        Element answer, text;
        
        // Initialize answer element
        answer = doc.createElement("answer");
        answer.setAttribute("fraction", frac.toString());

        // Add the text
        text = doc.createElement("text");
        text.setTextContent(typetext);
        answer.appendChild(text);
        
        return answer;
    }
}
