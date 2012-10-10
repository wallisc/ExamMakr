package qsolutions.view;

import java.util.Collections;
import java.util.LinkedList;
import javax.swing.ButtonGroup;
import qsolutions.api.MatchingApi;
import qsolutions.api.QuestionApi;

/**
 * The answer panel associated with a Matching Question
 */
public class MatchingAnswerPanel extends VariableNumFieldsPanel
{
    private ButtonGroup radioGroup;

    /**
     * Constructor for a MatchingAnswerPanel
     * @param view The view of the current question type (MatchingView)
     */
    public MatchingAnswerPanel(DocumentItemView view)
    {
        super();
        contextPanel = view;
    }

    /**
     * Creates a new choice panel associated with this answer panel
     * @param vnfp Reference to the parent for accessor methods
     * @param ndx Index of the choice panel 
     * @return A new choice panel associated with this answer panel
     */
    @Override
    protected ChoicePanel newChoicePanel(DocumentItemView view, 
        VariableNumFieldsPanel vnfp, int ndx)
    {
        contextPanel = view;
        return new MatchingChoicePanel(vnfp, ndx);
    }

    /**
     * Add the choices in this panel to the given question
     * @param question Question that will have choices added to
     */
    @Override
    protected void addChoicesToQuestion(QuestionApi question)
    {
        MatchingApi match = (MatchingApi)question;
        sortByStatus();
        
        // For each choice in the panel, add the choice to the question
        for (int ndx = 0; ndx < choicePanels.size(); ndx++)
        {
            MatchingChoicePanel mcp =
                    (MatchingChoicePanel)choicePanels.get(ndx);
            match.addMatchingPair(mcp.getLeftText(), mcp.getRightText(), 
                    mcp.isActive());
            match.setDisplayIndex(ndx, mcp.getPosition());
        }
        // Set the positions of all the new panels
        // What is this actually doing?
        for (int ndx = 0; ndx < choicePanels.size(); ndx++)
        {
            MatchingChoicePanel mcp =
                    (MatchingChoicePanel)choicePanels.get(ndx);
            mcp.setPosition(ndx);
        }
    }
    
    /**
     * Fill the fields in this panel with the given question
     * @param question Question whose fields will be copied from
     */
    protected void fillFields(QuestionApi question)
    {
        MatchingApi match = (MatchingApi)question;
        removeAllChoices();

        // For each pair, copy the data from the pair into the question
        for (int count = 0; count < match.getNumPairs(); count++)
        {
            addChoice();
            MatchingChoicePanel mcp = 
                    (MatchingChoicePanel)choicePanels.get(count);
            mcp.setLeftText(match.getLeft(count));
            mcp.setRightText(match.getRight(count));
            mcp.setActive(match.isActive(count));

            // If the current choice is indexable, set the position label
            if (mcp.isIndexable())
            {
                mcp.setPositionLabel(match.getDisplayIndex(count)+1);
            }
            mcp.setPosition(match.getDisplayIndex(count));
        }
    }

    /**
     * Get the number of indexable choices
     * @return Number of indexable choices
     */
    protected int getNumIndexable()
    {
        int ret = 0;

        // Loop through all the choices and count all the indexable
        for (Integer ndx = 0; ndx < choicePanels.size(); ndx++) 
        {
            MatchingChoicePanel mcp = ((MatchingChoicePanel)choicePanels.get(ndx));

            // If it's indexable, increment the counter
            if (mcp.isIndexable())
            {
                ret += 1;
            }
        }
        return ret;
    }

    /**
     * Randomize all the choices in this answer panel
     */
    @Override
    protected void randomize()
    {
        LinkedList<Integer> positions = new LinkedList<Integer>();
        int count = 0;

        // Populate a list with the number of ints for indexable items
        for (Integer ndx = 0; ndx < getNumIndexable(); ndx++) 
        {
            positions.add(ndx);
        }
        Collections.shuffle(positions);

        // Assign the ints from above into the indexable items
        for (Integer ndx = 0; ndx < choicePanels.size(); ndx++) 
        {
            MatchingChoicePanel mcp = ((MatchingChoicePanel)choicePanels.get(ndx));

            // If the choice is indexable, assign it a random index
            if (mcp.isIndexable())
            {
                mcp.setPosition(positions.get(count));
                mcp.setPositionLabel(positions.get(count)+1);
                count += 1;
            }
        }
    }

    /**
     * Remove a display index at the given index
     * @param ndx Index of the item to have their display index removed
     */
    public void removeIndex(int ndx)
    {
        int curr = ((MatchingChoicePanel)choicePanels.get(ndx)).getPosition();

        // Loop through all the choices
        for (int count = 0; count < choicePanels.size(); count++)
        {
            MatchingChoicePanel mcp = ((MatchingChoicePanel)choicePanels.get(count));

            // If the position is greater, decrement it
            if (mcp.getPosition() > curr)
            {
                mcp.setPosition(mcp.getPosition()-1); 

                // If it's indexable, update the position label
                if (mcp.isIndexable())
                {
                    mcp.setPositionLabel(mcp.getPosition()+1);
                }
            }
        }
        ((MatchingChoicePanel)choicePanels.get(ndx)).hideLabel();
        ((MatchingChoicePanel)choicePanels.get(ndx)).setPosition(
                choicePanels.size()-1);
    }

    /**
     * Adds the display index to the correct position
     * @param ndx Index of the choice your trying to add
     */
    public void addIndex(int ndx)
    {
        int lowestInvalid = ndx, tmp;

        // Loop through all the choices
        for (int count = 0; count < choicePanels.size(); count++)
        {
            MatchingChoicePanel mcp = ((MatchingChoicePanel)choicePanels.get(count));
            int test = mcp.getPosition();

            // If the panel is indexable and has a lower position set it
            if ((!mcp.isIndexable() || count == ndx) && mcp.getPosition() < 
                    ((MatchingChoicePanel)choicePanels.get(lowestInvalid)).getPosition())
            {
                lowestInvalid = count;
            }
        }
        tmp = ((MatchingChoicePanel)choicePanels.get(lowestInvalid)).getPosition(); 
        ((MatchingChoicePanel)choicePanels.get(lowestInvalid)).setPosition(
                ((MatchingChoicePanel)choicePanels.get(ndx)).getPosition()); 
        ((MatchingChoicePanel)choicePanels.get(ndx)).setPosition(tmp); 

        ((MatchingChoicePanel)choicePanels.get(ndx)).showLabel();
    }
}
