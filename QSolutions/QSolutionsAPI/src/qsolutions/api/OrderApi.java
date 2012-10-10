package qsolutions.api;

/**
 * The Order interface for use with LookUp. Only contains methods needed
 * externally.
 * @author Ryan Dollahon
 */
public interface OrderApi extends QuestionApi
{
    /**
     * Generates a new instance of an Order item.
     * @return the new Order
     */
    public OrderApi newItem();

    /**
     * Gets the number of order entries currently held in orderEntries
     * @return int containing the number of order entries
     */
    public int getNumOrders();

    /**
     * Removes all of the OrderEntries in this question
     */
    public void removeChoices();

    /**
     * Set index at which to display
     * Precondition: which must be < displayIndex.size() 
     * @param which index in the ArrayList
     * @param value value to set the index to
     */
    public void setDisplayIndex(int which, int value);

    /**
     * Gets the text for the particular order entry
     * @param ndx int containing the index to get 
     * @return String returning the text of the order entry
     */
    public String getOrderText(int ndx);

    /**
     * Gets the active value of the order entry
     * @param ndx index to check if is active
     * @return boolean indicating if the order entry is active
     */
    public boolean isActive(int ndx);

    /**
     * Gets the display index for the choice
     * @param ndx Index of the choice to get the display index
     * @return Index of the choice's display index
     */
    public int getDisplayIndex(int ndx);

    /**
     * Adds an order choice to the question
     * @param text String containing the text to be displayed
     * @param active Whether or not the choice is active
     */
    public void addOrderChoice(String text, boolean active);
}