/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.action;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.JDialog;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle.Messages;

/**
 * ActionListener for setting up Header and Footer
 */
@ActionID(category = "Exam",
id = "qsolutions.action.HeaderFooterAction")
@ActionRegistration(iconBase = "img/headerfooter.png",
displayName = "qsolutions.action.Bundle#HEADERFOOTER")
@ActionReferences(
{
    @ActionReference(path = "Menu/Exam", position = HeaderFooterAction.kMenuPos),
    @ActionReference(path = "Toolbars/File", position = HeaderFooterAction.kToolbarPos)
})
@Messages("CTL_HeaderFooterAction=qsolutions.action.Bundle#HEADERFOOTER")
public final class HeaderFooterAction implements ActionListener
{
    /** Position of the action in the menu */
    public static final int kMenuPos = 3333;
    /** Position of the action in the tool bar */
    public static final int kToolbarPos = 500;
    
    private static final String kBundleName = "qsolutions/action/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    
    /**
     * Action handler for the header and footer
     * @param e unused
     */
    public void actionPerformed(ActionEvent e)
    {
        JDialog dialog = new JDialog((Frame)null,
                kBundle.getString("EXAM TITLE GROUP"));
        qsolutions.view.HeaderFooterView dPane = new 
                qsolutions.view.HeaderFooterView(dialog);
        dialog.getContentPane().add(dPane);
        dialog.setSize(dPane.getPreferredSize());
        dialog.setVisible(true);
    }
}
