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
import qsolutions.api.OrderApi;

/**
 * Represents an order question that requires an Exam taker to put a series of
 * items in a specific order.
 */
@ServiceProvider(service = OrderApi.class)
public class Order extends Question implements OrderApi, Randomizable
{
    private ArrayList<OrderEntry> orderEntries;
    private ArrayList<Integer> displayIndex;

    /**
     * Sets the type to "Order"
     */
    public Order()
    {
        super();
        setType(kBundle.getString("ORDER"));
        setAbbreviation(kBundle.getString("ORD"));
        orderEntries = new ArrayList<OrderEntry>();
        displayIndex = new ArrayList<Integer>();
    }

    /**
     * An inner class to hold each order text entry and its answer place
     */
    private class OrderEntry implements Serializable
    {
        private String text;
        private boolean active;

        /**
         * Initializes the orderEntry with parameters
         *
         * @param text String containing the entry text
         * @param answer String Containing the answer
         * @param active boolean to indicate if the entry should be displayed
         */
        public OrderEntry(String text, boolean active)
        {
            this.text = text;
            this.active = active;
        }

        /**
         * Get the text for the entry
         *
         * @return Entry text
         */
        public String getText()
        {
            return this.text;
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
         * Sets the text for the entry
         *
         * @param text Text to be set
         */
        public void setText(String text)
        {
            this.text = text;
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
                    getText(), "");
        }
    }

    /**
     * Adds an order choice to the question
     *
     * @param text String containing the text to be displayed
     * @param active whether the choice is to be active
     */
    public void addOrderChoice(String text, boolean active)
    {
        displayIndex.add(orderEntries.size());
        orderEntries.add(new OrderEntry(text, active));
    }

    /**
     * Removes an order entry by index
     *
     * @param ndx int of the index
     */
    public void removeOrderChoice(int ndx)
    {
        // If the index is valid
        if (ndx < orderEntries.size())
        {
            orderEntries.remove(ndx);
            displayIndex.remove(orderEntries.size());
        }
    }

    /**
     * Removes all of the OrderEntries in this question
     */
    public void removeChoices()
    {
        orderEntries = new ArrayList<OrderEntry>();
        displayIndex = new ArrayList<Integer>();
    }

    /**
     * Gets the display index for the choice
     *
     * @param ndx Index of the choice to get the display index
     * @return Index of the choice's display index
     */
    public int getDisplayIndex(int ndx)
    {
        // If index is valid
        if (ndx < orderEntries.size() && ndx >= 0)
        {
            return displayIndex.get(ndx);
        }
        return -1;
    }

    /**
     *      * Precondition: which must be < displayIndex.size()
     *
     * @param which index in the ArrayList
     * @param value value to set the index to
     */
    public void setDisplayIndex(int which, int value)
    {
        displayIndex.set(which, value);
    }

    /**
     * Gets the text for the particular order entry
     *
     * @param ndx int containing the index to get
     * @return String returning the text of the order entry
     */
    public String getOrderText(int ndx)
    {
        // If the index is valid
        if (ndx < orderEntries.size())
        {
            return orderEntries.get(ndx).getText();
        }
        return "";
    }

    /**
     * Sets the text of the order entry
     *
     * @param ndx int containing the index to set
     * @param text String to set the order entry text to
     */
    public void setOrderText(int ndx, String text)
    {
        // If the index is valid
        if (ndx < orderEntries.size())
        {
            orderEntries.get(ndx).setText(text);
        }
    }

    /**
     * Gets the number of order entries currently held in orderEntries
     *
     * @return int containing the number of order entries
     */
    public int getNumOrders()
    {
        return orderEntries.size();
    }

    /**
     * Gets the active value of the order entry
     *
     * @param ndx Index of the order entry to check
     * @return boolean indicating if the order entry is active
     */
    public boolean isActive(int ndx)
    {
        // verify valid index
        if (ndx < orderEntries.size())
        {
            return orderEntries.get(ndx).active;
        }
        return false;
    }

    /**
     * Sets the active variable of the order entry
     *
     * @param ndx index of the order entry to change the active value of
     * @param active boolean indicating if the order entry should be active
     */
    public void setActive(int ndx, boolean active)
    {
        // If the index is valid
        if (ndx < orderEntries.size())
        {
            orderEntries.get(ndx).setActive(active);
        }
    }

