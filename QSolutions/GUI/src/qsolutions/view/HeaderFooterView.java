package qsolutions.view;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLEditorKit;
import org.openide.util.Lookup;
import qsolutions.api.ExamApi;
import qsolutions.api.HTMLEditorKitAPI;
import qsolutions.api.HeaderFooterApi;

/**
 * Panel for editing the header and footer in the gui
 */
public class HeaderFooterView extends JPanel implements KeyListener
{
    private Lookup.Result<ExamApi> result = null;
    private ExamApi exam;
    private JDialog parent;
    
    private static final String kBundleName = "qsolutions/view/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    
    /**
     * Creates new form TitleGroupDesignPanel in nParent referencing nExam
     * @param nParent the dialog box this is placed in
     */
    public HeaderFooterView(JDialog nParent)
    {
        initComponents();
        parent = nParent;
        // if the informaiton is valid fill the fields
        result = Lookup.getDefault().lookupResult(ExamApi.class);
        Collection<ExamApi> inst = (Collection<ExamApi>) result.allInstances();
        exam = inst.iterator().next();
        
        EditorKit kit = (EditorKit)Lookup.getDefault().lookup(
                HTMLEditorKitAPI.class);
        preparePanel(headerTextLeft);
        preparePanel(headerTextRight);
        preparePanel(headerTextMiddle);
        preparePanel(footerTextLeft);
        preparePanel(footerTextRight);
        preparePanel(footerTextMiddle);
             
        // if any of the exam's fields are not empty, fill the gui fields
        if ( !exam.getHeaderFooter().getHeaderContentLeft().equals( "" ) || 
                !exam.getHeaderFooter().getHeaderContentMiddle().equals( "" ) || 
                !exam.getHeaderFooter().getHeaderContentRight().equals( "" ) || 
                !exam.getHeaderFooter().getFooterContentLeft().equals( "" ) || 
                !exam.getHeaderFooter().getFooterContentMiddle().equals( "" ) || 
                !exam.getHeaderFooter().getFooterContentRight().equals( "" ) || 
                exam.getHeaderFooter().getShowHeader() || 
                exam.getHeaderFooter().getShowFooter() )
        {
            fillFields();
        }
        
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
        HeaderFooterApi hf = exam.getHeaderFooter();
        exam.bulkUpdate(true);
        hf.setShowHeader( headerToggle.isSelected() );
        hf.setShowFooter( footerToggle.isSelected() );
        hf.setHeaderContentLeft( headerTextLeft.getText() );
        hf.setHeaderContentMiddle( headerTextMiddle.getText() );
        hf.setHeaderContentRight( headerTextRight.getText() );
        hf.setFooterContentLeft( footerTextLeft.getText() );
        hf.setFooterContentMiddle( footerTextMiddle.getText() );
        hf.setFooterContentRight( footerTextRight.getText() );
        exam.bulkUpdate(false);
    }
    
    
    /**
     * Returns true if the title group has been edited, otherwise false
     * @return true if the title group has been edited, otherwise false
     */
    public boolean isDifferent()
    {
        return ( !( exam.getHeaderFooter().getShowHeader() == 
                ( headerToggle.isSelected() ) ) || 
            !( exam.getHeaderFooter().getShowFooter() == 
                ( footerToggle.isSelected() ) ) || 
            !exam.getHeaderFooter().getFooterContentLeft().equals( 
                footerTextLeft.getText() ) || 
            !exam.getHeaderFooter().getFooterContentMiddle().equals( 
                footerTextMiddle.getText() ) || 
            !exam.getHeaderFooter().getFooterContentRight().equals( 
                footerTextRight.getText() ) || 
            !exam.getHeaderFooter().getHeaderContentLeft().equals(
                headerTextLeft.getText() )|| 
            !exam.getHeaderFooter().getHeaderContentMiddle().equals( 
                headerTextMiddle.getText() )|| 
            !exam.getHeaderFooter().getHeaderContentRight().equals( 
                headerTextRight.getText() ) );
    }
    
    /**
     * Fills the GUI fields from the exam
     */
    protected void fillFields()
    {
        headerTextLeft.setText( exam.getHeaderFooter().getHeaderContentLeft() );
        headerTextMiddle.setText( exam.getHeaderFooter().getHeaderContentMiddle() );
        headerTextRight.setText( exam.getHeaderFooter().getHeaderContentRight() );
        footerTextLeft.setText( exam.getHeaderFooter().getFooterContentLeft() );
        footerTextMiddle.setText( exam.getHeaderFooter().getFooterContentMiddle() );
        footerTextRight.setText( exam.getHeaderFooter().getFooterContentRight() );
        headerToggle.setSelected( exam.getHeaderFooter().getShowHeader() );
        footerToggle.setSelected( exam.getHeaderFooter().getShowFooter() );
    }
    
