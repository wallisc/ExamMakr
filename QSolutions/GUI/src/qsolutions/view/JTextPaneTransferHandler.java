package qsolutions.view;

//Import for createTextPanes
import javax.swing.JTextPane;
//Import for initialize
import javax.swing.text.StyledDocument;
//Import for setContent
import javax.swing.text.BadLocationException;
//Import for StringTransferHandler
import javax.swing.TransferHandler;
import javax.swing.JComponent;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.Clipboard;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
//Import for StringTransferSelection
import javax.swing.text.DefaultStyledDocument;

/**
 * A transfer handler for copy and paste from external sources.
 * @author Gudradain, mmease
 * Most of this source was taken from Gudrarain.
 */
class JTextPaneTransferHandler extends TransferHandler
{
     
    public JTextPaneTransferHandler()
    {
    }
     
    public boolean canImport(JComponent comp, DataFlavor[] transferFlavors)
    {
        JTextPaneSelection sel = new JTextPaneSelection(""); //Dummy object
        boolean retour = false;
        //For each known flavor, check if the current flavor matches (>=1)
        for(int cFlav = 0; cFlav<transferFlavors.length; cFlav++)
        {
            //If the flavor is supported, we can import
            if( sel.isDataFlavorSupported(transferFlavors[cFlav]) )
            {
                retour = true;
            }
        }
        return retour;
    }
     
    protected Transferable createTransferable(JComponent c)
    {
        JTextPane aTextPane = (JTextPane)c;
        int start = aTextPane.getSelectionStart();
        int end = aTextPane.getSelectionEnd();
         
        StyledDocument aSDoc = aTextPane.getStyledDocument();
        DefaultStyledDocument dSDocSelection = copyDocument(aSDoc, start, end);
        return (new JTextPaneSelection(dSDocSelection));
    }
     
    /**
     * Copy the document
     * @param src the document to be copied
     * @param selectionStart the position to begin copying
     * @param selectionEnd the position to end copying
     * @return 
     */
    public static DefaultStyledDocument copyDocument(StyledDocument src, 
            int selectionStart, int selectionEnd) 
    { 
        Element rootElement, paragraphElement, textElement; 
        SimpleAttributeSet copyAttrs;
        int startOffset, endOffset;
        String copyString;
         
        rootElement = src.getDefaultRootElement();
        DefaultStyledDocument copyDoc = new DefaultStyledDocument();
        //For each paragraph, prepare its elements for restyling
        for (int lpParagraph = 0; lpParagraph<rootElement.getElementCount(); 
                lpParagraph++)
        {
            paragraphElement = rootElement.getElement(lpParagraph);
             
            //Check if the paragraph need to be copy
            if(paragraphElement.getEndOffset() < selectionStart)
            {
                continue; //Go to the next paragraph
            }
            //Ensure that the text is not empty
            if(paragraphElement.getStartOffset() > selectionEnd)
            {
                break; //Exit the boucle
            }
            //For each element, restyle the text
            for (int lpText = 0; lpText<paragraphElement.getElementCount();
                    lpText++) 
            {
                //Insert a Element in the new Document    
                textElement = paragraphElement.getElement(lpText);
                 
                //Check if the Element need to be copied
                if( textElement.getEndOffset() < selectionStart )
                {
                    continue; //Go to the next Element
                }
                //ensure the string is not empty
                if( textElement.getStartOffset() > selectionEnd )
                {
                    break; //Exit the boucle
                }
                 
                copyAttrs = new SimpleAttributeSet(textElement.getAttributes());
                 
                //Find the value of startOffset and endOffset
                if(textElement.getStartOffset() < selectionStart)
                {
                    startOffset = selectionStart;
                }
                else
                {
                    startOffset = textElement.getStartOffset();
                }
                //ensure the string is not empty
                if( textElement.getEndOffset() > selectionEnd )
                {
                    endOffset = selectionEnd;
                }
                else
                {
                    endOffset = textElement.getEndOffset();
                }
                     
                try
                {
                    copyString = src.getText(startOffset, 
                            (endOffset-startOffset));
                    copyDoc.insertString(copyDoc.getLength(), 
                            copyString, copyAttrs);
                }
                catch (BadLocationException e)
                {
                    e.printStackTrace();
                }
            }
            //Modify the Style of the paragraph
            copyAttrs = new SimpleAttributeSet(
                    paragraphElement.getAttributes());
            startOffset = paragraphElement.getStartOffset();
            endOffset = paragraphElement.getEndOffset();
             
            copyDoc.setParagraphAttributes(startOffset, (endOffset-startOffset), 
                    copyAttrs, true);
        }
       
        return copyDoc;
    }
     