    /**
     * Creates a new Order object and calls its parent's copyData method
     *
     * @return Returns a new Order object that is identical to "this"
     */
    protected Order copy()
    {
        Order copy = new Order();
        OrderEntry oe;
        int ndx;
        //  Loop through all the entries and copy them
        for (ndx = 0; ndx < orderEntries.size(); ndx++)
        {
            oe = orderEntries.get(ndx);
            copy.addOrderChoice(oe.text, oe.active);
            copy.setDisplayIndex(ndx, getDisplayIndex(ndx));
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
        Order od;
        int ndx;
        Driver driver = new Driver();

        // Make sure the other object is a Order
        if (!(obj instanceof Order))
        {
            return false;
        }
        od = (Order) obj;

        // Ensure the parent's equals method is true
        if (!super.equals(od))
        {
            return false;
        }

        // Ensure the number of order entries is the same
        if (orderEntries.size() != od.getNumOrders())
        {
            return false;
        }

        // Check every order choice
        for (ndx = 0; ndx < orderEntries.size(); ndx++)
        {
            // Make sure the active value is the same
            if (orderEntries.get(ndx).active != od.isActive(ndx))
            {
                return false;
            }

            // Make sure the text equals each other
            if (!(driver.compareExtractedStrings(orderEntries.get(ndx).text,
                    od.getOrderText(ndx))))
            {
                return false;
            }

            // If the display indexes are not equivalent
            if (displayIndex.get(ndx) != od.getDisplayIndex(ndx))
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the HTML string for an Order question.
     *
     * @param format The associated format class
     * @param isAnswerDoc Indicates whether the Order should show answers.
     * @return A string containing HTML for this question
     */
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

        // Return nothing for inactive questions
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

        sb.append("<div class=\"order\">").append(
                "<ul style=\"list-style-type:none;\">");

        // Iterete through each order choice
        for (int ndx = 0; ndx < getNumOrders(); ndx++)
        {
            // If the item is not empty and is active
            if (!driver.compareExtractedStrings(
                    getOrderText(getDisplayIndex(ndx)), "") && isActive(ndx))
            {
                sb.append("<li style=\"margin-top:0.5em;\">").append(
                        "<div style=\"width:2em; text-align:center;").append(
                        "border-bottom:1px solid black;").append(
                        "display:inline;\">").append(
                        " &nbsp; <span style=\"color:red;\">");

                //If we're printing the answers
                if (isAnswerDoc)
                {
                    sb.append(displayIndex.indexOf(ndx) + 1);
                }
                else
                {
                    sb.append("&nbsp;");
                }
                sb.append("</span> &nbsp; </div> &nbsp;").append(
                        "&nbsp; &nbsp;").append(
                        driver.cleanHTML2(getOrderText(displayIndex.indexOf(
                        ndx)))).append("</li>");

            }
        }
        sb.append("</ul></div>");
        return sb.toString();
    }

    /**
     * Randomizes the Order's answers.
     */
    @Override
    public void randomizeAnswers()
    {
        LinkedList<Integer> positions = new LinkedList();
        OrderEntry currEntry;
        int count = 0;

        // Populate a list with the number of ints for indexable items
        for (Integer ndx = 0; ndx < getNumIndexable(); ndx++)
        {
            positions.add(ndx);
        }
        Collections.shuffle(positions);

        // Assign the ints from above into the indexable items
        for (Integer ndx = 0; ndx < orderEntries.size(); ndx++)
        {
            currEntry = orderEntries.get(ndx);
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
        for (Integer ndx = 0; ndx < orderEntries.size(); ndx++)
        {
            // If it's indexable, increment the counter
            if (orderEntries.get(ndx).isIndexable())
            {
                ret += 1;
            }
        }
        return ret;
    }

    /**
     * Generates a new instance of a Order question.
     *
     * @return the new Order
     */
    @Override
    public OrderApi newItem()
    {
        Order ret = new Order();
        ret.addOrderChoice("", true);
        ret.addOrderChoice("", true);
        ret.addOrderChoice("", true);
        ret.addOrderChoice("", true);
        return ret;
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
        String questionStr = Lookup.getDefault().lookup(
                DriverApi.class).cleanHTML2(this.getText()), answerStr = "";

        LinkedList<Integer> order = new LinkedList<Integer>();

        // Create a shuffled list of positions
        for (int ndx = 0; ndx < orderEntries.size(); ndx++)
        {
            order.addLast(ndx);
        }
        Collections.shuffle(order);

        // Loop through all the orders and serialize them
        for (int ndx = 0; ndx < orderEntries.size(); ndx++)
        {
            OrderEntry oe = orderEntries.get(order.get(ndx));

            // If the order entry is indexable, serialize it
            if (oe.isIndexable())
            {
                questionStr += "<br/>__ " + Lookup.getDefault().lookup(
                        DriverApi.class).cleanHTML2(oe.getText());
                answerStr += (order.get(ndx) + 1) + ": " + 
                        Lookup.getDefault().lookup(
                        DriverApi.class).cleanHTML2(oe.getText()) + "<br/>";
            }
        }

        Element question = doc.createElement("question"),
                subQuestion, subQuestionText, subAnswer, subAnswerText,
                questionText, text, answer;
        CDATASection cdataText, cdataSubAnswerText, cdataSubQuestionText;

        question.setAttribute("type", "essay");

        // Question text
        questionText = doc.createElement("questiontext");
        questionText.setAttribute("format", "html");
        text = doc.createElement("text");
        cdataText = doc.createCDATASection(questionStr);
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

        // Add the answer
        answer = doc.createElement("graderinfo");
        cdataSubAnswerText = doc.createCDATASection(answerStr);
        text = doc.createElement("text");
        text.appendChild(cdataSubAnswerText);
        answer.appendChild(text);

        question.appendChild(answer);

        return question;
    }
}
