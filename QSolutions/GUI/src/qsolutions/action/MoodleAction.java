/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import qsolutions.api.MoodleHandlerApi;

/**
 * ActionListener to export to Moodle
 */
@ActionID(category = "MyFile",
id = "qsolutions.action.MoodleAction")
@ActionRegistration(displayName = "#CTL_MoodleAction")
@ActionReferences(
{
    @ActionReference(path = "Menu/MyFile/Export", position = MoodleAction.kMenuPos)
})
@Messages("CTL_MoodleAction=Moodle")
public final class MoodleAction implements ActionListener
{
    /** Position of the action in the menu */
    public static final int kMenuPos = 3433;

    /**
     * Generates Moodle XML when the action is performed
     * @param e unused
     */
    public void actionPerformed(ActionEvent e)
    {
        Lookup.getDefault().lookup(MoodleHandlerApi.class).export();
    }
}
