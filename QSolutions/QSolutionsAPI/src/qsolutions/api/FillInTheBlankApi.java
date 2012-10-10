package qsolutions.api;

/**
 * The FillInTheBlank interface for use with LookUp. Only contains methods 
 * needed externally.
 * @author Ryan Dollahon
 */
public interface FillInTheBlankApi extends QuestionApi
{
    /**
     * Generates a new instance of a FillInTheBlank question.
     * @return the new FillInTheBlank
     */
    public FillInTheBlankApi newItem();
}
