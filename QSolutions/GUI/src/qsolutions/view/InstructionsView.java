package qsolutions.view;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLEditorKit;
import org.openide.util.Lookup;
import qsolutions.api.DocumentItemApi;
import qsolutions.api.HTMLEditorKitAPI;
import qsolutions.api.InstructionsApi;

/** 
 * Panel for adding or editing instructions in the exam
 */
public class InstructionsView extends DesignItemView implements KeyListener
{
    protected File tempImage;
    protected float tempScale;
    protected int imageAlignment;
    private static final int kDefaultImageWidth = 100;
    private static final int kDefaultImageHeight = 100;
    /** Creates new form InstructionsView */
    public InstructionsView()
    {
        super();
        item = Lookup.getDefault().lookup(InstructionsApi.class).newItem();
        setEditView(false);
        initComponents();
        
        instructions.setEditorKit((EditorKit)
                (Lookup.getDefault().lookup(HTMLEditorKitAPI.class)));
        instructions.addKeyListener(this);  
        instructions.setFocusTraversalKeys(
                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        prepareButtons();
        fillFields();
        instructions.setTransferHandler(new JTextPaneTransferHandler());
        
        instructions.addFocusListener(this);
        addDocumentItem.addFocusListener(this);
    }
    
    /**
     * Instructions view created from existing instance
     * @param nItem existing instance to populate instruction view
     */
    public InstructionsView(InstructionsApi nItem) 
    {
        super();
        item = nItem;
        setEditView( true );
        initComponents();
        
        instructions.setEditorKit((EditorKit)
                (Lookup.getDefault().lookup(HTMLEditorKitAPI.class)));
        instructions.addKeyListener(this);
        
        prepareButtons();
        fillFields();
        addDocumentItem.setText(kBundle.getString("UPDATE INSTUCTIONS"));
        instructions.setTransferHandler(new JTextPaneTransferHandler());
        
        instructions.addFocusListener(this);
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
     * Stores the item or the update to the exam
     */
    public void addItem()
    {
        super.addItem();
        
        setEditView(false);
        item = Lookup.getDefault().lookup(InstructionsApi.class).newItem();
        fillFields();
    }
    
    /**
     * Fills the fields in the GUI from nItem
     * @param inItem the item used to fill the fields
     */
    protected void fillFields( DocumentItemApi inItem ) 
    {
        File file = inItem.getImage();
        InstructionsApi iDesignItem = ( InstructionsApi ) inItem;
        instructions.setText( iDesignItem.getText() );
        keepTogether.setSelected( iDesignItem.isKeepTogether() );
        examNavigatorPanel.refreshView(isEditView());
        try
        {
            setImage(file, inItem.getScale());
            tempImage = file;
        }
        catch (IOException e)
        {
            System.out.println("Invalid image stored in exam");
        }
        
        tempScale = inItem.getScale();
        imageAlignment = inItem.getImageAlignmnet();
    }
    
    /**
     * Returns an Instructions built from the GUI
     * @return the new Instructions
     */
    protected InstructionsApi extractItem()
    {
        InstructionsApi toAdd = Lookup.getDefault().lookup(
                InstructionsApi.class).newItem();
        String instructionText = "";
        //toAdd.setItem( exam.getQuestions().size() );
        
        instructionText = instructions.getText();
        // If no text is filled in instructions
        if ( instructionText == null )
        {
            instructionText = "";
        }

        toAdd.setText( instructionText );
        toAdd.setKeepTogether( keepTogether.isSelected() );
                
        toAdd.setImageFile( tempImage );
        toAdd.setScale( tempScale );
        toAdd.setImageAlignment( imageAlignment );

        // If is an edit view and the image is unchanged
        if ( isEditView() && !imageChanged )
        {
            toAdd.setShallowImage(item);
        }
        return toAdd;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // GEN-BEGIN
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addDocumentItem = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        instructions = new javax.swing.JTextPane();
        regular = new javax.swing.JButton();
        monospace = new javax.swing.JButton();
        underline = new javax.swing.JButton();
        italics = new javax.swing.JButton();
        bold = new javax.swing.JButton();
        keepTogether = new javax.swing.JCheckBox();
        examNavigatorPanel = new ExamNavigatorPanel(this);
        addImage = new javax.swing.JButton();
        imageLabel = new javax.swing.JLabel();

        addDocumentItem.setText(org.openide.util.NbBundle.getMessage(InstructionsView.class, "InstructionsView.addDocumentItem.text")); // NOI18N
        addDocumentItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDocumentItemActionPerformed(evt);
            }
        });

