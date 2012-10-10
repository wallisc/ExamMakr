package qsolutions.exam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import property.Marks;
import qsolutions.api.DriverApi;
import qsolutions.api.FormatApi;
import qsolutions.api.MatchingApi;

/**
 * Represents a matching question.
 */
@ServiceProvider(service = MatchingApi.class)
public class Matching extends Question implements MatchingApi, Randomizable
{
    private static final int kNumSpaces = 30;
    private ArrayList<MatchingPair> pairs;
    private ArrayList<Integer> displayIndex;

    /**
     * A pair of matching choices
     */
    private class MatchingPair implements Serializable
    {
        private String leftValue;
        private String rightValue;
        private boolean active;

        /**
         * Create a new MatchingPair
         *
         * @param left Left text in the pair
         * @param right Right text in the pair
         * @param active Whether it's active
         */
        public MatchingPair(String left, String right, boolean active)
        {
            leftValue = left;
            rightValue = right;
            this.active = active;
        }

        /**
         * Get the left value text for the entry
         *
         * @return Entry text
         */
        public String getLeftValue()
        {
            return this.leftValue;
        }

        /**
         * Get the right value text for the entry
         *
         * @return Entry text
         */
        public String getRightValue()
        {
            return this.rightValue;
        }

        /**
         * Whether this entry is active
         *
         * @return Whether this entry is active
         */
        public boolean isActive()
        {
            return this.active;
        }

        /**
         * Sets the left value text for the entry
         *
         * @param text Text to be set
         */
        public void setLeftValue(String text)
        {
            this.leftValue = text;
        }

        /**
         * Sets the right value text for the entry
         *
         * @param text Text to be set
         */
        public void setRightValue(String text)
        {
            this.rightValue = text;
        }

        /**
         * Sets the entry as active or not
         *
         * @param active Whether the entry should be active or not
         */
        public void setActive(boolean active)
        {
            this.active = active;
        }

        /**
         * Whether this item is indexable
         *
         * @return Whether this item is indexable
         */
        public boolean isIndexable()
        {
            Driver driver = new Driver();
            return isActive() && !driver.compareExtractedStrings( 
                getLeftValue(), "") && !driver.compareExtractedStrings(
                    getRightValue(), "");
        }
    }
    
    /**
     * Initializes Matching variables
     */
    public Matching()
    {
        super();
        setType(kBundle.getString("MATCHING"));
        setAbbreviation(kBundle.getString("MCH"));
        pairs = new ArrayList<MatchingPair>();
        displayIndex = new ArrayList<Integer>();
    }
    
    /**
     * Accessor for the left text
     *
     * @param ndx index of the matching pair
     * @return String text of the left value
     */
    public String getLeft(int ndx)
    {
        // If the index is valid
        if (ndx < pairs.size() && ndx > -1)
        {
            return pairs.get(ndx).leftValue;
        }
        else
        {
            return "";
        }
    }
    
    /**
     * Accessor for the right text
     *
     * @param ndx index of the matching pair
     * @return String text of the right value
     */
    public String getRight(int ndx)
    {
        // If it's a valid ndx, return the active property
        if (ndx < pairs.size() && ndx > -1)
        {
            return pairs.get(ndx).rightValue;
        }
        else
        {
            return "";
        }
    }
    
