/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javax.swing.JDialog;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import qsolutions.action.TablePopupMenu;
import qsolutions.api.DocumentItemApi;
import qsolutions.api.ExamApi;
import qsolutions.api.ExamHandlerApi;
import qsolutions.view.ExamTableModel;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//qsolutions.gui//TableView//EN",
autostore = false)
@TopComponent.Description(preferredID = "TableViewTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@ActionID(category = "Window", id = "qsolutions.gui.TableViewTopComponent")
@ActionReference(path = "Menu/MyWindow" /*
 * , position = 333
 */)
@TopComponent.OpenActionRegistration(displayName = "qsolutions.gui.Bundle#TABLEVIEW",
preferredID = "TableViewTopComponent")
@Messages({
    "CTL_TableViewAction=TableView",
    "CTL_TableViewTopComponent=Table View",
    "HINT_TableViewTopComponent=View all of the document items in your exam"
})
public final class TableViewTopComponent extends TopComponent
    implements Observer
{
    
    final static int kActiveCol = 0;
    final static int kItemCol = 1;
    final static int kNumberCol = 2;
    final static int kTypeCol = 3;
    final static int kTextCol = 4;
    final static int kMarksCol = 5;
    final static int kLevelCol = 6;
    final static int kCategoryCol = 7;
    final static int kPictureCol = 8;
    private ExamApi exam;
    private int anchoredRow;
    private int highlightEndRow;
    private static final int kHeightSpacer = 100;
    private boolean needsRefresh = false;
    // Triggered if the user hit shift while dragging the mouse
    private boolean shiftHit;
    /**
     * Creates new form TableView
     */
    private ExamHandlerApi examHandler;
    
    private static final String kBundleName = "qsolutions/gui/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);

    private void tableDelete() 
    {        
        anchoredRow = table.getSelectedRow();
        exam.removeItem(anchoredRow);
        refreshTable();
        // if the last row is selected
        if (anchoredRow == table.getRowCount())
        {
            anchoredRow--;
        }
        // if a valid row is selected
        if ( anchoredRow >= 0 )
        {
            table.setRowSelectionInterval(anchoredRow, anchoredRow);
            table.showEditPanel(anchoredRow);
        }
        else
        {
            table.showEditPanel(-1);
        }
    }

    private void cutCopyPaste(KeyEvent evt, int keyCode) 
    {
        // Check the key that is down
        if ((evt.isControlDown() || evt.isMetaDown()) && keyCode
                == java.awt.event.KeyEvent.VK_C)
        {
            copyActionPerformed(null);
        }
        // If the control key is being held and the 'v' key is pushed 
        else if ((evt.isControlDown() || evt.isMetaDown()) && keyCode
                == java.awt.event.KeyEvent.VK_V)
        {
            pasteActionPerformed(null);
        }
        // If the control key is being held and the 'x' key is pushed 
        else if ((evt.isControlDown() || evt.isMetaDown()) && keyCode
                == java.awt.event.KeyEvent.VK_X)
        {
            cutActionPerformed(null);
        }
    }

    private void singleSelectedRow(int keyCode, KeyEvent evt) 
    {    
        // If the delete key was hit
        if (keyCode == java.awt.event.KeyEvent.VK_BACK_SPACE || keyCode
                == java.awt.event.KeyEvent.VK_DELETE)
        {
            // If a row is selected and items exist in the table
            if (table.getSelectedRow() >= 0 && table.getSelectedRow()
                    < table.getRowCount())
            {
                tableDelete();
            }
        }
        /*
            * If up key was hit and the row being selected doesn't go past the
            * top of the table
            */
        // if the user hit the up key and the exam has items in it
        else if (keyCode == java.awt.event.KeyEvent.VK_UP &&
                table.getSelectedRow() > 0)
        {
            anchoredRow = table.getSelectedRow();
            table.showEditPanel(anchoredRow);
        }
        // If the down key was hit and the row being selected doesn't go
        // past the bottom of the table
        else if (keyCode == java.awt.event.KeyEvent.VK_DOWN && 
                table.getSelectedRow() + 1 < table.getRowCount())
        {
            anchoredRow = table.getSelectedRow();
            table.showEditPanel(anchoredRow);
        }
        // If the control key is being held and the 'c' key is pushed
        else
        {
            cutCopyPaste(evt, keyCode);
        }
    }
    
    /**
     * MouseAdaptor that pops up TablePopupMenu when the user right clicks on
     * the table
     */
    private class PopupClickListener extends MouseAdapter 
    {
        private ActionListener parent;
        public PopupClickListener(ActionListener nParent)
        {
            parent = nParent;
        }
        
        @Override
        public void mousePressed(MouseEvent evt)
        {
            // If the event is a right click
            if ( SwingUtilities.isRightMouseButton( evt ) )
            {
                // get the coordinates of the mouse click
                Point point = evt.getPoint();

                // get the row index that contains that coordinate
                int rowNumber = table.rowAtPoint( point );

                // Get the ListSelectionModel of the JTable
                ListSelectionModel model = table.getSelectionModel();

                // set the selected interval of rows. Using the "rowNumber"
                // variable for the beginning and end selects only that one row.
                model.setSelectionInterval( rowNumber, rowNumber );
            }
            // If the event is a popup trigger
            if (evt.isPopupTrigger())
            {
                doPop(evt);
            }
        }

        @Override
        public void mouseReleased(MouseEvent evt)
        {
            // If the event is a pop trigger
            if (evt.isPopupTrigger())
            {
                doPop(evt);
            }
        }

        private void doPop(MouseEvent evt)
        {
            TablePopupMenu menu = new TablePopupMenu(parent);
            menu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }
    
    /**
     * Top component of the table view
     */
    public TableViewTopComponent()
    {
        initComponents();
        setName(Bundle.CTL_TableViewTopComponent());
        setDisplayName(kBundle.getString("TABLE VIEW"));
        setToolTipText(kBundle.getString("TABLE VIEW TT"));
        exam = Lookup.getDefault().lookup(ExamApi.class);
        table.init();
        refreshTable();
        examHandler = 
            Lookup.getDefault().lookup(ExamHandlerApi.class);
        exam.addObserver(this);
        table.addMouseListener(new PopupClickListener(table));
    }
    
    /**
     * Add a document item to the table
     * @param toAdd Document item to add
     */
    public void addDocumentItem(DocumentItemApi toAdd)
    {
        exam.addItem( toAdd );
        refreshTable();
    }
    
    /**
     * Replaces a document item with another one in the table
     * @param toRemove The document item to be replaced
     * @param toAdd The new document item
     */
    public void editDocumentItem(DocumentItemApi toRemove, DocumentItemApi toAdd)
    {
        exam.replaceItem( toRemove, toAdd );
        refreshTable();
    }

    /**
     * Refreshes the table.
     * @param obs the observable object
     * @param args an argument passed to the notifyObservers method
     */
    @Override
    public void update(Observable obs, Object args)
    {
        refreshTable();
        //If the exam has not already been edited, set edited to true
        if (!examHandler.getEdited())
        {
            examHandler.setEdited( true );
        }
            
    }

    /**
     * Returns an int representing the selected index
     * @return the selected index
     */
    public int getAnchoredRow()
    {
        return anchoredRow;
    }
    
    /**
     * Selects the first DocumentItem in the exam
     * @return -1 if there are no rows, otherwise 0
     */
    public int selectFirst()
    {
        int ret = -1;
        // If there are items in the exam, select the first
        if (table.getRowCount() > 0)
        {
            anchoredRow = 0;
            table.showEditPanel(anchoredRow);
            table.setRowSelectionInterval(anchoredRow, anchoredRow);
            ret = anchoredRow;
            clearAddTreeSelection();
        }
        return ret;
    }

    /**
     * Selects the previous DocumentItem in the exam
     * @return -1 if there are no rows, otherwise the selected index
     */
    public int selectPrevious()
    {
        int ret = -1;
        // If the table has items
        if (table.getRowCount() > 0)
        {
            // If there are no rows selected and the last recorded selected row
            // was valid
            if (table.getSelectedRowCount() == 0 && anchoredRow >= 0)
            {
                table.setRowSelectionInterval(anchoredRow, anchoredRow);
                
            }

            // If one item is selected and the first isn't, select the previous
            if (table.getSelectedRowCount() == 1 && table.getSelectedRow() > 0)
            {
                anchoredRow = table.getSelectedRow() - 1;
                table.showEditPanel(anchoredRow);
                table.setRowSelectionInterval(anchoredRow, anchoredRow);
                ret = anchoredRow;
                clearAddTreeSelection();
            }
        }
        return ret;
    }

    /**
     * Selects the next DocumentItem in the exam
     * @return -1 if there are no rows, otherwise the selected index
     */
    public int selectNext()
    {
        int ret = -1;
        // If the exam has 1 or more items
        if (table.getRowCount() > 0)
        {
            // If there are no rows selected and the last recorded selected row
            // was valid
            if (table.getSelectedRowCount() == 0 && anchoredRow >= 0)
            {
                table.setRowSelectionInterval(anchoredRow, anchoredRow);
            }


            // If one item is selected and the last is not selected, select the next
            if (table.getSelectedRowCount() == 1 && table.getSelectedRow() + 1
                    < table.getRowCount())
            {
                anchoredRow = table.getSelectedRow() + 1;
                table.showEditPanel(anchoredRow);
                table.setRowSelectionInterval(anchoredRow, anchoredRow);
                ret = anchoredRow;
                clearAddTreeSelection();
            }
        }
        return ret;
    }

    /**
     * Selects the last DocumentItem in the exam
     * @return -1 if there are no rows, otherwise table.getRowCount() - 1
     */
    public int selectLast()
    {
        int ret = -1;
        // if there are items in the exam, select the last
        if (table.getRowCount() > 0)
        {
            anchoredRow = table.getRowCount() - 1;
            table.showEditPanel(anchoredRow);
            table.setRowSelectionInterval(anchoredRow, anchoredRow);
            ret = anchoredRow;
            clearAddTreeSelection();
        }
        return ret;
    }
    
    /**
     * Clears the selection in the table if nothing should be highlighted.
     */
    public void clearSelection()
    {
        anchoredRow = -1;
        table.clearSelection();
    }
    
    private void clearAddTreeSelection()
    {
        AddTreeTopComponent at = (AddTreeTopComponent) 
                WindowManager.getDefault()
                .findTopComponent("AddTreeTopComponent");
        at.clearSelection();
    }
    
    /**
     * Updates the table contents
     */
    public void refreshTable()
    {
        needsRefresh = false;
        table.refreshTable();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        randomButton = new javax.swing.JButton();
        up = new javax.swing.JButton();
        down = new javax.swing.JButton();
        cut = new javax.swing.JButton();
        paste = new javax.swing.JButton();
        copy = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        table = new qsolutions.view.ExamTable();
        filterButton = new javax.swing.JButton();

        randomButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/shuffle48.png"))); // NOI18N
        randomButton.setToolTipText(org.openide.util.NbBundle.getMessage(TableViewTopComponent.class, "TableViewTopComponent.randomButton.toolTipText")); // NOI18N
        randomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomButtonActionPerformed(evt);
            }
        });

        up.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/upArrow48.png"))); // NOI18N
        up.setToolTipText(org.openide.util.NbBundle.getMessage(TableViewTopComponent.class, "TableViewTopComponent.up.toolTipText")); // NOI18N
        up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upActionPerformed(evt);
            }
        });

        down.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/downArrow48.png"))); // NOI18N
        down.setToolTipText(org.openide.util.NbBundle.getMessage(TableViewTopComponent.class, "TableViewTopComponent.down.toolTipText")); // NOI18N
        down.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downActionPerformed(evt);
            }
        });

        cut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cut.png"))); // NOI18N
        cut.setToolTipText(org.openide.util.NbBundle.getMessage(TableViewTopComponent.class, "TableViewTopComponent.cut.toolTipText")); // NOI18N
        cut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutActionPerformed(evt);
            }
        });

        paste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/paste.png"))); // NOI18N
        paste.setToolTipText(org.openide.util.NbBundle.getMessage(TableViewTopComponent.class, "TableViewTopComponent.paste.toolTipText")); // NOI18N
        paste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteActionPerformed(evt);
            }
        });

        copy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/copy.png"))); // NOI18N
        copy.setToolTipText(org.openide.util.NbBundle.getMessage(TableViewTopComponent.class, "TableViewTopComponent.copy.toolTipText")); // NOI18N
        copy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyActionPerformed(evt);
            }
        });

        table.setModel(new ExamTableModel());
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableMouseReleased(evt);
            }
        });
        table.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tableMouseDragged(evt);
            }
        });
        table.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablePropertyChange(evt);
            }
        });
        table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tableKeyPressed(evt);
            }
        });
        scrollPane.setViewportView(table);

        filterButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/filter.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(filterButton, org.openide.util.NbBundle.getMessage(TableViewTopComponent.class, "TableViewTopComponent.filterButton.text")); // NOI18N
        filterButton.setToolTipText(org.openide.util.NbBundle.getMessage(TableViewTopComponent.class, "TableViewTopComponent.filterButton.toolTipText")); // NOI18N
        filterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(up, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(down, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cut, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(copy, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paste, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(randomButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(copy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(randomButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(down, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(up, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(paste, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(filterButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void randomButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_randomButtonActionPerformed
    {//GEN-HEADEREND:event_randomButtonActionPerformed
        JDialog dialog = new JDialog((Frame)null, "Randomize Options");
        qsolutions.view.RandomizeView dPane = new 
           qsolutions.view.RandomizeView(dialog);
        dialog.getContentPane().add(dPane);
        dialog.setSize(dPane.getPreferredSize());
        dialog.setVisible(true);      
    }//GEN-LAST:event_randomButtonActionPerformed

    private void upActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_upActionPerformed
    {//GEN-HEADEREND:event_upActionPerformed
        table.moveRow(table.getSelectedRows(), -1);
    }//GEN-LAST:event_upActionPerformed

    private void downActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_downActionPerformed
    {//GEN-HEADEREND:event_downActionPerformed
        table.moveRow(table.getSelectedRows(), 1);
    }//GEN-LAST:event_downActionPerformed

    private void cutActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cutActionPerformed
    {//GEN-HEADEREND:event_cutActionPerformed
        int[] selectedRows = table.getSelectedRows();
        // If the user has rows selected
        if (selectedRows != null && selectedRows.length > 0 
                && table.getRowCount() > 0)
        {
            exam.cut(selectedRows);
            // If the table is not empty
            if (table.getRowCount() > 0)
            {
                // If the selected row is in the table
                if (selectedRows[0] < table.getRowCount() - 1)
                {
                    anchoredRow = selectedRows[0];
                }
                // if there's still rows left in the table
                else
                {
                    anchoredRow = table.getRowCount() - 1;
                }
                table.setRowSelectionInterval(anchoredRow, anchoredRow);
                table.showEditPanel(anchoredRow);
            }
            else
            {
                table.showEditPanel(-1);
            }
        }
    }//GEN-LAST:event_cutActionPerformed

    private void pasteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_pasteActionPerformed
    {//GEN-HEADEREND:event_pasteActionPerformed
        int[] pastedRows;
        int[] selectedRows = table.getSelectedRows();
        // If items are selected in the table
        if (table.getSelectedRows() != null)
        {
            // If 1 or more items are selected
            if (selectedRows.length > 0)
            {
                anchoredRow = selectedRows[selectedRows.length - 1];
            }
            else
            {
                anchoredRow = -1;
            }
            pastedRows = exam.paste(anchoredRow);
            refreshTable();
            // If the user is pasting items
            if (pastedRows.length > 0)
            {
                anchoredRow = pastedRows[0];
                table.setRowSelectionInterval(pastedRows[0],
                        pastedRows[pastedRows.length - 1]);
                table.showEditPanel(pastedRows[0]);
            }
        }
    }//GEN-LAST:event_pasteActionPerformed

    private void copyActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_copyActionPerformed
    {//GEN-HEADEREND:event_copyActionPerformed
        int[] selectedRows = table.getSelectedRows();
        // If the user has rows selected
        if (selectedRows != null && selectedRows.length > 0)
        {
            exam.copy(selectedRows);
        }
    }//GEN-LAST:event_copyActionPerformed

    private void filterButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_filterButtonActionPerformed
    {//GEN-HEADEREND:event_filterButtonActionPerformed
        Dimension defaultSize;
        JDialog dialog = new JDialog((Frame)null, "Filter Quiz");
        
        qsolutions.view.FilterView dPane = new 
           qsolutions.view.FilterView(dialog);
        defaultSize = dPane.getPreferredSize();
        defaultSize.height += kHeightSpacer;
        dPane.setSize(defaultSize);
        dialog.getContentPane().add(dPane);
        
        dialog.setSize(dPane.getPreferredSize());
        dialog.setVisible(true);  
    }//GEN-LAST:event_filterButtonActionPerformed

    private void tableKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_tableKeyPressed
    {//GEN-HEADEREND:event_tableKeyPressed
        int keyCode = evt.getKeyCode();
        //if ( keyCode == java.awt.event.KeyEvent.VK_SHIFT )
        //return;
        // If only one row is selected
        if (table.getSelectedRowCount() == 1)
        {
            singleSelectedRow(keyCode, evt);
        }
        // If more than one row is selected
        else if (table.getSelectedRowCount() > 1)
        {
            //parent.setPositionLabel("");
            int[] rows = table.getSelectedRows();
            Arrays.sort(rows);
            
            // If the user isn't holding down the shift key
            if(!evt.isShiftDown())
            {
                anchoredRow = rows[0];
            }
            
            // If the delete key was hit
            if (keyCode == java.awt.event.KeyEvent.VK_BACK_SPACE || keyCode
                    == java.awt.event.KeyEvent.VK_DELETE)
            {
                exam.removeItems(rows);
                refreshTable();
                // If there are rows left in the table select the location
                // where the delete occured 
                if (table.getRowCount() > 0)
                {
                    // Decrement if row is greater than total rows
                    if (anchoredRow >= table.getRowCount())
                    {
                        anchoredRow = table.getRowCount() - 1;
                    }
                    table.setRowSelectionInterval(anchoredRow, anchoredRow);
                    table.showEditPanel(anchoredRow);
                }
                else
                {
                    table.showEditPanel(-1);
                }
            }
            else
            {
                cutCopyPaste(evt, keyCode);
            }
        }
    }//GEN-LAST:event_tableKeyPressed

    private void tablePropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_tablePropertyChange
    {//GEN-HEADEREND:event_tablePropertyChange
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        DocumentItemApi docItem = null;
        // If the column selected is the Active column
        if (row > -1 && row < table.getRowCount())
        {
            // If the changed item was the active check box
            if (col == kActiveCol)
            {
                table.clearSelection();
                docItem = exam.getItemAt(row);
                docItem.setActive((Boolean) table.getValueAt(row, col));
                exam.updateList();
                needsRefresh = true;
            }
        }
        // if the table needs to be refreshed
        if (needsRefresh)
        {
            refreshTable();
        }
    }//GEN-LAST:event_tablePropertyChange

    /**
     * Actions that respond while pressing on the mouse and pressing shift
     * @param selectedRows The selected rows in the table
     */
    private void tableMousePressedShift(int[] selectedRows)
    {
        // If the user moved the mouse
        if (table.getSelectedRows().length > 1)
        {
            // If the user dragged the mouse up
            if (anchoredRow > selectedRows[0] && selectedRows[0] != highlightEndRow)
            {
                table.setRowSelectionInterval(selectedRows[0], anchoredRow);
                highlightEndRow = selectedRows[0];
            }
            // Else the user dragged the mouse down
            else
            {
                highlightEndRow = selectedRows[selectedRows.length - 1];
                table.setRowSelectionInterval(anchoredRow, highlightEndRow);
            }
        }
    }

    /**
     * Actions that respond while pressing on the mouse
     * @param selectedRows The selected rows in the table
     */
    private void tableMousePressedNoShift(int[] selectedRows)
    {
        highlightEndRow = anchoredRow;
        // If the user moved the mouse
        if (table.getSelectedRows().length > 1)
        {
            // If the user dragged the mouse up
            if (anchoredRow > selectedRows[0])
            {
                anchoredRow = selectedRows[0];
            }
            // Else the user dragged the mouse down
            else
            {
                anchoredRow = selectedRows[selectedRows.length - 1];
            }
        }
        else
        {
            //Check for right click
            if (selectedRows.length != 0)
            {
                anchoredRow = selectedRows[0];
            }
        }

        // if a valid row is selected
        if (anchoredRow >= 0 && anchoredRow < table.getRowCount())
        {
            table.showEditPanel(anchoredRow);
            clearAddTreeSelection();
            
            //parent.setPositionLabel((table.getSelectedRow() + 1) + " of "
            //      + numOfItems);
        }
        else
        {
            //parent.setPositionLabel("");
        }
        table.setRowSelectionInterval(anchoredRow, anchoredRow);
    }
    
    private void tableMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_tableMousePressed
    {//GEN-HEADEREND:event_tableMousePressed
        int[] selectedRows = table.getSelectedRows();
        // If an invalid area in the table was selected
        if (selectedRows == null || selectedRows.length == 0 || 
                (evt.isControlDown() || evt.isMetaDown()))
        {
            return;
        }
        // If the user has hit the shift key
        if (evt.isShiftDown())
        {
            tableMousePressedShift(selectedRows);
        }            
        else
        {
            tableMousePressedNoShift(selectedRows);
        }
    }//GEN-LAST:event_tableMousePressed

    private void tableMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_tableMouseDragged
    {//GEN-HEADEREND:event_tableMouseDragged
        int[] selectedRows = table.getSelectedRows();
        int lastRowIdx = selectedRows.length - 1;
        int newSelection;

         // If an invalid area in the table was selected
        if (selectedRows == null || selectedRows.length == 0 )
        {
            return;
        }
        
        // If the user has hit the shift key
        if (evt.isShiftDown())
        {
            shiftHit = true;
        }
        // If the user has dragged the mouse over several items
        if (lastRowIdx > 0)
        {
            // If the user has hit the shift key
            if (shiftHit)
            {
                //do the multiselection
            }
            // If the user has highlighted to the bottom of the test
            else if (selectedRows[lastRowIdx] >= table.getRowCount())
            {
                table.setRowSelectionInterval(anchoredRow, anchoredRow);
                highlightEndRow = anchoredRow;
            }
            else
            {
                // If the user dragged the mouse up
                if (anchoredRow > selectedRows[0])
                {
                    newSelection = selectedRows[0];
                }
                // Else the user dragged the mouse down
                else
                {
                    newSelection = selectedRows[lastRowIdx];
                }
                exam.moveGroup(anchoredRow, anchoredRow, newSelection);
                refreshTable();
                table.setRowSelectionInterval(newSelection, newSelection);
                anchoredRow = newSelection;
                highlightEndRow = anchoredRow;
            }
        }
        else
        {
            highlightEndRow = anchoredRow;
            // If the anchored row is valid
            if ( anchoredRow >= 0 && anchoredRow < table.getRowCount() )
            {
                table.setRowSelectionInterval(anchoredRow, anchoredRow);
            }
        }
    }//GEN-LAST:event_tableMouseDragged

    private void tableMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_tableMouseReleased
    {//GEN-HEADEREND:event_tableMouseReleased
        shiftHit = false;
    }//GEN-LAST:event_tableMouseReleased

    /**
     * Randomizes the exam items and has the table act appropriately
     * @param randAll whether to randomize all the items
     * @param randSel whether to randomize the selected items
     * @param randMC whether to randomize the multiple choice answers
     * @param randOrd whether to randomize the order question items
     * @param randMat whether to randomize the matching question items
     */
    public void randomize(boolean randAll, boolean randSel, boolean randMC,
            boolean randOrd, boolean randMat)
    {
        int[] rows = table.getSelectedRows();
        // If the user wants to randomize all the items
        if ( randAll )
        {
            exam.randomizeAll(randMC, randOrd, randMat);
        }
        // If the user only wants to randomize certain question answers
        else if ( !randSel )
        {
            exam.randomizeAnswers(randMC, randOrd, randMat);
        }
        // If the table has items in it
        else if (table.getRowCount() > 0)
        {
            // If more then one row is selected
            if (table.getSelectedRowCount() >= 2)
            {
                exam.randomizeIndexes(rows, randMC, randOrd, randMat);
            }
        }

    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton copy;
    private javax.swing.JButton cut;
    private javax.swing.JButton down;
    private javax.swing.JButton filterButton;
    private javax.swing.JButton paste;
    private javax.swing.JButton randomButton;
    private javax.swing.JScrollPane scrollPane;
    private qsolutions.view.ExamTable table;
    private javax.swing.JButton up;
    // End of variables declaration//GEN-END:variables
    /**
     * Code executed when the component is opened
     */
    @Override
    public void componentOpened()
    {
        // TODO add custom code on component opening
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
}
