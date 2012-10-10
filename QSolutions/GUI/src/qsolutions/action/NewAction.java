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
import org.openide.windows.WindowManager;
import qsolutions.api.ExamApi;
import qsolutions.api.ExamHandlerApi;
import qsolutions.api.NewOpenSaveHandlerApi;
import qsolutions.gui.DesignViewTopComponent;
import qsolutions.gui.PropertiesTopComponent;

/**
 * ActionListener for opening a new Exam
 */
@ActionID(category = "MyFile",
id = "qsolutions.action.NewAction")
@ActionRegistration(iconBase = "img/new.png",
displayName = "qsolutions.action.Bundle#NEW")
@ActionReferences(
{
    @ActionReference(path = "Menu/MyFile", position = NewAction.kMenuPos),
    @ActionReference(path = "Toolbars/File", position = NewAction.kToolbarPos),
    @ActionReference(path = "Shortcuts", name = "D-N")
})
@Messages("CTL_NewAction=qsolutions.action.Bundle#NEW")
public final class NewAction implements ActionListener
{
    /** Position of the action in the menu */
    public static final int kMenuPos = 1300;
    /** Position of the action in the tool bar*/
    public static final int kToolbarPos = 300;

    /**
     * Action handler for opening new Exams
     * @param e unused
     */
    public void actionPerformed(ActionEvent e)
    {
        // If the user wants a new exam, create a new exam
        if( Lookup.getDefault().lookup(NewOpenSaveHandlerApi.class).newExam())
        {
            ((DesignViewTopComponent) WindowManager.getDefault()
                    .findTopComponent("DesignViewTopComponent"))
                    .openDocumentItem(null);
        }
        
        
    }
}
