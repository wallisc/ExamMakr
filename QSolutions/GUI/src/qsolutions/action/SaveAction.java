/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import qsolutions.api.NewOpenSaveHandlerApi;

/**
 * ActionListener for saving exams
 * @author Ryan Dollahon
 */
@ActionID(category = "MyFile",
id = "qsolutions.action.SaveAction")
@ActionRegistration(iconBase = "img/save.png",
displayName = "qsolutions.action.Bundle#SAVE")
@ActionReferences(
{
    @ActionReference(path = "Menu/MyFile", position = SaveAction.kMenuPos),
    @ActionReference(path = "Toolbars/File", position = SaveAction.kToolbarPos)
})
@Messages("CTL_SaveAction=qsolutions.action.Bundle#SAVE")
public final class SaveAction implements ActionListener
{
    /** Position of the action in the menu */
    public static final int kMenuPos = 1375;
    /** Position of the action in the tool bar */
    public static final int kToolbarPos = 326;
    
    /**
     * Action handler to save exams
     * @param e unused
     */
    public void actionPerformed(ActionEvent e)
    {
        Lookup.getDefault().lookup(NewOpenSaveHandlerApi.class).save();
    }
}