        jLabel5.setText(org.openide.util.NbBundle.getMessage(InstructionsView.class, "InstructionsView.jLabel5.text")); // NOI18N

        jScrollPane2.setViewportView(instructions);

        regular.setText(org.openide.util.NbBundle.getMessage(InstructionsView.class, "InstructionsView.regular.text")); // NOI18N
        regular.setToolTipText(org.openide.util.NbBundle.getMessage(InstructionsView.class, "InstructionsView.regular.toolTipText")); // NOI18N

        monospace.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/monospace.png"))); // NOI18N
        monospace.setToolTipText(org.openide.util.NbBundle.getMessage(InstructionsView.class, "InstructionsView.monospace.toolTipText")); // NOI18N

        underline.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/underline.png"))); // NOI18N
        underline.setToolTipText(org.openide.util.NbBundle.getMessage(InstructionsView.class, "InstructionsView.underline.toolTipText")); // NOI18N

        italics.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/italics.png"))); // NOI18N
        italics.setToolTipText(org.openide.util.NbBundle.getMessage(InstructionsView.class, "InstructionsView.italics.toolTipText")); // NOI18N

        bold.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bold.png"))); // NOI18N
        bold.setToolTipText(org.openide.util.NbBundle.getMessage(InstructionsView.class, "InstructionsView.bold.toolTipText")); // NOI18N

        keepTogether.setText(org.openide.util.NbBundle.getMessage(InstructionsView.class, "InstructionsView.keepTogether.text")); // NOI18N

        addImage.setText(org.openide.util.NbBundle.getMessage(InstructionsView.class, "InstructionsView.addImage.text")); // NOI18N
        addImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addImageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bold, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(italics, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(underline, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(monospace, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(regular, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addImage, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(223, 223, 223))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(keepTogether)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addDocumentItem, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(examNavigatorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(examNavigatorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bold)
                            .addComponent(italics)
                            .addComponent(underline)
                            .addComponent(monospace)
                            .addComponent(regular)
                            .addComponent(addImage))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5))
                    .addComponent(imageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(keepTogether)
                    .addComponent(addDocumentItem, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // GEN-END

    /**
     * Adds the item or saves the item to the exam
     * @param evt unused
     */
    private void addDocumentItemActionPerformed( java.awt.event.ActionEvent evt )//GEN-FIRST:event_addDocumentItemActionPerformed
    {//GEN-HEADEREND:event_addDocumentItemActionPerformed
        addItem();
    }//GEN-LAST:event_addDocumentItemActionPerformed

    private void addImageActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addImageActionPerformed
    {//GEN-HEADEREND:event_addImageActionPerformed
        JDialog dialog = new JDialog((Frame)null,
                kBundle.getString("SET IMAGE"));
        SetImageView dPane = new 
           SetImageView(this, dialog);
        dialog.getContentPane().add(dPane);
        dialog.setSize(dPane.getPreferredSize());
        dialog.setVisible(true); 
    }//GEN-LAST:event_addImageActionPerformed

    private Image scaleImage(BufferedImage toScale, int 
        width, int height)
    {
        BufferedImage dest = new BufferedImage(width, height, 
                toScale.getType());
        Graphics2D g2 = dest.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(toScale, 0, 0, width, height, null);
        return dest;
    }
    
    protected void setImage(File imageFile, float scale) throws IOException
    {
        tempScale = scale;
        tempImage = imageFile;
        // If there is not image
        if (imageFile == null)
        {
            imageLabel.setVisible(false);
        }
        else
        {
            imageLabel.setVisible(true);
            BufferedImage image = ImageIO.read(imageFile);
            Image scaledImage = scaleImage(image, kDefaultImageWidth, 
                    kDefaultImageHeight);
            imageLabel.setIcon( new ImageIcon( scaledImage ) );
        }
        
        
    }
    
    /**
     * Clicks the bold button in this context.
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
     * Returns the image for this question
     * @return the image for the question
     */ 
    public File getImageFile()
    {
        return tempImage;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addDocumentItem;
    private javax.swing.JButton addImage;
    private javax.swing.JButton bold;
    private qsolutions.view.ExamNavigatorPanel examNavigatorPanel;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JTextPane instructions;
    private javax.swing.JButton italics;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JCheckBox keepTogether;
    private javax.swing.JButton monospace;
    private javax.swing.JButton regular;
    private javax.swing.JButton underline;
    // End of variables declaration//GEN-END:variables
}
