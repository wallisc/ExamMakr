package qsolutions.view;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLEditorKit;
import org.openide.util.Lookup;
import org.openide.windows.WindowManager;
import qsolutions.api.ExamApi;

/**
 * The panel that contains options for changing the title group of an Exam.
 * @author Ryan Dollahon, Jake Tobin
 */
public class TitleGroupDesignPanel extends javax.swing.JPanel 
    implements KeyListener
{
    private static final String kBundleName = "qsolutions/view/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    private static final String kInvalidPrompt =
            "INVALID INPUT FOR FONT SIZE, PLEASE INPUT AN NUMBER";
    
    private Lookup.Result<ExamApi> result = null;
    private ExamApi exam;
    private JDialog parent;
    /**
     * Instantiates a TitleGroupDesignPanel in a JFrame referencing nExam
     * setting isWindow to isW.
     * @param nParent the dialog box this panel is pasted in
     */
    public TitleGroupDesignPanel(JDialog nParent)
    {
        initComponents();
        parent = nParent;
        // if the informaiton is valid fill the fields
        result = Lookup.getDefault().lookupResult(ExamApi.class);
        Collection<ExamApi> inst = (Collection<ExamApi>) result.allInstances();
        exam = inst.iterator().next();
        
        preparePanel(titleField);
        preparePanel(descriptionText);
        preparePanel(topLeftCell);
        preparePanel(topRightCell);
        preparePanel(bottomLeftCell);
        preparePanel(bottomRightCell);
        
        // Checks to see if the title group is empty
        /*if (exam.getTitleGroup().getTitle().equals("") || 
                exam.getTitleGroup().getDescription().equals("") ||
                exam.getTitleGroup().getTopLeftCell().equals("") || 
                exam.getTitleGroup().getTopRightCell().equals("") ||
                exam.getTitleGroup().getBottomLeftCell().equals("") ||
                exam.getTitleGroup().getBottomRightCell().equals("") || 
                exam.getTitleGroup().getShowTitleGroup())
        {*/
        fillFields();
        //}
        fontSize.setText("" + exam.getTitleGroup().getTitleGroupFontSize());
        titleFontSize.setText("" + exam.getTitleGroup().getTitleFontSize());
        prepareButtons();
    }
    
    private void preparePanel(JTextPane panel)
    {
        panel.setEditorKit(new HTMLEditorKit());
        panel.addKeyListener(this);
        panel.setTransferHandler(new JTextPaneTransferHandler());
        panel.setFocusTraversalKeys(
                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
    }
    
    /**
     * Prepares the buttons for their initial state in the GUI.
     */
    private void prepareButtons()
    {
        bold.setAction(new HTMLEditorKit.BoldAction());
        bold.setIcon(new ImageIcon(
                this.getClass().getClassLoader().getResource("img/bold.png")));
        bold.setText("");
        bold.setToolTipText(kBundle.getString("BOLD"));
        italics.setAction(new HTMLEditorKit.ItalicAction());
        italics.setIcon(new ImageIcon(
                this.getClass().getClassLoader().getResource(
                "img/italics.png")));
        italics.setText("");
        italics.setToolTipText(kBundle.getString("ITALICS"));
        underline.setAction(new HTMLEditorKit.UnderlineAction());
        underline.setIcon(new ImageIcon(
                this.getClass().getClassLoader().getResource(
                "img/underline.png")));
        underline.setText("");
        underline.setToolTipText(kBundle.getString("UNDERLINE"));
        monospace.setAction(new HTMLEditorKit.FontFamilyAction("Monospace",
                "monospace"));
        monospace.setIcon(new ImageIcon(
                this.getClass().getClassLoader().getResource(
                "img/monospace.png")));
        monospace.setText("");
        monospace.setToolTipText(kBundle.getString("SET HIGHLIGHTED TEXT TO "
                + "MONOSPACE FONT"));
        regular.setAction(new HTMLEditorKit.FontFamilyAction(
                "Plain", "Times New Roman"));
        regular.setText(kBundle.getString("PLAIN"));
        regular.setToolTipText(kBundle.getString("SET HIGHLIGHTED TEXT TO "
                + "PLAIN FONT"));
    }

    /**
     * Store the title group to the exam
     */
    public void addItem()
    {
        int instructionFontSizeInt = -1;
        int titleFontSizeInt = -1;
        exam.bulkUpdate(true);
        exam.getTitleGroup().setShowTitleGroup(showTitleGroup.isSelected());
        exam.getTitleGroup().setTitle(titleField.getText());
        exam.getTitleGroup().setDescription(descriptionText.getText());
        exam.getTitleGroup().setTopLeftCell(topLeftCell.getText());
        exam.getTitleGroup().setBottomLeftCell(bottomLeftCell.getText());
        exam.getTitleGroup().setTopRightCell(topRightCell.getText());
        exam.getTitleGroup().setBottomRightCell(bottomRightCell.getText());
        exam.getTitleGroup().setTitleGroupFontGlobal(globalToggle.isSelected());
        try
        {
            instructionFontSizeInt = Integer.parseInt(fontSize.getText());
            titleFontSizeInt = Integer.parseInt(titleFontSize.getText());
        }
        catch ( NumberFormatException e )
        {
            javax.swing.JOptionPane.showMessageDialog(
                    WindowManager.getDefault().findTopComponent(
                    "DocumentViewTopComponent"), 
                        kBundle.getString(kInvalidPrompt));
        }
        exam.getTitleGroup().setTitleGroupFontSize(instructionFontSizeInt);
        exam.getTitleGroup().setTitleFontSize(titleFontSizeInt);
        exam.bulkUpdate(false);
    }

    /**
     * Returns true if the title group has been edited, otherwise false
     *
     * @return true if the title group has been edited, otherwise false
     */
    protected boolean isDifferent()
    {
        return (!exam.getTitleGroup().getTitle().equals(
                    titleField.getText()) || 
                !exam.getTitleGroup().getDescription().equals(
                    descriptionText.getText()) ||
                !exam.getTitleGroup().getTopLeftCell().equals(
                    topLeftCell.getText()) || 
                !exam.getTitleGroup().getTopRightCell().equals(
                    topRightCell.getText()) ||
                !exam.getTitleGroup().getBottomLeftCell().equals(
                    bottomLeftCell.getText()) ||
                !exam.getTitleGroup().getBottomRightCell().equals(
                    bottomRightCell.getText()) ||
                !exam.getTitleGroup().getTitleGroupFontGlobal() == 
                    globalToggle.isSelected() ||
                !exam.getTitleGroup().getShowTitleGroup() == 
                    showTitleGroup.isSelected());
    }

    /**
     * Fills the GUI fields from the exam
     */
    protected void fillFields()
    {
        titleField.setText(exam.getTitleGroup().getTitle());
        descriptionText.setText(exam.getTitleGroup().getDescription());
        bottomRightCell.setText(exam.getTitleGroup().getBottomRightCell());
        showTitleGroup.setSelected(exam.getTitleGroup().getShowTitleGroup());
        globalToggle.setSelected(exam.getTitleGroup().getTitleGroupFontGlobal());
        topLeftCell.setText(exam.getTitleGroup().getTopLeftCell());
        topRightCell.setText(exam.getTitleGroup().getTopRightCell());
        bottomLeftCell.setText(exam.getTitleGroup().getBottomLeftCell());
    }

    /**
     * Returns a string representing the type
     *
     * @return String literal "Title Group"
     */
    public String getType()
    {
        return kBundle.getString("TITLE GROUP");
    }
    
    /**
     * Unused key release event method.
     * @param e the KeyEvent from the KeyListener
     */
    public void keyReleased(KeyEvent e)
    {

    }
    
    /**
     * Unused key typed event method.
     * @param e the KeyEvent from the KeyListener
     */
    public void keyTyped(KeyEvent e)
    {
        
    }
    
    
    /**
     * Determines which wysiwyg button should be pressed.
     * @param e the KeyEvent from the KeyListener
     */
    public void keyPressed(KeyEvent e)
    {
        //if control b is pressed
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_B)
        {
            bold.doClick();
        }
        //if control i is pressed
        else if (e.isControlDown()&& e.getKeyCode() == KeyEvent.VK_I)
        {
            italics.doClick();
        }
        //if control u is pressed
        else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_U)
        {
            underline.doClick();
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        showTitleGroup = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        fontSize = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        titleFontSize = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        applyButton = new javax.swing.JButton();
        globalToggle = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        bold = new javax.swing.JButton();
        italics = new javax.swing.JButton();
        underline = new javax.swing.JButton();
        monospace = new javax.swing.JButton();
        regular = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        titleField = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        descriptionText = new javax.swing.JTextPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        topLeftCell = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        topRightCell = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        bottomLeftCell = new javax.swing.JTextPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        bottomRightCell = new javax.swing.JTextPane();

        jLabel1.setText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.jLabel2.text")); // NOI18N

        okButton.setText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.okButton.text")); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        jLabel3.setText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.jLabel3.text")); // NOI18N

        jLabel4.setText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.jLabel4.text")); // NOI18N

        jLabel5.setText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.jLabel5.text")); // NOI18N

        showTitleGroup.setText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.showTitleGroup.text")); // NOI18N
        showTitleGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showTitleGroupActionPerformed(evt);
            }
        });

        jLabel6.setText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.jLabel6.text")); // NOI18N

        jLabel7.setText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.jLabel7.text")); // NOI18N

        fontSize.setText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.fontSize.text")); // NOI18N

        jLabel8.setText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.jLabel8.text")); // NOI18N

        titleFontSize.setText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.titleFontSize.text")); // NOI18N
        titleFontSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleFontSizeActionPerformed(evt);
            }
        });

        cancelButton.setText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.cancelButton.text")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        applyButton.setText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.applyButton.text")); // NOI18N
        applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButtonActionPerformed(evt);
            }
        });

        globalToggle.setText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.globalToggle.text")); // NOI18N
        globalToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                globalToggleActionPerformed(evt);
            }
        });

        jLabel9.setText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.jLabel9.text")); // NOI18N

        bold.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bold.png"))); // NOI18N
        bold.setToolTipText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.bold.toolTipText")); // NOI18N

        italics.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/italics.png"))); // NOI18N
        italics.setToolTipText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.italics.toolTipText")); // NOI18N

        underline.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/underline.png"))); // NOI18N
        underline.setToolTipText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.underline.toolTipText")); // NOI18N

        monospace.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/monospace.png"))); // NOI18N
        monospace.setToolTipText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.monospace.toolTipText")); // NOI18N

        regular.setText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.regular.text")); // NOI18N
        regular.setToolTipText(org.openide.util.NbBundle.getMessage(TitleGroupDesignPanel.class, "TitleGroupDesignPanel.regular.toolTipText")); // NOI18N

        jScrollPane2.setViewportView(titleField);

        jScrollPane3.setViewportView(descriptionText);

        jScrollPane1.setViewportView(topLeftCell);

        jScrollPane4.setViewportView(topRightCell);

        jScrollPane5.setViewportView(bottomLeftCell);

        jScrollPane6.setViewportView(bottomRightCell);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(layout.createSequentialGroup()
                                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(jLabel3)
                                            .add(jLabel4))
                                        .add(94, 94, 94))
                                    .add(layout.createSequentialGroup()
                                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                            .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane5)
                                            .add(jScrollPane1))
                                        .add(54, 54, 54)))
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(jLabel5)
                                    .add(jLabel6)
                                    .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .add(jScrollPane6)))
                            .add(layout.createSequentialGroup()
                                .add(jLabel7)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(fontSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(globalToggle)))
                        .addContainerGap())
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(okButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(applyButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cancelButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(41, 41, 41))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(jLabel1)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 189, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel8)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(titleFontSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jLabel2)
                            .add(showTitleGroup)
                            .add(jLabel9)
                            .add(layout.createSequentialGroup()
                                .add(bold, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(italics, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(underline, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(monospace)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(regular))
                            .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 384, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(showTitleGroup)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(bold)
                            .add(italics))
                        .add(underline, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(monospace)
                    .add(regular))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel8)
                        .add(titleFontSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jLabel1)
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(18, 18, 18)
                .add(jLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(fontSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(globalToggle))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(jLabel5))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jScrollPane4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(jLabel6))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jScrollPane6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jLabel9)
                .add(13, 13, 13)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(okButton)
                    .add(cancelButton)
                    .add(applyButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    //GEN-END
    /**
     * Saves title group appropriately
     *
     * @param evt unused
     */
    private void okButtonActionPerformed( java.awt.event.ActionEvent evt )//GEN-FIRST:event_okButtonActionPerformed
    {//GEN-HEADEREND:event_okButtonActionPerformed
        addItem();
        parent.dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    private void showTitleGroupActionPerformed( java.awt.event.ActionEvent evt )//GEN-FIRST:event_showTitleGroupActionPerformed
    {//GEN-HEADEREND:event_showTitleGroupActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_showTitleGroupActionPerformed

    private void titleFontSizeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_titleFontSizeActionPerformed
    {//GEN-HEADEREND:event_titleFontSizeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_titleFontSizeActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelButtonActionPerformed
    {//GEN-HEADEREND:event_cancelButtonActionPerformed
        parent.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void applyButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_applyButtonActionPerformed
    {//GEN-HEADEREND:event_applyButtonActionPerformed
        addItem();
    }//GEN-LAST:event_applyButtonActionPerformed

    private void globalToggleActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_globalToggleActionPerformed
    {//GEN-HEADEREND:event_globalToggleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_globalToggleActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton applyButton;
    private javax.swing.JButton bold;
    private javax.swing.JTextPane bottomLeftCell;
    private javax.swing.JTextPane bottomRightCell;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextPane descriptionText;
    private javax.swing.JTextField fontSize;
    private javax.swing.JCheckBox globalToggle;
    private javax.swing.JButton italics;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JButton monospace;
    private javax.swing.JButton okButton;
    private javax.swing.JButton regular;
    private javax.swing.JCheckBox showTitleGroup;
    private javax.swing.JTextPane titleField;
    private javax.swing.JTextField titleFontSize;
    private javax.swing.JTextPane topLeftCell;
    private javax.swing.JTextPane topRightCell;
    private javax.swing.JButton underline;
    // End of variables declaration//GEN-END:variables
}
