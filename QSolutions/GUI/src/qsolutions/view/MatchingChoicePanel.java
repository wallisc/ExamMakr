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
 * Choice panel for the matching questions
 */
public class MatchingChoicePanel extends ChoicePanel implements KeyListener, 
        FocusListener
{
    private MatchingAnswerPanel parentPanel;
    private int position;
    private static final int kBufferHeight = 30;
    
    /**
     * Creates new form MultipleChoiceChoicePanel
     * @param vnf Reference to the parent panel
     * @param index Index of this choice panel
     */
    public MatchingChoicePanel(VariableNumFieldsPanel vnf, int index)
    {
        super(vnf, index);
        initComponents();
        parentPanel = (MatchingAnswerPanel)vnf;
        leftAnswer.setFocusTraversalKeys(
                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        leftAnswer.setFocusTraversalKeys(
                KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        rightAnswer.setFocusTraversalKeys(
                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        rightAnswer.setFocusTraversalKeys(
                KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        leftAnswer.addKeyListener(this);
        leftAnswer.setEditorKit(new HTMLEditorKit());
        leftAnswer.setTransferHandler(new JTextPaneTransferHandler());
        rightAnswer.addKeyListener(this);
        rightAnswer.setEditorKit(new HTMLEditorKit());
        rightAnswer.setTransferHandler(new JTextPaneTransferHandler());
        leftAnswer.addFocusListener(this);
        rightAnswer.addFocusListener(this);
        position = index;
    }

    /**
     * Get the text in the left field
     * @return Text in the left field
     */
    public String getLeftText() 
    {
        return leftAnswer.getText();
    }

    /**
     * Get the text in the right field
     * @return Text in the right field
     */
    public String getRightText() 
    {
        return rightAnswer.getText();
    }

    /**
     * Get whether the choice is active
     * @return Whether the choice is active
     */
    @Override
    public boolean isActive()
    {
        return choiceCheckBox.isSelected();
    }

    /**
     * Set the text in the left field
     * @param text Text to be set
     */
    public void setLeftText(String text)
    {
        leftAnswer.setText(text);
    }

    /**
     * Set the text in the right field
     * @param text Text to be set
     */
    public void setRightText(String text)
    {
        rightAnswer.setText(text);
    }

    /**
     * Sets the choice to be active or not
     * @param active Whether the choice should be active or not
     */
    public void setActive(boolean active)
    {
        choiceCheckBox.setSelected(active);
    }
    
    /**
     * Sets the display position of this choice
     * @param pos Display position of this choice
     */
    public void setPosition(int pos)
    {
        position = pos;
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
     * Get the display position of this choice
     * @return display position of this choice
     */
    public int getPosition()
    {
        return position;
    }

    /**
     * Hide the position label
     */
    public void hideLabel()
    {
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
     * Return whether this item is indexable
     * @return whether this item is indexable
     */
    public boolean isIndexable()
    {
        return isActive() && !Lookup.getDefault().lookup(DriverApi.class)
                .compareExtractedStrings(leftAnswer.getText(), "") 
                && !Lookup.getDefault().lookup(DriverApi.class)
                .compareExtractedStrings(rightAnswer.getText(), "");
    }

    /**
     * Whether the choice panel is empty
     * @return Whether the choice panel is empty
     */
    @Override
    public boolean isEmpty()
    {
        return Lookup.getDefault().lookup(
                DriverApi.class).compareExtractedStrings(
                leftAnswer.getText(), "") ||
                Lookup.getDefault().lookup(
                DriverApi.class).compareExtractedStrings(
                rightAnswer.getText(), "");
    }

    /**
     * Modifies the display index
     */
    private void modifyIndex()
    {
        // Make sure the item hasn't been deleted
        if (parentPanel.getNumChoices() > choiceIndex)
        {
            // If it's not indexable
            if (!isIndexable())
            {
                parentPanel.removeIndex(choiceIndex);
            }
            // Add it if there's not a current label, and is indexable
            else if (positionLabel.getText().isEmpty() && isIndexable())
            {
                parentPanel.addIndex(choiceIndex);
            }
        }
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
    // GEN-BEGIN
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        choiceCheckBox = new javax.swing.JCheckBox();
        deleteButton = new javax.swing.JButton();
        positionLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        leftAnswer = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        rightAnswer = new javax.swing.JTextPane();

        choiceCheckBox.setSelected(true);
        choiceCheckBox.setText(org.openide.util.NbBundle.getMessage(MatchingChoicePanel.class, "MatchingChoicePanel.choiceCheckBox.text")); // NOI18N
        choiceCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(MatchingChoicePanel.class, "MatchingChoicePanel.choiceCheckBox.toolTipText")); // NOI18N
        choiceCheckBox.setFocusable(false);
        choiceCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choiceCheckBoxActionPerformed(evt);
            }
        });

        deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete.png"))); // NOI18N
        deleteButton.setText(org.openide.util.NbBundle.getMessage(MatchingChoicePanel.class, "MatchingChoicePanel.deleteButton.text")); // NOI18N
        deleteButton.setToolTipText(org.openide.util.NbBundle.getMessage(MatchingChoicePanel.class, "MatchingChoicePanel.deleteButton.toolTipText")); // NOI18N
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        positionLabel.setText(org.openide.util.NbBundle.getMessage(MatchingChoicePanel.class, "MatchingChoicePanel.positionLabel.text")); // NOI18N
        positionLabel.setToolTipText(org.openide.util.NbBundle.getMessage(MatchingChoicePanel.class, "MatchingChoicePanel.positionLabel.toolTipText")); // NOI18N

        leftAnswer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                leftAnswerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                leftAnswerFocusLost(evt);
            }
        });
        leftAnswer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                leftAnswerKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(leftAnswer);

        rightAnswer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                rightAnswerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                rightAnswerFocusLost(evt);
            }
        });
        rightAnswer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rightAnswerKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(rightAnswer);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(choiceCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(positionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteButton))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(positionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(choiceCheckBox)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
            .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // GEN-END

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_deleteButtonActionPerformed
    {//GEN-HEADEREND:event_deleteButtonActionPerformed
        parentPanel.removeIndex(choiceIndex);
        this.delete();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void choiceCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_choiceCheckBoxActionPerformed
    {//GEN-HEADEREND:event_choiceCheckBoxActionPerformed
        modifyIndex();
    }//GEN-LAST:event_choiceCheckBoxActionPerformed

    private void leftAnswerFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_leftAnswerFocusLost
    {//GEN-HEADEREND:event_leftAnswerFocusLost
        modifyIndex();
    }//GEN-LAST:event_leftAnswerFocusLost

    private void rightAnswerFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_rightAnswerFocusLost
    {//GEN-HEADEREND:event_rightAnswerFocusLost
        modifyIndex();
    }//GEN-LAST:event_rightAnswerFocusLost

    private void leftAnswerFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_leftAnswerFocusGained
    {//GEN-HEADEREND:event_leftAnswerFocusGained
        leftAnswer.selectAll();
    }//GEN-LAST:event_leftAnswerFocusGained

    private void rightAnswerFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_rightAnswerFocusGained
    {//GEN-HEADEREND:event_rightAnswerFocusGained
        rightAnswer.selectAll();
    }//GEN-LAST:event_rightAnswerFocusGained

    private void rightAnswerKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_rightAnswerKeyPressed
    {//GEN-HEADEREND:event_rightAnswerKeyPressed
        // If the user presses enter, ignore the keypress
        if (evt.getKeyChar()==KeyEvent.VK_ENTER)
        {
            evt.consume();
        }
    }//GEN-LAST:event_rightAnswerKeyPressed

    private void leftAnswerKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_leftAnswerKeyPressed
    {//GEN-HEADEREND:event_leftAnswerKeyPressed
        // If the user presses enter, ignore the keypress
        if (evt.getKeyChar()==KeyEvent.VK_ENTER)
        {
            evt.consume();
        }
    }//GEN-LAST:event_leftAnswerKeyPressed

    /**
     * Called if some component gained keyboard focus (whether it be from 
     * keyboard tabbing or mouse click)
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
    private javax.swing.JCheckBox choiceCheckBox;
    private javax.swing.JButton deleteButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane leftAnswer;
    private javax.swing.JLabel positionLabel;
    private javax.swing.JTextPane rightAnswer;
    // End of variables declaration//GEN-END:variables
}
