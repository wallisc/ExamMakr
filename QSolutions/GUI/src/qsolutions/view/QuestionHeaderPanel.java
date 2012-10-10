/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.view;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLEditorKit;
import qsolutions.api.*;


/**
 * Top panel for all question type views
 */
public final class QuestionHeaderPanel extends javax.swing.JPanel 
    implements KeyListener, FocusListener
{
    protected File tempImage;
    protected float tempScale = 1.0f;
    protected int imageAlignment;
    protected boolean imageChanged;
    private static final int kDefaultImageWidth = 100;
    private static final int kDefaultImageHeight = 100;
    
    private static final String kBundleName = "qsolutions/view/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    
    /**
     * Creates new form QuestionHeaderPanel
     */
    public QuestionHeaderPanel()
    {
        initComponents();
        
        /*if (Lookup.getDefault().lookup(HTMLEditorKitAPI.class) == null)
            javax.swing.JOptionPane.showMessageDialog(null, "Null editorkit");*/
        questionPane.setEditorKit(new HTMLEditorKit());
        questionPane.addKeyListener(this);
        prepareButtons();
        questionPane.setTransferHandler(new JTextPaneTransferHandler());
        questionPane.setFocusTraversalKeys(
                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        marksField.addFocusListener(this);
        imageChanged = false;
    }
    
    /**
     * Creates new form QuestionHeaderPanel with a Question's data
     * @param question the question to fill the GUI
     */
    public QuestionHeaderPanel( QuestionApi question )
    {
        this();
        fillFields( question );
        prepareButtons();
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
     * Sets the members in the Question with the values from the GUI
     * @param question the Question to be filled
     */
    public void extractQuestion( QuestionApi question )
    {
        String questionText;
        question.setActive( activeBox.isSelected() );
        
        try
        {
            question.setMarks( Integer.parseInt( marksField.getText() ) );
        }
        catch ( NumberFormatException exception )
        {
            marksField.setText( "0" );
            question.setMarks( 0 );
        }

        question.setLevel( levelField.getText() );
        question.setCategory( categoryField.getText() );
        
        questionText = questionPane.getText();
        
        // If no text is filled in questionPane
        if ( questionText == null )
        {
            questionText = "";
        }
        
        question.setText( questionText );
        
        question.setKeepTogether( keepTogetherBox.isSelected() );  

        question.setImageFile( tempImage );
        question.setScale( tempScale );
        question.setImageAlignment(imageAlignment);
    }
    
    /**
     * Fills the GUI fields with the Question's member data
     * @param question the Question to get member data from
     */
    public void fillFields( QuestionApi question )
    {
        File file = question.getImage();
        activeBox.setSelected( question.isActive() );
        marksField.setText( Integer.toString( question.getMarks() ) );
        levelField.setText( question.getLevel() );
        categoryField.setText( question.getCategory() );
        keepTogetherBox.setSelected( question.isKeepTogether() );
        questionPane.setText( question.getText() );
        
        tempImage = file;
        try
        {
            setImage(file, question.getScale());
        }
        catch (IOException e)
        {
            System.out.println("Invalid image stored in exam");
        }
        
        tempScale = question.getScale();
        imageAlignment = question.getImageAlignmnet();
    }
            

    
    /**
     * Sets the questionPane's EditorKit to editor
     * @param editor the EditorKit to use
     */
    public void setEditorKit( EditorKit editor )
    {
        questionPane.setEditorKit( editor );    
        questionPane.setBackground(Color.WHITE);
    }
    
    /**
     * Calls the putProperty method for the questionPane's Document
     * @param property the first parameter for the putProperty
     * @param val the second parameter for the putProperty
     */
    public void putProperty( String property, Boolean val )
    {
        questionPane.getDocument().putProperty( property, val );
    }
    
    /**
     * Returns a String with the contents of questionPane
     * @return the contents of questionPane
     */
    public String getText()
    {
        return questionPane.getText();
    }
    
    /**
     * Returns the questionPane's Document
     * @return the questionPane's Document
     */
    public Document getDocument()
    {
        return questionPane.getDocument();
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

        activeBox = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        levelField = new javax.swing.JTextField();
        categoryField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        marksField = new IntField();
        keepTogetherBox = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        questionPane = new javax.swing.JTextPane();
        bold = new javax.swing.JButton();
        italics = new javax.swing.JButton();
        underline = new javax.swing.JButton();
        monospace = new javax.swing.JButton();
        regular = new javax.swing.JButton();
        addImage = new javax.swing.JButton();
        imageLabel = new javax.swing.JLabel();

        activeBox.setText(org.openide.util.NbBundle.getMessage(QuestionHeaderPanel.class, "QuestionHeaderPanel.activeBox.text")); // NOI18N
        activeBox.setFocusable(false);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(QuestionHeaderPanel.class, "QuestionHeaderPanel.jLabel1.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(QuestionHeaderPanel.class, "QuestionHeaderPanel.jLabel3.text")); // NOI18N

        jLabel4.setText(org.openide.util.NbBundle.getMessage(QuestionHeaderPanel.class, "QuestionHeaderPanel.jLabel4.text")); // NOI18N

        marksField.setText(org.openide.util.NbBundle.getMessage(QuestionHeaderPanel.class, "QuestionHeaderPanel.marksField.text")); // NOI18N
        marksField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        keepTogetherBox.setText(org.openide.util.NbBundle.getMessage(QuestionHeaderPanel.class, "QuestionHeaderPanel.keepTogetherBox.text")); // NOI18N
        keepTogetherBox.setFocusable(false);

        jLabel5.setText(org.openide.util.NbBundle.getMessage(QuestionHeaderPanel.class, "QuestionHeaderPanel.jLabel5.text")); // NOI18N

        scrollPane.setViewportView(questionPane);

        bold.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bold.png"))); // NOI18N
        bold.setToolTipText(org.openide.util.NbBundle.getMessage(QuestionHeaderPanel.class, "QuestionHeaderPanel.bold.toolTipText")); // NOI18N
        bold.setFocusable(false);

        italics.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/italics.png"))); // NOI18N
        italics.setToolTipText(org.openide.util.NbBundle.getMessage(QuestionHeaderPanel.class, "QuestionHeaderPanel.italics.toolTipText")); // NOI18N
        italics.setFocusable(false);

        underline.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/underline.png"))); // NOI18N
        underline.setToolTipText(org.openide.util.NbBundle.getMessage(QuestionHeaderPanel.class, "QuestionHeaderPanel.underline.toolTipText")); // NOI18N
        underline.setFocusable(false);

        monospace.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/monospace.png"))); // NOI18N
        monospace.setToolTipText(org.openide.util.NbBundle.getMessage(QuestionHeaderPanel.class, "QuestionHeaderPanel.monospace.toolTipText")); // NOI18N
        monospace.setFocusable(false);

        regular.setText(org.openide.util.NbBundle.getMessage(QuestionHeaderPanel.class, "QuestionHeaderPanel.regular.text")); // NOI18N
        regular.setToolTipText(org.openide.util.NbBundle.getMessage(QuestionHeaderPanel.class, "QuestionHeaderPanel.regular.toolTipText")); // NOI18N
        regular.setFocusable(false);

        addImage.setText(org.openide.util.NbBundle.getMessage(QuestionHeaderPanel.class, "QuestionHeaderPanel.addImage.text")); // NOI18N
        addImage.setFocusable(false);
        addImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addImageActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(scrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 537, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(28, 28, 28)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(marksField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 66, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel1)
                            .add(layout.createSequentialGroup()
                                .add(bold, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(italics, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jLabel5))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(underline, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(monospace))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(levelField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabel3)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel4)
                                    .add(categoryField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 107, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(keepTogetherBox)
                                    .add(layout.createSequentialGroup()
                                        .add(activeBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 77, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(18, 18, 18)
                                        .add(addImage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 149, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                            .add(regular))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(imageLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel1)
                            .add(jLabel3)
                            .add(jLabel4))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(marksField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(levelField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(categoryField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(bold)
                            .add(italics)
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(underline, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(monospace))
                            .add(regular))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jLabel5))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(imageLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(activeBox)
                                    .add(addImage))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(keepTogetherBox)
                                .add(0, 0, Short.MAX_VALUE)))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(scrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 57, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

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
     * Get the image that is being displayed in the preview pane
     * @return the file of the displayed image
     */
    public File getStoredImage()
    {
        return tempImage;
    }
    
    /**
     * Clicks the bold button in this context.
     */
    public void boldAction()
    {
        bold.doClick();
    }
    
    /**
     * Clicks the italics button in this context.
     */
    public void italicAction()
    {
        italics.doClick();
    }
    
    /**
     * Clicks the underline button in this context.
     */
    public void underlineAction()
    {
        underline.doClick();
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
            boldAction();
        }
        //If control i is pressed, click italics
        else if (e.isControlDown()&& e.getKeyCode() == KeyEvent.VK_I)
        {
            italicAction();
        }
        //If control u is pressed, click underline
        else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_U)
        {
            underlineAction();
        }
    }
    
   /**
    * Called if some component gains keyboard focus (whether it be from 
    * keyboard tabbing or mouse click off the component)
    * @param ev the event generated from the focus gain, include the component
    * that got focused
    */
    @Override
    public void focusGained(FocusEvent ev)
    {
        this.scrollRectToVisible(ev.getComponent().getBounds());
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
    //GEN-END
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox activeBox;
    private javax.swing.JButton addImage;
    private javax.swing.JButton bold;
    private javax.swing.JTextField categoryField;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JButton italics;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JCheckBox keepTogetherBox;
    private javax.swing.JTextField levelField;
    private javax.swing.JTextField marksField;
    private javax.swing.JButton monospace;
    private javax.swing.JTextPane questionPane;
    private javax.swing.JButton regular;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton underline;
    // End of variables declaration//GEN-END:variables
}
