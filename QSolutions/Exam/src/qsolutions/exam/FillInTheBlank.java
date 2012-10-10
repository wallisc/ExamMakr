package qsolutions.exam;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import property.Marks;
import qsolutions.api.DriverApi;
import qsolutions.api.FillInTheBlankApi;
import qsolutions.api.FormatApi;

/**
 * Questions that usually have a sentence with a word or phrase replace with a 
 * blank that the student is expected to fill in.
 */
@ServiceProvider(service = FillInTheBlankApi.class)
public class FillInTheBlank extends Question implements FillInTheBlankApi
{
    /**
     * Creates a FillInTheBlank with default type and abbreviation.
     */
    public FillInTheBlank()
    {
        super();
        setType(kBundle.getString("Fill-in-the-Blanks"));
        setAbbreviation(kBundle.getString("FITB"));
    }

    /**
     * Creates a new FillInTheBlank object and calls its parent's copyData
     * method.
     *
     * @return Returns a new FillInTheBlank object that is identical to "this"
     */
    protected FillInTheBlank copy()
    {
        FillInTheBlank copy = new FillInTheBlank();

        super.copyData(copy);

        return copy;
    }

    /**
     * Performs a deep compare with "this" and another Object
     *
     * @param o An Object to compare
     * @return A boolean that indicates whether or not Object o is equal to
     * "this"
     */
    @Override
    public boolean equals(Object o)
    {
        FillInTheBlank fitb;

        // Make sure the other object is FillInTheBlank
        if (!(o instanceof FillInTheBlank))
        {
            return false;
        }
        fitb = (FillInTheBlank) o;
        
        // Ensure the parent's equals method is true
        if (!super.equals(fitb))
        {
            return false;
        }
        return true;
    }

    /**
     * Gets the HTML representation of the fill in the blank
     * question
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
        boolean showParen = ((Boolean) format.getProperty(
                    Format.Property.ShowParen)).booleanValue();
        String curr, text = getText();
        Pattern myPattern = Pattern.compile("[^/]\\[.*");
        Matcher matcher = myPattern.matcher(text);
        StringBuilder sb = new StringBuilder();
        char[] chars;
        // Ensure the question is active
        if (isActive())
        {
            String[] matches = text.split("\\]");
            text = text.replace("/[", "[");
            // For each blank, fill in appropriately
            for (int ndx = 0; ndx < matches.length; ndx++)
            {
                matcher = myPattern.matcher(matches[ndx]);
                //If the pattern we are looking for is found
                if (matcher.find())
                {
                    curr = matcher.group().trim();
                    //If the answers should be printed, fill the blanks in red
                    if (isAnswerDoc)
                    {
                        curr = curr.replaceFirst("\\[", "");
                        text = text.replace("["+curr+"]", "<b class="
                                + "\"answer\">" + curr + "</b>");
                    }
                    else
                    {
                        chars = new char[curr.length() - 1];
                        Arrays.fill(chars, '_');
                        text = text.replace(curr + "]", "" + new String(chars)
                                + "");
                    }
                }
            }
            //Checks where the marks should be located

            endHTML(sb, format, pos, showParen, text);
            text = sb.toString();
            
        }
        else
        {
            text = "";
        }
        return text;
    }

    private void endHTML(StringBuilder sb, FormatApi format, 
            Marks.MarksEnum pos, boolean showParen, String text)
    {
        String mfix;
                    
        sb.append(format.getMarksHtml(this.getMarks(), 
                pos, showParen));
        
        sb.append(format.getImageHtml(this.getImage(), this.getScale(), 
                this.getImageAlignmnet()));
        mfix = getFillMarksHtml(pos, text);
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
    }
    
    /**
     * Get the marks HTML based on position and text
     * @param pos Marks position
     * @param text Question
     * @return 
     */
    
    private String getFillMarksHtml(Marks.MarksEnum pos, String text)
    {
        String str;
        //If the current format is parenthesis, fill in the marks here
        if (pos == Marks.MarksEnum.InLine)
        {
            str = Lookup.getDefault().lookup(DriverApi.class).cleanHTML(
                    text);
        }
        else
        {
            str = Lookup.getDefault().lookup(DriverApi.class).cleanHTML(
                    text);
        }
        return str;
    }
    
    /**
     * Generates a new instance of a FillInTheBlank question.
     * @return the new FillInTheBlank
     */
    @Override
    public FillInTheBlank newItem()
    {
        return new FillInTheBlank();
    }

    /**
     * Serialize this document item into a Moodle XML element
     * @param doc Root document
     * @return Moodle XML element representing this document item
     */
    @Override
    public Element serializeMoodle(Document doc)
    {
        Element question = doc.createElement("question"), questionText, text,
                answer, answerText;
        CDATASection cdataText, cdataAnswerText;

        question.setAttribute("type", "cloze");

        // Generate fill in the blank question
        String pattern = "[^/]\\[[^\\]]*\\]";
        int points;
        int matches = this.getText().split(pattern).length-1;
        
        // If there are 0 matching pairs, set the number of points to 0
        if (matches == 0)
        {
            points = 0;
        }
        else
        {
            points = (this.getMarks()/matches)+1;
        }
        String substituteQuestion = this.getText().replaceAll(
                "([^/])\\[([^\\]]*)\\]", "$1{" + points + ":SHORTANSWER:=$2}");
        substituteQuestion = substituteQuestion.replaceAll("/\\[", "[");

        // Question text
        questionText = doc.createElement("questiontext");
        questionText.setAttribute("format", "html");
        text = doc.createElement("text");
        cdataText = doc.createCDATASection(substituteQuestion);
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
        
        return question;
    }
}
