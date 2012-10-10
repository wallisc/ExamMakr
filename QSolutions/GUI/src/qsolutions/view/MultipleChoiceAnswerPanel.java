package qsolutions.view;

import java.util.Collections;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import qsolutions.api.MultipleChoiceApi;
import qsolutions.api.QuestionApi;

/**
 * The answer panel associated with a multiple choice questions
 */
public class MultipleChoiceAnswerPanel extends VariableNumFieldsPanel
{
    private ButtonGroup radioGroup;

    /**
     * Instantiates a new MultipleChoiceAnswerPanel
     * @param view The view of the current question type (MatchingView)
     */
    public MultipleChoiceAnswerPanel(DocumentItemView view)
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
        return new MultipleChoiceChoicePanel(vnfp, ndx);
    }

    /**
     * Adds the give radio button to the radio group
     * @param radioButton 
     */
    protected void addToRadioGroup(JRadioButton radioButton)
    {
        // If the radio group has not been created, create it
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
        MultipleChoiceApi mc = (MultipleChoiceApi)question;
        mc.clearChoices();
        sortByStatus();

        // For each choice in the panel, add the choice to the question
        for (int ndx = 0; ndx < choicePanels.size(); ndx++)
        {
            MultipleChoiceChoicePanel mccp =
                    (MultipleChoiceChoicePanel)choicePanels.get(ndx);
            mc.addChoice(mccp.getText(), mccp.isActive(),
                    mccp.isAnswer());
        }
    }
    
    /**
     * Fill the fields in this panel with the given question
     * @param question Question whose fields will be copied from
     */
    protected void fillFields(QuestionApi question)
    {
        MultipleChoiceApi mc = (MultipleChoiceApi)question;
        removeAllChoices();

        // For each pair, copy the data from the pair into the question
        for (int count = 0; count < mc.getNumChoices(); count++)
        {
            addChoice();
            MultipleChoiceChoicePanel mccp = 
                    (MultipleChoiceChoicePanel)choicePanels.get(count);
            mccp.setText(mc.getChoiceText(count));
            mccp.setActive(mc.getChoiceActive(count));

            // If the answer index is the current index, set answer
            if (mc.getAnswerNdx() == count)
            {
                mccp.setAnswer();
            }
        }
    }

    /**
     * Randomize all the choices in this answer panel
     */
    @Override
    protected void randomize()
    {
        Collections.shuffle(choicePanels);
    }
}
