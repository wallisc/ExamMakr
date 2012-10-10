/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

/**
 * ActionListener for displaying help contents
 */
@ActionID(category = "MyHelp",
id = "qsolutions.action.HelpContentsAction")
@ActionRegistration(displayName = "qsolutions.action.Bundle#HELPCONTENTS")
@ActionReferences(
{
    @ActionReference(path = "Menu/MyHelp", position = HelpContentsAction.kMenuPos)
})
@Messages("CTL_HelpContentsAction=qsolutions.action.Bundle#HELPCONTENTS")
public final class HelpContentsAction implements ActionListener
{
    /** Position of the action in the menu */
    public static final int kMenuPos = -102;
    
    private static final String kBundleName = "qsolutions/action/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    
    /**
     * Action handler for generating help content
     * @param e unused
     */
    public void actionPerformed(ActionEvent e)
    {
        JOptionPane.showMessageDialog(null, new qsolutions.view.HelpContents(),
                kBundle.getString("HELP"), -1);
    }
}
