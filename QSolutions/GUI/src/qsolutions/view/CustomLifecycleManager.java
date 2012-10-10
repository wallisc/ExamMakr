package qsolutions.view;

import java.util.Collection;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import org.openide.LifecycleManager;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import qsolutions.api.ExamHandlerApi;
import qsolutions.api.NewOpenSaveHandlerApi;

/**
 * Custom LifecycleManager to exit properly
 * @author Ryan Dollahon
 */
@ServiceProvider(service = LifecycleManager.class, position = 1)
public class CustomLifecycleManager extends LifecycleManager
{
    private static final String kBundleName = "qsolutions/view/Bundle";
    private static final String kExamEditPrompt =
            "THE EXAM HAS BEEN EDITED BUT NOT SAVED, WOULD YOU LIKE TO SAVE?";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    
    /**
     * Accessor to CustomLifecycleManager from lookup
     * @return the CustomLifecycleManager
     */
    public static LifecycleManager getDefault()
    {
        LifecycleManager lm = (LifecycleManager) Lookup.getDefault().lookup(
                LifecycleManager.class);
        
        // If the LifecycleManager has not been initialized
        if (lm == null) 
        {
            lm = new CustomLifecycleManager();
        }
 
        return lm;
    }

    /**
     * Method for properly saving all data
     */
    @Override
    public void saveAll()
    {
    }

    /**
     * Method that exits the running program
     */
    @Override
    public void exit()
    {
        int confirm;
        boolean ext = true;
        
        // If the exam has been edited
        if (Lookup.getDefault().lookup(ExamHandlerApi.class).getEdited())
        {
            confirm = JOptionPane.showConfirmDialog(null,
                    kBundle.getString(kExamEditPrompt),
                    kBundle.getString("CONFIRM CHANGES"),
                    JOptionPane.YES_NO_CANCEL_OPTION);
            ext = confirm != JOptionPane.CANCEL_OPTION;
            //If the yes option was selected
            if (confirm == JOptionPane.YES_OPTION)
            {
                ext = Lookup.getDefault().lookup(NewOpenSaveHandlerApi.class).save();
            }
        }
        
        // If the user does not want to make changes exit
        if (ext)
        {
            Lookup.getDefault().lookup(ExamHandlerApi.class).setEdited(false);
            Collection<LifecycleManager> col = 
                    Lookup.getDefault().lookup(
                    new Lookup.Template(LifecycleManager.class)).allInstances();
            
            // for each LifecycleManager in  col
            for (LifecycleManager lm : col)
            {
                // if lm is not this LifecycleManager
                if (lm != this)
                {
                    lm.exit();
                }
            }
        }
    }
}
