/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.openide.util.Lookup;
import org.openide.windows.WindowManager;
import qsolutions.action.TablePopupMenu;
import qsolutions.api.*;
import qsolutions.exam.*;
import qsolutions.gui.DesignViewTopComponent;

/**
 * Custom JTable for TableViewTopComponent to display the Exam with
 */
public class ExamTable extends JTable implements ActionListener
{
    private static final String kBundleName = "qsolutions/view/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    
    private ExamTableModel tableModel;
    private ExamApi exam;
    private final static String kConfig = "." + File.separator +
            kBundle.getString("CONFIG_FILE");
    private boolean init;
    private Map<String, DocumentItemApi> map;
    
    /**
     * Default constructor for ExamTable
     */
    public ExamTable()
    {
        super();
        exam = Lookup.getDefault().lookup(ExamApi.class);
        init = false;
        map = generateMap();
    }
    
    private Map<String, DocumentItemApi> generateMap()
    {
        Map<String, DocumentItemApi> genMap = 
                new HashMap<String, DocumentItemApi>();
        
        genMap.put(TablePopupMenu.kDVName, new Divider());
        genMap.put(TablePopupMenu.kFITBName, new FillInTheBlank());
        genMap.put(TablePopupMenu.kInsName, new Instructions());
        genMap.put(TablePopupMenu.kFRName, new FreeResponse());
        //Add the List DocumentItem type
        //genMap.put(TablePopupMenu.kListName, new List());
        genMap.put(TablePopupMenu.kMCName, new MultipleChoice());
        genMap.put(TablePopupMenu.kMatName, new Matching());
        genMap.put(TablePopupMenu.kNRName, new NumberingRestart());
        genMap.put(TablePopupMenu.kOrdName, new Order());
        genMap.put(TablePopupMenu.kPBName, new PageBreak());
        genMap.put(TablePopupMenu.kSTName, new SectionTitle());
        genMap.put(TablePopupMenu.kTFName, new TrueFalse());
        
        return genMap;
    }
    
