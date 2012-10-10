package qsolutions.exam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import property.AnswerIndexing;
import property.Marks;
import qsolutions.api.DriverApi;
import qsolutions.api.FormatApi;
import qsolutions.api.MultipleChoiceApi;

/**
 * Represents a multiple choice question
 */
@ServiceProvider(service = MultipleChoiceApi.class)
public class MultipleChoice extends Question implements Randomizable, MultipleChoiceApi
{
    private static final int kMaxChoices = 15;
    static final int kDefaultChoices = 4;
    private List<ChoiceEntry> choiceList;
    private int numChoices, answerNdx;

    /**
     * Represents a single choice in a multiple choice question
     */
    public class ChoiceEntry implements Serializable
    {
        /** The text of the answer */
        private String text;
        /** Indicates if the answer is active */
        private boolean active;

        /**
         * Creates a new ChoiceEntry
         *
         * @param text Text of the choice
         * @param active Whether the choice is active
         */
        public ChoiceEntry(String text, boolean active)
        {
            this.text = text;
            this.active = active;
        }
        
        /**
         * Returns the answer text
         * @return a string of representing the answer
         */
        public String getText()
        {
            return text;
        }
        
        /**
         * Indicates if the current answer is active.
         * @return true if it is, false otherwise
         */
        public boolean isActive()
        {
            return active;
        }
    }

    /**
     * Creates a new MultipleChoice question
     */
    public MultipleChoice()
    {
        super();
        setAbbreviation(kBundle.getString("MC"));
        setType(kBundle.getString("MULTIPLE CHOICE"));
        choiceList = new ArrayList<ChoiceEntry>();
        numChoices = kDefaultChoices;
        answerNdx = 0;
        // For each possible choice, set the default values for active and text
        for (int ndx = 0; ndx < numChoices; ndx++)
        {
            choiceList.add(new MultipleChoice.ChoiceEntry("", true));
        }
    }

    /**
     * Creates a new MultipleChoice object and calls its parent's copyData
     * method
     *
     * @return Returns a new MultipleChoice object that is identical to "this"
     */
    protected MultipleChoice copy()
    {
        MultipleChoice copy = new MultipleChoice();
        copy.clearChoices();
        
        int ndx;

        super.copyData(copy);

        // For each choice in choices, copy the attributes of each choice
        for (ndx = 0; ndx < numChoices; ndx++)
        {
            copy.addChoice(this.choiceList.get(ndx).text,
                    this.choiceList.get(ndx).active, ndx == this.answerNdx);
        }

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
        int ndx;
        MultipleChoice mc;
        Driver driver = new Driver();
        // Make sure the other object is a MultipleChoice object
        if (!(obj instanceof MultipleChoice))
        {
            return false;
        }
        mc = (MultipleChoice) obj;
        // Make sure all the default question fields match
        // Ensure the parent's equals method is true
        if (!super.equals(mc))
        {
            return false;
        }

        // Make sure the question properties are the same
        if (this.numChoices != mc.numChoices || this.answerNdx != mc.answerNdx)
        {
            return false;
        }
        // Loop through the choices
        for (ndx = 0; ndx < this.numChoices; ndx++)
        {
            /*System.out.println("Equals?:" + (new Driver().compareExtractedStrings(
                    this.choiceList.get(ndx).text, mc.choiceList.get(ndx).text)));*/
            // Make sure the choices are the same
            if (!(driver.compareExtractedStrings(
                    this.choiceList.get(ndx).text, mc.choiceList.get(ndx).text))
                    || this.choiceList.get(ndx).active 
                    != mc.choiceList.get(ndx).active)
            {
                return false;
            }
        }
        // If the answers match up
        if (this.getAnswerNdx() == -1 ||
                !(driver.compareExtractedStrings(this.getAnswer(), 
                    mc.choiceList.get(answerNdx).text)))
        {
            return false;
        }
        return true;
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

        question.setAttribute("type", "multichoice");

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

        // Serialize each entry
        for (int ndx = 0; ndx < numChoices; ndx++)
        {
            ChoiceEntry entry = choiceList.get(ndx);

            // Initialize elements
            answer = doc.createElement("answer");
            answerText = doc.createElement("text");
            cdataAnswerText = doc.createCDATASection(entry.getText());

            // If answer or not
            if (ndx == answerNdx)
            {
                answer.setAttribute("fraction", "100");
            }
            else
            {
                answer.setAttribute("fraction", "0");
            }

            // Build tree
            answerText.appendChild(cdataAnswerText);
            answer.appendChild(answerText);
            question.appendChild(answer);
        }

        return question;
    }

