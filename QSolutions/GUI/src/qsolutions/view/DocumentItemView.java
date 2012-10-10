/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.view;

import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import org.openide.util.Lookup;
import org.openide.windows.WindowManager;
import qsolutions.api.DocumentItemApi;
import qsolutions.api.ExamApi;
import qsolutions.exam.Driver;
import qsolutions.exam.Question;
import qsolutions.gui.TableViewTopComponent;


/**
 * Panel for updating and adding DocumentItems to the exam, parent for all the
 * other DesignPanels
 *
 * @author Ryan Dollahon
 */
public abstract class DocumentItemView extends javax.swing.JPanel
    implements Observer, KeyListener, FocusListener
{
    // boolean flag indicating true if the panel exists in the exam
    private boolean editView;
    // The DocumentItem being worked on in the panel
    protected DocumentItemApi item;
    private static final int kBufferHeight = 30;
    protected boolean imageChanged;
    
    
    private static final String kBundleName = "qsolutions/view/Bundle";
    protected static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    
    /**
     * Sets up observable
     */
    public DocumentItemView()
    {
        Lookup.getDefault().lookup(ExamApi.class).addObserver(this);
        imageChanged = false;
    }
    
    /**
     * Updates when observable is triggered
     * @param obs The object being observed
     * @param args The arguments
     */
    @Override
    public void update(Observable obs, Object args)
    {
        // If the view has been edited, the fields are filled
        if ( isEditView() )
        {
            fillFields(item);
        }
    }
    
    /**
     * Indicates if the DocumentItem is new or being edited
     *
     * @return false if the DocumentItem has not been added to the exam,
     * otherwise true
     */
    public final boolean isEditView()
    {
        return editView;
    }

    /**
     * Sets the flag indicating that item is in the exam
     *
     * @param val the value set to editView
     */
    public final void setEditView(boolean val)
    {
        editView = val;
    }

    /**
     * Returns a String of the DocumentItem type of item
     *
     * @return a String of the DocumentItem type of item
     */
    public final String getType()
    {
        return item.getType();
    }

    /**
     * Fills the gui from item, sets editView to true
     */
    protected final void fillFields()
    {
        fillFields(item);
    }
    
    /**
     * Fills the gui from inItem, sets editView to true
     *
     * @param inItem the item to load information from
     */
    protected abstract void fillFields(DocumentItemApi inItem);

    /**
     * Returns a boolean indicating if the gui representation still matches item
     *
     * @return false if item matches extractItem(), otherwise false
     */
    public boolean isDifferent()
    {
        //return item != null && !item.equals(extractItem());
        // If there are no changes to the questions
        if ( item == null )
        {
            return false;
        }
        // if the items are equal they aren't different
        if ( item.equals(extractItem() ) )
        {
            //javax.swing.JOptionPane.showMessageDialog(null, "String: \"" + 
            //item.getText() + "\"" + "\nString: \"" + 
            //extractItem().getText() + "\"");
            return false;       
        }
        else
        {

            DocumentItemApi extrctItem = extractItem();
            Driver driver = new Driver();
            // If both texts are filled with only tags (aka just an empty 
            // string). 
            if (driver.compareExtracted(extrctItem, item))
            {
                extrctItem.setText(item.getText());
                //ATTEMPT at fixing question issues
                if(extrctItem instanceof Question && 
                        driver.compareExtractedStrings(
                        ((Question)extrctItem).getAnswer(), 
                        ((Question)item).getAnswer()))
                {
                    ((Question)extrctItem).setAnswer(((Question)item).getAnswer());
                }
                return !item.equals(extrctItem);
            }
            return true;
        }
    }

    /**
     * Returns a DocumentItem constructed from the fields in the gui
     *
     * @return DocumentItem constructed from the gui fields
     */
    protected abstract DocumentItemApi extractItem();

    /**
     * Stores item appropriately in exam
     */
    public void addItem()
    {
        DocumentItemApi toAdd = extractItem();
        ExamApi exam = Lookup.getDefault().lookup(ExamApi.class);
        // If adding a question
        if (this instanceof QuestionView )
        {
            addItemQuestionsView(toAdd);
        }
        // If the DocumentItemView is an InstructionsView
        else if (this instanceof InstructionsView)
        {
            addItemInstructionsView(toAdd);
        }
        // if isEditView save changes to the exam
        if ( isEditView() )
        {
            exam.replaceItem(item, toAdd);
            TableViewTopComponent tv = (TableViewTopComponent) WindowManager
                    .getDefault().findTopComponent("TableViewTopComponent");
            //If the table exists, clear the selection status
            if (tv != null)
            {
                tv.clearSelection();
            }
        }
        // otherwise adds the item to the exam
        else 
        {
            exam.addItem(toAdd);
        }
        setEditView(false);
    }

    private void addItemQuestionsView(DocumentItemApi toAdd)
    {
        File image;
        // If the image has been changed
        if ( imageChanged )
        {
            try
            {
                image = ((QuestionView)this).getImageFile();
                // If the question is associated with an image
                if (image != null)
                {
                    toAdd.setImage(image);
                }
            }
            catch(IOException e)
            {
                JOptionPane.showMessageDialog(null, "Question contains a bad "
                        + "file");
            }
        }
        else 
        {
            toAdd.setShallowImage(item);
        }
    }
    
    private void addItemInstructionsView(DocumentItemApi toAdd)
    {
        File image;
        // If the image has been changed
        if ( imageChanged )
        {
            try
            {
                image = ((InstructionsView)this).getImageFile();
                // If the question is associated with an image
                if (image != null)
                {
                    toAdd.setImage(image);
                }
            }
            catch(IOException e)
            {
                JOptionPane.showMessageDialog(null, "Instruction contains a "
                        + "bad file");
            } 
        }
        else 
        {
            toAdd.setShallowImage(item);
        }            
    }

    /**
     * Returns the documentItemApi
     * @return item
     */
    public DocumentItemApi getItem()
    {
        return item;
    }
    
    /**
     * Unused keyListener method.
     * @param e the keyEvent that we ignore
     */
    public void keyReleased(KeyEvent e)
    {

    }
    
    /**
     * Unused keyListener method.
     * @param e the keyEvent that we ignore
     */
    public void keyTyped(KeyEvent e)
    {
        
    }
    
    /**
     * Determines which button corresponds with a provided hot-key event.
     * @param e the keyEvent generated by the user
     */
    public void keyPressed(KeyEvent e)
    {
        //If control b is pressed, click bold
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_B)
        {
            boldAction();
        }
        //If control i is pressed, click italics
        else if (e.isControlDown()&& e.getKeyCode() == KeyEvent.VK_I)
        {
            italicAction();
        }
        //If control u is pressed, click underline
        else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_U)
        {
            underlineAction();
        }
    }
    
    /**
     * Triggers clicking the bold WYSYWIG button via keyboard shortcuts. Should
     * be overriden if WYSYWIG functionality is desired for that particular 
     * view.
     */
    public void boldAction()
    {
        
    }
    
    /**
     * Triggers clicking the italics WYSYWIG button via keyboard shortcuts. 
     * Should be overriden if WYSYWIG functionality is desired for that 
     * particular view.
     */
    public void italicAction()
    {
        
    }
    
    /**
     * Triggers clicking the underline WYSYWIG button via keyboard shortcuts. 
     * Should be overriden if WYSYWIG functionality is desired for that 
     * particular view.
     */
    public void underlineAction()
    {
        
    }
    
    /**
     * Set scroll pane position when in focus
     * @param e focus event
     */
    @Override
    public void focusGained(FocusEvent e)
    {
        Rectangle newFocusBox = e.getComponent().getBounds();
        newFocusBox.y += kBufferHeight; 
        this.scrollRectToVisible(newFocusBox);
    }
    
    
    /**
     * Set scroll pane position when lost focus
     * @param e focus event
     */
    @Override
    public void focusLost(FocusEvent e) 
    {
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
