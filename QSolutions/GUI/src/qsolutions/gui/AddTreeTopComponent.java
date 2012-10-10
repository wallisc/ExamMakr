/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import qsolutions.action.TablePopupMenu;
import qsolutions.api.ExamApi;
import qsolutions.view.DocumentItemView;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//qsolutions.gui//AddTree//EN",
autostore = false)
@TopComponent.Description(preferredID = "AddTreeTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "qsolutions.gui.AddTreeTopComponent")
@ActionReference(path = "Menu/MyWindow" /*
 * , position = 333
 */)
@TopComponent.OpenActionRegistration(displayName = "qsolutions.gui.Bundle#ADDTREE",
preferredID = "AddTreeTopComponent")
@Messages({
    "CTL_AddTreeAction=AddTree",
    "CTL_AddTreeTopComponent=Document Items",
    "HINT_AddTreeTopComponent=Click to add any of the items below to your exam"
})
public final class AddTreeTopComponent extends TopComponent 
    implements TreeSelectionListener, LookupListener 
{
    private Lookup.Result<ExamApi> result = null; 
    private ExamApi iexam; // Exam Instance
    private boolean doChange;
    private String kTrueFalse = "True False";
    private String kHaltValue = "Halt";
    private Map<String, String> map;
    
    private static final String kBundleName = "qsolutions/gui/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    
    /**
     * Constructor for AddTreeTopComponent
     */
    public AddTreeTopComponent()
    {              
        initComponents();
        setTableStrings();
        setName(Bundle.CTL_AddTreeTopComponent());
        setDisplayName(kBundle.getString("DOCUMENT ITEMS"));
        setToolTipText(kBundle.getString("ADD TREE TT"));
        jTree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree.addTreeSelectionListener(this);
        jTree.expandRow(2);
        jTree.expandRow(1);
        doChange = true;
        map = generateMap();
    }
    
    /**
     * Sets the jTree options.
     */
    private void setTableStrings()
    {
        javax.swing.tree.DefaultMutableTreeNode treeNode1 =
                new javax.swing.tree.DefaultMutableTreeNode(
                kBundle.getString("EXAM"));
        javax.swing.tree.DefaultMutableTreeNode treeNode2 =
                new javax.swing.tree.DefaultMutableTreeNode(
                kBundle.getString("QUESTION ITEMS"));
        javax.swing.tree.DefaultMutableTreeNode treeNode3 =
                new javax.swing.tree.DefaultMutableTreeNode(
                kBundle.getString("FILL-IN-THE-BLANKS"));
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode(
                kBundle.getString("FREE RESPONSE"));
        treeNode2.add(treeNode3);
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode(
                kBundle.getString("MATCHING"));
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode(
                kBundle.getString("MULTIPLE CHOICE"));
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode(
                kBundle.getString("ORDER"));
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode(
                kBundle.getString("TRUE FALSE"));
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode(
                kBundle.getString("DESIGN ITEMS"));
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode(
                kBundle.getString("DIVIDER"));
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode(
                kBundle.getString("NUMBERING RESTART"));
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode(
                kBundle.getString("INSTRUCTIONS"));
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode(
                kBundle.getString("PAGE BREAK"));
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode(
                kBundle.getString("SECTION TITLE"));
        
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        jTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));  
    }
    
    private HashMap<String, String> generateMap()
    {
        HashMap<String, String> genMap = 
                new HashMap<String, String>();
        
        genMap.put(kBundle.getString("EXAM"), kHaltValue);
        genMap.put(kBundle.getString("QUESTION ITEMS"), kHaltValue);
        genMap.put(kBundle.getString("DESIGN ITEMS"), kHaltValue);
        
        genMap.put(TablePopupMenu.kDVName, "qsolutions.view.DividerView");
        genMap.put(TablePopupMenu.kFITBName
                , "qsolutions.view.FillInTheBlankView");
        genMap.put(TablePopupMenu.kInsName, "qsolutions.view.InstructionsView");
        genMap.put(TablePopupMenu.kFRName, "qsolutions.view.FreeResponseView");
        genMap.put(TablePopupMenu.kMCName
                , "qsolutions.view.MultipleChoiceView");
        genMap.put(TablePopupMenu.kMatName, "qsolutions.view.MatchingView");
        genMap.put(TablePopupMenu.kNRName
                , "qsolutions.view.NumberingRestartView");
        genMap.put(TablePopupMenu.kOrdName, "qsolutions.view.OrderView");
        genMap.put(TablePopupMenu.kPBName, "qsolutions.view.PageBreakView");
        genMap.put(TablePopupMenu.kSTName, "qsolutions.view.SectionTitleView");
        genMap.put(TablePopupMenu.kTFName, "qsolutions.view.TrueFalseView");
        
        return genMap;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        jTree = new javax.swing.JTree();

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Exam");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Question Items");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Fill-In-The-Blanks");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Free Response");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Matching");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Multiple Choice");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Order");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("True False");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Design Items");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Divider");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Numbering Restart");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Instructions");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Page Break");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Section Title");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        jTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        scrollPane.setViewportView(jTree);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree jTree;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Method executed when the component is opened
     */
    @Override
    public void componentOpened()
    {
        result = Utilities.actionsGlobalContext().lookupResult(ExamApi.class);
        result.addLookupListener(this);
    }

    /**
     * Method executed when the component is closed
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
     * Clears the selection from the jTree
     */
    public void clearSelection()
    {
        doChange = false;
        jTree.clearSelection();
        doChange = true;
    }
    
    /**
     * Method executed when a value is changed in the object
     * @param evt unused
     */
    @Override
    public void valueChanged(TreeSelectionEvent evt) 
    {
        // If the selection was not just cleared
        if (doChange)
        {
            DesignViewTopComponent dv =
                    (DesignViewTopComponent) 
                    WindowManager.getDefault().findTopComponent(
                    "DesignViewTopComponent");
            result = Lookup.getDefault().lookupResult(ExamApi.class);
            Collection<ExamApi> inst = 
                    (Collection<ExamApi>) result.allInstances();
            iexam = inst.iterator().next();

            TreePath evtPath = evt.getPath();

            String selected = (String) evtPath.getPath()[
                    evtPath.getPathCount() - 1].toString();

            DocumentItemView holder = null;
            String className;
            // If the dv is not changed stop
            if ( !dv.isChanged() )
            {
                return;
            }
 
            className = map.get(selected);
  
            // If an active DocumentItem was selected open it
            if ( className != null )
            {
                // If not the last item
                if (!className.equals(kHaltValue))
                {
                    try
                    {
                        holder = (DocumentItemView)Class.forName(className)
                                .newInstance();
                    }
                    catch(Exception ex)
                    {
                        System.out.println("Error: " + ex);
                    }
                    dv.openDocumentItem(holder);
                    dv.requestActive();
                    TableViewTopComponent tv = (TableViewTopComponent) 
                            WindowManager.getDefault()
                            .findTopComponent("TableViewTopComponent");

                    tv.clearSelection();
                }
            }
            else
            {
                javax.swing.JOptionPane.showMessageDialog( this, ( selected +
                        kBundle.getString(" HAS NOT YET BEEN IMPLEMENTED") ) );   
            }
        }
    }

    /**
     * Method executed when the result is changed
     * @param le unused
     */
    @Override
    public void resultChanged(LookupEvent le)
    {
    }
    
}