    /**
     * Randomizes the ChoiceEntries in the MultipleChoice question
     */
    @Override
    public void randomizeAnswers()
    {
        // If choices have been input
        if (numChoices > 0)
        {
            int numValidAns = numChoices;
            String answer = choiceList.get(answerNdx).getText();
            
            Collections.shuffle(choiceList);
            
            // For every quesiton
            for (int ndx = 0; ndx < numValidAns; ndx++)
            {
                // If the item is the answer
                if (Lookup.getDefault().lookup(
                        DriverApi.class).compareExtractedStrings(
                        choiceList.get(ndx).getText(), answer))
                {
                    answerNdx = ndx;
                }
                
                // If the choice is blank or inactive
                if (Lookup.getDefault().lookup(
                        DriverApi.class).compareExtractedStrings(
                        choiceList.get(ndx).getText(), "") ||
                        !choiceList.get(ndx).isActive())
                {
                    choiceList.add(choiceList.remove(ndx));
                    numValidAns--;
                    ndx--;
                }
            }
        }
    }

    /**
     * Accessor for the active of ChoiceEntry at an index
     * 
     * @param ndx the index to get the active value for
     * @return the active value for the given index
     */
    public boolean isActive(int ndx)
    {
        ChoiceEntry temp = choiceList.get(ndx);
        return temp.isActive();
    }
    
    /**
     * Sets the ChoiceEntry active/not active at the given index
     *
     * @param ndx Index of the ChoiceEntry to be set active or not active
     * @param active Whether the ChoiceEntry should be active or not
     */
    public void setActive(int ndx, boolean active)
    {
        ChoiceEntry temp = choiceList.get(ndx);
        temp.active = active;
    }
    
    /**
     * Accessor for the text of ChoiceEntry at an index
     * 
     * @param ndx the index to get the text for
     * @return the text for the given choice index
     */
    public String getText(int ndx)
    {
        ChoiceEntry temp = choiceList.get(ndx);
        return temp.getText();
    }
    
    /**
     * Sets the ChoiceEntry text at the given index
     * 
     * @param ndx the index of the ChoiceEntry to set
     * @param text the String to set the ChoiceEntry's text to
     */
    public void setText(int ndx, String text)
    {
        ChoiceEntry temp = choiceList.get(ndx);
        temp.text = text;
    }

    /**
     * Adds a choice to the choice array of the MultipleChoice question.
     * @param text The text of the choice to be added
     * @param isActive indicates if the question is active    
     * @param isAnswer Indicates if the choice is the answer of the question
     */
    public void addChoice(String text, boolean isActive, boolean isAnswer)
    {
        // If the number of choices is less then kMaxChoices
        if (numChoices < kMaxChoices)
        {
            choiceList.add(new MultipleChoice.ChoiceEntry(text, isActive));
            // If that choice is the answer
            if (isAnswer)
            {
                answerNdx = numChoices;
                setAnswer(getText(answerNdx));
            }
            numChoices += 1;
        }
    }

    /**
     * Edit a multiple choice question choice at the given index
     *
     * @param ndx Index of the choice that will be edited
     * @param text What the text of the edited choice should be
     * @param activeChoice Whether the edited choice should be active
     * @param isAnswer Whether the edited choice should be an answer
     */
    public void editChoice(int ndx, String text, boolean activeChoice,
            boolean isAnswer)
    {
        // If the index is a valid index (greater then 0 and less then the total
        // choices
        if (-1 < ndx && ndx < numChoices)
        {
            choiceList.remove(ndx);
            choiceList.add(ndx, new MultipleChoice.ChoiceEntry(text, activeChoice));
            // If the choice is the answer
            if (isAnswer)
            {
                answerNdx = ndx;
                setAnswer(text);
            }
        }
    }
    
