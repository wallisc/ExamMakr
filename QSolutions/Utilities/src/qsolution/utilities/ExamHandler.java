package qsolution.utilities;

import java.util.ResourceBundle;
import org.openide.util.lookup.ServiceProvider;
import org.openide.windows.WindowManager;
import qsolutions.api.ExamHandlerApi;

/**
 * Determines if the exam has been edited and saves these values.
 */
@ServiceProvider(service = ExamHandlerApi.class)
public class ExamHandler implements ExamHandlerApi
{
    private boolean edited;

    /**
     * Default constructor that automatically sets the exam as unedited
     */
    public ExamHandler()
    {
        edited = false;
    }
    /**
     * Set the exam to be edited/unedited
     * @param nEdited whether the exam is edited
     */
    public void setEdited( boolean nEdited )
    {
        edited = nEdited;
        // If the exam has been edited
        if (edited)
        {
            String title = WindowManager.getDefault().getMainWindow().getTitle();
            // If the exam title in the name is marked (to show it's been 
            // edited
            if (!title.startsWith("*"))
            {
                WindowManager.getDefault().getMainWindow().setTitle("*" + title);
            }
        }
        else
        {
            String title = WindowManager.getDefault().getMainWindow().getTitle();
            // If the exam title in the name is marked (to show it's been 
            // edited
            if (title.startsWith("*"))
            {
                WindowManager.getDefault().getMainWindow().setTitle(title.substring(1));
            }
        }
    }
   
    /**
      * Return whether the exam has been edited
      * @return whether the exam has been edited
      */
    public boolean getEdited()
    {
        return edited;
    }
   
}
