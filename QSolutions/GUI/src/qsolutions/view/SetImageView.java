/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.openide.util.Lookup;
import qsolutions.api.FormatApi;

/**
 * The dialog box that allows the user to select an image and scale for that 
 * image
 * @author Chris
 */
public class SetImageView extends javax.swing.JPanel
{
    private static final String kBundleName = "qsolutions/view/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    private static final String kResizePrompt =
            "LOADED IMAGE WAS SCALED DOWN TO FIT IN THE EXAM";
    private static final String kInvalidPrompt =
            "LOADED IMAGE IS AN INVALID IMAGE TYPE";
    
    private static final int kDefaultImageWidth = 100;
    private static final int kDefaultImageHeight = 100;
    private static final float kPercentagesPerUnit = 100.0f;
    private float imageWidth;
    private float unscaledWidth;
    private float pageWidth;
    private Float imageScale;
    private File tempImage;
    private JDialog parent;
    private JPanel parentPanel;
  
    /**
     * SetImageViews only constructor
     * @param nParentPanel The design panel for the question that the user wants
     * an image for
     * @param nParent The dialog box this panel is placed in
     */
    public SetImageView(JPanel nParentPanel, JDialog nParent)
    {
        initComponents();
        parent = nParent;
        parentPanel = nParentPanel;
        pageWidth = Lookup.getDefault().lookup(FormatApi.class).getPageWidth();
        
        extractInformation();

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

        chooseImage = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        apply = new javax.swing.JButton();
        scaleLabel = new javax.swing.JLabel();
        imageLabel = new javax.swing.JLabel();
        scaleSlider = new javax.swing.JSlider();
        scaleValue = new javax.swing.JLabel();
        alignmentDropDown = new javax.swing.JComboBox();
        alignmentLabel = new javax.swing.JLabel();

        chooseImage.setText(org.openide.util.NbBundle.getMessage(SetImageView.class, "SetImageView.chooseImage.text")); // NOI18N
        chooseImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseImageActionPerformed(evt);
            }
        });

        cancel.setText(org.openide.util.NbBundle.getMessage(SetImageView.class, "SetImageView.cancel.text")); // NOI18N
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        apply.setText(org.openide.util.NbBundle.getMessage(SetImageView.class, "SetImageView.apply.text")); // NOI18N
        apply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyActionPerformed(evt);
            }
        });

        scaleLabel.setText(org.openide.util.NbBundle.getMessage(SetImageView.class, "SetImageView.scaleLabel.text")); // NOI18N

        imageLabel.setText(org.openide.util.NbBundle.getMessage(SetImageView.class, "SetImageView.imageLabel.text")); // NOI18N
        imageLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        scaleSlider.setMinimum(1);
        scaleSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                scaleSliderStateChanged(evt);
            }
        });

        scaleValue.setText(org.openide.util.NbBundle.getMessage(SetImageView.class, "SetImageView.scaleValue.text")); // NOI18N

        alignmentDropDown.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Left", "Centered", "Right" }));
        alignmentDropDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alignmentDropDownActionPerformed(evt);
            }
        });

        alignmentLabel.setText(org.openide.util.NbBundle.getMessage(SetImageView.class, "SetImageView.alignmentLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(alignmentLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(alignmentDropDown, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(83, 83, 83))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(apply)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancel)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(scaleSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(scaleLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scaleValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(imageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chooseImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(115, 115, 115))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(imageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chooseImage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scaleLabel)
                    .addComponent(scaleValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scaleSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(alignmentDropDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(alignmentLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel)
                    .addComponent(apply))
                .addContainerGap(50, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // GEN-END

    /**
     * Extracts information from the parent panel and fills the image view 
     * accordingly
     */
    private void extractInformation()
    {
        // Extract information from Instructions
        if (parentPanel instanceof InstructionsView)
        {
            InstructionsView insView = (InstructionsView)parentPanel;
            imageScale = insView.tempScale;
            setAlignment(insView.imageAlignment);
            setImage(insView.tempImage);
        }
        // If it's not an instruction, extract from question header panel
        else if (parentPanel instanceof QuestionHeaderPanel)
        {
            QuestionHeaderPanel qhView = (QuestionHeaderPanel)parentPanel;
            imageScale = qhView.tempScale;
            setAlignment(qhView.imageAlignment);
            setImage(qhView.tempImage);
        }
    }
    
    private void chooseImageActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_chooseImageActionPerformed
    {//GEN-HEADEREND:event_chooseImageActionPerformed
        final javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        int returnVal;
        File file;
        
        returnVal = fc.showOpenDialog(null);
        // If the user chose a file
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION)
        {
            file = fc.getSelectedFile();

            //if the file already exists
            if (!file.isFile())
            {
                javax.swing.JOptionPane.showMessageDialog(null,
                        "File does not exist");
            }
            else
            {
                setImage(file);
                tempImage = file;
                setImageChanged();
            }
        }
    }//GEN-LAST:event_chooseImageActionPerformed

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
    
    private void setImage(File imageFile)
    {
        boolean imageExists = (imageFile != null);
        scaleValue.setEnabled( imageExists );
        scaleLabel.setEnabled( imageExists );
        scaleSlider.setEnabled( imageExists );
        alignmentDropDown.setEnabled( imageExists );
        alignmentLabel.setEnabled( imageExists );
        tempImage = imageFile;
        // If an actual image is being loaded
        if ( imageExists )
        {
            try
            {
                BufferedImage image = ImageIO.read(imageFile);
                Image scaledImage;

                scaledImage = scaleImage(image, kDefaultImageWidth, 
                    kDefaultImageHeight);
                unscaledWidth = image.getWidth();
                imageWidth = unscaledWidth * imageScale;
                // If the image is too big to fit in the exam
                if (imageWidth > pageWidth)
                {
                    imageScale = pageWidth / imageWidth;
                    scaleSlider.setValue((int)kPercentagesPerUnit);
                    JOptionPane.showMessageDialog(null,
                            kBundle.getString(kResizePrompt));
                }
                else
                {
                    scaleSlider.setValue((int)(imageWidth * kPercentagesPerUnit 
                            / pageWidth));
                }
                imageLabel.setText("");
                imageLabel.setIcon( new ImageIcon( scaledImage ) );

            }
            catch (IOException exp)
            {
                JOptionPane.showMessageDialog(null,
                        kBundle.getString(kInvalidPrompt));
            }
            
        }
    }
    
    private void applyActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_applyActionPerformed
    {//GEN-HEADEREND:event_applyActionPerformed
        try
        {
            //If the image came from question header panel
            if ( parentPanel instanceof QuestionHeaderPanel )
            {
                ((QuestionHeaderPanel)parentPanel).setImage(tempImage,
                    imageScale);
                ((QuestionHeaderPanel)parentPanel).imageAlignment = 
                        getAlignment();
            }
            //If the image came from an instructions view
            else if ( parentPanel instanceof InstructionsView )
            {
                ((InstructionsView)parentPanel).setImage(tempImage, imageScale);
                ((InstructionsView)parentPanel).imageAlignment = getAlignment();
            }
        }
        catch(IOException exc)
        {
            JOptionPane.showMessageDialog(null,
                    kBundle.getString("INVALID IMAGE LOADED"));
        }

        parent.dispose();
    }//GEN-LAST:event_applyActionPerformed

    private void scaleSliderStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_scaleSliderStateChanged
    {//GEN-HEADEREND:event_scaleSliderStateChanged
        if ( tempImage != null )
        {
            scaleValue.setText(scaleSlider.getValue() + "%");
            float pageWidthRatio = scaleSlider.getValue() / kPercentagesPerUnit;
            imageScale = pageWidthRatio * pageWidth / unscaledWidth;
        }
    }//GEN-LAST:event_scaleSliderStateChanged

    private void cancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelActionPerformed
    {//GEN-HEADEREND:event_cancelActionPerformed
        parent.dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void alignmentDropDownActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_alignmentDropDownActionPerformed
    {//GEN-HEADEREND:event_alignmentDropDownActionPerformed
        
    }//GEN-LAST:event_alignmentDropDownActionPerformed

    private void setAlignment(int align)
    {
        alignmentDropDown.setSelectedIndex(align); 
    }
    
    private int getAlignment()
    {
        return alignmentDropDown.getSelectedIndex();
    }
    
    private void setImageChanged()
    {
        //If the image came from question header panel
        if ( parentPanel instanceof QuestionHeaderPanel )
        {
            ((QuestionHeaderPanel)parentPanel).imageChanged = true;
        }
        //If the image came from an instructions view
        else if ( parentPanel instanceof InstructionsView )
        {
            ((InstructionsView)parentPanel).imageChanged = true;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox alignmentDropDown;
    private javax.swing.JLabel alignmentLabel;
    private javax.swing.JButton apply;
    private javax.swing.JButton cancel;
    private javax.swing.JButton chooseImage;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JLabel scaleLabel;
    private javax.swing.JSlider scaleSlider;
    private javax.swing.JLabel scaleValue;
    // End of variables declaration//GEN-END:variables
}
