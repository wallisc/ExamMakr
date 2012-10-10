package qsolutions.view;

import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLEditorKit;
import org.openide.util.Lookup;
import qsolutions.api.DocumentItemApi;
import qsolutions.api.HTMLEditorKitAPI;
import qsolutions.api.SectionTitleApi;

/**
 * The GUI containing the SectionTitleDesignPanel which serves as the interface
 * for SectionTitle construction.
 * @author Jiahe Kuang
 */
public class SectionTitleView extends DesignItemView
{
    /**
     * Creates new form SectionTitleView
     */
    public SectionTitleView() 
    {
        super();
        item = Lookup.getDefault().lookup(SectionTitleApi.class).newItem();
        setEditView(false);
        initComponents();
        
        sectionTitleField.addKeyListener(this);
        sectionTitleField.setEditorKit((EditorKit)
                (Lookup.getDefault().lookup(HTMLEditorKitAPI.class)));
        prepareButtons();
        sectionTitleField.setTransferHandler(new JTextPaneTransferHandler());
        
        addDocumentItem.addFocusListener(this);
    }
    /**
     * Create new SectionTitle view with existing instance
     * @param nItem instance to mimic
     */
    public SectionTitleView(SectionTitleApi nItem) 
    {
        super();
        item = nItem;
        setEditView(true);
        initComponents();
        
        sectionTitleField.addKeyListener(this);
        sectionTitleField.setEditorKit((EditorKit)
                (Lookup.getDefault().lookup(HTMLEditorKitAPI.class)));
        prepareButtons();
        fillFields(item);
        addDocumentItem.setText(kBundle.getString("UPDATE SECTION TITLE"));
        
        addDocumentItem.addFocusListener(this);
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
     * Creates an item from the contents of the GUI
     * @return a new SectionTitle
     */
    protected SectionTitleApi extractItem()
    {
        SectionTitleApi toAdd = 
                Lookup.getDefault().lookup(SectionTitleApi.class).newItem();
        String titleText;
        
        toAdd.setActive( true );

        titleText = sectionTitleField.getText();
        // If no text is filled in questionPane
        if ( titleText == null )
        {
            titleText = "";
        }
        
        toAdd.setText( titleText );
        return toAdd;
    }
    
    
    /**
     * Stores the item to the exam appropriately
     */
    public void addItem()
    {
        super.addItem();
        
        item = Lookup.getDefault().lookup(SectionTitleApi.class).newItem();
        fillFields( item );
    }
    
    /**
     * Fills the GUI from inItem
     * @param inItem the item used to fill the GUI
     */
    protected void fillFields( DocumentItemApi inItem ) 
    {
        SectionTitleApi iDesignItem = ( SectionTitleApi ) inItem;
        sectionTitleField.setText( iDesignItem.getText() );
    }

    /**
     * Triggers clicking the bold WYSYWIG button
     */
    @Override
    public void boldAction()
    {
        bold.doClick();
    }
    
    /**
     * Triggers clicking the italic WYSYWIG button
     */
    @Override
    public void italicAction()
    {
        italics.doClick();
    }
    /**
     * Triggers clicking the underline WYSYWIG button
     */
    @Override
    public void underlineAction()
    {
        underline.doClick();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    //GEN-BEGIN
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addDocumentItem = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sectionTitleField = new javax.swing.JTextPane();
        bold = new javax.swing.JButton();
        italics = new javax.swing.JButton();
        underline = new javax.swing.JButton();
        monospace = new javax.swing.JButton();
        regular = new javax.swing.JButton();
        examNavigatorPanel = new ExamNavigatorPanel(this);

        addDocumentItem.setText(org.openide.util.NbBundle.getMessage(SectionTitleView.class, "SectionTitleView.addDocumentItem.text")); // NOI18N
        addDocumentItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDocumentItemActionPerformed(evt);
            }
        });

        jLabel1.setText(org.openide.util.NbBundle.getMessage(SectionTitleView.class, "SectionTitleView.jLabel1.text")); // NOI18N

        jScrollPane1.setViewportView(sectionTitleField);

        bold.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bold.png"))); // NOI18N
        bold.setToolTipText(org.openide.util.NbBundle.getMessage(SectionTitleView.class, "SectionTitleView.bold.toolTipText")); // NOI18N

        italics.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/italics.png"))); // NOI18N
        italics.setToolTipText(org.openide.util.NbBundle.getMessage(SectionTitleView.class, "SectionTitleView.italics.toolTipText")); // NOI18N

        underline.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/underline.png"))); // NOI18N
        underline.setToolTipText(org.openide.util.NbBundle.getMessage(SectionTitleView.class, "SectionTitleView.underline.toolTipText")); // NOI18N

        monospace.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/monospace.png"))); // NOI18N
        monospace.setToolTipText(org.openide.util.NbBundle.getMessage(SectionTitleView.class, "SectionTitleView.monospace.toolTipText")); // NOI18N

        regular.setText(org.openide.util.NbBundle.getMessage(SectionTitleView.class, "SectionTitleView.regular.text")); // NOI18N
        regular.setToolTipText(org.openide.util.NbBundle.getMessage(SectionTitleView.class, "SectionTitleView.regular.toolTipText")); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jLabel1)
                            .add(layout.createSequentialGroup()
                                .add(bold, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(italics, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(underline, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(monospace, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(regular)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, addDocumentItem))
                        .addContainerGap())))
            .add(examNavigatorPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(examNavigatorPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(bold)
                    .add(italics)
                    .add(underline)
                    .add(monospace)
                    .add(regular))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 41, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(addDocumentItem)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    //GEN-END
    /**
     * Stores the item to the exam as appropriate
     * @param evt unused
     */
    private void addDocumentItemActionPerformed( java.awt.event.ActionEvent evt )//GEN-FIRST:event_addDocumentItemActionPerformed
    {//GEN-HEADEREND:event_addDocumentItemActionPerformed
        addItem();
    }//GEN-LAST:event_addDocumentItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addDocumentItem;
    private javax.swing.JButton bold;
    private qsolutions.view.ExamNavigatorPanel examNavigatorPanel;
    private javax.swing.JButton italics;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton monospace;
    private javax.swing.JButton regular;
    private javax.swing.JTextPane sectionTitleField;
    private javax.swing.JButton underline;
    // End of variables declaration//GEN-END:variables
}
