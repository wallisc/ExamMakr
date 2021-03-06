/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.view;

import org.openide.util.Lookup;
import qsolutions.api.DocumentItemApi;
import qsolutions.api.NumberingRestartApi;

/**
 * Panel for adding and editing Dividers to the exam, subclass of DesignItemView
 *
 * @author Jake
 */
public class NumberingRestartView extends DesignItemView
{
    /**
     * Creates new form PageBreakDesignPanel
     */
    public NumberingRestartView()
    {
        super();
        item = Lookup.getDefault().lookup(NumberingRestartApi.class).newItem();
        setEditView(false);
        initComponents();
        fillFields();
        addDocumentItem.addFocusListener(this);
    }
    
    /**
     * Initializes the numbering restart view
     * @param nItem item to be defaulted to
     */
    public NumberingRestartView(NumberingRestartApi nItem) 
    {
        super();
        item = nItem;
        setEditView(true);
        initComponents();
        fillFields();
        addDocumentItem.setText(kBundle.getString("UPDATE NUMBERING RESTART"));
        addDocumentItem.addFocusListener(this);
    }

    /**
     * Fills the gui from inItem, sets isEditView to true
     *
     * @param inItem the item to load information from
     */
    protected void fillFields(DocumentItemApi inItem)
    {
        //no fields
        examNavigatorPanel.refreshView(isEditView());
    }
    
    /**
     * Adds the GUI's item to the exam
     */
    @Override
    public void addItem()
    {
        super.addItem();
        item = Lookup.getDefault().lookup(NumberingRestartApi.class).newItem();
        fillFields();
    }
    
    /**
     * Loads a new Numbering Restart when extracting
     *
     * @return a new Numbering Restart
     */
    @Override
    protected DocumentItemApi extractItem()
    {
        return Lookup.getDefault().lookup(NumberingRestartApi.class).newItem();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addDocumentItem = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        examNavigatorPanel = new ExamNavigatorPanel(this);

        addDocumentItem.setText(org.openide.util.NbBundle.getMessage(NumberingRestartView.class, "NumberingRestartView.addDocumentItem.text")); // NOI18N
        addDocumentItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDocumentItemActionPerformed(evt);
            }
        });

        jLabel2.setText(org.openide.util.NbBundle.getMessage(NumberingRestartView.class, "NumberingRestartView.jLabel2.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(examNavigatorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(addDocumentItem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(examNavigatorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(addDocumentItem)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addDocumentItemActionPerformed( java.awt.event.ActionEvent evt )//GEN-FIRST:event_addDocumentItemActionPerformed
    {//GEN-HEADEREND:event_addDocumentItemActionPerformed
        addItem();
    }//GEN-LAST:event_addDocumentItemActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addDocumentItem;
    private qsolutions.view.ExamNavigatorPanel examNavigatorPanel;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
