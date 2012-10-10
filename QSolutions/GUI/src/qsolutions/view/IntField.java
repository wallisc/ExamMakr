/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.view;

import javax.swing.JTextField;
import javax.swing.text.*;

/** TextField that only allows ints to be entered
 *
 * @author Ryan Dollahon
 */
public class IntField extends JTextField
{
    /**
     * Creates an IntField
     */
    public IntField()
    {
        super();
    }
    
    /**
     * Creates an IntField with a quantity of columns
     * @param cols Number of columns
     */
    public IntField( int cols )
    {
        super( cols );
    }
    
    /**
     * Returns a new Document that only takes ints
     * @return new IntDocument
     */
    protected Document createDefaultModel() 
    {
        return new IntDocument();
    }
    
    /**
     * private IntDocument that only allows ints
     */
    private class IntDocument extends PlainDocument
    {
        /**
        * Filters non ints from being input in this Document
        * @param offset offset of insertion
        * @param string the String to insert
        * @param attr the AttributeSet
        * @throws BadLocationException exception for invalid offset 
        */
        @Override
        public void insertString( int offset, String string, AttributeSet attr )
            throws BadLocationException
        {
            try
            {
                Integer.parseInt( string );
                super.insertString( offset, string, attr );
            }
            catch ( NumberFormatException excep )
            {
                System.out.println(excep);
            }
        }
    }
}