    /**
     * Restores column order and width if it has been changed
     */
    public void init()
    {
        File file = new File(kConfig);
        // If there is a config file for the column set up
        if (file.exists())
        {
            try
            {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fis);
                
                String[] names = (String[]) in.readObject();
                Map<String, Integer> widths = 
                        (Map<String, Integer>) in.readObject();
                
                in.close();
                
                TableColumnModel tcm = getColumnModel();
                
                // For every key in widths set the column width
                for (String itr : widths.keySet())
                {
                    tcm.getColumn(tcm.getColumnIndex(itr))
                            .setPreferredWidth(widths.get(itr));
                }
                
                // For every column order it according to the saved array's order
                for (int itr = 0; itr < names.length; itr++)
                {
                    moveColumn(tcm.getColumnIndex(names[itr]), itr);
                }
                
                refreshTable();
            }
            catch (IOException except)
            {
                System.err.println("Error loading from file.");
            }
            catch (ClassNotFoundException except)
            {
                System.err.println("Object in file was not an 'exam'.");
            }
            catch (NullPointerException ex)
            {
                System.err.println("Cannot process null files.");
            }
        }
        init = true;
    }
    
    /**
     * Call super and if the model is an ExamTableModel set tableModel to model
     * @param model the new TableModel
     */
    @Override
    public void setModel(TableModel model)
    {
        super.setModel(model);
        // If model is an ExamTableModel
        if (model instanceof ExamTableModel)
        {
            tableModel = (ExamTableModel) model;
        }
    }
    
    /**
     * Displays the information from exam in the table
     */
    public void refreshTable()
    {
        tableModel.emptyTable();
        // for every item in exam
        for (DocumentItemApi itr : exam.getItems())
        {
            // if DesignItem use DesignItem
            if (itr instanceof DesignItemApi)
            {
                addItem((DesignItemApi) itr);
            }
            // otherwise use Question
            else
            {
                addItem((QuestionApi) itr);
            }
        }
        
        revalidate();
    }
    
    /**
     * Adds the DocumentItemApi to the table
     *
     * @param toAdd the DocumentItemApi to add
     */
    public void addItem(DocumentItemApi toAdd)
    {
        // Determines if the item should be added to design or question api's
        if (toAdd instanceof DesignItemApi)
        {
            addDesignItem((DesignItemApi) toAdd);
        }
        // Adds if the api is a questionApi
        else if (toAdd instanceof QuestionApi)
        {
            addQuestion((QuestionApi) toAdd);
        }
    }
    
    /**
     * Adds the DesignItem to the table
     *
     * @param toAdd the DesignItem to add
     */
    private void addDesignItem(DesignItemApi toAdd)
    {
        tableModel.addRow();
            
        int row = getRowCount() - 1;

        tableModel.setValueAt(toAdd.isActive(), row,
                kBundle.getString("ACTIVE"));
        tableModel.setValueAt(getRowCount(), row, kBundle.getString("ITEM"));
        tableModel.setValueAt(null, row, kBundle.getString("QUESTION"));
        tableModel.setValueAt(toAdd.getAbbreviation(), row,
                kBundle.getString("TYPE"));
        tableModel.setValueAt(null, row, kBundle.getString("MARKS"));
        tableModel.setValueAt(null, row, kBundle.getString("LEVEL"));
        tableModel.setValueAt(null, row, kBundle.getString("CATEGORY"));
        tableModel.setValueAt(toAdd.getImage() != null, row,
                kBundle.getString("PICTURE"));
        tableModel.setValueAt(toAdd.getText(), row, kBundle.getString("TEXT"));

        revalidate();
    }
    
    /**
     * Adds the Question to the table
     *
     * @param toAdd the Question to add
     */
    private void addQuestion(QuestionApi toAdd)
    {
        // If adding a question that is NOT the first question 
        if (toAdd.getQNum() > 0)
        {
            tableModel.addRow();
            
            int row = getRowCount() - 1;
            
            tableModel.setValueAt(toAdd.isActive(), row,
                    kBundle.getString("ACTIVE"));
            tableModel.setValueAt(getRowCount(), row,
                    kBundle.getString("ITEM"));
            tableModel.setValueAt(toAdd.getActualQNum(), row,
                    kBundle.getString("QUESTION"));
            tableModel.setValueAt(toAdd.getAbbreviation() + " " +
                toAdd.getQNum() + " / " + exam.getNumType(toAdd), row,
                kBundle.getString("TYPE"));
            tableModel.setValueAt(toAdd.getMarks(), row,
                    kBundle.getString("MARKS"));
            tableModel.setValueAt(toAdd.getLevel(), row,
                    kBundle.getString("LEVEL"));
            tableModel.setValueAt(toAdd.getCategory(), row,
                    kBundle.getString("CATEGORY"));
            tableModel.setValueAt(toAdd.getImage() != null, row,
                    kBundle.getString("PICTURE"));
            tableModel.setValueAt(toAdd.getText(), row,
                    kBundle.getString("TEXT"));
            
            revalidate();
        }
        else
        {
            tableModel.addRow();
            
            int row = getRowCount() - 1;
            
            tableModel.setValueAt(toAdd.isActive(), row,
                    kBundle.getString("ACTIVE"));
            tableModel.setValueAt(getRowCount(), row,
                    kBundle.getString("ITEM"));
            tableModel.setValueAt(null, row, kBundle.getString("QUESTION"));
            tableModel.setValueAt(toAdd.getAbbreviation(), row,
                    kBundle.getString("TYPE"));
            tableModel.setValueAt(toAdd.getMarks(), row,
                    kBundle.getString("MARKS"));
            tableModel.setValueAt(toAdd.getLevel(), row,
                    kBundle.getString("LEVEL"));
            tableModel.setValueAt(toAdd.getCategory(), row,
                    kBundle.getString("CATEGORY"));
            tableModel.setValueAt(false, row,
                    kBundle.getString("PICTURE"));
            tableModel.setValueAt(toAdd.getText(), row,
                    kBundle.getString("TEXT"));
            
            revalidate();
        }
    }
    
    /**
     * Handles row moves from button clicks
     *
     * @param toMove the row indeces to move
     * @param distance how far to move the rows
     */
    public void moveRow(int[] toMove, int distance)
    {
        int row;
        boolean actionDone = false;
        boolean contiguousBlock;

        //Check if the block is contiguous
        for (row = 1; row < toMove.length
                && toMove[row] == toMove[row - 1] + 1; row++) 
        {
        }
        contiguousBlock = (row == toMove.length);

        // If a contiguous block of rows is selected
        if (contiguousBlock)
        {
            // If the user is moving the items up
            if (distance < 0)
            {
                // If the user is not moving the rows off the top of the table
                if (distance + toMove[0] >= 0)
                {
                    exam.moveGroup(toMove[0], toMove[toMove.length - 1],
                            toMove[0] + distance);
                    actionDone = true;
                }
            }
            // If the user is moving the items down
            else if (distance > 0)
            {
                // If the user is not moving the rows off the bottom of the table
                if (distance + toMove[toMove.length - 1] < getRowCount())
                {

                    exam.moveGroup(toMove[0], toMove[toMove.length - 1],
                            toMove[toMove.length - 1] + distance);
                    actionDone = true;

                }
            }

        }
        else
        {
            //no behavior defined for non-contiguous blocks
        }

        // If the exam was modified
        if ( actionDone )
        {
            refreshTable();
            setSelectionMode(
                    ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            setRowSelectionInterval(toMove[0] + distance, toMove[toMove.length
                    - 1] + distance);
        }
    }
    
    /**
     * Displays the DocumentItemView for a reference in the Exam
     *
     * @param row the index of the selected DocumentItem
     */
    public void showEditPanel(int row)
    {
        DesignViewTopComponent dv = (DesignViewTopComponent)
                WindowManager.getDefault().findTopComponent(
                "DesignViewTopComponent");
        
        // If the user does not want to change the design panel
        if (dv.isChanged())
        {
            DocumentItemView holder = null;
            // If the row is valid
            if (row >= 0 && row < getRowCount())
            {
                setRowSelectionInterval(row, row);
                DocumentItemApi selItem = exam.getItemAt(row);

                // If the item is a question item, get the view
                if (getQuestionItemView(selItem) != null)
                {
                    holder = getQuestionItemView(selItem);
                }

                // If the item is a document item, get the view
                if (getDocumentItemView(selItem) != null)
                {
                    holder = getDocumentItemView(selItem);
                }
            }
            dv.openDocumentItem(holder);
        }
    }

    /**
     * Get the item view associated with the question item
     * @param selItem The selected item
     * @return The view of the question item
     */
    private DocumentItemView getQuestionItemView(DocumentItemApi selItem)
    {
        DocumentItemView holder = null;

        // instantiates the appropriate DocumentItemView type
        if (selItem instanceof MultipleChoiceApi)
        {
            holder = new MultipleChoiceView((MultipleChoiceApi) selItem);
        }
        //If it is a FillInTheBlank item
        else if (selItem instanceof FillInTheBlankApi)
        {
            holder = new FillInTheBlankView((FillInTheBlankApi) selItem);
        }
        //If it is a TrueFalse item
        else if (selItem instanceof TrueFalseApi)
        {
            holder = new TrueFalseView((TrueFalseApi) selItem);
        }
        //If it is a Instructions item
        else if (selItem instanceof InstructionsApi)
        {
            holder = new InstructionsView((InstructionsApi) selItem);
        }
        //If it is a SectionTitle item
        else if (selItem instanceof SectionTitleApi)
        {
            holder = new SectionTitleView((SectionTitleApi) selItem);
        }
        //If it is a FreeResponse item
        else if (selItem instanceof FreeResponseApi)
        {
            holder = new FreeResponseView((FreeResponseApi) selItem);
        }
        //If it is a Matching item
        else if (selItem instanceof MatchingApi)
        {
            holder = new MatchingView((MatchingApi) selItem);
        }
        return holder;
    }

    /**
     * Get the item view associated with the document item
     * @param selItem The selected item
     * @return The view of the document item
     */
    private DocumentItemView getDocumentItemView(DocumentItemApi selItem)
    {
        DocumentItemView holder = null;

        //If it is a Order item
        if (selItem instanceof OrderApi)
        {
            holder = new OrderView((OrderApi) selItem);
        }
        //If it is a Divider item
        else if (selItem instanceof DividerApi)
        {
            holder = new DividerView((DividerApi) selItem);
        }
        //If it is a NumberingRestart item
        else if (selItem instanceof NumberingRestartApi)
        {
            holder = new NumberingRestartView((NumberingRestartApi) selItem);
        }
        //If it is a PageBreak item
        else if (selItem instanceof PageBreakApi)
        {
            holder = new PageBreakView((PageBreakApi) selItem);
        }
        return holder;
    }
    
    /**
     * Method executed when margin changes cause column shifts
     * @param evt The event that triggered column shifts
     */
    @Override
    public void columnMarginChanged(javax.swing.event.ChangeEvent evt)
    {
        super.columnMarginChanged(evt);
        saveState();
    }
    
    /**
     * Method executed when a column is moved
     * @param evt The event that moved a column
     */
    @Override
    public void columnMoved(javax.swing.event.TableColumnModelEvent evt)
    {
        super.columnMoved(evt);
        saveState();
    }
    
    private void saveState()
    {
        // If the ExamTableModel has already attempted to load from kConfig
        if (init)
        {
            try
            {
                File file = new File(kConfig);
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(fos);
                
                Map<String, Integer> widths = new HashMap<String, Integer>();
                String[] names = new String[getColumnCount()];
                
                // For every column save the name and width
                for (int col = 0; col < getColumnCount(); col++)
                {
                    names[col] = getColumnName(col);
                    widths.put(names[col], getColumn(names[col]).getWidth());
                }
                
                out.writeObject(names);
                out.writeObject(widths);
                
                out.close();
            }
            catch (IOException except)
            {
                System.err.println("IOException occurred.");
            }
            catch (NullPointerException ex)
            {
                System.err.println("\"file\" was null.");
            }
        }
    }
    
    /**
     * Method responsible for handling inserts
     * @param evt The even calling the insert
     */
    @Override
    public void actionPerformed(ActionEvent evt) 
    { 
        int sel = getSelectedRow();
        DocumentItemApi item;
           
        item = map.get(evt.getActionCommand());
        // If evt is a valid command
        if ( item != null )
        {
            exam.addItemAt(item.clone(), sel);
            showEditPanel(sel);
        }
    }
}
