package qsolutions.gui;

import java.util.ResourceBundle;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.nodes.Node;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import qsolutions.view.ExamNode;

/**
 * Top component which displays Format for the Exam.
 * @author Ryan Dollahon
 */
@ConvertAsProperties(dtd = "-//qsolutions.gui//Properties//EN",
autostore = false)
@TopComponent.Description(preferredID = "PropertiesTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "properties", openAtStartup = true)
@ActionID(category = "Window", id = "qsolutions.gui.PropertiesTopComponent")
@ActionReference(path = "Menu/MyWindow" /*
 * , position = 333
 */)
@TopComponent.OpenActionRegistration(displayName = 
        "qsolutions.gui.Bundle#DOCUMENTOPTIONS",
preferredID = "PropertiesTopComponent")
@Messages(
{
    "CTL_PropertiesAction=Document Options",
    "CTL_PropertiesTopComponent=Document Options",
    "HINT_PropertiesTopComponent=This is a Properties window"
})
public final class PropertiesTopComponent extends TopComponent
{
    private static final String kBundleName = "qsolutions/gui/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    
    /**
     * Default constructor for PropertiesTopComponent
     */
    public PropertiesTopComponent()
    {
        initComponents();
        setName(Bundle.CTL_PropertiesTopComponent());
        setDisplayName(kBundle.getString("DOCUMENT OPTIONS"));
        setToolTipText(kBundle.getString("PROPERTIES TT"));

        sheet.setNodes(new Node[] {new ExamNode()});
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sheet = new org.openide.explorer.propertysheet.PropertySheet();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 460, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(sheet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 499, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(sheet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.openide.explorer.propertysheet.PropertySheet sheet;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Method executed upon component opening
     */
    @Override
    public void componentOpened()
    {
        // TODO add custom code on component opening
    }

    /**
     * Calls the parent's componentShowing method
     */
    @Override
    public void componentShowing()
    {
        super.componentShowing();
    }
    
    /**
     * Method executed upon component closing
     */
    @Override
    public void componentClosed()
    {
        // TODO add custom code on component closing
    }

    /**
     * Writes the properties of this top component to Properties
     * @param p the Properties to write to
     */
    void writeProperties(java.util.Properties p)
    {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    /**
     * Reads the properties of this top component from Properties
     * @param p the Properties to read from
     */
    void readProperties(java.util.Properties p)
    {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
