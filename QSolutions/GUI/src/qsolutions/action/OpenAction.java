/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileFilter;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;
import qsolutions.api.*;
import qsolutions.gui.DesignViewTopComponent;
import qsolutions.gui.DocumentViewTopComponent;
import qsolutions.gui.PropertiesTopComponent;

/**
 * ActionListener for opening Exams
 */
@ActionID(category = "MyFile",
id = "qsolutions.action.OpenAction")
@ActionRegistration(iconBase = "img/open.png",
displayName = "qsolutions.action.Bundle#OPEN")
@ActionReferences(
{
    @ActionReference(path = "Menu/MyFile", position = OpenAction.kMenuPos),
    @ActionReference(path = "Toolbars/File", position = OpenAction.kToolbarPos)
})
@Messages("CTL_OpenAction=qsolutions.action.Bundle#OPEN")
public final class OpenAction implements ActionListener
{
    /** Position of the action in the menu */
    public static final int kMenuPos = 1350;
    /** Position of the action in the tool bar */
    public static final int kToolbarPos = 325;
    /** Tool tip text for the action */
    public static final String kToolTip = "ok";
    
    /**
     * Action handler for opening Exams
     * @param e unused
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //If the exam was successfully opened clear the design view
        if (Lookup.getDefault().lookup(NewOpenSaveHandlerApi.class).open())
        {
            ((DesignViewTopComponent) WindowManager.getDefault()
                    .findTopComponent("DesignViewTopComponent"))
                    .openDocumentItem(null);
            PropertiesTopComponent ptc = (PropertiesTopComponent) 
                    WindowManager.getDefault().findTopComponent(
                    "PropertiesTopComponent");
        }
    }
}
