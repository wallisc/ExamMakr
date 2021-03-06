/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.gui;

import java.io.*;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import org.jpedal.examples.simpleviewer.Commands;
import org.jpedal.examples.simpleviewer.SimpleViewer;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;
import org.xhtmlrenderer.pdf.ITextRenderer;
import qsolutions.api.ExamApi;


/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//qsolutions.gui//DocumentView//EN",
autostore = false)
@TopComponent.Description(preferredID = "DocumentViewTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "qsolutions.gui.DocumentViewTopComponent")
@ActionReference(path = "Menu/MyWindow" /*
 * , position = 333
 */)
@TopComponent.OpenActionRegistration(displayName = "qsolutions.gui.Bundle#DOCUMENTVIEW",
preferredID = "DocumentViewTopComponent")
@Messages({
    "CTL_DocumentViewAction=DocumentView",
    "CTL_DocumentViewTopComponent=Document View",
    "HINT_DocumentViewTopComponent=Preview your exam here"
})
public final class DocumentViewTopComponent extends TopComponent 
    implements Observer
{

    /** Maximum Scale value */
    private final static int kMaxScale = 14;
    
    /** The initial scale value */
    private final static int kInitialScale = 4;
    
    
    // The Exam being worked on
    private ExamApi exam;
    
    // pdf viewer
    private SimpleViewer viewer;
    // temp pdf file path
    private String pdfFile;
    // scale for displaying the exam
    private float scale;
    private final Result<ExamApi> result;
    
    private static final String kBundleName = "qsolutions/gui/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    
    /**
     * Creates new form DocumentView
     */
    
    public DocumentViewTopComponent() 
    {
        initComponents();
        setName(Bundle.CTL_DocumentViewTopComponent());
        setDisplayName(kBundle.getString("DOCUMENT VIEW"));
        setToolTipText(kBundle.getString("DOCUMENT VIEW TT"));
        scale = kInitialScale;   
        
        result = Lookup.getDefault().lookupResult(ExamApi.class);
        Collection<ExamApi> inst = (Collection<ExamApi>) result.allInstances();
        exam = inst.iterator().next();
        initComponents();
        
        viewer = new SimpleViewer(previewPanel, 
                "jar:/org/jpedal/examples/simpleviewer/"
                + "res/preferences/NavOnly.xml");
        // "jar:/org/jpedal/examples/simpleviewer/res/preferences/NoGUI.xml" );
        // "jar:/org/jpedal/examples/simpleviewer/res/preferences/NavOnly.xml" );
        
        viewer.setupViewer();
        viewer.currentGUI.setScaling( scale );
        
    
        refreshPreview();
        exam.addObserver(this);
    }

    /**
     * Determines if the properties is opened, opens it, and closes the table
     * view.
     */
    @Override
    public void componentShowing()
    {
        super.componentShowing();
        PropertiesTopComponent ptc = (PropertiesTopComponent) 
                    WindowManager.getDefault().findTopComponent(
                    "PropertiesTopComponent");
        TableViewTopComponent tvtc = (TableViewTopComponent) 
                    WindowManager.getDefault().findTopComponent(
                    "TableViewTopComponent");
        //opens the properties sheet if it is not already open
        if (!ptc.isOpened())
        {
            ptc.open();
        }
        ptc.requestActive();
        refreshPreview();
        tvtc.close();
        
    }
    
    /**
     * Refreshes the document preview
     * @param obs The Observable object that called update
     * @param args Observable arguments
     */
    @Override
    public void update(Observable obs, Object args)
    {
        // Only refresh if it is opened
        if (this.isShowing())
        {
            refreshPreview();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        previewPanel = new javax.swing.JPanel();
        zoomLabel = new javax.swing.JLabel();
        zoomOutLabel = new javax.swing.JLabel();
        answerButton = new javax.swing.JButton();

        javax.swing.GroupLayout previewPanelLayout = new javax.swing.GroupLayout(previewPanel);
        previewPanel.setLayout(previewPanelLayout);
        previewPanelLayout.setHorizontalGroup(
            previewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        previewPanelLayout.setVerticalGroup(
            previewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 264, Short.MAX_VALUE)
        );

        zoomLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zoomIn24.png"))); // NOI18N
        zoomLabel.setToolTipText(org.openide.util.NbBundle.getMessage(DocumentViewTopComponent.class, "DocumentViewTopComponent.zoomLabel.toolTipText")); // NOI18N
        zoomLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        zoomLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                zoomLabelMouseClicked(evt);
            }
        });

        zoomOutLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zoomOut24.png"))); // NOI18N
        zoomOutLabel.setToolTipText(org.openide.util.NbBundle.getMessage(DocumentViewTopComponent.class, "DocumentViewTopComponent.zoomOutLabel.toolTipText")); // NOI18N
        zoomOutLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        zoomOutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                zoomOutLabelMouseClicked(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(answerButton, org.openide.util.NbBundle.getMessage(DocumentViewTopComponent.class, "DocumentViewTopComponent.answerButton.text")); // NOI18N
        answerButton.setToolTipText(org.openide.util.NbBundle.getMessage(DocumentViewTopComponent.class, "DocumentViewTopComponent.answerButton.toolTipText")); // NOI18N
        answerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answerButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(zoomOutLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(zoomLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 194, Short.MAX_VALUE)
                .addComponent(answerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(previewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(zoomLabel)
                    .addComponent(zoomOutLabel)
                    .addComponent(answerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(previewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void zoomLabelMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_zoomLabelMouseClicked
    {//GEN-HEADEREND:event_zoomLabelMouseClicked
        // if the scale is less than the max scale increase it
        if (scale < kMaxScale - 1) 
        {
            scale++;
            // if the scale is less than the initial, set it to the initial
            if (scale < kInitialScale) 
            {
                scale = kInitialScale;
            }
            viewer.currentGUI.setScaling(scale);
        }
    }//GEN-LAST:event_zoomLabelMouseClicked

    private void zoomOutLabelMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_zoomOutLabelMouseClicked
    {//GEN-HEADEREND:event_zoomOutLabelMouseClicked
        // if the scale is greater than or less the the initial, zoom out
        if (scale >= kInitialScale) 
        {
            scale--;
            viewer.currentGUI.setScaling(scale);
        }
    }//GEN-LAST:event_zoomOutLabelMouseClicked

    private void answerButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_answerButtonActionPerformed
    {//GEN-HEADEREND:event_answerButtonActionPerformed
        // if the exam is displaying answers hide them, otherwise show them
        if (exam.getAnswerDoc()) 
        {
            answerButton.setText(kBundle.getString("SHOW ANSWERS"));
            answerButton.setToolTipText(kBundle.getString("SHOW ANSWERS"));
        } 
        else 
        {
            answerButton.setText(kBundle.getString("HIDE ANSWERS"));
            answerButton.setToolTipText(kBundle.getString("HIDE ANSWERS"));
        }
        exam.toggleAnswerDoc();
        refreshPreview();
    }//GEN-LAST:event_answerButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton answerButton;
    private javax.swing.JPanel previewPanel;
    private javax.swing.JLabel zoomLabel;
    private javax.swing.JLabel zoomOutLabel;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Code executed when the component is opened
     */
    @Override
    public void componentOpened()
    {
        refreshPreview();
    }

    /**
     * Code executed when the component is closed
     */
    @Override
    public void componentClosed()
    {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) 
    {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) 
    {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
    
    /**
     * Refreshes the previewWindow
     */
    public void refreshPreview()
    {
        Object[] input;
        try 
        {
            input = new Object[]{refreshPDF()};
            viewer.executeCommand( Commands.OPENFILE, input );
        } 
        catch ( Exception ex )
        {
            JOptionPane.showMessageDialog(this, "EXCEPTION: "+ex.toString());
        }
    }
    
    
    /**
     * Refresh the pdf view
     * @return a String representing the tempPDF file path
     * @throws Exception from failed reads or writes
     */
    private String refreshPDF() throws Exception
    {
        File xmltemp = File.createTempFile( "exam-xhtml-generator", ".tmp" ); 
        File pdftemp = File.createTempFile( "temppdf", ".pdf" ); 
        
        xmltemp.deleteOnExit();
        pdftemp.deleteOnExit();
        
        //Write xhtml to temporary file
        FileWriter fstream = new FileWriter( xmltemp );
        BufferedWriter out = new BufferedWriter( fstream );
        out.write( exam.generateXhtmlExam() );
        out.close();

        //Setup renderer input ( xhtml ) and output ( pdf )
        String xmlstring = xmltemp.toURI().toURL().toString();
        OutputStream os = new FileOutputStream( pdftemp );

        //Setup Renderer
        ITextRenderer renderer = new ITextRenderer();
        
        renderer.setDocument( xmltemp );
        renderer.layout();

        //Render PDF and close
        renderer.createPDF( os );
        os.close();
                
        return pdftemp.getAbsolutePath();
    }
    
}
