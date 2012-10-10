package qsolutions.api;

/**
 * The LongAnswer interface for use with LookUp. Only contains methods needed
 * externally.
 * @author Ryan Dollahon
 */
public interface FreeResponseApi extends QuestionApi
{
    /**
     * Gets the extra lines set for the question.
     *
     * @return The number of extra lines
     */
    int getExtraLines();
    
    /**
     * Generates a new instance of a LongAnswer question.
     * @return the new LongAnswer
     */
    public FreeResponseApi newItem();
    
    /**
     * Set the number of extra lines for the given question.
     *
     * @param extra The number of extra lines
     */
    void setExtraLines(int extra);
}