    /**
     * Checks whether or not the pair is active
     *
     * @param ndx index of the matching pair
     * @return the active value of a matching pair
     */
    public boolean isActive(int ndx)
    {
        // If it's a valid ndx, return the active property
        if (ndx < pairs.size() && ndx > -1)
        {
            return pairs.get(ndx).active;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Gets the number of pairs in a matching question
     *
     * @return Number of pairs in a matching question
     */
    public int getNumPairs()
    {
        return pairs.size();
    }
    
    /**
     * Removes all of the MatchingPairs in this question
     */
    public void removePairs()
    {
        pairs = new ArrayList<MatchingPair>();
        displayIndex = new ArrayList<Integer>();
    }
    
    /**
     * Gets the display index for each matching choice
     *
     * @param ndx Index of the matching question
     * @return Display index
     */
    public int getDisplayIndex(int ndx)
    {
        // If the index is valid
        if (ndx < displayIndex.size() && ndx > -1)
        {
            return displayIndex.get(ndx);
        }
        else
        {
            return -1;
        }
    }
    
    /**
     * Gets the text of the left field
     *
     * @return Text in the left field
     */
    public ArrayList<String> getLeftText()
    {
        ArrayList<String> left = new ArrayList<String>();
        
        // Loop through all the pairs and build up with left text
        for (int ndx = 0; getDisplayIndex(ndx) > -1; ndx++)
        {
            left.add(getLeft(ndx));
        }
        return left;
    }
    
    /**
     * Gets the text of the right field
     *
     * @return Text in the right field
     */
    public ArrayList<String> getRightText()
    {
        ArrayList<String> right = new ArrayList<String>();
        
        // Loop through all the pairs and build up with right text
        for (int ndx = 0; getDisplayIndex(ndx) > -1; ndx++)
        {
            right.add(getRight(getDisplayIndex(ndx)));
        }
        return right;
    }
    
    /**
     * Sets the display index for the pair at the given index Precondition:
     * which must be < displayIndex.size()
     *
     * @param which index in the ArrayList
     * @param value value to set the index to
     */
    public void setDisplayIndex(int which, int value)
    {
        displayIndex.set(which, value);
    }
    
    /**
     * Sets the left text of a particular matching pair
     *
     * @param ndx index of the matching pair
     * @param text text to set the left value to
     */
    public void setLeft(int ndx, String text)
    {
        // If the index is valid
        if (ndx < pairs.size())
        {
            pairs.get(ndx).setLeftValue(text);
        }
    }
    
    /**
     * Sets the right text of a particular matching pair
     *
     * @param ndx index of the matching pair
     * @param text text to set the right value to
     */
    public void setRight(int ndx, String text)
    {
        // If the index is valid
        if (ndx < pairs.size())
        {
            pairs.get(ndx).setRightValue(text);
        }
    }
    
    /**
     * Sets the active value of a particular matching pair
     *
     * @param ndx index of the matching pair
     * @param active boolean to set the active value to
     */
    public void setActive(int ndx, boolean active)
    {
        // If the index is valid
        if (ndx < pairs.size())
        {
            pairs.get(ndx).setActive(active);
        }
    }
    
    /**
     * Adds a new matching pair to the class
     *
     * @param left text for the left value of the pair
     * @param right text for the right value of the pair
     * @param active active value for the pair
     */
    public final void addMatchingPair(String left, String right, boolean active)
    {
        displayIndex.add(pairs.size());
        pairs.add(new MatchingPair(left, right, active));
    }
    
    /**
     * Remove a pair at the given index
     *
     * @param ndx Index of the pair to remove
     */
    public void removeMatchingPair(int ndx)
    {
        // If index is valid
        if (ndx < pairs.size())
        {
            pairs.remove(ndx);
            displayIndex.remove(ndx);
        }
    }
    
    /**
     * Generates a new instance of a Order question.
     *
     * @return the new Order
     */
    @Override
    public MatchingApi newItem()
    {
        Matching ret = new Matching();
        ret.addMatchingPair("", "", true);
        ret.addMatchingPair("", "", true);
        ret.addMatchingPair("", "", true);
        ret.addMatchingPair("", "", true);
        return ret;
    }
    
    /**
     * Creates a new Matching object and calls its parent's copyData method
     *
     * @return Returns a new Order object that is identical to "this"
     */
    public Matching copy()
    {
        Matching copy = new Matching();
        MatchingPair mp;
        int ndx;
        
        // Copy each of the pairs
        for (ndx = 0; ndx < pairs.size(); ndx++)
        {
            mp = pairs.get(ndx);
            copy.addMatchingPair(mp.leftValue, mp.rightValue, mp.active);
            copy.setDisplayIndex(ndx, displayIndex.get(ndx));
        }
        
        super.copyData(copy);
        
        return copy;
    }
    
    /**
     * Equals comparison method
     *
     * @param obj Object to compare to this
     * @return boolean indicating equality
     */
    @Override
    public boolean equals(Object obj)
    {
        Matching match;
        Driver driver = new Driver();
        
        // Make sure the other object is a Matching object
        if (!(obj instanceof Matching))
        {
            return false;
        }
        match = (Matching) obj;
        
        // Ensure the parent's equals method is true
        if (!super.equals(match))
        {
            return false;
        }
        
        // Ensure the number of pairs is the same
        if (pairs.size() != match.getNumPairs())
        {
            return false;
        }
        
        // Loop through all the choices to make sure they're equal
        for (int ndx = 0; ndx < pairs.size(); ndx++)
        {
            // Make sure the active value is the same
            if (pairs.get(ndx).active != match.isActive(ndx))
            {
                return false;
            }
            
            // Make sure the left value equals the other
            if (!(driver.compareExtractedStrings(pairs.get(ndx).leftValue,
                    match.getLeft(ndx))))
            {
                return false;
            }
            
            // Make sure the right value equals each other
            if (!(driver.compareExtractedStrings(pairs.get(ndx).rightValue,
                    match.getRight(ndx))))
            {
                return false;
            }
            
            // Make sure the display indexes are equivalant
            if (displayIndex.get(ndx) != match.getDisplayIndex(ndx))
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Randomizes the Matching answers.
     */
    @Override
    public void randomizeAnswers()
    {
        LinkedList<Integer> positions = new LinkedList();
        MatchingPair currEntry;
        int count = 0;
        
        // Populate a list with the number of ints for indexable items
        for (Integer ndx = 0; ndx < getNumIndexable(); ndx++)
        {
            positions.add(ndx);
        }
        Collections.shuffle(positions);
        
        // Assign the ints from above into the indexable items
        for (Integer ndx = 0; ndx < pairs.size(); ndx++)
        {
            currEntry = pairs.get(ndx);
            // If the choice is indexable, assign it a random index
            if (currEntry.isIndexable())
            {
                displayIndex.set(ndx, positions.get(count));
                count += 1;
            }
        }
    }
    
    private int getNumIndexable()
    {
        int ret = 0;
        
        // Loop through all the choices and count all the indexable
        for (Integer ndx = 0; ndx < pairs.size(); ndx++)
        {
            // If it's indexable, increment the counter
            if (pairs.get(ndx).isIndexable())
            {
                ret += 1;
            }
        }
        return ret;
    }
    
    /**
     * HTML representation of a matching question
     *
     * @param format Format to be rendered
     * @param isAnswerDoc Whether to render an answer document
     * @return String of HTML representation
     */
    @Override
    public String getHTML(FormatApi format, boolean isAnswerDoc)
    {
        StringBuilder sb = new StringBuilder();
        DriverApi driver = Lookup.getDefault().lookup(DriverApi.class);
        //Indicates if parenthesis should be shown
        boolean showParen = ((Boolean) format.getProperty(
                Format.Property.ShowParen)).booleanValue();
        //Indicates where the marks should be located
        Marks.MarksEnum pos = (Marks.MarksEnum) format.getProperty(
                Format.Property.MarksPosition);
        
        // If it's not active, return an empty string
        if (!isActive())
        {
            return "";
        }
        //Checks where the marks should be located
        String mfix;
        sb.append(format.getMarksHtml(this.getMarks(),
                pos, showParen));
        
        sb.append(format.getImageHtml(this.getImage(), this.getScale(),
                this.getImageAlignmnet()));
        mfix = Lookup.getDefault().lookup(DriverApi.class).cleanHTML(
                this.getText());
        // If it contains an inline mark
        if (pos == Marks.MarksEnum.InLine)
        {
            // If it contains an empty top margin
            if (mfix.contains("<p style=\"margin-top: 0\">"))
            {
                mfix = mfix.replaceFirst("<p style=\"margin-top: 0\">", "");
            }
            else
            {
                mfix = mfix + "</p>";
            }
        }
        sb.append(mfix);
        
        sb.append("<div class=\"matching\">"
                + "<div class=\"matching_left\">").append(
                "<ul style=\"list-style-type:none;\">");
        
        // Loop through all the pairs and add them to the HTML
        for (int ndx = 0; ndx < getNumPairs(); ndx++)
        {
            // If the pair is not empty and is active
            if (!driver.compareExtractedStrings(getLeft(getDisplayIndex(ndx)),
                    "") && !driver.compareExtractedStrings(getRight(
                        getDisplayIndex(ndx)), "") && isActive(ndx))
            {
                sb.append("<li style=\"margin-top:0.5em;\">").append(
                        "<div style=\"width:2em; text-align:center;").append(
                        "" + "border-bottom:1px solid black; display:inline;\">"
                        + " &nbsp; <span style=\"color:red;\">");
                
                // Print the answer if active
                if (isAnswerDoc && isActive(ndx))
                {
                    sb.append(getDisplayIndex(ndx) + 1);
                }
                else
                {
                    sb.append("&nbsp;");
                }
                sb.append("</span> &nbsp; </div> &nbsp;").append(
                        "&nbsp; &nbsp;").append(driver.cleanHTML2(
                        getLeft(ndx))).append("</li>");
            }
        }
        
        return finishHtml(sb);
    }
    
    /**
     * Serialize this document item into a Moodle XML element
     *
     * @param doc Root document
     * @return Moodle XML element representing this document item
     */
    @Override
    public Element serializeMoodle(Document doc)
    {
        Element question = doc.createElement("question"),
                subQuestion, subQuestionText, subAnswer, subAnswerText,
                questionText, text;
        CDATASection cdataText, cdataSubAnswerText, cdataSubQuestionText;
        
        question.setAttribute("type", "matching");
        
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
        defaultgrade.setTextContent(((Integer) this.getMarks()).toString());
        question.appendChild(defaultgrade);
        
        // Penalty
        Element penalty = doc.createElement("penalty");
        penalty.setTextContent("1");
        question.appendChild(penalty);
        
        // Serialize each pair
        for (MatchingPair pair : pairs)
        {
            // If the pair is indexable
            if (pair.isIndexable())
            {
                // Initialize elements
                subQuestion = doc.createElement("subquestion");
                subQuestionText = doc.createElement("text");
                subAnswer = doc.createElement("answer");
                subAnswerText = doc.createElement("text");
                cdataSubQuestionText = doc.createCDATASection(pair.leftValue);
                cdataSubAnswerText = doc.createCDATASection(pair.rightValue);
                
                // Build tree
                subAnswerText.appendChild(cdataSubAnswerText);
                subQuestionText.appendChild(cdataSubQuestionText);
                subAnswer.appendChild(subAnswerText);
                subQuestion.appendChild(subAnswer);
                subQuestion.appendChild(subQuestionText);
                question.appendChild(subQuestion);
            }
        }
        
        return question;
    }
    
    private String finishHtml(StringBuilder sb)
    {
        DriverApi driver = Lookup.getDefault().lookup(DriverApi.class);
        sb.append("</ul></div>");
        sb.append("<div class=\"matching_right\"><ol>");
        
        // Print the options for the right side
        for (int ndx = 0; ndx < getNumPairs(); ndx++)
        {
            // Print if active and non empty
            if (!getRight(getDisplayIndex(ndx)).equals("") && isActive(ndx))
            {
                // Loop through and find the corresponding display index
                for (int innerNdx = 0; innerNdx < getNumPairs(); innerNdx++)
                {
                    // If it's the display index, display it
                    if (getDisplayIndex(innerNdx) == ndx)
                    {
                        String rightText = getRight(innerNdx);
                        sb.append("<li style=\"margin-top:0.5em;\">").append(
                                driver.cleanHTML(rightText)).append("</li>");
                        break;
                    }
                }
            }
        }
        sb.append("</ol></div>");
        sb.append("<div class=\"clearfix\"></div></div>");
        
        return sb.toString();
    }
}
