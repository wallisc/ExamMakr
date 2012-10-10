package qsolutions.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.LifecycleManager;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import qsolutions.view.CustomLifecycleManager;

/**
 * ActionListener to display the about information
 */
@ActionID(category = "MyFile",
id = "qsolutions.action.ExitAction")
@ActionRegistration(displayName = "qsolutions.action.Bundle#EXIT")
@ActionReferences(
{
    @ActionReference(path = "Menu/MyFile", position = ExitAction.kMenuPos)
})
@Messages("CTL_ExitAction=qsolutions.action.Bundle#EXIT")
public final class ExitAction implements ActionListener
{
    /** Position of the action in the menu */
    public static final int kMenuPos = 2001;
    
    /**
     * Launches a window when the "exit" menu item is clicked
     * @param e unused
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        LifecycleManager lm = (LifecycleManager) Lookup.getDefault().lookup(
                LifecycleManager.class);
        lm.exit();
    }
}