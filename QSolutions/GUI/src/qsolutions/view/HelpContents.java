/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.view;

import java.util.ResourceBundle;
import qsolutions.view.help.*;

/**
 * Help table of contents to assist the user
 */
public class HelpContents extends javax.swing.JPanel 
{
    private static final String kBundleName = "qsolutions/view/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);

    /**
     * Creates new form HelpContents
     */
    public HelpContents() 
    {
        initComponents();
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new 
                javax.swing.tree.DefaultMutableTreeNode("TestMakr");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new 
                javax.swing.tree.DefaultMutableTreeNode(kBundle.getString(
                    "ITEM TABLE"));
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new 
                javax.swing.tree.DefaultMutableTreeNode(kBundle.getString(
                    "RANDOMIZE"));
        treeNode2.add(treeNode3);
        treeNode3 = new 
                javax.swing.tree.DefaultMutableTreeNode(kBundle.getString(
                    "ROW MOVEMENTS"));
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        helpDirectory.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
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

        TitleLabel = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        helpDirectory = new javax.swing.JTree();
        Description = new javax.swing.JScrollPane();

        TitleLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        TitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TitleLabel.setText(org.openide.util.NbBundle.getMessage(HelpContents.class, "HelpContents.TitleLabel.text")); // NOI18N

        jSplitPane1.setDividerLocation(150);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("TestMakr");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Item Table");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Randomize");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Row Movements");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        helpDirectory.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        helpDirectory.setMaximumSize(new java.awt.Dimension(110, 50));
        helpDirectory.setPreferredSize(new java.awt.Dimension(110, 50));
        helpDirectory.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                helpDirectoryhelpDirectoryValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(helpDirectory);

        jSplitPane1.setLeftComponent(jScrollPane1);
        jSplitPane1.setRightComponent(Description);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TitleLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TitleLabel)
                .addContainerGap(324, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(55, Short.MAX_VALUE)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(16, 16, 16)))
        );
    }// </editor-fold>//GEN-END:initComponents
    // GEN-END

    private void helpDirectoryhelpDirectoryValueChanged(javax.swing.event.TreeSelectionEvent evt)//GEN-FIRST:event_helpDirectoryhelpDirectoryValueChanged
    {//GEN-HEADEREND:event_helpDirectoryhelpDirectoryValueChanged
        /**
         * Returns the last path element of the selection. This method is useful
         * only when the selection model allows a single selection.
         */
        String selected =
                helpDirectory.getLastSelectedPathComponent().toString();
        javax.swing.JPanel holder;
        holder = null;
        // loads the help selected by the user
        if (selected.equals(kBundle.getString("ROW MOVEMENTS"))) 
        {
            holder = new RowMovementHelp();
        } // if the user wants to know about randomizing
        else if (selected.equals(kBundle.getString("RANDOMIZE"))) 
        {
            holder = new RandomizeHelp();
        } 
        else 
        {
            holder = new TableOfContentsHelp();
        }
        Description.setViewportView(holder);
    }//GEN-LAST:event_helpDirectoryhelpDirectoryValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane Description;
    private javax.swing.JLabel TitleLabel;
    private javax.swing.JTree helpDirectory;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables
}