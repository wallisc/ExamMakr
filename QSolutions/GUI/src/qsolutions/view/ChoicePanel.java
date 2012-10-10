package qsolutions.view;

import javax.swing.JPanel;

/**
 * Choice panel interface. For use with VariableNumFieldsAnswerPanel.
 */
public abstract class ChoicePanel extends JPanel
{
    protected int choiceIndex;
    private VariableNumFieldsPanel vnf;

    /**
     * Whether the choice panel is active
     * @return The choice panels active state
     */
    public abstract boolean isActive();

    /**
     * Whether the contents of the choice panel are empty
     * @return Whether the ChoicePanel is empty
     */
    public abstract boolean isEmpty();
    
    /**
     * Creates a new instance of a ChoicePanel
     * @param vnf reference to the parent panel
     * @param index index this choice panel will be at
     */
    public ChoicePanel(VariableNumFieldsPanel vnf, int index)
    {
        this.vnf = vnf;
        this.choiceIndex = index;
    }

    /**
     * Deletes this choice panel from the VariableNumFieldsPanel
     */
    public void delete()
    {
        vnf.removeChoice(this.choiceIndex);
    }

    /**
     * Sets the index of this choices
     * @param ndx Index to be set
     */
    public void setChoiceIndex(int ndx)
    {  
        this.choiceIndex = ndx;
    }
}
