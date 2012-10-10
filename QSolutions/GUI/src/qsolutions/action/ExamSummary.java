/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.action;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle.Messages;
import qsolutions.view.ExamSummaryView;

/**Action?*/
//BEGIN-GEN
@ActionID(category = "Summary",
id = "qsolutions.action.ExamSummary")
@ActionRegistration(displayName = "qsolutions.action.Bundle#SUMMARY")
@ActionReferences(
{
    @ActionReference(path = "Menu/Exam", position = ExamSummary.kMenuPos)
})
@Messages("CTL_ExamSummary=qsolutions.action.Bundle#SUMMARY")
//END-GEN
public final class ExamSummary implements ActionListener
{
    /** Position of the action in the menu */
    public final static int kMenuPos = 3233;
    /**
     * Exam Summary Action
     * @param e action event
     */
    public void actionPerformed(ActionEvent e)
    {
        JDialog dialog = new JDialog((Frame)null, "Exam Title Group");
        ExamSummaryView dPane = new 
                ExamSummaryView(dialog);
        dialog.getContentPane().add(dPane);
        dialog.setSize(dPane.getPreferredSize());
        dialog.setVisible(true);
    }
}
