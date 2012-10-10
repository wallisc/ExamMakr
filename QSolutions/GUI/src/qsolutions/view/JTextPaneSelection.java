package qsolutions.view;

import java.awt.datatransfer.*;
import java.io.IOException;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

/**
 * Serves as an intermediate between the transfer handler and the jTextPane.
 * @author Gudradain, mmease
 * Most of this source was taken from Gudrarain.
 */
class JTextPaneSelection implements Transferable, ClipboardOwner
{
    private String dataString;
    private DefaultStyledDocument dataDoc;
    /** The flavor alternative to a string */
    public final static DataFlavor kDSDocFlavor = new DataFlavor(
            DefaultStyledDocument.class, "DSDocFlavor");
    private DataFlavor [] supportedFlavors = {kDSDocFlavor, 
        DataFlavor.stringFlavor};
     
    //Usefull for Dummy StringTransferSelection Object
    public JTextPaneSelection(String dataString)
    {
        this.dataString = dataString;
    }
     
    public JTextPaneSelection(DefaultStyledDocument dataDoc)
    {
        this.dataDoc = dataDoc;
        try
        {
            dataString = dataDoc.getText(0, dataDoc.getLength());
        }
        catch(BadLocationException e)
        {
            e.printStackTrace();
        }
    }
     
    public DataFlavor[] getTransferDataFlavors()
    {
        return supportedFlavors;
    }
     
    public boolean isDataFlavorSupported(DataFlavor flavor)
    {
        //If the given flavor can be matched to a string, it is supported
        if( flavor.equals(DataFlavor.stringFlavor) )
        {
            return true;
        }
        //If the given flavor can be matched to a DSDocFl, it is supported
        if( flavor.equals(kDSDocFlavor) )
        {
            return true;
        }
        return false;
    }
     
    /**
    * Gets the appropriate transfer data for transfer from jTextPane to/from 
    * external source.
    * @return The document containing the appropriate transfer data.
    */
    public Object getTransferData(DataFlavor flavor) throws 
            UnsupportedFlavorException, IOException
    {
        //Check if we can covert to a string
        if( flavor.equals(DataFlavor.stringFlavor) )
        {
            return dataString;
        }
        //check if we can covert to a dsdoc
        else if( flavor.equals(kDSDocFlavor) )
        {
            return dataDoc;
        }
        else
        {
            throw new UnsupportedFlavorException(flavor);
        }
    }
     
    public void lostOwnership(Clipboard clipboard, Transferable contents)
    {
    }
}