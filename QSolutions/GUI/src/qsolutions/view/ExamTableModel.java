package qsolutions.view;

import java.util.ResourceBundle;
import javax.swing.table.AbstractTableModel;

/**
 * Model for the table that holds the exam
 */
public class ExamTableModel extends AbstractTableModel
{
    
    private static final String kBundleName = "qsolutions/view/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    
    // String [] containing the table's column names
    private String[] columnNames = new String[]
    {
        kBundle.getString("ACTIVE"), kBundle.getString("ITEM"),
        kBundle.getString("QUESTION"), kBundle.getString("TYPE"),
        kBundle.getString("TEXT"), kBundle.getString("MARKS"),
        kBundle.getString("LEVEL"), kBundle.getString("CATEGORY"),
        kBundle.getString("PICTURE")
    };
    // Object[][] containing the table's data
    private Object[][] data = {};
    
    // Object[] with the initial value for new entries in the table
    Object[] initRow()
    {
        Object[] newRow =
        {
            new Boolean(false), null, "", "", "", null, "", "",
            new Boolean(false)
        };
        return newRow;
    }

    /**
     * Adds a new row to the table
     */
    public void addRow()
    {
        int oldTableHeight = getRowCount();
        Object[][] newTable = new Object[oldTableHeight + 1][getColumnCount()];
        // Copy the old data array into the new one
        for (int row = 0; row < oldTableHeight; row++)
        {
            System.arraycopy(data[row], 0, newTable[row], 0, getColumnCount());
        }
        newTable[oldTableHeight] = initRow();
        data = newTable;
    }
    
    /**
     * Delete the current data
     */
    public void emptyTable()
    {
        data = new Object[0][getColumnCount()];
    }
    
    // Class[] with the classes of the table's columns
    private Class[] types = new Class[]
    {
        java.lang.Boolean.class, java.lang.Integer.class, java.lang.String.class,
        java.lang.String.class, java.lang.String.class, java.lang.Integer.class,
        java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
    };
    // boolean[] with true values indicating editable fields
    private boolean[] canEdit = new boolean[]
    {
        true, false, false, false, false, false, false, false, false
    };

    /**
     * Returns the class for columnIndex's field
     *
     * @param columnIndex the column index
     * @return the class for the column's field
     */
    @Override
    public Class getColumnClass(int columnIndex)
    {
        return types[columnIndex];
    }

    /**
     * Returns the number of columns in the table
     *
     * @return the number of columns in the table as an int
     */
    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    /**
     * Returns the number of rows in the table
     *
     * @return the number of rows in the table as an int
     */
    @Override
    public int getRowCount()
    {
        return data.length;
    }

    /**
     * Returns the name of the column at index col
     *
     * @param col the column index
     * @return a String with the name of the column
     */
    @Override
    public String getColumnName(int col)
    {
        return columnNames[col];
    }
    
    /**
     * Returns the array of column names
     * @return columnNames
     */
    public String[] getColumnNames()
    {
        return columnNames;
    }

    /**
     * Returns the Object at row index row in column col from the table
     *
     * @param row the row index
     * @param col the column index
     * @return the Object from the table
     */
    @Override
    public Object getValueAt(int row, int col)
    {
        return data[row][col];
    }

    /**
     * Returns true if the cell is editable, otherwise false
     *
     * @param row not used, all rows are editable
     * @param col the column index of the item
     * @return the boolean from canEdit at column index col
     */
    @Override
    public boolean isCellEditable(int row, int col)
    {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        return canEdit[col];
    }

    /**
     * Sets the Object at row index row and column index col to value
     *
     * @param value the Object to replace the current contents
     * @param row the row index for the table
     * @param col the column index for the table
     */
    @Override
    public void setValueAt(Object value, int row, int col)
    {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
    
    /**
     * Sets the Object at row index row and with column name colName
     * 
     * @param value the Object to replace the current contents
     * @param row the row index for the table
     * @param colName the name of the column header
     */
    public void setValueAt(Object value, int row, String colName)
    {
        boolean done = false;
        // For each column
        for(int itr = 0; itr < columnNames.length && !done; itr++)
        {
            // If the colName matches the column name
            if (colName.equals(getColumnName(itr)))
            {
                setValueAt(value, row, itr);
                done = true;
            }
        }
    }
}