    public void exportToClipboard(JComponent comp, Clipboard clip, int action) 
    {
        super.exportToClipboard(comp, clip, action);
    }
     
    public int getSourceActions(JComponent c) 
    {
        return COPY_OR_MOVE;
    }
     
    protected void exportDone(JComponent source, Transferable data, int action)
    {
        JTextPane srcTextPane = (JTextPane)source;
        //If we are simply moving, the original text is deleted
        if(action == MOVE)
        {
            srcTextPane.replaceSelection("");
        }
    }
     
    public boolean importData(JComponent comp, Transferable t)
    {
        //If the 
        if( canImport(comp, t.getTransferDataFlavors()) )
        {
            //If the flavor of this data is supported, translate it
            if( t.isDataFlavorSupported(JTextPaneSelection.kDSDocFlavor) )
            {
                JTextPane theTextPane = (JTextPane)comp;
                theTextPane.replaceSelection("");
                StyledDocument theSDoc = new DefaultStyledDocument();
                try
                {
                    theSDoc = (StyledDocument)t.getTransferData(
                            JTextPaneSelection.kDSDocFlavor);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                int thePos = theTextPane.getCaretPosition();
                insertDocument(theSDoc, thePos, theTextPane);
                return true;
                 
            }
            else
            {
                String textString = "";
                try
                {
                    textString = (String)t.getTransferData(
                            DataFlavor.stringFlavor);
                    JTextPane aTextPane = (JTextPane)comp;
                    aTextPane.replaceSelection(textString);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }
     
    //Insert a Document in a another Document
    public static void insertDocument(StyledDocument srcSDoc, 
            int srcPos, JTextPane theTextPane)
    {
        StyledDocument theSDoc = theTextPane.getStyledDocument();
        Element rootElement, paragraphElement, textElement;
        SimpleAttributeSet copyAttrs;
        int startOffset, endOffset;
        int pos = srcPos;
        String copyString;
         
        rootElement = srcSDoc.getDefaultRootElement();
        //For each paragraph, analyze and reformat every element
        for (int lpParagraph = 0; lpParagraph<rootElement.getElementCount(); 
                lpParagraph++)
        {
            paragraphElement = rootElement.getElement(lpParagraph);
             //For each element, insert text into styled document
            for (int lpText = 0; lpText<paragraphElement.getElementCount(); 
                    lpText++)
            {
                textElement = paragraphElement.getElement(lpText);
                copyAttrs = new SimpleAttributeSet(textElement.getAttributes());
                startOffset = textElement.getStartOffset();
                endOffset = textElement.getEndOffset();
                //A Verifier
                try
                {
                    copyString = srcSDoc.getText(startOffset, 
                            (endOffset-startOffset));
                    theSDoc.insertString(pos, copyString, copyAttrs);
                }
                catch (BadLocationException e)
                {
                    e.printStackTrace();
                }
                pos += endOffset-startOffset;
            }
            //Modify the Style of the paragraph
            copyAttrs = new SimpleAttributeSet(
                    paragraphElement.getAttributes());
            startOffset = paragraphElement.getStartOffset();
            endOffset = paragraphElement.getEndOffset();
             
            theSDoc.setParagraphAttributes(startOffset, (endOffset-startOffset), 
                    copyAttrs, true);
        }
        try
        {
            theSDoc.remove(pos-1, 1);
        }
        catch(BadLocationException e)
        {
            e.printStackTrace();
        }
    }
     
    //END OF THE CLASS StringTransferHandler
}