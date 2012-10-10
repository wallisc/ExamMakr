package qsolutions.exam;

import qsolutions.api.DocumentItemApi;
import qsolutions.api.QuestionApi;

/**
 * Represents a question in the exam.
 */
public abstract class Question extends DocumentItem implements QuestionApi
{
    private int marks;
    private String answer;
    private int questionNum;
    private String category;
    private String level;
    private int actualQNum;
    //Used for passing arguments to cleanHTML for answer fields
    protected final static int kAnswerCompareInt = -2;
    
    /**
     * Inherits the the question status and text from the document item.
     */
    public Question()
    {
        super();
        setText("");
        answer = "";
        category = "";
        marks = 1;
        level = "";
    }

    /**
     * Copy properties from this question to another question
     * @param copy Where the properties are to be copied to
     */
    protected void copyData(Question copy)
    {

        super.copyData(copy);
        copy.marks = this.marks;
        copy.answer = this.answer;
        copy.questionNum = this.questionNum;
        copy.category = this.category;
        copy.level = this.level;
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
        Question quest;

        // Make sure the other object is a Question
        if (!(obj instanceof Question))
        {
            return false;
        }

        quest = (Question) obj;
        
        // Ensure the parent's equals method is true
        if (!super.equals(quest))
        {
            return false;
        }
        /*|| !(new Driver().compareExtractedStrings(
                this.answer, quest.answer))*/
        // Make sure the fields of the other Question are the same 
        if ( this.marks != quest.marks
                || !(new Driver().compareExtractedStrings(
                    this.answer, quest.answer))
                || !this.category.equals(quest.category)
                || !this.level.equals(quest.level))
        {
            return false;
        }
        return true;
    }

    /**
     * Gets the answer from the current question.
     *
     * @return answer of the question in a string
     */
    @Override
    public String getAnswer()
    {
        return answer;
    }

    /**
     * Sets the question answer.
     *
     * @param answer to the current question in a string
     */
    @Override
    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    /**
     * Get the marks from current question.
     *
     * @return marks of the current question in an integer
     */
    @Override
    public int getMarks()
    {
        return marks;
    }

    /**
     * Assigns a value of marks to the current question.
     *
     * @param marks the value for the marks of the current question
     */
    @Override
    public void setMarks(int marks)
    {
        this.marks = marks;
    }

    /**
     * Assigns an integer value to the question number.
     *
     * @param num an assigned integer value for the current question number
     */
    @Override
    public void setQNum(int num)
    {
        questionNum = num;
    }

    /**
     * Gets the question number from the current question.
     *
     * @return the question number of the current question in an integer
     */
    @Override
    public int getQNum()
    {
        return questionNum;
    }

    /**
     * Sets the question category of the current question using the parameter
     * string.
     *
     * @param category string to set the question category
     */
    @Override
    public void setCategory(String category)
    {
        this.category = category;
    }

    /**
     * gets the question category of the current question
     *
     * @return the category of the current question
     */
    @Override
    public String getCategory()
    {
        return category;
    }

    /**
     * Set the question level to the parameter string.
     *
     * @param level the question level of the current question
     */
    @Override
    public void setLevel(String level)
    {
        this.level = level;
    }

    /**
     * Gets the question level of the current question.
     *
     * @return level the question level of the current question
     */
    @Override
    public String getLevel()
    {
        return level;
    }
    
    /**
     * Set the actual question number to the parameter string.
     *
     * @param num the question number of the current question
     */
    public void setActualQNum(int num)
    {
        this.actualQNum = num;
    }

    /**
     * Gets the actual question number of the current question.
     *
     * @return the actual question number of the current question
     */
    public int getActualQNum()
    {
        return actualQNum;
    }
}
