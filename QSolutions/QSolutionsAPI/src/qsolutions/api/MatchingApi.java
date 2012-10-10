package qsolutions.api;

/**
 * The Matching interface for use with LookUp. Only contains methods needed
 * externally.
 */
public interface MatchingApi extends QuestionApi
{
    /**
     * Generates a new instance of a Matching question.
     * @return the new LongAnswer
     */
    public MatchingApi newItem();

    /** 
     * Adds a new matching pair to the class 
     * @param left text for the left value of the pair
     * @param right text for the right value of the pair
     * @param active active value for the pair
     */
    public void addMatchingPair(String left, String right, boolean active);

    /**
     * Gets the number of pairs in a matching question
     * @return Number of pairs in a matching question
     */
    public int getNumPairs();
    /**
     * Accessor for the left text
     * @param ndx index of the matching pair
     * @return String text of the left value
     */
    public String getLeft(int ndx);
    
    /**
     * Accessor for the right text
     * @param ndx index of the matching pair
     * @return String text of the right value
     */
    public String getRight(int ndx);

    /**
     * Checks whether or not the pair is active
     * @param ndx index of the matching pair
     * @return the active value of a matching pair
     */
    public boolean isActive(int ndx);

    /**
     * Removes all of the MatchingPairs in this question
     */
    public void removePairs();

    /**
     * Gets the display index for each matching choice
     * @param ndx Index of the matching question
     * @return Display index
     */
    public int getDisplayIndex(int ndx);

    /**
     * Set index to display at
     * Precondition: which must be < displayIndex.size() 
     * @param which index in the ArrayList
     * @param value value to set the index to
     */
    public void setDisplayIndex(int which, int value);
}
