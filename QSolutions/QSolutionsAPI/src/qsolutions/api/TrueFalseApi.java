package qsolutions.api;

/**
 * The TrueFalse interface for use with LookUp. Only contains methods needed
 * externally.
 */
public interface TrueFalseApi extends QuestionApi
{
    /**
     * Gets the text of the first choice.
     * @return Text of the first choice
     */
    String getChoice1();
    
    /**
     * Gets the text of the second choice.
     * @return Text of the first choice
     */
    String getChoice2();
    
    /**
     * Generates a new instance of a TrueFalse question.
     * @return the new TrueFalse
     */
    TrueFalseApi newItem();
    
    /**
     * Sets the properties of the second choice.
     *
     * @param nText Text of the second choice
     * @param isAnswer Whether the second choice is an answer
     */
    void setChoice1(String nText, boolean isAnswer);
    
    /**
     * Sets the properties of the first choice.
     *
     * @param nText Text of the first choice
     * @param isAnswer Whether the first choice is an answer
     */
    void setChoice2(String nText, boolean isAnswer);
}
