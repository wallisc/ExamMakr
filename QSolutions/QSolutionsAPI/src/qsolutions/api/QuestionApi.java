package qsolutions.api;

/**
 * The Question interface for use with LookUp. Only contains methods needed
 * externally.
 * @author Ryan Dollahon
 */
public interface QuestionApi extends DocumentItemApi
{
    /**
     * Gets the answer from the current question.
     *
     * @return answer of the question in a string
     */
    String getAnswer();
    /**
     * Gets the question number from the current question.
     *
     * @return the question number of the current question in an integer
     */
    int getQNum();
    
    /**
     * Generates a new instance of a Question.
     * @return the new TrueFalse
     */
    @Override
    QuestionApi newItem();
    /**
     * Sets the answer for the current question.
     *
     * @param nAnswer answer of the question as a String
     */
    void setAnswer(String nAnswer);
    /**
     * Sets the question category of the current question using the parameter
     * string.
     *
     * @param nCategory string to set the question category
     */
    void setCategory(String nCategory);
    /**
     * Set the question level to the parameter string.
     *
     * @param nLevel the question level of the current question
     */
    void setLevel(String nLevel);
    /**
     * Assigns a value of marks to the current question.
     *
     * @param nMarks the value for the marks of the current question
     */
    void setMarks(int nMarks);
    /**
     * assigns an integer value to the question number
     *
     * @param nNum an assigned integer value for the current question number
     */
    void setQNum(int nNum);
    /**
     * Gets the actual question number of the current question.
     *
     * @return the actual question number of the current question
     */
    public int getActualQNum();
    /**
     * Set the actual question number to the parameter string.
     *
     * @param num the question number of the current question
     */
    public void setActualQNum(int num);
}
