package qsolutions.api;

/**
 * The MultipleChoice interface for use with LookUp. Only contains methods
 * needed externally.
 */
public interface MultipleChoiceApi extends QuestionApi
{
    /**
     * Edit a multiple choice question choice at the given index.
     *
     * @param ndx Index of the choice that will be edited
     * @param txt What the text of the edited choice should be
     * @param act Whether the edited choice should be active
     * @param ans Whether the edited choice should be an answer
     */
    void editChoice(int ndx, String txt, boolean act, boolean ans);
    /**
     * Gets all of the choices associated with the question.
     *
     * @return Array of ChoiceEntries associated with the question
     */
    Object[] getChoices();
    /**
     * Gets the index of the ChoiceEntry that is the answer.
     *
     * @return index of the ChoiceEntry that is the answer
     */
    int getAnswerNdx();
    /**
     * Gets the number of choices that have content
     * 
     * @return Number of choices that have content
     */
    int getNumChoices();
    /**
     * Removes all choices from the question
     */
    void clearChoices();
    /**
     * Returns the text of the choice for the index or an empty String
     * @param ndx the index of the choice
     * @return the text of the choice or empty if the index is out of bounds
     */
    public String getChoiceText(int ndx);
    /**
     * Returns true if the choice at index is active
     * @param ndx the index of the choice
     * @return false if the choice is inactive or the index is out of bounds
     *      otherwise true
     */
    public boolean getChoiceActive(int ndx);
    /**
     * Generates a new instance of a MC question.
     * @return the new MultipleChoice
     */
    public MultipleChoiceApi newItem();
    /**
     * Randomizes the ChoiceEntries in the MultipleChoice question.
     */
    void randomizeAnswers();
    /**
     * Adds a choice to the choice array of the MultipleChoice question.
     * @param text The text of the choice to be added
     * @param isActive Indicates if the choice should be active
     * @param isAnswer Indicates if the choice is the answer of the question
     */
    public void addChoice(String text, boolean isActive, boolean isAnswer);
}