    /**
     * Returns a string representing the type
     * @return String literal "Title Group"
     */
    public String getType()
    {
        return kBundle.getString("HEADER AND FOOTER");
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
            bold.doClick();
        }
        //If control i is pressed, click italics
        else if (e.isControlDown()&& e.getKeyCode() == KeyEvent.VK_I)
        {
            italics.doClick();
        }
        //If control u is pressed, click underline
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
    // GEN-BEGIN
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerToggle = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        footerToggle = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        headerTextLeft = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        headerTextMiddle = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        headerTextRight = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        footerTextLeft = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        footerTextMiddle = new javax.swing.JTextPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        footerTextRight = new javax.swing.JTextPane();
        okButton = new javax.swing.JButton();
        bold = new javax.swing.JButton();
        italics = new javax.swing.JButton();
        underline = new javax.swing.JButton();
        monospace = new javax.swing.JButton();
        regular = new javax.swing.JButton();
        applyButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("qsolutions/view/Bundle"); // NOI18N
        headerToggle.setText(bundle.getString("SHOW HEADER")); // NOI18N

        jLabel1.setText(bundle.getString("HEADER CONTENT: ")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(HeaderFooterView.class, "HeaderFooterView.jLabel2.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(HeaderFooterView.class, "HeaderFooterView.jLabel3.text")); // NOI18N

        footerToggle.setText(bundle.getString("SHOW FOOTER")); // NOI18N

        jLabel4.setText(bundle.getString("FOOTER CONTENT:")); // NOI18N

        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setEnabled(false);
        jButton1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel5.setText(org.openide.util.NbBundle.getMessage(HeaderFooterView.class, "HeaderFooterView.jLabel5.text")); // NOI18N

        jScrollPane1.setViewportView(headerTextLeft);

        jScrollPane2.setViewportView(headerTextMiddle);

        jScrollPane3.setViewportView(headerTextRight);

        jScrollPane4.setViewportView(footerTextLeft);

        jScrollPane5.setViewportView(footerTextMiddle);

        footerTextRight.setText(bundle.getString("PAGE <PN> OF <PC>")); // NOI18N
        jScrollPane6.setViewportView(footerTextRight);

        okButton.setText(bundle.getString("OK")); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        bold.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bold.png"))); // NOI18N
        bold.setToolTipText(bundle.getString("BOLD")); // NOI18N
        bold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boldActionPerformed(evt);
            }
        });

        italics.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/italics.png"))); // NOI18N
        italics.setToolTipText(bundle.getString("ITALIC")); // NOI18N

        underline.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/underline.png"))); // NOI18N
        underline.setToolTipText(bundle.getString("UNDERLINE")); // NOI18N

        monospace.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/monospace.png"))); // NOI18N
        monospace.setToolTipText(bundle.getString("SET HIGHLIGHTED TEXT TO MONOSPACE FONT")); // NOI18N

        regular.setText(bundle.getString("PLAIN")); // NOI18N
        regular.setToolTipText(bundle.getString("SET HIGHLIGHTED TEXT TO PLAIN FONT")); // NOI18N
        regular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regularActionPerformed(evt);
            }
        });

        applyButton.setText(bundle.getString("APPLY")); // NOI18N
        applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButtonActionPerformed(evt);
            }
        });

        cancelButton.setText(bundle.getString("CANCEL")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(headerToggle)
                                .addGap(18, 18, 18)
                                .addComponent(footerToggle)
                                .addGap(139, 139, 139))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(okButton))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(applyButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(cancelButton))
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(bold, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(italics, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(underline, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(monospace, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(regular, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(269, 269, 269)
                            .addComponent(jButton1))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bold)
                            .addComponent(italics)
                            .addComponent(underline)
                            .addComponent(monospace)
                            .addComponent(regular))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(headerToggle)
                        .addComponent(footerToggle))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(okButton)
                        .addComponent(applyButton)
                        .addComponent(cancelButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // GEN-END

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_okButtonActionPerformed
    {//GEN-HEADEREND:event_okButtonActionPerformed
        addItem();
        parent.dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    private void applyButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_applyButtonActionPerformed
    {//GEN-HEADEREND:event_applyButtonActionPerformed
        addItem();
    }//GEN-LAST:event_applyButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelButtonActionPerformed
    {//GEN-HEADEREND:event_cancelButtonActionPerformed
        parent.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void regularActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_regularActionPerformed
    {//GEN-HEADEREND:event_regularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_regularActionPerformed

    private void boldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_boldActionPerformed
    {//GEN-HEADEREND:event_boldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton applyButton;
    private javax.swing.JButton bold;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextPane footerTextLeft;
    private javax.swing.JTextPane footerTextMiddle;
    private javax.swing.JTextPane footerTextRight;
    private javax.swing.JCheckBox footerToggle;
    private javax.swing.JTextPane headerTextLeft;
    private javax.swing.JTextPane headerTextMiddle;
    private javax.swing.JTextPane headerTextRight;
    private javax.swing.JCheckBox headerToggle;
    private javax.swing.JButton italics;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JButton monospace;
    private javax.swing.JButton okButton;
    private javax.swing.JButton regular;
    private javax.swing.JButton underline;
    // End of variables declaration//GEN-END:variables


}
