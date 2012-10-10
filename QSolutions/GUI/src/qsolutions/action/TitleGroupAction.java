package qsolutions.action;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle.Messages;

/**
 * ActionListener to display title group
 * @author Ryan Dollahon
 */
@ActionID(category = "Exam",
id = "qsolutions.action.TitleGroupAction")
@ActionRegistration(iconBase = "img/titlegroup.png",
displayName = "qsolutions.action.Bundle#TITLEGROUP")
@ActionReferences(
{
    @ActionReference(path = "Menu/Exam", position = TitleGroupAction.kMenuPos),
    @ActionReference(path = "Toolbars/File", position = TitleGroupAction.kToolbarPos)
})
@Messages("CTL_TitleGroupAction=qsolutions.action.Bundle#TITLEGROUP")
public final class TitleGroupAction implements ActionListener
{    
    /** Position of the action in the menu */
    public static final int kMenuPos = 3333;
    /** Position of the action in the tool bar */
    public static final int kToolbarPos = 500;
    
    private static final String kBundleName = "qsolutions/action/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    
    /**
     * Action handler for displaying the title group
     * @param e unused
     */
    public void actionPerformed(ActionEvent e)
    {
        JDialog dialog = new JDialog((Frame)null,
                kBundle.getString("EXAM TITLE GROUP"));
        qsolutions.view.TitleGroupDesignPanel dPane = new 
                qsolutions.view.TitleGroupDesignPanel(dialog);
        dialog.getContentPane().add(dPane);
        dialog.pack();
        //dialog.setSize(dPane.getPreferredSize());
        dialog.setVisible(true);
    }
}
