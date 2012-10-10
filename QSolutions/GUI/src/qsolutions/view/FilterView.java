/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JDialog;
import org.openide.util.Lookup;
import qsolutions.api.ExamApi;

/**
 * The the panel that is brought up when the user wants to filter categories and
 * levels
 * @author Chris
 */
public class FilterView extends javax.swing.JPanel
{

    private JDialog parent;
    private ArrayList<CheckBoxView> levelBoxes;
    private ArrayList<CheckBoxView> categoryBoxes;
    /** The text used to describe the empty string */
    public static final String kEmptyString = "<empty>";
     
    /**
     * Creates new form FilterView
     * @param nParent the parent dialog box
     */
    public FilterView(JDialog nParent)
    {
        ExamApi exam;
        initComponents();
        parent = nParent;
        filterLevel.setSelected(true);
        categoryPanel.setLayout(new GridLayout(0, 1));
        levelPanel.setLayout(new GridLayout(0, 1));
        exam = Lookup.getDefault().lookup(ExamApi.class);
        categoryBoxes = new ArrayList<CheckBoxView>();
        levelBoxes = new ArrayList<CheckBoxView>();
        CheckBoxView box;
        
        // For all the unique categories in the exam
        for (String category: exam.getCategories())
        {
            // If the category is the empty string, ignore it
            if (category.equals(""))
            {
                continue;
            }
            box = new CheckBoxView(category);
            categoryBoxes.add(box);
            categoryPanel.add(box);
        }
        box = new CheckBoxView(kEmptyString);
        categoryBoxes.add(box);
        categoryPanel.add(box);
        
        
        // for all the unique levels in the exam
        for (String level: exam.getLevels())
        {
            // If the level is the empty string, ignore it
            if (level.equals(""))
            {
                continue;
            }
            box = new CheckBoxView(level);
            levelBoxes.add(box);
            levelPanel.add(box);
        }
        box = new CheckBoxView("<empty>");
        levelBoxes.add(box);
        levelPanel.add(box);
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

        filterType = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        categoryPanel = new javax.swing.JPanel();
        levelPanel = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        filterButton = new javax.swing.JButton();
        categoryAll = new javax.swing.JCheckBox();
        levelAll = new javax.swing.JCheckBox();
        filterCatAndLevel = new javax.swing.JRadioButton();
        filterCategory = new javax.swing.JRadioButton();
        filterLevel = new javax.swing.JRadioButton();
        jSeparator2 = new javax.swing.JSeparator();
        filterEither = new javax.swing.JRadioButton();

        jLabel1.setText(org.openide.util.NbBundle.getMessage(FilterView.class, "FilterView.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(FilterView.class, "FilterView.jLabel2.text")); // NOI18N

        categoryPanel.setBackground(new java.awt.Color(255, 255, 255));
        categoryPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        categoryPanel.setForeground(new java.awt.Color(240, 240, 240));

        javax.swing.GroupLayout categoryPanelLayout = new javax.swing.GroupLayout(categoryPanel);
        categoryPanel.setLayout(categoryPanelLayout);
        categoryPanelLayout.setHorizontalGroup(
            categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        categoryPanelLayout.setVerticalGroup(
            categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 195, Short.MAX_VALUE)
        );

        levelPanel.setBackground(new java.awt.Color(255, 255, 255));
        levelPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout levelPanelLayout = new javax.swing.GroupLayout(levelPanel);
        levelPanel.setLayout(levelPanelLayout);
        levelPanelLayout.setHorizontalGroup(
            levelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
        );
        levelPanelLayout.setVerticalGroup(
            levelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 194, Short.MAX_VALUE)
        );

        cancelButton.setText(org.openide.util.NbBundle.getMessage(FilterView.class, "FilterView.cancelButton.text")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        filterButton.setText(org.openide.util.NbBundle.getMessage(FilterView.class, "FilterView.filterButton.text")); // NOI18N
        filterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterButtonActionPerformed(evt);
            }
        });

