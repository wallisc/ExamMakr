/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import qsolutions.api.NewOpenSaveHandlerApi;

/**
 * ActionListener for saveAs for exams
 * @author Ryan Dollahon
 */
@ActionID(category = "myFile",
id = "qsolutions.action.SaveAsAction")
@ActionRegistration(iconBase = "img/saveas.png",
displayName = "qsolutions.action.Bundle#SAVEAS")
@ActionReferences(
{
    @ActionReference(path = "Menu/MyFile", position = SaveAsAction.kMenuPos),
    @ActionReference(path = "Toolbars/File", position = SaveAsAction.kToolbarPos)
})
@Messages("CTL_SaveAsAction=qsolutions.action.Bundle#SAVEAS")
public final class SaveAsAction implements ActionListener
{
    private static final String kBundleName = "qsolutions/action/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    
    /** Position of the action in the menu */
    public static final int kMenuPos = 1375;
    /** Position of the action in the tool bar */
    public static final int kToolbarPos = 327;
    /** Tool tip for the action */
    public static final String kToolTip = kBundle.getString("SAVE AS");
    
    
    /**
     * Action handler for saveAs action
     * @param e unused
     */
    public void actionPerformed(ActionEvent e)
    {
        Lookup.getDefault().lookup(NewOpenSaveHandlerApi.class).saveAs();
    }
}
