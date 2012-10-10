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
import qsolutions.api.PrintPdfApi;

/**
 * ActionListener to export to PDF
 */
@ActionID(category = "MyFile",
id = "qsolutions.action.ExportPdfAction")
@ActionRegistration(iconBase = "img/pdf.png",
displayName = "qsolutions.action.Bundle#EXPORTPDF")
@ActionReferences(
{
    @ActionReference(path = "Menu/MyFile/Export", 
        position = ExportPdfAction.kMenuPos)
})
@Messages("CTL_ExportPdfAction=qsolutions.action.Bundle#EXPORTPDF")
public final class ExportPdfAction implements ActionListener
{
    /** Position of the action in the menu */
    public final static int kMenuPos = 3333;
    
    /**
     * Generates the pdf when the action is performed
     * @param e unused
     */
    public void actionPerformed(ActionEvent e)
    {
        Lookup.getDefault().lookup(PrintPdfApi.class).generatePDF();
    }
}