        categoryAll.setText(org.openide.util.NbBundle.getMessage(FilterView.class, "FilterView.categoryAll.text")); // NOI18N
        categoryAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryAllActionPerformed(evt);
            }
        });

        levelAll.setText(org.openide.util.NbBundle.getMessage(FilterView.class, "FilterView.levelAll.text")); // NOI18N
        levelAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                levelAllActionPerformed(evt);
            }
        });

        filterType.add(filterCatAndLevel);
        filterCatAndLevel.setText(org.openide.util.NbBundle.getMessage(FilterView.class, "FilterView.filterCatAndLevel.text")); // NOI18N

        filterType.add(filterCategory);
        filterCategory.setText(org.openide.util.NbBundle.getMessage(FilterView.class, "FilterView.filterCategory.text")); // NOI18N

        filterType.add(filterLevel);
        filterLevel.setText(org.openide.util.NbBundle.getMessage(FilterView.class, "FilterView.filterLevel.text")); // NOI18N

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        filterType.add(filterEither);
        filterEither.setText(org.openide.util.NbBundle.getMessage(FilterView.class, "FilterView.filterEither.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(levelAll)
                            .addComponent(levelPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(categoryAll)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(categoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(filterLevel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(filterCategory)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(filterCatAndLevel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(filterEither)
                                .addGap(0, 34, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(filterButton)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(levelAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(levelPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(categoryAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(categoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filterCatAndLevel)
                    .addComponent(filterCategory)
                    .addComponent(filterLevel)
                    .addComponent(filterEither))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filterButton)
                    .addComponent(cancelButton))
                .addContainerGap(75, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // GEN-END

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelButtonActionPerformed
    {//GEN-HEADEREND:event_cancelButtonActionPerformed
        parent.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void filterButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_filterButtonActionPerformed
    {//GEN-HEADEREND:event_filterButtonActionPerformed
        ArrayList<String> levels = new ArrayList<String>();
        ArrayList<String> categories = new ArrayList<String>();
        String[] levArr, catArr;
        int[] filteredRows;
        ExamApi exam = Lookup.getDefault().lookup(ExamApi.class);
        boolean filLevel = filterLevel.isSelected();
        boolean filCat = filterCategory.isSelected();
        // For all the category boxes
        for (CheckBoxView box : categoryBoxes)
        {
            // If the checkbox is selected or the user wants all levels
            if ( box.isSelected() || filLevel )
            {
                // If the empty string box is checked
                if (box.getText().equals(kEmptyString))
                {
                    categories.add("");
                }
                else
                {
                    categories.add(box.getText());
                }
            }
        }
        // For all the level boxes
        for (CheckBoxView box : levelBoxes)
        {
            // If the box is set to be filtered
            if ( box.isSelected() || filCat )
            {
                // If the empty string box is checked
                if (box.getText().equals(kEmptyString))
                {
                    levels.add("");
                }
                else
                {
                    levels.add(box.getText());
                }
            }
        }
        levArr = new String[levels.size()];
        catArr = new String[categories.size()];
        
        levels.toArray(levArr);
        categories.toArray(catArr);
        filteredRows = exam.getLevelAndCategoryIndexes(levArr, catArr,
                    !filterEither.isSelected());

        exam.filterActive(filteredRows);
        parent.dispose();
    }//GEN-LAST:event_filterButtonActionPerformed

    private void categoryAllActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_categoryAllActionPerformed
    {//GEN-HEADEREND:event_categoryAllActionPerformed
        for (CheckBoxView box : categoryBoxes)
        {
            box.setSelected(categoryAll.isSelected());
        }
    }//GEN-LAST:event_categoryAllActionPerformed

    private void levelAllActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_levelAllActionPerformed
    {//GEN-HEADEREND:event_levelAllActionPerformed
        for (CheckBoxView box : levelBoxes)
        {
            box.setSelected(levelAll.isSelected());
        }
    }//GEN-LAST:event_levelAllActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JCheckBox categoryAll;
    private javax.swing.JPanel categoryPanel;
    private javax.swing.JButton filterButton;
    private javax.swing.JRadioButton filterCatAndLevel;
    private javax.swing.JRadioButton filterCategory;
    private javax.swing.JRadioButton filterEither;
    private javax.swing.JRadioButton filterLevel;
    private javax.swing.ButtonGroup filterType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JCheckBox levelAll;
    private javax.swing.JPanel levelPanel;
    // End of variables declaration//GEN-END:variables
}
