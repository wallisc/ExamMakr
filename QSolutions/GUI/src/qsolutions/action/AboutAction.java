package qsolutions.action;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

/**
 * ActionListener to display the about information
 */
@ActionID(category = "MyHelp",
id = "qsolutions.action.AboutAction")
@ActionRegistration(displayName = "qsolutions.action.Bundle#ABOUT")
@ActionReferences(
{
    @ActionReference(path = "Menu/MyHelp", position = AboutAction.kMenuPos)
})
@Messages("CTL_AboutAction=qsolutions.action.Bundle#ABOUT")
public final class AboutAction implements ActionListener
{
    /** Position of the action in the menu */
    public static final int kMenuPos = -101;
    
    private static final String kBundleName = "qsolutions/action/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    
    /**
     * Launches a window when the "about" menu item is clicked
     * @param e unused
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        JOptionPane.showMessageDialog(
                null, new qsolutions.view.AboutExam(), kBundle.getString("ABOUT"), -1);
    }
}
