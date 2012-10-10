package qsolutions.view;

import java.awt.GridLayout;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import qsolutions.api.QuestionApi;

/**
 * An answer panel that consists of a varying number of answer choice panel
 */
public abstract class VariableNumFieldsPanel extends javax.swing.JPanel
{
    private static final int kMaxChoices = 15;
    private static final int kSpaceBetweenChoices = 10;
    protected DocumentItemView contextPanel;
    
    protected LinkedList<ChoicePanel> choicePanels;
    protected abstract ChoicePanel newChoicePanel(DocumentItemView view, 
            VariableNumFieldsPanel vnfp, int size);
    protected abstract void addChoicesToQuestion(QuestionApi question);
    protected abstract void randomize();

    /**
     * Creates new form VariableNumFieldsPanel
     */
    public VariableNumFieldsPanel()
    {
        initComponents();
        choicePanels = new LinkedList<ChoicePanel>();
        choicesJPanel.setLayout(new GridLayout(1, 1, 0, 
                kSpaceBetweenChoices));
    }
    
    /**
     * Adds a new answer choice to the answer panel
     */
    public final void addChoice()
    {
        ChoicePanel cp = this.newChoicePanel(contextPanel, this, 
                choicePanels.size());
        choicePanels.add(cp);
        this.resizeGridLayout(choicePanels.size());
        choicesJPanel.add(cp);
        choicesJPanel.updateUI();

        // If they reach the max limit of choices disable the add button
        if (choicePanels.size() == kMaxChoices)
        {
            plusButton.setEnabled(false);
        }
    }
    
    /**
     * Removes an answer choice at the given index from the answer panel
     * @param ndx Index of the item to remove
     */
    public final void removeChoice(int ndx)
    {
        choicePanels.remove(ndx);
        choicesJPanel.remove(ndx);
        choicesJPanel.updateUI();

        this.resizeGridLayout(choicePanels.size());
        this.reindexChoicePanels();
        
        // If they remove a choice make sure to reenable the add button
        if (!plusButton.isEnabled())
        {
            plusButton.setEnabled(true);
        }
    }

    /**
     * Resizes the grid layout to have the specified number of rows
     * @param rows Number of rows the grid layout should have
     */
    private void resizeGridLayout(int rows) 
    {
        choicesJPanel.setLayout(new GridLayout(rows, 1, 0,
                kSpaceBetweenChoices));
    }

    /**
     * Renumbers the indexes of each choice panel
     */
    private void reindexChoicePanels()
    {
        // Loop through each of the panels and reindex
        for (int ndx = 0; ndx < choicePanels.size(); ndx++)
        {
            choicePanels.get(ndx).setChoiceIndex(ndx);
        }
    }

    /**
     * Remove all the choice panels
     */
    protected void removeAllChoices()
    {
        choicePanels = new LinkedList<ChoicePanel>();
        choicesJPanel.removeAll();
        choicesJPanel.updateUI();
    }

    /**
     * Sort the choices by their status
     */
    protected void sortByStatus()
    {
        Collections.sort(choicePanels, new Comparator<ChoicePanel>() {
            @Override
            public int compare(ChoicePanel a, ChoicePanel b)
            {
                // Make the active appear higher
                if (b.isActive() && !a.isActive())
                {
                    return 1;
                }
                // If they're both (in)active don't sort
                else if (b.isActive() == a.isActive())
                {
                    return 0;
                }
                return -1;
            } 
        });
        Collections.sort(choicePanels, new Comparator<ChoicePanel>() {
            @Override
            public int compare(ChoicePanel a, ChoicePanel b)
            {
                // Make the nonempty fields appear higher
                if (b.isEmpty() && !a.isEmpty())
                {
                    return -1;
                }
                // If they're both full/empty, don't sort
                else if (b.isEmpty() == a.isEmpty())
                {
                    return 0;
                }
                return 1;
            } 
        });
    }

    /**
     * Get the number of choices in this answer panel
     * @return Number of choices in this answer panel
     */
    public int getNumChoices()
    {
        return choicePanels.size();
    }
    
    /**
     * Clicks the bold button when a specific keystroke is performed.
     */
    public void boldAction()
    {
        contextPanel.boldAction();
    }
    
    /**
     * Clicks the italic button when a specific keystroke is performed.
     */
    public void italicAction()
    {
        contextPanel.italicAction();
    }
    
    /**
     * Clicks the underline button when a specific keystroke is performed.
     */
    public void underlineAction()
    {
        contextPanel.underlineAction();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    //GEN-BEGIN
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        randomizeButton = new javax.swing.JButton();
        plusButton = new javax.swing.JButton();
        choicesJPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        randomizeButton.setText(org.openide.util.NbBundle.getMessage(VariableNumFieldsPanel.class, "VariableNumFieldsPanel.randomizeButton.text")); // NOI18N
        randomizeButton.setFocusable(false);
        randomizeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomizeButtonActionPerformed(evt);
            }
        });

        plusButton.setText(org.openide.util.NbBundle.getMessage(VariableNumFieldsPanel.class, "VariableNumFieldsPanel.plusButton.text")); // NOI18N
        plusButton.setFocusable(false);
        plusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plusButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout choicesJPanelLayout = new javax.swing.GroupLayout(choicesJPanel);
        choicesJPanel.setLayout(choicesJPanelLayout);
        choicesJPanelLayout.setHorizontalGroup(
            choicesJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 428, Short.MAX_VALUE)
        );
        choicesJPanelLayout.setVerticalGroup(
            choicesJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel1.setText(org.openide.util.NbBundle.getMessage(VariableNumFieldsPanel.class, "VariableNumFieldsPanel.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 227, Short.MAX_VALUE)
                .addComponent(randomizeButton)
                .addGap(18, 18, 18)
                .addComponent(plusButton))
            .addComponent(choicesJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(randomizeButton)
                    .addComponent(plusButton)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(choicesJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    //GEN-END
    private void plusButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_plusButtonActionPerformed
    {//GEN-HEADEREND:event_plusButtonActionPerformed
        this.addChoice();
    }//GEN-LAST:event_plusButtonActionPerformed

    private void randomizeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_randomizeButtonActionPerformed
    {//GEN-HEADEREND:event_randomizeButtonActionPerformed
        choicesJPanel.removeAll();
        this.randomize(); 
        this.sortByStatus();
        // Re-add elements after randomize
        for (int choice = 0; choice < choicePanels.size(); choice++)
        {
            choicesJPanel.add(choicePanels.get(choice));
        }
        this.reindexChoicePanels();
        choicesJPanel.updateUI();
    }//GEN-LAST:event_randomizeButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel choicesJPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton plusButton;
    private javax.swing.JButton randomizeButton;
    // End of variables declaration//GEN-END:variables
}