    /**
     * Returns the text of the choice for the index or an empty String
     * @param ndx the index of the choice
     * @return the text of the choice or empty if the index is out of bounds
     */
    public String getChoiceText(int ndx)
    {
        // Return an empty string for out of bounds indexes
        if (ndx < 0 || choiceList.size() <= ndx)
        {
            return "";
        }
        return choiceList.get(ndx).getText();
    }
    
    /**
     * Returns true if the choice at index is active
     * @param ndx the index of the choice
     * @return false if the choice is inactive or the index is out of bounds
     *      otherwise true
     */
    public boolean getChoiceActive(int ndx)
    {
        // Return false for out of bounds indexes
        if (ndx < 0 || choiceList.size() <= ndx)
        {
            return false;
        }
        return choiceList.get(ndx).active;
    }

    /**
     * Checks whether the multiple choice question at the given index has the
     * given text and state.
     *
     * @param ndx Index of the item to be checked
     * @param text Text that the checked item should have
     * @param active Whether the checked item is active
     * @return Whether the multiple choice question at the given index has the
     * given text and state
     */
    public boolean checkChoice(int ndx, String text, boolean active)
    {
        return (-1 < ndx && ndx < numChoices
                && choiceList.get(ndx).text.equals(text) && choiceList.get(ndx).active
                == active);
    }

    /**
     * Gets the index of the ChoiceEntry that is the answer.
     *
     * @return index of the ChoiceEntry that is the answer
     */
    public int getAnswerNdx()
    {
        return answerNdx;
    }

    /**
     * Gets all of the choices associated with the question.
     *
     * @return Array of ChoiceEntries associated with the question
     */
    public ChoiceEntry[] getChoices()
    {
        return choiceList.toArray(new ChoiceEntry[choiceList.size()]);
    }

    /**
     * Gets the number of choices that have content
     * 
     * @return Number of choices that have content
     */
    public int getNumChoices()
    {
        return numChoices;
    }
    
    /**
     * Gets the HTML representation of the multiple choice question.
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
        String listType, mfix;
        String[] numberingStyle;

        //Indicates where the marks should be located
        Marks.MarksEnum pos =
                (Marks.MarksEnum) format.getProperty(
                    Format.Property.MarksPosition);
        //Indicates the type of numbering to be used in the MC Question
        AnswerIndexing.AnswerIndexingEnum prop =
                (AnswerIndexing.AnswerIndexingEnum) format.getProperty(
                    Format.Property.MCNumberingType);
        //Indcates whether parenthesis should be shown
        boolean showParen = ((Boolean) format.getProperty(
                    Format.Property.ShowParen)).booleanValue();

        // If item is active
        if (isActive())
        {
            listType = getListType(prop);
            numberingStyle = getNumberingStyle(prop);
            StringBuilder sb = new StringBuilder();
            
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
            // If horizontal arrangement (vertical is true)
            if (((Boolean) format.getProperty(
                    Format.Property.MCPosition)).booleanValue())
            {
                //genVerticalAlignment(sb, listType, isAnswerDoc);
                genHorizontalAlignment(sb, numberingStyle, isAnswerDoc);
                
            }
            //Else vertical arrangement
            else
            {
                genVerticalAlignment(sb, listType, isAnswerDoc);
                //genHorizontalAlignment(sb, numberingStyle, isAnswerDoc);
            }

            return sb.toString();
        }
        else
        {
            return "";
        }
    }

    /**
     * Get the list style for multiple choice questions
     * @param prop The property that determines the style
     * @return What the style of the list should be
     */
    private String getListType(AnswerIndexing.AnswerIndexingEnum prop)
    {
        String listType = "";
        // Switch on the specific property
        switch (prop)
        {
            case CapLetters:
                listType = "multiple_choice-CapLetters";
                break;
            case LowLetters:
                listType = "multiple_choice-LowLetters";
                break;
            case CapRoman:
                listType = "multiple_choice-CapRoman";
                break;
            case LowRoman:
                listType = "multiple_choice-LowRoman";
                break;
            case Numbers:
                listType = "multiple_choice-Numbers";
                break;
            default:
                listType = "multiple_choice-LowLetters";
                break;
        }
        return listType;
    }

