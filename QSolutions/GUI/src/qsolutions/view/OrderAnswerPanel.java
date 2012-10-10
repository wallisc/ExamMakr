package qsolutions.view;

import java.util.Collections;
import java.util.LinkedList;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import qsolutions.api.OrderApi;
import qsolutions.api.QuestionApi;

/**
 * Answer panel for order questions
 */
public class OrderAnswerPanel extends VariableNumFieldsPanel
{
    private ButtonGroup radioGroup;

    /**
     * Instantiates a new OrderAnswerPanel
     * @param view The view of the current question type (MatchingView)
     */
    public OrderAnswerPanel(DocumentItemView view)
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
        return new OrderChoicePanel(vnfp, ndx);
    }

    /**
     * Add a radio button to the radio group associated with this panel
     * @param radioButton  
     */
    protected void addToRadioGroup(JRadioButton radioButton)
    {
        // If the radio group has not been created yet, create it
        if (radioGroup == null)
        {
            radioGroup = new ButtonGroup();
        }
        radioGroup.add(radioButton);
    }

    /**
     * Add the choices in this panel to the given question
     * @param question Question that will have choices added to
     */
    @Override
    protected void addChoicesToQuestion(QuestionApi question)
    {
        OrderApi order = (OrderApi)question;
        sortByStatus();

        // For each choice in the panel, add the choice to the question
        for (int ndx = 0; ndx < choicePanels.size(); ndx++)
        {
            OrderChoicePanel ocp = (OrderChoicePanel)choicePanels.get(ndx);
            order.addOrderChoice(ocp.getText(), ocp.isActive());
            order.setDisplayIndex(ndx, ocp.getPosition());
        }

        // Set the positions of all the new panels
        // What is this actually doing?
        for (int ndx = 0; ndx < choicePanels.size(); ndx++)
        {
            OrderChoicePanel oap =
                    (OrderChoicePanel)choicePanels.get(ndx);
            oap.setPosition(ndx);
        }
    }
    
    /**
     * Fill the fields in this panel with the given question
     * @param question Question whose fields will be copied from
     */
    protected void fillFields(QuestionApi question)
    {
        OrderApi order = (OrderApi)question;
        removeAllChoices();

        // For each pair, copy the data from the pair into the question
        for (int count = 0; count < order.getNumOrders(); count++)
        {
            addChoice();
            OrderChoicePanel ocp = 
                    (OrderChoicePanel)choicePanels.get(count);
            ocp.setText(order.getOrderText(count));
            ocp.setActive(order.isActive(count));
            ocp.setPosition(order.getDisplayIndex(count));

            // If the current choice is indexable, set the position label
            if (ocp.isIndexable())
            {
                ocp.setPositionLabel(order.getDisplayIndex(count)+1);
            }
            ocp.setPosition(order.getDisplayIndex(count));
            reorderLeftLabels();
        }
    }

    /**
     * Remove a display index at the given index
     * @param ndx Index of the item to have their display index removed
     */
    public void removeIndex(int ndx)
    {
        int curr = ((OrderChoicePanel)choicePanels.get(ndx)).getPosition();

        // Loop through all the choices
        for (int count = 0; count < choicePanels.size(); count++)
        {
            OrderChoicePanel ocp = ((OrderChoicePanel)choicePanels.get(count));

            // If the position is greater, decrement it
            if (ocp.getPosition() > curr)
            {
                ocp.setPosition(ocp.getPosition()-1); 

                // If it's indexable, update the position label
                if (ocp.isIndexable())
                {
                    ocp.setPositionLabel(ocp.getPosition()+1);
                }
            }
        }
        ((OrderChoicePanel)choicePanels.get(ndx)).hideLabels();
        ((OrderChoicePanel)choicePanels.get(ndx)).setPosition(
                choicePanels.size()-1);
        reorderLeftLabels();
    }

    /**
     * Reorder/renumber the indexes in the left label
     */
    public void reorderLeftLabels()
    {
        int tmp = 1;
        
        // Loop through all the choices
        for (int count = 0; count < choicePanels.size(); count++)
        {
            OrderChoicePanel ocp = ((OrderChoicePanel)choicePanels.get(count));

            // If it's indexible, set the left label
            if (ocp.isIndexable())
            {
                ocp.setLeftLabel("" + (tmp++));
            }
        }

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
            OrderChoicePanel ocp = ((OrderChoicePanel)choicePanels.get(count));
            int test = ocp.getPosition();

            // If the panel is indexable and has a lower position set it
            if ((!ocp.isIndexable() || count == ndx) && ocp.getPosition() < 
                    ((OrderChoicePanel)choicePanels.get(lowestInvalid)).getPosition())
            {
                lowestInvalid = count;
            }
        }
        tmp = ((OrderChoicePanel)choicePanels.get(lowestInvalid)).getPosition(); 
        ((OrderChoicePanel)choicePanels.get(lowestInvalid)).setPosition(
                ((OrderChoicePanel)choicePanels.get(ndx)).getPosition()); 
        ((OrderChoicePanel)choicePanels.get(ndx)).setPosition(tmp); 

        ((OrderChoicePanel)choicePanels.get(ndx)).showLabel();
        reorderLeftLabels();
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
            OrderChoicePanel ocp = ((OrderChoicePanel)choicePanels.get(ndx));

            // If it's indexable, increment the counter
            if (ocp.isIndexable())
            {
                ret += 1;
            }
        }
        return ret;
    }

    @Override
    protected void randomize()
    {
        LinkedList<Integer> positions = new LinkedList();
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
            OrderChoicePanel ocp = ((OrderChoicePanel)choicePanels.get(ndx));

            // If the choice is indexable, assign it a random index
            if (ocp.isIndexable())
            {
                ocp.setPosition(positions.get(count));
                ocp.setPositionLabel(positions.get(count)+1);
                count += 1;
            }
        }
    }
}