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

/**
 * ActionListener to display the credits
 */
@ActionID(category = "MyHelp",
id = "qsolutions.action.CreditsAction")
@ActionRegistration(displayName = "qsolutions.action.Bundle#CREDITS")
@ActionReferences(
{
    @ActionReference(path = "Menu/MyHelp", position = CreditsAction.kMenuPos)
})
@Messages("CTL_CreditsAction=qsolutions.action.Bundle#CREDITS")
public final class CreditsAction implements ActionListener
{
    /** Position of the action in the menu */
    public static final int kMenuPos = -100;
    
    private static final String kBundleName = "qsolutions/action/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    
    /**
     * Action handler for displaying credits
     * @param e unused
     */
    public void actionPerformed(ActionEvent e)
    {
        JOptionPane.showMessageDialog(null, new qsolutions.view.Credits(),
                kBundle.getString("CREDITS"), -1);
    }
}