    /**
     * Get the numbering style for multiple choice questions
     * @param prop The property that determines the style
     * @return What the style of the numbering should be
     */
    private String[] getNumberingStyle(AnswerIndexing.AnswerIndexingEnum prop)
    {
        String[] numberingStyle;
        // Switch on the specific property
        switch (prop)
        {
            case CapLetters:
                numberingStyle = Format.kCapCharArray;
                break;
            case LowLetters:
                numberingStyle = Format.kLowCharArray;
                break;
            case CapRoman:
                numberingStyle = Format.kCapRomanArray;
                break;
            case LowRoman:
                numberingStyle = Format.kLowRomanArray;
                break;
            case Numbers:
                numberingStyle = Format.kNumArray;
                break;
            default:
                numberingStyle = Format.kLowCharArray;
                break;
        }
        return numberingStyle;
    }

    /**
     * Generate vertical alignment code in the generated HTML
     * @param sb The string of HTML text to be appended to
     * @param listType The list type / style
     * @param isAnswerDoc Whether to generate an answer doc
     */
    private void genVerticalAlignment(StringBuilder sb, String listType,
            boolean isAnswerDoc)
    {
        DriverApi driver = Lookup.getDefault().lookup(DriverApi.class);
        sb.append("<ol class=\"" + listType + "\">");
        // For every choice
        for (int ndx = 0; ndx < numChoices; ndx++)
        {
            // If the choice is active
            if (choiceList.get(ndx).active 
                    && !driver.compareExtractedStrings(
                        choiceList.get(ndx).text, "") )
            {
                // If the choice is the answer
                if (isAnswerDoc && ndx == answerNdx)
                {
                    sb.append("<li><b class=\"answer\">"
                            + driver.cleanHTML2(choiceList.get(ndx).text) + "</b></li>");
                }
                else
                {
                    sb.append("<li>" + driver.cleanHTML2(
                            choiceList.get(ndx).text) 
                            + "</li>");
                }
            }
        }
        sb.append("</ol>");
    }

    /**
     * Generate horizontal alignment code in the generated HTML
     * @param sb The string of HTML text to be appended to
     * @param numberingStyle The numbering type / style
     * @param isAnswerDoc Whether to generate an answer doc
     */
    private void genHorizontalAlignment(StringBuilder sb, 
            String[] numberingStyle, boolean isAnswerDoc)
    {
        DriverApi driver = Lookup.getDefault().lookup(DriverApi.class);
        // For every choice
        sb.append("<br />");
        // for every choice in the MultipleChoice
        for (int ndx = 0; ndx < numChoices; ndx++)
        {
            // If the choice is activated
            if (choiceList.get(ndx).active 
                    && !driver.compareExtractedStrings(
                        choiceList.get(ndx).text, "") )
            {
                // If the choice is the answer
                if (isAnswerDoc && ndx == answerNdx)
                {
                    sb.append("<b class=\"answer\">" + numberingStyle[ndx]
                            + ". " + driver.cleanHTML2(
                            choiceList.get(ndx).text)
                            + "</b>&nbsp;&nbsp; ");
                }
                else
                {
                    sb.append(numberingStyle[ndx]
                            + ". "+ driver.cleanHTML2(
                            choiceList.get(ndx).text) 
                            + "&nbsp;&nbsp; ");
                }
            }
        }
    }
    
    /**
     * Generates a new instance of a MC question.
     * @return the new MultipleChoice
     */
    public MultipleChoiceApi newItem()
    {
        return new MultipleChoice();
    }
    
    /**
     * Removes all choices from the question
     */
    public void clearChoices()
    {
        choiceList.clear();
        numChoices = 0;
        answerNdx = -1;
    }
}
