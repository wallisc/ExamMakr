package qsolutions.view;

import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.text.html.HTMLEditorKit;
import org.openide.util.Lookup;
import qsolutions.api.DriverApi;

/**
 * Choice panel for Order questions
 */
public class OrderChoicePanel extends ChoicePanel implements KeyListener,
        FocusListener
{
    private OrderAnswerPanel parentPanel; 
    private int position;
    private static final int kBufferHeight = 30;
    
    /**
     * Creates new form OrderChoicePanel
     * @param vnf The panel associated with this specific order panel
     * @param index the index of the panel amongst the other order panels 
     * for this question
     */
    public OrderChoicePanel(VariableNumFieldsPanel vnf, int index)
    {
        super(vnf, index);
        initComponents();
        parentPanel = (OrderAnswerPanel)vnf;
        answer.setFocusTraversalKeys(
                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        answer.setFocusTraversalKeys(
                KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        position = index;
        answer.addKeyListener(this);
        answer.setEditorKit(new HTMLEditorKit());
        answer.setTransferHandler(new JTextPaneTransferHandler());
        answer.addFocusListener(this);
    }

    /**
     * Set the position of the choice panel
     * @param index Index of the position to be set
     */
    public void setPosition(int index)
    {
        position = index;
    }

    /**
     * Get the display position of this choice
     * @return display position of this choice
     */
    public int getPosition()
    {
        return position;
    }

    /**
     * Get the text in this choice panel
     * @return Text in the choice panel
     */
    public String getText() 
    {
        return answer.getText();
    }

    /**
     * Whether the multiple choice question choice is active
     * @return if the choice is active
     */
    public boolean isActive()
    {
        return choiceCheckBox.isSelected();
    }

    /**
     * Sets the text of this choice
     * @param text Text to be set in the choice
     */
    public void setText(String text)
    {
        answer.setText(text);
    }

    /**
     * Sets the choice to be active or not
     * @param active Whether it's to be active or not
     */
    public void setActive(boolean active)
    {
        choiceCheckBox.setSelected(active);
    }

    /**
     * Return whether this item is indexable
     * @return whether this item is indexable
     */
    public boolean isIndexable()
    {
        return isActive() && !Lookup.getDefault().lookup(
                DriverApi.class).compareExtractedStrings(answer.getText(), 
                "");
    }

    /**
     * Sets the display position label
     * @param pos Position to be set in the label
     */
    public void setPositionLabel(int pos)
    {
        positionLabel.setText(pos + "");
    }

    /**
     * Hide the position label
     */
    public void hideLabels()
    {
        leftLabel.setText("");
        positionLabel.setText("");
    }

    /**
     * Show the position label
     */
    public void showLabel()
    {
        positionLabel.setText((position+1) + "");
    }

    /**
     * Sets the text of the left label
     * @param text Text to be assigned to the left label
     */
    public void setLeftLabel(String text)
    {
        leftLabel.setText(text);
    }

    /**
     * Modifies the display index
     */
    private void modifyIndex()
    {
        // Make sure the item hasn't been deleted
        if (parentPanel.getNumChoices() > choiceIndex)
        {
            // If it's not indexible, remove it
            if (!isIndexable())
            {
                parentPanel.removeIndex(choiceIndex);
            }
            // If there's no current labe, add the index
            else if (positionLabel.getText().isEmpty() && isIndexable())
            {
                parentPanel.addIndex(choiceIndex);
            }
        }
    }

    /**
     * Whether the choice panel is empty
     * @return Whether the choice panel is empty
     */
    @Override
    public boolean isEmpty()
    {
        return Lookup.getDefault().lookup(DriverApi.class)
                .compareExtractedStrings(answer.getText(), "");
    }
    
    /**
     * Unused keyListener method.
     * @param e the keyEvent that we ignore
     */
    public void keyReleased(KeyEvent e)
    {

    }
    
    /**
     * Unused keyListener method.
     * @param e the keyEvent that we ignore
     */
    public void keyTyped(KeyEvent e)
    {
        
    }
    
    /**
     * Determines which button corresponds with a provided hot-key event.
     * @param e the keyEvent generated by the user
     */
    public void keyPressed(KeyEvent e)
    {
        //If control b is pressed, click bold
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_B)
        {
            parentPanel.boldAction();
        }
        //If control i is pressed, click italics
        else if (e.isControlDown()&& e.getKeyCode() == KeyEvent.VK_I)
        {
            parentPanel.italicAction();
        }
        //If control u is pressed, click underline
        else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_U)
        {
            parentPanel.underlineAction();
        }
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

        choiceCheckBox = new javax.swing.JCheckBox();
        deleteButton = new javax.swing.JButton();
        leftLabel = new javax.swing.JLabel();
        positionLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        answer = new javax.swing.JTextPane();

        choiceCheckBox.setSelected(true);
        choiceCheckBox.setText(org.openide.util.NbBundle.getMessage(OrderChoicePanel.class, "OrderChoicePanel.choiceCheckBox.text")); // NOI18N
        choiceCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(OrderChoicePanel.class, "OrderChoicePanel.choiceCheckBox.toolTipText")); // NOI18N
        choiceCheckBox.setFocusable(false);
        choiceCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choiceCheckBoxActionPerformed(evt);
            }
        });

        deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete.png"))); // NOI18N
        deleteButton.setText(org.openide.util.NbBundle.getMessage(OrderChoicePanel.class, "OrderChoicePanel.deleteButton.text")); // NOI18N
        deleteButton.setToolTipText(org.openide.util.NbBundle.getMessage(OrderChoicePanel.class, "OrderChoicePanel.deleteButton.toolTipText")); // NOI18N
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        leftLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        leftLabel.setText(org.openide.util.NbBundle.getMessage(OrderChoicePanel.class, "OrderChoicePanel.leftLabel.text")); // NOI18N

        positionLabel.setText(org.openide.util.NbBundle.getMessage(OrderChoicePanel.class, "OrderChoicePanel.positionLabel.text")); // NOI18N
        positionLabel.setToolTipText(org.openide.util.NbBundle.getMessage(OrderChoicePanel.class, "OrderChoicePanel.positionLabel.toolTipText")); // NOI18N

        answer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                answerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                answerFocusLost(evt);
            }
        });
        answer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                answerKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(answer);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(choiceCheckBox)
                .addGap(26, 26, 26)
                .addComponent(leftLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addGap(28, 28, 28)
                .addComponent(positionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteButton))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(leftLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(positionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choiceCheckBox))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    //GEN-END
    
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_deleteButtonActionPerformed
    {//GEN-HEADEREND:event_deleteButtonActionPerformed
        parentPanel.removeIndex(choiceIndex);
        this.delete();
        parentPanel.reorderLeftLabels();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void choiceCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_choiceCheckBoxActionPerformed
    {//GEN-HEADEREND:event_choiceCheckBoxActionPerformed
        modifyIndex();
    }//GEN-LAST:event_choiceCheckBoxActionPerformed

    private void answerFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_answerFocusLost
    {//GEN-HEADEREND:event_answerFocusLost
        modifyIndex();
    }//GEN-LAST:event_answerFocusLost

    private void answerFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_answerFocusGained
    {//GEN-HEADEREND:event_answerFocusGained
        answer.selectAll();
    }//GEN-LAST:event_answerFocusGained

    private void answerKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_answerKeyPressed
    {//GEN-HEADEREND:event_answerKeyPressed
        // If the user presses enter, ignore the keypress
        if (evt.getKeyChar()==KeyEvent.VK_ENTER)
        {
            evt.consume();
        }
    }//GEN-LAST:event_answerKeyPressed


    
   /**
    * Called if some component gains keyboard focus (whether it be from 
    * keyboard tabbing or mouse click off the component)
    * @param ev the event generated from the focus gain, include the component
    * that got focused
    */
    @Override
    public void focusGained(FocusEvent ev)
    {
        Rectangle newFocusBox = ev.getComponent().getBounds();
        newFocusBox.y += kBufferHeight; 
        this.scrollRectToVisible(newFocusBox);
    }
    
   /**
    * Called if some component loses keyboard focus (whether it be from 
    * keyboard tabbing or mouse click off the component)
    * @param ev the event generated from the focus loss, include the component
    * that got focused
    */
    @Override
    public void focusLost(FocusEvent ev) 
    {
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane answer;
    private javax.swing.JCheckBox choiceCheckBox;
    private javax.swing.JButton deleteButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel leftLabel;
    private javax.swing.JLabel positionLabel;
    // End of variables declaration//GEN-END:variables
}
